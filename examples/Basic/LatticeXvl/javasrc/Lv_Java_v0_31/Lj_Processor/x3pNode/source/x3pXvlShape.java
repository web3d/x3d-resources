//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pXvlShape.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pXvlShape extends x3pContent {
	
	public static final int TYPE_LINE_SEQUENCE   = 0x00;
	public static final int TYPE_BEZIER_SEQUENCE = 0x01;
	public static final int TYPE_POLYGON_MESH    = 0x02;
	public static final int TYPE_LATTICE_MESH    = 0x03;
	public static final int TYPE_GREGORY_MESH    = 0x04;

	public int  typeXvlShape	= null_int;			// #REQUIRED

	public x3pXvlShape()
	{
		type = EL_XVLSHAPE;
		
		int[]  defContType0 = {
			EL_XVLFACES,
			EL_XVLEDGES,
			EL_XVLVERTICES
		};
		defContType = defContType0;
	}

}
