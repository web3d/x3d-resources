//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvEps.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * ���e�덷�p�̃N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvEps extends lvRoot {
	
	/** l1xSqrt1_3 --- epsilon * sqrt(1/3) for length�i���܂�g���K�v�͂Ȃ��j
	 */
	public static final double  l1xSqrt1_3 = 0.57735026918962576451e-6;


	/** l1 --- epsilon for length�i���܂�g���K�v�͂Ȃ��j					*/
	public static final double  l1 = 1.0e-6;			// l1 --- epsilon for length
	
	/** l_1 --- epsilon for curvature�i���܂�g���K�v�͂Ȃ��j				*/
//	public static final double  l_1 = 1.0e-4;			// l_1 --- epsilon for curvature
	
	/** l1xl1 --- epsilon^2 for length�i���܂�g���K�v�͂Ȃ��j				*/
	public static final double  l1xl1 = 1.0e-12;		// l1xl1 --- epsilon^2 for length
	
	/** t1 --- tolerance for point/curve on surface�i���܂�g���K�v�͂Ȃ��j	*/
	public static final double  t1 = 1.0e-3;			// t1 --- tolerance for point/curve on surface
	
	/** a0 --- epsilon for angle/parameter�i���܂�g���K�v�͂Ȃ��j			*/
	public static final double  a0 = 1.0e-8;			// a0 --- epsilon for angle/parameter
	
	/** w0 --- epsilon for weight�i���܂�g���K�v�͂Ȃ��j					*/
//	public static final double  w0 = 1.0e-8;			// w0 --- epsilon for weight
	
	/** e0 --- epsilon for "double"�i���܂�g���K�v�͂Ȃ��j					*/
	public static final double  e0 = 1.0e-15;			// e0 --- epsilon for "double"

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvEps( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * r0 �� eps �̌덷���� 0 ���ǂ������肷��i�񐄏��j
	 * @param  r0		(( I )) ����f�[�^
	 * @param  eps		(( I )) ���e�덷
	 * @return			��0: false,		0: true
	 */
	public static final boolean
	IsZero( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return ( -eps <= r0 && r0 <= eps ) ? true : false;
	}
	/**
	 * r0 �� l1 �̌덷���� 0 ���ǂ������肷��i�����j
	 * @param  r0		(( I )) ����f�[�^
	 * @return			��0: false,		0: true
	 */
	public static final boolean
	IsZero( double r0 )
	{
		return IsZero( r0, -1.0 );
	}

	/**
	 * r0 �� eps * eps �̌덷���� 0 ���ǂ������肷��i�񐄏��j
	 * @param  r0		(( I )) ����f�[�^
	 * @param  eps		(( I )) ���e�덷
	 * @return			��0: false,		0: true
	 */
	public static final boolean
	IsZero2( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		double	eps2 = ( eps == l1 ) ? l1xl1 : eps * eps;
		return ( -eps2 <= r0 && r0 <= eps2 ) ? true : false;
	}
	/**
	 * r0 �� l1xl1 �̌덷���� 0 ���ǂ������肷��i�����j
	 * @param  r0		(( I )) ����f�[�^
	 * @return			��0: false,		0: true
	 */
	public static final boolean
	IsZero2( double r0 )
	{
		return IsZero2( r0, -1.0 );
	}

	/**
	 * r1 �� r2 �� eps �̌덷���œ��������ǂ������肷��i�񐄏��j
	 * @param  r1		(( I )) ����f�[�^0
	 * @param  r2		(( I )) ����f�[�^1
	 * @param  eps		(( I )) ���e�덷
	 * @return			�������Ȃ�: false,	������: true
	 */
	public static final boolean
	IsSame( double r1, double r2, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		double	r0 = r1 - r2;
		return	( -eps <= r0 && r0 <= eps ) ? true : false;
	}
	/**
	 * r1 �� r2 �� l1 �̌덷���œ��������ǂ������肷��i�����j
	 * @param  r1		(( I )) ����f�[�^0
	 * @param  r2		(( I )) ����f�[�^1
	 * @return			�������Ȃ�: false,	������: true
	 */
	public static final boolean
	IsSame( double r1, double r2 )
	{
		return	IsSame( r1, r2, -1.0 );
	}

	/**
	 * r1 �� r2 �� eps �Ɋ�Â��덷���œ��������ǂ������肷��i�񐄏��j
	 * @param  r1		(( I )) ����f�[�^0
	 * @param  r2		(( I )) ����f�[�^1
	 * @param  eps		(( I )) ���e�덷
	 * @return			�������Ȃ�: false,	������: true
	 */
	public final boolean
	IsSame21( double r1, double r2, double eps ) throws lvThrowable
	{
		Err().Assert( ( r1 >= 0.0 && r2 >= 0.0 ), "lvEps.IsSame21(0)" );
		if( eps < 0.0 )
			eps = l1;
		double	r0 = r1 - r2 * r2;
		double	eps21 = 2.0 * r2 * eps;
		return ( -eps21 <= r0 && r0 <= eps21 ) ? true : false;
	}
	/**
	 * r1 �� r2 ������덷���œ��������ǂ������肷��i�����j
	 * @param  r1		(( I )) ����f�[�^0
	 * @param  r2		(( I )) ����f�[�^1
	 * @return			�������Ȃ�: false,	������: true
	 */
	public final boolean
	IsSame21( double r1, double r2 ) throws lvThrowable
	{
		return	IsSame21( r1, r2, -1.0 );
	}
	
	/**
	 * r0 �����l�ł��邩���肷��i�񐄏��j
	 * @param  r0		(( I )) ����f�[�^
	 * @param  eps		(( I )) ���e�덷
	 * @return			�񐳒l: false,	���l: true
	 */
	public static final boolean
	IsPosi( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 > eps ) ? true : false;
	}
	/**
	 * r0 �����l�ł��邩���肷��i�����j
	 * @param  r0		(( I )) ����f�[�^
	 * @return			�񐳒l: false,	���l: true
	 */
	public static final boolean
	IsPosi( double r0 )
	{
		return  IsPosi( r0, -1.0 );
	}

	/**
	 * r0 �����l�܂��� 0�i�덷���j�ł��邩���肷��i�񐄏��j
	 * @param  r0		(( I )) ����f�[�^
	 * @param  eps		(( I )) ���e�덷
	 * @return			���l: false,	���l�܂��� 0: true
	 */
	public static final boolean
	IsPosiZero( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 >= -eps ) ? true : false;
	}
	/**
	 * r0 �����l�܂��� 0�i�덷���j�ł��邩���肷��i�����j
	 * @param  r0		(( I )) ����f�[�^
	 * @return			���l: false,	���l�܂��� 0: true
	 */
	public static final boolean
	IsPosiZero( double r0 )
	{
		return  IsPosiZero( r0, -1.0 );
	}

	/**
	 * r0 �����l�ł��邩���肷��i�񐄏��j
	 * @param  r0		(( I )) ����f�[�^
	 * @param  eps		(( I )) ���e�덷
	 * @return			�񕉒l: false,	���l: true
	 */
	public static final boolean
	IsNega( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 < -eps ) ? true : false;
	}
	/**
	 * r0 �����l�ł��邩���肷��i�����j
	 * @param  r0		(( I )) ����f�[�^
	 * @return			�񕉒l: false,	���l: true
	 */
	public static final boolean
	IsNega( double r0 )
	{
		return  IsNega( r0, -1.0 );
	}

	/**
	 * r0 �����l�܂��� 0�i�덷���j�ł��邩���肷��i�񐄏��j
	 * @param  r0		(( I )) ����f�[�^
	 * @param  eps		(( I )) ���e�덷
	 * @return			���l: false,	���l�܂��� 0: true
	 */
	public static final boolean
	IsNegaZero( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 <= eps ) ? true : false;
	}
	/**
	 * r0 �����l�܂��� 0�i�덷���j�ł��邩���肷��i�����j
	 * @param  r0		(( I )) ����f�[�^
	 * @return			���l: false,	���l�܂��� 0: true
	 */
	public static final boolean
	IsNegaZero( double r0 )
	{
		return  IsNegaZero( r0, -1.0 );
	}

}
