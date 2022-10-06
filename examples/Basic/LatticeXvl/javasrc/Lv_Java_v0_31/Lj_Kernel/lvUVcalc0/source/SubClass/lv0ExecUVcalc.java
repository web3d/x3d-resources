//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0ExecUVcalc.java		（実装版）
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
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** lvMakeUVpublicオブジェクト		*/
		private lvMakeUVpublic     makeUVpublic		= null;

		/** lvMakeUVboundオブジェクト		*/
		private lvMakeUVbound      makeUVbound		= null;

		/** lvMakeUVedgeオブジェクト		*/
		private lvMakeUVedgeVtx    makeUVedgeVtx	= null;

		/** lvMakeUVspaceUVオブジェクト		*/
		private lvMakeUVspaceUV    makeUVspaceUV	= null;

		/** lvMakeUVspaceSTオブジェクト		*/
		private lvMakeUVspaceST    makeUVspaceST	= null;

		/** lvMakeUVoutオブジェクト			*/
		private lvMakeUVout        makeUVout		= null;

		/** lvMakeUVspaceType.UVpublicデータ			*/
		private lvMakeUVspaceType.UVpublic    uvPublic			= null;

		/** lvMakeUVspaceType.Boundデータ				*/
		private lvMakeUVspaceType.Bound       bound				= null;

		/** lvMakeUVspaceType.EdgeVtxデータ				*/
		private lvMakeUVspaceType.EdgeVtx     edgeVtx			= null;

		/** lvMakeUVspaceType.UVspaceUVデータ			*/
		private lvMakeUVspaceType.UVspaceUV   uvSpaceUV			= null;

		/** lvMakeUVspaceType.UVspaceSTデータ			*/
		private lvMakeUVspaceType.UVspaceST   uvSpaceST			= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
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

	/** 当クラス用のグローバルデータ	*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gExecUVcalc;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
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
