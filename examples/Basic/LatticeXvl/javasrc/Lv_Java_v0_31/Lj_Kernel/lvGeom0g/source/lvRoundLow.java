//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvRoundLow.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Round情報の作成クラス（下位レイヤー）
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvRoundLow extends lvRoot {
	
	private static final double	 def_fact = ( 1.0 / ( 1.0 + lvConst.LV_SQRT1_2 ) );
	private static final double	 min_fact = 0.25;
	private static final double	 max_fact = 0.75;
	
	private static final double
	vx3_fact = ( ( lvConst.LV_SQRT3 - lvConst.LV_SQRT2 )
			   / ( ( 2.0 * lvConst.LV_SQRT1_3 + lvConst.LV_SQRT1_2 / 3.0 - 2.0 * lvConst.LV_SQRT2 / 3.0 )
			   * ( 2.0 - lvConst.LV_SQRT2 ) ) );
		
// -------------------------------------------------------------------
	
	/**
	 * radial に対する「上位レイヤー(topo0)から送られるデータ用（DownRound）の内部クラス」
	 */
	public static class DownRadial {
		
		/** ある radial に対して、対となる頂点の座標			*/
		public lvVector        mateVtxPos	= null;
	
		/** 稜線丸め係数										*/
		public double          edgeRound;
	
		/** 基本丸めベクトル									*/
		public lvVector        roundVec		= null;

		/** 隣接格子は、NG面か？	GS面:false,  NG面:true --- 初期値：	false	*/
		public boolean         isNG			= false;
		
		/** 隣接格子(GS面)の中心位置座標と面法線				*/
		public lvRec.PosNorHi  center		= null;

		/**
		 *  隣接格子(NG面)の中心位置座標						<br>
		 *		基本丸めベクトルの互換性の為に必要
		 */
		public lvVector        centerNG		= null;

		/** 隣接格子(GS面)の面積								*/
		public double          area;

		/** 1round における Iノードの数							*/
		public int             numINode		= 1;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DownRadial( lvGlobal dt )
		{
			mateVtxPos = new lvVector( dt );
			roundVec   = new lvVector( dt );
			center     = new lvRec.PosNorHi( dt );
			centerNG   = new lvVector( dt );
		}
	}
	
	/**
	 * 上位レイヤー(topo0)から送られるデータ用（DownRound）の内部クラス
	 */
	public static class DownRound {
		
		/** 頂点の座標			*/
		public lvVector    vtxPos			= null;
	
		/** 頂点丸め係数		*/
		public double      vtxRound;
		
		/** その頂点のradial数（最大radial.length）		*/
		public int         numRadial;
		/** radial情報の配列（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）	*/
		public DownRadial  radial[]			= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  DownRound( lvGlobal dt )
		{
			vtxPos = new lvVector( dt );
		}
	}
		
// -------------------------------------------------------------------
	
	/**
	 * radial に対する「上位レイヤー(topo0)に送るデータ用（UpRound）の内部クラス」
	 */
	public static class UpRadial {
		
		/** Bezgon用のハンドルベクトル		*/
		public lvVector  handVec		= null;
	
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  UpRadial( lvGlobal dt )
		{
			handVec = new lvVector( dt );
		}
	}
		
	/**
	 * 上位レイヤー(topo0)に送るデータ用（UpRound）の内部クラス
	 */
	public static class UpRound {
		
		/** Bezgon用の頂点位置			*/
		public lvVector  vtxPos			= null;
	
		/** radial情報の配列（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）	*/
		public UpRadial  radial[]		= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  UpRound( lvGlobal dt )
		{
			vtxPos = new lvVector( dt );
		}
	}
	
// -------------------------------------------------------------------

	/**
	 * radial に対する一時的内部クラス（TmpRound0用）
	 */
	private static class TmpRadial0 {
		
		/** その radial の稜線は丸め可能か？	不可能:false, 可能:true		*/
		private boolean  enableEdge;
		
		/** 中間補正後の稜線丸め係数				*/
		private double   tmpNewEdgeRound;
		
		/** 補正後の稜線丸め係数（最終丸め係数）	*/
		private double   newEdgeRound;
	}
	
	/**
	 * Round情報の作成の際、一時的に使用する情報のための内部クラス
	 */
	private static class TmpRound0 {
		
		/** radial内の丸め可能な稜線の数0	*/
		private int         numRoundEdge;
		
		/** radial情報の配列（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）	*/
		private TmpRadial0  radial[]	= null;
		
		/** 当頂点は丸め可能か？	不可能:false, 可能:true		*/
		private boolean     enableVtx;
		
		/** 補正後の頂点丸め係数			*/
		private double      newVtxRound;
	}
	
// -------------------------------------------------------------------

	/**
	 * radial に対する一時的内部クラス1（TmpRound1用）
	 */
	private static class TmpRadial1 {
		
		/** 頂点と面中心の中間位置を補正した位置（GS面用）					*/
		private lvVector  faceCenter	= null;
		
		/**
		 *  頂点と面中心の中間位置を補正した位置（NG面用）					<br>
		 *		基本丸めベクトルの互換性の為に必要
		 */
		private lvVector  faceCenterNG	= null;
		
		/** 「2個の faceCenter を頂点とする稜線」の中間位置を補正した位置	*/
		private lvVector  edgeCenter	= null;
		
		/** UpRadial.handVec 作成に必要な edgeCenter上の法線				*/
		private lvVector  edgeNormal				= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  TmpRadial1( lvGlobal dt )
		{
			faceCenter   = new lvVector( dt );
			faceCenterNG = new lvVector( dt );
			edgeCenter   = new lvVector( dt );
			edgeNormal   = new lvVector( dt );
		}
	}
	
	/**
	 * radial に対する一時的内部クラス1a（TmpRound1用）						<br>
	 * 配列（TmpRound1.radialA[]）内では、TmpRadial1a内の各メンバ変数によって、先頭から何番目までに有効な値が入っているかが異なる
	 */
	private static class TmpRadial1a {
		
		/**
		 * 当radialの、とある「丸め不能な稜線」から次の「丸め不能な稜線 - 1 」までのradial数の配列。					<br>
		 * 「丸め不能な稜線」,「丸め可能な稜線0」,「丸め可能な稜線1」,---, 「丸め可能な稜線(n-1)」となっている場合、	<br>
		 * 「 n + 1 」と記述する。 配列長は、当radialの「丸め不能な稜線」の数となる。
		 */
		private lvRec.SeqPart  numSharpToSharp		= new lvRec.SeqPart();
	}
		
	/**
	 * Round情報の作成の際、一時的に使用する情報のための内部クラス
	 */
	private static class TmpRound1 {
		
		/** radial内の丸め可能な稜線の数1	*/
		private int          numRoundEdgeA;
		
		/**
		 * radial情報の配列（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）。	<br>
		 * 有効な長さは、そのときの頂点におけるradial数である（ TmpRadial1a内の各メンバ変数で共通）
		 */
		private TmpRadial1   radial[]			= null;
		
		/**
		 * radial情報の配列（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）。	<br>
		 * TmpRadial1a内の各メンバ変数によって、先頭から何番目までに有効な値が入っているかは異なる
		 */
		private TmpRadial1a  radialA[]			= null;
		
		/** この radial に NG面が存在するか？	存在しない:false, 存在する:true		*/
		private boolean      hasNG;
		
		/** 当radialの、とある「丸め不能な稜線」の位置		*/
		private int          tmpRadialNoOrg;
		
		/** UpRadial.handVec 作成に必要な面全体の法線( TmpRound0().numRoundEdge >= ( DownRound().numRadial - 1 ) の場合 )	*/
		private lvVector     edgeNormalPoly		= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  TmpRound1( lvGlobal dt )
		{
			edgeNormalPoly = new lvVector( dt );
		}
	}
	
// -------------------------------------------------------------------
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/** カレントの「上位レイヤー(topo0)から送られるデータ」			*/
		private DownRound  curDownRound		= null;
		/** カレントの「上位レイヤー(topo0)に送るデータ」				*/
		private UpRound    curUpRound		= null;

		/** シェル作成の際、一時的に使用する情報0		*/
		private TmpRound0  tmpRound0		= null;

		/** シェル作成の際、一時的に使用する情報1		*/
		private TmpRound1  tmpRound1		= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt ) {
			tmpRound0 = new TmpRound0();
			tmpRound1 = new TmpRound1( dt );
			
			GlobalTmp( dt );
		}
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvSetFaceCenter[]         = null;
		private lvDouble  tdSetFaceCenter[]         = null;
		private lvVector  tvSetEdgeCenter[]         = null;
		private lvDouble  tdSetEdgeCenter[]         = null;
		private lvVector  tvCorrectEdgeCenter[]     = null;
		private lvVector  tvAryCorrectEdgeCenter0[] = null;
		private lvVector  tvAryCorrectEdgeCenter1[] = null;
		private lvVector  tvAryCorrectEdgeCenter2[] = null;
		private lvVector  tvSetFaceCoordSys[]       = null;
		private lvDouble  tdSetFaceCoordSys[]       = null;
		private lvVector  tvArySetEdgeNormal[]      = null;
		private lvVector  tvSetEdgeNormal[]         = null;
		private lvVector  tvSetEdgeNormal2[]        = null;
		private lvVector  tvSetEdgeNormalMain[]     = null;
		private lvVector  tvArySetEdgeNormalMain[]  = null;
		private lvVector  tvSetHandVec[]            = null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvSetFaceCenter     = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetFaceCenter[ i ]     = new lvVector( dt );
			tdSetFaceCenter     = new lvDouble[ 2 ];	for( int i=0; i<2; i++ )	tdSetFaceCenter[ i ]     = new lvDouble( dt );
			tvSetEdgeCenter     = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetEdgeCenter[ i ]     = new lvVector( dt );
			tdSetEdgeCenter     = new lvDouble[ 4 ];	for( int i=0; i<4; i++ )	tdSetEdgeCenter[ i ]     = new lvDouble( dt );
			tvCorrectEdgeCenter = new lvVector[ 8 ];	for( int i=0; i<8; i++ )	tvCorrectEdgeCenter[ i ] = new lvVector( dt );
			tvSetFaceCoordSys   = new lvVector[ 8 ];	for( int i=0; i<8; i++ )	tvSetFaceCoordSys[ i ]   = new lvVector( dt );
			tdSetFaceCoordSys   = new lvDouble[ 2 ];	for( int i=0; i<2; i++ )	tdSetFaceCoordSys[ i ]   = new lvDouble( dt );
			tvSetEdgeNormal     = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvSetEdgeNormal[ i ]     = new lvVector( dt );
			tvSetEdgeNormal2    = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetEdgeNormal2[ i ]    = new lvVector( dt );
			tvSetEdgeNormalMain = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetEdgeNormalMain[ i ] = new lvVector( dt );
			tvSetHandVec        = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvSetHandVec[ i ]        = new lvVector( dt );
		}

		/**
		 * CorrectEdgeCenter() 内のローカル配列 faceCenter[] 用の new 代用バッファ初期化（ Exec() で初期化）
		 * @param  num		(( I )) 配列長（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）
		*/
		private final void
		NewCorrectEdgeCenter( lvGlobal dt, int num )
		{
			tvAryCorrectEdgeCenter0 = new lvVector[ num ];
			
			tvAryCorrectEdgeCenter1 = new lvVector[ num ];
			for( int i=0; i<num; i++ )
				tvAryCorrectEdgeCenter1[ i ] = new lvVector( dt );
			
			tvAryCorrectEdgeCenter2 = new lvVector[ num ];
			for( int i=0; i<num; i++ )
				tvAryCorrectEdgeCenter2[ i ] = new lvVector( dt );
		}

		/**
		 * SetEdgeNormal() 内のローカル配列 edgeCenter[] 用の new 代用バッファ初期化（ Exec() で初期化）
		 * @param  num		(( I )) 配列長（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）
		*/
		private final void
		NewSetEdgeNormal( int num )
		{
			tvArySetEdgeNormal = new lvVector[ num ];
		}

		/**
		 * SetEdgeNormalMain() 内のローカル配列 pntOnEdge[] 用の new 代用バッファ初期化（ Exec() で初期化）
		 * @param  dt		(( I )) グローバルデータ
		 * @param  num		(( I )) 配列長（あるシェル内の頂点の中で、最も多くのradialを持つものを考えた場合、長さはそのradial数となる）
		*/
		private final void
		NewSetEdgeNormalMain( lvGlobal dt, int num )
		{
			tvArySetEdgeNormalMain = new lvVector[ num ];
			for( int i=0; i<num; i++ )
				tvArySetEdgeNormalMain[ i ] = new lvVector( dt );
		}
	}

// -------------------------------------------------------------------

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gRoundLow;
	}
	/** DownRound用のグローバルデータ				*/
	private final DownRound
	DownRound()
	{
		return  Gbl().curDownRound;
	}
	/** UpRound用のグローバルデータ				*/
	private final UpRound
	UpRound()
	{
		return  Gbl().curUpRound;
	}
	/** TmpRound0用のグローバルデータ				*/
	private final TmpRound0
	TmpRound0()
	{
		return  Gbl().tmpRound0;
	}
	/** TmpRound1用のグローバルデータ				*/
	private final TmpRound1
	TmpRound1()
	{
		return  Gbl().tmpRound1;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvRoundLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1頂点を round する
	 * @param  init				(( I )) あるシェルに対して、最初に当関数を実行するときは、true
	 * @param  downRound		(( I )) 上位レイヤー(topo0)から送られるデータ
	 * @param  upRound			(( O )) 上位レイヤー(topo0)に送るデータ
	 */
	public final void
	Exec( boolean init, DownRound downRound, UpRound upRound ) throws lvThrowable
	{
		Gbl().curDownRound = downRound;
		Gbl().curUpRound   = upRound;
		
		if( init == true ) {
			int  num = DownRound().radial.length;
			NewTmpRadial0( num );
			NewTmpRadial1( num );
			NewTmpRadial1a( num );
			
			Gbl().NewCorrectEdgeCenter( global, num );
			Gbl().NewSetEdgeNormal( num );
			Gbl().NewSetEdgeNormalMain( global, num+1 );
		}
		
		SetTmpRound0();
		SetTmpRound1();
	}
	
	private final void
	NewTmpRadial0( int num )
	{
		TmpRound0().radial = new TmpRadial0[ num ];
		for( int i=0; i<num; i++ )
			TmpRound0().radial[ i ] = new TmpRadial0();
	}
	
	private final void
	NewTmpRadial1( int num )
	{
		TmpRound1().radial = new TmpRadial1[ num ];
		for( int i=0; i<num; i++ )
			TmpRound1().radial[ i ] = new TmpRadial1( global );
	}
	
	private final void
	NewTmpRadial1a( int num )
	{
		TmpRound1().radialA = new TmpRadial1a[ num ];
		for( int i=0; i<num; i++ )
			TmpRound1().radialA[ i ] = new TmpRadial1a();
	}
	
	private final void
	SetTmpRound0() throws lvThrowable
	{
		SetRoundVtx();
		SetTmpRadial0();
		
		GetCorrectVtxRound();
		SetTmpRound0Main();

		Err().Assert( ( 0 <= TmpRound0().numRoundEdge && TmpRound0().numRoundEdge <= DownRound().numRadial ),
						"lvRoundLow.SetTmpRound0(0)" );
	}
	
	private final void
	SetRoundVtx()
	{
		if( DownRound().vtxRound > lvEps.a0 )
			TmpRound0().enableVtx = true;
		else
			TmpRound0().enableVtx = false;
	}
	
	private final void
	SetTmpRadial0() throws lvThrowable
	{
		for( int i=0; i<DownRound().numRadial; i++ ) {
			int  nf = ( i + DownRound().numRadial - 1 ) % DownRound().numRadial;
			
			if( TmpRound0().enableVtx == false )
				TmpRound0().radial[ i ].enableEdge = false;
			else if( DownRound().radial[ i ].isNG == true )
				TmpRound0().radial[ i ].enableEdge = false;
			else if( DownRound().radial[ nf ].isNG == true )
				TmpRound0().radial[ i ].enableEdge = false;
			else if( DownRound().radial[ i ].edgeRound <= lvEps.a0 )
				TmpRound0().radial[ i ].enableEdge = false;
			else
				TmpRound0().radial[ i ].enableEdge = true;
		}
		
		for( int i=0; i<DownRound().numRadial; i++ ) {
			if( TmpRound0().radial[ i ].enableEdge == true )
				TmpRound0().radial[ i ].tmpNewEdgeRound = DownRound().radial[ i ].edgeRound;
			else
				TmpRound0().radial[ i ].tmpNewEdgeRound = 0.0;
			Err().Assert( ( TmpRound0().radial[ i ].tmpNewEdgeRound >= 0.0 ), "lvRoundLow.SetTmpRadial0(0)" );
		}
		
		TmpRound0().numRoundEdge = 0;
		for( int i=0; i<DownRound().numRadial; i++ ) {
			if( TmpRound0().radial[ i ].enableEdge == true )
				TmpRound0().numRoundEdge++;
		}
	}
	
	private final void
	GetCorrectVtxRound()
	{
		TmpRound0().newVtxRound = 1.0;
		if( TmpRound0().enableVtx == true )
			TmpRound0().newVtxRound = DownRound().vtxRound;
			
		if( DownRound().numRadial == 3 && TmpRound0().numRoundEdge == DownRound().numRadial )
			TmpRound0().newVtxRound *= vx3_fact;
	}
	
	private final void
	SetTmpRound0Main()
	{
		double  roundB = 0.0;
		double  roundF = 0.0;
		
		for( int i=0; i<DownRound().numRadial; i++ ) {
			int  nb = ( i + DownRound().numRadial - 1 ) % DownRound().numRadial;
			int  nf = ( i + 1 ) % DownRound().numRadial;
			if( TmpRound0().radial[ nb ].enableEdge == true && TmpRound0().radial[ nf ].enableEdge == true ) {
				roundB = TmpRound0().radial[ nb ].tmpNewEdgeRound * def_fact;
				roundF = TmpRound0().radial[ nf ].tmpNewEdgeRound * def_fact;
			}
			else if( TmpRound0().radial[ nb ].enableEdge == true )
				roundB = roundF = TmpRound0().radial[ nb ].tmpNewEdgeRound * def_fact;
			else if( TmpRound0().radial[ nf ].enableEdge == true )
				roundB = roundF = TmpRound0().radial[ nf ].tmpNewEdgeRound * def_fact;
			else {
				if( DownRound().numRadial > 2 )
					roundB = roundF = 0.5;
				else
					roundB = roundF = def_fact;
			}
		
			double  w = TmpRound0().newVtxRound * ( roundB + roundF ) * 0.5;
			if( w < min_fact )
				w = min_fact;
			else if( w > max_fact )
				w = max_fact;
			TmpRound0().radial[ i ].newEdgeRound = w;
		}
	}
	
	private final void
	SetTmpRound1() throws lvThrowable
	{
		SetFaceCenter();
		SetEdgeCenter();
		SetNewVtxPos();
		CorrectEdgeCenter();
		SetEdgeNormal();
		SetHandVec();
	}
	
	private final void
	SetFaceCenter()
	{
		lvVector  posB = Gbl().tvSetFaceCenter[ 0 ];			// posB = new lvVector( global );
		lvVector  posF = Gbl().tvSetFaceCenter[ 1 ];			// posF = new lvVector( global );
		lvVector  q    = Gbl().tvSetFaceCenter[ 2 ];			// q    = new lvVector( global );
		lvDouble  dbl  = Gbl().tdSetFaceCenter[ 0 ];			// dbl  = new lvDouble( global );
		
		lvVector  pos = DownRound().vtxPos;
		TmpRound1().hasNG         = false;		
		TmpRound1().numRoundEdgeA = 0;
		
		for( int i=0; i<DownRound().numRadial; i++ ) {
			int  nf = ( i + 1 ) % DownRound().numRadial;
			
			posB.Assign( ( pos.Add( DownRound().radial[ i  ].mateVtxPos ) ).Mul( 0.5 ) );
												// posB = ( pos + DownRound().radial[ i  ].mateVtxPos ) * 0.5;
			posF.Assign( ( pos.Add( DownRound().radial[ nf ].mateVtxPos ) ).Mul( 0.5 ) );
												// posF = ( pos + DownRound().radial[ nf ].mateVtxPos ) * 0.5;
			
			lvVector  posC;
			if( DownRound().radial[ i ].isNG == true )
				posC = DownRound().radial[ i ].centerNG;
			else
				posC = DownRound().radial[ i ].center.pos;
			
			double  u = TmpRound0().radial[ i  ].newEdgeRound;
			double  v = TmpRound0().radial[ nf ].newEdgeRound;
			
			q.SetXYZ( 0.0, 0.0, 0.0 );
			q.AddAssign( ( dbl.Set( ( 1.0-u ) * ( 1.0-v ) ) ).Mul( pos  ) );	// q += ( 1.0-u ) * ( 1.0-v ) * pos
			q.AddAssign( ( dbl.Set(     u     * ( 1.0-v ) ) ).Mul( posB ) );	// q +=     u     * ( 1.0-v ) * posB
			q.AddAssign( ( dbl.Set( ( 1.0-u ) *     v     ) ).Mul( posF ) );	// q += ( 1.0-u ) *     v     * posF
			q.AddAssign( ( dbl.Set(     u     *     v     ) ).Mul( posC ) );	// q +=     u     *     v     * posC
			if( DownRound().radial[ i ].isNG == true )
				TmpRound1().radial[ i ].faceCenterNG.Assign( q );
			else
				TmpRound1().radial[ i ].faceCenter.Assign( q );

			if( DownRound().radial[ i ].isNG == true )
				TmpRound1().hasNG = true;
			
			boolean  enableEdge;
			if( DownRound().radial[ i ].isNG == true )
				enableEdge = false;
			else if( DownRound().radial[ nf ].isNG == true )
				enableEdge = false;
			if( DownRound().radial[ i ].edgeRound <= lvEps.a0 )
				enableEdge = false;
			else
				enableEdge = true;
			
			if( enableEdge == true )
				TmpRound1().numRoundEdgeA++;
		}
	}
	
	private final void
	SetEdgeCenter() throws lvThrowable
	{
		lvVector  posB = Gbl().tvSetEdgeCenter[ 0 ];			// posB = new lvVector( global );
		lvVector  posF = Gbl().tvSetEdgeCenter[ 1 ];			// posF = new lvVector( global );
		lvDouble  dbl0 = Gbl().tdSetEdgeCenter[ 0 ];			// dbl0 = new lvDouble( global );
		lvDouble  dbl1 = Gbl().tdSetEdgeCenter[ 1 ];			// dbl1 = new lvDouble( global );

		lvVector  pos  = DownRound().vtxPos;

		for( int i=0; i<DownRound().numRadial; i++ ) {
			int  nb = ( i + DownRound().numRadial - 1 ) % DownRound().numRadial;
			
			if( TmpRound0().radial[ i ].enableEdge == true ) {
				Err().Assert( ( DownRound().radial[ nb ].isNG == false ), "lvRoundLow.SetEdgeCenter(0)" );
				Err().Assert( ( DownRound().radial[ i  ].isNG == false ), "lvRoundLow.SetEdgeCenter(1)" );
				lvVector  edgeCenter = TmpRound1().radial[ i ].edgeCenter;
				edgeCenter.Assign( ( TmpRound1().radial[ nb ].faceCenter.Add( TmpRound1().radial[ i ].faceCenter ) ).Mul( 0.5 ) );
								// edgeCenter = ( TmpRound1().radial[ i ].faceCenter + TmpRound1().radial[ nf ].faceCenter ) * 0.5;
				if( DownRound().numRadial == 2 ) {
					posB.Assign( ( pos.Add( DownRound().radial[ nb ].mateVtxPos ) ).Mul( 0.5 ) );
												// posB = ( pos + DownRound().radial[ i  ].mateVtxPos ) * 0.5;
					posF.Assign( ( pos.Add( DownRound().radial[ i  ].mateVtxPos ) ).Mul( 0.5 ) );
												// posF = ( pos + DownRound().radial[ nf ].mateVtxPos ) * 0.5;
					edgeCenter.AddAssign( ( posF.Sub( posB ) ).Div( 6.0 ) );	// edgeCenter += ( posF - posB ) / 6.0;
				}
			}
			else {
				double    t = TmpRound0().radial[ i ].newEdgeRound * 0.5;
				lvVector  p = DownRound().radial[ i ].mateVtxPos;
				lvVector  edgeCenter = TmpRound1().radial[ i ].edgeCenter;
				edgeCenter.Assign( ( ( dbl0.Set( 1.0 - t ) ).Mul( pos ) ).Add( ( dbl1.Set( t ) ).Mul( p ) ) );
			}
		}
	}
	
	private final void
	SetNewVtxPos() throws lvThrowable
	{
		if( TmpRound0().numRoundEdge ==   DownRound().numRadial ||
			TmpRound0().numRoundEdge == ( DownRound().numRadial - 1 ) )
		{
			UpRound().vtxPos.SetXYZ( 0.0, 0.0, 0.0 );
			for( int i=0; i<DownRound().numRadial; i++ ) {
				UpRound().vtxPos.AddAssign( TmpRound1().radial[ i ].faceCenter );
										// UpRound().vtxPos += TmpRound1().radial[ i ].faceCenter;
			}
			UpRound().vtxPos.DivAssign( DownRound().numRadial );	// UpRound().vtxPos /= DownRound().numRadial;
		}
		else if( TmpRound0().numRoundEdge == ( DownRound().numRadial - 2 )  && TmpRound0().enableVtx == true ) {
			UpRound().vtxPos.SetXYZ( 0.0, 0.0, 0.0 );
			int  cnt = 0;
			for( int i=0; i<DownRound().numRadial; i++ ) {
				if( TmpRound0().radial[ i ].enableEdge == false ) {
					UpRound().vtxPos.AddAssign( TmpRound1().radial[ i ].edgeCenter );
										// UpRound().vtxPos += TmpRound1().radial[ i ].edgeCenter;
					cnt++;
				}
			}
			Err().Assert( ( cnt == 2 ), "lvRoundLow.SetNewVtxPos(0)" );
			UpRound().vtxPos.DivAssign( 2 );						// UpRound().vtxPos /= 2;
		}
		else
			UpRound().vtxPos.Assign( DownRound().vtxPos );
	}	

	private final void
	CorrectEdgeCenter() throws lvThrowable
	{
		lvVector  x = Gbl().tvCorrectEdgeCenter[ 0 ];				// x = new lvVector( global );
		lvVector  y = Gbl().tvCorrectEdgeCenter[ 1 ];				// y = new lvVector( global );
		lvVector  z = Gbl().tvCorrectEdgeCenter[ 2 ];				// z = new lvVector( global );

		lvVector  faceCenter[] = Gbl().tvAryCorrectEdgeCenter0;		// faceCenter[] = new lvVector[ DownRound().numRadial ];
		lvVector  faceNormal[] = Gbl().tvAryCorrectEdgeCenter1;		// faceNormal[] = new lvVector[ DownRound().numRadial ];
		lvVector  edgeCenter[] = Gbl().tvAryCorrectEdgeCenter2;		// edgeCenter[] = new lvVector[ DownRound().numRadial ];
		
		for( int i=0; i<DownRound().numRadial; i++ ) {
			if( DownRound().radial[ i ].isNG == true )
				faceCenter[ i ] = TmpRound1().radial[ i ].faceCenterNG;
			else
				faceCenter[ i ] = TmpRound1().radial[ i ].faceCenter;
			
			faceNormal[ i ].Assign( DownRound().radial[ i ].center.normal );
			faceNormal[ i ].MulAssign(  Math.sqrt( DownRound().radial[ i ].area ) * 0.5 );
		}
		
		for( int i=0; i<DownRound().numRadial; i++ ) {
			if( SetFaceCoordSys( i, faceCenter, faceNormal, x, y, z ) == false )
				continue;
			
			lvVector  roundVec = DownRound().radial[ i ].roundVec;

			edgeCenter[ i ].Assign( UpRound().vtxPos );						// edgeCenter[ i ]  = UpRound().vtxPos;
			edgeCenter[ i ].AddAssign( x.Mul( roundVec.x ) );				// edgeCenter[ i ] += x * roundVec.x;
			edgeCenter[ i ].AddAssign( y.Mul( roundVec.y ) );				// edgeCenter[ i ] += y * roundVec.y;
			edgeCenter[ i ].AddAssign( z.Mul( roundVec.z ) );				// edgeCenter[ i ] += z * roundVec.z;
		}
		for( int i=0; i<DownRound().numRadial; i++ )
			TmpRound1().radial[ i ].edgeCenter.Assign( edgeCenter[ i ] );
	}
	
	private final boolean
	SetFaceCoordSys( int radialNo0, lvVector faceCenter[], lvVector faceNormal[], lvVector x, lvVector y,
			lvVector z ) throws lvThrowable
	{
		lvDouble  l = Gbl().tdSetFaceCoordSys[ 0 ];			// l = new lvDouble( global );
		
		int  radialNo = ( radialNo0 + DownRound().numRadial - 1 ) % DownRound().numRadial;
							// 基本丸めベクトルの互換性の為の補正

		int  radialB  = ( radialNo +  DownRound().numRadial - 1 ) % DownRound().numRadial;
		int  radialF  = ( radialNo + 1 ) % DownRound().numRadial;
		int  radialF2 = ( radialNo + 2 ) % DownRound().numRadial;

		if( DownRound().numRadial > 2 ) {
			x.Assign( ( faceCenter[ radialNo ].Add( faceCenter[ radialB ] ) ).Mul( 0.5 ) );
							// x  = ( faceCenter[ radialNo ] + faceCenter[ radialB ] ) * 0.5;
			x.SubAssign( ( faceCenter[ radialF ].Add( faceCenter[ radialF2 ] ) ).Mul( 0.5 ) );
							// x -= ( faceCenter[ radialF ] + faceCenter[ radialF2 ] ) * 0.5;
			x.MulAssign( 0.5 );
							// x *= 0.5;
			
			y.Assign( ( faceCenter[ radialF ].Add( faceCenter[ radialNo ] ) ).Mul( 0.5 ) );
							// y  = ( faceCenter[ radialF ] + faceCenter[ radialNo ] ) * 0.5;
			y.SubAssign( UpRound().vtxPos );
							// y -= UpRound().vtxPos;
			
			z.Normal( faceCenter, DownRound().numRadial, l );
			z.NegAssign();												// z = -z;
			z.MulAssign( l.val );										// z *= l;
		}
		else {
			x.Assign( ( faceCenter[ radialNo ].Sub( faceCenter[ radialF ] ) ).Mul( 0.5 ) );
							// x  = ( faceCenter[ radialNo ] - faceCenter[ radialF ] ) * 0.5;
			
			if( x.IsZero() == false ) {
				y.Assign( TmpRound1().radial[ radialF ].edgeCenter.Sub( UpRound().vtxPos ) );
								// y  = TmpRound1().radial[ radialF ].edgeCenter - UpRound().vtxPos;
			
				z.Assign( faceCenter[ 1 ] );
				z.NormalAssign( TmpRound1().radial[ 1 ].edgeCenter, faceCenter[ 0 ], TmpRound1().radial[ 0 ].edgeCenter, l );
			}
			else {
				lvVector  normal  = faceNormal[ radialNo ];
				lvVector  normalF = faceNormal[ radialF  ];
				if( normal.IsZero() == normalF.IsZero() )
					return  false;
				if( normalF.IsZero() == true )
					x.Assign( normal.Neg() );
				else
					x.Assign( normalF.Neg() );
				
				y.Assign( TmpRound1().radial[ radialF ].edgeCenter.Sub( UpRound().vtxPos ) );
								// y  = TmpRound1().radial[ radialF ].edgeCenter - UpRound().vtxPos;
				
				z.Assign( x.Cross( y ) );
				l.val = z.Length();
			}
		}
		if( x.IsZero() == true || y.IsZero() == true || Eps().IsZero( l.val ) == true )
			return  false;
			
		z.DivAssign( Math.sqrt( l.val ) );		// z /= Math.sqrt( l );
		
		lvVector  v1 = Gbl().tvSetFaceCoordSys[ 0 ];			// v1 = new lvVector( global );
		
		v1.Assign( TmpRound1().radial[ radialF ].edgeCenter.Sub( UpRound().vtxPos ) );
							// v1  = TmpRound1().radial[ radialF ].faceCenter - UpRound().vtxPos;
		if( y.IsSame( v1 ) == true )
			return  true;
		
		double  d0 = y.Length();
		double  d1 = v1.Length();
		if( Eps().IsZero( d0 ) || Eps().IsZero( d1 ) )
			return  false;
		double  k = d1 / d0;
		
		lvVector  w0 = Gbl().tvSetFaceCoordSys[ 1 ];			// w0 = new lvVector( global );
		lvVector  w1 = Gbl().tvSetFaceCoordSys[ 2 ];			// w1 = new lvVector( global );
		lvVector  u  = Gbl().tvSetFaceCoordSys[ 3 ];			// u  = new lvVector( global );
		
		w0.Assign( y.Div(  d0 ) );								// w0 = y  / d0;
		w1.Assign( v1.Div( d1 ) );								// w1 = v1 / d1;
		u.Assign( ( w0.Cross( w1 ) ).Unit( lvEps.a0 ) );		// u  = ( w0 % w1 ).Unit( lvEps.a0 );
		if( u.IsZero() == false ) {
			double  a = w0.Angle( w1 );
			x.Rotate( u, a );
			y.Rotate( u, a );
			z.Rotate( u, a );
		}
		
		x.MulAssign( k );				// x *= k;
		y.MulAssign( k );				// y *= k;
		z.MulAssign( k );				// z *= k;
		Err().Assert( ( v1.IsSame( y ) == true ), "lvRoundLow.SetFaceCoordSys(0)" );
		
		return  true;
	}
	
	private final void
	SetEdgeNormal() throws lvThrowable
	{
		lvVector  normal = Gbl().tvSetEdgeNormal[ 0 ];			// normal = new lvVector( global );

		lvVector  edgeCenter[] = Gbl().tvArySetEdgeNormal;		// edgeCenter[] = new lvVector[ DownRound().numRadial ];
		for( int i=0; i<DownRound().numRadial; i++ )
			edgeCenter[ i ] = TmpRound1().radial[ i ].edgeCenter;
			
		if( TmpRound0().numRoundEdge >= ( DownRound().numRadial - 1 ) ) {
			normal.Normal( edgeCenter, DownRound().numRadial );
			TmpRound1().edgeNormalPoly.Assign( normal );
		}
		else {
			if( TmpRound0().numRoundEdge == ( DownRound().numRadial - 2 ) && TmpRound0().enableVtx == true )
				SetEdgeNormal2();
			if( TmpRound0().enableVtx == false && TmpRound1().hasNG == true &&
				TmpRound1().numRoundEdgeA == ( DownRound().numRadial - 2 ) )
			{
				SetEdgeNormalNG();
			}
		
			SetFactForEdgeNormal();
			SetEdgeNormalMain();
		}
	}

	private final void
	SetEdgeNormal2() throws lvThrowable
	{
		lvVector  v0 = Gbl().tvSetEdgeNormal2[ 0 ];			// v0 = new lvVector( global );
		lvVector  v1 = Gbl().tvSetEdgeNormal2[ 1 ];			// v1 = new lvVector( global );
		lvVector  v2 = Gbl().tvSetEdgeNormal2[ 2 ];			// v2 = new lvVector( global );

		int  i1 = -1;
		int  i2 = -1;
		for( int i=0; i<DownRound().numRadial; i++ ) {
			if( TmpRound0().radial[ i ].enableEdge == false ) {
				if( i1 < 0 )
					i1 = i;
				else if( i2 < 0 ) {
					i2 = i;
					break;
				}
			}
		}
		Err().Assert( ( i1 >= 0 && i2 >= 0 ), "lvRoundLow.SetEdgeNormal2(0)" );
		
		v1.Assign( TmpRound1().radial[ i1 ].edgeCenter.Sub( UpRound().vtxPos ) );
					// v1 = TmpRound1().radial[ i1 ].edgeCenter - UpRound().vtxPos;
		v2.Assign( TmpRound1().radial[ i2 ].edgeCenter.Sub( UpRound().vtxPos ) );
					// v2 = TmpRound1().radial[ i2 ].edgeCenter - UpRound().vtxPos;
		v0.Assign( ( v2.Sub( v1 ) ).Unit() );		// v0 = ( v2 - v1 ).Unit();
		
		lvVector  edgeCenter0 = TmpRound1().radial[ i1 ].edgeCenter;
		lvVector  edgeCenter1 = TmpRound1().radial[ i2 ].edgeCenter;
		if( v0.IsZero( lvEps.e0 ) == false ) {
			edgeCenter0.Assign( UpRound().vtxPos.Add( ( v0.DotR( v1 ) ).Mul( v0 ) ) );
								// edgeCenter0 = UpRound().vtxPos + ( v0 * v1 ) * v0;
			edgeCenter1.Assign( UpRound().vtxPos.Add( ( v0.DotR( v2 ) ).Mul( v0 ) ) );
								// edgeCenter1 = UpRound().vtxPos + ( v0 * v2 ) * v0;
		}
	}
	
	private final void
	SetEdgeNormalNG() throws lvThrowable
	{
		for( int i=0; i<DownRound().numRadial; i++ ) {
			Err().Assert( ( TmpRound0().radial[ i ].enableEdge == false ), "lvRoundLow.SetEdgeNormalNG(0)" );

			int  nf = ( i + 1 ) % DownRound().numRadial;
			
			boolean  enableEdge;
			if( DownRound().radial[ i ].isNG == true )
				enableEdge = false;
			else if( DownRound().radial[ nf ].isNG == true )
				enableEdge = false;
			if( DownRound().radial[ i ].edgeRound <= lvEps.a0 )
				enableEdge = false;
			else
				enableEdge = true;
			
			if( enableEdge == false )
				continue;
				
			TmpRound0().radial[ i ].enableEdge = true;
			TmpRound0().numRoundEdge++;
		}
		Err().Assert( ( TmpRound0().numRoundEdge <= ( DownRound().numRadial - 2 ) ), "lvRoundLow.SetEdgeNormalNG(0)" );
	}
	
	private final void
	SetFactForEdgeNormal() throws lvThrowable
	{
		boolean  hasNonRadial = false;
		TmpRound1().tmpRadialNoOrg = 0;
		for( int i=0; i<DownRound().numRadial; i++ ) {
			if( TmpRound0().radial[ i ].enableEdge == false ) {
				TmpRound1().tmpRadialNoOrg = i;
				hasNonRadial = true;
				break;
			}
		}
		Err().Assert( ( hasNonRadial == true ), "lvRoundLow.SetFactForEdgeNormal(0)" );
		
		int  numNonRound = DownRound().numRadial - TmpRound0().numRoundEdge;
		int  roundNoOrg  = ( TmpRound1().tmpRadialNoOrg + 1 ) % DownRound().numRadial;
		
		int  rCnt = 0;
		for( int i=0; i<numNonRound; i++ ) {
			int  eCnt = 0;
			int  rNo = ( rCnt + roundNoOrg ) % DownRound().numRadial;
			while( TmpRound0().radial[ rNo ].enableEdge == true ) {
				eCnt++;
				rCnt++;
				rNo = ( rCnt + roundNoOrg ) % DownRound().numRadial;
			}
			
			TmpRound1().radialA[ i ].numSharpToSharp.num = eCnt + 1;
			rCnt++;
		}
		Err().Assert( ( rCnt == DownRound().numRadial ), "lvRoundLow.SetFactForEdgeNormal(1)" );
		
		int  cnt = 0;
		for( int i=0; i<numNonRound; i++ ) {
			TmpRound1().radialA[ i ].numSharpToSharp.start = cnt;
			cnt += TmpRound1().radialA[ i ].numSharpToSharp.num;
		}
		Err().Assert( ( cnt == DownRound().numRadial ), "lvRoundLow.SetFactForEdgeNormal(2)" );
	}
	
	private final void
	SetEdgeNormalMain() throws lvThrowable
	{
		lvVector  normal      = Gbl().tvSetEdgeNormalMain[ 0 ];		// normal      = new lvVector( global );
		lvVector  v0          = Gbl().tvSetEdgeNormalMain[ 1 ];		// v0          = new lvVector( global );
		lvVector  n1          = Gbl().tvSetEdgeNormalMain[ 2 ];		// n1          = new lvVector( global );
		lvVector  pntOnEdge[] = Gbl().tvArySetEdgeNormalMain;		// pntOnEdge[] = new lvVector[ DownRound().radial.length+1 ];
		//															// for( int i=0; i<DownRound().radial.length+1; i++ )
		//															//     pntOnEdge[ i ] = new lvVector( global );
			
		int  numNonRound = DownRound().numRadial - TmpRound0().numRoundEdge;
		
		for( int i=0; i<numNonRound; i++ ) {
			pntOnEdge[ 0 ].Assign( UpRound().vtxPos );
			
			int  startOfs =  TmpRound1().tmpRadialNoOrg + TmpRound1().radialA[ i ].numSharpToSharp.start;
			for( int j=0; j<( TmpRound1().radialA[ i ].numSharpToSharp.num + 1 ); j++ ) {
				int  rNo = ( startOfs + j ) % DownRound().numRadial;
				pntOnEdge[ j+1 ].Assign( TmpRound1().radial[ rNo ].edgeCenter );
			}
			normal.Normal( pntOnEdge, ( TmpRound1().radialA[ i ].numSharpToSharp.num + 2 ) );
			
			int  j0 = TmpRound1().radialA[ i ].numSharpToSharp.num + 1;
			if( pntOnEdge[ 0 ].IsLine( pntOnEdge[ 1 ], pntOnEdge[ j0 ] ) == true ) {
				v0.Assign( ( pntOnEdge[ j0 ].Sub( pntOnEdge[ 1 ] ) ).Unit() );
									// v0 = ( pntOnEdge[ j0 ] - pntOnEdge[ 1 ] ).Unit();
				if( normal.IsZero() == false && v0.IsZero() == false && normal.IsPara( v0 ) == false ) {
					normal.SubAssign( ( normal.DotR( v0 ) ).Mul( v0 ) );		// normal -= ( normal * v0 ) * v0;
					normal.UnitAssign();
				}
				n1.Assign( normal.Neg() );										// normal.Neg()  ***  -normal
			}
			else {
				n1.Assign( pntOnEdge[ 0 ] );
				n1.NormalAssign( pntOnEdge[ 1 ], pntOnEdge[ j0 ] );
				if( normal.Dot( n1 ) < 0.0 )
					n1.NegAssign();													// n1 = -n1;
				n1.NegAssign();													// n1 = -n1;
			}
			
			for( int j=0; j<TmpRound1().radialA[ i ].numSharpToSharp.num; j++ ) {
				int  rNo = ( startOfs + j ) % DownRound().numRadial;
				TmpRound1().radial[ rNo ].edgeNormal.Assign( n1 );
			}
		}
	}
	
	private final void
	SetHandVec()
	{
		lvVector  v0 = Gbl().tvSetHandVec[ 0 ];		// v0 = new lvVector( global );
		
		if( TmpRound0().numRoundEdge >= ( DownRound().numRadial - 1 ) ) {
			for( int i=0; i<DownRound().numRadial; i++ ) {
				v0.Assign( ( TmpRound1().radial[ i ].edgeCenter.Sub( UpRound().vtxPos ) ).Mul( 4.0 / 3.0 ) );
								// v0 = ( TmpRound1().radial[ i ].edgeCenter - UpRound().vtxPos ) * ( 4.0 / 3.0 );
				v0.MulAssign( GetMulFactForHandVec( i ) );		// v0 *= GetMulFactForHandVec( i );
				v0.SubAssign( ( v0.DotR( TmpRound1().edgeNormalPoly ) ).Mul( TmpRound1().edgeNormalPoly ) );
								// v0 -= ( v0 * TmpRound1().edgeNormalPoly ) * TmpRound1().edgeNormalPoly;
				UpRound().radial[ i ].handVec.Assign( v0 );
			}
		}
		else {
			for( int i=0; i<DownRound().numRadial; i++ ) {
				v0.Assign( ( TmpRound1().radial[ i ].edgeCenter.Sub( UpRound().vtxPos ) ).Mul( 4.0 / 3.0 ) );
								// v0 = ( TmpRound1().radial[ i ].edgeCenter - UpRound().vtxPos ) * ( 4.0 / 3.0 );
				v0.MulAssign( GetMulFactForHandVec( i ) );		// v0 *= GetMulFactForHandVec( i );
				if( TmpRound0().radial[ i ].enableEdge == true ) {
					v0.SubAssign( ( v0.DotR( TmpRound1().radial[ i ].edgeNormal ) ).Mul( TmpRound1().radial[ i ].edgeNormal ) );
								// v0 -= ( v0 * TmpRound1().radialA[ i ].edgeNormal ) * TmpRound1().radialA[ i ].edgeNormal;
				}
				UpRound().radial[ i ].handVec.Assign( v0 );
			}
		}
	}
	
	private final double
	GetMulFactForHandVec( int radialNo )
	{
		if( DownRound().numRadial <= 2 )
			return  1.0;
		else if( DownRound().radial[ radialNo ].numINode <= 1 )
			return  1.0;
		
		return  4.0 / ( 3.0 * DownRound().radial[ radialNo ].numINode );
	}
	
	/**
	 * round を終了する
	 */
	public final void
	Finish()
	{
		Gbl().curDownRound	= null;		// Delete( Gbl().curDownRound  );
		TmpRound0().radial  = null;		// Delete( TmpRound0().radial  );
		TmpRound1().radial  = null;		// Delete( TmpRound1().radial  );
		TmpRound1().radialA = null;		// Delete( TmpRound1().radialA );
	}
	
}
