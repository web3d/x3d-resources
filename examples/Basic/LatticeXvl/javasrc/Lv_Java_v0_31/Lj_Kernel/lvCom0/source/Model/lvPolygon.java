//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvPolygon.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel のシェル内格子情報（幾何＋位相）クラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvPolygon {

	/**
	 * 面NoとハーフエッジNoの情報内部クラス（稜線情報内部クラス、vtxFaceSeq[]用）
	 */
	public static class InfoFaceHalf {
		
		/** 面No				*/
		public int	faceNo;
		
		/** ハーフエッジNo		*/
		public int	halfNo;
	}

	/**
	 * 稜線情報内部クラス
	 */
	public static class Edge {
	
		/** 全体丸め係数				*/
		public double		 allRound;
	
		/**
		 * lvToKernel.Attr.type == lvConst.LV_SS_LATTICE の時、
		 *		基本丸めベクトル
		 * lvToKernel.Attr.type == lvConst.LV_SS_GREGORY の時、
		 *		稜線上頂点微分ベクトル
		 */
		public lvVecDt	     vec[/*2*/]			= null;
	
		/** 相対する面（面NoとハーフエッジNo）の情報	*/
		public InfoFaceHalf  face[/*2*/]		= null;
		
		public  Edge()
		{
			vec = new lvVecDt[ 2 ];
			for( int i=0; i<2; i++ )
				vec[ i ] = new lvVecDt();
			
			face  = new InfoFaceHalf[ 2 ];
			for( int i=0; i<2; i++ )
				face[ i ] = new InfoFaceHalf();
		}
	}

	/**
	 * 頂点情報内部クラス
	 */
	public static class Vertex {
	
		/** 頂点座標			*/
		public lvVecDt		  pos		= new lvVecDt();
	
		/** 丸め係数			*/
		public double		  round;
	
		/** vtxFaceSeq[] に対する、開始点と個数（radial数）を示す	*/
		public lvRec.SeqPart  vtxFace	= new lvRec.SeqPart();
	}
	
	/**
	 * 面のハーフエッジ情報を羅列した線形データ配列用の内部クラス
	 */
	public static class FaceHalf {
		
		/** 対応頂点No				*/
		public int	 vtxNo;
		
		/** 対応頂点上の radialNo	*/
		public int	 radialNo;
		
		/** 対応稜線No				*/
		public int	 edgeNo;
		
		/** 当ハーフエッジと対応稜線の方向に基づくインデックス（ 同方向 : 0, 反方向 : 1 ）	*/
		public byte  edgeIdx;
	}
	
// -------------------------------------------------------------------

	/**
	 * 頂点情報部の配列（頂点数だけ存在する「 3以上 」） --- 初期値 null
	 */
	public Vertex		  vertex[]			= null;
	/**
	 * 頂点がn個ある場合、													<br>
	 * 頂点0のradialNo列, 頂点1のradialNo列, ---, 頂点( n-1 )のradialNo列	<br>
	 * と続く。配列の長さは、												<br>
	 * 頂点0のradial数 + 頂点1のradial数 + --- + 頂点( n-1 )のradial数		<br>
	 * となる。 --- 初期値 null
	 */
	public InfoFaceHalf   vtxFaceSeq[]		= null;
	
	/**
	 * 稜線情報部の配列（稜線数だけ存在する「 3以上 」） --- 初期値 null
	 */
	public Edge		      edge[]			= null;
	
	/**
	 * face[]は前半:GS面、後半:NG面と分かれている。そのNG面の開始位置 --- 初期値 0
	 */
	public int			  ngStartNo			= 0;
	/**
	 * 面情報部の配列（面数だけ存在する「 1以上 」） --- 初期値 null	<br>
	 * faceHalfSeq[] に対する、開始点と個数（ハーフエッジ数＝頂点数＝稜線数）を示す
	 */
	public lvRec.SeqPart  face[]			= null;
	/**
	 * 面がn個ある場合、																<br>
	 * 面0のハーフエッジNo列, 面1のハーフエッジNo列, ---, 面( n-1 )のハーフエッジNo列	<br>
	 * と続く。配列の長さは、															<br>
	 * 面0のハーフエッジ数 + 面1のハーフエッジ数 + --- + 面( n-1 )のハーフエッジ数		<br>
	 * となる。 --- 初期値 null
	 */
	public FaceHalf	      faceHalfSeq[]		= null;

// -------------------------------------------------------------------

	public final void
	NewVertex( int num )
	{
		vertex = new Vertex[ num ];
		for( int i=0; i<num; i++ )
			vertex[ i ] = new Vertex();
	}
	public final void
	NewVtxFaceSeq( int num )
	{
		vtxFaceSeq = new InfoFaceHalf[ num ];
		for( int i=0; i<num; i++ )
			vtxFaceSeq[ i ] = new InfoFaceHalf();
	}
	public final void
	NewEdge( int num )
	{
		edge = new Edge[ num ];
		for( int i=0; i<num; i++ )
			edge[ i ] = new Edge();
	}
	public final void
	NewFace( int num )
	{
		face = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			face[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewFaceHalfSeq( int num )
	{
		faceHalfSeq = new FaceHalf[ num ];
		for( int i=0; i<num; i++ )
			faceHalfSeq[ i ] = new FaceHalf();
	}
}
