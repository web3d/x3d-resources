//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVout.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/04/09-)
 * 
 */
public class lvMakeUVout extends lvRoot {

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo						*/
		private int                          curShellNo;

		/** 入力データ								*/
		private lvMakeUVspaceType.UVpublic   srcUVpublic		= null;

		/** 入力データ								*/
		private lvMakeUVspaceType.UVspaceST  srcUVspaceST		= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Global( lvGlobal dt )
		{
		}
	}


	/** 当クラス用のグローバルデータ						*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gMakeUVout;
	}
	/** lvMakeUVspaceType.UVpublicデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVpublic
	SrcUVpublic()
	{
		return  Gbl().srcUVpublic;
	}
	/** lvMakeUVspaceType.UVspaceUVデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVspaceST
	SrcUVspaceST()
	{
		return  Gbl().srcUVspaceST;
	}
	/** lvPolygonデータ用クラスオブジェクト					*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** lvUVpublicデータ用クラスオブジェクト				*/
	private final lvUVpublic
	ShellUVpublic()
	{
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvPublic;
	}
	/** lvUVspaceデータ用クラスオブジェクト				*/
	private final lvUVspace
	ShellUVspace()
	{
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvSpace;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lvMakeUVout( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public void
	Exec( int shellNo, lvMakeUVspaceType.UVpublic srcUVpublic, lvMakeUVspaceType.UVspaceST srcUVspaceST ) throws lvThrowable
	{
		if( srcUVpublic == null )
			return;
			
		Gbl().curShellNo   = shellNo;
		Gbl().srcUVpublic  = srcUVpublic;
		Gbl().srcUVspaceST = srcUVspaceST;
		
		VtxProc();
		GsProc();
		UVspaceProc();
		
		Finish();
	}

	private final void
	VtxProc()
	{
		NewVtxUV();
		NewVtxUVinfoSeq();
		
		SetVtxUV();
		SetVtxUVinfoSeq();
	}
	
	private final void
	NewVtxUV()
	{
		int  num = SrcUVpublic().vtxUV.length;
		ShellUVspace().vtxUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().vtxUV[ i ] = new lvRec.SeqPart();
	}
	
	private final void
	NewVtxUVinfoSeq()
	{
		int  num = SrcUVpublic().vtxUVseq.length;
		ShellUVspace().vtxUVinfoSeq = new lvUVspace.VtxInfo[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().vtxUVinfoSeq[ i ] = new lvUVspace.VtxInfo();
	}
	
	private final void
	SetVtxUV()
	{
		int  num = SrcUVpublic().vtxUV.length;
		for( int i=0; i<num; i++ )
			lvRec.SeqPart.Copy( SrcUVpublic().vtxUV[ i ], ShellUVspace().vtxUV[ i ] );
	}
	
	private final void
	SetVtxUVinfoSeq()
	{
		int  num = SrcUVpublic().vtxUVseq.length;
		for( int i=0; i<num; i++ ) {
			ShellUVspace().vtxUVinfoSeq[ i ].uvSpaceNo = SrcUVpublic().vtxUVseq[ i ].uvSpaceNo;
			lvUVdt.Copy( SrcUVspaceST().vtxSTseq[ i ], ShellUVspace().vtxUVinfoSeq[ i ].uv );
		}
	}
	
	private final void
	GsProc() throws lvThrowable
	{
		NewGsInfo();
		NewGsUVspaceSeq();
		
		SetGsInfo();
		SetGsUVspaceSeq();
	}
	
	private final void
	NewGsInfo()
	{
		int  num = SrcUVpublic().gsNumUV.length;
		ShellUVspace().gsUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().gsUV[ i ] = new lvRec.SeqPart();
	}
	
	private final void
	NewGsUVspaceSeq()
	{
		int  num = SrcUVpublic().gsUVseq.length;
		ShellUVspace().gsUVspaceSeq = new lvUVspace.GsInfo[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().gsUVspaceSeq[ i ] = new lvUVspace.GsInfo();
	}
	
	private final void
	SetGsInfo()
	{
		int  num = SrcUVpublic().gsNumUV.length;
		for( int i=0; i<num; i++ )
			lvRec.SeqPart.Copy( SrcUVpublic().gsNumUV[ i ], ShellUVspace().gsUV[ i ] );
	}
	
	private final void
	SetGsUVspaceSeq() throws lvThrowable
	{
		int  num = SrcUVpublic().gsNumUV.length;
		for( int i=0; i<num; i++ ) {
			lvRec.SeqPart  gsUV = ShellUVspace().gsUV[ i ];
			for( int j=0; j<gsUV.num; j++ ) {
				lvUVspace.GsInfo  gsUVspaceSeq = ShellUVspace().gsUVspaceSeq[ gsUV.start + j ];
				
				gsUVspaceSeq.uvSpaceNo = SrcUVpublic().gsUVseq[ gsUV.start + j ];
				SetCenter( gsUVspaceSeq.uvSpaceNo, i, gsUVspaceSeq.center );
			}
		}
	}
	
	private final void
	SetCenter( int uvSpaceNo, int gsNo, lvUVdt center ) throws lvThrowable
	{
		lvRec.SeqPart  face = Polygon().face[ gsNo ];
		
		lvUVdt.SetUV( 0.0, 0.0, center );
		for( int i=0; i<face.num; i++ ) {
			int  vtxNo = Polygon().faceHalfSeq[ face.start + i ].vtxNo;
			
			lvRec.SeqPart  vtxUV = ShellUVspace().vtxUV[ vtxNo ];
			boolean  exist = false;
			int      j;
			for( j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNoVtx = ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uvSpaceNo;
				if( uvSpaceNo == uvSpaceNoVtx ) {
					exist = true;
					break;
				}
			}
			Err().Assert( ( exist == true ), "lvMakeUVout.SetCenter(0)" );
			
			center.u += ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uv.u;
			center.v += ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uv.v;
		}
		
		Err().Assert( ( face.num > 0 ), "lvMakeUVout.SetCenter(1)" );
		center.u /= face.num;
		center.v /= face.num;
	}
	
	private final void
	UVspaceProc()
	{
		UvSpaceMain();
		UvSpaceGsSeqProc();
		UvSpaceVtxSeqProc();
	}

	private final void
	UvSpaceMain()
	{
		NewUvSpace();
		SetUvSpaceGS();
		SetUvSpaceVtx();
	}
	
	private final void
	NewUvSpace()
	{
		int  num = ShellUVpublic().uvSpace.length;
		ShellUVspace().uvSpace = new lvUVspace.UVspace[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().uvSpace[ i ] = new lvUVspace.UVspace();
	}
	
	private final void
	SetUvSpaceGS()
	{
		int  numSpace = ShellUVspace().uvSpace.length;
		for( int i=0; i<numSpace; i++ )
			ShellUVspace().uvSpace[ i ].gs.num = 0;
			
		int  numGs = ShellUVspace().gsUV.length;
		for( int i=0; i<numGs; i++ ) {
			lvRec.SeqPart  gsUV = ShellUVspace().gsUV[ i ];
			for( int j=0; j<gsUV.num; j++ ) {
				int  uvSpaceNo = ShellUVspace().gsUVspaceSeq[ gsUV.start + j ].uvSpaceNo;
				ShellUVspace().uvSpace[ uvSpaceNo ].gs.num++;
			}
		}
		
		int  cnt = 0;
		for( int i=0; i<numSpace; i++ ) {
			ShellUVspace().uvSpace[ i ].gs.start = cnt;
			cnt += ShellUVspace().uvSpace[ i ].gs.num;
		}
	}
	
	private final void
	SetUvSpaceVtx()
	{
		int  numSpace = ShellUVspace().uvSpace.length;
		for( int i=0; i<numSpace; i++ )
			ShellUVspace().uvSpace[ i ].vtx.num = 0;
			
		int  numVtx = ShellUVspace().vtxUV.length;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  vtxUV = ShellUVspace().vtxUV[ i ];
			for( int j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNo = ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uvSpaceNo;
				ShellUVspace().uvSpace[ uvSpaceNo ].vtx.num++;
			}
		}
		
		int  cnt = 0;
		for( int i=0; i<numSpace; i++ ) {
			ShellUVspace().uvSpace[ i ].vtx.start = cnt;
			cnt += ShellUVspace().uvSpace[ i ].vtx.num;
		}
	}
	
	private final void
	UvSpaceGsSeqProc()
	{
		NewUvSpaceGsSeq();
		SetUvSpaceGsSeq();
	}
	
	private final void
	NewUvSpaceGsSeq()
	{
		int  numSpace = ShellUVspace().uvSpace.length;
		
		int  num = 0;
		for( int i=0; i<numSpace; i++ )
			num += ShellUVspace().uvSpace[ i ].gs.num;
		
		ShellUVspace().uvSpaceGsSeq = new int[ num ];
	}
	
	private final void
	SetUvSpaceGsSeq()
	{
		int  numSpace = ShellUVspace().uvSpace.length;
		for( int i=0; i<numSpace; i++ )
			ShellUVspace().uvSpace[ i ].gs.num = 0;
			
		int  numGs = ShellUVspace().gsUV.length;
		for( int i=0; i<numGs; i++ ) {
			lvRec.SeqPart  gsUV = ShellUVspace().gsUV[ i ];
			for( int j=0; j<gsUV.num; j++ ) {
				int  uvSpaceNo = ShellUVspace().gsUVspaceSeq[ gsUV.start + j ].uvSpaceNo;
				
				lvRec.SeqPart  gs = ShellUVspace().uvSpace[ uvSpaceNo ].gs;
				ShellUVspace().uvSpaceGsSeq[ gs.start + gs.num ] = i;
				
				ShellUVspace().uvSpace[ uvSpaceNo ].gs.num++;
			}
		}
	}
		
	private final void
	UvSpaceVtxSeqProc()
	{
		NewUvSpaceVtxSeq();
		SetUvSpaceVtxSeq();
	}
	
	private final void
	NewUvSpaceVtxSeq()
	{
		int  numSpace = ShellUVspace().uvSpace.length;
		
		int  num = 0;
		for( int i=0; i<numSpace; i++ )
			num += ShellUVspace().uvSpace[ i ].vtx.num;
		
		ShellUVspace().uvSpaceVtxSeq = new int[ num ];
	}

	private final void
	SetUvSpaceVtxSeq()
	{
		int  numSpace = ShellUVspace().uvSpace.length;
		for( int i=0; i<numSpace; i++ )
			ShellUVspace().uvSpace[ i ].vtx.num = 0;
			
		int  numVtx = ShellUVspace().vtxUV.length;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  vtxUV = ShellUVspace().vtxUV[ i ];
			for( int j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNo = ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uvSpaceNo;
				
				lvRec.SeqPart  vtx = ShellUVspace().uvSpace[ uvSpaceNo ].vtx;
				ShellUVspace().uvSpaceVtxSeq[ vtx.start + vtx.num ] = i;
				
				ShellUVspace().uvSpace[ uvSpaceNo ].vtx.num++;
			}
		}
	}

	private final void
	Finish()
	{
		Gbl().srcUVpublic  = null;
		Gbl().srcUVspaceST = null;
	}
	
}
