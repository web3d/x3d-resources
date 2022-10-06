//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvTessellateUV.java
//

package jp.co.lattice.vKernel.tex.a0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * 1個の面を作成するクラス
 * @author	  created by Eishin Matsui (00/04/02-)
 * 
 */
public class lvTessellateUV extends lvRoot {
	
	/**
	 * 1本のハーフエッジ点に関する「lvTessellateから送られるデータ用の内部クラス」
	 */
	public static class DownTessellateUVone {
		
		/**
		 * 頂点（ハーフエッジ始点）UV座標の配列（DownTessellate.isDegenerate == true				<br>
		 * の時は、「ハーフエッジNo0 == ハーフエッジNo3」）
		 */
		public lvUVdt  uv[/*4*/]		= null;
		
		/**
		 * コンストラクタ
		 */
		public  DownTessellateUVone()
		{
			uv = new lvUVdt[ 4 ];
			for( int i=0; i<4; i++ )
				uv[ i ] = new lvUVdt();
		}
	}
	
	/**
	 * lvTessellateから送られるデータ用の内部クラス
	 */
	public static class DownTessellateUV {
		
		/** uvSpace[] の有効配列数		*/
		public int                  numUVspace;
		
		/** UV空間情報配列			*/
		public DownTessellateUVone  uvSpace[]	= null;
	}
	
	/**
	 * lvTessellateに送るデータ用の内部クラス
	 */
	public static class UpTessellateUVone {
		
		/**
		 * 頂点UV座標の配列（頂点数だけ存在する「 3以上 」） --- 初期値 null						<br>
		 * 頂点数は稜線分割数「lvToKAttr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
		 */
		public lvUVdt  uv[]		= null;
	}
	
	/**
	 * lvTessellateに送るデータ用の内部クラス
	 */
	public static class UpTessellateUV {
		
		/** UV空間情報配列				*/
		public UpTessellateUVone  uvSpace[]		= null;
	}
	    
// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lvTessellateUV( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	public void
	Init( lvDivFaceType.DownDivFace	      downDivFace,
		  lvDivFaceType.DeriveDivFace     deriveDivFace,
		  lvDivFaceType.UpDivFace         upDivFace,
		  lvTessellateUV.DownTessellateUV downTessellate,
		  lvTessellateUV.UpTessellateUV   upTessellate )
	{
	}
	
	public void
	NewUpDivFaceUV( int numVertex )
	{
	}
	
	public void
	NewUpTessellateUV( lvRec.SeqPart infoVertex )
	{
	}

	public void
	SetQuad()
	{
	}
	
	public void
	SetGtQuad( int patchNo, int numPatch ) throws lvThrowable
	{
	}

	public void
	Finish()
	{
	}

}
