//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFace.java
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * Round���̍쐬�N���X�i��ʃ��C���[�j
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvDivFace extends lvRoot {
	
	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvDivFace( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1�̖ʂ��e�Z���[�V��������
	 * @param  shellNo		(( I )) lvToKernel.SetNumShell( num ) �ƃZ�b�g�����ꍇ�A0 �` ( num-1 )
	 * @param  gsNo			(( I )) �z�� lvToKernel.gsNumVtx[] �̒����� n �̏ꍇ�A0 �` ( n-1 )
	 * @return				���ʃ��C���[(geom0)���瑗����f�[�^
	 */
	public lvDivFaceType.UpDivFace
	Exec( int shellNo, int gsNo ) throws lvThrowable
	{
		return null;
	}

}
