//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pContent.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pContent extends x3pElement {
	
	public x3pElement[]  content;

	public x3pContent()
	{
		type = EL_CONTENT;
	}

	public x3pContent( int n, int[] defContType0 )
	{
		type = n;
		defContType = defContType0;
	}
	
}
