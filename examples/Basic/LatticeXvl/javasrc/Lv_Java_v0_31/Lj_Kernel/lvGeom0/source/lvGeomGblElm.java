//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvGeomGblElm.java
//

package jp.co.lattice.vKernel.core.g0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvGeomGblElm extends lvGblElm {
	
	public lvDivPolyLow.Global     gDivPolyLow		= null;
	
	public  lvGeomGblElm( lvGlobal dt )
	{
		gDivPolyLow    = new lvDivPolyLow.Global( dt );
	}
	
}
