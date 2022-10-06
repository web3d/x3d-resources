//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lvSparseMatrix.java
//

package jp.co.lattice.vKernel.texEx.a0x;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * Sparse Matrix 演算用クラス
 * @author	  created by Eishin Matsui (00/04/01-)
 * 
 */
public class lvSparseMatrix extends lvRoot {
	
	/**
	 * Sparse Matrix 要素の設定用内部クラス
	 */
	public static class SetElm {
	
		/** number of row		*/
		public int     row;
		
		/** number of column	*/
		public int     column;
		
		/** matrix element		*/
		public double  data;
	}
	
	/**
	 * Sparse Matrix 要素情報内部クラス
	 */
	public static class Elm {
		
		/** number of row/column	*/
		public int     index;
		
		/** matrix element		*/
		public double  data;
	}
	
// -------------------------------------------------------------------

	/** Sparse Matrix 要素の設定用配列	*/
	public SetElm  setElm[]						= null;
	
	/** number of matrix elements along row/column		*/
	public int     numMat						= 0;
	
	/** elements of sparse matrix		*/
	public Elm     nval[][]						= null;
	
	/** elements of transposed matrix	*/
	public Elm     tval[][]						= null;
	
// -------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public lvSparseMatrix( lvGlobal dt )
	{
		super( dt );
	}
	
// -------------------------------------------------------------------

	public final void
	Set( int numMat0, lvSparseMatrix.SetElm setElm0[] )
	{
		numMat = numMat0;
		setElm = setElm0;
	}
	
	public final void
	PreConjGrad()
	{
		int  nnum[] = new int[ numMat ];
		int  tnum[] = new int[ numMat ];

		nval = new Elm[ numMat ][];
		tval = new Elm[ numMat ][];
		for( int i=0; i<numMat; i++ ) {
			nval[ i ] = null;
			tval[ i ] = null;
		}		
		
		SetAryPreConjGrad( nnum, tnum );
		PreConjGradMain( nnum, tnum );
		CorrectPreConjGradRow( nnum );
		CorrectPreConjGradColumn( tnum );
	}
	
	private final void
	SetAryPreConjGrad( int nnum[], int tnum[] )
	{
		int  i;
		
		for( i=0; i<numMat; i++ ) {
			nnum[ i ] = 0;
			tnum[ i ] = 0;
		}
		
		for( i=0; i<setElm.length; i++ ) {
			int  row    = setElm[ i ].row;
			int  column = setElm[ i ].column;
			nnum[ row    ]++;
			tnum[ column ]++;
		}
		
		for( i=0; i<numMat; i++ ) {
			nval[ i ] = new Elm[ nnum[ i ] ];
			for( int j=0; j<nnum[ i ]; j++ )
				nval[ i ][ j ] = new Elm();
				
			tval[ i ] = new Elm[ tnum[ i ] ];
			for( int j=0; j<tnum[ i ]; j++ )
				tval[ i ][ j ] = new Elm();
		}
	}
	
	private final void
	PreConjGradMain( int nnum[], int tnum[] )
	{
		int      i, n;
		boolean  exist;
		
		for( i=0; i<numMat; i++ ) {
			nnum[ i ] = 0;
			tnum[ i ] = 0;
		}
		
		for( i=0; i<setElm.length; i++ ) {
			int  row    = setElm[ i ].row;
			int  column = setElm[ i ].column;
			int  nCnt   = nnum[ row    ];
			int  tCnt   = tnum[ column ];
			
			exist = false;
			n = nCnt;
			for( int j=0; j<nCnt; j++ ) {
				if( column == nval[ row ][ j ].index ) {
					exist = true;
					n = j;
					break;
				}
			}
			if( exist == false )
				nnum[ row ]++;
			nval[ row ][ n ].index = column;
			nval[ row ][ n ].data  = setElm[ i ].data;
			
			exist = false;
			n = tCnt;
			for( int j=0; j<tCnt; j++ ) {
				if( row == tval[ column ][ j ].index ) {
					exist = true;
					n = j;
					break;
				}
			}
			if( exist == false )
				tnum[ column ]++;
			tval[ column ][ n ].index = row;
			tval[ column ][ n ].data  = setElm[ i ].data;
		}
	}
	
	private final void
	CorrectPreConjGradRow( int nnum[] )
	{
		int  n;
		
		for( int i=0; i<numMat; i++ ) {
			if( nval[ i ] == null )
				n = 0;
			else
				n = nval[ i ].length;
				
			if( nnum[ i ] < n ) {
				Elm  tmp[] = new Elm[ nnum[ i ] ];
				for( int j=0; j<nnum[ i ]; j++ )
					tmp[ j ] = nval[ i ][ j ];
				nval[ i ] = tmp;
			}
		}
	}
	
	private final void
	CorrectPreConjGradColumn( int tnum[] )
	{
		int  n;
		
		for( int i=0; i<numMat; i++ ) {
			if( tval[ i ] == null )
				n = 0;
			else
				n = tval[ i ].length;
				
			if( tnum[ i ] < n ) {
				Elm  tmp[] = new Elm[ tnum[ i ] ];
				for( int j=0; j<tnum[ i ]; j++ )
					tmp[ j ] = tval[ i ][ j ];
				tval[ i ] = tmp;
			}
		}
	}
	
	public final double
	Get( int i0, int j0 )
	{
		int	 n = nval[ i0 ].length;
		int	 m = tval[ j0 ].length;
		if( n <= m ) {
			for( int j=0; j<n; j++ ) {
				if( nval[ i0 ][ j ].index == j0 )
					return nval[ i0 ][ j ].data;
			}
		}
		else {
			for( int i=0; i<m; i++ ) {
				if( tval[ j0 ][ i ].index == i0 )
					return tval[ j0 ][ i ].data;
			}
		}
		return 0.0;
	}

	public final void
	Mul( double xin[], double xout[], boolean tflag )
	{
		if( !tflag ) {
			int	 i, j, n;
			for( i=0; i<numMat; i++ ) {
				xout[ i ] = 0.0;
				n = nval[ i ].length;
				for( j=0; j<n; j++ ) {
					int	 k = nval[ i ][ j ].index;
					xout[ i ] += nval[ i ][ j ].data * xin[ k ];
				}
			}
		}
		else {
			int	 i, j, m;
			for( j=0; j<numMat; j++ ) {
				xout[ j ] = 0.0;
				m = tval[ j ].length;
				for( i=0; i<m; i++ ) {
					int	 k = tval[ j ][ i ].index;
					xout[ j ] += tval[ j ][ i ].data * xin[ k ];
				}
			}
		}
	}
/*
	public final void
	ConjGrad( double b[], double x[] ) throws lvThrowable
	{
		lvGauss  gauss = new lvGauss( global );
		
		double  tab[] = new double[ numMat * numMat ];
		double  ans[] = new double[ numMat * numMat ];
		
		for( int i=0; i<numMat; i++ ) {
			for( int j=0; j<numMat; j++ )
				tab[ i * numMat + j ] = Get( i, j );
		}
		
		boolean  result = gauss.Exec( tab, ans, numMat );
		Err().Assert( ( result == lvConst.LV_SUCCESS ), "lvSparseMatrix.ConjGrad(0)" );
		
		for( int i=0; i<numMat; i++ ) {
			x[ i ] = 0.0;
			for( int j=0; j<numMat; j++ )
				x[ i ] += ans[ i * numMat + j ] * b[ j ];
		}
	}
*/
	public final void
	ConjGrad( double b[], double x[] ) throws lvThrowable
	{
		int	    n = numMat;
		int	    j, iter, irst;
		double	aden, anum, dgg, eps2, gam, gg, rp, rsq;
		
		double  g[]  = new double[ n ];
		double  h[]  = new double[ n ];
		double  xi[] = new double[ n ];
		double  xj[] = new double[ n ];

		eps2 = n * Eps().a0 * Eps().a0;
		if( eps2 < Eps().e0 )
			eps2 = Eps().e0;
			
		for( irst=0; irst<3; irst++ ) {
			
			Mul( x, xi, false );
			rp = 0.0;
			for( j=0; j<n; j++ ) {
				xi[ j ] -= b[ j ];
				rp += xi[ j ] * xi[ j ];
			}
			if( rp <= eps2 )
				return;
				
			Mul( xi, g, true );
			for( j=0; j<n; j++ ) {
				h[j] = -g[j];
				g[j] = -g[j];
			}
			
			for( iter=0; iter<( 10 * n ); iter++ ) {
				Mul( h, xi, false );
				anum = 0.0;
				aden = 0.0;
				for( j=0; j<n; j++ ) {
					anum += g[ j ]  * h[ j ];
					aden += xi[ j ] * xi[ j ];
				}
				Err().Assert( ( aden >= 0.0 ), "lvSparseMatrix.ConjGrad(0)" );
				if( aden == 0.0 )
					Err().Assert( false, "lvSparseMatrix.ConjGrad(1)" );
					
				anum /= aden;
				for( j = 0; j < n; j++ ) {
					xi[ j ] = x[ j ];
					x[ j ] += anum * h[ j ];
				}
				
				Mul( x, xj, false );
				rsq = 0.0;
				for( j=0; j<n; j++ ) {
					xj[ j ] -= b[ j ];
					rsq += xj[ j ] * xj[ j ];
				}
				if( rsq == rp || rsq <= eps2 )
					return;
					
				if( rsq > rp ) {
					for( j=0; j<n; j++ )
						x[ j ] = xi[ j ];
					if( irst >= 2 )
						return;
					break;
				}
				rp = rsq;
				
				Mul( xj, xi, true );
				gg  = 0.0;
				dgg = 0.0;
				for( j=0; j<n; j++ ) {
					gg  += g[ j ] * g[ j ];
					dgg += ( xi[ j ] + g[ j ] ) * xi[ j ];
				}
				if( gg == 0.0 )
					return;
					
				gam = dgg / gg;
				for( j=0; j<n; j++ ) {
					g[ j ] = -xi[ j ];
					h[ j ] = g[ j ] + gam * h[ j ];
				}
			}
		}
		
		Err().Assert( false, "lvSparseMatrix.ConjGrad(2)" );
	}
	
}
