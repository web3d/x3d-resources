//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// xvRoot.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;
import  jp.co.lattice.vKernel.core.c0.*;


/**
 * Mainクラス
 * @author	  created by Eishin Matsui (00/03/23-)
 */
public abstract class xvRoot {
	
	/** 継承用グローバルデータ		*/
	public xvGlobal  global		= null;

	/** デフォルトコンストラクタは禁止				*/
	private xvRoot() {  }
	
	/** グローバルデータを入力するコンストラクタ	*/
	public xvRoot( xvGlobal dt )
	{
		global = dt;
	}

	private x3pGlobal
	Processor()
	{
		return global.processor;
	}
	/** lvError.err用のグローバルデータ				*/
	protected lvError
	Err()
	{
		return  ( ( lvComGblElm )Processor().GCom() ).err;
	}

}
