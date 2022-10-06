//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvShellAtt.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のシェル情報（属性）クラス
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvShellAtt {
	
	/**
	 * 外部UV空間（テクスチャ用）情報（ 外部UV空間を持たない場合は、null ） --- 初期値 null
	 */
	public lvUVpublic  uvPublic				= null;
	
	/**
	 * （内部）UV空間（テクスチャ用）情報（ UV空間を持たない場合は、null ） --- 初期値 null
	 */
	public lvUVspace   uvSpace				= null;
}
