//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivPolyLow.java
//

package jp.co.lattice.vKernel.core.g0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.a0.lvDivPolyUV;
import	jp.co.lattice.vKernel.tex.a0.lv0DivPolyUV;


/**
 * DivPoly情報の作成クラス（下位レイヤー）
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDivPolyLow extends lvRoot {

	private static final int  maxNumFaceVtx = 256;
		
// -------------------------------------------------------------------
	
	/**
	 * 上位レイヤー(topo0)から送られるデータ用（DownDivPoly）の内部クラス
	 */
	public static class DownDivPoly {
		
		/** vtxPos[] の有効配列数「 3以上 」	*/
		public int	     numVtxPos;
		
		/** 頂点の座標の配列（1個のポリゴンの頂点数以上の長さで存在する「 3以上 」） --- 初期値 null		*/
		public lvVector  vtxPos[]	= null;
		
		/** * UV空間情報	*/
		public lvDivPolyUV.DownDivPolyUV  divPolyUV = new lv0DivPolyUV.DownDivPolyUV();
	}
		
	/**
	 * 上位レイヤー(topo0)に送るデータ用（UpDivPoly）の内部クラス
	 */
	public static class UpDivPoly {
		
		/** vertex[] の有効配列数「 3以上 」	*/
		public int	numVertex;
		/**
		 * 頂点座標の配列（頂点の数の MAX数だけ存在する「 3以上 」） --- 初期値 null									<br>
		 * n角形の MAX数 --- nが奇数の時: n + ( n - 3 ) / 2,	nが偶数の時: n + ( n - 2 ) / 2
		 */
		public lvRec.PosNorHi  vertex[]			= null;
	
		/** triIndex[] の有効配列数「 1以上 」	*/
		public int	numTriIndex;
		/**
		 * 3頂点No.Indexから成る三角ポリゴンの配列（三角ポリゴンの数の MAX数だけだけ存在する「 1以上 」） --- 初期値 null	<br>
		 * n角形の MAX数 --- nが奇数の時: 2 * ( n - 3 ) + 1,	nが偶数の時: 2 * ( n - 2 )
		 */
		public lvRec.TriIndex  triIndex[]		= null;
		
		/** * UV空間情報	*/
		public lvDivPolyUV.UpDivPolyUV  divPolyUV = new lv0DivPolyUV.UpDivPolyUV();
	}

// -------------------------------------------------------------------

	/**
	 * 面情報に対する一時的内部クラス（TmpDivPoly用）
	 */
	private static class TmpFace {
		
		/** TmpDivPoly.faceHalfSeq 用の面情報		*/
		private lvRec.SeqPart  info			= new lvRec.SeqPart();
		
		/** その面に対して、分割処理を終了したか？	未終了:false, 終了:true		*/
		private boolean	   finish;
	}
	
	/**
	 * UpDivPoly情報の作成の際、一時的に使用する情報のための内部クラス
	 */
	private static class TmpDivPoly {
		
		/** face[] の有効長さ		*/
		private int	 numFace;
		
		/** 面情報の配列（ n角形の場合、長さは「 n - 2 」） --- 初期値 null			*/
		private TmpFace  face[]			= null;
		
		/**
		 * 面がn個ある場合、																<br>
		 * 面0のハーフエッジNo列, 面1のハーフエッジNo列, ---, 面( n-1 )のハーフエッジNo列	<br>
		 * と続く。配列の長さは、															<br>
		 * 面0のハーフエッジ数 + 面1のハーフエッジ数 + --- + 面( n-1 )のハーフエッジ数		<br>
		 * となる。（ n角形の場合、長さは「 3 * ( n - 2 ) 」） --- 初期値 null
		 */
		private int	 faceHalfSeq[]		= null;
	}
	
	/**
	 * UpDivPoly情報の作成の際、一時的に使用する情報のための内部クラス
	 */
	private static class TmpDivPolyOne {
		
		/** この面のハーフエッジ数		*/
		private int  numHalf;
		
		/** この面のハーフエッジNo列（ n角形の場合、長さは n ） --- 初期値 null		*/
		private int  faceHalfSeq[]		= null;
	}
	
	/**
	 * UpDivPoly情報の作成の際、一時的に使用する情報のための内部クラス
	 */
	private static class TmpDivPolyOther {

		/** 現在、作業中の面No				*/
		private int	      curFaceNo;
		
		/** 面法線							*/
		private lvVector  faceNormal	= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public	TmpDivPolyOther( lvGlobal dt )
		{
			faceNormal = new lvVector( dt );
		}
	}
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** カレントの「上位レイヤー(topo0)から送られるデータ」		*/
		private DownDivPoly    curDownDivPoly		= new DownDivPoly();
		/** カレントの「上位レイヤー(topo0)に送るデータ」			*/
		private UpDivPoly	   curUpDivPoly			= new UpDivPoly();

		/** 小規模な curUpDivPoly 用のグローバルデータ				*/
		private UpDivPoly	   staticUp				= new UpDivPoly();

		// -------------------------------------------------------------------

		/** すべての分割面に対する TmpDivPoly情報					*/
		private TmpDivPoly		 allDivPoly			= new TmpDivPoly();
		/** allDivPoly の再構築用									*/
		private TmpDivPoly		 tmpDivPoly			= new TmpDivPoly();
		
		/** 1個の入力分割面情報										*/
		private TmpDivPolyOne	 srcDivPoly			= new TmpDivPolyOne();
		/** 1個の出力分割面情報0									*/
		private TmpDivPolyOne	 dstDivPoly0		= new TmpDivPolyOne();
		/** 1個の出力分割面情報1									*/
		private TmpDivPolyOne	 dstDivPoly1		= new TmpDivPolyOne();

		/** 小規模な allDivPoly 用のグローバルデータ				*/
		private TmpDivPoly		 staticAll			= new TmpDivPoly();
		/** 小規模な tmpDivPoly 用のグローバルデータ				*/
		private TmpDivPoly		 staticTmp			= new TmpDivPoly();

		/** 小規模な srcDivPoly 用のグローバルデータ				*/
		private TmpDivPolyOne	 staticSrc			= new TmpDivPolyOne();
		/** 小規模な dstDivPoly0 用のグローバルデータ				*/
		private TmpDivPolyOne	 staticDst0			= new TmpDivPolyOne();
		/** 小規模な dstDivPoly1 用のグローバルデータ				*/
		private TmpDivPolyOne	 staticDst1			= new TmpDivPolyOne();
		
		/** その他の一時的情報										*/
		private TmpDivPolyOther  tmpDt				= null;
		
		// -------------------------------------------------------------------

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public	Global( lvGlobal dt )
		{
			GlobalIO( dt );
			GlobalTmpFace();
			GlobalTmpFaceOne();
			
			tmpDt = new TmpDivPolyOther( dt );
			
			GlobalTmp( dt );
		}

		private final void
		GlobalIO( lvGlobal dt )
		{
			int  numVertex = GetNumVertex( maxNumFaceVtx );
			staticUp.vertex = new lvRec.PosNorHi[ numVertex ];
			for( int i=0; i<numVertex; i++ )
				staticUp.vertex[ i ] = new lvRec.PosNorHi( dt );

			int  numTriIndex = GetNumTriIndex( maxNumFaceVtx );
			staticUp.triIndex = new lvRec.TriIndex[ numTriIndex ];
			for( int i=0; i<numTriIndex; i++ )
				staticUp.triIndex[ i ] = new lvRec.TriIndex();
				
			staticUp.divPolyUV.uvSpace      = new lv0DivPolyUV.UpDivPolyUVone[ 1 ];
			staticUp.divPolyUV.uvSpace[ 0 ] = new lv0DivPolyUV.UpDivPolyUVone();
			staticUp.divPolyUV.uvSpace[ 0 ].vertex = new lvUVdt[ numVertex ];
			for( int i=0; i<numVertex; i++ )
				staticUp.divPolyUV.uvSpace[ 0 ].vertex[ i ] = new lvUVdt();
		}
		
		private final void
		GlobalTmpFace()
		{
			int  numFace = GetNumFace( maxNumFaceVtx );
			staticAll.face = new TmpFace[ numFace ];
			for( int i=0; i<numFace; i++ )
				staticAll.face[ i ] = new TmpFace();
			staticTmp.face = new TmpFace[ numFace ];
			for( int i=0; i<numFace; i++ )
				staticTmp.face[ i ] = new TmpFace();
				
			int  numFaceHalfSeq = GetNumFaceHalfSeq( maxNumFaceVtx );
			staticAll.faceHalfSeq = new int[ numFaceHalfSeq ];
			staticTmp.faceHalfSeq = new int[ numFaceHalfSeq ];
		}
		
		private final void
		GlobalTmpFaceOne()
		{
			staticSrc.faceHalfSeq  = new int[ maxNumFaceVtx ];
			staticDst0.faceHalfSeq = new int[ maxNumFaceVtx ];
			staticDst1.faceHalfSeq = new int[ maxNumFaceVtx ];
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private lvType.IntR     tiDivVtxToVtx[]			= null;
		private lvVector	    tvAryGetCenterVtxPos[] 	= new lvVector[ maxNumFaceVtx ];
		private lvType.IntR     tiDivVtxToVtx4[]		= null;
		private lvVector        tvSearchConcave[]		= null;
		private lvType.IntR     tiDivVtxToVtxOver4[]	= null;
		private lvRec.CoordSys  tcSearchMateVtxMain[]	= null;
		private lvVector        tvMakeCoordSys[]		= null;
		private lvVector        tvSearchMateVtxOne[]	= null;
		private lvDouble        tdSearchMateVtxOne[]	= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tiDivVtxToVtx       = new lvType.IntR[ 2 ];		for( int i=0; i<2; i++ ) tiDivVtxToVtx[ i ]       = new lvType.IntR();
			tiDivVtxToVtx4      = new lvType.IntR[ 2 ];		for( int i=0; i<2; i++ ) tiDivVtxToVtx4[ i ]      = new lvType.IntR();
			tvSearchConcave     = new lvVector[ 4 ];		for( int i=0; i<4; i++ ) tvSearchConcave[ i ]     = new lvVector( dt );
			tiDivVtxToVtxOver4  = new lvType.IntR[ 4 ];		for( int i=0; i<4; i++ ) tiDivVtxToVtxOver4[ i ]  = new lvType.IntR();
			tcSearchMateVtxMain = new lvRec.CoordSys[ 2 ];	for( int i=0; i<2; i++ ) tcSearchMateVtxMain[ i ] = new lvRec.CoordSys( dt );
			tvMakeCoordSys      = new lvVector[ 2 ];		for( int i=0; i<2; i++ ) tvMakeCoordSys[ i ]      = new lvVector( dt );
			tvSearchMateVtxOne  = new lvVector[ 4 ];		for( int i=0; i<4; i++ ) tvSearchMateVtxOne[ i ]  = new lvVector( dt );
			tdSearchMateVtxOne  = new lvDouble[ 2 ];		for( int i=0; i<2; i++ ) tdSearchMateVtxOne[ i ]  = new lvDouble( dt );
		}

		/**
		 * GetCenterVtxPos() 内のローカル配列 vtxPos[] 用の new 代用バッファ初期化
		 * @param  num		(( I )) 配列長
		 * @return					lvVector配列
		*/
		private final lvVector[]
		NewGetCenterVtxPos( int num )
		{
			if( num > maxNumFaceVtx )
				return	new lvVector[ num ];
			else
				return	tvAryGetCenterVtxPos;
		}
	}

// -------------------------------------------------------------------

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return	( ( lvGeomGblElm )global.GGeom() ).gDivPolyLow;
	}
	/** DownDivPoly用のグローバルデータ				*/
	private final DownDivPoly
	DownDivPoly()
	{
		return	Gbl().curDownDivPoly;
	}
	/** UpRound用のグローバルデータ				*/
	private final UpDivPoly
	UpDivPoly()
	{
		return	Gbl().curUpDivPoly;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvDivPolyLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1個のポリゴンをテセレーションする
	 * @param  downDivPoly		(( I )) 上位レイヤー(topo0)から送られるデータ
	 * @param  upDivPoly		(( O )) 上位レイヤー(topo0)に送るデータ
	 */
	public final void
	Exec( DownDivPoly downDivPoly, UpDivPoly upDivPoly ) throws lvThrowable
	{
		Gbl().curDownDivPoly = downDivPoly;
		Gbl().curUpDivPoly	 = upDivPoly;
		
		NewProc( DownDivPoly().numVtxPos );
		Init();
		
		DivVtxToVtx();
		DivRadial();
		
		Finish();
	}
	
	private static final int
	GetNumVertex( int numFaceVtx )
	{
		if( ( numFaceVtx % 2 ) != 0 )
			return	numFaceVtx + ( numFaceVtx - 3 ) / 2;
		else
			return	numFaceVtx + ( numFaceVtx - 2 ) / 2;
	}
	
	private static final int
	GetNumTriIndex( int numFaceVtx )
	{
		if( ( numFaceVtx % 2 ) != 0 )
			return	2 * ( numFaceVtx - 3 ) + 1;
		else
			return	2 * ( numFaceVtx - 2 );
	}
	
	private static final int
	GetNumFace( int numFaceVtx )
	{
		return	numFaceVtx - 2;
	}
	
	private static final int
	GetNumFaceHalfSeq( int numFaceVtx )
	{
		return	3 * ( numFaceVtx - 2 );
	}
	
	private final void
	NewProc( int num )
	{
		NewUpVertex( num );
		NewUpTriIndex( num );
		NewUpVtxUV( num );
		
		NewTmp( num, Gbl().staticAll, Gbl().allDivPoly );
		NewTmp( num, Gbl().staticTmp, Gbl().tmpDivPoly );
		NewTmpOne( num, Gbl().staticSrc, Gbl().srcDivPoly );
		NewTmpOne( num, Gbl().staticDst0, Gbl().dstDivPoly0 );
		NewTmpOne( num, Gbl().staticDst1, Gbl().dstDivPoly1 );
	}
	
	private final void
	NewUpVertex( int num )
	{
		int  numVertex	  = GetNumVertex( num );
		int  maxNumVertex = GetNumVertex( maxNumFaceVtx );
		
		if( num > maxNumFaceVtx ) {
			UpDivPoly().vertex = new lvRec.PosNorHi[ numVertex ];
			for( int i=0; i<maxNumVertex; i++ )
				UpDivPoly().vertex[ i ] = Gbl().staticUp.vertex[ i ];
			for( int i=maxNumVertex; i<numVertex; i++ )
				UpDivPoly().vertex[ i ] = new lvRec.PosNorHi( global );
		}
		else
			UpDivPoly().vertex = Gbl().staticUp.vertex;
	}
	
	private final void
	NewUpTriIndex( int num )
	{
		int  numTriIndex	= GetNumTriIndex( num );
		int  maxNumTriIndex = GetNumTriIndex( maxNumFaceVtx );
		
		if( num > maxNumFaceVtx ) {
			UpDivPoly().triIndex = new lvRec.TriIndex[ numTriIndex ];
			for( int i=0; i<maxNumTriIndex; i++ )
				UpDivPoly().triIndex[ i ] = Gbl().staticUp.triIndex[ i ];
			for( int i=maxNumTriIndex; i<numTriIndex; i++ )
				UpDivPoly().triIndex[ i ] = new lvRec.TriIndex();
		}
		else
			UpDivPoly().triIndex = Gbl().staticUp.triIndex;
	}
	
	private final void
	NewTmp( int num, TmpDivPoly staticDivPoly, TmpDivPoly divPoly )
	{
		NewTmpFace( num, staticDivPoly, divPoly );
		NewTmpFaceHalfSeq( num, staticDivPoly, divPoly );
	}
	
	private final void
	NewTmpFace( int num, TmpDivPoly staticDivPoly, TmpDivPoly divPoly )
	{
		int  numFace	= GetNumFace( num );
		int  maxNumFace = GetNumFace( maxNumFaceVtx );
		
		if( num > maxNumFaceVtx ) {
			divPoly.face = new TmpFace[ numFace ];
			for( int i=0; i<maxNumFace; i++ )
				divPoly.face[ i ] = staticDivPoly.face[ i ];
			for( int i=maxNumFace; i<numFace; i++ )
				divPoly.face[ i ] = new TmpFace();
		}
		else
			divPoly.face = staticDivPoly.face;
	}
	
	private final void
	NewTmpFaceHalfSeq( int num, TmpDivPoly staticDivPoly, TmpDivPoly divPoly )
	{
		int  numFaceHalfSeq = GetNumFaceHalfSeq( num );
		
		if( num > maxNumFaceVtx )
			divPoly.faceHalfSeq = new int[ numFaceHalfSeq ];
		else
			divPoly.faceHalfSeq = staticDivPoly.faceHalfSeq;
	}
	
	private final void
	NewTmpOne( int num, TmpDivPolyOne staticDivPoly, TmpDivPolyOne divPoly )
	{
		if( num > maxNumFaceVtx )
			divPoly.faceHalfSeq = new int[ num ];
		else
			divPoly.faceHalfSeq = staticDivPoly.faceHalfSeq;
	}
	
	private final void
	Init() throws lvThrowable
	{
		Gbl().allDivPoly.numFace = 1;
		
		Gbl().allDivPoly.face[ 0 ].info.start = 0;
		Gbl().allDivPoly.face[ 0 ].info.num   = DownDivPoly().numVtxPos;
		Gbl().allDivPoly.face[ 0 ].finish	  = false;
		
		for( int i=0; i<DownDivPoly().numVtxPos; i++ )
			Gbl().allDivPoly.faceHalfSeq[ i ] = i;
			
		SetFaceNormal();
	}

	private final void
	SetFaceNormal() throws lvThrowable
	{
		Gbl().tmpDt.faceNormal.Normal( DownDivPoly().vtxPos, DownDivPoly().numVtxPos );
	}

	private final void
	DivVtxToVtx() throws lvThrowable
	{
		lvType.IntR  faceNo = Gbl().tiDivVtxToVtx[ 0 ];			// faceNo = new lvType.IntR();
		
		while( ChkAllFinish( faceNo ) == false ) {
			Gbl().tmpDt.curFaceNo = faceNo.val;
			SetSrcDivPoly();
			boolean  needConv = DivVtxToVtxMain();
			if( needConv == true )
				ConvAllDivPoly();
			else
				Gbl().allDivPoly.face[ Gbl().tmpDt.curFaceNo ].finish = true;
		}
	}
	
	private final boolean
	ChkAllFinish( lvType.IntR faceNo )
	{
		boolean  finish = true;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			if( Gbl().allDivPoly.face[ i ].finish == false ) {
				finish	   = false;
				faceNo.val = i;
				break;
			}
		}
		return	finish;
	}
	
	private final void
	SetSrcDivPoly()
	{
		lvRec.SeqPart  curFace = Gbl().allDivPoly.face[ Gbl().tmpDt.curFaceNo ].info;
		Gbl().srcDivPoly.numHalf = curFace.num;
		
		for( int i=0; i<curFace.num; i++ )
			Gbl().srcDivPoly.faceHalfSeq[ i ] = Gbl().allDivPoly.faceHalfSeq[ curFace.start + i ];
	}
	
	private final boolean
	DivVtxToVtxMain() throws lvThrowable
	{
		if( Gbl().srcDivPoly.numHalf <= 3 )
			return	false;
			
		if( Gbl().srcDivPoly.numHalf == 4 )
			return	DivVtxToVtx4();
		else
			return	DivVtxToVtxOver4();
	}
	
	private final boolean
	DivVtxToVtx4() throws lvThrowable
	{
		lvType.IntR  vtxIdx = Gbl().tiDivVtxToVtx4[ 0 ];			// vtxIdx = new lvType.IntR();
		
		boolean  hasConcave = SearchConcave( vtxIdx );
		if( hasConcave == false )
			return	false;
			
		Gbl().dstDivPoly0.numHalf = 3;
		Gbl().dstDivPoly1.numHalf = 3;
		
		int  vtxNo0 = vtxIdx.val;
		
		Gbl().dstDivPoly0.faceHalfSeq[ 0 ] = vtxNo0;
		Gbl().dstDivPoly0.faceHalfSeq[ 1 ] = ( vtxNo0 + 2 ) % 4;
		Gbl().dstDivPoly0.faceHalfSeq[ 2 ] = ( vtxNo0 + 3 ) % 4;
		
		Gbl().dstDivPoly1.faceHalfSeq[ 0 ] = vtxNo0;
		Gbl().dstDivPoly1.faceHalfSeq[ 1 ] = ( vtxNo0 + 1 ) % 4;
		Gbl().dstDivPoly1.faceHalfSeq[ 2 ] = ( vtxNo0 + 2 ) % 4;
		
		return	true;
	}
	
	private final boolean
	SearchConcave( lvType.IntR concaveVtxIdx ) throws lvThrowable
	{
		lvVector  vecIn	      = Gbl().tvSearchConcave[ 0 ];			// vecIn	   = new lvVector( global );
		lvVector  vecOut	  = Gbl().tvSearchConcave[ 1 ];			// vecOut	   = new lvVector( global );
		lvVector  localNormal = Gbl().tvSearchConcave[ 2 ];			// localNormal = new lvVector( global );
		
		boolean  hasConcave = false;
		
		for( int i=0; i<Gbl().srcDivPoly.numHalf; i++ ) {
			int  nb = ( i + Gbl().srcDivPoly.numHalf - 1 ) % Gbl().srcDivPoly.numHalf;
			int  nf = ( i + 1 ) % Gbl().srcDivPoly.numHalf;
			
			int  vtxNo = Gbl().srcDivPoly.faceHalfSeq[ i  ];
			int  vtxB  = Gbl().srcDivPoly.faceHalfSeq[ nb ];
			int  vtxF  = Gbl().srcDivPoly.faceHalfSeq[ nf ];
			
			lvVector  vtxPos  = DownDivPoly().vtxPos[ vtxNo ];
			lvVector  vtxPosB = DownDivPoly().vtxPos[ vtxB  ];
			lvVector  vtxPosF = DownDivPoly().vtxPos[ vtxF  ];
			
			vecIn.Assign(  ( vtxPos.Sub( vtxPosB ) ).Unit() );
							// vecIn  = ( vtxPos - vtxPosB ).Unit();
			if( vecIn.IsZero() == true )
				continue;
			vecOut.Assign( ( vtxPosF.Sub( vtxPos ) ).Unit() );
							// vecOut = ( vtxPosF - vtxPos ).Unit();
			if( vecOut.IsZero() == true )
				continue;
				
			if( vtxPos.AngleStatus( vtxPosB, vtxPosF, lvEps.t1 ) == lvVector.LV_ANGSTAT_G1 )
				continue;
			
			localNormal.Assign( ( ( vecOut.Cross( vecIn ) ).Neg() ).Unit() );	// localNormal = -( vecOut % vecIn ).Unit();
			
					// Gbl().tmpDt.faceNormal.Dot( localNormal ) --- Gbl().tmpDt.faceNormal * localNormal
			if( Eps().IsNega( Gbl().tmpDt.faceNormal.Dot( localNormal ), lvEps.a0 ) == true ) {
				hasConcave        = true;
				concaveVtxIdx.val = i;
				break;
			}
		}
		
		return	hasConcave;
	}
	
	private final boolean
	DivVtxToVtxOver4() throws lvThrowable
	{
		lvType.IntR  vtxIdx     = Gbl().tiDivVtxToVtxOver4[ 0 ];		// vtxIdx     = new lvType.IntR();
		lvType.IntR  mateVtxIdx = Gbl().tiDivVtxToVtxOver4[ 1 ];		// mateVtxIdx = new lvType.IntR();
		
		boolean  hasConcave = SearchConcave( vtxIdx );
		if( hasConcave == false )
			return	false;
		
		boolean  hasMate = SearchMateVtx( vtxIdx.val, mateVtxIdx );
		if( hasMate == false )
			mateVtxIdx.val = ( vtxIdx.val + 2 ) % Gbl().srcDivPoly.numHalf;
		Err().Assert( ( vtxIdx.val != mateVtxIdx.val ), "lvDivPolyLow.DivVtxToVtxOver4(0)" );

		DivVtxToVtxOver4Dst0( vtxIdx.val, mateVtxIdx.val );
		DivVtxToVtxOver4Dst1( vtxIdx.val, mateVtxIdx.val );
		
		return	true;
	}
	
	private final boolean
	SearchMateVtx( int concaveVtxIdx, lvType.IntR mateVtxIdx ) throws lvThrowable
	{
		int  numHalf = ( ( Gbl().srcDivPoly.numHalf - 3 ) + 1 ) / 2;
		boolean  odd = false;
		if( ( ( Gbl().srcDivPoly.numHalf - 3 ) % 2 ) != 0 )
			odd = true;
		
		int      n;
		int      vtxIdx  = 0;
		boolean  hasMate = false;
		for( int i=0; i<numHalf; i++ ) {
			n = concaveVtxIdx + i + 2;
			vtxIdx = n % Gbl().srcDivPoly.numHalf;
			hasMate = SearchMateVtxMain( concaveVtxIdx, vtxIdx );
			if( hasMate == true ) 
				break;
				
			if( i == ( numHalf - 1 ) && odd == true )
					break;
		
			n = concaveVtxIdx - i - 2;
			vtxIdx = ( n + Gbl().srcDivPoly.numHalf ) % Gbl().srcDivPoly.numHalf;
			hasMate = SearchMateVtxMain( concaveVtxIdx, vtxIdx );
			if( hasMate == true ) 
				break;
		}
		if( hasMate == true )
			mateVtxIdx.val = vtxIdx;
		
		return  hasMate;
	}
	
	private final boolean
	SearchMateVtxMain( int concaveVtxIdx, int targetVtxIdx ) throws lvThrowable
	{
		lvRec.CoordSys  coordSys = Gbl().tcSearchMateVtxMain[ 0 ];			// coordSys = new lvRec.CoordSys( global );
		int  concaveVtxNo = Gbl().srcDivPoly.faceHalfSeq[ concaveVtxIdx ];
		int  targetVtxNo  = Gbl().srcDivPoly.faceHalfSeq[ targetVtxIdx  ];
		MakeCoordSys( DownDivPoly().vtxPos[ concaveVtxNo ], DownDivPoly().vtxPos[ targetVtxNo ], coordSys );
		
		int  idx0, idx1;
		if( targetVtxIdx > concaveVtxIdx ) {
			idx0 = concaveVtxIdx;
			idx1 = targetVtxIdx;
		}
		else {
			idx0 = targetVtxIdx;
			idx1 = concaveVtxIdx;
		}
		
		boolean  cross = false;

		for( int i=0; i<( idx0-2 ); i++ ) {
			cross = SearchMateVtxOne( coordSys, DownDivPoly().vtxPos[ targetVtxNo ], i );
			if( cross == true )
				break;
		}
		if( cross == false )
			return  true;

		for( int i=( idx0+1 ); i<( idx1-2 ); i++ ) {
			cross = SearchMateVtxOne( coordSys, DownDivPoly().vtxPos[ targetVtxNo ], i );
			if( cross == true )
				break;
		}
		if( cross == false )
			return  true;

		for( int i=( idx1+1 ); i<Gbl().srcDivPoly.numHalf; i++ ) {
			cross = SearchMateVtxOne( coordSys, DownDivPoly().vtxPos[ targetVtxNo ], i );
			if( cross == true )
				break;
		}
		if( cross == false )
			return  true;
			
		return  false;
	}
	
	private final void
	MakeCoordSys( lvVector concaveVtxPos, lvVector targetVtxPos, lvRec.CoordSys coordSys ) throws lvThrowable
	{
		lvVector  tmpXAxis = Gbl().tvMakeCoordSys[ 0 ];					// tmpXAxis = new lvVector( global );

		coordSys.org.Assign( concaveVtxPos );
		tmpXAxis.Assign( targetVtxPos.Sub( coordSys.org ) );			// tmpXAxis = targetVtxPos - org;
		coordSys.yAxis.Assign( Gbl().tmpDt.faceNormal );
		coordSys.zAxis.Assign( ( tmpXAxis.Cross( coordSys.yAxis ) ).Unit() );
									// coordSys.zAxis = ( tmpXAxis % coordSys.yAxis ).Unit()
		coordSys.xAxis.Assign( ( coordSys.yAxis.Cross( coordSys.zAxis ) ).Unit() );
									// coordSys.xAxis = ( coordSys.yAxis % coordSys.zAxis ).Unit()
	}

	private final boolean
	SearchMateVtxOne( lvRec.CoordSys coordSys, lvVector targetVtxPos, int vtxIdx ) throws lvThrowable
	{
		lvVector  start    = Gbl().tvSearchMateVtxOne[ 0 ];					// start    = new lvVector( global );
		lvVector  end      = Gbl().tvSearchMateVtxOne[ 1 ];					// end      = new lvVector( global );
		lvVector  crossPos = Gbl().tvSearchMateVtxOne[ 2 ];					// crossPos = new lvVector( global );
		lvDouble  crossT   = Gbl().tdSearchMateVtxOne[ 0 ];					// crossT   = new lvDouble( global );
		
		double  eps = lvEps.a0;
		
		int  vtxIdxF = ( vtxIdx + 1 ) % Gbl().srcDivPoly.numHalf;
		
		int  vtxNo = Gbl().srcDivPoly.faceHalfSeq[ vtxIdx  ];
		int  vtxF  = Gbl().srcDivPoly.faceHalfSeq[ vtxIdxF ];
		start.Assign( DownDivPoly().vtxPos[ vtxNo ] );
		end.Assign( DownDivPoly().vtxPos[ vtxF ] );
		
		double  dot;
		int     intersec = start.IntersecLinePlane( end, coordSys.org, coordSys.zAxis, crossPos, crossT );
		if( intersec == lvVector.LV_INTERSEC_CROSS ) {
			if( Eps().IsPosiZero( crossT.val, eps ) == true && Eps().IsNegaZero( ( crossT.val - 1.0 ), eps ) == true ) {
				dot = coordSys.xAxis.Dot( crossPos.Sub( coordSys.org ) );		// dot = coordSys.xAxis * ( crossPos - coordSys.org );
				if( Eps().IsPosiZero( dot, eps ) == true ) {
					dot = coordSys.xAxis.Dot( crossPos.Sub( targetVtxPos ) );	// dot = coordSys.xAxis * ( crossPos - targetVtxPos );
					if( Eps().IsNegaZero( dot, eps ) == true )
						return  true;
				}
			}
		}
		else if( intersec == lvVector.LV_INTERSEC_ON ) {
			dot = coordSys.xAxis.Dot( start.Sub( coordSys.org ) );		// dot = coordSys.xAxis * ( start - coordSys.org );
			if( Eps().IsPosiZero( dot, eps ) == true ) {
				dot = coordSys.xAxis.Dot( start.Sub( targetVtxPos ) );	// dot = coordSys.xAxis * ( start - targetVtxPos );
				if( Eps().IsNegaZero( dot, eps ) == true )
					return  true;
			}
			dot = coordSys.xAxis.Dot( end.Sub( coordSys.org ) );		// dot = coordSys.xAxis * ( end - coordSys.org );
			if( Eps().IsPosiZero( dot, eps ) == true ) {
				dot = coordSys.xAxis.Dot( end.Sub( targetVtxPos ) );	// dot = coordSys.xAxis * ( end - targetVtxPos );
				if( Eps().IsNegaZero( dot, eps ) == true )
					return  true;
			}
		}
		
		return  false;
	}

	private final void
	DivVtxToVtxOver4Dst0( int concaveVtxIdx, int mateVtxIdx )
	{
		if( mateVtxIdx > concaveVtxIdx )
			Gbl().dstDivPoly0.numHalf = ( Gbl().srcDivPoly.numHalf - mateVtxIdx ) + concaveVtxIdx + 1;
		else
			Gbl().dstDivPoly0.numHalf = concaveVtxIdx - mateVtxIdx + 1;
		
		int cnt = mateVtxIdx;
		for( int i=0; i<Gbl().dstDivPoly0.numHalf; i++ ) {
			int  n = ( i + cnt ) % Gbl().srcDivPoly.numHalf;
			Gbl().dstDivPoly0.faceHalfSeq[ i ] = Gbl().srcDivPoly.faceHalfSeq[ n ];
		}
	}

	private final void
	DivVtxToVtxOver4Dst1( int concaveVtxIdx, int mateVtxIdx )
	{
		if( mateVtxIdx < concaveVtxIdx )
			Gbl().dstDivPoly1.numHalf = ( Gbl().srcDivPoly.numHalf - concaveVtxIdx ) + mateVtxIdx + 1;
		else
			Gbl().dstDivPoly1.numHalf = mateVtxIdx - concaveVtxIdx + 1;
		
		int cnt = concaveVtxIdx;
		for( int i=0; i<Gbl().dstDivPoly1.numHalf; i++ ) {
			int  n = ( i + cnt ) % Gbl().srcDivPoly.numHalf;
			Gbl().dstDivPoly1.faceHalfSeq[ i ] = Gbl().srcDivPoly.faceHalfSeq[ n ];
		}
	}

	private final void
	ConvAllDivPoly() throws lvThrowable
	{
		DelCurFace();
		
		AppendTmpDivPoly( Gbl().dstDivPoly0 );
		AppendTmpDivPoly( Gbl().dstDivPoly1 );
		
		CopyTmpToAll();
	}
	
	private final void
	DelCurFace() throws lvThrowable
	{
		Err().Assert( ( Gbl().allDivPoly.numFace >= 1 ), "lvDivPolyLow.DelCurFace(0)" );
		
		int  cnt;
		
		cnt = 0;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			if( i == Gbl().tmpDt.curFaceNo )
				continue;

			lvRec.SeqPart  curFace = Gbl().allDivPoly.face[ i ].info;
			for( int j=0; j<curFace.num; j++ ) {
				Gbl().tmpDivPoly.faceHalfSeq[ cnt ] = Gbl().allDivPoly.faceHalfSeq[ curFace.start + j ];
				cnt++;
			}
		}

		cnt = 0;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			if( i == Gbl().tmpDt.curFaceNo )
				continue;
			
			Gbl().tmpDivPoly.face[ cnt ].info.num = Gbl().allDivPoly.face[ i ].info.num;
			Gbl().tmpDivPoly.face[ cnt ].finish   = Gbl().allDivPoly.face[ i ].finish;
			cnt++;
		}
		
		Gbl().tmpDivPoly.numFace = Gbl().allDivPoly.numFace - 1;
		
		cnt = 0;
		for( int i=0; i<Gbl().tmpDivPoly.numFace; i++ ) {
			Gbl().tmpDivPoly.face[ i ].info.start = cnt;
			cnt += Gbl().tmpDivPoly.face[ i ].info.num;
		}
	}
	
	private final void
	AppendTmpDivPoly( TmpDivPolyOne dstDivPoly ) throws lvThrowable
	{
		Err().Assert( ( ( Gbl().tmpDivPoly.numFace + 1 ) <= Gbl().tmpDivPoly.face.length ), "lvDivPolyLow.AppendTmpDivPoly(0)" );
		
		int  cnt = 0;
		for( int i=0; i<Gbl().tmpDivPoly.numFace; i++ )
			cnt += Gbl().tmpDivPoly.face[ i ].info.num;

		int  num = Gbl().tmpDivPoly.numFace;
		Err().Assert( ( ( cnt + dstDivPoly.numHalf ) <= Gbl().tmpDivPoly.faceHalfSeq.length ),
																	"lvDivPolyLow.AppendTmpDivPoly(1)" );
		Gbl().tmpDivPoly.face[ num ].info.start = cnt;
		Gbl().tmpDivPoly.face[ num ].info.num	= dstDivPoly.numHalf;
		Gbl().tmpDivPoly.face[ num ].finish	= false;
		
		for( int i=0; i<dstDivPoly.numHalf; i++ )
			Gbl().tmpDivPoly.faceHalfSeq[ cnt + i ] = dstDivPoly.faceHalfSeq[ i ];
			
		Gbl().tmpDivPoly.numFace++;
	}
	
	private final void
	CopyTmpToAll()
	{
		for( int i=0; i<Gbl().tmpDivPoly.numFace; i++ ) {
			lvRec.SeqPart  curFace = Gbl().tmpDivPoly.face[ i ].info;
			for( int j=0; j<curFace.num; j++ )
				Gbl().allDivPoly.faceHalfSeq[ curFace.start + j ] = Gbl().tmpDivPoly.faceHalfSeq[ curFace.start + j ];
		}

		for( int i=0; i<Gbl().tmpDivPoly.numFace; i++ ) {
			lvRec.SeqPart.Copy( Gbl().tmpDivPoly.face[ i ].info, Gbl().allDivPoly.face[ i ].info );
			Gbl().allDivPoly.face[ i ].finish = Gbl().tmpDivPoly.face[ i ].finish;
		}
		
		Gbl().allDivPoly.numFace = Gbl().tmpDivPoly.numFace;
	}
	
	private final void
	DivRadial() throws lvThrowable
	{
		UpDivPoly().numVertex = DownDivPoly().numVtxPos + NumNonTriangle();
		Err().Assert( ( UpDivPoly().numVertex <= UpDivPoly().vertex.length ), "lvDivPolyLow.DivRadial(0)" );
		
		DivRadialVtxPos();
		DivRadialIndex();
		DivRadialVtxNormal();
		DivRadialVtxUV();
	}
	
	private final int
	NumNonTriangle() throws lvThrowable
	{
		int  cnt = 0;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			int  num = Gbl().allDivPoly.face[ i ].info.num;
			Err().Assert( ( num >= 3 ), "lvDivPolyLow.NumNotTriangle(0)" );
			if( num >= 4 )
				cnt++;
		}
		return	cnt;
	}

	private final void
	DivRadialVtxPos() throws lvThrowable
	{
		for( int i=0; i<DownDivPoly().numVtxPos; i++ )
			UpDivPoly().vertex[ i ].pos.Assign( DownDivPoly().vtxPos[ i ] );
			
		int  cnt = DownDivPoly().numVtxPos;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			lvRec.SeqPart  curFace = Gbl().allDivPoly.face[ i ].info;
			if( curFace.num < 4 )
				continue;
			
			GetCenterVtxPos( curFace, UpDivPoly().vertex[ cnt ].pos );
			cnt++;
		}
	}
	
	private final void
	GetCenterVtxPos( lvRec.SeqPart curFace, lvVector center ) throws lvThrowable
	{
		lvVector  vtxPos[] = Gbl().NewGetCenterVtxPos( curFace.num );
		for( int i=0; i<curFace.num; i++ ) {
			int  vtxNo = Gbl().allDivPoly.faceHalfSeq[ curFace.start + i ];
			vtxPos[ i ] = DownDivPoly().vtxPos[ vtxNo ];
		}
		
		center.Center( vtxPos, curFace.num );
	}
	
	private final void
	DivRadialIndex()
	{
		int  nonTriNo = 0;
		int  cnt	  = 0;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			lvRec.SeqPart  curFace = Gbl().allDivPoly.face[ i ].info;
			if( curFace.num < 4 ) {
				DivRadialIndex3( curFace, UpDivPoly().triIndex[ cnt ] );
				cnt++;
			}
			else {
				DivRadialIndexMain( curFace, cnt, nonTriNo, UpDivPoly().triIndex );
				cnt += curFace.num;
				nonTriNo++;
			}
		}
		UpDivPoly().numTriIndex = cnt;
	}

	private final void
	DivRadialIndex3( lvRec.SeqPart curFace, lvRec.TriIndex triIndex )
	{
		for( int i=0; i<3; i++ )
			triIndex.vtxNo[ i ] = Gbl().allDivPoly.faceHalfSeq[ curFace.start + i ];
	}

	private final void
	DivRadialIndexMain( lvRec.SeqPart curFace, int startPos, int nonTriNo, lvRec.TriIndex triIndex[] )
	{
		for( int i=0; i<curFace.num; i++ ) {
			int  nf = ( i + 1 ) % curFace.num;
			triIndex[ startPos + i ].vtxNo[ 0 ] = Gbl().allDivPoly.faceHalfSeq[ curFace.start + i  ];
			triIndex[ startPos + i ].vtxNo[ 1 ] = Gbl().allDivPoly.faceHalfSeq[ curFace.start + nf ];
			triIndex[ startPos + i ].vtxNo[ 2 ] = DownDivPoly().numVtxPos + nonTriNo;
		}
	}

	private final void
	DivRadialVtxNormal()
	{
		for( int i=0; i<UpDivPoly().numVertex; i++ )
			UpDivPoly().vertex[ i ].normal.Assign( Gbl().tmpDt.faceNormal );
	}
	
	private final void
	NewUpVtxUV( int numFaceVtx )
	{
		lvDivPolyUV.DownDivPolyUVone  uvSpace[] = DownDivPoly().divPolyUV.uvSpace;
		if( uvSpace == null )
			return;
			
		int  numUVspace = uvSpace.length;
			
		if( numUVspace == 1 )
			UpDivPoly().divPolyUV.uvSpace = Gbl().staticUp.divPolyUV.uvSpace;
		else {
			UpDivPoly().divPolyUV.uvSpace = new lv0DivPolyUV.UpDivPolyUVone[ numUVspace ];
			UpDivPoly().divPolyUV.uvSpace[ 0 ] = Gbl().staticUp.divPolyUV.uvSpace[ 0 ];
			for( int i=1; i<numUVspace; i++ )
				UpDivPoly().divPolyUV.uvSpace[ i ] = new lv0DivPolyUV.UpDivPolyUVone();
		}
		
		for( int i=0; i<numUVspace; i++ )
			NewUpVtxUVmain( i, numFaceVtx );
	}
	
	private final void
	NewUpVtxUVmain( int uvSpaceOfs, int numFaceVtx )
	{
		int  numVertex	  = GetNumVertex( numFaceVtx );
		int  maxNumVertex = GetNumVertex( maxNumFaceVtx );
		
		lvDivPolyUV.UpDivPolyUVone  staticUpUVspace = Gbl().staticUp.divPolyUV.uvSpace[ uvSpaceOfs ];
		lvDivPolyUV.UpDivPolyUVone  upUVspace       = UpDivPoly().divPolyUV.uvSpace[ uvSpaceOfs ];
		
		if( numFaceVtx > maxNumFaceVtx ) {
			upUVspace.vertex = new lvUVdt[ numVertex ];
			for( int i=0; i<maxNumVertex; i++ )
				upUVspace.vertex[ i ] = staticUpUVspace.vertex[ i ];
			for( int i=maxNumVertex; i<numVertex; i++ )
				upUVspace.vertex[ i ] = new lvUVdt();
		}
		else
			upUVspace.vertex = staticUpUVspace.vertex;
	}
	
	private final void
	DivRadialVtxUV() throws lvThrowable
	{
		lvDivPolyUV.DownDivPolyUVone  uvSpace[] = DownDivPoly().divPolyUV.uvSpace;
		if( uvSpace == null )
			return;
			
		for( int i=0; i<uvSpace.length; i++ )
			DivRadialVtxUVmain( i );
	}
		
	private final void
	DivRadialVtxUVmain( int uvSpaceOfs ) throws lvThrowable
	{
		lvDivPolyUV.DownDivPolyUVone  downUVspace = DownDivPoly().divPolyUV.uvSpace[ uvSpaceOfs ];
		lvDivPolyUV.UpDivPolyUVone    upUVspace   = UpDivPoly().divPolyUV.uvSpace[ uvSpaceOfs ];
		
		upUVspace.numVertex = UpDivPoly().numVertex;
			
		for( int i=0; i<downUVspace.numVtxUV; i++ )
			lvUVdt.Copy( downUVspace.vtxUV[ i ], upUVspace.vertex[ i ] );
			
		int  cnt = downUVspace.numVtxUV;
		for( int i=0; i<Gbl().allDivPoly.numFace; i++ ) {
			lvRec.SeqPart  curFace = Gbl().allDivPoly.face[ i ].info;
			if( curFace.num < 4 )
				continue;
			
			GetCenterVtxUV( uvSpaceOfs, curFace, upUVspace.vertex[ cnt ] );
			cnt++;
		}
	}
	
	private final void
	GetCenterVtxUV( int uvSpaceOfs, lvRec.SeqPart curFace, lvUVdt center ) throws lvThrowable
	{
		lvDivPolyUV.DownDivPolyUVone  downUVspace = DownDivPoly().divPolyUV.uvSpace[ uvSpaceOfs ];
		
		lvUVdt.SetUV( 0.0, 0.0, center );
		for( int i=0; i<curFace.num; i++ ) {
			int  vtxNo = Gbl().allDivPoly.faceHalfSeq[ curFace.start + i ];
			
			center.u += downUVspace.vtxUV[ vtxNo ].u;
			center.v += downUVspace.vtxUV[ vtxNo ].v;
		}
		
		Err().Assert( ( curFace.num > 0 ), "lvDivPolyLow.GetCenterVtxUV(0)" );
		center.u /= curFace.num;
		center.v /= curFace.num;
	}
	
	private final void
	Finish()
	{
		Gbl().allDivPoly.face         = null;		// Delete( Gbl().allDivPoly.face );
		Gbl().allDivPoly.faceHalfSeq  = null;		// Delete( Gbl().allDivPoly.faceHalfSeq );
		
		Gbl().tmpDivPoly.face         = null;		// Delete( Gbl().tmpDivPoly.face );
		Gbl().tmpDivPoly.faceHalfSeq  = null;		// Delete( Gbl().tmpDivPoly.faceHalfSeq );
		
		Gbl().srcDivPoly.faceHalfSeq  = null;		// Delete( Gbl().srcDivPoly.faceHalfSeq );
		Gbl().dstDivPoly0.faceHalfSeq = null;		// Delete( Gbl().dstDivPoly0.faceHalfSeq );
		Gbl().dstDivPoly1.faceHalfSeq = null;		// Delete( Gbl().dstDivPoly1.faceHalfSeq );
	}

}
