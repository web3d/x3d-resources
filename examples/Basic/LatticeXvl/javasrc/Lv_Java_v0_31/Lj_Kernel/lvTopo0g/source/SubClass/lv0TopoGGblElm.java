//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0TopoGGblElm.java		�i�����Łj
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static�ϐ��̑�p�N���X
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lv0TopoGGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return false;
	}

	public lv0DivFace.Global        gDivFace		= null;
	public lvRound.Global           gRound	    	= null;
	public lvConvGreg.Global        gConvGreg    	= null;
	public lv0MakeGreg.Global       gMakeGreg    	= null;
	
	public  lv0TopoGGblElm( lvGlobal dt )
	{
		gDivFace       = new lv0DivFace.Global( dt );
		gRound         = new lvRound.Global( dt );
		gConvGreg      = new lvConvGreg.Global( dt );
		gMakeGreg      = new lv0MakeGreg.Global( dt );
	}
	
}
