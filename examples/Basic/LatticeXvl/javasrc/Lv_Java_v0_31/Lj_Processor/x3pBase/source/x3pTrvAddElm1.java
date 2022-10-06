//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvAddElm1.java
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
public class x3pTrvAddElm1 extends x3pTraverse {
	
	/** コンストラクタ				*/
	public x3pTrvAddElm1( x3pGlobal dt )
	{
		super( dt );
	}

	/** グローバルデータ			*/
	private final x3pFromProcessor.Global
	FromProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gFromProcessor;
	}


	private x3pXvlShapeEx  currentShell = null;
	private int            cntUVcoord   = 0;
	
	private int            cntUVspaceNo = 0;
	private boolean        existTexture = false;

	
	private final void
	Clear()
	{
		cntUVspaceNo = 0;
		cntUVcoord   = 0;
	}

	public final void
	AddElement( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvAddElm1.addElement(0)" );
		
		if( FromProcessor().texture == null )
			return;
		
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う
	}

	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvAddElm1.TraverseXvlShape(0)" );

		currentShell = elm;
		Clear();

		if( currentShell.uvSpace != null ) {
			for( int i=0; i<currentShell.tmpVtxUV.length; i++ ) {
				currentShell.tmpVtxUV[ i ].vtxNumUV.num     =  0;
				currentShell.tmpVtxUV[ i ].currentUVspaceNo = -1;
			}
		}
		
		TraverseMFElement( elm.content, td );
		
		if( currentShell.uvSpace != null ) {
			int  cnt = 0;
			for( int i=0; i<currentShell.tmpVtxUV.length; i++ ) {
				currentShell.tmpVtxUV[ i ].vtxNumUV.start = cnt;
				cnt += currentShell.tmpVtxUV[ i ].vtxNumUV.num;
			}
			
			int  numVtxUVseq = 0;
			for( int i=0; i<currentShell.tmpVtxUV.length; i++ )
				numVtxUVseq += currentShell.tmpVtxUV[ i ].vtxNumUV.num;
			
			if( numVtxUVseq > 0 ) {
				elm.uvSpace.vtxUVseq = new lvToKernelUV.VtxUV[ numVtxUVseq ];
				for( int i=0; i<numVtxUVseq; i++ )
					elm.uvSpace.vtxUVseq[ i ] = new lvToKernelUV.VtxUV();
			}
		}
	}

	protected void
	TraverseTextureCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pTextureCoordinate  elm = ( x3pTextureCoordinate )elm0; 
		Err().Assert( ( elm.data != null ), "x3pTrvAddElm1.TraverseTextureCoordinate(0)" );

		for( int i=0; i<elm.data.length; i++ ) {
			lvUVdt  uv = currentShell.tmpUVcoord[ cntUVcoord + i ];
			uv.u = elm.data[ i ].u;
			uv.v = elm.data[ i ].v;
		}

		cntUVcoord += elm.data.length;
	}

	protected void
	TraverseUseTextureCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
	}
	
	protected void
	TraverseXvlFaces( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlFacesEx  elm = ( x3pXvlFacesEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvAddElm1.TraverseFaces(0)" );

		existTexture = elm.existTexture;
			
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
		x3pXvlFace  elm = ( x3pXvlFace )elm0; 
		Err().Assert( ( elm.coordIndex != null ), "x3pTrvAddElm1.TraverseXvlFace(0)" );

		if( elm.empty == false && existTexture == true && elm.texCoordIndex != null ) {
			for( int i=0; i<elm.coordIndex.length; i++ ) {
				if( elm.coordIndex[ i ] == -1 )
					break;
				
				int  oldVtxNo = elm.coordIndex[ i ];
				int  newVtxNo = currentShell.tmpVtxGs[ oldVtxNo ].newVtxNo;
				if( elm.texCoordIndex[ i ] >= 0 )
					currentShell.tmpVtxUV[ newVtxNo ].vtxNumUV.num++;
			}
		}
	}
	
}
