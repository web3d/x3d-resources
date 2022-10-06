//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvFromKernelUV.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * UV空間（テクスチャ）に関する「Java Lattice Kernel から上位階層に引き渡すデータ」のクラス
 * @author	  created by Eishin Matsui (99/03/27-)
 * 
 */
public class lvFromKernelUV {

	/**
	 * UV空間No.
	 */
	public int     uvSpaceNo;
	
	/**
	 * UV座標値（配列長は、lvFromKernel.vertex と等しくなる）--- 初期値 null
	 */
	public lvUVdt  uv[]		= null;
	
}
