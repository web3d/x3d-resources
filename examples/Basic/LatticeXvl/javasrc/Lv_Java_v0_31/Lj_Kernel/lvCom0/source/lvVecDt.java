//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvVecDt.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Vector�^�ŁA�����o�ϐ��̂݋L�q�����N���X�i�S�̃T�C�Y�̑傫���z��ȂǂŎg�p�j			<br>
 * lvVector.VecDt2Vector(), Vector2VecDt() �𗘗p���āAlvVector�^�Ƒ��ݕϊ����s��
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvVecDt {
	
	/** X�v�f */
	public double  x;
	/** Y�v�f */
	public double  y;
	/** Z�v�f */
	public double  z;

// -------------------------------------------------------------------

	/**
	 * �R�s�[�֐�
	 * @param  src		(( I )) �R�s�[��
	 * @param  dst		(( O )) �R�s�[��
	 */
	public static final void
	Copy( lvVecDt src, lvVecDt dst )
	{
		dst.x = src.x;	dst.y = src.y;	dst.z = src.z;
	}

	/**
	 * lvVecDt�ϐ���X,Y,Z�l���Z�b�g����
	 * @param  x		(( I )) X�l
	 * @param  y		(( I )) Y�l
	 * @param  z		(( I )) Z�l
	 * @param  dst		(( O )) �o�͐�
	 */
	public static final void
	SetXYZ( double x, double y, double z, lvVecDt dst )
	{
		dst.x = x;  dst.y = y;  dst.z = z;
	}
	
}
