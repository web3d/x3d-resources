//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernelLow.java
//

package jp.co.lattice.vKernel.core.t0;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.lvToKernelUV;


/**
 * ��ʊK�w���� Java Lattice Kernel �Ɉ����n���f�[�^�̃N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvToKernelLow extends lvRoot {

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvToKernelLow( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * GS�ʂ̊e���_���i 3�ȏ� �j�̔z��iGS�ʂ̐��������݂���u 1�ȏ� �v�j --- �����l null
	 */
	public int	gsNumVtx[]		= null;
	
	/**
	 * GS�ʂ�n����ꍇ�A												<br>
	 * GS��0�̒��_No��, GS��1�̒��_No��, ---, GS��( n-1 )�̒��_No��		<br>
	 * �Ƒ����B�z��̒����́A											<br>
	 * GS��0�̒��_�� + GS��1�̒��_�� + --- + GS��( n-1 )�̒��_��		<br>
	 * �ƂȂ�B --- �����l null
	 */
	public int	gsVtxSeq[]		= null;
	
	/**
	 * NG�ʁiconstric�̏ꍇ�j�̊e���_���i 3�ȏ� �j�̔z��iNG�ʂ̐��������݂���j --- �����l null   <br>
	 * NG�ʂ̐���0�̂Ƃ��́Anull�Ƃ��Ă���
	 */
	public int	ngNumVtx[]		= null;
	
	/**
	 * NG�ʁiconstric�̏ꍇ�j��n����ꍇ�A							<br>
	 * NG��0�̒��_No��, NG��1�̒��_No��, ---, NG��( n-1 )�̒��_No��		<br>
	 * �Ƒ����B�z��̒����́A											<br>
	 * NG��0�̒��_�� + NG��1�̒��_�� + --- + NG��( n-1 )�̒��_��		<br>
	 * �ƂȂ�B --- �����l null										<br>
	 * NG�ʂ̐���0�̂Ƃ��́Anull�Ƃ��Ă���
	 */
	public int	ngVtxSeq[]		= null;
	
	
	/**
	 * �Ő���񕔂̔z��i�ۂߌW�������Ő����ȏ�̒��������uMax:�S�Ő����v�j --- �����l null   <br>
	 * �Ő���񕔂̐���0�̂Ƃ��́Anull�Ƃ��Ă���
	 */
	public lvToKernelType.Edge    edge[]	  = null;
	
	
	/**
	 * ���_��񕔂̔z��i���_���������݂���u 3�ȏ� �v�j --- �����l null
	 */
	public lvToKernelType.Vertex  vertex[]    = null;

	/**
	 * UV��ԏ��i�e�N�X�`���p�j�B���V�F����UV��ԁi�e�N�X�`���j�������Ȃ����́Anull �Ƃ���
	 */
	public lvToKernelUV           uvSpace     = null;
	
}
