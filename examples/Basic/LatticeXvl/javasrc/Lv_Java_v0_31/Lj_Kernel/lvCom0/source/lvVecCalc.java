//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvVecCalc.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * Vector�^�̐��l���Z�p�N���X    �i���ʕ��j<br>
 * ���jlvDblCalc,lvDouble,lvVecCalc,lvVector,lvMatCalc,lvMatrix�N���X���ȊO�ł́AlvVecCalc�I�u�W�F�N�g�𐶐����Ă͂Ȃ�Ȃ�
 * @author	  created by Eishin Matsui (99/08/17-)
 * 
 */
public class lvVecCalc extends lvRoot {
	
	/** �萔 --- ���Ȃ��Ƃ��A����̃x�N�g���̒����� 0 �ł���		*/
	public static final byte  LV_ANGSTAT_ZEROVEC	= 0;
	/** �萔 --- 2�{�̃x�N�g���̐����p�x�� 0 �ł���					*/
	public static final byte  LV_ANGSTAT_ZEROANG	= 1;
	/** �萔 --- 2�{�̃x�N�g���� G1�ڑ����Ă���						*/
	public static final byte  LV_ANGSTAT_G1			= 2;
	/** �萔 --- ���̑�												*/
	public static final byte  LV_ANGSTAT_OTHER		= 3;
	
// -------------------------------------------------------------------

	/** X�v�f */
	public double  x;
	/** Y�v�f */
	public double  y;
	/** Z�v�f */
	public double  z;
	
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
			GlobalBuf( dt );
			GlobalTmp( dt );
		}

		/** lvVecCalc��result�p�� new ��p�o�b�t�@��			*/
		private static final int  num_resultBufV  = 128;
		/** lvVecCalc��result�p�� new ��p�o�b�t�@�G���A		*/
		private lvVecCalc  resultBufV[]    = null;
		/** lvVecCalc��result�p�� new ��p�o�b�t�@�J�E���^		*/
		private int		   cnt_resultBufV  = 0;
	
		/** lvDblCalc��result�p�� new ��p�o�b�t�@��			*/
		private static final int  num_resultBufD  = 128;
		/** lvDblCalc��result�p�� new ��p�o�b�t�@�G���A		*/
		private lvDouble   resultBufD[]	  = null;
		/** lvDblCalc��result�p�� new ��p�o�b�t�@�J�E���^		*/
		private int		   cnt_resultBufD  = 0;
		
		/**
		 * result�p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		 */
		private final void
		GlobalBuf( lvGlobal dt )
		{
			resultBufV = new lvVecCalc[ num_resultBufV ];
			for( int i=0; i<num_resultBufV; i++ )
				resultBufV[ i ] = new lvVecCalc( dt );

			resultBufD = new lvDouble[ num_resultBufD ];
			for( int i=0; i<num_resultBufD; i++ )
				resultBufD[ i ] = new lvDouble( dt );
		}

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A		*/
//		private lvVector  tvOnLine[]      = null;
//		private lvVector  tvOnLnSeg[]     = null;
//		private lvDouble  tdOnLnSeg[]     = null;
		private lvVector  tvAngleStatus[] = null;
		private lvDouble  tdAngleStatus[] = null;
		private lvVector  tvIntersecLinePlane[] = null;

		/** ���[�J���ϐ��p�� new ��p�o�b�t�@�G���A�ilvKernelGreg�p�j		*/
//		private lvVector  tvAngle[]       = null;
//		private lvVector  tvDivide[]      = null;
		private lvDouble  tdIsLine[]      = null;
		private lvVector  tvIsLine[]      = null;
		private lvVector  tvIsPlane[]     = null;
		private lvDouble  tdIsPlane[]     = null;

		/**
		 * ���[�J���ϐ��p�� new ��p�o�b�t�@�������i�R���X�g���N�^�Ŏg�p�j
		 * @param  dt		(( I )) �O���[�o���f�[�^
		*/
		private final void
		GlobalTmp( lvGlobal dt )
		{
//			tvOnLine      = new lvVector[  8 ];		for( int i=0; i<8;  i++ )	tvOnLine[ i ]      = new lvVector( dt );
//			tvOnLnSeg     = new lvVector[  8 ];		for( int i=0; i<8;  i++ )	tvOnLnSeg[ i ]     = new lvVector( dt );
//			tdOnLnSeg     = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdOnLnSeg[ i ]     = new lvDouble( dt );
			tvAngleStatus = new lvVector[ 12 ];		for( int i=0; i<12; i++ )	tvAngleStatus[ i ] = new lvVector( dt );
			tdAngleStatus = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdAngleStatus[ i ] = new lvDouble( dt );
			tvIntersecLinePlane = new lvVector[ 4 ];
												for( int i=0; i<4; i++ )	tvIntersecLinePlane[ i ] = new lvVector( dt );
			
			// ���[�J���ϐ��p�� new ��p�o�b�t�@�������ilvKernelGreg�p�j
//			tvAngle       = new lvVector[  4 ];		for( int i=0; i<4;  i++ )	tvAngle[ i ]       = new lvVector( dt );
//			tvDivide      = new lvVector[  8 ];		for( int i=0; i<8;  i++ )	tvDivide[ i ]      = new lvVector( dt );
			tdIsLine      = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdIsLine[ i ]      = new lvDouble( dt );
			tvIsLine      = new lvVector[  2 ];		for( int i=0; i<2;  i++ )	tvIsLine[ i ]      = new lvVector( dt );
			tvIsPlane     = new lvVector[  4 ];		for( int i=0; i<4;  i++ )	tvIsPlane[ i ]     = new lvVector( dt );
			tdIsPlane     = new lvDouble[  2 ];		for( int i=0; i<2;  i++ )	tdIsPlane[ i ]     = new lvDouble( dt );
		}
	}

	/** ���N���X�p�̃O���[�o���f�[�^		*/
	private final Global
	Gbl()
	{
		return  ( ( lvComGblElm )global.GCom() ).gVecCalc;
	}

// -------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�BlvVecCalc,lvVector�N���X���ȊO�ł́A�g�p���Ă͂Ȃ�Ȃ�
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 */
	public  lvVecCalc( lvGlobal dt )
	{
		super( dt );
	}
	/**
	 * �R�s�[�R���X�g���N�^�BlvVecCalc,lvVector�N���X���ȊO�ł́A�g�p���Ă͂Ȃ�Ȃ�
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 * @param  val		(( I )) �R�s�[���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 */
	public  lvVecCalc( lvGlobal dt, lvVecCalc val )
	{
		super( dt );
		x = val.x;	y = val.y;	z = val.z;
	}
	/**
	 * �����l x,y,z �̃R���X�g���N�^�BlvVecCalc,lvVector�N���X���ȊO�ł́A�g�p���Ă͂Ȃ�Ȃ�
	 * @param  dt		(( I )) �O���[�o���f�[�^
	 * @param  x		(( I )) �R���X�g���N�^�̏����lX
	 * @param  y		(( I )) �R���X�g���N�^�̏����lY
	 * @param  z		(( I )) �R���X�g���N�^�̏����lZ
	 */
	public  lvVecCalc( lvGlobal dt, double x, double y, double z )
	{
		super( dt );
		this.x = x;  this.y = y;  this.z = z;
	}

// -------------------------------------------------------------------

	/**
	 * ���g�̃x�N�g���l�ɃR�s�[����BlvDouble,lvVecCalc,lvVector,lvMatCalc,lvMatrix�N���X���ȊO�ł́A�g�p���Ă͂Ȃ�Ȃ�
	 * @param  val		(( I )) �R�s�[���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			this �̎Q��
	 */
	public final lvVecCalc
	Copy_Local( lvVecCalc val )
	{
		x = val.x;	y = val.y;	z = val.z;
		return	this;
	}
	
	/**
	 * ���g��X,Y,Z�l���Z�b�g����
	 * @param  x		(( I )) X�l
	 * @param  y		(( I )) Y�l
	 * @param  x		(( I )) Z�l
	 * @return			this �̎Q��
	 */
	private lvVecCalc
	SetXYZ_Local( double x, double y, double z )
	{
		this.x = x;  this.y = y;  this.z = z;
		return	this;
	}	

	/** lvVecCalc��result�p�� new ��p�o�b�t�@�̃C���N�������g		*/
	private final void
	IncResultBufV()
	{
		Gbl().cnt_resultBufV = ( Gbl().cnt_resultBufV + 1 ) % Gbl().num_resultBufV;
	}
	/** lvDblCalc��result�p�� new ��p�o�b�t�@�̃C���N�������g		*/
	private final void
	IncResultBufD()
	{
		Gbl().cnt_resultBufD = ( Gbl().cnt_resultBufD + 1 ) % Gbl().num_resultBufD;
	}
	
	/**
	 * Vector�^�̉��Z�֐��i�񐄏��j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Add( lvVecCalc val, lvVecCalc result )
	{
		result.SetXYZ_Local( ( x + val.x ), ( y + val.y ), ( z + val.z ) );
		return	result;
	}
	/**
	 * Vector�^�̉��Z�֐��i�����j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Add( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Add( val, result );
	}

	/**
	 * Vector�^�̌��Z�֐��i�񐄏��j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Sub( lvVecCalc val, lvVecCalc result )
	{
		result.SetXYZ_Local( ( x - val.x ), ( y - val.y ), ( z - val.z ) );
		return	result;
	}
	/**
	 * Vector�^�̌��Z�֐��i�����j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Sub( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Sub( val, result );
	}

	/**
	 * Vector�^�̃X�J���[�{�֐��i�񐄏��j
	 * @param  k		(( I )) �X�J���[
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Mul( double k, lvVecCalc result )
	{
		result.SetXYZ_Local( ( x * k ), ( y * k ),	( z * k ) );
		return	result;
	}
	/**
	 * Vector�^�̃X�J���[�{�֐��i�����j
	 * @param  k		(( I )) �X�J���[
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Mul( double k )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Mul( k, result );
	}
	
	/**
	 * Vector�^�̃X�J���[���Z�֐��i�񐄏��j
	 * @param  k		(( I )) �X�J���[
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Div( double k, lvVecCalc result ) throws lvThrowable
	{
		Err().Assert( ( k != 0.0 ), "lvVecCalc.Div(0)" );
		result.SetXYZ_Local( ( x / k ), ( y / k ),	( z / k ) );
		return	result;
	}
	/**
	 * Vector�^�̃X�J���[���Z�֐��i�����j
	 * @param  k		(( I )) �X�J���[
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Div( double k ) throws lvThrowable
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Div( k, result );
	}

	/**
	 * Vector�^�̓��ϊ֐�
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			���ϒl�idouble:��{�f�[�^�^�j
	 */
	public final double
	Dot( lvVecCalc val )
	{
		return	x * val.x + y * val.y + z * val.z;
	}
	/**
	 * Vector�^�̓��ϊ֐��i�񐄏��j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			���ϒl�ilvDouble:�N���X�j
	 */
	public final lvDouble
	DotR( lvVecCalc val, lvDouble result )
	{
		result.val = Dot( val );
		return	result;
	}
	/**
	 * Vector�^�̓��ϊ֐��i�����j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			���ϒl�ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = DotR( b ).val; �̂悤�ɂ���B
	 */
	public final lvDouble
	DotR( lvVecCalc val )
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	DotR( val, result );
	}

	/**
	 * Vector�^�̊O�ϊ֐��i�񐄏��j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Cross( lvVecCalc val, lvVecCalc result )
	{
		result.SetXYZ_Local( ( y*val.z - z*val.y ), ( z*val.x - x*val.z ), ( x*val.y - y*val.x ) );
		return	result;
	}
	/**
	 * Vector�^�̊O�ϊ֐��i�����j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Cross( lvVecCalc val )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Cross( val, result );
	}

	/**
	 * ���g�i�x�N�g��:lvVector�j�ɍs�� m2 �ŃA�t�B���ϊ������x�N�g����Ԃ��i�񐄏��j
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
/*
	public final lvVecCalc
	MulVec( lvMatCalc m2, lvVecCalc result )
	{
		result.SetXYZ_Local( x * m2.m[0][0] + y * m2.m[1][0] + z * m2.m[2][0],
							 x * m2.m[0][1] + y * m2.m[1][1] + z * m2.m[2][1],
							 x * m2.m[0][2] + y * m2.m[1][2] + z * m2.m[2][2] );
		return	result;
	}
*/
	/**
	 * ���g�i�x�N�g��:lvVector�j�ɍs�� m2 �ŃA�t�B���ϊ������x�N�g����Ԃ��i�����j
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
/*
	public final lvVecCalc
	MulVec( lvMatCalc m2 )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	MulVec( m2, result );
	}
*/

	/**
	 * ���g�i3�������W�l�Ƃ��Ẵx�N�g��:lvVector�j�ɍs�� m2 �ŃA�t�B���ϊ������x�N�g����Ԃ��i�񐄏��j
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
/*
	public final lvVecCalc
	MulPos( lvMatCalc m2, lvVecCalc result )
	{
		result.SetXYZ_Local( x * m2.m[0][0] + y * m2.m[1][0] + z * m2.m[2][0] + m2.m[3][0],
							 x * m2.m[0][1] + y * m2.m[1][1] + z * m2.m[2][1] + m2.m[3][1],
							 x * m2.m[0][2] + y * m2.m[1][2] + z * m2.m[2][2] + m2.m[3][2] );
		return	result;
	}
*/
	/**
	 * ���g�i3�������W�l�Ƃ��Ẵx�N�g��:lvVector�j�ɍs�� m2 �ŃA�t�B���ϊ������x�N�g����Ԃ��i�����j
	 * @param  m0		(( I )) �s��B�K�� lvMatrix�^�ϐ��A�܂��͐��l�֐����g������i lvMatCalc�^�͎g�p���Ȃ����� �j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
/*
	public final lvVecCalc
	MulPos( lvMatCalc m2 )
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	MulPos( m2, result );
	}
*/

	/**
	 * Vector�^�̔��]�֐��i�񐄏��j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Neg( lvVecCalc result )
	{
		result.SetXYZ_Local( -x, -y, -z );
		return	result;
	}
	/**
	 * Vector�^�̔��]�֐��i�����j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Neg()
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Neg( result );
	}

	/**
	 * ���g��P�ʃx�N�g���ɂ���i�񐄏��j
	 * @param  result	(( O )) �K�� lvVector�^�ϐ��Ƃ���i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			result �̎Q��
	 */
	public final lvVecCalc
	Unit( lvVecCalc result, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVector.Unit(0)" );
		double	len2 = Length2();
		
		if( Eps().IsZero2( len2, eps ) )
			result.x = result.y = result.z = 0.0;
		else {
			double  len = Math.sqrt( len2 );
			result.SetXYZ_Local( x/len, y/len, z/len );
		}
			
		return	result;
	}
	/**
	 * ���g��P�ʃx�N�g���ɂ���i�񐄏��j
	 * @param  eps		(( I )) ���e�덷
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Unit( double eps ) throws lvThrowable
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Unit( result, eps );
	}
	/**
	 * ���g��P�ʃx�N�g���ɂ���i�����j
	 * @return			�K�����l�֐��� Assign�n�֐��ŕ�ނ���
	 */
	public final lvVecCalc
	Unit() throws lvThrowable
	{
		lvVecCalc  result = Gbl().resultBufV[ Gbl().cnt_resultBufV ];	// result = new lvVecCalc();
		IncResultBufV();
		return	Unit( result, lvEps.l1 );
	}

	/**
	 * ���g�i�x�N�g���j�� eps �̌덷���� 0 ���ǂ������肷��i�񐄏��j
	 * @param  eps		(( I )) ���e�덷
	 * @return			��0: false,		0: true
	 */
	public final boolean
	IsZero( double eps ) throws lvThrowable
	{
		Err().Assert( ( Dot( this ) >= 0.0 ), "lvVecCalc.IsZero(0)" );
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsZero(1)" );

		if( x < -eps || x > eps )
			return	false;

		if( x == 0.0 && y == 0.0 && z == 0.0 )
			return	true;

		if( y < -eps || y > eps )
			return	false;
		if( z < -eps || z > eps )
			return	false;

		Err().Assert( ( eps > 0.0 ), "lvVecCalc.IsZero(2)" );
		double	eps_sqrt1_3 = ( eps == lvEps.l1 )
			? lvEps.l1xSqrt1_3 : eps * lvConst.LV_SQRT1_3;
		if( -eps_sqrt1_3 <= x && x <= eps_sqrt1_3 &&
			-eps_sqrt1_3 <= y && y <= eps_sqrt1_3 &&
			-eps_sqrt1_3 <= z && z <= eps_sqrt1_3 )
		{
			return	true;
		}
	
		return	Eps().IsZero2( Dot( this ), eps );
	}
	/**
	 * ���g�i�x�N�g���j�� lvEps.l1 �̌덷���� 0 ���ǂ������肷��i�����j
	 * @return			��0: false,		0: true
	 */
	public final boolean
	IsZero() throws lvThrowable
	{
		return	IsZero( lvEps.l1 );
	}

	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� v0 �� eps �̌덷���œ��������ǂ������肷��i�񐄏��j
	 * @param  v0		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			�������Ȃ�: false,	������: true
	 */
	public final boolean
	IsSame( lvVecCalc v0, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsSame(0)" );
		return	v0.Sub( this ).IsZero( eps );		// v0.Sub( this ) ... ( v0 - this )
	}
	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� v0 �� lvEps.l1 �̌덷���œ��������ǂ������肷��i�����j
	 * @param  v0		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�������Ȃ�: false,	������: true
	 */
	public final boolean
	IsSame( lvVecCalc v0 ) throws lvThrowable
	{
		return	IsSame( v0, lvEps.l1 );
	}

	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� v0 �� eps �̌덷���Ő������ǂ������肷��i�񐄏��j
	 * @param  v0		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			�����łȂ�: false,	����: true
	 */
/*
	public final boolean
	IsPerp( lvVecCalc u0, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPerp(0)" );
		Err().Assert( Eps().IsSame21( u0.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPerp(1)" );
		Err().Assert( ( 0.0 <= eps && eps < 1.0 ), "lvVecCalc.IsPerp(2)" );
		return	( Math.abs( Dot( u0 ) ) <= eps ) ? true : false;		// Dot( u0 ) ... ( this * u0 )
	}
*/
	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� v0 �� lvEps.a0 �̌덷���Ő������ǂ������肷��i�����j
	 * @param  v0		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�����łȂ�: false,	����: true
	 */
/*
	public final boolean
	IsPerp( lvVecCalc u0 ) throws lvThrowable
	{
		return	IsPerp( u0, lvEps.a0 );
	}
*/

	/**
	 * ���g�̃x�N�g������Ԃ�
	 * @return			�x�N�g�����idouble:��{�f�[�^�^�j
	 */
	public final double
	Length() throws lvThrowable
	{
		return	Math.sqrt( Length2() );
	}
	/**
	 * ���g�̃x�N�g������Ԃ��i�񐄏��j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�x�N�g�����ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	LengthR( lvDouble result ) throws lvThrowable
	{
		result.val = Length();
		return	result;
	}
*/
	/**
	 * ���g�̃x�N�g������Ԃ��i�����j
	 * @return			�x�N�g�����ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = LengthR().val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	LengthR() throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];		// result = new lvDouble();
		IncResultBufD();
		return	LengthR( result );
	}
*/
	
	/**
	 * ���g�̃x�N�g�����̕�����Ԃ�
	 * @return			�x�N�g�����̕����idouble:��{�f�[�^�^�j
	 */
	public final double
	Length2() throws lvThrowable
	{
		Err().Assert( ( Dot( this ) >= 0.0 ), "lvVecCalc.Length2(0)" );		// Dot( this ) ... ( this * this )
		return	Dot( this );
	}
	/**
	 * ���g�̃x�N�g�����̕�����Ԃ��i�񐄏��j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�x�N�g�����̕����ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	Length2R( lvDouble result ) throws lvThrowable
	{
		result.val = Length2();
		return	result;
	}
*/
	/**
	 * ���g�̃x�N�g�����̕�����Ԃ��i�����j
	 * @return			�x�N�g�����̕����ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = Length2R().val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	Length2R() throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];		// result = new lvDouble();
		IncResultBufD();
		return	Length2R( result );
	}
*/

	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� val �̋��������߂�
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�����idouble:��{�f�[�^�^�j
	 */
/*
	public final double
	Dist( lvVecCalc val ) throws lvThrowable
	{
		return	Math.sqrt( Dist2( val ) );
	}
*/
	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� val �̋��������߂�i�񐄏��j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�����ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	DistR( lvVecCalc val, lvDouble result ) throws lvThrowable
	{
		result.val = Dist( val );
		return	result;
	}
*/
	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� val �̋��������߂�i�����j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�����ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = DistR( b ).val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	DistR( lvVecCalc val ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	DistR( val, result );
	}
*/
	
	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� val �̋����̕��������߂�
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�����̕����idouble:��{�f�[�^�^�j
	 */
/*
	public final double
	Dist2( lvVecCalc val ) throws lvThrowable
	{
		return	Sub( val ).Length2();
	}
*/
	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� val �̋����̕��������߂�i�񐄏��j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�����̕����ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	Dist2R( lvVecCalc val, lvDouble result ) throws lvThrowable
	{
		result.val = Dist2( val );
		return	result;
	}
*/
	/**
	 * ���g�i�x�N�g���j�ƃx�N�g�� val �̋����̕��������߂�i�����j
	 * @param  val		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�����̕����ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = Dist2R( b ).val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	Dist2R( lvVecCalc val ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	Dist2R( val, result );
	}
*/

	/**
	 * ���g�̓_��������ɂ��邩�ǂ����𒲂ׂ�i�񐄏��j
	 * @param  p1		(( I )) �����̎n�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �����̏I�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			������ɂȂ�:false,  ������:true
	 */
/*
	public final boolean
	OnLine( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.OnLine(0)" );

		lvVector  v1 = Gbl().tvOnLine[0];					// v1 = new lvVector();
		v1.Assign( Sub( p1 ) );							// v1 = this - p1;
		if( v1.IsZero( eps ) )
			return	true;
		lvVector  v2 = Gbl().tvOnLine[1];					// v2 = new lvVector();
		v2.Assign( Sub( p2 ) );							// v2 = this - p2;
		if( v2.IsZero( eps ) )
			return	true;
		lvVector  v0 = Gbl().tvOnLine[2];					// v0 = new lvVector();
		v0.Assign( p2.Sub( p1 ) );						// v0 = p2 - p1;
		if( v0.IsZero( eps ) )
			return	false;

		lvVector  n0 = Gbl().tvOnLine[3];					// n0 = new lvVector();
		n0.Assign( v0.Cross( v1 ) );					// n0 = v0 % v1;
		double	  d0 = n0.Dot( n0 ) / v0.Dot( v0 );		// d0 = ( n0 * n0 ) / ( v0 * v0 );
		return	Eps().IsZero2( d0, eps );
	}
*/
	/**
	 * ���g�̓_��������ɂ��邩�ǂ����𒲂ׂ�i�����j
	 * @param  p1		(( I )) �����̎n�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �����̏I�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			������ɂȂ�:false,  ������:true
	 */
/*
	public final boolean
	OnLine( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	OnLine( p1, p2, lvEps.l1 );
	}
*/

	/**
	 * ���g�̓_��������ɂ��邩�ǂ����𒲂ׂ�i�񐄏��j
	 * @param  p1		(( I )) �����̎n�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �����̏I�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			������ɂȂ�:false,  ������:true
	 */
/*
	public final boolean
	OnLnSeg( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.OnLnSeg(0)" );

		lvVector  v1 = Gbl().tvOnLnSeg[0];			// v1 = new lvVector();
		v1.Assign( Sub( p1 ) );					// v1 = this - p1;
		if( v1.IsZero( eps ) )
			return	true;
		lvVector  v2 = Gbl().tvOnLnSeg[1];			// v2 = new lvVector();
		v2.Assign( Sub( p2 ) );					// v2 = this - p2;
		if( v2.IsZero( eps ) )
			return	true;
		lvVector  v0 = Gbl().tvOnLnSeg[2];			// v0 = new lvVector();
		v0.Assign( p2.Sub( p1 ) );				// v0 = p2 - p1;
		if( v0.IsZero( eps ) )
			return	false;

		double	t0 = v0.Dot( v1 ) / v0.Dot( v0 );	// t0 = ( v0 * v1 ) / ( v0 * v0 );
		if( t0 < 0.0 || 1.0 < t0 )
			return	false;
		lvDouble  t0_r = Gbl().tdOnLnSeg[0];	t0_r.val =  t0;			// t0_r = new lvDouble( t0 );
		lvVector  p0   = Gbl().tvOnLnSeg[3];							// p0	= new lvVector();
		p0.Assign( p1.Add( t0_r.Mul( v0 ) ) );		// p0 = p1 + t0 * v0;
		return	IsSame( p0, eps );
	}
*/
	/**
	 * ���g�̓_��������ɂ��邩�ǂ����𒲂ׂ�i�����j
	 * @param  p1		(( I )) �����̎n�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �����̏I�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			������ɂȂ�:false,  ������:true
	 */
/*
	public final boolean
	OnLnSeg( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	OnLnSeg( p1, p2, lvEps.l1 );
	}
*/
	
	/**
	 * �u���g�̓_�Ɠ_1����Ȃ�����v�Ɓu���g�̓_�Ɠ_2����Ȃ�����v�̊p�x�Ɋւ����Ԃ𒲂ׂ�i�񐄏��j
	 * @param  p1		(( I )) �_1�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_2�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			LV_ANGSTAT_ZEROVEC, LV_ANGSTAT_ZEROANG, LV_ANGSTAT_G1, LV_ANGSTAT_OTHER �̂��Âꂩ
	 */
	public final int
	AngleStatus( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.AngleStatus(0)" );

		lvVector  v1 = Gbl().tvAngleStatus[0];				// v1 = new lvVector();
		v1.Assign( Sub( p1 ) );							// v1 = this - p1;
		if( v1.IsZero( eps ) )
			return	LV_ANGSTAT_ZEROVEC;
		lvVector  v2 = Gbl().tvAngleStatus[1];				// v2 = new lvVector();
		v2.Assign( Sub( p2 ) );							// v2 = this - p2;
		if( v2.IsZero( eps ) )
			return	LV_ANGSTAT_ZEROVEC;
		lvVector  v0 = Gbl().tvAngleStatus[2];				// v0 = new lvVector();
		v0.Assign( p2.Sub( p1 ) );						// v0 = p2 - p1;
		if( v0.IsZero( eps ) )
			return	LV_ANGSTAT_ZEROANG;

		double	  t0;
		lvDouble  t0_r = Gbl().tdAngleStatus[0];			// t0_r = new lvDouble( t0 );
		lvVector  p0   = Gbl().tvAngleStatus[3];			// p0	= new lvVector();

		t0 = v0.Dot( v1 ) / v0.Dot( v0 );				// t0 = ( v0 * v1 ) / ( v0 * v0 );
		if( 0.0 <= t0 && t0 <= 1.0 ) {
			t0_r.val =  t0;
			p0.Assign( p1.Add( t0_r.Mul( v0 ) ) );		// p0 = p1 + t0 * v0;
			boolean  same = IsSame( p0, eps );
			if( same == true )
				return  LV_ANGSTAT_G1;
			else
				return  LV_ANGSTAT_OTHER;
		}
		
		lvVector  ps = Gbl().tvAngleStatus[4];				// ps = new lvVector();
		lvVector  pl = Gbl().tvAngleStatus[5];				// pl = new lvVector();
		lvVector  vs, vl;
		if( v1.Length2() >= v2.Length2() ) {
			pl.Assign( p1 );		vl = v1;
			ps.Assign( p2 );		vs = v2;
		}
		else {
			pl.Assign( p2 );		vl = v2;
			ps.Assign( p1 );		vs = v1;
		}
		vl.NegAssign();									// vl = -vl;
		vs.NegAssign();									// vs = -vs;
		
		t0 = vl.Dot( vs ) / vl.Dot( vl );				// t0 = ( vl * vs ) / ( vl * vl );
		if( 0.0 <= t0 && t0 <= 1.0 ) {
			t0_r.val =  t0;
			p0.Assign( Add( t0_r.Mul( vl ) ) );			// p0 = this + t0 * vl;
			boolean  same = ps.IsSame( p0, eps );
			if( same == true )
				return  LV_ANGSTAT_ZEROANG;
			else
				return  LV_ANGSTAT_OTHER;
		}
		
		return  LV_ANGSTAT_OTHER;
	}
	/**
	 * �u���g�̓_�Ɠ_1����Ȃ�����v�Ɓu���g�̓_�Ɠ_2����Ȃ�����v�̊p�x�Ɋւ����Ԃ𒲂ׂ�i�����j
	 * @param  p1		(( I )) �_1�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_2�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			LV_ANGSTAT_ZEROVEC, LV_ANGSTAT_ZEROANG, LV_ANGSTAT_G1, LV_ANGSTAT_OTHER �̂��Âꂩ
	 */
	public final int
	AngleStatus( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	AngleStatus( p1, p2, lvEps.l1 );
	}

	/**
	 * �u���g���n�_�Ƃ�������v�ƕ��ʂ��������Ă��邩�ǂ������ׂ�i�񐄏��j
	 * @param  lEnd		(( I )) �����̏I�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  org		(( I )) ���ʏ��1�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  normal	(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @param  crossPos (( O )) �����_�̈ʒu
	 * @param  crossT   (( O )) �����_�� t�l
	 * @return			LV_INTERSEC_PARA, LV_INTERSEC_ON, LV_INTERSEC_CROSS �̂��Âꂩ
	 */
	public final int
	IntersecLinePlane( lvVecCalc lEnd, lvVecCalc org, lvVecCalc normal, double eps, lvVector crossPos,
			lvDouble crossT ) throws lvThrowable
	{
		lvVector  vl = Gbl().tvIntersecLinePlane[0];			// vl = new lvVector();
		vl.Assign( lEnd.Sub( this ) );							// vl = lEnd - this;
		
		if( vl.IsZero( eps ) == true ) {
			lvVector  vd = Gbl().tvIntersecLinePlane[1];						// vd = new lvVector();
			vd.Assign( ( ( Add( lEnd ) ).Div( 2.0 ) ).Sub( org ) );				// vd = ( this + lEnd ) / 2.0 - org;
			if( Eps().IsZero( vd.Dot( normal ), eps ) == true )					// vd.Dot( normal ) --- vd * normal
				return  lvVector.LV_INTERSEC_ON;
				
			return  lvVector.LV_INTERSEC_PARA;
		}
		
		double  nor_vl = normal.Dot( vl );										// nor_vl = normal * vl;
		if( Eps().IsZero( nor_vl, eps ) == true ) {
			if( Eps().IsZero( ( Sub( org ) ).Dot( normal ), eps ) == true )		// ( this - org ) * normal
				return  lvVector.LV_INTERSEC_ON;
				
			return  lvVector.LV_INTERSEC_PARA;
		}
		
		crossT.val = -( normal.Dot( Sub( org ) ) ) / ( normal.Dot( vl ) );
									// crossT.val = -( normal * ( this - org ) ) / ( normal * vl );
		crossPos.Assign( Add( vl.Mul( crossT.val ) ) );							// crossPos = this + vl * crossT.val;
		
		return  lvVector.LV_INTERSEC_CROSS;
	}	 
	/**
	 * �u���g���n�_�Ƃ�������v�ƕ��ʂ��������Ă��邩�ǂ������ׂ�i�����j
	 * @param  lEnd		(( I )) �����̏I�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  org		(( I )) ���ʏ��1�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  normal	(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  crossPos (( O )) �����_�̈ʒu
	 * @param  crossT   (( O )) �����_�� t�l
	 * @return			LV_INTERSEC_PARA, LV_INTERSEC_ON, LV_INTERSEC_CROSS �̂��Âꂩ
	 */
	public final int
	IntersecLinePlane( lvVecCalc lEnd, lvVecCalc org, lvVecCalc normal, lvVector crossPos, lvDouble crossT ) throws lvThrowable
	{
		return  IntersecLinePlane( lEnd, org, normal, lvEps.l1, crossPos, crossT );
	}


// -------------------------------------------------------------------
//		lvKernelGreg�p API
// -------------------------------------------------------------------

	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� v0 �� eps �̌덷���ŕ��s���ǂ������肷��i�񐄏��j
	 * @param  v0		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			���s�łȂ�: false,	���s: true
	 */
	public final boolean
	IsPara( lvVecCalc u0, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPara(0)" );
		Err().Assert( Eps().IsSame21( u0.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.IsPara(1)" );
		Err().Assert( ( 0.0 <= eps && eps < 1.0 ), "lvVecCalc.IsPara(2)" );
		return	( IsSame( u0, eps ) || IsSame( u0.Neg(), eps ) ) ? true : false;	// u0.Neg() ... -u0
	}
	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� v0 �� lvEps.a0 �̌덷���ŕ��s���ǂ������肷��i�����j
	 * @param  v0		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			���s�łȂ�: false,	���s: true
	 */
	public final boolean
	IsPara( lvVecCalc u0 ) throws lvThrowable
	{
		return	IsPara( u0, lvEps.a0 );
	}
	
	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� u1 �̊p�x�����߂�
	 * @param  u1		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�p�x�F���W�A���idouble:��{�f�[�^�^�j
	 */
	public final double
	Angle( lvVecCalc u1 ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( Length2(), 1.0, lvEps.e0 ), "lvVecCalc.Angle(00)" );
		Err().Assert( Eps().IsSame21( u1.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.Angle(01)" );

		double	f01 = Dot( u1 );

		if( f01 < -( 1.0 - 1.0e-12 ) ) {
			f01 = Add( u1 ).Length() / 2.0;						// Add( u1 ) ... this + u1
			return	lvConst.LV_PI - 2.0 * Math.asin( f01 );
		}

		if( f01 > ( 1.0 - 1.0e-12 ) ) {
			f01 = Sub( u1 ).Length() / 2.0;						// Sub( u1 ) ... this - u1
			return	2.0 * Math.asin( f01 );
		}

		return	Math.acos( f01 );
	}
	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� u1 �̊p�x�����߂�i�񐄏��j
	 * @param  u1		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�p�x�F���W�A���ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc u1, lvDouble result ) throws lvThrowable
	{
		result.val = Angle( u1 );
		return	result;
	}
*/
	/**
	 * ���g�i�P�ʃx�N�g���j�ƒP�ʃx�N�g�� u1 �̊p�x�����߂�i�����j
	 * @param  u1		(( I )) �P�ʃx�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�p�x�F���W�A���ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = AngleR( b ).val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc u1 ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	AngleR( u1, result );
	}
*/

	/**
	 * ���ʂɎˉe�������g�i�x�N�g���j�ƃx�N�g�� v1 �̊p�x�����߂�i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) ���ʂ̖@���x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			�p�x�F���W�A���idouble:��{�f�[�^�^�j
	 */
/*
	public final double
	Angle( lvVecCalc v1, lvVecCalc n0, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( n0.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.Angle(10)" );
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.Angle(11)" );

		lvVector  v2 = Gbl().tvAngle[ 0 ];						// v2 = new lvVector();
		v2.Assign( Sub( ( DotR( n0 ) ).Mul( n0 ) ) );			// v2 = this - ( this * n0 ) * n0;
		if( v2.UnitAssign( eps ).IsZero( lvEps.e0 ) )
			return	lvConst.LV_PI_2;

		lvVector  v3 = Gbl().tvAngle[ 1 ];						// v3 = new lvVector();
		v3.Assign( v1.Sub( ( v1.DotR( n0 ) ).Mul( n0 ) ) );		// v3 = v1 - ( v1 * n0 ) * n0;
		if( v3.UnitAssign( eps ).IsZero( lvEps.e0 ) )
			return	lvConst.LV_PI_2;

		double	theta = v2.Angle( v3 );
		Err().Assert( ( 0.0 <= theta && theta <= lvConst.LV_PI ), "lvVecCalc.Angle(12)" );
		theta = ( ( v2.Cross( v3 ) ).Dot( n0 ) >= 0.0 ) ? theta : lvConst.LV_2PI - theta;
					// ( v2.Cross( v3 ) ).Dot( n0 ) ... ( v2 % v3 ) * n0
		Err().Assert( ( 0.0 <= theta && theta <= lvConst.LV_2PI ), "lvVecCalc.Angle(13)" );
		return	( theta < lvConst.LV_2PI ) ? theta : 0.0;
	}
*/
	/**
	 * ���ʂɎˉe�������g�i�x�N�g���j�ƃx�N�g�� v1 �̊p�x�����߂�i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�p�x�F���W�A���ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0, double eps, lvDouble result ) throws lvThrowable
	{
		result.val = Angle( v1, n0, eps );
		return	result;
	}
*/
	/**
	 * ���ʂɎˉe�������g�i�x�N�g���j�ƃx�N�g�� v1 �̊p�x�����߂�i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			�p�x�F���W�A���ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = AngleR( b, c, e ).val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0, double eps ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	AngleR( v1, n0, eps, result );
	}
*/

	/**
	 * ���ʂɎˉe�������g�i�x�N�g���j�ƃx�N�g�� v1 �̊p�x�����߂�i�����j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�p�x�F���W�A���idouble:��{�f�[�^�^�j
	 */
/*
	public final double
	Angle( lvVecCalc v1, lvVecCalc n0 ) throws lvThrowable
	{
		return	Angle( v1, n0, lvEps.l1 );
	}
*/
	/**
	 * ���ʂɎˉe�������g�i�x�N�g���j�ƃx�N�g�� v1 �̊p�x�����߂�i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  result	(( I )) �Ԃ�l
	 * @return			�p�x�F���W�A���ilvDouble:�N���X�j
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0, lvDouble result ) throws lvThrowable
	{
		result.val = Angle( v1, n0, lvEps.l1 );
		return	result;
	}
*/
	/**
	 * ���ʂɎˉe�������g�i�x�N�g���j�ƃx�N�g�� v1 �̊p�x�����߂�i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n0		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			�p�x�F���W�A���ilvDouble:�N���X�j�B�K�����l�֐��ŕ�ނ��Adouble a = AngleR( b, c ).val; �̂悤�ɂ���B
	 */
/*
	public final lvDouble
	AngleR( lvVecCalc v1, lvVecCalc n0 ) throws lvThrowable
	{
		lvDouble  result = Gbl().resultBufD[ Gbl().cnt_resultBufD ];	// result = new lvDouble();
		IncResultBufD();
		return	AngleR( v1, n0, lvEps.l1, result );
	}
*/

	/**
	 * ���g��2�x�N�g���ɕ�������i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  v2		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  e1p		(( O )) v1�����̐���
	 * @param  e2p		(( O )) v2�����̐���
	 * @param  eps1		(( I )) ������ 0 �ƌ��Ȃ����e�덷
	 * @param  eps0		(( I )) �p�x�^�p�����[�^�� 0 �ƌ��Ȃ����e�덷
	 * @return			�����ł������H �����ł���:false,  ����:true
	 */
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvDouble e1p, lvDouble e2p, double eps1, double eps0 ) throws lvThrowable
	{
		Err().Assert( ( eps1 >= 0.0 ), "lvVecCalc.Divide(00)" );
		Err().Assert( ( 0.0 <= eps0 && eps0 < 1.0 ), "lvVecCalc.Divide(01)" );

		double	f01 = Dot( v1 );				// f01 = this * v1;
		double	f02 = Dot( v2 );				// f02 = this * v2;
		double	f11 = v1.Dot( v1 );				// f11 = v1 * v1;
		double	f12 = v1.Dot( v2 );				// f12 = v1 * v2;
		double	f22 = v2.Dot( v2 );				// f22 = v2 * v2;
		double	eps2 = ( eps1 == lvEps.l1 ) ? lvEps.l1xl1 : eps1 * eps1;

		if( f11 <= eps2 ) {
			if( f22 <= eps2 ) {
				e1p.val = 0.0;
				e2p.val = 0.0;
				return	false;
			}
			e1p.val = 0.0;
			e2p.val = f02 / f22;
			return	IsSame( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ), eps1 );	// e1p * v1 + e2p * v2
		}

		if( f22 <= eps2 ) {
			e1p.val = f01 / f11;
			e2p.val = 0.0;
			return	IsSame( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ), eps1 );	// e1p * v1 + e2p * v2
		}

		double	det = f11 * f22 - f12 * f12;
		if( det <= f11 * f22 * eps0 * eps0 ) {
			e1p.val = ( ( f11 >= f22 ) ? f01 / f11 : f02 / f22 );
			e2p.val = 0.0;
			return	IsSame( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ), eps1 );	// e1p * v1 + e2p * v2
		}

		e1p.val = ( f01 * f22 - f12 * f02 ) / det;
		e2p.val = ( f11 * f02 - f01 * f12 ) / det;
		return	true;
	}
	/**
	 * ���g��2�x�N�g���ɕ�������i�����j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  v2		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  e1p		(( O )) v1�����̐���
	 * @param  e2p		(( O )) v2�����̐���
	 * @return			�����ł������H �����ł���:false,  ����:true
	 */
/*
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvDouble e1p, lvDouble e2p ) throws lvThrowable
	{
		return	Divide( v1, v2, e1p, e2p, lvEps.l1, lvEps.a0 );
	}
*/
	
	/**
	 * ���g��3�x�N�g���ɕ�������i�񐄏��j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  v2		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  v3		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  e1p		(( O )) v1�����̐���
	 * @param  e2p		(( O )) v2�����̐���
	 * @param  e2p		(( O )) v3�����̐���
	 * @param  eps1		(( I )) ������ 0 �ƌ��Ȃ����e�덷
	 * @param  eps0		(( I )) �p�x�^�p�����[�^�� 0 �ƌ��Ȃ����e�덷
	 * @return			�����ł������H �����ł���:false,  ����:true
	 */
/*
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvVecCalc v3, lvDouble e1p, lvDouble e2p, lvDouble e3p, double eps1, double eps0 )
			throws lvThrowable
	{
		Err().Assert( ( eps1 >= 0.0 ), "lvVecCalc.Divide(10)" );
		Err().Assert( ( 0.0 <= eps0 && eps0 < 1.0 ), "lvVecCalc.Divide(11)" );

		lvVector  u1 = Gbl().tvDivide[0];			// u1 = new lvVector();
		u1.Assign( v2.Cross( v3 ) );			// u1 = v2 % v3;
		double	  k1 = v1.Dot( u1 );			// k1 = v1 * u1;
		if( !Eps().IsZero( k1, eps1 ) && !Eps().IsZero( k1, eps0 ) )
			e1p.val = Dot( u1 ) / k1;			// e1p = ( this * u1 ) / k1;
		else
			e1p.val = 0.0;

		lvVector  u2 = Gbl().tvDivide[1];			// u2 = new lvVector();
		u2.Assign( v3.Cross( v1 ) );			// u2 = v3 % v1;
		double	  k2 = v2.Dot( u2 );			// k2 = v2 * u2;
		if( !Eps().IsZero( k2, eps1 ) && !Eps().IsZero( k2, eps0 ) )
			e2p.val =  Dot( u2 ) / k2;			// e2p = ( this * u2 ) / k2;
		else
			e2p.val =  0.0;

		lvVector  u3 = Gbl().tvDivide[2];			// u3 = new lvVector();
		u3.Assign( v1.Cross( v2 ) );			// u3 = v1 % v2;
		double	  k3 = v3.Dot( u3 );			// k3 = v3 * u3;
		if( !Eps().IsZero( k3, eps1 ) && !Eps().IsZero( k3, eps0 ) )
			e3p.val = Dot( u3 ) / k3;			// e3p = ( this * u3 ) / k3;
		else
			e3p.val = 0.0;

		return	IsSame( ( ( e1p.Mul( v1 ) ).Add( e2p.Mul( v2 ) ) ).Add( e3p.Mul( v3 ) ), eps1 );
						// e1p * v1 + e2p * v2 + e3p * v3
	}
*/
	/**
	 * ���g��3�x�N�g���ɕ�������i�����j
	 * @param  v1		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  v2		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  v3		(( I )) �x�N�g���B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  e1p		(( O )) v1�����̐���
	 * @param  e2p		(( O )) v2�����̐���
	 * @param  e2p		(( O )) v3�����̐���
	 * @return			�����ł������H �����ł���:false,  ����:true
	 */
/*
	public final boolean
	Divide( lvVecCalc v1, lvVecCalc v2, lvVecCalc v3, lvDouble e1p, lvDouble e2p, lvDouble e3p ) throws lvThrowable
	{
		return	Divide( v1, v2, v3, e1p, e2p, e3p, lvEps.l1, lvEps.a0 );
	}
*/
	
	/**
	 * ���g�̓_�����ʏ�ɂ��邩�ǂ����𒲂ׂ�i�񐄏��j
	 * @param  p1		(( I )) ���ʏ��1�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n1		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			������ɂȂ�:false,  ������:true
	 */
	public final boolean
	OnPlane( lvVecCalc p1, lvVecCalc n1, double eps ) throws lvThrowable
	{
		Err().Assert( Eps().IsSame21( n1.Length2(), 1.0, lvEps.e0 ), "lvVecCalc.OnPlane(0)" );
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.OnPlane(1)" );

		return	( Math.abs( ( Sub( p1 ) ).Dot( n1 ) ) <= eps ) ? true : false;
					// ( Sub( p1 ) ).Dot( n1 ) ... ( this - p1 ) * n1
	}
	/**
	 * ���g�̓_�����ʏ�ɂ��邩�ǂ����𒲂ׂ�i�����j
	 * @param  p1		(( I )) ���ʏ��1�_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  n1		(( I )) ���ʂ̖@���x�N�g���i�P�ʃx�N�g���j�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			������ɂȂ�:false,  ������:true
	 */
/*
	public final boolean
	OnPlane( lvVecCalc p1, lvVecCalc n1 ) throws lvThrowable
	{
		return	OnPlane( p1, n1, lvEps.l1 );
	}
*/

	/**
	 * ���g�̓_���܂߂�3�_�����꒼����ɂ��邩�ǂ����𒲂ׂ�i�񐄏��j
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			���꒼����ɂȂ�:false,  ���꒼����:true
	 */
	public final boolean
	IsLine( lvVecCalc p1, lvVecCalc p2, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsLine(0)" );

		lvDouble  a0 = Gbl().tdIsLine[0];		// a0 = new lvDouble();
		lvVector  p0 = Gbl().tvIsLine[0];		// p0 = new lvVector();
		p0.Assign( this );
		p0.NormalAssign( p1, p2, a0, eps );
		return	( a0.val == 0.0 ) ? true : false;
	}
	/**
	 * ���g�̓_���܂߂�3�_�����꒼����ɂ��邩�ǂ����𒲂ׂ�i�����j
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			���꒼����ɂȂ�:false,  ���꒼����:true
	 */
	public final boolean
	IsLine( lvVecCalc p1, lvVecCalc p2 ) throws lvThrowable
	{
		return	IsLine( p1, p2, lvEps.l1 );
	}
	
	/**
	 * ���g�̓_���܂߂�4�_�����ꕽ�ʏ�ɂ��邩�ǂ����𒲂ׂ�i�񐄏��j
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p3		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  eps		(( I )) ���e�덷
	 * @return			���ꕽ�ʏ�ɂȂ�:false,  ���ꕽ�ʏ�:true
	 */
	public final boolean
	IsPlane( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3, double eps ) throws lvThrowable
	{
		Err().Assert( ( eps >= 0.0 ), "lvVecCalc.IsPlane(0)" );

		lvDouble  a0 = Gbl().tdIsPlane[0];			// a0 = new lvDouble();
		lvVector  n0 = Gbl().tvIsPlane[0];			// n0 = new lvVector();
		n0.Assign( this );
		n0.NormalAssign( p1, p2, p3, a0, eps );
		if( a0.val == 0.0 )
			return	true;

		lvVector  c0 = Gbl().tvIsPlane[1];			// c0 = new lvVector();
		c0.Assign( ( Add( p1 ).Add( p2 ).Add( p3 ) ).Mul( 0.25 ) );
							// c0 = ( this + p1 + p2 + p3 ) * 0.25
		if( !OnPlane( c0, n0, eps ) || !p1.OnPlane( c0, n0, eps )
			|| !p2.OnPlane( c0, n0, eps ) || !p3.OnPlane( c0, n0, eps ) )
		{
			return	false;
		}
		return	true;
	}
	/**
	 * ���g�̓_���܂߂�4�_�����ꕽ�ʏ�ɂ��邩�ǂ����𒲂ׂ�i�����j
	 * @param  p1		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p2		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @param  p3		(( I )) �_�B�K�� lvVector�^�ϐ��A�܂��͐��l�֐����g������i lvVecCalc�^�͎g�p���Ȃ����� �j
	 * @return			���ꕽ�ʏ�ɂȂ�:false,  ���ꕽ�ʏ�:true
	 */
	public final boolean
	IsPlane( lvVecCalc p1, lvVecCalc p2, lvVecCalc p3 ) throws lvThrowable
	{
		return	IsPlane( p1, p2, p3, lvEps.l1 );
	}

}
