//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pComGblElm.java
//

package jp.co.lattice.vProcessor.com;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * static•Ï”‚Ì‘ã—pƒNƒ‰ƒX
 * @author	  created by Eishin Matsui (00/03/23-)
 * 
 */
public class x3pComGblElm extends lvGblElm {
	
	public x3pMatrix.Global  gMatrix = null;
	
	public  x3pComGblElm( x3pGlobal dt )
	{
		gMatrix = new x3pMatrix.Global( dt );
	}

}
