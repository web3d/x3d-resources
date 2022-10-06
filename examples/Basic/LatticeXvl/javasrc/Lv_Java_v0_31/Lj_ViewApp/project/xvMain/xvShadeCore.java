///
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvShadeCore.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvShadeCore extends xvRoot {
	
	// 環境
	public xvRend    rend;
	
	// 頂点
	public lvVector  vf0;
	public lvVector  vf1;
	public lvVector  vf2;

	// 色(R/G/B)
	public int		 c0;
	public int		 c1;
	public int		 c2;

	// フラグ
	public int		 flag;

	/**
	 * コンストラクタ
	 */
	public xvShadeCore( xvGlobal dt, xvRend p_rend )
	{
		super( dt );
		
		rend = p_rend;
	}
	
	public void
	Draw() throws lvThrowable
	{
	}
	
	/**
	 *	固定小数点
	 */
	class Vector3I {
		
		public int	x;
		public int	y;
		public int	z;

		public Vector3I()
		{
		}
		public Vector3I( Vector3I v )
		{
			x = v.x;
			y = v.y;
			z = v.z;
		}

		public Vector3I( double nx, double ny, double nz )
		{
			x = ( int )( nx * rend.FP );
			y = ( int )( ny * rend.FP );
			z = ( int )( nz * rend.FP );
		}

		public final Vector3I
		Set( double nx, double ny, double nz )
		{
			x = ( int )( nx * rend.FP );
			y = ( int )( ny * rend.FP );
			z = ( int )( nz * rend.FP );
			return this;
		}

		public final Vector3I
		Set( Vector3I v )
		{
			x = v.x;
			y = v.y;
			z = v.z;
			return this;
		}

		public final Vector3I
		Add( Vector3I v )
		{
			x += v.x;
			y += v.y;
			z += v.z;
			return this;
		}

		public final Vector3I
		Sub( Vector3I v )
		{
			x -= v.x;
			y -= v.y;
			z -= v.z;
			return this;
		}

		public final Vector3I
		Div( int d )
		{
			x /= d;
			y /= d;
			z /= d;
			return this;
		}

		public final Vector3I
		Mul( int d )
		{
			x *= d;
			y *= d;
			z *= d;
			return this;
		}
	}

	protected static final int  DITHER[][] = 
	{
		{  0*0x8000, 12*0x8000,	 2*0x8000, 14*0x8000 },
		{  8*0x8000,  4*0x8000, 10*0x8000,  7*0x8000 },
		{  3*0x8000, 15*0x8000,  1*0x8000, 13*0x8000 },
		{ 11*0x8000,  6*0x8000,  9*0x8000,  5*0x8000 }
	};

}
