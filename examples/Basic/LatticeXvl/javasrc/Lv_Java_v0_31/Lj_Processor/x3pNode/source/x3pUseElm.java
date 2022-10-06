//
// Copyright (C) 1999-2001 Lattice Technology, Inc. All rights reserved.
//

//
// x3pUseElm.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/07/12-)
 */
public class x3pUseElm extends x3pElement {
	
	public String  use = null;

	public x3pUseElm()
	{
		type = EL_USE_ELM;
	}
	
	public x3pUseElm( int n )
	{
		type = n;
	}

}
