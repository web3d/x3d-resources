//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvWorld.java
//

package jp.co.lattice.vKernel.core.b0;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel �̃��[���h���N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvWorld {

	/**
	 * Java Lattice Kernel �̏������֐��B									<br>
	 * Java Lattice Kernel ���g�p����ꍇ�A�K���ŏ��ɓ��֐������s����		<br>
	 * <pre>
	 * (( �� ))
	 *      lvGlobal    mGlobal  = lvWorld.Init();
	 *                      �
	 *                      �
	 *      lvToKernel  toKernel = new lvToKernel( mGlobal );
	 * </pre>
	 * Init()�𕡐���s�����Ƃɂ��A���f���i�V�F���̏W���j�̍č쐬���s������A���f���𕡐��Ƃ��邱�Ƃ��ł���
	 *
	 * @return			lvGlobal�f�[�^���o�́B�����C�u�����̃R���X�g���N�^�́A���̒l�������Ƃ�����̂�����
	 */
	public static final lvGlobal
	Init()
	{
		lvGblCore  gblCore = new lvGblCore();
		lvGlobal   gbl     = new lvGlobal( gblCore );
		
		return  gbl;
	}
}
