//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivPolyUV.java
//

package jp.co.lattice.vKernel.tex.a0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvDivPolyUV extends lvRoot {
	
	/**
	 * 1��UV��Ԃɑ΂���u��ʃ��C���[(topo0)�ɑ���f�[�^�p�iDownDivPolyUV�j�v�̓����N���X
	 */
	public static class DownDivPolyUVone {
		
		/** vtxUV[] �̗L���z�񐔁u 3�ȏ� �v	*/
		public int	   numVtxUV;
		
		/** ���_��UV���W�̔z��i1�̃|���S���̒��_���ȏ�̒����ő��݂���u 3�ȏ� �v�j --- �����l null		*/
		public lvUVdt  vtxUV[]				= null;
	}

	/**
	 * ��ʃ��C���[(topo0)�ɑ���f�[�^�p�iDownDivPolyUV�j�̓����N���X
	 */
	public static class DownDivPolyUV {
		
		/** UV��ԏ��z��			*/
		public DownDivPolyUVone  uvSpace[]	= null;
	}
		
	/**
	 * 1��UV��Ԃɑ΂���u��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivPolyUV�j�v�̓����N���X
	 */
	public static class UpDivPolyUVone {
		
		/** vertex[] �̗L���z�񐔁u 3�ȏ� �v	*/
		public int	   numVertex;
		/**
		 * ���_UV���W�̔z��i���_�̐��� MAX���������݂���u 3�ȏ� �v�j --- �����l null				<br>
		 * n�p�`�� MAX�� --- n����̎�: n + ( n - 3 ) / 2,	n�������̎�: n + ( n - 2 ) / 2
		 */
		public lvUVdt  vertex[]				= null;
	}

	/**
	 * ��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivPolyUV�j�̓����N���X
	 */
	public static class UpDivPolyUV {
		
		/** UV��ԏ��z��			*/
		public UpDivPolyUVone  uvSpace[]	= null;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvDivPolyUV( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	public void
	SetDownDivPolyUV( int shellNo, int gsNo, lvDivPolyUV.DownDivPolyUV downDivPolyUV0 ) throws lvThrowable
	{
	}
	
	public void
	SetUpDivPolyUV()
	{
	}
	
	public void
	Finish()
	{
	}

}
