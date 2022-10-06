//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernelUV.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * UV��ԁi�e�N�X�`���j�Ɋւ���u��ʊK�w���� Java Lattice Kernel �Ɉ����n���f�[�^�v�̃N���X
 * @author	  created by Eishin Matsui (00/03/27-)
 * 
 */
public class lvToKernelUV {

	/**
	 * GS�ʂɑ΂���UV��ԏ��̓����N���X
	 */
	public static class GsUV {
		
		/** UV���No.	*/
		public int  uvSpaceNo;
	}
	 
	/**
	 * ���_�ɑ΂���UV��ԏ��̓����N���X
	 */
	public static class VtxUV {
	
		/** UV���No.	*/
		public int     uvSpaceNo;
	
		/**
		 * UV���W�l
		 */
		public lvUVdt  uv		= new lvUVdt();
	}

// -------------------------------------------------------------------

	/**
	 * ���V�F������UV��ԁi�e�N�X�`���j�̐����L�q����											<br>
	 * ����ɂ��AUV���No.�́A0 �` ( numUVspace - 1 ) �ƂȂ� --- �����l 0
	 */
	public int  numUVspace;
	
	/**
	 * �e�v�f�ɂ́A����GS�ʂ�������UV��ԁi�e�N�X�`���j�̐�������BUV��Ԃ����݂��Ȃ��ꍇ�́A	<br>
	 * 0 �Ƃ���B�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�́A2 �ȏ�ƂȂ�				<br>
	 * �i�z�񒷂́AlvToKernel.gsNumVtx �Ɠ���������j--- �����l null
	 */
	public int  gsNumUV[]					= null;
	
	/**
	 * GS�ʂɑ΂���UV��ԏ��̔z��BGS�ʂ�n����ꍇ�A 										<br>
	 *     GS��0��UV��ԏ���, GS��1��UV��ԏ���, ---, GS��( n-1 )��UV��ԏ��� 			<br>
	 * �Ƒ����B�z��̒����́A 																	<br>
	 *     gsNumUV[0] + gsNumUV[1] + --- + gsNumUV[n-1] 										<br>
	 * �ƂȂ�B --- �����l null
	 */
	public lvToKernelUV.GsUV  gsUVseq[]		= null;
	
	/**
	 * �e�v�f�ɂ́A�u���̒��_��������UV��ԁi�e�N�X�`���j�v�̒��Łu���̒��_��UV�l�������Ă���		<br>
	 * UV��ԁv�̑���������BUV��Ԃ����݂��Ȃ��ꍇ�₻�̒��_��UV�l�������Ă��Ȃ��ꍇ�́A0 �Ƃ���B	<br>
	 * �����_���e�N�X�`�����E�ł��鎞��A�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�ŁA		<br>
	 * ���̒��_������UV�l�����ꍇ�́A2 �ȏ�ƂȂ�i�z�񒷂́AlvToKernel.vertex �Ɠ���������j		<br>
	 * --- �����l null
	 */
	public int  vtxNumUV[]					= null;
	
	/**
	 * ���_�ɑ΂���UV��ԏ��̔z��B���_��n����ꍇ�A 										<br>
	 *     ���_0��UV��ԏ���, ���_1��UV��ԏ���, ---, ���_( n-1 )��UV��ԏ��� 			<br>
	 * �Ƒ����B�z��̒����́A 																	<br>
	 *     vtxNumUV[0] + vtxNumUV[1] + --- + vtxNumUV[n-1] 										<br>
	 * �ƂȂ�B�z�񒷂� 0 �̎��́Anull �Ƃ��� --- �����l null
	 */
	public lvToKernelUV.VtxUV  vtxUVseq[]	= null;
	
}
