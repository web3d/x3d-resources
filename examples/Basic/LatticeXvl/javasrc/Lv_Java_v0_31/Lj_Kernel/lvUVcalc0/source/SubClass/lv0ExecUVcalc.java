//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0ExecUVcalc.java		�i�����Łj
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/06/07-)
 * 
 */
public class lv0ExecUVcalc extends lvExecUVcalc {

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** lvMakeUVpublic�I�u�W�F�N�g		*/
		private lvMakeUVpublic     makeUVpublic		= null;

		/** lvMakeUVbound�I�u�W�F�N�g		*/
		private lvMakeUVbound      makeUVbound		= null;

		/** lvMakeUVedge�I�u�W�F�N�g		*/
		private lvMakeUVedgeVtx    makeUVedgeVtx	= null;

		/** lvMakeUVspaceUV�I�u�W�F�N�g		*/
		private lvMakeUVspaceUV    makeUVspaceUV	= null;

		/** lvMakeUVspaceST�I�u�W�F�N�g		*/
		private lvMakeUVspaceST    makeUVspaceST	= null;

		/** lvMakeUVout�I�u�W�F�N�g			*/
		private lvMakeUVout        makeUVout		= null;

		/** lvMakeUVspaceType.UVpublic�f�[�^			*/
		private lvMakeUVspaceType.UVpublic    uvPublic			= null;

		/** lvMakeUVspaceType.Bound�f�[�^				*/
		private lvMakeUVspaceType.Bound       bound				= null;

		/** lvMakeUVspaceType.EdgeVtx�f�[�^				*/
		private lvMakeUVspaceType.EdgeVtx     edgeVtx			= null;

		/** lvMakeUVspaceType.UVspaceUV�f�[�^			*/
		private lvMakeUVspaceType.UVspaceUV   uvSpaceUV			= null;

		/** lvMakeUVspaceType.UVspaceST�f�[�^			*/
		private lvMakeUVspaceType.UVspaceST   uvSpaceST			= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Global( lvGlobal dt )
		{
			makeUVpublic  = new lvMakeUVpublic( dt );
			makeUVbound   = new lvMakeUVbound( dt );
			makeUVedgeVtx = new lvMakeUVedgeVtx( dt );
			makeUVspaceUV = new lvMakeUVspaceUV( dt );
			makeUVspaceST = new lvMakeUVspaceST( dt );
			makeUVout     = new lvMakeUVout( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^	*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gExecUVcalc;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lv0ExecUVcalc( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public void
	Exec( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		Gbl().uvPublic  = Gbl().makeUVpublic.Exec(  shellNo, srcUVspace     );
		Gbl().bound     = Gbl().makeUVbound.Exec(   shellNo, Gbl().uvPublic );
		Gbl().edgeVtx   = Gbl().makeUVedgeVtx.Exec( shellNo, Gbl().uvPublic, Gbl().bound   );
		Gbl().uvSpaceUV = Gbl().makeUVspaceUV.Exec( shellNo, Gbl().uvPublic, Gbl().edgeVtx );
		Gbl().uvSpaceST = Gbl().makeUVspaceST.Exec( shellNo, Gbl().uvPublic, Gbl().edgeVtx, Gbl().uvSpaceUV );
		
		Gbl().makeUVout.Exec( shellNo, Gbl().uvPublic, Gbl().uvSpaceST );
	}
	
	public void
	Finish()
	{
		Gbl().uvPublic  = null;
		Gbl().bound     = null;
		Gbl().edgeVtx   = null;
		Gbl().uvSpaceUV = null;
		Gbl().uvSpaceST = null;
	}
	
}
