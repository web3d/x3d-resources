//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pFieldColor.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pFieldColor extends x3pField {
	
	public double  r;
	public double  g;
	public double  b;

	public x3pFieldColor( double pr, double pg, double pb )
	{
		r = pr;   g = pg;   b = pb;
	}

	public final int GetRGB()
	{
		int  ir, ig, ib;

		ir = ( int )( 0xff * r );
		ig = ( int )( 0xff * g );
		ib = ( int )( 0xff * b );

		return ( ( ir << 24 ) | ( ig << 24 ) | ( ib ) );
	}
	
}
