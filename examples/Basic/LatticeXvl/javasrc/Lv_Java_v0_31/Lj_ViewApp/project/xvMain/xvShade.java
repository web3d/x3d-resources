//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// xvShade.java
//

package jp.co.lattice.vApplet;

import	jp.co.lattice.vProcessor.base.*;
import	jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class xvShade extends xvShadeCore {

	// ���[�N
	private Vector3I  v0 = new Vector3I();
	private Vector3I  v1 = new Vector3I();
	private Vector3I  v2 = new Vector3I();

	private x3pColor  dc0 = null;
	private x3pColor  dc1 = null;
	private x3pColor  dc2 = null;

	private DrawLine  dl  = null;
	
	private Vector3I  tuv = new Vector3I();


	/**
	 * �R���X�g���N�^
	 */
	public xvShade( xvGlobal dt, xvRend p_rend )
	{
		super( dt, p_rend );
		
		dc0 = new x3pColor( dt.processor, 0 );
		dc1 = new x3pColor( dt.processor, 0 );
		dc2 = new x3pColor( dt.processor, 0 );

		dl  = new DrawLine( dt );
	}

// -------------------------------------------------------------------

	private xvRend
	Rend()
	{
		return global.rend;
	}

// -------------------------------------------------------------------

	/*
	 * �O�p�`�`��
	 */
	public void
	Draw() throws lvThrowable
	{
		Rend().texUV.vf0 = vf0;
		Rend().texUV.vf1 = vf1;
		Rend().texUV.vf2 = vf2;
		
		// �Œ菬���_�ϊ�
		v0.Set( vf0.x, vf0.y, vf0.z );
		v1.Set( vf1.x, vf1.y, vf1.z );
		v2.Set( vf2.x, vf2.y, vf2.z );

		v0.z = ( int )( vf0.z * 0x10000 * 0x80 );
		v1.z = ( int )( vf1.z * 0x10000 * 0x80 );
		v2.z = ( int )( vf2.z * 0x10000 * 0x80 );

		// ���_���ёւ��i�x���W���傫�����Ɂj
		Vector3I  tmp;
		int	      tmc;
		if( v0.y > v1.y ) {
			tmp = v0;	v0 = v1;	v1 = tmp;
			tmc = c0;	c0 = c1;	c1 = tmc;
		}
		if( v1.y > v2.y ) {
			tmp = v1;	v1 = v2;	v2 = tmp;
			tmc = c1;	c1 = c2;	c2 = tmc;
		}
		if( v0.y > v1.y ) {
			tmp = v0;	v0 = v1;	v1 = tmp;
			tmc = c0;	c0 = c1;	c1 = tmc;
		}

		// ���W���v�Z�i�����^���x�j
		int  dx0 = 0, dx1 = 0,  dx2 = 0;
		int  dz0 = 0, dz1 = 0,  dz2 = 0;
		dc0.Set( c0 );
		dc1.Set( c0 );
		dc2.Set( c0 );
		int  dy;

		dy = ( v1.y - v0.y ) >> 16;
		if( dy != 0 ) {
			dx0 = ( v1.x - v0.x ) / dy;
			dz0 = ( v1.z - v0.z ) / dy;
			dc0.Set( c1 );
			dc0.Sub( c0 );
			dc0.Mul( 0x10000 );
			try {
				dc0.Div( dy );
			}
			catch( lvThrowable exception ) {
				Err().Assert( false, "xvShade.Draw(0)" );
			}
		}
		dy = ( v2.y - v1.y ) >> 16;
		if( dy != 0 ) {
			dx1 = ( v2.x - v1.x ) / dy;
			dz1 = ( v2.z - v1.z ) / dy;
			dc1.Set( c2 );
			dc1.Sub( c1 );
			dc1.Mul( 0x10000 );
			try {
				dc1.Div( dy );
			}
			catch( lvThrowable exception ) {
				Err().Assert( false, "xvShade.Draw(1)" );
			}
		}
		dy = ( v2.y - v0.y ) >> 16;
		if( dy != 0 ) {
			dx2 = ( v2.x - v0.x ) / dy;
			dz2 = ( v2.z - v0.z ) / dy;
			dc2.Set( c2 );
			dc2.Sub( c0 );
			dc2.Mul( 0x10000 );
			try {
				dc2.Div( dy );
			}
			catch( lvThrowable exception ) {
				Err().Assert( false, "xvShade.Draw(2)" );
			}
		}

		/*
		 * V0����V1�܂ł̐������`��
		 *	v0->v1
		 *	v0-> M (->v2)
		 */
		// �X�^�[�g���W
		dl.swap = false;
		dl.ys = v0.y >> 16;
		dl.ye = v1.y >> 16;
	
		dl.xs = v0.x;
		dl.zs = v0.z;
		dl.cs.Set( c0 );
		dl.cs.Mul( 0x10000 );

		dl.xe = v0.x;
		dl.ze = v0.z;
		dl.ce.Set( c0 );
		dl.ce.Mul( 0x10000 );

		// �x����������
		dl.dxs = dx0;
		dl.dzs = dz0;
		dl.dcs = dc0;

		dl.dxe = dx2;
		dl.dze = dz2;
		dl.dce = dc2;
		
		Rend().texUV.CalcInitV0to1();
		// �`��
		dl.Draw();

		/*
		 * V1����V2�܂ł̐������`��
		 *  v1 -> v2
		 *  M  -> v2
		 */
		dl.ys = v1.y >> 16;
		dl.ye = v2.y >> 16;
		if( !dl.swap ) {			// x0���������ꍇ�A��������Ă���ꍇ������
			dl.dxs = dx1;
			dl.dzs = dz1;
			dl.dcs = dc1;
			
			dl.xs = v1.x;
			dl.zs = v1.z;
			dl.cs.Set( c1 );
			dl.cs.Mul( 0x10000 );
		}
		else {
			dl.dxe = dx1;
			dl.dze = dz1;
			dl.dce = dc1;
			
			dl.xe = v1.x;
			dl.ze = v1.z;
			dl.ce.Set( c1 );
			dl.ce.Mul( 0x10000 );
		}
		
		Rend().texUV.CalcInitV1to2();
		// �`��
		dl.Draw();
	}

	/**
	 * ���C���`��p�f�[�^�쐬
	 */
	private class DrawLine extends xvRoot {
		
		private boolean   swap = false;
		
		// �J�n�A�I��
		private int		  ys;		// �x
		private int		  ye;

		private int		  xs;		// �w
		private int		  xe;

		private int		  zs;		// �y
		private int		  ze;

		private x3pColor  cs = null;
		private x3pColor  ce = null;

		// �x���� ������
		private int		  dxs;
		private int		  dzs;
		private x3pColor  dcs;

		private int		  dxe;
		private int		  dze;
		private x3pColor  dce;

		private x3pColor  hdc;				// �����J���[������
		private x3pColor  drawCol;
		private	x3pColor  cc		= null;
		private	x3pColor  tpc		= null;

		private DrawLine( xvGlobal dt )
		{
			super( dt );

			cs = new x3pColor( dt.processor );
			ce = new x3pColor( dt.processor );

			hdc		= new x3pColor( dt.processor );
			drawCol = new x3pColor( dt.processor );
			cc      = new x3pColor( dt.processor );
			tpc     = new x3pColor( dt.processor );
		}

		private x3pGlobal
		Processor()
		{
			return global.processor;
		}

		private final boolean
		Draw() throws lvThrowable
		{
			if( ys == ye )
			    return true;

			swap = false;

			// �n�_�A�I�_�Ƃ��A��ʏ�O �܂��� ��ʉ��O
			if( ( ys < 0 && ye < 0 ) || ( ys >= rend.height && ye >= rend.height ) ) {
				xs += dxs * ( ye - ys );
				zs += dzs * ( ye - ys );
				cs.Add( ( new x3pColor( Processor(), dcs ) ).Mul( ye - ys ) );

				xe += dxe * ( ye - ys );
				ze += dze * ( ye - ys );
				ce.Add( ( new x3pColor( Processor(), dce ) ).Mul( ye - ys ) );

				return false;
			}

			// �n�_����ʏ�O
			if( ys < 0 ) {
				xs += dxs * ( -ys );
				zs += dzs * ( -ys );
				cs.Add( ( new x3pColor( Processor(), dcs ) ).Mul( -ys ) );

				xe += dxe * ( -ys );
				ze += dze * ( -ys );
				ce.Add( ( new x3pColor( Processor(), dce ) ).Mul( -ys ) );
				ys = 0;
			}

			// �I�_����ʉ��O
			int  yeOut = 0;
			if( ye >= rend.height ) {
				yeOut = ( ye - ( rend.height - 1 ) );
				ye = rend.height - 1;
			}
			
			// Line�`��
			for( int l=ys; l<ye; l++ ) {
				Rend().texUV.CalcLineInit( l );
				
				DrawLine( l );
				xs += dxs;
				zs += dzs;
				cs.Add( dcs );

				xe += dxe;
				ze += dze;
				ce.Add( dce );
				
				Rend().texUV.CalcLineFinish();
			}

			return true;
		}

		/**
		 * �������`��
		 */
		private final void
		DrawLine( int l ) throws lvThrowable
		{
			//�Œ菬���؂�̂�
			int x0 = xs >> 16;
			int x1 = xe >> 16;

			if( x0 > x1 ) {
				int 	  t;
				x3pColor  tc;
				t  = xs;	xs = xe;    xe = t;
				t  = zs;	zs = ze;    ze = t;
				tc = cs;	cs = ce;    ce = tc;

				t  = dxs;	dxs = dxe;      dxe = t;
				t  = dzs;	dzs = dze;      dze = t;
				tc = dcs;	dcs = dce;      dce = tc;
				if( swap == true )
				    swap = false;
				else
				    swap = true;
				
				x0 = xs >> 16;
				x1 = xe >> 16;
			}

			// ���E�N���b�v
			if( ( x0 < 0 && x1 < 0 ) || ( x0 >= rend.width && x1 >= rend.width ) )
			    return;
			if( x0 < 0 )
			    x0 = 0;
			if( x1 >= rend.width )
			    x1 = rend.width - 1;

			// ����������
			int  dz;
			hdc.Set( 0xff );
			if( x1 != x0 ) {
				int  dx = x1 - x0;
				
				dz = ( ze - zs ) / dx;
				hdc.Set( ce );
				try {
					hdc.Sub( cs ).Div( dx );
				}
				catch( lvThrowable exception ) {
					Err().Assert( false, "xvShade.DrawLine.DrawLine(0)" );
				}
			}
			else
				dz = 0;

			int  yoffs = l * rend.width;		// Y���W�������[�I�t�Z�b�g
			int  z = zs;
			int  pos;
			drawCol.Set( cs );
			
			int  dith;
			
			for( int i=x0; i<=x1; i++ ) {
				boolean  texEnable = Rend().texUV.CalcPixel( i, tuv );
				pos = yoffs + i;
				if( z <= rend.zbuf[ pos ] ) {
					cc.Set( drawCol ); 
					dith = DITHER[ i&0x03 ][ l&0x03 ];
					cc.Add( dith, dith, dith );

					if( texEnable == true ) {
						// �e�N�X�`���J���[�擾
						tpc.Set( rend.texImage.GetPixel( tuv.x, tuv.y, rend.texture.repeatS, rend.texture.repeatT ) );
						tpc.Mul( 0x10000 );
						tpc.Div( 0x100 );
						cc.Div( 0x10000 );
						cc.Mul( tpc );
					}
					
					rend.mem[ pos ]  = cc.DivGetColor( 0x10000 ) | 0xff000000;
					rend.zbuf[ pos ] = z;
				}
				z += dz;
				drawCol.Add( hdc );
			}
		}
	}
	
}
