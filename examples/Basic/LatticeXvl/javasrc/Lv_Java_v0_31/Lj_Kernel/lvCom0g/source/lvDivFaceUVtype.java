//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFaceUVtype.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/04/03-)
 * 
 */
public class lvDivFaceUVtype {
	
	/**
	 * 1個のUV空間に対する「上位レイヤー(topo0)に送るデータ用（DownDivFaceUV）」の内部クラス
	 */
	public static class DownDivFaceUVone {
		
		/** vtxUV[] の有効配列数「 3以上 」	*/
		public int	   numVtxUV;
		
		/** 頂点のUV座標の配列（1個のポリゴンの頂点数以上の長さで存在する「 3以上 」） --- 初期値 null		*/
		public lvUVdt  vtxUV[]				= null;
		
		/** GS面中心のUV値		*/
		public lvUVdt  center				= new lvUVdt();
	}

	/**
	 * 上位レイヤー(topo0)に送るデータ用（DownDivFaceUV）の内部クラス
	 */
	public static class DownDivFaceUV {
		
		/** UV空間情報配列			*/
		public DownDivFaceUVone  uvSpace[]	= null;
	}
		
	/**
	 * 1個のUV空間に対する「上位レイヤー(topo0)から送られるデータ用（DownDivFaceUV）」の内部クラス
	 */
	public static class UpDivFaceUVone {
		
		/**
		 * 頂点UV座標の配列（頂点数だけ存在する「 3以上 」） --- 初期値 null						<br>
		 * 頂点数は稜線分割数「lvToKAttr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
		 */
		public lvUVdt  uv[]		= null;
	}

	/**
	 * 上位レイヤー(topo0)から送られるデータ用（DownDivFaceUV）の内部クラス
	 */
	public static class UpDivFaceUV {
		
		/** UV空間情報配列			*/
		public UpDivFaceUVone  uvSpace[]	= null;
	}
	
}