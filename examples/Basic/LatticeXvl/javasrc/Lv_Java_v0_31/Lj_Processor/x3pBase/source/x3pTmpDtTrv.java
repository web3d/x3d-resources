//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTmpDtTrv.java

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTmpDtTrv {

 	public static class ToKernelVtxGs {
 		
 		/** �ʏ�̒��_���H false:�ʏ�ɖ����Atrue:�ʏ�ɗL��	*/
		public boolean  onFace;
		
		/** �V�����U��ꂽ���_No.		*/
		public int      newVtxNo;
		
		/** XYZ�f�[�^					*/
		public lvVecDt  xyz			= new lvVecDt();
	}

 	public static class ToKernelVtxUV {
 		
		/**
		 * �u���̒��_��������UV��ԁi�e�N�X�`���j�v�̒��Łu���̒��_��UV�l�������Ă���UV��ԁv�̑�����	<br>
		 * ����BUV��Ԃ����݂��Ȃ��ꍇ�₻�̒��_��UV�l�������Ă��Ȃ��ꍇ�́A0 �Ƃ���B					<br>
		 * �����_���e�N�X�`�����E�ł��鎞��A�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�ŁA		<br>
		 * ���̒��_������UV�l�����ꍇ�́A2 �ȏ�ƂȂ�													<br>
		 * --- �����l null
		 */
		public lvRec.SeqPart  vtxNumUV		= new lvRec.SeqPart();
		
		/** ���_�ɑ΂���u���ݍ�ƒ���UV����No�v		*/
		public int            currentUVoffset;
		
		/** ���_�ɑ΂���u���ݍ�ƒ���UVspaceNo�v		*/
		public int            currentUVspaceNo;
	}
	
}
