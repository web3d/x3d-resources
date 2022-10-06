//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0GeomGGblElm.java		�i�����Łj
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static�ϐ��̑�p�N���X
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lv0GeomGGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return false;
	}

	public lvDivFaceLow.Global     gDivFaceLow		= null;
	public lvGregory.Global        gGregory			= null;
	public lvGregoryStd.Global     gGregoryStd		= null;
	public lvMakeInnerEdge.Global  gMakeInnerEdge	= null;
	public lvRoundLow.Global       gRoundLow	    = null;
	public lvTessellate.Global     gTessellate		= null;
	public lvTessellateLow.Global  gTessellateLow	= null;
	public lvMakeCbd.Global        gMakeCbd	    	= null;
	
	public  lv0GeomGGblElm( lvGlobal dt )
	{
		gDivFaceLow    = new lvDivFaceLow.Global( dt );
		gGregory	   = new lvGregory.Global( dt );
		gGregoryStd    = new lvGregoryStd.Global( dt );
		gMakeInnerEdge = new lvMakeInnerEdge.Global( dt );
		gRoundLow      = new lvRoundLow.Global( dt );
		gTessellate    = new lvTessellate.Global( dt );
		gTessellateLow = new lvTessellateLow.Global( dt );
		gMakeCbd       = new lvMakeCbd.Global( dt );
	}
	
}
