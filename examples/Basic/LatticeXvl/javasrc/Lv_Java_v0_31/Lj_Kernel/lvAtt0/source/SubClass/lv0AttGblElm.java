//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0AttGblElm.java		（実装版）
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static変数の代用クラス
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lv0AttGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return false;
	}

	public lv0PolyFromKernelUV.Global  gPolyFromKernelUV	= null;
	public lv0DivPolyUV.Global         gDivPolyUV			= null;
	public lv0MakeUVspace.Global       gMakeUVspace			= null;
	public lvCheckUVspace.Global       gCheckUVspace		= null;
	public lvMakeUVnonCalc.Global      gMakeUVnonCalc		= null;

	public  lv0AttGblElm( lvGlobal dt )
	{
		gPolyFromKernelUV = new lv0PolyFromKernelUV.Global( dt );
		gDivPolyUV        = new lv0DivPolyUV.Global( dt );
		gMakeUVspace      = new lv0MakeUVspace.Global( dt );
		gCheckUVspace     = new lvCheckUVspace.Global( dt );
		gMakeUVnonCalc    = new lvMakeUVnonCalc.Global( dt );
	}
	
}
