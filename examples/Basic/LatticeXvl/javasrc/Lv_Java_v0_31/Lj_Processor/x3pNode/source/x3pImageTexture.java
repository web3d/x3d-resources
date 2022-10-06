//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pImageTexture.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pImageTexture extends x3pElement {
	
	public String   url			= null;
	public boolean  repeatS		= true;
	public boolean  repeatT		= true;

	public x3pImageTexture()
	{
		type = EL_IMAGETEXTURE;
	}

}
