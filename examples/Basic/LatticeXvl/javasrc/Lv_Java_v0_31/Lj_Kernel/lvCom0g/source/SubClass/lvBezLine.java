//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvBezLine.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * ベジェ曲線の演算用クラス
 * @author	  created by Eishin Matsui (99/09/29-)
 * 
 */
public class lvBezLine {
	
	private static final int  maxNumSegmentParam_Delta = 256;
		
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** lvBezLine.Local型の変数		*/
		private lvBezLine.Local  local		= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			local = new lvBezLine.Local( dt );
			
			GlobalTmp( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private lvVector  tvDerivative[]    				= null;
		private lvVector  tvNormalBound[]   				= null;
		private lvVector  tvBezNormalBound[]				= new lvVector[ 4 ];		// 一本のベジェ曲線用
		private lvVector  tvNormalBoundMain[]				= null;
		private lvVector  tvDivideBezier[]  				= null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvDerivative      = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvDerivative[ i ]      = new lvVector( dt );
			tvNormalBound     = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormalBound[ i ]     = new lvVector( dt );
			tvNormalBoundMain = new lvVector[ 8 ];	for( int i=0; i<8; i++ )	tvNormalBoundMain[ i ] = new lvVector( dt );
			tvDivideBezier    = new lvVector[ 8 ];	for( int i=0; i<8; i++ )	tvDivideBezier[ i ]    = new lvVector( dt );
		}
		
		/** 小規模な配列用のグローバルデータ				*/
		private double  sdSegmentParam_Delta[]	= new double[ maxNumSegmentParam_Delta ];
	}

// -------------------------------------------------------------------

	private static class Local extends lvRoot {
		
		/** 当クラス用のグローバルデータ		*/
		private final lvBezLine.Global
		Gbl()
		{
			return  ( ( lv0ComGGblElm )global.GComG() ).gBezLine;
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

		/**
		 * t値でのベジェ曲線上の位置を求める
		 * @param  p		(( I )) 制御点配列（長さは 4 ）
		 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
		 * @param  result	(( O )) 位置
		 */
		private final void
		Position( lvVector p[/*4*/], double t, lvVector result )
		{
			double  tn   = 1 - t;
			double  t2   = t * t;
			double  tn2  = tn * tn;
			double  prm0 = tn2 * tn;
			double  prm1 = 3.0 * tn2 * t;
			double  prm2 = 3.0 * tn * t2;
			double  prm3 = t2 * t;
			
			result.Assign( ( p[ 0 ].Mul( prm0 ) ).Add( p[ 1 ].Mul( prm1 ) ).Add( p[ 2 ].Mul( prm2 ) ).Add( p[ 3 ].Mul( prm3 ) ) );
														// result = p[ 0 ] * prm0 + p[ 1 ] * prm1 + p[ 2 ] * prm2 + p[ 3 ] * prm3;
		}

		/**
		 * t値でのベジェ曲線上の微分ベクトルを求める
		 * @param  p		(( I )) 制御点配列（長さは 4 ）
		 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
		 * @param  result	(( O )) 微分ベクトル
		 */
		private final void
		Derivative( lvVector p[/*4*/], double t, lvVector result )
		{
			double  tn   = 1 - t;
			double  prm0 = tn * tn;
			double  prm1 = 2.0 * tn * t;
			double  prm2 = t * t;

			lvVector  a0 = Gbl().tvDerivative[0];
			lvVector  a1 = Gbl().tvDerivative[1];
			lvVector  a2 = Gbl().tvDerivative[2];
			a0.Assign( p[ 1 ].Sub( p[ 0 ] ) );			// a0 = p[ 1 ] - p[ 0 ];
			a1.Assign( p[ 2 ].Sub( p[ 1 ] ) );			// a1 = p[ 2 ] - p[ 1 ];
			a2.Assign( p[ 3 ].Sub( p[ 2 ] ) );			// a2 = p[ 3 ] - p[ 2 ];
		
			result.Assign( ( ( a0.Mul( prm0 ) ).Add( a1.Mul( prm1 ) ).Add( a2.Mul( prm2 ) ) ).Mul( 3.0 ) );
														// result = ( a0 * prm0 + a1 * prm1 + a2 * prm2 ) * 3.0;
		}

		/**
		 * 複数点を結ぶベジェ境界面の単位平均法線ベクトルと面積を求める（非推奨）
		 * @param  nP0		(( I )) 点の配列（「始点、始点側制御点、終点側制御点、終点（＝次の始点）、次の始点側制御点」の順）
		 * @param  num0		(( I )) 点の数
		 * @param  normal	(( O )) 単位平均法線ベクトル
		 * @param  a0p		(( O )) 面積
		 * @param  eps		(( I )) 許容誤差
		 */
		private final void
		NormalBound( lvVector nP0[], int num0, lvVector normal, lvDouble a0p, double eps ) throws lvThrowable
		{
			Err().Assert( ( eps >= 0.0 ), "lvBezLine.NormalBound(0)" );

			lvVector  n0    = Gbl().tvNormalBound[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );	// n0    = new lvVector( 0.0, 0.0, 0.0 );
			lvVector  v     = Gbl().tvNormalBound[1];									// v     = new lvVector( *, *, * );
			lvVector  pm    = Gbl().tvNormalBound[2];									// pm    = new lvVector( *, *, * );
			lvVector  bez[] = Gbl().tvBezNormalBound;									// bez[] = new lvVector[];
			
			double  d0 = 0.0;
			for( int i=0; i<( num0/3 ); i++ ) {
				for( int j=0; j<4; j++ ) {
					int  n = ( i * 3 + j ) % num0;
					bez[ j ] = nP0[ n ];
				}
				
				NormalBoundMain( bez, v );
				n0.AddAssign( v );
						
				Position( bez, 0.5, pm );
				d0 += ( bez[ 3 ].Sub( pm ) ).Length() + ( pm.Sub( bez[ 0 ] ) ).Length();
											// d0 += ( bez[ 3 ] - pm ).Length() + ( pm - bez[ 0 ] ).Length();
			}
			d0 *= 0.5;

			double	a0 = n0.Length();
			if( !Eps().IsZero( d0, eps ) && !Eps().IsZero( a0 / d0, eps ) )
				n0.DivAssign( a0 );		// n0 /= a0;
			else {
				a0 = 0.0;
				n0.SetXYZ( 0.0, 0.0, 0.0 );
			}

			if( a0p != null )
				a0p.val = a0;
			
			normal.Assign( n0 );
		}
	
		private final void
		NormalBoundMain( lvVector p[/*4*/], lvVector n0 )
		{
			lvVector  a0 = Gbl().tvNormalBoundMain[0];
			lvVector  a1 = Gbl().tvNormalBoundMain[1];
			lvVector  a2 = Gbl().tvNormalBoundMain[2];
			a0.Assign( p[ 1 ].Sub( p[ 0 ] ) );			// a0 = p[ 1 ] - p[ 0 ];
			a1.Assign( p[ 2 ].Sub( p[ 1 ] ) );			// a1 = p[ 2 ] - p[ 1 ];
			a2.Assign( p[ 3 ].Sub( p[ 2 ] ) );			// a2 = p[ 3 ] - p[ 2 ];

			lvVector  v  = Gbl().tvNormalBoundMain[3];

			n0.SetXYZ( 0.0, 0.0, 0.0 );
			
			n0.AddAssign( ( SetParam( p[0], a0, v ) ).Mul( 1.0/ 2.0 ) );
			n0.AddAssign( ( SetParam( p[0], a1, v ) ).Mul( 1.0/ 5.0 ) );
			n0.AddAssign( ( SetParam( p[0], a2, v ) ).Mul( 1.0/20.0 ) );
			
			n0.AddAssign( ( SetParam( p[1], a0, v ) ).Mul( 3.0/10.0 ) );
			n0.AddAssign( ( SetParam( p[1], a1, v ) ).Mul( 3.0/10.0 ) );
			n0.AddAssign( ( SetParam( p[1], a2, v ) ).Mul( 3.0/20.0 ) );
			
			n0.AddAssign( ( SetParam( p[2], a0, v ) ).Mul( 3.0/20.0 ) );
			n0.AddAssign( ( SetParam( p[2], a1, v ) ).Mul( 3.0/10.0 ) );
			n0.AddAssign( ( SetParam( p[2], a2, v ) ).Mul( 3.0/10.0 ) );
			
			n0.AddAssign( ( SetParam( p[3], a0, v ) ).Mul( 1.0/20.0 ) );
			n0.AddAssign( ( SetParam( p[3], a1, v ) ).Mul( 1.0/ 5.0 ) );
			n0.AddAssign( ( SetParam( p[3], a2, v ) ).Mul( 1.0/ 2.0 ) );
		}
		
		private final lvVector
		SetParam( lvVector src0, lvVector src1, lvVector dst )
		{
			return  dst.SetXYZ( src0.y * src1.z, src0.z * src1.x, src0.x * src1.y );
		}
		
		/**
		 * 複数点を結ぶベジェ境界面の単位平均法線ベクトルと面積を求める（推奨）
		 * @param  nP0		(( I )) 点の配列（「始点、始点側制御点、終点側制御点、終点（＝次の始点）、次の始点側制御点」の順）
		 * @param  num0		(( I )) 点の数
		 * @param  normal	(( O )) 単位平均法線ベクトル
		 * @param  a0p		(( O )) 面積
		 */
/*
		private final void
		NormalBound( lvVector nP0[], int num0, lvVector normal, lvDouble a0p ) throws lvThrowable
		{
			NormalBound( nP0, num0, normal, a0p, lvEps.l1 );
		}
*/
		/**
		 * 複数点を結ぶベジェ境界面の単位平均法線ベクトルを求める（推奨）
		 * @param  nP0		(( I )) 点の配列（「始点、始点側制御点、終点側制御点、終点（＝次の始点）、次の始点側制御点」の順）
		 * @param  num0		(( I )) 点の数
		 * @param  normal	(( O )) 単位平均法線ベクトル
		 */
		private final void
		NormalBound( lvVector nP0[], int num0, lvVector normal ) throws lvThrowable
		{
			NormalBound( nP0, num0, normal, null, lvEps.l1 );
		}
				
		/**
		 * ベジェ曲線をt値にて、左右2本のベジェ曲線に分ける
		 * @param  srcBez		(( I )) 元のベジェ曲線
		 * @param  t			(( I )) パラメータ値（ 0 ～ 1 ）
		 * @param  leftBez		(( O )) 左のベジェ曲線（ null可 ）
		 * @param  rightBez		(( O )) 右のベジェ曲線（ null可 ）
		 */
		private final void
		DivideBezier( lvVector srcBez[/*4*/], double t, lvVector leftBez[/*4*/], lvVector rightBez[/*4*/] )
		{
			lvVector  bez10 = Gbl().tvDivideBezier[ 0 ];				// bez10 = new lvVector( global );
			lvVector  bez11 = Gbl().tvDivideBezier[ 1 ];				// bez11 = new lvVector( global );
			lvVector  bez12 = Gbl().tvDivideBezier[ 2 ];				// bez12 = new lvVector( global );
			lvVector  bez20 = Gbl().tvDivideBezier[ 3 ];				// bez20 = new lvVector( global );
			lvVector  bez21 = Gbl().tvDivideBezier[ 4 ];				// bez21 = new lvVector( global );
			lvVector  bez30 = Gbl().tvDivideBezier[ 5 ];				// bez30 = new lvVector( global );

			double  mt = 1 - t;
			
			bez10.Assign( ( srcBez[ 0 ].Mul( mt ) ).Add( srcBez[ 1 ].Mul( t ) ) );		// bez10 = srcBez[0] * mt + srcBez[1] * t;
			bez11.Assign( ( srcBez[ 1 ].Mul( mt ) ).Add( srcBez[ 2 ].Mul( t ) ) );		// bez11 = srcBez[1] * mt + srcBez[2] * t;
			bez12.Assign( ( srcBez[ 2 ].Mul( mt ) ).Add( srcBez[ 3 ].Mul( t ) ) );		// bez12 = srcBez[2] * mt + srcBez[3] * t;
			
			bez20.Assign( ( bez10.Mul( mt ) ).Add( bez11.Mul( t ) ) );				// bez20 = bez10 * mt + bez11 * t;
			bez21.Assign( ( bez11.Mul( mt ) ).Add( bez12.Mul( t ) ) );				// bez21 = bez11 * mt + bez12 * t;
			bez30.Assign( ( bez20.Mul( mt ) ).Add( bez21.Mul( t ) ) );				// bez30 = bez20 * mt + bez21 * t;
			
			if( leftBez != null ) {
				leftBez[ 0 ].Assign( srcBez[ 0 ] );
				leftBez[ 1 ].Assign( bez10 );
				leftBez[ 2 ].Assign( bez20 );
				leftBez[ 3 ].Assign( bez30 );
			}
			if( rightBez != null ) {
				rightBez[ 0 ].Assign( bez30 );
				rightBez[ 1 ].Assign( bez21 );
				rightBez[ 2 ].Assign( bez12 );
				rightBez[ 3 ].Assign( srcBez[ 3 ] );
			}
		}
		
    }

// -------------------------------------------------------------------

	/** 当クラス用のグローバルデータ		*/
	private static final lvBezLine.Local
	Local( lvGlobal gbl )
	{
		return  ( ( lv0ComGGblElm )gbl.GComG() ).gBezLine.local;
	}

// -------------------------------------------------------------------

	/**
	 * t値でのベジェ曲線上の位置を求める
	 * @param  p		(( I )) 制御点配列（長さは 4 ）
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 位置
	 */
	public static final void
	Position( lvVector p[/*4*/], double t, lvVector result )
	{
		lvGlobal  gbl = p[ 0 ].global;
		Local( gbl ).Position( p, t, result );
	}

	/**
	 * t値でのベジェ曲線上の微分ベクトルを求める
	 * @param  p		(( I )) 制御点配列（長さは 4 ）
	 * @param  t		(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  result	(( O )) 微分ベクトル
	 */
	public static final void
	Derivative( lvVector p[/*4*/], double t, lvVector result )
	{
		lvGlobal  gbl = p[ 0 ].global;
		Local( gbl ).Derivative( p, t, result );
	}
		
	/**
	 * 複数点を結ぶベジェ境界面の単位平均法線ベクトルと面積を求める（非推奨）
	 * @param  nP0		(( I )) 点の配列（「始点、始点側制御点、終点側制御点、終点（＝次の始点）、次の始点側制御点」の順）
	 * @param  num0		(( I )) 点の数
	 * @param  normal	(( O )) 単位平均法線ベクトル
	 * @param  a0p		(( O )) 面積
	 * @param  eps		(( I )) 許容誤差
	 */
/*
	public static final void
	NormalBound( lvVector nP0[], int num0, lvVector normal, lvDouble a0p, double eps ) throws lvThrowable
	{
		lvComGGlobal  gbl = ( lvComGGlobal )nP0[ 0 ].global;
		Local( gbl ).NormalBound( nP0, num0, normal, a0p, eps );
	}
*/
	/**
	 * 複数点を結ぶベジェ境界面の単位平均法線ベクトルと面積を求める（推奨）
	 * @param  nP0		(( I )) 点の配列（「始点、始点側制御点、終点側制御点、終点（＝次の始点）、次の始点側制御点」の順）
	 * @param  num0		(( I )) 点の数
	 * @param  normal	(( O )) 単位平均法線ベクトル
	 * @param  a0p		(( O )) 面積
	 */
/*
	public static final void
	NormalBound( lvVector nP0[], int num0, lvVector normal, lvDouble a0p ) throws lvThrowable
	{
		lvComGGlobal  gbl = ( lvComGGlobal )nP0[ 0 ].global;
		Local( gbl ).NormalBound( nP0, num0, normal, a0p );
	}
*/
	/**
	 * 複数点を結ぶベジェ境界面の単位平均法線ベクトルを求める（推奨）
	 * @param  nP0		(( I )) 点の配列（「始点、始点側制御点、終点側制御点、終点（＝次の始点）、次の始点側制御点」の順）
	 * @param  num0		(( I )) 点の数
	 * @param  normal	(( O )) 単位平均法線ベクトル
	 */
	public static final void
	NormalBound( lvVector nP0[], int num0, lvVector normal ) throws lvThrowable
	{
		lvGlobal  gbl = nP0[ 0 ].global;
		Local( gbl ).NormalBound( nP0, num0, normal );
	}
	
	/**
	 * ベジェ曲線をt値にて、左右2本のベジェ曲線に分ける
	 * @param  srcBez		(( I )) 元のベジェ曲線
	 * @param  t			(( I )) パラメータ値（ 0 ～ 1 ）
	 * @param  leftBez		(( O )) 左のベジェ曲線（ null可 ）
	 * @param  rightBez		(( O )) 右のベジェ曲線（ null可 ）
	 */
	public static final void
	DivideBezier( lvVector srcBez[/*4*/], double t, lvVector leftBez[/*4*/], lvVector rightBez[/*4*/] )
	{
		lvGlobal  gbl = srcBez[ 0 ].global;
		Local( gbl ).DivideBezier( srcBez, t, leftBez, rightBez );
	}

}
