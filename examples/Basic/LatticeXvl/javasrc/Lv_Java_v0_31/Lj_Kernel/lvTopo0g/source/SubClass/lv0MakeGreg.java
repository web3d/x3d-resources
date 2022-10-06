//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0MakeGreg.java		（実装版）
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
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** オブジェクト				*/
		private lvRound			round		= null;
		private lvConvGreg		convGreg	= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public	Global( lvGlobal dt )
		{
			round		= new lvRound( dt );
			convGreg	= new lvConvGreg( dt );
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0TopoGGblElm )global.GTopoG() ).gMakeGreg;
	}
	/** lvModelPoly用のグローバルデータ				*/
	private final lvModelPoly.Global
	ModelPoly()
	{
		return	( ( lvComGblElm )global.GCom() ).gModelPoly;
	}
	/** lvModelGreg用のグローバルデータ				*/
	private final lvModelGreg.Global
	ModelGreg()
	{
		return	( ( lv0ComGGblElm )global.GComG() ).gModelGreg;
	}
	/** lvShellPoly.Attrデータ用クラスオブジェクト		*/
	private final lvShellPoly.Attr
	Attr( int shellNo )
	{
		return	ModelPoly().shell[ shellNo ].attr;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
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
