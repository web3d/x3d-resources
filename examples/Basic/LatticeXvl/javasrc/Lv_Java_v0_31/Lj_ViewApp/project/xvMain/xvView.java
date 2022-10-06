//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvRend.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvView extends xvRoot {

	//----------------------------------------
	//		���_
	//----------------------------------------
	/**
	 *	���_�����ϊ�����]�p
	 */
	public lvVector   viewDir;
	/*
	 *	���_���ړ_
	 */
	public lvVector   viewLookPt;
	/**
	 *	���_����
	 */
	public double	  viewDistant;
	
	// ���[�N
	public x3pMatrix  viewMat;		    // ���_�ϊ�
	public x3pMatrix  viewRotateMat;	// ���_��]�ϊ��̂�

	//----------------------------------------
	//		����
	//----------------------------------------
	/**
	 *	��������(�ǂ̕����Ɍ��������邩�H�j
	 */
	public  lvVector  lightDir;


// ---------------------------------------------------------------

	public xvView( xvGlobal dt )
	{
		super( dt );
	}

	private x3pGlobal
	Processor()
	{
		return global.processor;
	}
	private xvRend
	Rend()
	{
		return global.rend;
	}

// ---------------------------------------------------------------

	public void
	Set( xvView src )
	{
		viewDir		= new lvVector( Processor(), src.viewDir );	
		viewLookPt	= new lvVector( Processor(), src.viewLookPt );	
		viewDistant	= src.viewDistant;

		viewMat = new x3pMatrix( Processor() );
		viewMat.Set( src.viewMat );
		
		viewRotateMat = new x3pMatrix( Processor() );
		viewRotateMat.Set( src.viewRotateMat );
		
		lightDir = new lvVector( Processor(), src.lightDir );	
	}

	/**
	 *	���_��]�}�g���N�X�擾
	 */
	public final x3pMatrix
	ViewGetRevRotateMatrix()
	{
		x3pMatrix  m = new x3pMatrix( Processor() );
		m.SetRotateYXZ( -viewDir.x, -viewDir.y, -viewDir.z );
		return m;
	}

	/**
	 *	���_�ϊ��}�g���N�X�擾
	 */
	public final x3pMatrix
	ViewGetMatrix()
	{
		return viewMat;
	}


	//---------------------------------------------------------
	//	���_�֘A
	//---------------------------------------------------------
	/**
	 *	���_�ϊ��}�g���N�X���v�Z����
	 */
	public final void
	ViewCalcMat()
	{
		// ���������ւ̕ϊ��p�x
		lvVector  viewDir0 = viewRotateMat.MatResoluteRot();
		viewDir = new lvVector( Processor(), viewDir0.x, viewDir0.y, viewDir0.z );

		// ���_�ʒu�������v�Z
		x3pMatrix  m = new x3pMatrix( Processor() );
		m.SetRotateYXZ( -viewDir.x, -viewDir.y, -viewDir.z );
		lvVector  viewPoint = new lvVector( Processor(), 0, 0, viewDistant );	// viewPoint = ���_���W
		xvUtil.Mul( viewPoint, m, viewPoint );				        			// ���_�ʒu
		viewPoint.AddAssign( viewLookPt );

		lightDir.SetXYZ( 0.0, 0.0, 1.0 );
		try {
			lightDir.UnitAssign();
		}
		catch( lvThrowable e ) {
			int  dmy;	dmy = 0;
		}
		xvUtil.Mul( lightDir, m, lightDir );

		//
		// ViewMat�쐬
		//

		// ���_���W�n�Ɉړ�
		lvVector  v = new lvVector( Processor(), viewPoint.x, viewPoint.y, viewPoint.z );
		v.MulAssign( -1.0 );
		viewMat.SetTranslate( v );

		// �����킹
		viewMat.Mul( viewRotateMat );

		// ���W�n�ϊ�
		x3pMatrix  mr = new x3pMatrix( Processor() );
		mr.SetScale( new lvVector( Processor(), 1.0, 1.0, -1.0 ) );
		viewMat.Mul( mr );
	}

	/**
	 *	���_��]
	 *		���݂̎��_������ɉ��Z
	 *		�����X�V�p�x���c��
	 */
	public final void
	ViewRotateDir( double x, double y, double z )
	{
		// ���_��]�����Z
		x3pMatrix  mv = new x3pMatrix( Processor() );
		mv.SetRotateZXY( x, y, z );
		viewRotateMat = viewRotateMat.Mul( mv );

		ViewCalcMat();
	}

	/**
	 *	���_�ړ�
	 */
	public final void
	ViewMove( double x, double y, double z )
	{
		lvVector  vt = new lvVector( Processor(), x, y, z );

		x3pMatrix  m = new x3pMatrix( Processor() );
		m.SetRotateYXZ( -viewDir.x, -viewDir.y, -viewDir.z );
		xvUtil.Mul( vt, m, vt );
		
		viewLookPt.AddAssign( vt );
		ViewCalcMat();
	}

	/**
	 *	���_�Y�[��
	 */
	public final void
	ViewZoom( double x, double y, double z )
	{
		viewDistant += x;
		ViewRotateDir( 0.0, 0.0, 0.0 );
	}

	/**
	 *	���_�t�B�b�g
	 */
	public final void
	ViewFit( lvVector boundMin, lvVector boundMax )
	{
		lvVector  p0 = new lvVector( Processor(), boundMin );
		lvVector  p1 = new lvVector( Processor(), boundMax );
		
		lvVector  v = new lvVector( Processor(), p0 );
		v.AddAssign( p1 );
		try {
			v.DivAssign( 2.0 );
		}
		catch( lvThrowable e ) {
			int  dmy;	dmy = 0;
		}
		
		viewLookPt.Assign( v );	
		viewRotateMat.SetUnit();		
		viewDistant = 0.0;

		ViewCalcMat();

		v.Assign( p1 );
		xvUtil.Mul( v, viewMat, v );
	
		double z0 = ( clipFlont * v.x ) / ( field * 0.30 );
		double z1 = ( clipFlont * v.y ) / ( field * 0.30 );

		if( z0 < 0 )
		    z0 *= -1.0;

		if( z0 < z1 )
			viewDistant = z1;
		else
			viewDistant = z0;
	
		ViewCalcMat();
		
		xvUtil.Mul( p0, viewMat, p0 );	// ���[�J���ϊ�
		TransformPersepectiv( p0 );	    // �����ϊ�

		xvUtil.Mul( p1, viewMat, p1 );	// ���[�J���ϊ�
		TransformPersepectiv( p1 );	    // �����ϊ�
	}


	/*=========================================================================
	/		�����ϊ�
	/-------------------------------------------------------------------------*/

	// �����ϊ�
    private double  clipFlont = 1.0;				// �t�����g�N���b�v�y
	private double  clipBack  = 5000.0;			    // �o�b�N�N���b�v�y	
	private double  field	  = 0.5;				// �t�B�[���h��

	/**
	 *	�����ϊ�
	 */
	public final lvVector
	TransformPersepectiv( lvVector v )
	{
		double  pers;
		double  ax, ay, az;

		pers = clipFlont / ( field * v.z );
		ax	 =  Rend().screenScl.x * pers * v.x + Rend().screenPoint.x;
		ay	 = -Rend().screenScl.x * pers * v.y + Rend().screenPoint.y;
		az	 = ( clipBack * ( v.z - clipFlont ) ) / ( v.z * ( clipBack - clipFlont ) );
		
		v.x = ax;
		v.y = ay;
		v.z = az;
		
		return v;
	}

}