//
// Copyright (C) 1998-1999 Lattice Technology, Inc. All rights reserved.
//

//
// lvGblCore.java
//

package jp.co.lattice.vKernel.core.b0;

import	jp.co.lattice.vKernel.core.c0.*;

import	jp.co.lattice.vKernel.core.b0.lvBaseGblElm;
import	jp.co.lattice.vKernel.greg.t0g.lv0TopoGGblElm;
import	jp.co.lattice.vKernel.greg.g0g.lv0GeomGGblElm;
import	jp.co.lattice.vKernel.tex.a0g.lv0AttGGblElm;
import	jp.co.lattice.vKernel.greg.c0g.lv0ComGGblElm;
import	jp.co.lattice.vKernel.core.t0.lvTopoGblElm;
import	jp.co.lattice.vKernel.core.g0.lvGeomGblElm;
import	jp.co.lattice.vKernel.tex.a0.lv0AttGblElm;
import	jp.co.lattice.vKernel.texEx.a0x.lv0UVcalcGblElm;
import	jp.co.lattice.vKernel.tex.c0a.lvComAGblElm;


/**
 * staticïœêîÇÃë„ópÉNÉâÉX
 * @author	  created by Eishin Matsui (00/05/09-)
 * 
 */
public class lvGblCore extends lvGblCoreNull {

	private lvGlobal  global	= null;

// -------------------------------------------------------------------

	public void
	Init( lvGlobal dt )
	{
		global = dt;
		
		gBase   = new lvBaseGblElm( global );
		gTopoG  = new lv0TopoGGblElm( global );
		gGeomG  = new lv0GeomGGblElm( global );
		gAttG   = new lv0AttGGblElm( global );
		gComG   = new lv0ComGGblElm( global );
		gTopo   = new lvTopoGblElm( global );
		gGeom   = new lvGeomGblElm( global );
		gAtt    = new lv0AttGblElm( global );
		gUVcalc = new lv0UVcalcGblElm( global );
		gComA   = new lvComAGblElm( global );
		gCom    = new lvComGblElm( global );
	}

// -------------------------------------------------------------------
	
	private lvGblElm  gBase		= null;
	private lvGblElm  gTopoG	= null;
	private lvGblElm  gGeomG	= null;
	private lvGblElm  gAttG		= null;
	private lvGblElm  gComG		= null;
	private lvGblElm  gTopo		= null;
	private lvGblElm  gGeom		= null;
	private lvGblElm  gAtt		= null;
	private lvGblElm  gUVcalc	= null;
	private lvGblElm  gComA		= null;
	private lvGblElm  gCom		= null;
	
	public lvGblElm
	GBase()
	{
		return gBase;
	}
	public lvGblElm
	GTopoG()
	{
		return gTopoG;
	}
	public lvGblElm
	GGeomG()
	{
		return gGeomG;
	}
	public lvGblElm
	GAttG()
	{
		return gAttG;
	}
	public lvGblElm
	GComG()
	{
		return gComG;
	}
	public lvGblElm
	GTopo()
	{
		return gTopo;
	}
	public lvGblElm
	GGeom()
	{
		return gGeom;
	}
	public lvGblElm
	GAtt()
	{
		return gAtt;
	}
	public lvGblElm
	GUVcalc()
	{
		return gUVcalc;
	}
	public lvGblElm
	GComA()
	{
		return gComA;
	}
	public lvGblElm
	GCom()
	{
		return gCom;
	}
	
}
