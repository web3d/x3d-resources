//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0AttGGblElm.java		（実装版）
//

package jp.co.lattice.vKernel.tex.a0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static変数の代用クラス
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lv0AttGGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return false;
	}

	public lv0FaceFromKernelUV.Global  gFaceFromKernelUV	= null;
	public lv0DivFaceUV.Global         gDivFaceUV			= null;
	public lv0TessellateUV.Global      gTessellateUV		= null;

	public  lv0AttGGblElm( lvGlobal dt )
	{
		gFaceFromKernelUV = new lv0FaceFromKernelUV.Global( dt );
		gDivFaceUV        = new lv0DivFaceUV.Global( dt );
		gTessellateUV     = new lv0TessellateUV.Global( dt );
	}
	
}
