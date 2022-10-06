//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvToKernel.java
//

package jp.co.lattice.vProcessor.base;

/*
	トラバース。
	シーンをトップからたどる	
*/

import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vProcessor.node.*;
import	jp.co.lattice.vProcessor.parse.*;

//ラティスカーネル
import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTrvToKernel extends x3pTraverse {
	
	/** コンストラクタ				*/
	public x3pTrvToKernel( x3pGlobal dt )
	{
		super( dt );
	}

	/** グローバルデータ			*/
	private final x3pGlobal
	Processor()
	{
		return	( x3pGlobal )global;
	}


	private lvToKernel  toKernel     = null;
	private int         numDiv       = 4;
	
	private int            currentShlNo = 0;
	private x3pXvlShapeEx  currentShell = null;

	private int  cntVertex   = 0;
	private int  cntEdge     = 0;
	private int  cntGsNumVtx = 0;
	private int  cntGsVtxSeq = 0;
	private int  cntNgNumVtx = 0;
	private int  cntNgVtxSeq = 0;

	private int      cntUVspaceNo = 0;
	private int      cntGsUVseq   = 0;
	private boolean  existTexture = false;
	private int      cntEachFace  = 0;

	private final void
	Clear()
	{
		cntVertex   = 0;
		cntEdge     = 0;
		cntGsNumVtx = 0;
		cntGsVtxSeq = 0;
		cntNgNumVtx = 0;
		cntNgVtxSeq = 0;
		
		cntUVspaceNo = 0;
		cntGsUVseq   = 0;
	}

	public final void
	ToKernel( x3pElement[] elm, int numDiv0 ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvToKernel.toKernel(0)" );
		    
		toKernel = new lvToKernel( Processor() );
		numDiv   = numDiv0;
		    
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う

		toKernel = null;
	}


	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		boolean  lvErr;
		
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0;
		Err().Assert( ( elm.content != null ),                     "x3pTrvToKernel.TraverseXvlShape(0)" );
		Err().Assert( ( elm.typeXvlShape != x3pElement.null_int ), "x3pTrvToKernel.TraverseXvlShape(1)" );

		currentShell = elm;
		Clear();

		if( currentShell.uvSpace != null ) {
			for( int i=0; i<currentShell.tmpVtxUV.length; i++ ) {
				currentShell.tmpVtxUV[ i ].currentUVoffset  = -1;
				currentShell.tmpVtxUV[ i ].currentUVspaceNo = -1;
			}
		}
		
		TraverseMFElement( elm.content, td );
		
		if( currentShell.uvSpace != null ) {
			for( int i=0; i<currentShell.uvSpace.vtxNumUV.length; i++ )
				currentShell.uvSpace.vtxNumUV[ i ] = currentShell.tmpVtxUV[ i ].vtxNumUV.num;
		}
		
		lvToKernelType.Attr  attr = new lvToKernelType.Attr();
		attr.numDiv = numDiv;
		if( elm.typeXvlShape == x3pXvlShape.TYPE_POLYGON_MESH )
			attr.type = lvConst.LV_SS_POLYGON;
		else if( elm.typeXvlShape == x3pXvlShape.TYPE_LATTICE_MESH )
			attr.type = lvConst.LV_SS_LATTICE;
		else if( elm.typeXvlShape == x3pXvlShape.TYPE_GREGORY_MESH )
			attr.type = lvConst.LV_SS_GREGORY;
		else
			Err().Assert( false, "x3pTrvToKernel.traverseShell(2)" );
			
		lvErr = toKernel.SetAttr( currentShlNo, attr );
		Err().Assert( ( lvErr == lvConst.LV_SUCCESS ), lvError.Message( Processor() ) );

		
		toKernel.gsNumVtx = currentShell.gsNumVtx;
		toKernel.gsVtxSeq = currentShell.gsVtxSeq;
		toKernel.ngNumVtx = currentShell.ngNumVtx;
		toKernel.ngVtxSeq = currentShell.ngVtxSeq;
		toKernel.edge	  = currentShell.edge;
		toKernel.vertex   = currentShell.vertex;
		toKernel.uvSpace  = currentShell.uvSpace;
		lvErr = toKernel.SetData( currentShlNo );
		Err().Assert( ( lvErr == lvConst.LV_SUCCESS ), lvError.Message( Processor() ) );
		
		currentShlNo++;
	}

	protected void
	TraverseCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pCoordinate  elm = ( x3pCoordinate )elm0; 
		Err().Assert( ( elm.data != null ),     "x3pTrvToKernel.TraverseCoordinate(0)" );
		Err().Assert( ( elm.data.length >= 3 ), "x3pTrvToKernel.TraverseCoordinate(1)" );

		for( int i=0; i<elm.data.length; i++ ) {
			int  oldVtxNo = cntVertex + i;
			x3pTmpDtTrv.ToKernelVtxGs  tmpVtxGs = currentShell.tmpVtxGs[ oldVtxNo ];
			if( tmpVtxGs.onFace == true ) {
				lvToKernelType.Vertex  vtx = currentShell.vertex[ tmpVtxGs.newVtxNo ];
				vtx.pos.x = elm.data[ i ].x;
				vtx.pos.y = elm.data[ i ].y;
				vtx.pos.z = elm.data[ i ].z;
			}
		}

		cntVertex += elm.data.length;
	}

	protected void
	TraverseUseCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
	}
	
	protected void
	TraverseXvlFaces( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlFacesEx  elm = ( x3pXvlFacesEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvAddElm1.TraverseXvlFaces(0)" );

		existTexture = elm.existTexture;
			
		cntEachFace = 0;

		TraverseMFElement( elm.content, td );
		
		if( existTexture == true ) {
			if( ( elm.numGs - elm.numEachFace ) > 0 )
				cntUVspaceNo++;
			
			if( elm.numEachFace > 0 )
				cntUVspaceNo += elm.numEachFace;
		}
	}
	
	protected void
	TraverseXvlFace( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		int  i, num;
		
		x3pXvlFace  elm = ( x3pXvlFace )elm0; 
		Err().Assert( ( elm.coordIndex != null ), "x3pTrvToKernel.TraverseXvlFace(0)" );

		num = 0;
		for( i=0; i<elm.coordIndex.length; i++ ) {
			if( elm.coordIndex[ i ] == -1 )
				break;
			num++;
		}
		if( num < 3 )
			return;
			
		if( elm.empty == false ) {
			for( i=0; i<num; i++ ) {
				int  oldVtxNo = elm.coordIndex[ i ];
				int  newVtxNo = currentShell.tmpVtxGs[ oldVtxNo ].newVtxNo;
				currentShell.gsVtxSeq[ cntGsVtxSeq ] = newVtxNo;
				cntGsVtxSeq++;
			}
			currentShell.gsNumVtx[ cntGsNumVtx ] = num;
		}
		else {
			for( i=0; i<num; i++ ) {
				int  oldVtxNo = elm.coordIndex[ i ];
				int  newVtxNo = currentShell.tmpVtxGs[ oldVtxNo ].newVtxNo;
				currentShell.ngVtxSeq[ cntNgVtxSeq ] = newVtxNo;
				cntNgVtxSeq++;
			}
			currentShell.ngNumVtx[ cntNgNumVtx ] = num;
			cntNgNumVtx++;
		}
		
		if( elm.empty == false )
			ProcTraverseXvlFaceUV( elm );
		
		if( elm.empty == false )
			cntGsNumVtx++;
	}

	private void
	ProcTraverseXvlFaceUV( x3pXvlFace elm ) throws lvThrowable
	{
		if( currentShell.uvSpace == null )
			return;
		
		currentShell.uvSpace.gsNumUV[ cntGsNumVtx ] = 0;
		if( existTexture == true ) {
			currentShell.uvSpace.gsNumUV[ cntGsNumVtx ] = 1;
			
			lvToKernelUV.GsUV  gsUVseq = currentShell.uvSpace.gsUVseq[ cntGsUVseq ];
			gsUVseq.uvSpaceNo = cntEachFace;
			
			cntGsUVseq++;
			
			if( elm.texCoordIndex != null ) {
				for( int i=0; i<elm.coordIndex.length; i++ ) {
					if( elm.coordIndex[ i ] == -1 )
						break;
					if( elm.texCoordIndex[ i ] >= 0 )
						ProcTraverseFaceUVvtx( elm, elm.coordIndex[ i ], elm.texCoordIndex[ i ], gsUVseq.uvSpaceNo );
				}
			}
			
			cntEachFace++;
		}
	}
	
	private void
	ProcTraverseFaceUVvtx( x3pXvlFace elm, int xyzIndex, int uvIndex, int uvSpaceNo ) throws lvThrowable
	{
		int  newVtxNo = currentShell.tmpVtxGs[ xyzIndex ].newVtxNo;
		int  start = currentShell.tmpVtxUV[ newVtxNo ].vtxNumUV.start;
		
		currentShell.tmpVtxUV[ newVtxNo ].currentUVoffset++;
		
		int  uvOffset = currentShell.tmpVtxUV[ newVtxNo ].currentUVoffset;
		Err().Assert( ( uvOffset < currentShell.tmpVtxUV[ newVtxNo ].vtxNumUV.num ), "x3pTrvToKernel.ProcTraverseFaceUVvtx(0)" );
		
		lvToKernelUV.VtxUV  vtxUVseq = currentShell.uvSpace.vtxUVseq[ start + uvOffset ];
		vtxUVseq.uvSpaceNo = uvSpaceNo;
		lvUVdt.Copy( currentShell.tmpUVcoord[ uvIndex ], vtxUVseq.uv );
	}
	
	protected void
	TraverseXvlEdge( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		int      oldVtxNo, newVtxNo;
		lvVecDt  vec;
		
		x3pXvlEdge  elm = ( x3pXvlEdge )elm0; 
		Err().Assert( ( elm.coordIndex != null ), "x3pTrvToKernel.TraverseXvlEdge(0)" );

		lvToKernelType.Edge  edge = currentShell.edge[ cntEdge ];
		
		oldVtxNo = elm.coordIndex[ 0 ];
		newVtxNo = currentShell.tmpVtxGs[ oldVtxNo ].newVtxNo;
		edge.v0  = newVtxNo;
		
		oldVtxNo = elm.coordIndex[ 1 ];
		newVtxNo = currentShell.tmpVtxGs[ oldVtxNo ].newVtxNo;
		edge.v1  = newVtxNo;
	
		if( elm.roundingWeight != x3pElement.null_double ) {
			edge.enableAll = true;
			edge.allRound  = elm.roundingWeight;
		}
		
		if( elm.vectorStartEnd != null ) {
			edge.vec0 = new lvVecDt();
			vec = currentShell.tmpVtxGs[ elm.vectorStartEnd[ 0 ] ].xyz;
			lvVecDt.Copy( vec, edge.vec0 );
			
			edge.vec1 = new lvVecDt();
			vec = currentShell.tmpVtxGs[ elm.vectorStartEnd[ 1 ] ].xyz;
			lvVecDt.Copy( vec, edge.vec1 );
			edge.vec1.x = -edge.vec1.x;
			edge.vec1.y = -edge.vec1.y;
			edge.vec1.z = -edge.vec1.z;
		}			
		
		cntEdge++;
	}

	protected void
	TraverseXvlVertex( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlVertex  elm = ( x3pXvlVertex )elm0; 
		Err().Assert( ( elm.coordIndex != x3pElement.null_int ), "x3pTrvToKernel.TraverseXvlVertex(0)" );

		int  oldVtxNo = elm.coordIndex;
		int  newVtxNo = currentShell.tmpVtxGs[ oldVtxNo ].newVtxNo;
		lvToKernelType.Vertex  vtx = currentShell.vertex[ newVtxNo ];
		if( elm.roundingWeight != x3pElement.null_double ) {
			vtx.enable = true;
			vtx.round  = elm.roundingWeight;
		}
	}

}
