//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvUVpublic.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel �̃V�F���� �u�O��UV��ԁi�e�N�X�`���p�j�v���N���X
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvUVpublic {

	/**
	 * �u�i�����jUV���No.�v����UV��ԏ��̓����N���X
	 */
	public static class UVspace {
		
		/** �O��UV���No.		*/
		public int  uvPublicNo;
		
	}
	
// -------------------------------------------------------------------

	/**
	 * �u�O��UV���No.�v�Ɓu�i�����jUV���No.�v�̑Ή��e�[�u���i���V�F�����̊O��UV��Ԃ̐�����	<br>
	 *  ���݂���u0�ȏ�v�j�BlvToKernelUV.numUVspace �͊O��UV��Ԃ̐��ł���A					<br>
	 *  lvToKernelUV.GsUV ��lvToKernelUV.VtxUV ���� uvSpaceNo �́A�O��UV���No.�ł���B			<br>
	 * �u�O��UV���No.�v��,�P��܂��͕����́uUV���No.�v�������Ă���B							<br>
	 *  �܂� uvPablic[].num �̍��v�́A�i�����jUV���No.�̌��ƈ�v����
	 */
	public lvRec.SeqPart  uvPublic[]			= null;
	
	/**
	 * �u�i�����jUV���No.�v����UV��ԏ��̔z��												<br>
	 * �i���V�F�����́i�����jUV��Ԃ̐��������݂���u0�ȏ�v�j--- �����l null
	 */
	public UVspace        uvSpace[]				= null;
	
	
// -------------------------------------------------------------------

/*
	public final void
	NewUvPablic( int num )
	{
		uvPublic = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			uvPublic[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewUvSpace( int num )
	{
		uvSpace = new UVspace[ num ];
		for( int i=0; i<num; i++ )
			uvSpace[ i ] = new UVspace();
	}
*/

}
