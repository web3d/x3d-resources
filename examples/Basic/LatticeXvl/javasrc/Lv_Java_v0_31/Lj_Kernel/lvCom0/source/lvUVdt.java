//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvUVdt.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * UV型で、メンバ変数のみ記述したクラス
 * @author	  created by Eishin Matsui (00/03/27-)
 * 
 */
public class lvUVdt {
	
	/** U要素 */
	public double  u;
	/** V要素 */
	public double  v;

// -------------------------------------------------------------------

	/**
	 * コピー関数
	 * @param  src		(( I )) コピー元
	 * @param  dst		(( O )) コピー先
	 */
	public static final void
	Copy( lvUVdt src, lvUVdt dst )
	{
		dst.u = src.u;	dst.v = src.v;
	}

	/**
	 * lvVecDt変数にU,V値をセットする
	 * @param  u		(( I )) U値
	 * @param  v		(( I )) V値
	 * @param  dst		(( O )) 出力先
	 */
	public static final void
	SetUV( double u, double v, lvUVdt dst )
	{
		dst.u = u;  dst.v = v;
	}
	
}
