//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvRoot.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel の Mainクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public abstract class lvRoot {
	
	/** 継承用グローバルデータ		*/
	public lvGlobal  global				= null;

	/** デフォルトコンストラクタは禁止				*/
	private lvRoot() {  }
	
	/** グローバルデータを入力するコンストラクタ	*/
	public  lvRoot( lvGlobal dt )
	{
		global = dt;
	}
	
	/** lvEps.eps用のグローバルデータ				*/
	protected lvEps
	Eps()
	{
		return  ( ( lvComGblElm )global.GCom() ).eps;
	}
	/** lvError.err用のグローバルデータ				*/
	protected lvError
	Err()
	{
		return  ( ( lvComGblElm )global.GCom() ).err;
	}
}
