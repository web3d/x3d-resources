//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0ComGGblElm.java		（実装版）
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static変数の代用クラス
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lv0ComGGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return false;
	}

	public lvBezLine.Global    gBezLine		= null;
	public lvModelGreg.Global  gModelGreg	= null;
	
	public  lv0ComGGblElm( lvGlobal dt )
	{
		gBezLine   = new lvBezLine.Global( dt );
		gModelGreg = new lvModelGreg.Global( dt );
	}
	
}
