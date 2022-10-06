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
 * Main�N���X
 * @author	  created by Eishin Matsui (00/03/23-)
 */
public abstract class xvRoot {
	
	/** �p���p�O���[�o���f�[�^		*/
	public xvGlobal  global		= null;

	/** �f�t�H���g�R���X�g���N�^�͋֎~				*/
	private xvRoot() {  }
	
	/** �O���[�o���f�[�^����͂���R���X�g���N�^	*/
	public xvRoot( xvGlobal dt )
	{
		global = dt;
	}

	private x3pGlobal
	Processor()
	{
		return global.processor;
	}
	/** lvError.err�p�̃O���[�o���f�[�^				*/
	protected lvError
	Err()
	{
		return  ( ( lvComGblElm )Processor().GCom() ).err;
	}

}
