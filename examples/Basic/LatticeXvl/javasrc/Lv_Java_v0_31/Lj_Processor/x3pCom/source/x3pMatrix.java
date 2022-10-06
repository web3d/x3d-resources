//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pMatrix.java
//

package jp.co.lattice.vProcessor.com;

import  java.lang.Math;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * 4 x 4 マトリクス型の数値演算用クラス
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pMatrix extends x3pRoot {

// ----------------------------------------------------------------------
//	メンバ
// ----------------------------------------------------------------------
	
	/**
	 *	マトリクスのデータ。4 x 4 の配列である		<br>
	 *												<pre>
	 *	i＼j	0		1		2		3
	 *	-------------------------------------
	 *	0	  [0][0]  [0][1]  [0][2]  [0][3]
	 *	1	  [1][0]  [1][1]  [1][2]  [1][3]
	 *	2	  [2][0]  [2][1]  [2][2]  [2][3]
	 *	3	  [3][0]  [3][1]  [3][2]  [3][3]
	 *												</pre>
	 */
	public double  m[][];

// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( x3pGlobal dt )
		{
			GlobalTmp( dt );
		}

		/** ローカル変数用の new 代用バッファエリア		*/
		private x3pMatrix  tmSetRotateZXY[]   = null;
		private x3pMatrix  tmSetRotateYXZ[]   = null;
		private lvVector   tvSetRotateV[]     = null;
		private x3pMatrix  tmMul0[] 		  = null;
		private lvVector   tvMul1[] 		  = null;
		private lvVector   tvMATresoluteRot[] = null;
		private x3pMatrix  tmMATresoluteRot[] = null;
		private x3pMatrix  tmInvert33[]       = null;
		private x3pMatrix  tmTranspose[]      = null;

		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( x3pGlobal dt )
		{
			tmSetRotateZXY   = new x3pMatrix[ 4 ];	for( int i=0; i<4; i++ )	tmSetRotateZXY[ i ]   = new x3pMatrix( dt );
			tmSetRotateYXZ   = new x3pMatrix[ 4 ];	for( int i=0; i<4; i++ )	tmSetRotateYXZ[ i ]   = new x3pMatrix( dt );
			tvSetRotateV     = new lvVector[ 8 ];	for( int i=0; i<8; i++ )	tvSetRotateV[ i ]     = new lvVector( dt );
			tmMul0           = new x3pMatrix[ 2 ];	for( int i=0; i<2; i++ )	tmMul0[ i ]           = new x3pMatrix( dt );
			tvMul1           = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvMul1[ i ]           = new lvVector( dt );
			tvMATresoluteRot = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvMATresoluteRot[ i ] = new lvVector( dt );
			tmMATresoluteRot = new x3pMatrix[ 4 ];	for( int i=0; i<4; i++ )	tmMATresoluteRot[ i ] = new x3pMatrix( dt );
			tmInvert33       = new x3pMatrix[ 2 ];	for( int i=0; i<2; i++ )	tmInvert33[ i ]       = new x3pMatrix( dt );
			tmTranspose      = new x3pMatrix[ 2 ];	for( int i=0; i<2; i++ )	tmTranspose[ i ]      = new x3pMatrix( dt );
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( x3pComGblElm )global.GComX3p() ).gMatrix;
	}

// ----------------------------------------------------------------------
//	コンストラクタ
// ----------------------------------------------------------------------
	public x3pMatrix( x3pGlobal dt )
	{
		super( dt );

		m = new double[4][4];
		SetUnit();
	}

	public x3pMatrix( x3pGlobal dt, x3pMatrix cm )
	{
		super( dt );

		m = new double[4][4];

		int	 x, y;
		for( y=0; y<4; y++ ) {
			for( x=0; x<4; x++ )
				m[y][x] = cm.m[y][x];
		}
	}

// ----------------------------------------------------------------------
//	メソッド
// ----------------------------------------------------------------------

    /**
     *	代入
     */
	public final void
	Set( x3pMatrix src )
	{
		for( int v=0; v<4; v++ ) {
			for( int u=0; u<4; u++ )
				m[ v ][ u ] = src.m[ v ][ u ];
		}
	}
	
    /**
     *	平行移動
     */
    public final x3pMatrix
    SetTranslate( lvVector v )
    {
    	m[0][0] = 1.0;
    	m[0][1] = 0.0;
    	m[0][2] = 0.0;
    	m[0][3] = 0.0;

    	m[1][0] = 0.0;
    	m[1][1] = 1.0;
    	m[1][2] = 0.0;
    	m[1][3] = 0.0;

    	m[2][0] = 0.0;
    	m[2][1] = 0.0;
    	m[2][2] = 1.0;
    	m[2][3] = 0.0;

    	m[3][0] = v.x;
    	m[3][1] = v.y;
    	m[3][2] = v.z;
    	m[3][3] = 1.0;
	
    	return this;
    }

    public final x3pMatrix
    SetScale( lvVector v )
    {
    	m[0][0] = v.x;
    	m[0][1] = 0.0;
    	m[0][2] = 0.0;
    	m[0][3] = 0.0;

    	m[1][0] = 0.0;
    	m[1][1] = v.y;
    	m[1][2] = 0.0;
    	m[1][3] = 0.0;

    	m[2][0] = 0.0;
    	m[2][1] = 0.0;
    	m[2][2] = v.z;
    	m[2][3] = 0.0;

    	m[3][0] = 0.0;
    	m[3][1] = 0.0;
    	m[3][2] = 0.0;
    	m[3][3] = 1.0;
	
    	return this;
    }

    public final x3pMatrix
    SetScale( double scale )
    {
    	m[0][0] = scale;
    	m[0][1] = 0.0;
    	m[0][2] = 0.0;
    	m[0][3] = 0.0;

    	m[1][0] = 0.0;
    	m[1][1] = scale;
    	m[1][2] = 0.0;
    	m[1][3] = 0.0;

    	m[2][0] = 0.0;
    	m[2][1] = 0.0;
    	m[2][2] = scale;
    	m[2][3] = 0.0;

    	m[3][0] = 0.0;
    	m[3][1] = 0.0;
    	m[3][2] = 0.0;
    	m[3][3] = 1.0;
	
    	return this;
    }

    public final x3pMatrix
    SetRotateX( double ang )
    {
    	double  s, c;
    	s = Math.sin( ang );
    	c = Math.cos( ang );

    	m[0][0] =  1.0;
    	m[0][1] =  0.0;
    	m[0][2] =  0.0;
    	m[0][3] =  0.0;

    	m[1][0] =  0.0;
    	m[1][1] =  c;
    	m[1][2] =  s;
    	m[1][3] =  0.0;

    	m[2][0] =  0.0;
    	m[2][1] = -s;
    	m[2][2] =  c;
    	m[2][3] =  0.0;

    	m[3][0] =  0.0;
    	m[3][1] =  0.0;
    	m[3][2] =  0.0;
    	m[3][3] =  1.0;

    	return this;
    }

    public final x3pMatrix
    SetRotateY( double ang )
    {
    	double  s, c;
    	s = Math.sin( ang );
    	c = Math.cos( ang );

    	m[0][0] =  c;
    	m[0][1] =  0.0;
    	m[0][2] = -s;
    	m[0][3] =  0.0;

    	m[1][0] =  0.0;
    	m[1][1] =  1.0;
    	m[1][2] =  0.0;
    	m[1][3] =  0.0;

    	m[2][0] =  s;
    	m[2][1] =  0.0;
    	m[2][2] =  c;
    	m[2][3] =  0.0;

    	m[3][0] =  0.0;
    	m[3][1] =  0.0;
    	m[3][2] =  0.0;
    	m[3][3] =  1.0;

    	return this;
    }

    public final x3pMatrix
    SetRotateZ( double ang )
    {
    	double  s, c;
    	s = Math.sin( ang );
    	c = Math.cos( ang );

    	m[0][0] =  c;
    	m[0][1] =  s;
    	m[0][2] =  0.0;
    	m[0][3] =  0.0;

    	m[1][0] = -s;
    	m[1][1] =  c;
    	m[1][2] =  0.0;
    	m[1][3] =  0.0;

    	m[2][0] =  0.0;
    	m[2][1] =  0.0;
    	m[2][2] =  1.0;
    	m[2][3] =  0.0;

    	m[3][0] =  0.0;
    	m[3][1] =  0.0;
    	m[3][2] =  0.0;
    	m[3][3] =  1.0;
	
    	return this;
    }

    public final x3pMatrix
    SetRotateZXY( double x, double y, double z )
    {
 		x3pMatrix  m0 = Gbl().tmSetRotateZXY[0];				// m0 = new x3pMatrix();
    	x3pMatrix  m1 = Gbl().tmSetRotateZXY[1];				// m1 = new x3pMatrix();

    	m0.SetRotateZ( z );
    	m1.SetRotateX( x );
    	m0.Mul( m1 );

    	m1.SetRotateY( y );
    	Set( m0.Mul( m1 ) );
	
    	return this;
    }

    public final x3pMatrix
    SetRotateYXZ( double x, double y, double z )
    {
  		x3pMatrix  m0 = Gbl().tmSetRotateYXZ[0];				// m0 = new x3pMatrix();
    	x3pMatrix  m1 = Gbl().tmSetRotateYXZ[1];				// m1 = new x3pMatrix();

    	m0.SetRotateY( y );
    	m1.SetRotateX( x );
    	m0.Mul( m1 );

    	m1.SetRotateZ( z );
    	Set( m0.Mul( m1 ) );
	
    	return this;
    }

    /**
     *	軸周りの回転
     */
	public final x3pMatrix
	SetRotateV( lvVector vn, double ang )
	{
		lvVector  o0 = Gbl().tvSetRotateV[0];	o0.SetXYZ( 0.0, 0.0, 0.0 );		// o0 = new lvVector( 0.0, 0.0, 0.0 );	
		lvVector  x0 = Gbl().tvSetRotateV[1];	x0.SetXYZ( 1.0, 0.0, 0.0 );		// x0 = new lvVector( 1.0, 0.0, 0.0 );	
		lvVector  y0 = Gbl().tvSetRotateV[2];	y0.SetXYZ( 0.0, 1.0, 0.0 );		// y0 = new lvVector( 0.0, 1.0, 0.0 );	
		lvVector  z0 = Gbl().tvSetRotateV[3];	z0.SetXYZ( 0.0, 0.0, 1.0 );		// z0 = new lvVector( 0.0, 0.0, 1.0 );
		
		x0.Rotate( vn, ang );
		y0.Rotate( vn, ang );
		z0.Rotate( vn, ang );

		SetVector( o0, x0, y0, z0 ); 

		return this;
	}

	public final x3pMatrix
	SetVector( lvVector p0, lvVector x0, lvVector y0, lvVector z0 )
	{
		m[0][0] = x0.x;  m[0][1] = x0.y;	m[0][2] = x0.z;  m[0][3] = 0.0;
		m[1][0] = y0.x;  m[1][1] = y0.y;	m[1][2] = y0.z;  m[1][3] = 0.0;
		m[2][0] = z0.x;  m[2][1] = z0.y;	m[2][2] = z0.z;  m[2][3] = 0.0;
		m[3][0] = p0.x;  m[3][1] = p0.y;	m[3][2] = p0.z;  m[3][3] = 1.0;
	
		return this;
	}

    public final void
    SetUnit()
    {
    	m[0][0] = 1.0;
    	m[0][1] = 0.0;
    	m[0][2] = 0.0;
    	m[0][3] = 0.0;

    	m[1][0] = 0.0;
    	m[1][1] = 1.0;
    	m[1][2] = 0.0;
    	m[1][3] = 0.0;

    	m[2][0] = 0.0;
    	m[2][1] = 0.0;
    	m[2][2] = 1.0;
    	m[2][3] = 0.0;

    	m[3][0] = 0.0;
    	m[3][1] = 0.0;
    	m[3][2] = 0.0;
    	m[3][3] = 1.0;
    }

    /**
     *	乗算
     */
//	static public final x3pMatrix
//	Mul( x3pMatrix mat1, x3pMatrix mat2 )
//	{
//		x3pMatrix  mt = new x3pMatrix();
//
//		int  x, y;
//		for( y=0; y<4; y++ ) {
//			for( x=0; x<4; x++ ) {
//				mt.m[y][x]  = mat1.m[y][0] * mat2.m[0][x];
//				mt.m[y][x] += mat1.m[y][1] * mat2.m[1][x];
//				mt.m[y][x] += mat1.m[y][2] * mat2.m[2][x];
//				mt.m[y][x] += mat1.m[y][3] * mat2.m[3][x];
//			}
//		}
//
//		return mt;
//	}

    /**
     *	乗算
     *		ret = this * mat2;
     */
    public final x3pMatrix
    Mul( x3pMatrix mat2 )
    {
  		x3pMatrix  mt = Gbl().tmMul0[0];				// mt = new x3pMatrix();
 
    	int  x, y;
    	for( y=0; y<4; y++ ) {
    		for( x=0; x<4; x++ ) {
	    		mt.m[y][x]  = m[y][0] * mat2.m[0][x];
    			mt.m[y][x] += m[y][1] * mat2.m[1][x];
    			mt.m[y][x] += m[y][2] * mat2.m[2][x];
    			mt.m[y][x] += m[y][3] * mat2.m[3][x];
    		}
    	}

    	Set( mt );
    	return this;
    }

    /**
     * ret = v * m
	 * @return			必ず数値関数か Assign系関数で包むこと
     */
	public final lvVecCalc
	Mul( lvVector vt )
	{
		lvVector  va = Gbl().tvMul1[0];					// va = new lvVector();

		va.x  = vt.x * m[0][0];
		va.x += vt.y * m[1][0];
		va.x += vt.z * m[2][0];
		va.x += m[3][0];				// * 1.0

		va.y  = vt.x * m[0][1];
		va.y += vt.y * m[1][1];
		va.y += vt.z * m[2][1];
		va.y += m[3][1];				// * 1.0

		va.z  = vt.x * m[0][2];
		va.z += vt.y * m[1][2];
		va.z += vt.z * m[2][2];
		va.z += m[3][2];	
   
		return va;
	}

    /*
    	ret = v * m
    */
//	public final lvVector
//	Mul( double x, double y, double z )
//	{
//		lvVector  va = new lvVector();
//
//		va.x  = x * m[0][0];
//		va.x += y * m[1][0];
//		va.x += z * m[2][0];
//		va.x += m[3][0];				// * 1.0
//
//		va.y  = x * m[0][1];
//		va.y += y * m[1][1];
//		va.y += z * m[2][1];
//		va.y += m[3][1];				// * 1.0
//
//		va.z  = x * m[0][2];
//		va.z += y * m[1][2];
//		va.z += z * m[2][2];
//		va.z += m[3][2];	
//
//		return va;
//	}

    /*-------------------------------------------------------------------
    /		現在のローテーションマトリクスを
    /--------------------------------------------------------------------*/
    public final lvVector
    MatResoluteRot()
    {
    	x3pMatrix  m  = this;
	
    	lvVector   vz = Gbl().tvMATresoluteRot[0];	vz.SetXYZ( 0.0, 0.0, 1.0 );		// vz = new lvVector( 0.0, 0.0, 1.0 );
    	lvVector   vy = Gbl().tvMATresoluteRot[1];	vy.SetXYZ( 0.0, 1.0, 0.0 );		// vy = new lvVector( 0.0, 1.0, 0.0 );
    	lvVector   vx = Gbl().tvMATresoluteRot[2];	vx.SetXYZ( 1.0, 0.0, 0.0 );		// vx = new lvVector( 1.0, 0.0, 0.0 );
   		x3pMatrix  mx = Gbl().tmMATresoluteRot[0];									// mx = new x3pMatrix();
   		x3pMatrix  my = Gbl().tmMATresoluteRot[1];									// my = new x3pMatrix();

    	lvVector   a  = new lvVector( global );

    	// 各軸のベクトルをマトリクスで回転させる
    	vz.Assign( m.Mul( vz ) );			// vz = m * vz;
    	vx.Assign( m.Mul( vx ) );			// vx = m * vx;
    	vy.Assign( m.Mul( vy ) );			// vy = m * vy;

       // Ｙ軸まわり成分を計算
    	if( -0.001 < vz.x && vz.x < 0.001 )
    	    vz.x = 0.0;
    	if( -0.001 < vz.z && vz.z < 0.001 )
    	    vz.z = 0.0;

    	// Z軸を利用
    	if( vz.z != 0 )
    		a.y = Math.atan( vz.x / vz.z );
    	else if( vz.z == 0.0 && vz.x != 0.0 )
    		a.y = lvConst.LV_PI / 2.0;
    	else
	    	a.y = 0.0;
    	if( vz.z < 0.0 )
	        a.y += lvConst.LV_PI;
    	if( a.y < 0.0)
    	    a.y += lvConst.LV_PI * 2.0;

    	// Ｙ軸まわり成分を消す	
    	my.SetRotateY( -a.y );
    	vx.Assign( my.Mul( vx ) );			// vx = my * vx;
    	vz.Assign( my.Mul( vz ) );			// vz = my * vz;
    	vy.Assign( my.Mul( vy ) );			// vy = my * vy;

        // Ｘ軸まわりの成分を計算
    	if( -0.001 < vz.y && vz.y < 0.001 )
    	    vz.y = 0.0;
    	if( -0.001 < vz.z && vz.z < 0.001 )
    	    vz.z = 0.0;

    	// Z軸をベースに
    	if(vz.y != 0)
    		a.x = Math.atan( vz.z / vz.y ) - lvConst.LV_PI / 2;
    	else if( vz.y == 0.0 && vz.z != 0.0 )
    		a.x = 0.0;
    	else
    		a.x = -lvConst.LV_PI / 2;
    	if( vz.y < 0.0 )
    	    a.x += lvConst.LV_PI;
    	if( a.x < 0.0 )
    	    a.x += lvConst.LV_PI * 2.0 ;
	
    	// Ｘ軸まわり成分を消す	
    	mx.SetRotateX( -a.x );
    	vx.Assign( mx.Mul( vx ) );			// vx = mx * vx;
    	vy.Assign( mx.Mul( vy ) );			// vy = mx * vy;
	    vz.Assign( mx.Mul( vz ) );			// vz = mx * vz;

        // Ｚ軸まわりの成分を計算
    	if( -0.001 < vx.x && vx.x < 0.001 )
          vx.x = 0.0;
    	if( -0.001 < vx.y && vx.y < 0.001 )
    	    vx.y = 0.0;

    	// X軸をベース
    	if( vx.x != 0 )		
    		a.z = Math.atan( vx.y / vx.x );
    	else if( vx.x == 0.0 && vx.y != 0.0 ) {
	    	if( vx.y >= 0.0 )
    			a.z =  lvConst.LV_PI / 2;
    		else
	    		a.z = -lvConst.LV_PI / 2;
    	}
    	else
    		a.z = 0.0;
    	if( vx.x < 0.0 )
    	    a.z += lvConst.LV_PI;
    	if( a.z < 0.0 )
	        a.z += lvConst.LV_PI * 2.0;

    	return a;
    }

	/**
	 * 自身を逆行列にする( 4 * 4 )
	 */
/*
	public final boolean
	Invert44()
	{
		double  eps = lvEps.a0;		// 許容誤差
		//
		// Apply Gauss-Jordan method.
		//
		int		   i, j, k, p;
		double	   f;
		x3pMatrix  e = Gbl().tmInvert44[0];				// e = new x3pMatrix();

		for( k=0; k<4; k++ ) {
			//
			// Apply partial pivoting method.
			//
			p = k;
			f = m[k][k];
			for( i=k+1; i<4; i++ ) {
				if (Math.abs( m[i][k] ) > Math.abs( f ) ) {
					p = i;
					f = m[i][k];
				}
			}
			if( p != k ) {
				for( i=0; i<4; i++ ) {
					f = m[k][i];
					m[k][i] = m[p][i];
					m[p][i] = f;
					f = e.m[k][i];
					e.m[k][i] = e.m[p][i];
					e.m[p][i] = f;
				}
			}

			//
			// Apply sweeping method.
			//
			f = m[k][k];
			if( Eps().IsZero( f, eps ) )
				Err().Assert( false, "x3pMatrix.invert33(0)" );		// throw ***;
			for( i=0; i<4; i++ ) {
				m[k][i]   /= f;
				e.m[k][i] /= f;
			}
			for( i=0; i<4; i++ ) {
				if( i == k )
					continue;
				f = m[i][k];
				for( j=0; j<4; j++ ) {
					m[i][j]   -= f * m[k][j];
					e.m[i][j] -= f * e.m[k][j];
				}
			}
		}

		set( e );
	}
*/
	
	/**
	 * 自身を逆行列にする( 3 * 3 )
	 */
	public final void
	Invert33() throws lvThrowable
	{
		double  eps = lvEps.a0;		// 許容誤差
		//
		// Apply Gauss-Jordan method.
		//
		int		   i, j, k, p;
		double	   f;
		x3pMatrix  e = Gbl().tmInvert33[0];				// e = new x3pMatrix();

		for( k=0; k<3; k++ ) {
			//
			// Apply partial pivoting method.
			//
			p = k;
			f = m[k][k];
			for( i=k+1; i<3; i++ ) {
				if (Math.abs( m[i][k] ) > Math.abs( f ) ) {
					p = i;
					f = m[i][k];
				}
			}
			if( p != k ) {
				for( i=0; i<3; i++ ) {
					f = m[k][i];
					m[k][i] = m[p][i];
					m[p][i] = f;
					f = e.m[k][i];
					e.m[k][i] = e.m[p][i];
					e.m[p][i] = f;
				}
			}

			//
			// Apply sweeping method.
			//
			f = m[k][k];
			if( Eps().IsZero( f, eps ) )
				Err().Assert( false, "x3pMatrix.invert33(0)" );		// throw ***;
			for( i=0; i<3; i++ ) {
				m[k][i]   /= f;
				e.m[k][i] /= f;
			}
			for( i=0; i<3; i++ ) {
				if( i == k )
					continue;
				f = m[i][k];
				for( j=0; j<3; j++ ) {
					m[i][j]   -= f * m[k][j];
					e.m[i][j] -= f * e.m[k][j];
				}
			}
		}

		Set( e );
	}
	
	/**
	 * 自身を転置行列にする
	 */
	public final void
	Transpose()
	{
		x3pMatrix  src = Gbl().tmTranspose[0];	src.Set( this );	// src = new x3pMatrix( this );
		
		for( int v=0; v<4; v++ ) {
			for( int u=0; u<4; u++ )
				m[ u ][ v ] = src.m[ v ][ u ];
		}
	}
	
}
