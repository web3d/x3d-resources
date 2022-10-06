//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvShellPoly.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel のシェル情報（幾何＋位相）クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvShellPoly {
	
	/**
	 * 属性内部クラス
	 */
	public static class Attr {
	
		/** 稜線分割数（ 2の倍数 ） --- 初期値：4					*/
		public int  numDiv		= 4;
	
		/**
		 * 当シェルの種類
		 * lvConst.LV_SS_POLYGON、lvConst.LV_SS_LATTICE、lvConst.LV_SS_GREGORY のいづれか
		 */
		public int  type		= lvConst.LV_SS_POLYGON;
	}

// -------------------------------------------------------------------
	
	/** シェル内格子情報							*/
	public lvPolygon  poly					= null;

	/** シェル内 属性情報							*/
	public Attr       attr					= new Attr();
	
}
