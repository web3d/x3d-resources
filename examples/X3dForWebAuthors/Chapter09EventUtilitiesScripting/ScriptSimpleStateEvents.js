// Filename:    ScriptSimpleStateEvents.js
// Author:      Leonard Daly and Don Brutzman
// Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/ScriptSimpleStateEvents.js
// Created:     10 June 2006
// Revised:     10 October 2015
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/ScriptSimpleStateEvents.x3d
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// License:     http://X3dGraphics.com/examples/X3dForWebAuthors/license.html

function buttonTimerActive (value) 
{
    if (! value) // (value == false) means timer operation complete
    {
        if (buttonDown) // button was down, return motion is now complete
        {
            buttonDown = false;
            lightColor = new SFColor (0.4, 0.4, 0.4); // dark grey
            // reset path to go back down next time
            newButtonPath = new MFVec3f (new SFVec3f(0, 0.25, 0), new SFVec3f(0, 0.05, 0));
        } 
        else // button was up, downward motion is now complete
        {
            buttonDown = true;
            lightColor = new SFColor (1, 1, 0); // yellow
            // reset path to go up next time
            newButtonPath = new MFVec3f (new SFVec3f(0, 0.05, 0), new SFVec3f(0, 0.25, 0));
        }
    }
 }
