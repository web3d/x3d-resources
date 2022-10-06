import vrml.*;
import vrml.field.*;
import vrml.node.*;

import java.applet.*;
import java.lang.*;
import java.net.*;

import jp.co.lattice.vProcessor.base.*;		// Java XVL3 Processor package
import jp.co.lattice.vProcessor.com.*;		// Java XVL3 Processor package
import jp.co.lattice.vProcessor.node.*;		// Java XVL3 Processor package
import jp.co.lattice.vProcessor.parse.*;	// Java XVL3 Processor package
import jp.co.lattice.vKernel.core.b0.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.core.c0.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.core.g0.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.core.t0.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.greg.c0g.*;	// Java XVL3 Kernel package
import jp.co.lattice.vKernel.greg.g0g.*;	// Java XVL3 Kernel package
import jp.co.lattice.vKernel.greg.t0g.*;	// Java XVL3 Kernel package
import jp.co.lattice.vKernel.tex.a0.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.tex.a0g.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.tex.c0a.*;		// Java XVL3 Kernel package
import jp.co.lattice.vKernel.texEx.a0x.*;	// Java XVL3 Kernel package

/**
 * VRML 2.0 Script node implementing the XvlShell prototype.
 * @author   Marc Jablonski
 * @version  1.0, 09/01/01
 */
public class XvlShell extends Script
{
	private lvGlobal	mlvGlobal;

	private SFInt32		mShellType;
	private SFInt32		mNumDiv;
	private SFNode		mCoord;
	private MFFloat		mVtxRound;
	private SFNode		mTexCoord;
	private MFInt32		mEdgeV0;
	private MFInt32		mEdgeV1;
	private MFFloat		mEdgeRound;
	private MFVec3f		mEdgeVec0;
	private MFVec3f		mEdgeVec1;
	private MFInt32		mFaceCoordIndex;
	private MFInt32		mFaceTexCoordIndex;
	private SFString	mFaceEmpty;	// Booleans MFBool
	private SFString	mFaceHidden;	// Booleans MFBool

	private SFNode		coord_changed;
	private MFInt32		coordIndex_changed;
	private SFNode		texCoord_changed;
	private SFNode		normal_changed;

	public void initialize()
	{
		mShellType			= (SFInt32)	getField( "mShellType" );
		mNumDiv				= (SFInt32)	getField( "mNumDiv" );
		mCoord				= (SFNode)	getField( "mCoord" );
		mTexCoord			= (SFNode)	getField( "mTexCoord" );
		mVtxRound			= (MFFloat)	getField( "mVtxRound" );
		mEdgeV0				= (MFInt32)	getField( "mEdgeV0" );
		mEdgeV1				= (MFInt32)	getField( "mEdgeV1" );
		mEdgeRound			= (MFFloat)	getField( "mEdgeRound" );
		mEdgeVec0			= (MFVec3f)	getField( "mEdgeVec0" );
		mEdgeVec1			= (MFVec3f)	getField( "mEdgeVec1" );
		mFaceCoordIndex		= (MFInt32)	getField( "mFaceCoordIndex" );
		mFaceTexCoordIndex	= (MFInt32)	getField( "mFaceTexCoordIndex" );
		mFaceEmpty			= (SFString)	getField( "mFaceEmpty" );
		mFaceHidden			= (SFString)	getField( "mFaceHidden" );

		coord_changed		= (SFNode)	getEventOut( "coord_changed" );
		coordIndex_changed	= (MFInt32)	getEventOut( "coordIndex_changed" );
		texCoord_changed	= (SFNode)	getEventOut( "texCoord_changed" );
		normal_changed		= (SFNode)	getEventOut( "normal_changed" );

		boolean result;

		int	shellNo = 0;	// only one shell
		int numGsFaces = 0;
		int numVisibleFaces = 0;

		MFString faceEmpty = to_MFString( mFaceEmpty );
		MFString faceHidden = to_MFString( mFaceHidden );

		boolean haveCoord = ( mCoord.getValue() != null );
		boolean haveTexture = ( mTexCoord.getValue() != null );
		boolean haveEmpty = ( faceEmpty.getSize() > 0 );
		boolean haveHidden = ( faceHidden.getSize() > 0 );

		MFVec3f mVertex = ( haveCoord ) ?
			(MFVec3f)((Node)mCoord.getValue()).getExposedField( "point" ):
			new MFVec3f();

		MFVec2f mUV = ( haveTexture ) ?
			( (MFVec2f)( (Node)mTexCoord.getValue() ).getExposedField( "point" ) ):
			new MFVec2f();

		if( !haveCoord )
			System.err.println( "XvlShell WARNING: shell has no coordinates" );

		// initialize the Lattice Kernel
		mlvGlobal = lvWorld.Init();

		// create new shell
		lvToKernel mlvToKernel = new lvToKernel( mlvGlobal );
		result = mlvToKernel.AppendNumShell( 1 );
		if( result == lvConst.LV_FAILURE )
			System.err.println( "lvToKernel.AppendNumShell(1): " + lvError.Message( mlvGlobal ) );

		// set the kernel data
		set_ToKernelAttributes( mlvToKernel, shellNo, mShellType, mNumDiv );
		set_ToKernelVertexes( mlvToKernel, mVertex, mVtxRound );
		set_ToKernelEdges( mlvToKernel, mEdgeV0, mEdgeV1, mEdgeRound, mEdgeVec0, mEdgeVec1 );
		numGsFaces = set_ToKernelFaces( mlvToKernel, mFaceCoordIndex, faceEmpty );
		if( haveTexture )
			set_ToKernelUV( mlvToKernel, mUV, mFaceTexCoordIndex );

//		print_lvToKernel( mlvToKernel, System.err );

		// process the geometry
		result = mlvToKernel.SetData( shellNo );
		if( result == lvConst.LV_FAILURE ) {
			System.err.println( "lvToKernel.SetData(0): " + lvError.Message( mlvGlobal ) );
		}

		//  count the visible faces
		if( !haveHidden ) {
			numVisibleFaces = numGsFaces;
		} else {
			if( haveEmpty ) {
				if( faceHidden.getSize() != faceEmpty.getSize() ) {
					System.err.println( "XvlShell ERROR: faceEmpty array length not equal to faceHidden array length" );
				}
				for( int i=faceHidden.getSize()-1; i>=0; i-- ) {
					if( faceEmpty.get1Value( i ).equals( "true" ) ) {
						// remove from hidden array
						faceHidden.delete( i );
					} else {
						if( faceHidden.get1Value( i ).equals( "false" ) )
							numVisibleFaces++;
					}
				}
			} else {
				for( int i=0; i<faceHidden.getSize(); i++ )
					if( faceHidden.get1Value( i ).equals( "false" ) )
						numVisibleFaces++;
			}
		}

		// get the resulting visible faces
		lvFromKernel[] mlvFromKernel = new lvFromKernel[ numVisibleFaces ];
		int vfi = 0;
		for( int gs=0; gs<numGsFaces; gs++ ) {
			if( !haveHidden || ( faceHidden.get1Value( gs ).equals( "false" ) ) ) {
				mlvFromKernel[ vfi ] = new lvFromKernel( mlvGlobal );

				result = mlvFromKernel[ vfi++ ].GetData( shellNo, gs );
				if( result == lvConst.LV_FAILURE ) {
					System.err.println( "lvFromKernel.GetData(0," + gs + "): " + lvError.Message( mlvGlobal ) );
				}
			}
		}

//		print_lvFromKernel( mlvFromKernel, shellNo, System.err );

		// create the VRML97 nodes
		String coordStr = create_Coord( mlvFromKernel );
		int[] coordIndex = create_CoordIndex( mlvFromKernel );
		String texCoordStr = create_TexCoord( mlvFromKernel );
		String normalStr = create_Normal( mlvFromKernel );
		
		try {
            coord_changed.setValue( getBrowser().createVrmlFromString( coordStr )[ 0 ] );
            coordIndex_changed.setValue( coordIndex );
            normal_changed.setValue( getBrowser().createVrmlFromString( normalStr )[ 0 ] );
			if( haveTexture )
	            texCoord_changed.setValue( getBrowser().createVrmlFromString( texCoordStr )[ 0 ] );
		}
		catch( InvalidVRMLSyntaxException e ) {
			System.err.println( e.getMessage() );
		}
	}


	/**
	 * Set the attributes for the kernel.
	 */
	private void
	set_ToKernelAttributes( lvToKernel toKernel, int shellNo, SFInt32 mShellType, SFInt32 mNumDiv ) 
	{
		boolean result;
		lvToKernelType.Attr  attr = new lvToKernelType.Attr();

		switch( mShellType.getValue() ) {
		case( 0 ):	// POLYGON_MESH
			attr.type = lvConst.LV_SS_POLYGON;
			break;
		case( 1 ):	// LATTICE_MESH
			attr.type = lvConst.LV_SS_LATTICE;
			break;
		default:
			System.err.println( "XvlShell ERROR: unknown shell type = " + mShellType.getValue() );
			break;
		}

		int nd = mNumDiv.getValue();
		if( ( nd < 2 ) || ( ( nd % 2 ) != 0 ) ) {
			System.err.println( "XvlShell ERROR: numDiv = " + nd + "; must be a positive even integer" );
		} else {
			attr.numDiv = nd;
		}

		result = toKernel.SetAttr( shellNo, attr );
		if( result == lvConst.LV_FAILURE )
			System.err.println( "lvToKernel.SetAttr(...): " + lvError.Message( mlvGlobal ) );
	}
	
	
	/**
	 * Set the vertex data for the kernel
	 */
	private void
	set_ToKernelVertexes( lvToKernel toKernel, MFVec3f mVertex, MFFloat mVtxRound )
	{
		float vec3s[] = new float [3];
		int nv = mVertex.getSize();
		int nvr = mVtxRound.getSize();
		if( nv < 3 ) {
			System.err.println( "XvlShell ERROR: surface has less than 3 vertices" );
		} else if( ( nvr > 0 ) && ( nvr != nv ) ) {
			System.err.println( "XvlShell ERROR: vertex rounding coefficient count does not equal vertext count" );
		} else {
			toKernel.vertex = new lvToKernelType.Vertex[ nv ];
			for( int i=0; i<nv; i++ ) {
				toKernel.vertex[ i ] = new lvToKernelType.Vertex();
				mVertex.get1Value( i, vec3s );
				toKernel.vertex[ i ].pos.x = vec3s[ 0 ];
				toKernel.vertex[ i ].pos.y = vec3s[ 1 ];
				toKernel.vertex[ i ].pos.z = vec3s[ 2 ];
				if( i < nvr ) {
					toKernel.vertex[ i ].enable = true;
					toKernel.vertex[ i ].round = mVtxRound.get1Value( i );
				}
			}
		}
	}
	
	
	/**
	 * Set the edge data for the kernel
	 */
	private void
	set_ToKernelEdges( lvToKernel toKernel, MFInt32 mEdgeV0, MFInt32 mEdgeV1, MFFloat mEdgeRound, MFVec3f mEdgeVec0, MFVec3f mEdgeVec1 )
	{
		float vec3s[] = new float [3];
		int nev0 = mEdgeV0.getSize();
		int nev1 = mEdgeV1.getSize();
		int ner = mEdgeRound.getSize();
		int nevc0 = mEdgeVec0.getSize();
		int nevc1 = mEdgeVec1.getSize();
		if( nev0 != nev1 ) {
			System.err.println( "XvlShell ERROR: number of edge start vertices does not equal number of edge end vertices" );
		} else if( ( ner > 0 ) && ( ner != nev0 ) ) {
			System.err.println( "XvlShell ERROR: number of edge rounding coefficients does not equal number of edges" );
		} else if( ( nevc0 > 0 ) && ( nevc0 != nev0 ) ) {
			System.err.println( "XvlShell ERROR: number of edge start vectors does not equal number of edges" );
		} else if( ( nevc1 > 0 ) && ( nevc1 != nev0 ) ) {
			System.err.println( "XvlShell ERROR: number of edge end vectors does not equal number of edges" );
		} else {
			toKernel.edge = new lvToKernelType.Edge[ mEdgeV0.getSize() ];
			for( int i=0; i<mEdgeV0.getSize(); i++ ) {
				toKernel.edge[ i ] = new lvToKernelType.Edge();
				toKernel.edge[ i ].v0 = mEdgeV0.get1Value( i );
				toKernel.edge[ i ].v1 = mEdgeV1.get1Value( i );
				if( mEdgeRound.getSize() > i ) {
					toKernel.edge[ i ].enableAll = true;
					toKernel.edge[ i ].allRound = mEdgeRound.get1Value( i );
				}
				if( mEdgeVec0.getSize() > i ) {
					toKernel.edge[ i ].vec0 = new lvVecDt();
					mEdgeVec0.get1Value( i, vec3s );
					toKernel.edge[ i ].vec0.x = vec3s[ 0 ];
					toKernel.edge[ i ].vec0.y = vec3s[ 1 ];
					toKernel.edge[ i ].vec0.z = vec3s[ 2 ];
				}
				if( mEdgeVec1.getSize() > i ) {
					toKernel.edge[ i ].vec1 = new lvVecDt();
					mEdgeVec1.get1Value( i, vec3s );
					toKernel.edge[ i ].vec1.x = vec3s[ 0 ];
					toKernel.edge[ i ].vec1.y = vec3s[ 1 ];
					toKernel.edge[ i ].vec1.z = vec3s[ 2 ];
				}
			}
		}
	}


	/**
	 * Set the face data for the kernel.
	 */
	private int
	set_ToKernelFaces( lvToKernel toKernel, MFInt32 mFaceCoordIndex, MFString mFaceEmpty )
	{
		int ni = mFaceCoordIndex.getSize();
		boolean haveEmpty = (mFaceEmpty.getSize() > 0);

		// count the gs and ng faces
		int ngsf = 0;
		int ngsv = 0;
		int nngf = 0;
		int nngv = 0;

		int ei = 0;
		boolean empty = ( haveEmpty && ( mFaceEmpty.get1Value( ei++ ).equals( "true" ) ) );
		if( empty ) nngf++; else ngsf++;

		int nfv = 0;
		for( int i=0; i<ni; i++ ) {
			if( mFaceCoordIndex.get1Value( i ) == -1 ) {
				if( nfv < 3 ) {
					System.err.println( "XvlShell ERROR: face has less than three vertices." );
				}
				nfv = 0;
				if( i < ni-1 ) {
					empty = ( haveEmpty && ( mFaceEmpty.get1Value( ei++ ).equals( "true" ) ) );
					if( empty ) nngf++; else ngsf++;
				}
			} else {
				nfv++;
				if( empty ) nngv++; else ngsv++;
			}
		}

		if( ngsf == 0 ) {
			System.err.println( "XvlShell WARNING: shell has no faces" );
		}

		// allocate the kernel arrays
		toKernel.gsNumVtx = new int[ ngsf ];
		toKernel.gsVtxSeq = new int[ ngsv ];
		toKernel.ngNumVtx = new int[ nngf ];
		toKernel.ngVtxSeq = new int[ nngv ];

		// fill in the kernel arrays
		int fvi = 0;
		int gsfi = 0;
		int gsvi = 0;
		int ngfi = 0;
		int ngvi = 0;

		ei = 0;
		empty = ( haveEmpty && ( mFaceEmpty.get1Value( ei++ ).equals( "true" ) ) );

		for( int i=0; i<ni; i++ ) {
			int v = mFaceCoordIndex.get1Value( i );
			if( v == -1 ) {
				if( i < ni-1 ) {
					if( empty ) ngfi++; else gsfi++;
					empty = ( haveEmpty && ( mFaceEmpty.get1Value( ei++ ).equals( "true" ) ) );
				}
			} else {
				if( empty ) {
					toKernel.ngNumVtx[ ngfi ] = toKernel.ngNumVtx[ ngfi ] + 1;
					toKernel.ngVtxSeq[ ngvi++ ] = v;
				} else {
					toKernel.gsNumVtx[ gsfi ] = toKernel.gsNumVtx[ gsfi ] + 1;
					toKernel.gsVtxSeq[ gsvi++ ] = v;
				}
			}
		}

		return ngsf;
	}


	/**
	 * Set the texture coordinate data for the kernel.
	 */
	private void
	set_ToKernelUV( lvToKernel toKernel, MFVec2f mUV, MFInt32 mFaceTexCoordIndex )
	{
		int nv = toKernel.vertex.length;
		int nf = toKernel.gsNumVtx.length;

		if( mUV.getSize() > 0 ) {
			// use a different UV spaces for each face
			toKernel.uvSpace = new lvToKernelUV();
			toKernel.uvSpace.numUVspace = nf;

			toKernel.uvSpace.gsNumUV = new int[ nf ];
			for( int i=0; i < nf; i++ ) {
				toKernel.uvSpace.gsNumUV[ i ] = 1;
			}

			toKernel.uvSpace.gsUVseq = new lvToKernelUV.GsUV[nf];
			for( int i=0; i < nf; i++ ) {
				toKernel.uvSpace.gsUVseq[ i ] = new lvToKernelUV.GsUV();
				toKernel.uvSpace.gsUVseq[ i ].uvSpaceNo = i;
			}

			toKernel.uvSpace.vtxNumUV = new int[ nv ];
			for( int i=0; i < nv; i++ ) {
				toKernel.uvSpace.vtxNumUV[ i ] = 0;
			}
			int start = 0;
			for( int i=0; i < nf; i++ ) {
				int n = toKernel.gsNumVtx[ i ];
				for( int j=0; j < n; j++ ) {
					int vtx = toKernel.gsVtxSeq[ start+j ];
					toKernel.uvSpace.vtxNumUV[ vtx ]++;
				}
				start += n;
			}

			int index[] = new int[ nv ];
			int n = 0;
			for( int i=0; i < nv; i++ ) {
				index[ i ] = n;
				n += toKernel.uvSpace.vtxNumUV[ i ];
			}
			toKernel.uvSpace.vtxUVseq = new lvToKernelUV.VtxUV[ n ];

			float vec2s[] = new float[ 2 ];
			int tci = 0;
			start = 0;
			for( int i=0; i < nf; i++ ) {
				n = toKernel.gsNumVtx[ i ];
				for( int j=0; j < n; j++ ) {
					int tc = mFaceTexCoordIndex.get1Value( tci+j );
					mUV.get1Value( tc, vec2s );

					int vtx = toKernel.gsVtxSeq[ start+j ];
					int idx0 = index[ vtx ];

					toKernel.uvSpace.vtxUVseq[ idx0 ] = new lvToKernelUV.VtxUV();
					toKernel.uvSpace.vtxUVseq[ idx0 ].uvSpaceNo = i;
					toKernel.uvSpace.vtxUVseq[ idx0 ].uv.u = vec2s[ 0 ];
					toKernel.uvSpace.vtxUVseq[ idx0 ].uv.v = vec2s[ 1 ];

					index[ vtx ]++;
				}
				start += n;
				tci += (n + 1);  // add 1 to skip the "-1"
			}
		}
	}


	/**
	 * Create a VRML97 Coordinate node from the kernel results.
	 * @return a string representation of the Coordinate node.
	 */
	private String
	create_Coord( lvFromKernel[] fromKernel )
	{
        StringBuffer buffer = new StringBuffer(256);
        buffer.append("Coordinate {\n\tpoint[\n");
		for( int i=0; i<fromKernel.length; i++ ) {
			for( int v=0; v<fromKernel[ i ].vertex.length; v++ ) {
				buffer.append(	"\t\t" +
								fromKernel[ i ].vertex[ v ].pos.x +
								"\t" +
								fromKernel[ i ].vertex[ v ].pos.y +
								"\t" +
								fromKernel[ i ].vertex[ v ].pos.z +
								",\n" );
			}
		}
		buffer.append( "\t]\n}\n" );
        return buffer.toString();
	}


	/**
	 * Create a VRML97 IndexedFaceSet's coordinateIndex field from the kernel results.
	 * @return a coordinate index array.
	 */
	private int[]
	create_CoordIndex( lvFromKernel[] fromKernel )
	{
        int[] indices = null;

		int nt = 0;
		for( int i=0; i<fromKernel.length; i++ )
			nt += fromKernel[ i ].triIndex.length;
		indices = new int[ nt*4 ];

		int ii = 0;
		int di = 0;
		for( int i=0; i<fromKernel.length; i++ ) {
			for( int t=0; t<fromKernel[ i ].triIndex.length; t++ ) {
				indices[ ii++ ] = fromKernel[ i ].triIndex[ t ].vtxNo[ 0 ] + di;
				indices[ ii++ ] = fromKernel[ i ].triIndex[ t ].vtxNo[ 1 ] + di;
				indices[ ii++ ] = fromKernel[ i ].triIndex[ t ].vtxNo[ 2 ] + di;
				indices[ ii++ ] = -1;
			}
			di += fromKernel[ i ].vertex.length;
		}

		return indices;
	}


	/**
	 * Create a VRML97 TextureCoordinate node from the kernel results.
	 * @return a string representation of the TextureCoordinate node.
	 */
	private String
	create_TexCoord( lvFromKernel[] fromKernel )
	{
        StringBuffer buffer = new StringBuffer(256);
		if(  fromKernel[ 0 ].uvSpace != null ) {
			// the following code is safe because this prototype uses exactly
			// one uv space per face.
			int uvSpaceNo = 0;
			buffer.append("TextureCoordinate {\n\tpoint[\n");
			for( int i=0; i<fromKernel.length; i++ ) {
				for( int v=0; v<fromKernel[ i ].vertex.length; v++ ) {
					buffer.append(	"\t\t" +
									fromKernel[ i ].uvSpace[ uvSpaceNo ].uv[ v ].u +
									"\t" +
									fromKernel[ i ].uvSpace[ uvSpaceNo ].uv[ v ].v +
									",\n" );
				}
			}
			buffer.append( "\t]\n}\n" );
		}
		return buffer.toString();
	}


	/**
	 * Create a VRML97 Normal node from the kernel results.
	 * @return a string representation of the Normal node.
	 */
	private String
	create_Normal( lvFromKernel[] fromKernel )
	{
        StringBuffer buffer = new StringBuffer(256);
        buffer.append("Normal {\n\tvector[\n");
		for( int i=0; i<fromKernel.length; i++ ) {
			for( int v=0; v<fromKernel[ i ].vertex.length; v++ ) {
				buffer.append(	"\t\t" +
								fromKernel[ i ].vertex[ v ].normal.x +
								"\t" +
								fromKernel[ i ].vertex[ v ].normal.y +
								"\t" +
								fromKernel[ i ].vertex[ v ].normal.z +
								",\n" );
			}
		}
		buffer.append( "\t]\n}\n" );
        return buffer.toString();
	}


	/**
	 * Print lvToKernel.
	 */
	private void
	print_lvToKernel (lvToKernel toKernel, java.io.PrintStream out)
	{
		int ns = toKernel.GetNumShell();

		out.println( "To Kernel Num Shell = " + ns );
		for( int s=0; s<ns; s++ ) {

			out.println( "Shell " + s + ":" );
			out.println( "\ttype = " + ( toKernel.GetAttr( s ) ).type );
			out.println( "\tnumDiv = " + ( toKernel.GetAttr( s ) ).numDiv );

			out.println( "\tnum vertices = " + toKernel.vertex.length );
			for( int i=0; i<toKernel.vertex.length; i++ ) {
				out.print( "\tvertex[" + i + "]:" );
				out.print( "\tenable = " + toKernel.vertex[ i ].enable );
				out.print( "\tpos = " + toKernel.vertex[ i ].pos.x + " " + toKernel.vertex[ i ].pos.y + " " + toKernel.vertex[ i ].pos.z );
				out.print( "\tround = " + toKernel.vertex[ i ].round );
				out.println( );
			}

			out.println( "\tnum edges = " + toKernel.edge.length );
			for( int i=0; i<toKernel.edge.length; i++ ) {
				out.print( "\tedge[" + i + "]:" );
				out.print( "\tenableAll = " + toKernel.edge[ i ].enableAll );
				out.print( "\tv0 = " + toKernel.edge[ i ].v0 );
				out.print( "\tv1 = " + toKernel.edge[ i ].v1 );
				out.print( "\tallRound = " + toKernel.edge[ i ].allRound );

				if( toKernel.edge[ i ].vec0 != null ) {
					out.print( "\tvec0 = " + toKernel.edge[ i ].vec0.x + " " + toKernel.edge[ i ].vec0.y + " " + toKernel.edge[ i ].vec0.z );
					out.print( "\tvec1 = " + toKernel.edge[ i ].vec1.x + " " + toKernel.edge[ i ].vec1.y + " " + toKernel.edge[ i ].vec1.z );
				}

				out.println();
			}

			out.println( "\tnum GS faces = " + toKernel.gsNumVtx.length );
			for( int vi=0, i=0; i<toKernel.gsNumVtx.length; i++ ) {
				out.print( "\tface[" + i + "]:" );
				out.print( "\tnum vtx = " + toKernel.gsNumVtx[ i ] );
				out.print( "\tvertices =" );
				for( int j=0; j<toKernel.gsNumVtx[ i ]; j++ ) {
					out.print( " " + toKernel.gsVtxSeq[ vi++ ] );
				}
				out.println();
			}

			out.println( "\tnum NG faces = " + toKernel.ngNumVtx.length );
			for( int vi=0, i=0; i<toKernel.ngNumVtx.length; i++ ) {
				out.print( "\tface[" + i + "]:" );
				out.print( "\tnum vtx = " + toKernel.ngNumVtx[ i ] );
				out.print( "\tvertices =" );
				for( int j=0; j<toKernel.ngNumVtx[ i ]; j++ ) {
					out.print( " " + toKernel.ngVtxSeq[ vi++ ] );
				}
				out.println();
			}

			out.println( "\thave texture = " + ( ( toKernel.uvSpace != null ) ? "true" : "false" ) );
			if( toKernel.uvSpace != null ) {
				out.println( "\tnumUVspace = " + toKernel.uvSpace.numUVspace );
				for( int vi=0, i=0; i<toKernel.gsNumVtx.length; i++ ) {
					out.print( "\tface[" + i + "]:" );
					out.print( "\tnum UV spaces = " + toKernel.uvSpace.gsNumUV[ i ] );
					out.print( "\tUV spaces =" );
					for( int j=0; j<toKernel.uvSpace.gsNumUV[ i ]; j++ ) {
						out.print( " " + toKernel.uvSpace.gsUVseq[ vi++ ].uvSpaceNo );
					}
					out.println();
				}
				for( int vi=0, i=0; i<toKernel.vertex.length; i++ ) {
					out.print( "\tvertex[" + i + "]:" );
					out.print( "\tnum UV spaces = " + toKernel.uvSpace.vtxNumUV[ i ] );
					out.print( "\tUV coordinates =" );
					for( int j=0; j<toKernel.uvSpace.vtxNumUV[ i ]; j++ ) {
						out.print( " " + toKernel.uvSpace.vtxUVseq[ vi+j ].uvSpaceNo + ":(" + toKernel.uvSpace.vtxUVseq[ vi+j ].uv.u + "," + toKernel.uvSpace.vtxUVseq[ vi+j ].uv.v + ")" );
					}
					out.println();
					vi += toKernel.uvSpace.vtxNumUV[ i ];
				}
			}
		}
	}

	/**
	 * Print lvFromKernel.
	 */
	private void print_lvFromKernel( lvFromKernel[] fromKernel, int shellNo, java.io.PrintStream out ) {

		out.println( "From Kernel Shell (" + shellNo + ") Num Faces = " + fromKernel.length  );

		for( int f=0; f < fromKernel.length; f++ ) {

			out.println( "Face " + f + ":" );

			out.println( "\tnum vertices = " + fromKernel[ f ].vertex.length );
			for( int i=0; i<fromKernel[ f ].vertex.length; i++ ) {
				out.print( "\tvertex[" + i + "]:" );
				out.print( "\tpos = " + fromKernel[ f ].vertex[ i ].pos.x + " " + fromKernel[ f ].vertex[ i ].pos.y + " " + fromKernel[ f ].vertex[ i ].pos.z );
				out.print( "\tnormal = " + fromKernel[ f ].vertex[ i ].normal.x + " " + fromKernel[ f ].vertex[ i ].normal.y + " " + fromKernel[ f ].vertex[ i ].normal.z );
				out.println( );
			}

			out.println( "\tnum triangles = " + fromKernel[ f ].triIndex.length );
			for( int i=0; i<fromKernel[ f ].triIndex.length; i++ ) {
				out.print( "\ttriangle[" + i + "]:" + fromKernel[ f ].triIndex[ i ].vtxNo[ 0 ] + " " + fromKernel[ f ].triIndex[ i ].vtxNo[ 1 ] + " " + fromKernel[ f ].triIndex[ i ].vtxNo[ 2 ] );
				out.println();
			}

			out.println( "\thave texture = " + ((fromKernel[ f ].uvSpace != null ) ? "true" : "false" ) );
			if( fromKernel[ f ].uvSpace != null ) {
				out.println( "\tnum UV spaces = " + fromKernel[ f ].uvSpace.length );
				for( int i=0; i<fromKernel[ f ].uvSpace.length; i++ ) {
					out.println( "\tUVspace[" + i + "]:" );
					out.println( "\t\tUV space number = " + fromKernel[ f ].uvSpace[ i ].uvSpaceNo );
					for( int j=0; j<fromKernel[ f ].vertex.length; j++ ) {
						out.println( "\t\tuv[" + j + "]:\t" + fromKernel[ f ].uvSpace[ i ].uv[ j ].u + " " + fromKernel[ f ].uvSpace[ i ].uv[ j ].v );
					}
				}
			}
		}
	}

	/**
	 * convert SFString representing MFBoolean to MFString.
	 * Why MFString? To make the eventual conversion to MFBoolean trivial.
	 */
	private MFString to_MFString( SFString str ) {

		MFString mfs = new MFString();

		String qs = str.getValue();
		String nqs = qs.replace('"', ' ');
		String s = nqs.trim();

		if ( s.length() > 0 ) {
			java.util.StringTokenizer st = new java.util.StringTokenizer( s );

			while ( st.hasMoreTokens() ) {
				mfs.addValue( st.nextToken() );
			}
		}

		return mfs;
	}
}
