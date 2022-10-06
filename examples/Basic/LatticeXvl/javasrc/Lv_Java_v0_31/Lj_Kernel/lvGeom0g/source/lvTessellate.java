//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvTessellate.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;
import	jp.co.lattice.vKernel.tex.a0g.lvTessellateUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0TessellateUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0AttGGblElm;


/**
 * 1個の面に存在するすべての内部稜線を作成するクラス
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvTessellate extends lvRoot {

	private static final int  maxNumPatch		= 256;
	private static final int  maxNumVertex		= 256;
	private static final int  maxNumTriIndex	= 256;
		
// -------------------------------------------------------------------
	
	/**
	 * 内部稜線に関する「NewUpDivFace()のために、一時的に使用する情報のための内部クラス」
	 */
	private static class TmpPatch {
		
		/** v方向（ハーフエッジNo0の終点→始点、ハーフエッジNo2の始点→終点）の分割数	*/
		public int       numDivV;
		/** u方向（ハーフエッジNo1の始点→終点、ハーフエッジNo3の終点→始点）の分割数	*/
		public int       numDivU;
		
		/** UpDivFace().vertex に対する各パッチのスタート位置と個数				*/
		private lvRec.SeqPart  infoVertex			= new lvRec.SeqPart();
		/** UpDivFace().triIndex に対する各パッチのスタート位置と個数			*/
		private lvRec.SeqPart  infoTriIndex			= new lvRec.SeqPart();
	}
		
	/**
	 * NewUpDivFace()のために、一時的に使用する情報のための内部クラス
	 */
	private static class TmpTessellate {
		
		/** patch[]の有効長										*/
		private int       numPatch;
		/** 内部稜線データ配列（長さは内部稜線数 or 1 ）		*/
		private TmpPatch  patch[]			= null;
	}
		
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** 下位レイヤーオブジェクト					*/
		private lvTessellateLow                 tessellateLow		= null;

		/** 下位レイヤーオブジェクト					*/
		private lvTessellateUV                  tessellateUV		= null;

		/** lv0AttGGblElmオブジェクト			*/
		private lv0AttGGblElm                   attGGblElm			= null;
		
		/** 下位レイヤーに送るデータ			*/
		private lvTessellateLow.DownTessellate  downTessellate		= null;
		
		/** 下位レイヤーから送られるデータ		*/
		private lvTessellateLow.UpTessellate    upTessellate		= null;


		/** カレントの「上位レイヤー(topo0)から送られるデータ」		*/
		private lvDivFaceType.DownDivFace       curDownDivFace		= null;
		/** カレントの「DownDivFaceから派生したデータ」				*/
		private lvDivFaceType.DeriveDivFace     curDeriveDivFace	= null;
		/** カレントの「上位レイヤー(topo0)に送るデータ」			*/
		private lvDivFaceType.UpDivFace	        curUpDivFace		= null;

		/** NewUpDivFace()のために、一時的に使用する情報		*/
		private TmpTessellate                   tmpTessellate		= null;

		private lvMakeCbd						makeCbd				= null;
		
		private lvVector        				antiCenter			= null;
		private lvVector        				antiVtx0			= null;
		private lvVector        				antiVtx1			= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			tessellateLow    = new lvTessellateLow( dt );
			tessellateUV     = new lv0TessellateUV( dt );
			attGGblElm	     = new lv0AttGGblElm( dt );
			downTessellate   = new lvTessellateLow.DownTessellate( dt );
			upTessellate     = new lvTessellateLow.UpTessellate();

			curDownDivFace   = new lvDivFaceType.DownDivFace( dt );
			curDeriveDivFace = new lvDivFaceType.DeriveDivFace( dt );
			curUpDivFace     = new lvDivFaceType.UpDivFace();
			
			tmpTessellate    = new TmpTessellate();
			
			makeCbd          = new lvMakeCbd( dt );
			
			antiCenter       = new lvVector( dt );
			antiVtx0         = new lvVector( dt );
			antiVtx1         = new lvVector( dt );

			GlobalTmp( dt );
			GlobalStatic( dt );
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvMakeCbd.DownCbd    tdGetBCbdOuter[]				= null;
		private lvMakeCbd.UpCbd      tuGetBCbdOuter[]				= null;
		private lvMakeCbd.DownCbd    tdGetBCbdInner[]				= null;
		private lvMakeCbd.UpCbd      tuGetBCbdInner[]				= null;
		private lvVector             tvGetBCbdMain[]				= null;
		private lvMakeCbd.DownCbd    tdGetBCbdHalfEdgeFirst[]		= null;
		private lvMakeCbd.UpHalfCbd  tuGetBCbdHalfEdgeFirst[]		= null;
		private lvMakeCbd.DownCbd    tdGetBCbdHalfEdgeLast[]		= null;
		private lvMakeCbd.UpHalfCbd  tuGetBCbdHalfEdgeLast[]		= null;
		private lvVector             tvGetBCbdHalfEdgeFirstMain[]	= null;
		private lvVector             tvGetBCbdHalfEdgeLastMain[]	= null;

		private lvVector  tvSetDownTessellateOuterFirst0[/*4*/]		= null;
		private lvVector  tvSetDownTessellateOuterFirst1[/*4*/]		= null;
		
		private lvVector  tvSetDownTessellateOuterLast0[/*4*/]		= null;
		private lvVector  tvSetDownTessellateOuterLast1[/*4*/]		= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tdGetBCbdOuter = new lvMakeCbd.DownCbd[ 2 ];
			for( int i=0; i<2; i++ )	tdGetBCbdOuter[ i ] = new lvMakeCbd.DownCbd( dt );
			tuGetBCbdOuter = new lvMakeCbd.UpCbd[ 2 ];
			for( int i=0; i<2; i++ )	tuGetBCbdOuter[ i ] = new lvMakeCbd.UpCbd( dt );
			tdGetBCbdInner = new lvMakeCbd.DownCbd[ 2 ];
			for( int i=0; i<2; i++ )	tdGetBCbdInner[ i ] = new lvMakeCbd.DownCbd( dt );
			tuGetBCbdInner = new lvMakeCbd.UpCbd[ 2 ];
			for( int i=0; i<2; i++ )	tuGetBCbdInner[ i ] = new lvMakeCbd.UpCbd( dt );

			tvGetBCbdMain   = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvGetBCbdMain[ i ]   = new lvVector( dt );
			
			tdGetBCbdHalfEdgeFirst = new lvMakeCbd.DownCbd[ 2 ];
			for( int i=0; i<2; i++ ) tdGetBCbdHalfEdgeFirst[ i ] = new lvMakeCbd.DownCbd( dt );
			tuGetBCbdHalfEdgeFirst = new lvMakeCbd.UpHalfCbd[ 2 ];
			for( int i=0; i<2; i++ ) tuGetBCbdHalfEdgeFirst[ i ] = new lvMakeCbd.UpHalfCbd( dt );
			tdGetBCbdHalfEdgeLast  = new lvMakeCbd.DownCbd[ 2 ];
			for( int i=0; i<2; i++ ) tdGetBCbdHalfEdgeLast[ i ]  = new lvMakeCbd.DownCbd( dt );
			tuGetBCbdHalfEdgeLast  = new lvMakeCbd.UpHalfCbd[ 2 ];
			for( int i=0; i<2; i++ ) tuGetBCbdHalfEdgeLast[ i ]  = new lvMakeCbd.UpHalfCbd( dt );
			
			tvGetBCbdHalfEdgeFirstMain = new lvVector[ 8 ];
			for( int i=0; i<8; i++ )	tvGetBCbdHalfEdgeFirstMain[ i ] = new lvVector( dt );
			tvGetBCbdHalfEdgeLastMain  = new lvVector[ 8 ];
			for( int i=0; i<8; i++ )	tvGetBCbdHalfEdgeLastMain[ i ]  = new lvVector( dt );

			tvSetDownTessellateOuterFirst0 = new lvVector[ 4 ];
			for( int i=0; i<4; i++ )	tvSetDownTessellateOuterFirst0[ i ] = new lvVector( dt );
			tvSetDownTessellateOuterFirst1 = new lvVector[ 4 ];
			for( int i=0; i<4; i++ )	tvSetDownTessellateOuterFirst1[ i ] = new lvVector( dt );
			
			tvSetDownTessellateOuterLast0 = new lvVector[ 4 ];
			for( int i=0; i<4; i++ )	tvSetDownTessellateOuterLast0[ i ] = new lvVector( dt );
			tvSetDownTessellateOuterLast1 = new lvVector[ 4 ];
			for( int i=0; i<4; i++ )	tvSetDownTessellateOuterLast1[ i ] = new lvVector( dt );
		}

		/** 小規模な Gbl().tmpTessellate.inner 用のグローバルデータ		*/
		private TmpPatch        staticPatch[]		= null;
		/** 小規模な DownTessellate().vertex 用のグローバルデータ		*/
		private lvRec.PosNorHi  staticVertex[]		= new lvRec.PosNorHi[ maxNumVertex ];
		/** 小規模な DownTessellate().triIndex 用のグローバルデータ		*/
		private lvRec.TriIndex  staticTriIndex[]	= new lvRec.TriIndex[ maxNumTriIndex ];
		
		/**
		 * 小規模な配列用のグローバルデータの初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalStatic( lvGlobal dt )
		{
			staticPatch = new TmpPatch[ maxNumPatch ];
			for( int i=0; i<maxNumPatch; i++ )
				staticPatch[ i ] = new TmpPatch();
		}
		
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gTessellate;
	}
	/** DownTessellate用のグローバルデータ			*/
	private final lvTessellateLow.DownTessellate
	DownTessellate()
	{
		return  Gbl().downTessellate;
	}
	/** UpTessellate用のグローバルデータ			*/
	private final lvTessellateLow.UpTessellate
	UpTessellate()
	{
		return  Gbl().upTessellate;
	}
	/** DownDivFace用のグローバルデータ				*/
	private final lvDivFaceType.DownDivFace
	DownDivFace()
	{
		return	Gbl().curDownDivFace;
	}
	/** DeriveDivFace用のグローバルデータ			*/
	private final lvDivFaceType.DeriveDivFace
	DeriveDivFace()
	{
		return	Gbl().curDeriveDivFace;
	}
	/** UpDivFace用のグローバルデータ				*/
	private final lvDivFaceType.UpDivFace
	UpDivFace()
	{
		return	Gbl().curUpDivFace;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvTessellate( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	/**
	 * 実行関数
	 */
	public final void
	Exec( lvDivFaceType.DownDivFace downDivFace, lvDivFaceType.DeriveDivFace deriveDivFace,
			lvDivFaceType.UpDivFace upDivFace ) throws lvThrowable
	{
		Gbl().curDownDivFace   = downDivFace;
		Gbl().curDeriveDivFace = deriveDivFace;
		Gbl().curUpDivFace	   = upDivFace;

		NewTmpTessellate();
		SetPatchInfo();
		SeqPartUpDivFace();

		if( Gbl().attGGblElm.IsDmy() == false ) {
			Gbl().tessellateUV.Init( DownDivFace(), DeriveDivFace(), UpDivFace(), DownTessellate().uvInfo,
					UpTessellate().uvInfo );
		}
		NewUpDivFace();
		
		if( DownDivFace().numHalf == 4 )
			ExecQuad();
		else
			ExecMain();
			
		Finish();
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().tessellateUV.Finish();
	}
	
	private final void
	NewTmpTessellate()
	{
		int  num;
		if( DownDivFace().numHalf == 4 )
			num = 1;
		else
			num = DownDivFace().numHalf;
			
		Gbl().tmpTessellate.numPatch = num;
		
		if( num > maxNumPatch ) {
			Gbl().tmpTessellate.patch = new TmpPatch[ num ];
			for( int i=0; i<maxNumPatch; i++ )
				Gbl().tmpTessellate.patch[ i ] = Gbl().staticPatch[ i ];
			for( int i=maxNumPatch; i<num; i++ )
				Gbl().tmpTessellate.patch[ i ] = new TmpPatch();
		}
		else
			Gbl().tmpTessellate.patch = Gbl().staticPatch;
	}
	
	private final void
	SetPatchInfo()
	{
		if( DownDivFace().numHalf == 4 ) {
			Gbl().tmpTessellate.patch[ 0 ].numDivU = DownDivFace().attr.numDiv;
			Gbl().tmpTessellate.patch[ 0 ].numDivV = DownDivFace().attr.numDiv;
			return;
		}

		for( int i=0; i<DownDivFace().numHalf; i++ ) {
			Gbl().tmpTessellate.patch[ i ].numDivU = DownDivFace().attr.numDiv / 2;
			Gbl().tmpTessellate.patch[ i ].numDivV = DownDivFace().attr.numDiv / 2;
		}
	}
	
	private final void
	SeqPartUpDivFace()
	{
		SeqPartUpDivFaceVertex();
		SeqPartUpDivFaceTriIndex();
	}
	
	private final void
	SeqPartUpDivFaceVertex()
	{
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ ) {
			lvRec.SeqPart  infoVertex = Gbl().tmpTessellate.patch[ i ].infoVertex;
			int  numDivU = Gbl().tmpTessellate.patch[ i ].numDivU;
			int  numDivV = Gbl().tmpTessellate.patch[ i ].numDivV;
			infoVertex.num = lvTessellateLow.NumVertexStd( numDivU, numDivV );
		}
		
		int  cnt = 0;
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ ) {
			Gbl().tmpTessellate.patch[ i ].infoVertex.start = cnt;
			cnt += Gbl().tmpTessellate.patch[ i ].infoVertex.num;
		}
	}
	
	private final void
	SeqPartUpDivFaceTriIndex()
	{
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ ) {
			lvRec.SeqPart  infoTriIndex = Gbl().tmpTessellate.patch[ i ].infoTriIndex;
			int  numDivU = Gbl().tmpTessellate.patch[ i ].numDivU;
			int  numDivV = Gbl().tmpTessellate.patch[ i ].numDivV;
			infoTriIndex.num = lvTessellateLow.NumTriIndexStd( numDivU, numDivV );
		}
		
		int  cnt = 0;
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ ) {
			Gbl().tmpTessellate.patch[ i ].infoTriIndex.start = cnt;
			cnt += Gbl().tmpTessellate.patch[ i ].infoTriIndex.num;
		}
	}
	
	private final void
	NewUpDivFace()
	{
		NewUpDivFaceVertex();
		NewUpDivFaceTriIndex();
	}
	
	private final void
	NewUpDivFaceVertex()
	{
		int  numVertex = 0;
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ )
			numVertex += Gbl().tmpTessellate.patch[ i ].infoVertex.num;
		
		UpDivFace().vertex = new lvRec.PosNorHi[ numVertex ];
		for( int i=0; i<numVertex; i++ )
			UpDivFace().vertex[ i ] = new lvRec.PosNorHi( global );
		
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().tessellateUV.NewUpDivFaceUV( numVertex );
	}
	
	private final void
	NewUpDivFaceTriIndex()
	{
		int  numTriIndex = 0;
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ )
			numTriIndex += Gbl().tmpTessellate.patch[ i ].infoTriIndex.num;

		UpDivFace().triIndex = new lvRec.TriIndex[ numTriIndex ];
		for( int i=0; i<numTriIndex; i++ )
			UpDivFace().triIndex[ i ] = new lvRec.TriIndex();
	}
	
	private final void
	ExecQuad() throws lvThrowable
	{
		NewUpTessellate( 0 );
		
		DownTessellate().numDivU = Gbl().tmpTessellate.patch[ 0 ].numDivU;
		DownTessellate().numDivV = Gbl().tmpTessellate.patch[ 0 ].numDivV;
		
		for( int i=0; i<4; i++ ) {
			DownTessellate().half[ i ].pos.Assign( DownDivFace().half[ i ].pos );
			for( int j=0; j<2; j++ )
				DownTessellate().half[ i ].handVec[ j ].Assign( DownDivFace().half[ i ].handVec[ j ] );
		}

		for( int i=0; i<4; i++ )
			GetBCbdOuter( i, DownTessellate().half[ i ].bCbd );
		
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().tessellateUV.SetQuad();
		Gbl().tessellateLow.Exec( DownTessellate(), UpTessellate() );
		CorrectTriIndexNo( 0 );
	}
	
	private final void
	NewUpTessellate( int patchNo )
	{
		lvRec.SeqPart infoVertex = Gbl().tmpTessellate.patch[ patchNo ].infoVertex;
		
		NewUpTessellateVertex( infoVertex );
		NewUpTessellateTriIndex( Gbl().tmpTessellate.patch[ patchNo ].infoTriIndex );
		
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().tessellateUV.NewUpTessellateUV( infoVertex );
	}
	
	private final void
	NewUpTessellateVertex( lvRec.SeqPart infoVertex )
	{
		UpTessellate().numVertex = infoVertex.num;
		
		if( infoVertex.num > maxNumVertex )
			UpTessellate().vertex = new lvRec.PosNorHi[ infoVertex.num ];
		else
			UpTessellate().vertex = Gbl().staticVertex;
			
		for( int i=0; i<infoVertex.num; i++ )
			UpTessellate().vertex[ i ] = UpDivFace().vertex[ infoVertex.start + i ];
	}
	
	private final void
	NewUpTessellateTriIndex( lvRec.SeqPart infoTriIndex )
	{
		UpTessellate().numTriIndex = infoTriIndex.num;
		
		if( infoTriIndex.num > maxNumTriIndex )
			UpTessellate().triIndex = new lvRec.TriIndex[ infoTriIndex.num ];
		else
			UpTessellate().triIndex = Gbl().staticTriIndex;
			
		for( int i=0; i<infoTriIndex.num; i++ )
			UpTessellate().triIndex[ i ] = UpDivFace().triIndex[ infoTriIndex.start + i ];
	}
	
	private final void
	GetBCbdOuter( int halfNo, lvVector bCbd[/*2*/] ) throws lvThrowable
	{
		lvMakeCbd.DownCbd  downCbd = Gbl().tdGetBCbdOuter[ 0 ];		// downCbd = lvMakeCbd.DownCbd( global );
		lvMakeCbd.UpCbd    upCbd   = Gbl().tuGetBCbdOuter[ 0 ];		// upCbd   = lvMakeCbd.UpCbd( global );
		
		SetDownCbdOuter( halfNo, downCbd );
		Gbl().makeCbd.Exec( downCbd, upCbd );
		GetBCbdMain( downCbd, upCbd, bCbd );
	}
	
	private final void
	SetDownCbdOuter( int halfNo, lvMakeCbd.DownCbd downCbd )
	{
		int  halfB  = ( halfNo + DownDivFace().numHalf - 1 ) % DownDivFace().numHalf;
		int  halfF  = ( halfNo + 1 ) % DownDivFace().numHalf;
		int  halfF2 = ( halfNo + 2 ) % DownDivFace().numHalf;
		
		downCbd.info[ 0 ].vtx.Assign( DownDivFace().half[ halfNo ].pos );
		downCbd.info[ 1 ].vtx.Assign( DownDivFace().half[ halfF  ].pos );
		
		downCbd.info[ 0 ].cVec.Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		downCbd.info[ 1 ].cVec.Assign( DownDivFace().half[ halfNo ].handVec[ 1 ].Neg() );
		
		downCbd.info[ 0 ].bCbd.Assign( DownDivFace().half[ halfB ].handVec[ 1 ] );
		downCbd.info[ 1 ].bCbd.Assign( DownDivFace().half[ halfF ].handVec[ 0 ] );
		
		downCbd.faceNormal.Assign( DownDivFace().center.normal );
		
		if( DownDivFace().numHalf == 3 ) {
			downCbd.antiCenter = Gbl().antiCenter;
			downCbd.antiCenter.Assign( DownDivFace().half[ halfB ].pos );
			
			downCbd.info[ 0 ].antiVtx = Gbl().antiVtx0;
			downCbd.info[ 1 ].antiVtx = Gbl().antiVtx1;
			downCbd.info[ 0 ].antiVtx.Assign( downCbd.antiCenter );
			downCbd.info[ 1 ].antiVtx.Assign( downCbd.antiCenter );
		}
		else if( DownDivFace().numHalf == 4 ) {
			downCbd.antiCenter = Gbl().antiCenter;
			lvDivFaceLow.Position( halfF2, 0.5, downCbd.antiCenter );
			
			downCbd.info[ 0 ].antiVtx = Gbl().antiVtx0;
			downCbd.info[ 1 ].antiVtx = Gbl().antiVtx1;
			downCbd.info[ 0 ].antiVtx.Assign( DownDivFace().half[ halfB  ].pos );
			downCbd.info[ 1 ].antiVtx.Assign( DownDivFace().half[ halfF2 ].pos );
		}
		else {
			downCbd.antiCenter        = null;
			downCbd.info[ 0 ].antiVtx = null;
			downCbd.info[ 1 ].antiVtx = null;
		}
	}
	
	private final void
	GetBCbdMain( lvMakeCbd.DownCbd downCbd, lvMakeCbd.UpCbd upCbd, lvVector bCbd[/*2*/] ) throws lvThrowable
	{
		lvVector  c1 = Gbl().tvGetBCbdMain[ 0 ];			// c1 = new lvVector( global );
		
		lvVector  a0 = upCbd.cbd[ 0 ].aCbd;
		lvVector  a3 = upCbd.cbd[ 1 ].aCbd;
		lvVector  c0 = downCbd.info[ 0 ].cVec;
		lvVector  c2 = downCbd.info[ 1 ].cVec;
		
		c1.Assign( ( downCbd.info[ 1 ].vtx.Sub( c2 ) ).Sub( downCbd.info[ 0 ].vtx.Add( c0 ) ) );
		
		double  k0 = upCbd.cbd[ 0 ].k;
		double  k1 = upCbd.k1;
		double  k2 = upCbd.cbd[ 1 ].k;
		
		double  h0 = upCbd.cbd[ 0 ].h;
		double  h1 = upCbd.cbd[ 1 ].h;
		
		lvVector  b1 = bCbd[ 0 ];
		lvVector  b2 = bCbd[ 1 ];
		
		b1.Assign( ( a3.Mul( 1.0/3.0 * k0 ) ).Add( a0.Mul( 2.0/3.0 * k1 ) ) );
		b1.AddAssign( ( c1.Mul( 2.0/3.0 * h0 ) ).Add( c0.Mul( 1.0/3.0 * h1 ) ) );
		
		b2.Assign( ( a3.Mul( 2.0/3.0 * k1 ) ).Add( a0.Mul( 1.0/3.0 * k2 ) ) );
		b2.AddAssign( ( c2.Mul( 1.0/3.0 * h0 ) ).Add( c1.Mul( 2.0/3.0 * h1 ) ) );
	}
	
	private final void
	CorrectTriIndexNo( int patchNo )
	{
		lvRec.SeqPart  infoVertex   = Gbl().tmpTessellate.patch[ patchNo ].infoVertex;
		lvRec.SeqPart  infoTriIndex = Gbl().tmpTessellate.patch[ patchNo ].infoTriIndex;
		
		for( int i=0; i<infoTriIndex.num; i++ ) {
			for( int j=0; j<3; j++ )
				UpTessellate().triIndex[ i ].vtxNo[ j ] += infoVertex.start;
		}
	}

	private final void
	ExecMain() throws lvThrowable
	{
		for( int i=0; i<Gbl().tmpTessellate.numPatch; i++ ) {
			NewUpTessellate( i );
			
			DownTessellate().numDivU = Gbl().tmpTessellate.patch[ i ].numDivU;
			DownTessellate().numDivV = Gbl().tmpTessellate.patch[ i ].numDivV;

			SetDownTessellate( i );
			
			if( Gbl().attGGblElm.IsDmy() == false )
				Gbl().tessellateUV.SetGtQuad( i, Gbl().tmpTessellate.numPatch );
			Gbl().tessellateLow.Exec( DownTessellate(), UpTessellate() );
			CorrectTriIndexNo( i );
		}
	}
	
	private final void
	SetDownTessellate( int halfNo ) throws lvThrowable
	{
		int  halfF = ( halfNo + 1 ) % Gbl().tmpTessellate.numPatch;
		
		SetDownTessellateInner( halfNo, DownTessellate().half[ 0 ], true  );
		SetDownTessellateOuter( halfNo, DownTessellate().half[ 1 ], true  );
		SetDownTessellateOuter( halfF,  DownTessellate().half[ 2 ], false );
		SetDownTessellateInner( halfF,  DownTessellate().half[ 3 ], false );
		
		GetBCbdInner( 0, DownTessellate().half );
		GetBCbdHalfEdgeLast(  halfNo, DownTessellate().half[ 1 ].bCbd );
		GetBCbdHalfEdgeFirst( halfF,  DownTessellate().half[ 2 ].bCbd );
		GetBCbdInner( 3, DownTessellate().half );
	}
	
	private final void
	SetDownTessellateInner( int halfNo, lvTessellateLow.DownHalf half, boolean reverse ) throws lvThrowable
	{
		if( reverse == false ) {
			lvDivFaceLow.Position( halfNo, 0.5, half.pos );
				
			for( int j=0; j<2; j++ )
				half.handVec[ j ].Assign( DeriveDivFace().inner[ halfNo ].handVec[ j ] );
		}
		else {
			half.pos.Assign( DeriveDivFace().center.pos );
				
			for( int j=0; j<2; j++ )
				half.handVec[ j ].Assign( DeriveDivFace().inner[ halfNo ].handVec[ 1 - j ] );
		}
	}
	
	private final void
	SetDownTessellateOuter( int halfNo, lvTessellateLow.DownHalf half, boolean halfEdgeLast ) throws lvThrowable
	{
		if( halfEdgeLast == false )
			SetDownTessellateOuterFirst( halfNo, half );
		else
			SetDownTessellateOuterLast( halfNo, half );
	}
	
	private final void
	SetDownTessellateOuterFirst( int halfNo, lvTessellateLow.DownHalf half ) throws lvThrowable
	{
		lvVector  curve[/*4*/]      = Gbl().tvSetDownTessellateOuterFirst0;
		lvVector  curveFirst[/*4*/] = Gbl().tvSetDownTessellateOuterFirst1;
		
		GetOuterCurve( halfNo, curve );
		lvBezLine.DivideBezier( curve, 0.5, curveFirst, null );
		
		half.pos.Assign( DownDivFace().half[ halfNo ].pos );
		
		half.handVec[ 0 ].Assign( curveFirst[ 1 ].Sub( curveFirst[ 0 ] ) );
						// half.handVec[ 0 ] = curveFirst[ 1 ] - curveFirst[ 0 ];
		half.handVec[ 1 ].Assign( curveFirst[ 2 ].Sub( curveFirst[ 3 ] ) );
						// half.handVec[ 1 ] = curveFirst[ 2 ] - curveFirst[ 3 ];
	}
	
	private final void
	GetOuterCurve( int halfNo, lvVector curve[/*4*/] )
	{
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		
		curve[ 0 ].Assign( DownDivFace().half[ halfNo ].pos );
		curve[ 1 ].Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		curve[ 2 ].Assign( DownDivFace().half[ halfNo ].handVec[ 1 ] );
		curve[ 3 ].Assign( DownDivFace().half[ halfF  ].pos );
		
		curve[ 1 ].AddAssign( curve[ 0 ] );
		curve[ 2 ].AddAssign( curve[ 3 ] );
	}
	
	private final void
	SetDownTessellateOuterLast( int halfNo, lvTessellateLow.DownHalf half ) throws lvThrowable
	{
		lvVector  curve[/*4*/]     = Gbl().tvSetDownTessellateOuterLast0;
		lvVector  curveLast[/*4*/] = Gbl().tvSetDownTessellateOuterLast1;
		
		GetOuterCurve( halfNo, curve );
		lvBezLine.DivideBezier( curve, 0.5, null, curveLast );
		
		half.pos.Assign( curveLast[ 0 ] );
		
		half.handVec[ 0 ].Assign( curveLast[ 1 ].Sub( curveLast[ 0 ] ) );
						// half.handVec[ 0 ] = curveFirst[ 1 ] - curveFirst[ 0 ];
		half.handVec[ 1 ].Assign( curveLast[ 2 ].Sub( curveLast[ 3 ] ) );
						// half.handVec[ 1 ] = curveFirst[ 2 ] - curveFirst[ 3 ];
	}
	
	private final void
	GetBCbdInner( int halfNo, lvTessellateLow.DownHalf half[/*4*/] ) throws lvThrowable
	{
		lvMakeCbd.DownCbd  downCbd = Gbl().tdGetBCbdInner[ 0 ];		// downCbd = lvMakeCbd.DownCbd( global );
		lvMakeCbd.UpCbd    upCbd   = Gbl().tuGetBCbdInner[ 0 ];		// upCbd   = lvMakeCbd.UpCbd( global );
		
		SetDownCbdInner( halfNo, half, downCbd );
		Gbl().makeCbd.Exec( downCbd, upCbd );
		GetBCbdMain( downCbd, upCbd, half[ halfNo ].bCbd );
	}
	
	private final void
	SetDownCbdInner( int halfNo, lvTessellateLow.DownHalf half[/*4*/], lvMakeCbd.DownCbd downCbd )
	{
		int  halfB = ( halfNo + 4 - 1 ) % 4;
		int  halfF = ( halfNo + 1 ) % 4;
		
		downCbd.info[ 0 ].vtx.Assign( half[ halfNo ].pos );
		downCbd.info[ 1 ].vtx.Assign( half[ halfF  ].pos );
		
		downCbd.info[ 0 ].cVec.Assign( half[ halfNo ].handVec[ 0 ] );
		downCbd.info[ 1 ].cVec.Assign( half[ halfNo ].handVec[ 1 ].Neg() );
		
		downCbd.info[ 0 ].bCbd.Assign( half[ halfB ].handVec[ 1 ] );
		downCbd.info[ 1 ].bCbd.Assign( half[ halfF ].handVec[ 0 ] );
		
		downCbd.faceNormal.Assign( DownDivFace().center.normal );
		
		downCbd.antiCenter        = null;
		downCbd.info[ 0 ].antiVtx = null;
		downCbd.info[ 1 ].antiVtx = null;
	}
	
	private final void
	GetBCbdHalfEdgeFirst( int halfNo, lvVector bCbd[/*2*/] ) throws lvThrowable
	{
		lvMakeCbd.DownCbd    downCbd   = Gbl().tdGetBCbdHalfEdgeFirst[ 0 ];		// downCbd   = lvMakeCbd.DownCbd( global );
		lvMakeCbd.UpHalfCbd  upHalfCbd = Gbl().tuGetBCbdHalfEdgeFirst[ 0 ];		// upHalfCbd = lvMakeCbd.UpHalfCbd( global );
		
		SetDownCbdOuter( halfNo, downCbd );
		Gbl().makeCbd.ExecHalf( downCbd, upHalfCbd );
		GetBCbdHalfEdgeFirstMain( halfNo, downCbd, upHalfCbd, bCbd );
	}
	
	private final void
	GetBCbdHalfEdgeFirstMain( int halfNo, lvMakeCbd.DownCbd downCbd, lvMakeCbd.UpHalfCbd upHalfCbd,
			lvVector bCbd[/*2*/] ) throws lvThrowable
	{
		lvVector  c0     = Gbl().tvGetBCbdHalfEdgeFirstMain[ 0 ];			// c0     = new lvVector( global );
		lvVector  c1     = Gbl().tvGetBCbdHalfEdgeFirstMain[ 1 ];			// c1     = new lvVector( global );
		lvVector  c2     = Gbl().tvGetBCbdHalfEdgeFirstMain[ 2 ];			// c2     = new lvVector( global );
		lvVector  center = Gbl().tvGetBCbdHalfEdgeFirstMain[ 3 ];			// center = new lvVector( global );
		
		lvVector  a0 = upHalfCbd.cbd[ 0 ].aCbd;
		lvVector  a3 = upHalfCbd.cbd[ 1 ].aCbd;
		
		lvDivFaceLow.Position( halfNo, 0.5, center );
		
		c0.Assign( downCbd.info[ 0 ].cVec.Div( 2.0 ) );
		lvDivFaceLow.Derivative( halfNo, 0.5, c2 );
		c2.DivAssign( 6.0 );
		c1.Assign( ( center.Sub( c2 ) ).Sub( downCbd.info[ 0 ].vtx.Add( c0 ) ) );
		
		double  k0 = upHalfCbd.cbd[ 0 ].k;
		double  k1 = upHalfCbd.k1[ 0 ];
		double  k2 = upHalfCbd.cbd[ 1 ].k;
		
		double  h0 = upHalfCbd.cbd[ 0 ].h;
		double  h1 = upHalfCbd.cbd[ 1 ].h;
		
		lvVector  b1 = bCbd[ 0 ];
		lvVector  b2 = bCbd[ 1 ];
		
		b1.Assign( ( a3.Mul( 1.0/3.0 * k0 ) ).Add( a0.Mul( 2.0/3.0 * k1 ) ) );
		b1.AddAssign( ( c1.Mul( 2.0/3.0 * h0 ) ).Add( c0.Mul( 1.0/3.0 * h1 ) ) );
		
		b2.Assign( ( a3.Mul( 2.0/3.0 * k1 ) ).Add( a0.Mul( 1.0/3.0 * k2 ) ) );
		b2.AddAssign( ( c2.Mul( 1.0/3.0 * h0 ) ).Add( c1.Mul( 2.0/3.0 * h1 ) ) );
	}
	
	private final void
	GetBCbdHalfEdgeLast( int halfNo, lvVector bCbd[/*2*/] ) throws lvThrowable
	{
		lvMakeCbd.DownCbd    downCbd   = Gbl().tdGetBCbdHalfEdgeLast[ 0 ];		// downCbd   = lvMakeCbd.DownCbd( global );
		lvMakeCbd.UpHalfCbd  upHalfCbd = Gbl().tuGetBCbdHalfEdgeLast[ 0 ];		// upHalfCbd = lvMakeCbd.UpHalfCbd( global );
		
		SetDownCbdOuter( halfNo, downCbd );
		Gbl().makeCbd.ExecHalf( downCbd, upHalfCbd );
		GetBCbdHalfEdgeLastMain( halfNo, downCbd, upHalfCbd, bCbd );
	}
	
	private final void
	GetBCbdHalfEdgeLastMain( int halfNo, lvMakeCbd.DownCbd downCbd, lvMakeCbd.UpHalfCbd upHalfCbd,
			lvVector bCbd[/*2*/] ) throws lvThrowable
	{
		lvVector  c0     = Gbl().tvGetBCbdHalfEdgeLastMain[ 0 ];			// c0     = new lvVector( global );
		lvVector  c1     = Gbl().tvGetBCbdHalfEdgeLastMain[ 1 ];			// c1     = new lvVector( global );
		lvVector  c2     = Gbl().tvGetBCbdHalfEdgeLastMain[ 2 ];			// c2     = new lvVector( global );
		lvVector  center = Gbl().tvGetBCbdHalfEdgeLastMain[ 3 ];			// center = new lvVector( global );
		
		lvVector  a0 = upHalfCbd.cbd[ 1 ].aCbd;
		lvVector  a3 = upHalfCbd.cbd[ 2 ].aCbd;
		
		lvDivFaceLow.Position( halfNo, 0.5, center );
		
		lvDivFaceLow.Derivative( halfNo, 0.5, c0 );
		c0.DivAssign( 6.0 );
		c2.Assign( downCbd.info[ 1 ].cVec.Div( 2.0 ) );
		c1.Assign( ( downCbd.info[ 1 ].vtx.Sub( c2 ) ).Sub( center.Add( c0 ) ) );
		
		double  k0 = upHalfCbd.cbd[ 1 ].k;
		double  k1 = upHalfCbd.k1[ 1 ];
		double  k2 = upHalfCbd.cbd[ 2 ].k;
		
		double  h0 = upHalfCbd.cbd[ 1 ].h;
		double  h1 = upHalfCbd.cbd[ 2 ].h;
		
		lvVector  b1 = bCbd[ 0 ];
		lvVector  b2 = bCbd[ 1 ];
		
		b1.Assign( ( a3.Mul( 1.0/3.0 * k0 ) ).Add( a0.Mul( 2.0/3.0 * k1 ) ) );
		b1.AddAssign( ( c1.Mul( 2.0/3.0 * h0 ) ).Add( c0.Mul( 1.0/3.0 * h1 ) ) );
		
		b2.Assign( ( a3.Mul( 2.0/3.0 * k1 ) ).Add( a0.Mul( 1.0/3.0 * k2 ) ) );
		b2.AddAssign( ( c2.Mul( 1.0/3.0 * h0 ) ).Add( c1.Mul( 2.0/3.0 * h1 ) ) );
	}
	
	private final void
	Finish()
	{
		UpTessellate().vertex     = null;		// Delete( UpTessellate().vertex );
		UpTessellate().triIndex   = null;		// Delete( UpTessellate().triIndex );
		
		Gbl().tmpTessellate.patch = null;		// Delete( Gbl().tmpTessellate.patch );
	}
	
}
