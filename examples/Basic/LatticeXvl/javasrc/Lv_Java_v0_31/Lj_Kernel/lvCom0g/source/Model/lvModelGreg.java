//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvModelGreg.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel �̃��f�����N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvModelGreg extends lvRoot {
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/** �V�F���z��i�V�F�����������݂���j --- �����l null		*/
		public lvShellGreg  shell[]		= null;

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
		return  ( ( lv0ComGGblElm )global.GComG() ).gModelGreg;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvModelGreg( lvGlobal dt )
	{
		super( dt );
	}
}
