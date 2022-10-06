//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvModelAtt.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のモデル情報クラス
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvModelAtt extends lvRoot {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** シェル配列（シェル数だけ存在する） --- 初期値 null		*/
		public lvShellAtt  shell[]		= null;

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
		return  ( ( lvComAGblElm )global.GCom() ).gModelAtt;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvModelAtt( lvGlobal dt )
	{
		super( dt );
	}
}
