//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvDraw.java
//

package jp.co.lattice.vApplet;

/*
	トラバース。
	シーンをトップからたどる	
*/

import	java.awt.*;
import	java.awt.image.*;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;

import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvDraw extends xvRoot {
	
	private x3pMatrix  posMat		= null;
	private x3pMatrix  normalMat	= null;
	
	private xvView     mView		= null;
	

	public xvDraw( xvGlobal dt )
	{
		super( dt );
		
		mView = new xvView( dt );
    }

// -------------------------------------------------------------------

	private xvRend
	Rend()
	{
		return global.rend;
	}

	private x3pGlobal
	Processor()
	{
		return global.processor;
	}

	/** lvError用のグローバルデータ				*/
	private final lvError.Global
	ErrProc()
	{
		return  ( ( lvComGblElm )Processor().GCom() ).gError;
	}

	private x3pFromProcessor
	FromProcess()
	{
		return global.fromProcess;
	}
	
	private xvTexture
	Texture( int index )
	{
		return global.texImage[ index ];
	}
	
	
// -------------------------------------------------------------------

	/**
	 *	関連付けられたシーンを描画する
	 *	@return	レンダリングされた画像
	 */
	public final boolean
	DrawScene()
	{
		try {
			ErrProc().BeginThrowMode();
			
				DrawSceneMain();
				
			ErrProc().EndThrowMode();
			return  lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return  lvConst.LV_FAILURE;
		}
	}

	private final void
	DrawSceneMain() throws lvThrowable
	{
		if( FromProcess() == null )
		    return;

		ViewProc();

		Rend().Clear();	        			// クリア
		DrawSceneSub();						// 描画

		Rend().Flush();
	}

	private final void
	ViewProc()
	{
		mView.Set( Rend().view );
		
		if( Rend().autoRotMat != null ) {
			mView.viewRotateMat = mView.viewRotateMat.Mul( Rend().autoRotMat );
			mView.ViewCalcMat();
			
			Rend().view.Set( mView );
		}
	}

	//
	// IndexedFaceSet Work
	//
	// 変換バッファ
	lvVector  v[]  = new lvVector[ 1 ];		// 頂点
	lvVector  fn[] = new lvVector[ 1 ];

	// 三角形描画バッファ
	lvVector[]	 nmlBuf = new lvVector[ 4 ];	
	int[]		 colBuf = new int[ 4 ];	


	private final void
	DrawSceneSub() throws lvThrowable
	{
		for( int i=0; i<FromProcess().shellInfo.length; i++ ) {
			
			x3pShellInfo  shellInfo = FromProcess().shellInfo[ i ];
			
			for( int j=0; j<shellInfo.instance.length; j++ ) {
				
				if( shellInfo.instance[ j ].visibility == true ) {
					SetInstance( shellInfo.instance[ j ] );
				
					for( int k=0; k<shellInfo.faces.length; k++ )
						DrawFaces( shellInfo.faces[ k ] );
				}
			}
		}
	}

	private void
	SetInstance( x3pShellInfo.Instance instance )
	{
		posMat    = instance.posMat;
		normalMat = instance.normalMat;
	}
	
	private final void
	DrawFaces( x3pShellInfo.Faces faces ) throws lvThrowable
	{
		x3pFromProcessor.Material  material = FromProcess().material[ faces.materialIndex ];
		SetMaterial( material );
		
		SetTexture( faces.enableTexture, faces.textureIndex );
		
		for( int i=0; i<faces.fromKernel.length; i++ )
			DrawSurface( faces.fromKernel[ i ], faces.face[ i ] );
	}
	
	private void
	SetMaterial( x3pFromProcessor.Material material )
	{
		Rend().SetAmbientIntensity( material.ambientIntensity );
		Rend().diffuseColor = new x3pColor( Processor(), material.diffuseColor );
		Rend().shininess	= material.shininess;
	}

	private void
	SetTexture( boolean enableTexture, int textureIndex )
	{
		Rend().texUV.SetEnableTexture( enableTexture );
		
		if( enableTexture == true ) {
			Rend().texture  = FromProcess().texture[ textureIndex ];
			Rend().texImage = Texture( textureIndex );
		}
		else {
			Rend().texture  = null;
			Rend().texImage = null;
		}
	}
	
	private final void
	DrawSurface( lvFromKernel fromKernel, x3pShellInfo.Face face ) throws lvThrowable
	{
		// バッファ配列
		int  i;
		if( v.length < fromKernel.vertex.length ) {
			v = new lvVector[ fromKernel.vertex.length ];
			for( i=0; i<fromKernel.vertex.length; i++ )
			    v[ i ] = new lvVector( Processor() );

			fn = new lvVector[ fromKernel.vertex.length ];
			for( i=0; i<fromKernel.vertex.length; i++ )
			    fn[ i ] = new lvVector( Processor() );
		}

		//vmat = unit * viewMat
		x3pMatrix  vmat = new x3pMatrix( Processor(), posMat );
		vmat.Mul( mView.ViewGetMatrix() );

		// 頂点変換
		for( int j=0; j<fromKernel.vertex.length; j++ ) {
			v[ j ].SetXYZ( fromKernel.vertex[ j ].pos.x, fromKernel.vertex[ j ].pos.y, fromKernel.vertex[ j ].pos.z );
			xvUtil.Mul( v[ j ], vmat, v[ j ] );				//ローカル変換
			Rend().view.TransformPersepectiv( v[ j ] );		// 透視変換
		}

		// 法線変換（光源計算のため）
		for( int j=0; j<fromKernel.vertex.length; j++ ) {
			fn[ j ].SetXYZ( fromKernel.vertex[ j ].normal.x, fromKernel.vertex[ j ].normal.y, fromKernel.vertex[ j ].normal.z );
			xvUtil.Mul( fn[ j ], normalMat, fn[ j ] );
			double  l = fn[ j ].Length();
			if( l >= 1.0e-6 )
				fn[ j ].DivAssign( l );
		}

		xvShadeCore      t  = Rend().shade;
		xvShadeTexCoord  tt = Rend().texUV;
		
		lvVector  view = new lvVector( Processor(), 0.0, 0.0, 1.0 );
		xvUtil.Mul( view, mView.ViewGetRevRotateMatrix(), view );

		for( i=0; i<fromKernel.triIndex.length; i++ ) {
			t.vf0 = v[ fromKernel.triIndex[i].vtxNo[0] ];
			t.vf1 = v[ fromKernel.triIndex[i].vtxNo[1] ];
			t.vf2 = v[ fromKernel.triIndex[i].vtxNo[2] ];

			nmlBuf[ 0 ] = fn[ fromKernel.triIndex[i].vtxNo[0] ];				
			nmlBuf[ 1 ] = fn[ fromKernel.triIndex[i].vtxNo[1] ];				
			nmlBuf[ 2 ] = fn[ fromKernel.triIndex[i].vtxNo[2] ];

			colBuf[ 0 ] = Rend().diffuseColor.GetColor() | 0xff000000;
			colBuf[ 1 ] = Rend().diffuseColor.GetColor() | 0xff000000;
			colBuf[ 2 ] = Rend().diffuseColor.GetColor() | 0xff000000;

			t.c0 = Rend().ColorMul( colBuf[ 0 ], nmlBuf[ 0 ], mView.lightDir, mView.lightDir );
			t.c1 = Rend().ColorMul( colBuf[ 1 ], nmlBuf[ 1 ], mView.lightDir, mView.lightDir );
			t.c2 = Rend().ColorMul( colBuf[ 2 ], nmlBuf[ 2 ], mView.lightDir, mView.lightDir );
			
			if( Rend().texture != null ) {
				tt.tf0 = face.uv[ fromKernel.triIndex[i].vtxNo[0] ];
				tt.tf1 = face.uv[ fromKernel.triIndex[i].vtxNo[1] ];
				tt.tf2 = face.uv[ fromKernel.triIndex[i].vtxNo[2] ];
			}

			t.Draw();
		}
	}

}
