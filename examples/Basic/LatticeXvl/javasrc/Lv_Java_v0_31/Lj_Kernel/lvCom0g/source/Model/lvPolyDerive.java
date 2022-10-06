//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvPolyDerive.java
//

package jp.co.lattice.vKernel.greg.c0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java Lattice Kernel �̃V�F�����i�q����̔h�����N���X
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvPolyDerive {

	/**
	 * �i�q(GS��)�������N���X
	 */
	public static class PolyCenterLattice {
		
		/** �i�q(GS��)�̒��S�ʒu���W�Ɩʖ@��	*/
		public lvRec.PosNorLow  center	= new lvRec.PosNorLow();
		
		/** �i�q(GS��)�̖ʐ�					*/
		public double           area;
	}
	
	/**
	 * Java Lattice Kernel �̃V�F�����i�q�̏��p�����N���X
	 * @author	  created by Eishin Matsui (99/08/17-)
	 * 
	 */
	public static class PolyCenter {
		
		/**
		 * �i�q(GS��)��񕔂̔z��iGS�ʐ��������݂���u 1�ȏ� �v�j --- �����l null
		 */
		public PolyCenterLattice  lattice[]		= null;
		
		/**
		 * NG�ʂ̒��S�ʒu���W�̔z��iNG�ʐ��������݂���j		   --- �����l null			<br>
		 *		��{�ۂ߃x�N�g���̌݊����ׂ̈ɕK�v
		 */
		public lvVecDt            centerNG[]	= null;

	// -------------------------------------------------------------------

		public final void
		NewLattice( int num )
		{
			lattice = new PolyCenterLattice[ num ];
			for( int i=0; i<num; i++ )
				lattice[ i ] = new PolyCenterLattice();
		}
		
		public final void
		NewNG( int num )
		{
			if( num == 0 )
				return;
			
			centerNG = new lvVecDt[ num ];
			for( int i=0; i<num; i++ )
				centerNG[ i ] = new lvVecDt();
		}
	}

// -------------------------------------------------------------------

	/**
	 * ���_�������N���X
	 */
	public static class BezgonVertex {
	
		/** ���_���W			*/
		public lvVecDt  pos					= new lvVecDt();
	}
	
	/**
	 * �Ő��������N���X
	 */
	public static class BezgonEdge {
	
		/** �n���h���x�N�g���i�C���f�b�N�X 0:�Ő��̎n�_�A 1:�Ő��̏I�_�j	*/
		public lvVecDt  handVec[/*2*/]		= null;
	
		public  BezgonEdge()
		{
			handVec = new lvVecDt[ 2 ];
			for( int i=0; i<2; i++ )
				handVec[ i ] = new lvVecDt();
		}
	}
	
	/**
	 * �ʏ������N���X
	 */
	public static class BezgonFace {
	
		/** �ʖ@��			*/
		public lvRec.PosNorLow  center		= new lvRec.PosNorLow();
	}
	
	/**
	 * Java Lattice Kernel �̃V�F���� Bezier�i�q�i��������_���j�������N���X
	 * @author	  created by Eishin Matsui (99/08/17-)
	 * 
	 */
	public static class Bezgon {

		/**
		 * ���_��񕔂̔z��i���_���������݂���u 3�ȏ� �v�j --- �����l null
		 */
		public BezgonVertex  vertex[]		= null;
	
		/**
		 * �Ő���񕔂̔z��i�Ő����������݂���u 3�ȏ� �v�j --- �����l null
		 */
		public BezgonEdge    edge[]			= null;

		/**
		 * GS�ʏ�񕔂̔z��iGS�ʐ��������݂���u 1�ȏ� �v�j --- �����l null
		 */
		public BezgonFace    face[]			= null;

	// -------------------------------------------------------------------

		public final void
		NewVertex( int num )
		{
			vertex = new BezgonVertex[ num ];
			for( int i=0; i<num; i++ )
				vertex[ i ] = new BezgonVertex();
		}
		public final void
		NewEdge( int num )
		{
			edge = new BezgonEdge[ num ];
			for( int i=0; i<num; i++ )
				edge[ i ] = new BezgonEdge();
		}
		public final void
		NewFace( int num )
		{
			face = new BezgonFace[ num ];
			for( int i=0; i<num; i++ )
				face[ i ] = new BezgonFace();
		}
	}
	
}
