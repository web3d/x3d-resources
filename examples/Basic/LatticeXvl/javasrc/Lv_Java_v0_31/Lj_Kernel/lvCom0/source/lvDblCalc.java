//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDblCalc.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * double型の数値演算用ラッパークラス
 * lvDouble型の数値演算用クラス<br>
 * 注）lvDblCalc,lvDouble,lvVecCalc,lvVector,lvMatCalc,lvMatrixクラス内以外では、lvDblCalcオブジェクトを生成してはならない
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDblCalc extends lvRoot {
	
	/** 実データ
	 */
	public double  val;
	
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** lvDblCalcのresult用の new 代用バッファ数			*/
		private static final int  num_resultBufD  = 128;
		/** lvDblCalcのresult用の new 代用バッファエリア		*/
		private lvDblCalc  resultBufD[]	   = null;
		/** lvDblCalcのresult用の new 代用バッファカウンタ		*/
		private int		   cnt_resultBufD  = 0;
		
		/** lvVecCalcのresult用の new 代用バッファ数			*/
		private static final int  num_resultBufV  = 128;
		/** lvVecCalcのresult用の new 代用バッファエリア		*/
		private lvVecCalc  resultBufV[]    = null;
		/** lvVecCalcのresult用の new 代用バッファカウンタ		*/
		private int		   cnt_resultBufV  = 0;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			resultBufD = new lvDblCalc[ num_resultBufD ];
			for( int i=0; i<num_resultBufD; i++ )
				resultBufD[ i ] = new lvDblCalc( dt );

			resultBufV = new lvVecCalc[ num_resultBufV ];
			for( int i=0; i<num_resultBufV; i++ )
				resultBufV[ i ] = new lvVecCalc( dt );
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gDblCalc;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvDblCalc( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * 初期値 val のコンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 * @param  val		(( I )) コンストラクタの初期値
	 */
	public	lvDblCalc( lvGlobal dt, double val )
	{
		super( dt );
		this.val = val;
	}

// -------------------------------------------------------------------

	/** lvDblCalcのresult用の new 代用バッファのインクリメント		*/
	private final void
	IncResultBufD()
	{
		Gbl().cnt_resultBufD = ( Gbl().cnt_resultBufD + 1 ) % Gbl().num_resultBufD;
	}

	/** lvVecCalcのresult用の new 代用バッファのインクリメント		*/
	private final void
	IncResultBufV()
	{
		Gbl().cnt_resultBufV = ( Gbl().cnt_resultBufV + 1 ) % Gbl().num_resultBufV;
	}

	/**
	 * lvDoubleオブジェクトとスカラー値 val を乗算する（非推奨）
	 * @param  val		(( I )) double型変数
	 * @param  result	(( O )) 必ず lvDouble型変数とする（ lvDblCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvDblCalc
	Mul( double val, lvDblCalc result )
	{
		result.val = val;
		return	result;
	}
	/**
	 * lvDoubleオブジェクトとスカラー値 val を乗算する（推奨）
	 * @param  val		(( I )) double型変数
	 * @return			result の参照
	 */
	public final lvDblCalc
	Mul( double val )
	{
		lvDblCalc  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDblCalc();
		IncResultBufD();
		return	Mul( val, result );
	}

	/**
	 * lvDoubleオブジェクトとベクトル val を乗算する（非推奨）
	 * @param  val		(( I )) 必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Mul( lvVecCalc val, lvVecCalc result )
	{
		result.Copy_Local( val.Mul( this.val ) );
		return	result;
	}
	/**
	 * lvDoubleオブジェクトとベクトル val を乗算する（推奨）
	 * @param  val		(( I )) 必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Mul( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Mul( val, result );
	}
}
