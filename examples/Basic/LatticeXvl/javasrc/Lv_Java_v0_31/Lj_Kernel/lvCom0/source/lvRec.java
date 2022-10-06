//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvRec.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel ���ŋ��ʂɈ����\���̂̃N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvRec {

	/**
	 * 1�̒��_�ɑ΂�����W�ʒu�Ɩ@���x�N�g���̓����N���X�ilvVecDt�p�j
	 */
	public static class PosNorLow {
	
		/** ���_�ʒu		*/
		public lvVecDt	pos			= new lvVecDt();
	
		/** �@���x�N�g��	*/
		public lvVecDt	normal		= new lvVecDt();

	// -------------------------------------------------------------------

		/**
		 * �R�s�[�֐�
		 * @param  src		(( I )) �R�s�[��
		 * @param  dst		(( O )) �R�s�[��
		 */
/*
		public static final void
		Copy( PosNorLow src, PosNorLow dst )
		{
			lvVecDt.Copy( src.pos,    dst.pos );
			lvVecDt.Copy( src.normal, dst.normal );
		}
*/
	}

	/**
	 * 1�̒��_�ɑ΂�����W�ʒu�Ɩ@���x�N�g���̓����N���X�ilvVector�p�j
	 */
	public static class PosNorHi {
	
		/** ���_�ʒu		*/
		public lvVector  pos		= null;
	
		/** �@���x�N�g��	*/
		public lvVector  normal		= null;

	// -------------------------------------------------------------------

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  PosNorHi( lvGlobal dt )
		{
			pos    = new lvVector( dt );
			normal = new lvVector( dt );
		}

	// -------------------------------------------------------------------

		/**
		 * �R�s�[�֐�
		 * @param  src		(( I )) �R�s�[��
		 * @param  dst		(( O )) �R�s�[��
		 */
/*
		public static final void
		Copy( PosNorHi src, PosNorHi dst )
		{
			dst.pos.Assign( src.pos );
			dst.normal.Assign( src.normal );
		}
*/

		/**
		 * PosNorHi�f�[�^��PosNorLow�f�[�^�ɕϊ�����
		 * @param  src		(( I )) PosNorHi�f�[�^
		 * @param  dst		(( O )) PosNorLow�f�[�^
		 */
		public static final void
		HiToLow( PosNorHi src, PosNorLow dst )
		{
			src.pos.Vector2VecDt( dst.pos );
			src.normal.Vector2VecDt( dst.normal );
		}
	}

	/**
	 * ������`�f�[�^�z��ɑ΂��Ĉꕔ�������o���ۂ́A�J�n�_�ƌ����L�q����N���X
	 */
	public static class SeqPart {
	
		/** �J�n�_ */
		public int	start;
	
		/** ��   */
		public int  num;

	// -------------------------------------------------------------------

		/**
		 * �R�s�[�֐�
		 * @param  src		(( I )) �R�s�[��
		 * @param  dst		(( O )) �R�s�[��
		 */
		public static final void
		Copy( SeqPart src, SeqPart dst )
		{
			dst.start = src.start;
			dst.num   = src.num;
		}
	}

	/**
	 * 1�̎O�p�|���S���̒��_No����L�q����N���X
	 */
	public static class TriIndex {
	
		/** �O�p�|���S���̒��_No�̔z��i���� = 3 �j		*/
		public int	vtxNo[/*3*/]	= new int[ 3 ];

	// -------------------------------------------------------------------

		/**
		 * �R�s�[�֐�
		 * @param  src		(( I )) �R�s�[��
		 * @param  dst		(( O )) �R�s�[��
		 */
		public static final void
		Copy( TriIndex src, TriIndex dst )
		{
			for( int i=0; i<3; i++ )
				dst.vtxNo[ i ] = src.vtxNo[ i ];
		}
	}
	
	/**
	 * ���W�n���L�q����N���X
	 */
	public static class CoordSys {
	
		/** ���_	*/
		public lvVector  org		= null;
		/** x��		*/
		public lvVector	 xAxis		= null;
		/** y��		*/
		public lvVector	 yAxis		= null;
		/** z��		*/
		public lvVector	 zAxis		= null;

	// -------------------------------------------------------------------

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  CoordSys( lvGlobal dt )
		{
			org   = new lvVector( dt );
			xAxis = new lvVector( dt );
			yAxis = new lvVector( dt );
			zAxis = new lvVector( dt );
		}

	// -------------------------------------------------------------------

		/**
		 * �R�s�[�֐�
		 * @param  src		(( I )) �R�s�[��
		 * @param  dst		(( O )) �R�s�[��
		 */
/*
		public static final void
		Copy( CoordSys src, CoordSys dst )
		{
			dst.org.Assign( src.org );
			dst.xAxis.Assign( src.xAxis );
			dst.yAxis.Assign( src.yAxis );
			dst.zAxis.Assign( src.zAxis );
		}
*/
	}
	
}
