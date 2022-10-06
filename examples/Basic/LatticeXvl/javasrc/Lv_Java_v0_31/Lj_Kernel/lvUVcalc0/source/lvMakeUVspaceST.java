//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVspaceST.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/04/09-)
 * 
 */
public class lvMakeUVspaceST extends lvRoot {

	private static class SparMat {
		
		private int                    numSparMat;
		
		private lvSparseMatrix.SetElm  setElm[]		= null;

		private lvUVdt                 uv[]			= null;
		private lvUVdt                 st[]			= null;
		
		private double                 cu[]			= null;
		private double                 cv[]			= null;
		private double                 au[]			= null;
		private double                 av[]			= null;
		
		private double                 ma[]			= new double[ 6 ];
		
		private double                 ax[]			= new double[ 3 ];
		private double                 ay[]			= new double[ 3 ];
		private double                 bx[]			= new double[ 3 ];
		private double                 by[]			= new double[ 3 ];
		private double                 cm[]			= new double[ 9 ];
		private double                 px[]			= new double[ 9 ];
		
		private double                 r0;
	}

// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo						*/
		private int                          curShellNo;

		/** 入力データ								*/
		private lvMakeUVspaceType.UVpublic   srcUVpublic		= null;

		/** 入力データ								*/
		private lvMakeUVspaceType.EdgeVtx    srcEdgeVtx			= null;

		/** 入力データ								*/
		private lvMakeUVspaceType.UVspaceUV  srcUVspaceUV		= null;

		/** 出力データ								*/
		private lvMakeUVspaceType.UVspaceST  dstUVspaceST		= null;
		
		private lvSparseMatrix               sparseMatrix		= null;
		
		private lvGauss                      gauss				= null;

		private SparMat                      sparMat			= new SparMat();

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Global( lvGlobal dt )
		{
			sparseMatrix = new lvSparseMatrix( dt );
			gauss        = new lvGauss( dt );
		}
	}


	/** 当クラス用のグローバルデータ						*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gMakeUVspaceST;
	}
	/** lvMakeUVspaceType.UVpublicデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVpublic
	SrcUVpublic()
	{
		return  Gbl().srcUVpublic;
	}
	/** lvMakeUVspaceType.Edgeデータ用クラスオブジェクト		*/
	private final lvMakeUVspaceType.EdgeVtx
	SrcEdgeVtx()
	{
		return  Gbl().srcEdgeVtx;
	}
	/** lvMakeUVspaceType.UVspaceUVデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVspaceUV
	SrcUVspaceUV()
	{
		return  Gbl().srcUVspaceUV;
	}
	/** lvMakeUVspaceType.UVspaceSTデータ用クラスオブジェクト	*/
	private final lvMakeUVspaceType.UVspaceST
	DstUVspaceST()
	{
		return  Gbl().dstUVspaceST;
	}
	/** lvPolygonデータ用クラスオブジェクト					*/
	private final lvPolygon
	Polygon()
	{
		return ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** lvUVpublicデータ用クラスオブジェクト				*/
	private final lvUVpublic
	ShellUVpublic()
	{
		return  ( ( lvComAGblElm )global.GComA() ).gModelAtt.shell[ Gbl().curShellNo ].uvPublic;
	}
	/** lvUVpublicデータ用クラスオブジェクト				*/
	private final SparMat
	SparMat()
	{
		return  Gbl().sparMat;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lvMakeUVspaceST( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------
	
	public lvMakeUVspaceType.UVspaceST
	Exec( int shellNo, lvMakeUVspaceType.UVpublic srcUVpublic, lvMakeUVspaceType.EdgeVtx srcEdgeVtx,
			lvMakeUVspaceType.UVspaceUV srcUVspaceUV ) throws lvThrowable
	{
		if( srcUVpublic == null )
			return null;
			
		Gbl().curShellNo    = shellNo;
		Gbl().srcUVpublic   = srcUVpublic;
		Gbl().srcEdgeVtx    = srcEdgeVtx;
		Gbl().srcUVspaceUV  = srcUVspaceUV;
		Gbl().dstUVspaceST  = new lvMakeUVspaceType.UVspaceST();
		
		NewUVspaceST();
		SparMatProc();
		
		Finish();
		
		return Gbl().dstUVspaceST;
	}
	
	private final void
	NewUVspaceST()
	{
		int  num = SrcUVpublic().vtxUVseq.length;
		DstUVspaceST().vtxSTseq = new lvUVdt[ num ];
		for( int i=0; i<num; i++ )
			DstUVspaceST().vtxSTseq[ i ] = new lvUVdt();
	}
	
	private final void
	SparMatProc() throws lvThrowable
	{
		int  numUVspace = ShellUVpublic().uvSpace.length;
		for( int i=0; i<numUVspace; i++ ) {
			int  numSparMat = NumSparMat( i );
			NewSparMat( numSparMat );
			
			SetUVST( i );
			InitMa();
			
			SparMat().r0 = SumFace( i );
			GetAffinMatrix();
			GetRBFtrans( i );
			
			SetST( i );
		}
	}
	
	private final int
	NumSparMat( int uvSpaceNo )
	{
		int cnt = 0;
		
		int  numFixVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		for( int i=0; i<numFixVtx; i++ ) {
			lvMakeUVspaceType.VtxOne  fixVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ];
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ fixVtx.vtxNo ];
			lvUVdt  st = SrcUVpublic().vtxUVseq[ vtxUV.start + fixVtx.uvSpaceOfs ].st;
			if( st != null )
				cnt++;
		}
		
		int  numFreeVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;
		for( int i=0; i<numFreeVtx; i++ ) {
			lvMakeUVspaceType.VtxOne  freeVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ];
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ freeVtx.vtxNo ];
			lvUVdt  st = SrcUVpublic().vtxUVseq[ vtxUV.start + freeVtx.uvSpaceOfs ].st;
			if( st != null )
				cnt++;
		}
		
		return cnt;
	}
	
	private final void
	NewSparMat( int numSparMat )
	{
		SparMat().numSparMat = numSparMat;
		
		SparMat().setElm = new lvSparseMatrix.SetElm[ numSparMat*numSparMat ];
		for( int i=0; i<( numSparMat*numSparMat ); i++ )
			SparMat().setElm[ i ] = new lvSparseMatrix.SetElm();
			
		SparMat().uv = new lvUVdt[ numSparMat ];
		SparMat().st = new lvUVdt[ numSparMat ];
		for( int i=0; i<numSparMat; i++ ) {
			SparMat().uv[ i ] = new lvUVdt();
			SparMat().st[ i ] = new lvUVdt();
		}
		
		SparMat().cu = new double[ numSparMat ];
		SparMat().cv = new double[ numSparMat ];
		SparMat().au = new double[ numSparMat ];
		SparMat().av = new double[ numSparMat ];
	}
	
	private final void
	SetUVST( int uvSpaceNo )
	{
		int cnt = 0;
		
		int  numFixVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		for( int i=0; i<numFixVtx; i++ ) {
			lvMakeUVspaceType.VtxOne  fixVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ];
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ fixVtx.vtxNo ];
			lvUVdt  st = SrcUVpublic().vtxUVseq[ vtxUV.start + fixVtx.uvSpaceOfs ].st;
			if( st != null ) {
				lvUVdt  uv = SrcUVspaceUV().vtxUVseq[ vtxUV.start + fixVtx.uvSpaceOfs ];
				lvUVdt.Copy( uv, SparMat().uv[ cnt ] );
				lvUVdt.Copy( st, SparMat().st[ cnt ] );
				cnt++;
			}
		}
		
		int  numFreeVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;
		for( int i=0; i<numFreeVtx; i++ ) {
			lvMakeUVspaceType.VtxOne  freeVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ];
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ freeVtx.vtxNo ];
			lvUVdt  st = SrcUVpublic().vtxUVseq[ vtxUV.start + freeVtx.uvSpaceOfs ].st;
			if( st != null ) {
				lvUVdt  uv = SrcUVspaceUV().vtxUVseq[ vtxUV.start + freeVtx.uvSpaceOfs ];
				lvUVdt.Copy( uv, SparMat().uv[ cnt ] );
				lvUVdt.Copy( st, SparMat().st[ cnt ] );
				cnt++;
			}
		}
	}
	
	private final void
	InitMa()
	{
		SparMat().ma[ 0 ] = 1.0;
		SparMat().ma[ 1 ] = 0.0;
		SparMat().ma[ 2 ] = 0.0;
		SparMat().ma[ 3 ] = 0.0;
		SparMat().ma[ 4 ] = 1.0;
		SparMat().ma[ 5 ] = 0.0;
	}
	
	private final void
	GetAffinMatrix()
	{
		for( int i=0; i<3; i++ ) {
			SparMat().ax[ i ] = 0.0;
			SparMat().ay[ i ] = 0.0;
			SparMat().bx[ i ] = 0.0;
			SparMat().by[ i ] = 0.0;
		}
		
		double  xSum  = 0.0;
		double  ySum  = 0.0;
		double  xxSum = 0.0;
		double  yySum = 0.0;
		double  xySum = 0.0;
		
		for( int i=0; i<SparMat().numSparMat; i++ ) {
			xSum  += SparMat().uv[ i ].u;
			ySum  += SparMat().uv[ i ].v;
			xxSum += SparMat().uv[ i ].u * SparMat().uv[ i ].u;
			yySum += SparMat().uv[ i ].v * SparMat().uv[ i ].v;
			xySum += SparMat().uv[ i ].u * SparMat().uv[ i ].v;
		}
		
		for( int i=0; i<SparMat().numSparMat; i++ ) {
			SparMat().bx[ 0 ] += SparMat().st[ i ].u * SparMat().uv[ i ].u;
			SparMat().bx[ 1 ] += SparMat().st[ i ].u * SparMat().uv[ i ].v;
			SparMat().bx[ 2 ] += SparMat().st[ i ].u;
			SparMat().by[ 0 ] += SparMat().st[ i ].v * SparMat().uv[ i ].u;
			SparMat().by[ 1 ] += SparMat().st[ i ].v * SparMat().uv[ i ].v;
			SparMat().by[ 2 ] += SparMat().st[ i ].v;
		}
		
		SparMat().cm[ 0 ] = xxSum;
		SparMat().cm[ 1 ] = xySum;
		SparMat().cm[ 2 ] =  xSum;
		SparMat().cm[ 3 ] = xySum;
		SparMat().cm[ 4 ] = yySum;
		SparMat().cm[ 5 ] =  ySum;
		SparMat().cm[ 6 ] =  xSum;
		SparMat().cm[ 7 ] =  ySum;
		SparMat().cm[ 8 ] = SparMat().numSparMat;

		boolean  result = Gbl().gauss.Exec( SparMat().cm, SparMat().px, 3 );
		if( result == lvConst.LV_FAILURE )
			return;
			
		for( int i=0; i<3; i++ ) {
			for( int j=0; j<3; j++ ) {
				double  cf_ij = SparMat().px[ 3*i + j ];
				SparMat().ax[ i ] += cf_ij * SparMat().bx[ j ];
				SparMat().ay[ i ] += cf_ij * SparMat().by[ j ];
			}
		}
		
		SparMat().ma[ 0 ] = SparMat().ax[ 0 ];
		SparMat().ma[ 1 ] = SparMat().ax[ 1 ];
		SparMat().ma[ 2 ] = SparMat().ax[ 2 ];
		SparMat().ma[ 3 ] = SparMat().ay[ 0 ];
		SparMat().ma[ 4 ] = SparMat().ay[ 1 ];
		SparMat().ma[ 5 ] = SparMat().ay[ 2 ];
	}
	
	private final void
	GetRBFtrans( int uvSpaceNo ) throws lvThrowable
	{
		if( SparMat().numSparMat == 0 )
			return;
		
		for( int i=0; i<SparMat().numSparMat; i++ ) {
			SparMat().au[ i ] = 1.0;
			SparMat().av[ i ] = 1.0;
		}
		for( int i=0; i<SparMat().numSparMat; i++ ) {
			SparMat().cu[ i ]  =   SparMat().st[ i ].u;
			SparMat().cv[ i ]  =   SparMat().st[ i ].v;
			SparMat().cu[ i ] -= ( SparMat().ma[ 0 ] * SparMat().uv[ i ].u
								 + SparMat().ma[ 1 ] * SparMat().uv[ i ].v
								 + SparMat().ma[ 2 ] );
			SparMat().cv[ i ] -= ( SparMat().ma[ 3 ] * SparMat().uv[ i ].u
								 + SparMat().ma[ 4 ] * SparMat().uv[ i ].v
								 + SparMat().ma[ 5 ] );
		}
		
		double  g0  = 0.0;
		int     cnt = 0;
		for( int i=0; i<SparMat().numSparMat; i++ ) {
			for( int j=0; j<SparMat().numSparMat; j++ ) {
				double  r_ij =   ( SparMat().uv[ i ].u - SparMat().uv[ j ].u )
							   * ( SparMat().uv[ i ].u - SparMat().uv[ j ].u )
							   + ( SparMat().uv[ i ].v - SparMat().uv[ j ].v )
							   * ( SparMat().uv[ i ].v - SparMat().uv[ j ].v );
				r_ij = Math.sqrt( r_ij );
				double  g = RadialFunc( r_ij, SparMat().r0 );
				if( i == 0 && j == 0 )
					g0 = g;
				
				SparMat().setElm[ cnt ].row    = i;
				SparMat().setElm[ cnt ].column = j;
				SparMat().setElm[ cnt ].data   = g;
				cnt++;
			}
		}
		Gbl().sparseMatrix.Set( SparMat().numSparMat, SparMat().setElm );
		Gbl().sparseMatrix.PreConjGrad();
		
		if( SparMat().numSparMat > 1 ) {
			Gbl().sparseMatrix.ConjGrad( SparMat().cu, SparMat().au );
			Gbl().sparseMatrix.ConjGrad( SparMat().cv, SparMat().av );
		}
		else {
			SparMat().au[ 0 ] = SparMat().cu[ 0 ] / g0;
			SparMat().av[ 0 ] = SparMat().cv[ 0 ] / g0;
		}
	}
	
	private final int
	SumFace( int uvSpaceNo )
	{
		int  numGs = SrcUVpublic().gsNumUV.length;
		
		int  cnt = 0;
		for( int i=0; i<numGs; i++ ) {
			lvRec.SeqPart  gsNumUV = SrcUVpublic().gsNumUV[ i ];
			for( int j=0; j<gsNumUV.num; j++ ) {
				int  uvSpaceNoGs = SrcUVpublic().gsUVseq[ gsNumUV.start + j ];
				if( uvSpaceNo == uvSpaceNoGs ) {
					cnt++;
					break;
				}
			}
		}
		
		return cnt;
	}
	
	private final double
	RadialFunc( double r, double r0 )
	{
		return  Math.exp( -r * r * ( 0.5 * r0 * r0 ) );
	}
	
	private final void
	SetST( int uvSpaceNo )
	{
		int  numFixVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx.length;
		for( int i=0; i<numFixVtx; i++ ) {
			lvMakeUVspaceType.VtxOne  fixVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].fixVtx[ i ];
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ fixVtx.vtxNo ];
			
			SetSTmain( vtxUV.start + fixVtx.uvSpaceOfs );
		}
		
		int  numFreeVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx.length;
		for( int i=0; i<numFreeVtx; i++ ) {
			lvMakeUVspaceType.VtxOne  freeVtx = SrcEdgeVtx().uvSpace[ uvSpaceNo ].freeVtx[ i ];
			lvRec.SeqPart  vtxUV = SrcUVpublic().vtxUV[ freeVtx.vtxNo ];
			
			SetSTmain( vtxUV.start + freeVtx.uvSpaceOfs );
		}
	}
	
	private final void
	SetSTmain( int no )
	{
		double  yu, yv;
		
		lvUVdt  uv = SrcUVspaceUV().vtxUVseq[ no ];
		
		if( SparMat().numSparMat == 0 ) {
			yu = uv.u;
			yv = uv.v;
		}
		else {
			yu = SparMat().ma[ 0 ] * uv.u + SparMat().ma[ 1 ] * uv.v + SparMat().ma[ 2 ];
			yv = SparMat().ma[ 3 ] * uv.u + SparMat().ma[ 4 ] * uv.v + SparMat().ma[ 5 ];
			
			for( int i=0; i<SparMat().numSparMat; i++ ) {
				double  r2 =   ( uv.u - SparMat().uv[ i ].u ) * ( uv.u - SparMat().uv[ i ].u )
							 + ( uv.v - SparMat().uv[ i ].v ) * ( uv.v - SparMat().uv[ i ].v );
				double  g  = RadialFunc( Math.sqrt( r2 ), SparMat().r0 );
				yu += SparMat().au[ i ] * g;
				yv += SparMat().av[ i ] * g;
			}
		}
		
		lvUVdt.SetUV( yu, yv, DstUVspaceST().vtxSTseq[ no ] );
	}
	
	private final void
	Finish()
	{
		Gbl().srcUVpublic  = null;
		Gbl().srcEdgeVtx   = null;
		Gbl().srcUVspaceUV = null;
	}
	
}
