//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTransform.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pTransform extends x3pContent {
	
	public x3pFieldVec3f     translation	= new x3pFieldVec3f( 0.0, 0.0, 0.0 );
	public x3pFieldRotation  rotation		= new x3pFieldRotation( 0.0, 0.0, 1.0, 0.0 );
	public x3pFieldVec3f     scale			= new x3pFieldVec3f( 1.0, 1.0, 1.0 );

	public x3pTransform()
	{
		type = EL_TRANSFORM;
		
		int[]  defContType0 = {
			EL_TRANSFORM,
			EL_XVLSHAPE
		};
		defContType = defContType0;
	}

}
