//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvBBox.java
//

package jp.co.lattice.vApplet;

/*
	トラバース。
	シーンをトップからたどる	
*/

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;

import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvBBox extends xvRoot {
	
	private xvRend            rend;
	private x3pFromProcessor  fromProcess;
	
	private x3pMatrix  posMat		= null;

	private lvVector   min  		= null;
	private lvVector   max  		= null;
	private boolean	   flag 		= false;


	public xvBBox( xvGlobal dt )
	{
		super( dt );
		
		rend 	    = null;
		fromProcess = null;
    }

	/**
	 *	コンストラクタ
	 *	@param	re	関連付けるレンダリング環境
	 *	@param	nd	シーン
	 */
	public xvBBox( xvGlobal dt, xvRend re, x3pFromProcessor fromProcess0 )
	{
		super( dt );
		
		rend 	    = re;
		fromProcess = fromProcess0;
	}

	private x3pGlobal
	Processor()
	{
		return global.processor;
	}

	/**
	 *	関連付けられたシーンを描画する
	 *	@return	レンダリングされた画像
	 */
	public final boolean
	GetBBox( lvVector min0, lvVector max0 )
	{
		if( fromProcess == null )
		    return true;

		min = min0;
		max = max0;
		min.SetXYZ( 0.0, 0.0, 0.0 );
		max.SetXYZ( 0.0, 0.0, 0.0 );
		flag = false;

		for( int i=0; i<fromProcess.shellInfo.length; i++ ) {
			
			x3pShellInfo  shellInfo = fromProcess.shellInfo[ i ];
			
			for( int j=0; j<shellInfo.instance.length; j++ ) {
				
				SetInstance( shellInfo.instance[ j ] );
				
				for( int k=0; k<shellInfo.faces.length; k++ )
					DrawFaces( shellInfo.faces[ k ] );
			}
		}
		
		return false;
	}

	private void
	SetInstance( x3pShellInfo.Instance instance )
	{
		posMat = instance.posMat;
	}
	
	private final void
	DrawFaces( x3pShellInfo.Faces faces )
	{
		for( int i=0; i<faces.fromKernel.length; i++ )
			DrawSurface( faces.fromKernel[ i ] );
	}
	
	private final void
	DrawSurface( lvFromKernel fromKernel )
	{
		for( int j=0; j<fromKernel.vertex.length; j++ ) {
			lvVector  v = new lvVector( Processor(),
								fromKernel.vertex[ j ].pos.x, fromKernel.vertex[ j ].pos.y, fromKernel.vertex[ j ].pos.z );
			xvUtil.Mul( v, posMat, v );

			if( flag == false ) {
				min.x = max.x = v.x;
				min.y = max.y = v.y;
				min.z = max.z = v.z;
				flag = true;
			}
				
			if( min.x > v.x )	min.x = v.x;
			if( min.y > v.y )	min.y = v.y;
			if( min.z > v.z )	min.z = v.z;

			if( max.x < v.x )	max.x = v.x;
			if( max.y < v.y )	max.y = v.y;
			if( max.z < v.z )	max.z = v.z;
		}
	}

}
