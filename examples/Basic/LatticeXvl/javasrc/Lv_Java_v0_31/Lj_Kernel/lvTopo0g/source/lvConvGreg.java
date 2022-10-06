//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvConvGreg.java
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.t0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * Shell�v�f�� Type ���ASS_GREGORY �̏ꍇ�p�̃N���X
 * @author	  created by Eishin Matsui (00/04/27-)
 * 
 */
public class lvConvGreg extends lvRoot {

	private static final int  maxNumHalfEdge	= 256;

// -------------------------------------------------------------------
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No							*/
		private int  curShellNo	= 0;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			GlobalTmp( dt );
			GlobalStatic( dt );
		}

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
		private lvVector  tvExecMain[]			= null;
		private lvVector  tvFaceCenterProc[]	= null;
		private lvVector  tvFaceNormalProc[]	= null;
		private lvVector  tvNormalFace[]		= null;
		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvExecMain       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvExecMain[ i ]       = new lvVector( dt );
			tvFaceCenterProc = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvFaceCenterProc[ i ] = new lvVector( dt );
			tvFaceNormalProc = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvFaceNormalProc[ i ] = new lvVector( dt );
			tvNormalFace     = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvNormalFace[ i ]     = new lvVector( dt );
		}

		/** ���K�͂Ȕz��p�̃O���[�o���f�[�^				*/
		private lvVector  svCtlPoint[]	= null;
		
		/**
		 * ���K�͂Ȕz��p�̃O���[�o���f�[�^�̏������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		private final void
		GlobalStatic( lvGlobal dt )
		{
			svCtlPoint = new lvVector[ maxNumHalfEdge * 3 ];
			for( int i=0; i<( maxNumHalfEdge * 3 ); i++ )
				svCtlPoint[ i ] = new lvVector( dt );
		}
		
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0TopoGGblElm )global.GTopoG() ).gConvGreg;
	}
	/** lvPolygon�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** lvPolyDerive.Bezgon�f�[�^�p�N���X�I�u�W�F�N�g	*/
	private final lvPolyDerive.Bezgon
	Bezgon()
	{
		return  ( ( lv0ComGGblElm )global.GComG() ).gModelGreg.shell[ Gbl().curShellNo ].bez;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lvConvGreg( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1�̃V�F���� round ����
	 * @param  shellNo		(( I )) �V�F��No
	 */
	public void
	Exec( int shellNo ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		
		NewBezgon();
		ExecMain();
		
		FaceProc();
	}
	
	private final void
	NewBezgon()
	{
		Bezgon().NewVertex( Polygon().vertex.length );
		Bezgon().NewEdge( Polygon().edge.length );
		Bezgon().NewFace( Polygon().ngStartNo );
	}

	private final void
	ExecMain() throws lvThrowable
	{
		ExecVertex();
		ExecEdge();
	}
	
	private final void
	ExecVertex() throws lvThrowable
	{
		int  num = Polygon().vertex.length;
		for( int i=0; i<num; i++ )
			lvVecDt.Copy( Polygon().vertex[ i ].pos, Bezgon().vertex[ i ].pos );
	}
	
	private final void
	ExecEdge() throws lvThrowable
	{
		lvVector  handVec = Gbl().tvExecMain[ 0 ];				// handVec = new lvVector( global );
		
		int  num = Polygon().edge.length;
		for( int i=0; i<num; i++ ) {
			for( int j=0; j<2; j++ ) {
				handVec.VecDt2Vector( Polygon().edge[ i ].vec[ j ] );
				handVec.DivAssign( 3.0 );									// handVec /= 3.0;
				handVec.Vector2VecDt( Bezgon().edge[ i ].handVec[ j ] );
			}
		}
	}
	
	private final void
	FaceProc() throws lvThrowable
	{
		FaceCenterProc();
		FaceNormalProc();
	}
	
	private final void
	FaceCenterProc() throws lvThrowable
	{
		lvVector  center = Gbl().tvFaceCenterProc[ 0 ];			// center = new lvVector( global );
		lvVector  pos    = Gbl().tvFaceCenterProc[ 1 ];			// pos    = new lvVector( global );
		
		int  numFace = Polygon().ngStartNo;
		for( int i=0; i<numFace; i++ ) {
			
			center.SetXYZ( 0.0, 0.0, 0.0 );
			lvRec.SeqPart  face = Polygon().face[ i ];
			
			for( int j=0; j<face.num; j++ ) {
				lvPolygon.FaceHalf	faceHalf  = Polygon().faceHalfSeq[ face.start + j ];
				pos.VecDt2Vector( Bezgon().vertex[ faceHalf.vtxNo ].pos );
				center.AddAssign( pos );
			}
			center.DivAssign( face.num );
			center.Vector2VecDt( Bezgon().face[ i ].center.pos );
		}
	}
	
	private final void
	FaceNormalProc() throws lvThrowable
	{
		lvVector  normal = Gbl().tvFaceNormalProc[ 0 ];			// normal = new lvVector( global );

		int  numFace = Polygon().ngStartNo;
		for( int i=0; i<numFace; i++ ) {
			NormalFace( i, normal );
			normal.Vector2VecDt( Bezgon().face[ i ].center.normal );
		}
	}
	
	private final void
	NormalFace( int faceNo, lvVector normal ) throws lvThrowable
	{
		lvVector  posF = Gbl().tvNormalFace[ 0 ];				// posF = new lvVector( global );

		lvRec.SeqPart  polyFace = Polygon().face[ faceNo ];
		lvVector  ctlPnt[] = NewCtlPoint( polyFace.num * 3 );
		
		for( int i=0; i<polyFace.num; i++ ) {
			int  nf = ( i + 1 ) % polyFace.num;
			lvPolygon.FaceHalf	faceHalf  = Polygon().faceHalfSeq[ polyFace.start + i  ];
			lvPolygon.FaceHalf	faceHalfF = Polygon().faceHalfSeq[ polyFace.start + nf ];
			
			ctlPnt[ i*3 + 0 ].VecDt2Vector( Bezgon().vertex[ faceHalf.vtxNo ].pos );
			ctlPnt[ i*3 + 1 ].VecDt2Vector( Bezgon().edge[ faceHalf.edgeNo ].handVec[ faceHalf.edgeIdx	   ] );
			ctlPnt[ i*3 + 2 ].VecDt2Vector( Bezgon().edge[ faceHalf.edgeNo ].handVec[ 1 - faceHalf.edgeIdx ] );
			posF.VecDt2Vector( Bezgon().vertex[ faceHalfF.vtxNo ].pos );
			
			ctlPnt[ i*3 + 1 ].AddAssign( ctlPnt[ i*3 + 0 ] );
			ctlPnt[ i*3 + 2 ].AddAssign( posF );
		}
		
		lvBezLine.NormalBound( ctlPnt, polyFace.num * 3, normal );
	}
	
	private final lvVector[]
	NewCtlPoint( int num )
	{
		lvVector  ctlPoint[];
		
		if( num > ( maxNumHalfEdge * 3 ) ) {
			ctlPoint = new lvVector[ num ];
			for( int i=0; i<( maxNumHalfEdge * 3 ); i++ )
				ctlPoint[ i ] = Gbl().svCtlPoint[ i ];
			for( int i=( maxNumHalfEdge * 3 ); i<num; i++ )
				ctlPoint[ i ] = new lvVector( global );
		}
		else
			ctlPoint = Gbl().svCtlPoint;
			
		return	ctlPoint;
	}
	
}
