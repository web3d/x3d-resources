//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pElement.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pElement {
	
	public static final int     null_int    = -2147483648;
	public static final double  null_double = -1.0e-149;
	
	
	// 要素タイプ
	public static final int  EL_X3D         	  = 0x00;
	public static final int  EL_SCENE         	  = 0x01;
	public static final int  EL_TRANSFORM         = 0x02;
	public static final int  EL_XVLSHAPE          = 0x03;
	
	public static final int  EL_XVLFACES          = 0x04;
	public static final int  EL_XVLEDGES          = 0x05;
	public static final int  EL_XVLVERTICES       = 0x06;
	public static final int  EL_XVLFACE           = 0x07;
	
	public static final int  EL_XVLEDGE 		  = 0x08;
	public static final int  EL_XVLVERTEX         = 0x09;
	public static final int  EL_MATERIAL		  = 0x0a;
	public static final int  EL_IMAGETEXTURE	  = 0x0b;
	
	public static final int  EL_COORDINATE        = 0x0c;	
	public static final int  EL_TEXTURECOORDINATE = 0x0d;


	public static final int  EL_UNKNOWN			  = 0x40;
	public static final int  EL_EMPTY			  = 0x41;

	public static final int  EL_ELEMENT           = 0x80;
	public static final int  EL_CONTENT           = 0x81;


	public static final int  USE_FLAG			  = 0x100;
	
	public static final int  EL_USE_ELM			      = USE_FLAG | EL_ELEMENT;
	public static final int  EL_USE_TRANSFORM	      = USE_FLAG | EL_TRANSFORM;
	public static final int  EL_USE_MATERIAL	      = USE_FLAG | EL_MATERIAL;
	public static final int  EL_USE_IMAGETEXTURE      = USE_FLAG | EL_IMAGETEXTURE;
	public static final int  EL_USE_COORDINATE        = USE_FLAG | EL_COORDINATE;
	public static final int  EL_USE_TEXTURECOORDINATE = USE_FLAG | EL_TEXTURECOORDINATE;
	

	public int    type;						// 要素タイプ
	public int[]  defContType = null;

	public x3pElement()
	{
		type = EL_ELEMENT;
	}
	
	public x3pElement( int n )
	{
		type = n;
	}
	
}
