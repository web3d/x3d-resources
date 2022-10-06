//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvVector.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Vector�^�̐��l���Z�p�N���X    �i���ʕ��j
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvVector extends lvVecCalc {
	
	/** �萔 --- ���������ʏ�ɂȂ��A���������s�ł���		*/
	public static final byte  LV_INTERSEC_PARA		= 0;
	/** �萔 --- ���������ʏ�ɂ���A���s�ł���				*/
	public static final byte  LV_INTERSEC_ON		= 1;
	/** �萔 --- ���������ʂƌ������Ă���					*/
	public static final byte  LV_INTERSEC_CROSS		= 2;
	
// -------------------------------------------------------------------

	/**
	 * static�ϐ���p�̂��߂̓����N���X
	 */
	public static class Global {

		/**
		 * �R���X�g���N�^
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		public  Global( lvGlobal dt )
		{
			GlobalTmp( dt );
		}

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
//		private lvVector  tvScale[]         = null;
//		private lvDouble  tdMirror0[]       = null;
//		private lvVector  tvMirror1[]       = null;
		private lvVector  tvNormal[]        = null;
		private lvVector  tvCenter[]        = null;

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A�ilvKernelGreg�p�j		*/
		private lvVector  tvRotate0[]       = null;
		private lvDouble  tdRotate0[]       = null;
//		private lvVector  tvRotate1[]       = null;
		private lvVector  tvNormalAssign0[] = null;
		private lvVector  tvNormalAssign1[] = null;

		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		private final void
		GlobalTmp( lvGlobal dt )
		{
//			tvScale         = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvScale[ i ]         = new lvVector( dt );
//			tdMirror0       = new lvDouble[ 2 ];	for( int i=0; i<2; i++ )	tdMirror0[ i ]       = new lvDouble( dt );
//			tvMirror1       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvMirror1[ i ]       = new lvVector( dt );
			tvNormal        = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormal[ i ]        = new lvVector( dt );
			tvCenter        = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvCenter[ i ]        = new lvVector( dt );

			// ���[�J���ϐ��p�� new ��p�o�b�t�@�������ilvKernelGreg�p�j
			tvRotate0       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvRotate0[ i ]       = new lvVector( dt );
			tdRotate0       = new lvDouble[ 4 ];	for( int i=0; i<4; i++ )	tdRotate0[ i ]       = new lvDouble( dt );
//			tvRotate1       = new lvVector[ 2 ];	for( int i=0; i<2; i++ )	tvRotate1[ i ]       = new lvVector( dt );
			tvNormalAssign0 = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormalAssign0[ i ] = new lvVector( dt );
			tvNormalAssign1 = new lvVector[ 4 ];	for( int i=0; i<4; i++ )	tvNormalAssign1[ i ] = new lvVector( dt );
		}
	}
	
	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gVector;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public	lvVector( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * �R�s�[�R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 * @param  val		(( I )) �R�s�[���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 */
	public	lvVector( lvGlobal dt, lvVecCalc val )
	{
		super( dt, val );
	}
	/**
	 * �����l x,y,z �̃R���X�g���N�^
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 * @param  x		(( I )) �R���X�g���N�^�̏����lX
	 * @param  y		(( I )) �R���X�g���N�^�̏����lY
	 * @param  z		(( I )) �R���X�g���N�^�̏����lZ
	 */
	public	lvVector( lvGlobal dt, double x, double y, double z )
	{
		super( dt, x, y, z );
	}

// -------------------------------------------------------------------

	/**
	 * Vector�^�̑���֐�
	 * @param  val		(( I )) ������B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
	public final lvVector
	Assign( lvVecCalc val )
	{
		x = val.x;	y = val.y;	z = val.z;
		return	this;
	}

	/**
	 * Vector�^�� += �֐�
	 * @param  val		(( I )) ������B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
	public final lvVector
	AddAssign( lvVecCalc val )
	{
		x += val.x;  y += val.y;  z += val.z;
		return	this;
	}
	/**
	 * Vector�^�� -= �֐�
	 * @param  val		(( I )) ������B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
	public final lvVector
	SubAssign( lvVecCalc val )
	{
		x -= val.x;  y -= val.y;  z -= val.z;
		return	this;
	}
	/**
	 * Vector�^�� *= �i�X�J���[��Z������Z�j�֐�
	 * @param  k		(( I )) �X�J���[
	 * @return			this �̎Q��
	 */
	public final lvVector
	MulAssign( double k )
	{
		x *= k;  y *= k;  z *= k;
		return	this;
	}
	/**
	 * Vector�^�� /= �i�X�J���[���Z������Z�j�֐�
	 * @param  m0		(( I )) �X�J���[
	 * @return			this �̎Q��
	 */
	public final lvVector
	DivAssign( double k ) throws lvThrowable
	{
		Err().Assert( ( k != 0.0 ), "lvVector.DivAssign(0)" );
		x /= k;  y /= k;  z /= k;
		return	this;
	}
	/**
	 * Vector�^�̊O�ϑ�����Z�֐�
	 * @param  val		(( I )) ������B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	CrossAssign( lvVecCalc val )
	{
		double	x, y, z;
		x = this.y * val.z - this.z * val.y;
		y = this.z * val.x - this.x * val.z;
		z = this.x * val.y - this.y * val.x;
		return	SetXYZ_Local( x, y, z );
	}
*/
	/**
	 * ���g�i�x�N�g��:lvVector�j�ɍs�� m2 �ŃA�t�B���ϊ������x�N�g�������g�ɑ������
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	MulVecAssign( lvMatCalc m0 )
	{
		this.Assign( this.MulVec( m0 ) );		// this = this % m0;
		return	this;
	}
*/
	/**
	 * ���g�i3�������W�l�Ƃ��Ẵx�N�g��:lvVector�j�ɍs�� m2 �ŃA�t�B���ϊ������x�N�g�������g�ɑ������
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	MulPosAssign( lvMatCalc m0 )
	{
		this.Assign( this.MulPos( m0 ) );		// this = this * m0;
		return	this;
	}
*/
	/**
	 * ���g�𔽓]����
	 * @return			this �̎Q��
	 */
	public final lvVector
	NegAssign()
	{
		x = -x;  y = -y;  z = -z;
		return	this;
	}
	
	/**
	 * �ړ��x�N�g���ɂ�莩�g���ړ�������
	 * @param  val		(( I )) �ړ��x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Move( lvVecCalc val )
	{
		return	AddAssign( val );		// this += val;		return this;
	}
*/

	/**
	 * ���_�𒆐S�Ɏ��g���g��k��������
	 * @param  k		(( I )) �g�嗦
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Scale( double k )
	{
		return	MulAssign( k );			// this *= k;		return this;
	}
*/
	/**
	 * �_ p �𒆐S�Ɏ��g���g��k��������
	 * @param  p		(( I )) ���S�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  k		(( I )) �g�嗦
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Scale( lvVecCalc p, double k )
	{
		lvVector  v = Gbl().tvScale[0];						// v = new lvVector();
		v.Assign( Sub( p ) );							// v = this - p
		Assign( ( v.Mul( k ) ).Add( p ) );				// this = v * k + p
		return	this;
	}
*/
	
	/**
	 * ���_�Ɩ@���x�N�g�� n0 �ŕ\����镽�ʂɑ΂��Ď��g�𔽓]������
	 * @param  n0		(( I )) �@���x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Mirror( lvVecCalc n0 )
	{
		lvDouble  d = Gbl().tdMirror0[0];		d.val = 2.0;		// d = new lvDouble( 2.0 );
		SubAssign( ( d.Mul( Dot( n0 ) ) ).Mul( n0 ) );				// this -= 2.0 * ( this * n0 ) * n0;
		return	this;
	}
*/
	/**
	 * p0 �Ɩ@���x�N�g�� n0 �ŕ\����镽�ʂɑ΂��Ď��g�𔽓]������
	 * @param  p0		(( I )) ��]����̓_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) �@���x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Mirror( lvVecCalc p0, lvVecCalc n0 )
	{
		lvVector  v0 = Gbl().tvMirror1[0];			// v0 = new lvVector();
		v0.Assign( Sub( p0 ) );					// v0 = this - p0;
		v0.Mirror( n0 );
		return	Assign( v0.Add( p0 ) );			// this = v0 + p0;
	}
*/

	/**
	 * �x�N�g���܂���3�������W�l�Ƃ��āA���g�ɃA�t�B���ϊ����{��
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @param  ispnt	(( I )) �x�N�g��:false,  3�������W�l:true
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Trans( lvMatCalc m0, boolean ispnt )
	{
		if( ispnt )
			MulPosAssign( m0 );		// this *= m0;
		else
			MulVecAssign( m0 );		// this %= m0;
		return	this;
	}
*/
	/**
	 * �x�N�g���Ƃ��āA���g�ɃA�t�B���ϊ����{��
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Trans( lvMatCalc m0 )
	{
		return	Trans( m0, false );
	}
*/
	
	/**
	 * �����_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g���Ɩʐς����߁A���g�ɒP�ʕ��ϖ@���x�N�g����������i�񐄏��j
	 * @param  nP0		(( I )) �_�̔z��
	 * @param  num0		(( I )) �_�̐�
	 * @param  a0p		(( O )) �ʐ�
	 * @param  eps		(( I )) ���e�덷
	 */
	public final void
	Normal( lvVector nP0[], int num0, lvDouble a0p, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVector.Normal(0)" );

		lvVector  n0 = Gbl().tvNormal[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );	// n0 = new lvVector( 0.0, 0.0, 0.0 );
		lvVector  v  = Gbl().tvNormal[1];									// v  = new lvVector( *, *, * );
		double	  d0 = 0.0;
		for( int i=0; i<num0; i++ ) {
			int  j = ( i == num0-1 ) ? 0 : i+1;
			
			n0.AddAssign( v.SetXYZ( ( nP0[i].y - nP0[j].y ) * ( nP0[i].z + nP0[j].z ),
									( nP0[i].z - nP0[j].z ) * ( nP0[i].x + nP0[j].x ),
									( nP0[i].x - nP0[j].x ) * ( nP0[i].y + nP0[j].y ) ) );
					// n0 += lvVector( ( nP0[i].y - nP0[j].y ) * ( nP0[i].z + nP0[j].z ),
					//				   ( nP0[i].z - nP0[j].z ) * ( nP0[i].x + nP0[j].x ),
					//				   ( nP0[i].x - nP0[j].x ) * ( nP0[i].y + nP0[j].y ) );
// Decord ( Begin )
		//	n0.AddAssign( v.SetXYZ_Local( ( nP0[i].y * nP0[j].z - nP0[j].y * nP0[i].z ),
		//							      ( nP0[i].z * nP0[j].x - nP0[j].z * nP0[i].x ),
		//							      ( nP0[i].x * nP0[j].y - nP0[j].x * nP0[i].y ) ) );
// Decord  ( End )
					
			d0 += nP0[j].Sub( nP0[i] ).Length();		// d0 += ( nP0[j] - nP0[i] ).Length();
		}
		n0.MulAssign( 0.5 );		// n0 *= 0.5;
		d0 *= 0.5;

		double	a0 = n0.Length();
		if( !Eps().IsZero( d0, eps ) && !Eps().IsZero( a0 / d0, eps ) )
			n0.DivAssign( a0 );		// n0 /= a0;
		else {
			a0 = 0.0;
			n0.SetXYZ( 0.0, 0.0, 0.0 );
		}

		if( a0p != null )
			a0p.val = a0;
			
		Assign( n0 );
	}
	/**
	 * �����_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g���Ɩʐς����߁A���g�ɒP�ʕ��ϖ@���x�N�g����������i�����j
	 * @param  nP0		(( I )) �_�̔z��
	 * @param  num0		(( I )) �_�̐�
	 * @param  a0p		(( O )) �ʐ�
	 */
	public final void
	Normal( lvVector nP0[], int num0, lvDouble a0p ) throws lvThrowable
	{
		Normal( nP0, num0, a0p, lvEps.l1 );
	}
	/**
	 * �����_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g�������߁A���g�ɑ������i�����j
	 * @param  nP0		(( I )) �_�̔z��
	 * @param  num0		(( I )) �_�̐�
	 */
	public final void
	Normal( lvVector nP0[], int num0 ) throws lvThrowable
	{
		Normal( nP0, num0, null, lvEps.l1 );
	}
	
	/**
	 * �����_�����ԑ��p�`�̒��S�ʒu�����߁A���g�ɑ������
	 * @param  nP0		(( I )) �_�̔z��
	 * @param  num0		(( I )) �_�̐�
	 * @return			���S�ʒu�i�K�����l�֐��� Assign�n�֐��ŕ�ނ��Ɓj
	 */
	public final void
	Center( lvVector nP0[], int num0 ) throws lvThrowable
	{
		lvVector  n0 = Gbl().tvCenter[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );	// n0 = new lvVector( 0.0, 0.0, 0.0 );

		for( int i=0; i<num0; i++ )
			n0.AddAssign( nP0[ i ] );
			
		n0.DivAssign( num0 );
		
		Assign( n0 );
	}

	/**
	 * lvVecDt�^�̃x�N�g���l�����g�ɃZ�b�g����
	 * @param  src		(( I )) lvVecDt�^�̃x�N�g���l
	 */
	public final void
	VecDt2Vector( lvVecDt src )
	{
		x = src.x;	y = src.y;	z = src.z;
	}
	/**
	 * ���g�̃x�N�g���l��lvVecDt�ϐ��ɃZ�b�g����
	 * @param  dst		(( O )) lvVecDt�ϐ�
	 */
	public final void
	Vector2VecDt( lvVecDt dst )
	{
		dst.x = x;	dst.y = y;	dst.z = z;
	}
	

// -------------------------------------------------------------------
//		lvKernelGreg�p API
// -------------------------------------------------------------------

	/**
	 * ���g��X,Y,Z�l���Z�b�g����
	 * @param  x		(( I )) X�l
	 * @param  y		(( I )) Y�l
	 * @param  x		(( I )) Z�l
	 * @return			this �̎Q��
	 */
	public final lvVector
	SetXYZ( double x, double y, double z )
	{
		this.x = x;  this.y = y;  this.z = z;
		return	this;
	}	
	
	/**
	 * ���g��P�ʃx�N�g���ɂ���i�񐄏��j
	 * @param  eps		(( I )) ���e�덷
	 * @return			this �̎Q��
	 */
	public final lvVector
	UnitAssign( double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVector.Unit(0)" );
		double	len2 = Length2();

		if( Eps().IsZero2( len2, eps ) )
			x = y = z = 0.0;
		else
			DivAssign( Math.sqrt( len2 ) );
			
		return	this;
	}
	/**
	 * ���g��P�ʃx�N�g���ɂ���i�����j
	 * @return			this �̎Q��
	 */
	public final lvVector
	UnitAssign() throws lvThrowable
	{
		return	UnitAssign( lvEps.l1 );
	}

	/**
	 * ���_��ʂ��]�� u0 �Ŏ��g����]������
	 * @param  u0		(( I )) ��]�������x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  a0		(( I )) ��]�p�i���W�A���j
	 * @return			this �̎Q��
	 */
	public final lvVector
	Rotate( lvVecCalc u0, double a0 )
	{
		a0 *= 0.5;
		lvDouble  sinval_r = Gbl().tdRotate0[0];		sinval_r.val = Math.sin( a0 );
					// sinval_r = new lvDouble( Math.sin( a0 ) );
		lvDouble  cosval_r = Gbl().tdRotate0[1];		cosval_r.val = Math.cos( a0 );
					// cosval_r = new lvDouble( Math.cos( a0 ) );

		lvDouble  s0_r = Gbl().tdRotate0[2];		s0_r.val = sinval_r.val * ( u0.Dot( this ) );
					// s0_r = new lvDouble( sinval_r.val * ( u0.Dot( this ) ) ); ... s0 = sinval * ( u0 * this )
		lvVector  v0   = Gbl().tvRotate0[0];										// v0 = new lvVector();
		v0.Assign( ( cosval_r.Mul( this ) ).Add( sinval_r.Mul( u0.Cross( this ) ) ) );
					// v0 = cosval * this + sinval * ( u0 % this )
		Assign( ( cosval_r.Mul( v0 ) ).Add( sinval_r.Mul( ( u0.Cross( v0 ) ).Add( s0_r.Mul( u0 ) ) ) ) );
					// this = cosval * v0 + sinval * ( u0 % v0 + s0 * u0 )

		return	this;
	}
	/**
	 * p0 ��ʂ��]�� u0 �Ŏ��g����]������
	 * @param  p0		(( I )) ��]����̓_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  u0		(( I )) ��]�������x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  a0		(( I )) ��]�p�i���W�A���j
	 * @return			this �̎Q��
	 */
/*
	public final lvVector
	Rotate( lvVecCalc p0, lvVecCalc u0, double a0 )
	{
		lvVector  v0 = Gbl().tvRotate1[0];			// v0 = new lvVector();
		v0.Assign( Sub( p0 ) );					// v0 = this - p0;
		v0.Rotate( u0, a0 );
		return	Assign( v0.Add( p0 ) );			// this = v0 + p0;
	}
*/

	/**
	 * ���g�̓_���܂߂�3�_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g���Ɩʐς����߂�i�񐄏��j	<br>
	 * ���g�͒P�ʕ��ϖ@���x�N�g���ƂȂ�
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  a0p		(( O )) �ʐ�
	 * @param  eps		(( I )) ���e�덷
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvDouble a0p, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.NormalAssign(00)" );

		lvVector  n0 = Gbl().tvNormalAssign0[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );		// n0 = new lvVector( 0.0, 0.0, 0.0 );
		lvVector  v  = Gbl().tvNormalAssign0[1];									// v  = new lvVector( *, *, * );

		n0.AddAssign( v.SetXYZ( ( y - p1.y ) * ( z + p1.z ),			// n0 += lvVector( ( y - p1.y ) * ( z + p1.z ),
								( z - p1.z ) * ( x + p1.x ),			//				   ( z - p1.z ) * ( x + p1.x ),
								( x - p1.x ) * ( y + p1.y ) ) );		//				   ( x - p1.x ) * ( y + p1.y ) );
		n0.AddAssign( v.SetXYZ( ( p1.y - p2.y ) * ( p1.z + p2.z ),		// n0 += lvVector( ( p1.y - p2.y ) * ( p1.z + p2.z ),
								( p1.z - p2.z ) * ( p1.x + p2.x ),		//				   ( p1.z - p2.z ) * ( p1.x + p2.x ),
								( p1.x - p2.x ) * ( p1.y + p2.y ) ) );	//				   ( p1.x - p2.x ) * ( p1.y + p2.y ) );
		n0.AddAssign( v.SetXYZ( ( p2.y - y ) * ( p2.z + z ),			// n0 += lvVector( ( p2.y - y ) * ( p2.z + z ),
								( p2.z - z ) * ( p2.x + x ),			//				   ( p2.z - z ) * ( p2.x + x ),
								( p2.x - x ) * ( p2.y + y ) ) );		//				   ( p2.x - x ) * ( p2.y + y ) );
// Decord ( Begin )
	//	n0.AddAssign( v.SetXYZ( ( y * p1.z - p1.y * z ), ( z * p1.x - p1.z * x ), ( x * p1.y - p1.x * y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p1.y * p2.z - p2.y * p1.z ), ( p1.z * p2.x - p2.z * p1.x ),  ( p1.x * p2.y - p2.x * p1.y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p2.y * z - y * p2.z ), ( p2.z * x - z * p2.x ), ( p2.x * y - x * p2.y ) ) );
// Decord  ( End )
 
		n0.MulAssign( 0.5 );	// n0 *= 0.5;

		double	d0 = 0.0;
		d0 = ( p1.Sub( this ) ).Length() + ( p2.Sub( p1 ) ).Length() + ( Sub( p2 ) ).Length();
				// d0 = ( p1 - this ).Length() + ( p2 - p1 ).Length() + ( this - p2 ).Length();
		d0 *= 0.5;

		double	a0 = n0.Length();
		if( !Eps().IsZero( d0, eps ) && !Eps().IsZero( a0 / d0, eps ) )
			n0.DivAssign( a0 );		// n0 /= a0;
		else {
			a0 = 0.0;
			n0.SetXYZ( 0.0, 0.0, 0.0 );
		}

		if( a0p != null )
			a0p.val = a0;
			
		Assign( n0 );
	}
	/**
	 * ���g�̓_���܂߂�3�_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g���Ɩʐς����߂�i�����j�j	<br>
	 * ���g�͒P�ʕ��ϖ@���x�N�g���ƂȂ�
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  a0p		(( O )) �ʐ�
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvDouble a0p ) throws lvThrowable
	{
		NormalAssign( p1, p2, a0p, lvEps.l1 );
	}
	/**
	 * ���g�̓_���܂߂�3�_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g�������߂�i�����j�j	<br>
	 * ���g�͒P�ʕ��ϖ@���x�N�g���ƂȂ�
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		NormalAssign( p1, p2, null, lvEps.l1 );
	}

	/**
	 * ���g�̓_���܂߂�4�_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g���Ɩʐς����߂�i�񐄏��j�j	<br>
	 * ���g�͒P�ʕ��ϖ@���x�N�g���ƂȂ�
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p3		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  a0p		(( O )) �ʐ�
	 * @param  eps		(( I )) ���e�덷
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3, lvDouble a0p, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.NormalAssign(10)" );

		lvVector  n0 = Gbl().tvNormalAssign1[0];    n0.SetXYZ( 0.0, 0.0, 0.0 );		// n0 = new lvVector( 0.0, 0.0, 0.0 );
		lvVector  v  = Gbl().tvNormalAssign1[1];									// v  = new lvVector( *, *, * );

		n0.AddAssign( v.SetXYZ( ( y - p1.y ) * ( z + p1.z ),			// n0 += lvVector( ( y - p1.y ) * ( z + p1.z ),
								( z - p1.z ) * ( x + p1.x ),			//				   ( z - p1.z ) * ( x + p1.x ),
								( x - p1.x ) * ( y + p1.y ) ) );		//				   ( x - p1.x ) * ( y + p1.y ) );
		n0.AddAssign( v.SetXYZ( ( p1.y - p2.y ) * ( p1.z + p2.z ),		// n0 += lvVector( ( p1.y - p2.y ) * ( p1.z + p2.z ),
								( p1.z - p2.z ) * ( p1.x + p2.x ),		//				   ( p1.z - p2.z ) * ( p1.x + p2.x ),
								( p1.x - p2.x ) * ( p1.y + p2.y ) ) );	//				   ( p1.x - p2.x ) * ( p1.y + p2.y ) );
		n0.AddAssign( v.SetXYZ( ( p2.y - p3.y ) * ( p2.z + p3.z ),		// n0 += lvVector( ( p2.y - p3.y ) * ( p2.z + p3.z ),
								( p2.z - p3.z ) * ( p2.x + p3.x ),		//				   ( p2.z - p3.z ) * ( p2.x + p3.x ),
								( p2.x - p3.x ) * ( p2.y + p3.y ) ) );	//				   ( p2.x - p3.x ) * ( p2.y + p3.y ) );
		n0.AddAssign( v.SetXYZ( ( p3.y - y ) * ( p3.z + z ),			// n0 += lvVector( ( p3.y - y ) * ( p3.z + z ),
								( p3.z - z ) * ( p3.x + x ),			//				   ( p3.z - z ) * ( p3.x + x ),
								( p3.x - x ) * ( p3.y + y ) ) );		//				   ( p3.x - x ) * ( p3.y + y ) );
// Decord ( Begin )
	//	n0.AddAssign( v.SetXYZ( ( y * p1.z - p1.y * z ), ( z * p1.x - p1.z * x ), ( x * p1.y - p1.x * y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p1.y * p2.z - p2.y * p1.z ), ( p1.z * p2.x - p2.z * p1.x ),  ( p1.x * p2.y - p2.x * p1.y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p2.y * p3.z - p3.y * p2.z ), ( p2.z * p3.x - p3.z * p2.x ),  ( p2.x * p3.y - p3.x * p2.y ) ) );
	//	n0.AddAssign( v.SetXYZ( ( p3.y * z - y * p3.z ), ( p3.z * x - z * p3.x ), ( p3.x * y - x * p3.y ) ) );
// Decord  ( End )

		n0.MulAssign( 0.5 );	// n0 *= 0.5;

		double	d0 = 0.0;
		d0 = ( p1.Sub( this ) ).Length() + ( p2.Sub( p1 ) ).Length() +
			 ( p3.Sub( p2	) ).Length() + (	Sub( p3 ) ).Length();
				// d0 = ( p1 - this ).Length() + ( p2 - p1 ).Length() + ( p3 - p2 ).Length() + ( this - p3 ).Length();
		d0 *= 0.5;

		double	a0 = n0.Length();
		if( !Eps().IsZero( d0, eps ) && !Eps().IsZero( a0 / d0, eps ) )
			n0.DivAssign( a0 );		// n0 /= a0;
		else {
			a0 = 0.0;
			n0.SetXYZ( 0.0, 0.0, 0.0 );
		}

		if( a0p != null )
			a0p.val = a0;
			
		Assign( n0 );
	}
	/**
	 * ���g�̓_���܂߂�4�_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g���Ɩʐς����߂�i�����j�j	<br>
	 * ���g�͒P�ʕ��ϖ@���x�N�g���ƂȂ�
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p3		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  a0p		(( O )) �ʐ�
	 */
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3, lvDouble a0p ) throws lvThrowable
	{
		NormalAssign( p1, p2, p3, a0p, lvEps.l1 );
	}
	/**
	 * ���g�̓_���܂߂�4�_�����ԑ��p�`�̒P�ʕ��ϖ@���x�N�g�������߂�i�����j�j	<br>
	 * ���g�͒P�ʕ��ϖ@���x�N�g���ƂȂ�
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p3		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 */
/*
	public final void
	NormalAssign( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3 ) throws lvThrowable
	{
		NormalAssign( p1, p2, p3, null, lvEps.l1 );
	}
*/

}
