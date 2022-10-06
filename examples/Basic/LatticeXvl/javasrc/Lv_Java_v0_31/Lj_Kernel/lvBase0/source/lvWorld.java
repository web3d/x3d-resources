//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvWorld.java
//

package jp.co.lattice.vKernel.core.b0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のワールド情報クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvWorld {

	/**
	 * Java Lattice Kernel の初期化関数。									<br>
	 * Java Lattice Kernel を使用する場合、必ず最初に当関数を実行する		<br>
	 * <pre>
	 * (( 例 ))
	 *      lvGlobal    mGlobal  = lvWorld.Init();
	 *                      ･
	 *                      ･
	 *      lvToKernel  toKernel = new lvToKernel( mGlobal );
	 * </pre>
	 * Init()を複数回行うことにより、モデル（シェルの集合）の再作成を行ったり、モデルを複数個とすることもできる
	 *
	 * @return			lvGlobalデータを出力。当ライブラリのコンストラクタは、この値を引数とするものが多い
	 */
	public static final lvGlobal
	Init()
	{
		lvGblCore  gblCore = new lvGblCore();
		lvGlobal   gbl     = new lvGlobal( gblCore );
		
		return  gbl;
	}
}
