//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvNumInstance.java
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
public class x3pTrvNumInstance extends x3pTraverse {
	
	/** コンストラクタ				*/
	public x3pTrvNumInstance( x3pGlobal dt )
	{
		super( dt );
	}

	/** グローバルデータ			*/
	private final x3pToProcessor.Global
	ToProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gToProcessor;
	}

	private final x3pFromProcessor.Global
	FromProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gFromProcessor;
	}

	public static final int TYPE_ONLYXVLSHAPE    = 0x00;
	public static final int TYPE_NONUSETRANSFORM = 0x01;
	public static final int TYPE_USETRANSFORM    = 0x02;
	
	
	/** トラバース伝播データクラス  */
	public static class TrvDataNumInstance extends TrvData {
		
		private int  type = TYPE_ONLYXVLSHAPE;
		
		public TrvDataNumInstance( int parent0 )
		{
			type = parent0;
		}
	}

	private int  numInstance[];
	
	private static final TrvDataNumInstance  trvDataOnlyXvlShape    = new TrvDataNumInstance( TYPE_ONLYXVLSHAPE    );
	private static final TrvDataNumInstance  trvDataNonUseTransform = new TrvDataNumInstance( TYPE_NONUSETRANSFORM );
	private static final TrvDataNumInstance  trvDataUseTransform    = new TrvDataNumInstance( TYPE_USETRANSFORM    );

	
	public final void
	GetNumInstance( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvNumInstance.getNumInstance(0)" );
		
		int  numShell = FromProcessor().shell.length;
		numInstance = new int[ numShell ];
		for( int i=0; i<numShell; i++ )
			numInstance[ i ] = 0;
		
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う
	}
	
	public final int[]
	GetNumInstance()
	{
		return numInstance;
	}


	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseX3D( x3pContent elm0, TrvData td0 ) throws lvThrowable
	{
		x3pContent  elm = elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvNumInstance.TraverseX3D(0)" );

		TraverseMFElement( elm.content, trvDataOnlyXvlShape );
	}

	protected void
	TraverseTransform( x3pContent elm0, TrvData td0 ) throws lvThrowable
	{
		x3pTransform  elm = ( x3pTransform )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvNumInstance.TraverseTransform(0)" );
		
		TrvDataNumInstance  td = ( TrvDataNumInstance )td0;
		if( td.type == TYPE_ONLYXVLSHAPE )
			td = trvDataNonUseTransform;
		
		TraverseMFElement( elm.content, td );
	}

	protected void
	TraverseUseTransform( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pUseElm  use = ( x3pUseElm )elm0;
		Err().Assert( ( use.use != null ), "x3pTrvNumInstance.traverseUseTransform(0)" );
			
		x3pTransform  elm = ( x3pTransform )ToProcessor().xmlIdTable.get( use.use );
		Err().Assert( ( elm.content != null ), "x3pTrvNumInstance.TraverseUseTransform(1)" );
		
		TraverseMFElement( elm.content, trvDataUseTransform );
	}

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td0 ) throws lvThrowable
	{
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 

		numInstance[ elm.shellNo ]++;
		
		TrvDataNumInstance  td = ( TrvDataNumInstance )td0;
		if( td.type == TYPE_ONLYXVLSHAPE || td.type == TYPE_NONUSETRANSFORM ) {
			Err().Assert( ( elm.content != null ), "x3pTrvNumInstance.TraverseXvlShape(0)" );
			TraverseMFElement( elm.content, td );
		}
	}

}
