//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvUVspace.java
//

package jp.co.lattice.vKernel.tex.c0a;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel �̃V�F���� UV��ԁi�e�N�X�`���p�j���N���X
 * @author	  created by Eishin Matsui (00/03/31-)
 * 
 */
public class lvUVspace {

	/**
	 * �uUV���No.�v����UV��ԏ��̓����N���X
	 */
	public static class UVspace {
		
		/**
		 * �uuvSpaceGsSeq[] �̊J�n�_�ƌ��i��UV���No.������GS�ʂ̐��u1�ȏ�v�j�v������
		 */
		public lvRec.SeqPart  gs	= new lvRec.SeqPart();
		
		/**
		 * �uuvSpaceVtxSeq[] �̊J�n�_�ƌ��i��UV���No.������GS�ʂ̐��u3�ȏ�v�j�v������
		 */
		public lvRec.SeqPart  vtx	= new lvRec.SeqPart();
	}
	
	/**
	 * GS�ʂɑ΂���UV��ԏ��̓����N���X
	 */
	public static class GsInfo {
	
		/** UV���No.	*/
		public int     uvSpaceNo;
	
		/** GS�ʒ��S��UV�l		*/
		public lvUVdt  center	= new lvUVdt();
	}
	
	/**
	 * ���_�ɑ΂���UV��ԏ��̓����N���X
	 */
	public static class VtxInfo {
	
		/** UV���No.	*/
		public int     uvSpaceNo;
	
		/** UV���W�l	*/
		public lvUVdt  uv		= new lvUVdt();
	}

// -------------------------------------------------------------------

	/**
	 * �uUV���No.�v����UV��ԏ��̔z��i���V�F������UV��Ԃ̐��������݂���u0�ȏ�v�j			<br>
	 * --- �����l null
	 */
	public UVspace        uvSpace[]				= null;
	
	/**
	 * GS�ʂɑ΂���uUV���No.�v����UV��ԏ��̔z��B�uUV���No.�v��n����ꍇ�A 				<br>
	 *     UV���No0��GS��No.��, UV���No1��GS��No.��, ---, UV���No( n-1 )��GS��No.�� 			<br>
	 * �Ƒ����B�z��̒����́A 																	<br>
	 *     uvSpace[0].gs + uvSpace[1].gs + --- + uvSpace[n-1].gs 								<br>
	 * �ƂȂ�B --- �����l null
	 */
	public int            uvSpaceGsSeq[]		= null;
	
	/**
	 * ���_�ɑ΂���uUV���No.�v����UV��ԏ��̔z��B�uUV���No.�v��n����ꍇ�A 				<br>
	 *     UV���No0�̒��_No.��, UV���No1�̒��_No.��, ---, UV���No( n-1 )�̒��_No.�� 			<br>
	 * �Ƒ����B�z��̒����́A 																	<br>
	 *     uvSpace[0].vtx + uvSpace[1].vtx + --- + uvSpace[n-1].vtx 							<br>
	 * �ƂȂ�B --- �����l null
	 */
	public int            uvSpaceVtxSeq[]		= null;

	
	/**
	 * �e�v�f�ɂ́A����GS�ʂ�������UV��ԁi�e�N�X�`���j�̐�������BUV��Ԃ����݂��Ȃ��ꍇ�́A	<br>
	 * num = 0 �Ƃ���B�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�́Anum >= 2 �ƂȂ�		<br>
	 * �iGS�ʐ��������݂���u1�ȏ�v�j--- �����l null
	 */
	public lvRec.SeqPart  gsUV[]				= null;
	
	/**
	 * GS�ʂɑ΂���UV��ԏ��̔z��BGS�ʂ�n����ꍇ�A 										<br>
	 *     GS��0��UV��ԏ���, GS��1��UV��ԏ���, ---, GS��( n-1 )��UV��ԏ��� 			<br>
	 * �Ƒ����B�z��̒����́A 																	<br>
	 *     gsInfo[0] + gsInfo[1] + --- + gsInfo[n-1] 											<br>
	 * �ƂȂ�B --- �����l null
	 */
	public GsInfo         gsUVspaceSeq[]		= null;

	
	/**
	 * �e�v�f�ɂ́A�u���̒��_��������UV��ԁi�e�N�X�`���j�v�̐�������BUV��Ԃ����݂��Ȃ�		<br>
	 * �ꍇ�́Anum = 0 �Ƃ���B�����_��UV��Ԃ������Ă��鎞�́A�K�� num >= 1�ƂȂ�				<br>
	 *�i����UV�l�̕�ԍρj�B																	<br>
	 * �����_���e�N�X�`�����E�ł��鎞��A�uUV��Ԃ̈قȂ鑽�d�}�b�s���O�v��������Ă��鎞�́A	<br>
	 * num >= 2 �ƂȂ�i���_���������݂���u3�ȏ�v�j--- �����l null
	 */
	public lvRec.SeqPart  vtxUV[]				= null;
	
	/**
	 * ���_�ɑ΂���UV��ԏ��̔z��B���_��n����ꍇ�A 										<br>
	 *     ���_0��UV��ԏ���, ���_1��UV��ԏ���, ---, ���_( n-1 )��UV��ԏ��� 			<br>
	 * �Ƒ����B�z��̒����́A 																	<br>
	 *     vtxUV[0] + vtxUV[1] + --- + vtxUV[n-1] 												<br>
	 * �ƂȂ�B�z�񒷂� 0 �̎��́Anull �Ƃ��� --- �����l null
	 */
	public VtxInfo        vtxUVinfoSeq[]		= null;
	
	
// -------------------------------------------------------------------

/*
	public final void
	NewUvSpace( int num )
	{
		uvSpace = new UVspace[ num ];
		for( int i=0; i<num; i++ )
			uvSpace[ i ] = new UVspace();
	}
	public final void
	NewUvSpaceGsSeq( int num )
	{
		uvSpaceGsSeq = new int[ num ];
	}
	public final void
	NewUvSpaceVtxSeq( int num )
	{
		uvSpaceVtxSeq = new int[ num ];
	}
	public final void
	NewGsUV( int num )
	{
		gsUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			gsUV[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewGsUVspaceSeq( int num )
	{
		gsUVspaceSeq = new GsInfo[ num ];
		for( int i=0; i<num; i++ )
			gsUVspaceSeq[ i ] = new GsInfo();
	}
	public final void
	NewVtxUV( int num )
	{
		vtxUV = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			vtxUV[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewVtxUVinfoSeq( int num )
	{
		vtxUVinfoSeq = new VtxInfo[ num ];
		for( int i=0; i<num; i++ )
			vtxUVinfoSeq[ i ] = new VtxInfo();
	}
*/

}
