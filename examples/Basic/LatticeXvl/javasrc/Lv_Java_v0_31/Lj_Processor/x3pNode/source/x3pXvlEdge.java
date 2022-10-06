//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pXvlEdge.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pXvlEdge extends x3pContent {
	
	public int[]          coordIndex     = null;			// #REQUIRED
	public int[/*2*/]     vectorStartEnd = null;
	public double         roundingWeight = null_double;

	public x3pXvlEdge()
	{
		type = EL_XVLEDGE;
		
		int[]  defContType0 = {
			EL_MATERIAL
		};
		defContType = defContType0;
	}

}
