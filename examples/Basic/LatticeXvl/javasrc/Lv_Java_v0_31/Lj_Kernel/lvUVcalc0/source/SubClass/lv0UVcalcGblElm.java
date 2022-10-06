//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0UVcalcGblElm.java		（実装版）
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static変数の代用クラス
 * @author	  created by Eishin Matsui (00/05/13-)
 * 
 */
public class lv0UVcalcGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return false;
	}

	public lv0ExecUVcalc.Global       gExecUVcalc			= null;
	public lvMakeUVpublic.Global      gMakeUVpublic			= null;
	public lvMakeUVpubCorrect.Global  gMakeUVpubCorrect		= null;
	public lvMakeUVbound.Global       gMakeUVbound			= null;
	public lvMakeUVedgeVtx.Global     gMakeUVedgeVtx		= null;
	public lvMakeUVspaceUV.Global     gMakeUVspaceUV		= null;
	public lvMakeUVspaceST.Global     gMakeUVspaceST		= null;
	public lvMakeUVout.Global         gMakeUVout			= null;
	public lvGauss.Global             gGauss    			= null;
	
	public  lv0UVcalcGblElm( lvGlobal dt )
	{
		gExecUVcalc       = new lv0ExecUVcalc.Global( dt );
		gMakeUVpublic     = new lvMakeUVpublic.Global( dt );
		gMakeUVpubCorrect = new lvMakeUVpubCorrect.Global( dt );
		gMakeUVbound      = new lvMakeUVbound.Global( dt );
		gMakeUVedgeVtx    = new lvMakeUVedgeVtx.Global( dt );
		gMakeUVspaceUV    = new lvMakeUVspaceUV.Global( dt );
		gMakeUVspaceST    = new lvMakeUVspaceST.Global( dt );
		gMakeUVout  	  = new lvMakeUVout.Global( dt );
		gGauss            = new lvGauss.Global( dt );
	}
	
}
