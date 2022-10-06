//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// xvGlobal.java
//

package jp.co.lattice.vApplet;

import	java.awt.*;
import  jp.co.lattice.vProcessor.base.*;
import  jp.co.lattice.vProcessor.com.*;


/**
 * グローバル変数用のクラス
 * @author	  created by Eishin Matsui (00/03/23-)
 */
public class xvGlobal {
	
	public xvRend	         rend			= null;			// レンダリングエンジン

	public x3pGlobal         processor		= null;
	public x3pFromProcessor  fromProcess	= null;
	public xvTexture         texImage[]     = null;
	
	public xvGlobal()
	{
	}
	
}
