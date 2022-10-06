//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvTexture.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvTexture extends xvRoot {

	public int	    w	    = 0;
	public int	    h	    = 0;
	public int[]    mem	    = null;		// ピクセル配列


	/** コンストラクタ */
	public xvTexture( xvGlobal dt )
	{
		super( dt );
	}

	/**
	 *	指定座標のピクセルを得る
	 *
	 *	@param	x	固定小数値
	 *	@param	y	固定小数値
	 *  @return		ピクセルカラー
	 */
	public int
	GetPixel( int x, int y, boolean repeatS, boolean repeatT )
	{
		int	tx = ( ( w - 1 ) * x ) / 0x10000;
		int	ty = ( ( h - 1 ) * y ) / 0x10000;

		if( repeatS == false ) {
			if( tx < 0 )
				tx = 0;
			else if( tx >= w )
				tx = w - 1;
		}
		if( repeatT == false ) {
			if( ty < 0 )
				ty = 0;
			else if( ty >= h )
				ty = h - 1;
		}
		
		if( tx < 0 )
			tx = w - ( ( -tx ) % w );
		else
			tx %= w;
			
		if( ty < 0 )
			ty = h - ( ( -ty ) % h );
		else
			ty %= h;
			
		ty = ( h - 1 ) - ty;

		return mem[ tx + ty * w ];
	}
	
}
