// Title:       donutmaker.js
// Description: Editable example ECMAscript (javascript) source file for use with X3D Script node
// Author:      Don Brutzman
// Identifier:  http://www.web3d.org/x3d/content/examples/Vrml2Sourcebook/Chapter30Scripts/donutmaker.js
// Created:     28 September 2000
// Revised:      3 January 2017
// Reference:   http://www.web3d.org/x3d/content/X3dTooltips.html#Script
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-FDIS-19775-1.2-X3D-AbstractSpecification/Part01/components/scripting.html
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-19777-1-X3DLanguageBindings-ECMAScript/Part1/X3D_ECMAScript.html
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/newECMAscriptTest.x3d
// License:     http://www.web3d.org/x3d/content/examples/license.html

function generateCrossSection( ) {
    var angle = 0.0;
    var delta = (2 * 3.141592653) / crossSectionResolution;
    var newCrossSection = new MFVec2f();
    var i; // index
    for ( i = 0; i <= crossSectionResolution; i++ ) {
        newCrossSection[i][0] =  crossSectionRadius * Math.cos( angle );
        newCrossSection[i][1] = -crossSectionRadius * Math.sin( angle );
        angle += delta;
    }
    // added code to make ends meet
    newCrossSection[crossSectionResolution + 1][0] = newCrossSection[0][0];
    newCrossSection[crossSectionResolution + 1][1] = newCrossSection[0][1];
    
    crossSection_changed = newCrossSection; // send single output event with all values
}

function generateSpine( ) {
    var angle = 0.0;
    var delta = (2 * 3.141592653) / spineResolution;
    var newSpine = new MFVec3f();
    var i; // index
    for ( i = 0; i <= spineResolution; i++ ) {
        spine_changed[i][0] =  spineRadius * Math.cos( angle );
        newSpine[i][1] =  0.0;
        newSpine[i][2] = -spineRadius * Math.sin( angle );
        angle += delta;
    }
    // added spine point  to make ends meet
    newSpine[spineResolution + 1][0] = newSpine[0][0];
    newSpine[spineResolution + 1][1] = 0.0;
    newSpine[spineResolution + 1][2] = newSpine[0][2];
    
    spine_changed = newSpine; // send single output event with all values
}

function set_crossSectionRadius( csr ) { // optional extra parameter: timestamp
    crossSectionRadius = csr;
    generateCrossSection();
//  Browser.print ('crossSectionRadius = ' + crossSectionRadius);
}

function set_spineRadius( sr ) { // optional extra parameter: timestamp
    spineRadius = sr;
    generateSpine();
//  Browser.print ('spineRadius = ' + spineRadius);
}

function initialize() {
    generateCrossSection();
    generateSpine();
}
