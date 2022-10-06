//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvFromKernelLow.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.lvFromKernelUV;
import	jp.co.lattice.vKernel.core.g0.lvDivPolyLow;


/**
 * Java Lattice Kernel から上位階層に引き渡すデータのクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvFromKernelLow extends lvRoot {
	
	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvFromKernelLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 頂点座標の配列（頂点の数だけ存在する「 3以上 」） --- 初期値 null									<br>
	 * 頂点数は稜線分割数「lvToKernelType.Attr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
	 */
	public lvRec.PosNorLow  vertex[]		= null;
	
	/**
	 * 3頂点No.Indexから成る三角ポリゴンの配列（三角ポリゴンの数だけ存在する「 1以上 」） --- 初期値 null	<br>
	 * 三角ポリゴン数は稜線分割数「lvToKernelType.Attr.numDiv を lvToKernel.SetAttr() でセット」などで異なる
	 */
	public lvRec.TriIndex   triIndex[]		= null;

	/**
	 * UV空間情報（テクスチャ用）。当シェルがUV空間（テクスチャ）を持たない時は、null となる。	<br>
	 * 多重マッピングの場合は、配列長は 2 以上となる --- 初期値 null
	 */
	public lvFromKernelUV    uvSpace[]		= null;
	
}
