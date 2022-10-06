//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvMakeUVspaceType.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/05/13-)
 * 
 */
public class lvMakeUVspaceType {

	public static class UVpublicVtx {
		
		/** �i�����jUV���No.		*/
		public int     uvSpaceNo;
		
		public lvUVdt  st						= null;
	}
	
	public static class UVpublic {

		/**
		 * �e�v�f�ɂ́A����GS�ʂ�������UV��ԁi�e�N�X�`���j�̐�������BUV��Ԃ����݂��Ȃ��ꍇ�́A	<br>
		 * 0 �Ƃ���B�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�́A2 �ȏ�ƂȂ�				<br>
		 * �i�z�񒷂́AlvToKernel.gsNumVtx �Ɠ���������j--- �����l null
		 */
		public lvRec.SeqPart  gsNumUV[]			= null;
		
		/**
		 * GS�ʂɑ΂���i�����jUV���No.�̔z��BGS�ʂ�n����ꍇ�A 								<br>
		 *     GS��0��UV���No.��, GS��1��UV���No.��, ---, GS��( n-1 )��UV���No.�� 				<br>
		 * �Ƒ����B�z��̒����́A 																	<br>
		 *     gsNumUV[0] + gsNumUV[1] + --- + gsNumUV[n-1] 										<br>
		 * �ƂȂ�B --- �����l null
		 */
		public int            gsUVseq[]			= null;

		/**
		 * �e�v�f�ɂ́A�u���̒��_��������i�����jUV��ԁi�e�N�X�`���j�v�̐�������BUV��Ԃ�			<br>
		 * ���݂��Ȃ��ꍇ�́Anum = 0 �Ƃ���B�����_��UV��Ԃ������Ă��鎞�́A�K�� num >= 1�ƂȂ�B	<br>
		 * �����_���e�N�X�`�����E�ł��鎞��A�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�́A	<br>
		 * num >= 2 �ƂȂ�i���_���������݂���u3�ȏ�v�j--- �����l null
		 */
		public lvRec.SeqPart  vtxUV[]			= null;
	
		/**
		 * ���_�ɑ΂���UV��ԏ��̔z��B���_��n����ꍇ�A 										<br>
		 *     ���_0��UV��ԏ���, ���_1��UV��ԏ���, ---, ���_( n-1 )��UV��ԏ��� 			<br>
		 * �Ƒ����B�z��̒����́A 																	<br>
		 *     vtxUV[0] + vtxUV[1] + --- + vtxUV[n-1] 												<br>
		 * �ƂȂ�B�z�񒷂� 0 �̎��́Anull �Ƃ��� --- �����l null
		 */
		public UVpublicVtx   vtxUVseq[]			= null;
		
	}
	
	/** ����UV��Ԃ̋ȖʏW������1���E�Z�O�����g�̒���1�n�[�t�G�b�W���		*/
	public static class HalfInfo {

		/** GS��No				*/
		public int	gsNo;
		
		/** �n�[�t�G�b�WNo		*/
		public int	halfNo;
	}
	
	/** ����UV��Ԃ̋ȖʏW������1���E�Z�O�����g���					*/
	public static class BoundOne {

		/** 1�Ȑ����i���E�Z�O�����g���̋Ȑ��̐��������݂���j		*/
		public HalfInfo  half[]					= null;
	}
	
	/** UV��Ԃŋ敪�����ꂽ�ȖʏW���̋��E�Z�O�����g���			*/
	public static class BoundBase {

		/**
		 * �eUV��Ԃɂ����鋫�E�Z�O�����g�̐��ƁAuvBndSeq[]���̊J�n�_������									<br>
		 * �iUV��Ԃ̐��������݂���j--- �����l null
		 */
		public lvRec.SeqPart  uvNumBnd			= new lvRec.SeqPart();
		
		/** Fix���鋫�E�Z�O�����gNO.								*/
		public int            fixBndNo;
	}
	
	/** UV��Ԃŋ敪�����ꂽ�ȖʏW���̋��E�Z�O�����g���			*/
	public static class Bound {

		/**
		 * �eUV��Ԃɂ����鋫�E�Z�O�����g�̐��ƁAuvBndSeq[]���̊J�n�_������									<br>
		 * �iUV��Ԃ̐��������݂���j--- �����l null
		 */
		public BoundBase  base[]				= null;
		
		/**
		 * ����UV��Ԃ̋ȖʏW������1���E�Z�O�����g���̔z��BUV��Ԃ�n����ꍇ�A 						<br>
		 *     UV���0�̃Z�O�����g����, UV���1�̃Z�O�����g����, ---, UV���( n-1 )�Z�O�����g���� 	<br>
		 * �Ƒ����B�z��̒����́A 																			<br>
		 *     uvNumBnd[0] + uvNumBnd[1] + --- + uvNumBnd[n-1] 												<br>
		 * �ƂȂ� --- �����l null
		 */
		public BoundOne   uvBndSeq[]			= null;
	}

	public static class EdgeOne {
		
		public int     edgeNo;
		
		public int     edgeIdx;
		
		public double  spring;
	}
	
	public static class VtxOne {
		
		public int            vtxNo;
		
		/** UVpublic.vtxUVseq[]�ɑ΂���I�t�Z�b�g	*/
		public int            uvSpaceOfs;
		
		/** EdgeVtxSet.fixRadial[] �܂��� EdgeVtxSet.freeRadial[] �p	*/
		public lvRec.SeqPart  radial		= new lvRec.SeqPart();
	}

	public static class RadialOne {
		
		public boolean        isFixEdge;
		
		public boolean        isFixVtx;
		
		public int            radialNo;
		
		/** EdgeVtxSet.fixEdge[] �܂��� EdgeVtxSet.freeEdge[] �p	*/
		public int            edgeIndex;
		
		/** EdgeVtxSet.fixVtx[]  �܂��� EdgeVtxSet.freeVtx[]  �p	*/
		public int            mateVtxIndex;
	}
	
	public static class EdgeVtxSet {
		
		public EdgeOne    fixEdge[]				= null;
		
		public EdgeOne    freeEdge[]			= null;
		
		public VtxOne     fixVtx[]				= null;
		
		public VtxOne     freeVtx[]				= null;
		
		/** �z�񒷂́AfixVtx[].radial.num �̑���			*/
		public RadialOne  fixRadial[]			= null;
		
		/** �z�񒷂́AfreeVtx[].radial.num �̑���			*/
		public RadialOne  freeRadial[]			= null;
	}

	public static class EdgeVtx {
		
		/**
		 * �iUV��Ԃ̐��������݂���j--- �����l null
		 */
		public EdgeVtxSet   uvSpace[]			= null;
	}
	
	public static class UVspaceUV {

		/** ���_�ɑ΂���UV���̔z��i�z�񒷂́AUVpublic.vtxUVseq[]�Ɠ����� �j			*/
		public lvUVdt  vtxUVseq[]				= null;
	}
	
	public static class UVspaceST {

		/** ���_�ɑ΂���UV���̔z��i�z�񒷂́AUVpublic.vtxUVseq[]�Ɠ����� �j			*/
		public lvUVdt  vtxSTseq[]				= null;
	}
		
}
