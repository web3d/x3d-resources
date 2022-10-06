// Description: corresponding external ECMAscript (javascript) source file for specification example
// Filename:     ECMAScriptSaiTouchSensorIsOverEventScript.js
// Identifier:  http://www.web3d.org/x3d/content/examples/newECMAscript.js
// Created:     16 January 2018
// Revised:     16 January 2018
// Reference:   http://www.web3d.org/x3d/content/X3dTooltips.html#Script
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-FDIS-19775-1.2-X3D-AbstractSpecification/Part01/components/scripting.html
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-19777-1-X3DLanguageBindings-ECMAScript/Part1/X3D_ECMAScript.html
// Generator:   X3D-Edit 3.3, https://savage.nps.edu/X3D-Edit
// License:     http://www.web3d.org/x3d/content/examples/license.html

// isOver SFBool inputOnly
// diffuseColor_changed SFColor outputOnly
function initialize() { }

function isOver(val) {
    if (val == true) {
        diffuseColor_changed = new SFColor(1,0,0);
    } 
    else {
        diffuseColor_changed = new SFColor(0,0,1);
    }
}
