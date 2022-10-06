//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pWorld.java
//

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vProcessor.com.*;
import  jp.co.lattice.vKernel.core.b0.*;


/**
 * Java XVL3 Processor のワールド情報クラス
 * @author	  created by Eishin Matsui (00/03/11-)
 * 
 */
public class x3pWorld {

	/**
	 * Java XVL3 Processor の初期化関数。									<br>
	 * Java XVL3 Processor を使用する場合、必ず最初に当関数を実行する		<br>
	 * <pre>
	 * (( 例 ))
	 *      x3pGlobal       mGlobal   = x3pWorld.Init();
	 *                             ･
	 *                             ･
	 *      x3pToProcessor  toProcess = new x3pToProcessor( mGlobal );
	 * </pre>
	 * Init()を複数回行うことにより、ノードツリーやモデル（シェルの集合）の再作成を行ったり、		<br>
	 * ノードツリー＆モデルを複数個とすることもできる
	 *
	 * @return			x3pGlobalデータを出力。当ライブラリのコンストラクタは、この値を引数とするものが多い
	 */
	public static final x3pGlobal
	Init()
	{
		lvGblCore   gblCore    = new lvGblCore();
		x3pGblCore  gblCoreX3p = new x3pGblCore();
		x3pGlobal   gbl        = new x3pGlobal( gblCore, gblCoreX3p );
		
		return  gbl;
	}
}
