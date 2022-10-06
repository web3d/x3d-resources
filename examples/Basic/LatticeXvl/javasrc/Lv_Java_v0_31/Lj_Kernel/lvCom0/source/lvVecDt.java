//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvVecDt.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Vector型で、メンバ変数のみ記述したクラス（全体サイズの大きい配列などで使用）			<br>
 * lvVector.VecDt2Vector(), Vector2VecDt() を利用して、lvVector型と相互変換を行う
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvVecDt {
	
	/** X要素 */
	public double  x;
	/** Y要素 */
	public double  y;
	/** Z要素 */
	public double  z;

// -------------------------------------------------------------------

	/**
	 * コピー関数
	 * @param  src		(( I )) コピー元
	 * @param  dst		(( O )) コピー先
	 */
	public static final void
	Copy( lvVecDt src, lvVecDt dst )
	{
		dst.x = src.x;	dst.y = src.y;	dst.z = src.z;
	}

	/**
	 * lvVecDt変数にX,Y,Z値をセットする
	 * @param  x		(( I )) X値
	 * @param  y		(( I )) Y値
	 * @param  z		(( I )) Z値
	 * @param  dst		(( O )) 出力先
	 */
	public static final void
	SetXYZ( double x, double y, double z, lvVecDt dst )
	{
		dst.x = x;  dst.y = y;  dst.z = z;
	}
	
}
