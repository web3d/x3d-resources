//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvRend.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvView extends xvRoot {

	//----------------------------------------
	//		視点
	//----------------------------------------
	/**
	 *	視点方向変換軸回転角
	 */
	public lvVector   viewDir;
	/*
	 *	視点注目点
	 */
	public lvVector   viewLookPt;
	/**
	 *	視点距離
	 */
	public double	  viewDistant;
	
	// ワーク
	public x3pMatrix  viewMat;		    // 視点変換
	public x3pMatrix  viewRotateMat;	// 視点回転変換のみ

	//----------------------------------------
	//		光源
	//----------------------------------------
	/**
	 *	光源方向(どの方向に光源があるか？）
	 */
	public  lvVector  lightDir;


// ---------------------------------------------------------------

	public xvView( xvGlobal dt )
	{
		super( dt );
	}

	private x3pGlobal
	Processor()
	{
		return global.processor;
	}
	private xvRend
	Rend()
	{
		return global.rend;
	}

// ---------------------------------------------------------------

	public void
	Set( xvView src )
	{
		viewDir		= new lvVector( Processor(), src.viewDir );	
		viewLookPt	= new lvVector( Processor(), src.viewLookPt );	
		viewDistant	= src.viewDistant;

		viewMat = new x3pMatrix( Processor() );
		viewMat.Set( src.viewMat );
		
		viewRotateMat = new x3pMatrix( Processor() );
		viewRotateMat.Set( src.viewRotateMat );
		
		lightDir = new lvVector( Processor(), src.lightDir );	
	}

	/**
	 *	視点回転マトリクス取得
	 */
	public final x3pMatrix
	ViewGetRevRotateMatrix()
	{
		x3pMatrix  m = new x3pMatrix( Processor() );
		m.SetRotateYXZ( -viewDir.x, -viewDir.y, -viewDir.z );
		return m;
	}

	/**
	 *	視点変換マトリクス取得
	 */
	public final x3pMatrix
	ViewGetMatrix()
	{
		return viewMat;
	}


	//---------------------------------------------------------
	//	視点関連
	//---------------------------------------------------------
	/**
	 *	視点変換マトリクスを計算する
	 */
	public final void
	ViewCalcMat()
	{
		// 視線方向への変換角度
		lvVector  viewDir0 = viewRotateMat.MatResoluteRot();
		viewDir = new lvVector( Processor(), viewDir0.x, viewDir0.y, viewDir0.z );

		// 視点位置を自動計算
		x3pMatrix  m = new x3pMatrix( Processor() );
		m.SetRotateYXZ( -viewDir.x, -viewDir.y, -viewDir.z );
		lvVector  viewPoint = new lvVector( Processor(), 0, 0, viewDistant );	// viewPoint = 視点座標
		xvUtil.Mul( viewPoint, m, viewPoint );				        			// 視点位置
		viewPoint.AddAssign( viewLookPt );

		lightDir.SetXYZ( 0.0, 0.0, 1.0 );
		try {
			lightDir.UnitAssign();
		}
		catch( lvThrowable e ) {
			int  dmy;	dmy = 0;
		}
		xvUtil.Mul( lightDir, m, lightDir );

		//
		// ViewMat作成
		//

		// 視点座標系に移動
		lvVector  v = new lvVector( Processor(), viewPoint.x, viewPoint.y, viewPoint.z );
		v.MulAssign( -1.0 );
		viewMat.SetTranslate( v );

		// 軸あわせ
		viewMat.Mul( viewRotateMat );

		// 座標系変換
		x3pMatrix  mr = new x3pMatrix( Processor() );
		mr.SetScale( new lvVector( Processor(), 1.0, 1.0, -1.0 ) );
		viewMat.Mul( mr );
	}

	/**
	 *	視点回転
	 *		現在の視点を方向に加算
	 *		自動更新角度が残る
	 */
	public final void
	ViewRotateDir( double x, double y, double z )
	{
		// 視点回転を加算
		x3pMatrix  mv = new x3pMatrix( Processor() );
		mv.SetRotateZXY( x, y, z );
		viewRotateMat = viewRotateMat.Mul( mv );

		ViewCalcMat();
	}

	/**
	 *	視点移動
	 */
	public final void
	ViewMove( double x, double y, double z )
	{
		lvVector  vt = new lvVector( Processor(), x, y, z );

		x3pMatrix  m = new x3pMatrix( Processor() );
		m.SetRotateYXZ( -viewDir.x, -viewDir.y, -viewDir.z );
		xvUtil.Mul( vt, m, vt );
		
		viewLookPt.AddAssign( vt );
		ViewCalcMat();
	}

	/**
	 *	視点ズーム
	 */
	public final void
	ViewZoom( double x, double y, double z )
	{
		viewDistant += x;
		ViewRotateDir( 0.0, 0.0, 0.0 );
	}

	/**
	 *	視点フィット
	 */
	public final void
	ViewFit( lvVector boundMin, lvVector boundMax )
	{
		lvVector  p0 = new lvVector( Processor(), boundMin );
		lvVector  p1 = new lvVector( Processor(), boundMax );
		
		lvVector  v = new lvVector( Processor(), p0 );
		v.AddAssign( p1 );
		try {
			v.DivAssign( 2.0 );
		}
		catch( lvThrowable e ) {
			int  dmy;	dmy = 0;
		}
		
		viewLookPt.Assign( v );	
		viewRotateMat.SetUnit();		
		viewDistant = 0.0;

		ViewCalcMat();

		v.Assign( p1 );
		xvUtil.Mul( v, viewMat, v );
	
		double z0 = ( clipFlont * v.x ) / ( field * 0.30 );
		double z1 = ( clipFlont * v.y ) / ( field * 0.30 );

		if( z0 < 0 )
		    z0 *= -1.0;

		if( z0 < z1 )
			viewDistant = z1;
		else
			viewDistant = z0;
	
		ViewCalcMat();
		
		xvUtil.Mul( p0, viewMat, p0 );	// ローカル変換
		TransformPersepectiv( p0 );	    // 透視変換

		xvUtil.Mul( p1, viewMat, p1 );	// ローカル変換
		TransformPersepectiv( p1 );	    // 透視変換
	}


	/*=========================================================================
	/		透視変換
	/-------------------------------------------------------------------------*/

	// 透視変換
    private double  clipFlont = 1.0;				// フロントクリップＺ
	private double  clipBack  = 5000.0;			    // バッククリップＺ	
	private double  field	  = 0.5;				// フィールド幅

	/**
	 *	透視変換
	 */
	public final lvVector
	TransformPersepectiv( lvVector v )
	{
		double  pers;
		double  ax, ay, az;

		pers = clipFlont / ( field * v.z );
		ax	 =  Rend().screenScl.x * pers * v.x + Rend().screenPoint.x;
		ay	 = -Rend().screenScl.x * pers * v.y + Rend().screenPoint.y;
		az	 = ( clipBack * ( v.z - clipFlont ) ) / ( v.z * ( clipBack - clipFlont ) );
		
		v.x = ax;
		v.y = ay;
		v.z = az;
		
		return v;
	}

}