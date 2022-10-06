//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pWorld.java
//

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vProcessor.com.*;
import  jp.co.lattice.vKernel.core.b0.*;


/**
 * Java XVL3 Processor �̃��[���h���N���X
 * @author	  created by Eishin Matsui (00/03/11-)
 * 
 */
public class x3pWorld {

	/**
	 * Java XVL3 Processor �̏������֐��B									<br>
	 * Java XVL3 Processor ���g�p����ꍇ�A�K���ŏ��ɓ��֐������s����		<br>
	 * <pre>
	 * (( �� ))
	 *      x3pGlobal       mGlobal   = x3pWorld.Init();
	 *                             �
	 *                             �
	 *      x3pToProcessor  toProcess = new x3pToProcessor( mGlobal );
	 * </pre>
	 * Init()�𕡐���s�����Ƃɂ��A�m�[�h�c���[�⃂�f���i�V�F���̏W���j�̍č쐬���s������A		<br>
	 * �m�[�h�c���[�����f���𕡐��Ƃ��邱�Ƃ��ł���
	 *
	 * @return			x3pGlobal�f�[�^���o�́B�����C�u�����̃R���X�g���N�^�́A���̒l�������Ƃ�����̂�����
	 */
	public static final x3pGlobal
	Init()
	{
		lvGblCore   gblCore    = new lvGblCore();
		x3pGblCore  gblCoreX3p = new x3pGblCore();
		x3pGlobal   gbl        = new x3pGlobal( gblCore, gblCoreX3p );
		
		return  gbl;
	}
}
