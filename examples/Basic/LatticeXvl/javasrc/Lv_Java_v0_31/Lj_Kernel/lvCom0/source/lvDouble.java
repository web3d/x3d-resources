//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDouble.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * double�^�̐��l���Z�p���b�p�[�N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDouble extends lvDblCalc {

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvDouble( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * �����l val �̃R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 * @param  val		(( I )) �R���X�g���N�^�̏����l
	 */
	public	lvDouble( lvGlobal dt, double val )
	{
		super( dt, val );
	}

// -------------------------------------------------------------------

	/**
	 * lvDouble�^�̑���֐�
	 * @param  val		(( I )) ������B�K�� lvDouble�^�ϐ��A�܂��͐��l�֐����g������i lvDblCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvDouble
	Assign( lvDblCalc val )
	{
		this.val = val.val;
		return	this;
	}
*/

	/**
	 * ���g�ɒl���Z�b�g����
	 * @param  val		(( I )) double�l
	 * @return			this �̎Q��
	 */
	public final lvDouble
	Set( double val )
	{
		this.val = val;
		return	this;
	}
}
