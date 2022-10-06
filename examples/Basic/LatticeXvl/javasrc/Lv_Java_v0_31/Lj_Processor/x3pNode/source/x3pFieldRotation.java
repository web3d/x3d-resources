//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pFieldRotation.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pFieldRotation extends x3pField {
	
	public double  x;
	public double  y;
	public double  z;
	public double  r;

	public x3pFieldRotation( double px, double py, double pz, double pr )
	{
		x = px;   y = py;   z = pz;   r = pr;
	}

}
