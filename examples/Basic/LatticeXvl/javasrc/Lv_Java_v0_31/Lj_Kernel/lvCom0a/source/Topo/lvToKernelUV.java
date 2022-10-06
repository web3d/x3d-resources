//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernelUV.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * UV空間（テクスチャ）に関する「上位階層から Java Lattice Kernel に引き渡すデータ」のクラス
 * @author	  created by Eishin Matsui (00/03/27-)
 * 
 */
public class lvToKernelUV {

	/**
	 * GS面に対するUV空間情報の内部クラス
	 */
	public static class GsUV {
		
		/** UV空間No.	*/
		public int  uvSpaceNo;
	}
	 
	/**
	 * 頂点に対するUV空間情報の内部クラス
	 */
	public static class VtxUV {
	
		/** UV空間No.	*/
		public int     uvSpaceNo;
	
		/**
		 * UV座標値
		 */
		public lvUVdt  uv		= new lvUVdt();
	}

// -------------------------------------------------------------------

	/**
	 * 当シェル内のUV空間（テクスチャ）の数を記述する											<br>
	 * これにより、UV空間No.は、0 ～ ( numUVspace - 1 ) となる --- 初期値 0
	 */
	public int  numUVspace;
	
	/**
	 * 各要素には、そのGS面が属するUV空間（テクスチャ）の数が入る。UV空間が存在しない場合は、	<br>
	 * 0 とする。「UV空間の異なる多重マッピング」が成されている時は、2 以上となる				<br>
	 * （配列長は、lvToKernel.gsNumVtx と等しくする）--- 初期値 null
	 */
	public int  gsNumUV[]					= null;
	
	/**
	 * GS面に対するUV空間情報の配列。GS面がn個ある場合、 										<br>
	 *     GS面0のUV空間情報列, GS面1のUV空間情報列, ---, GS面( n-1 )のUV空間情報列 			<br>
	 * と続く。配列の長さは、 																	<br>
	 *     gsNumUV[0] + gsNumUV[1] + --- + gsNumUV[n-1] 										<br>
	 * となる。 --- 初期値 null
	 */
	public lvToKernelUV.GsUV  gsUVseq[]		= null;
	
	/**
	 * 各要素には、「その頂点が属するUV空間（テクスチャ）」の中で「その頂点がUV値を持っている		<br>
	 * UV空間」の総数が入る。UV空間が存在しない場合やその頂点がUV値を持っていない場合は、0 とする。	<br>
	 * 当頂点がテクスチャ境界である時や、「UV空間の異なる多重マッピング」が成されている時で、		<br>
	 * その頂点が複数UV値を持つ場合は、2 以上となる（配列長は、lvToKernel.vertex と等しくする）		<br>
	 * --- 初期値 null
	 */
	public int  vtxNumUV[]					= null;
	
	/**
	 * 頂点に対するUV空間情報の配列。頂点がn個ある場合、 										<br>
	 *     頂点0のUV空間情報列, 頂点1のUV空間情報列, ---, 頂点( n-1 )のUV空間情報列 			<br>
	 * と続く。配列の長さは、 																	<br>
	 *     vtxNumUV[0] + vtxNumUV[1] + --- + vtxNumUV[n-1] 										<br>
	 * となる。配列長が 0 の時は、null とする --- 初期値 null
	 */
	public lvToKernelUV.VtxUV  vtxUVseq[]	= null;
	
}
