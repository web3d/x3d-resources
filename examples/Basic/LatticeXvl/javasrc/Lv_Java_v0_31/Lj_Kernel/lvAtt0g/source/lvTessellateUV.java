//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvTessellateUV.java
//

package jp.co.lattice.vKernel.tex.a0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * 1�̖ʂ��쐬����N���X
 * @author	  created by Eishin Matsui (00/04/02-)
 * 
 */
public class lvTessellateUV extends lvRoot {
	
	/**
	 * 1�{�̃n�[�t�G�b�W�_�Ɋւ���ulvTessellate���瑗����f�[�^�p�̓����N���X�v
	 */
	public static class DownTessellateUVone {
		
		/**
		 * ���_�i�n�[�t�G�b�W�n�_�jUV���W�̔z��iDownTessellate.isDegenerate == true				<br>
		 * �̎��́A�u�n�[�t�G�b�WNo0 == �n�[�t�G�b�WNo3�v�j
		 */
		public lvUVdt  uv[/*4*/]		= null;
		
		/**
		 * �R���X�g���N�^
		 */
		public  DownTessellateUVone()
		{
			uv = new lvUVdt[ 4 ];
			for( int i=0; i<4; i++ )
				uv[ i ] = new lvUVdt();
		}
	}
	
	/**
	 * lvTessellate���瑗����f�[�^�p�̓����N���X
	 */
	public static class DownTessellateUV {
		
		/** uvSpace[] �̗L���z��		*/
		public int                  numUVspace;
		
		/** UV��ԏ��z��			*/
		public DownTessellateUVone  uvSpace[]	= null;
	}
	
	/**
	 * lvTessellate�ɑ���f�[�^�p�̓����N���X
	 */
	public static class UpTessellateUVone {
		
		/**
		 * ���_UV���W�̔z��i���_���������݂���u 3�ȏ� �v�j --- �����l null						<br>
		 * ���_���͗Ő��������ulvToKAttr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
		 */
		public lvUVdt  uv[]		= null;
	}
	
	/**
	 * lvTessellate�ɑ���f�[�^�p�̓����N���X
	 */
	public static class UpTessellateUV {
		
		/** UV��ԏ��z��				*/
		public UpTessellateUVone  uvSpace[]		= null;
	}
	    
// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public lvTessellateUV( lvGlobal dt )
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
	}
	
	public void
	NewUpDivFaceUV( int numVertex )
	{
	}
	
	public void
	NewUpTessellateUV( lvRec.SeqPart infoVertex )
	{
	}

	public void
	SetQuad()
	{
	}
	
	public void
	SetGtQuad( int patchNo, int numPatch ) throws lvThrowable
	{
	}

	public void
	Finish()
	{
	}

}
