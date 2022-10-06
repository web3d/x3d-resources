//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// x3pGblCore.java
//

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;

import	jp.co.lattice.vProcessor.base.x3pBaseGblElm;
import	jp.co.lattice.vProcessor.parse.x3pParseGblElm;
import	jp.co.lattice.vProcessor.node.x3pNodeGblElm;
import	jp.co.lattice.vProcessor.com.x3pComGblElm;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class x3pGblCore extends x3pGblCoreNull {

	private x3pGlobal  global	= null;

// -------------------------------------------------------------------

	public void
	Init( x3pGlobal dt )
	{
		global = dt;
		
		gBaseX3p  = new x3pBaseGblElm( global );
		gParseX3p = new x3pParseGblElm( global );
		gNodeX3p  = new x3pNodeGblElm( global );
		gComX3p   = new x3pComGblElm( global );
	}

// -------------------------------------------------------------------
	
	private lvGblElm  gBaseX3p		= null;
	private lvGblElm  gParseX3p		= null;
	private lvGblElm  gNodeX3p		= null;
	private lvGblElm  gComX3p		= null;
	
	public lvGblElm
	GBaseX3p()
	{
		return gBaseX3p;
	}
	public lvGblElm
	GParseX3p()
	{
		return gParseX3p;
	}
	public lvGblElm
	GNodeX3p()
	{
		return gNodeX3p;
	}
	public lvGblElm
	GComX3p()
	{
		return gComX3p;
	}
	
}
