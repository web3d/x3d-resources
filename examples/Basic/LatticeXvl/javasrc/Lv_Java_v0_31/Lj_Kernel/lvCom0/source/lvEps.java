//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvEps.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * 許容誤差用のクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvEps extends lvRoot {
	
	/** l1xSqrt1_3 --- epsilon * sqrt(1/3) for length（あまり使う必要はない）
	 */
	public static final double  l1xSqrt1_3 = 0.57735026918962576451e-6;


	/** l1 --- epsilon for length（あまり使う必要はない）					*/
	public static final double  l1 = 1.0e-6;			// l1 --- epsilon for length
	
	/** l_1 --- epsilon for curvature（あまり使う必要はない）				*/
//	public static final double  l_1 = 1.0e-4;			// l_1 --- epsilon for curvature
	
	/** l1xl1 --- epsilon^2 for length（あまり使う必要はない）				*/
	public static final double  l1xl1 = 1.0e-12;		// l1xl1 --- epsilon^2 for length
	
	/** t1 --- tolerance for point/curve on surface（あまり使う必要はない）	*/
	public static final double  t1 = 1.0e-3;			// t1 --- tolerance for point/curve on surface
	
	/** a0 --- epsilon for angle/parameter（あまり使う必要はない）			*/
	public static final double  a0 = 1.0e-8;			// a0 --- epsilon for angle/parameter
	
	/** w0 --- epsilon for weight（あまり使う必要はない）					*/
//	public static final double  w0 = 1.0e-8;			// w0 --- epsilon for weight
	
	/** e0 --- epsilon for "double"（あまり使う必要はない）					*/
	public static final double  e0 = 1.0e-15;			// e0 --- epsilon for "double"

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvEps( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * r0 が eps の誤差内で 0 かどうか判定する（非推奨）
	 * @param  r0		(( I )) 判定データ
	 * @param  eps		(( I )) 許容誤差
	 * @return			非0: false,		0: true
	 */
	public static final boolean
	IsZero( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return ( -eps <= r0 && r0 <= eps ) ? true : false;
	}
	/**
	 * r0 が l1 の誤差内で 0 かどうか判定する（推奨）
	 * @param  r0		(( I )) 判定データ
	 * @return			非0: false,		0: true
	 */
	public static final boolean
	IsZero( double r0 )
	{
		return IsZero( r0, -1.0 );
	}

	/**
	 * r0 が eps * eps の誤差内で 0 かどうか判定する（非推奨）
	 * @param  r0		(( I )) 判定データ
	 * @param  eps		(( I )) 許容誤差
	 * @return			非0: false,		0: true
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
	 * r0 が l1xl1 の誤差内で 0 かどうか判定する（推奨）
	 * @param  r0		(( I )) 判定データ
	 * @return			非0: false,		0: true
	 */
	public static final boolean
	IsZero2( double r0 )
	{
		return IsZero2( r0, -1.0 );
	}

	/**
	 * r1 と r2 が eps の誤差内で等しいかどうか判定する（非推奨）
	 * @param  r1		(( I )) 判定データ0
	 * @param  r2		(( I )) 判定データ1
	 * @param  eps		(( I )) 許容誤差
	 * @return			等しくない: false,	等しい: true
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
	 * r1 と r2 が l1 の誤差内で等しいかどうか判定する（推奨）
	 * @param  r1		(( I )) 判定データ0
	 * @param  r2		(( I )) 判定データ1
	 * @return			等しくない: false,	等しい: true
	 */
	public static final boolean
	IsSame( double r1, double r2 )
	{
		return	IsSame( r1, r2, -1.0 );
	}

	/**
	 * r1 と r2 が eps に基づく誤差内で等しいかどうか判定する（非推奨）
	 * @param  r1		(( I )) 判定データ0
	 * @param  r2		(( I )) 判定データ1
	 * @param  eps		(( I )) 許容誤差
	 * @return			等しくない: false,	等しい: true
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
	 * r1 と r2 がある誤差内で等しいかどうか判定する（推奨）
	 * @param  r1		(( I )) 判定データ0
	 * @param  r2		(( I )) 判定データ1
	 * @return			等しくない: false,	等しい: true
	 */
	public final boolean
	IsSame21( double r1, double r2 ) throws lvThrowable
	{
		return	IsSame21( r1, r2, -1.0 );
	}
	
	/**
	 * r0 が正値であるか判定する（非推奨）
	 * @param  r0		(( I )) 判定データ
	 * @param  eps		(( I )) 許容誤差
	 * @return			非正値: false,	正値: true
	 */
	public static final boolean
	IsPosi( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 > eps ) ? true : false;
	}
	/**
	 * r0 が正値であるか判定する（推奨）
	 * @param  r0		(( I )) 判定データ
	 * @return			非正値: false,	正値: true
	 */
	public static final boolean
	IsPosi( double r0 )
	{
		return  IsPosi( r0, -1.0 );
	}

	/**
	 * r0 が正値または 0（誤差内）であるか判定する（非推奨）
	 * @param  r0		(( I )) 判定データ
	 * @param  eps		(( I )) 許容誤差
	 * @return			負値: false,	正値または 0: true
	 */
	public static final boolean
	IsPosiZero( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 >= -eps ) ? true : false;
	}
	/**
	 * r0 が正値または 0（誤差内）であるか判定する（推奨）
	 * @param  r0		(( I )) 判定データ
	 * @return			負値: false,	正値または 0: true
	 */
	public static final boolean
	IsPosiZero( double r0 )
	{
		return  IsPosiZero( r0, -1.0 );
	}

	/**
	 * r0 が負値であるか判定する（非推奨）
	 * @param  r0		(( I )) 判定データ
	 * @param  eps		(( I )) 許容誤差
	 * @return			非負値: false,	負値: true
	 */
	public static final boolean
	IsNega( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 < -eps ) ? true : false;
	}
	/**
	 * r0 が負値であるか判定する（推奨）
	 * @param  r0		(( I )) 判定データ
	 * @return			非負値: false,	負値: true
	 */
	public static final boolean
	IsNega( double r0 )
	{
		return  IsNega( r0, -1.0 );
	}

	/**
	 * r0 が負値または 0（誤差内）であるか判定する（非推奨）
	 * @param  r0		(( I )) 判定データ
	 * @param  eps		(( I )) 許容誤差
	 * @return			正値: false,	負値または 0: true
	 */
	public static final boolean
	IsNegaZero( double r0, double eps )
	{
		if( eps < 0.0 )
			eps = l1;
		return  ( r0 <= eps ) ? true : false;
	}
	/**
	 * r0 が負値または 0（誤差内）であるか判定する（推奨）
	 * @param  r0		(( I )) 判定データ
	 * @return			正値: false,	負値または 0: true
	 */
	public static final boolean
	IsNegaZero( double r0 )
	{
		return  IsNegaZero( r0, -1.0 );
	}

}
