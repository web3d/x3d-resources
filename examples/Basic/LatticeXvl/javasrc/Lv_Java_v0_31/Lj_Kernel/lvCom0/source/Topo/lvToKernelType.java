//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvToKernelType.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * ��ʊK�w���� Java Lattice Kernel �Ɉ����n���f�[�^��Type��`�N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvToKernelType {

	/**
	 * �Ő��������N���X
	 */
	public static class Edge {
	
		/** ���_No�i�n�_�j				*/
		public int		v0;
	
		/** ���_No�i�I�_�j				*/
		public int		v1;
	
		/** �S�̊ۂߌW���L��  ��:false,  �L:true   --- �����l�Ffalse	*/
		public boolean	enableAll  = false;
		/** �S�̊ۂߌW��							*/
		public double	allRound;
	
		/**
		 * lvToKernel.Attr.type == lvConst.LV_SS_LATTICE �̎��A
		 *		�n�_�ۂߌW��
		 *  	�����ꍇ�A�unull�v, �L��ꍇ�A�uvec0 = new lvVecDt();�v�Ƃ���
		 * lvToKernel.Attr.type == lvConst.LV_SS_GREGORY �̎��A
		 *		�n�_�����x�N�g���B�K���l�������Anull�ɂ͂Ȃ�Ȃ�
		 */
		public lvVecDt	vec0	   = null;
	
		/**
		 * lvToKernel.Attr.type == lvConst.LV_SS_LATTICE �̎��A
		 *		�I�_�ۂߌW��
		 *  	�����ꍇ�A�unull�v, �L��ꍇ�A�uvec1 = new lvVecDt();�v�Ƃ���
		 * lvToKernel.Attr.type == lvConst.LV_SS_GREGORY �̎��A
		 *		�I�_�����x�N�g���B�K���l�������Anull�ɂ͂Ȃ�Ȃ�
		 */
		public lvVecDt	vec1	   = null;
	}

	/**
	 * ���_�������N���X
	 */
	public static class Vertex {
	
		/** ���_���W			*/
		public lvVecDt	pos			= new lvVecDt();
	
		/** �ۂߌW��			*/
		public double	round;
	
		/** �ۂߌW���L��  ��:false,  �L:true   --- �����l�Ffalse	*/
		public boolean	enable		= false;
	}

	/**
	 * ��ʊK�w���� Java Lattice Kernel �Ɉ����n�������̓����N���X
	 */
	public static class Attr {
	
		/** �Ő��������i 2�̔{�� �j --- �����l�F4					*/
		public int  numDiv		= 4;
	
		/**
		 * ���V�F���̎��
		 * lvConst.LV_SS_POLYGON�AlvConst.LV_SS_LATTICE�AlvConst.LV_SS_GREGORY �̂��Âꂩ
		 */
		public int  type		= lvConst.LV_SS_POLYGON;
	}

}
