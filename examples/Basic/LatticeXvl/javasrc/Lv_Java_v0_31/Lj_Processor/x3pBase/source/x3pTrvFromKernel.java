//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pTrvFromKernel.java
//

package jp.co.lattice.vProcessor.base;

/*
	トラバース。
	シーンをトップからたどる	
*/

import	jp.co.lattice.vProcessor.node.*;
import	jp.co.lattice.vProcessor.parse.*;
import	jp.co.lattice.vProcessor.com.*;

//ラティスカーネル
import	jp.co.lattice.vKernel.core.b0.*;
import	jp.co.lattice.vKernel.core.c0.*;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pTrvFromKernel extends x3pTraverse {
	
	/**
	 * static変数代用のための内部クラス
	 */
	public static class Global {

		private x3pBinToImage  binToImage	= null;

		/**
		 * コンストラクタ
		 * @param  dt		(( I )) グローバルデータ
		 */
		public  Global( x3pGlobal dt )
		{
			binToImage = new x3pBinToImage( dt );
		}
	}

	/** 当クラス用のグローバルデータ		*/
	private final Global
	Gbl()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gTrvFromKernel;
	}

// ----------------------------------------------------------------------

	/** コンストラクタ				*/
	public x3pTrvFromKernel( x3pGlobal dt )
	{
		super( dt );
	}

	/** グローバルデータ			*/
	private final x3pToProcessor.Global
	ToProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gToProcessor;
	}

	private final x3pFromProcessor.Global
	FromProcessor()
	{
		return  ( ( x3pBaseGblElm )global.GBaseX3p() ).gFromProcessor;
	}

	private int      currentShlNo     = 0;
	private int      currentGsNo      = 0;
	private int      currentFacesNo   = 0;
	private int      commonFacesNo    = 0;
	private int      currentComGsNo   = 0;
	
	private int      materialIndex    =  0;
	private int      textureIndex     = -1;
	
	private boolean  existMaterialTexture = false;
	private boolean  localMode            = false;
	private int      materialIndex2       =  0;
	private int      textureIndex2        = -1;
	private int      currentLocalFacesNo  = 0;
	

	public final void
	FromKernel( x3pElement[] elm ) throws lvThrowable
	{
		Err().Assert( ( elm != null ), "x3pTrvFromKernel.fromKernel(0)" );
		    
		TraverseMFElement( elm, dmyTrvData );				// シーンツリーを追う
	}


	//---------------------------------------------------------------
	//		トラバース
	//---------------------------------------------------------------

	protected void
	TraverseXvlShape( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlShapeEx  elm = ( x3pXvlShapeEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvFromKernel.TraverseXvlShape(0)" );

		currentGsNo    = 0;
		currentFacesNo = 0;

		FromProcessor().shell[ currentShlNo ].faces = new x3pShellInfo.Faces[ elm.numGsFaces ];
		for( int i=0; i<elm.numGsFaces; i++ )
			FromProcessor().shell[ currentShlNo ].faces[ i ] = new x3pShellInfo.Faces();
		
		TraverseMFElement( elm.content, td );
		
		currentShlNo++;
	}

	protected void
	TraverseXvlFaces( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlFacesEx  elm = ( x3pXvlFacesEx )elm0; 
		Err().Assert( ( elm.content != null ), "x3pTrvFromKernel.TraverseXvlFaces(0)" );

		commonFacesNo = currentFacesNo + ( elm.numGsFaces - 1 );
		
		if( elm.numGsFaces > 0 ) {
			int  num = elm.numGsFaces;
			if( elm.numCommonGs > 0 )
				num--;
				
			for( int i=0; i<num; i++ ) {
				FromProcessor().shell[ currentShlNo ].faces[ currentFacesNo + i ].fromKernel = new lvFromKernel[ 1 ];
				
				FromProcessor().shell[ currentShlNo ].faces[ currentFacesNo + i ].face = new x3pShellInfo.Face[ 1 ];
				FromProcessor().shell[ currentShlNo ].faces[ currentFacesNo + i ].face[ 0 ] = new x3pShellInfo.Face();
			}
				
			if( elm.numCommonGs > 0 ) {
				FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].fromKernel = new lvFromKernel[ elm.numCommonGs ];
				
				FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].face = new x3pShellInfo.Face[ elm.numCommonGs ];
				for( int i=0; i<elm.numCommonGs; i++ )
					FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].face[ i ] = new x3pShellInfo.Face();
			}
			
			for( int i=0; i<elm.numGsFaces; i++ )
				FromProcessor().shell[ currentShlNo ].faces[ currentFacesNo + i ].enableTexture = false;
		}
		
		currentComGsNo      =  0;
		currentLocalFacesNo =  0;
		materialIndex       =  0;
		textureIndex        = -1;
		
		TraverseMFElement( elm.content, td );
		
		Err().Assert( ( currentComGsNo == elm.numCommonGs ), "x3pTrvFromKernel.TraverseFaces(1)" );
		if( elm.numGsFaces > 0 && elm.numCommonGs > 0 ) {
			FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].materialIndex = materialIndex;
		
			if( FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].enableTexture == true ) {
				Err().Assert( ( textureIndex >= 0 ), "x3pTrvFromKernel.traverseFaces(2)" );
				FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].textureIndex = textureIndex;
			}
		}
		
		currentFacesNo += elm.numGsFaces;
	}
	
	protected void
	TraverseXvlFace( x3pContent elm0, TrvData td ) throws lvThrowable
	{
		x3pXvlFace  elm = ( x3pXvlFace )elm0; 
		if( elm.empty == true )
			return;
		
		localMode = true;
			
		existMaterialTexture = false;
		materialIndex2       =  0;
		textureIndex2        = -1;
		
		TraverseMFElement( elm.content, td );
		
		lvFromKernel  fromKernel = new lvFromKernel( global );

		boolean  lvErr = fromKernel.GetData( currentShlNo, currentGsNo );
		Err().Assert( ( lvErr == lvConst.LV_SUCCESS ), lvError.Message( global ) );
		
		if( existMaterialTexture == false )
			TraverseXvlFaceCommon( fromKernel );
		else
			TraverseXvlFaceLocal( fromKernel );
		
		currentGsNo++;
		
		localMode = false;
	}

	private void
	TraverseXvlFaceCommon( lvFromKernel fromKernel )
	{
		FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].fromKernel[ currentComGsNo ] = fromKernel;
		
		if( fromKernel.uvSpace != null ) {
			FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].enableTexture = true;
			
			x3pShellInfo.Face  face = FromProcessor().shell[ currentShlNo ].faces[ commonFacesNo ].face[ currentComGsNo ];
			face.uv = fromKernel.uvSpace[ 0 ].uv;
		}
		
		currentComGsNo++;
	}

	private void
	TraverseXvlFaceLocal( lvFromKernel fromKernel ) throws lvThrowable
	{
		int  facesNo = currentFacesNo + currentLocalFacesNo;
		
		FromProcessor().shell[ currentShlNo ].faces[ facesNo ].fromKernel[ 0 ] = fromKernel;
		FromProcessor().shell[ currentShlNo ].faces[ facesNo ].materialIndex   = materialIndex2;
		
		if( fromKernel.uvSpace != null ) {
			FromProcessor().shell[ currentShlNo ].faces[ facesNo ].enableTexture = true;
			
			x3pShellInfo.Face  face = FromProcessor().shell[ currentShlNo ].faces[ facesNo ].face[ 0 ];
			face.uv = fromKernel.uvSpace[ 0 ].uv;
			
			Err().Assert( ( textureIndex2 >= 0 ), "x3pTrvFromKernel.TraverseFaceLocal(0)" );
			FromProcessor().shell[ currentShlNo ].faces[ facesNo ].textureIndex = textureIndex2;
		}

		currentLocalFacesNo++;
	}
	
	protected void
	TraverseMaterial( x3pElement elm0, TrvData td )
	{
		x3pMaterialEx  elm = ( x3pMaterialEx )elm0;
		
		TraverseMaterialMain( elm, FromProcessor().material[ elm.materialNo ] );
		
		if( localMode == false )
			materialIndex = elm.materialNo;
		else {
			materialIndex2       = elm.materialNo;
			existMaterialTexture = true;
		}
	}

	private void
	TraverseMaterialMain( x3pMaterial elm, x3pFromProcessor.Material material )
	{
		material.ambientIntensity = elm.ambientIntensity;
		material.diffuseColor.Set(  elm.diffuseColor.r, elm.diffuseColor.g, elm.diffuseColor.b );
		material.emissiveColor.Set( elm.emissiveColor.r, elm.emissiveColor.g, elm.emissiveColor.b );
		material.shininess	      = elm.shininess;
		material.specularColor.Set( elm.specularColor.r, elm.specularColor.g, elm.specularColor.b );
		material.transparency     = elm.transparency;
	}

	protected void
	TraverseUseMaterial( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pUseElm  use = ( x3pUseElm )elm0;
		Err().Assert( ( use.use != null ), "x3pTrvFromKernel.TraverseUseMaterial(0)" );
			
		x3pMaterialEx  elm = ( x3pMaterialEx )ToProcessor().xmlIdTable.get( use.use );

		if( localMode == false )
			materialIndex = elm.materialNo;
		else {
			materialIndex2       = elm.materialNo;
			existMaterialTexture = true;
		}
	}

	protected void
	TraverseImageTexture( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pImageTextureEx  elm = ( x3pImageTextureEx )elm0;
		
		TraverseImageTextureMain( elm, FromProcessor().texture[ elm.textureNo ] );
		
		if( localMode == false )
			textureIndex = elm.textureNo;
		else {
			textureIndex2        = elm.textureNo;
			existMaterialTexture = true;
		}
	}

	private void
	TraverseImageTextureMain( x3pImageTexture elm, x3pFromProcessor.Texture texture ) throws lvThrowable
	{
		if( elm.url != null )
			texture.url = new String( elm.url );
			
		texture.repeatS = elm.repeatS;
		texture.repeatT = elm.repeatT;
	}

	protected void
	TraverseUseImageTexture( x3pElement elm0, TrvData td ) throws lvThrowable
	{
		x3pUseElm  use = ( x3pUseElm )elm0;
		Err().Assert( ( use.use != null ), "x3pTrvFromKernel.TraverseUseImageTexture(0)" );
			
		x3pImageTextureEx  elm = ( x3pImageTextureEx )ToProcessor().xmlIdTable.get( use.use );

		if( localMode == false )
			textureIndex = elm.textureNo;
		else {
			textureIndex2        = elm.textureNo;
			existMaterialTexture = true;
		}
	}

}
