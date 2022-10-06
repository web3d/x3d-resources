//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivPoly.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.core.g0.lvDivPolyLow;
import	jp.co.lattice.vKernel.tex.a0.lvDivPolyUV;
import	jp.co.lattice.vKernel.tex.a0.lv0DivPolyUV;
import	jp.co.lattice.vKernel.tex.a0.lv0AttGblElm;


/**
 * Round���̍쐬�N���X�i��ʃ��C���[�j
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvDivPoly extends lvRoot {
	
	private static final int  maxNumVtxPos = 256;
		
// -------------------------------------------------------------------
	
	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�V�F��No							*/
		private int  curShellNo		= 0;
		
		/** �J�����gGS��No								*/
		private int  curGsNo		= 0;

		/** ���ʃ��C���[(geom0)�I�u�W�F�N�g				*/
		private lvDivPolyLow              divPolyLow		= null;

		/** ���ʃ��C���[(att0)�I�u�W�F�N�g				*/
		private lvDivPolyUV               divPolyUV			= null;

		/** lv0AttGblElm�I�u�W�F�N�g		*/
		private lv0AttGblElm              attGblElm			= null;
		
		/** ���ʃ��C���[(geom0)�ɑ���f�[�^				*/
		private lvDivPolyLow.DownDivPoly  downDivPoly		= null;
		
		/** ���ʃ��C���[(geom0)���瑗����f�[�^		*/
		private lvDivPolyLow.UpDivPoly    upDivPoly			= null;

		/** ���K�͂� DownDivPoly().vtxPos �p�̃O���[�o���f�[�^		*/
		private lvVector  tmpVtxPos[]						= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			divPolyLow  = new lvDivPolyLow( dt );
			divPolyUV   = new lv0DivPolyUV( dt );
			attGblElm	= new lv0AttGblElm( dt );
			downDivPoly = new lvDivPolyLow.DownDivPoly();
			upDivPoly   = new lvDivPolyLow.UpDivPoly();

			tmpVtxPos = new lvVector[ maxNumVtxPos ];
			for( int i=0; i<maxNumVtxPos; i++ )
				tmpVtxPos[ i ] = new lvVector( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lvTopoGblElm )global.GTopo() ).gDivPoly;
	}
	/** lvPolygon�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvPolygon
	Polygon()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].poly;
	}
	/** DownRound�p�̃O���[�o���f�[�^				*/
	private final lvDivPolyLow.DownDivPoly
	DownDivPoly()
	{
		return  Gbl().downDivPoly;
	}
	/** UpRound�p�̃O���[�o���f�[�^				*/
	private final lvDivPolyLow.UpDivPoly
	UpDivPoly()
	{
		return  Gbl().upDivPoly;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvDivPoly( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1�̃|���S�����e�Z���[�V��������
	 * @param  shellNo		(( I )) lvToKernel.SetNumShell( num ) �ƃZ�b�g�����ꍇ�A0 �` ( num-1 )
	 * @param  gsNo			(( I )) �z�� lvToKernel.gsNumVtx[] �̒����� n �̏ꍇ�A0 �` ( n-1 )
	 * @return				���ʃ��C���[(geom0)���瑗����f�[�^
	 */
	public final lvDivPolyLow.UpDivPoly
	Exec( int shellNo, int gsNo ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		
		SetDownDivPoly();
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().divPolyUV.SetDownDivPolyUV( shellNo, gsNo, DownDivPoly().divPolyUV );
		
		Gbl().divPolyLow.Exec( DownDivPoly(), UpDivPoly() );
		
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().divPolyUV.SetUpDivPolyUV();
		SetUpDivPoly();
		
		Finish();
		if( Gbl().attGblElm.IsDmy() == false )
			Gbl().divPolyUV.Finish();
		
		return  UpDivPoly();
	}
	
	private final void
	SetDownDivPoly()
	{
		lvRec.SeqPart  face = Polygon().face[ Gbl().curGsNo ];
		NewDownDivPoly( face.num );
		
		for( int i=0; i<face.num; i++ ) {
			lvPolygon.FaceHalf  faceHalfSeq = Polygon().faceHalfSeq[ face.start + i ];
			lvVector            vtxPos      = DownDivPoly().vtxPos[ i ];
			vtxPos.VecDt2Vector( Polygon().vertex[ faceHalfSeq.vtxNo ].pos );
		}
	}
	
	private final void
	NewDownDivPoly( int num )
	{
		DownDivPoly().numVtxPos = num;
		
		if( num > maxNumVtxPos ) {
			DownDivPoly().vtxPos = new lvVector[ num ];
			for( int i=0; i<maxNumVtxPos; i++ )
				DownDivPoly().vtxPos[ i ] = Gbl().tmpVtxPos[ i ];
			for( int i=maxNumVtxPos; i<num; i++ )
				DownDivPoly().vtxPos[ i ] = new lvVector( global );
		}
		else
			DownDivPoly().vtxPos = Gbl().tmpVtxPos;
	}
	
	private final void
	SetUpDivPoly()
	{
		// no process
	}
	
	private final void
	Finish()
	{
		DownDivPoly().vtxPos = null;		// Delete( DownDivPoly().vtxPos );
	}
	
}
