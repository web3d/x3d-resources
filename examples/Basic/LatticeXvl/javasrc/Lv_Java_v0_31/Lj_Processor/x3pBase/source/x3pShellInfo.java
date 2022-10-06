//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pShellInfo.java
//

package jp.co.lattice.vProcessor.base;

import	jp.co.lattice.vProcessor.com.*;

import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * 1個のシェルに関する「Java XVL3 Processor から上位階層に引き渡すデータ」のクラス
* @author	  created by Eishin Matsui (00/03/11-)
 */
public class x3pShellInfo {
	
	/**
	 * インスタンスに関する内部クラス										<br>
	 * fromKernel[]内の頂点位置、法線ベクトルは、そのままでは使用せず、		<br>
	 * この中の情報（例えば頂点位置マトリクス）と組み合わせて使用する
	*/
	public static class Instance {
		/**
		 * 頂点位置マトリクス										<br>
		 * m = posMat.m;（m[4][4]の配列）とおくと、					<br>
		 *		xd = x*m[0][0] + y*m[1][0] + z*m[2][0] + m[3][0]	<br>
		 *		yd = x*m[0][1] + y*m[1][1] + z*m[2][1] + m[3][1]	<br>
		 *		zd = x*m[0][2] + y*m[1][2] + z*m[2][2] + m[3][2]
		*/
		public x3pMatrix  posMat		= null;
		
		/**
		 * 法線ベクトルマトリクス								<br>
		 * m = noramlMat.m;（m[4][4]の配列）とおくと、			<br>
		 *		xd = x*m[0][0] + y*m[1][0] + z*m[2][0]			<br>
		 *		yd = x*m[0][1] + y*m[1][1] + z*m[2][1]			<br>
		 *		zd = x*m[0][2] + y*m[1][2] + z*m[2][2]			<br>
		 * normalMatは、										<br>
		 * 		posMatのm[3][i]（i=0～2)を0とし、				<br>
		 * 		逆転置行列(inverse transpose Matrix)に変換		<br>
		 *      したマトリクス									<br>
		 * である（変換後の長さは1.0とは限らない）
		 */
		public x3pMatrix  normalMat		= null;

		/** 表示／非表示  false: 非表示、true: 表示		*/
		public boolean    visibility	= true;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public Instance( x3pGlobal dt )
		{
			posMat    = new x3pMatrix( dt );
			normalMat = new x3pMatrix( dt );
		}
	}
	
    /** Facesに関する内部クラス	*/
	public static class Faces {
		/**
		 * 「Java Lattice Kernel から上位階層に引き渡すデータ」の配列		<br>
		 *  	--- 「Faces内のFace」の数だけ存在する
		 */
		public lvFromKernel  fromKernel[]	= null;
		
		/**
		 * Face情報の配列（配列長は、fromKernelと等しくなる）
		 */
		public Face          face[]			= null;
		
		/** マテリアルIndex */
		public int           materialIndex	= -1;
		
		/** テクスチャの存在の有無 無:false, 有:true --- 初期値：false */
		public boolean       enableTexture	= false;
		
		/** テクスチャIndex */
		public int           textureIndex	= -1;
	}
	
    /** Faceに関する内部クラス	*/
	public static class Face {
		/**
		 * テクスチャのUV座標値。テクスチャが存在しない時は、null			<br>
		 * （配列長は、lvFromKernel.vertex と等しくなる）--- 初期値 null
		 */
		public lvUVdt  uv[]		= null;
	}
	

	/**
	 * 「インスタンスに関する情報」の配列														<br>
	 *		インスタンスは1個とは限らず、複数存在するときは、その数だけ表示されることになる		<br>
	 *		--- 「トップノードから当シェルに至るルート」の数だけ存在する
	 */
	public Instance  instance[]	= null;
	
	/** Faces の配列 --- 「当シェル内のFaces」の数だけ存在する */
	public Faces     faces[]	= null;
	
}
