//
// Copyright (C) 1999-2000 Lattice Technology, Inc. All rights reserved.
//

//
// x3pMaterial.java
//

package jp.co.lattice.vProcessor.node;


/**
 * @author	  created by Eishin Matsui (00/06/29-)
 */
public class x3pMaterial extends x3pElement {
	
	public double         ambientIntensity = 0.2;
	public x3pFieldColor  diffuseColor     = new x3pFieldColor( 0.8, 0.8, 0.8 );
	public x3pFieldColor  emissiveColor    = new x3pFieldColor( 0.0, 0.0, 0.0 );
	public double         shininess        = 0.2;
	public x3pFieldColor  specularColor    = new x3pFieldColor( 0.0, 0.0, 0.0 );
	public double         transparency     = 0.0;

	public x3pMaterial()
	{
		type = EL_MATERIAL;
	}

}
