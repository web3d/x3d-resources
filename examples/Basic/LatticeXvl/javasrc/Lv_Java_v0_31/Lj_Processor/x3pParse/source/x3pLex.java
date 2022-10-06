//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pLex.java
//

/*
	初期化
		固定キーワード（ノード名、フィールド名などの予約語）の
		ハッシュテーブルへの登録

	字句解析
		１）トークンの切り出し
		２）キーワードのＩＤ化

	数値
		0x1234
		0.2345
		.12345
		+0.12345
		-0.12345
		+0.123E+45
		-0.123e-45

	予約語はKeyword Classで定義
*/

package jp.co.lattice.vProcessor.parse;

import	java.util.Hashtable;
import	java.io.*;
import  jp.co.lattice.vProcessor.com.*;
import  jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/29-)
 */
public class x3pLex extends x3pRoot implements x3pKeyword {

	class GetCharInfo {
		private	static final int  buffSize = 4096;	// バッファサイズ
	
		private int		buffPos = buffSize;			// バッファ内カレント位置
		private int		rsize   = 0;			    // バッファに読み込んだサイズ
		private byte[]  buff    = new byte[ buffSize ];

		private InputStream  inStream;      		// ストリーム入力
		
		private GetCharInfo( InputStream in )
		{
			inStream = in;
		}
	}
	
	class GetTokenInfo {
		private	int		buff_size = 256;
		private	byte[]  buff      = new byte[ buff_size ];
	}
	
	private GetCharInfo   getCharInfo;
	private GetTokenInfo  getStringToken = new GetTokenInfo();
	private GetTokenInfo  getWordToken   = new GetTokenInfo();
	private GetTokenInfo  getBase64Token = new GetTokenInfo();

	private	int		   lineNo   = 0;		// 現在読込み中の行
	private	char	   c        = '\0';		// 読み込んだ文字
	private byte       byteChar = 0;		// 読み込んだ文字(byteのまま。GetStringToken()用)

	private	double	   floatValue;			// カレントフロート値
	private	int		   intValue;			// カレントフロート値
	private	String	   string;				// カレント文字列

	private Hashtable  keyWordTable;
	private int        encodingId		= ID_TY_UTF8;	// 文字符号化形式	( ID_TY_UTF8 or ID_TY_SJIS )
	
	
	/**
	 *	コンストラクタ
	 */
	public x3pLex( x3pGlobal dt, InputStream in ) throws lvThrowable
	{
		super( dt );

		int		 i;
		Integer  id;

		getCharInfo = new GetCharInfo( in );

		// キーワードテーブルを作成
		keyWordTable = new Hashtable( 128 );

		// ElementNameキーワード登録
		for( i=0; i<ID_EL_NUMBER; i++ ) {
			id = new Integer( ( ID_EL | i ) );
			keyWordTable.put( ElmStr[ i ].toLowerCase(), id );
		}

		// AttributeNameキーワード登録
		for( i=0; i<ID_AT_NUMBER; i++ ) {
			id = new Integer( ( ID_AT | i ) );
			keyWordTable.put( AttStr[ i ].toLowerCase(), id );
		}

		// TypeNameキーワード登録
		for( i=0; i<ID_TY_NUMBER; i++ ) {
			id = new Integer( ( ID_TY | i ) );
			keyWordTable.put( TypeStr[ i ].toLowerCase(), id );
		}

		GetChar();	    // 最初の一文字
	}

	/**
	 *	トークン取り出し
	 *	@return	トークンＩＤ
	 */
	public final int
	GetToken() throws lvThrowable
	{
		int	 id;

		// ホワイトスペース( ' ', \n, \t ）
		SkipWhiteSpace();

		if( c == '\0' )
			return ID_EOF;
			
		else if( ( '0'<=c && c<='9' ) || c=='+' || c=='-' || c=='.' ) {    // 数字（値）
			id = GetValueToken();
			
			return id;
		}

		else if( c == '<' ) {                                           // セパレータ
			GetChar();
			return ID_SEP_TAG_LEFT;
		}
		else if( c == '>' ) {
			GetChar();
			return ID_SEP_TAG_RIGHT;
		}
		else if( c == ',' ) {
			GetChar();
			return ID_SEP_COMMA;
		}
		else if( c == '!' ) {
			GetChar();
			return ID_SEP_EXCLAMATION;
		}
		else if( c == '?' ) {
			GetChar();
			return ID_SEP_QUESTION;
		}
		else if( c == '/' ) {
			GetChar();
			return ID_SEP_SLASH;
		}
		else if( c == '\"' ) {
			GetChar();
			return ID_SEP_DOUBLEQUOTE;
		}
		else if( c == '=' ) {
			GetChar();
			return ID_SEP_ASSIGN;
		}
		
		else {
			// 識別子
			Integer  i;
			GetWordToken();

			i = ( Integer )( keyWordTable.get( string.toLowerCase() ) );
			if( i == null ) {
				GetChar();
				return ID_UNKNOWN;		// 予約語ではない
			}
			id = i.intValue();
			
			return id;
		}
	}

	/**
	 *	要素か？
	 */
	public final boolean
	IsElement( int id )
	{
		id &= 0xffffff00;
		if( id == ID_EL )
			return true;

		return false;
	}

	/**
	 *	フロート値を取得
	 */
	public final double
	GetFloatValue()
	{
		return floatValue;
	}

	/**
	 *	整数値を取得
	 */
	public final int
	GetIntValue()
	{
		return intValue;
	}
	
	/**
	 *	行番号を取得
	 */
	public final int
	GetLineNo()
	{
		return lineNo;
	}

	/**
	 *	未定義ワードを取得
	 */
	public final String
	GetUnknown()
	{
		String sc = new String( string );
		return sc;
	}

	/**
	 * 文字符号化形式をセット
	 */
	public final void
	SetEncodingId( int encodingId0 )
	{
		encodingId = encodingId0;
	}

	/**
	 *	文字列 取得
	 *		"(文字)*"
	 */
	public final String
	GetStringToken() throws lvThrowable
	{
		int  pos = 0;

		while( c != '\0' ) {
			if( c == '\"' ) {
				if( pos == 0 || getStringToken.buff[ pos-1 ] != '\\' )
					break;
			}
			
			getStringToken.buff[ pos ] = byteChar;
			pos++;

			if( pos >= getStringToken.buff_size ) {
				byte  b2[] = new byte[ getStringToken.buff_size + 256 ];
				for( int i=0; i<getStringToken.buff_size; i++ )
					b2[ i ] = getStringToken.buff[ i ];
				getStringToken.buff_size += 256;
			}
			GetChar();
		}

		AssertEx( ( c != '\0' ), "Not close string" );

		String  sc = null;
	    try {
	    	switch( encodingId ) {
	    	case ID_TY_UTF8:
				sc = new String( getStringToken.buff, 0, pos );
				break;
	    	case ID_TY_SJIS:
				sc = new String( getStringToken.buff, 0, pos, "SJIS" );
				break;
			default:
				Err().Assert( false, "x3pLex.GetStringToken(0)" );
			}
		}
	    catch( IOException e ) {
	        Err().Assert( false, "x3pLex.GetStringToken(1)" );
		}
		
		GetToken();
		
		return  sc;
	}

	/**
	 *	文字列 取得
	 *		"(文字)*"
	 */
	public final int
	ReadBase64Line() throws lvThrowable
	{
		// ホワイトスペース( ' ', \n, \t ）
		SkipWhiteSpace();

		if( c == '\0' || c == '<' )
			return 0;
			
		int  pos = 0;

		while( ( 'a' <= c && c <= 'z' ) || ( 'A' <= c && c <= 'Z' ) || ( '0' <= c && c <= '9' ) ||
			   c == '+' || c == '/' || c == '=' )
		{
			getBase64Token.buff[ pos ] = byteChar;
			pos++;

			if( pos >= getBase64Token.buff_size ) {
				byte  b2[] = new byte[ getBase64Token.buff_size + 256 ];
				for( int i=0; i<getBase64Token.buff_size; i++ )
					b2[ i ] = getBase64Token.buff[ i ];
				getBase64Token.buff_size += 256;
			}

			GetChar();
		}
		
		return pos;
	}
	
	public final byte[]
	GetBase64Line()
	{
		return getBase64Token.buff;
	}

	public final void
	AssertEx( boolean chk, String msg ) throws lvThrowable
	{
		if( chk == false ) {
			String  strLineNo = String.valueOf( lineNo );
			Err().Assert( chk, strLineNo + "  " + msg );
		}
	}

	//-----------------------------------------------------
	//	private method
	//-----------------------------------------------------
	
	/**
	 *	１文字読込み
	 */
	private final void
	GetChar() throws lvThrowable
	{
		if( getCharInfo.rsize == -1 ) {
			c = '\0';
			return;
		}

		if( getCharInfo.buffPos >= getCharInfo.rsize ) {
			try {
            	getCharInfo.rsize = getCharInfo.inStream.read( getCharInfo.buff );
	        }
	        catch( IOException e ) {
	        	Err().Assert( false, "x3pLex.GetChar(0)" );
	        }

			getCharInfo.buffPos = 0;
			if( getCharInfo.rsize == -1 ) {
	            try {
	            	getCharInfo.inStream.close();
	            }
	            catch( IOException e ) {
	        		Err().Assert( false, "x3pLex.GetChar(1)" );
				}
	            
				c = '\0';
				return;
			}
		}

		byteChar = getCharInfo.buff[ getCharInfo.buffPos ];
		c = ( char )byteChar;
		getCharInfo.buffPos++;

		if( c == '\n' )
			lineNo++;
	}

	/**
	 *	ホワイトスペースをスキップ
	 */
	private final void
	SkipWhiteSpace() throws lvThrowable
	{
		for(;;) {
			if( c == ' ' || c == '\t' || c == '\n' || c == '\r' )
				GetChar();
			else
				return;
		}
	}

	/**
	 *	数値トークン取り出し
	 */
	private final int
	GetValueToken() throws lvThrowable
	{
		double  negative = 1.0;		// 符号
		int     val		 = 0;		// 整数部

		// 符号
		if( c == '+' ) {
			negative = 1.0;
			GetChar();
		}
		else if( c == '-' ) {
			negative = -1.0;
			GetChar();
		}

		// HEX
		if( c == '0' ) {
			// '0'は読み捨てても問題ない
			GetChar();
			if( c == 'x' || c == 'X' ) {
				// Hex Int;
				intValue  = GetHexToken();
				intValue *= negative;
				return ID_VALUE_INT;
			}
		}

		// 整数部
		if( '0' <= c && c <= '9' ) {
			// 整数部を取得!
			val = GetIntToken();
		}

		//小数部
		if( c == '.' ) {
			// Float値
			floatValue = GetDecimalToken( val );
			// 指数
			if( c == 'e' || c == 'E' )
				floatValue = GetExpToken( floatValue );
			floatValue *= negative;
			return ID_VALUE_FLOAT;
		}
		else if( c == 'e' || c == 'E' ) {		// 指数
			floatValue = GetExpToken( val );
			floatValue *= negative;
			return ID_VALUE_FLOAT;
		}
		else {
			intValue  = val;
			intValue *= ( int )negative;

			floatValue = intValue;
			return ID_VALUE_INT;
		}
		
	}

	/**
	 *	16進トークン
	 *	in
	 *		xNNNN
	 *		x0123aA
	 */
	private final int
	GetHexToken() throws lvThrowable
	{
		int  val = 0;

		GetChar();			// 'x'をパス

		while( true ) {
			if( '0' <= c && c <= '9' ) {
				val *= 0x10;
				val += ( int )( c - '0' );
			}
			else if( 'A' <= c && c <= 'F' ) {
				val *= 0x10;
				val += ( int )( c - 'A' + 10 );
			}
			else if( 'a' <= c && c <= 'f' ) {
				val *= 0x10;
				val += ( int )( c - 'A' + 10 );
			}
			else
				break;
				
			GetChar();
		}
		return val;
	}

	/**
	 *	整数部
	 *	in
	 *		0123
	 */
	private final int
	GetIntToken() throws lvThrowable
	{
		int  val = 0;

		while( true ) {
			if( '0' <= c && c <= '9' ) {
				val *= 10;
				val += ( int )( c - '0' );
			}
			else
				break;
				
			GetChar();
		}
		return val;
	}

	/**
	 *	小数部
	 *	in
	 *		.0123
	 */
	private final double
	GetDecimalToken( int iv ) throws lvThrowable
	{
		double  val = 0.0;
		double  mul = 0.1;

		GetChar();		// '.'をパス
		
		while( true ) {
			if( '0' <= c && c <= '9' ) {
				val += ( double )( c - '0' ) * mul;
				mul *= 0.1;
			}
			else
				break;
				
			GetChar();
		}
		val += iv;

		return val;
	}

	/**
	 *	指数部
	 *	in
	 *		e+023
	 */
	private final double
	GetExpToken( double iv ) throws lvThrowable
	{
		double  val = iv;
		int	    v   = 0;

		GetChar();
		if( c == '+' ) {
			GetChar();
			v = GetIntToken();
			for( int i=0; i<v; i++ )
				val *= 10;
		}
		else if( c == '-' ) {
			GetChar();
			v = GetIntToken();
			for( int i=0; i<v; i++ )
				val *= 0.1;
		}

		return val;
	}

	/**
	 *	文字トークン取得
	 */
	private final boolean
	GetWordToken() throws lvThrowable
	{
		int  pos = 0;

		// 先頭文字
		if( ( 'a' <= c && c <= 'z' ) || ( 'A' <= c && c <= 'Z' ) || c == '_' ) {
			getWordToken.buff[ pos ] = byteChar;
			pos++;
			GetChar();
		}
		else
			return false;

		while( ( 'a' <= c && c <= 'z' ) || ( 'A' <= c && c <= 'Z' ) || ( '0' <= c && c <= '9' ) ||
			   c == '_' || c == '-' || c == ':' )
		{
			getWordToken.buff[ pos ] = byteChar;
			pos++;

			if( pos >= getWordToken.buff_size ) {
				byte  b2[] = new byte[ getWordToken.buff_size + 256 ];
				for( int i=0; i<getWordToken.buff_size; i++ )
					b2[ i ] = getWordToken.buff[ i ];
				getWordToken.buff_size += 256;
			}

			GetChar();
		}
		
		string = new String( getWordToken.buff, 0, pos );
		
		return true;
	}
	
}
