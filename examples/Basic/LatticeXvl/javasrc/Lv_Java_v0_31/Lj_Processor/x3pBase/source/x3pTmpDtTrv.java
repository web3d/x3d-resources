//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTmpDtTrv.java

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTmpDtTrv {

 	public static class ToKernelVtxGs {
 		
 		/** 面上の頂点か？ false:面上に無い、true:面上に有る	*/
		public boolean  onFace;
		
		/** 新しく振られた頂点No.		*/
		public int      newVtxNo;
		
		/** XYZデータ					*/
		public lvVecDt  xyz			= new lvVecDt();
	}

 	public static class ToKernelVtxUV {
 		
		/**
		 * 「その頂点が属するUV空間（テクスチャ）」の中で「その頂点がUV値を持っているUV空間」の総数が	<br>
		 * 入る。UV空間が存在しない場合やその頂点がUV値を持っていない場合は、0 とする。					<br>
		 * 当頂点がテクスチャ境界である時や、「UV空間の異なる多重マッピング」が成されている時で、		<br>
		 * その頂点が複数UV値を持つ場合は、2 以上となる													<br>
		 * --- 初期値 null
		 */
		public lvRec.SeqPart  vtxNumUV		= new lvRec.SeqPart();
		
		/** 頂点に対する「現在作業中のUV相対No」		*/
		public int            currentUVoffset;
		
		/** 頂点に対する「現在作業中のUVspaceNo」		*/
		public int            currentUVspaceNo;
	}
	
}
