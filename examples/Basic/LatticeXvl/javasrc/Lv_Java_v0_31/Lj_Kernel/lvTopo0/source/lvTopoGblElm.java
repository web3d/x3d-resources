//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvTopoGblElm.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvTopoGblElm extends lvGblElm {
	
	public lvDivPoly.Global        gDivPoly			= null;
	public lvMakePoly.Global       gMakePoly	   	= null;
	
	public  lvTopoGblElm( lvGlobal dt )
	{
		gDivPoly       = new lvDivPoly.Global( dt );
		gMakePoly      = new lvMakePoly.Global( dt );
	}
	
}
