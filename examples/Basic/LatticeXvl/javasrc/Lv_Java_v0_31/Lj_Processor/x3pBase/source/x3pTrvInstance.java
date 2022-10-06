//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvInstance.java
//

package jp.co.lattice.vProcessor.base;

/*
	トラバース。
	シーンをトップからたどる	
*/

import	jp.co.lattice.vProcessor.node.*;
import	jp.co.lattice.vProcessor.parse.*;
import	jp.co.lattice.vProcessor.com.*;

//ラティスカーネル
import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTrvInstance extends x3pTraverse {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( x3pGlobal dt )
		{
			GlobalTmp( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private x3pMatrix  tmTraverseTransform[]    = null;
		private x3pMatrix  tmTraverseUseTransform[] = null;
		private x3pMatrix  tmMakeMatrix[]			= null;
		private lvVector   tvMakeMatrix[]			= null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( x3pGlobal dt )
		{
			tmTraverseTransform    = new x3pMatrix[ 2 ];  for( int i=0; i<2; i++ )	tmTraverseTransform[ i ]    = new x3pMatrix( dt );
			tmTraverseUseTransform = new x3pMatrix[ 2 ];  for( int i=0; i<2; i++ )	tmTraverseUseTransform[ i ] = new x3pMatrix( dt );
			tmMakeMatrix           = new x3pMatrix[ 2 ];  for( int i=0; i<2; i++ )	tmMakeMatrix[ i ]           = new x3pMatrix( dt );
			tvMakeMatrix           = new lvVector[ 2 ];	  for( int i=0; i<2; i++ )	tvMakeMatrix[ i ]           = new lvVector( dt );
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gTrvInstance;
	}

// ----------------------------------------------------------------------

	/** コンストラクタ				*/
	public x3pTrvInstance( x3pGlobal dt )
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
		
		private int        type 		= TYPE_ONLYXVLSHAPE;
		private x3pMatrix  posMat		= null;
		private boolean    visibility	= true; 
		
		public TrvDataNumInstance( x3pGlobal gbl0, int parent0 )
		{
			type   = parent0;
			posMat = new x3pMatrix( gbl0 );
		}
		
		public TrvDataNumInstance( int parent0, x3pMatrix posMat0, boolean visibility0 )
		{
			type       = parent0;
			posMat     = posMat0;
			visibility = visibility0;
		}
	}

	private int  cntInstance[];

	
	public final void
	SetInstance( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvInstance.setInstance(0)" );
		
		int  numShell = FromProcessor().shell.length;
		cntInstance = new int[ numShell ];
		for( int i=0; i<numShell; i++ )
			cntInstance[ i ] = 0;
		
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う
	}

	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseX3D( x3pContent elm0, TrvData td0 ) throws lvThrowable
	{
		x3pContent  elm = elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvInstance.TraverseX3D(0)" );

		TrvDataNumInstance  dstTD = new TrvDataNumInstance( global, TYPE_ONLYXVLSHAPE );
		
		TraverseMFElement( elm.content, dstTD );
	}

	protected void
	TraverseTransform( x3pContent elm0, TrvData td0 ) throws lvThrowable
	{
		x3pTransform  elm = ( x3pTransform )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvInstance.TraverseTransform(0)" );
		
		TrvDataNumInstance  srcTD = ( TrvDataNumInstance )td0;
		
		x3pMatrix  elmMat = Gbl().tmTraverseTransform[0];				// elmMat = new x3pMatrix( Processor() );
		MakeMatrix( elm, elmMat );
		x3pMatrix  posMat = new x3pMatrix( global, srcTD.posMat );
		posMat.Mul( elmMat );
		
		TrvDataNumInstance  dstTD = new TrvDataNumInstance( srcTD.type, posMat, true );
		if( dstTD.type == TYPE_ONLYXVLSHAPE )
			dstTD.type = TYPE_NONUSETRANSFORM;
		
		TraverseMFElement( elm.content, dstTD );
	}

	protected void
	TraverseUseTransform( x3pElement elm0, TrvData td0 ) throws lvThrowable
	{
		x3pUseElm  use = ( x3pUseElm )elm0;
		Err().Assert( ( use.use != null ), "x3pTrvInstance.TraverseUseTransform(0)" );
			
		x3pTransform  elm = ( x3pTransform )ToProcessor().xmlIdTable.get( use.use );
		Err().Assert( ( elm.content != null ), "x3pTrvInstance.TraverseUseTransform(1)" );
		
		TrvDataNumInstance  srcTD = ( TrvDataNumInstance )td0;

		x3pMatrix  elmMat = Gbl().tmTraverseUseTransform[0];			// elmMat = new x3pMatrix( Processor() );
		MakeMatrix( elm, elmMat );
		x3pMatrix  posMat = new x3pMatrix( global, srcTD.posMat );
		posMat.Mul( elmMat );
		
		TrvDataNumInstance  dstTD = new TrvDataNumInstance( TYPE_USETRANSFORM, posMat, true );
		
		TraverseMFElement( elm.content, dstTD );
	}

	private void
	MakeMatrix( x3pTransform elm, x3pMatrix dst )
	{
		x3pMatrix  tmpMat = Gbl().tmMakeMatrix[0];			// tmpMat = new x3pMatrix( Processor() );
		lvVector   tmpVec = Gbl().tvMakeMatrix[0];			// tmpVec = new lvVector( Processor() );
		
		dst.SetUnit();
		
		tmpVec.SetXYZ( elm.scale.x, elm.scale.y, elm.scale.z );
		tmpMat. SetScale( tmpVec );
		dst.Mul( tmpMat );
		
		tmpVec.SetXYZ( elm.rotation.x, elm.rotation.y, elm.rotation.z );
		tmpMat. SetRotateV( tmpVec, elm.rotation.r );
		dst.Mul( tmpMat );
		
		tmpVec.SetXYZ( elm.translation.x, elm.translation.y, elm.translation.z );
		tmpMat. SetTranslate( tmpVec );
		dst.Mul( tmpMat );
	}

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td0 ) throws lvThrowable
	{
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 

		TrvDataNumInstance  srcTD = ( TrvDataNumInstance )td0;

		int  instanceNo = cntInstance[ elm.shellNo ];
		FromProcessor().shell[ elm.shellNo ].instance[ instanceNo ].posMat.Set( srcTD.posMat );
		FromProcessor().shell[ elm.shellNo ].instance[ instanceNo ].visibility = true;
		cntInstance[ elm.shellNo ]++;
		
		if( srcTD.type == TYPE_ONLYXVLSHAPE || srcTD.type == TYPE_NONUSETRANSFORM ) {
			Err().Assert( ( elm.content != null ), "x3pTrvInstance.TraverseXvlShape(0)" );
			TraverseMFElement( elm.content, srcTD );
		}
	}

}
