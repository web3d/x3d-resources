//
// Copyright (C) 1999-2001 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pParse.java
//

package jp.co.lattice.vProcessor.parse;

import	java.util.Hashtable;
import	java.io.*;
import  jp.co.lattice.vProcessor.com.*;
import	jp.co.lattice.vProcessor.node.*;
import  jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/29-)
 * @author	  renewed by Eishin Matsui (00/07/12-)
 */
public class x3pParse extends x3pRoot implements x3pKeyword {
	
	private   static final x3pElement  elmUnknown = new x3pElement( x3pElement.EL_UNKNOWN );
	protected static final x3pElement  elmEmpty   = new x3pElement( x3pElement.EL_EMPTY   );

	private static final Boolean  boolFalse = new Boolean( false );
	private static final Boolean  boolTrue  = new Boolean( true  );


	protected x3pLex	    lexer;						// 字句入力
	protected x3pElement[]  topElms;
	protected Hashtable     xmlIdTable;					// XML IDテーブル

	// Work
	private   int           id;

	/**
	 *	コンストラクタ
	 */
	public x3pParse( x3pGlobal dt, InputStream in ) throws lvThrowable
	{
		super( dt );

		lexer	   = new x3pLex( dt, in );
		xmlIdTable = new Hashtable( 150 );
	}

	/**
	 *	解析中の行番号を取得する
	 *	@return	行番号
	 */
	public final int
	GetLineNo()
	{
		int  ln = 0;
		
		if( lexer != null )
			ln = lexer.GetLineNo();
		return ln;
	}

	/**
	 *	パースを開始する
	 */
	public final void
	Parse() throws lvThrowable
	{
		GetToken();
		topElms = PsDeclarations();
	}
	
	public final x3pElement[]
	GetTopElms()
	{
		return topElms;
	}
	public Hashtable
	GetXmlIdTable()
	{
		return xmlIdTable;
	}

	/**
	 *	Lexerからトークンを取り出す
	 */
	protected final void
	GetToken() throws lvThrowable
	{
		id = lexer.GetToken();
	}

	protected final void
	SkipToken( int n ) throws lvThrowable
	{
		for( int i=0; i<n; i++ )
			id = lexer.GetToken();
	}


	//----------------------------------------------------------
	//		解析関数群
	//----------------------------------------------------------

	protected final x3pElement[]
	PsDeclarations() throws lvThrowable
	{
		x3pElement	  te;
		x3pElement[]  ate;
		x3pElement[]  scee = new x3pElement[ 32 ];
		int		      size = 32;
		int		      cnt  = 0;
		int		      i;

		while( true ) {
			te = PsDeclaration();
			if( te == null ) {	// 終了
				break;
			}
			else {	// 追加
				scee[ cnt ] = te;
				cnt++;
				if( cnt >= size ) {
					ate = new x3pElement[ size + 32 ];
					for( i=0; i<cnt; i++ )
					    ate[ i ] = scee[ i ];
					scee  = ate;
					size += 32;
				}
			}
		}
		
		// リサイズ
		ate = new x3pElement[ cnt ];
		for( i=0; i<cnt; i++ )
		    ate[ i ] = scee[ i ];
		scee = ate;
		
		return scee;
	}

	protected final x3pElement
	PsDeclaration() throws lvThrowable
	{
		x3pElement  te  = null;

		for(;;) {
			if( id == ID_SEP_TAG_LEFT ) {
				GetToken();
				if( lexer.IsElement( id ) == true ) {
					int  type_id = id;
					GetToken();
					te = PsElement( type_id );
					if( te == null )
						return null;
					else if( te.type == x3pElement.EL_UNKNOWN ) {
						boolean  eof = SkipBlockForWord( type_id );
						if( eof == true )
							return null;
					}
					else if( te.type != x3pElement.EL_EMPTY )
						break;
				}
				else if( id == ID_SEP_QUESTION ) {
					GetToken();
					boolean  eof = GetQuestionElm();
					if( eof == true )
						return null;
				}
				else if( id == ID_SEP_EXCLAMATION ) {
					GetToken();
					boolean  eof = SkipTag();
					if( eof == true )
						return null;
				}
				else if( id == ID_UNKNOWN ) {
					String  unknown = lexer.GetUnknown();
					GetToken();
					boolean  eof = SkipBlockForUnknown( unknown );
					if( eof == true )
						return null;
				}
				else
					AssertSyntaxError( false );
			}	
			else if( id == ID_EOF )
				return null;
			else
				GetToken();
		}

		return te;
	}

	protected final boolean
	GetQuestionElm() throws lvThrowable
	{
		for(;;) {
			if( id == ID_SEP_QUESTION ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_ENCODING ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				if( id == ID_TY_UTF8 || id == ID_TY_SJIS )
					lexer.SetEncodingId( id );
				else
					AssertSyntaxError( false );
				
				GetToken();					// "\""
			}
			else
				GetToken();
		}
		
		return false;
	}

	protected final x3pElement
	PsElement( int type_id ) throws lvThrowable
	{
		x3pElement  elm = null;
		
		switch( type_id ) {
		case ID_EL_X3D:
			elm = PsElmX3D();
			break;
		case ID_EL_SCENE:
			elm = PsElmScene();
			break;
		case ID_EL_TRANSFORM:
			elm = PsElmTransform();
			break;
		case ID_EL_XVLSHAPE:
			elm = PsElmXvlShape();
			break;
		case ID_EL_XVLFACES:
			elm = PsElmXvlFaces();
			break;
		case ID_EL_XVLEDGES:
			elm = PsElmXvlEdges();
			break;
		case ID_EL_XVLVERTICES:
			elm = PsElmXvlVertices();
			break;
		case ID_EL_XVLFACE:
			elm = PsElmXvlFace();
			break;
		case ID_EL_XVLEDGE:
			elm = PsElmXvlEdge();
			break;
		case ID_EL_XVLVERTEX:
			elm = PsElmXvlVertex();
			break;
		case ID_EL_MATERIAL:
			elm = PsElmMaterial();
			break;
		case ID_EL_IMAGETEXTURE:
			elm = PsElmImageTexture();
			break;
		case ID_EL_COORDINATE:
			elm = PsElmCoordinate();
			break;
		case ID_EL_TEXTURECOORDINATE:
			elm = PsElmTextureCoordinate();
			break;
		}

		return elm;
	}

	protected final x3pElement
	PsElmX3D() throws lvThrowable
	{
		x3pContent  elm = NewX3D();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_X3D, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmScene() throws lvThrowable
	{
		x3pContent  elm = NewScene();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_SCENE, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmTransform() throws lvThrowable
	{
		x3pTransform  elm = NewTransform();
		x3pUseElm     use = NewUseElm( x3pElement.EL_USE_TRANSFORM );
		
		boolean  useEnable = false;
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				if( useEnable == true )
					return use;
				else
					return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_TRANSLATION ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				PsSfVec3f( elm.translation );
				GetToken();					// "\""
			}
			else if( id == ID_AT_ROTATION ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				PsSfRotation( elm.rotation );
				GetToken();					// "\""
			}
			else if( id == ID_AT_SCALE ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				PsSfVec3f( elm.scale );
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				
				String  id = lexer.GetStringToken();
				xmlIdTable.put( id, elm );
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_USE ) {
				SkipToken( 2 );				// "=", "\""
				
				use.use   = lexer.GetStringToken();
				useEnable = true;
				
				GetToken();					// "\""
			}
			else
				GetToken();
		}
		
		x3pElement[]  elms = PsMfElement( ID_EL_TRANSFORM, elm.defContType );
		
		if( useEnable == true )
			return use;
		
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;

		return elm;
	}

	protected final x3pElement
	PsElmXvlShape() throws lvThrowable
	{
		x3pXvlShape  elm = NewXvlShape();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_TYPE ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				if( id == ID_TY_POLYGONMESH )
					elm.typeXvlShape = x3pXvlShape.TYPE_POLYGON_MESH;
				else if( id == ID_TY_LATTICEMESH )
					elm.typeXvlShape = x3pXvlShape.TYPE_LATTICE_MESH;
				else if( id == ID_TY_GREGORYMESH )
					elm.typeXvlShape = x3pXvlShape.TYPE_GREGORY_MESH;
				else
					AssertSyntaxError( false );
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				lexer.GetStringToken();
				GetToken();					// "\""
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_XVLSHAPE, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmXvlFaces() throws lvThrowable
	{
		x3pContent  elm = NewXvlFaces();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_XVLFACES, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmXvlEdges() throws lvThrowable
	{
		x3pContent  elm = NewXvlEdges();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_XVLEDGES, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmXvlVertices() throws lvThrowable
	{
		x3pContent  elm = NewXvlVertices();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elmEmpty;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_XVLVERTICES, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmXvlFace() throws lvThrowable
	{
		x3pXvlFace  elm = NewXvlFace();
		
		int    ixOrgLength = x3pElement.null_int;
		int[]  tx          = null;
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				
				PsElmXvlFaceTexCoordIndex( elm, ixOrgLength, tx );
				return elm;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_COORDINDEX ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				int[]  ix = PsMfIntForAtt();
				ixOrgLength = ix.length;
				if( ix.length < 3 )
					AssertSyntaxError( false );
				else if( ix[ ix.length-1 ] == -1 && ix.length < 4 )
					AssertSyntaxError( false );
				else if( ix[ ix.length-1 ] != -1 ) {
					int[]  ix2 = new int[ ix.length+1 ];
					for( int i=0; i<ix.length; i++ )
						 ix2[ i ] = ix[ i ];
					ix2[ ix.length ] = -1;
					ix = ix2;
				}
				elm.coordIndex = ix;
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_EMPTY ) {
				SkipToken( 2 );				// "=", "\""
				lexer.GetStringToken();				
				GetToken();					// "\""
				
				elm.empty = true;
			}
			else if( id == ID_AT_TEXCOORDINDEX ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				tx = PsMfIntForAtt();
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				lexer.GetStringToken();
				GetToken();					// "\""
			}
			else
				GetToken();
		}

		PsElmXvlFaceTexCoordIndex( elm, ixOrgLength, tx );

		x3pElement[]  elms = PsMfElement( ID_EL_XVLFACE, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	private final void
	PsElmXvlFaceTexCoordIndex( x3pXvlFace elm, int ixOrgLength, int[] tx ) throws lvThrowable
	{
		if( tx != null ) {
			AssertSyntaxError( tx.length == ixOrgLength );
			
			if( elm.coordIndex.length == ( ixOrgLength + 1 ) ) {
				int[]  tx2 = new int[ tx.length+1 ];
				for( int i=0; i<tx.length; i++ )
					tx2[ i ] = tx[ i ];
				tx2[ tx.length ] = -1;
				tx = tx2;
			}
			elm.texCoordIndex = tx;
		}
	}
	
	protected final x3pElement
	PsElmXvlEdge() throws lvThrowable
	{
		x3pXvlEdge  elm = NewXvlEdge();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elm;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_COORDINDEX ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				int[] its = PsMfIntForAtt();
				AssertSyntaxError( its.length == 2 );
				elm.coordIndex = its;
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_ROUNDINGWEIGHT ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.roundingWeight = PsSfFloat();
				GetToken();					// "\""
			}
			else if( id == ID_AT_VECTORSTARTEND ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				int[] vecs = PsMfIntForAtt();
				AssertSyntaxError( vecs.length == 2 );
				elm.vectorStartEnd = vecs;
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				lexer.GetStringToken();
				GetToken();					// "\""
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_XVLEDGE, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmXvlVertex() throws lvThrowable
	{
		x3pXvlVertex  elm = NewXvlVertex();
		
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				return elm;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_COORDINDEX ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.coordIndex = PsSfInt();
				GetToken();					// "\""
			}
			else if( id == ID_AT_ROUNDINGWEIGHT ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.roundingWeight = PsSfFloat();
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				lexer.GetStringToken();
				GetToken();					// "\""
			}
			else
				GetToken();
		}

		x3pElement[]  elms = PsMfElement( ID_EL_XVLVERTEX, elm.defContType );
		if( elms == null )
			return null;
		else if( elms.length == 0 )
			return elmEmpty;
		elm.content = elms;
		
		return elm;
	}
	
	protected final x3pElement
	PsElmMaterial() throws lvThrowable
	{
		x3pMaterial  elm = NewMaterial();
		x3pUseElm    use = NewUseElm( x3pElement.EL_USE_MATERIAL );
		
		boolean  useEnable = false;
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				break;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				AssertSyntaxError( false );
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_AMBIENTINTENSITY ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.ambientIntensity = PsSfFloat();
				GetToken();					// "\""
			}
			else if( id == ID_AT_DIFFUSECOLOR ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				PsSfColor( elm.diffuseColor );
				GetToken();					// "\""
			}
			else if( id == ID_AT_EMISSIVECOLOR ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				PsSfColor( elm.emissiveColor );
				GetToken();					// "\""
			}
			else if( id == ID_AT_SHININESS ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.shininess = PsSfFloat();
				GetToken();					// "\""
			}
			else if( id == ID_AT_SPECULARCOLOR ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				PsSfColor( elm.specularColor );
				GetToken();					// "\""
			}
			else if( id == ID_AT_TRANSPARENCY ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.transparency = PsSfFloat();
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				
				String  id = lexer.GetStringToken();
				xmlIdTable.put( id, elm );
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_USE ) {
				SkipToken( 2 );				// "=", "\""
				
				use.use   = lexer.GetStringToken();
				useEnable = true;
				
				GetToken();					// "\""
			}
			else
				GetToken();
		}
		
		if( useEnable == true )
			return use;
		
		return elm;
	}

	protected final x3pElement
	PsElmImageTexture() throws lvThrowable
	{
		x3pImageTexture  elm = NewImageTexture();
		x3pUseElm        use = NewUseElm( x3pElement.EL_USE_IMAGETEXTURE );
		
		boolean  useEnable = false;
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				break;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				AssertSyntaxError( false );
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else if( id == ID_AT_URL ) {
				SkipToken( 2 );				// "=", "\""
				elm.url = lexer.GetStringToken();
				GetToken();					// "\""
			}
			else if( id == ID_AT_REPEATS ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.repeatS = PsSfBool();
				GetToken();					// "\""
			}
			else if( id == ID_AT_REPEATT ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				elm.repeatT = PsSfBool();
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				
				String  id = lexer.GetStringToken();
				xmlIdTable.put( id, elm );
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_USE ) {
				SkipToken( 2 );				// "=", "\""
				
				use.use   = lexer.GetStringToken();
				useEnable = true;
				
				GetToken();					// "\""
			}
			else
				GetToken();
		}
		
		if( useEnable == true )
			return use;
		
		return elm;
	}

	protected final x3pElement
	PsElmCoordinate() throws lvThrowable
	{
		x3pCoordinate  elm = NewCoordinate();
		x3pUseElm      use = NewUseElm( x3pElement.EL_USE_COORDINATE );
		
		boolean  useEnable = false;
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				break;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				AssertSyntaxError( false );
			}
			else if( id == ID_AT_POINT ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				x3pFieldVec3f[]  vecs = PsMfVec3fForAtt();
				if( vecs == null )
					return null;
				else if( vecs.length == 0 )
					return elmEmpty;
				elm.data = vecs;
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				
				String  id = lexer.GetStringToken();
				xmlIdTable.put( id, elm );
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_USE ) {
				SkipToken( 2 );				// "=", "\""
				
				use.use   = lexer.GetStringToken();
				useEnable = true;
				
				GetToken();					// "\""
			}
			else
				GetToken();
		}
		
		if( useEnable == true )
			return use;

		return elm;
	}
	
	protected final x3pElement
	PsElmTextureCoordinate() throws lvThrowable
	{
		x3pTextureCoordinate  elm = NewTextureCoordinate();
		x3pUseElm             use = NewUseElm( x3pElement.EL_USE_TEXTURECOORDINATE );
		
		boolean  useEnable = false;
		for(;;) {
			if( id == ID_SEP_SLASH ) {
				GetToken();
				AssertSyntaxError( id == ID_SEP_TAG_RIGHT );
				break;
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				AssertSyntaxError( false );
			}
			else if( id == ID_AT_POINT ) {
				SkipToken( 3 );				// "=", "\"", "<etc.>"
				
				x3pFieldUV[]  uvs = PsMfUVForAtt();
				if( uvs == null )
					return null;
				else if( uvs.length == 0 )
					return elmEmpty;
				elm.data = uvs;
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_DEF ) {
				SkipToken( 2 );				// "=", "\""
				
				String  id = lexer.GetStringToken();
				xmlIdTable.put( id, elm );
				
				GetToken();					// "\""
			}
			else if( id == ID_AT_USE ) {
				SkipToken( 2 );				// "=", "\""
				
				use.use   = lexer.GetStringToken();
				useEnable = true;
				
				GetToken();					// "\""
			}
			else
				GetToken();
		}
		
		if( useEnable == true )
			return use;

		return elm;
	}
	

	protected x3pContent
	NewX3D()
	{
		int[]  defContType = {
			x3pElement.EL_SCENE
		};
		return new x3pContent( x3pElement.EL_X3D, defContType );
	}
	
	protected x3pContent
	NewScene()
	{
		int[]  defContType = {
			x3pElement.EL_TRANSFORM,
			x3pElement.EL_XVLSHAPE
		};
		return new x3pContent( x3pElement.EL_SCENE, defContType );
	}
	
	protected x3pTransform
	NewTransform()
	{
		return new x3pTransform();
	}

	protected x3pXvlShape
	NewXvlShape()
	{
		return new x3pXvlShape();
	}
	
	protected x3pContent
	NewXvlFaces()
	{
		int[]  defContType = {
			x3pElement.EL_COORDINATE,
			x3pElement.EL_TEXTURECOORDINATE,
			x3pElement.EL_MATERIAL,
			x3pElement.EL_IMAGETEXTURE,
			x3pElement.EL_XVLFACE
		};
		return new x3pContent( x3pElement.EL_XVLFACES, defContType );
	}
	
	protected x3pContent
	NewXvlEdges()
	{
		int[]  defContType = {
			x3pElement.EL_COORDINATE,
			x3pElement.EL_MATERIAL,
			x3pElement.EL_XVLEDGE
		};
		return new x3pContent( x3pElement.EL_XVLEDGES, defContType );
	}
	
	protected x3pContent
	NewXvlVertices()
	{
		int[]  defContType = {
			x3pElement.EL_COORDINATE,
			x3pElement.EL_MATERIAL,
			x3pElement.EL_XVLVERTEX
		};
		return new x3pContent( x3pElement.EL_XVLVERTICES, defContType );
	}
	
	protected x3pXvlFace
	NewXvlFace()
	{
		return new x3pXvlFace();
	}
	
	protected x3pXvlEdge
	NewXvlEdge()
	{
		return new x3pXvlEdge();
	}
	
	protected x3pXvlVertex
	NewXvlVertex()
	{
		return new x3pXvlVertex();
	}
	
	protected x3pMaterial
	NewMaterial()
	{
		return new x3pMaterial();
	}

	protected x3pImageTexture
	NewImageTexture()
	{
		return new x3pImageTexture();
	}

	protected x3pCoordinate
	NewCoordinate()
	{
		return new x3pCoordinate();
	}
	
	protected x3pTextureCoordinate
	NewTextureCoordinate()
	{
		return new x3pTextureCoordinate();
	}
	
	protected x3pUseElm
	NewUseElm( int type )
	{
		return new x3pUseElm( type );
	}
	
	
	//---------------------------------------------------------------
	//	フィールド
	//---------------------------------------------------------------
	
	protected final x3pElement[]
	PsMfElement( int parentId, int[] defContType ) throws lvThrowable
	{
		int  size = 32;
		int  cnt  = 0;
		int	 i;

		x3pElement[]  elms = new x3pElement[ size ];

		for(;;) {
			int  type_id = x3pElement.null_int;
			
			boolean  exit = false;
			for(;;) {
				if( id == ID_SEP_TAG_LEFT ) {
					GetToken();
					if( id == ID_SEP_SLASH ) {
						GetToken();
						if( id == parentId ) {		// 終了
							SkipTag();
							exit = true;
							break;
						}
					}
					else if( lexer.IsElement( id ) == true ) {
						if( ChkDefContType( id, defContType ) == true ) {
							type_id = id;
							GetToken();
							break;
						}
					}
				}	
				else if( id == ID_EOF )
					AssertSyntaxError( false );
			
				GetToken();
			}
			if( exit == true )
				break;

			x3pElement  elm = PsElement( type_id );
			AssertSyntaxError( elm.type != x3pElement.EL_UNKNOWN );

			elms[ cnt ] = elm;
			cnt++;
			if( cnt >= size ) {		// リサイズ
				x3pElement[]  te = new x3pElement[ size + 32 ];
				for( i=0; i<cnt; i++ )
				    te[ i ] = elms[ i ];
				elms  = te;
				size += 32;
			}
			elms[ cnt ] = null;
		}
		
		if( cnt == 0 ) {
			x3pElement[]  te2 = new x3pElement[ 0 ];
			return te2;
		}

		// サイズ補正
		x3pElement[]  te2 = new x3pElement[ cnt ];
		for( i=0; i<cnt; i++ )
		    te2[ i ] = elms[ i ];
		    
		return te2;
	}
	
	protected final x3pFieldVec3f[]
	PsMfVec3fForAtt() throws lvThrowable
	{
		int  size = 32;
		int  cnt  = 0;
		int	 i;

		x3pFieldVec3f[]  vecs = new x3pFieldVec3f[ size ];

		for(;;) {
			boolean  exit = false;
			for(;;) {
				if( id == ID_SEP_DOUBLEQUOTE ) {
					exit = true;
					break;
				}
				else if( id == ID_VALUE_FLOAT || id == ID_VALUE_INT )
					break;
				else if( id == ID_EOF )
					AssertSyntaxError( false );
			
				GetToken();
			}
			if( exit == true )
				break;

			vecs[ cnt ] = new x3pFieldVec3f();
			PsSfVec3f( vecs[ cnt ] );			// 最後の getToken() は ","
			cnt++;
			if( cnt >= size ) {					// リサイズ
				x3pFieldVec3f[]  tv = new x3pFieldVec3f[ size + 32 ];
				for( i=0; i<cnt; i++ )
				    tv[ i ] = vecs[ i ];
				vecs  = tv;
				size += 32;
			}
			vecs[ cnt ] = null;
			
			if( id == ID_SEP_COMMA )
				GetToken();						// "," の次
		}
		
		if( cnt == 0 ) {
			x3pFieldVec3f[]  tv2 = new x3pFieldVec3f[ 0 ];
			return tv2;
		}

		// サイズ補正
		x3pFieldVec3f[]  tv2 = new x3pFieldVec3f[ cnt ];
		for( i=0; i<cnt; i++ )
		    tv2[ i ] = vecs[ i ];

		return tv2;
	}

	protected final x3pFieldUV[]
	PsMfUVForAtt() throws lvThrowable
	{
		int  size = 32;
		int  cnt  = 0;
		int	 i;

		x3pFieldUV[]  uvs = new x3pFieldUV[ size ];

		for(;;) {
			boolean  exit = false;
			for(;;) {
				if( id == ID_SEP_DOUBLEQUOTE ) {
					exit = true;
					break;
				}
				else if( id == ID_VALUE_FLOAT || id == ID_VALUE_INT )
					break;
				else if( id == ID_EOF )
					AssertSyntaxError( false );
			
				GetToken();
			}
			if( exit == true )
				break;

			uvs[ cnt ] = new x3pFieldUV();
			PsSfUV( uvs[ cnt ] );			// 最後の getToken() は ","
			cnt++;
			if( cnt >= size ) {					// リサイズ
				x3pFieldUV[]  tuv = new x3pFieldUV[ size + 32 ];
				for( i=0; i<cnt; i++ )
				    tuv[ i ] = uvs[ i ];
				uvs  = tuv;
				size += 32;
			}
			uvs[ cnt ] = null;
			
			if( id == ID_SEP_COMMA )
				GetToken();						// "," の次
		}
		
		if( cnt == 0 ) {
			x3pFieldUV[]  tuv2 = new x3pFieldUV[ 0 ];
			return tuv2;
		}

		// サイズ補正
		x3pFieldUV[]  tuv2 = new x3pFieldUV[ cnt ];
		for( i=0; i<cnt; i++ )
		    tuv2[ i ] = uvs[ i ];

		return tuv2;
	}

	protected final int[]
	PsMfIntForAtt() throws lvThrowable
	{
		int  size = 32;
		int  cnt  = 0;
		int	 i;

		int[]  its = new int[ size ];

		for(;;) {
			boolean  exit = false;
			for(;;) {
				if( id == ID_SEP_DOUBLEQUOTE ) {
					exit = true;
					break;
				}
				else if( id == ID_VALUE_INT )
					break;
				else if( id == ID_EOF )
					AssertSyntaxError( false );
			
				GetToken();
			}
			if( exit == true )
				break;

			its[ cnt ] = lexer.GetIntValue();
			cnt++;
			if( cnt >= size ) {		// リサイズ
				int[]  ti = new int[ size + 32 ];
				for( i=0; i<cnt; i++ )
				    ti[ i ] = its[ i ];
				its   = ti;
				size += 32;
			}
			its[ cnt ] = 0;
			
			GetToken();
		}
		
		if( cnt == 0 ) {
			int[]  ti2 = new int[ 0 ];
			return ti2;
		}

		// サイズ補正
		int[]  ti2 = new int[ cnt ];
		for( i=0; i<cnt; i++ )
		    ti2[ i ] = its[ i ];

		return ti2;
	}
	
	protected final void
	PsSfVec3f( x3pFieldVec3f dt ) throws lvThrowable
	{
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.x = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.y = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.z = lexer.GetFloatValue();
		GetToken();
	}
	
	protected final void
	PsSfRotation( x3pFieldRotation dt ) throws lvThrowable
	{
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.x = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.y = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.z = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.r = lexer.GetFloatValue();
		GetToken();
	}
	
	protected final void
	PsSfUV( x3pFieldUV dt ) throws lvThrowable
	{
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.u = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.v = lexer.GetFloatValue();
		GetToken();
	}
	
	protected final boolean
	PsSfBool() throws lvThrowable
	{
		if( id == ID_TY_TRUE ) {
			GetToken();
			return true;
		}
		else if( id == ID_TY_FALSE ) {
			GetToken();
			return false;
		}
		else
			AssertSyntaxError( false );

		return false;
	}

	protected final int
	PsSfInt() throws lvThrowable
	{
		int  val;
		
		AssertSyntaxError( id == ID_VALUE_INT );
		val	= lexer.GetIntValue();
		GetToken();
		return val;
	}

	protected final double
	PsSfFloat() throws lvThrowable
	{
		double  val;
		
		AssertSyntaxError( id == ID_VALUE_INT || id == ID_VALUE_FLOAT );
		val	= lexer.GetFloatValue();
		GetToken();
		return val;
	}

	protected final void
	PsSfColor( x3pFieldColor dt ) throws lvThrowable
	{
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.r = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.g = lexer.GetFloatValue();
		GetToken();
		
		if( id == ID_VALUE_INT || id == ID_VALUE_FLOAT )
			dt.b = lexer.GetFloatValue();
		GetToken();
	}

	protected final char[]
	PsSfCharForContent( int parentId ) throws lvThrowable
	{
		int  size = 1024;
		int  cnt  = 0;
		int	 i, numSrc;

		byte[]  src = null;
		char[]  dst = new char[ size ];

		for(;;) {
			numSrc = lexer.ReadBase64Line();
			if( numSrc == 0 )
				break;
				
			src = lexer.GetBase64Line();
			if( ( cnt + numSrc ) > size ) {		// リサイズ
				size = cnt + numSrc + 1024;
				char[]  tdst = new char[ size ];
				for( i=0; i<cnt; i++ )
				    tdst[ i ] = dst[ i ];
				dst = tdst;
			}
			
			for( i=0; i<numSrc; i++ )
				dst[ cnt + i ] = ( char )src[ i ];
				
			cnt += numSrc;
		}
		AssertSyntaxError( cnt > 0 );

		boolean  exit = false;
		GetToken();
		if( id == ID_SEP_TAG_LEFT ) {
			GetToken();
			if( id == ID_SEP_SLASH ) {
				GetToken();
				if( id == parentId ) {			// 終了
					SkipTag();
					exit = true;
				}
			}
		}
		AssertSyntaxError( exit == true );
		
		// サイズ補正
		char[]  tdst = new char[ cnt ];
		for( i=0; i<cnt; i++ )
		    tdst[ i ] = dst[ i ];

		return tdst;
	}

	protected final boolean
	SkipTag() throws lvThrowable
	{
		for(;;) {
			if( id == ID_SEP_TAG_LEFT ) {
				GetToken();
				SkipTag();
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			else
				GetToken();
		}
		
		return false;
	}

	protected final boolean
	SkipBlockForWord( int type_id ) throws lvThrowable
	{
		Boolean  empty = null;
		boolean  eof   = SkipTagAndChkEmpty( empty );
		if( eof == true )
			return true;
		else if( empty.booleanValue() == true )
			return false;

		int cnt = 1;	//同nameタグの数のカウント

		for(;;) {
			if( id == ID_SEP_TAG_LEFT ) {
				GetToken();
				if( id == type_id ) {
					eof = SkipTagAndChkEmpty( empty );
					if( eof == true )
						return true;
					else if( empty.booleanValue() == false )
						cnt++;
				}
				else if( id == ID_SEP_SLASH ) {
					GetToken();
					if( id == type_id )
						cnt--;
				}
			}	
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			
			if( cnt == 0 ) {
				SkipTag();
				break;
			}
			
			GetToken();
		}
		
		return false;
	}

	protected final boolean
	SkipBlockForUnknown( String unknown ) throws lvThrowable
	{
		Boolean  empty = null;
		boolean  eof   = SkipTagAndChkEmpty( empty );
		if( eof == true )
			return true;
		else if( empty.booleanValue() == true )
			return false;

		int cnt = 1;	//同nameタグの数のカウント

		for(;;) {
			if( id == ID_SEP_TAG_LEFT ) {
				GetToken();
				String  word = lexer.GetUnknown();
				if( unknown.equalsIgnoreCase( word ) == true ) {
					eof = SkipTagAndChkEmpty( empty );
					if( eof == true )
						return true;
					else if( empty.booleanValue() == false )
						cnt++;
				}
				else if( id == ID_SEP_SLASH ) {
					GetToken();
					word = lexer.GetUnknown();
					if( unknown.equalsIgnoreCase( word ) == true )
						cnt--;
				}
			}	
			else if( id == ID_EOF )
				AssertSyntaxError( false );
			
			if( cnt == 0 ) {
				SkipTag();
				break;
			}
			
			GetToken();
		}
		
		return false;
	}

	protected final boolean
	SkipTagAndChkEmpty( Boolean empty ) throws lvThrowable
	{
		boolean  slash = false;
		
		for(;;) {
			if( id == ID_SEP_SLASH )
				slash = true;
			else if( id == ID_SEP_TAG_LEFT ) {
				GetToken();
				SkipTag();
			}
			else if( id == ID_SEP_TAG_RIGHT ) {
				GetToken();
				break;
			}
			else if( id == ID_EOF )
				return true;
			else
				slash = false;
			
			GetToken();
		}
		
		if( slash == false )
			empty = boolFalse;
		else
			empty = boolTrue;
		
		return false;
	}

	protected final boolean
	ChkDefContType( int keyword, int[] defContType )
	{
		if( defContType == null || defContType.length == 0 )
			return false;
		
		int  elmId = GetElementType( keyword );
		if( elmId == x3pElement.EL_UNKNOWN )
			return false;
		
		for( int i=0; i<defContType.length; i++ ) {
			if( defContType[ i ] == elmId )
				return true;
		}
		
		return false;
	}

	protected final int
	GetElementType( int keyword )
	{
		switch( keyword ) {
		case ID_EL_X3D:					return x3pElement.EL_X3D;
		case ID_EL_SCENE:				return x3pElement.EL_SCENE;
		case ID_EL_TRANSFORM:			return x3pElement.EL_TRANSFORM;
		case ID_EL_XVLSHAPE:			return x3pElement.EL_XVLSHAPE;
		
		case ID_EL_XVLFACES:			return x3pElement.EL_XVLFACES;
		case ID_EL_XVLEDGES:			return x3pElement.EL_XVLEDGES;
		case ID_EL_XVLVERTICES:			return x3pElement.EL_XVLVERTICES;
		case ID_EL_XVLFACE:				return x3pElement.EL_XVLFACE;
		
		case ID_EL_XVLEDGE:				return x3pElement.EL_XVLEDGE;
		case ID_EL_XVLVERTEX:			return x3pElement.EL_XVLVERTEX;
		case ID_EL_MATERIAL:			return x3pElement.EL_MATERIAL;
		case ID_EL_IMAGETEXTURE:		return x3pElement.EL_IMAGETEXTURE;
		
		case ID_EL_COORDINATE:			return x3pElement.EL_COORDINATE;
		case ID_EL_TEXTURECOORDINATE:	return x3pElement.EL_TEXTURECOORDINATE;
		}
		
		return x3pElement.EL_UNKNOWN;
	}
	
	private final void
	AssertSyntaxError( boolean chk ) throws lvThrowable
	{
		lexer.AssertEx( chk, "Syntax Error!" );
	}
	
}
