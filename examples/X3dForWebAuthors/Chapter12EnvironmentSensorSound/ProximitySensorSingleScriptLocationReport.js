// Description: Type conversion of position and orientation values to MFString
// Filename:    ProximitySensorSingleScriptLocationReport.js
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter12EnvironmentSensorSound/ProximitySensorSingleScriptLocationReport.js
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter12EnvironmentSensorSound/ProximitySensor.x3d
// Created:     15 July 2006
// Revised:     10 October 2015
// License:     ../license.html

function setDigits (v, p) {
  return Math.floor (v*p + 0.5) / p;
}

function position (value) {
  var x = setDigits (value[0], 100);
  var y = setDigits (value[1], 100);
  var z = setDigits (value[2], 100);
  var positionText = new MFString ('Position  ' + x + '  ' + y + '  ' + z);

  Browser.print (positionText[0]+'\n');
}
function orientation (value) {
  var x = setDigits (value[0], 1000);
  var y = setDigits (value[1], 1000);
  var z = setDigits (value[2], 1000);
  var r = setDigits (value[3], 100);
  var orientationText = new MFString ('Rotation  ' + x + '  ' + y + '  ' + z + '  ' + r);

  Browser.print (orientationText[0]+'\n');
}
