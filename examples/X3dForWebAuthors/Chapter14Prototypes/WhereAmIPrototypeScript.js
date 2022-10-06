// Description: Script that writes current view position and orientation to X3C browser console
// Filename:    WhereAmIPrototypeScript.js
// Author:      Leonard Daly
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/WhereAmIPrototypeScript.js
// Created:     1 January 1999
// Revised:     10 October 2015
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// License:     ../license.html


function setDigits (v, p) {
  return Math.floor (v*p + 0.5) / p;
}

function position (value) {
  var x = setDigits (value[0], 100);
  var y = setDigits (value[1], 100);
  var z = setDigits (value[2], 100);
  Browser.print ('Position (x, y, z) = ' + x + ' ' + y + ' ' + z + '\n');
}

function orientation (value) {
  var x = setDigits (value[0], 1000);
  var y = setDigits (value[1], 1000);
  var z = setDigits (value[2], 1000);
  var r = setDigits (value[3], 100);
  Browser.print ('Orientation (x, y, z, r) = ' + x + ' ' + y + ' ' + z + ' ' + r + '\n');
}
