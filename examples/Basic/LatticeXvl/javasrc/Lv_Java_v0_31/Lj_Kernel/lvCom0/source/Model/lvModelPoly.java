//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvModelPoly.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel のモデル情報クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvModelPoly extends lvRoot {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** シェル配列（シェル数だけ存在する） --- 初期値 null		*/
		public lvShellPoly  shell[]		= null;

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
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvModelPoly( lvGlobal dt )
	{
		super( dt );
	}
}
