//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvUVdt.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * UV�^�ŁA�����o�ϐ��̂݋L�q�����N���X
 * @author	  created by Eishin Matsui (00/03/27-)
 * 
 */
public class lvUVdt {
	
	/** U�v�f */
	public double  u;
	/** V�v�f */
	public double  v;

// -------------------------------------------------------------------

	/**
	 * �R�s�[�֐�
	 * @param  src		(( I )) �R�s�[��
	 * @param  dst		(( O )) �R�s�[��
	 */
	public static final void
	Copy( lvUVdt src, lvUVdt dst )
	{
		dst.u = src.u;	dst.v = src.v;
	}

	/**
	 * lvVecDt�ϐ���U,V�l���Z�b�g����
	 * @param  u		(( I )) U�l
	 * @param  v		(( I )) V�l
	 * @param  dst		(( O )) �o�͐�
	 */
	public static final void
	SetUV( double u, double v, lvUVdt dst )
	{
		dst.u = u;  dst.v = v;
	}
	
}
