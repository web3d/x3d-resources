//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDblCalc.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * double�^�̐��l���Z�p���b�p�[�N���X
 * lvDouble�^�̐��l���Z�p�N���X<br>
 * ���jlvDblCalc,lvDouble,lvVecCalc,lvVector,lvMatCalc,lvMatrix�N���X���ȊO�ł́AlvDblCalc�I�u�W�F�N�g�𐶐����Ă͂Ȃ�Ȃ�
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDblCalc extends lvRoot {
	
	/** ���f�[�^
	 */
	public double  val;
	
// -------------------------------------------------------------------

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/** lvDblCalc��result�p�� new ��p�o�b�t�@��			*/
		private static final int  num_resultBufD  = 128;
		/** lvDblCalc��result�p�� new ��p�o�b�t�@�G���A		*/
		private lvDblCalc  resultBufD[]	   = null;
		/** lvDblCalc��result�p�� new ��p�o�b�t�@�J�E���^		*/
		private int		   cnt_resultBufD  = 0;
		
		/** lvVecCalc��result�p�� new ��p�o�b�t�@��			*/
		private static final int  num_resultBufV  = 128;
		/** lvVecCalc��result�p�� new ��p�o�b�t�@�G���A		*/
		private lvVecCalc  resultBufV[]    = null;
		/** lvVecCalc��result�p�� new ��p�o�b�t�@�J�E���^		*/
		private int		   cnt_resultBufV  = 0;
		
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			resultBufD = new lvDblCalc[ num_resultBufD ];
			for( int i=0; i<num_resultBufD; i++ )
				resultBufD[ i ] = new lvDblCalc( dt );

			resultBufV = new lvVecCalc[ num_resultBufV ];
			for( int i=0; i<num_resultBufV; i++ )
				resultBufV[ i ] = new lvVecCalc( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gDblCalc;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvDblCalc( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * �����l val �̃R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 * @param  val		(( I )) �R���X�g���N�^�̏����l
	 */
	public	lvDblCalc( lvGlobal dt, double val )
	{
		super( dt );
		this.val = val;
	}

// -------------------------------------------------------------------

	/** lvDblCalc��result�p�� new ��p�o�b�t�@�̃C���N�������g		*/
	private final void
	IncResultBufD()
	{
		Gbl().cnt_resultBufD = ( Gbl().cnt_resultBufD + 1 ) % Gbl().num_resultBufD;
	}

	/** lvVecCalc��result�p�� new ��p�o�b�t�@�̃C���N�������g		*/
	private final void
	IncResultBufV()
	{
		Gbl().cnt_resultBufV = ( Gbl().cnt_resultBufV + 1 ) % Gbl().num_resultBufV;
	}

	/**
	 * lvDouble�I�u�W�F�N�g�ƃX�J���[�l val ����Z����i�񐄏��j
	 * @param  val		(( I )) double�^�ϐ�
	 * @param  result	(( O )) �K�� lvDouble�^�ϐ��Ƃ���i lvDblCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvDblCalc
	Mul( double val, lvDblCalc result )
	{
		result.val = val;
		return	result;
	}
	/**
	 * lvDouble�I�u�W�F�N�g�ƃX�J���[�l val ����Z����i�����j
	 * @param  val		(( I )) double�^�ϐ�
	 * @return			result �̎Q��
	 */
	public final lvDblCalc
	Mul( double val )
	{
		lvDblCalc  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDblCalc();
		IncResultBufD();
		return	Mul( val, result );
	}

	/**
	 * lvDouble�I�u�W�F�N�g�ƃx�N�g�� val ����Z����i�񐄏��j
	 * @param  val		(( I )) �K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Mul( lvVecCalc val, lvVecCalc result )
	{
		result.Copy_Local( val.Mul( this.val ) );
		return	result;
	}
	/**
	 * lvDouble�I�u�W�F�N�g�ƃx�N�g�� val ����Z����i�����j
	 * @param  val		(( I )) �K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Mul( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Mul( val, result );
	}
}
