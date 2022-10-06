//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeCbd.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * DivPoly情報の作成クラス（下位レイヤー）
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvMakeCbd extends lvRoot {
	
	private static final int  TYPE1		= 1;
	private static final int  TYPE2		= 2;
	private static final int  TYPE3		= 3;
	private static final int  TYPE4		= 4;
	private static final int  TYPE5		= 5;
	private static final int  TYPE6		= 6;
	private static final int  TYPE7		= 7;
	private static final int  TYPE8		= 8;
	private static final int  TYPE9		= 9;
	
// -------------------------------------------------------------------

	public static class DownCbdOne {
		
		/** 頂点位置				*/
		public lvVector  vtx		= null;
		
		/** 頂点上微分ベクトル方向制御点間ベクトル		*/
		public lvVector  cVec		= null;
		
		/** 頂点上の実パッチCBD方向制御点間ベクトル		*/
		public lvVector  bCbd		= null;
		
		/**
		 *  k1値取得の為の隣接稜線対岸頂点（3,4角パッチ用)		<br>
		 *  3,4角パッチ以外では、null
		 */
		public lvVector  antiVtx	= null;

		public DownCbdOne( lvGlobal dt )
		{
			vtx  = new lvVector( dt );
			cVec = new lvVector( dt );
			bCbd = new lvVector( dt );
		}
	}
	
	public static class DownCbd {
		
		/** 稜線両端上の情報		*/
		public DownCbdOne  info[/*2*/]		= null;
		
		/** 面法線					*/
		public lvVector    faceNormal		= null;
		
		/**
		 *  k1値取得の為の対岸点（3,4角パッチ用)				<br>
		 *  3,4角パッチ以外では、null
		 */
		public lvVector    antiCenter		= null;
		
		public DownCbd( lvGlobal dt )
		{
			info = new DownCbdOne[ 2 ];
			for( int i=0; i<2; i++ )
				info[ i ] = new DownCbdOne( dt );
				
			faceNormal = new lvVector( dt );
		}
	}
	
	public static class UpCbdOne {
		
		/** 基礎パッチCBDベクトル		*/
		public lvVector  aCbd		= null;
		
		/** k要素		*/
		double  k;
		
		/** h要素		*/
		double  h;
		
		public UpCbdOne( lvGlobal dt )
		{
			aCbd = new lvVector( dt );
		}
	}
	
	public static class UpCbd {
		
		/** 稜線両端上のCBD情報		*/
		public UpCbdOne  cbd[/*2*/]		= null;
		
		/** k1要素		*/
		public double    k1;
		
		public UpCbd( lvGlobal dt )
		{
			cbd = new UpCbdOne[ 2 ];
			for( int i=0; i<2; i++ )
				cbd[ i ] = new UpCbdOne( dt );
		}
	}
		
	public static class UpHalfCbd {
		
		/** 稜線両端上のCBD情報		*/
		public UpCbdOne  cbd[/*3*/]		= null;
		
		/** k1要素		*/
		public double    k1[/*2*/]		= new double[ 2 ];
		
		
		public UpHalfCbd( lvGlobal dt )
		{
			cbd = new lvMakeCbd.UpCbdOne[ 3 ];
			for( int i=0; i<3; i++ )
				cbd[ i ] = new lvMakeCbd.UpCbdOne( dt );
		}
	}
		
	
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		private DownCbd    curDownCbd		= null;
		private UpCbd      curUpCbd			= null;
		private UpHalfCbd  curUpHalfCbd		= null;
		
		private double     lenRatio[]		= new double[ 2 ];
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Global( lvGlobal dt )
		{
			GlobalTmp( dt );
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvDouble  tdExecMain[]				= null;
		private lvVector  tvExecMain[]				= null;
		private lvVector  tvGetType[]				= null;
		private lvVector  tvGetAdir[]				= null;
		private lvVector  tvExec3or4Main[]			= null;
		private lvVector  tvLenRatio[]				= null;
		private lvVector  tvHandLenCenter[]			= null;
		private lvVector  tvAryPosition[/*4*/]		= null;
		private UpCbd     tuExecHalf[]				= null;
		private lvVector  tvSetACbd[]				= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tdExecMain      = new lvDouble[ 4 ];	for( int i=0; i<4; i++ )	tdExecMain[ i ]      = new lvDouble( dt );
			tvExecMain      = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvExecMain[ i ]      = new lvVector( dt );
			tvGetType       = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvGetType[ i ]       = new lvVector( dt );
			tvGetAdir       = new lvVector[ 8 ];	for( int i=0; i<8; i++ )	tvGetAdir[ i ]       = new lvVector( dt );
			tvExec3or4Main  = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvExec3or4Main[ i ]  = new lvVector( dt );
			tvLenRatio      = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvLenRatio[ i ]      = new lvVector( dt );
			tvHandLenCenter = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvHandLenCenter[ i ] = new lvVector( dt );
			tvAryPosition   = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryPosition[ i ]   = new lvVector( dt );
			tuExecHalf      = new UpCbd[ 2 ];		for( int i=0; i<2; i++ )	tuExecHalf[ i ]      = new UpCbd( dt );
			tvSetACbd       = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvSetACbd[ i ]       = new lvVector( dt );
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gMakeCbd;
	}
	/** DownCbd用のグローバルデータ				*/
	private final DownCbd
	DownCbd()
	{
		return	Gbl().curDownCbd;
	}
	/** UpCbd用のグローバルデータ				*/
	private final UpCbd
	UpCbd()
	{
		return	Gbl().curUpCbd;
	}
	/** UpHalfCbd用のグローバルデータ				*/
	private final UpHalfCbd
	UpHalfCbd()
	{
		return	Gbl().curUpHalfCbd;
	}


// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lvMakeCbd( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public final double
	Alpha()
	{
		return 0.05;
	}
	
	public final double
	Beta()
	{
		return 2.0 * Math.sin( ( 1.0/2.0 ) * 5.0 * lvConst.LV_PI / 180.0 );
	}

	public final void
	Exec( DownCbd downCbd, UpCbd upCbd ) throws lvThrowable
	{
		Gbl().curDownCbd = downCbd;
		Gbl().curUpCbd   = upCbd;
		
		for( int i=0; i<2; i++ )
			ExecMain( i );
			
		Exec3or4();
	}

	private final void
	ExecMain( int n ) throws lvThrowable
	{
		int  type = GetType( n );
		
		double  aLen = GetAlen( type, n );
		lvVector  aDir = Gbl().tvExecMain[ 0 ];					// aDir = new lvVector( global );
		GetAdir( type, n, aDir );
		UpCbd().cbd[ n ].aCbd.Assign( aDir.Mul( aLen ) );		// UpCbd().cbd[ n ].aCbd = aDir * aLen;
		
		if( type == TYPE4 || type == TYPE5 || type == TYPE6 || type == TYPE8 || type == TYPE9 ) {
			lvDouble  k = Gbl().tdExecMain[ 0 ];				// k = new lvDouble( global );
			lvDouble  h = Gbl().tdExecMain[ 1 ];				// h = new lvDouble( global );
			GetKH45689( n, k, h );
			UpCbd().cbd[ n ].k = k.val;
			UpCbd().cbd[ n ].h = h.val;
		}
		else {
			UpCbd().cbd[ n ].k = GetK( type, n );
			UpCbd().cbd[ n ].h = GetH( type, n );
		}
		
		Gbl().lenRatio[ n ] = LenRatio( type, n );
	}
	
	private final int
	GetType( int n ) throws lvThrowable
	{
		double  l    = ( DownCbd().info[ 0 ].vtx.Sub( DownCbd().info[ 1 ].vtx ) ).Length();
							// l = ( DownCbd().info[ 0 ].vtx - DownCbd().info[ 1 ].vtx ).Length();
		double  lenB = DownCbd().info[ n ].bCbd.Length();
		
		if( lvEps.IsZero( lenB ) == true )
			return TYPE1;
		if( lenB < ( Alpha() * l ) )
			return TYPE2;
		
		double  lenC = DownCbd().info[ n ].cVec.Length();

		if( lvEps.IsZero( lenC ) == true )
			return TYPE3;
			
		lvVector  bCbd2 = Gbl().tvGetType[ 0 ];					// bCbd2 = new lvVector( global );
		lvVector  cVec2 = Gbl().tvGetType[ 1 ];					// cVec2 = new lvVector( global );
		bCbd2.Assign( DownCbd().info[ n ].bCbd.Div( lenB ) );	// bCbd2 = DownCbd().info[ n ].bCbd / lenB;
		cVec2.Assign( DownCbd().info[ n ].cVec.Div( lenC ) );	// cVec2 = DownCbd().info[ n ].cVec / lenC;
		double  lenCross = ( bCbd2.Cross( cVec2 ) ).Length();	// lenCross = ( bCbd2 % cVec2 ).Length();
		
		if( lenC < ( Alpha() * l ) ) {
			if( lvEps.IsZero( lenCross ) == true )
				return TYPE4;
			else if( lenCross < Beta() )
				return TYPE5;
			else
				return TYPE6;
		}
		else {
			if( lvEps.IsZero( lenCross ) == true )
				return TYPE7;
			else if( lenCross < Beta() )
				return TYPE8;
			else
				return TYPE9;
		}
	}
	
	private final double
	GetAlen( int type, int n ) throws lvThrowable
	{
		double  l    = ( DownCbd().info[ 0 ].vtx.Sub( DownCbd().info[ 1 ].vtx ) ).Length();
							// l = ( DownCbd().info[ 0 ].vtx - DownCbd().info[ 1 ].vtx ).Length();
		double  lenB = DownCbd().info[ n ].bCbd.Length();
		
		switch( type ) {
		case TYPE1:
			return 0.0;
		case TYPE2:
			return lenB / ( 3.0 * Alpha() );
		case TYPE3:
		case TYPE4:
		case TYPE5:
		case TYPE6:
		case TYPE7:
		case TYPE8:
		default:	// case TYPE9:
			return 1.0 / 3.0 * l;
		}
	}
	
	private final void
	GetAdir( int type, int n, lvVector aDir ) throws lvThrowable
	{
		double    lenB  = DownCbd().info[ n ].bCbd.Length();

		lvVector  bCbd2  = Gbl().tvGetAdir[ 0 ];					// bCbd2  = new lvVector( global );
		lvVector  cVec2  = Gbl().tvGetAdir[ 1 ];					// cVec2  = new lvVector( global );
		lvVector  cross0 = Gbl().tvGetAdir[ 2 ];					// cross0 = new lvVector( global );
		lvVector  cross1 = Gbl().tvGetAdir[ 3 ];					// cross1 = new lvVector( global );
		lvVector  tmp    = Gbl().tvGetAdir[ 4 ];					// tmp    = new lvVector( global );
		
		double  t0 = 0.0;
		double  t1 = 0.0;
		switch( type ) {
		case TYPE4:
		case TYPE5:
		case TYPE6:
		case TYPE7:
		case TYPE8:
		case TYPE9:
			cross0.Assign( DownCbd().faceNormal.Cross( DownCbd().info[ n ].cVec ) );
			cross0.UnitAssign();
			
			double  l    = ( DownCbd().info[ 0 ].vtx.Sub( DownCbd().info[ 1 ].vtx ) ).Length();
								// l = ( DownCbd().info[ 0 ].vtx - DownCbd().info[ 1 ].vtx ).Length();
			double  lenC = DownCbd().info[ n ].cVec.Length();
			t0 = lenC / ( Alpha() * l );
			
			bCbd2.Assign( DownCbd().info[ n ].bCbd.Div( lenB ) );	// bCbd2 = DownCbd().info[ n ].bCbd / lenB;
			cVec2.Assign( DownCbd().info[ n ].cVec.Div( lenC ) );	// cVec2 = DownCbd().info[ n ].cVec / lenC;
			double  lenCross = ( bCbd2.Cross( cVec2 ) ).Length();	// lenCross = ( bCbd2 % cVec2 ).Length();
			t1 = lenCross / Beta();
			
			cross1.Assign( cVec2.Cross( bCbd2.Cross( cVec2 ) ) );	// corss = cVec2 % ( bCbd2 % cVec2 );
			cross1.UnitAssign();
			
			break;
		}
		
		switch( type ) {
		case TYPE1:
			aDir.SetXYZ( 0.0, 0.0, 0.0 );
			break;
		case TYPE2:
		case TYPE3:
			aDir.Assign( DownCbd().info[ n ].bCbd.Div( lenB ) );
			break;
		case TYPE4:
			aDir.Assign( ( bCbd2.Mul( 1.0 - t0 ) ).Add( cross0.Mul( t0 ) ) );	// aDir = bCbd2 * ( 1.0 - t0 ) + cross0 * t0;
			aDir.UnitAssign();
			break;
		case TYPE5:
			tmp.Assign( ( bCbd2.Mul( 1.0 - t0 ) ).Add( cross0.Mul( t0 ) ) );	// tmp = bCbd2 * ( 1.0 - t0 ) + cross0 * t0;
			tmp.UnitAssign();
			aDir.Assign( ( tmp.Mul( 1.0 - t1 ) ).Add( cross1.Mul( t1 ) ) );		// aDir = tmp * ( 1.0 - t1 ) + cross1 * t1;
			aDir.UnitAssign();
			break;
		case TYPE6:
			aDir.Assign( cross1 );
			break;
		case TYPE7:
			aDir.Assign( cross0 );
			break;
		case TYPE8:
			aDir.Assign( ( cross0.Mul( 1.0 - t1 ) ).Add( cross1.Mul( t1 ) ) );	// aDir = cross0 * ( 1.0 - t1 ) + cross1 * t1;
			aDir.UnitAssign();
			break;
		default:	// case TYPE9:
			aDir.Assign( cross1 );
			break;
		}
	}
	
	private final void
	GetKH45689( int n, lvDouble k, lvDouble h ) throws lvThrowable
	{
		lvVector  aCbd = UpCbd().cbd[ n ].aCbd;
		lvVector  bCbd = DownCbd().info[ n ].bCbd;
		lvVector  cVec = DownCbd().info[ n ].cVec;
		boolean  success = bCbd.Divide( aCbd, cVec, k, h, lvEps.l1, lvEps.l1 );
		Err().Assert( ( success == true ), "lvMakeCbd.GetKH45689(0)" );
	}

	private final double
	GetK( int type, int n ) throws lvThrowable
	{
		switch( type ) {
		case TYPE1:
		case TYPE2:
			return 3.0 * Alpha();
		case TYPE3:
			double  lenA = UpCbd().cbd[ n ].aCbd.Length();
			double  lenB = DownCbd().info[ n ].bCbd.Length();
			return lenB / lenA;
		default:	// case TYPE7:
			return 0.0;
		}
	}
	
	private final double
	GetH( int type, int n ) throws lvThrowable
	{
		switch( type ) {
		case TYPE1:
		case TYPE2:
		case TYPE3:
			return 0.0;
		default:	// case TYPE7:
			double  lenA = UpCbd().cbd[ n ].aCbd.Length();
			double  lenC = DownCbd().info[ n ].cVec.Length();
			return lenC / lenA;
		}
	}
	
	private final double
	LenRatio( int type, int n ) throws lvThrowable
	{
		lvVector  edgeVec  = Gbl().tvLenRatio[ 0 ];					// edgeVec  = new lvVector( global );
		lvVector  edgeVecE = Gbl().tvLenRatio[ 1 ];					// edgeVecE = new lvVector( global );
		lvVector  handVecE = Gbl().tvLenRatio[ 2 ];					// handVecE = new lvVector( global );
		
		if( DownCbd().antiCenter == null )
			return 1.0;
			
		lvVector  basePos = DownCbd().info[ n ].vtx;
		lvVector  antiPos = DownCbd().info[ n ].antiVtx;
		
		edgeVec.Assign( antiPos.Sub( basePos ) );					// edgeVec = antiPos - basePos;
		lvVector  handVec = DownCbd().info[ n ].bCbd;
		
		double  edgeLen = edgeVec.Length();
		Err().Assert( ( Eps().IsZero( edgeLen ) == false ), "lvMakeCbd.LenRatio(0)" );
		double  oldHandLen = handVec.Length();
		double  ratio0     = 1.0;
		if( Eps().IsZero( oldHandLen ) == false ) {
			edgeVecE.Assign( edgeVec.Div( edgeLen ) );				// edgeVecE = edgeVec / edgeLen;
			handVecE.Assign( handVec.Div( oldHandLen ) );			// handVecE = handVec / oldHandLen;
			double  dot = edgeVecE.Dot( handVecE );					// dot = edgeVecE * handVecE;
			if( dot < 0.0 )
				dot = 0.0;
			double  newHandLen = edgeLen * 2.0 / ( 3.0 * ( 1.0 + dot ) );
			ratio0 = oldHandLen / newHandLen;
		}
		
		double  l    = ( DownCbd().info[ 0 ].vtx.Sub( DownCbd().info[ 1 ].vtx ) ).Length();
							// l = ( DownCbd().info[ 0 ].vtx - DownCbd().info[ 1 ].vtx ).Length();
		double  lenB = DownCbd().info[ n ].bCbd.Length();
		
		double  ratio;
		switch( type ) {
		case TYPE1:
			ratio = 1.0;
			break;
		case TYPE2:
			double  t = lenB / ( Alpha() * l );
			ratio = ( 1.0 - t ) * 1.0 + t * ratio0;
			break;
		case TYPE3:
		case TYPE4:
		case TYPE5:
		case TYPE6:
		case TYPE7:
		case TYPE8:
		default:	// case TYPE9:
			ratio = ratio0;
			break;
		}

		return  ratio;
	}
	
	private final void
	Exec3or4() throws lvThrowable
	{
		if( DownCbd().antiCenter == null ) {
			UpCbd().k1 = ( UpCbd().cbd[ 0 ].k + UpCbd().cbd[ 1 ].k ) / 2.0;
			
			double  k0 = UpCbd().cbd[ 0 ].k;
			if( k0 < 0.0 )
				k0 = -k0;
			double  k2 = UpCbd().cbd[ 1 ].k;
			if( k2 < 0.0 )
				k2 = -k2;
			double  ave = ( k0 + k2 ) / 2.0;
		
			if( UpCbd().k1 < ( 1.0/3.0 * ave ) )
				UpCbd().k1 = 1.0/3.0 * ave;
				
			return;
		}
		
		Exec3or4Main();
	}
	
	private final void
	Exec3or4Main() throws lvThrowable
	{
		lvVector  baseCenter = Gbl().tvExec3or4Main[ 0 ];		// baseCenter = new lvVector( global );
		lvVector  aCbdCenter = Gbl().tvExec3or4Main[ 1 ];		// aCbdCenter = new lvVector( global );
		
		Position( 0.5, baseCenter );
		lvVector  antiCenter = DownCbd().antiCenter;
		
		double  lenRatioLeft  = Gbl().lenRatio[ 0 ];
		double  lenRatioRight = Gbl().lenRatio[ 1 ];
		
		lvVector  aCbdLeft  = UpCbd().cbd[ 0 ].aCbd;
		lvVector  aCbdRight = UpCbd().cbd[ 1 ].aCbd;
		aCbdCenter.Assign( ( aCbdLeft.Add( aCbdRight ) ).Div( 2.0 ) );
								// aCbdCenter = ( aCbdLeft + aCbdRight ) / 2.0;
		double  handLenCenter = HandLenCenter( baseCenter, antiCenter, aCbdCenter );
		double  correctLen    = handLenCenter * ( ( lenRatioLeft + lenRatioRight ) / 2.0 );
		
		Exec3or4K1( aCbdCenter, correctLen );
	}
	
	private final void
	Position( double t, lvVector result )
	{
		lvVector  ctlPnt[/*4*/] = Gbl().tvAryPosition;					// ctlPnt[] = new lvVector[ 4 ];
		
		ctlPnt[ 0 ].Assign( DownCbd().info[ 0 ].vtx );
		ctlPnt[ 1 ].Assign( DownCbd().info[ 0 ].vtx.Add( DownCbd().info[ 0 ].cVec ) );
		ctlPnt[ 2 ].Assign( DownCbd().info[ 1 ].vtx.Sub( DownCbd().info[ 1 ].cVec ) );
		ctlPnt[ 3 ].Assign( DownCbd().info[ 1 ].vtx );
			
		lvBezLine.Position( ctlPnt, t, result );
	}
	
	private final double
	HandLenCenter( lvVector basePos, lvVector antiPos, lvVector handVec ) throws lvThrowable
	{
		lvVector  edgeVec  = Gbl().tvHandLenCenter[ 0 ];			// edgeVec  = new lvVector( global );
		lvVector  edgeVecE = Gbl().tvHandLenCenter[ 1 ];			// edgeVecE = new lvVector( global );
		lvVector  handVecE = Gbl().tvHandLenCenter[ 2 ];			// handVecE = new lvVector( global );
		
		edgeVec.Assign( antiPos.Sub( basePos ) );					// edgeVec = antiPos - basePos;
		double  edgeLen = edgeVec.Length();
		Err().Assert( ( Eps().IsZero( edgeLen ) == false ), "lvMakeCbd.HandLenCenter(0)" );
		double  oldHandLen = handVec.Length();
		
		double  newHandLen = 0.0;
		if( Eps().IsZero( oldHandLen ) == true )
			return newHandLen;
		
		edgeVecE.Assign( edgeVec.Div( edgeLen ) );				// edgeVecE = edgeVec / edgeLen;
		handVecE.Assign( handVec.Div( oldHandLen ) );			// handVecE = handVec / oldHandLen;
		double  dot = edgeVecE.Dot( handVecE );					// dot = edgeVecE * handVecE;
		if( dot < 0.0 )
			dot = 0.0;
		newHandLen = edgeLen * 2.0 / ( 3.0 * ( 1.0 + dot ) );
		if( DownCbd().info[ 0 ].antiVtx.IsSame( DownCbd().info[ 1 ].antiVtx ) == true )		// 3角パッチの場合
			newHandLen *= ( Math.sqrt( 6.0 ) - 2.0 ) * ( 2.0 + Math.sqrt( 2.0 ) ) / 2.0;
		
		if( oldHandLen < ( Alpha() * edgeLen ) ) {
			double  t = oldHandLen / ( Alpha() * edgeLen );
			newHandLen *= t;
		}
		
		return  newHandLen;
	}
	
	private final void
	Exec3or4K1( lvVector aCbdCenter, double srcLen ) throws lvThrowable
	{
		double  k0   = UpCbd().cbd[ 0 ].k;
		double  k2   = UpCbd().cbd[ 1 ].k;
		double  ave = ( k0 + k2 ) / 2.0;
		
		double  aCbdCenterLen = aCbdCenter.Length();
		
		double  k1;
		if( ( 2.0 * srcLen ) > ( ( 3.0 * ave ) + ave ) * aCbdCenterLen )
			k1 = 3.0 * ave;
		else if( ( 2.0 * srcLen ) < ( ( 1.0/3.0 * ave ) + ave ) * aCbdCenterLen )
			k1 = 1.0/3.0 * ave;
		else if( Eps().IsZero( aCbdCenterLen ) == true )
			k1 = ave;
		else
			k1 = ( 2.0 * srcLen / aCbdCenterLen ) - ave;

		UpCbd().k1 = k1;
	}

// -------------------------------------------------------------------

	public final void
	ExecHalf( DownCbd downCbd, UpHalfCbd upHalfCbd ) throws lvThrowable
	{
		UpCbd  upCbd = Gbl().tuExecHalf[ 0 ];			// upCbd = new UpCbd( global );
		
		Exec( downCbd, upCbd );
		ExecHalfMain( downCbd, upCbd, upHalfCbd );
	}
	
	private final void
	ExecHalfMain( DownCbd downCbd, UpCbd upCbd, UpHalfCbd upHalfCbd ) throws lvThrowable
	{
		Gbl().curDownCbd   = downCbd;
		Gbl().curUpCbd     = upCbd;
		Gbl().curUpHalfCbd = upHalfCbd;
		
		SetACbd();
		SetK();
		SetH();
	}

	private final void
	SetACbd() throws lvThrowable
	{
		lvVector  aCbdLeft  = Gbl().tvSetACbd[ 0 ];			// aCbdLeft  = new lvVector( global );
		lvVector  aCbdRight = Gbl().tvSetACbd[ 1 ];			// aCbdRight = new lvVector( global );
		aCbdLeft.Assign(  UpCbd().cbd[ 0 ].aCbd.Div( 2.0 ) );
		aCbdRight.Assign( UpCbd().cbd[ 1 ].aCbd.Div( 2.0 ) );
		
		UpHalfCbd().cbd[ 0 ].aCbd.Assign( UpCbd().cbd[ 0 ].aCbd );
		UpHalfCbd().cbd[ 2 ].aCbd.Assign( UpCbd().cbd[ 1 ].aCbd );
		
		UpHalfCbd().cbd[ 1 ].aCbd.Assign( ( aCbdLeft.Add( aCbdRight ) ).Div( 2.0 ) );
								// UpHalfCbd().cbd[ 1 ].aCbd = ( aCbdLeft + aCbdRight ) / 2.0;
	}
	
	private final void
	SetK()
	{
		double  k0 = UpCbd().cbd[ 0 ].k;
		double  k1 = UpCbd().k1;
		double  k2 = UpCbd().cbd[ 1 ].k;
		
		UpHalfCbd().cbd[ 0 ].k = k0;
		UpHalfCbd().cbd[ 2 ].k = k2;
		UpHalfCbd().k1[ 0 ]    = ( k0 + k1 ) / 2.0;
		UpHalfCbd().k1[ 1 ]    = ( k1 + k2 ) / 2.0;
		UpHalfCbd().cbd[ 1 ].k = ( UpHalfCbd().k1[ 0 ] + UpHalfCbd().k1[ 1 ] ) / 2.0;
	}
	
	private final void
	SetH()
	{
		double  h0 = UpCbd().cbd[ 0 ].h;
		double  h1 = UpCbd().cbd[ 1 ].h;
		
		UpHalfCbd().cbd[ 0 ].h = h0;
		UpHalfCbd().cbd[ 2 ].h = h1;
		UpHalfCbd().cbd[ 1 ].h = ( h0 + h1 ) / 2.0;
	}

}
