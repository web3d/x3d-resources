//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvBaseGblElm.java
//

package jp.co.lattice.vKernel.core.b0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvBaseGblElm extends lvGblElm {

	public lvFromKernel.Global	gFromKernel	    = null;
	public lvToKernel.Global	gToKernel		= null;
	
	public	lvBaseGblElm( lvGlobal dt )
	{
		gFromKernel = new lvFromKernel.Global( dt );
		gToKernel	= new lvToKernel.Global( dt );
	}
}
