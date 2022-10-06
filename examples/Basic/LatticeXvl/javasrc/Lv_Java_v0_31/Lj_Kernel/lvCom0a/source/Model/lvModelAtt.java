//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvModelAtt.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel �̃��f�����N���X
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvModelAtt extends lvRoot {
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/** �V�F���z��i�V�F�����������݂���j --- �����l null		*/
		public lvShellAtt  shell[]		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt ) {  }
	}

	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComAGblElm )global.GCom() ).gModelAtt;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvModelAtt( lvGlobal dt )
	{
		super( dt );
	}
}
