//
// Copyright (C) 1998-2000 Lattice Technology, Inc. All rights reserved.
//

//
// lv0GregoryUV.java		（実装版）
//

package jp.co.lattice.vKernel.tex.a0g;

import	jp.co.lattice.vKernel.core.c0.*;


/**
 * ???? クラス
 * @author	  created by Eishin Matsui (00/04/04-)
 * 
 */
public class lv0GregoryUV {
	
	/**
	 * 実行関数
	 */
	public static final void
	Exec( int vtxNo, double u, double v, lvTessellateUV.DownTessellateUV downUV, lvTessellateUV.UpTessellateUV  upUV )
	{
		double  leftU, leftV, rightU, rightV;
		
		if( downUV.uvSpace == null )
			return;
			
		for( int i=0; i<downUV.numUVspace; i++ ) {
			leftU  = ( ( 1.0 - v ) * downUV.uvSpace[ i ].uv[ 1 ].u ) + ( v * downUV.uvSpace[ i ].uv[ 0 ].u );
			leftV  = ( ( 1.0 - v ) * downUV.uvSpace[ i ].uv[ 1 ].v ) + ( v * downUV.uvSpace[ i ].uv[ 0 ].v );
			
			rightU = ( ( 1.0 - v ) * downUV.uvSpace[ i ].uv[ 2 ].u ) + ( v * downUV.uvSpace[ i ].uv[ 3 ].u );
			rightV = ( ( 1.0 - v ) * downUV.uvSpace[ i ].uv[ 2 ].v ) + ( v * downUV.uvSpace[ i ].uv[ 3 ].v );
			
			upUV.uvSpace[ i ].uv[ vtxNo ].u = ( ( 1.0 - u ) * leftU + u * rightU );
			upUV.uvSpace[ i ].uv[ vtxNo ].v = ( ( 1.0 - u ) * leftV + u * rightV );
		}
	}
	
}
