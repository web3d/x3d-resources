//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0PolyFromKernelUV.java		（実装版）
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lv0PolyFromKernelUV extends lvPolyFromKernelUV {

	private static class DstUVspace {
		
		/**
		 * UV空間情報（テクスチャ用）。当シェルがUV空間（テクスチャ）を持たない時は、null となる。	<br>
		 * 多重マッピングの場合は、配列長は 2 以上となる --- 初期値 null
		 */
		public lvFromKernelUV  uvSpace[]	= null;
	}

// -------------------------------------------------------------------

	/**
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo			*/
		private int  curShellNo		= 0;
		/** カレントGS面No				*/
		private int  curGsNo		= 0;
		
		/** 上位レイヤー(base0)から送られるデータ（ UV空間を持たない場合は、null が返る）	*/
		public  lvDivPolyUV.UpDivPolyUV  upDivPolyUV	= null;

		/** 上位レイヤー(base0)に送るデータ（ UV空間を持たない場合は、null）				*/
		private DstUVspace               dstUVspace		= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGblElm )global.GAtt() ).gPolyFromKernelUV;
	}
	/** UpDivPolyUV用のグローバルデータ				*/
	private final lvDivPolyUV.UpDivPolyUV
	UpDivPolyUV()
	{
		return  Gbl().upDivPolyUV;
	}
	/** UpDivUVspace用のグローバルデータ				*/
	private final DstUVspace
	DstUVspace()
	{
		return  Gbl().dstUVspace;
	}
	/** lvUVspaceデータ用クラスオブジェクト		*/
	private final lvUVspace
	ShlUVspace()
	{
		if( ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell == null )
			return null;
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvSpace;
	}
	/** lvUVpublicデータ用クラスオブジェクト		*/
	private final lvUVpublic
	ShlUVpublic()
	{
		if( ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell == null )
			return null;
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvPublic;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lv0PolyFromKernelUV( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public lvFromKernelUV[]
	ExecPoly( int shellNo, int gsNo, lvDivPolyUV.UpDivPolyUV upDivPolyUV ) throws lvThrowable
	{
		DstUVspace  dstUVspace = new DstUVspace();
		
		NewUpDivUVspace( shellNo, gsNo, dstUVspace );
		ExecUVspaceNo( shellNo, gsNo, dstUVspace );
		ExecPolyProc( upDivPolyUV, dstUVspace );
		
		Finish();
		
		return dstUVspace.uvSpace;
	}

	private final void
	NewUpDivUVspace( int shellNo, int gsNo, DstUVspace dstUVspace )
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		Gbl().dstUVspace = dstUVspace;
		
		DstUVspace().uvSpace = null;
		if( ShlUVspace() == null )
			return;

		NewUpDivUVspaceMain();
	}
	
	private final void
	NewUpDivUVspaceMain()
	{
		int  num = ShlUVspace().gsUV[ Gbl().curGsNo ].num;
		if( num == 0 )
			return;
		
		DstUVspace().uvSpace = new lvFromKernelUV[ num ];
		for( int i=0; i<num; i++ )
			DstUVspace().uvSpace[ i ] = new lvFromKernelUV();
	}
	
	private final void
	ExecUVspaceNo( int shellNo, int gsNo, DstUVspace dstUVspace )
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		Gbl().dstUVspace = dstUVspace;
		
		if( DstUVspace().uvSpace == null )
			return;

		ExecUVspaceNoMain();
	}

	private final void
	ExecUVspaceNoMain()
	{
		lvRec.SeqPart  gsUV = ShlUVspace().gsUV[ Gbl().curGsNo ];
		
		for( int i=0; i<gsUV.num; i++ ) {
			int  uvSpaceNo = ShlUVspace().gsUVspaceSeq[ gsUV.start + i ].uvSpaceNo;
			DstUVspace().uvSpace[ i ].uvSpaceNo = ShlUVpublic().uvSpace[ uvSpaceNo ].uvPublicNo;
		}
	}
	
	private final void
	ExecPolyProc( lvDivPolyUV.UpDivPolyUV upDivPolyUV, DstUVspace dstUVspace ) throws lvThrowable
	{
		Gbl().upDivPolyUV = upDivPolyUV;
		Gbl().dstUVspace  = dstUVspace;

		if( DstUVspace().uvSpace == null )
			return;

		ExecPolyMain();
	}

	private final void
	ExecPolyMain() throws lvThrowable
	{
		int  numUVspace = DstUVspace().uvSpace.length;
		Err().Assert( ( numUVspace == UpDivPolyUV().uvSpace.length ), "lvDivUVspace.ExecPoly(0)" );
		
		for( int i=0; i<numUVspace; i++ ) {
			int  numVtx = UpDivPolyUV().uvSpace[ i ].numVertex;
			NewUV( i, numVtx );
			for( int j=0; j<numVtx; j++ ) {
				lvUVdt  srcUV = UpDivPolyUV().uvSpace[ i ].vertex[ j ];
				lvUVdt  dstUV = DstUVspace().uvSpace[ i ].uv[ j ];
				lvUVdt.Copy( srcUV, dstUV );
			}
		}
	}
		
	private final void
	NewUV( int uvSpaceNo, int numVtx )
	{
		DstUVspace().uvSpace[ uvSpaceNo ].uv = new lvUVdt[ numVtx ];
		for( int i=0; i<numVtx; i++ )
			DstUVspace().uvSpace[ uvSpaceNo ].uv[ i ] = new lvUVdt();
	}

	private final void
	Finish()
	{
		Gbl().upDivPolyUV = null;
	}
	
}
