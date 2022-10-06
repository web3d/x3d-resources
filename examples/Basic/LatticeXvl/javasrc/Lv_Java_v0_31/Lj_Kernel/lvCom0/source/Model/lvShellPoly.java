//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvShellPoly.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Java Lattice Kernel �̃V�F�����i�􉽁{�ʑ��j�N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvShellPoly {
	
	/**
	 * ���������N���X
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

// -------------------------------------------------------------------
	
	/** �V�F�����i�q���							*/
	public lvPolygon  poly					= null;

	/** �V�F���� �������							*/
	public Attr       attr					= new Attr();
	
}
