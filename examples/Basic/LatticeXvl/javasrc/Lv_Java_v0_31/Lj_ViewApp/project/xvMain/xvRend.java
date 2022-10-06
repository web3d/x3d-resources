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
	�O�[���[�V�F�[�f�B���O����
		�V�F�[�f�B���O�p�i�f�t�H���g�U�O�x�j

	�����v�Z
		�������W
		�����P�x
		��ΐ���i�t�F�[�h�A�E�g�Ȃǂ̂��߁j
*/


/**
 * �����_�����O�G���W���i���j
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvRend extends xvRoot {

	//----------------------------------------
	//		�萔��`
	//----------------------------------------
	/**
	 *	�Œ菬���萔
	 */
	public static final int  FP = 0x10000;

	/**
	 *	���ő�l
	 */
    private static final int  ALPHA =0xff000000;

	//----------------------------------------
	//		��
	//----------------------------------------
	public	Applet  applet;
	
	// �o�̓T�C�Y
	public	int  width;
	public	int  height;

	// �`��o�b�t�@
	public	Image		image;						// �C���[�W
	public	int[]		mem;			    		// �������[
	public	int[]		zbuf;						// Z�o�b�t�@

	private int			bgColor		= 0xff000000;
	private int[]		bgTexMem	= null;			//�������[�C���[�W
	
	public	x3pMatrix   autoRotMat	= null;
	
	// �`��f�o�C�X
	public	xvShadeCore      shade	= null;
	
	// �e�N�X�`��UV�l���Z�f�o�C�X
	public	xvShadeTexCoord  texUV	= null;


	//----------------------------------------
	//		���_
	//----------------------------------------
	/**
	 *	���_
	 */
	public xvView  view		= null;

	// �X�N���[���ϊ�
	public lvVector  screenScl   = null;	    // �X�N���[���g�嗦
	public lvVector  screenPoint = null;	    // �X�N���[���ʒu�␳


	//----------------------------------------
	//		����
	//----------------------------------------
	/**
	 *	�����F
	 */
	private x3pColor  lightColor = null;
	/**
	 *	�������x
	 */
	private int		  lightAmbientIntensity = ( int )( 0.50 * 0x100 );


	//----------------------------------------
	//		�f��
	//----------------------------------------
	/**
	 *	�����_�����O�X�e�[�^�X�iAmbient)
	 */
	public int		   ambientIntensity = ( int )( 0.2 * 0x100 );			// �����W��
	public x3pColor	   diffuseColor	    = null;								// �g�U���W��
//	public x3pColor	   emissiveColor	= null;								// ���ˌ�����
	public double	   shininess		= 0.2;								// ���ʌ��̎w��
//	public x3pColor	   specularColor	= null;								// ���ʌ��W��
//	public double	   transparency	    = 0.0;								// �����x

	public x3pFromProcessor.Texture  texture	= null;						// �J�����g�e�N�X�`�����P
	public xvTexture                 texImage	= null;						// �J�����g�e�N�X�`�����Q


//-------------------------------------------------------------------------

	public final void
	SetAmbientIntensity( double val )
	{
		ambientIntensity = ( int )( val * 0x100 );
	}


	//=========================================================
	//	���\�b�h
	//---------------------------------------------------------

	/**
	 *	�R���X�g���N�^
	 *	@param	w	�\����
	 *	@param	h	�\����
	 *	@param	ap	�e�A�v���b�g
	 */
	public xvRend( xvGlobal dt, int w, int h, Applet ap )
	{
		super( dt );
		Constractor( dt );
		
		applet = ap;
		SetSize( w, h, ap );				    // �ݒ�

		// �����ݒ�
		view.lightDir = new lvVector( dt.processor, 1.0, 0.0, -1.0 );
		try {
			view.lightDir.UnitAssign();
		}
		catch( lvThrowable e ) {
			int  dmy;	dmy = 0;
		}

		// ���_�ݒ�
		view.viewDir	   = new lvVector( dt.processor, 0.0, 0.0, 0.0 );	
		view.viewLookPt	   = new lvVector( dt.processor, 0.0, 0.0, 0.0 );	
		view.viewDistant   = 12.0;

		view.viewMat	   = new x3pMatrix( dt.processor );
		view.viewRotateMat = new x3pMatrix( dt.processor );

		view.ViewRotateDir( 0.0, 0.0, 0.0 );		// �v�Z

		shade = new xvShade( dt, this );
		texUV = new xvShadeTexCoord( dt, this );
	}

	private void
	Constractor( xvGlobal dt )
	{
		view = new xvView( dt );
		
		screenScl   = new lvVector( dt.processor, 200.0, 200.0, 0.0 );	    // �X�N���[���g�嗦
		screenPoint = new lvVector( dt.processor, 100.0, 100.0, 0.0 );	    // �X�N���[���ʒu�␳

		lightColor = new x3pColor( dt.processor, 1.0, 1.0, 1.0 );
		
		diffuseColor  = new x3pColor( dt.processor, 0.8, 0.8, 0.8 );	// �g�U���W��
//		emissiveColor = new x3pColor( dt.processor, 0.0, 0.0, 0.0 );	// ���ˌ�����
//		specularColor = new x3pColor( dt.processor, 0.0, 0.0, 0.0 );	// ���ʌ��W��

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
	 *	�T�C�Y�ݒ�
	 *	@param	w	�\����
	 *	@param	h	�\����
	 *	@param	ap	�e�A�v���b�g
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
	 *	�w�i�F�̓o�^
	 */
	public void
	SetBgColor( String bgColStr )
	{
		String  str = bgColStr.substring( 1 );
		bgColor = ( Integer.valueOf( str, 16 ) ).intValue() | ALPHA;
	}
	
	/**
	 *	�w�i�C���[�W�̓o�^
	 */
	public boolean
	SetBgTex( String bgTex )
	{
		Image  tmpImg;
		
		Image  bgImage = SetBgTexMain( bgTex );
		if( bgImage == null )
			return true;

		// ��ʃT�C�Y�ɍ��킹��
		tmpImg = applet.createImage( width, height );
		
		Graphics  tmpGrp = tmpImg.getGraphics();
		tmpGrp.setColor( applet.getBackground() );
		tmpGrp.fillRect( 0, 0, width, height );
		tmpGrp.drawImage( bgImage, 0, 0, width, height, null );
		
		// Pixel Data�̎��o��		
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
		// �Ǎ��ݏI���܂ő҂�
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
	//	�����_�����O
	//---------------------------------------------------------

	/**
	 *	�w�i����
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
	 *	�C���[�W��flush
	 *		�����_�����O���I��������A�Ăяo������
	 */
	public final void
	Flush()
	{
		image.flush();
	}

	/**
	 *	���邳���Z
	 *	@param	color	diffuse(�g�U���j
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

		// ambient����
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
		
		// diffuse����
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
