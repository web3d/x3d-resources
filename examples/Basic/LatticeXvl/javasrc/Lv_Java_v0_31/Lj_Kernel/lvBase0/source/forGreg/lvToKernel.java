//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernel.java
//

package jp.co.lattice.vKernel.core.b0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.core.t0.lvToKernelLow;
import	jp.co.lattice.vKernel.core.c0.lvShellPoly;
import	jp.co.lattice.vKernel.core.c0.lvModelPoly;
import	jp.co.lattice.vKernel.core.t0.lvMakePoly;
import	jp.co.lattice.vKernel.greg.t0g.lvMakeGreg;
import	jp.co.lattice.vKernel.greg.t0g.lv0MakeGreg;
import	jp.co.lattice.vKernel.greg.t0g.lv0TopoGGblElm;
import	jp.co.lattice.vKernel.tex.a0.lvMakeUVspace;
import	jp.co.lattice.vKernel.tex.a0.lv0MakeUVspace;
import	jp.co.lattice.vKernel.tex.a0.lv0AttGblElm;
import	jp.co.lattice.vKernel.greg.c0g.lvModelGreg;
import	jp.co.lattice.vKernel.greg.c0g.lvShellGreg;
import	jp.co.lattice.vKernel.greg.c0g.lv0ComGGblElm;
import	jp.co.lattice.vKernel.tex.c0a.lvModelAtt;
import	jp.co.lattice.vKernel.tex.c0a.lvShellAtt;
import	jp.co.lattice.vKernel.tex.c0a.lvComAGblElm;


/**
 * 上位階層から Java Lattice Kernel に引き渡すデータのクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvToKernel extends lvToKernelLow {
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		private boolean  hasGreg	= false;
		private boolean  hasAtt		= false;
		
		/** オブジェクト				*/
		private lvMakePoly		makePoly	= null;
		private lv0TopoGGblElm	topoGGblElm	= null;
		private lvMakeGreg		makeGreg	= null;
		private lv0AttGblElm	attGblElm	= null;
		private lvMakeUVspace	makeUVspace	= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public	Global( lvGlobal dt )
		{
			makePoly	= new lvMakePoly( dt );
			topoGGblElm = new lv0TopoGGblElm( dt );
			makeGreg	= new lv0MakeGreg( dt );
			attGblElm	= new lv0AttGblElm( dt );
			makeUVspace = new lv0MakeUVspace( dt );
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return	( ( lvBaseGblElm )global.GBase() ).gToKernel;
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

	/** lvModelAtt用のグローバルデータ				*/
	private final lvModelAtt.Global
	ModelAtt()
	{
		return	( ( lvComAGblElm )global.GComA() ).gModelAtt;
	}
	/** lvShellPoly.Attrデータ用クラスオブジェクト		*/
	private final lvShellPoly.Attr
	Attr( int shellNo )
	{
		return	ModelPoly().shell[ shellNo ].attr;
	}
	/** lvError用のグローバルデータ				*/
	private final lvError.Global
	ErrProc()
	{
		return	( ( lvComGblElm )global.GCom() ).gError;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvToKernel( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 現在のシェル数を通知する
	 * @return				現在のシェル数
	 */
	public final int
	GetNumShell()
	{
		if( ModelPoly().shell == null )
			return	0;
		else
			return	ModelPoly().shell.length;
	}

	/**
	 * Java Lattice Kernel にシェルを追加（一番最初は作成）する
	 * @param  num			(( I )) シェル数（ 1以上 ）
	 * @return				lvConst.LV_SUCCESS または lvConst.LV_FAILURE
	 */
	public final boolean
	AppendNumShell( int num )
	{
		try {
			ErrProc().BeginThrowMode();
			
				AppendNumShellMain( num );
				
			ErrProc().EndThrowMode();
			return	lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return	lvConst.LV_FAILURE;
		}
	}
	
	private final void
	AppendNumShellMain( int num ) throws lvThrowable
	{
		Err().Assert( ( num > 0 ), "lvToKernel.AppendNumShellMain(0)" );
		
		int		 oldNum;
		lvShellPoly  oldShellPoly[] = ModelPoly().shell;
		if( oldShellPoly == null )
			oldNum = 0;
		else
			oldNum = oldShellPoly.length;
					
		ModelPoly().shell = new lvShellPoly[ oldNum + num ];
		if( oldShellPoly != null ) {
			for( int i=0; i<oldNum; i++ )
				ModelPoly().shell[ i ] = oldShellPoly[ i ];
		}
		for( int i=0; i<num; i++ )
			ModelPoly().shell[ oldNum + i ] = new lvShellPoly( /*global*/ );
				
		if( Gbl().hasGreg == true )
			AppendNumShellGreg( oldNum, num );
				
		if( Gbl().hasAtt == true )
			AppendNumShellAtt( oldNum, num );
	}
	
	private final void
	AppendNumShellGreg( int oldNum, int newNum )
	{
		lvShellGreg  oldShellGreg[] = ( lvShellGreg[] )ModelGreg().shell;
		ModelGreg().shell = new lvShellGreg[ oldNum + newNum ];
				
		for( int i=0; i<oldNum; i++ )
			ModelGreg().shell[ i ] = oldShellGreg[ i ];
		for( int i=0; i<newNum; i++ )
			ModelGreg().shell[ oldNum + i ] = new lvShellGreg( /*global*/ );
	}
	
	private final void
	AppendNumShellAtt( int oldNum, int newNum )
	{
		lvShellAtt	oldShellAtt[] = ( lvShellAtt[] )ModelAtt().shell;
		ModelAtt().shell = new lvShellAtt[ oldNum + newNum ];
			
		for( int i=0; i<oldNum; i++ )
			ModelAtt().shell[ i ] = oldShellAtt[ i ];
		for( int i=0; i<newNum; i++ )
			ModelAtt().shell[ oldNum + i ] = new lvShellAtt( /*global*/ );
	}
	
	/**
	 * Java Lattice Kernel内の属性を取得する
	 * @param  shellNo		(( I )) int num = GetNumShell() の場合、0 ～ ( num-1 )
	 * @return				属性（内部データをコピーしたもの）
	 */
	public final lvToKernelType.Attr
	GetAttr( int shellNo )
	{
		lvToKernelType.Attr  attr = new lvToKernelType.Attr();
		
		attr.numDiv = ModelPoly().shell[ shellNo ].attr.numDiv;
		attr.type	= ModelPoly().shell[ shellNo ].attr.type;
		
		return attr;
	}
	
	/**
	 * Java Lattice Kernel に引き渡す属性をセットする									<br>
	 * （属性が初期値以外の場合、lvFromKernel内の関数より先に実行する必要有り）
	 * @param  shellNo		(( I )) int num = GetNumShell() の場合、0 ～ ( num-1 )
	 * @param  attr			(( I )) Java Lattice Kernel に引き渡す属性
	 * @return				lvConst.LV_SUCCESS または lvConst.LV_FAILURE
	 */
	public final boolean
	SetAttr( int shellNo, lvToKernelType.Attr attr )
	{
		SetAttrMain( shellNo, attr );
		
		if( ModelPoly().shell[ shellNo ].poly == null )
			return lvConst.LV_SUCCESS;
		
		try {
			ErrProc().BeginThrowMode();
			
				AddModelGreg( shellNo );
				ExecGreg( shellNo );
				
			ErrProc().EndThrowMode();
			return	lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return	lvConst.LV_FAILURE;
		}
	}
	
	private final void
	SetAttrMain( int shellNo, lvToKernelType.Attr attr )
	{
		ModelPoly().shell[ shellNo ].attr.numDiv = attr.numDiv;
		ModelPoly().shell[ shellNo ].attr.type	 = attr.type;
	}
	
	/**
	 * ある shellNo の Java Lattice Kernel に引き渡すデータをセットする					<br>
	 * この関数実行後、オブジェクトを保持し続ける必要はない
	 * @param  shellNo		(( I )) int num = GetNumShell() の場合、0 ～ ( num-1 )
	 * @return				lvConst.LV_SUCCESS または lvConst.LV_FAILURE
	 */
	public final boolean
	SetData( int shellNo )
	{
		try {
			ErrProc().BeginThrowMode();
			
				AddModel( shellNo );
				Exec( shellNo );
				
			ErrProc().EndThrowMode();
			return	lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return	lvConst.LV_FAILURE;
		}
	}
	
	private final void
	AddModel( int shellNo )
	{
		AddModelGreg( shellNo );
		AddModelAtt( shellNo );
	}
	
	private final void
	AddModelGreg( int shellNo )
	{
		int  num = ModelPoly().shell.length;
		
		if( Gbl().hasGreg == false ) {
			if( Attr( shellNo ).type == lvConst.LV_SS_LATTICE ||
				Attr( shellNo ).type == lvConst.LV_SS_GREGORY )
			{
				Gbl().hasGreg = true;
				ModelGreg().shell = new lvShellGreg[ num ];
				for( int i=0; i<num; i++ )
					ModelGreg().shell[ i ] = new lvShellGreg( /*global*/ );
			}
		}
	}
	
	private final void
	AddModelAtt( int shellNo )
	{
		int  num = ModelPoly().shell.length;
		
		if( Gbl().hasAtt == false && uvSpace != null ) {
			Gbl().hasAtt = true;
			ModelAtt().shell = new lvShellAtt[ num ];
			for( int i=0; i<num; i++ )
				ModelAtt().shell[ i ] = new lvShellAtt( /*global*/ );
		}
	}
	
	private final void
	Exec( int shellNo ) throws lvThrowable
	{
		ExecPoly( shellNo );
		ExecAtt( shellNo );
		ExecGreg( shellNo );
	}
	
	private final void
	ExecPoly( int shellNo ) throws lvThrowable
	{
		ModelPoly().shell[ shellNo ].poly = new lvPolygon();
		
		Gbl().makePoly.Exec( shellNo, this );
	}
	
	private final void
	ExecAtt( int shellNo ) throws lvThrowable
	{
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().makeUVspace.Exec( shellNo, uvSpace );
	}
	
	private final void
	ExecGreg( int shellNo ) throws lvThrowable
	{
		if( Gbl().topoGGblElm.IsDmy() == false )
			Gbl().makeGreg.Exec( shellNo );
	}
	
}
