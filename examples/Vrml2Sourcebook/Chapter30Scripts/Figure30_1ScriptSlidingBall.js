// Title:       Figure30_1ScriptSlidingBall.js
// Description: Editable example ECMAscript (javascript) source file for use with X3D Script node
// Author:      Don Brutzman
// Identifier:  http://www.web3d.org/x3d/content/examples/Vrml2Sourcebook/Chapter30Scripts/Figure30_1ScriptSlidingBall.js
// Created:     28 September 2000
// Revised:     13 April 2017
// Reference:   http://www.web3d.org/x3d/content/X3dTooltips.html#Script
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-FDIS-19775-1.2-X3D-AbstractSpecification/Part01/components/scripting.html
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-19777-1-X3DLanguageBindings-ECMAScript/Part1/X3D_ECMAScript.html
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/newECMAscriptTest.x3d
// License:     http://www.web3d.org/x3d/content/examples/license.html

// (note that an external .js file does not start with the key phrase ecmascript:

// Move a shape in a straight path
function set_fraction( fraction ) {
	value_changed[0] = fraction;    // X component
	value_changed[1] = 0.0;         // Y component
	value_changed[2] = 0.0;         // Z component
}
