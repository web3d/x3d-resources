//
// Copyright (C) 1998-2001 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakePoly.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * 1個のシェル内格子（ lvModel.shell[ n ].poly「 n はシェルNo 」）を作成するクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * @author	  debuged by Eishin Matsui (01/07/12)
 * 
 */
public class lvMakePoly extends lvRoot {
	
	/**
	 * 頂点に対する一時的内部クラス（TmpPoly用）
	 */
	private static class TmpVertex {
		
		/**
		 * radialSeq[] に対する、開始点と個数（radial数）を示す --- 初期値 null		<br>
		 * radial数は、最終radial数もしくは仮radial数（最終radial数*2 または、(最終radial数-1)*2 ）
		 */
		private lvRec.SeqPart  radial	= new lvRec.SeqPart();
		
		/**
		 * SetTmpRadialSeq0()用の一時的カウンタ
		 */
		private int            tmpCnt;
		
		/**
		 * この頂点が（非constricな）NG面を持つかどうか？	持たない:false, 持つ:true
		 */
		private boolean        hasNg;
	}
	
	/**
	 * radial に対する一時的内部クラス（TmpPoly用）
	 */
	private static class TmpRadial {
		
		/** ある radial に対して、対となる頂点の番号		*/
		private int      mateVtxNo;
		
		/**
		 * radialにより稜線検索を行った場合、最初に発見されるかを示す。 2回目:false, 最初:true		<br>
		 * （ 対となる頂点の radial に関しては稜線検索の必要はない ）
		 */
		private boolean  firstLook;
		
		/** lvToKernel.edge[]の No を記述する。無い場合は、-1		*/
		private int      srcEdgeNo;
		
		/** TmpEdge.edge[]の No を記述する。初期値は、-1			*/
		private int      dstEdgeNo;
		/** TmpEdge.edge[]の始点が、当radialの頂点かどうか？	始点=頂点:false, 終点=頂点:true		*/
		private boolean  dstEdgeReverse;

		// -------------------------------------------------------------------

		/**
		 * コピー関数
		 * @param  src		(( I )) コピー元
		 * @param  dst		(( O )) コピー先
		 */
		private static final void
		Copy( TmpRadial src, TmpRadial dst )
		{
			dst.mateVtxNo      = src.mateVtxNo;
			dst.firstLook      = src.firstLook;
			dst.srcEdgeNo      = src.srcEdgeNo;
			dst.dstEdgeNo      = src.dstEdgeNo;
			dst.dstEdgeReverse = src.dstEdgeReverse;
		}
	}
	
	/**
	 * 面に対する一時的内部クラス（TmpPoly用）
	 */
	private static class TmpFace {
		
		/**
		 * 面の開始点と個数（頂点数（3以上）)の配列（GS面+NG面（constricの場合）の数だけ存在する「 1以上 」） --- 初期値 null
		 */
		private lvRec.SeqPart  vertex	= new lvRec.SeqPart();
	}
	
	/**
	 * （非constricの）NG面を持つ頂点に対する一時的内部クラス（TmpPoly用）
	 */
	private static class TmpNgVertex {
		
		/**
		 * （非constricの）NG面を持つ頂点の番号
		 */
		private int      vertexNo;
		
		/**
		 * SchNgVertex()用のフラグ
		 */
		private boolean  alreadyExec;
	}
	
	/**
	 * 稜線に対する一時的内部クラス（TmpPoly用）
	 */
	private static class TmpEdge {
		
		/** 頂点No（始点）				*/
		private int		v0;
	
		/** 頂点No（終点）				*/
		private int		v1;
	}

// -------------------------------------------------------------------
	
	/**
	 * シェル内格子作成の際、一時的に使用する情報のための内部クラス
	 */
	private static class TmpPoly {
		
		/**
		 * radialSeq[] に対する、開始点と個数（radial数）を示す --- 初期値 null						<br>
		 * radial数は、最終radial数もしくは仮radial数（最終radial数*2 または、(最終radial数-1)*2 ）
		 */
		private TmpVertex  vertex[]				= null;
		
		/**
		 * 頂点がn個ある場合、															<br>
		 * 頂点0の仮radialNo列, 頂点1の仮radialNo列, ---, 頂点( n-1 )の仮radialNo列		<br>
		 * と続く。配列の長さは、														<br>
		 * 頂点0の仮radial数 + 頂点1の仮radial数 + --- + 頂点( n-1 )の仮radial数		<br>
		 * となる。 --- 初期値 null														<br>
		 */
		private TmpRadial  radialSeq[]			= null;
		
		/**
		 * 1個の頂点の仮radialNo列。配列の長さは、1個の頂点の仮radialNo列の内、最大長のものとなる --- 初期値 null
		 */
		private TmpRadial  radialOneVtx[]		= null;
		
		/**
		 * CorrectRadialMain()の一時的な配列。配列の長さは、1個の頂点の仮radialNo列の内、最大長のものの 1/2 となる --- 初期値 null
		 */
		private boolean    alreadyExecOneVtx[]	= null;
		
		/**
		 * 面の開始点と個数（頂点数（3以上）)の配列（GS面+NG面（constricの場合）の数だけ存在する「 1以上 」） --- 初期値 null
		 */
		private TmpFace    face[]				= null;

		/**
		 * 面がn個ある場合、											<br>
		 * 面0の頂点No列, 面1の頂点No列, ---, 面( n-1 )の頂点No列		<br>
		 * と続く。配列の長さは、										<br>
		 * 面0の頂点数 + 面1の頂点数 + --- + 面( n-1 )の頂点数			<br>
		 * となる。 --- 初期値 null
		 */
		private int	faceVtxSeq[]				= null;
		
		/**
		 * （非constricの）NG面を持つ頂点に関する情報配列。					<br>
		 *  hasNg == true の頂点の数だけ存在する。 --- 初期値 null
		 */
		private TmpNgVertex  srcNgVtxSeq[]		= null;

		/** ngVtxSeq[]の配列長。NG面の数となる。		*/
		private int        numNgFace			= 0;
		/**
		 * NG面（非constricの場合）の開始点と個数（頂点数（3以上）)の配列。			<br>
		 * 「hasNg == true の頂点の数」の1/3の数、存在する（NG面の数より多い）。	<br>
		 * 必要な長さは、NG面の数だけである--- 初期値 null							<br>
		 * 注）「hasNg == true の頂点の数」の1/3とは、NG面がすべて三角形のときのNG面数である。
		 */
		private TmpFace    ngFace[]				= null;

		/**
		 * （非constricの）NG面内の頂点Noを羅列した配列。					<br>
		 *  hasNg == true の頂点の数だけ存在する。							<br>
		 * NG面がn個ある場合、												<br>
		 * NG面0の頂点No列, NG面1の頂点No列, ---, NG面( n-1 )の頂点No列		<br>
		 * と続く。配列の長さは、											<br>
		 * NG面0の頂点数 + NG面1の頂点数 + --- + NG面( n-1 )の頂点数		<br>
		 * となる。 --- 初期値 null
		 */
		private int  dstNgVtxSeq[]				= null;
		
		/**
		 * 稜線の両頂点を示す配列（稜線数だけ存在する「 3以上 」） --- 初期値 null
		 */
		private TmpEdge  edge[]					= null;
	}
	
// -------------------------------------------------------------------
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** シェル作成の際、一時的に使用する情報		*/
		private TmpPoly        tmpPoly			= null;
		
		/** カレントシェルNo							*/
		private int            curShellNo		= 0;
		/** カレントlvToKernelデータ					*/
		private lvToKernelLow  curSrc			= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt ) {
			tmpPoly = new TmpPoly();
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lvTopoGblElm )global.GTopo() ).gMakePoly;
	}
	/** TmpPoly用のグローバルデータ				*/
	private final TmpPoly
	TmpPoly()
	{
		return  Gbl().tmpPoly;
	}
	/** ソースデータ用クラスオブジェクト		*/
	private final lvToKernelLow
	CurSrc()
	{
		return  Gbl().curSrc;
	}
	/** デストネーションデータ用クラスオブジェクト		*/
	private final lvPolygon
	CurDst()
	{
		return ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvMakePoly( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1個のシェル内格子（ lvModel.shell[ n ].poly「 n はシェルNo 」）を作成する
	 * @param  shellNo		(( I )) シェルNo
	 * @param  src			(( I )) ソースオブジェクト
	 */
	public final void
	Exec( int shellNo, lvToKernelLow src ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		Gbl().curSrc     = src;
		
		VertexProc0();
		RadialProc0();
		FaceProc0();
		RadialProc1();
		EdgeProc0();
		FaceProc1();
		EdgeProc1();
		
		Finish();
	}
	
	private final void
	VertexProc0() throws lvThrowable
	{
		int  num = CurSrc().vertex.length;
		Err().Assert( ( num >= 3 ), "lvMakePoly.VertexProc0(0)" );
		CurDst().NewVertex( num );
		
		SetVetrexInfo();
	}
	
	private final void
	SetVetrexInfo()
	{
		int  num = CurSrc().vertex.length;
		for( int i=0; i<num; i++ ) {
			lvVecDt.Copy( CurSrc().vertex[ i ].pos, CurDst().vertex[ i ].pos );
			if( CurSrc().vertex[ i ].enable == true )
				CurDst().vertex[ i ].round  = CurSrc().vertex[ i ].round;
			else
				CurDst().vertex[ i ].round  = 1.0;
		}
	}
	
	private final void
	RadialProc0() throws lvThrowable
	{
		int  num, numNgVtx, numNgVtxSeq;
		
		Err().Assert( ( CurSrc().gsNumVtx.length >= 1 ), "lvMakePoly.RadialProc0(0)" );
		if( CurSrc().ngNumVtx == null )
			numNgVtx = 0;
		else
			numNgVtx = CurSrc().ngNumVtx.length;
		num = CurSrc().gsNumVtx.length + numNgVtx;
		NewTmpFace( num );
		SetTmpFace();
		
		if( CurSrc().ngVtxSeq == null )
			numNgVtxSeq = 0;
		else
			numNgVtxSeq = CurSrc().ngVtxSeq.length;
		num = CurSrc().gsVtxSeq.length + numNgVtxSeq;
		NewTmpFaceVtxSeq( num );
		SetTmpFaceVtxSeq();

		num = CurSrc().vertex.length;
		NewTmpVertex( num );
		SetTmpVertex();
		
		num = GetNumTmpRadialSeq();
		NewTmpRadialSeq( num );
		SetTmpRadialSeq0();
		
		num = GetMaxTmpRadialSeq();
		NewTmpRadialOneVtx( num );
		NewTmpAlreadyExecOneVtx( num/2 );
		
		CorrectRadialInfo0();

		SetVtxFace();
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().faceの new する時の長さ
	 */
	private final void
	NewTmpFace( int num )
	{
		TmpPoly().face = new TmpFace[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().face[ i ] = new TmpFace();
	}
	
	private final void
	SetTmpFace() throws lvThrowable
	{
		int  num;
		int  cnt = 0;
		
		num = CurSrc().gsNumVtx.length;
		for( int i=0; i<num; i++ ) {
			Err().Assert( ( CurSrc().gsNumVtx[ i ] >= 3 ), "lvMakePoly.SetTmpFace(0)" );
			TmpPoly().face[ cnt ].vertex.num = CurSrc().gsNumVtx[ i ];
			cnt++;
		}
		
		if( CurSrc().ngNumVtx == null )
			num = 0;
		else
			num = CurSrc().ngNumVtx.length;
		for( int i=0; i<num; i++ ) {
			Err().Assert( ( CurSrc().ngNumVtx[ i ] >= 3 ), "lvMakePoly.SetTmpFace(1)" );
			TmpPoly().face[ cnt ].vertex.num = CurSrc().ngNumVtx[ i ];
			cnt++;
		}
		
		cnt = 0;
		num = TmpPoly().face.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().face[ i ].vertex.start = cnt;
			cnt += TmpPoly().face[ i ].vertex.num;
		}
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().faceVtxSeqの new する時の長さ
	 */
	private final void
	NewTmpFaceVtxSeq( int num )
	{
		TmpPoly().faceVtxSeq = new int[ num ];
	}

	private final void
	SetTmpFaceVtxSeq()
	{
		int  num;
		int  cnt = 0;
		
		num = CurSrc().gsVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().faceVtxSeq[ cnt ] = CurSrc().gsVtxSeq[ i ];
			cnt++;
		}
		
		if( CurSrc().ngVtxSeq == null )
			num = 0;
		else
			num = CurSrc().ngVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().faceVtxSeq[ cnt ] = CurSrc().ngVtxSeq[ i ];
			cnt++;
		}
	}

	/**
	 * @param  num		(( I )) TmpPoly().vertexの new する時の長さ
	 */
	private final void
	NewTmpVertex( int num )
	{
		TmpPoly().vertex = new TmpVertex[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().vertex[ i ] = new TmpVertex();
	}

	private final void
	SetTmpVertex() throws lvThrowable
	{
		int  num, cnt;
		
		num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			TmpPoly().vertex[ i ].radial.num = 0;
			
		int  numFace = TmpPoly().face.length;
		for( int i=0; i<numFace; i++ ) {
			int  numVtx = TmpPoly().face[ i ].vertex.num;
			int  pos    = TmpPoly().face[ i ].vertex.start;
			for( int j=0; j<numVtx; j++ ) {
				int  vtxNo = TmpPoly().faceVtxSeq[ pos ];
				pos++;
				TmpPoly().vertex[ vtxNo ].radial.num += 2;
			}
		}

		cnt = 0;
		num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			TmpPoly().vertex[ i ].radial.start = cnt;
			Err().Assert( ( TmpPoly().vertex[ i ].radial.num >= 2 ), "lvMakePoly.SetTmpVertex(0)" );
			cnt += TmpPoly().vertex[ i ].radial.num;
		}
	}

	/**
	 * @return			TmpPoly().radialSeqの new する時の長さを得る
	 */
	private final int
	GetNumTmpRadialSeq()
	{
		int  cnt = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			cnt += TmpPoly().vertex[ i ].radial.num;
			
		return  cnt;
	}

	/**
	 * @param  num		(( I )) TmpPoly().radialSeqの new する時の長さ
	 */
	private final void
	NewTmpRadialSeq( int num )
	{
		TmpPoly().radialSeq = new TmpRadial[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ] = new TmpRadial();
	}
	
	private final void
	SetTmpRadialSeq0()
	{
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			TmpPoly().vertex[ i ].tmpCnt = 0;
			
		int  numFace = TmpPoly().face.length;
		for( int i=0; i<numFace; i++ ) {
			int  numVtx = TmpPoly().face[ i ].vertex.num;
			int  start  = TmpPoly().face[ i ].vertex.start;
			for( int j=0; j<numVtx; j++ ) {
				int  vtxB  = TmpPoly().faceVtxSeq[ start + ( j+numVtx-1 ) % numVtx ];
				int  vtxNo = TmpPoly().faceVtxSeq[ start + j ];
				int  vtxF  = TmpPoly().faceVtxSeq[ start + ( j+1 ) % numVtx ];
				
				int  cnt = TmpPoly().vertex[ vtxNo ].radial.start + TmpPoly().vertex[ vtxNo ].tmpCnt;
				TmpPoly().radialSeq[ cnt   ].mateVtxNo = vtxF;
				TmpPoly().radialSeq[ cnt+1 ].mateVtxNo = vtxB;
				TmpPoly().vertex[ vtxNo ].tmpCnt += 2;
			}
		}
	}

	/**
	 * @return			TmpPoly().radialOneVtxの new する時の長さを得る
	 */
	private final int
	GetMaxTmpRadialSeq()
	{
		int  max = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( max < TmpPoly().vertex[ i ].radial.num )
				max = TmpPoly().vertex[ i ].radial.num;
		}
			
		return  max;
	}

	/**
	 * @param  num		(( I )) TmpPoly().radialOneVtxの new する時の長さ
	 */
	private final void
	NewTmpRadialOneVtx( int num )
	{
		TmpPoly().radialOneVtx = new TmpRadial[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().radialOneVtx[ i ] = new TmpRadial();
	}

	/**
	 * @param  num		(( I )) TmpPoly().alreadyExecOneVtxの new する時の長さ
	 */
	private final void
	NewTmpAlreadyExecOneVtx( int num )
	{
		TmpPoly().alreadyExecOneVtx = new boolean[ num ];
	}

	private final void
	CorrectRadialInfo0() throws lvThrowable
	{
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			lvRec.SeqPart  radial    = TmpPoly().vertex[ i ].radial;
			int            posFace   = SchRadialFirstPosition( radial );
			int            numRadial = CorrectRadialMain( radial, posFace );
			boolean        hasNg     = HasNg( numRadial );
			if( hasNg == false )
				numRadial--;
			TmpPoly().vertex[ i ].radial.num = numRadial;
			TmpPoly().vertex[ i ].hasNg      = hasNg;
			RadialOneVtxToSeq( radial.start, numRadial );
		}
	}
	
	/**
	 * @param  radial		(( I )) TmpPoly().vertex[ * ].radialオブジェクト
	 * @return				GS面とNG面（非constricの場合）の切れ目の位置
	 */
	private final int
	SchRadialFirstPosition( lvRec.SeqPart radial ) throws lvThrowable
	{
		int  numFace = radial.num / 2;
		Err().Assert( ( numFace > 0 ), "lvMakePoly.SchRadialFirstPosition(0)" );
		if( numFace <= 1 )
			return  0;
		
		boolean  chk;
		int      posFace = 0;
		for( int i=0; i<numFace; i++ ) {
			chk = false;
			for( int j=0; j<numFace; j++ ) {
				if( i == j )
					continue;
				int  i2 = i * 2;
				int  j2 = j * 2 + 1;
				int  vtx0 = TmpPoly().radialSeq[ radial.start + i2 ].mateVtxNo;
				int  vtx1 = TmpPoly().radialSeq[ radial.start + j2 ].mateVtxNo;
				if( vtx0 == vtx1 ) {
					chk = true;
					break;
				}
			}
			if( chk == false ) {
				posFace = i;
				break;
			}
		}
		
		return  posFace;
	}

	/**
	 * @param  radial		(( I )) TmpPoly().vertex[ * ].radialオブジェクト
	 * @param  posFace		GS面とNG面（非constricの場合）の切れ目の位置
	 * @return				仮のラジアル数を得る
	 */
	private final int
	CorrectRadialMain( lvRec.SeqPart radial, int posFace ) throws lvThrowable
	{
		int  numFace = radial.num / 2;
		for( int i=0; i<numFace; i++ )
			TmpPoly().alreadyExecOneVtx[ i ] = false;
		
		TmpRadial.Copy( TmpPoly().radialSeq[ radial.start + ( posFace*2     ) ], TmpPoly().radialOneVtx[ 0 ]  );
		TmpRadial.Copy( TmpPoly().radialSeq[ radial.start + ( posFace*2 + 1 ) ], TmpPoly().radialOneVtx[ 1 ]  );
		
		TmpPoly().alreadyExecOneVtx[ posFace ] = true;
		
		boolean  chk;
		int      pos = posFace;
		for( int i=1; i < numFace; i++ ) {
			chk = false;
			for( int j=0; j<numFace; j++ ) {
				if( pos == j || TmpPoly().alreadyExecOneVtx[ j ] == true )
					continue;
				int  i2 = pos * 2 + 1;
				int  j2 =   j * 2;
				int  vtx0 = TmpPoly().radialSeq[ radial.start + i2 ].mateVtxNo;
				int  vtx1 = TmpPoly().radialSeq[ radial.start + j2 ].mateVtxNo;
				if( vtx0 == vtx1 ) {
					pos = j;
					chk = true;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.CorrectRadialMain(0)" );
			TmpRadial.Copy( TmpPoly().radialSeq[ radial.start + ( pos*2 + 1 ) ], TmpPoly().radialOneVtx[ i+1 ] );
			TmpPoly().alreadyExecOneVtx[ pos ] = true;
		}
		
		return  numFace + 1;
	}
	
	/**
	 * @param  radial		(( I )) 仮のラジアル数
	 * @return				（非constricの）NG面を持つか？	持たない:false, 持つ:true
	 */
	private final boolean
	HasNg( int num )
	{
		if( TmpPoly().radialOneVtx[ 0 ].mateVtxNo != TmpPoly().radialOneVtx[ num-1 ].mateVtxNo )
			return  true;
			
		return  false;
	}

	/**
	 * @param  start		(( I )) TmpPoly().radialSeq 内の開始点
	 * @param  num			(( I )) （真の）ラジアル数
	 */
	private final void
	RadialOneVtxToSeq( int start, int num )
	{
		for( int i=0; i<num; i++ )
			TmpRadial.Copy( TmpPoly().radialOneVtx[ i ], TmpPoly().radialSeq[ start+i ] );
	}
	
	private final void
	SetVtxFace()
	{
		int  num, cnt;
		
		num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ )
			CurDst().vertex[ i ].vtxFace.num = TmpPoly().vertex[ i ].radial.num;

		cnt = 0;
		num = CurDst().vertex.length;
		for( int i=0; i<num; i++ ) {
			CurDst().vertex[ i ].vtxFace.start = cnt;
			cnt += CurDst().vertex[ i ].vtxFace.num;
		}
	}

	private final void
	FaceProc0() throws lvThrowable
	{
		int  num, numDstNgVtxSeq;

		MakeNg();
		
		CurDst().ngStartNo = CurSrc().gsNumVtx.length;

		num = TmpPoly().face.length + TmpPoly().numNgFace;
		CurDst().NewFace( num );
		SetFaceInfo();
		
		if( TmpPoly().dstNgVtxSeq == null )
			numDstNgVtxSeq = 0;
		else
			numDstNgVtxSeq = TmpPoly().dstNgVtxSeq.length;
		num = TmpPoly().faceVtxSeq.length + numDstNgVtxSeq;
		CurDst().NewFaceHalfSeq( num );
		SetFaceHalfSeq();
		
		SetFaceHalfRadial();
	}
	
	private final void
	MakeNg() throws lvThrowable
	{
		int  num = GetNumTmpNgVtxSeq();
		if( num == 0 )
			return;
			
		NewTmpNgFace( num/3 );
		NewTmpDstNgVtxSeq( num );
		
		NewTmpSrcNgVtxSeq( num );
		InitTmpSrcNgVtxSeq();
		
		TmpPoly().numNgFace = MakeNgMain();
	}
	
	/**
	 * @return			TmpPoly().dstNgVtxSeq, TmpPoly().srcNgVtxSeq の new する時の長さを得る
	 */
	private final int
	GetNumTmpNgVtxSeq()
	{
		int  cnt = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( TmpPoly().vertex[ i ].hasNg == true )
				cnt++;
		}
			
		return  cnt;
	}

	/**
	 * @param  num		(( I )) TmpPoly().ngFaceの new する時の長さ
	 */
	private final void
	NewTmpNgFace( int num )
	{
		TmpPoly().ngFace = new TmpFace[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().ngFace[ i ] = new TmpFace();
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().dstNgVtxSeqの new する時の長さ
	 */
	private final void
	NewTmpDstNgVtxSeq( int num )
	{
		TmpPoly().dstNgVtxSeq = new int[ num ];
	}
	
	/**
	 * @param  num		(( I )) TmpPoly().srcNgVtxSeqの new する時の長さ
	 */
	private final void
	NewTmpSrcNgVtxSeq( int num )
	{
		TmpPoly().srcNgVtxSeq = new TmpNgVertex[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().srcNgVtxSeq[ i ] = new TmpNgVertex();
	}
	
	/**
	 * @return			TmpPoly().srcNgVtxSeq の初期化した時の長さ
	 */
	private final int
	InitTmpSrcNgVtxSeq()
	{
		int  cnt = 0;
		int  num = TmpPoly().vertex.length;
		for( int i=0; i<num; i++ ) {
			if( TmpPoly().vertex[ i ].hasNg == true ) {
				TmpPoly().srcNgVtxSeq[ cnt ].vertexNo    = i;
				TmpPoly().srcNgVtxSeq[ cnt ].alreadyExec = false;
				cnt++;
			}
		}
			
		return  cnt;
	}

	/**
	 * @return			TmpPoly().numNgFaceの配列長を返す。NG面の数となる。
	 */
	private final int
	MakeNgMain() throws lvThrowable
	{
		int  cnt  = 0;
		int  ngNo = 0;
		
		int  num = TmpPoly().srcNgVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			if( TmpPoly().srcNgVtxSeq[ i ].alreadyExec == true )
				continue;
			Err().Assert( ( ngNo < TmpPoly().ngFace.length ), "lvMakePoly.MakeNgMain(0)" );
			cnt = MakeNgOne( i, cnt, ngNo );
			ngNo++;
		}
		
		return  ngNo;
	}
	
	/**
	 * @param  posStart		(( I )) TmpPoly().srcNgVtxSeq の開始点インデックス
	 * @param  cntStart		(( I )) TmpPoly().dstNgVtxSeq の開始点インデックス
	 * @param  ngNo			(( I )) TmpPoly().ngFace のインデックス
	 * @return				TmpPoly().dstNgVtxSeq の次期開始点インデックス
	 */
	private final int
	MakeNgOne( int posStart, int cntStart, int ngNo ) throws lvThrowable
	{
		lvRec.SeqPart  radial;
		int  vtx1 = 0;
		
		int  cnt  = cntStart;
		int  nVtx = 0;
		int  pos  = posStart;
		int  vtxOrg = TmpPoly().srcNgVtxSeq[ posStart ].vertexNo;

		boolean  chk2 = false;
		int  num = TmpPoly().srcNgVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			int  vtx0 = TmpPoly().srcNgVtxSeq[ pos ].vertexNo;
			TmpPoly().dstNgVtxSeq[ cnt ] = vtx0;
			cnt++;
			nVtx++;
			TmpPoly().srcNgVtxSeq[ pos ].alreadyExec = true;
			
			radial = TmpPoly().vertex[ vtx0 ].radial;
			int  mate0 = TmpPoly().radialSeq[ radial.start + ( radial.num-1 ) ].mateVtxNo;
				
			boolean  chk = false;
			for( int j=0; j<num; j++ ) {
				if( pos == j || ( TmpPoly().srcNgVtxSeq[ j ].alreadyExec == true && posStart != j ) )
					continue;
					
				vtx1   = TmpPoly().srcNgVtxSeq[ j ].vertexNo;
				radial = TmpPoly().vertex[ vtx1 ].radial;
				int  mate1 = TmpPoly().radialSeq[ radial.start + 0 ].mateVtxNo;
				
				if( mate0 == vtx1 ) {
					Err().Assert( ( mate1 == vtx0 ), "lvMakePoly.MakeNgOne(0)" );
					pos = j;
					chk = true;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.MakeNgOne(1)" );
			if( chk == true && vtx1 == vtxOrg ) {
				chk2 = true;
				break;
			}
		}
		Err().Assert( ( chk2 == true ), "lvMakePoly.MakeNgOne(2)" );
		Err().Assert( ( nVtx >= 3 ),    "lvMakePoly.MakeNgOne(3)" );
		TmpPoly().ngFace[ ngNo ].vertex.num = nVtx;
		
		return  cnt;
	}

	private final void
	SetFaceInfo()
	{
		int  num;
		int  cnt = 0;
		
		num = TmpPoly().face.length;
		for( int i=0; i<num; i++ ) {
			CurDst().face[ cnt ].num = TmpPoly().face[ i ].vertex.num;
			cnt++;
		}
		
		num = TmpPoly().numNgFace;
		for( int i=0; i<num; i++ ) {
			CurDst().face[ cnt ].num = TmpPoly().ngFace[ i ].vertex.num;
			cnt++;
		}
		
		cnt = 0;
		num = CurDst().face.length;
		for( int i=0; i<num; i++ ) {
			CurDst().face[ i ].start = cnt;
			cnt += CurDst().face[ i ].num;
		}
	}
	
	private final void
	SetFaceHalfSeq()
	{
		int  num;
		int  cnt = 0;
		
		num = TmpPoly().faceVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			CurDst().faceHalfSeq[ cnt ].vtxNo = TmpPoly().faceVtxSeq[ i ];
			cnt++;
		}
		
		if( TmpPoly().dstNgVtxSeq == null )
			num = 0;
		else
			num = TmpPoly().dstNgVtxSeq.length;
		for( int i=0; i<num; i++ ) {
			CurDst().faceHalfSeq[ cnt ].vtxNo = TmpPoly().dstNgVtxSeq[ i ];
			cnt++;
		}
	}

	private final void
	SetFaceHalfRadial() throws lvThrowable
	{
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  radial = CurDst().face[ i ];
			for( int j=0; j<radial.num; j++ ) {
				int  vtxNo = CurDst().faceHalfSeq[ radial.start + j ].vtxNo;
				int  vtxF  = CurDst().faceHalfSeq[ radial.start + ( ( j+1 ) % radial.num ) ].vtxNo;
				CurDst().faceHalfSeq[ radial.start + j ].radialNo = SetFaceHalfRadialMain( vtxNo, vtxF );
			}
		}
	}

	/**
	 * @param  vtxNo		(( I )) 基準となる頂点No
	 * @param  vtxB			(( I )) 面内において１個前となる頂点No
	 * @return				ラジアルNo
	 */
	private final int
	SetFaceHalfRadialMain( int vtxNo, int vtxF ) throws lvThrowable
	{
		int      radialNo = 0;
		boolean  chk      = false;
		lvRec.SeqPart  radial = TmpPoly().vertex[ vtxNo ].radial;
		for( int i=0; i<radial.num; i++ ) {
			if( vtxF == TmpPoly().radialSeq[ radial.start + i ].mateVtxNo ) {
				radialNo = i;
				chk      = true;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakePoly.SetFaceHalfRadialMain(0)" );
		
		return  radialNo;
	}
	
	private final void
	RadialProc1()
	{
		int  num = GetNumVtxFaceSeq();
		CurDst().NewVtxFaceSeq( num );
		SetVtxFaceSeq();
	}
	
	/**
	 * @return			CurDst().NewVtxFaceSeqの new する時の長さを得る
	 */
	private final int
	GetNumVtxFaceSeq()
	{
		int  cnt = 0;
		int  num = CurDst().vertex.length;
		for( int i=0; i<num; i++ )
			cnt += CurDst().vertex[ i ].vtxFace.num;
			
		return  cnt;
	}
	
	private final void
	SetVtxFaceSeq()
	{
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  radial = CurDst().face[ i ];
			for( int j=0; j<radial.num; j++ ) {
				int  vtxNo    = CurDst().faceHalfSeq[ radial.start + j ].vtxNo;
				int  radialNo = CurDst().faceHalfSeq[ radial.start + j ].radialNo;
				
				int  start    = CurDst().vertex[ vtxNo ].vtxFace.start;
				CurDst().vtxFaceSeq[ start + radialNo ].faceNo = i;
				CurDst().vtxFaceSeq[ start + radialNo ].halfNo = j;
			}
		}
	}
	
	private final void
	EdgeProc0() throws lvThrowable
	{
		int  num = CurDst().vtxFaceSeq.length / 2;
		CurDst().NewEdge( num );
		NewTmpEdge( num );
		
		SetTmpEdge();
		
		SetSrcEdgeNo();
		SetDstEdgeNo();
		
		SetEdgeInit();
		SetEdgeInfo();
	}

	/**
	 * @param  num		(( I )) TmpPoly().edgeの new する時の長さ
	 */
	private final void
	NewTmpEdge( int num )
	{
		TmpPoly().edge = new TmpEdge[ num ];
		for( int i=0; i<num; i++ )
			TmpPoly().edge[ i ] = new TmpEdge();
	}
	
	private final void
	SetTmpEdge() throws lvThrowable
	{
		int  num = TmpPoly().radialSeq.length;
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ].firstLook = false;
		
		int  cnt    = 0;
		int  numVtx = TmpPoly().vertex.length;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  radial = TmpPoly().vertex[ i ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( TmpPoly().radialSeq[ radial.start + j ].firstLook == true )
					continue;
				Err().Assert( ( cnt < TmpPoly().edge.length ), "lvMakePoly.SetTmpEdge(0)" );
				TmpPoly().edge[ cnt ].v0 = i;
				TmpPoly().edge[ cnt ].v1 = TmpPoly().radialSeq[ radial.start + j ].mateVtxNo;
				TmpPoly().radialSeq[ radial.start + j ].firstLook = true;
				SetMateFirstLook( TmpPoly().edge[ cnt ].v1, TmpPoly().edge[ cnt ].v0 );
				cnt++;
			}
		}
	}
	
	/**
	 * @param  vtxNo		(( I )) 基準となる頂点No
	 * @param  mateVtxNo	(( I )) 対となる頂点No
	 */
	private final void
	SetMateFirstLook( int vtxNo, int mateVtxNo ) throws lvThrowable
	{
		boolean  chk      = false;
		int      radialNo = 0;
		lvRec.SeqPart  radial = TmpPoly().vertex[ vtxNo ].radial;
		for( int i=0; i<radial.num; i++ ) {
			if( mateVtxNo == TmpPoly().radialSeq[ radial.start + i ].mateVtxNo ) {
				chk      = true;
				radialNo = i;
				break;
			}
		}
		Err().Assert( ( chk == true ), "lvMakePoly.SetMateFirstLook(0)" );
		
		TmpPoly().radialSeq[ radial.start + radialNo ].firstLook = true;
	}
	
	private final void
	SetSrcEdgeNo() throws lvThrowable
	{
		int  num = TmpPoly().radialSeq.length;
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ].srcEdgeNo = -1;
		
		boolean  chk      = false;
		int      radialNo = 0;
		int      numEdge;
		if( CurSrc().edge == null )
			numEdge = 0;
		else
			numEdge = CurSrc().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			int  v0 = CurSrc().edge[ i ].v0;
			int  v1 = CurSrc().edge[ i ].v1;
			lvRec.SeqPart  radial = TmpPoly().vertex[ v0 ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( v1 == TmpPoly().radialSeq[ radial.start + j ].mateVtxNo ) {
					chk      = true;
					radialNo = j;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.SetSrcEdgeNo(0)" );
			Err().Assert( ( TmpPoly().radialSeq[ radial.start + radialNo ].srcEdgeNo < 0 ), "lvMakePoly.SetSrcEdgeNo(1)" );
			TmpPoly().radialSeq[ radial.start + radialNo ].srcEdgeNo = i;
		}
	}
	
	private final void
	SetDstEdgeNo() throws lvThrowable
	{
		int  num = TmpPoly().radialSeq.length;
		for( int i=0; i<num; i++ )
			TmpPoly().radialSeq[ i ].dstEdgeNo = -1;
		
		SetDstEdgeNo0();
		SetDstEdgeNo1();
	}
	
	private final void
	SetDstEdgeNo0() throws lvThrowable
	{
		boolean  chk      = false;
		int      radialNo = 0;
		int      numEdge = TmpPoly().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			int  v0 = TmpPoly().edge[ i ].v0;
			int  v1 = TmpPoly().edge[ i ].v1;
			lvRec.SeqPart  radial = TmpPoly().vertex[ v0 ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( v1 == TmpPoly().radialSeq[ radial.start + j ].mateVtxNo ) {
					chk      = true;
					radialNo = j;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.SetDstEdgeNo0(0)" );
			Err().Assert( ( TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo < 0 ), "lvMakePoly.SetDstEdgeNo0(1)" );
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo      = i;
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeReverse = false;
		}
	}
	
	private final void
	SetDstEdgeNo1() throws lvThrowable
	{
		boolean  chk      = false;
		int      radialNo = 0;
		int      numEdge = TmpPoly().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			int  v0 = TmpPoly().edge[ i ].v0;
			int  v1 = TmpPoly().edge[ i ].v1;
			lvRec.SeqPart  radial = TmpPoly().vertex[ v1 ].radial;
			for( int j=0; j<radial.num; j++ ) {
				if( v0 == TmpPoly().radialSeq[ radial.start + j ].mateVtxNo ) {
					chk      = true;
					radialNo = j;
					break;
				}
			}
			Err().Assert( ( chk == true ), "lvMakePoly.SetDstEdgeNo1(0)" );
			Err().Assert( ( TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo < 0 ), "lvMakePoly.SetDstEdgeNo1(1)" );
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo      = i;
			TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeReverse = true;
		}
	}
	
	private final void
	SetEdgeInit()
	{
		int  numEdge = CurDst().edge.length;
		for( int i=0; i<numEdge; i++ ) {
			CurDst().edge[ i ].allRound = 1.0;
			for( int j=0; j<2; j++ )
				lvVecDt.SetXYZ( 0.0, 1.0, 0.0, CurDst().edge[ i ].vec[ j ] );
		}
	}
	
	private final void
	SetEdgeInfo() throws lvThrowable
	{
		int  numVtx = TmpPoly().vertex.length;
		for( int i=0; i<numVtx; i++ ) {
			lvRec.SeqPart  radial = TmpPoly().vertex[ i ].radial;
			for( int j=0; j<radial.num; j++ ) {
				int      srcEdgeNo = TmpPoly().radialSeq[ radial.start + j ].srcEdgeNo;
				int      dstEdgeNo = TmpPoly().radialSeq[ radial.start + j ].dstEdgeNo;
				Err().Assert( ( dstEdgeNo >= 0 ), "lvMakePoly.SetEdgeInfo(0)" );
				boolean  reverse   = TmpPoly().radialSeq[ radial.start + j ].dstEdgeReverse;
				SetEdgeMain( srcEdgeNo, dstEdgeNo, reverse );
			}
		}
	}
	
	/**
	 * @param  srcEdgeNo	(( I )) CurSrc().edge 用インデックス
	 * @param  dstEdgeNo	(( I )) CurDst().edge 用インデックス
	 * @param  reverse		(( I )) ラジアル中心の頂点Noから始まっていない場合、true
	 */
	private final void
	SetEdgeMain( int srcEdgeNo, int dstEdgeNo, boolean reverse )
	{
		if( srcEdgeNo < 0 )
			return;
		
		if( CurSrc().edge[ srcEdgeNo ].enableAll == true )
			CurDst().edge[ dstEdgeNo ].allRound = CurSrc().edge[ srcEdgeNo ].allRound;

		if( reverse == false ) {
			if( CurSrc().edge[ srcEdgeNo ].vec0 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec0, CurDst().edge[ dstEdgeNo ].vec[ 0 ] );
			if( CurSrc().edge[ srcEdgeNo ].vec1 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec1, CurDst().edge[ dstEdgeNo ].vec[ 1 ] );
		}
		else {
			if( CurSrc().edge[ srcEdgeNo ].vec0 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec0, CurDst().edge[ dstEdgeNo ].vec[ 1 ] );
			if( CurSrc().edge[ srcEdgeNo ].vec1 != null )
				lvVecDt.Copy( CurSrc().edge[ srcEdgeNo ].vec1, CurDst().edge[ dstEdgeNo ].vec[ 0 ] );
		}
	}
	
	private final void
	FaceProc1() throws lvThrowable
	{
		SetFaceHalfSeqEdge();
	}
	
	private final void
	SetFaceHalfSeqEdge() throws lvThrowable
	{
		int  num = CurDst().faceHalfSeq.length;
		for( int i=0; i<num; i++ )
			CurDst().faceHalfSeq[ i ].edgeNo = -1;
		
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  face = CurDst().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  vtxNo    = CurDst().faceHalfSeq[ face.start + j ].vtxNo;
				int  radialNo = CurDst().faceHalfSeq[ face.start + j ].radialNo;
			
				lvRec.SeqPart  radial = TmpPoly().vertex[ vtxNo ].radial;
				int  edgeNo     = TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeNo;
				boolean reverse = TmpPoly().radialSeq[ radial.start + radialNo ].dstEdgeReverse;
			
				Err().Assert( ( CurDst().faceHalfSeq[ face.start + j ].edgeNo < 0 ), "lvMakePoly.SetFaceHalfSeqEdge(0)" );
				CurDst().faceHalfSeq[ face.start + j ].edgeNo = edgeNo;
				if( reverse == false )
					CurDst().faceHalfSeq[ face.start + j ].edgeIdx = 0;
				else
					CurDst().faceHalfSeq[ face.start + j ].edgeIdx = 1;
			}
		}
	}
	
	private final void
	EdgeProc1() throws lvThrowable
	{
		SetEdgeFace();
	}

	private final void
	SetEdgeFace() throws lvThrowable
	{
		int  num = CurDst().edge.length;
		for( int i=0; i<num; i++ ) {
			for( int j=0; j<2; j++ )
				CurDst().edge[ i ].face[ j ].faceNo = -1;
		}
		
		int  numFace = CurDst().face.length;
		for( int i=0; i<numFace; i++ ) {
			lvRec.SeqPart  face = CurDst().face[ i ];
			for( int j=0; j<face.num; j++ ) {
				int  edgeNo  = CurDst().faceHalfSeq[ face.start + j ].edgeNo;
				int  edgeIdx = CurDst().faceHalfSeq[ face.start + j ].edgeIdx;
				
				Err().Assert( ( CurDst().edge[ edgeNo ].face[ edgeIdx ].faceNo < 0 ), "lvMakePoly.SetEdgeFace(0)" );
				CurDst().edge[ edgeNo ].face[ edgeIdx ].faceNo = i;
				CurDst().edge[ edgeNo ].face[ edgeIdx ].halfNo = j;
			}
		}
	}
	
	private final void
	Finish()
	{
		TmpPoly().vertex			= null;		// Delete( TmpPoly().vertex );
		TmpPoly().radialSeq			= null;		// Delete( TmpPoly().radialSeq );
		TmpPoly().radialOneVtx		= null;		// Delete( TmpPoly().radialOneVtx );
		TmpPoly().alreadyExecOneVtx	= null;		// Delete( TmpPoly().alreadyExecOneVtx );
		TmpPoly().face				= null;		// Delete( TmpPoly().face );
		TmpPoly().faceVtxSeq		= null;		// Delete( TmpPoly().faceVtxSeq );
		TmpPoly().srcNgVtxSeq		= null;		// Delete( TmpPoly().srcNgVtxSeq );
		TmpPoly().numNgFace			= 0;
		TmpPoly().ngFace			= null;		// Delete( TmpPoly().ngFace );
		TmpPoly().dstNgVtxSeq		= null;		// Delete( TmpPoly().dstNgVtxSeq );
		TmpPoly().edge				= null;		// Delete( TmpPoly().edge );
		
		Gbl().curSrc    			= null;		// Delete( Gbl().curSrc );
	}
	
}
