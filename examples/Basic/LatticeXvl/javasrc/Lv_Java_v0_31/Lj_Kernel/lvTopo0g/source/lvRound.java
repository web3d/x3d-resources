//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvRound.java
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.t0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;
import	jp.co.lattice.vKernel.greg.g0g.lvRoundLow;


/**
 * Round情報の作成クラス（上位レイヤー）
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvRound extends lvRoot {
	
	private static final int  maxNumVtxForCenter = 256;
	private static final int  maxNumHalfEdge	 = 256;

// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo							*/
		private int  curShellNo		= 0;

		/** 下位レイヤー(geom0)オブジェクト				*/
		private lvRoundLow            roundLow		= null;

		/** 下位レイヤー(geom0)に送るデータ				*/
		private lvRoundLow.DownRound  downRound		= null;
		
		/** 下位レイヤー(geom0)から送られるデータ		*/
		private lvRoundLow.UpRound    upRound		= null;
		
		/** static関数用のオブジェクト					*/
		private lvRound               local			= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			roundLow  = new lvRoundLow( dt );
			downRound = new lvRoundLow.DownRound( dt );
			upRound   = new lvRoundLow.UpRound( dt );
			local     = new lvRound( dt );
			
			GlobalTmp( dt );
			GlobalStatic( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvSetPolyCenterPos[]		= null;
		private lvVector  tvSetPolyCenterNormal[]	= null;
		private lvDouble  tdSetPolyCenterNormal[]	= null;
		private lvVector  tvFaceCenterProc[]		= null;
		private lvVector  tvFaceNormalProc[]		= null;
		private lvVector  tvNormalFace[]			= null;
		private lvVector  tvAryPosition[]			= null;
		private lvVector  tvAryDerivative[]			= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvSetPolyCenterPos    = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetPolyCenterPos[ i ]    = new lvVector( dt );
			tvSetPolyCenterNormal = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvSetPolyCenterNormal[ i ] = new lvVector( dt );
			tdSetPolyCenterNormal = new lvDouble[ 2 ];	for( int i=0; i<2; i++ )	tdSetPolyCenterNormal[ i ] = new lvDouble( dt );
			tvFaceCenterProc      = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvFaceCenterProc[ i ]      = new lvVector( dt );
			tvFaceNormalProc      = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvFaceNormalProc[ i ]      = new lvVector( dt );
			tvNormalFace          = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvNormalFace[ i ]          = new lvVector( dt );
			tvAryPosition         = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryPosition[ i ]         = new lvVector( dt );
			tvAryDerivative       = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryDerivative[ i ]       = new lvVector( dt );
		}

		/** 小規模な配列用のグローバルデータ				*/
		private lvVector  svVtxForCenter[]			= null;
		private lvVector  svCtlPoint[]				= null;
		
		/**
		 * 小規模な配列用のグローバルデータの初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalStatic( lvGlobal dt )
		{
			svVtxForCenter = new lvVector[ maxNumVtxForCenter ];
			for( int i=0; i<maxNumVtxForCenter; i++ )
				svVtxForCenter[ i ] = new lvVector( dt );

			svCtlPoint = new lvVector[ maxNumHalfEdge * 3 ];
			for( int i=0; i<( maxNumHalfEdge * 3 ); i++ )
				svCtlPoint[ i ] = new lvVector( dt );
		}
		
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0TopoGGblElm )global.GTopoG() ).gRound;
	}
	/** lvPolygonデータ用クラスオブジェクト		*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** lvPolygonデータ用クラスオブジェクト		*/
	private final lvPolygon
	Polygon( int shellNo )
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ shellNo ].poly;
	}
	/** lvPolyDerive.PolyCenterデータ用クラスオブジェクト	*/
	private final lvPolyDerive.PolyCenter
	PolyCenter()
	{
		return  ( ( lv0ComGGblElm )global.GComG() ).gModelGreg.shell[ Gbl().curShellNo ].center;
	}
	/** lvPolyDerive.Bezgonデータ用クラスオブジェクト	*/
	private final lvPolyDerive.Bezgon
	Bezgon()
	{
		return  ( ( lv0ComGGblElm )global.GComG() ).gModelGreg.shell[ Gbl().curShellNo ].bez;
	}
	/** lvPolyDerive.Bezgonデータ用クラスオブジェクト	*/
	private final lvPolyDerive.Bezgon
	Bezgon( int shellNo )
	{
		return  ( ( lv0ComGGblElm )global.GComG() ).gModelGreg.shell[ shellNo ].bez;
	}
	/** DownRound用のグローバルデータ				*/
	private final lvRoundLow.DownRound
	DownRound()
	{
		return  Gbl().downRound;
	}
	/** UpRound用のグローバルデータ				*/
	private final lvRoundLow.UpRound
	UpRound()
	{
		return  Gbl().upRound;
	}
	/** 当クラス用のグローバルデータ		*/
	private static final lvRound
	Local( lvGlobal gbl )
	{
		return  ( ( lv0TopoGGblElm )gbl.GTopoG() ).gRound.local;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvRound( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1個のシェルを round する
	 * @param  shellNo		(( I )) シェルNo
	 */
	public void
	Exec( int shellNo ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		
		SetPolyCenter();
		
		NewBezgon();
		int  num = GetMaxRadial();
		NewDownRadial( num );
		NewUpRadial( num );
		
		ExecMain();
		
		FaceProc();
		
		Finish();
	}
	
	private final void
	SetPolyCenter() throws lvThrowable
	{
		int  numGS = Polygon().ngStartNo;
		PolyCenter().NewLattice( numGS );
		
		int  numNG = Polygon().face.length - Polygon().ngStartNo;
		PolyCenter().NewNG( numNG );
		
		SetPolyCenterPos();
		SetPolyCenterNormal();
	}
	
	private final void
	SetPolyCenterPos() throws lvThrowable
	{
		lvVector  center = Gbl().tvSetPolyCenterPos[ 0 ];			// center = new lvVector( global );
		lvVector  pos    = Gbl().tvSetPolyCenterPos[ 1 ];			// pos    = new lvVector( global );
		
		int  numGS = Polygon().ngStartNo;
		for( int i=0; i<numGS; i++ ) {
			
			center.SetXYZ( 0.0, 0.0, 0.0 );
			lvRec.SeqPart  face = Polygon().face[ i ];
			
			for( int j=0; j<face.num; j++ ) {
				int  vtxNo = Polygon().faceHalfSeq[ face.start + j ].vtxNo;
				pos.VecDt2Vector( Polygon().vertex[ vtxNo ].pos );
				center.AddAssign( pos );
			}
			center.DivAssign( face.num );
			center.Vector2VecDt( PolyCenter().lattice[ i ].center.pos );
		}
		
		int  numNG = Polygon().face.length - Polygon().ngStartNo;
		for( int i=0; i<numNG; i++ ) {
			
			center.SetXYZ( 0.0, 0.0, 0.0 );
			lvRec.SeqPart  face = Polygon().face[ Polygon().ngStartNo + i ];
			
			for( int j=0; j<face.num; j++ ) {
				int  vtxNo = Polygon().faceHalfSeq[ face.start + j ].vtxNo;
				pos.VecDt2Vector( Polygon().vertex[ vtxNo ].pos );
				center.AddAssign( pos );
			}
			center.DivAssign( face.num );
			center.Vector2VecDt( PolyCenter().centerNG[ i ] );
		}
	}
	
	private final void
	SetPolyCenterNormal() throws lvThrowable
	{
		lvVector  faceNormal = Gbl().tvSetPolyCenterNormal[ 0 ];					// faceNormal = new lvVector( global );
		lvDouble  faceArea   = Gbl().tdSetPolyCenterNormal[ 0 ];					// faceArea   = new lvDouble( global );
		
		int  numFace   = Polygon().ngStartNo;
		int  maxNumVtx = 0;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  face = Polygon().face[ i ];
			if( maxNumVtx < face.num )
				maxNumVtx = face.num;
		}
		
		lvVector  faceVtx[] = NewFaceVtx( maxNumVtx );
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  face = Polygon().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  vtxNo = Polygon().faceHalfSeq[ face.start + j ].vtxNo;
				faceVtx[ j ].VecDt2Vector( Polygon().vertex[ vtxNo ].pos );
			}
			faceNormal.Normal( faceVtx, face.num, faceArea );
			faceNormal.Vector2VecDt( PolyCenter().lattice[ i ].center.normal );
			PolyCenter().lattice[ i ].area = faceArea.val;
		}
	}
	
	private final lvVector[]
	NewFaceVtx( int numVtx )
	{
		lvVector  pos[];
		if( numVtx > maxNumVtxForCenter ) {
			pos = new lvVector[ numVtx ];
			for( int i=0; i<maxNumVtxForCenter; i++ )
				pos[ i ] = Gbl().svVtxForCenter[ i ];
			for( int i=maxNumVtxForCenter; i<numVtx; i++ )
				pos[ i ] = new lvVector( global );
		}
		else
			pos = Gbl().svVtxForCenter;
			
		return  pos;
	}
	
	private final void
	NewBezgon()
	{
		Bezgon().NewVertex( Polygon().vertex.length );
		Bezgon().NewEdge( Polygon().edge.length );
		Bezgon().NewFace( Polygon().ngStartNo );
	}

	private final int
	GetMaxRadial()
	{
		int  max = 0;
		int  num = Polygon().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( max < Polygon().vertex[ i ].vtxFace.num )
				max = Polygon().vertex[ i ].vtxFace.num;
		}
			
		return  max;
	}

	private final void
	NewDownRadial( int num )
	{
		DownRound().radial = new lvRoundLow.DownRadial[ num ];
		for( int i=0; i<num; i++ )
			DownRound().radial[ i ] = new lvRoundLow.DownRadial( global );
	}
	
	private final void
	NewUpRadial( int num )
	{
		UpRound().radial = new lvRoundLow.UpRadial[ num ];
		for( int i=0; i<num; i++ )
			UpRound().radial[ i ] = new lvRoundLow.UpRadial( global );
	}
	
	private final void
	ExecMain() throws lvThrowable
	{
		boolean  init = true;
		int      num  = Polygon().vertex.length;
		for( int i=0; i<num; i++ ) {
			SetDownRound( i );
			Gbl().roundLow.Exec( init, DownRound(), UpRound() );
			SetUpRound( i );
			init = false;
		}
		Gbl().roundLow.Finish();
	}
	
	private final void
	SetDownRound( int vtxNo )
	{
		DownRound().vtxPos.VecDt2Vector( Polygon().vertex[ vtxNo ].pos );
		DownRound().vtxRound   = Polygon().vertex[ vtxNo ].round;
		
		lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
		DownRound().numRadial  = vtxFace.num;
		
		for( int i=0; i<vtxFace.num; i++ ) {
			lvPolygon.InfoFaceHalf  vtxFaceSeq   = Polygon().vtxFaceSeq[ vtxFace.start + i ];
			lvRec.SeqPart           face         = Polygon().face[ vtxFaceSeq.faceNo ];
			lvPolygon.FaceHalf	    faceHalfSeq  = Polygon().faceHalfSeq[ face.start + vtxFaceSeq.halfNo ];
			
			int                     halfF        = ( vtxFaceSeq.halfNo + 1 ) % face.num;
			lvPolygon.FaceHalf	    faceHalfSeqF = Polygon().faceHalfSeq[ face.start + halfF ];
			lvVector                mateVtxPos   = DownRound().radial[ i ].mateVtxPos;
			mateVtxPos.VecDt2Vector( Polygon().vertex[ faceHalfSeqF.vtxNo ].pos );
		
			DownRound().radial[ i ].edgeRound  = Polygon().edge[ faceHalfSeq.edgeNo ].allRound;
			lvVector  roundVec = DownRound().radial[ i ].roundVec;
			roundVec.VecDt2Vector( Polygon().edge[ faceHalfSeq.edgeNo ].vec[ faceHalfSeq.edgeIdx ] );
			
			if( vtxFaceSeq.faceNo >= Polygon().ngStartNo ) {
				DownRound().radial[ i ].isNG = true;
				lvVector  pos = DownRound().radial[ i ].centerNG;
				pos.VecDt2Vector( PolyCenter().centerNG[ vtxFaceSeq.faceNo - Polygon().ngStartNo ] );
			}
			else {
				DownRound().radial[ i ].isNG = false;
				lvVector  pos    = DownRound().radial[ i ].center.pos;
				pos.VecDt2Vector( PolyCenter().lattice[ vtxFaceSeq.faceNo ].center.pos );
				lvVector  normal = DownRound().radial[ i ].center.normal;
				normal.VecDt2Vector( PolyCenter().lattice[ vtxFaceSeq.faceNo ].center.normal );
				DownRound().radial[ i ].area = PolyCenter().lattice[ vtxFaceSeq.faceNo ].area;
			}
			
			DownRound().radial[ i ].numINode = GetNumINode( face, vtxFaceSeq.halfNo );
		}
	}

	private final int
	GetNumINode( lvRec.SeqPart face, int halfNoOrg )
	{
		int  numINode = 1;
		for( int i=1; i<face.num; i++ ) {
			int  halfNo    = ( halfNoOrg + i ) % face.num;
			int  vtxNo     = Polygon().faceHalfSeq[ face.start + halfNo ].vtxNo;
			int  numRadial = Polygon().vertex[ vtxNo ].vtxFace.num;
			if( numRadial == 2 )
				numINode++;
			else
				break;
		}
		
		return  numINode;
	}

	private final void
	SetUpRound( int vtxNo )
	{
		lvRec.SeqPart  vtxFace = Polygon().vertex[ vtxNo ].vtxFace;
		
		UpRound().vtxPos.Vector2VecDt( Bezgon().vertex[ vtxNo ].pos );

		for( int i=0; i<vtxFace.num; i++ ) {
			lvPolygon.InfoFaceHalf  vtxFaceSeq   = Polygon().vtxFaceSeq[ vtxFace.start + i ];
			lvRec.SeqPart           face         = Polygon().face[ vtxFaceSeq.faceNo ];
			lvPolygon.FaceHalf	    faceHalfSeq  = Polygon().faceHalfSeq[ face.start + vtxFaceSeq.halfNo ];
			
			lvVector  handVec = UpRound().radial[ i ].handVec;
			handVec.Vector2VecDt( Bezgon().edge[ faceHalfSeq.edgeNo ].handVec[ faceHalfSeq.edgeIdx ] );
		}
	}
	
	private final void
	FaceProc() throws lvThrowable
	{
		FaceCenterProc();
		FaceNormalProc();
	}
	
	private final void
	FaceCenterProc() throws lvThrowable
	{
		lvVector  center = Gbl().tvFaceCenterProc[ 0 ];			// center = new lvVector( global );
		lvVector  pos    = Gbl().tvFaceCenterProc[ 1 ];			// pos    = new lvVector( global );
		
		int  numFace = Polygon().ngStartNo;
		for( int i=0; i<numFace; i++ ) {
			
			center.SetXYZ( 0.0, 0.0, 0.0 );
			lvRec.SeqPart  face = Polygon().face[ i ];
			
			for( int j=0; j<face.num; j++ ) {
				lvPolygon.FaceHalf	faceHalf  = Polygon().faceHalfSeq[ face.start + j ];
				pos.VecDt2Vector( Bezgon().vertex[ faceHalf.vtxNo ].pos );
				center.AddAssign( pos );
			}
			center.DivAssign( face.num );
			center.Vector2VecDt( Bezgon().face[ i ].center.pos );
		}
	}
	
	private final void
	FaceNormalProc() throws lvThrowable
	{
		lvVector  normal = Gbl().tvFaceNormalProc[ 0 ];			// normal = new lvVector( global );

		int  numFace = Polygon().ngStartNo;
		for( int i=0; i<numFace; i++ ) {
			NormalFace( i, normal );
			normal.Vector2VecDt( Bezgon().face[ i ].center.normal );
		}
	}
	
	private final void
	NormalFace( int faceNo, lvVector normal ) throws lvThrowable
	{
		lvVector  posF = Gbl().tvNormalFace[ 0 ];				// posF = new lvVector( global );

		lvRec.SeqPart  polyFace = Polygon().face[ faceNo ];
		lvVector  ctlPnt[] = NewCtlPoint( polyFace.num * 3 );
		
		for( int i=0; i<polyFace.num; i++ ) {
			int  nf = ( i + 1 ) % polyFace.num;
			lvPolygon.FaceHalf	faceHalf  = Polygon().faceHalfSeq[ polyFace.start + i  ];
			lvPolygon.FaceHalf	faceHalfF = Polygon().faceHalfSeq[ polyFace.start + nf ];
			
			ctlPnt[ i*3 + 0 ].VecDt2Vector( Bezgon().vertex[ faceHalf.vtxNo ].pos );
			ctlPnt[ i*3 + 1 ].VecDt2Vector( Bezgon().edge[ faceHalf.edgeNo ].handVec[ faceHalf.edgeIdx	   ] );
			ctlPnt[ i*3 + 2 ].VecDt2Vector( Bezgon().edge[ faceHalf.edgeNo ].handVec[ 1 - faceHalf.edgeIdx ] );
			posF.VecDt2Vector( Bezgon().vertex[ faceHalfF.vtxNo ].pos );
			
			ctlPnt[ i*3 + 1 ].AddAssign( ctlPnt[ i*3 + 0 ] );
			ctlPnt[ i*3 + 2 ].AddAssign( posF );
		}
		
		lvBezLine.NormalBound( ctlPnt, polyFace.num * 3, normal );
	}
	
	private final lvVector[]
	NewCtlPoint( int num )
	{
		lvVector  ctlPoint[];
		
		if( num > ( maxNumHalfEdge * 3 ) ) {
			ctlPoint = new lvVector[ num ];
			for( int i=0; i<( maxNumHalfEdge * 3 ); i++ )
				ctlPoint[ i ] = Gbl().svCtlPoint[ i ];
			for( int i=( maxNumHalfEdge * 3 ); i<num; i++ )
				ctlPoint[ i ] = new lvVector( global );
		}
		else
			ctlPoint = Gbl().svCtlPoint;
			
		return	ctlPoint;
	}
	
	/**
	 * t値でのベジェ曲線上の位置を求める
	 * @param  edgeNo	(( I )) 稜線No
	 * @param  edgeIdx	(( I )) 0:稜線の始点、1:稜線の終点
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 位置
	 */
	public static final void
	Position( int shellNo, int edgeNo, int edgeIdx, double t, lvVector result )
	{
		lvGlobal  gbl = result.global;
		Local( gbl ).PositionLocal( shellNo, edgeNo, edgeIdx, t, result );
	}
	
	/**
	 * t値でのベジェ曲線上の位置を求める（ローカル）
	 * @param  edgeNo	(( I )) 稜線No
	 * @param  edgeIdx	(( I )) 0:稜線の始点、1:稜線の終点
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 位置
	 */
	private final void
	PositionLocal( int shellNo, int edgeNo, int edgeIdx, double t, lvVector result )
	{
		lvVector  ctlPnt[/*4*/] = Gbl().tvAryPosition;					// ctlPnt[] = new lvVector[ 4 ];
		
		lvPolygon.InfoFaceHalf  faceHalf0 = Polygon( shellNo ).edge[ edgeNo ].face[ edgeIdx ];
		lvRec.SeqPart  polyFace0 = Polygon( shellNo ).face[ faceHalf0.faceNo ];
		int            vtx0      = Polygon( shellNo ).faceHalfSeq[ polyFace0.start + faceHalf0.halfNo ].vtxNo;
		lvPolygon.InfoFaceHalf  faceHalf1 = Polygon( shellNo ).edge[ edgeNo ].face[ 1 - edgeIdx ];
		lvRec.SeqPart  polyFace1 = Polygon( shellNo ).face[ faceHalf1.faceNo ];
		int            vtx1      = Polygon( shellNo ).faceHalfSeq[ polyFace1.start + faceHalf1.halfNo ].vtxNo;
		
		ctlPnt[ 0 ].VecDt2Vector( Bezgon( shellNo ).vertex[ vtx0 ].pos );
		ctlPnt[ 1 ].VecDt2Vector( Bezgon( shellNo ).edge[ edgeNo ].handVec[ edgeIdx	   ] );
		ctlPnt[ 2 ].VecDt2Vector( Bezgon( shellNo ).edge[ edgeNo ].handVec[ 1 - edgeIdx ] );
		ctlPnt[ 3 ].VecDt2Vector( Bezgon( shellNo ).vertex[ vtx1 ].pos );
			
		ctlPnt[ 1 ].AddAssign( ctlPnt[ 0 ] );
		ctlPnt[ 2 ].AddAssign( ctlPnt[ 3 ] );
		
		lvBezLine.Position( ctlPnt, t, result );
	}

	/**
	 * t値でのベジェ曲線上の微分ベクトルを求める
	 * @param  edgeNo	(( I )) 稜線No
	 * @param  edgeIdx	(( I )) 0:稜線の始点、1:稜線の終点
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 微分ベクトル
	 */
/*
	public static final void
	Derivative( int shellNo, int edgeNo, int edgeIdx, double t, lvVector result )
	{
		lvGlobal  gbl = result.global;
		Local( gbl ).DerivativeLocal( shellNo, edgeNo, edgeIdx, t, result );
	}
*/

	/**
	 * t値でのベジェ曲線上の微分ベクトルを求める（ローカル）
	 * @param  edgeNo	(( I )) 稜線No
	 * @param  edgeIdx	(( I )) 0:稜線の始点、1:稜線の終点
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 微分ベクトル
	 */
/*
	private final void
	DerivativeLocal( int shellNo, int edgeNo, int edgeIdx, double t, lvVector result )
	{
*///	lvVector  ctlPnt[/*4*/] = Gbl().tvAryDerivative;					// ctlPnt[] = new lvVector[ 4 ];
/*		
		lvPolygon.InfoFaceHalf  faceHalf0 = Polygon( shellNo ).edge[ edgeNo ].face[ edgeIdx ];
		lvRec.SeqPart  polyFace0 = Polygon( shellNo ).face[ faceHalf0.faceNo ];
		int            vtx0      = Polygon( shellNo ).faceHalfSeq[ polyFace0.start + faceHalf0.halfNo ].vtxNo;
		lvPolygon.InfoFaceHalf  faceHalf1 = Polygon( shellNo ).edge[ edgeNo ].face[ 1 - edgeIdx ];
		lvRec.SeqPart  polyFace1 = Polygon( shellNo ).face[ faceHalf1.faceNo ];
		int            vtx1      = Polygon( shellNo ).faceHalfSeq[ polyFace1.start + faceHalf1.halfNo ].vtxNo;
		
		ctlPnt[ 0 ].VecDt2Vector( Bezgon( shellNo ).vertex[ vtx0 ].pos );
		ctlPnt[ 1 ].VecDt2Vector( Bezgon( shellNo ).edge[ edgeNo ].handVec[ edgeIdx	   ] );
		ctlPnt[ 2 ].VecDt2Vector( Bezgon( shellNo ).edge[ edgeNo ].handVec[ 1 - edgeIdx ] );
		ctlPnt[ 3 ].VecDt2Vector( Bezgon( shellNo ).vertex[ vtx1 ].pos );
			
		ctlPnt[ 1 ].AddAssign( ctlPnt[ 0 ] );
		ctlPnt[ 2 ].AddAssign( ctlPnt[ 3 ] );
		
		lvBezLine.Derivative( ctlPnt, t, result );
	}
*/

	private final void
	Finish()
	{
		DownRound().radial = null;		// Delete( DownRound().radial );
		UpRound().radial   = null;		// Delete( UpRound().radial );
	}
	
}
