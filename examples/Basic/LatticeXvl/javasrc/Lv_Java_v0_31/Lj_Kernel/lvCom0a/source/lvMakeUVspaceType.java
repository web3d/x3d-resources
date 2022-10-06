//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVspaceType.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/05/13-)
 * 
 */
public class lvMakeUVspaceType {

	public static class UVpublicVtx {
		
		/** （内部）UV空間No.		*/
		public int     uvSpaceNo;
		
		public lvUVdt  st						= null;
	}
	
	public static class UVpublic {

		/**
		 * 各要素には、そのGS面が属するUV空間（テクスチャ）の数が入る。UV空間が存在しない場合は、	<br>
		 * 0 とする。「UV空間の異なる多重マッピング」が成されている時は、2 以上となる				<br>
		 * （配列長は、lvToKernel.gsNumVtx と等しくする）--- 初期値 null
		 */
		public lvRec.SeqPart  gsNumUV[]			= null;
		
		/**
		 * GS面に対する（内部）UV空間No.の配列。GS面がn個ある場合、 								<br>
		 *     GS面0のUV空間No.列, GS面1のUV空間No.列, ---, GS面( n-1 )のUV空間No.列 				<br>
		 * と続く。配列の長さは、 																	<br>
		 *     gsNumUV[0] + gsNumUV[1] + --- + gsNumUV[n-1] 										<br>
		 * となる。 --- 初期値 null
		 */
		public int            gsUVseq[]			= null;

		/**
		 * 各要素には、「その頂点が属する（内部）UV空間（テクスチャ）」の数が入る。UV空間が			<br>
		 * 存在しない場合は、num = 0 とする。当頂点がUV空間を持っている時は、必ず num >= 1となる。	<br>
		 * 当頂点がテクスチャ境界である時や、「UV空間の異なる多重マッピング」が成されている時は、	<br>
		 * num >= 2 となる（頂点数だけ存在する「3以上」）--- 初期値 null
		 */
		public lvRec.SeqPart  vtxUV[]			= null;
	
		/**
		 * 頂点に対するUV空間情報の配列。頂点がn個ある場合、 										<br>
		 *     頂点0のUV空間情報列, 頂点1のUV空間情報列, ---, 頂点( n-1 )のUV空間情報列 			<br>
		 * と続く。配列の長さは、 																	<br>
		 *     vtxUV[0] + vtxUV[1] + --- + vtxUV[n-1] 												<br>
		 * となる。配列長が 0 の時は、null とする --- 初期値 null
		 */
		public UVpublicVtx   vtxUVseq[]			= null;
		
	}
	
	/** 同一UV空間の曲面集合内の1境界セグメントの中の1ハーフエッジ情報		*/
	public static class HalfInfo {

		/** GS面No				*/
		public int	gsNo;
		
		/** ハーフエッジNo		*/
		public int	halfNo;
	}
	
	/** 同一UV空間の曲面集合内の1境界セグメント情報					*/
	public static class BoundOne {

		/** 1曲線情報（境界セグメント内の曲線の数だけ存在する）		*/
		public HalfInfo  half[]					= null;
	}
	
	/** UV空間で区分けされた曲面集合の境界セグメント情報			*/
	public static class BoundBase {

		/**
		 * 各UV空間における境界セグメントの数と、uvBndSeq[]内の開始点を示す									<br>
		 * （UV空間の数だけ存在する）--- 初期値 null
		 */
		public lvRec.SeqPart  uvNumBnd			= new lvRec.SeqPart();
		
		/** Fixする境界セグメントNO.								*/
		public int            fixBndNo;
	}
	
	/** UV空間で区分けされた曲面集合の境界セグメント情報			*/
	public static class Bound {

		/**
		 * 各UV空間における境界セグメントの数と、uvBndSeq[]内の開始点を示す									<br>
		 * （UV空間の数だけ存在する）--- 初期値 null
		 */
		public BoundBase  base[]				= null;
		
		/**
		 * 同一UV空間の曲面集合内の1境界セグメント情報の配列。UV空間がn個ある場合、 						<br>
		 *     UV空間0のセグメント情報列, UV空間1のセグメント情報列, ---, UV空間( n-1 )セグメント情報列 	<br>
		 * と続く。配列の長さは、 																			<br>
		 *     uvNumBnd[0] + uvNumBnd[1] + --- + uvNumBnd[n-1] 												<br>
		 * となる --- 初期値 null
		 */
		public BoundOne   uvBndSeq[]			= null;
	}

	public static class EdgeOne {
		
		public int     edgeNo;
		
		public int     edgeIdx;
		
		public double  spring;
	}
	
	public static class VtxOne {
		
		public int            vtxNo;
		
		/** UVpublic.vtxUVseq[]に対するオフセット	*/
		public int            uvSpaceOfs;
		
		/** EdgeVtxSet.fixRadial[] または EdgeVtxSet.freeRadial[] 用	*/
		public lvRec.SeqPart  radial		= new lvRec.SeqPart();
	}

	public static class RadialOne {
		
		public boolean        isFixEdge;
		
		public boolean        isFixVtx;
		
		public int            radialNo;
		
		/** EdgeVtxSet.fixEdge[] または EdgeVtxSet.freeEdge[] 用	*/
		public int            edgeIndex;
		
		/** EdgeVtxSet.fixVtx[]  または EdgeVtxSet.freeVtx[]  用	*/
		public int            mateVtxIndex;
	}
	
	public static class EdgeVtxSet {
		
		public EdgeOne    fixEdge[]				= null;
		
		public EdgeOne    freeEdge[]			= null;
		
		public VtxOne     fixVtx[]				= null;
		
		public VtxOne     freeVtx[]				= null;
		
		/** 配列長は、fixVtx[].radial.num の総数			*/
		public RadialOne  fixRadial[]			= null;
		
		/** 配列長は、freeVtx[].radial.num の総数			*/
		public RadialOne  freeRadial[]			= null;
	}

	public static class EdgeVtx {
		
		/**
		 * （UV空間の数だけ存在する）--- 初期値 null
		 */
		public EdgeVtxSet   uvSpace[]			= null;
	}
	
	public static class UVspaceUV {

		/** 頂点に対するUV情報の配列（配列長は、UVpublic.vtxUVseq[]と等しい ）			*/
		public lvUVdt  vtxUVseq[]				= null;
	}
	
	public static class UVspaceST {

		/** 頂点に対するUV情報の配列（配列長は、UVpublic.vtxUVseq[]と等しい ）			*/
		public lvUVdt  vtxSTseq[]				= null;
	}
		
}
