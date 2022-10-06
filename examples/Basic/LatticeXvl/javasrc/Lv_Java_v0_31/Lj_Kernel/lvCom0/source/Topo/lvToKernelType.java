//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernelType.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * 上位階層から Java Lattice Kernel に引き渡すデータのType定義クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvToKernelType {

	/**
	 * 稜線情報内部クラス
	 */
	public static class Edge {
	
		/** 頂点No（始点）				*/
		public int		v0;
	
		/** 頂点No（終点）				*/
		public int		v1;
	
		/** 全体丸め係数有無  無:false,  有:true   --- 初期値：false	*/
		public boolean	enableAll  = false;
		/** 全体丸め係数							*/
		public double	allRound;
	
		/**
		 * lvToKernel.Attr.type == lvConst.LV_SS_LATTICE の時、
		 *		始点丸め係数
		 *  	無い場合、「null」, 有る場合、「vec0 = new lvVecDt();」とする
		 * lvToKernel.Attr.type == lvConst.LV_SS_GREGORY の時、
		 *		始点微分ベクトル。必ず値を持ち、nullにはならない
		 */
		public lvVecDt	vec0	   = null;
	
		/**
		 * lvToKernel.Attr.type == lvConst.LV_SS_LATTICE の時、
		 *		終点丸め係数
		 *  	無い場合、「null」, 有る場合、「vec1 = new lvVecDt();」とする
		 * lvToKernel.Attr.type == lvConst.LV_SS_GREGORY の時、
		 *		終点微分ベクトル。必ず値を持ち、nullにはならない
		 */
		public lvVecDt	vec1	   = null;
	}

	/**
	 * 頂点情報内部クラス
	 */
	public static class Vertex {
	
		/** 頂点座標			*/
		public lvVecDt	pos			= new lvVecDt();
	
		/** 丸め係数			*/
		public double	round;
	
		/** 丸め係数有無  無:false,  有:true   --- 初期値：false	*/
		public boolean	enable		= false;
	}

	/**
	 * 上位階層から Java Lattice Kernel に引き渡す属性の内部クラス
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

}
