//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDouble.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * double型の数値演算用ラッパークラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDouble extends lvDblCalc {

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvDouble( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * 初期値 val のコンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 * @param  val		(( I )) コンストラクタの初期値
	 */
	public	lvDouble( lvGlobal dt, double val )
	{
		super( dt, val );
	}

// -------------------------------------------------------------------

	/**
	 * lvDouble型の代入関数
	 * @param  val		(( I )) 代入元。必ず lvDouble型変数、または数値関数自身を入れる（ lvDblCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvDouble
	Assign( lvDblCalc val )
	{
		this.val = val.val;
		return	this;
	}
*/

	/**
	 * 自身に値をセットする
	 * @param  val		(( I )) double値
	 * @return			this の参照
	 */
	public final lvDouble
	Set( double val )
	{
		this.val = val;
		return	this;
	}
}
