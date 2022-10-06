//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivPolyUV.java
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvDivPolyUV extends lvRoot {
	
	/**
	 * 1個のUV空間に対する「上位レイヤー(topo0)に送るデータ用（DownDivPolyUV）」の内部クラス
	 */
	public static class DownDivPolyUVone {
		
		/** vtxUV[] の有効配列数「 3以上 」	*/
		public int	   numVtxUV;
		
		/** 頂点のUV座標の配列（1個のポリゴンの頂点数以上の長さで存在する「 3以上 」） --- 初期値 null		*/
		public lvUVdt  vtxUV[]				= null;
	}

	/**
	 * 上位レイヤー(topo0)に送るデータ用（DownDivPolyUV）の内部クラス
	 */
	public static class DownDivPolyUV {
		
		/** UV空間情報配列			*/
		public DownDivPolyUVone  uvSpace[]	= null;
	}
		
	/**
	 * 1個のUV空間に対する「上位レイヤー(topo0)から送られるデータ用（DownDivPolyUV）」の内部クラス
	 */
	public static class UpDivPolyUVone {
		
		/** vertex[] の有効配列数「 3以上 」	*/
		public int	   numVertex;
		/**
		 * 頂点UV座標の配列（頂点の数の MAX数だけ存在する「 3以上 」） --- 初期値 null				<br>
		 * n角形の MAX数 --- nが奇数の時: n + ( n - 3 ) / 2,	nが偶数の時: n + ( n - 2 ) / 2
		 */
		public lvUVdt  vertex[]				= null;
	}

	/**
	 * 上位レイヤー(topo0)から送られるデータ用（DownDivPolyUV）の内部クラス
	 */
	public static class UpDivPolyUV {
		
		/** UV空間情報配列			*/
		public UpDivPolyUVone  uvSpace[]	= null;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvDivPolyUV( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public void
	SetDownDivPolyUV( int shellNo, int gsNo, lvDivPolyUV.DownDivPolyUV downDivPolyUV0 ) throws lvThrowable
	{
	}
	
	public void
	SetUpDivPolyUV()
	{
	}
	
	public void
	Finish()
	{
	}

}
