//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvUVspace.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel のシェル内 UV空間（テクスチャ用）情報クラス
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvUVspace {

	/**
	 * 「UV空間No.」毎のUV空間情報の内部クラス
	 */
	public static class UVspace {
		
		/**
		 * 「uvSpaceGsSeq[] の開始点と個数（当UV空間No.を持つGS面の数「1以上」）」を示す
		 */
		public lvRec.SeqPart  gs	= new lvRec.SeqPart();
		
		/**
		 * 「uvSpaceVtxSeq[] の開始点と個数（当UV空間No.を持つGS面の数「3以上」）」を示す
		 */
		public lvRec.SeqPart  vtx	= new lvRec.SeqPart();
	}
	
	/**
	 * GS面に対するUV空間情報の内部クラス
	 */
	public static class GsInfo {
	
		/** UV空間No.	*/
		public int     uvSpaceNo;
	
		/** GS面中心のUV値		*/
		public lvUVdt  center	= new lvUVdt();
	}
	
	/**
	 * 頂点に対するUV空間情報の内部クラス
	 */
	public static class VtxInfo {
	
		/** UV空間No.	*/
		public int     uvSpaceNo;
	
		/** UV座標値	*/
		public lvUVdt  uv		= new lvUVdt();
	}

// -------------------------------------------------------------------

	/**
	 * 「UV空間No.」毎のUV空間情報の配列（当シェル内のUV空間の数だけ存在する「0以上」）			<br>
	 * --- 初期値 null
	 */
	public UVspace        uvSpace[]				= null;
	
	/**
	 * GS面に対する「UV空間No.」毎のUV空間情報の配列。「UV空間No.」がn個ある場合、 				<br>
	 *     UV空間No0のGS面No.列, UV空間No1のGS面No.列, ---, UV空間No( n-1 )のGS面No.列 			<br>
	 * と続く。配列の長さは、 																	<br>
	 *     uvSpace[0].gs + uvSpace[1].gs + --- + uvSpace[n-1].gs 								<br>
	 * となる。 --- 初期値 null
	 */
	public int            uvSpaceGsSeq[]		= null;
	
	/**
	 * 頂点に対する「UV空間No.」毎のUV空間情報の配列。「UV空間No.」がn個ある場合、 				<br>
	 *     UV空間No0の頂点No.列, UV空間No1の頂点No.列, ---, UV空間No( n-1 )の頂点No.列 			<br>
	 * と続く。配列の長さは、 																	<br>
	 *     uvSpace[0].vtx + uvSpace[1].vtx + --- + uvSpace[n-1].vtx 							<br>
	 * となる。 --- 初期値 null
	 */
	public int            uvSpaceVtxSeq[]		= null;

	
	/**
	 * 各要素には、そのGS面が属するUV空間（テクスチャ）の数が入る。UV空間が存在しない場合は、	<br>
	 * num = 0 とする。「UV空間の異なる多重マッピング」が成されている時は、num >= 2 となる		<br>
	 * （GS面数だけ存在する「1以上」）--- 初期値 null
	 */
	public lvRec.SeqPart  gsUV[]				= null;
	
	/**
	 * GS面に対するUV空間情報の配列。GS面がn個ある場合、 										<br>
	 *     GS面0のUV空間情報列, GS面1のUV空間情報列, ---, GS面( n-1 )のUV空間情報列 			<br>
	 * と続く。配列の長さは、 																	<br>
	 *     gsInfo[0] + gsInfo[1] + --- + gsInfo[n-1] 											<br>
	 * となる。 --- 初期値 null
	 */
	public GsInfo         gsUVspaceSeq[]		= null;

	
	/**
	 * 各要素には、「その頂点が属するUV空間（テクスチャ）」の数が入る。UV空間が存在しない		<br>
	 * 場合は、num = 0 とする。当頂点がUV空間を持っている時は、必ず num >= 1となる				<br>
	 *（未定UV値の補間済）。																	<br>
	 * 当頂点がテクスチャ境界である時や、「UV空間の異なる多重マッピング」が成されている時は、	<br>
	 * num >= 2 となる（頂点数だけ存在する「3以上」）--- 初期値 null
	 */
	public lvRec.SeqPart  vtxUV[]				= null;
	
	/**
	 * 頂点に対するUV空間情報の配列。頂点がn個ある場合、 										<br>
	 *     頂点0のUV空間情報列, 頂点1のUV空間情報列, ---, 頂点( n-1 )のUV空間情報列 			<br>
	 * と続く。配列の長さは、 																	<br>
	 *     vtxUV[0] + vtxUV[1] + --- + vtxUV[n-1] 												<br>
	 * となる。配列長が 0 の時は、null とする --- 初期値 null
	 */
	public VtxInfo        vtxUVinfoSeq[]		= null;
	
	
// -------------------------------------------------------------------

/*
	public final void
	NewUvSpace( int num )
	{
		uvSpace = new UVspace[ num ];
		for( int i=0; i<num; i++ )
			uvSpace[ i ] = new UVspace();
	}
	public final void
	NewUvSpaceGsSeq( int num )
	{
		uvSpaceGsSeq = new int[ num ];
	}
	public final void
	NewUvSpaceVtxSeq( int num )
	{
		uvSpaceVtxSeq = new int[ num ];
	}
	public final void
	NewGsUV( int num )
	{
		gsUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			gsUV[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewGsUVspaceSeq( int num )
	{
		gsUVspaceSeq = new GsInfo[ num ];
		for( int i=0; i<num; i++ )
			gsUVspaceSeq[ i ] = new GsInfo();
	}
	public final void
	NewVtxUV( int num )
	{
		vtxUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			vtxUV[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewVtxUVinfoSeq( int num )
	{
		vtxUVinfoSeq = new VtxInfo[ num ];
		for( int i=0; i<num; i++ )
			vtxUVinfoSeq[ i ] = new VtxInfo();
	}
*/

}
