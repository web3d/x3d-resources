//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvGlobal.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * グローバル変数用のクラス。																		<br>
 * モデル（シェルの集合）情報は lvGlobalデータ内に保持されているので、lvWorld.Init()を複数回		<br>
 * 行うことにより、モデルの再作成を行ったり、モデルを複数個とすることもできる
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvGlobal {
	
	private lvGblCoreNull  gblCore = null;
	
	public lvGblElm  GBase()	{  return gblCore.GBase();    }
	public lvGblElm  GTopoG()	{  return gblCore.GTopoG();   }
	public lvGblElm  GGeomG()	{  return gblCore.GGeomG();   }
	public lvGblElm  GAttG()	{  return gblCore.GAttG();    }
	public lvGblElm  GComG()	{  return gblCore.GComG();    }
	public lvGblElm  GTopo()	{  return gblCore.GTopo();    }
	public lvGblElm  GGeom()	{  return gblCore.GGeom();    }
	public lvGblElm  GAtt()		{  return gblCore.GAtt();     }
	public lvGblElm  GUVcalc()	{  return gblCore.GUVcalc();  }
	public lvGblElm  GComA()	{  return gblCore.GComA();    }
	public lvGblElm  GCom()		{  return gblCore.GCom();     }
	
	public lvGlobal( lvGblCoreNull dt )
	{
		gblCore = dt;
		dt.Init( this );
	}
	
}
