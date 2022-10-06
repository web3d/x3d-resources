//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDebugMode.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * デバッグ用標準出力の設定を行うクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDebugMode extends lvRoot {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/**
		 * デバッグ用標準出力モード					<br>
		 * 出力をしない: false,	出力を行う: true
		 */
		private boolean  debugMode  = false;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt ) {  }
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gDebugMode;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvDebugMode( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * デバッグ用標準出力を行うモードにする
	 */
/*
	public final void
	SetDebugMode()
	{
		Gbl().debugMode = true;
	}
*/
	/**
	 * デバッグ用標準出力をしないモードにする
	 */
/*
	public final void
	ResetDebugMode()
	{
		Gbl().debugMode = false;
	}
*/
	
	/**
	 * デバッグモード時に System.out.print() を行う
	 * @param  s		(( I )) 標準出力文字列
	 */
/*
	public final void
	Print( String s )
	{
		if( Gbl().debugMode == true )
			System.out.print( s );
	}
*/
	/**
	 * デバッグモード時に System.out.println() を行う
	 * @param  s		(( I )) 標準出力文字列
	 */
	public final void
	Println( String s )
	{
		if( Gbl().debugMode == true )
			System.out.println( s );
	}
}
