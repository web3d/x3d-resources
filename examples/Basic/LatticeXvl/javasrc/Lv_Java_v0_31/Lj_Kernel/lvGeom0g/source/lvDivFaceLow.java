//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFaceLow.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * DivPoly情報の作成クラス（下位レイヤー）
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvDivFaceLow extends lvRoot {

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントの「上位レイヤー(topo0)から送られるデータ」		*/
		private lvDivFaceType.DownDivFace    curDownDivFace		= null;
		
		/** DownDivFaceから派生したデータ				*/
		private lvDivFaceType.DeriveDivFace  deriveDivFace		= null;
		
		/** lvMakeInnerEdgeオブジェクト					*/
		private lvMakeInnerEdge  makeInnerEdge		= null;
		
		/** lvTessellateオブジェクト					*/
		private lvTessellate     tessellate			= null;

		/** static関数用のオブジェクト					*/
		private lvDivFaceLow     local				= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			deriveDivFace = new lvDivFaceType.DeriveDivFace( dt );
			makeInnerEdge = new lvMakeInnerEdge( dt );
			tessellate    = new lvTessellate( dt );
			local         = new lvDivFaceLow( dt );
			
			GlobalTmp( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvAryPosition[]			= null;
		private lvVector  tvAryDerivative[]			= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvAryPosition    = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryPosition[ i ]    = new lvVector( dt );
			tvAryDerivative  = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryDerivative[ i ]  = new lvVector( dt );
		}
		
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gDivFaceLow;
	}
	/** DownDivFace用のグローバルデータ				*/
	private final lvDivFaceType.DownDivFace
	DownDivFace()
	{
		return	Gbl().curDownDivFace;
	}
	/** DeriveDivFace0用のグローバルデータ		*/
	private final lvDivFaceType.DeriveDivFace
	DeriveDivFace()
	{
		return  Gbl().deriveDivFace;
	}
	/** 当クラス用のグローバルデータ		*/
	private static final lvDivFaceLow
	Local( lvGlobal gbl )
	{
		return  ( ( lv0GeomGGblElm )gbl.GGeomG() ).gDivFaceLow.local;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvDivFaceLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1個の面をテセレーションする
	 * @param  downDivPoly		(( I )) 上位レイヤー(topo0)から送られるデータ
	 * @param  upDivPoly		(( O )) 上位レイヤー(topo0)に送るデータ
	 */
	public final void
	Exec( lvDivFaceType.DownDivFace downDivFace, lvDivFaceType.UpDivFace upDivFace ) throws lvThrowable
	{
		Gbl().curDownDivFace = downDivFace;

		if( DownDivFace().numHalf != 4 )
			Gbl().makeInnerEdge.Exec( downDivFace, DeriveDivFace() );
			
		Gbl().tessellate.Exec( downDivFace, DeriveDivFace(), upDivFace );
	
		Finish();
	}
	
	private final void
	Finish()
	{
		Gbl().curDownDivFace      = null;		// Delete( Gbl().curDownDivFace );
		Gbl().deriveDivFace.inner = null;		// Delete( Gbl().deriveDivFace.inner );
	}
	
	/**
	 * t値でのベジェ曲線上の位置を求める
	 * @param  halfNo	(( I )) ハーフエッジ線No
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 位置
	 */
	public static final void
	Position( int halfNo, double t, lvVector result )
	{
		lvGlobal  gbl = result.global;
		Local( gbl ).PositionLocal( halfNo, t, result );
	}
	
	/**
	 * t値でのベジェ曲線上の位置を求める（ローカル）
	 * @param  halfNo	(( I )) ハーフエッジ線No
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 位置
	 */
	private final void
	PositionLocal( int halfNo, double t, lvVector result )
	{
		lvVector  ctlPnt[/*4*/] = Gbl().tvAryPosition;					// ctlPnt[] = new lvVector[ 4 ];
		
		ctlPnt[ 0 ].Assign( DownDivFace().half[ halfNo ].pos );
		ctlPnt[ 1 ].Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		ctlPnt[ 2 ].Assign( DownDivFace().half[ halfNo ].handVec[ 1 ] );
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		ctlPnt[ 3 ].Assign( DownDivFace().half[ halfF ].pos );
			
		ctlPnt[ 1 ].AddAssign( ctlPnt[ 0 ] );
		ctlPnt[ 2 ].AddAssign( ctlPnt[ 3 ] );
		
		lvBezLine.Position( ctlPnt, t, result );
	}

	/**
	 * t値でのベジェ曲線上の微分ベクトルを求める
	 * @param  halfNo	(( I )) ハーフエッジ線No
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 微分ベクトル
	 */
	public static final void
	Derivative( int halfNo, double t, lvVector result )
	{
		lvGlobal  gbl = result.global;
		Local( gbl ).DerivativeLocal( halfNo, t, result );
	}
	
	/**
	 * t値でのベジェ曲線上の微分ベクトルを求める（ローカル）
	 * @param  halfNo	(( I )) ハーフエッジ線No
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 微分ベクトル
	 */
	private final void
	DerivativeLocal( int halfNo, double t, lvVector result )
	{
		lvVector  ctlPnt[/*4*/] = Gbl().tvAryDerivative;					// ctlPnt[] = new lvVector[ 4 ];
		
		ctlPnt[ 0 ].Assign( DownDivFace().half[ halfNo ].pos );
		ctlPnt[ 1 ].Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		ctlPnt[ 2 ].Assign( DownDivFace().half[ halfNo ].handVec[ 1 ] );
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		ctlPnt[ 3 ].Assign( DownDivFace().half[ halfF ].pos );
			
		ctlPnt[ 1 ].AddAssign( ctlPnt[ 0 ] );
		ctlPnt[ 2 ].AddAssign( ctlPnt[ 3 ] );
		
		lvBezLine.Derivative( ctlPnt, t, result );
	}
	
}
