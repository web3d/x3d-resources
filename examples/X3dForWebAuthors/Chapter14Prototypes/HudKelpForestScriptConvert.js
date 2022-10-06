// Description: Script that converts typed values
// Filename:    HudKelpForestScriptConvert.js
// Author:      Leonard Daly
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/HudKelpForestScriptConvert.js
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/HudKelpForest.x3d
// Created:     1 January 1999
// Revised:     10 October 2015
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// License:     ../license.html

function setDigits (v, p) {
  return Math.floor (v*p + 0.5) / p;
}

function sfVec3f (value) {
  var x = setDigits (value[0], 100);
  var y = setDigits (value[1], 100);
  var z = setDigits (value[2], 100);
  sfVec3fString = new MFString (x + '  ' + y + '  ' + z);
}

function sfRotation (value) {
  var x = setDigits (value[0], 100);
  var y = setDigits (value[1], 100);
  var z = setDigits (value[2], 100);
  var r = setDigits (value[3], 1000);
  sfRotationString = new MFString (x + '  ' + y + '  ' + z + '  ' + r);
}
