//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0MakeUVspace.java		�i�����Łj
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;
import	jp.co.lattice.vKernel.texEx.a0x.lvExecUVcalc;
import	jp.co.lattice.vKernel.texEx.a0x.lv0ExecUVcalc;
import	jp.co.lattice.vKernel.texEx.a0x.lv0UVcalcGblElm;


/**
 * @author	  created by Eishin Matsui (00/04/07-)
 * 
 */
public class lv0MakeUVspace extends lvMakeUVspace {

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** lv0UVcalcGblElm�I�u�W�F�N�g		*/
		private lv0UVcalcGblElm    uvCalcGblElm		= null;
		
		/** lvCheckUVspace�I�u�W�F�N�g		*/
		private lvCheckUVspace     checkUVspace		= null;

		/** lvMakeUVnonCalc�I�u�W�F�N�g		*/
		private lvMakeUVnonCalc    makeUVnonCalc	= null;

		/** lvExecUVcalc�I�u�W�F�N�g		*/
		private lvExecUVcalc       execUVcalc		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Global( lvGlobal dt )
		{
			uvCalcGblElm  = new lv0UVcalcGblElm( dt );
			checkUVspace  = new lvCheckUVspace( dt );
			makeUVnonCalc = new lvMakeUVnonCalc( dt );
			execUVcalc    = new lv0ExecUVcalc( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^	*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGblElm )global.GAtt() ).gMakeUVspace;
	}
	private final lvModelAtt.Global
	ModelAtt()
	{
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lv0MakeUVspace( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public void
	Exec( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		if( srcUVspace == null )
			return;
			
		if( Gbl().checkUVspace.NeedUVcalc( shellNo, srcUVspace ) == false ) {
			ModelAtt().shell[ shellNo ].uvPublic = new lvUVpublic();
			ModelAtt().shell[ shellNo ].uvSpace  = new lvUVspace();

			ExecUVnonCalc( shellNo, srcUVspace );
		}
		else if( Gbl().uvCalcGblElm.IsDmy() == false ) {
			ModelAtt().shell[ shellNo ].uvPublic = new lvUVpublic();
			ModelAtt().shell[ shellNo ].uvSpace  = new lvUVspace();

			ExecUVcalc( shellNo, srcUVspace );
		}
			
		Finish();
	}
	
	private final void
	ExecUVnonCalc( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		Gbl().makeUVnonCalc.Exec( shellNo, srcUVspace );
	}
	
	private final void
	ExecUVcalc( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		Gbl().execUVcalc.Exec( shellNo, srcUVspace );
	}
	
	private final void
	Finish()
	{
		Gbl().execUVcalc.Finish();
	}
	
}
