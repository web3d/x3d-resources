//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvRend.java
//

package jp.co.lattice.vApplet;

import	java.applet.*;
import	java.awt.*;
import	java.awt.image.*;
import  java.net.*;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;

import	jp.co.lattice.vKernel.core.c0.*;


/*
	グーローシェーディング制御
		シェーディング角（デフォルト６０度）

	光源計算
		光源座標
		光源輝度
		絶対制御（フェードアウトなどのため）
*/


/**
 * レンダリングエンジン（環境）
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvRend extends xvRoot {

	//----------------------------------------
	//		定数定義
	//----------------------------------------
	/**
	 *	固定小数定数
	 */
	public static final int  FP = 0x10000;

	/**
	 *	α最大値
	 */
    private static final int  ALPHA =0xff000000;

	//----------------------------------------
	//		環境
	//----------------------------------------
	public	Applet  applet;
	
	// 出力サイズ
	public	int  width;
	public	int  height;

	// 描画バッファ
	public	Image		image;						// イメージ
	public	int[]		mem;			    		// メモリー
	public	int[]		zbuf;						// Zバッファ

	private int			bgColor		= 0xff000000;
	private int[]		bgTexMem	= null;			//メモリーイメージ
	
	public	x3pMatrix   autoRotMat	= null;
	
	// 描画デバイス
	public	xvShadeCore      shade	= null;
	
	// テクスチャUV値演算デバイス
	public	xvShadeTexCoord  texUV	= null;


	//----------------------------------------
	//		視点
	//----------------------------------------
	/**
	 *	視点
	 */
	public xvView  view		= null;

	// スクリーン変換
	public lvVector  screenScl   = null;	    // スクリーン拡大率
	public lvVector  screenPoint = null;	    // スクリーン位置補正


	//----------------------------------------
	//		光源
	//----------------------------------------
	/**
	 *	光源色
	 */
	private x3pColor  lightColor = null;
	/**
	 *	環境光強度
	 */
	private int		  lightAmbientIntensity = ( int )( 0.50 * 0x100 );


	//----------------------------------------
	//		素材
	//----------------------------------------
	/**
	 *	レンダリングステータス（Ambient)
	 */
	public int		   ambientIntensity = ( int )( 0.2 * 0x100 );			// 環境光係数
	public x3pColor	   diffuseColor	    = null;								// 拡散光係数
//	public x3pColor	   emissiveColor	= null;								// 放射光成分
	public double	   shininess		= 0.2;								// 鏡面光の指数
//	public x3pColor	   specularColor	= null;								// 鏡面光係数
//	public double	   transparency	    = 0.0;								// 透明度

	public x3pFromProcessor.Texture  texture	= null;						// カレントテクスチャ情報１
	public xvTexture                 texImage	= null;						// カレントテクスチャ情報２


//-------------------------------------------------------------------------

	public final void
	SetAmbientIntensity( double val )
	{
		ambientIntensity = ( int )( val * 0x100 );
	}


	//=========================================================
	//	メソッド
	//---------------------------------------------------------

	/**
	 *	コンストラクタ
	 *	@param	w	表示幅
	 *	@param	h	表示高
	 *	@param	ap	親アプレット
	 */
	public xvRend( xvGlobal dt, int w, int h, Applet ap )
	{
		super( dt );
		Constractor( dt );
		
		applet = ap;
		SetSize( w, h, ap );				    // 設定

		// 光源設定
		view.lightDir = new lvVector( dt.processor, 1.0, 0.0, -1.0 );
		try {
			view.lightDir.UnitAssign();
		}
		catch( lvThrowable e ) {
			int  dmy;	dmy = 0;
		}

		// 視点設定
		view.viewDir	   = new lvVector( dt.processor, 0.0, 0.0, 0.0 );	
		view.viewLookPt	   = new lvVector( dt.processor, 0.0, 0.0, 0.0 );	
		view.viewDistant   = 12.0;

		view.viewMat	   = new x3pMatrix( dt.processor );
		view.viewRotateMat = new x3pMatrix( dt.processor );

		view.ViewRotateDir( 0.0, 0.0, 0.0 );		// 計算

		shade = new xvShade( dt, this );
		texUV = new xvShadeTexCoord( dt, this );
	}

	private void
	Constractor( xvGlobal dt )
	{
		view = new xvView( dt );
		
		screenScl   = new lvVector( dt.processor, 200.0, 200.0, 0.0 );	    // スクリーン拡大率
		screenPoint = new lvVector( dt.processor, 100.0, 100.0, 0.0 );	    // スクリーン位置補正

		lightColor = new x3pColor( dt.processor, 1.0, 1.0, 1.0 );
		
		diffuseColor  = new x3pColor( dt.processor, 0.8, 0.8, 0.8 );	// 拡散光係数
//		emissiveColor = new x3pColor( dt.processor, 0.0, 0.0, 0.0 );	// 放射光成分
//		specularColor = new x3pColor( dt.processor, 0.0, 0.0, 0.0 );	// 鏡面光係数

		cw0 = new x3pColor( dt.processor );
		cw1 = new x3pColor( dt.processor );
		cw2 = new x3pColor( dt.processor );
		
		vh  = new lvVector( dt.processor );
	}
	
	private x3pGlobal
	Processor()
	{
		return global.processor;
	}

	/**
	 *	サイズ設定
	 *	@param	w	表示幅
	 *	@param	h	表示高
	 *	@param	ap	親アプレット
	 */
    private final void
    SetSize( int w, int h, Applet ap )
	{
		width  = w;
		height = h;
		mem  = new int[ w * h ];
		zbuf = new int[ w * h ];

		for( int i=0; i<( w*h ); i++ )
			mem[ i ] = 0xff000000;

		ColorModel  colorModel = ColorModel.getRGBdefault();
		MemoryImageSource  mis = new MemoryImageSource( w, h, colorModel, mem, 0, w );
		image = ap.createImage( mis );

		screenPoint.SetXYZ( w/2.0, h/2.0, 0 );
		screenScl.SetXYZ( w, h, 0 );
	}

	/**
	 *	背景色の登録
	 */
	public void
	SetBgColor( String bgColStr )
	{
		String  str = bgColStr.substring( 1 );
		bgColor = ( Integer.valueOf( str, 16 ) ).intValue() | ALPHA;
	}
	
	/**
	 *	背景イメージの登録
	 */
	public boolean
	SetBgTex( String bgTex )
	{
		Image  tmpImg;
		
		Image  bgImage = SetBgTexMain( bgTex );
		if( bgImage == null )
			return true;

		// 画面サイズに合わせる
		tmpImg = applet.createImage( width, height );
		
		Graphics  tmpGrp = tmpImg.getGraphics();
		tmpGrp.setColor( applet.getBackground() );
		tmpGrp.fillRect( 0, 0, width, height );
		tmpGrp.drawImage( bgImage, 0, 0, width, height, null );
		
		// Pixel Dataの取り出し		
		bgTexMem = new int[ width * height ];
		PixelGrabber  pg= new PixelGrabber( tmpImg, 0, 0, width, height, bgTexMem, 0, width );

		try {
			pg.grabPixels();
		}
		catch( InterruptedException e ) {
			return true;
		}
		
		return false;
	}

	private Image
	SetBgTexMain( String bgTex )
	{
		Image  bgImage = null;
		
		bgImage = SetBgTexForIE( bgTex );
		if( bgImage != null )
			return bgImage;
		
		bgImage = SetBgTexForNC( bgTex );
		if( bgImage != null )
			return bgImage;
		
		return null;
	}
	
	private Image
	SetBgTexForIE( String bgTex )
	{
		Image  bgImage = applet.getImage( applet.getDocumentBase(), bgTex );

		boolean  err = SetBgTexMediaTracker( bgImage );
		if( err == true )
			return null;
		
		int  w = bgImage.getWidth( null );
		int  h = bgImage.getHeight( null );
		if( w <= 0 || h <= 0 )
			bgImage = null;

		return bgImage;
	}
	
	private Image
	SetBgTexForNC( String bgTex )
	{
		String  bgTexEnc = URLEncoder.encode( bgTex );
		Image   bgImage  = applet.getImage( applet.getDocumentBase(), bgTexEnc );

		boolean  err = SetBgTexMediaTracker( bgImage );
		if( err == true )
			return null;
		
		int  w = bgImage.getWidth( null );
		int  h = bgImage.getHeight( null );
		if( w <= 0 || h <= 0 )
			bgImage = null;

		return bgImage;
	}
	
	private boolean
	SetBgTexMediaTracker( Image bgImage )
	{
		// 読込み終了まで待つ
		MediaTracker  tracker;
		tracker = new MediaTracker( applet );
		tracker.addImage( bgImage, 0 );
		try {
			tracker.waitForID( 0 );
		}
		catch( InterruptedException e ) {
			return true;
		}
		
		return false;
	}
	
	public void
	SetAutoRotate( int factor )
	{
		autoRotMat = new x3pMatrix( Processor() );
		autoRotMat.SetRotateY( factor * lvConst.LV_PI / 180.0 * 2 );
	}

	//---------------------------------------------------------
	//	レンダリング
	//---------------------------------------------------------

	/**
	 *	背景消去
	 */
	public final void
	Clear()
	{
		if( bgTexMem != null ) {
			for( int i=0; i<height*width; i++ ) {
				mem[ i ]  = bgTexMem[ i ] | ALPHA;
				zbuf[ i ] = 0x7fffffff;
			}
		}
		else {
			for( int i=0; i<height*width; i++ ) {
				mem[ i ]  = bgColor;
				zbuf[ i ] = 0x7fffffff;
			}
		}
	}

	/**
	 *	イメージのflush
	 *		レンダリングが終了したら、呼び出すこと
	 */
	public final void
	Flush()
	{
		image.flush();
	}

	/**
	 *	明るさ演算
	 *	@param	color	diffuse(拡散光）
	 *	poaram
	 */
	private x3pColor  cw0 = null;
	private x3pColor  cw1 = null;
	private x3pColor  cw2 = null;
	private lvVector  vh  = null;
	
	public final int
	ColorMul( int color, lvVector nvec, lvVector lvec, lvVector vvec ) throws lvThrowable
	{
		double  t = nvec.Dot( vvec );
		if( t < 0 )
		    t *= -0.0;

		// ambient成分
		cw1.Set( lightColor );
		cw1.Mul( lightAmbientIntensity );			// x100
		
		cw0.Set( color );
		cw0.Mul( ambientIntensity );				// x100

		cw0.Mul( cw1 );							    //
		try {
			cw0.Div( 0x1000000 );					// x1
		}
		catch( lvThrowable exception ) {
			Err().Assert( false, "xvRend.ColorMul(0)" );
		}
		
		// diffuse成分
		cw1.Set( color );
		cw1.Mul( t );								//
		
		cw0.Add( cw1 );
		cw0.Mul( lightColor );
		try {
			cw0.Div( 0x100 );
		}
		catch( lvThrowable exception ) {
			Err().Assert( false, "xvRend.ColorMul(1)" );
		}

		if( cw0.r > 0xff )	cw0.r = 0xff;
		if( cw0.g > 0xff )	cw0.g = 0xff;
		if( cw0.b > 0xff )	cw0.b = 0xff;
		int  ret = ( cw0.r<<16 ) | ( cw0.g<<8 ) | cw0.b;

		return ret;
	}
	
}
