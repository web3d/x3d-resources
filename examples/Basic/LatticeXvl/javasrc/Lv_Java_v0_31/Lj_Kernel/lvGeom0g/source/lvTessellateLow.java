//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvTessellateLow.java
//

package jp.co.lattice.vKernel.greg.g0g;

import	jp.co.lattice.vKernel.core.g0.*;
import	jp.co.lattice.vKernel.core.c0.*;

import	jp.co.lattice.vKernel.tex.a0g.lvTessellateUV;
import	jp.co.lattice.vKernel.tex.a0g.lv0TessellateUV;


/**
 * 1�̖ʂ��쐬����N���X
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvTessellateLow extends lvRoot {

	/**
	 * 1�{�̃n�[�t�G�b�W�_�Ɋւ���ulvTessellate���瑗����f�[�^�p�̓����N���X�v
	 */
	public static class DownHalf {
		
		/** ���_�i�n�[�t�G�b�W�n�_�j���W							*/
		public lvVector  pos					= null;
		
		/** �n���h���x�N�g���i�C���f�b�N�X 0:�n�_�A 1:�I�_�j		*/
		public lvVector  handVec[/*2*/]			= null;

		/** CBD�u b�x�N�g�� �v�i�C���f�b�N�X 0:�n�_�A 1:�I�_�j			*/
		public lvVector  bCbd[/*2*/]			= null;
		
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  DownHalf( lvGlobal dt )
		{
			pos = new lvVector( dt );
			
			handVec = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )
				handVec[ i ] = new lvVector( dt );
				
			bCbd = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )
				bCbd[ i ] = new lvVector( dt );
		}
	}
	
	/**
	 * lvTessellate���瑗����f�[�^�p�̓����N���X
	 */
	public static class DownTessellate {
		
		/** v�����i�n�[�t�G�b�WNo0�̏I�_���n�_�A�n�[�t�G�b�WNo2�̎n�_���I�_�j�̕�����	*/
		public int       numDivV;
		/**u�����i�n�[�t�G�b�WNo1�̎n�_���I�_�A�n�[�t�G�b�WNo3�̏I�_���n�_�j�̕�����	*/
		public int       numDivU;
		
		/** �n�[�t�G�b�W��񕔂̔z��iisDegenerate == true �̎��́A�n�[�t�G�b�WNo3�͖����j	*/
		public DownHalf  half[/*4*/]					= null;

		/** UV��ԏ��̔z��		*/
		public lvTessellateUV.DownTessellateUV  uvInfo	= new lv0TessellateUV.DownTessellateUV();
		
		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  DownTessellate( lvGlobal dt )
		{
			half = new DownHalf[ 4 ];
			for( int i=0; i<4; i++ )
				half[ i ] = new DownHalf( dt );
		}
	}
	
	/**
	 * lvTessellate�ɑ���f�[�^�p�̓����N���X
	 */
	public static class UpTessellate {
		
		/** ���_��				*/
		public int             numVertex;
		/**
		 * ���_���W�̔z��i�L�����͒��_�̐��u 3�ȏ� �v�j --- �����l null									<br>
		 * ���_���͗Ő��������ulvToKAttr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
		 */
		public lvRec.PosNorHi  vertex[]							= null;
	
		/** �O�p�|���S����		*/
		public int             numTriIndex;
		/**
		 * 3���_No.Index���琬��O�p�|���S���̔z��i�L�����͎O�p�|���S���̐��u 1�ȏ� �v�j --- �����l null	<br>
		 * �O�p�|���S�����͗Ő��������ulvToKAttr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
		 */
		public lvRec.TriIndex  triIndex[]						= null;

		/** UV��ԏ��̔z��		*/
		public lvTessellateUV.UpTessellateUV  uvInfo	= new lv0TessellateUV.UpTessellateUV();
	}
		
// -------------------------------------------------------------------

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		/** ���ʃ��C���[�I�u�W�F�N�g							*/
		private lvGregoryStd    gregoryStd				= null;

		/** ���ʃ��C���[�ɑ���f�[�^			*/
		private lvGregoryStd.DownGregoryStd      downGregoryStd		= null;

		/** �J�����g�́u��ʃ��C���[���瑗����f�[�^�v		*/
		private DownTessellate  curDownTessellate		= null;
		/** �J�����g�́u��ʃ��C���[�ɑ���f�[�^�v				*/
		private UpTessellate      curUpTessellate		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			gregoryStd        = new lvGregoryStd( dt );
			downGregoryStd    = new lvGregoryStd.DownGregoryStd( dt );
			
			curDownTessellate = new DownTessellate( dt );
			curUpTessellate   = new UpTessellate();
			
			GlobalTmp( dt );
		}
		
		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
		private lvVector  tvAryExecMain[/*5*/]				= null;
		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
			tvAryExecMain = new lvVector[ 5 ];	for( int i=0; i<5; i++ )	tvAryExecMain[ i ] = new lvVector( dt );
		}

	}

	/** ���N���X�p�̃O���[�o���f�[�^			*/
	private final Global
	Gbl()
	{
		return  ( ( lv0GeomGGblElm )global.GGeomG() ).gTessellateLow;
	}
	/** DownGregoryStd�p�̃O���[�o���f�[�^			*/
	private final lvGregoryStd.DownGregoryStd
	DownGregoryStd()
	{
		return  Gbl().downGregoryStd;
	}
	/** DownTessellate�p�̃O���[�o���f�[�^			*/
	private final lvTessellateLow.DownTessellate
	DownTessellate()
	{
		return  Gbl().curDownTessellate;
	}
	/** UpRound�p�̃O���[�o���f�[�^				*/
	private final lvTessellateLow.UpTessellate
	UpTessellate()
	{
		return  Gbl().curUpTessellate;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public	lvTessellateLow( lvGlobal dt )
	{
		super( dt );
	}
 
// -------------------------------------------------------------------

	/**
	 * ���s�֐�
	 */
	public final void
	Exec( DownTessellate downTessellate, UpTessellate upTessellate ) throws lvThrowable
	{
		Gbl().curDownTessellate = downTessellate;
		Gbl().curUpTessellate   = upTessellate;
		
		ExecStd();
	}
	
	private final void
	ExecStd() throws lvThrowable
	{
		DownGregoryStd().numDivU = DownTessellate().numDivU;
		DownGregoryStd().numDivV = DownTessellate().numDivV;
		
		for( int i=0; i<4; i++ )
			ExecMain( i, DownGregoryStd().ctlPnt );
			
		DownGregoryStd().uvInfo = DownTessellate().uvInfo;
			
		Gbl().gregoryStd.Exec( DownGregoryStd(), UpTessellate() );
	}
	
	private final void
	ExecMain( int halfNo, double ctlPnt[/*20*/][/*3*/] ) throws lvThrowable
	{
		lvVector  ctl[/*5*/] = Gbl().tvAryExecMain;
		
		int  halfB = ( halfNo + 4 - 1 ) % 4;
		
		ctl[ 2 ].Assign( DownTessellate().half[ halfNo ].pos );
		
		ctl[ 1 ].Assign( DownTessellate().half[ halfB  ].handVec[ 1 ].Add( ctl[ 2 ] ) );
				// ctl[ 1 ] = DownTessellate().half[ halfB  ].handVec[ 1 ] + ctl[ 2 ];
		ctl[ 3 ].Assign( DownTessellate().half[ halfNo ].handVec[ 0 ].Add( ctl[ 2 ] ) );
				// ctl[ 3 ] = DownTessellate().half[ halfNo ].handVec[ 0 ] + ctl[ 2 ];
		
		ctl[ 0 ].Assign( DownTessellate().half[ halfB  ].bCbd[ 1 ].Add( ctl[ 1 ] ) );
				// ctl[ 0 ] = DownTessellate().half[ halfB  ].bCbd[ 1 ] + ctl[ 1 ];
		ctl[ 4 ].Assign( DownTessellate().half[ halfNo ].bCbd[ 0 ].Add( ctl[ 3 ] ) );
				// ctl[ 4 ] = DownTessellate().half[ halfNo ].bCbd[ 0 ] + ctl[ 3 ];
		
		for( int i=0; i<5; i++ ) {
			ctlPnt[ halfNo*5 + i ][ 0 ] = ctl[ i ].x;
			ctlPnt[ halfNo*5 + i ][ 1 ] = ctl[ i ].y;
			ctlPnt[ halfNo*5 + i ][ 2 ] = ctl[ i ].z;
		}
	}

	public static final int
	NumVertexStd( int numDivU, int numDivV )
	{
		return  ( numDivU + 1 ) * ( numDivV + 1 );
	}
	
	public static final int
	NumTriIndexStd( int numDivU, int numDivV )
	{
		return  numDivU * numDivV * 2;
	}

}
