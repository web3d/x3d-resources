//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvGregory.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * lvGregoryStd, lvGregoryDegen 用の共通部クラス
 * @author	  created by Eishin Matsui (99/10/16-)
 * 
 */
public class lvGregory {
	
	/**
	 * lvGregoryStd, lvGregoryDegenで使用するパラメータセットの内部クラス
	 */
	public static class ParamSet {
		
	/** u値			*/
		public double  u;
	/** v値			*/
		public double  v;
	/** ( 1 - u )	*/
		public double  ru;
	/** ( 1 - v )	*/
		public double  rv;
	/** ( u * u )	*/
		public double  u2;
	/** ( v * v )	*/
		public double  v2;
	/** ( u * v )	*/
		public double  uv;
	/** ( 1 - u ) * ( 1 - u )	*/
		public double  ru2;
	/** ( 1 - v ) * ( 1 - v )	*/
		public double  rv2;
		
	/** 3 * u		*/
		public double  s3u;
	/** 3 * v		*/
		public double  s3v;
	/** 3 * ( u * u )	*/
		public double  s3u2;
	/** 3 * ( v * v )	*/
		public double  s3v2;
		
	/** u * ( 1 - u )	*/
		public double  ruu;
	/** v * ( 1 - v )	*/
		public double  rvv;
	/** ( u * ( 1 - u ) ) + ( v * ( 1 - v ) )	*/
		public double  ruu_rvv;
	/** 1 / ( ( u * ( 1 - u ) ) + ( v * ( 1 - v ) ) )	*/
		public double  r_ruu_rvv;
	
	/** 3次バーンスタイン多項式		*/
		public double  b3u0;
		public double  b3u1;
		public double  b3u2;
		public double  b3u3;
		public double  b3v0;
		public double  b3v1;
		public double  b3v2;
		public double  b3v3;
	
	/** 2次バーンスタイン多項式		*/
		public double  b2u0;
		public double  b2u1;
		public double  b2u2;
		public double  b2u3;
		public double  b2v0;
		public double  b2v1;
		public double  b2v2;
		public double  b2v3;
		
	/** du用のパラメータ内要素		*/
		public double  duElem;
	/** dv用のパラメータ内要素		*/
		public double  dvElem;
		
	}
	
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** lvGregory.Local型の変数		*/
		private lvGregory.Local  local		= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			local = new lvGregory.Local( dt );
			
			GlobalTmp( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private double    tdAryPosition[]				= new double[ 3 ];
		private double    tdAryDerivativeUlocal[]		= new double[ 3 ];
		private double    tdAryDerivativeVlocal[]		= new double[ 3 ];
		
		private lvVector  tvNormal[]    				= null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvNormal = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormal[ i ] = new lvVector( dt );
		}
	}

// -------------------------------------------------------------------

	private static class Local extends lvRoot {
		
		/** 当クラス用のグローバルデータ		*/
		private final lvGregory.Global
		Gbl()
		{
			return  ( ( lv0GeomGGblElm )global.GGeomG() ).gGregory;
		}

		// -------------------------------------------------------------------

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public	Local( lvGlobal dt )
		{
			super( dt );
		}
		
		// -------------------------------------------------------------------

		private final void
		Position( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector pos )
		{
			double  val[/*3*/] = Gbl().tdAryPosition;			// val[] = new double[ 3 ];
		
			if( ( lvEps.IsZero( param.u ) == true || lvEps.IsZero( 1.0 - param.u ) == true ) &&
				( lvEps.IsZero( param.v ) == true || lvEps.IsZero( 1.0 - param.v ) == true ) )
			{
				if( lvEps.IsZero( param.u ) == true && lvEps.IsZero( 1.0 - param.v ) == true ) {
					pos.x = ctlPnt[  2 ][ 0 ];		pos.y = ctlPnt[  2 ][ 1 ];		pos.z = ctlPnt[  2 ][ 2 ];
				}
				else if( lvEps.IsZero( param.u ) == true && lvEps.IsZero( param.v ) == true ) {
					pos.x = ctlPnt[  7 ][ 0 ];		pos.y = ctlPnt[  7 ][ 1 ];		pos.z = ctlPnt[  7 ][ 2 ];
				}
				else if( lvEps.IsZero( 1.0 - param.u ) == true && lvEps.IsZero( param.v ) == true ) {
					pos.x = ctlPnt[ 12 ][ 0 ];		pos.y = ctlPnt[ 12 ][ 1 ];		pos.z = ctlPnt[ 12 ][ 2 ];
				}
				else {	// if( lvEps.IsZero( 1.0 - param.u ) == true && lvEps.IsZero( param.v ) == true )
					pos.x = ctlPnt[ 17 ][ 0 ];		pos.y = ctlPnt[ 17 ][ 1 ];		pos.z = ctlPnt[ 17 ][ 2 ];
				}
				return;
			}
		
			for( int i=0; i<3; i++ ) {
				val[ i ]  = PosParam00( param ) * ctlPnt[  0 ][ i ];
				val[ i ] += PosParam01( param ) * ctlPnt[  1 ][ i ];
				val[ i ] += PosParam02( param ) * ctlPnt[  2 ][ i ];
				val[ i ] += PosParam03( param ) * ctlPnt[  3 ][ i ];
				val[ i ] += PosParam04( param ) * ctlPnt[  4 ][ i ];
				val[ i ] += PosParam05( param ) * ctlPnt[  5 ][ i ];
				val[ i ] += PosParam06( param ) * ctlPnt[  6 ][ i ];
				val[ i ] += PosParam07( param ) * ctlPnt[  7 ][ i ];
				val[ i ] += PosParam08( param ) * ctlPnt[  8 ][ i ];
				val[ i ] += PosParam09( param ) * ctlPnt[  9 ][ i ];
			}
			// ループを2回に分けたのは、Netscape4 Symantec JIT Compiler の Bug対策の為
			for( int i=0; i<3; i++ ) {
				val[ i ] += PosParam10( param ) * ctlPnt[ 10 ][ i ];
				val[ i ] += PosParam11( param ) * ctlPnt[ 11 ][ i ];
				val[ i ] += PosParam12( param ) * ctlPnt[ 12 ][ i ];
				val[ i ] += PosParam13( param ) * ctlPnt[ 13 ][ i ];
				val[ i ] += PosParam14( param ) * ctlPnt[ 14 ][ i ];
				val[ i ] += PosParam15( param ) * ctlPnt[ 15 ][ i ];
				val[ i ] += PosParam16( param ) * ctlPnt[ 16 ][ i ];
				val[ i ] += PosParam17( param ) * ctlPnt[ 17 ][ i ];
				val[ i ] += PosParam18( param ) * ctlPnt[ 18 ][ i ];
				val[ i ] += PosParam19( param ) * ctlPnt[ 19 ][ i ];
			}
			pos.x = val[ 0 ];
			pos.y = val[ 1 ];
			pos.z = val[ 2 ];
		}

		private final void
		Normal( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector normal ) throws lvThrowable
		{
			lvVector  du = Gbl().tvNormal[0];							// du = new lvVector( *, *, * );
			lvVector  dv = Gbl().tvNormal[1];							// dv = new lvVector( *, *, * );
			
			DerivativeUlocal( param, ctlPnt, du );
			DerivativeVlocal( param, ctlPnt, dv );
			
			normal.Assign( ( du.Cross( dv ) ).Unit() );					// normal = ( du % dv ).Unit();
		}
		
		private final void
		DerivativeUlocal( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector du )
		{
			double  val[/*3*/] = Gbl().tdAryDerivativeUlocal;			// val[] = new double[ 3 ];
		
			if( ( lvEps.IsZero( param.u ) == true || lvEps.IsZero( 1.0 - param.u ) == true ) &&
				( lvEps.IsZero( param.v ) == true || lvEps.IsZero( 1.0 - param.v ) == true ) )
			{
				int  flag;
				if( lvEps.IsZero( param.u ) == true && lvEps.IsZero( 1.0 - param.v ) == true )
					flag = 0;
				else if( lvEps.IsZero( param.u ) == true && lvEps.IsZero( param.v ) == true )
					flag = 1;
				else if( lvEps.IsZero( 1.0 - param.u ) == true && lvEps.IsZero( param.v ) == true )
					flag = 2;
				else	// if( lvEps.IsZero( 1.0 - param.u ) == true && lvEps.IsZero( param.v ) == true )
					flag = 3;
			
				for( int i=0; i<3; i++ ) {
					switch( flag ) {
					case 0:
						val[ i ] = ( ctlPnt[  1 ][ i ] - ctlPnt[  2 ][ i ] ) * 3.0;
						break;
					case 1:
						val[ i ] = ( ctlPnt[  8 ][ i ] - ctlPnt[  7 ][ i ] ) * 3.0;
						break;
					case 2:
						val[ i ] = ( ctlPnt[ 12 ][ i ] - ctlPnt[ 11 ][ i ] ) * 3.0;
						break;
					default:	// case 3:
						val[ i ] = ( ctlPnt[ 17 ][ i ] - ctlPnt[ 18 ][ i ] ) * 3.0;
					}
				}
				du.x = val[ 0 ];
				du.y = val[ 1 ];
				du.z = val[ 2 ];
			
				return;
			}
		
			for( int i=0; i<3; i++ ) {
				val[ i ]  = DuParam00( param ) * ctlPnt[  0 ][ i ];
				val[ i ] += DuParam01( param ) * ctlPnt[  1 ][ i ];
				val[ i ] += DuParam02( param ) * ctlPnt[  2 ][ i ];
				val[ i ] += DuParam03( param ) * ctlPnt[  3 ][ i ];
				val[ i ] += DuParam04( param ) * ctlPnt[  4 ][ i ];
				val[ i ] += DuParam05( param ) * ctlPnt[  5 ][ i ];
				val[ i ] += DuParam06( param ) * ctlPnt[  6 ][ i ];
				val[ i ] += DuParam07( param ) * ctlPnt[  7 ][ i ];
				val[ i ] += DuParam08( param ) * ctlPnt[  8 ][ i ];
				val[ i ] += DuParam09( param ) * ctlPnt[  9 ][ i ];
			}
			// ループを2回に分けたのは、Netscape4 Symantec JIT Compiler の Bug対策の為
			for( int i=0; i<3; i++ ) {
				val[ i ] += DuParam10( param ) * ctlPnt[ 10 ][ i ];
				val[ i ] += DuParam11( param ) * ctlPnt[ 11 ][ i ];
				val[ i ] += DuParam12( param ) * ctlPnt[ 12 ][ i ];
				val[ i ] += DuParam13( param ) * ctlPnt[ 13 ][ i ];
				val[ i ] += DuParam14( param ) * ctlPnt[ 14 ][ i ];
				val[ i ] += DuParam15( param ) * ctlPnt[ 15 ][ i ];
				val[ i ] += DuParam16( param ) * ctlPnt[ 16 ][ i ];
				val[ i ] += DuParam17( param ) * ctlPnt[ 17 ][ i ];
				val[ i ] += DuParam18( param ) * ctlPnt[ 18 ][ i ];
				val[ i ] += DuParam19( param ) * ctlPnt[ 19 ][ i ];
			}
			du.x = val[ 0 ];
			du.y = val[ 1 ];
			du.z = val[ 2 ];
		}

		private final void
		DerivativeVlocal( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector dv )
		{
			double  val[/*3*/] = Gbl().tdAryDerivativeVlocal;			// val[] = new double[ 3 ];
		
			if( ( lvEps.IsZero( param.u ) == true || lvEps.IsZero( 1.0 - param.u ) == true ) &&
				( lvEps.IsZero( param.v ) == true || lvEps.IsZero( 1.0 - param.v ) == true ) )
			{
				int  flag;
				if( lvEps.IsZero( param.u ) == true && lvEps.IsZero( 1.0 - param.v ) == true )
					flag = 0;
				else if( lvEps.IsZero( param.u ) == true && lvEps.IsZero( param.v ) == true )
					flag = 1;
				else if( lvEps.IsZero( 1.0 - param.u ) == true && lvEps.IsZero( param.v ) == true )
					flag = 2;
				else	// if( lvEps.IsZero( 1.0 - param.u ) == true && lvEps.IsZero( param.v ) == true )
					flag = 3;
			
				for( int i=0; i<3; i++ ) {
					switch( flag ) {
					case 0:
						val[ i ] = ( ctlPnt[  2 ][ i ] - ctlPnt[  3 ][ i ] ) * 3.0;
						break;
					case 1:
						val[ i ] = ( ctlPnt[  6 ][ i ] - ctlPnt[  7 ][ i ] ) * 3.0;
						break;
					case 2:
						val[ i ] = ( ctlPnt[ 13 ][ i ] - ctlPnt[ 12 ][ i ] ) * 3.0;
						break;
					default:	// case 3:
						val[ i ] = ( ctlPnt[ 17 ][ i ] - ctlPnt[ 16 ][ i ] ) * 3.0;
					}
				}
				dv.x = val[ 0 ];
				dv.y = val[ 1 ];
				dv.z = val[ 2 ];
			
				return;
			}
		
			for( int i=0; i<3; i++ ) {
				val[ i ]  = DvParam00( param ) * ctlPnt[  0 ][ i ];
				val[ i ] += DvParam01( param ) * ctlPnt[  1 ][ i ];
				val[ i ] += DvParam02( param ) * ctlPnt[  2 ][ i ];
				val[ i ] += DvParam03( param ) * ctlPnt[  3 ][ i ];
				val[ i ] += DvParam04( param ) * ctlPnt[  4 ][ i ];
				val[ i ] += DvParam05( param ) * ctlPnt[  5 ][ i ];
				val[ i ] += DvParam06( param ) * ctlPnt[  6 ][ i ];
				val[ i ] += DvParam07( param ) * ctlPnt[  7 ][ i ];
				val[ i ] += DvParam08( param ) * ctlPnt[  8 ][ i ];
				val[ i ] += DvParam09( param ) * ctlPnt[  9 ][ i ];
			}
			// ループを2回に分けたのは、Netscape4 Symantec JIT Compiler の Bug対策の為
			for( int i=0; i<3; i++ ) {
				val[ i ] += DvParam10( param ) * ctlPnt[ 10 ][ i ];
				val[ i ] += DvParam11( param ) * ctlPnt[ 11 ][ i ];
				val[ i ] += DvParam12( param ) * ctlPnt[ 12 ][ i ];
				val[ i ] += DvParam13( param ) * ctlPnt[ 13 ][ i ];
				val[ i ] += DvParam14( param ) * ctlPnt[ 14 ][ i ];
				val[ i ] += DvParam15( param ) * ctlPnt[ 15 ][ i ];
				val[ i ] += DvParam16( param ) * ctlPnt[ 16 ][ i ];
				val[ i ] += DvParam17( param ) * ctlPnt[ 17 ][ i ];
				val[ i ] += DvParam18( param ) * ctlPnt[ 18 ][ i ];
				val[ i ] += DvParam19( param ) * ctlPnt[ 19 ][ i ];
			}
			dv.x = val[ 0 ];
			dv.y = val[ 1 ];
			dv.z = val[ 2 ];
		}
	}

// -------------------------------------------------------------------

	/** 当クラス用のグローバルデータ		*/
	private static final lvGregory.Local
	Local( lvGlobal gbl )
	{
		return  ( ( lv0GeomGGblElm )gbl.GGeomG() ).gGregory.local;
	}

// -------------------------------------------------------------------

	public static final void
	SetParam( int numU, int denomU, int numV, int denomV, ParamSet param )
	{
		if( denomU == 0 )
			param.u = 0.0;
		else
			param.u = ( double )numU / denomU;
		if( denomV == 0 )
			param.v = 0.0;
		else
			param.v = ( double )numV / denomV;
		
		param.ru		= 1.0 - param.u;				// ( 1 - u )
		param.rv		= 1.0 - param.v;				// ( 1 - v )
		param.u2		= param.u * param.u;			// ( u * u )
		param.v2		= param.v * param.v;			// ( v * v )
		param.uv		= param.u * param.v;			// ( u * v )
		param.ru2		= param.ru * param.ru;			// ( 1 - u ) * ( 1 - u )
		param.rv2		= param.rv * param.rv;			// ( 1 - v ) * ( 1 - v )
		
		param.s3u		= 3.0 * param.u;				// 3 * u
		param.s3v		= 3.0 * param.v;				// 3 * v
		param.s3u2		= 3.0 * param.u2;				// 3 * ( u * u )
		param.s3v2		= 3.0 * param.v2;				// 3 * ( v * v )
		
		param.ruu		= param.ru * param.u;			// u * ( 1 - u )
		param.rvv		= param.rv * param.v;			// v * ( 1 - v )
		param.ruu_rvv	= param.ruu + param.rvv;		// ( u * ( 1 - u ) ) + ( v * ( 1 - v ) )
		if( lvEps.IsZero( param.ruu_rvv ) == false )
			param.r_ruu_rvv	= 1.0 / param.ruu_rvv;			// 1 / ( ( u * ( 1 - u ) ) + ( v * ( 1 - v ) ) )
		else
			param.r_ruu_rvv	= 0.0;
		
	/* 3次バーンスタイン多項式		*/
		param.b3u0		= param.ru   * param.ru2;
		param.b3u1		= param.s3u  * param.ru2;
		param.b3u2		= param.s3u2 * param.ru;
		param.b3u3		= param.u    * param.u2;
		
		param.b3v0		= param.rv   * param.rv2;
		param.b3v1		= param.s3v  * param.rv2;
		param.b3v2		= param.s3v2 * param.rv;
		param.b3v3		= param.v    * param.v2;
		
	/* 2次バーンスタイン多項式		*/
		param.b2u0		= -3.0 * param.ru2;
		param.b2u1		=  3.0 * param.ru * ( 1.0 - param.s3u );
		param.b2u2		=  3.0 * param.u  * ( 2.0 - param.s3u );
		param.b2u3		=  3.0 * param.u2;
		
		param.b2v0		= -3.0 * param.rv2;
		param.b2v1		=  3.0 * param.rv * ( 1.0 - param.s3v );
		param.b2v2		=  3.0 * param.v  * ( 2.0 - param.s3v );
		param.b2v3		=  3.0 * param.v2;
	
	/* du用のパラメータ内要素		*/
		param.duElem	= ( ( 1.0 - 2.0 * param.u ) * param.rvv ) / ( param.ruu_rvv * param.ruu_rvv );
	/* dv用のパラメータ内要素		*/
		param.dvElem	= ( param.ruu * ( 1.0 - 2.0 * param.v ) ) / ( param.ruu_rvv * param.ruu_rvv );
	}
	
	public static final void
	Position( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector pos )
	{
		lvGlobal  gbl = pos.global;
		Local( gbl ).Position( param, ctlPnt, pos );
	}

	public static final void
	Normal( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector normal ) throws lvThrowable
	{
		lvGlobal  gbl = normal.global;
		Local( gbl ).Normal( param, ctlPnt, normal );
	}

	public static final void
	DerivativeU( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector du )
	{
		lvGlobal  gbl = du.global;
		Local( gbl ).DerivativeUlocal( param, ctlPnt, du );
	}

	public static final void
	DerivativeV( ParamSet param, double ctlPnt[/*20*/][/*3*/], lvVector dv )
	{
		lvGlobal  gbl = dv.global;
		Local( gbl ).DerivativeVlocal( param, ctlPnt, dv );
	}


// P00 - P30( Begin ) -->
	public static final double
	PosParam07( ParamSet param )		// P00
	{
		return  param.b3u0 * param.b3v0;
	}
	public static final double
	PosParam08( ParamSet param )		// P10
	{
		return  param.b3u1 * param.b3v0;
	}
	public static final double
	PosParam11( ParamSet param )		// P20
	{
		return  param.b3u2 * param.b3v0;
	}
	public static final double
	PosParam12( ParamSet param )		// P30
	{
		return  param.b3u3 * param.b3v0;
	}
// --> P00 - P30( End )

// P03 - P33( Begin ) -->
	public static final double
	PosParam02( ParamSet param )		// P03
	{
		return  param.b3u0 * param.b3v3;
	}
	public static final double
	PosParam01( ParamSet param )		// P13
	{
		return  param.b3u1 * param.b3v3;
	}
	public static final double
	PosParam18( ParamSet param )		// P23
	{
		return  param.b3u2 * param.b3v3;
	}
	public static final double
	PosParam17( ParamSet param )		// P33
	{
		return  param.b3u3 * param.b3v3;
	}
// --> P03 - P33( End )

// P01 - P02( Begin ) -->
	public static final double
	PosParam06( ParamSet param )		// P01
	{
		return  param.b3u0 * param.b3v1;
	}
	public static final double
	PosParam03( ParamSet param )		// P02
	{
		return  param.b3u0 * param.b3v2;
	}
// --> P01 - P02( End )

// P31 - P32( Begin ) -->
	public static final double
	PosParam13( ParamSet param )		// P31
	{
		return  param.b3u3 * param.b3v1;
	}
	public static final double
	PosParam16( ParamSet param )		// P31
	{
		return  param.b3u3 * param.b3v2;
	}
// --> P31 - P32( End )

// P11u - P21u( Begin ) -->
	public static final double
	PosParam05( ParamSet param )		// P11u
	{
		return  param.rvv * param.b3u1 * param.b3v1 * param.r_ruu_rvv;
	}
	public static final double
	PosParam14( ParamSet param )		// P21u
	{
		return  param.rvv * param.b3u2 * param.b3v1 * param.r_ruu_rvv;
	}
// --> P11u - P21u( End )

// P11v - P21v( Begin ) -->
	public static final double
	PosParam09( ParamSet param )		// P11v
	{
		return  param.ruu * param.b3u1 * param.b3v1 * param.r_ruu_rvv;
	}
	public static final double
	PosParam10( ParamSet param )		// P21v
	{
		return  param.ruu * param.b3u2 * param.b3v1 * param.r_ruu_rvv;
	}
// --> P11v - P21v( End )

// P12u - P22u( Begin ) -->
	public static final double
	PosParam04( ParamSet param )		// P12u
	{
		return  param.rvv * param.b3u1 * param.b3v2 * param.r_ruu_rvv;
	}
	public static final double
	PosParam15( ParamSet param )		// P22u
	{
		return  param.rvv * param.b3u2 * param.b3v2 * param.r_ruu_rvv;
	}
// --> P12u - P22u( End )

// P12v - P22v( Begin ) -->
	public static final double
	PosParam00( ParamSet param )		// P12v
	{
		return  param.ruu * param.b3u1 * param.b3v2 * param.r_ruu_rvv;
	}
	public static final double
	PosParam19( ParamSet param )		// P22v
	{
		return  param.ruu * param.b3u2 * param.b3v2 * param.r_ruu_rvv;
	}
// --> P12v - P22v( End )


// P00 - P30( Begin ) -->
	public static final double
	DuParam07( ParamSet param )		// P00
	{
		return  param.b2u0 * param.b3v0;
	}
	public static final double
	DuParam08( ParamSet param )		// P10
	{
		return  param.b2u1 * param.b3v0;
	}
	public static final double
	DuParam11( ParamSet param )		// P20
	{
		return  param.b2u2 * param.b3v0;
	}
	public static final double
	DuParam12( ParamSet param )		// P30
	{
		return  param.b2u3 * param.b3v0;
	}
// --> P00 - P30( End )

// P03 - P33( Begin ) -->
	public static final double
	DuParam02( ParamSet param )		// P03
	{
		return  param.b2u0 * param.b3v3;
	}
	public static final double
	DuParam01( ParamSet param )		// P13
	{
		return  param.b2u1 * param.b3v3;
	}
	public static final double
	DuParam18( ParamSet param )		// P23
	{
		return  param.b2u2 * param.b3v3;
	}
	public static final double
	DuParam17( ParamSet param )		// P33
	{
		return  param.b2u3 * param.b3v3;
	}
// --> P03 - P33( End )

// P01 - P02( Begin ) -->
	public static final double
	DuParam06( ParamSet param )		// P01
	{
		return  param.b2u0 * param.b3v1;
	}
	public static final double
	DuParam03( ParamSet param )		// P01
	{
		return  param.b2u0 * param.b3v2;
	}
// --> P01 - P02( End )

// P31 - P32( Begin ) -->
	public static final double
	DuParam13( ParamSet param )		// P31
	{
		return  param.b2u3 * param.b3v1;
	}
	public static final double
	DuParam16( ParamSet param )		// P31
	{
		return  param.b2u3 * param.b3v2;
	}
// --> P31 - P32( End )

// P11u - P21u( Begin ) -->
	public static final double
	DuParam05( ParamSet param )		// P11u
	{
		double  param0 = -param.duElem * param.b3u1 * param.b3v1;
		double  param1 = param.rvv * param.b2u1 * param.b3v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DuParam14( ParamSet param )		// P21u
	{
		double  param0 = -param.duElem * param.b3u2 * param.b3v1;
		double  param1 = param.rvv * param.b2u2 * param.b3v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P11u - P21u( End )

// P11v - P21v( Begin ) -->
	public static final double
	DuParam09( ParamSet param )		// P11v
	{
		double  param0 = param.duElem * param.b3u1 * param.b3v1;
		double  param1 = param.ruu * param.b2u1 * param.b3v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DuParam10( ParamSet param )		// P21v
	{
		double  param0 = param.duElem * param.b3u2 * param.b3v1;
		double  param1 = param.ruu * param.b2u2 * param.b3v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P11v - P21v( End )

// P12u - P22u( Begin ) -->
	public static final double
	DuParam04( ParamSet param )		// P12u
	{
		double  param0 = -param.duElem * param.b3u1 * param.b3v2;
		double  param1 = param.rvv * param.b2u1 * param.b3v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DuParam15( ParamSet param )		// P22u
	{
		double  param0 = -param.duElem * param.b3u2 * param.b3v2;
		double  param1 = param.rvv * param.b2u2 * param.b3v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P12u - P22u( End )

// P12v - P22v( Begin ) -->
	public static final double
	DuParam00( ParamSet param )		// P12v
	{
		double  param0 = param.duElem * param.b3u1 * param.b3v2;
		double  param1 = param.ruu * param.b2u1 * param.b3v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DuParam19( ParamSet param )		// P22v
	{
		double  param0 = param.duElem * param.b3u2 * param.b3v2;
		double  param1 = param.ruu * param.b2u2 * param.b3v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P12v - P22v( End )


// P00 - P30( Begin ) -->
	public static final double
	DvParam07( ParamSet param )		// P00
	{
		return  param.b3u0 * param.b2v0;
	}
	public static final double
	DvParam08( ParamSet param )		// P10
	{
		return  param.b3u1 * param.b2v0;
	}
	public static final double
	DvParam11( ParamSet param )		// P20
	{
		return  param.b3u2 * param.b2v0;
	}
	public static final double
	DvParam12( ParamSet param )		// P30
	{
		return  param.b3u3 * param.b2v0;
	}
// --> P00 - P30( End )

// P03 - P33( Begin ) -->
	public static final double
	DvParam02( ParamSet param )		// P03
	{
		return  param.b3u0 * param.b2v3;
	}
	public static final double
	DvParam01( ParamSet param )		// P13
	{
		return  param.b3u1 * param.b2v3;
	}
	public static final double
	DvParam18( ParamSet param )		// P23
	{
		return  param.b3u2 * param.b2v3;
	}
	public static final double
	DvParam17( ParamSet param )		// P33
	{
		return  param.b3u3 * param.b2v3;
	}
// --> P03 - P33( End )

// P01 - P02( Begin ) -->
	public static final double
	DvParam06( ParamSet param )		// P01
	{
		return  param.b3u0 * param.b2v1;
	}
	public static final double
	DvParam03( ParamSet param )		// P02
	{
		return  param.b3u0 * param.b2v2;
	}
// --> P01 - P02( End )

// P31 - P32( Begin ) -->
	public static final double
	DvParam13( ParamSet param )		// P31
	{
		return  param.b3u3 * param.b2v1;
	}
	public static final double
	DvParam16( ParamSet param )		// P31
	{
		return  param.b3u3 * param.b2v2;
	}
// --> P31 - P32( End )

// P11u - P21u( Begin ) -->
	public static final double
	DvParam05( ParamSet param )		// P11u
	{
		double  param0 = param.dvElem * param.b3u1 * param.b3v1;
		double  param1 = param.rvv * param.b3u1 * param.b2v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DvParam14( ParamSet param )		// P21u
	{
		double  param0 = param.dvElem * param.b3u2 * param.b3v1;
		double  param1 = param.rvv * param.b3u2 * param.b2v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P11u - P21u( End )

// P11v - P21v( Begin ) -->
	public static final double
	DvParam09( ParamSet param )		// P11v
	{
		double  param0 = -param.dvElem * param.b3u1 * param.b3v1;
		double  param1 = param.ruu * param.b3u1 * param.b2v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DvParam10( ParamSet param )		// P21v
	{
		double  param0 = -param.dvElem * param.b3u2 * param.b3v1;
		double  param1 = param.ruu * param.b3u2 * param.b2v1 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P11v - P21v( End )

// P12u - P22u( Begin ) -->
	public static final double
	DvParam04( ParamSet param )		// P12u
	{
		double  param0 = param.dvElem * param.b3u1 * param.b3v2;
		double  param1 = param.rvv * param.b3u1 * param.b2v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DvParam15( ParamSet param )		// P22u
	{
		double  param0 = param.dvElem * param.b3u2 * param.b3v2;
		double  param1 = param.rvv * param.b3u2 * param.b2v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P12u - P22u( End )

// P12v - P22v( Begin ) -->
	public static final double
	DvParam00( ParamSet param )		// P12v
	{
		double  param0 = -param.dvElem * param.b3u1 * param.b3v2;
		double  param1 = param.ruu * param.b3u1 * param.b2v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
	public static final double
	DvParam19( ParamSet param )		// P22v
	{
		double  param0 = -param.dvElem * param.b3u2 * param.b3v2;
		double  param1 = param.ruu * param.b3u2 * param.b2v2 * param.r_ruu_rvv;
		return  param0 + param1;
	}
// --> P12v - P22v( End )

}
