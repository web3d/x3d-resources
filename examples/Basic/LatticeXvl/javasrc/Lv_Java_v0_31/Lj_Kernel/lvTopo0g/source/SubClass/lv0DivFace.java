//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lv0DivFace.java		�i�����Łj
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.t0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;
import	jp.co.lattice.vKernel.greg.g0g.lvDivFaceLow;
import	jp.co.lattice.vKernel.tex.a0g.lvDivFaceUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0DivFaceUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0AttGGblElm;


/**
 * Round���̍쐬�N���X�i��ʃ��C���[�j
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lv0DivFace extends lvDivFace {
	
	private static final int  maxNumHalf = 256;
	private static final int  maxNumSeg  = 256;
		
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
		private lvDivFaceLow               divFaceLow		= null;

		/** ���ʃ��C���[(att0)�I�u�W�F�N�g				*/
		private lvDivFaceUV                divFaceUV		= null;

		/** lv0AttGblElm�I�u�W�F�N�g		*/
		private lv0AttGGblElm              attGGblElm		= null;
		
		/** ���ʃ��C���[(geom0)�ɑ���f�[�^				*/
		private lvDivFaceType.DownDivFace  downDivFace		= null;
		
		/** ���ʃ��C���[(geom0)���瑗����f�[�^		*/
		private lvDivFaceType.UpDivFace    upDivFace		= null;

		/** ���K�͂� DownDivFace().half �p�̃O���[�o���f�[�^		*/
		private lvDivFaceType.DownHalf     staticHalf[]		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			divFaceLow  = new lvDivFaceLow( dt );
			divFaceUV   = new lv0DivFaceUV( dt );
			attGGblElm	= new lv0AttGGblElm( dt );
			downDivFace = new lvDivFaceType.DownDivFace( dt );
			upDivFace   = new lvDivFaceType.UpDivFace();

			staticHalf = new lvDivFaceType.DownHalf[ maxNumHalf ];
			for( int i=0; i<maxNumHalf; i++ )
				staticHalf[ i ] = new lvDivFaceType.DownHalf( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0TopoGGblElm )global.GTopoG() ).gDivFace;
	}
	/** lvShell.Attr�f�[�^�p�N���X�I�u�W�F�N�g		*/
	private final lvShellPoly.Attr
	Attr()
	{
		return  ( ( lvComGblElm )global.GCom() ).gModelPoly.shell[ Gbl().curShellNo ].attr;
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
		return	( ( lv0ComGGblElm )global.GComG() ).gModelGreg.shell[ Gbl().curShellNo ].bez;
	}
	/** DownRound�p�̃O���[�o���f�[�^			*/
	private final lvDivFaceType.DownDivFace
	DownDivFace()
	{
		return  Gbl().downDivFace;
	}
	/** UpRound�p�̃O���[�o���f�[�^				*/
	private final lvDivFaceType.UpDivFace
	UpDivFace()
	{
		return  Gbl().upDivFace;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lv0DivFace( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1�̖ʂ��e�Z���[�V��������
	 * @param  shellNo		(( I )) lvToKernel.SetNumShell( num ) �ƃZ�b�g�����ꍇ�A0 �` ( num-1 )
	 * @param  gsNo			(( I )) �z�� lvToKernel.gsNumVtx[] �̒����� n �̏ꍇ�A0 �` ( n-1 )
	 * @return				���ʃ��C���[(geom0)���瑗����f�[�^
	 */
	public lvDivFaceType.UpDivFace
	Exec( int shellNo, int gsNo ) throws lvThrowable
	{
		Gbl().curShellNo = shellNo;
		Gbl().curGsNo    = gsNo;
		
		SetDownDivFace();
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().divFaceUV.SetDownDivFaceUV( shellNo, gsNo, DownDivFace().divFaceUV );
		
		Gbl().divFaceLow.Exec( DownDivFace(), UpDivFace() );
		
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().divFaceUV.SetUpDivFaceUV();
		SetUpDivFace();

		Finish();
		if( Gbl().attGGblElm.IsDmy() == false )
			Gbl().divFaceUV.Finish();
		
		return  UpDivFace();
	}
	
	private final void
	SetDownDivFace() throws lvThrowable
	{
		SetDownHalf();
		SetDownNormal();
		SetDownAttr();
	}
	
	private final void
	SetDownHalf()
	{
		lvRec.SeqPart  face = Polygon().face[ Gbl().curGsNo ];
		NewDownHalf( face.num );
		
		for( int i=0; i<face.num; i++ ) {
			lvPolygon.FaceHalf  faceHalfSeq = Polygon().faceHalfSeq[ face.start + i ];
			
			lvVector  pos = DownDivFace().half[ i ].pos;
			pos.VecDt2Vector( Bezgon().vertex[ faceHalfSeq.vtxNo ].pos );

			lvVector  handVec0 = DownDivFace().half[ i ].handVec[ 0 ];
			handVec0.VecDt2Vector( Bezgon().edge[ faceHalfSeq.edgeNo ].handVec[ faceHalfSeq.edgeIdx     ] );
			lvVector  handVec1 = DownDivFace().half[ i ].handVec[ 1 ];
			handVec1.VecDt2Vector( Bezgon().edge[ faceHalfSeq.edgeNo ].handVec[ 1 - faceHalfSeq.edgeIdx ] );
		}
	}
	
	private final void
	NewDownHalf( int num )
	{
		DownDivFace().numHalf = num;
		
		if( num > maxNumHalf ) {
			DownDivFace().half = new lvDivFaceType.DownHalf[ num ];
			for( int i=0; i<maxNumHalf; i++ )
				DownDivFace().half[ i ] = Gbl().staticHalf[ i ];
			for( int i=maxNumHalf; i<num; i++ )
				DownDivFace().half[ i ] = new lvDivFaceType.DownHalf( global );
		}
		else
			DownDivFace().half = Gbl().staticHalf;
	}

	private final void
	SetDownNormal()
	{
		DownDivFace().center.pos.VecDt2Vector( Bezgon().face[ Gbl().curGsNo ].center.pos );
		DownDivFace().center.normal.VecDt2Vector( Bezgon().face[ Gbl().curGsNo ].center.normal );
	}
	
	private final void
	SetDownAttr() throws lvThrowable
	{
		Err().Assert( ( ( Attr().numDiv % 2 ) == 0 ), "lvDivFace.SetDownAttr(0)" );
		DownDivFace().attr.numDiv = Attr().numDiv;
	}
	
	private final void
	SetUpDivFace()
	{
		// no process
	}
	
	private final void
	Finish()
	{
		DownDivFace().half = null;		// Delete( DownDivFace().half );
	}

}
