// ecmascript:  // uncomment this line if this code is copied into a CDATA section, embedded inside an X3D Script node

// creator      http://www.itl.nist.gov/div897/ctg/vrml/members.html
// reference    http://www.nist.gov/vrml.html
// reference    http://xsun.sdct.itl.nist.gov/~mkass/vts/html/vrml.html

// Description: Script support for FontStyle test scene driver.x3d
// Filename:    driver.js
// Identifier:  http://www.web3d.org/x3d/content/examples/ConformanceNist/Appearance/FontStyle/driver.js
// Created:     21 January 2001
// Reference:   driver.x3d
// License:     ../../license.html

// TODO         change createVrmlFromString to createX3DFromString when browsers support

function getFile (value)
{
//	myTrans.set_children=Browser.createX3DFromString  (
//  '#X3D V3.2 utf8' +
//  'PROFILE Immersive' +

	myTrans.set_children=Browser.createVrmlFromString (
    ' Shape { ' +
	'	appearance Appearance { ' +
	'			material Material{} ' +
	'   } ' +
	'	geometry DEF MYTEXT Text{ string ["Read" "some text" "today!"] ' +
	'	  fontStyle DEF MYFONT FontStyle { ' +
	'        topToBottom ' + topToBottom +
	'        leftToRight ' + leftToRight +
	'        horizontal  ' + horizontal +
	'        justify     [ "' + justifyMajor + '" ,' + '"' + justifyMinor + '" ] ' +
	'	  } ' +
	'	} ' +
	' } ' );
}
	
function leftToRightTrue (value,timestamp)
{
	leftToRight = 'true';
	leftToRightTrueMaterial.set_diffuseColor = green;
	leftToRightFalseMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
	
function leftToRightFalse (value,timestamp)
{
	leftToRight = 'false';
	leftToRightFalseMaterial.set_diffuseColor = green;
	leftToRightTrueMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
	
function horizontalTrue (value,timestamp)
{
	horizontal = 'true';
	horizontalTrueMaterial.set_diffuseColor = green;
	horizontalFalseMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
	
function horizontalFalse (value,timestamp)
{
	horizontal = 'false';
	horizontalFalseMaterial.set_diffuseColor = green;
	horizontalTrueMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
	
function justifyMajorBegin (value,timestamp)
{
	justifyMajor = 'BEGIN';
	beginMaterial.set_diffuseColor = green;
	firstMaterial.set_diffuseColor = white;
	middleMaterial.set_diffuseColor = white;
	endMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}

function justifyMajorFirst (value,timestamp)
{
	justifyMajor = 'FIRST';
	firstMaterial.set_diffuseColor = green;
	beginMaterial.set_diffuseColor = white;
	middleMaterial.set_diffuseColor = white;
	endMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
	
function justifyMajorMiddle (value,timestamp)
{
	justifyMajor = 'MIDDLE';
	middleMaterial.set_diffuseColor = green;
	beginMaterial.set_diffuseColor = white;
	firstMaterial.set_diffuseColor = white;
	endMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}

function justifyMajorEnd (value,timestamp)
{
	justifyMajor = 'END';
	endMaterial.set_diffuseColor = green;
	beginMaterial.set_diffuseColor = white;
	firstMaterial.set_diffuseColor = white;
	middleMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
	
function justifyMinorBegin (value,timestamp)
{
	justifyMinor = 'BEGIN';
	beginMaterialMinor.set_diffuseColor = green;
	firstMaterialMinor.set_diffuseColor = white;
	middleMaterialMinor.set_diffuseColor = white;
	endMaterialMinor.set_diffuseColor = white;
	getFile (value,timestamp);
}

function justifyMinorFirst (value,timestamp)
{
	justifyMinor = 'FIRST';
	firstMaterialMinor.set_diffuseColor = green;
	beginMaterialMinor.set_diffuseColor = white;
	middleMaterialMinor.set_diffuseColor = white;
	endMaterialMinor.set_diffuseColor = white;
	getFile (value,timestamp);
}

function justifyMinorMiddle (value,timestamp)
{
	justifyMinor = 'MIDDLE';
	middleMaterialMinor.set_diffuseColor = green;
	beginMaterialMinor.set_diffuseColor = white;
	firstMaterialMinor.set_diffuseColor = white;
	endMaterialMinor.set_diffuseColor = white;
	getFile (value,timestamp);
}

function justifyMinorEnd (value,timestamp)
{
	justifyMinor = 'END';
	endMaterialMinor.set_diffuseColor = green;
	beginMaterialMinor.set_diffuseColor = white;
	firstMaterialMinor.set_diffuseColor = white;
	middleMaterialMinor.set_diffuseColor = white;
	getFile (value,timestamp);
}

function topToBottomTrue (value,timestamp)
{
	topToBottom = 'true';
	topToBottomTrueMaterial.set_diffuseColor = green;
	topToBottomFalseMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}

function topToBottomFalse (value,timestamp)
{
	topToBottom = 'false';
	topToBottomFalseMaterial.set_diffuseColor = green;
	topToBottomTrueMaterial.set_diffuseColor = white;
	getFile (value,timestamp);
}
