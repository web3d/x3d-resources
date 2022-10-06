//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvVecCalc.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Vector型の数値演算用クラス    （共通部）<br>
 * 注）lvDblCalc,lvDouble,lvVecCalc,lvVector,lvMatCalc,lvMatrixクラス内以外では、lvVecCalcオブジェクトを生成してはならない
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvVecCalc extends lvRoot {
	
	/** 定数 --- 少なくとも、一方のベクトルの長さが 0 である		*/
	public static final byte  LV_ANGSTAT_ZEROVEC	= 0;
	/** 定数 --- 2本のベクトルの成す角度が 0 である					*/
	public static final byte  LV_ANGSTAT_ZEROANG	= 1;
	/** 定数 --- 2本のベクトルが G1接続している						*/
	public static final byte  LV_ANGSTAT_G1			= 2;
	/** 定数 --- その他												*/
	public static final byte  LV_ANGSTAT_OTHER		= 3;
	
// -------------------------------------------------------------------

	/** X要素 */
	public double  x;
	/** Y要素 */
	public double  y;
	/** Z要素 */
	public double  z;
	
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
			GlobalBuf( dt );
			GlobalTmp( dt );
		}

		/** lvVecCalcのresult用の new 代用バッファ数			*/
		private static final int  num_resultBufV  = 128;
		/** lvVecCalcのresult用の new 代用バッファエリア		*/
		private lvVecCalc  resultBufV[]    = null;
		/** lvVecCalcのresult用の new 代用バッファカウンタ		*/
		private int		   cnt_resultBufV  = 0;
	
		/** lvDblCalcのresult用の new 代用バッファ数			*/
		private static final int  num_resultBufD  = 128;
		/** lvDblCalcのresult用の new 代用バッファエリア		*/
		private lvDouble   resultBufD[]	  = null;
		/** lvDblCalcのresult用の new 代用バッファカウンタ		*/
		private int		   cnt_resultBufD  = 0;
		
		/**
		 * result用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalBuf( lvGlobal dt )
		{
			resultBufV = new lvVecCalc[ num_resultBufV ];
			for( int i=0; i<num_resultBufV; i++ )
				resultBufV[ i ] = new lvVecCalc( dt );

			resultBufD = new lvDouble[ num_resultBufD ];
			for( int i=0; i<num_resultBufD; i++ )
				resultBufD[ i ] = new lvDouble( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
//		private lvVector  tvOnLine[]      = null;
//		private lvVector  tvOnLnSeg[]     = null;
//		private lvDouble  tdOnLnSeg[]     = null;
		private lvVector  tvAngleStatus[] = null;
		private lvDouble  tdAngleStatus[] = null;
		private lvVector  tvIntersecLinePlane[] = null;

		/** ローカル変数用の new 代用バッファエリア（lvKernelGreg用）		*/
//		private lvVector  tvAngle[]       = null;
//		private lvVector  tvDivide[]      = null;
		private lvDouble  tdIsLine[]      = null;
		private lvVector  tvIsLine[]      = null;
		private lvVector  tvIsPlane[]     = null;
		private lvDouble  tdIsPlane[]     = null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
//			tvOnLine      = new lvVector[  8 ];		for( int i=0; i<8;  i++ )	tvOnLine[ i ]      = new lvVector( dt );
//			tvOnLnSeg     = new lvVector[  8 ];		for( int i=0; i<8;  i++ )	tvOnLnSeg[ i ]     = new lvVector( dt );
//			tdOnLnSeg     = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdOnLnSeg[ i ]     = new lvDouble( dt );
			tvAngleStatus = new lvVector[ 12 ];		for( int i=0; i<12; i++ )	tvAngleStatus[ i ] = new lvVector( dt );
			tdAngleStatus = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdAngleStatus[ i ] = new lvDouble( dt );
			tvIntersecLinePlane = new lvVector[ 4 ];
												for( int i=0; i<4; i++ )	tvIntersecLinePlane[ i ] = new lvVector( dt );
			
			// ローカル変数用の new 代用バッファ初期化（lvKernelGreg用）
//			tvAngle       = new lvVector[  4 ];		for( int i=0; i<4;  i++ )	tvAngle[ i ]       = new lvVector( dt );
//			tvDivide      = new lvVector[  8 ];		for( int i=0; i<8;  i++ )	tvDivide[ i ]      = new lvVector( dt );
			tdIsLine      = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdIsLine[ i ]      = new lvDouble( dt );
			tvIsLine      = new lvVector[  2 ];		for( int i=0; i<2;  i++ )	tvIsLine[ i ]      = new lvVector( dt );
			tvIsPlane     = new lvVector[  4 ];		for( int i=0; i<4;  i++ )	tvIsPlane[ i ]     = new lvVector( dt );
			tdIsPlane     = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdIsPlane[ i ]     = new lvDouble( dt );
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gVecCalc;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ。lvVecCalc,lvVectorクラス内以外では、使用してはならない
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvVecCalc( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * コピーコンストラクタ。lvVecCalc,lvVectorクラス内以外では、使用してはならない
	 * @param  dt		(( I )) グローバルデータ
	 * @param  val		(( I )) コピー元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 */
	public  lvVecCalc( lvGlobal dt, lvVecCalc val )
	{
		super( dt );
		x = val.x;	y = val.y;	z = val.z;
	}
	/**
	 * 初期値 x,y,z のコンストラクタ。lvVecCalc,lvVectorクラス内以外では、使用してはならない
	 * @param  dt		(( I )) グローバルデータ
	 * @param  x		(( I )) コンストラクタの初期値X
	 * @param  y		(( I )) コンストラクタの初期値Y
	 * @param  z		(( I )) コンストラクタの初期値Z
	 */
	public  lvVecCalc( lvGlobal dt, double x, double y, double z )
	{
		super( dt );
		this.x = x;  this.y = y;  this.z = z;
	}

// -------------------------------------------------------------------

	/**
	 * 自身のベクトル値にコピーする。lvDouble,lvVecCalc,lvVector,lvMatCalc,lvMatrixクラス内以外では、使用してはならない
	 * @param  val		(( I )) コピー元。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			this の参照
	 */
	public final lvVecCalc
	Copy_Local( lvVecCalc val )
	{
		x = val.x;	y = val.y;	z = val.z;
		return	this;
	}
	
	/**
	 * 自身にX,Y,Z値をセットする
	 * @param  x		(( I )) X値
	 * @param  y		(( I )) Y値
	 * @param  x		(( I )) Z値
	 * @return			this の参照
	 */
	private lvVecCalc
	SetXYZ_Local( double x, double y, double z )
	{
		this.x = x;  this.y = y;  this.z = z;
		return	this;
	}	

	/** lvVecCalcのresult用の new 代用バッファのインクリメント		*/
	private final void
	IncResultBufV()
	{
		Gbl().cnt_resultBufV = ( Gbl().cnt_resultBufV + 1 ) % Gbl().num_resultBufV;
	}
	/** lvDblCalcのresult用の new 代用バッファのインクリメント		*/
	private final void
	IncResultBufD()
	{
		Gbl().cnt_resultBufD = ( Gbl().cnt_resultBufD + 1 ) % Gbl().num_resultBufD;
	}
	
	/**
	 * Vector型の加算関数（非推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Add( lvVecCalc val, lvVecCalc result )
	{
		result.SetXYZ_Local( ( x + val.x ), ( y + val.y ), ( z + val.z ) );
		return	result;
	}
	/**
	 * Vector型の加算関数（推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Add( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Add( val, result );
	}

	/**
	 * Vector型の減算関数（非推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Sub( lvVecCalc val, lvVecCalc result )
	{
		result.SetXYZ_Local( ( x - val.x ), ( y - val.y ), ( z - val.z ) );
		return	result;
	}
	/**
	 * Vector型の減算関数（推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Sub( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Sub( val, result );
	}

	/**
	 * Vector型のスカラー倍関数（非推奨）
	 * @param  k		(( I )) スカラー
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Mul( double k, lvVecCalc result )
	{
		result.SetXYZ_Local( ( x * k ), ( y * k ),	( z * k ) );
		return	result;
	}
	/**
	 * Vector型のスカラー倍関数（推奨）
	 * @param  k		(( I )) スカラー
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Mul( double k )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Mul( k, result );
	}
	
	/**
	 * Vector型のスカラー除算関数（非推奨）
	 * @param  k		(( I )) スカラー
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Div( double k, lvVecCalc result ) throws lvThrowable
	{
		Err().Assert( ( k != 0.0 ), "lvVecCalc.Div(0)" );
		result.SetXYZ_Local( ( x / k ), ( y / k ),	( z / k ) );
		return	result;
	}
	/**
	 * Vector型のスカラー除算関数（推奨）
	 * @param  k		(( I )) スカラー
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Div( double k ) throws lvThrowable
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Div( k, result );
	}

	/**
	 * Vector型の内積関数
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			内積値（double:基本データ型）
	 */
	public final double
	Dot( lvVecCalc val )
	{
		return	x * val.x + y * val.y + z * val.z;
	}
	/**
	 * Vector型の内積関数（非推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( I )) 返り値
	 * @return			内積値（lvDouble:クラス）
	 */
	public final lvDouble
	DotR( lvVecCalc val, lvDouble result )
	{
		result.val = Dot( val );
		return	result;
	}
	/**
	 * Vector型の内積関数（推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			内積値（lvDouble:クラス）。必ず数値関数で包むか、double a = DotR( b ).val; のようにする。
	 */
	public final lvDouble
	DotR( lvVecCalc val )
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	DotR( val, result );
	}

	/**
	 * Vector型の外積関数（非推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Cross( lvVecCalc val, lvVecCalc result )
	{
		result.SetXYZ_Local( ( y*val.z - z*val.y ), ( z*val.x - x*val.z ), ( x*val.y - y*val.x ) );
		return	result;
	}
	/**
	 * Vector型の外積関数（推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Cross( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Cross( val, result );
	}

	/**
	 * 自身（ベクトル:lvVector）に行列 m2 でアフィン変換したベクトルを返す（非推奨）
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
/*
	public final lvVecCalc
	MulVec( lvMatCalc m2, lvVecCalc result )
	{
		result.SetXYZ_Local( x * m2.m[0][0] + y * m2.m[1][0] + z * m2.m[2][0],
							 x * m2.m[0][1] + y * m2.m[1][1] + z * m2.m[2][1],
							 x * m2.m[0][2] + y * m2.m[1][2] + z * m2.m[2][2] );
		return	result;
	}
*/
	/**
	 * 自身（ベクトル:lvVector）に行列 m2 でアフィン変換したベクトルを返す（推奨）
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
/*
	public final lvVecCalc
	MulVec( lvMatCalc m2 )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	MulVec( m2, result );
	}
*/

	/**
	 * 自身（3次元座標値としてのベクトル:lvVector）に行列 m2 でアフィン変換したベクトルを返す（非推奨）
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
/*
	public final lvVecCalc
	MulPos( lvMatCalc m2, lvVecCalc result )
	{
		result.SetXYZ_Local( x * m2.m[0][0] + y * m2.m[1][0] + z * m2.m[2][0] + m2.m[3][0],
							 x * m2.m[0][1] + y * m2.m[1][1] + z * m2.m[2][1] + m2.m[3][1],
							 x * m2.m[0][2] + y * m2.m[1][2] + z * m2.m[2][2] + m2.m[3][2] );
		return	result;
	}
*/
	/**
	 * 自身（3次元座標値としてのベクトル:lvVector）に行列 m2 でアフィン変換したベクトルを返す（推奨）
	 * @param  m0		(( I )) 行列。必ず lvMatrix型変数、または数値関数自身を入れる（ lvMatCalc型は使用しないこと ）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
/*
	public final lvVecCalc
	MulPos( lvMatCalc m2 )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	MulPos( m2, result );
	}
*/

	/**
	 * Vector型の反転関数（非推奨）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @return			result の参照
	 */
	public final lvVecCalc
	Neg( lvVecCalc result )
	{
		result.SetXYZ_Local( -x, -y, -z );
		return	result;
	}
	/**
	 * Vector型の反転関数（推奨）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Neg()
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Neg( result );
	}

	/**
	 * 自身を単位ベクトルにする（非推奨）
	 * @param  result	(( O )) 必ず lvVector型変数とする（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			result の参照
	 */
	public final lvVecCalc
	Unit( lvVecCalc result, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVector.Unit(0)" );
		double	len2 = Length2();
		
		if( Eps().IsZero2( len2, eps ) )
			result.x = result.y = result.z = 0.0;
		else {
			double  len = Math.sqrt( len2 );
			result.SetXYZ_Local( x/len, y/len, z/len );
		}
			
		return	result;
	}
	/**
	 * 自身を単位ベクトルにする（非推奨）
	 * @param  eps		(( I )) 許容誤差
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Unit( double eps ) throws lvThrowable
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Unit( result, eps );
	}
	/**
	 * 自身を単位ベクトルにする（推奨）
	 * @return			必ず数値関数か Assign系関数で包むこと
	 */
	public final lvVecCalc
	Unit() throws lvThrowable
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Unit( result, lvEps.l1 );
	}

	/**
	 * 自身（ベクトル）が eps の誤差内で 0 かどうか判定する（非推奨）
	 * @param  eps		(( I )) 許容誤差
	 * @return			非0: false,		0: true
	 */
	public final boolean
	IsZero( double eps ) throws lvThrowable
	{
		Err().Assert( ( Dot( this ) >= 0.0 ), "lvVecCalc.IsZero(0)" );
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsZero(1)" );

		if( x < -eps || x > eps )
			return	false;

		if( x == 0.0 && y == 0.0 && z == 0.0 )
			return	true;

		if( y < -eps || y > eps )
			return	false;
		if( z < -eps || z > eps )
			return	false;

		Err().Assert( ( eps > 0.0 ), "lvVecCalc.IsZero(2)" );
		double	eps_sqrt1_3 = ( eps == lvEps.l1 )
			? lvEps.l1xSqrt1_3 : eps * lvConst.LV_SQRT1_3;
		if( -eps_sqrt1_3 <= x && x <= eps_sqrt1_3 &&
			-eps_sqrt1_3 <= y && y <= eps_sqrt1_3 &&
			-eps_sqrt1_3 <= z && z <= eps_sqrt1_3 )
		{
			return	true;
		}
	
		return	Eps().IsZero2( Dot( this ), eps );
	}
	/**
	 * 自身（ベクトル）が lvEps.l1 の誤差内で 0 かどうか判定する（推奨）
	 * @return			非0: false,		0: true
	 */
	public final boolean
	IsZero() throws lvThrowable
	{
		return	IsZero( lvEps.l1 );
	}

	/**
	 * 自身（ベクトル）とベクトル v0 が eps の誤差内で等しいかどうか判定する（非推奨）
	 * @param  v0		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			等しくない: false,	等しい: true
	 */
	public final boolean
	IsSame( lvVecCalc v0, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsSame(0)" );
		return	v0.Sub( this ).IsZero( eps );		// v0.Sub( this ) ... ( v0 - this )
	}
	/**
	 * 自身（ベクトル）とベクトル v0 が lvEps.l1 の誤差内で等しいかどうか判定する（推奨）
	 * @param  v0		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			等しくない: false,	等しい: true
	 */
	public final boolean
	IsSame( lvVecCalc v0 ) throws lvThrowable
	{
		return	IsSame( v0, lvEps.l1 );
	}

	/**
	 * 自身（単位ベクトル）と単位ベクトル v0 が eps の誤差内で垂直かどうか判定する（非推奨）
	 * @param  v0		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			垂直でない: false,	垂直: true
	 */
/*
	public final boolean
	IsPerp( lvVecCalc u0, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPerp(0)" );
		Err().Assert( Eps().IsSame21( u0.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPerp(1)" );
		Err().Assert( ( 0.0 <= eps && eps < 1.0 ), "lvVecCalc.IsPerp(2)" );
		return	( Math.abs( Dot( u0 ) ) <= eps ) ? true : false;		// Dot( u0 ) ... ( this * u0 )
	}
*/
	/**
	 * 自身（単位ベクトル）と単位ベクトル v0 が lvEps.a0 の誤差内で垂直かどうか判定する（推奨）
	 * @param  v0		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			垂直でない: false,	垂直: true
	 */
/*
	public final boolean
	IsPerp( lvVecCalc u0 ) throws lvThrowable
	{
		return	IsPerp( u0, lvEps.a0 );
	}
*/

	/**
	 * 自身のベクトル長を返す
	 * @return			ベクトル長（double:基本データ型）
	 */
	public final double
	Length() throws lvThrowable
	{
		return	Math.sqrt( Length2() );
	}
	/**
	 * 自身のベクトル長を返す（非推奨）
	 * @param  result	(( I )) 返り値
	 * @return			ベクトル長（lvDouble:クラス）
	 */
/*
	public final lvDouble
	LengthR( lvDouble result ) throws lvThrowable
	{
		result.val = Length();
		return	result;
	}
*/
	/**
	 * 自身のベクトル長を返す（推奨）
	 * @return			ベクトル長（lvDouble:クラス）。必ず数値関数で包むか、double a = LengthR().val; のようにする。
	 */
/*
	public final lvDouble
	LengthR() throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];		// result = new lvDouble();
		IncResultBufD();
		return	LengthR( result );
	}
*/
	
	/**
	 * 自身のベクトル長の平方を返す
	 * @return			ベクトル長の平方（double:基本データ型）
	 */
	public final double
	Length2() throws lvThrowable
	{
		Err().Assert( ( Dot( this ) >= 0.0 ), "lvVecCalc.Length2(0)" );		// Dot( this ) ... ( this * this )
		return	Dot( this );
	}
	/**
	 * 自身のベクトル長の平方を返す（非推奨）
	 * @param  result	(( I )) 返り値
	 * @return			ベクトル長の平方（lvDouble:クラス）
	 */
/*
	public final lvDouble
	Length2R( lvDouble result ) throws lvThrowable
	{
		result.val = Length2();
		return	result;
	}
*/
	/**
	 * 自身のベクトル長の平方を返す（推奨）
	 * @return			ベクトル長の平方（lvDouble:クラス）。必ず数値関数で包むか、double a = Length2R().val; のようにする。
	 */
/*
	public final lvDouble
	Length2R() throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];		// result = new lvDouble();
		IncResultBufD();
		return	Length2R( result );
	}
*/

	/**
	 * 自身（ベクトル）とベクトル val の距離を求める
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			距離（double:基本データ型）
	 */
/*
	public final double
	Dist( lvVecCalc val ) throws lvThrowable
	{
		return	Math.sqrt( Dist2( val ) );
	}
*/
	/**
	 * 自身（ベクトル）とベクトル val の距離を求める（非推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( I )) 返り値
	 * @return			距離（lvDouble:クラス）
	 */
/*
	public final lvDouble
	DistR( lvVecCalc val, lvDouble result ) throws lvThrowable
	{
		result.val = Dist( val );
		return	result;
	}
*/
	/**
	 * 自身（ベクトル）とベクトル val の距離を求める（推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			距離（lvDouble:クラス）。必ず数値関数で包むか、double a = DistR( b ).val; のようにする。
	 */
/*
	public final lvDouble
	DistR( lvVecCalc val ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	DistR( val, result );
	}
*/
	
	/**
	 * 自身（ベクトル）とベクトル val の距離の平方を求める
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			距離の平方（double:基本データ型）
	 */
/*
	public final double
	Dist2( lvVecCalc val ) throws lvThrowable
	{
		return	Sub( val ).Length2();
	}
*/
	/**
	 * 自身（ベクトル）とベクトル val の距離の平方を求める（非推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( I )) 返り値
	 * @return			距離の平方（lvDouble:クラス）
	 */
/*
	public final lvDouble
	Dist2R( lvVecCalc val, lvDouble result ) throws lvThrowable
	{
		result.val = Dist2( val );
		return	result;
	}
*/
	/**
	 * 自身（ベクトル）とベクトル val の距離の平方を求める（推奨）
	 * @param  val		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			距離の平方（lvDouble:クラス）。必ず数値関数で包むか、double a = Dist2R( b ).val; のようにする。
	 */
/*
	public final lvDouble
	Dist2R( lvVecCalc val ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	Dist2R( val, result );
	}
*/

	/**
	 * 自身の点が直線上にあるかどうかを調べる（非推奨）
	 * @param  p1		(( I )) 直線の始点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 直線の終点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			直線上にない:false,  直線上:true
	 */
/*
	public final boolean
	OnLine( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.OnLine(0)" );

		lvVector  v1 = Gbl().tvOnLine[0];					// v1 = new lvVector();
		v1.Assign( Sub( p1 ) );							// v1 = this - p1;
		if( v1.IsZero( eps ) )
			return	true;
		lvVector  v2 = Gbl().tvOnLine[1];					// v2 = new lvVector();
		v2.Assign( Sub( p2 ) );							// v2 = this - p2;
		if( v2.IsZero( eps ) )
			return	true;
		lvVector  v0 = Gbl().tvOnLine[2];					// v0 = new lvVector();
		v0.Assign( p2.Sub( p1 ) );						// v0 = p2 - p1;
		if( v0.IsZero( eps ) )
			return	false;

		lvVector  n0 = Gbl().tvOnLine[3];					// n0 = new lvVector();
		n0.Assign( v0.Cross( v1 ) );					// n0 = v0 % v1;
		double	  d0 = n0.Dot( n0 ) / v0.Dot( v0 );		// d0 = ( n0 * n0 ) / ( v0 * v0 );
		return	Eps().IsZero2( d0, eps );
	}
*/
	/**
	 * 自身の点が直線上にあるかどうかを調べる（推奨）
	 * @param  p1		(( I )) 直線の始点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 直線の終点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			直線上にない:false,  直線上:true
	 */
/*
	public final boolean
	OnLine( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	OnLine( p1, p2, lvEps.l1 );
	}
*/

	/**
	 * 自身の点が線分上にあるかどうかを調べる（非推奨）
	 * @param  p1		(( I )) 線分の始点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 線分の終点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			線分上にない:false,  線分上:true
	 */
/*
	public final boolean
	OnLnSeg( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.OnLnSeg(0)" );

		lvVector  v1 = Gbl().tvOnLnSeg[0];			// v1 = new lvVector();
		v1.Assign( Sub( p1 ) );					// v1 = this - p1;
		if( v1.IsZero( eps ) )
			return	true;
		lvVector  v2 = Gbl().tvOnLnSeg[1];			// v2 = new lvVector();
		v2.Assign( Sub( p2 ) );					// v2 = this - p2;
		if( v2.IsZero( eps ) )
			return	true;
		lvVector  v0 = Gbl().tvOnLnSeg[2];			// v0 = new lvVector();
		v0.Assign( p2.Sub( p1 ) );				// v0 = p2 - p1;
		if( v0.IsZero( eps ) )
			return	false;

		double	t0 = v0.Dot( v1 ) / v0.Dot( v0 );	// t0 = ( v0 * v1 ) / ( v0 * v0 );
		if( t0 < 0.0 || 1.0 < t0 )
			return	false;
		lvDouble  t0_r = Gbl().tdOnLnSeg[0];	t0_r.val =  t0;			// t0_r = new lvDouble( t0 );
		lvVector  p0   = Gbl().tvOnLnSeg[3];							// p0	= new lvVector();
		p0.Assign( p1.Add( t0_r.Mul( v0 ) ) );		// p0 = p1 + t0 * v0;
		return	IsSame( p0, eps );
	}
*/
	/**
	 * 自身の点が線分上にあるかどうかを調べる（推奨）
	 * @param  p1		(( I )) 線分の始点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 線分の終点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			線分上にない:false,  線分上:true
	 */
/*
	public final boolean
	OnLnSeg( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	OnLnSeg( p1, p2, lvEps.l1 );
	}
*/
	
	/**
	 * 「自身の点と点1からなる線分」と「自身の点と点2からなる線分」の角度に関する状態を調べる（非推奨）
	 * @param  p1		(( I )) 点1。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点2。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			LV_ANGSTAT_ZEROVEC, LV_ANGSTAT_ZEROANG, LV_ANGSTAT_G1, LV_ANGSTAT_OTHER のいづれか
	 */
	public final int
	AngleStatus( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.AngleStatus(0)" );

		lvVector  v1 = Gbl().tvAngleStatus[0];				// v1 = new lvVector();
		v1.Assign( Sub( p1 ) );							// v1 = this - p1;
		if( v1.IsZero( eps ) )
			return	LV_ANGSTAT_ZEROVEC;
		lvVector  v2 = Gbl().tvAngleStatus[1];				// v2 = new lvVector();
		v2.Assign( Sub( p2 ) );							// v2 = this - p2;
		if( v2.IsZero( eps ) )
			return	LV_ANGSTAT_ZEROVEC;
		lvVector  v0 = Gbl().tvAngleStatus[2];				// v0 = new lvVector();
		v0.Assign( p2.Sub( p1 ) );						// v0 = p2 - p1;
		if( v0.IsZero( eps ) )
			return	LV_ANGSTAT_ZEROANG;

		double	  t0;
		lvDouble  t0_r = Gbl().tdAngleStatus[0];			// t0_r = new lvDouble( t0 );
		lvVector  p0   = Gbl().tvAngleStatus[3];			// p0	= new lvVector();

		t0 = v0.Dot( v1 ) / v0.Dot( v0 );				// t0 = ( v0 * v1 ) / ( v0 * v0 );
		if( 0.0 <= t0 && t0 <= 1.0 ) {
			t0_r.val =  t0;
			p0.Assign( p1.Add( t0_r.Mul( v0 ) ) );		// p0 = p1 + t0 * v0;
			boolean  same = IsSame( p0, eps );
			if( same == true )
				return  LV_ANGSTAT_G1;
			else
				return  LV_ANGSTAT_OTHER;
		}
		
		lvVector  ps = Gbl().tvAngleStatus[4];				// ps = new lvVector();
		lvVector  pl = Gbl().tvAngleStatus[5];				// pl = new lvVector();
		lvVector  vs, vl;
		if( v1.Length2() >= v2.Length2() ) {
			pl.Assign( p1 );		vl = v1;
			ps.Assign( p2 );		vs = v2;
		}
		else {
			pl.Assign( p2 );		vl = v2;
			ps.Assign( p1 );		vs = v1;
		}
		vl.NegAssign();									// vl = -vl;
		vs.NegAssign();									// vs = -vs;
		
		t0 = vl.Dot( vs ) / vl.Dot( vl );				// t0 = ( vl * vs ) / ( vl * vl );
		if( 0.0 <= t0 && t0 <= 1.0 ) {
			t0_r.val =  t0;
			p0.Assign( Add( t0_r.Mul( vl ) ) );			// p0 = this + t0 * vl;
			boolean  same = ps.IsSame( p0, eps );
			if( same == true )
				return  LV_ANGSTAT_ZEROANG;
			else
				return  LV_ANGSTAT_OTHER;
		}
		
		return  LV_ANGSTAT_OTHER;
	}
	/**
	 * 「自身の点と点1からなる線分」と「自身の点と点2からなる線分」の角度に関する状態を調べる（推奨）
	 * @param  p1		(( I )) 点1。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点2。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			LV_ANGSTAT_ZEROVEC, LV_ANGSTAT_ZEROANG, LV_ANGSTAT_G1, LV_ANGSTAT_OTHER のいづれか
	 */
	public final int
	AngleStatus( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	AngleStatus( p1, p2, lvEps.l1 );
	}

	/**
	 * 「自身を始点とする線分」と平面が交差しているかどうか調べる（非推奨）
	 * @param  lEnd		(( I )) 線分の終点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  org		(( I )) 平面上の1点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  normal	(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @param  crossPos (( O )) 交差点の位置
	 * @param  crossT   (( O )) 交差点の t値
	 * @return			LV_INTERSEC_PARA, LV_INTERSEC_ON, LV_INTERSEC_CROSS のいづれか
	 */
	public final int
	IntersecLinePlane( lvVecCalc lEnd, lvVecCalc org, lvVecCalc normal, double eps, lvVector crossPos,
			lvDouble crossT ) throws lvThrowable
	{
		lvVector  vl = Gbl().tvIntersecLinePlane[0];			// vl = new lvVector();
		vl.Assign( lEnd.Sub( this ) );							// vl = lEnd - this;
		
		if( vl.IsZero( eps ) == true ) {
			lvVector  vd = Gbl().tvIntersecLinePlane[1];						// vd = new lvVector();
			vd.Assign( ( ( Add( lEnd ) ).Div( 2.0 ) ).Sub( org ) );				// vd = ( this + lEnd ) / 2.0 - org;
			if( Eps().IsZero( vd.Dot( normal ), eps ) == true )					// vd.Dot( normal ) --- vd * normal
				return  lvVector.LV_INTERSEC_ON;
				
			return  lvVector.LV_INTERSEC_PARA;
		}
		
		double  nor_vl = normal.Dot( vl );										// nor_vl = normal * vl;
		if( Eps().IsZero( nor_vl, eps ) == true ) {
			if( Eps().IsZero( ( Sub( org ) ).Dot( normal ), eps ) == true )		// ( this - org ) * normal
				return  lvVector.LV_INTERSEC_ON;
				
			return  lvVector.LV_INTERSEC_PARA;
		}
		
		crossT.val = -( normal.Dot( Sub( org ) ) ) / ( normal.Dot( vl ) );
									// crossT.val = -( normal * ( this - org ) ) / ( normal * vl );
		crossPos.Assign( Add( vl.Mul( crossT.val ) ) );							// crossPos = this + vl * crossT.val;
		
		return  lvVector.LV_INTERSEC_CROSS;
	}	 
	/**
	 * 「自身を始点とする線分」と平面が交差しているかどうか調べる（推奨）
	 * @param  lEnd		(( I )) 線分の終点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  org		(( I )) 平面上の1点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  normal	(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  crossPos (( O )) 交差点の位置
	 * @param  crossT   (( O )) 交差点の t値
	 * @return			LV_INTERSEC_PARA, LV_INTERSEC_ON, LV_INTERSEC_CROSS のいづれか
	 */
	public final int
	IntersecLinePlane( lvVecCalc lEnd, lvVecCalc org, lvVecCalc normal, lvVector crossPos, lvDouble crossT ) throws lvThrowable
	{
		return  IntersecLinePlane( lEnd, org, normal, lvEps.l1, crossPos, crossT );
	}


// -------------------------------------------------------------------
//		lvKernelGreg用 API
// -------------------------------------------------------------------

	/**
	 * 自身（単位ベクトル）と単位ベクトル v0 が eps の誤差内で平行かどうか判定する（非推奨）
	 * @param  v0		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			平行でない: false,	平行: true
	 */
	public final boolean
	IsPara( lvVecCalc u0, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPara(0)" );
		Err().Assert( Eps().IsSame21( u0.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPara(1)" );
		Err().Assert( ( 0.0 <= eps && eps < 1.0 ), "lvVecCalc.IsPara(2)" );
		return	( IsSame( u0, eps ) || IsSame( u0.Neg(), eps ) ) ? true : false;	// u0.Neg() ... -u0
	}
	/**
	 * 自身（単位ベクトル）と単位ベクトル v0 が lvEps.a0 の誤差内で平行かどうか判定する（推奨）
	 * @param  v0		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			平行でない: false,	平行: true
	 */
	public final boolean
	IsPara( lvVecCalc u0 ) throws lvThrowable
	{
		return	IsPara( u0, lvEps.a0 );
	}
	
	/**
	 * 自身（単位ベクトル）と単位ベクトル u1 の角度を求める
	 * @param  u1		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			角度：ラジアン（double:基本データ型）
	 */
	public final double
	Angle( lvVecCalc u1 ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( Length2(), 1.0, lvEps.e0 ), "lvVecCalc.Angle(00)" );
		Err().Assert( Eps().IsSame21( u1.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.Angle(01)" );

		double	f01 = Dot( u1 );

		if( f01 < -( 1.0 - 1.0e-12 ) ) {
			f01 = Add( u1 ).Length() / 2.0;						// Add( u1 ) ... this + u1
			return	lvConst.LV_PI - 2.0 * Math.asin( f01 );
		}

		if( f01 > ( 1.0 - 1.0e-12 ) ) {
			f01 = Sub( u1 ).Length() / 2.0;						// Sub( u1 ) ... this - u1
			return	2.0 * Math.asin( f01 );
		}

		return	Math.acos( f01 );
	}
	/**
	 * 自身（単位ベクトル）と単位ベクトル u1 の角度を求める（非推奨）
	 * @param  u1		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( I )) 返り値
	 * @return			角度：ラジアン（lvDouble:クラス）
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc u1, lvDouble result ) throws lvThrowable
	{
		result.val = Angle( u1 );
		return	result;
	}
*/
	/**
	 * 自身（単位ベクトル）と単位ベクトル u1 の角度を求める（推奨）
	 * @param  u1		(( I )) 単位ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			角度：ラジアン（lvDouble:クラス）。必ず数値関数で包むか、double a = AngleR( b ).val; のようにする。
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc u1 ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	AngleR( u1, result );
	}
*/

	/**
	 * 平面に射影した自身（ベクトル）とベクトル v1 の角度を求める（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 平面の法線ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			角度：ラジアン（double:基本データ型）
	 */
/*
	public final double
	Angle( lvVecCalc v1, lvVecCalc n0, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( n0.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.Angle(10)" );
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.Angle(11)" );

		lvVector  v2 = Gbl().tvAngle[ 0 ];						// v2 = new lvVector();
		v2.Assign( Sub( ( DotR( n0 ) ).Mul( n0 ) ) );			// v2 = this - ( this * n0 ) * n0;
		if( v2.UnitAssign( eps ).IsZero( lvEps.e0 ) )
			return	lvConst.LV_PI_2;

		lvVector  v3 = Gbl().tvAngle[ 1 ];						// v3 = new lvVector();
		v3.Assign( v1.Sub( ( v1.DotR( n0 ) ).Mul( n0 ) ) );		// v3 = v1 - ( v1 * n0 ) * n0;
		if( v3.UnitAssign( eps ).IsZero( lvEps.e0 ) )
			return	lvConst.LV_PI_2;

		double	theta = v2.Angle( v3 );
		Err().Assert( ( 0.0 <= theta && theta <= lvConst.LV_PI ), "lvVecCalc.Angle(12)" );
		theta = ( ( v2.Cross( v3 ) ).Dot( n0 ) >= 0.0 ) ? theta : lvConst.LV_2PI - theta;
					// ( v2.Cross( v3 ) ).Dot( n0 ) ... ( v2 % v3 ) * n0
		Err().Assert( ( 0.0 <= theta && theta <= lvConst.LV_2PI ), "lvVecCalc.Angle(13)" );
		return	( theta < lvConst.LV_2PI ) ? theta : 0.0;
	}
*/
	/**
	 * 平面に射影した自身（ベクトル）とベクトル v1 の角度を求める（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @param  result	(( I )) 返り値
	 * @return			角度：ラジアン（lvDouble:クラス）
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0, double eps, lvDouble result ) throws lvThrowable
	{
		result.val = Angle( v1, n0, eps );
		return	result;
	}
*/
	/**
	 * 平面に射影した自身（ベクトル）とベクトル v1 の角度を求める（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			角度：ラジアン（lvDouble:クラス）。必ず数値関数で包むか、double a = AngleR( b, c, e ).val; のようにする。
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0, double eps ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	AngleR( v1, n0, eps, result );
	}
*/

	/**
	 * 平面に射影した自身（ベクトル）とベクトル v1 の角度を求める（推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			角度：ラジアン（double:基本データ型）
	 */
/*
	public final double
	Angle( lvVecCalc v1, lvVecCalc n0 ) throws lvThrowable
	{
		return	Angle( v1, n0, lvEps.l1 );
	}
*/
	/**
	 * 平面に射影した自身（ベクトル）とベクトル v1 の角度を求める（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  result	(( I )) 返り値
	 * @return			角度：ラジアン（lvDouble:クラス）
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0, lvDouble result ) throws lvThrowable
	{
		result.val = Angle( v1, n0, lvEps.l1 );
		return	result;
	}
*/
	/**
	 * 平面に射影した自身（ベクトル）とベクトル v1 の角度を求める（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n0		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			角度：ラジアン（lvDouble:クラス）。必ず数値関数で包むか、double a = AngleR( b, c ).val; のようにする。
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0 ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	AngleR( v1, n0, lvEps.l1, result );
	}
*/

	/**
	 * 自身を2ベクトルに分解する（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  v2		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  e1p		(( O )) v1方向の成分
	 * @param  e2p		(( O )) v2方向の成分
	 * @param  eps1		(( I )) 長さを 0 と見なす許容誤差
	 * @param  eps0		(( I )) 角度／パラメータを 0 と見なす許容誤差
	 * @return			分解できたか？ 分解できず:false,  分解:true
	 */
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvDouble e1p, lvDouble e2p, double eps1, double eps0 ) throws lvThrowable
	{
		Err().Assert( ( eps1 >= 0.0 ), "lvVecCalc.Divide(00)" );
		Err().Assert( ( 0.0 <= eps0 && eps0 < 1.0 ), "lvVecCalc.Divide(01)" );

		double	f01 = Dot( v1 );				// f01 = this * v1;
		double	f02 = Dot( v2 );				// f02 = this * v2;
		double	f11 = v1.Dot( v1 );				// f11 = v1 * v1;
		double	f12 = v1.Dot( v2 );				// f12 = v1 * v2;
		double	f22 = v2.Dot( v2 );				// f22 = v2 * v2;
		double	eps2 = ( eps1 == lvEps.l1 ) ? lvEps.l1xl1 : eps1 * eps1;

		if( f11 <= eps2 ) {
			if( f22 <= eps2 ) {
				e1p.val = 0.0;
				e2p.val = 0.0;
				return	false;
			}
			e1p.val = 0.0;
			e2p.val = f02 / f22;
			return	IsSame( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ), eps1 );	// e1p * v1 + e2p * v2
		}

		if( f22 <= eps2 ) {
			e1p.val = f01 / f11;
			e2p.val = 0.0;
			return	IsSame( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ), eps1 );	// e1p * v1 + e2p * v2
		}

		double	det = f11 * f22 - f12 * f12;
		if( det <= f11 * f22 * eps0 * eps0 ) {
			e1p.val = ( ( f11 >= f22 ) ? f01 / f11 : f02 / f22 );
			e2p.val = 0.0;
			return	IsSame( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ), eps1 );	// e1p * v1 + e2p * v2
		}

		e1p.val = ( f01 * f22 - f12 * f02 ) / det;
		e2p.val = ( f11 * f02 - f01 * f12 ) / det;
		return	true;
	}
	/**
	 * 自身を2ベクトルに分解する（推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  v2		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  e1p		(( O )) v1方向の成分
	 * @param  e2p		(( O )) v2方向の成分
	 * @return			分解できたか？ 分解できず:false,  分解:true
	 */
/*
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvDouble e1p, lvDouble e2p ) throws lvThrowable
	{
		return	Divide( v1, v2, e1p, e2p, lvEps.l1, lvEps.a0 );
	}
*/
	
	/**
	 * 自身を3ベクトルに分解する（非推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  v2		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  v3		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  e1p		(( O )) v1方向の成分
	 * @param  e2p		(( O )) v2方向の成分
	 * @param  e2p		(( O )) v3方向の成分
	 * @param  eps1		(( I )) 長さを 0 と見なす許容誤差
	 * @param  eps0		(( I )) 角度／パラメータを 0 と見なす許容誤差
	 * @return			分解できたか？ 分解できず:false,  分解:true
	 */
/*
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvVecCalc v3, lvDouble e1p, lvDouble e2p, lvDouble e3p, double eps1, double eps0 )
			throws lvThrowable
	{
		Err().Assert( ( eps1 >= 0.0 ), "lvVecCalc.Divide(10)" );
		Err().Assert( ( 0.0 <= eps0 && eps0 < 1.0 ), "lvVecCalc.Divide(11)" );

		lvVector  u1 = Gbl().tvDivide[0];			// u1 = new lvVector();
		u1.Assign( v2.Cross( v3 ) );			// u1 = v2 % v3;
		double	  k1 = v1.Dot( u1 );			// k1 = v1 * u1;
		if( !Eps().IsZero( k1, eps1 ) && !Eps().IsZero( k1, eps0 ) )
			e1p.val = Dot( u1 ) / k1;			// e1p = ( this * u1 ) / k1;
		else
			e1p.val = 0.0;

		lvVector  u2 = Gbl().tvDivide[1];			// u2 = new lvVector();
		u2.Assign( v3.Cross( v1 ) );			// u2 = v3 % v1;
		double	  k2 = v2.Dot( u2 );			// k2 = v2 * u2;
		if( !Eps().IsZero( k2, eps1 ) && !Eps().IsZero( k2, eps0 ) )
			e2p.val =  Dot( u2 ) / k2;			// e2p = ( this * u2 ) / k2;
		else
			e2p.val =  0.0;

		lvVector  u3 = Gbl().tvDivide[2];			// u3 = new lvVector();
		u3.Assign( v1.Cross( v2 ) );			// u3 = v1 % v2;
		double	  k3 = v3.Dot( u3 );			// k3 = v3 * u3;
		if( !Eps().IsZero( k3, eps1 ) && !Eps().IsZero( k3, eps0 ) )
			e3p.val = Dot( u3 ) / k3;			// e3p = ( this * u3 ) / k3;
		else
			e3p.val = 0.0;

		return	IsSame( ( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ) ).Add( e3p.Mul( v3 ) ), eps1 );
						// e1p * v1 + e2p * v2 + e3p * v3
	}
*/
	/**
	 * 自身を3ベクトルに分解する（推奨）
	 * @param  v1		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  v2		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  v3		(( I )) ベクトル。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  e1p		(( O )) v1方向の成分
	 * @param  e2p		(( O )) v2方向の成分
	 * @param  e2p		(( O )) v3方向の成分
	 * @return			分解できたか？ 分解できず:false,  分解:true
	 */
/*
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvVecCalc v3, lvDouble e1p, lvDouble e2p, lvDouble e3p ) throws lvThrowable
	{
		return	Divide( v1, v2, v3, e1p, e2p, e3p, lvEps.l1, lvEps.a0 );
	}
*/
	
	/**
	 * 自身の点が平面上にあるかどうかを調べる（非推奨）
	 * @param  p1		(( I )) 平面上の1点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n1		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			線分上にない:false,  線分上:true
	 */
	public final boolean
	OnPlane( lvVecCalc p1, lvVecCalc n1, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( n1.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.OnPlane(0)" );
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.OnPlane(1)" );

		return	( Math.abs( ( Sub( p1 ) ).Dot( n1 ) ) <= eps ) ? true : false;
					// ( Sub( p1 ) ).Dot( n1 ) ... ( this - p1 ) * n1
	}
	/**
	 * 自身の点が平面上にあるかどうかを調べる（推奨）
	 * @param  p1		(( I )) 平面上の1点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  n1		(( I )) 平面の法線ベクトル（単位ベクトル）。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			線分上にない:false,  線分上:true
	 */
/*
	public final boolean
	OnPlane( lvVecCalc p1, lvVecCalc n1 ) throws lvThrowable
	{
		return	OnPlane( p1, n1, lvEps.l1 );
	}
*/

	/**
	 * 自身の点を含めた3点が同一直線上にあるかどうかを調べる（非推奨）
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			同一直線上にない:false,  同一直線上:true
	 */
	public final boolean
	IsLine( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsLine(0)" );

		lvDouble  a0 = Gbl().tdIsLine[0];		// a0 = new lvDouble();
		lvVector  p0 = Gbl().tvIsLine[0];		// p0 = new lvVector();
		p0.Assign( this );
		p0.NormalAssign( p1, p2, a0, eps );
		return	( a0.val == 0.0 ) ? true : false;
	}
	/**
	 * 自身の点を含めた3点が同一直線上にあるかどうかを調べる（推奨）
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			同一直線上にない:false,  同一直線上:true
	 */
	public final boolean
	IsLine( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	IsLine( p1, p2, lvEps.l1 );
	}
	
	/**
	 * 自身の点を含めた4点が同一平面上にあるかどうかを調べる（非推奨）
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p3		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  eps		(( I )) 許容誤差
	 * @return			同一平面上にない:false,  同一平面上:true
	 */
	public final boolean
	IsPlane( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsPlane(0)" );

		lvDouble  a0 = Gbl().tdIsPlane[0];			// a0 = new lvDouble();
		lvVector  n0 = Gbl().tvIsPlane[0];			// n0 = new lvVector();
		n0.Assign( this );
		n0.NormalAssign( p1, p2, p3, a0, eps );
		if( a0.val == 0.0 )
			return	true;

		lvVector  c0 = Gbl().tvIsPlane[1];			// c0 = new lvVector();
		c0.Assign( ( Add( p1 ).Add( p2 ).Add( p3 ) ).Mul( 0.25 ) );
							// c0 = ( this + p1 + p2 + p3 ) * 0.25
		if( !OnPlane( c0, n0, eps ) || !p1.OnPlane( c0, n0, eps )
			|| !p2.OnPlane( c0, n0, eps ) || !p3.OnPlane( c0, n0, eps ) )
		{
			return	false;
		}
		return	true;
	}
	/**
	 * 自身の点を含めた4点が同一平面上にあるかどうかを調べる（推奨）
	 * @param  p1		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p2		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @param  p3		(( I )) 点。必ず lvVector型変数、または数値関数自身を入れる（ lvVecCalc型は使用しないこと ）
	 * @return			同一平面上にない:false,  同一平面上:true
	 */
	public final boolean
	IsPlane( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3 ) throws lvThrowable
	{
		return	IsPlane( p1, p2, p3, lvEps.l1 );
	}

}
