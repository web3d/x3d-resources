//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pColor.java
//

/*
	各成分を整数ＲＧＢ値で保持する。
	それぞれの成分は ０～２５５となる。
*/
package jp.co.lattice.vProcessor.com;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 *	カラー情報。各成分を整数RGB値で保持する。	<br>
 *		--- それぞれの成分は 0～255
 * @author	  created by Eishin Matsui (00/06/29-)
*/
public class x3pColor extends x3pRoot {

	/** 赤成分( 0～255 ) */
	public int	r;
	/** 緑成分( 0～255 ) */
	public int	g;
	/** 青成分( 0～255 ) */
	public int	b;


	/**
	 *	コンストラクタ
	 */
	public x3pColor( x3pGlobal dt )
	{
		super( dt );

		r = 0;
		g = 0;
		b = 0;
	}

	/**
	 *	コンストラクタ（整数値から）
	 */
	public x3pColor( x3pGlobal dt, int pr, int pg, int pb )
	{
		super( dt );

		r = pr;
		g = pg;
		b = pb;
	}

	/**
	 *	コンストラクタ（double値から）
	 */
	public x3pColor( x3pGlobal dt, double pr, double pg, double pb )
	{
		super( dt );

		r = ( int )( 0xff * pr );
		g = ( int )( 0xff * pg );
		b = ( int )( 0xff * pb );
	}

	/**
	 *	コンストラクタ（合成32bit値から）
	 */
	public x3pColor( x3pGlobal dt, int rgb )
	{
		super( dt );

		r = ( ( rgb >> 16 ) & 0xff );
		g = ( ( rgb >> 8  ) & 0xff );
		b = ( ( rgb	      ) & 0xff );
	}

	/**
	 *	コンストラクタ（コピー）
	 */
	public x3pColor( x3pGlobal dt, x3pColor c )
	{
		super( dt );

		r = c.r;
		g = c.g;
		b = c.b;
	}

	//-----------------------------------------------------
	//	代入
	//-----------------------------------------------------

	/**
	 *	セット
	 */
//	public final void
//	Set( int pr, int pg, int pb )
//	{
//		r = pr;
//		g = pg;
//		b = pb;
//	}

	public final void
	Set( double pr, double pg, double pb )
	{
		r = ( int )( 0xff * pr );
		g = ( int )( 0xff * pg );
		b = ( int )( 0xff * pb );
	}

	/**
	 *	マージされているＲＧＢ値でセット
	 */
	public final void
	Set( int rgb )
	{
		r = ( ( rgb >> 16 ) & 0xff );
		g = ( ( rgb >> 8  ) & 0xff );
		b = ( ( rgb	      ) & 0xff );
	}

	public final void
	Set( x3pColor c )
	{
		r = c.r;
		g = c.g;
		b = c.b;
	}

	/**
	 *	マージされたＲＧＢ値を返す
	 */
	public final int
	GetColor()
	{
		int  col;
		return col = ( int )( ( r<<16 ) | ( g<<8 ) | b );
	}

	/**
	 *	マージされたＲＧＢ値を返す
	 *		dで割った色を返す
	 *		固定小数化されたものを、合成int値として返す
	 */
	public final int
	DivGetColor( int d )
	{
		int  col;

		if( r > 0xff0000 )	r = 0xff0000;
		if( g > 0xff0000 )	g = 0xff0000;
		if( b > 0xff0000 )	b = 0xff0000;
		
		return col = ( int )( ( r&0xff0000 ) | ( ( g&0xff0000 )>>8 ) | ( b>>16 ) );
	}

	/**
	 *	加算
	 */
	public final x3pColor
	Add( int pr, int pg, int pb )
	{
		r += pr;
		g += pb;
		b += pg;
		
		return this;
	}

//	public final x3pColor
//	Add( int rgb )
//	{
//		int  pr = ( ( rgb >> 16 ) & 0xff );
//		int  pg = ( ( rgb >> 8  ) & 0xff );
//		int  pb = ( ( rgb	    ) & 0xff );
//
//		r += pr;
//		g += pg;
//		b += pb;
//		
//		return this;
//	}

	/**
	 *	差分
	 */
//	public static final x3pColor
//	Sub( x3pColor c0, x3pColor c1 )
//	{
//		x3pColor  c = new x3pColor( c0.r - c1.r, c0.g - c1.g, c0.b - c1.b );
//		return c;
//	}

	/**
	 *	引く
	 */
	public final x3pColor
	Sub( x3pColor c0 )
	{
		r -= c0.r;
		g -= c0.g;
		b -= c0.b;
		
		return this;
	}

	public final x3pColor
	Mul( x3pColor c0 )
	{
		r *= c0.r;
		g *= c0.g;
		b *= c0.b;
		
		return this;
	}

//	public final x3pColor
//	Mul( double pr, double pg,double pb )
//	{
//		r *= pr;
//		g *= pg;
//		b *= pb;
//		
//		return this;
//	}

	public final x3pColor
	Sub( int rgb )
	{
		int  pr = ( ( rgb >> 16 ) & 0xff );
		int  pg = ( ( rgb >> 8  ) & 0xff );
		int  pb = ( ( rgb	    ) & 0xff );

		r -= pr;
		g -= pg;
		b -= pb;
		
		return this;
	}

	public final x3pColor
	Add( x3pColor c0 )
	{
		r += c0.r;
		g += c0.g;
		b += c0.b;
		
		return this;
	}

	/**
	 *	除算
	 */
	public final x3pColor
	Div( int d ) throws lvThrowable
	{
		Err().Assert( ( d != 0 ), "x3pColor.div(0)" );
		
		r /= d;
		g /= d;
		b /= d;
		
		return this;
	}

	/**
	 *	乗算
	 */
	public final x3pColor
	Mul( int d )
	{
		r *= d;
		g *= d;
		b *= d;
		
		return this;
	}
	
	public final x3pColor
	Mul( double d )
	{
		r *= d;
		g *= d;
		b *= d;
		
		return this;
	}

}
