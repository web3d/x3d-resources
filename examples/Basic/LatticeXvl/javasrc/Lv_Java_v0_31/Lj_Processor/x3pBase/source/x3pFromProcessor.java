//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pFromProcessor.java

package jp.co.lattice.vProcessor.base;


import  java.awt.*;

import  jp.co.lattice.vProcessor.com.*;

// ���e�B�X�J�[�l��
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Java XVL3 Processor �����ʊK�w�Ɉ����n���f�[�^�̃N���X
 * @author	  created by Eishin Matsui (00/03/11-)
 */
public class x3pFromProcessor extends x3pRoot {

    /** �}�e���A���Ɋւ�������N���X	*/
	public static class Material {
		
		/** �����̗� */
		public double    ambientIntensity = 0.2;
		/** �g�U���ˌ��F */
		public x3pColor  diffuseColor     = null;
		/** �����F */
		public x3pColor  emissiveColor    = null;
		/** ���ʔ��˂̉s�� */
		public double    shininess        = 0.2;
		/** ���ʔ��ˌ��F */
		public x3pColor  specularColor    = null;
		/** �����x */
		public double    transparency     = 0.0;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public Material( x3pGlobal dt )
		{
			diffuseColor  = new x3pColor( dt, 0.8, 0.8, 0.8 );
			emissiveColor = new x3pColor( dt, 0.0, 0.0, 0.0 );
			specularColor = new x3pColor( dt, 0.0, 0.0, 0.0 );
		}
	}
	
   /** �e�N�X�`���Ɋւ�������N���X	*/
	public static class Texture {
		
		/**
		 * �e�N�X�`���t�@�C��URL�BXVL3�t�@�C���ɋL�q���ꂽ�܂܂̕�����ŕԂ��B								<br>
		 * url != null �̎��� image == null�  url == null �̎��� image != null �ƂȂ�B						<br>
		 * ���� url == null && image == null �̏ꍇ�́A�e�N�X�`���t�@�C����摜�����݂��Ȃ��������Ƃ�����
		 */
		public String   url 		= null;
		
		/**
		 * �e�N�X�`���t�@�C��Image�BXVL3�t�@�C�����ɁiBASE64���ŃG���R�[�h���ꂽ�j�摜�t�@�C��				<br>
		 * �����݂���ꍇ�A�ϐ��uimage�v�ɒl������B														<br>
		 * url != null �̎��� image == null�  url == null �̎��� image != null �ƂȂ�B						<br>
		 * ���� url == null && image == null �̏ꍇ�́A�e�N�X�`���t�@�C����摜�����݂��Ȃ��������Ƃ�����
		 */
		public Image    image		= null;
		
		/**
		 * S�����J��Ԃ��t���O �J��Ԃ��Ȃ�:false, �J��Ԃ�:true --- �����l�Ftrue
		 */
		public boolean  repeatS		= true;
		
		/**
		 * T�����J��Ԃ��t���O �J��Ԃ��Ȃ�:false, �J��Ԃ�:true --- �����l�Ftrue
		 */
		public boolean  repeatT		= true;
	}
	
	/** �u1�̃V�F���Ɋւ���uJava XVL3 Processor �����ʊK�w�Ɉ����n���f�[�^�v�v�̔z�� --- �����l null */
	public x3pShellInfo[]  shellInfo	= null;
	
	/** �}�e���A���o�^�e�[�u�� --- �����l null */
	public Material[]      material     = null;

	/** �e�N�X�`���o�^�e�[�u���B�e�N�X�`�������݂��Ȃ����́Anull --- �����l null */
	public Texture[]       texture      = null;


// -------------------------------------------------------------------

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {
		
		public x3pShellInfo[]  shell		= null;
		public Material[]      material		= null;
		public Texture[]       texture		= null;

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( x3pGlobal dt )
		{
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private final Global
	Gbl()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gFromProcessor;
	}

	private final x3pToProcessor.Global
	ToProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gToProcessor;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  x3pFromProcessor( x3pGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/** lvError�p�̃O���[�o���f�[�^				*/
	private final lvError.Global
	ErrProc()
	{
		return   ( ( lvComGblElm )global.GCom() ).gError;
	}

// -------------------------------------------------------------------

	/**
	 * Java XVL3 Processor ����f�[�^���擾����i���̊֐������s����O�� shellInfo[] �� null �Ƃ��Ă���)
	 * @return				lvConst.LV_SUCCESS �܂��� lvConst.LV_FAILURE
	 */
	public final boolean
	GetData()
	{
		try {
			ErrProc().BeginThrowMode();
			
				GetDataMain();
				
			ErrProc().EndThrowMode();
			return  lvConst.LV_SUCCESS;
		}
		catch( lvThrowable exception ) {
			ErrProc().EndThrowMode();
			return  lvConst.LV_FAILURE;
		}
	}
	
	private final void
	GetDataMain() throws lvThrowable
	{
		ExecTraverse();
		ExecInstance();
		MakeNormalMat();
		
		shellInfo = Gbl().shell;
		material  = Gbl().material;
		texture   = Gbl().texture;
		
		Finish();
	}
	
	private final void
	ExecTraverse() throws lvThrowable
	{
		x3pTrvFromKernel  xvl0 = new x3pTrvFromKernel( global );
		xvl0.FromKernel( ToProcessor().topElms );
	}
	
	private final void
	ExecInstance() throws lvThrowable
	{
		int  numInstance[] = null;

		x3pTrvNumInstance  xvl0 = new x3pTrvNumInstance( global );
		xvl0.GetNumInstance( ToProcessor().topElms );
		numInstance = xvl0.GetNumInstance();
		
		int  numShell = Gbl().shell.length;
		for( int i=0; i<numShell; i++ ) {
			Gbl().shell[ i ].instance = new x3pShellInfo.Instance[ numInstance[ i ] ];
			for( int j=0; j<numInstance[ i ]; j++ )
				Gbl().shell[ i ].instance[ j ] = new x3pShellInfo.Instance( global );
		}
			
		x3pTrvInstance  xvl1 = new x3pTrvInstance( global );
		xvl1.SetInstance( ToProcessor().topElms );
	}
	
	private final void
	MakeNormalMat() throws lvThrowable
	{
		int  numShell = Gbl().shell.length;
		for( int i=0; i<numShell; i++ ) {
			int  numInstance = Gbl().shell[ i ].instance.length;
			for( int j=0; j<numInstance; j++ ) {
				x3pShellInfo.Instance  instance = Gbl().shell[ i ].instance[ j ];
				MakeNormalMatMain( instance );
			}
		}
	}
	
	private final void
	MakeNormalMatMain( x3pShellInfo.Instance instance ) throws lvThrowable
	{
		instance.normalMat.Set( instance.posMat );
		instance.normalMat.m[ 3 ][ 0 ] = 0;
		instance.normalMat.m[ 3 ][ 1 ] = 0;
		instance.normalMat.m[ 3 ][ 2 ] = 0;
		
		x3pMatrix  normalMat = new x3pMatrix( global, instance.normalMat );
		
		normalMat.Invert33();
		normalMat.Transpose();
		
		instance.normalMat.Set( normalMat );
	}
	
	private final void
	Finish()
	{
	}
	
}
