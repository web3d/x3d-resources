//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVnonCalc.java
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/05/12-)
 * 
 */
public class lvMakeUVnonCalc extends lvRoot {

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo						*/
		private int           curShellNo;

		/** カレントlvToKernelUVデータ				*/
		private lvToKernelUV  srcUVspace			= null;

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
		return  ( ( lv0AttGblElm )global.GAtt() ).gMakeUVnonCalc;
	}
	/** lvToKernelUVデータ用クラスオブジェクト				*/
	private final lvToKernelUV
	SrcUVspace()
	{
		return  Gbl().srcUVspace;
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
	public lvMakeUVnonCalc( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public final void
	Exec( int shellNo, lvToKernelUV srcUVspace ) throws lvThrowable
	{
		if( srcUVspace == null )
			return;
			
		Gbl().curShellNo = shellNo;
		Gbl().srcUVspace = srcUVspace;
		
		UVpublicProc();
		UVspaceProc();
		
		Finish();
	}
	
	private final void
	UVpublicProc()
	{
		int  numUVspace = SrcUVspace().numUVspace;
		
		ShellUVpublic().uvPublic = new lvRec.SeqPart[ numUVspace ];
		for( int i=0; i<numUVspace; i++ ) {
			ShellUVpublic().uvPublic[ i ] = new lvRec.SeqPart();
			
			ShellUVpublic().uvPublic[ i ].start = i;
			ShellUVpublic().uvPublic[ i ].num   = 1;
		}

		ShellUVpublic().uvSpace = new lvUVpublic.UVspace[ numUVspace ];
		for( int i=0; i<numUVspace; i++ ) {
			ShellUVpublic().uvSpace[ i ] = new lvUVpublic.UVspace();
			
			ShellUVpublic().uvSpace[ i ].uvPublicNo = i;
		}
	}

	private final void
	UVspaceProc() throws lvThrowable
	{
		VtxProc0();
		GsProc0();
		UVspaceProc0();
	}

	private final void
	VtxProc0()
	{
		NewVtxUV();
		NewVtxUVinfoSeq();
		
		SetVtxUV();
		SetVtxUVinfoSeq();
	}
	
	private final void
	NewVtxUV()
	{
		int  num = SrcUVspace().vtxNumUV.length;
		ShellUVspace().vtxUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().vtxUV[ i ] = new lvRec.SeqPart();
	}
	
	private final void
	NewVtxUVinfoSeq()
	{
		int  num = SrcUVspace().vtxUVseq.length;
		ShellUVspace().vtxUVinfoSeq = new lvUVspace.VtxInfo[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().vtxUVinfoSeq[ i ] = new lvUVspace.VtxInfo();
	}
	
	private final void
	SetVtxUV()
	{
		int  cnt = 0;
		int  num = SrcUVspace().vtxNumUV.length;
		for( int i=0; i<num; i++ ) {
			ShellUVspace().vtxUV[ i ].start = cnt;
			ShellUVspace().vtxUV[ i ].num   = SrcUVspace().vtxNumUV[ i ];
			
			cnt += ShellUVspace().vtxUV[ i ].num;
		}
	}
	
	private final void
	SetVtxUVinfoSeq()
	{
		int  num = SrcUVspace().vtxUVseq.length;
		for( int i=0; i<num; i++ ) {
			ShellUVspace().vtxUVinfoSeq[ i ].uvSpaceNo = SrcUVspace().vtxUVseq[ i ].uvSpaceNo;
			lvUVdt.Copy( SrcUVspace().vtxUVseq[ i ].uv, ShellUVspace().vtxUVinfoSeq[ i ].uv );
		}
	}
	
	private final void
	GsProc0() throws lvThrowable
	{
		NewGsInfo();
		NewGsUVspaceSeq();
		
		SetGsInfo();
		SetGsUVspaceSeq();
	}
	
	private final void
	NewGsInfo()
	{
		int  num = SrcUVspace().gsNumUV.length;
		ShellUVspace().gsUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().gsUV[ i ] = new lvRec.SeqPart();
	}
	
	private final void
	NewGsUVspaceSeq()
	{
		int  num = SrcUVspace().gsUVseq.length;
		ShellUVspace().gsUVspaceSeq = new lvUVspace.GsInfo[ num ];
		for( int i=0; i<num; i++ )
			ShellUVspace().gsUVspaceSeq[ i ] = new lvUVspace.GsInfo();
	}
	
	private final void
	SetGsInfo()
	{
		int  cnt = 0;
		int  num = SrcUVspace().gsNumUV.length;
		for( int i=0; i<num; i++ ) {
			ShellUVspace().gsUV[ i ].start = cnt;
			ShellUVspace().gsUV[ i ].num   = SrcUVspace().gsNumUV[ i ];
			
			cnt += ShellUVspace().gsUV[ i ].num;
		}
	}
	
	private final void
	SetGsUVspaceSeq() throws lvThrowable
	{
		int  num = SrcUVspace().gsNumUV.length;
		for( int i=0; i<num; i++ ) {
			lvRec.SeqPart  gsUV = ShellUVspace().gsUV[ i ];
			for( int j=0; j<gsUV.num; j++ ) {
				lvUVspace.GsInfo  gsUVspaceSeq = ShellUVspace().gsUVspaceSeq[ gsUV.start + j ];
				
				gsUVspaceSeq.uvSpaceNo = SrcUVspace().gsUVseq[ gsUV.start + j ].uvSpaceNo;
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
			Err().Assert( ( exist == true ), "lvMakeUVnonCalc.SetCenter(0)" );
			
			center.u += ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uv.u;
			center.v += ShellUVspace().vtxUVinfoSeq[ vtxUV.start + j ].uv.v;
		}
		
		Err().Assert( ( face.num > 0 ), "lvMakeUVnonCalc.SetCenter(1)" );
		center.u /= face.num;
		center.v /= face.num;
	}

	private final void
	UVspaceProc0()
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
		Gbl().srcUVspace = null;
	}

}
