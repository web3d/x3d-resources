//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// x3pTraverse.java
//

package jp.co.lattice.vProcessor.node;

import  jp.co.lattice.vProcessor.com.*;


/*
	トラバース。
	シーンをトップからたどる	
	シーンフィルター、シーンレンダラーなどの
	親クラス

*/

import  jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTraverse extends x3pRoot {
	
	static public class TrvData {
		
	}
	protected static TrvData  dmyTrvData = new TrvData();


	/**
	 *	コンストラクタ
	 */
	public x3pTraverse( x3pGlobal dt )
	{
		super( dt );
	}

	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------
	/**
	 *	MFElement
	 *	@param	elm[]	
	 *	@param	tdata		
	 */
	protected final void
	TraverseMFElement( x3pElement[] elms, TrvData tdata ) throws lvThrowable
	{
		if( elms == null )
		    return;

		x3pElement  elm;
		int         size = elms.length;
	
		for( int i=0; i<size; i++ ) {
			elm = elms[ i ];
			if( elm != null )
			    TraverseElement( elm, tdata );
		}
	}

	/**
	 *	Node
	 *	@param	elm		Element
	 *	@param	tdata		
	 */
	protected final void
	TraverseElement( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
		if( elm == null )
		    return;
		    
		switch( elm.type ) {
		case x3pElement.EL_X3D:
			TraverseX3D( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_SCENE:
			TraverseScene( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_TRANSFORM:
			TraverseTransform( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLSHAPE:
			TraverseXvlShape( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLFACES:
			TraverseXvlFaces( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLEDGES:
			TraverseXvlEdges( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLVERTICES:
			TraverseXvlVertices( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLFACE:
			TraverseXvlFace( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLEDGE:
			TraverseXvlEdge( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_XVLVERTEX:
			TraverseXvlVertex( ( x3pContent )elm, tdata );
			break;
		case x3pElement.EL_MATERIAL:
			TraverseMaterial( elm, tdata );
			break;
		case x3pElement.EL_IMAGETEXTURE:
			TraverseImageTexture( elm, tdata );
			break;
		case x3pElement.EL_COORDINATE:
			TraverseCoordinate( elm, tdata );
			break;
		case x3pElement.EL_TEXTURECOORDINATE:
			TraverseTextureCoordinate( elm, tdata );
			break;
		
		case x3pElement.EL_USE_TRANSFORM:
			TraverseUseTransform( elm, tdata );
			break;
		case x3pElement.EL_USE_MATERIAL:
			TraverseUseMaterial( elm, tdata );
			break;
		case x3pElement.EL_USE_IMAGETEXTURE:
			TraverseUseImageTexture( elm, tdata );
			break;
		case x3pElement.EL_USE_COORDINATE:
			TraverseUseCoordinate( elm, tdata );
			break;
		case x3pElement.EL_USE_TEXTURECOORDINATE:
			TraverseUseTextureCoordinate( elm, tdata );
			break;
			
		default:
			Err().Assert( false, "x3pTraverse.TraverseElement(0)" );
		}
	}

	protected void
	TraverseX3D( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseScene( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseTransform( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseXvlShape( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseXvlFaces( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseXvlEdges( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseXvlVertices( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, true );
	}

	protected void
	TraverseXvlFace( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, false );
	}

	protected void
	TraverseXvlEdge( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, false );
	}

	protected void
	TraverseXvlVertex( x3pContent elm, TrvData tdata ) throws lvThrowable
	{
		TraverseGeoms( elm, tdata, false );
	}

	protected void
	TraverseMaterial( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseImageTexture( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseCoordinate( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseTextureCoordinate( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseUseTransform( x3pElement elm0, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseUseMaterial( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseUseImageTexture( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseUseCoordinate( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	protected void
	TraverseUseTextureCoordinate( x3pElement elm, TrvData tdata ) throws lvThrowable
	{
	}

	private final void
	TraverseGeoms( x3pContent elm, TrvData tdata, boolean needCont ) throws lvThrowable
	{
		if( elm.content == null ) {
			Err().Assert( ( needCont == false ), "x3pTraverse.TraverseGeoms(0)" );
			return;
		}

		TraverseMFElement( elm.content, tdata );
	}
	
}
