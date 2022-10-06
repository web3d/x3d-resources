//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvModelPoly.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel �̃��f�����N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvModelPoly extends lvRoot {
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/** �V�F���z��i�V�F�����������݂���j --- �����l null		*/
		public lvShellPoly  shell[]		= null;

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
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvModelPoly( lvGlobal dt )
	{
		super( dt );
	}
}
