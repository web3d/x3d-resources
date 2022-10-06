// Description: Process key presses to bind next, previous viewpoints
// Filename:    KeySensorLeftyScript.js
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter08UserInteractivity/KeySensorLeftyScript.js
// Created:     10 June 2006
// Revised:     2 April 2017
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter08UserInteractivity/KeySensorLefty.x3d
// License:     http://X3dGraphics.com/examples/X3dForWebAuthors/license.html

function keyPress (value) {
  Browser.print ('Key press = >' + value + '< Initial viewpoint: ' + viewpointIndex + '\n');
  // bind next viewpoint
  if (value == 'N' || value == 'n') {
    viewpointIndex ++;
    if (viewpointIndex > 6) {
      viewpointIndex = 1;
    }
    if (viewpointIndex == 1) {
      bind_View1 = true;
    }
    if (viewpointIndex == 2) {
      bind_View2 = true;
    }
    if (viewpointIndex == 3) {
      bind_View3 = true;
    }
    if (viewpointIndex == 4) {
      bind_View4 = true;
    }
    if (viewpointIndex == 5) {
      bind_View5 = true;
    }
    if (viewpointIndex == 6) {
      bind_View6 = true;
    }
  }
  // similarly, bind previous viewpoint
  if (value == 'P' || value == 'p') {
    if (viewpointIndex == 1) {
      bind_View1 = false;
    }
    if (viewpointIndex == 2) {
      bind_View2 = false;
    }
    if (viewpointIndex == 3) {
      bind_View3 = false;
    }
    if (viewpointIndex == 4) {
      bind_View4 = false;
    }
    if (viewpointIndex == 5) {
      bind_View5 = false;
    }
    if (viewpointIndex == 6) {
      bind_View6 = false;
    }
    viewpointIndex --;
    if (viewpointIndex < 1) {
      viewpointIndex = 6;
    }
  }
}
