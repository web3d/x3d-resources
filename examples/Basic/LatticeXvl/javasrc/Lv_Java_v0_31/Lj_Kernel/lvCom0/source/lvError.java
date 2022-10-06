//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvError.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * �G���[�������̃N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvError extends lvRoot {

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/** lvDebugMode�I�u�W�F�N�g							*/
		private lvDebugMode  debug   		= null;
		
		/** lvConst.LV_FAILURE ��Ԃ����ꍇ�̃��b�Z�[�W		*/
		private String       message 		= null;
		
		/** throw���[�h										*/
		private int          throwModeCnt	= 0;

		// -------------------------------------------------------------------

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			debug = new lvDebugMode( dt );
		}

		// -------------------------------------------------------------------
		
		/** Throw���[�h���J�n����		*/
		public final void
		BeginThrowMode()
		{
			throwModeCnt++;
		}
		/** Throw���[�h���I������		*/
		public final void
		EndThrowMode()
		{
			if( throwModeCnt > 0 )
				throwModeCnt--;
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gError;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvError( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * ���� chk �� false �Ȃ�A�G���[���������s��
	 * @param  chk		(( I )) �G���[���莮
	 * @param  s		(( I )) �G���[���̏o�͕�����i�f�o�b�O���[�h���j
	 */
	public final void
	Assert( boolean chk, String s ) throws lvThrowable
	{
		if( chk == false ) {
			Gbl().debug.Println( s );
			
			if( Gbl().throwModeCnt > 0 ) {
				Gbl().message = new String( s );
				throw new lvThrowable();
			}
			else
				System.exit( 1 );
		}
	}
	
	/**
	 * ����֐��� lvConst.LV_FAILURE ��Ԃ����ꍇ�̃��b�Z�[�W�𓾂�i lvConst.LV_SUCCESS ��Ԃ����ꍇ�Anull�܂��͉ߋ��̃��b�Z�[�W�j
	 * @param  gbl		(( I )) lvWorld.Init()�ŏo�͂��� lvGlobal�f�[�^
	 * @return			���b�Z�[�W�i null �̏ꍇ���L��j
	 */
	public static final String
	Message( lvGlobal gbl )
	{
		return  ( ( lvComGblElm )gbl.GCom() ).gError.message;
	}
}
