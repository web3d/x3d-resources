//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTextureCoordinate.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pTextureCoordinate extends x3pElement {
	
	public x3pFieldUV[]  data = null;

	public x3pTextureCoordinate()
	{
		type = EL_TEXTURECOORDINATE;
	}

}
