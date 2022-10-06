//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvFromKernelLow.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.lvFromKernelUV;
import	jp.co.lattice.vKernel.core.g0.lvDivPolyLow;


/**
 * Java Lattice Kernel �����ʊK�w�Ɉ����n���f�[�^�̃N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvFromKernelLow extends lvRoot {
	
	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvFromKernelLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * ���_���W�̔z��i���_�̐��������݂���u 3�ȏ� �v�j --- �����l null									<br>
	 * ���_���͗Ő��������ulvToKernelType.Attr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
	 */
	public lvRec.PosNorLow  vertex[]		= null;
	
	/**
	 * 3���_No.Index���琬��O�p�|���S���̔z��i�O�p�|���S���̐��������݂���u 1�ȏ� �v�j --- �����l null	<br>
	 * �O�p�|���S�����͗Ő��������ulvToKernelType.Attr.numDiv �� lvToKernel.SetAttr() �ŃZ�b�g�v�ȂǂňقȂ�
	 */
	public lvRec.TriIndex   triIndex[]		= null;

	/**
	 * UV��ԏ��i�e�N�X�`���p�j�B���V�F����UV��ԁi�e�N�X�`���j�������Ȃ����́Anull �ƂȂ�B	<br>
	 * ���d�}�b�s���O�̏ꍇ�́A�z�񒷂� 2 �ȏ�ƂȂ� --- �����l null
	 */
	public lvFromKernelUV    uvSpace[]		= null;
	
}
