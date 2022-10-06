//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvVector.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Vector型の数値演算用クラス    （共通部）
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvVector extends lvVecCalc {
	
	/** 定数 --- 直線が平面上になく、しかも平行である		*/
	public static final byte  LV_INTERSEC_PARA		= 0;
	/** 定数 --- 直線が平面上にあり、平行である				*/
	public static final byte  LV_INTERSEC_ON		= 1;
	/** 定数 --- 直線が平面と交差している					*/
	public static final byte  LV_INTERSEC_CROSS		= 2;
	
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			GlobalTmp( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
//		private lvVector  tvScale[]         = null;
//		private lvDouble  tdMirror0[]       = null;
//		private lvVector  tvMirror1[]       = null;
		private lvVector  tvNormal[]        = null;
		private lvVector  tvCenter[]        = null;

		/** ローカル変数用の new 代用バッファエリア（lvKernelGreg用）		*/
		private lvVector  tvRotate0[]       = null;
		private lvDouble  tdRotate0[]       = null;
//		private lvVector  tvRotate1[]       = null;
		private lvVector  tvNormalAssign0[] = null;
		private lvVector  tvNormalAssign1[] = null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalTmp( lvGlobal dt )
		{
//			tvScale         = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvScale[ i ]         = new lvVector( dt );
//			tdMirror0       = new lvDouble[ 2 ];	for( int i=0; i<2; i++ )	tdMirror0[ i ]       = new lvDouble( dt );
//			tvMirror1       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvMirror1[ i ]       = new lvVector( dt );
			tvNormal        = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormal[ i ]        = new lvVector( dt );
			tvCenter        = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvCenter[ i ]        = new lvVector( dt );

			// ローカル変数用の new 代用バッファ初期化（lvKernelGreg用）
			tvRotate0       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvRotate0[ i ]       = new lvVector( dt );
			tdRotate0       = new lvDouble[ 4 ];	for( int i=0; i<4; i++ )	tdRotate0[ i ]       = new lvDouble( dt );
//			tvRotate1       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvRotate1[ i ]       = new lvVector( dt );
			tvNormalAssign0 = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormalAssign0[ i ] = new lvVector( dt );
			tvNormalAssign1 = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormalAssign1[ i ] = new lvVector( dt );
		}
	}
	
	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gVector;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvVector( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * コピーコンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 * @param  val		(( I )) コピー元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 */
	public	lvVector( lvGlobal dt, lvVecCalc val )
	{
		super( dt, val );
	}
	/**
	 * 初期値 x,y,z のコンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 * @param  x		(( I )) コンストラクタの初期値X
	 * @param  y		(( I )) コンストラクタの初期値Y
	 * @param  z		(( I )) コンストラクタの初期値Z
	 */
	public	lvVector( lvGlobal dt, double x, double y, double z )
	{
		super( dt, x, y, z );
	}

// -------------------------------------------------------------------

	/**
	 * Vector型の代入関数
	 * @param  val		(( I )) 代入元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
	public final lvVector
	Assign( lvVecCalc val )
	{
		x = val.x;	y = val.y;	z = val.z;
		return	this;
	}

	/**
	 * Vector型の += 関数
	 * @param  val		(( I )) 代入元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
	public final lvVector
	AddAssign( lvVecCalc val )
	{
		x += val.x;  y += val.y;  z += val.z;
		return	this;
	}
	/**
	 * Vector型の -= 関数
	 * @param  val		(( I )) 代入元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
	public final lvVector
	SubAssign( lvVecCalc val )
	{
		x -= val.x;  y -= val.y;  z -= val.z;
		return	this;
	}
	/**
	 * Vector型の *= （スカラー乗算代入演算）関数
	 * @param  k		(( I )) スカラー
	 * @return			this の参照
	 */
	public final lvVector
	MulAssign( double k )
	{
		x *= k;  y *= k;  z *= k;
		return	this;
	}
	/**
	 * Vector型の /= （スカラー除算代入演算）関数
	 * @param  m0		(( I )) スカラー
	 * @return			this の参照
	 */
	public final lvVector
	DivAssign( double k ) throws lvThrowable
	{
		Err().Assert( ( k != 0.0 ), "lvVector.DivAssign(0)" );
		x /= k;  y /= k;  z /= k;
		return	this;
	}
	/**
	 * Vector型の外積代入演算関数
	 * @param  val		(( I )) 代入元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	CrossAssign( lvVecCalc val )
	{
		double	x, y, z;
		x = this.y * val.z - this.z * val.y;
		y = this.z * val.x - this.x * val.z;
		z = this.x * val.y - this.y * val.x;
		return	SetXYZ_Local( x, y, z );
	}
*/
	/**
	 * 自身（ベクトル:lvVector）に行列 m2 でアフィン変換したベクトルを自身に代入する
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	MulVecAssign( lvMatCalc m0 )
	{
		this.Assign( this.MulVec( m0 ) );		// this = this % m0;
		return	this;
	}
*/
	/**
	 * 自身（3次元座標値としてのベクトル:lvVector）に行列 m2 でアフィン変換したベクトルを自身に代入する
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	MulPosAssign( lvMatCalc m0 )
	{
		this.Assign( this.MulPos( m0 ) );		// this = this * m0;
		return	this;
	}
*/
	/**
	 * 自身を反転する
	 * @return			this の参照
	 */
	public final lvVector
	NegAssign()
	{
		x = -x;  y = -y;  z = -z;
		return	this;
	}
	
	/**
	 * 移動ベクトルにより自身を移動させる
	 * @param  val		(( I )) 移動ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	Move( lvVecCalc val )
	{
		return	AddAssign( val );		// this += val;		return this;
	}
*/

	/**
	 * 原点を中心に自身を拡大縮小させる
	 * @param  k		(( I )) 拡大率
	 * @return			this の参照
	 */
/*
	public final lvVector
	Scale( double k )
	{
		return	MulAssign( k );			// this *= k;		return this;
	}
*/
	/**
	 * 点 p を中心に自身を拡大縮小させる
	 * @param  p		(( I )) 中心点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  k		(( I )) 拡大率
	 * @return			this の参照
	 */
/*
	public final lvVector
	Scale( lvVecCalc p, double k )
	{
		lvVector  v = Gbl().tvScale[0];						// v = new lvVector();
		v.Assign( Sub( p ) );							// v = this - p
		Assign( ( v.Mul( k ) ).Add( p ) );				// this = v * k + p
		return	this;
	}
*/
	
	/**
	 * 原点と法線ベクトル n0 で表される平面に対して自身を反転させる
	 * @param  n0		(( I )) 法線ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	Mirror( lvVecCalc n0 )
	{
		lvDouble  d = Gbl().tdMirror0[0];		d.val = 2.0;		// d = new lvDouble( 2.0 );
		SubAssign( ( d.Mul( Dot( n0 ) ) ).Mul( n0 ) );				// this -= 2.0 * ( this * n0 ) * n0;
		return	this;
	}
*/
	/**
	 * p0 と法線ベクトル n0 で表される平面に対して自身を反転させる
	 * @param  p0		(( I )) 回転軸上の点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 法線ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	Mirror( lvVecCalc p0, lvVecCalc n0 )
	{
		lvVector  v0 = Gbl().tvMirror1[0];			// v0 = new lvVector();
		v0.Assign( Sub( p0 ) );					// v0 = this - p0;
		v0.Mirror( n0 );
		return	Assign( v0.Add( p0 ) );			// this = v0 + p0;
	}
*/

	/**
	 * ベクトルまたは3次元座標値として、自身にアフィン変換を施す
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @param  ispnt	(( I )) ベクトル:false,  3次元座標値:true
	 * @return			this の参照
	 */
/*
	public final lvVector
	Trans( lvMatCalc m0, boolean ispnt )
	{
		if( ispnt )
			MulPosAssign( m0 );		// this *= m0;
		else
			MulVecAssign( m0 );		// this %= m0;
		return	this;
	}
*/
	/**
	 * ベクトルとして、自身にアフィン変換を施す
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @return			this の参照
	 */
/*
	public final lvVector
	Trans( lvMatCalc m0 )
	{
		return	Trans( m0, false );
	}
*/
	
	/**
	 * 複数点を結ぶ多角形の単位平均法線ベクトルと面積を求め、自身に単位平均法線ベクトルを代入する（非推奨）
	 * @param  nP0		(( I )) 点の配列
	 * @param  num0		(( I )) 点の数
	 * @param  a0p		(( O )) 面積
	 * @param  eps		(( I )) 許容誤差
	 */
	public final void
	Normal( lvVector nP0[], int num0, lvDouble a0p, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVector.Normal(0)" );

		lvVector  n0 = Gbl().tvNormal[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );	// n0 = new lvVector( 0.0, 0.0, 0.0 );
		lvVector  v  = Gbl().tvNormal[1];									// v  = new lvVector( *, *, * );
		double	  d0 = 0.0;
		for( int i=0; i<num0; i++ ) {
			int  j = ( i == num0-1 ) ? 0 : i+1;
			
			n0.AddAssign( v.SetXYZ( ( nP0[i].y - nP0[j].y ) * ( nP0[i].z + nP0[j].z ),
									( nP0[i].z - nP0[j].z ) * ( nP0[i].x + nP0[j].x ),
									( nP0[i].x - nP0[j].x ) * ( nP0[i].y + nP0[j].y ) ) );
					// n0 += lvVector( ( nP0[i].y - nP0[j].y ) * ( nP0[i].z + nP0[j].z ),
					//				   ( nP0[i].z - nP0[j].z ) * ( nP0[i].x + nP0[j].x ),
					//				   ( nP0[i].x - nP0[j].x ) * ( nP0[i].y + nP0[j].y ) );
// Decord ( Begin )
		//	n0.AddAssign( v.SetXYZ_Local( ( nP0[i].y * nP0[j].z - nP0[j].y * nP0[i].z ),
		//							      ( nP0[i].z * nP0[j].x - nP0[j].z * nP0[i].x ),
		//							      ( nP0[i].x * nP0[j].y - nP0[j].x * nP0[i].y ) ) );
// Decord  ( End )
					
			d0 += nP0[j].Sub( nP0[i] ).Length();		// d0 += ( nP0[j] - nP0[i] ).Length();
		}
		n0.MulAssign( 0.5 );		// n0 *= 0.5;
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
			
		Assign( n0 );
	}
	/**
	 * 複数点を結ぶ多角形の単位平均法線ベクトルと面積を求め、自身に単位平均法線ベクトルを代入する（推奨）
	 * @param  nP0		(( I )) 点の配列
	 * @param  num0		(( I )) 点の数
	 * @param  a0p		(( O )) 面積
	 */
	public final void
	Normal( lvVector nP0[], int num0, lvDouble a0p ) throws lvThrowable
	{
		Normal( nP0, num0, a0p, lvEps.l1 );
	}
	/**
	 * 複数点を結ぶ多角形の単位平均法線ベクトルを求め、自身に代入する（推奨）
	 * @param  nP0		(( I )) 点の配列
	 * @param  num0		(( I )) 点の数
	 */
	public final void
	Normal( lvVector nP0[], int num0 ) throws lvThrowable
	{
		Normal( nP0, num0, null, lvEps.l1 );
	}
	
	/**
	 * 複数点を結ぶ多角形の中心位置を求め、自身に代入する
	 * @param  nP0		(( I )) 点の配列
	 * @param  num0		(( I )) 点の数
	 * @return			中心位置（必ず数値関数か Assign系関数で包むこと）
	 */
	public final void
	Center( lvVector nP0[], int num0 ) throws lvThrowable
	{
		lvVector  n0 = Gbl().tvCenter[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );	// n0 = new lvVector( 0.0, 0.0, 0.0 );

		for( int i=0; i<num0; i++ )
			n0.AddAssign( nP0[ i ] );
			
		n0.DivAssign( num0 );
		
		Assign( n0 );
	}

	/**
	 * lvVecDt型のベクトル値を自身にセットする
	 * @param  src		(( I )) lvVecDt型のベクトル値
	 */
	public final void
	VecDt2Vector( lvVecDt src )
	{
		x = src.x;	y = src.y;	z = src.z;
	}
	/**
	 * 自身のベクトル値をlvVecDt変数にセットする
	 * @param  dst		(( O )) lvVecDt変数
	 */
	public final void
	Vector2VecDt( lvVecDt dst )
	{
		dst.x = x;	dst.y = y;	dst.z = z;
	}
	

// -------------------------------------------------------------------
//		lvKernelGreg用 API
// -------------------------------------------------------------------

	/**
	 * 自身にX,Y,Z値をセットする
	 * @param  x		(( I )) X値
	 * @param  y		(( I )) Y値
	 * @param  x		(( I )) Z値
	 * @return			this の参照
	 */
	public final lvVector
	SetXYZ( double x, double y, double z )
	{
		this.x = x;  this.y = y;  this.z = z;
		return	this;
	}	
	
	/**
	 * 自身を単位ベクトルにする（非推奨）
	 * @param  eps		(( I )) 許容誤差
	 * @return			this の参照
	 */
	public final lvVector
	UnitAssign( double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVector.Unit(0)" );
		double	len2 = Length2();

		if( Eps().IsZero2( len2, eps ) )
			x = y = z = 0.0;
		else
			DivAssign( Math.sqrt( len2 ) );
			
		return	this;
	}
	/**
	 * 自身を単位ベクトルにする（推奨）
	 * @return			this の参照
	 */
	public final lvVector
	UnitAssign() throws lvThrowable
	{
		return	UnitAssign( lvEps.l1 );
	}

	/**
	 * 原点を通る回転軸 u0 で自身を回転させる
	 * @param  u0		(( I )) 回転軸方向ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  a0		(( I )) 回転角（ラジアン）
	 * @return			this の参照
	 */
	public final lvVector
	Rotate( lvVecCalc u0, double a0 )
	{
		a0 *= 0.5;
		lvDouble  sinval_r = Gbl().tdRotate0[0];		sinval_r.val = Math.sin( a0 );
					// sinval_r = new lvDouble( Math.sin( a0 ) );
		lvDouble  cosval_r = Gbl().tdRotate0[1];		cosval_r.val = Math.cos( a0 );
					// cosval_r = new lvDouble( Math.cos( a0 ) );

		lvDouble  s0_r = Gbl().tdRotate0[2];		s0_r.val = sinval_r.val * ( u0.Dot( this ) );
					// s0_r = new lvDouble( sinval_r.val * ( u0.Dot( this ) ) ); ... s0 = sinval * ( u0 * this )
		lvVector  v0   = Gbl().tvRotate0[0];										// v0 = new lvVector();
		v0.Assign( ( cosval_r.Mul( this ) ).Add( sinval_r.Mul( u0.Cross( this ) ) ) );
					// v0 = cosval * this + sinval * ( u0 % this )
		Assign( ( cosval_r.Mul( v0 ) ).Add( sinval_r.Mul( ( u0.Cross( v0 ) ).Add( s0_r.Mul( u0 ) ) ) ) );
					// this = cosval * v0 + sinval * ( u0 % v0 + s0 * u0 )

		return	this;
	}
	/**
	 * p0 を通る回転軸 u0 で自身を回転させる
	 * @param  p0		(( I )) 回転軸上の点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  u0		(( I )) 回転軸方向ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  a0		(( I )) 回転角（ラジアン）
	 * @return			this の参照
	 */
/*
	public final lvVector
	Rotate( lvVecCalc p0, lvVecCalc u0, double a0 )
	{
		lvVector  v0 = Gbl().tvRotate1[0];			// v0 = new lvVector();
		v0.Assign( Sub( p0 ) );					// v0 = this - p0;
		v0.Rotate( u0, a0 );
		return	Assign( v0.Add( p0 ) );			// this = v0 + p0;
	}
*/

	/**
	 * 自身の点を含めた3点を結ぶ多角形の単位平均法線ベクトルと面積を求める（非推奨）	<br>
	 * 自身は単位平均法線ベクトルとなる
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  a0p		(( O )) 面積
	 * @param  eps		(( I )) 許容誤差
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvDouble a0p, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.NormalAssign(00)" );

		lvVector  n0 = Gbl().tvNormalAssign0[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );		// n0 = new lvVector( 0.0, 0.0, 0.0 );
		lvVector  v  = Gbl().tvNormalAssign0[1];									// v  = new lvVector( *, *, * );

		n0.AddAssign( v.SetXYZ( ( y - p1.y ) * ( z + p1.z ),			// n0 += lvVector( ( y - p1.y ) * ( z + p1.z ),
								( z - p1.z ) * ( x + p1.x ),			//				   ( z - p1.z ) * ( x + p1.x ),
								( x - p1.x ) * ( y + p1.y ) ) );		//				   ( x - p1.x ) * ( y + p1.y ) );
		n0.AddAssign( v.SetXYZ( ( p1.y - p2.y ) * ( p1.z + p2.z ),		// n0 += lvVector( ( p1.y - p2.y ) * ( p1.z + p2.z ),
								( p1.z - p2.z ) * ( p1.x + p2.x ),		//				   ( p1.z - p2.z ) * ( p1.x + p2.x ),
								( p1.x - p2.x ) * ( p1.y + p2.y ) ) );	//				   ( p1.x - p2.x ) * ( p1.y + p2.y ) );
		n0.AddAssign( v.SetXYZ( ( p2.y - y ) * ( p2.z + z ),			// n0 += lvVector( ( p2.y - y ) * ( p2.z + z ),
								( p2.z - z ) * ( p2.x + x ),			//				   ( p2.z - z ) * ( p2.x + x ),
								( p2.x - x ) * ( p2.y + y ) ) );		//				   ( p2.x - x ) * ( p2.y + y ) );
// Decord ( Begin )
	//	n0.AddAssign( v.SetXYZ( ( y * p1.z - p1.y * z ), ( z * p1.x - p1.z * x ), ( x * p1.y - p1.x * y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p1.y * p2.z - p2.y * p1.z ), ( p1.z * p2.x - p2.z * p1.x ),  ( p1.x * p2.y - p2.x * p1.y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p2.y * z - y * p2.z ), ( p2.z * x - z * p2.x ), ( p2.x * y - x * p2.y ) ) );
// Decord  ( End )
 
		n0.MulAssign( 0.5 );	// n0 *= 0.5;

		double	d0 = 0.0;
		d0 = ( p1.Sub( this ) ).Length() + ( p2.Sub( p1 ) ).Length() + ( Sub( p2 ) ).Length();
				// d0 = ( p1 - this ).Length() + ( p2 - p1 ).Length() + ( this - p2 ).Length();
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
			
		Assign( n0 );
	}
	/**
	 * 自身の点を含めた3点を結ぶ多角形の単位平均法線ベクトルと面積を求める（推奨））	<br>
	 * 自身は単位平均法線ベクトルとなる
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  a0p		(( O )) 面積
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvDouble a0p ) throws lvThrowable
	{
		NormalAssign( p1, p2, a0p, lvEps.l1 );
	}
	/**
	 * 自身の点を含めた3点を結ぶ多角形の単位平均法線ベクトルを求める（推奨））	<br>
	 * 自身は単位平均法線ベクトルとなる
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		NormalAssign( p1, p2, null, lvEps.l1 );
	}

	/**
	 * 自身の点を含めた4点を結ぶ多角形の単位平均法線ベクトルと面積を求める（非推奨））	<br>
	 * 自身は単位平均法線ベクトルとなる
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p3		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  a0p		(( O )) 面積
	 * @param  eps		(( I )) 許容誤差
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3, lvDouble a0p, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.NormalAssign(10)" );

		lvVector  n0 = Gbl().tvNormalAssign1[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );		// n0 = new lvVector( 0.0, 0.0, 0.0 );
		lvVector  v  = Gbl().tvNormalAssign1[1];									// v  = new lvVector( *, *, * );

		n0.AddAssign( v.SetXYZ( ( y - p1.y ) * ( z + p1.z ),			// n0 += lvVector( ( y - p1.y ) * ( z + p1.z ),
								( z - p1.z ) * ( x + p1.x ),			//				   ( z - p1.z ) * ( x + p1.x ),
								( x - p1.x ) * ( y + p1.y ) ) );		//				   ( x - p1.x ) * ( y + p1.y ) );
		n0.AddAssign( v.SetXYZ( ( p1.y - p2.y ) * ( p1.z + p2.z ),		// n0 += lvVector( ( p1.y - p2.y ) * ( p1.z + p2.z ),
								( p1.z - p2.z ) * ( p1.x + p2.x ),		//				   ( p1.z - p2.z ) * ( p1.x + p2.x ),
								( p1.x - p2.x ) * ( p1.y + p2.y ) ) );	//				   ( p1.x - p2.x ) * ( p1.y + p2.y ) );
		n0.AddAssign( v.SetXYZ( ( p2.y - p3.y ) * ( p2.z + p3.z ),		// n0 += lvVector( ( p2.y - p3.y ) * ( p2.z + p3.z ),
								( p2.z - p3.z ) * ( p2.x + p3.x ),		//				   ( p2.z - p3.z ) * ( p2.x + p3.x ),
								( p2.x - p3.x ) * ( p2.y + p3.y ) ) );	//				   ( p2.x - p3.x ) * ( p2.y + p3.y ) );
		n0.AddAssign( v.SetXYZ( ( p3.y - y ) * ( p3.z + z ),			// n0 += lvVector( ( p3.y - y ) * ( p3.z + z ),
								( p3.z - z ) * ( p3.x + x ),			//				   ( p3.z - z ) * ( p3.x + x ),
								( p3.x - x ) * ( p3.y + y ) ) );		//				   ( p3.x - x ) * ( p3.y + y ) );
// Decord ( Begin )
	//	n0.AddAssign( v.SetXYZ( ( y * p1.z - p1.y * z ), ( z * p1.x - p1.z * x ), ( x * p1.y - p1.x * y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p1.y * p2.z - p2.y * p1.z ), ( p1.z * p2.x - p2.z * p1.x ),  ( p1.x * p2.y - p2.x * p1.y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p2.y * p3.z - p3.y * p2.z ), ( p2.z * p3.x - p3.z * p2.x ),  ( p2.x * p3.y - p3.x * p2.y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p3.y * z - y * p3.z ), ( p3.z * x - z * p3.x ), ( p3.x * y - x * p3.y ) ) );
// Decord  ( End )

		n0.MulAssign( 0.5 );	// n0 *= 0.5;

		double	d0 = 0.0;
		d0 = ( p1.Sub( this ) ).Length() + ( p2.Sub( p1 ) ).Length() +
			 ( p3.Sub( p2	) ).Length() + (	Sub( p3 ) ).Length();
				// d0 = ( p1 - this ).Length() + ( p2 - p1 ).Length() + ( p3 - p2 ).Length() + ( this - p3 ).Length();
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
			
		Assign( n0 );
	}
	/**
	 * 自身の点を含めた4点を結ぶ多角形の単位平均法線ベクトルと面積を求める（推奨））	<br>
	 * 自身は単位平均法線ベクトルとなる
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p3		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  a0p		(( O )) 面積
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3, lvDouble a0p ) throws lvThrowable
	{
		NormalAssign( p1, p2, p3, a0p, lvEps.l1 );
	}
	/**
	 * 自身の点を含めた4点を結ぶ多角形の単位平均法線ベクトルを求める（推奨））	<br>
	 * 自身は単位平均法線ベクトルとなる
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p3		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 */
/*
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3 ) throws lvThrowable
	{
		NormalAssign( p1, p2, p3, null, lvEps.l1 );
	}
*/

}
