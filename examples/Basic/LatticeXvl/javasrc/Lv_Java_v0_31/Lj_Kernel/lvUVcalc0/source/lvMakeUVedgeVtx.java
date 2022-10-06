//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVedgeVtx.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/04/09-)
 * 
 */
public class lvMakeUVedgeVtx extends lvRoot {

	private static final int  EDGETYPE_HASNOT_UVSPACE		= 0;
	private static final int  EDGETYPE_FIX_UVSPACE			= 1;
	private static final int  EDGETYPE_FREE_UVSPACE			= 2;

// -------------------------------------------------------------------

	private static class TmpEdge {
		
		/** EDGETYPE_HASNOT_UVSPACE, EDGETYPE_FIX_UVSPACE, EDGETYPE_FREE_UVSPACE のいずれか		*/
		private int  type;
		
		private int  index;
	}
	
	private static class TmpVtx {
		
		private boolean  freeFlag;
		
		private int      index;
	}

// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo						*/
		private int                          curShellNo;

		/** 入力データ								*/
		private lvMakeUVspaceType.UVpublic   srcUVpublic		= null;

		/** 入力データ								*/
		private lvMakeUVspaceType.Bound      srcBndInfo			= null;

		/** 出力データ								*/
		private lvMakeUVspaceType.EdgeVtx    dstEdgeVtx			= null;
		
		private TmpEdge                      tmpEdge[]			= null;
		
		private TmpVtx                       tmpVtx[]			= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Global( lvGlobal dt )
		{
			GlobalTmp( dt );
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvSetSpringMain[]	= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvSetSpringMain = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetSpringMain[ i ] = new lvVector( dt );
		}
	}


	/** 当クラス用のグローバルデータ						*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gMakeUVedgeVtx;
	}
	/** lvMakeUVspaceType.UVpublicデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVpublic
	SrcUVpublic()
	{
		return  Gbl().srcUVpublic;
	}
	/** lvMakeUVspaceType.Boundデータ用クラスオブジェクト		*/
	private final lvMakeUVspaceType.Bound
	SrcBndInfo()
	{
		return  Gbl().srcBndInfo;
	}
	/** lvMakeUVspaceType.UVspaceUVデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.EdgeVtx
	DstEdgeVtx()
	{
		return  Gbl().dstEdgeVtx;
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

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lvMakeUVedgeVtx( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public lvMakeUVspaceType.EdgeVtx
	Exec( int shellNo, lvMakeUVspaceType.UVpublic srcUVpublic, lvMakeUVspaceType.Bound srcBndInfo ) throws lvThrowable
	{
		if( srcUVpublic == null )
			return null;
			
		Gbl().curShellNo   = shellNo;
		Gbl().srcUVpublic  = srcUVpublic;
		Gbl().srcBndInfo   = srcBndInfo;
		Gbl().dstEdgeVtx   = new lvMakeUVspaceType.EdgeVtx();
		
		NewEdgeVtx();
		NewEdgeVtxSet();
		NewTmpAry();
		
		GetEdgeVtxNo();
		SetSpring();
		
		Finish();
		
		return Gbl().dstEdgeVtx;
	}
	
	private final void
	NewEdgeVtx()
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		DstEdgeVtx().uvSpace = new lvMakeUVspaceType.EdgeVtxSet[ numUVspace ];
		for( int i=0; i<numUVspace; i++ )
			DstEdgeVtx().uvSpace[ i ] = new lvMakeUVspaceType.EdgeVtxSet();
	}
	
	private final void
	NewEdgeVtxSet()
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ ) {
			NewFixEdge( i );
			NewFreeEdge( i );
			NewFixVtx( i );
			NewFreeVtx( i );
		}
	}
	
	private final void
	NewFixEdge( int uvSpaceNo )
	{
		lvRec.SeqPart  uvNumBnd = SrcBndInfo().base[ uvSpaceNo ].uvNumBnd;
		int            fixBndNo = SrcBndInfo().base[ uvSpaceNo ].fixBndNo;
		
		int  numFixEdge = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half.length;
		
		DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge = new lvMakeUVspaceType.EdgeOne[ numFixEdge ];
		for( int i=0; i<numFixEdge; i++ )
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge[ i ] = new lvMakeUVspaceType.EdgeOne();
	}
	
	private final void
	NewFreeEdge( int uvSpaceNo )
	{
		int  numAllEdge  = NumAllEdge( uvSpaceNo );
		int  numFreeEdge = numAllEdge - DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge.length;
		
		DstEdgeVtx().uvSpace[ uvSpaceNo ].freeEdge = new lvMakeUVspaceType.EdgeOne[ numFreeEdge ];
		for( int i=0; i<numFreeEdge; i++ )
			DstEdgeVtx().uvSpace[ uvSpaceNo ].freeEdge[ i ] = new lvMakeUVspaceType.EdgeOne();
	}
	
	private final int
	NumAllEdge( int uvSpaceNo )
	{
		int  numEdge = Polygon().edge.length;
		
		int  cnt = 0;
		for( int i=0; i<numEdge; i++ ) {
			boolean  exist = false;
			for( int j=0; j<2; j++ ) {
				lvPolygon.InfoFaceHalf  faceHalf = Polygon().edge[ i ].face[ j ];
				if( faceHalf.faceNo >= Polygon().ngStartNo )
					continue;
					
				lvRec.SeqPart  gsNumUV = SrcUVpublic().gsNumUV[ faceHalf.faceNo ];
				for( int k=0; k<gsNumUV.num; k++ ) {
					int  uvSpaceNoGs = SrcUVpublic().gsUVseq[ gsNumUV.start + k ];
					if( uvSpaceNo == uvSpaceNoGs ) {
						exist = true;
						break;
					}
				}
				if( exist == true )
					break;
			}
			if( exist == true )
				cnt++;
		}
		
		return cnt;
	}
	
	private final void
	NewFixVtx( int uvSpaceNo )
	{
		lvRec.SeqPart  uvNumBnd = SrcBndInfo().base[ uvSpaceNo ].uvNumBnd;
		int            fixBndNo = SrcBndInfo().base[ uvSpaceNo ].fixBndNo;
		
		int  numFixVtx = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half.length;
		
		DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx = new lvMakeUVspaceType.VtxOne[ numFixVtx ];
		for( int i=0; i<numFixVtx; i++ )
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ] = new lvMakeUVspaceType.VtxOne();
	}
	
	private final void
	NewFreeVtx( int uvSpaceNo )
	{
		int  numAllVtx  = NumAllVtx( uvSpaceNo );
		int  numFreeVtx = numAllVtx - DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		
		DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx = new lvMakeUVspaceType.VtxOne[ numFreeVtx ];
		for( int i=0; i<numFreeVtx; i++ )
			DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ] = new lvMakeUVspaceType.VtxOne();
	}
	
	private final int
	NumAllVtx( int uvSpaceNo )
	{
		int  numVtx = Polygon().vertex.length;
		
		int  cnt = 0;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ i ];
			boolean  exist = false;
			for( int j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNoVtx = SrcUVpublic().vtxUVseq[ vtxUV.start + j ].uvSpaceNo;
				if( uvSpaceNo == uvSpaceNoVtx ) {
					exist = true;
					break;
				}
			}
			if( exist == true )
				cnt++;
		}
		
		return cnt;
	}
	
	private final void
	NewTmpAry()
	{
		NewTmpEdge();
		NewTmpVtx();
	}
	
	private final void
	NewTmpEdge()
	{
		int  numEdge = Polygon().edge.length;
		Gbl().tmpEdge = new TmpEdge[ numEdge ];
		for( int i=0; i<numEdge; i++ )
			Gbl().tmpEdge[ i ] = new TmpEdge();
	}
	
	private final void
	NewTmpVtx()
	{
		int  numVtx = Polygon().vertex.length;
		Gbl().tmpVtx = new TmpVtx[ numVtx ];
		for( int i=0; i<numVtx; i++ )
			Gbl().tmpVtx[ i ] = new TmpVtx();
	}
	
	private final void
	GetEdgeVtxNo() throws lvThrowable
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ ) {
			GetEdgeNoFix( i );
			SetEdgeType( i );
		
			GetEdgeNoFree( i );
			GetVtxNoFix( i );
			GetVtxNoFree( i );
			GetRadialNoFix( i );
			GetRadialNoFree( i );
		}
	}
	
	private final void
	SetEdgeType( int uvSpaceNo )
	{
		int  numEdge = Polygon().edge.length;
		for( int i=0; i<numEdge; i++ )
			Gbl().tmpEdge[ i ].type = EDGETYPE_HASNOT_UVSPACE;
			
		SetEdgeType0( uvSpaceNo );
		SetEdgeType1( uvSpaceNo );
	}
	
	private final void
	SetEdgeType0( int uvSpaceNo )
	{
		int  numEdge = Polygon().edge.length;
		
		for( int i=0; i<numEdge; i++ ) {
			boolean  exist = false;
			for( int j=0; j<2; j++ ) {
				lvPolygon.InfoFaceHalf  faceHalf = Polygon().edge[ i ].face[ j ];
				if( faceHalf.faceNo >= Polygon().ngStartNo )
					continue;
					
				lvRec.SeqPart  gsNumUV = SrcUVpublic().gsNumUV[ faceHalf.faceNo ];
				for( int k=0; k<gsNumUV.num; k++ ) {
					int  uvSpaceNoGs = SrcUVpublic().gsUVseq[ gsNumUV.start + k ];
					if( uvSpaceNo == uvSpaceNoGs ) {
						exist = true;
						break;
					}
				}
				if( exist == true )
					break;
			}
			if( exist == true )
				Gbl().tmpEdge[ i ].type = EDGETYPE_FREE_UVSPACE;
		}
	}
	
	private final void
	SetEdgeType1( int uvSpaceNo )
	{
		lvRec.SeqPart  uvNumBnd = SrcBndInfo().base[ uvSpaceNo ].uvNumBnd;
		int            fixBndNo = SrcBndInfo().base[ uvSpaceNo ].fixBndNo;
		
		int  numFixEdge = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half.length;
		
		for( int i=0; i<numFixEdge; i++ ) {
			int  edgeNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge[ i ].edgeNo;
			Gbl().tmpEdge[ edgeNo ].type = EDGETYPE_FIX_UVSPACE;
		}
	}

	private final void
	GetEdgeNoFix( int uvSpaceNo )
	{
		lvRec.SeqPart  uvNumBnd = SrcBndInfo().base[ uvSpaceNo ].uvNumBnd;
		int            fixBndNo = SrcBndInfo().base[ uvSpaceNo ].fixBndNo;
		
		int  numFixEdge = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half.length;
		
		for( int i=0; i<numFixEdge; i++ ) {
			int  gsNo   = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half[ i ].gsNo;
			int  halfNo = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half[ i ].halfNo;
			
			lvRec.SeqPart  face    = Polygon().face[ gsNo ];
			int            edgeNo  = Polygon().faceHalfSeq[ face.start + halfNo ].edgeNo;
			int            edgeIdx = Polygon().faceHalfSeq[ face.start + halfNo ].edgeIdx;

			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge[ i ].edgeNo  = edgeNo;
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge[ i ].edgeIdx = edgeIdx;
		}
	}
	
	private final void
	GetEdgeNoFree( int uvSpaceNo )
	{
		int  numEdge = Polygon().edge.length;
		
		int  cnt = 0;
		for( int i=0; i<numEdge; i++ ) {
			if( Gbl().tmpEdge[ i ].type == EDGETYPE_FREE_UVSPACE ) {
				DstEdgeVtx().uvSpace[ uvSpaceNo ].freeEdge[ cnt ].edgeNo = i;
				cnt++;
			}
		}
	}
	
	private final void
	GetVtxNoFix( int uvSpaceNo ) throws lvThrowable
	{
		int  uvSpaceOfs = -1;
		
		lvRec.SeqPart  uvNumBnd = SrcBndInfo().base[ uvSpaceNo ].uvNumBnd;
		int            fixBndNo = SrcBndInfo().base[ uvSpaceNo ].fixBndNo;
		
		int  numFixEdge = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half.length;
		
		for( int i=0; i<numFixEdge; i++ ) {
			int  gsNo   = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half[ i ].gsNo;
			int  halfNo = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half[ i ].halfNo;
			
			lvRec.SeqPart  face   = Polygon().face[ gsNo ];
			int            vtxNo  = Polygon().faceHalfSeq[ face.start + halfNo ].vtxNo;

			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].vtxNo = vtxNo;
			
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ vtxNo ];
			boolean  exist = false;
			for( int j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNoVtx = SrcUVpublic().vtxUVseq[ vtxUV.start + j ].uvSpaceNo;
				if( uvSpaceNo == uvSpaceNoVtx ) {
					exist      = true;
					uvSpaceOfs = j;
					break;
				}
			}
			Err().Assert( ( exist == true ), "lvMakeUVedgeVtx.GetVtxNoFix(0)" );
			
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].uvSpaceOfs = uvSpaceOfs;
		}
	}
	
	private final void
	GetVtxNoFree( int uvSpaceNo ) throws lvThrowable
	{
		int  uvSpaceOfs = -1;
		
		SetFreeVtxFlag( uvSpaceNo );
		
		int  numVtx = Polygon().vertex.length;
		
		int  cnt = 0;
		for( int i=0; i<numVtx; i++ ) {
			if( Gbl().tmpVtx[ i ].freeFlag == true ) {
				DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ cnt ].vtxNo = i;
			
				lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ i ];
				boolean  exist = false;
				for( int j=0; j<vtxUV.num; j++ ) {
					int  uvSpaceNoVtx = SrcUVpublic().vtxUVseq[ vtxUV.start + j ].uvSpaceNo;
					if( uvSpaceNo == uvSpaceNoVtx ) {
						exist      = true;
						uvSpaceOfs = j;
						break;
					}
				}
				Err().Assert( ( exist == true ), "lvMakeUVedgeVtx.GetVtxNoFree(0)" );
			
				DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ cnt ].uvSpaceOfs = uvSpaceOfs;
				
				cnt++;
			}
		}
	}
	
	private final void
	SetFreeVtxFlag( int uvSpaceNo )
	{
		int  numVtx = Polygon().vertex.length;
		for( int i=0; i<numVtx; i++ )
			Gbl().tmpVtx[ i ].freeFlag = false;
			
		SetFreeVtxFlag0( uvSpaceNo );
		SetFreeVtxFlag1( uvSpaceNo );
	}
	
	private final void
	SetFreeVtxFlag0( int uvSpaceNo )
	{
		int  numVtx = Polygon().vertex.length;
		
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ i ];
			boolean  exist = false;
			for( int j=0; j<vtxUV.num; j++ ) {
				int  uvSpaceNoVtx = SrcUVpublic().vtxUVseq[ vtxUV.start + j ].uvSpaceNo;
				if( uvSpaceNo == uvSpaceNoVtx ) {
					exist = true;
					break;
				}
			}
			if( exist == true )
				Gbl().tmpVtx[ i ].freeFlag = true;
		}
	}
	
	private final void
	SetFreeVtxFlag1( int uvSpaceNo )
	{
		lvRec.SeqPart  uvNumBnd = SrcBndInfo().base[ uvSpaceNo ].uvNumBnd;
		int            fixBndNo = SrcBndInfo().base[ uvSpaceNo ].fixBndNo;
		
		int  numFixEdge = SrcBndInfo().uvBndSeq[ uvNumBnd.start + fixBndNo ].half.length;
		
		for( int i=0; i<numFixEdge; i++ ) {
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].vtxNo;
			Gbl().tmpVtx[ vtxNo ].freeFlag = false;
		}
	}

	private final void
	GetRadialNoFix( int uvSpaceNo )
	{
		SetNumRadialNoFix( uvSpaceNo );
		NewRadialNoFix( uvSpaceNo );
		GetRadialNoEdgeIndex( uvSpaceNo );
		GetRadialNoVtxIndex( uvSpaceNo );
		
		GetRadialNoFix0( uvSpaceNo );
	}
	
	private final void
	SetNumRadialNoFix( int uvSpaceNo )
	{
		int  numFixVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		for( int i=0; i<numFixVtx; i++ ) {
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial.num = 0;
			
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].vtxNo;
			lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
			for( int j=0; j<vtxFace.num; j++ ) {
				lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
				lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
				int                     edgeNo     = Polygon().faceHalfSeq[ face.start + vtxFaceSeq.halfNo ].edgeNo;
				
				switch( Gbl().tmpEdge[ edgeNo ].type ) {
				case EDGETYPE_FIX_UVSPACE:
				case EDGETYPE_FREE_UVSPACE:
					DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial.num++;
					break;
				default:	// case EDGETYPE_HASNOT_UVSPACE:
				}
			}
		}
		
		int  cnt = 0;
		for( int i=0; i<numFixVtx; i++ ) {
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial.start = cnt;
			cnt += DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial.num;
		}
	}
	
	private final void
	NewRadialNoFix( int uvSpaceNo )
	{
		int  numFixVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;

		int  cnt = 0;
		for( int i=0; i<numFixVtx; i++ )
			cnt += DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial.num;

		DstEdgeVtx().uvSpace[ uvSpaceNo ].fixRadial = new lvMakeUVspaceType.RadialOne[ cnt ];
		for( int i=0; i<cnt; i++ )
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixRadial[ i ] = new lvMakeUVspaceType.RadialOne();
	}
	
	private final void
	GetRadialNoEdgeIndex( int uvSpaceNo )
	{
		int  numFixEdge = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge.length;
		for( int i=0; i<numFixEdge; i++ ) {
			int  edgeNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixEdge[ i ].edgeNo;
			Gbl().tmpEdge[ edgeNo ].index = i;
		}
		
		int  numFreeEdge = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeEdge.length;
		for( int i=0; i<numFreeEdge; i++ ) {
			int  edgeNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeEdge[ i ].edgeNo;
			Gbl().tmpEdge[ edgeNo ].index = i;
		}
	}
	
	private final void
	GetRadialNoVtxIndex( int uvSpaceNo )
	{
		int  numFixVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		for( int i=0; i<numFixVtx; i++ ) {
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].vtxNo;
			Gbl().tmpVtx[ vtxNo ].index = i;
		}
		
		int  numFreeVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;
		for( int i=0; i<numFreeVtx; i++ ) {
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].vtxNo;
			Gbl().tmpVtx[ vtxNo ].index = i;
		}
	}
	
	private final void
	GetRadialNoFix0( int uvSpaceNo )
	{
		int  numFixVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		for( int i=0; i<numFixVtx; i++ ) {
			DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial.num = 0;
			
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].vtxNo;
			lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
			for( int j=0; j<vtxFace.num; j++ ) {
				lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
				lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
				int  edgeNo    = Polygon().faceHalfSeq[ face.start + vtxFaceSeq.halfNo ].edgeNo;
				int  mateVtxNo = Polygon().faceHalfSeq[ face.start + ( ( vtxFaceSeq.halfNo + 1 ) % face.num ) ].vtxNo;
				
				lvRec.SeqPart  radial = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ].radial;
				lvMakeUVspaceType.RadialOne  fixRadial		= null;
				if( Gbl().tmpEdge[ edgeNo ].type == EDGETYPE_FIX_UVSPACE  ||
					Gbl().tmpEdge[ edgeNo ].type == EDGETYPE_FREE_UVSPACE )
				{
					fixRadial = DstEdgeVtx().uvSpace[ uvSpaceNo ].fixRadial[ radial.start + radial.num ];
				}
				
				switch( Gbl().tmpEdge[ edgeNo ].type ) {
				case EDGETYPE_FIX_UVSPACE:
					fixRadial.isFixEdge = true;
					break;
				case EDGETYPE_FREE_UVSPACE:
					fixRadial.isFixEdge = false;
					break;
				default:	// case EDGETYPE_HASNOT_UVSPACE:
				}
				
				if( Gbl().tmpEdge[ edgeNo ].type == EDGETYPE_FIX_UVSPACE  ||
					Gbl().tmpEdge[ edgeNo ].type == EDGETYPE_FREE_UVSPACE )
				{
					fixRadial.radialNo     = j;
					fixRadial.edgeIndex    = Gbl().tmpEdge[ edgeNo ].index;
					fixRadial.mateVtxIndex = Gbl().tmpVtx[ mateVtxNo ].index;
					radial.num++;
					
					if( Gbl().tmpVtx[ mateVtxNo ].freeFlag == false )
						fixRadial.isFixVtx = true;
					else
						fixRadial.isFixVtx = false;
				}
			}
		}
	}
	
	private final void
	GetRadialNoFree( int uvSpaceNo ) throws lvThrowable
	{
		SetNumRadialNoFree( uvSpaceNo );
		NewRadialNoFree( uvSpaceNo );
		GetRadialNoEdgeIndex( uvSpaceNo );
		GetRadialNoVtxIndex( uvSpaceNo );
		
		GetRadialNoFree0( uvSpaceNo );
	}
			
	private final void
	SetNumRadialNoFree( int uvSpaceNo ) throws lvThrowable
	{
		int  numFreeVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;
		for( int i=0; i<numFreeVtx; i++ ) {
			DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial.num = 0;
			
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].vtxNo;
			lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
			for( int j=0; j<vtxFace.num; j++ ) {
				lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
				lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
				int                     edgeNo     = Polygon().faceHalfSeq[ face.start + vtxFaceSeq.halfNo ].edgeNo;
				
				switch( Gbl().tmpEdge[ edgeNo ].type ) {
				case EDGETYPE_FIX_UVSPACE:
					Err().Assert( false, "lvMakeUVedgeVtx.NumRadialNoFree(0)" );
					break;
				case EDGETYPE_FREE_UVSPACE:
					DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial.num++;
					break;
				default:	// case EDGETYPE_HASNOT_UVSPACE:
				}
			}
		}
		
		int  cnt = 0;
		for( int i=0; i<numFreeVtx; i++ ) {
			DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial.start = cnt;
			cnt += DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial.num;
		}
	}
	
	private final void
	NewRadialNoFree( int uvSpaceNo )
	{
		int  numFreeVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;

		int  cnt = 0;
		for( int i=0; i<numFreeVtx; i++ )
			cnt += DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial.num;

		DstEdgeVtx().uvSpace[ uvSpaceNo ].freeRadial = new lvMakeUVspaceType.RadialOne[ cnt ];
		for( int i=0; i<cnt; i++ )
			DstEdgeVtx().uvSpace[ uvSpaceNo ].freeRadial[ i ] = new lvMakeUVspaceType.RadialOne();
	}
	
	private final void
	GetRadialNoFree0( int uvSpaceNo ) throws lvThrowable
	{
		int  numFreeVtx = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;
		for( int i=0; i<numFreeVtx; i++ ) {
			DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial.num = 0;
			
			int  vtxNo = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].vtxNo;
			lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
			for( int j=0; j<vtxFace.num; j++ ) {
				lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
				lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
				int  edgeNo    = Polygon().faceHalfSeq[ face.start + vtxFaceSeq.halfNo ].edgeNo;
				int  mateVtxNo = Polygon().faceHalfSeq[ face.start + ( ( vtxFaceSeq.halfNo + 1 ) % face.num ) ].vtxNo;
				
				lvRec.SeqPart  radial = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ].radial;
				lvMakeUVspaceType.RadialOne  freeRadial = DstEdgeVtx().uvSpace[ uvSpaceNo ].freeRadial[ radial.start + radial.num ];
				switch( Gbl().tmpEdge[ edgeNo ].type ) {
				case EDGETYPE_FIX_UVSPACE:
					Err().Assert( false, "lvMakeUVedgeVtx.GetRadialNoFree0(0)" );
					break;
				case EDGETYPE_FREE_UVSPACE:
					freeRadial.isFixEdge = false;
					freeRadial.radialNo  = j;
					freeRadial.edgeIndex    = Gbl().tmpEdge[ edgeNo ].index;
					freeRadial.mateVtxIndex = Gbl().tmpVtx[ mateVtxNo ].index;
					radial.num++;
					break;
				default:	// case EDGETYPE_HASNOT_UVSPACE:
				}
				
				if( Gbl().tmpVtx[ mateVtxNo ].freeFlag == false )
					freeRadial.isFixVtx = true;
				else
					freeRadial.isFixVtx = false;
			}
		}
	}
	
	private final void
	SetSpring() throws lvThrowable
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ ) {
			SetSpringMain( DstEdgeVtx().uvSpace[ i ].fixEdge  );
			SetSpringMain( DstEdgeVtx().uvSpace[ i ].freeEdge );
		}
	}

	private final void
	SetSpringMain( lvMakeUVspaceType.EdgeOne edgeSet[] ) throws lvThrowable
	{
		lvVector  pos0 = Gbl().tvSetSpringMain[ 0 ];			// pos0 = new lvVector( global );
		lvVector  pos1 = Gbl().tvSetSpringMain[ 1 ];			// pos1 = new lvVector( global );

		for( int i=0; i<edgeSet.length; i++ ) {
			int  edgeNo = edgeSet[ i ].edgeNo;
			lvPolygon.InfoFaceHalf  faceHalf = Polygon().edge[ edgeNo ].face[ 0 ];
			lvRec.SeqPart           face     = Polygon().face[ faceHalf.faceNo ];
			int  vtxNo0 = Polygon().faceHalfSeq[ face.start + faceHalf.halfNo ].vtxNo;
			int  vtxNo1 = Polygon().faceHalfSeq[ face.start + ( ( faceHalf.halfNo + 1 ) % face.num ) ].vtxNo;
			
			pos0.VecDt2Vector( Polygon().vertex[ vtxNo0 ].pos );
			pos1.VecDt2Vector( Polygon().vertex[ vtxNo1 ].pos );
			double  len = ( pos0.Sub( pos1 ) ).Length();
			double  spring;
			if( Eps().IsZero( len ) == true )
				spring = 1000.0;		// very large value;
			else
				spring = 1.0 / Math.pow( len, 1.0 );
				
			edgeSet[ i ].spring = spring;
		}
	}

	private final void
	Finish()
	{
		Gbl().srcUVpublic = null;
		Gbl().srcBndInfo  = null;
		Gbl().tmpEdge     = null;
		Gbl().tmpVtx      = null;
	}

}
