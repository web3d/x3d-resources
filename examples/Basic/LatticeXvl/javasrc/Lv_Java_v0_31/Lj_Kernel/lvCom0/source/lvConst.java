//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvConst.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel ���ŋ��ʂɈ����萔�̃N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvConst {
	
	/** �萔 --- �Βl			*/
	public static final double	LV_PI	   = 3.14159265358979323846;	// pi
	
	/** �萔 --- 2 * �Βl		*/
	public static final double	LV_2PI	   = 6.28318530717958647692;	// 2*pi
	
	/** �萔 --- �Βl / 2		*/
	public static final double	LV_PI_2    = 1.57079632679489661923;	// pi/2
	

	/** �萔 --- ��2			*/
	public static final double	LV_SQRT2   = 1.41421356237309504880;	// sqrt(2)

	/** �萔 --- 1 / ��2		*/
	public static final double	LV_SQRT1_2 = 0.70710678118654752440;	// 1/sqrt(2)

	/** �萔 --- ��3			*/
	public static final double	LV_SQRT3   = 1.73205080756887729352;	// sqrt(3)

	/** �萔 --- 1 / ��3		*/
	public static final double	LV_SQRT1_3 = 0.57735026918962576451;	// 1/sqrt(3)

// -------------------------------------------------------------------

	/** �萔 --- �֐����s�����������ꍇ�̖߂�l
	 */
	public static final boolean  LV_SUCCESS		= false;

	/** �萔 --- �֐����s�����s�����ꍇ�̖߂�l
	 */
	public static final boolean  LV_FAILURE		= true;

// -------------------------------------------------------------------

	/** lvToKernel.Attr.type�̎��		*/
	public static final int  LV_SS_POLYGON		= 0;
	public static final int  LV_SS_LATTICE		= 1;
	public static final int  LV_SS_GREGORY		= 2;
}
