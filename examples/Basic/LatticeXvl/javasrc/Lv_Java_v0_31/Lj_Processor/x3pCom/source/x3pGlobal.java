//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// x3pGlobal.java
//

package jp.co.lattice.vProcessor.com;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * �O���[�o���ϐ��p�̃N���X�B																						<br>
 * �m�[�h�c���[�⃂�f���i�V�F���̏W���j���� x3pGlobal�f�[�^���ɕێ�����Ă���̂ŁAx3pWorld.Init()�𕡐���		<br>
 * �s�����Ƃɂ��A�m�[�h�c���[�����f���̍č쐬���s������A�m�[�h�c���[�����f���𕡐��Ƃ��邱�Ƃ��ł���B			<br>
 *																													<br>
 * lvGlobal ���p�����Ă���̂ŁA																					<br>
 *		lvError.Message( ??? ) �̈����i???�̕����j																	<br>
 * �Ƃ��邱�Ƃ��\�ł���B																							<br>
 *																													<br>
 * ���������݂̂Ƃ���A x3pGlobal �� lvError.Message() �ȊO�� Java Lattice Kernel �̃��\�b�h�i�R���X�g���N�^		<br>
 * �܂ށj�̈����Ƃ��邱�Ƃ́A�ۏ؂��Ȃ��B���� lvToKernel�R���X�g���N�^�̈����Ƃ��邱�Ƃ͋ւ���
 *
 * @author	  created by Eishin Matsui (00/03/11-)
 */
public class x3pGlobal extends lvGlobal {
	
	private x3pGblCoreNull  gblCoreX3p = null;
	
	public lvGblElm  GBaseX3p()		{  return gblCoreX3p.GBaseX3p();   }
	public lvGblElm  GNodeX3p()		{  return gblCoreX3p.GNodeX3p();   }
	public lvGblElm  GParseX3p()	{  return gblCoreX3p.GParseX3p();  }
	public lvGblElm  GComX3p()		{  return gblCoreX3p.GComX3p();    }
	
	public x3pGlobal( lvGblCoreNull dt0, x3pGblCoreNull dt1 )
	{
		super( dt0 );
		
		gblCoreX3p = dt1;
		dt1.Init( this );
	}
	
}
