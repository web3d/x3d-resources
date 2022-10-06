//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvPolyDerive.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のシェル内格子からの派生情報クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvPolyDerive {

	/**
	 * 格子(GS面)情報内部クラス
	 */
	public static class PolyCenterLattice {
		
		/** 格子(GS面)の中心位置座標と面法線	*/
		public lvRec.PosNorLow  center	= new lvRec.PosNorLow();
		
		/** 格子(GS面)の面積					*/
		public double           area;
	}
	
	/**
	 * Java Lattice Kernel のシェル内格子の情報用内部クラス
	 * @author	  created by Eishin Matsui (99/08/17-)
	 * 
	 */
	public static class PolyCenter {
		
		/**
		 * 格子(GS面)情報部の配列（GS面数だけ存在する「 1以上 」） --- 初期値 null
		 */
		public PolyCenterLattice  lattice[]		= null;
		
		/**
		 * NG面の中心位置座標の配列（NG面数だけ存在する）		   --- 初期値 null			<br>
		 *		基本丸めベクトルの互換性の為に必要
		 */
		public lvVecDt            centerNG[]	= null;

	// -------------------------------------------------------------------

		public final void
		NewLattice( int num )
		{
			lattice = new PolyCenterLattice[ num ];
			for( int i=0; i<num; i++ )
				lattice[ i ] = new PolyCenterLattice();
		}
		
		public final void
		NewNG( int num )
		{
			if( num == 0 )
				return;
			
			centerNG = new lvVecDt[ num ];
			for( int i=0; i<num; i++ )
				centerNG[ i ] = new lvVecDt();
		}
	}

// -------------------------------------------------------------------

	/**
	 * 頂点情報内部クラス
	 */
	public static class BezgonVertex {
	
		/** 頂点座標			*/
		public lvVecDt  pos					= new lvVecDt();
	}
	
	/**
	 * 稜線情報内部クラス
	 */
	public static class BezgonEdge {
	
		/** ハンドルベクトル（インデックス 0:稜線の始点、 1:稜線の終点）	*/
		public lvVecDt  handVec[/*2*/]		= null;
	
		public  BezgonEdge()
		{
			handVec = new lvVecDt[ 2 ];
			for( int i=0; i<2; i++ )
				handVec[ i ] = new lvVecDt();
		}
	}
	
	/**
	 * 面情報内部クラス
	 */
	public static class BezgonFace {
	
		/** 面法線			*/
		public lvRec.PosNorLow  center		= new lvRec.PosNorLow();
	}
	
	/**
	 * Java Lattice Kernel のシェル内 Bezier格子（内部制御点無）情報内部クラス
	 * @author	  created by Eishin Matsui (99/08/17-)
	 * 
	 */
	public static class Bezgon {

		/**
		 * 頂点情報部の配列（頂点数だけ存在する「 3以上 」） --- 初期値 null
		 */
		public BezgonVertex  vertex[]		= null;
	
		/**
		 * 稜線情報部の配列（稜線数だけ存在する「 3以上 」） --- 初期値 null
		 */
		public BezgonEdge    edge[]			= null;

		/**
		 * GS面情報部の配列（GS面数だけ存在する「 1以上 」） --- 初期値 null
		 */
		public BezgonFace    face[]			= null;

	// -------------------------------------------------------------------

		public final void
		NewVertex( int num )
		{
			vertex = new BezgonVertex[ num ];
			for( int i=0; i<num; i++ )
				vertex[ i ] = new BezgonVertex();
		}
		public final void
		NewEdge( int num )
		{
			edge = new BezgonEdge[ num ];
			for( int i=0; i<num; i++ )
				edge[ i ] = new BezgonEdge();
		}
		public final void
		NewFace( int num )
		{
			face = new BezgonFace[ num ];
			for( int i=0; i<num; i++ )
				face[ i ] = new BezgonFace();
		}
	}
	
}
