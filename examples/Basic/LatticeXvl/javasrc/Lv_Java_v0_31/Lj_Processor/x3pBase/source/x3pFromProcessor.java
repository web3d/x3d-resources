//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pFromProcessor.java

package jp.co.lattice.vProcessor.base;


import  java.awt.*;

import  jp.co.lattice.vProcessor.com.*;

// ラティスカーネル
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java XVL3 Processor から上位階層に引き渡すデータのクラス
 * @author	  created by Eishin Matsui (00/03/11-)
 */
public class x3pFromProcessor extends x3pRoot {

    /** マテリアルに関する内部クラス	*/
	public static class Material {
		
		/** 環境光の量 */
		public double    ambientIntensity = 0.2;
		/** 拡散反射光色 */
		public x3pColor  diffuseColor     = null;
		/** 発光色 */
		public x3pColor  emissiveColor    = null;
		/** 鏡面反射の鋭さ */
		public double    shininess        = 0.2;
		/** 鏡面反射光色 */
		public x3pColor  specularColor    = null;
		/** 透明度 */
		public double    transparency     = 0.0;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Material( x3pGlobal dt )
		{
			diffuseColor  = new x3pColor( dt, 0.8, 0.8, 0.8 );
			emissiveColor = new x3pColor( dt, 0.0, 0.0, 0.0 );
			specularColor = new x3pColor( dt, 0.0, 0.0, 0.0 );
		}
	}
	
   /** テクスチャに関する内部クラス	*/
	public static class Texture {
		
		/**
		 * テクスチャファイルURL。XVL3ファイルに記述されたままの文字列で返す。								<br>
		 * url != null の時は image == null､  url == null の時は image != null となる。						<br>
		 * もし url == null && image == null の場合は、テクスチャファイルや画像が存在しなかったことを示す
		 */
		public String   url 		= null;
		
		/**
		 * テクスチャファイルImage。XVL3ファイル内に（BASE64等でエンコードされた）画像ファイル				<br>
		 * が存在する場合、変数「image」に値が入る。														<br>
		 * url != null の時は image == null､  url == null の時は image != null となる。						<br>
		 * もし url == null && image == null の場合は、テクスチャファイルや画像が存在しなかったことを示す
		 */
		public Image    image		= null;
		
		/**
		 * S方向繰り返しフラグ 繰り返さない:false, 繰り返す:true --- 初期値：true
		 */
		public boolean  repeatS		= true;
		
		/**
		 * T方向繰り返しフラグ 繰り返さない:false, 繰り返す:true --- 初期値：true
		 */
		public boolean  repeatT		= true;
	}
	
	/** 「1個のシェルに関する「Java XVL3 Processor から上位階層に引き渡すデータ」」の配列 --- 初期値 null */
	public x3pShellInfo[]  shellInfo	= null;
	
	/** マテリアル登録テーブル --- 初期値 null */
	public Material[]      material     = null;

	/** テクスチャ登録テーブル。テクスチャが存在しない時は、null --- 初期値 null */
	public Texture[]       texture      = null;


// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		public x3pShellInfo[]  shell		= null;
		public Material[]      material		= null;
		public Texture[]       texture		= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( x3pGlobal dt )
		{
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gFromProcessor;
	}

	private final x3pToProcessor.Global
	ToProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gToProcessor;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  x3pFromProcessor( x3pGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/** lvError用のグローバルデータ				*/
	private final lvError.Global
	ErrProc()
	{
		return   ( ( lvComGblElm )global.GCom() ).gError;
	}

// -------------------------------------------------------------------

	/**
	 * Java XVL3 Processor からデータを取得する（この関数を実行する前は shellInfo[] を null としておく)
	 * @return				lvConst.LV_SUCCESS または lvConst.LV_FAILURE
	 */
	public final boolean
	GetData()
	{
		try {
			ErrProc().BeginThrowMode();
			
				GetDataMain();
				
			ErrProc().EndThrowMode();
			return  lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return  lvConst.LV_FAILURE;
		}
	}
	
	private final void
	GetDataMain() throws lvThrowable
	{
		ExecTraverse();
		ExecInstance();
		MakeNormalMat();
		
		shellInfo = Gbl().shell;
		material  = Gbl().material;
		texture   = Gbl().texture;
		
		Finish();
	}
	
	private final void
	ExecTraverse() throws lvThrowable
	{
		x3pTrvFromKernel  xvl0 = new x3pTrvFromKernel( global );
		xvl0.FromKernel( ToProcessor().topElms );
	}
	
	private final void
	ExecInstance() throws lvThrowable
	{
		int  numInstance[] = null;

		x3pTrvNumInstance  xvl0 = new x3pTrvNumInstance( global );
		xvl0.GetNumInstance( ToProcessor().topElms );
		numInstance = xvl0.GetNumInstance();
		
		int  numShell = Gbl().shell.length;
		for( int i=0; i<numShell; i++ ) {
			Gbl().shell[ i ].instance = new x3pShellInfo.Instance[ numInstance[ i ] ];
			for( int j=0; j<numInstance[ i ]; j++ )
				Gbl().shell[ i ].instance[ j ] = new x3pShellInfo.Instance( global );
		}
			
		x3pTrvInstance  xvl1 = new x3pTrvInstance( global );
		xvl1.SetInstance( ToProcessor().topElms );
	}
	
	private final void
	MakeNormalMat() throws lvThrowable
	{
		int  numShell = Gbl().shell.length;
		for( int i=0; i<numShell; i++ ) {
			int  numInstance = Gbl().shell[ i ].instance.length;
			for( int j=0; j<numInstance; j++ ) {
				x3pShellInfo.Instance  instance = Gbl().shell[ i ].instance[ j ];
				MakeNormalMatMain( instance );
			}
		}
	}
	
	private final void
	MakeNormalMatMain( x3pShellInfo.Instance instance ) throws lvThrowable
	{
		instance.normalMat.Set( instance.posMat );
		instance.normalMat.m[ 3 ][ 0 ] = 0;
		instance.normalMat.m[ 3 ][ 1 ] = 0;
		instance.normalMat.m[ 3 ][ 2 ] = 0;
		
		x3pMatrix  normalMat = new x3pMatrix( global, instance.normalMat );
		
		normalMat.Invert33();
		normalMat.Transpose();
		
		instance.normalMat.Set( normalMat );
	}
	
	private final void
	Finish()
	{
	}
	
}
