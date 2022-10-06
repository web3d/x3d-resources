//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernelLow.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.lvToKernelUV;


/**
 * 上位階層から Java Lattice Kernel に引き渡すデータのクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvToKernelLow extends lvRoot {

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvToKernelLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * GS面の各頂点数（ 3以上 ）の配列（GS面の数だけ存在する「 1以上 」） --- 初期値 null
	 */
	public int	gsNumVtx[]		= null;
	
	/**
	 * GS面がn個ある場合、												<br>
	 * GS面0の頂点No列, GS面1の頂点No列, ---, GS面( n-1 )の頂点No列		<br>
	 * と続く。配列の長さは、											<br>
	 * GS面0の頂点数 + GS面1の頂点数 + --- + GS面( n-1 )の頂点数		<br>
	 * となる。 --- 初期値 null
	 */
	public int	gsVtxSeq[]		= null;
	
	/**
	 * NG面（constricの場合）の各頂点数（ 3以上 ）の配列（NG面の数だけ存在する） --- 初期値 null   <br>
	 * NG面の数が0個のときは、nullとしておく
	 */
	public int	ngNumVtx[]		= null;
	
	/**
	 * NG面（constricの場合）がn個ある場合、							<br>
	 * NG面0の頂点No列, NG面1の頂点No列, ---, NG面( n-1 )の頂点No列		<br>
	 * と続く。配列の長さは、											<br>
	 * NG面0の頂点数 + NG面1の頂点数 + --- + NG面( n-1 )の頂点数		<br>
	 * となる。 --- 初期値 null										<br>
	 * NG面の数が0個のときは、nullとしておく
	 */
	public int	ngVtxSeq[]		= null;
	
	
	/**
	 * 稜線情報部の配列（丸め係数を持つ稜線数以上の長さを持つ「Max:全稜線数」） --- 初期値 null   <br>
	 * 稜線情報部の数が0個のときは、nullとしておく
	 */
	public lvToKernelType.Edge    edge[]	  = null;
	
	
	/**
	 * 頂点情報部の配列（頂点数だけ存在する「 3以上 」） --- 初期値 null
	 */
	public lvToKernelType.Vertex  vertex[]    = null;

	/**
	 * UV空間情報（テクスチャ用）。当シェルがUV空間（テクスチャ）を持たない時は、null とする
	 */
	public lvToKernelUV           uvSpace     = null;
	
}
