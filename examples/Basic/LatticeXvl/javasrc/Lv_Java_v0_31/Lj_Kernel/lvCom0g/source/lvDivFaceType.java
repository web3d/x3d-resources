//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFaceType.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * DivPoly情報の作成クラス（下位レイヤー）
 * @author	  created by Eishin Matsui (00/04/03-)
 * 
 */
public class lvDivFaceType {

	/**
	 * １個のハーフエッジに関する「上位レイヤー(topo0)から送られるデータ用（DownDivFace）の内部クラス」
	 */
	public static class DownHalf {
		
		/** 頂点（ハーフエッジ始点）座標						*/
		public lvVector  pos					= null;
		
		/** ハンドルベクトル（インデックス 0:始点、 1:終点）	*/
		public lvVector  handVec[/*2*/]			= null;

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
		}
	}
	
	/**
	 * シェル属性に関する「上位レイヤー(topo0)から送られるデータ用（DownDivFace）の内部クラス」
	 */
	public static class DownAttr {
	
		/** 稜線分割数（ 2の倍数 ） --- 初期値：4					*/
		public int		numDiv		= 4;
	}
	
	/**
	 * 上位レイヤー(topo0)から送られるデータ用（DownDivFace）の内部クラス
	 */
	public static class DownDivFace {
		
		/** その面のハーフエッジ数（＝half[] の有効配列数「 3以上 」）	*/
		public int	     numHalf;
		/** ハーフエッジ情報配列（1個の面のハーフエッジ数以上の長さで存在する「 3以上 」） --- 初期値 null		*/
		public DownHalf  half[]				= null;
		
		/** 面の中心位置座標と面法線		*/
		public lvRec.PosNorHi  center		= null;
		
		/** シェル内 属性情報				*/
		public DownAttr        attr			= new DownAttr();
	
		/** * UV空間情報	*/
		public lvDivFaceUVtype.DownDivFaceUV  divFaceUV = new lvDivFaceUVtype.DownDivFaceUV();

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DownDivFace( lvGlobal dt )
		{
			center = new lvRec.PosNorHi( dt );
		}
	}
		
	/**
	 * 上位レイヤー(topo0)に送るデータ用（UpDivFace）の内部クラス
	 */
	public static class UpDivFace {
		
		/**
		 * 頂点座標の配列（頂点数だけ存在する「 3以上 」） --- 初期値 null										<br>
		 * 頂点数は稜線分割数「lvToKAttr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
		 */
		public lvRec.PosNorHi  vertex[]			= null;
	
		/**
		 * 3頂点No.Indexから成る三角ポリゴンの配列（三角ポリゴン数だけ存在する「 1以上 」） --- 初期値 null	<br>
		 * 三角ポリゴン数は稜線分割数「lvToKAttr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
		 */
		public lvRec.TriIndex  triIndex[]		= null;
		
		/** * UV空間情報	*/
		public lvDivFaceUVtype.UpDivFaceUV  divFaceUV = new lvDivFaceUVtype.UpDivFaceUV();
	}
	
	/**
	 * 内部稜線に関する「DownDivFaceから派生したデータの内部クラス」
	 */
	public static class DeriveInner {
		
		/** ハンドルベクトル（インデックス 0:内部稜線の外部稜線上の点、 1:内部稜線の中心側の点）	*/
		public lvVector  handVec[/*2*/]			= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DeriveInner( lvGlobal dt )
		{
			handVec = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )
				handVec[ i ] = new lvVector( dt );
		}
	}
	
	/**
	 * DownDivFaceから派生したデータの内部クラス
	 */
	public static class DeriveDivFace {
		
		/** 内部稜線が集まる中心点の位置と法線				*/
		public lvRec.PosNorHi  center			= null;
		
		/** 内部稜線データ配列（長さは内部稜線数）			*/
		public DeriveInner     inner[]			= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DeriveDivFace( lvGlobal dt )
		{
			center = new lvRec.PosNorHi( dt );
		}
	}

}
