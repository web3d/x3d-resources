// Description: 
// Filename:    Gimbals.js
// Author:      Eric Bachmann and Don Brutzman
// Identifier:  http://www.web3d.org/x3d/content/examples/Basic/DistributedInteractiveSimulation/Gimbals.js
// Created:     19 April 1999
// Revised:     17 October 2015
// Reference:   Gimbals.x3d
// License:     ../license.html

function calculateRotation() {
   newRotation = new SFRotation( 1, 0, 0, 0 );
   newRotation = newRotation.multiply( new SFRotation( 1, 0, 0, roll * Math.PI / 180.0 ));
   newRotation = newRotation.multiply( new SFRotation( 0, 0, 1, elevation * Math.PI / 180.0 ));
   newRotation = newRotation.multiply( new SFRotation( 0, 1, 0, -azimuth2 * Math.PI / 180.0 ));
   newRotationString = (Math.round(newRotation.x*1000.0)     / 1000.0).toString() + ', ' +
                       (Math.round(newRotation.y*1000.0)     / 1000.0).toString() + ', ' +
                       (Math.round(newRotation.z*1000.0)     / 1000.0).toString() + ', ' +
                       (Math.round(newRotation.angle*1000.0) / 1000.0).toString();
}
function buildRotationMessage() {
    rotationMessage_changed = new MFString(
            'DIS (roll, elevation, azimuth) = (' +
            (Math.round(roll * 10.0) / 10.0).toString() + ', ' +
            (Math.round(elevation * 10.0) / 10.0).toString() + ', ' +
            (Math.round(azimuth * 10.0) / 10.0).toString() + ') degrees',
            'X3D SFRotation (axis, angle) = (' + newRotationString.toString() + ')');
    Browser.print('buildRotationMessage() rotationMessage_changed=' + rotationMessage_changed.toString() + '\n');
}

function initialize() {
   roll = elevation = azimuth = azimuth2 = 0.0;
   rotationMessage_changed = new MFString (
    'DIS (roll, elevation, azimuth) = (0, 0, 0) degrees',
    'Axis-angle SFRotation = (1 0 0 0)' );
   Browser.print ('initialize() rotationMessage_changed=' + rotationMessage_changed.toString() + '\n');
}

function set_rollRotation( value) {
   roll = (-value[3] * 180.0 / Math.PI);
   // negate rotation to get DIS value
   while (roll < -180.0) 
   {
       roll += 360.0;
   }
   while (roll >= 180.0) 
   {
       roll -= 360.0;
   }
   calculateRotation();
   roll = Math.round(roll * 10.0) / 10.0; //round to nearest tenth
   buildRotationMessage();
}

function set_elevationRotation( value ) {
   elevation = value[3] * 180.0 / Math.PI;
   while (elevation < -180.0) 
   {
       elevation += 360.0;
   }
   while (elevation >= 180.0) 
   {
       elevation -= 360.0;
   }
   calculateRotation();
   elevation = Math.round(elevation*10.0) / 10.0; //round to nearest tenth
   buildRotationMessage();
}

function set_azimuthRotation( value ) {
   azimuth = -value[3] * 180.0 / Math.PI;
   //negate rotation to get DIS value
   while (azimuth < 0.0)    
   {
       azimuth += 360.0;
   }
   while (azimuth >= 360.0) 
   {
       azimuth -= 360.0;
   }
   azimuth2 = azimuth;
   while (azimuth2 < -180.0) 
   {
       azimuth2 += 360.0;
   }
   while (azimuth2 >= 180.0) 
   {
       azimuth2 -= 360.0;
   }
   calculateRotation();
// Browser.print ('set_azimuth(' + value.toString() + ') ' + 'newRotation=' + newRotation.toString() + '\n');
   azimuth = Math.round(azimuth * 10.0) / 10.0; //round to nearest tenth
   buildRotationMessage();
}
