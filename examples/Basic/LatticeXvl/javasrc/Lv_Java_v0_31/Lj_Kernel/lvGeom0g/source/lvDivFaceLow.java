//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFaceLow.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * DivPoly���̍쐬�N���X�i���ʃ��C���[�j
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvDivFaceLow extends lvRoot {

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** �J�����g�́u��ʃ��C���[(topo0)���瑗����f�[�^�v		*/
		private lvDivFaceType.DownDivFace    curDownDivFace		= null;
		
		/** DownDivFace����h�������f�[�^				*/
		private lvDivFaceType.DeriveDivFace  deriveDivFace		= null;
		
		/** lvMakeInnerEdge�I�u�W�F�N�g					*/
		private lvMakeInnerEdge  makeInnerEdge		= null;
		
		/** lvTessellate�I�u�W�F�N�g					*/
		private lvTessellate     tessellate			= null;

		/** static�֐��p�̃I�u�W�F�N�g					*/
		private lvDivFaceLow     local				= null;
		
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			deriveDivFace = new lvDivFaceType.DeriveDivFace( dt );
			makeInnerEdge = new lvMakeInnerEdge( dt );
			tessellate    = new lvTessellate( dt );
			local         = new lvDivFaceLow( dt );
			
			GlobalTmp( dt );
		}

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
		private lvVector  tvAryPosition[]			= null;
		private lvVector  tvAryDerivative[]			= null;
		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvAryPosition    = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryPosition[ i ]    = new lvVector( dt );
			tvAryDerivative  = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvAryDerivative[ i ]  = new lvVector( dt );
		}
		
	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gDivFaceLow;
	}
	/** DownDivFace�p�̃O���[�o���f�[�^				*/
	private final lvDivFaceType.DownDivFace
	DownDivFace()
	{
		return	Gbl().curDownDivFace;
	}
	/** DeriveDivFace0�p�̃O���[�o���f�[�^		*/
	private final lvDivFaceType.DeriveDivFace
	DeriveDivFace()
	{
		return  Gbl().deriveDivFace;
	}
	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private static final lvDivFaceLow
	Local( lvGlobal gbl )
	{
		return  ( ( lv0GeomGGblElm )gbl.GGeomG() ).gDivFaceLow.local;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public	lvDivFaceLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1�̖ʂ��e�Z���[�V��������
	 * @param  downDivPoly		(( I )) ��ʃ��C���[(topo0)���瑗����f�[�^
	 * @param  upDivPoly		(( O )) ��ʃ��C���[(topo0)�ɑ���f�[�^
	 */
	public final void
	Exec( lvDivFaceType.DownDivFace downDivFace, lvDivFaceType.UpDivFace upDivFace ) throws lvThrowable
	{
		Gbl().curDownDivFace = downDivFace;

		if( DownDivFace().numHalf != 4 )
			Gbl().makeInnerEdge.Exec( downDivFace, DeriveDivFace() );
			
		Gbl().tessellate.Exec( downDivFace, DeriveDivFace(), upDivFace );
	
		Finish();
	}
	
	private final void
	Finish()
	{
		Gbl().curDownDivFace      = null;		// Delete( Gbl().curDownDivFace );
		Gbl().deriveDivFace.inner = null;		// Delete( Gbl().deriveDivFace.inner );
	}
	
	/**
	 * t�l�ł̃x�W�F�Ȑ���̈ʒu�����߂�
	 * @param  halfNo	(( I )) �n�[�t�G�b�W��No
	 * @param  t		(( I )) �p�����[�^�l�i 0 �` 1 �j
	 * @param  result	(( O )) �ʒu
	 */
	public static final void
	Position( int halfNo, double t, lvVector result )
	{
		lvGlobal  gbl = result.global;
		Local( gbl ).PositionLocal( halfNo, t, result );
	}
	
	/**
	 * t�l�ł̃x�W�F�Ȑ���̈ʒu�����߂�i���[�J���j
	 * @param  halfNo	(( I )) �n�[�t�G�b�W��No
	 * @param  t		(( I )) �p�����[�^�l�i 0 �` 1 �j
	 * @param  result	(( O )) �ʒu
	 */
	private final void
	PositionLocal( int halfNo, double t, lvVector result )
	{
		lvVector  ctlPnt[/*4*/] = Gbl().tvAryPosition;					// ctlPnt[] = new lvVector[ 4 ];
		
		ctlPnt[ 0 ].Assign( DownDivFace().half[ halfNo ].pos );
		ctlPnt[ 1 ].Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		ctlPnt[ 2 ].Assign( DownDivFace().half[ halfNo ].handVec[ 1 ] );
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		ctlPnt[ 3 ].Assign( DownDivFace().half[ halfF ].pos );
			
		ctlPnt[ 1 ].AddAssign( ctlPnt[ 0 ] );
		ctlPnt[ 2 ].AddAssign( ctlPnt[ 3 ] );
		
		lvBezLine.Position( ctlPnt, t, result );
	}

	/**
	 * t�l�ł̃x�W�F�Ȑ���̔����x�N�g�������߂�
	 * @param  halfNo	(( I )) �n�[�t�G�b�W��No
	 * @param  t		(( I )) �p�����[�^�l�i 0 �` 1 �j
	 * @param  result	(( O )) �����x�N�g��
	 */
	public static final void
	Derivative( int halfNo, double t, lvVector result )
	{
		lvGlobal  gbl = result.global;
		Local( gbl ).DerivativeLocal( halfNo, t, result );
	}
	
	/**
	 * t�l�ł̃x�W�F�Ȑ���̔����x�N�g�������߂�i���[�J���j
	 * @param  halfNo	(( I )) �n�[�t�G�b�W��No
	 * @param  t		(( I )) �p�����[�^�l�i 0 �` 1 �j
	 * @param  result	(( O )) �����x�N�g��
	 */
	private final void
	DerivativeLocal( int halfNo, double t, lvVector result )
	{
		lvVector  ctlPnt[/*4*/] = Gbl().tvAryDerivative;					// ctlPnt[] = new lvVector[ 4 ];
		
		ctlPnt[ 0 ].Assign( DownDivFace().half[ halfNo ].pos );
		ctlPnt[ 1 ].Assign( DownDivFace().half[ halfNo ].handVec[ 0 ] );
		ctlPnt[ 2 ].Assign( DownDivFace().half[ halfNo ].handVec[ 1 ] );
		int  halfF = ( halfNo + 1 ) % DownDivFace().numHalf;
		ctlPnt[ 3 ].Assign( DownDivFace().half[ halfF ].pos );
			
		ctlPnt[ 1 ].AddAssign( ctlPnt[ 0 ] );
		ctlPnt[ 2 ].AddAssign( ctlPnt[ 3 ] );
		
		lvBezLine.Derivative( ctlPnt, t, result );
	}
	
}
