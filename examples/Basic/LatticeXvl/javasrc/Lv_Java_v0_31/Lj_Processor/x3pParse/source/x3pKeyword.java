//
// Copyright (C) 1999-2001 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pKeyword.java
//

package jp.co.lattice.vProcessor.parse;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/29-)
 * @author	  renewed by Eishin Matsui (00/07/12-)
 */
public interface x3pKeyword {
	
	// General
	public static final int  ID_GEN_NUMBER      = 0x00b;				// グループの数
	public static final int  ID_GEN             = 0x100;

	public static final int  ID_VALUE_INT       = ID_GEN | 0x00;
	public static final int  ID_VALUE_FLOAT     = ID_GEN | 0x01;

	public static final int  ID_SEP_TAG_LEFT    = ID_GEN | 0x03;
	public static final int  ID_SEP_TAG_RIGHT   = ID_GEN | 0x04;
	public static final int  ID_SEP_COMMA       = ID_GEN | 0x05;
	public static final int  ID_SEP_EXCLAMATION = ID_GEN | 0x06;
	public static final int  ID_SEP_QUESTION    = ID_GEN | 0x07;
	public static final int  ID_SEP_SLASH       = ID_GEN | 0x08;
	public static final int  ID_SEP_DOUBLEQUOTE = ID_GEN | 0x09;
	public static final int  ID_SEP_ASSIGN		= ID_GEN | 0x0a;

	public static final int  ID_EOF             = 0;
	public static final int  ID_UNKNOWN         = ID_GEN | 0x80;


	// ElementName
	public static final int  ID_EL_NUMBER            = 0x00e;			// グループの数
	public static final int  ID_EL                   = 0x200;

	public static final int  ID_EL_X3D         		 = ID_EL | 0x00;
	public static final int  ID_EL_SCENE      		 = ID_EL | 0x01;
	public static final int  ID_EL_TRANSFORM      	 = ID_EL | 0x02;
	public static final int  ID_EL_XVLSHAPE     	 = ID_EL | 0x03;
	
	public static final int  ID_EL_XVLFACES          = ID_EL | 0x04;
	public static final int  ID_EL_XVLEDGES          = ID_EL | 0x05;
	public static final int  ID_EL_XVLVERTICES       = ID_EL | 0x06;
	public static final int  ID_EL_XVLFACE           = ID_EL | 0x07;
	
	public static final int  ID_EL_XVLEDGE 			 = ID_EL | 0x08;
	public static final int  ID_EL_XVLVERTEX         = ID_EL | 0x09;
	public static final int  ID_EL_MATERIAL			 = ID_EL | 0x0a;
	public static final int  ID_EL_IMAGETEXTURE		 = ID_EL | 0x0b;
	
	public static final int  ID_EL_COORDINATE        = ID_EL | 0x0c;
	public static final int  ID_EL_TEXTURECOORDINATE = ID_EL | 0x0d;
	
	// Token Str
	public static final String[]  ElmStr = {
		"X3D",				"Scene",			"Transform",		"XvlShape",
		"XvlFaces",			"XvlEdges",			"XvlVertices",		"XvlFace",
		"XvlEdge",			"XvlVertex",		"Material",			"ImageTexture",
		"Coordinate",		"TextureCoordinate"
	};


	// AttributeName
	public static final int  ID_AT_NUMBER			= 0x017;			// グループの数
	public static final int  ID_AT					= 0x300;

	public static final int  ID_AT_SCALE		    = ID_AT | 0x00;
	public static final int  ID_AT_TRANSLATION	    = ID_AT | 0x01;
	public static final int  ID_AT_ROTATION		    = ID_AT | 0x02;
	public static final int  ID_AT_DEF	    		= ID_AT | 0x03;
	
	public static final int  ID_AT_USE	    		= ID_AT | 0x04;
	public static final int  ID_AT_TYPE	    		= ID_AT | 0x05;
	public static final int  ID_AT_COORDINDEX		= ID_AT | 0x06;
	public static final int  ID_AT_EMPTY	    	= ID_AT | 0x07;
	
	public static final int  ID_AT_TEXCOORDINDEX    = ID_AT | 0x08;
	public static final int  ID_AT_VECTORSTARTEND	= ID_AT | 0x09;
	public static final int  ID_AT_ROUNDINGWEIGHT	= ID_AT | 0x0a;
	public static final int  ID_AT_TEMPORARYVERTEX  = ID_AT | 0x0b;
	
	public static final int  ID_AT_AMBIENTINTENSITY	= ID_AT | 0x0c;
	public static final int  ID_AT_DIFFUSECOLOR 	= ID_AT | 0x0d;
	public static final int  ID_AT_EMISSIVECOLOR	= ID_AT | 0x0e;
	public static final int  ID_AT_SHININESS	    = ID_AT | 0x0f;
	
	public static final int  ID_AT_SPECULARCOLOR	= ID_AT | 0x10;
	public static final int  ID_AT_TRANSPARENCY 	= ID_AT | 0x11;
	public static final int  ID_AT_URL		    	= ID_AT | 0x12;
	public static final int  ID_AT_REPEATS			= ID_AT | 0x13;
	
	public static final int  ID_AT_REPEATT			= ID_AT | 0x14;
	public static final int  ID_AT_ENCODING		    = ID_AT | 0x15;
	public static final int  ID_AT_POINT		    = ID_AT | 0x16;

	// Token Str
	public static final String[]  AttStr = {
		"scale",		    "translation",		"rotation",		    "DEF",
		"USE",		    	"type",				"coordIndex",	    "empty",
		"texCoordIndex",	"vectorStartEnd",	"roundingWeight", 	"temporaryVertex",
		"ambientIntensity",	"diffuseColor",		"emissiveColor",	"shininess",
		"specularColor",	"transparency",		"url",				"repeatS",
		"repeatT",			"encoding",			"point"
	};
	
	
	// TypeNo
	public static final int ID_TY_NUMBER	     =	0x009;				//グループの数
	public static final int ID_TY			     =	0x400;

	public static final int ID_TY_FALSE		     =	ID_TY | 0x00;
	public static final int ID_TY_TRUE		     =	ID_TY | 0x01;
	public static final int ID_TY_LINESEQUENCE	 =	ID_TY | 0x02;
	public static final int ID_TY_BEZIERSEQUENCE =	ID_TY | 0x03;
	
	public static final int ID_TY_POLYGONMESH	 =	ID_TY | 0x04;
	public static final int ID_TY_LATTICEMESH	 =	ID_TY | 0x05;
	public static final int ID_TY_GREGORYMESH	 =	ID_TY | 0x06;
	public static final int ID_TY_UTF8		     =	ID_TY | 0x07;
	
	public static final int ID_TY_SJIS		     =	ID_TY | 0x08;

	// Token Str
	public static final String[]  TypeStr = {
		"false",		"true",			"LINE_SEQUENCE", "BEZIER_SEQUENCE",
		"POLYGON_MESH",	"LATTICE_MESH",	"GREGORY_MESH",	 "UTF-8",
		"Shift_JIS"
	};

}
