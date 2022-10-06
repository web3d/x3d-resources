//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvMain.java
//
 
package jp.co.lattice.vApplet;

import	java.applet.*;
import	java.awt.*;
import	java.util.Date;
import	java.io.*;
import	java.util.Hashtable;
import	java.awt.event.*;
import	java.util.zip.*;
import	java.net.*;

import	jp.co.lattice.vProcessor.node.*;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;

// ラティスカーネル
import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvMain {
	
	/**
	 *	初期化を行うスレッド
	 */
	class Init extends Thread
	{
		Applet               app;
		MouseListener        mouse;
		MouseMotionListener  mMotion;
		KeyListener          key;
		
		public Init( Applet app0, MouseListener mouse0, MouseMotionListener mMotion0, KeyListener key0 )
		{
			app     = app0;
			mouse   = mouse0;
			mMotion = mMotion0;
			key     = key0;
		}
		
		public void run()
		{
			Initialize( app, mouse, mMotion, key );
		}
		
	}


	private Thread    runner;
	private Init      initThread;

	// 初期化	
	private	boolean   initialized		= false;
	private	long	  startTime			= -1;		// 開始時間
	private int		  cntForPaintInit	=  0;		// カウンタ
	private int		  cntForPaintMain	=  0;		// カウンタ
	private int       sleepTime			= 100;

	private int       xPressed;
	private int       yPressed;
	private	long	  timePressed;

	private static final int  PAINTSTAT_ORG					= 0;  
	private static final int  PAINTSTAT_PREPAINT_NOW		= 1;  
	private static final int  PAINTSTAT_PREPAINT_FINISH		= 2;  
	private static final int  PAINTSTAT_PAINT_NOW			= 3;  
	private int		          paintStat						= PAINTSTAT_ORG;

	// エラー状態
	private String    errorMessage	= null;

	// データ
	private xvDraw    view;			        		// XVL3レンダラー
	
	private lvVector  boundMin		= null;
	private lvVector  boundMax		= null;

	public  xvGlobal  mXvGlobal		= new xvGlobal();


// -------------------------------------------------------------------

	private x3pGlobal
	Processor()
	{
		return mXvGlobal.processor;
	}

	/** lvError用のグローバルデータ				*/
	private final lvError.Global
	ErrProc()
	{
		return ( ( lvComGblElm )Processor().GCom() ).gError;
	}

	private xvRend
	Rend()
	{
		return mXvGlobal.rend;
	}

// -------------------------------------------------------------------

	private final void
	Initialize( Applet app, MouseListener mouse, MouseMotionListener mMotion, KeyListener key )
	{
		boolean  err;
		
		mXvGlobal.processor = x3pWorld.Init();
		
		// XVL3読込み
		String  xvl3FileName = app.getParameter( "FileName" );
		
		URL  xvl3FileUrl = GetXvl3FileUrl( app, xvl3FileName );
		if( xvl3FileUrl == null ) {
			SetError( "XVL3 file not found." );
			return;
		}
		
		URL  xvl3Base = MakeXvl3Base( xvl3FileUrl );
		
		// レンダラー設定
		mXvGlobal.rend = new xvRend( mXvGlobal, app.getSize().width, app.getSize().height, app );
		
		int  numDiv = 0;
		String  param_resolution = app.getParameter( "DivNum" );
		if( param_resolution != null ) {
			numDiv = ( Integer.valueOf( param_resolution ) ).intValue();

			if( ( numDiv % 2 ) != 0 )
			    numDiv += 1;
			if( numDiv > 8 )	numDiv = 8;
			if( numDiv < 2 )	numDiv = 2;
		}

		String  bgColor = app.getParameter( "BgColor" );
		if( bgColor != null )
			Rend().SetBgColor( bgColor );

		String  bgTex = app.getParameter( "BgTex" );
		if( bgTex != null ) {
			err = Rend().SetBgTex( bgTex );
			if( err == true )
				return;
		}
		
		String  autoRotStr = app.getParameter( "AutoRotate" );
		if( autoRotStr != null ) {
			int  autoRotate = ( Integer.valueOf( autoRotStr ) ).intValue();
			if( autoRotate > 10 )
				autoRotate = 10;
			else if( autoRotate < -10 )
				autoRotate = -10;
			else if( autoRotate == 0 )
				autoRotate = 1;
				
			Rend().SetAutoRotate( autoRotate );
		}
		
		err = ToProcessor( xvl3FileUrl, numDiv );
		if( err == true )
			return;
		err = FromProcessor();
		if( err == true )
			return;
		err = TexFileToImage( xvl3Base );
		if( err == true )
			return;

		// シーン表示オブジェクト作成
		view = new xvDraw( mXvGlobal );

		boundMin = new lvVector( mXvGlobal.processor );
		boundMax = new lvVector( mXvGlobal.processor );

		xvBBox  bBox = new xvBBox( mXvGlobal, Rend(), mXvGlobal.fromProcess );
		err = bBox.GetBBox( boundMin, boundMax );
		if( err == true ) {
			SetError( "BoundBox Error" );
			return;
		}
			
		Rend().view.ViewFit( boundMin, boundMax );

		app.addKeyListener( key );
		app.addMouseListener( mouse );
		app.addMouseMotionListener( mMotion );

		initialized     = true;
		cntForPaintMain =  0;
	}
	
	private final URL
	GetXvl3FileUrl( Applet app, String xvl3FileName )
	{
		URL  xvl3FileUrl = null;
		
		xvl3FileUrl = GetXvl3FileUrlForIE( app, xvl3FileName );
		if( xvl3FileUrl != null )
			return xvl3FileUrl;
		
		xvl3FileUrl = GetXvl3FileUrlForNC( app, xvl3FileName );
		if( xvl3FileUrl != null )
			return xvl3FileUrl;
		
		return null;
	}
	
	private final URL
	GetXvl3FileUrlForIE( Applet app, String xvl3FileName )
	{
		URL  xvl3FileUrl;
		try { 
			xvl3FileUrl = new URL( app.getDocumentBase(), xvl3FileName );
			xvl3FileUrl.openStream();
		}
		catch( Exception e ) {
			return null;
		}

		return xvl3FileUrl;
	}
	
	private final URL
	GetXvl3FileUrlForNC( Applet app, String xvl3FileName )
	{
		String  xvl3FileNameEnc = URLEncoder.encode( xvl3FileName );
		
		URL  xvl3FileUrl;
		try { 
			xvl3FileUrl = new URL( app.getDocumentBase(), xvl3FileNameEnc );
			xvl3FileUrl.openStream();
		}
		catch( Exception e ) {
			return null;
		}

		return xvl3FileUrl;
	}
	
	private final URL
	MakeXvl3Base( URL xvl3FileUrl )
	{
		String  fullPath    = xvl3FileUrl.toString();
		int     lastSlush   = fullPath.lastIndexOf( "/" );
		String  xvl3BaseStr = fullPath.substring( 0, lastSlush+1 );

		URL  xvl3Base;
		try { 
			xvl3Base = new URL( xvl3BaseStr );
		}
		catch( Exception e ) {
			SetError( e.getMessage() );
			return null;
		}
		
		return xvl3Base;
	}
	
	private final boolean
	ToProcessor( URL xvl3FileUrl, int numDiv )
	{
		x3pToProcessor  toProcess = new x3pToProcessor( mXvGlobal.processor );
		
		toProcess.xvl3FileUrl = xvl3FileUrl;
		if( numDiv > 0 )
			toProcess.numDivEdgeOnGreg = numDiv;
			
		boolean  result = toProcess.SetData();
		if( result == lvConst.LV_FAILURE ) {
			SetError( lvError.Message( mXvGlobal.processor ) );
			return true;
		}
		
		toProcess = null;
		
		return false;
	}

	private final boolean
	FromProcessor()
	{
		mXvGlobal.fromProcess = new x3pFromProcessor( mXvGlobal.processor );
		
		boolean  result = mXvGlobal.fromProcess.GetData();
		if( result == lvConst.LV_FAILURE ) {
			SetError( lvError.Message( mXvGlobal.processor ) );
			return true;
		}
		
		return false;
	}
	
	private final boolean
	TexFileToImage( URL xvl3Base )
	{
		try {
			ErrProc().BeginThrowMode();
			
				TexFileToImageMain( xvl3Base );
				
			ErrProc().EndThrowMode();
			return false;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			SetError( lvError.Message( mXvGlobal.processor ) );
			return true;
		}
	}
	
	private final void
	TexFileToImageMain( URL xvl3Base ) throws lvThrowable
	{
		xvTexFileToImage  f2i = new xvTexFileToImage( mXvGlobal, Rend(), xvl3Base );
		
		f2i.Exec();
		
		f2i = null;
	}
	
	public final void
	Start( Applet app0, Runnable run0, MouseListener mouse0, MouseMotionListener mMotion0, KeyListener key0 )
	{
		runner = new Thread( run0 );
		runner.start();
		
		initThread = new Init( app0, mouse0, mMotion0, key0 );
		initThread.start();
		
		app0.requestFocus();
	}

	public final void
	Stop()
	{
		runner = null;
	}

	public final void
	Run( Applet app )
	{
		while( runner != null ) {
			PostKeyPressed();
			PrePaint();
			app.repaint();
			
			try {
			    Thread.sleep( sleepTime );
			}
			catch( InterruptedException e ) {
				SetError( e.getMessage() );
				return;
			}
		}
	}
	
	private final void
	PrePaint()
	{
		if( paintStat != PAINTSTAT_ORG )
			return;
		paintStat = PAINTSTAT_PREPAINT_NOW;
		
		if( initialized == true ) {
			boolean  result = view.DrawScene();
			if( result == lvConst.LV_FAILURE ) {
				SetError( lvError.Message( mXvGlobal.processor ) );
				paintStat = PAINTSTAT_ORG;
				return;
			}
		}
			
		paintStat = PAINTSTAT_PREPAINT_FINISH;
	}		
	
	public final void
	Paint( Applet app, Graphics g )
	{
		if( errorMessage != null ) {
			PrintError( g );
			return;
		}
		
		if( paintStat != PAINTSTAT_PREPAINT_FINISH )
			return;
		paintStat = PAINTSTAT_PAINT_NOW;
			
		if( initialized == false )
			PaintInit( app, g );
		else
			PaintMain( app, g );
		
		paintStat = PAINTSTAT_ORG;
	}

	private final void
	PaintInit( Applet app, Graphics g )
	{
		final String  lp[] = { "-", " ", "|", "/" };
	
		g.setColor( Color.black );
		g.fillRect( 0, 0, app.getSize().width, app.getSize().height );
		g.setColor( new Color( 0, 255, 0 ) );
		g.drawLine( 10, 26, 160, 26 );

		g.setColor( new Color( 0, 255, 0 ) );
		g.drawString( lp[ cntForPaintInit % 4 ], 10, 40 );
		g.drawString( "Now Initializing", 20, 40 );

		g.setColor( new Color( 0, 255, 0 ) );
		long  time1 = new Date().getTime();
		if( startTime < 0 )
			startTime = time1;
		g.drawString( String.valueOf( time1 - startTime ), 10, 52 );
		
		cntForPaintInit++;
	}

	private final void
	PaintMain( Applet app, Graphics g )
	{
		Image  screen = Rend().image;
		if( screen != null )
			g.drawImage( screen, 0, 0, app );
			
		if( cntForPaintMain > 30 )
			sleepTime = 10;
		else
			cntForPaintMain++;
	}

	private final void
	SetError( String s )
	{
		errorMessage = s;
	}
	
	private final void
	PrintError( Graphics g )
	{
		g.setColor( new Color( 255, 0, 0 ) );
		g.drawString( errorMessage, 10, 10 );
	}

	/**
	 *	画面更新
	 */
	public final void
	Update( Applet app, Graphics g )
	{
		app.paint( g );
	}

	/**
	 *	マウス処理
	 */
	public static final int  PAN    = 0;
	public static final int  EXAMIN = 1;
	public static final int  ZOOM   = 2;
	private int      mode			= EXAMIN;		// 入力の動作モード
	private int      prevx;
	private int      prevy;
	private boolean  mouseDragInit	= true;

	public final void
	MousePressed( MouseEvent e )
	{
		mouseDragInit = true;
		
		if( Rend().autoRotMat != null && mode == EXAMIN ) {
			xPressed    = e.getX();
			yPressed    = e.getY();
			timePressed = new Date().getTime();
		}
	}

	public final void
	MouseReleased( Applet app, MouseEvent e )
	{
		if( Rend().autoRotMat != null && mode == EXAMIN ) {
			int   x    = e.getX();
			int   y    = e.getY();
			long  time = new Date().getTime();
			
			if( xPressed == x && yPressed == y ) {
				Rend().autoRotMat.SetUnit();
				return;
			}
			
			int  dTime = ( int )( time - timePressed );
			
			double  xMove = ( double )( x - xPressed ) / dTime * 300.0;
			double  yMove = ( double )( y - yPressed ) / dTime * 300.0;
			
			double  dx = yMove * lvConst.LV_PI / app.getSize().width * 1.50;
			double  dy = xMove * lvConst.LV_PI / app.getSize().width * 1.50;
			Rend().autoRotMat.SetRotateZXY( dx, dy, 0.0 );
		}
	}
	
	public final void
	MouseDragged( Applet app, MouseEvent e )
	{
		if( mouseDragInit == true ) {
			mouseDragInit = false;
			prevx = e.getX();
			prevy = e.getY();
		}

		int  x = e.getX();
		int  y = e.getY();

		if( mode == EXAMIN ) {
			double  dx = ( y - prevy ) * lvConst.LV_PI / app.getSize().width * 1.50;
			double  dy = ( x - prevx ) * lvConst.LV_PI / app.getSize().width * 1.50;
			Rend().view.ViewRotateDir( dx, dy, 0.0 );
		}
		else if( mode == PAN ) {
			double  dx = -( x - prevx ) * 3.00 / app.getSize().width  * 1.30;
			double  dy =  ( y - prevy ) * 3.00 / app.getSize().height * 1.30;
			Rend().view.ViewMove( dx, dy, 0.0 );
		}
		else if( mode == ZOOM ) {
			lvVector  p0 = boundMin;
			lvVector  p1 = boundMax;
			double  mx = p1.x - p0.x;
			double  my = p1.y - p0.y;
			double  mz = p1.z - p0.z;
			double  d = (mx + my + mz ) / 3.0;

			double  dx = d * ( x - prevx ) * 5.00 / app.getSize().width;
			double  dy = d * ( y - prevy ) * 5.00 / app.getSize().height;
			Rend().view.ViewZoom( -dx, dy, 0.0 );
		}

		prevx = x;
		prevy = y;
	}


 	private boolean  viewFitFlag		= false;
 	private int      numDivForPress		= 0;
  
	public final void
	KeyPressed( Applet app, KeyEvent evt )
	{
		char  c = evt.getKeyChar();
		if( c == 'x' )
			mode = EXAMIN;
		else if( c == 'z' )
			mode = PAN;
		else if( c == 'c' )
			mode = ZOOM;
		else if( c == 'a' )
			viewFitFlag    = true;
		else if( c == '2' )
			numDivForPress = 2;
		else if( c == '4' )
			numDivForPress = 4;
		else if( c == '6' )
			numDivForPress = 6;
		else if( c == '8' )
			numDivForPress = 8;
			
		if( numDivForPress > 0 ) {
			if( Rend().autoRotMat != null )
				Rend().autoRotMat.SetUnit();
				
			sleepTime = 1000;
		}
	}
	
	private final void
	PostKeyPressed()
	{
		PostKeyPressedViewFit();
		PostKeyPressedNumDiv();
	}

	private final void
	PostKeyPressedViewFit()
	{
		if( viewFitFlag == true ) {
			viewFitFlag = false;

			Rend().view.ViewFit( boundMin, boundMax );
			if( Rend().autoRotMat != null )
				Rend().autoRotMat.SetUnit();
		}
	}
	
	private final void
	PostKeyPressedNumDiv()
	{
		boolean  err;

		if( numDivForPress > 0 ) {
			int  numDiv = numDivForPress;
			numDivForPress = 0;
			
			PostKeyPressedNumDivMain( numDiv );

			err = FromProcessor();
			if( err == true )
				return;
			
			xvBBox  bBox = new xvBBox( mXvGlobal, Rend(), mXvGlobal.fromProcess );
			err = bBox.GetBBox( boundMin, boundMax );
			if( err == true ) {
				SetError( "BoundBox Error" );
				return;
			}
			
			sleepTime       = 100;
			cntForPaintMain = 0;
		}
	}

	private final void
	PostKeyPressedNumDivMain( int numDiv )
	{
		lvToKernel  toKernel = new lvToKernel( mXvGlobal.processor );
			
		for( int i=0; i<mXvGlobal.fromProcess.shellInfo.length; i++ ) {
			
			lvToKernelType.Attr  attr = toKernel.GetAttr( i );
			attr.numDiv = numDiv;
				
			boolean  result = toKernel.SetAttr( i, attr );
			if( result == lvConst.LV_FAILURE ) {
				SetError( lvError.Message( mXvGlobal.processor ) );
				return;
			}
		}
		
		toKernel = null;
	}

}
