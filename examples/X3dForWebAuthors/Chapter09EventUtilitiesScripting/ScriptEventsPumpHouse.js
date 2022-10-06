// Filename:    ScriptEventsPumpHouse.js
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/ScriptEventsPumpHouse.js
// Created:     8 October 2007
// Revised:     10 October 2015
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// License:     http://X3dGraphics.com/examples/X3dForWebAuthors/license.html

function angle (value) {
  positionRed       = new SFVec3f (Math.cos (value), 1.5 * Math.sin(value), 0.5);
  positionGreen     = new SFVec3f (Math.cos (value+2.094), 1.5 * Math.sin(value+2.094),    0);
  positionTurquoise = new SFVec3f (Math.cos (value+4.189), 1.5 * Math.sin(value+4.189), -0.5);

  orientationRed       = new SFRotation (0, 0, 1, -2*value);
  orientationGreen     = new SFRotation (0, 0, 1, -2*(value+2.094));
  orientationTurquoise = new SFRotation (0, 0, 1, -2*(value+4.189));
}
