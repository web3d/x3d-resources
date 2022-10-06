//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pXvlFace.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pXvlFace extends x3pContent {
	
	public int[]    coordIndex    = null;				// #REQUIRED
	public boolean  empty         = false;
	public int[]    texCoordIndex = null;

	public x3pXvlFace()
	{
		type = EL_XVLFACE;
		
		int[]  defContType0 = {
			EL_MATERIAL,
			EL_IMAGETEXTURE
		};
		defContType = defContType0;
	}

}
