//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvAddElm0.java
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
public class x3pTrvAddElm0 extends x3pTraverse {
	
	/** コンストラクタ				*/
	public x3pTrvAddElm0( x3pGlobal dt )
	{
		super( dt );
	}

	/** グローバルデータ			*/
	private final x3pFromProcessor.Global
	FromProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gFromProcessor;
	}

	
	private int      numShell       = 0;
	
	private int      numEdge        = 0;
	private int      numGsNumVtx    = 0;
	private int      numGsVtxSeq    = 0;
	private int      numNgNumVtx    = 0;
	private int      numNgVtxSeq    = 0;
	
	private int      numGsFacesShl  = 0;
	private int      numGs          = 0;
	private int      numCommonGs    = 0;
	private int      numGsFaces     = 0;
	
	private int      numMaterial    = 1;
	
	private int      numTexture     = 0;
	
	private boolean  existTexture   = false;
	private int      numUVspace     = 0;
	private int      numGsUVseq     = 0;
	private int      numUVcoord     = 0;
	private int      numEachFace    = 0;

	private boolean  localMode      = false;
	private boolean  existMaterial2 = false;
	private boolean  existTexture2  = false;
	
	
	private final void
	Clear()
	{
		numEdge       = 0;
		numGsNumVtx   = 0;
		numGsVtxSeq   = 0;
		numNgNumVtx   = 0;
		numNgVtxSeq   = 0;
		
		numGsFacesShl = 0;
		
		numUVspace    = 0;
		numGsUVseq    = 0;
		numUVcoord    = 0;
	}

	public final void
	AddElement( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvAddElm0.addElement(0)" );
		
		lvToKernel  toKernel = new lvToKernel( global );
		numMaterial = 1;
		numTexture  = 0;
		    
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う

		boolean  lvErr = toKernel.AppendNumShell( numShell );
		Err().Assert( ( lvErr == lvConst.LV_SUCCESS ), lvError.Message( global ) );
		toKernel = null;
		
		FromProcessor().shell = new x3pShellInfo[ numShell ];
		for( int i=0; i<numShell; i++ )
			FromProcessor().shell[ i ] = new x3pShellInfo();
			
		FromProcessor().material = new x3pFromProcessor.Material[ numMaterial ];
		for( int i=0; i<numMaterial; i++ )
			FromProcessor().material[ i ] = new x3pFromProcessor.Material( global );
		
		if( numTexture > 0 ) {
			FromProcessor().texture = new x3pFromProcessor.Texture[ numTexture ];
			for( int i=0; i<numTexture; i++ )
				FromProcessor().texture[ i ] = new x3pFromProcessor.Texture();
		}
	}

	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		int  i;
		
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvAddElm0.TraverseXvlShape(0)" );

		Clear();

		TraverseMFElement( elm.content, td );
		
		elm.edge = new lvToKernelType.Edge[ numEdge ];
		for( i=0; i<numEdge; i++ )
			elm.edge[ i ] = new lvToKernelType.Edge();
		
		elm.gsNumVtx = new int[ numGsNumVtx ];
		elm.gsVtxSeq = new int[ numGsVtxSeq ];
		elm.ngNumVtx = new int[ numNgNumVtx ];
		elm.ngVtxSeq = new int[ numNgVtxSeq ];
		
		Err().Assert( ( numGsFacesShl > 0 ), "x3pTrvAddElm0.TraverseShell(1)" );
		elm.numGsFaces = numGsFacesShl;
		elm.shellNo    = numShell;
		
		if( numUVspace > 0 ) {
			elm.uvSpace = new lvToKernelUV();
			
			elm.uvSpace.numUVspace = numUVspace;
			elm.uvSpace.gsNumUV    = new int[ numGsNumVtx ];
			elm.uvSpace.gsUVseq    = new lvToKernelUV.GsUV[ numGsUVseq ];
			for( i=0; i<numGsUVseq; i++ )
				elm.uvSpace.gsUVseq[ i ] = new lvToKernelUV.GsUV();
				
			int  numVertex = elm.vertex.length;
			elm.uvSpace.vtxNumUV = new int[ numVertex ];
			
			elm.tmpVtxUV = new x3pTmpDtTrv.ToKernelVtxUV[ numVertex ];
			for( i=0; i<numVertex; i++ )
				elm.tmpVtxUV[ i ] = new x3pTmpDtTrv.ToKernelVtxUV();
		}
		
		if( numUVcoord > 0 ) {
			elm.tmpUVcoord = new lvUVdt[ numUVcoord ];
			for( i=0; i<numUVcoord; i++ )
				elm.tmpUVcoord[ i ] = new lvUVdt();
		}
		
		numShell++;
	}

	protected void
	TraverseTextureCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pTextureCoordinate  elm = ( x3pTextureCoordinate )elm0; 
		Err().Assert( ( elm.data != null ), "x3pTrvAddElm0.TraverseTextureCoordinate(0)" );

		numUVcoord += elm.data.length;
	}

	protected void
	TraverseUseTextureCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
	}
	
	protected void
	TraverseXvlFaces( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlFacesEx  elm = ( x3pXvlFacesEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvAddElm0.TraverseXvlFaces(0)" );

		numGs        = 0;
		numCommonGs  = 0;
		numGsFaces   = 0;
		existTexture = false;
		numEachFace  = 0;

		TraverseMFElement( elm.content, td );
			
		if( numCommonGs > 0 ) {
			numGsFacesShl++;
			numGsFaces++;
		}
			
		elm.numGs        = numGs;
		elm.numGsFaces   = numGsFaces;
		elm.numCommonGs  = numCommonGs;
		elm.existTexture = existTexture;
		elm.numEachFace  = numEachFace;
			
		if( existTexture == true ) {
			if( ( numGs - numEachFace ) > 0 ) {
				numUVspace++;
				numGsUVseq += ( numGs - numEachFace );
			}
			if( numEachFace > 0 ) {
				numUVspace += numEachFace;
				numGsUVseq += numEachFace;
			}
		}
	}
	
	protected void
	TraverseXvlFace( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		int  i;
		
		localMode = true;
		
		x3pXvlFace  elm = ( x3pXvlFace )elm0; 
		Err().Assert( ( elm.coordIndex != null ), "x3pTrvAddElm0.TraverseXvlFace(0)" );

		existMaterial2 = false;
		existTexture2  = false;
		
		TraverseMFElement( elm.content, td );
		
		if( elm.empty == false ) {
			if( existMaterial2 == false && existTexture2 == false )
				numCommonGs++;
			else {
				numGsFacesShl++;
				numGsFaces++;
			}
		}
		
		if( elm.empty == false ) {
			numGsNumVtx++;
			for( i=0; i<elm.coordIndex.length; i++ ) {
				if( elm.coordIndex[ i ] == -1 )
					break;
				numGsVtxSeq++;
			}
			
			numGs++;
			numEachFace++;
		}
		else {
			numNgNumVtx++;
			for( i=0; i<elm.coordIndex.length; i++ ) {
				if( elm.coordIndex[ i ] == -1 )
					break;
				numNgVtxSeq++;
			}
		}
		
		localMode = false;
	}

	protected void
	TraverseXvlEdge( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlEdge  elm = ( x3pXvlEdge )elm0; 
		Err().Assert( ( elm.coordIndex != null ), "x3pTrvAddElm0.TraverseXvlEdge(0)" );

		numEdge++;
	}

	protected void
	TraverseMaterial( x3pElement elm0, TrvData td )
	{
		x3pMaterialEx  elm = ( x3pMaterialEx )elm0;
		
		elm.materialNo = numMaterial;
		numMaterial++;
		
		if( localMode == true )
			existMaterial2 = true;
	}

	protected void
	TraverseUseMaterial( x3pElement elm0, TrvData td )
	{
		if( localMode == true )
			existMaterial2 = true;
	}

	protected void
	TraverseImageTexture( x3pElement elm0, TrvData td )
	{
		x3pImageTextureEx  elm = ( x3pImageTextureEx )elm0;
		
		elm.textureNo = numTexture;
		numTexture++;
		
		if( localMode == false )
			existTexture  = true;
		else
			existTexture2 = true;
	}

	protected void
	TraverseUseImageTexture( x3pElement elm0, TrvData td )
	{
		if( localMode == false )
			existTexture  = true;
		else
			existTexture2 = true;
	}

}
