//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVbound.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/04/08-)
 * 
 */
public class lvMakeUVbound extends lvRoot {

	/**
	 * 境界セグメントに対する一時的内部構造体
	 */
	private static class TmpHalf {

		/** 境界セグメント上にあるか？									*/
		private boolean  onBound;

		/** 一時的境界セグメント生成用巡回リスト（片方向）				*/
		private int      nextGsNo;
		private int      nextHalfNo;
		
		/** SetUVnumBnd(), SetUVbndSeq() 用の一時フラグ					*/
		private boolean  alreadyUsed;
	}

	/** 同一UV空間の曲面集合内の1境界セグメント情報	（テンポラリ）		*/
	public static class TmpBndVtx {

		/** 頂点位置の配列（境界セグメント内の頂点の数だけ存在する）	*/
		public lvVector  vtx[]					= null;
	}
	
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo						*/
		private int                         curShellNo;

		/** 入力データ								*/
		private lvMakeUVspaceType.UVpublic  srcUVpublic			= null;

		/** 出力データ								*/
		private lvMakeUVspaceType.Bound     dstBndInfo			= null;

		/** 境界セグメントに対する一時情報（配列長は、Polygon().face[ ngStartNo ].start に等しい ）		*/
		private TmpHalf                     tmpHalf[]			= null;

		/** 「そのGS面は、該当UV空間No.を持つか？」の配列（GS面の数だけ存在する）	*/
		private boolean                     gsHasUVspace[]		= null;

		/** 同一UV空間の曲面集合内の1境界セグメント情報（配列長は、DstBndInfo().uvBndSeq[]と等しい ）	*/
		private TmpBndVtx                   tmpBndVtx[]			= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Global( lvGlobal dt )
		{
			GlobalTmp( dt );
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvSetFixBndNo0One[]	= null;
		private lvDouble  tdSetFixBndNo0One[]	= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvSetFixBndNo0One  = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvSetFixBndNo0One[ i ] = new lvVector( dt );
			tdSetFixBndNo0One  = new lvDouble[ 2 ];	for( int i=0; i<2; i++ )	tdSetFixBndNo0One[ i ] = new lvDouble( dt );
		}
	}


	/** 当クラス用のグローバルデータ						*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gMakeUVbound;
	}
	/** lvMakeUVspaceType.UVpublicデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVpublic
	SrcUVpublic()
	{
		return  Gbl().srcUVpublic;
	}
	/** lvMakeUVspaceType.Boundデータ用クラスオブジェクト		*/
	private final lvMakeUVspaceType.Bound
	DstBndInfo()
	{
		return  Gbl().dstBndInfo;
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
	public lvMakeUVbound( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public lvMakeUVspaceType.Bound
	Exec( int shellNo, lvMakeUVspaceType.UVpublic srcUVpublic ) throws lvThrowable
	{
		if( srcUVpublic == null )
			return null;
			
		Gbl().curShellNo  = shellNo;
		Gbl().srcUVpublic = srcUVpublic;
		Gbl().dstBndInfo  = new lvMakeUVspaceType.Bound();

		ExecMain();
		SetFixBndNo();
		
		Finish();
		
		return Gbl().dstBndInfo;
	}

	private final void
	ExecMain() throws lvThrowable
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ ) {
			ExecOne( i );
			SetDstBndInfo( i );
		}
	}
	
	private final void
	ExecOne( int uvSpaceNo ) throws lvThrowable
	{
		if( uvSpaceNo == 0 ) {
			NewTmpHalf();
			NewGsHasUVspace();
		}
		
		InitBoundList( uvSpaceNo );
		ChkOnBound();
		JoinBoundList();
	}
	
	private final void
	NewTmpHalf()
	{
		int  numHalf;
		if( Polygon().face.length > Polygon().ngStartNo )
			numHalf = Polygon().face[ Polygon().ngStartNo ].start;
		else
			numHalf = Polygon().faceHalfSeq.length;
		Gbl().tmpHalf = new TmpHalf[ numHalf ];
		for( int i=0; i<numHalf; i++ )
			Gbl().tmpHalf[ i ] = new TmpHalf();
	}

	private final void
	NewGsHasUVspace()
	{
		int  numGs = Polygon().ngStartNo;
		Gbl().gsHasUVspace = new boolean[ numGs ];
	}

	private final void
	InitBoundList( int uvSpaceNo )
	{
		int  numGs = Polygon().ngStartNo;
		
		for( int i=0; i<numGs; i++ ) {
			Gbl().gsHasUVspace[ i ] = false;
			
			lvRec.SeqPart  gsNumUV = SrcUVpublic().gsNumUV[ i ];
			boolean  exist = false;
			for( int j=0; j<gsNumUV.num; j++ ) {
				if( SrcUVpublic().gsUVseq[ gsNumUV.start + j ] == uvSpaceNo ) {
					exist = true;
					break;
				}
			}
			if( exist == true )
				Gbl().gsHasUVspace[ i ] = true;
		}
		
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				Gbl().tmpHalf[ face.start + j ].onBound  = false;
				Gbl().tmpHalf[ face.start + j ].nextGsNo = -1;
			}
		}
	}
	
	private final void
	ChkOnBound()
	{
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  edgeNo  = Polygon().faceHalfSeq[ face.start + j ].edgeNo;
				int  edgeIdx = Polygon().faceHalfSeq[ face.start + j ].edgeIdx;
				
				boolean  isSame = IsSameUVspaceFace( edgeNo, 1-edgeIdx );
				if( isSame == true )
					continue;
				
				Gbl().tmpHalf[ face.start + j ].onBound = true;
			}
		}
	}

	private final boolean
	IsSameUVspaceFace( int edgeNo, int edgeIdx )
	{
		lvPolygon.InfoFaceHalf  faceHalf = Polygon().edge[ edgeNo ].face[ edgeIdx ];
		if( faceHalf.faceNo >= Polygon().ngStartNo )
			return false;
		
		if( Gbl().gsHasUVspace[ faceHalf.faceNo ] == false )
			return false;
			
		return true;
	}
	
	private final void
	JoinBoundList() throws lvThrowable
	{
		int  num = Polygon().vertex.length;
		for( int i=0; i<num; i++ )
			JoinBoundListMain( i );
	}
	
	private final void
	JoinBoundListMain( int vtxNo ) throws lvThrowable
	{
		int  startNoOrg = -1;
		int  gsNoOrg    = -1;
		int  halfNoOrg  = -1;
		int  startNo, num, nextStartNo, gsNo, halfNo;
		
		lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
		
		boolean  init = true;
		int      cnt  = 0;
		for( int i=0; i<vtxFace.num; i++ ) {
			lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + i ];
			if( Gbl().gsHasUVspace[ vtxFaceSeq.faceNo ] == false )
				continue;
			lvRec.SeqPart  face = Polygon().face[ vtxFaceSeq.faceNo ];
			if( Gbl().tmpHalf[ face.start + vtxFaceSeq.halfNo ].onBound == true ) {
				if( init == true ) {
					init = false;
					startNoOrg = i;
					gsNoOrg    = vtxFaceSeq.faceNo;
					halfNoOrg  = vtxFaceSeq.halfNo;
				}
				cnt++;
			}
		}
		if( cnt == 0 )
			return;
		
		startNo = startNoOrg;
		for( int i=0; i<cnt; i++ ) {
			num         = -1;
			gsNo        = -1;
			halfNo      = -1;
			nextStartNo = -1;
			if( i < ( cnt-1 ) ) {
				boolean  chk = false;
				for( int j=( startNo+1 ); j<vtxFace.num; j++ ) {
					lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
					lvRec.SeqPart           face       = Polygon().face[ vtxFaceSeq.faceNo ];
					if( Gbl().tmpHalf[ face.start + vtxFaceSeq.halfNo ].onBound == true ) {
						chk    = true;
						nextStartNo = j;
						num    = nextStartNo - startNo;
						gsNo   = vtxFaceSeq.faceNo;
						halfNo = vtxFaceSeq.halfNo;
						break;
					}
				}
				Err().Assert( ( chk == true ), "lvMakeUVbound.JoinBoundListMain(0)" );
			}
			else {
				num    = ( startNoOrg + vtxFace.num ) - startNo;
				gsNo   = gsNoOrg;
				halfNo = halfNoOrg;
			}
			
			JoinBoundListOne( vtxNo, startNo, num, gsNo, halfNo );
			if( i < ( cnt-1 ) )
				startNo = nextStartNo;
		}
	}

	private final void
	JoinBoundListOne( int vtxNo, int startNo, int num, int gsNo, int halfNo ) throws lvThrowable
	{
		lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
		
		boolean  chk = false;
		for( int i=0; i<num; i++ ) {
			int  j = ( startNo + i ) % vtxFace.num;
			lvPolygon.InfoFaceHalf  vtxFaceSeq = Polygon().vtxFaceSeq[ vtxFace.start + j ];
			if( Gbl().gsHasUVspace[ vtxFaceSeq.faceNo ] == false )
				continue;
			lvRec.SeqPart  face = Polygon().face[ vtxFaceSeq.faceNo ];
			int  prevHalfNo = ( vtxFaceSeq.halfNo + face.num - 1 ) % face.num;
			if( Gbl().tmpHalf[ face.start + prevHalfNo ].onBound == true ) {
				chk = true;
				Gbl().tmpHalf[ face.start + prevHalfNo ].nextGsNo   = gsNo;
				Gbl().tmpHalf[ face.start + prevHalfNo ].nextHalfNo = halfNo;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVbound.JoinBoundListOne(0)" );
	}
	
	private final void
	SetDstBndInfo( int uvSpaceNo ) throws lvThrowable
	{
		SetUVnumBnd( uvSpaceNo );
		SetUVbndSeq( uvSpaceNo );
	}
	
	private final void
	SetUVnumBnd( int uvSpaceNo ) throws lvThrowable
	{
		if( uvSpaceNo == 0 )
			NewUVnumBnd();
		
		SetUVnumBnd0( uvSpaceNo );
		SetUVnumBnd1( uvSpaceNo );
	}
	
	private final void
	NewUVnumBnd()
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		DstBndInfo().base = new lvMakeUVspaceType.BoundBase[ numUVspace ];
		for( int i=0; i<numUVspace; i++ )
			DstBndInfo().base[ i ] = new lvMakeUVspaceType.BoundBase();
	}
	
	private final void
	SetUVnumBnd0( int uvSpaceNo ) throws lvThrowable
	{
		InitAlreadyUsed();
		SetUVnumBnd0Main( uvSpaceNo );
	}
	
	private final void
	InitAlreadyUsed()
	{
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ )
				Gbl().tmpHalf[ face.start + j ].alreadyUsed = false;
		}
	}
	
	private final void
	SetUVnumBnd0Main( int uvSpaceNo ) throws lvThrowable
	{
		int  numUVnumBnd = 0;
		
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				if( Gbl().tmpHalf[ face.start + j ].onBound     == true  &&
					Gbl().tmpHalf[ face.start + j ].alreadyUsed == false )
				{
					SetUVnumBnd0One( i, j );
					numUVnumBnd++;
				}
			}
		}
		
		Err().Assert( ( numUVnumBnd > 0 ), "lvMakeUVbound.SetUVnumBnd0Main(0)" );
		DstBndInfo().base[ uvSpaceNo ].uvNumBnd.num = numUVnumBnd;
	}
	
	private final void
	SetUVnumBnd0One( int gsNo0, int halfNo0 ) throws lvThrowable
	{
		int  numHalf = Gbl().tmpHalf.length;
		
		int      gsNo   = gsNo0;
		int      halfNo = halfNo0;
		boolean  chk    = false;
		for( int i=0; i<numHalf; i++ ) {
			lvRec.SeqPart  face = Polygon().face[ gsNo ];
			Gbl().tmpHalf[ face.start + halfNo ].alreadyUsed = true;
			gsNo = Gbl().tmpHalf[ face.start + halfNo ].nextGsNo;
			Err().Assert( ( gsNo >= 0 ), "lvMakeUVbound.SetUVnumBnd0One(0)" );
			
			halfNo = Gbl().tmpHalf[ face.start + halfNo ].nextHalfNo;
			if( gsNo == gsNo0 && halfNo == halfNo0 ) {
				chk = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVbound.SetUVnumBnd0One(1)" );
	}
	
	private final void
	SetUVnumBnd1( int uvSpaceNo )
	{
		int  start;
		
		if( uvSpaceNo == 0 )
			start = 0;
		else
			start = DstBndInfo().base[ uvSpaceNo-1 ].uvNumBnd.start + DstBndInfo().base[ uvSpaceNo-1 ].uvNumBnd.num;

		DstBndInfo().base[ uvSpaceNo ].uvNumBnd.start = start;
	}
	
	private final void
	SetUVbndSeq( int uvSpaceNo ) throws lvThrowable
	{
		NewUVbndSeq( uvSpaceNo );
		
		SetUVbndSeq0( uvSpaceNo );
		SetUVbndSeq1( uvSpaceNo );
	}
	
	private final void
	NewUVbndSeq( int uvSpaceNo )
	{
		int  cnt = 0;
		for( int i=0; i<( uvSpaceNo + 1 ); i++ )
			cnt += DstBndInfo().base[ i ].uvNumBnd.num;
		
		if( uvSpaceNo == 0 ) {
			DstBndInfo().uvBndSeq = new lvMakeUVspaceType.BoundOne[ cnt ];
			for( int i=0; i<cnt; i++ )
				DstBndInfo().uvBndSeq[ i ] = new lvMakeUVspaceType.BoundOne();
		}
		else {
			lvMakeUVspaceType.BoundOne  oldUVbndSeq[] = DstBndInfo().uvBndSeq;
			DstBndInfo().uvBndSeq = new lvMakeUVspaceType.BoundOne[ cnt ];
			for( int i=0; i<oldUVbndSeq.length; i++ )
				DstBndInfo().uvBndSeq[ i ] = oldUVbndSeq[ i ];
			for( int i=oldUVbndSeq.length; i<cnt; i++ )
				DstBndInfo().uvBndSeq[ i ] = new lvMakeUVspaceType.BoundOne();
		}
	}
	
	private final void
	SetUVbndSeq0( int uvSpaceNo ) throws lvThrowable
	{
		InitAlreadyUsed();
		SetUVbndSeq0Main( uvSpaceNo );
	}

	private final void
	SetUVbndSeq0Main( int uvSpaceNo ) throws lvThrowable
	{
		int  cntUVnumBnd = 0;
		
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				if( Gbl().tmpHalf[ face.start + j ].onBound     == true  &&
					Gbl().tmpHalf[ face.start + j ].alreadyUsed == false )
				{
					SetUVbndSeq0One( uvSpaceNo, i, j, cntUVnumBnd );
					cntUVnumBnd++;
				}
			}
		}
	}
	
	private final void
	SetUVbndSeq0One( int uvSpaceNo, int gsNo0, int halfNo0, int cntUVnumBnd ) throws lvThrowable
	{
		int  numHalf = Gbl().tmpHalf.length;
		
		int      cnt    = 0;
		int      gsNo   = gsNo0;
		int      halfNo = halfNo0;
		boolean  chk    = false;
		for( int i=0; i<numHalf; i++ ) {
			lvRec.SeqPart  face = Polygon().face[ gsNo ];
			Gbl().tmpHalf[ face.start + halfNo ].alreadyUsed = true;
			
			cnt++;
			
			gsNo = Gbl().tmpHalf[ face.start + halfNo ].nextGsNo;
			Err().Assert( ( gsNo >= 0 ), "lvMakeUVbound.SetUVbndSeq0One(0)" );
			
			halfNo = Gbl().tmpHalf[ face.start + halfNo ].nextHalfNo;
			if( gsNo == gsNo0 && halfNo == halfNo0 ) {
				chk = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVbound.SetUVbndSeq0One(1)" );
		
		lvRec.SeqPart               uvNumBnd = DstBndInfo().base[ uvSpaceNo ].uvNumBnd;
		lvMakeUVspaceType.BoundOne  uvBndSeq = DstBndInfo().uvBndSeq[ uvNumBnd.start + cntUVnumBnd ];
		
		uvBndSeq.half = new lvMakeUVspaceType.HalfInfo[ cnt ];
		for( int i=0; i<cnt; i++ )
			uvBndSeq.half[ i ] = new lvMakeUVspaceType.HalfInfo();
	}
	
	private final void
	SetUVbndSeq1( int uvSpaceNo ) throws lvThrowable
	{
		InitAlreadyUsed();
		SetUVbndSeq1Main( uvSpaceNo );
	}

	private final void
	SetUVbndSeq1Main( int uvSpaceNo ) throws lvThrowable
	{
		int  cntUVnumBnd = 0;
		
		int  numGs = Polygon().ngStartNo;
		for( int i=0; i<numGs; i++ ) {
			if( Gbl().gsHasUVspace[ i ] == false )
				continue;
			
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				if( Gbl().tmpHalf[ face.start + j ].onBound     == true  &&
					Gbl().tmpHalf[ face.start + j ].alreadyUsed == false )
				{
					SetUVbndSeq1One( uvSpaceNo, i, j, cntUVnumBnd );
					cntUVnumBnd++;
				}
			}
		}
	}
	
	private final void
	SetUVbndSeq1One( int uvSpaceNo, int gsNo0, int halfNo0, int cntUVnumBnd ) throws lvThrowable
	{
		lvRec.SeqPart               uvNumBnd = DstBndInfo().base[ uvSpaceNo ].uvNumBnd;
		lvMakeUVspaceType.BoundOne  uvBndSeq = DstBndInfo().uvBndSeq[ uvNumBnd.start + cntUVnumBnd ];
		
		int  numHalf = Gbl().tmpHalf.length;
		
		int      gsNo   = gsNo0;
		int      halfNo = halfNo0;
		boolean  chk    = false;
		for( int i=0; i<numHalf; i++ ) {
			lvRec.SeqPart  face = Polygon().face[ gsNo ];
			Gbl().tmpHalf[ face.start + halfNo ].alreadyUsed = true;
			
			uvBndSeq.half[ i ].gsNo   = gsNo;
			uvBndSeq.half[ i ].halfNo = halfNo;
			
			gsNo = Gbl().tmpHalf[ face.start + halfNo ].nextGsNo;
			Err().Assert( ( gsNo >= 0 ), "lvMakeUVbound.SetUVbndSeq1One(0)" );
			
			halfNo = Gbl().tmpHalf[ face.start + halfNo ].nextHalfNo;
			if( gsNo == gsNo0 && halfNo == halfNo0 ) {
				chk = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakeUVbound.SetUVbndSeq1One(1)" );
	}
	
	private final void
	SetFixBndNo() throws lvThrowable
	{
		SetTmpBndVtx();
		SetFixBndNo0();
	}
	
	private final void
	SetTmpBndVtx()
	{
		NewTmpBndVtx();
		SetTmpBndVtxMain();
	}
	
	private final void
	NewTmpBndVtx()
	{
		int  num = DstBndInfo().uvBndSeq.length;
		Gbl().tmpBndVtx = new TmpBndVtx[ num ];
		for( int i=0; i<num; i++ ) {
			Gbl().tmpBndVtx[ i ] = new TmpBndVtx();
			
			lvMakeUVspaceType.BoundOne  uvBndSeq = DstBndInfo().uvBndSeq[ i ];
			int  numHalf = uvBndSeq.half.length;
			Gbl().tmpBndVtx[ i ].vtx = new lvVector[ numHalf ];
			for( int j=0; j<numHalf; j++ )
				Gbl().tmpBndVtx[ i ].vtx[ j ] = new lvVector( global );
		}
	}
	
	private final void
	SetTmpBndVtxMain()
	{
		int  num = DstBndInfo().uvBndSeq.length;
		for( int i=0; i<num; i++ ) {
			lvMakeUVspaceType.BoundOne  uvBndSeq = DstBndInfo().uvBndSeq[ i ];
			int  numHalf = uvBndSeq.half.length;
			for( int j=0; j<numHalf; j++ ) {
				lvMakeUVspaceType.HalfInfo  halfInfo = uvBndSeq.half[ j ];
				lvRec.SeqPart           face     = Polygon().face[ halfInfo.gsNo ];
				int  vtxNo = Polygon().faceHalfSeq[ face.start + halfInfo.halfNo ].vtxNo;
				
				Gbl().tmpBndVtx[ i ].vtx[ j ].VecDt2Vector( Polygon().vertex[ vtxNo ].pos );
			}
		}
	}
	
	private final void
	SetFixBndNo0() throws lvThrowable
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ )
			SetFixBndNo0Main( i );
	}
	
	private final void
	SetFixBndNo0Main( int uvSpaceNo ) throws lvThrowable
	{
		lvRec.SeqPart  uvNumBnd = DstBndInfo().base[ uvSpaceNo ].uvNumBnd;
		
		double  max   =  0.0;
		int     maxNo = -1;
		for( int i=0; i<uvNumBnd.num; i++ ) {
			double  area = SetFixBndNo0One( Gbl().tmpBndVtx[ uvNumBnd.start + i ].vtx );
			if( i == 0 || max < area ) {
				max   = area;
				maxNo = i;
			}
		}
		DstBndInfo().base[ uvSpaceNo ].fixBndNo = maxNo;
	}

	private final double
	SetFixBndNo0One( lvVector vtx[] ) throws lvThrowable
	{
		lvVector  dmy  = Gbl().tvSetFixBndNo0One[ 0 ];		// dmy  = new lvVector( global );
		lvDouble  area = Gbl().tdSetFixBndNo0One[ 0 ];		// area = new lvDouble( global );
		
		dmy.Normal( vtx, vtx.length, area );
		
		return area.val;
	}

	private final void
	Finish()
	{
		Gbl().srcUVpublic  = null;
		Gbl().tmpHalf      = null;
		Gbl().gsHasUVspace = null;
		Gbl().tmpBndVtx    = null;
	}

}
