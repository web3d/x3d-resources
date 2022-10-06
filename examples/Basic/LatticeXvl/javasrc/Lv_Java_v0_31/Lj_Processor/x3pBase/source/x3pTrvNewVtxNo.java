//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvNewVtxNo.java
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
public class x3pTrvNewVtxNo extends x3pTraverse {
	
	/** コンストラクタ				*/
	public x3pTrvNewVtxNo( x3pGlobal dt )
	{
		super( dt );
	}


	private x3pTmpDtTrv.ToKernelVtxGs  currentVtxGs[]	= null;

	private int  cntVertex		= 0;
	
	
	private final void
	Clear()
	{
		cntVertex = 0;
	}
	
	public final void
	NewVtxNo( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvNewVtxNo.NewVtxNo(0)" );
		
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う
	}

	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvNewVtxNo.TraverseShell(0)" );

		Clear();
		InitVtxGs( elm.tmpVtxGs );
		currentVtxGs = elm.tmpVtxGs;

		TraverseMFElement( elm.content, td );
		
		SetNewVtxNo( elm );
	}
	
	private void
	InitVtxGs( x3pTmpDtTrv.ToKernelVtxGs tmpVtxGs[] )
	{
		int  num = tmpVtxGs.length;
		for( int i=0; i<num; i++ ) {
			tmpVtxGs[ i ].onFace   = false;
			tmpVtxGs[ i ].newVtxNo = -1;
		}
	}

	private void
	SetNewVtxNo( x3pXvlShapeEx elm )
	{
		int  num = elm.tmpVtxGs.length;
		
		int  cnt = 0;
		for( int i=0; i<num; i++ ) {
			if( elm.tmpVtxGs[ i ].onFace == true ) {
				elm.tmpVtxGs[ i ].newVtxNo = cnt;
				cnt++;
			}
		}
		
		elm.vertex = new lvToKernelType.Vertex[ cnt ];
		for( int i=0; i<cnt; i++ )
			elm.vertex[ i ] = new lvToKernelType.Vertex();
	}

	protected void
	TraverseCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pCoordinate  elm = ( x3pCoordinate )elm0; 
		Err().Assert( ( elm.data != null ), "x3pTrvNewVtxNo.TraverseXYZ(0)" );

		for( int i=0; i<elm.data.length; i++ ) {
			lvVecDt  xyz = currentVtxGs[ cntVertex + i ].xyz;
			xyz.x = elm.data[ i ].x;
			xyz.y = elm.data[ i ].y;
			xyz.z = elm.data[ i ].z;
		}

		cntVertex += elm.data.length;
	}
	
	protected void
	TraverseUseCoordinate( x3pElement elm0, TrvData td ) throws lvThrowable
	{
	}
	
	protected void
	TraverseXvlFace( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlFace  elm = ( x3pXvlFace )elm0; 
		Err().Assert( ( elm.coordIndex != null ), "x3pTrvNewVtxNo.TraverseFace(0)" );
		
		TraverseMFElement( elm.content, td );
		
		if( elm.empty == false ) {
			for( int i=0; i<elm.coordIndex.length; i++ ) {
				if( elm.coordIndex[ i ] == -1 )
					break;
					
				int  vtxNo = elm.coordIndex[ i ];
				Err().Assert( ( 0 <= vtxNo && vtxNo < currentVtxGs.length ), "x3pTrvNewVtxNo.TraverseFace(1)" );
				currentVtxGs[ vtxNo ].onFace = true;
			}
		}
	}

}
