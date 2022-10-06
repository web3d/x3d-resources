//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pBaseGblElm.java
//

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vProcessor.com.*;
import  jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pBaseGblElm extends lvGblElm {
	
	public x3pToProcessor.Global    gToProcessor   = null;
	public x3pFromProcessor.Global  gFromProcessor = null;
	public x3pTrvInstance.Global    gTrvInstance   = null;
	public x3pBinToImage.Global     gBinToImage    = null;
	public x3pTrvFromKernel.Global  gTrvFromKernel = null;
	
	public x3pBaseGblElm( x3pGlobal dt )
	{
		gToProcessor   = new x3pToProcessor.Global( dt );
		gFromProcessor = new x3pFromProcessor.Global( dt );
		gTrvInstance   = new x3pTrvInstance.Global( dt );
		gBinToImage    = new x3pBinToImage.Global( dt );
		gTrvFromKernel = new x3pTrvFromKernel.Global( dt );
	}
	
}
