//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFaceType.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * DivPoly���̍쐬�N���X�i���ʃ��C���[�j
 * @author	  created by Eishin Matsui (00/04/03-)
 * 
 */
public class lvDivFaceType {

	/**
	 * �P�̃n�[�t�G�b�W�Ɋւ���u��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivFace�j�̓����N���X�v
	 */
	public static class DownHalf {
		
		/** ���_�i�n�[�t�G�b�W�n�_�j���W						*/
		public lvVector  pos					= null;
		
		/** �n���h���x�N�g���i�C���f�b�N�X 0:�n�_�A 1:�I�_�j	*/
		public lvVector  handVec[/*2*/]			= null;

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
		}
	}
	
	/**
	 * �V�F�������Ɋւ���u��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivFace�j�̓����N���X�v
	 */
	public static class DownAttr {
	
		/** �Ő��������i 2�̔{�� �j --- �����l�F4					*/
		public int		numDiv		= 4;
	}
	
	/**
	 * ��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivFace�j�̓����N���X
	 */
	public static class DownDivFace {
		
		/** ���̖ʂ̃n�[�t�G�b�W���i��half[] �̗L���z�񐔁u 3�ȏ� �v�j	*/
		public int	     numHalf;
		/** �n�[�t�G�b�W���z��i1�̖ʂ̃n�[�t�G�b�W���ȏ�̒����ő��݂���u 3�ȏ� �v�j --- �����l null		*/
		public DownHalf  half[]				= null;
		
		/** �ʂ̒��S�ʒu���W�Ɩʖ@��		*/
		public lvRec.PosNorHi  center		= null;
		
		/** �V�F���� �������				*/
		public DownAttr        attr			= new DownAttr();
	
		/** * UV��ԏ��	*/
		public lvDivFaceUVtype.DownDivFaceUV  divFaceUV = new lvDivFaceUVtype.DownDivFaceUV();

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  DownDivFace( lvGlobal dt )
		{
			center = new lvRec.PosNorHi( dt );
		}
	}
		
	/**
	 * ��ʃ��C���[(topo0)�ɑ���f�[�^�p�iUpDivFace�j�̓����N���X
	 */
	public static class UpDivFace {
		
		/**
		 * ���_���W�̔z��i���_���������݂���u 3�ȏ� �v�j --- �����l null										<br>
		 * ���_���͗Ő��������ulvToKAttr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
		 */
		public lvRec.PosNorHi  vertex[]			= null;
	
		/**
		 * 3���_No.Index���琬��O�p�|���S���̔z��i�O�p�|���S�����������݂���u 1�ȏ� �v�j --- �����l null	<br>
		 * �O�p�|���S�����͗Ő��������ulvToKAttr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
		 */
		public lvRec.TriIndex  triIndex[]		= null;
		
		/** * UV��ԏ��	*/
		public lvDivFaceUVtype.UpDivFaceUV  divFaceUV = new lvDivFaceUVtype.UpDivFaceUV();
	}
	
	/**
	 * �����Ő��Ɋւ���uDownDivFace����h�������f�[�^�̓����N���X�v
	 */
	public static class DeriveInner {
		
		/** �n���h���x�N�g���i�C���f�b�N�X 0:�����Ő��̊O���Ő���̓_�A 1:�����Ő��̒��S���̓_�j	*/
		public lvVector  handVec[/*2*/]			= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  DeriveInner( lvGlobal dt )
		{
			handVec = new lvVector[ 2 ];
			for( int i=0; i<2; i++ )
				handVec[ i ] = new lvVector( dt );
		}
	}
	
	/**
	 * DownDivFace����h�������f�[�^�̓����N���X
	 */
	public static class DeriveDivFace {
		
		/** �����Ő����W�܂钆�S�_�̈ʒu�Ɩ@��				*/
		public lvRec.PosNorHi  center			= null;
		
		/** �����Ő��f�[�^�z��i�����͓����Ő����j			*/
		public DeriveInner     inner[]			= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  DeriveDivFace( lvGlobal dt )
		{
			center = new lvRec.PosNorHi( dt );
		}
	}

}
