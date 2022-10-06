//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pRoot.java
//

package jp.co.lattice.vProcessor.com;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * Java XVL3 Processor の Mainクラス
 * @author	  created by Eishin Matsui (00/03/23-)
 * 
 */
public abstract class x3pRoot {
	
	/** 継承用グローバルデータ		*/
	public x3pGlobal  global		= null;

	/** デフォルトコンストラクタは禁止				*/
	private x3pRoot() {  }
	
	/** グローバルデータを入力するコンストラクタ	*/
	public  x3pRoot( x3pGlobal dt )
	{
		global = dt;
	}
	
	/** lvEps.eps用のグローバルデータ				*/
	protected lvEps
	Eps()
	{
		return   ( ( lvComGblElm )global.GCom() ).eps;
	}
	/** lvError.err用のグローバルデータ				*/
	protected lvError
	Err()
	{
		return   ( ( lvComGblElm )global.GCom() ).err;
	}
}
