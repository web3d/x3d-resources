//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pXvlVertex.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pXvlVertex extends x3pContent {
	
	public int      coordIndex      = null_int;			// #REQUIRED
	public double   roundingWeight  = null_double;
//	public boolean  temporaryVertex = false;

	public x3pXvlVertex()
	{
		type = EL_XVLVERTEX;
		
		int[]  defContType0 = {
			EL_MATERIAL
		};
		defContType = defContType0;
	}

}
