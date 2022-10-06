//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvRec.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel 内で共通に扱う構造体のクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvRec {

	/**
	 * 1個の頂点に対する座標位置と法線ベクトルの内部クラス（lvVecDt用）
	 */
	public static class PosNorLow {
	
		/** 頂点位置		*/
		public lvVecDt	pos			= new lvVecDt();
	
		/** 法線ベクトル	*/
		public lvVecDt	normal		= new lvVecDt();

	// -------------------------------------------------------------------

		/**
		 * コピー関数
		 * @param  src		(( I )) コピー元
		 * @param  dst		(( O )) コピー先
		 */
/*
		public static final void
		Copy( PosNorLow src, PosNorLow dst )
		{
			lvVecDt.Copy( src.pos,    dst.pos );
			lvVecDt.Copy( src.normal, dst.normal );
		}
*/
	}

	/**
	 * 1個の頂点に対する座標位置と法線ベクトルの内部クラス（lvVector用）
	 */
	public static class PosNorHi {
	
		/** 頂点位置		*/
		public lvVector  pos		= null;
	
		/** 法線ベクトル	*/
		public lvVector  normal		= null;

	// -------------------------------------------------------------------

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  PosNorHi( lvGlobal dt )
		{
			pos    = new lvVector( dt );
			normal = new lvVector( dt );
		}

	// -------------------------------------------------------------------

		/**
		 * コピー関数
		 * @param  src		(( I )) コピー元
		 * @param  dst		(( O )) コピー先
		 */
/*
		public static final void
		Copy( PosNorHi src, PosNorHi dst )
		{
			dst.pos.Assign( src.pos );
			dst.normal.Assign( src.normal );
		}
*/

		/**
		 * PosNorHiデータをPosNorLowデータに変換する
		 * @param  src		(( I )) PosNorHiデータ
		 * @param  dst		(( O )) PosNorLowデータ
		 */
		public static final void
		HiToLow( PosNorHi src, PosNorLow dst )
		{
			src.pos.Vector2VecDt( dst.pos );
			src.normal.Vector2VecDt( dst.normal );
		}
	}

	/**
	 * ある線形データ配列に対して一部分を取り出す際の、開始点と個数を記述するクラス
	 */
	public static class SeqPart {
	
		/** 開始点 */
		public int	start;
	
		/** 個数   */
		public int  num;

	// -------------------------------------------------------------------

		/**
		 * コピー関数
		 * @param  src		(( I )) コピー元
		 * @param  dst		(( O )) コピー先
		 */
		public static final void
		Copy( SeqPart src, SeqPart dst )
		{
			dst.start = src.start;
			dst.num   = src.num;
		}
	}

	/**
	 * 1個の三角ポリゴンの頂点No列を記述するクラス
	 */
	public static class TriIndex {
	
		/** 三角ポリゴンの頂点Noの配列（長さ = 3 ）		*/
		public int	vtxNo[/*3*/]	= new int[ 3 ];

	// -------------------------------------------------------------------

		/**
		 * コピー関数
		 * @param  src		(( I )) コピー元
		 * @param  dst		(( O )) コピー先
		 */
		public static final void
		Copy( TriIndex src, TriIndex dst )
		{
			for( int i=0; i<3; i++ )
				dst.vtxNo[ i ] = src.vtxNo[ i ];
		}
	}
	
	/**
	 * 座標系を記述するクラス
	 */
	public static class CoordSys {
	
		/** 原点	*/
		public lvVector  org		= null;
		/** x軸		*/
		public lvVector	 xAxis		= null;
		/** y軸		*/
		public lvVector	 yAxis		= null;
		/** z軸		*/
		public lvVector	 zAxis		= null;

	// -------------------------------------------------------------------

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  CoordSys( lvGlobal dt )
		{
			org   = new lvVector( dt );
			xAxis = new lvVector( dt );
			yAxis = new lvVector( dt );
			zAxis = new lvVector( dt );
		}

	// -------------------------------------------------------------------

		/**
		 * コピー関数
		 * @param  src		(( I )) コピー元
		 * @param  dst		(( O )) コピー先
		 */
/*
		public static final void
		Copy( CoordSys src, CoordSys dst )
		{
			dst.org.Assign( src.org );
			dst.xAxis.Assign( src.xAxis );
			dst.yAxis.Assign( src.yAxis );
			dst.zAxis.Assign( src.zAxis );
		}
*/
	}
	
}
