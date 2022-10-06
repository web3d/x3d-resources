//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0TessellateUV.java		（実装版）
//

package jp.co.lattice.vKernel.tex.a0g;

import	jp.co.lattice.vKernel.tex.a0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * 1個の面を作成するクラス
 * @author	  created by Eishin Matsui (00/04/02-)
 * 
 */
public class lv0TessellateUV extends lvTessellateUV {
	
	private static final int  maxNumVertex		= 256;
	private static final int  maxNumUVspaceOfs	= 256;
		
// -------------------------------------------------------------------

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		lvDivFaceType.DownDivFace		 downDivFace		= null;
		lvDivFaceType.DeriveDivFace      deriveDivFace		= null;
		lvDivFaceType.UpDivFace          upDivFace			= null;
		lvDivFaceUVtype.DownDivFaceUV    downDivFaceUV		= null;
		lvDivFaceUVtype.UpDivFaceUV      upDivFaceUV		= null;
		lvTessellateUV.DownTessellateUV  downTessUV			= null;
		lvTessellateUV.UpTessellateUV    upTessUV			= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			GlobalTmp( dt );
			GlobalStatic();
		}
		
		/** ローカル変数用の new 代用バッファエリア		*/
		private lvUVdt  tuSetGtQuadInner[]		= null;
		private lvUVdt  tuSetGtQuadOuterLast[]	= null;
		/**
		 * ローカル変数用の new 代用バッファ初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tuSetGtQuadInner     = new lvUVdt[ 4 ];		for( int i=0; i<4; i++ )	tuSetGtQuadInner[ i ]     = new lvUVdt();
			tuSetGtQuadOuterLast = new lvUVdt[ 4 ];		for( int i=0; i<4; i++ )	tuSetGtQuadOuterLast[ i ] = new lvUVdt();
		}

		/** 小規模な tessellate.uvSpace 用のグローバルデータ		*/
		private DownTessellateUVone  staticDownUVspace[]	= null;
		
		/** 小規模な tessellate.uvSpace 用のグローバルデータ		*/
		private UpTessellateUVone    staticUpUVspace[]		= null;
		
		/** 小規模な tessellate.uvSpace[ i ].uv 用のグローバルデータ		*/
		private lvUVdt               staticUV[]				= new lvUVdt[ maxNumVertex ];
		
		/**
		 * 小規模な配列用のグローバルデータの初期化（コンストラクタで使用）
		 * @param  dt		(( I )) グローバルデータ
		 */
		private final void
		GlobalStatic()
		{
			staticDownUVspace = new DownTessellateUVone[ maxNumUVspaceOfs ];
			for( int i=0; i<maxNumUVspaceOfs; i++ )
				staticDownUVspace[ i ] = new DownTessellateUVone();
				
			staticUpUVspace      = new UpTessellateUVone[ 1 ];
			staticUpUVspace[ 0 ] = new UpTessellateUVone();
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0AttGGblElm )global.GAttG() ).gTessellateUV;
	}
	
	private final lvDivFaceType.DownDivFace
	DownDivFace()
	{
		return  Gbl().downDivFace;
	}
	private final lvDivFaceType.DeriveDivFace
	DeriveDivFace()
	{
		return  Gbl().deriveDivFace;
	}
	private final lvDivFaceType.UpDivFace
	UpDivFace()
	{
		return  Gbl().upDivFace;
	}
	private final lvDivFaceUVtype.DownDivFaceUV
	DownDivFaceUV()
	{
		return  Gbl().downDivFaceUV;
	}
	private final lvDivFaceUVtype.UpDivFaceUV
	UpDivFaceUV()
	{
		return  Gbl().upDivFaceUV;
	}
	private final lvTessellateUV.DownTessellateUV
	DownTessUV()
	{
		return  Gbl().downTessUV;
	}
	private final lvTessellateUV.UpTessellateUV
	UpTessUV()
	{
		return  Gbl().upTessUV;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lv0TessellateUV( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	public void
	Init( lvDivFaceType.DownDivFace	      downDivFace,
		  lvDivFaceType.DeriveDivFace     deriveDivFace,
		  lvDivFaceType.UpDivFace         upDivFace,
		  lvTessellateUV.DownTessellateUV downTessellate,
		  lvTessellateUV.UpTessellateUV   upTessellate )
	{
		Gbl().downDivFace	= downDivFace;
		Gbl().deriveDivFace = deriveDivFace;
		Gbl().upDivFace		= upDivFace;
		Gbl().downDivFaceUV = downDivFace.divFaceUV;
		Gbl().upDivFaceUV   = upDivFace.divFaceUV;
		Gbl().downTessUV    = downTessellate;
		Gbl().upTessUV	    = upTessellate;
	}
	
	public void
	NewUpDivFaceUV( int numVertex )
	{
		UpDivFaceUV().uvSpace = null;
		if( DownDivFaceUV().uvSpace == null )
			return;
			
		int  num = DownDivFaceUV().uvSpace.length;
		
		UpDivFaceUV().uvSpace = new lvDivFaceUVtype.UpDivFaceUVone[ num ];
		for( int i=0; i<num; i++ ) {
			UpDivFaceUV().uvSpace[ i ] = new lvDivFaceUVtype.UpDivFaceUVone();
			
			UpDivFaceUV().uvSpace[ i ].uv = new lvUVdt[ numVertex ];
			for( int j=0; j<numVertex; j++ )
				UpDivFaceUV().uvSpace[ i ].uv[ j ] = new lvUVdt();
		}
	}
	
	public void
	NewUpTessellateUV( lvRec.SeqPart infoVertex )
	{
		UpTessUV().uvSpace = null;
		if( UpDivFaceUV().uvSpace == null )
			return;

		if( UpDivFaceUV().uvSpace.length > 1 ) {
			UpTessUV().uvSpace = new UpTessellateUVone[ UpDivFaceUV().uvSpace.length ];
			for( int i=0; i<UpDivFaceUV().uvSpace.length; i++ )
				UpTessUV().uvSpace[ i ] = new UpTessellateUVone();
		}
		else
			UpTessUV().uvSpace = Gbl().staticUpUVspace;
		
		for( int i=0; i<UpDivFaceUV().uvSpace.length; i++ ) {
			if( i >= 1 )
				UpTessUV().uvSpace[ i ].uv = new lvUVdt[ infoVertex.num ];
			else if( infoVertex.num > maxNumVertex )
				UpTessUV().uvSpace[ i ].uv = new lvUVdt[ infoVertex.num ];
			else
				UpTessUV().uvSpace[ i ].uv = Gbl().staticUV;
			
			for( int j=0; j<infoVertex.num; j++ )
				UpTessUV().uvSpace[ i ].uv[ j ] = UpDivFaceUV().uvSpace[ i ].uv[ infoVertex.start + j ];
		}
	}

	public void
	SetQuad()
	{
		DownTessUV().uvSpace = null;
		if( DownDivFaceUV().uvSpace == null )
			return;
		
		NewDownTessUV();
		
		for( int i=0; i<DownDivFaceUV().uvSpace.length; i++ ) {
			for( int j=0; j<4; j++ )
				lvUVdt.Copy( DownDivFaceUV().uvSpace[ i ].vtxUV[ j ], DownTessUV().uvSpace[ i ].uv[ j ] );
		}
	}
	
	private final void
	NewDownTessUV()
	{
		if( DownDivFaceUV().uvSpace == null ) {
			DownTessUV().numUVspace = 0;
			return;
		}
		
		DownTessUV().numUVspace = DownDivFaceUV().uvSpace.length;
			
		if( DownDivFaceUV().uvSpace.length > maxNumUVspaceOfs ) {
			DownTessUV().uvSpace = new DownTessellateUVone[ DownDivFaceUV().uvSpace.length ];
			for( int i=0; i<maxNumUVspaceOfs; i++ )
				DownTessUV().uvSpace[ i ] = Gbl().staticDownUVspace[ i ];
			for( int i=maxNumUVspaceOfs; i<DownDivFaceUV().uvSpace.length; i++ )
				DownTessUV().uvSpace[ i ] = new DownTessellateUVone();
		}
		else
			DownTessUV().uvSpace = Gbl().staticDownUVspace;
	}
	
	public void
	SetGtQuad( int patchNo, int numPatch ) throws lvThrowable
	{
		DownTessUV().uvSpace = null;
		if( DownDivFaceUV().uvSpace == null )
			return;
			
		NewDownTessUV();
		
		for( int i=0; i<DownDivFaceUV().uvSpace.length; i++ )
			SetGtQuadStd( i, patchNo, numPatch );
	}

	private final void
	SetGtQuadStd( int uvSpaceOfs, int halfNo, int numHalf ) throws lvThrowable
	{
		int  halfF = ( halfNo + 1 ) % numHalf;
		
		SetGtQuadInner( uvSpaceOfs, halfNo, DownTessUV().uvSpace[ uvSpaceOfs ].uv[ 0 ], true  );
		SetGtQuadOuter( uvSpaceOfs, halfNo, DownTessUV().uvSpace[ uvSpaceOfs ].uv[ 1 ], true  );
		SetGtQuadOuter( uvSpaceOfs, halfF,  DownTessUV().uvSpace[ uvSpaceOfs ].uv[ 2 ], false );
		SetGtQuadInner( uvSpaceOfs, halfF,  DownTessUV().uvSpace[ uvSpaceOfs ].uv[ 3 ], false );
	}
	
	private final void
	SetGtQuadInner( int uvSpaceOfs, int halfNo, lvUVdt downTessUV, boolean reverse )
	{
		int halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		
		lvUVdt  uv0 = Gbl().tuSetGtQuadInner[ 0 ];		// uv0 = new lvUVdt();
		lvUVdt  uv1 = Gbl().tuSetGtQuadInner[ 1 ];		// uv1 = new lvUVdt();
		
		if( reverse == false ) {
			lvUVdt.Copy( DownDivFaceUV().uvSpace[ uvSpaceOfs ].vtxUV[ halfNo ], uv0 );
			lvUVdt.Copy( DownDivFaceUV().uvSpace[ uvSpaceOfs ].vtxUV[ halfF  ], uv1 );
			downTessUV.u = ( uv0.u + uv1.u ) / 2.0;
			downTessUV.v = ( uv0.v + uv1.v ) / 2.0;
		}
		else
			lvUVdt.Copy( DownDivFaceUV().uvSpace[ uvSpaceOfs ].center, downTessUV );
	}
	
	private final void
	SetGtQuadOuter( int uvSpaceOfs, int halfNo, lvUVdt downTessUV, boolean halfEdgeLast ) throws lvThrowable
	{
		if( halfEdgeLast == false )
			SetGtQuadOuterFirst( uvSpaceOfs, halfNo, downTessUV );
		else
			SetGtQuadOuterLast( uvSpaceOfs, halfNo, downTessUV );
	}
	
	private final void
	SetGtQuadOuterFirst( int uvSpaceOfs, int halfNo, lvUVdt downTessUV )
	{
		lvUVdt.Copy( DownDivFaceUV().uvSpace[ uvSpaceOfs ].vtxUV[ halfNo ], downTessUV );
	}
	
	private final void
	SetGtQuadOuterLast( int uvSpaceOfs, int halfNo, lvUVdt downTessUV )
	{
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		
		lvUVdt  uv0 = Gbl().tuSetGtQuadOuterLast[ 0 ];		// uv0 = new lvUVdt();
		lvUVdt  uv1 = Gbl().tuSetGtQuadOuterLast[ 1 ];		// uv1 = new lvUVdt();
		
		lvUVdt.Copy( DownDivFaceUV().uvSpace[ uvSpaceOfs ].vtxUV[ halfNo ], uv0 );
		lvUVdt.Copy( DownDivFaceUV().uvSpace[ uvSpaceOfs ].vtxUV[ halfF  ], uv1 );
		downTessUV.u = ( uv0.u + uv1.u ) / 2.0;
		downTessUV.v = ( uv0.v + uv1.v ) / 2.0;
	}
	
	public void
	Finish()
	{
		UpTessUV().uvSpace = null;
	}

}
