//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDebugMode.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * �f�o�b�O�p�W���o�͂̐ݒ���s���N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDebugMode extends lvRoot {
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/**
		 * �f�o�b�O�p�W���o�̓��[�h					<br>
		 * �o�͂����Ȃ�: false,	�o�͂��s��: true
		 */
		private boolean  debugMode  = false;

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
		return  ( ( lvComGblElm )global.GCom() ).gDebugMode;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvDebugMode( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * �f�o�b�O�p�W���o�͂��s�����[�h�ɂ���
	 */
/*
	public final void
	SetDebugMode()
	{
		Gbl().debugMode = true;
	}
*/
	/**
	 * �f�o�b�O�p�W���o�͂����Ȃ����[�h�ɂ���
	 */
/*
	public final void
	ResetDebugMode()
	{
		Gbl().debugMode = false;
	}
*/
	
	/**
	 * �f�o�b�O���[�h���� System.out.print() ���s��
	 * @param  s		(( I )) �W���o�͕�����
	 */
/*
	public final void
	Print( String s )
	{
		if( Gbl().debugMode == true )
			System.out.print( s );
	}
*/
	/**
	 * �f�o�b�O���[�h���� System.out.println() ���s��
	 * @param  s		(( I )) �W���o�͕�����
	 */
	public final void
	Println( String s )
	{
		if( Gbl().debugMode == true )
			System.out.println( s );
	}
}
