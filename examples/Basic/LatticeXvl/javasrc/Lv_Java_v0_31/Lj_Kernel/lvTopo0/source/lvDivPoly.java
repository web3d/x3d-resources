//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivPoly.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.core.g0.lvDivPolyLow;
import	jp.co.lattice.vKernel.tex.a0.lvDivPolyUV;
import	jp.co.lattice.vKernel.tex.a0.lv0DivPolyUV;
import	jp.co.lattice.vKernel.tex.a0.lv0AttGblElm;


/**
 * Round情報の作成クラス（上位レイヤー）
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDivPoly extends lvRoot {
	
	private static final int  maxNumVtxPos = 256;
		
// -------------------------------------------------------------------
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo							*/
		private int  curShellNo		= 0;
		
		/** カレントGS面No								*/
		private int  curGsNo		= 0;

		/** 下位レイヤー(geom0)オブジェクト				*/
		private lvDivPolyLow              divPolyLow		= null;

		/** 下位レイヤー(att0)オブジェクト				*/
		private lvDivPolyUV               divPolyUV			= null;

		/** lv0AttGblElmオブジェクト		*/
		private lv0AttGblElm              attGblElm			= null;
		
		/** 下位レイヤー(geom0)に送るデータ				*/
		private lvDivPolyLow.DownDivPoly  downDivPoly		= null;
		
		/** 下位レイヤー(geom0)から送られるデータ		*/
		private lvDivPolyLow.UpDivPoly    upDivPoly			= null;

		/** 小規模な DownDivPoly().vtxPos 用のグローバルデータ		*/
		private lvVector  tmpVtxPos[]						= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			divPolyLow  = new lvDivPolyLow( dt );
			divPolyUV   = new lv0DivPolyUV( dt );
			attGblElm	= new lv0AttGblElm( dt );
			downDivPoly = new lvDivPolyLow.DownDivPoly();
			upDivPoly   = new lvDivPolyLow.UpDivPoly();

			tmpVtxPos = new lvVector[ maxNumVtxPos ];
			for( int i=0; i<maxNumVtxPos; i++ )
				tmpVtxPos[ i ] = new lvVector( dt );
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lvTopoGblElm )global.GTopo() ).gDivPoly;
	}
	/** lvPolygonデータ用クラスオブジェクト		*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** DownRound用のグローバルデータ				*/
	private final lvDivPolyLow.DownDivPoly
	DownDivPoly()
	{
		return  Gbl().downDivPoly;
	}
	/** UpRound用のグローバルデータ				*/
	private final lvDivPolyLow.UpDivPoly
	UpDivPoly()
	{
		return  Gbl().upDivPoly;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvDivPoly( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1個のポリゴンをテセレーションする
	 * @param  shellNo		(( I )) lvToKernel.SetNumShell( num ) とセットした場合、0 ～ ( num-1 )
	 * @param  gsNo			(( I )) 配列 lvToKernel.gsNumVtx[] の長さが n の場合、0 ～ ( n-1 )
	 * @return				下位レイヤー(geom0)から送られるデータ
	 */
	public final lvDivPolyLow.UpDivPoly
	Exec( int shellNo, int gsNo ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		
		SetDownDivPoly();
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().divPolyUV.SetDownDivPolyUV( shellNo, gsNo, DownDivPoly().divPolyUV );
		
		Gbl().divPolyLow.Exec( DownDivPoly(), UpDivPoly() );
		
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().divPolyUV.SetUpDivPolyUV();
		SetUpDivPoly();
		
		Finish();
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().divPolyUV.Finish();
		
		return  UpDivPoly();
	}
	
	private final void
	SetDownDivPoly()
	{
		lvRec.SeqPart  face = Polygon().face[ Gbl().curGsNo ];
		NewDownDivPoly( face.num );
		
		for( int i=0; i<face.num; i++ ) {
			lvPolygon.FaceHalf  faceHalfSeq = Polygon().faceHalfSeq[ face.start + i ];
			lvVector            vtxPos      = DownDivPoly().vtxPos[ i ];
			vtxPos.VecDt2Vector( Polygon().vertex[ faceHalfSeq.vtxNo ].pos );
		}
	}
	
	private final void
	NewDownDivPoly( int num )
	{
		DownDivPoly().numVtxPos = num;
		
		if( num > maxNumVtxPos ) {
			DownDivPoly().vtxPos = new lvVector[ num ];
			for( int i=0; i<maxNumVtxPos; i++ )
				DownDivPoly().vtxPos[ i ] = Gbl().tmpVtxPos[ i ];
			for( int i=maxNumVtxPos; i<num; i++ )
				DownDivPoly().vtxPos[ i ] = new lvVector( global );
		}
		else
			DownDivPoly().vtxPos = Gbl().tmpVtxPos;
	}
	
	private final void
	SetUpDivPoly()
	{
		// no process
	}
	
	private final void
	Finish()
	{
		DownDivPoly().vtxPos = null;		// Delete( DownDivPoly().vtxPos );
	}
	
}
