//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvGlobal.java
//

package jp.co.lattice.vKernel.core.c0;


/**
 * �O���[�o���ϐ��p�̃N���X�B																		<br>
 * ���f���i�V�F���̏W���j���� lvGlobal�f�[�^���ɕێ�����Ă���̂ŁAlvWorld.Init()�𕡐���		<br>
 * �s�����Ƃɂ��A���f���̍č쐬���s������A���f���𕡐��Ƃ��邱�Ƃ��ł���
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
