//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvPolygon.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel �̃V�F�����i�q���i�􉽁{�ʑ��j�N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvPolygon {

	/**
	 * ��No�ƃn�[�t�G�b�WNo�̏������N���X�i�Ő��������N���X�AvtxFaceSeq[]�p�j
	 */
	public static class InfoFaceHalf {
		
		/** ��No				*/
		public int	faceNo;
		
		/** �n�[�t�G�b�WNo		*/
		public int	halfNo;
	}

	/**
	 * �Ő��������N���X
	 */
	public static class Edge {
	
		/** �S�̊ۂߌW��				*/
		public double		 allRound;
	
		/**
		 * lvToKernel.Attr.type == lvConst.LV_SS_LATTICE �̎��A
		 *		��{�ۂ߃x�N�g��
		 * lvToKernel.Attr.type == lvConst.LV_SS_GREGORY �̎��A
		 *		�Ő��㒸�_�����x�N�g��
		 */
		public lvVecDt	     vec[/*2*/]			= null;
	
		/** ���΂���ʁi��No�ƃn�[�t�G�b�WNo�j�̏��	*/
		public InfoFaceHalf  face[/*2*/]		= null;
		
		public  Edge()
		{
			vec = new lvVecDt[ 2 ];
			for( int i=0; i<2; i++ )
				vec[ i ] = new lvVecDt();
			
			face  = new InfoFaceHalf[ 2 ];
			for( int i=0; i<2; i++ )
				face[ i ] = new InfoFaceHalf();
		}
	}

	/**
	 * ���_�������N���X
	 */
	public static class Vertex {
	
		/** ���_���W			*/
		public lvVecDt		  pos		= new lvVecDt();
	
		/** �ۂߌW��			*/
		public double		  round;
	
		/** vtxFaceSeq[] �ɑ΂���A�J�n�_�ƌ��iradial���j������	*/
		public lvRec.SeqPart  vtxFace	= new lvRec.SeqPart();
	}
	
	/**
	 * �ʂ̃n�[�t�G�b�W���𗅗񂵂����`�f�[�^�z��p�̓����N���X
	 */
	public static class FaceHalf {
		
		/** �Ή����_No				*/
		public int	 vtxNo;
		
		/** �Ή����_��� radialNo	*/
		public int	 radialNo;
		
		/** �Ή��Ő�No				*/
		public int	 edgeNo;
		
		/** ���n�[�t�G�b�W�ƑΉ��Ő��̕����Ɋ�Â��C���f�b�N�X�i ������ : 0, ������ : 1 �j	*/
		public byte  edgeIdx;
	}
	
// -------------------------------------------------------------------

	/**
	 * ���_��񕔂̔z��i���_���������݂���u 3�ȏ� �v�j --- �����l null
	 */
	public Vertex		  vertex[]			= null;
	/**
	 * ���_��n����ꍇ�A													<br>
	 * ���_0��radialNo��, ���_1��radialNo��, ---, ���_( n-1 )��radialNo��	<br>
	 * �Ƒ����B�z��̒����́A												<br>
	 * ���_0��radial�� + ���_1��radial�� + --- + ���_( n-1 )��radial��		<br>
	 * �ƂȂ�B --- �����l null
	 */
	public InfoFaceHalf   vtxFaceSeq[]		= null;
	
	/**
	 * �Ő���񕔂̔z��i�Ő����������݂���u 3�ȏ� �v�j --- �����l null
	 */
	public Edge		      edge[]			= null;
	
	/**
	 * face[]�͑O��:GS�ʁA�㔼:NG�ʂƕ�����Ă���B����NG�ʂ̊J�n�ʒu --- �����l 0
	 */
	public int			  ngStartNo			= 0;
	/**
	 * �ʏ�񕔂̔z��i�ʐ��������݂���u 1�ȏ� �v�j --- �����l null	<br>
	 * faceHalfSeq[] �ɑ΂���A�J�n�_�ƌ��i�n�[�t�G�b�W�������_�����Ő����j������
	 */
	public lvRec.SeqPart  face[]			= null;
	/**
	 * �ʂ�n����ꍇ�A																<br>
	 * ��0�̃n�[�t�G�b�WNo��, ��1�̃n�[�t�G�b�WNo��, ---, ��( n-1 )�̃n�[�t�G�b�WNo��	<br>
	 * �Ƒ����B�z��̒����́A															<br>
	 * ��0�̃n�[�t�G�b�W�� + ��1�̃n�[�t�G�b�W�� + --- + ��( n-1 )�̃n�[�t�G�b�W��		<br>
	 * �ƂȂ�B --- �����l null
	 */
	public FaceHalf	      faceHalfSeq[]		= null;

// -------------------------------------------------------------------

	public final void
	NewVertex( int num )
	{
		vertex = new Vertex[ num ];
		for( int i=0; i<num; i++ )
			vertex[ i ] = new Vertex();
	}
	public final void
	NewVtxFaceSeq( int num )
	{
		vtxFaceSeq = new InfoFaceHalf[ num ];
		for( int i=0; i<num; i++ )
			vtxFaceSeq[ i ] = new InfoFaceHalf();
	}
	public final void
	NewEdge( int num )
	{
		edge = new Edge[ num ];
		for( int i=0; i<num; i++ )
			edge[ i ] = new Edge();
	}
	public final void
	NewFace( int num )
	{
		face = new lvRec.SeqPart[ num ];
		for( int i=0; i<num; i++ )
			face[ i ] = new lvRec.SeqPart();
	}
	public final void
	NewFaceHalfSeq( int num )
	{
		faceHalfSeq = new FaceHalf[ num ];
		for( int i=0; i<num; i++ )
			faceHalfSeq[ i ] = new FaceHalf();
	}
}
