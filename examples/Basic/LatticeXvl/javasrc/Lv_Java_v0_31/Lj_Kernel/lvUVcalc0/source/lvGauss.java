//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvGauss.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * N x N Gauss-Jordan 演算用クラス
 * @author	  created by Eishin Matsui (00/04/06-)
 * 
 */
public class lvGauss extends lvRoot {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		private int     num;
		private double  tab0[]		= null;
		private double  tab1[]		= null;
		
		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( lvGlobal dt )
		{
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( lv0UVcalcGblElm )global.GUVcalc() ).gGauss;
	}

// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvGauss( lvGlobal dt )
	{
		super( dt );
	}
	
// -------------------------------------------------------------------

	public final boolean
	Exec( double tab0[], double tab1[], int num0, double eps )
	{
		Gbl().num  = num0;
		Gbl().tab0 = tab0;
		Gbl().tab1 = tab1;
		
		boolean  result = ExecMain( tab0, tab1, num0, eps );
		Finish();
		
		return result;
	}	
	
	private final boolean
	ExecMain( double tab0[], double tab1[], int num0, double eps )
	{
		double  dt, f;
		int	    i, j, k, p;

		Gbl().num  = num0;
		Gbl().tab0 = tab0;
		Gbl().tab1 = tab1;
		
		// Set default epsilon if "eps" is negative.
		if( eps < 0.0 )
			eps = lvEps.a0;

		for( i=0; i<num0; i++ ) {
			for( j=0; j<num0; j++ ) {
				if( i == j )
					dt = 1.0;
				else
					dt = 0.0;
				SetE( i, j, dt );
			}
		}

		// Apply Gauss-Jordan method.
		for( k=0; k<num0; k++ ) {
			// Apply partial pivoting method.
			p = k;
			f = M( k, k );
			for( i=( k+1 ); i<num0; i++ ) {
				if( Math.abs( M( i, k ) ) > Math.abs( f ) ) {
					p = i;
					f = M( i, k );
				}
			}
			if( p != k ) {
				for( i=0; i<num0; i++ ) {
					f = M( k, i );
					SetM( k, i, M( p, i ) );
					SetM( p, i, f );
					
					f = E( k, i );
					SetE( k, i, E( p, i ) );
					SetE( p, i, f );
				}
			}

			// Apply sweeping method.
			f = M( k, k );
			if( lvEps.IsZero( f, eps ) )
				return lvConst.LV_FAILURE;
				
			for( i=0; i<num0; i++ ) {
				dt = M( k, i ) / f;
				SetM( k, i, dt );
				
				dt = E( k, i ) / f;
				SetE( k, i, dt );
			}
			for( i=0; i<num0; i++ ) {
				if( i == k )
					continue;
				f = M( i, k );
				for( j=0; j<num0; j++ ) {
					dt  = M( i, j );
					dt -= f * M( k, j );
					SetM( i, j, dt );
					
					dt  = E( i, j );
					dt -= f * E( k, j );
					SetE( i, j, dt );
				}
			}
		}
		
		return lvConst.LV_SUCCESS;
	}

	private final double
	M( int i, int j )
	{
		return Gbl().tab0[ i * Gbl().num + j ];
	}
	
	private final void
	SetM( int i, int j, double data )
	{
		Gbl().tab0[ i * Gbl().num + j ] = data;
	}
	
	private final double
	E( int i, int j )
	{
		return Gbl().tab1[ i * Gbl().num + j ];
	}
	
	private final void
	SetE( int i, int j, double data )
	{
		Gbl().tab1[ i * Gbl().num + j ] = data;
	}
	
	public final boolean
	Exec( double tab0[], double tab1[], int num0 )
	{
		return Exec( tab0, tab1, num0, -1.0 );
	}
	
	private final void
	Finish()
	{
		Gbl().tab0 = null;
		Gbl().tab1 = null;
	}
	
}
