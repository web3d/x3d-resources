//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvTessellateLow.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;

import	jp.co.lattice.vKernel.tex.a0g.lvTessellateUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0TessellateUV;


/**
 * 1個の面を作成するクラス
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvTessellateLow extends lvRoot {

	/**
	 * 1本のハーフエッジ点に関する「lvTessellateから送られるデータ用の内部クラス」
	 */
	public static class DownHalf {
		
		/** 頂点（ハーフエッジ始点）座標							*/
		public lvVector  pos					= null;
		
		/** ハンドルベクトル（インデックス 0:始点、 1:終点）		*/
		public lvVector  handVec[/*2*/]			= null;

		/** CBD「 bベクトル 」（インデックス 0:始点、 1:終点）			*/
		public lvVector  bCbd[/*2*/]			= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DownHalf( lvGlobal dt )
		{
			pos = new lvVector( dt );
			
			handVec = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )
				handVec[ i ] = new lvVector( dt );
				
			bCbd = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )
				bCbd[ i ] = new lvVector( dt );
		}
	}
	
	/**
	 * lvTessellateから送られるデータ用の内部クラス
	 */
	public static class DownTessellate {
		
		/** v方向（ハーフエッジNo0の終点→始点、ハーフエッジNo2の始点→終点）の分割数	*/
		public int       numDivV;
		/**u方向（ハーフエッジNo1の始点→終点、ハーフエッジNo3の終点→始点）の分割数	*/
		public int       numDivU;
		
		/** ハーフエッジ情報部の配列（isDegenerate == true の時は、ハーフエッジNo3は無効）	*/
		public DownHalf  half[/*4*/]					= null;

		/** UV空間情報の配列		*/
		public lvTessellateUV.DownTessellateUV  uvInfo	= new lv0TessellateUV.DownTessellateUV();
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DownTessellate( lvGlobal dt )
		{
			half = new DownHalf[ 4 ];
			for( int i=0; i<4; i++ )
				half[ i ] = new DownHalf( dt );
		}
	}
	
	/**
	 * lvTessellateに送るデータ用の内部クラス
	 */
	public static class UpTessellate {
		
		/** 頂点数				*/
		public int             numVertex;
		/**
		 * 頂点座標の配列（有効数は頂点の数「 3以上 」） --- 初期値 null									<br>
		 * 頂点数は稜線分割数「lvToKAttr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
		 */
		public lvRec.PosNorHi  vertex[]							= null;
	
		/** 三角ポリゴン数		*/
		public int             numTriIndex;
		/**
		 * 3頂点No.Indexから成る三角ポリゴンの配列（有効数は三角ポリゴンの数「 1以上 」） --- 初期値 null	<br>
		 * 三角ポリゴン数は稜線分割数「lvToKAttr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
		 */
		public lvRec.TriIndex  triIndex[]						= null;

		/** UV空間情報の配列		*/
		public lvTessellateUV.UpTessellateUV  uvInfo	= new lv0TessellateUV.UpTessellateUV();
	}
		
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** 下位レイヤーオブジェクト							*/
		private lvGregoryStd    gregoryStd				= null;

		/** 下位レイヤーに送るデータ			*/
		private lvGregoryStd.DownGregoryStd      downGregoryStd		= null;

		/** カレントの「上位レイヤーから送られるデータ」		*/
		private DownTessellate  curDownTessellate		= null;
		/** カレントの「上位レイヤーに送るデータ」				*/
		private UpTessellate      curUpTessellate		= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			gregoryStd        = new lvGregoryStd( dt );
			downGregoryStd    = new lvGregoryStd.DownGregoryStd( dt );
			
			curDownTessellate = new DownTessellate( dt );
			curUpTessellate   = new UpTessellate();
			
			GlobalTmp( dt );
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvAryExecMain[/*5*/]				= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvAryExecMain = new lvVector[ 5 ];	for( int i=0; i<5; i++ )	tvAryExecMain[ i ] = new lvVector( dt );
		}

	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gTessellateLow;
	}
	/** DownGregoryStd用のグローバルデータ			*/
	private final lvGregoryStd.DownGregoryStd
	DownGregoryStd()
	{
		return  Gbl().downGregoryStd;
	}
	/** DownTessellate用のグローバルデータ			*/
	private final lvTessellateLow.DownTessellate
	DownTessellate()
	{
		return  Gbl().curDownTessellate;
	}
	/** UpRound用のグローバルデータ				*/
	private final lvTessellateLow.UpTessellate
	UpTessellate()
	{
		return  Gbl().curUpTessellate;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvTessellateLow( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	/**
	 * 実行関数
	 */
	public final void
	Exec( DownTessellate downTessellate, UpTessellate upTessellate ) throws lvThrowable
	{
		Gbl().curDownTessellate = downTessellate;
		Gbl().curUpTessellate   = upTessellate;
		
		ExecStd();
	}
	
	private final void
	ExecStd() throws lvThrowable
	{
		DownGregoryStd().numDivU = DownTessellate().numDivU;
		DownGregoryStd().numDivV = DownTessellate().numDivV;
		
		for( int i=0; i<4; i++ )
			ExecMain( i, DownGregoryStd().ctlPnt );
			
		DownGregoryStd().uvInfo = DownTessellate().uvInfo;
			
		Gbl().gregoryStd.Exec( DownGregoryStd(), UpTessellate() );
	}
	
	private final void
	ExecMain( int halfNo, double ctlPnt[/*20*/][/*3*/] ) throws lvThrowable
	{
		lvVector  ctl[/*5*/] = Gbl().tvAryExecMain;
		
		int  halfB = ( halfNo + 4 - 1 ) % 4;
		
		ctl[ 2 ].Assign( DownTessellate().half[ halfNo ].pos );
		
		ctl[ 1 ].Assign( DownTessellate().half[ halfB  ].handVec[ 1 ].Add( ctl[ 2 ] ) );
				// ctl[ 1 ] = DownTessellate().half[ halfB  ].handVec[ 1 ] + ctl[ 2 ];
		ctl[ 3 ].Assign( DownTessellate().half[ halfNo ].handVec[ 0 ].Add( ctl[ 2 ] ) );
				// ctl[ 3 ] = DownTessellate().half[ halfNo ].handVec[ 0 ] + ctl[ 2 ];
		
		ctl[ 0 ].Assign( DownTessellate().half[ halfB  ].bCbd[ 1 ].Add( ctl[ 1 ] ) );
				// ctl[ 0 ] = DownTessellate().half[ halfB  ].bCbd[ 1 ] + ctl[ 1 ];
		ctl[ 4 ].Assign( DownTessellate().half[ halfNo ].bCbd[ 0 ].Add( ctl[ 3 ] ) );
				// ctl[ 4 ] = DownTessellate().half[ halfNo ].bCbd[ 0 ] + ctl[ 3 ];
		
		for( int i=0; i<5; i++ ) {
			ctlPnt[ halfNo*5 + i ][ 0 ] = ctl[ i ].x;
			ctlPnt[ halfNo*5 + i ][ 1 ] = ctl[ i ].y;
			ctlPnt[ halfNo*5 + i ][ 2 ] = ctl[ i ].z;
		}
	}

	public static final int
	NumVertexStd( int numDivU, int numDivV )
	{
		return  ( numDivU + 1 ) * ( numDivV + 1 );
	}
	
	public static final int
	NumTriIndexStd( int numDivU, int numDivV )
	{
		return  numDivU * numDivV * 2;
	}

}
