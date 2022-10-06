//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFaceUVtype.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/04/03-)
 * 
 */
public class lvDivFaceUVtype {
	
	/**
	 * 1��UV��Ԃɑ΂���u��ʃ��C���[(topo0)�ɑ���f�[�^�p�iDownDivFaceUV�j�v�̓����N���X
	 */
	public static class DownDivFaceUVone {
		
		/** vtxUV[] �̗L���z�񐔁u 3�ȏ� �v	*/
		public int	   numVtxUV;
		
		/** ���_��UV���W�̔z��i1�̃|���S���̒��_���ȏ�̒����ő��݂���u 3�ȏ� �v�j --- �����l null		*/
		public lvUVdt  vtxUV[]				= null;
		
		/** GS�ʒ��S��UV�l		*/
		public lvUVdt  center				= new lvUVdt();
	}

	/**
	 * ��ʃ��C���[(topo0)�ɑ���f�[�^�p�iDownDivFaceUV�j�̓����N���X
	 */
	public static class DownDivFaceUV {
		
		/** UV��ԏ��z��			*/
		public DownDivFaceUVone  uvSpace[]	= null;
	}
		
	/**
	 * 1��UV��Ԃɑ΂���u��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivFaceUV�j�v�̓����N���X
	 */
	public static class UpDivFaceUVone {
		
		/**
		 * ���_UV���W�̔z��i���_���������݂���u 3�ȏ� �v�j --- �����l null						<br>
		 * ���_���͗Ő��������ulvToKAttr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
		 */
		public lvUVdt  uv[]		= null;
	}

	/**
	 * ��ʃ��C���[(topo0)���瑗����f�[�^�p�iDownDivFaceUV�j�̓����N���X
	 */
	public static class UpDivFaceUV {
		
		/** UV��ԏ��z��			*/
		public UpDivFaceUVone  uvSpace[]	= null;
	}
	
}