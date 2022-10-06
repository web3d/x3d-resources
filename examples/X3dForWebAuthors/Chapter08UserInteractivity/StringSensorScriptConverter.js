// Description: Collection of various data-type conversion utility methods.
// Filename:    StringSensorScriptConverter.js
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter08UserInteractivity/StringSensorScriptConverter.js
// Created:     17 June 2006
// Revised:     10 October 2015
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter08UserInteractivity/StringSensor.x3d
// License:     http://X3dGraphics.com/examples/X3dForWebAuthors/license.html
//
//	The name of a method indicates the incoming and outgoing datatypes.
//
//	If a particular element needs to be selected (e.g., SFVec3F to SFFloat), then
//	the element name is indicated after the incoming datatype (e.g., SFVec3fX means
//	the X ([0]) element of the datum.
//
//	Outgoing values (events) are always named after the datatype with '_out' appended.
//	If a particular element was selected, then the element name appears after the
//	underscore and before 'out'.
//
//	The exception to this naming convention is a conversion from MF* to SF*. In that
//	case, the first element ([0]) is always taken and no special notation is used.

function SFString_MFString (value) {
  MFString_out = new MFString (value);
}

function SFVec3fX_SFFloat (value) {
  SFFloat_Xout = value[0];
}

function SFVec3fY_SFFloat (value) {
  SFFloat_Yout = value[1];
}

function SFVec3fZ_SFFloat (value) {
  SFFloat_Zout = value[2];
}
