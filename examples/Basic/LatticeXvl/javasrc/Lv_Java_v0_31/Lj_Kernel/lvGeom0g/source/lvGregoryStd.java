//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvGregoryStd.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;

import	jp.co.lattice.vKernel.tex.a0g.lvTessellateUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0GregoryUV;


/**
 * 1個の非縮退面をポリゴン分割するクラス
 * @author	  created by Eishin Matsui (99/10/16-)
 * 
 */
public class lvGregoryStd extends lvRoot {

	/**
	 * lvTessellateLowから送られるデータ用の内部クラス
	 */
	public static class DownGregoryStd {
		
		/** u方向（ハーフエッジNo1の始点→終点、ハーフエッジNo3の終点→始点）の分割数		*/
		public int		 numDivU;
		/** v方向（ハーフエッジNo0の終点→始点、ハーフエッジNo2の始点→終点）の分割数		*/
		public int		 numDivV;
		
		/** 制御点位置							*/
		public double    ctlPnt[/*20*/][/*3*/]				= null;
		
		//	
		//					   edge3
		//	
		//		  2			1		  18		17
		//			+-------+---------+-------+
		//			|		|		  |		  |
		//			|		|		  |		  |
		//			|		x 0	   19 x		  |
		//	   e  3 +-----+				+-----+ 16	e
		//	   d	|	  4				15	  |		d
		//	   g	|						  |		g
		//	   e	|	  5				14	  |		e
		//	   0  6 +-----+				+-----+ 13	2
		//			|		x 9	   10 x		  |
		//			|		|		  |		  |
		//			|		|		  |		  |
		//			+-------+---------+-------+
		//		  7			8		  11		12
		//	
		//						edge1
		//

		/** UV空間情報の配列		*/
		public lvTessellateUV.DownTessellateUV  uvInfo		= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public	DownGregoryStd( lvGlobal dt )
		{
			ctlPnt = new double[ 20 ][];
			for( int i=0; i<20; i++ )
				ctlPnt[ i ] = new double[ 3 ];
		}
	}
	
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントの「上位レイヤーから送られるデータ」		*/
		private DownGregoryStd                curDownGregoryStd		= null;
		/** カレントの「上位レイヤーに送るデータ」				*/
		private lvTessellateLow.UpTessellate  curUpTessellate		= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			curDownGregoryStd = new DownGregoryStd( dt );
			curUpTessellate   = new lvTessellateLow.UpTessellate();
			
			GlobalTmp( dt );
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvGregory.ParamSet  tpVertexProc[]				= null;
		private lvVector            tvTriIndexProc[]			= null;
		private lvVector            tvAryGetFaceNormal[/*4*/]	= null;
		private lvVector            tvIsConcave[]				= null;
		private lvVector            tvCorrectNormal0[]			= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tpVertexProc    = new lvGregory.ParamSet[ 2 ]; for( int i=0; i<2; i++ )	tpVertexProc[ i ]    = new lvGregory.ParamSet();
			tvTriIndexProc  = new lvVector[ 2 ];		   for( int i=0; i<2; i++ )	tvTriIndexProc[ i ]  = new lvVector( dt );
			tvAryGetFaceNormal = new lvVector[ 4 ];
			for( int i=0; i<4; i++ )	tvAryGetFaceNormal[ i ] = new lvVector( dt );
			tvIsConcave     = new lvVector[ 2 ];		   for( int i=0; i<2; i++ )	tvIsConcave[ i ]     = new lvVector( dt );
			tvCorrectNormal0   = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )	tvCorrectNormal0[ i ]   = new lvVector( dt );
		}

	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gGregoryStd;
	}
	/** DownGregoryStd用のグローバルデータ			*/
	private final DownGregoryStd
	DownGregoryStd()
	{
		return  Gbl().curDownGregoryStd;
	}
	/** UpRound用のグローバルデータ				*/
	private final lvTessellateLow.UpTessellate
	UpTessellate()
	{
		return  Gbl().curUpTessellate;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public	lvGregoryStd( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	/**
	 * 実行関数
	 */
	public final void
	Exec( DownGregoryStd downGregoryStd, lvTessellateLow.UpTessellate upTessellate ) throws lvThrowable
	{
		Gbl().curDownGregoryStd = downGregoryStd;
		Gbl().curUpTessellate   = upTessellate;
		
		VertexProc();
		TriIndexProc();
		CorrectNormal();
	}
	
	private final void
	VertexProc() throws lvThrowable
	{
		lvGregory.ParamSet  param = Gbl().tpVertexProc[ 0 ];					// param = new lvGregory.ParamSet();
		
		for( int v=0; v<( DownGregoryStd().numDivV + 1 ); v++ ) {
			for( int u=0; u<( DownGregoryStd().numDivU + 1 ); u++ ) {
				int  denomU = DownGregoryStd().numDivU;
				int  denomV = DownGregoryStd().numDivV;
				lvGregory.SetParam( u, denomU, v, denomV, param );
				double    ctlPnt[/*20*/][/*3*/] = DownGregoryStd().ctlPnt;
				int       n = ( v * ( DownGregoryStd().numDivU + 1 ) ) + u;
				
				lvGregory.Position( param, ctlPnt, UpTessellate().vertex[ n ].pos );
				lvGregory.Normal( param, ctlPnt, UpTessellate().vertex[ n ].normal );
				
				lv0GregoryUV.Exec( n, param.u, param.v, DownGregoryStd().uvInfo, UpTessellate().uvInfo );
			}
		}
	}
	
	private final void
	TriIndexProc() throws lvThrowable
	{
		lvVector  faceNormal = Gbl().tvTriIndexProc[ 0 ];					// faceNormal = new lvVector( global );
		
		for( int v=0; v<DownGregoryStd().numDivV; v++ ) {
			for( int u=0; u<DownGregoryStd().numDivU; u++ ) {
				int  n00 = ( v * ( DownGregoryStd().numDivU + 1 ) ) + u;
				int  n10 = ( v * ( DownGregoryStd().numDivU + 1 ) ) + ( u + 1 );
				int  n01 = ( ( v + 1 ) * ( DownGregoryStd().numDivU + 1 ) ) + u;
				int  n11 = ( ( v + 1 ) * ( DownGregoryStd().numDivU + 1 ) ) + ( u + 1 );
				
				int  n   = ( ( v * DownGregoryStd().numDivU ) + u ) * 2;
				
				boolean  isZero00 = UpTessellate().vertex[ n00 ].normal.IsZero();
				boolean  isZero10 = UpTessellate().vertex[ n10 ].normal.IsZero();
				boolean  isZero01 = UpTessellate().vertex[ n01 ].normal.IsZero();
				boolean  isZero11 = UpTessellate().vertex[ n11 ].normal.IsZero();
				if( isZero00 == true || isZero10 == true || isZero01 == true || isZero11 == true ) {
					if( isZero00 == true || isZero11 == true )
						TriIndexProc0( n, n00, n10, n01, n11 );
					else
						TriIndexProc1( n, n00, n10, n01, n11 );
					continue;
				}
				
				GetFaceNormal( n00, n10, n01, n11, faceNormal );
				boolean  isConcave00 = IsConcave( faceNormal, n01, n00, n10 );
				boolean  isConcave10 = IsConcave( faceNormal, n00, n10, n11 );
				boolean  isConcave01 = IsConcave( faceNormal, n11, n01, n00 );
				boolean  isConcave11 = IsConcave( faceNormal, n10, n11, n01 );
				if( isConcave00 == true || isConcave10 == true || isConcave01 == true || isConcave11 == true ) {
					if( isConcave00 == true || isConcave11 == true )
						TriIndexProc0( n, n00, n10, n01, n11 );
					else
						TriIndexProc1( n, n00, n10, n01, n11 );
					continue;
				}
						
				double   len0 = ( UpTessellate().vertex[ n00 ].pos.Sub( UpTessellate().vertex[ n11 ].pos ) ).Length2();
							// len0 = ( UpTessellate().vertex[ n00 ].pos - UpTessellate().vertex[ n11 ].pos ).Length2();
				double   len1 = ( UpTessellate().vertex[ n10 ].pos.Sub( UpTessellate().vertex[ n01 ].pos ) ).Length2();
							// len0 = ( UpTessellate().vertex[ n10 ].pos - UpTessellate().vertex[ n01 ].pos ).Length2();
				if( len0 <= len1 )
					TriIndexProc0( n, n00, n10, n01, n11 );
				else
					TriIndexProc1( n, n00, n10, n01, n11 );
			}
		}
	}

	private final void
	TriIndexProc0( int n, int n00, int n10, int n01, int n11 )
	{
		UpTessellate().triIndex[ n ].vtxNo[ 0 ] = n00;
		UpTessellate().triIndex[ n ].vtxNo[ 1 ] = n10;
		UpTessellate().triIndex[ n ].vtxNo[ 2 ] = n11;
		UpTessellate().triIndex[ n+1 ].vtxNo[ 0 ] = n11;
		UpTessellate().triIndex[ n+1 ].vtxNo[ 1 ] = n01;
		UpTessellate().triIndex[ n+1 ].vtxNo[ 2 ] = n00;
	}

	private final void
	TriIndexProc1( int n, int n00, int n10, int n01, int n11 )
	{
		UpTessellate().triIndex[ n ].vtxNo[ 0 ] = n01;
		UpTessellate().triIndex[ n ].vtxNo[ 1 ] = n00;
		UpTessellate().triIndex[ n ].vtxNo[ 2 ] = n10;
		UpTessellate().triIndex[ n+1 ].vtxNo[ 0 ] = n10;
		UpTessellate().triIndex[ n+1 ].vtxNo[ 1 ] = n11;
		UpTessellate().triIndex[ n+1 ].vtxNo[ 2 ] = n01;
	}
	
	private final void
	GetFaceNormal( int n00, int n10, int n01, int n11, lvVector faceNormal ) throws lvThrowable
	{
		lvVector  pos[/*4*/] = Gbl().tvAryGetFaceNormal;
		
		pos[ 0 ].Assign(  UpTessellate().vertex[ n00 ].pos );
		pos[ 1 ].Assign(  UpTessellate().vertex[ n10 ].pos );
		pos[ 2 ].Assign(  UpTessellate().vertex[ n11 ].pos );
		pos[ 3 ].Assign(  UpTessellate().vertex[ n01 ].pos );
		faceNormal.Normal( pos, 4 );
		double  len = faceNormal.Length();
		if( lvEps.IsZero( len ) == false )
			faceNormal.DivAssign( len );
	}
	
	private final boolean
	IsConcave( lvVector faceNormal, int nb, int n, int nf )
	{
		lvVector  cross = Gbl().tvIsConcave[ 0 ];					// cross = new lvVector( global );
		
		lvVector  pb = UpTessellate().vertex[ nb ].pos;
		lvVector  p  = UpTessellate().vertex[ n  ].pos;
		lvVector  pf = UpTessellate().vertex[ nf ].pos;
		
		cross.Assign( ( pf.Sub( p ) ).Cross( pb.Sub( p ) ) );
		if( faceNormal.Dot( cross ) < 0.0 )
			return true;
			
		return false;
	}
	
	private final void
	CorrectNormal() throws lvThrowable
	{
		CorrectNormal0();
		CorrectNormal1();
	}
	
	private final void
	CorrectNormal0() throws lvThrowable
	{
		lvVector  faceNormal = Gbl().tvCorrectNormal0[ 0 ];					// faceNormal = new lvVector( global );
		
		for( int v=0; v<DownGregoryStd().numDivV; v++ ) {
			for( int u=0; u<DownGregoryStd().numDivU; u++ ) {
				int n00 = ( v * ( DownGregoryStd().numDivU + 1 ) ) + u;
				int n10 = ( v * ( DownGregoryStd().numDivU + 1 ) ) + ( u + 1 );
				int n01 = ( ( v + 1 ) * ( DownGregoryStd().numDivU + 1 ) ) + u;
				int n11 = ( ( v + 1 ) * ( DownGregoryStd().numDivU + 1 ) ) + ( u + 1 );
				
				GetFaceNormal( n00, n10, n01, n11, faceNormal );

				boolean  isZero00 = UpTessellate().vertex[ n00 ].normal.IsZero();
				boolean  isZero10 = UpTessellate().vertex[ n10 ].normal.IsZero();
				boolean  isZero01 = UpTessellate().vertex[ n01 ].normal.IsZero();
				boolean  isZero11 = UpTessellate().vertex[ n11 ].normal.IsZero();
				
				double  dot00 = faceNormal.Dot( UpTessellate().vertex[ n00 ].normal );
				double  dot10 = faceNormal.Dot( UpTessellate().vertex[ n10 ].normal );
				double  dot01 = faceNormal.Dot( UpTessellate().vertex[ n01 ].normal );
				double  dot11 = faceNormal.Dot( UpTessellate().vertex[ n11 ].normal );
						
				if( isZero00 == false && dot00 < 0.0 )
					UpTessellate().vertex[ n00 ].normal.NegAssign();
				if( isZero10 == false && dot10 < 0.0 )
					UpTessellate().vertex[ n10 ].normal.NegAssign();
				if( isZero01 == false && dot01 < 0.0 )
					UpTessellate().vertex[ n01 ].normal.NegAssign();
				if( isZero11 == false && dot11 < 0.0 )
					UpTessellate().vertex[ n11 ].normal.NegAssign();
			}
		}
	}

	private final void
	CorrectNormal1() throws lvThrowable
	{
		for( int v=0; v<DownGregoryStd().numDivV; v++ ) {
			for( int u=0; u<DownGregoryStd().numDivU; u++ ) {
				int n00 = ( v * ( DownGregoryStd().numDivU + 1 ) ) + u;
				int n10 = ( v * ( DownGregoryStd().numDivU + 1 ) ) + ( u + 1 );
				int n01 = ( ( v + 1 ) * ( DownGregoryStd().numDivU + 1 ) ) + u;
				int n11 = ( ( v + 1 ) * ( DownGregoryStd().numDivU + 1 ) ) + ( u + 1 );
				
				boolean  isZero00 = UpTessellate().vertex[ n00 ].normal.IsZero();
				boolean  isZero10 = UpTessellate().vertex[ n10 ].normal.IsZero();
				boolean  isZero01 = UpTessellate().vertex[ n01 ].normal.IsZero();
				boolean  isZero11 = UpTessellate().vertex[ n11 ].normal.IsZero();
						
				if( isZero00 == true )
					CorrectNormalMain( n01, n00, n10 );
				if( isZero10 == true )
					CorrectNormalMain( n00, n10, n11 );
				if( isZero11 == true )
					CorrectNormalMain( n10, n11, n01 );
				if( isZero01 == true )
					CorrectNormalMain( n11, n01, n00 );
			}
		}
	}

	private final void
	CorrectNormalMain( int nb, int n, int nf ) throws lvThrowable
	{
		lvVector  vb = UpTessellate().vertex[ nb ].normal;
		lvVector  vf = UpTessellate().vertex[ nf ].normal;
		
		UpTessellate().vertex[ n ].normal.Assign( vb.Add( vf ) );
		double  len = UpTessellate().vertex[ n ].normal.Length();
		if( lvEps.IsZero( len ) == false )
			UpTessellate().vertex[ n ].normal.DivAssign( len );
	}
	
}
