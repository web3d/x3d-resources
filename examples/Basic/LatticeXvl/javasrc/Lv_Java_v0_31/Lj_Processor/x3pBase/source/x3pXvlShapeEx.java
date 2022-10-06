//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pXvlShapeEx.java
//

package jp.co.lattice.vProcessor.base;

// XVL3ノード
import	jp.co.lattice.vProcessor.node.*;

// ラティスカーネル
import	jp.co.lattice.vKernel.core.c0.*;
import	jp.co.lattice.vKernel.tex.c0a.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pXvlShapeEx extends x3pXvlShape {
	
	public x3pTmpDtTrv.ToKernelVtxGs  tmpVtxGs[]		= null;
	
	public lvToKernelType.Vertex      vertex[]			= null;
	public lvToKernelType.Edge        edge[]			= null;

	public int  gsNumVtx[]  = null;
	public int  gsVtxSeq[]  = null;

	public int  ngNumVtx[]  = null;
	public int  ngVtxSeq[]  = null;
	
	public int  numGsFaces;
	public int  shellNo;
	
	public lvToKernelUV               uvSpace			= null;
	public x3pTmpDtTrv.ToKernelVtxUV  tmpVtxUV[]		= null;
	public lvUVdt                     tmpUVcoord[]		= null;
	
}
