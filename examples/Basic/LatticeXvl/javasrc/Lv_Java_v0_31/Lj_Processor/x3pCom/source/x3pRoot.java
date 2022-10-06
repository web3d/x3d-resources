//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pRoot.java
//

package jp.co.lattice.vProcessor.com;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * Java XVL3 Processor �� Main�N���X
 * @author	  created by Eishin Matsui (00/03/23-)
 * 
 */
public abstract class x3pRoot {
	
	/** �p���p�O���[�o���f�[�^		*/
	public x3pGlobal  global		= null;

	/** �f�t�H���g�R���X�g���N�^�͋֎~				*/
	private x3pRoot() {  }
	
	/** �O���[�o���f�[�^����͂���R���X�g���N�^	*/
	public  x3pRoot( x3pGlobal dt )
	{
		global = dt;
	}
	
	/** lvEps.eps�p�̃O���[�o���f�[�^				*/
	protected lvEps
	Eps()
	{
		return   ( ( lvComGblElm )global.GCom() ).eps;
	}
	/** lvError.err�p�̃O���[�o���f�[�^				*/
	protected lvError
	Err()
	{
		return   ( ( lvComGblElm )global.GCom() ).err;
	}
}
