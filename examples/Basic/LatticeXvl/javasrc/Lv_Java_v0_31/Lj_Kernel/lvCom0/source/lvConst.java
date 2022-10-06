//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvConst.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel 内で共通に扱う定数のクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvConst {
	
	/** 定数 --- π値			*/
	public static final double	LV_PI	   = 3.14159265358979323846;	// pi
	
	/** 定数 --- 2 * π値		*/
	public static final double	LV_2PI	   = 6.28318530717958647692;	// 2*pi
	
	/** 定数 --- π値 / 2		*/
	public static final double	LV_PI_2    = 1.57079632679489661923;	// pi/2
	

	/** 定数 --- √2			*/
	public static final double	LV_SQRT2   = 1.41421356237309504880;	// sqrt(2)

	/** 定数 --- 1 / √2		*/
	public static final double	LV_SQRT1_2 = 0.70710678118654752440;	// 1/sqrt(2)

	/** 定数 --- √3			*/
	public static final double	LV_SQRT3   = 1.73205080756887729352;	// sqrt(3)

	/** 定数 --- 1 / √3		*/
	public static final double	LV_SQRT1_3 = 0.57735026918962576451;	// 1/sqrt(3)

// -------------------------------------------------------------------

	/** 定数 --- 関数実行が成功した場合の戻り値
	 */
	public static final boolean  LV_SUCCESS		= false;

	/** 定数 --- 関数実行が失敗した場合の戻り値
	 */
	public static final boolean  LV_FAILURE		= true;

// -------------------------------------------------------------------

	/** lvToKernel.Attr.typeの種類		*/
	public static final int  LV_SS_POLYGON		= 0;
	public static final int  LV_SS_LATTICE		= 1;
	public static final int  LV_SS_GREGORY		= 2;
}
