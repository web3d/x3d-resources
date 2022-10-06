//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pXvlFacesEx.java
//

package jp.co.lattice.vProcessor.base;

// XVL3ÉmÅ[Éh
import	jp.co.lattice.vProcessor.node.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pXvlFacesEx extends x3pContent {
	
	public int      numGs;
	public int      numGsFaces;
	public int      numCommonGs;
	public boolean  existTexture;
	public int      numEachFace;

	public x3pXvlFacesEx()
	{
		type = EL_XVLFACES;
		
		int[]  defContType0 = {
			x3pElement.EL_COORDINATE,
			x3pElement.EL_TEXTURECOORDINATE,
			x3pElement.EL_MATERIAL,
			x3pElement.EL_IMAGETEXTURE,
			x3pElement.EL_XVLFACE
		};
		defContType = defContType0;
	}
}
