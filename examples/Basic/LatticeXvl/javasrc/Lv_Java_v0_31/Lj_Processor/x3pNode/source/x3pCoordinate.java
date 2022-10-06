//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pCoordinate.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pCoordinate extends x3pElement {
	
	public x3pFieldVec3f[]  data = null;

	public x3pCoordinate()
	{
		type = EL_COORDINATE;
	}

}
