//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0ComGGblElm.java		�i�_�~�[�Łj
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * static�ϐ��̑�p�N���X
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lv0ComGGblElm extends lvGblElm {
	
	public boolean
	IsDmy()
	{
		return true;
	}

	public lvModelGreg.Global  gModelGreg	= null;

	public  lv0ComGGblElm( lvGlobal dt )
	{
		gModelGreg = new lvModelGreg.Global( dt );
	}
	
}
