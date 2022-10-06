//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// x3pGlobal.java
//

package jp.co.lattice.vProcessor.com;

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * グローバル変数用のクラス。																						<br>
 * ノードツリーやモデル（シェルの集合）情報は x3pGlobalデータ内に保持されているので、x3pWorld.Init()を複数回		<br>
 * 行うことにより、ノードツリー＆モデルの再作成を行ったり、ノードツリー＆モデルを複数個とすることもできる。			<br>
 *																													<br>
 * lvGlobal を継承しているので、																					<br>
 *		lvError.Message( ??? ) の引数（???の部分）																	<br>
 * とすることが可能である。																							<br>
 *																													<br>
 * ただし現在のところ、 x3pGlobal を lvError.Message() 以外の Java Lattice Kernel のメソッド（コンストラクタ		<br>
 * 含む）の引数とすることは、保証しない。特に lvToKernelコンストラクタの引数とすることは禁じる
 *
 * @author	  created by Eishin Matsui (00/03/11-)
 */
public class x3pGlobal extends lvGlobal {
	
	private x3pGblCoreNull  gblCoreX3p = null;
	
	public lvGblElm  GBaseX3p()		{  return gblCoreX3p.GBaseX3p();   }
	public lvGblElm  GNodeX3p()		{  return gblCoreX3p.GNodeX3p();   }
	public lvGblElm  GParseX3p()	{  return gblCoreX3p.GParseX3p();  }
	public lvGblElm  GComX3p()		{  return gblCoreX3p.GComX3p();    }
	
	public x3pGlobal( lvGblCoreNull dt0, x3pGblCoreNull dt1 )
	{
		super( dt0 );
		
		gblCoreX3p = dt1;
		dt1.Init( this );
	}
	
}
