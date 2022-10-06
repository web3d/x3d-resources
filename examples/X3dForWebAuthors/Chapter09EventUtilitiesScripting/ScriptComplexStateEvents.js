// Filename:    ScriptComplexStateEvents.js
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/ScriptComplexStateEvents.js
// Created:     10 June 2006
// Revised:     10 October 2015
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/ScriptComplexStateEvents.x3d
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// License:     http://X3dGraphics.com/examples/X3dForWebAuthors/license.html

function buttonMotionDone (eventValue) 
{
    if (eventValue == false) // eventValue is false when button touch is released
    {
        // the buttonPushCount eventValue toggles 0..1..2..3 to represent four states, off/low/medium/high
        buttonPushCount ++;
        
        if (buttonPushCount > 3) // can use modulo operator % instead
        {
            buttonPushCount = 0;
        }
        if (buttonPushCount == 0) 
        {
            // color of lightBulb indicates off/low/medium/high
            lightBulbColor = new SFColor (0.2, 0.2, 0.2); // off
        } 
        else if (buttonPushCount == 1) 
        {
            lightBulbColor = new SFColor (0.6, 0.5, 0.2); // low
        } 
        else if (buttonPushCount == 2) 
        {
            lightBulbColor = new SFColor (0.7, 0.7, 0.2); // medium
        } 
        else  if (buttonPushCount == 3) 
        {
            lightBulbColor = new SFColor (1, 1, 0.6);   // high
        }
        else // problem detected, what happened?
        {
            Browser.println ('buttonMotionDone() improper initialization!');
            buttonPushCount = 1; // first click followed initial state
            lightBulbColor = new SFColor (0.6, 0.5, 0.2); // low
        }
        Browser.println ('buttonMotionDone() new buttonPushCount=' + buttonPushCount);
        Browser.println ('buttonMotionDone() new  lightBulbColor=' + lightBulbColor);
    }
    // ignore when event eventValue is true
}                    