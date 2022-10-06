//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0MakeGreg.java		�i�����Łj
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.core.t0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * @author	  created by Eishin Matsui (00/06/07-)
 */
public class lv0MakeGreg extends lvMakeGreg {

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �I�u�W�F�N�g				*/
		private lvRound			round		= null;
		private lvConvGreg		convGreg	= null;
		
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public	Global( lvGlobal dt )
		{
			round		= new lvRound( dt );
			convGreg	= new lvConvGreg( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0TopoGGblElm )global.GTopoG() ).gMakeGreg;
	}
	/** lvModelPoly�p�̃O���[�o���f�[�^				*/
	private final lvModelPoly.Global
	ModelPoly()
	{
		return	( ( lvComGblElm )global.GCom() ).gModelPoly;
	}
	/** lvModelGreg�p�̃O���[�o���f�[�^				*/
	private final lvModelGreg.Global
	ModelGreg()
	{
		return	( ( lv0ComGGblElm )global.GComG() ).gModelGreg;
	}
	/** lvShellPoly.Attr�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvShellPoly.Attr
	Attr( int shellNo )
	{
		return	ModelPoly().shell[ shellNo ].attr;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lv0MakeGreg( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public void
	Exec( int shellNo ) throws lvThrowable
	{
		if( Attr( shellNo ).type == lvConst.LV_SS_LATTICE )
			Gbl().round.Exec( shellNo );
		else if( Attr( shellNo ).type == lvConst.LV_SS_GREGORY )
			Gbl().convGreg.Exec( shellNo );
	}
	
}
