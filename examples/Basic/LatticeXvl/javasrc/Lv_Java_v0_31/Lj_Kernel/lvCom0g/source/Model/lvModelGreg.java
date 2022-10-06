//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvModelGreg.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のモデル情報クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvModelGreg extends lvRoot {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** シェル配列（シェル数だけ存在する） --- 初期値 null		*/
		public lvShellGreg  shell[]		= null;

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
		return  ( ( lv0ComGGblElm )global.GComG() ).gModelGreg;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvModelGreg( lvGlobal dt )
	{
		super( dt );
	}
}
