//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvComAGblElm.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvComAGblElm extends lvGblElm {
	
	public lvModelAtt.Global  gModelAtt   = null;
	
	public  lvComAGblElm( lvGlobal dt )
	{
		gModelAtt = new lvModelAtt.Global( dt );
	}
	
}
