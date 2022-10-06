//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvShadeTexCoord.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvShadeTexCoord extends xvShadeCore {

	public lvUVdt  tf0 = new lvUVdt();
	public lvUVdt  tf1 = new lvUVdt();
	public lvUVdt  tf2 = new lvUVdt();
	
	private boolean   enable   = false;
	private boolean   needExit = false;

	// ワーク
	private Vector3I  v0a = new Vector3I();
	private Vector3I  v1a = new Vector3I();
	private Vector3I  v2a = new Vector3I();

	private Vector3I  t0  = new Vector3I();
	private Vector3I  t1  = new Vector3I();
	private Vector3I  t2  = new Vector3I();

	private Vector3I  dt0 = new Vector3I();
	private Vector3I  dt1 = new Vector3I();
	private Vector3I  dt2 = new Vector3I();

	private CalcLine  cl  = new CalcLine();
	
	private int       dx1;


	/**
	 * コンストラクタ
	 */
	public xvShadeTexCoord( xvGlobal dt, xvRend p_rend )
	{
		super( dt, p_rend );
	}
	
	public void
	SetEnableTexture( boolean enable0 )
	{
		enable = enable0;
	}
	
	public void
	CalcInitV0to1()
	{
		needExit = false;
		if( enable == true )
			needExit = CalcInitV0to1Main();
	}
	
	public void
	CalcInitV1to2()
	{
		needExit = false;
		if( enable == true )
			needExit = CalcInitV1to2Main();
	}
	
	public void
	CalcLineInit( int y ) throws lvThrowable
	{
		if( enable == true && needExit == false )
			cl.CalcLineInit( y );
	}
	
	public boolean
	CalcPixel( int x, Vector3I tuv ) throws lvThrowable
	{
		if( enable == true && needExit == false )
			cl.CalcPixel( x, tuv );
			
		return enable;
	}
	
	public void
	CalcLineFinish()
	{
		if( enable == true && needExit == false )
			cl.CalcLineFinish();
	}
	
	/*
	 * 三角形演算
	 */
	private boolean
	CalcInitV0to1Main()
	{
		// 固定小数点変換
		v0a.Set( vf0.x, vf0.y, 0.0 );
		v1a.Set( vf1.x, vf1.y, 0.0 );
		v2a.Set( vf2.x, vf2.y, 0.0 );

		t0.Set( tf0.u, tf0.v, 0.0 );
		t1.Set( tf1.u, tf1.v, 0.0 );
		t2.Set( tf2.u, tf2.v, 0.0 );

		// 頂点並び替え（Ｙ座標が大きい順に）
		Vector3I  tmp = new Vector3I();
		if( v0a.y > v1a.y ) {
			tmp = v0a;	v0a = v1a;	v1a = tmp;
			tmp = t0;	t0  = t1;	t1  = tmp;
		}
		if( v1a.y > v2a.y ) {
			tmp = v1a;	v1a = v2a;	v2a = tmp;
			tmp = t1;	t1  = t2;	t2  = tmp;
		}
		if( v0a.y > v1a.y ) {
			tmp = v0a;	v0a = v1a;	v1a = tmp;
			tmp = t0;	t0  = t1;	t1  = tmp;
		}

		// Δ係数計算（Δｎ／ΔＹ）
		int  dx0 = 0, dx2 = 0;
		
		dx1 = 0;
		dt0.Set( t0 );
		dt1.Set( t0 );
		dt2.Set( t0 );
		
		int  dy = ( v1a.y - v0a.y ) >> 16;
		if( dy != 0 ) {
			dx0 = ( v1a.x - v0a.x ) / dy;
			dt0.Set( t1 );
			dt0.Sub( t0 );
			dt0.Div( dy );
		}
		dy = ( v2a.y - v1a.y ) >> 16;
		if( dy != 0 ) {
			dx1 = ( v2a.x - v1a.x ) / dy;
			dt1.Set( t2 );
			dt1.Sub( t1 );
			dt1.Div( dy );
		}
		dy = ( v2a.y - v0a.y ) >> 16;
		if( dy != 0 ) {
			dx2 = ( v2a.x - v0a.x ) / dy;
			dt2.Set( t2 );
			dt2.Sub( t0 );
			dt2.Div( dy );
		}

		/*
		 * V0からV1までの水平線演算
		 *	v0->v1
		 *	v0-> M (->v2)
		 */
		// スタート座標
		cl.swap = false;
		cl.ys = v0a.y >> 16;
		cl.ye = v1a.y >> 16;
	
		cl.xs = v0a.x;
		cl.ts.Set( t0 );

		cl.xe = v0a.x;
		cl.te.Set( t0 );

		// Ｙ方向増加量
		cl.dxs = dx0;
		cl.dts = dt0;
		
		cl.dxe = dx2;
		cl.dte = dt2;

		boolean  exit = cl.CalcInit();
		if( exit == true )
			return true;
			
		return false;
	}

	private boolean
	CalcInitV1to2Main()
	{
		/*
		 * V1からV2までの水平線演算
		 *  v1 -> v2
		 *  M  -> v2
		 */
		cl.ys = v1a.y >> 16;
		cl.ye = v2a.y >> 16;
		if( !cl.swap ) {			// x0が小さい場合、交換されている場合がある
			cl.dxs = dx1;
			cl.dts = dt1;
			
			cl.xs = v1a.x;
			cl.ts.Set( t1 );
		}
		else {
			cl.dxe = dx1;
			cl.dte = dt1;
			
			cl.xe = v1a.x;
			cl.te.Set( t1 );
		}
		
		boolean  exit = cl.CalcInit();
		if( exit == true )
			return true;
			
		return false;
	}

	/**
	 * ライン演算用データ作成
	 */
	private class CalcLine {
		
		private boolean   swap = false;
		
		// 開始、終了
		private int		  ys;		// Ｙ
		private int		  ye;

		private int		  xs;		// Ｘ
		private int		  xe;

		private Vector3I  ts = new Vector3I();		// Texture 
		private Vector3I  te = new Vector3I();


		// Ｙ方向 増加率
		private int		  dxs;
		private Vector3I  dts;

		private int		  dxe;
		private Vector3I  dte;

		private Vector3I  tpos = new Vector3I();
		private Vector3I  hdt  = new Vector3I();
		
		private int       lineY;
		private int       pixelX;


		private boolean
		CalcInit()
		{
			swap = false;

			// 始点、終点とも、画面上外 または 画面下外
			if( ( ys < 0 && ye < 0 ) || ( ys >= rend.height && ye >= rend.height ) ) {
				xs += dxs * ( ye - ys );
				ts.Add( ( new Vector3I( dts ) ).Mul( ye - ys ) );
				
				xe += dxe * ( ye - ys );
				te.Add( ( new Vector3I( dte ) ).Mul( ye - ys ) );

				return true;
			}

			// 始点が画面上外
			if( ys < 0 ) {
				xs += dxs * ( -ys );
				ts.Add( ( new Vector3I( dts ) ).Mul( -ys ) );

				xe += dxe * ( -ys );
				te.Add( ( new Vector3I( dte ) ).Mul( -ys ) );
				ys = 0;
			}

			// 終点が画面下外
			int  yeOut = 0;
			if( ye >= rend.height ) {
				yeOut = ( ye - ( rend.height - 1 ) );
				ye = rend.height - 1;
			}
			
			lineY = ys;
			
			return false;
		}

		/**
		 * 水平線演算
		 */
		private void
		CalcLineInit( int y0 ) throws lvThrowable
		{
			Err().Assert( ( y0 == lineY ), "xvShadeTexture.CalcLine.CalcLineInit(0)" );
			
			// 固定小数切り捨て
			int  x0 = xs >> 16;
			int  x1 = xe >> 16;
			
			if( x0 > x1 ) {
				int 	  t;
				Vector3I  tt;
				t  = xs;	xs = xe;	xe = t;
				tt = ts;	ts = te;	te = tt;

				t  = dxs;	dxs = dxe;		dxe = t;
				tt = dts;	dts = dte;		dte = tt;
				if( swap == true )
					swap = false;
				else
					swap = true;
				
				x0 = xs >> 16;
				x1 = xe >> 16;
			}

			// 左右クリップ
			if( ( x0 < 0 && x1 < 0 ) || ( x0 >= rend.width && x1 >= rend.width ) )
				return;
			if( x0 < 0 )
			    x0 = 0;
			if( x1 >= rend.width )
			    x1 = rend.width - 1;

			// 水平増加率
			hdt.Set( 0, 0, 0 );
			if( x1 != x0 ) {
				int  dx = x1 - x0;

				hdt.Set( te );
				hdt.Sub( ts ).Div( dx );
			}

			tpos.Set( ts );

			pixelX = x0;
		}
		
		private void
		CalcPixel( int x0, Vector3I tuv ) throws lvThrowable
		{
			Err().Assert( ( x0 == pixelX ), "xvShadeTexture.CalcLine.CalcPixel(0)" );
			
			tuv.Set( tpos );
			tpos.Add( hdt );
			
			pixelX++;
		}
			
		private void
		CalcLineFinish()
		{
			xs += dxs;
			ts.Add( dts );

			xe += dxe;
			te.Add( dte );
			
			lineY++;
		}
		
	}
}
