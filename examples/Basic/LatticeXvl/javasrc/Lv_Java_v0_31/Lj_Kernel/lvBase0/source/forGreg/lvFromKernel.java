//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvFromKernel.java
//

package jp.co.lattice.vKernel.core.b0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.core.t0.lvFromKernelLow;
import	jp.co.lattice.vKernel.core.c0.lvShellPoly;
import	jp.co.lattice.vKernel.core.t0.lvDivPoly;
import	jp.co.lattice.vKernel.core.g0.lvDivPolyLow;
import	jp.co.lattice.vKernel.greg.t0g.lvDivFace;
import	jp.co.lattice.vKernel.greg.t0g.lv0DivFace;
import	jp.co.lattice.vKernel.greg.t0g.lv0TopoGGblElm;
import	jp.co.lattice.vKernel.tex.a0g.lvFaceFromKernelUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0FaceFromKernelUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0AttGGblElm;
import	jp.co.lattice.vKernel.tex.a0.lvPolyFromKernelUV;
import	jp.co.lattice.vKernel.tex.a0.lv0PolyFromKernelUV;
import	jp.co.lattice.vKernel.tex.a0.lv0AttGblElm;
import	jp.co.lattice.vKernel.greg.c0g.lvDivFaceType;


/**
 * Java Lattice Kernel から上位階層に引き渡すデータのクラス
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvFromKernel extends lvFromKernelLow {

	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {
		
		/** カレントシェルNo			*/
		private int  curShellNo		= 0;
		
		/** オブジェクト				*/
		private lvDivPoly            divPoly			= null;
		private lv0TopoGGblElm       topoGGblElm		= null;
		private lvDivFace            divFace			= null;
		private lv0AttGblElm         attGblElm			= null;
		private lvPolyFromKernelUV   polyFromKernelUV	= null;
		private lv0AttGGblElm        attGGblElm			= null;
		private lvFaceFromKernelUV   faceFromKernelUV	= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
			divPoly			 = new lvDivPoly( dt );
			topoGGblElm      = new lv0TopoGGblElm( dt );
			divFace			 = new lv0DivFace( dt );
			attGblElm	     = new lv0AttGblElm( dt );
			polyFromKernelUV = new lv0PolyFromKernelUV( dt );
			attGGblElm	     = new lv0AttGGblElm( dt );
			faceFromKernelUV = new lv0FaceFromKernelUV( dt );
		}
	}

	/** 当クラス用のグローバルデータ			*/
	private final Global
	Gbl()
	{
		return  ( ( lvBaseGblElm )global.GBase() ).gFromKernel;
	}
	/** lvShell.Attrデータ用クラスオブジェクト		*/
	private final lvShellPoly.Attr
	Attr()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].attr;
	}
	/** lvError用のグローバルデータ				*/
	private final lvError.Global
	ErrProc()
	{
		return  ( ( lvComGblElm )global.GCom() ).gError;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvFromKernel( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * テセレーションデータを得る（この関数を実行する前は vertex[], triIndex[] を null としておく)
	 * @param  shellNo		(( I )) int num = lvToKernel.GetNumShell() の場合、0 ～ ( num-1 )
	 * @param  gsNo			(( I )) 配列 lvToKernel.gsNumVtx[] の長さが n の場合、0 ～ ( n-1 )
	 * @return				lvConst.LV_SUCCESS または lvConst.LV_FAILURE
	 */
	public final boolean
	GetData( int shellNo, int gsNo )
	{
		Gbl().curShellNo = shellNo;
		
		try {
			ErrProc().BeginThrowMode();
			
				Exec( shellNo, gsNo );
				
			ErrProc().EndThrowMode();
			return  lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return  lvConst.LV_FAILURE;
		}
	}
	
	private final void
	Exec( int shellNo, int gsNo ) throws lvThrowable
	{
		if( Gbl().topoGGblElm.IsDmy() == true ) {
			ExecPoly( shellNo, gsNo );
			return;
		}
		
		switch( Attr().type ) {
		case lvConst.LV_SS_POLYGON:
			ExecPoly( shellNo, gsNo );
			break;
		case lvConst.LV_SS_LATTICE:
		case lvConst.LV_SS_GREGORY:
			ExecFace( shellNo, gsNo );
			break;
		}
	}

	private final void
	ExecPoly( int shellNo, int gsNo ) throws lvThrowable
	{
		lvDivPolyLow.UpDivPoly  upDivPoly = Gbl().divPoly.Exec( shellNo, gsNo );
		SetPoly( upDivPoly );
		
		if( Gbl().attGblElm.IsDmy() == false )
			uvSpace = Gbl().polyFromKernelUV.ExecPoly( shellNo, gsNo, upDivPoly.divPolyUV );
		else
			uvSpace = null;
		upDivPoly.divPolyUV.uvSpace = null;		// Delete( upDivPoly.divPolyUV.uvSpace );
	}
	
	private final void
	SetPoly( lvDivPolyLow.UpDivPoly upDivPoly )
	{
		int  numVertex   = upDivPoly.numVertex;
		int  numTriIndex = upDivPoly.numTriIndex;
		
		NewVertex( numVertex );
		NewTriIndex( numTriIndex );

		for( int i=0; i<numVertex; i++ )
			lvRec.PosNorHi.HiToLow( upDivPoly.vertex[ i ], vertex[ i ] );
		
		for( int i=0; i<numTriIndex; i++ )
			lvRec.TriIndex.Copy( upDivPoly.triIndex[ i ], triIndex[ i ] );
			
		upDivPoly.vertex   = null;		// Delete( upDivPoly.vertex );
		upDivPoly.triIndex = null;		// Delete( upDivPoly.triIndex );
	}
	
	private final void
	ExecFace( int shellNo, int gsNo ) throws lvThrowable
	{
		lvDivFaceType.UpDivFace  upDivFace = null;
		upDivFace = Gbl().divFace.Exec( shellNo, gsNo );
		SetFace( upDivFace );
		
		if( Gbl().attGGblElm.IsDmy() == false )
			uvSpace = Gbl().faceFromKernelUV.ExecFace( shellNo, gsNo, upDivFace.divFaceUV );
		else
			uvSpace = null;
		upDivFace.divFaceUV.uvSpace = null;		// Delete( upDivFace.divFaceUV.uvSpace );
	}
	
	private final void
	SetFace( lvDivFaceType.UpDivFace upDivFace )
	{
		int  numVertex   = upDivFace.vertex.length;
		int  numTriIndex = upDivFace.triIndex.length;
		
		NewVertex( numVertex );
		NewTriIndex( numTriIndex );

		for( int i=0; i<numVertex; i++ )
			lvRec.PosNorHi.HiToLow( upDivFace.vertex[ i ], vertex[ i ] );
		
		for( int i=0; i<numTriIndex; i++ )
			lvRec.TriIndex.Copy( upDivFace.triIndex[ i ], triIndex[ i ] );
			
		upDivFace.vertex   = null;		// Delete( upDivFace.vertex );
		upDivFace.triIndex = null;		// Delete( upDivFace.triIndex );
	}
	
	private final void
	NewVertex( int num )
	{
		vertex = new lvRec.PosNorLow[ num ];
		for( int i=0; i<num; i++ )
			vertex[ i ] = new lvRec.PosNorLow();
	}
	
	private final void
	NewTriIndex( int num )
	{
		triIndex = new lvRec.TriIndex[ num ];
		for( int i=0; i<num; i++ )
			triIndex[ i ] = new lvRec.TriIndex();
	}

}
