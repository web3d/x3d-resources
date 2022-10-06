//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvComGblElm.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvComGblElm extends lvGblElm {
	
	public lvEps        eps     = null;
	public lvError      err     = null;

	public lvDblCalc.Global        gDblCalc	   = null;
	public lvDebugMode.Global      gDebugMode  = null;
	public lvError.Global          gError	   = null;
	public lvVecCalc.Global        gVecCalc    = null;
	public lvVector.Global         gVector     = null;
	public lvModelPoly.Global      gModelPoly  = null;
	
	public  lvComGblElm( lvGlobal dt )
	{
		eps = new lvEps( dt );
		err = new lvError( dt );

		gDblCalc    = new lvDblCalc.Global( dt );
		gDebugMode  = new lvDebugMode.Global( dt );
		gError      = new lvError.Global( dt );
		gVecCalc    = new lvVecCalc.Global( dt );
		gVector     = new lvVector.Global( dt );
		gModelPoly  = new lvModelPoly.Global( dt );
	}
	
}
