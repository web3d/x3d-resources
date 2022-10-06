//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pShellInfo.java
//

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vProcessor.com.*;

import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * 1�̃V�F���Ɋւ���uJava XVL3 Processor �����ʊK�w�Ɉ����n���f�[�^�v�̃N���X
* @author	  created by Eishin Matsui (00/03/11-)
 */
public class x3pShellInfo {
	
	/**
	 * �C���X�^���X�Ɋւ�������N���X										<br>
	 * fromKernel[]���̒��_�ʒu�A�@���x�N�g���́A���̂܂܂ł͎g�p�����A		<br>
	 * ���̒��̏��i�Ⴆ�Β��_�ʒu�}�g���N�X�j�Ƒg�ݍ��킹�Ďg�p����
	*/
	public static class Instance {
		/**
		 * ���_�ʒu�}�g���N�X										<br>
		 * m = posMat.m;�im[4][4]�̔z��j�Ƃ����ƁA					<br>
		 *		xd = x*m[0][0] + y*m[1][0] + z*m[2][0] + m[3][0]	<br>
		 *		yd = x*m[0][1] + y*m[1][1] + z*m[2][1] + m[3][1]	<br>
		 *		zd = x*m[0][2] + y*m[1][2] + z*m[2][2] + m[3][2]
		*/
		public x3pMatrix  posMat		= null;
		
		/**
		 * �@���x�N�g���}�g���N�X								<br>
		 * m = noramlMat.m;�im[4][4]�̔z��j�Ƃ����ƁA			<br>
		 *		xd = x*m[0][0] + y*m[1][0] + z*m[2][0]			<br>
		 *		yd = x*m[0][1] + y*m[1][1] + z*m[2][1]			<br>
		 *		zd = x*m[0][2] + y*m[1][2] + z*m[2][2]			<br>
		 * normalMat�́A										<br>
		 * 		posMat��m[3][i]�ii=0�`2)��0�Ƃ��A				<br>
		 * 		�t�]�u�s��(inverse transpose Matrix)�ɕϊ�		<br>
		 *      �����}�g���N�X									<br>
		 * �ł���i�ϊ���̒�����1.0�Ƃ͌���Ȃ��j
		 */
		public x3pMatrix  normalMat		= null;

		/** �\���^��\��  false: ��\���Atrue: �\��		*/
		public boolean    visibility	= true;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Instance( x3pGlobal dt )
		{
			posMat    = new x3pMatrix( dt );
			normalMat = new x3pMatrix( dt );
		}
	}
	
    /** Faces�Ɋւ�������N���X	*/
	public static class Faces {
		/**
		 * �uJava Lattice Kernel �����ʊK�w�Ɉ����n���f�[�^�v�̔z��		<br>
		 *  	--- �uFaces����Face�v�̐��������݂���
		 */
		public lvFromKernel  fromKernel[]	= null;
		
		/**
		 * Face���̔z��i�z�񒷂́AfromKernel�Ɠ������Ȃ�j
		 */
		public Face          face[]			= null;
		
		/** �}�e���A��Index */
		public int           materialIndex	= -1;
		
		/** �e�N�X�`���̑��݂̗L�� ��:false, �L:true --- �����l�Ffalse */
		public boolean       enableTexture	= false;
		
		/** �e�N�X�`��Index */
		public int           textureIndex	= -1;
	}
	
    /** Face�Ɋւ�������N���X	*/
	public static class Face {
		/**
		 * �e�N�X�`����UV���W�l�B�e�N�X�`�������݂��Ȃ����́Anull			<br>
		 * �i�z�񒷂́AlvFromKernel.vertex �Ɠ������Ȃ�j--- �����l null
		 */
		public lvUVdt  uv[]		= null;
	}
	

	/**
	 * �u�C���X�^���X�Ɋւ�����v�̔z��														<br>
	 *		�C���X�^���X��1�Ƃ͌��炸�A�������݂���Ƃ��́A���̐������\������邱�ƂɂȂ�		<br>
	 *		--- �u�g�b�v�m�[�h���瓖�V�F���Ɏ��郋�[�g�v�̐��������݂���
	 */
	public Instance  instance[]	= null;
	
	/** Faces �̔z�� --- �u���V�F������Faces�v�̐��������݂��� */
	public Faces     faces[]	= null;
	
}
