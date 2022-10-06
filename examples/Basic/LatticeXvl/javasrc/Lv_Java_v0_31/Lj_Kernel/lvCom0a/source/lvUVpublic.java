//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvUVpublic.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のシェル内 「外部UV空間（テクスチャ用）」情報クラス
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvUVpublic {

	/**
	 * 「（内部）UV空間No.」毎のUV空間情報の内部クラス
	 */
	public static class UVspace {
		
		/** 外部UV空間No.		*/
		public int  uvPublicNo;
		
	}
	
// -------------------------------------------------------------------

	/**
	 * 「外部UV空間No.」と「（内部）UV空間No.」の対応テーブル（当シェル内の外部UV空間の数だけ	<br>
	 *  存在する「0以上」）。lvToKernelUV.numUVspace は外部UV空間の数であり、					<br>
	 *  lvToKernelUV.GsUV やlvToKernelUV.VtxUV 内の uvSpaceNo は、外部UV空間No.である。			<br>
	 * 「外部UV空間No.」は,単一または複数の「UV空間No.」を持っている。							<br>
	 *  また uvPablic[].num の合計は、（内部）UV空間No.の個数と一致する
	 */
	public lvRec.SeqPart  uvPublic[]			= null;
	
	/**
	 * 「（内部）UV空間No.」毎のUV空間情報の配列												<br>
	 * （当シェル内の（内部）UV空間の数だけ存在する「0以上」）--- 初期値 null
	 */
	public UVspace        uvSpace[]				= null;
	
	
// -------------------------------------------------------------------

/*
	public final void
	NewUvPablic( int num )
	{
		uvPublic = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			uvPublic[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewUvSpace( int num )
	{
		uvSpace = new UVspace[ num ];
		for( int i=0; i<num; i++ )
			uvSpace[ i ] = new UVspace();
	}
*/

}
