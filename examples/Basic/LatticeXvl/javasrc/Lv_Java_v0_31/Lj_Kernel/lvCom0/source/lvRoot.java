//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvRoot.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel �� Main�N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public abstract class lvRoot {
	
	/** �p���p�O���[�o���f�[�^		*/
	public lvGlobal  global				= null;

	/** �f�t�H���g�R���X�g���N�^�͋֎~				*/
	private lvRoot() {  }
	
	/** �O���[�o���f�[�^����͂���R���X�g���N�^	*/
	public  lvRoot( lvGlobal dt )
	{
		global = dt;
	}
	
	/** lvEps.eps�p�̃O���[�o���f�[�^				*/
	protected lvEps
	Eps()
	{
		return  ( ( lvComGblElm )global.GCom() ).eps;
	}
	/** lvError.err�p�̃O���[�o���f�[�^				*/
	protected lvError
	Err()
	{
		return  ( ( lvComGblElm )global.GCom() ).err;
	}
}
