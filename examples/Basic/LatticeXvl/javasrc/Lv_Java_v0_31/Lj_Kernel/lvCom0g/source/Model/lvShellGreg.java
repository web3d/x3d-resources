//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvShellGreg.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のシェル情報（幾何＋位相）クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvShellGreg {
	
	/** 各格子(GS面)の中心位置座標					*/
	public lvPolyDerive.PolyCenter  center	= new lvPolyDerive.PolyCenter();

	/** シェル内 Bezier格子（内部制御点無）情報		*/
	public lvPolyDerive.Bezgon      bez		= new lvPolyDerive.Bezgon();

}
