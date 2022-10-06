// Filename:    LoadSensorKelpForestMainScript.js
// Description: Report LoadSensor progress
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter12EnvironmentSensorSound/LoadSensorKelpForestMainScript.js
// Created:     10 June 2006
// Revised:     2 April 2017
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter12EnvironmentSensorSound/LoadSensorKelpForestMain.x3d
// License:     http://X3dGraphics.com/examples/X3dForWebAuthors/license.html

// <field accessType='inputOnly' name='progress' type='SFFloat'/>

function initialize () {
  Browser.print ('*************************   LoadSensor initialize()  *************');
}

function progress (value, time) {
  Browser.print ('***** New Entry ****');
  Browser.print (time.toString() + ':  Progress = ' + value.toString());
}
