//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvNumNewVtxNo.java
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


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTrvNumNewVtxNo extends x3pTraverse {
	
	/** コンストラクタ				*/
	public x3pTrvNumNewVtxNo( x3pGlobal dt )
	{
		super( dt );
	}

	
	private int  numVertex = 0;
	
	
	private final void
	Clear()
	{
		numVertex = 0;
	}

	public final void
	NumNewVtxNo( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvNumNewVtxNo.NumNewVtxNo(0)" );
		
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う
	}

	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvNumNewVtxNo.TraverseXvlShape(0)" );

		Clear();

		TraverseMFElement( elm.content, td );
		
		elm.tmpVtxGs = new x3pTmpDtTrv.ToKernelVtxGs[ numVertex ];
		for( int i=0; i<numVertex; i++ )
			elm.tmpVtxGs[ i ] = new x3pTmpDtTrv.ToKernelVtxGs();
	}

	protected void
	TraverseCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pCoordinate  elm = ( x3pCoordinate )elm0; 
		Err().Assert( ( elm.data != null ), "x3pTrvNumNewVtxNo.TraverseCoordinate(0)" );

		numVertex += elm.data.length;
	}

	protected void
	TraverseUseCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
	}
	
}
