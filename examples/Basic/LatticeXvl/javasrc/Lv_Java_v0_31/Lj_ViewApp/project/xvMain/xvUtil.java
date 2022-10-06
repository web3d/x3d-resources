//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvUtil.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvUtil {

	public static final void
	Mul( lvVector src, x3pMatrix m, lvVector dst )
	{
		double  x, y, z;
		
		x  = src.x * m.m[0][0];
		x += src.y * m.m[1][0];
		x += src.z * m.m[2][0];
		x += m.m[3][0];				// * 1.0

		y  = src.x * m.m[0][1];
		y += src.y * m.m[1][1];
		y += src.z * m.m[2][1];
		y += m.m[3][1];				// * 1.0

		z  = src.x * m.m[0][2];
		z += src.y * m.m[1][2];
		z += src.z * m.m[2][2];
		z += m.m[3][2];
		
		dst.x = x;
		dst.y = y;
		dst.z = z;
	}
	
}

