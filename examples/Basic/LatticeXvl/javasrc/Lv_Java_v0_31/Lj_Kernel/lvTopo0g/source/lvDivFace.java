//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvDivFace.java
//

package jp.co.lattice.vKernel.greg.t0g;

import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.greg.c0g.*;


/**
 * Round情報の作成クラス（上位レイヤー）
 * @author	  created by Eishin Matsui (99/10/12-)
 * 
 */
public class lvDivFace extends lvRoot {
	
	/**
	 * コンストラクタ
	 * @param  dt		(( I )) グローバルデータ
	 */
	public  lvDivFace( lvGlobal dt )
	{
		super( dt );
	}

// -------------------------------------------------------------------

	/**
	 * 1個の面をテセレーションする
	 * @param  shellNo		(( I )) lvToKernel.SetNumShell( num ) とセットした場合、0 ～ ( num-1 )
	 * @param  gsNo			(( I )) 配列 lvToKernel.gsNumVtx[] の長さが n の場合、0 ～ ( n-1 )
	 * @return				下位レイヤー(geom0)から送られるデータ
	 */
	public lvDivFaceType.UpDivFace
	Exec( int shellNo, int gsNo ) throws lvThrowable
	{
		return null;
	}

}
