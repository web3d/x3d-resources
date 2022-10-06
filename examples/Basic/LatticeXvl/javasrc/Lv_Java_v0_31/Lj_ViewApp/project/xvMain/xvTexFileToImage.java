//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvTexFileToImage.java
//

package jp.co.lattice.vApplet;

import  java.applet.*;
import  java.awt.*;
import  java.awt.image.*;
import  java.net.*;

import  jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvTexFileToImage extends xvRoot {

	private xvRend  rend;
	private URL     xvl3Base;
	
	/**
	 * コンストラクタ
	 */
	public xvTexFileToImage( xvGlobal dt, xvRend p_rend, URL p_xvl3Base )
	{
		super( dt );
		
		rend     = p_rend;
		xvl3Base = p_xvl3Base;
	}
	
	private x3pFromProcessor
	FromProcess()
	{
		return global.fromProcess;
	}

// -------------------------------------------------------------------

	/**
	 * テクスチャデータの変換
	 */
	public void
	Exec() throws lvThrowable
	{
		if( FromProcess().texture == null )
			return;
		
		global.texImage = new xvTexture[ FromProcess().texture.length ];
			
		for( int i=0; i<FromProcess().texture.length; i++ ) {
			Image  img = Exec0( i );
			global.texImage[ i ] = Exec1( img );
		}
	}
	 
	private Image
	Exec0( int texNo ) throws lvThrowable
	{
		if( FromProcess().texture[ texNo ].url != null )
			return Exec0Main( FromProcess().texture[ texNo ].url );
		else if( FromProcess().texture[ texNo ].image != null )
			return FromProcess().texture[ texNo ].image;
			
		Err().Assert( false, "Texture file not found. (0)" );
		return null;
	}
	
	private Image
	Exec0Main( String texUrl ) throws lvThrowable
	{
		Image  img = null;
		
		img = Exec0ForIE( texUrl );
		if( img != null )
			return img;
		
		img = Exec0ForNC( texUrl );
		if( img != null )
			return img;
		
		Err().Assert( false, "Texture file not found. (1)" );
		return null;
	}
	
	private Image
	Exec0ForIE( String texUrl ) throws lvThrowable
	{
		Image  img = rend.applet.getImage( xvl3Base, texUrl );

		Exec0MediaTracker( img );
		
		int  w = img.getWidth( null );
		int  h = img.getHeight( null );
		if( w <= 0 || h <= 0 )
			img = null;

		return img;
	}
	
	private Image
	Exec0ForNC( String texUrl ) throws lvThrowable
	{
		String  texUrlEnc = URLEncoder.encode( texUrl );
		Image   img = rend.applet.getImage( xvl3Base, texUrlEnc );

		Exec0MediaTracker( img );
		
		int  w = img.getWidth( null );
		int  h = img.getHeight( null );
		if( w <= 0 || h <= 0 )
			img = null;

		return img;
	}
	
	private void
	Exec0MediaTracker( Image img ) throws lvThrowable
	{
		// 読込み終了まで待つ
		MediaTracker  tracker;
		tracker = new MediaTracker( rend.applet );
		tracker.addImage( img, 0 );
		try {
			tracker.waitForID( 0 );
		}
		catch( InterruptedException e ) {
			Err().Assert( false, "xvTexFileToImage.Exec0MediaTracker(0)" );
		}
	}
	
	public xvTexture
	Exec1( Image img ) throws lvThrowable
	{
		xvTexture  texImage = new xvTexture( global );
		
		// サイズ取得
		int  w = img.getWidth( null );
		int  h = img.getHeight( null );
		Err().Assert( ( w > 0 && h > 0 ), "TxvTexFileToImage.Exec1(0)" );
		
		texImage.w = w;
		texImage.h = h;

		// Pixel Dataの取り出し		
		texImage.mem = new int[ w * h ];
		PixelGrabber  pg = new PixelGrabber( img, 0, 0, w, h, texImage.mem, 0, w );
		try {
			pg.grabPixels();
		}
		catch( InterruptedException e ) {
			Err().Assert( false, "xvTexFileToImage.Exec1(1)" );
		}
		
		return texImage;
	}

}
