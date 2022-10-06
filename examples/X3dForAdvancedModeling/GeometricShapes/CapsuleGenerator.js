// Description: Script computing coordinates and index values in support of CapsuleGenerator.x3d
// Filename:    CapsuleGenerator.js
// Author:      Don Brutzman
// Identifier:  http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/CapsuleGenerator.js
// Created:     13 October 2014
// Revised:     17 October 2015
// Reference:   CapsuleGenerator.x3d
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-FDIS-19775-1.2/Part01/components/scripting.html
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-19777-1-X3DLanguageBindings-ECMAScript/Part1/X3D_ECMAScript.html
// License:     ../license.html

// height SFFloat initializeOnly 1.0; total height, includes top and bottom hemispheres 
// radius SFFloat initializeOnly 0.5; for cylinder and hemispherical end caps
// numberOfPoints SFInt32 initializeOnly 36; horizontal resolution for cylinder and hemispherical end caps
// numberOfLevels SFInt32 initializeOnly 10;   vertical resolution for cylinder and hemispherical end caps
// top SFBool initializeOnly true; whether to draw top hemisphere
// side SFBool initializeOnly true; whether to draw side cylinder
// bottom SFBool initializeOnly true; whether to draw bottom hemisphere
// pointsComputed MFVec3f outputOnly; points for Coordinate node
// indicesComputed MFInt32 outputOnly; index values for IFS
// traceEnabled SFBool initializeOnly true; whether to trace output values on X3D browser console
// geometryType SFString initializeOnly polygon; determines console output node type: polygons=IndexedFaceSet, lines=IndexedLineSet, points=PointSet
// 
// pointsComputed MFVec3f outputOnly; points for Coordinate node
// indicesComputed MFInt32 outputOnly; index values for IFS

// utility methods ======================================================

// This variable declaration may be hidden or removed if defined in parent X3D Script node:
// var traceEnabled = true; // local variable

function forcePrint (stringValue)
{
    var scriptName   = 'CapsuleGenerator.js';
    var terseTrace   = false;
    // Browser.print, println is the function to output text on the X3D player's console window
    if ((scriptName.length > 0) && (terseTrace == false))
    {
        Browser.print ('[' + scriptName + '] ' + stringValue + '\n');
    }
    else 
    {
        Browser.print (                          stringValue + '\n');
    }
}
function tracePrint (stringValue)
{
    if (traceEnabled)
    {
        forcePrint (stringValue);
    }
}
// ================================================================

function tracePolygonIndices (hIndex, vIndex, newIndexArray)
{
        tracePrint ('new polygon[' + hIndex + '][' + vIndex + ']');
        
    if (newIndexArray.length >= 5)
    {
        tracePrint ('newIndexArray[' + (newIndexArray.length - 5) + ']=' + newIndexArray[newIndexArray.length - 5].toString());
        tracePrint ('newIndexArray[' + (newIndexArray.length - 4) + ']=' + newIndexArray[newIndexArray.length - 4].toString());
        tracePrint ('newIndexArray[' + (newIndexArray.length - 3) + ']=' + newIndexArray[newIndexArray.length - 3].toString());
        tracePrint ('newIndexArray[' + (newIndexArray.length - 2) + ']=' + newIndexArray[newIndexArray.length - 2].toString());
        // -1 value terminated quadrilateral
        tracePrint ('newIndexArray[' + (newIndexArray.length - 1) + ']=' + newIndexArray[newIndexArray.length - 1].toString());
    }
}
function initialize() // no parameters allowed
{
    // The initialize() function is automatically invoked when the Script node is first activated, prior to other events
    // The initialize() function can also be invoked by other functions, if appropriate

    var element1,element2,capsuleDescription;

    var saveTraceEnabled = traceEnabled;
//          traceEnabled = false; // intermediate trace statements are for debugging purposes only

    var vIndex,vAngle,radiusFactor,levelHeightFactor,hIndex,hAngle,point,indexEndCapOffset,newPointArray,newIndexArray;
    
    tracePrint('initialize() method commenced...');
    tracePrint('... (top=' + top + ', side=' + side + ', bottom=' + bottom + ', geometryType=' + geometryType + ')');

    // reset, defer assigning/sending any of the output events until done constructing
    newPointArray = new MFVec3f();
    newIndexArray = new MFInt32();

//  tracePrint ("empty newPointArray=", newPointArray.toString());

    // top endcap ===========================================================

    for (vIndex = 0; vIndex <= numberOfLevels; vIndex++) //   vertical index
    {
              vAngle = (Math.PI / 2.0) * vIndex / numberOfLevels; //  [0..90] degrees total
        radiusFactor = Math.cos(vAngle); // decreases with increasing vAngle, [1..0]
   levelHeightFactor = Math.sin(vAngle); // increases with increasing vAngle, [0..1]
        tracePrint ('(vIndex / numberOfLevels)=' + (vIndex / numberOfLevels) + ', vAngle=' + (vAngle * 180.0 / Math.PI) + ', radiusFactor=' + radiusFactor);

        for (hIndex = 0; hIndex < numberOfPoints; hIndex++) // horizontal index
        {
            hAngle = 2.0 * Math.PI * hIndex / numberOfPoints;  // horizontal angle, [0..360) degrees total
            
            if ((vIndex == 0) || (top == true)) // top points for drawing the side are handled by initial level of vIndex
            {
                // (dimensionality == ARRAY_3D_XZ_PLANE)
                // height for each endcap is 1/4 of total height, height for sides is 1/2 of total height
                // http://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFVec3f
                // append new value to end of array:
                point = new SFVec3f(( Math.sin(hAngle)  * radius * horizontalScale * radiusFactor),
                                    ((levelHeightFactor * height / 4.0) + (height / 4.0)),
                                    ( Math.cos(hAngle)  * radius * horizontalScale * radiusFactor));

                newPointArray[newPointArray.length] = point;
                tracePrint ('newPointArray[' + (newPointArray.length - 1) + ']=' + newPointArray[newPointArray.length - 1].toString());
            }
            
            if ((hIndex > 0) && (vIndex > 0)) // wait for second/second loop before starting to define polygon indices
            {
                if (top == true) // polygons only get indexed when top is drawn
                {
                    // http://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFInt32
                    // append new values to end of array, follow right-hand rule for outward-facing normals:
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) +  hIndex     );
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) + (hIndex - 1));
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) + (hIndex - 1));
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) +  hIndex     );
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(-1); // end of polygon
                    tracePolygonIndices (hIndex, vIndex, newIndexArray);
                }

                if ((hIndex == (numberOfPoints - 1)) && (top == true)) // add another polygon at end to stitch seam
                {
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints)     );
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) + hIndex);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) + hIndex);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints)     );
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(-1); // end of polygon
                    tracePolygonIndices (hIndex, vIndex, newIndexArray);
                }
            }
        }
    }
    tracePrint ('... top endcap complete');
        
    // bottom endcap ===========================================================
    
    if (top == true)
    {
        indexEndCapOffset = (numberOfLevels + 1) * numberOfPoints;
    }
    else // if (side == true) // with no top
    {
        indexEndCapOffset = numberOfPoints;
    }
    tracePrint('indexEndCapOffset=' + indexEndCapOffset);
    
    for (vIndex = 0; vIndex <= numberOfLevels; vIndex++) //   vertical index
    {
              vAngle = (Math.PI / 2.0) * vIndex / numberOfLevels; //  [0..90] degrees total
        radiusFactor = Math.cos(vAngle); // decreases with increasing vAngle, [1..0]
   levelHeightFactor = Math.sin(vAngle); // increases with increasing vAngle, [0..1]
//        traceEnabled = true;
        tracePrint ('(vIndex / numberOfLevels)=' + (vIndex / numberOfLevels) + ', vAngle=' + (vAngle * 180.0 / Math.PI) + ', radiusFactor=' + radiusFactor);
//        traceEnabled = false;
     
        for (hIndex = 0; hIndex < numberOfPoints; hIndex++) // horizontal index
        {
            hAngle = 2.0 * Math.PI * hIndex / numberOfPoints;  // horizontal angle, [0..360) degrees total
            
            if ((vIndex == 0) || (bottom == true)) // bottom points for drawing the side are handled by initial level of vIndex
            {
                // (dimensionality == ARRAY_3D_XZ_PLANE)
                // height for each endcap is 1/4 of total height, height for sides is 1/2 of total height
                // http://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFVec3f
                // append new value to end of array:
                point = new SFVec3f(( Math.sin(hAngle)   * radius * horizontalScale * radiusFactor),
                                    (-(levelHeightFactor * height / 4.0) - (height / 4.0)),
                                    ( Math.cos(hAngle)   * radius * horizontalScale * radiusFactor));

                newPointArray[newPointArray.length] = point;
                tracePrint ('newPointArray[' + (newPointArray.length - 1) + ']=' + newPointArray[newPointArray.length - 1].toString());
            }
            
            if ((hIndex > 0) && (vIndex > 0)) // wait for second/second loop before starting to define polygon indices
            {
                if (bottom == true) // polygons only get indexed when bottom is drawn
                {
                    // http://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFInt32
                    // append new values to end of array, follow right-hand rule for outward-facing normals:
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) + (hIndex - 1) + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) +  hIndex      + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) +  hIndex      + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) + (hIndex - 1) + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(-1); // end of polygon
                    tracePolygonIndices (hIndex, vIndex, newIndexArray);
                }

                if ((hIndex == (numberOfPoints - 1)) && (bottom == true)) // add another polygon at end to stitch seam
                {
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) + hIndex + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(( vIndex      * numberOfPoints) +          indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) +          indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(((vIndex - 1) * numberOfPoints) + hIndex + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(-1); // end of polygon
                    tracePolygonIndices (hIndex, vIndex, newIndexArray);
                }
            }
        }
    }
    tracePrint ('... bottom endcap complete');
    
    // sides ===========================================================
    
    if (side == true)
    {
        for (hIndex = 1; hIndex < numberOfPoints; hIndex++) // horizontal index
        {
                    // append new values to end of array, follow right-hand rule for outward-facing normals:
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */ hIndex     ;
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(hIndex - 1);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(hIndex - 1 + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(hIndex     + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */ -1 ; // end of polygon
        }
        // add another polygon at end to stitch seam
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */  0 ;
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(numberOfPoints - 1);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */(numberOfPoints - 1 + indexEndCapOffset);
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */                      indexEndCapOffset ;
                    newIndexArray[newIndexArray.length] = /* new SFInt32 */ -1 ; // end of polygon
    }
    tracePrint ('... side complete');
    tracePrint ('geometryType=' + geometryType);
    
    traceEnabled = saveTraceEnabled; // restore
    forcePrint ('computation complete!');
//  tracePrint ('newPointArray=' + newPointArray.toString());
//  tracePrint ('newIndexArray=' + newIndexArray.toString());

    if      (geometryType == 'points')
    {
        element1 = '<PointSet ';
        element2 = '</PointSet>';
    }
    else if (geometryType == 'lines')
    {
        element1 = '<IndexedLineSet ';
        element2 = '</IndexedLineSet>';
    }
    else // (geometryType == 'points')
    {
        // escaped apostrophe http://www.w3schools.com/js/js_strings.asp
        element1 = '<IndexedFaceSet creaseAngle=\'0.785398\' solid=\'true\' ';
        element2 = '</IndexedFaceSet>';
    }

    capsuleDescription = geometryType + ' Capsule consisting of ';
    if (side)   { capsuleDescription +=       numberOfPoints + ' sides, with '; }
    else        { capsuleDescription += 'no sides, with '; }
    if (top)    { capsuleDescription += '[' + numberOfPoints + ' circumference points * (' + numberOfLevels + '+1) vertical levels = ' + ((numberOfLevels +1) * numberOfPoints) + '] top-cap and '; }
    else        { capsuleDescription += 'no top-cap and '; }
    if (!top && bottom)
                { capsuleDescription += '[' + numberOfPoints + ' circumference points * (' + numberOfLevels + '+1) vertical levels = ' + ((numberOfLevels +1) * numberOfPoints) + '] '; }
    if (bottom) { capsuleDescription += ((numberOfLevels +1) * numberOfPoints) + ' bottom-cap '; }
    else        { capsuleDescription += 'no bottom-cap '; }
    if ((geometryType == 'points'))
         { capsuleDescription += 'points '; }
    else { capsuleDescription += 'quadrilaterals '; }
    capsuleDescription += 'together making a total of ' + newPointArray.length + ' Coordinate point values';

    Browser.println ("");
    Browser.println ("<Shape DEF='" + geometryType + "Capsule'>");
    Browser.println ("  <!-- " + capsuleDescription + " -->");
    if  (geometryType == 'points')
         { Browser.println ("  " + element1 + ">"); }
    else { Browser.println ("  " + element1 + "coordIndex='" + newIndexArray + "'>"); }
    Browser.println ("    <Coordinate point='" + newPointArray + "'/>");
    Browser.println ("  " + element2);
    Browser.println ("  <Appearance>");
    if ((geometryType == 'points') || (geometryType == 'lines'))
         { Browser.println ("    <Material emissiveColor='0.2 0.5 0.8'/>"); }
    else { Browser.println ("    <Material diffuseColor='0.2 0.5 0.8'/>"); }
    Browser.println ("  </Appearance>");
    Browser.println ("</Shape>");
    Browser.println ("");
    
    tracePrint ("send output events...");
     pointsComputed = newPointArray; // send via ROUTE in scene
    indicesComputed = newIndexArray; // send via ROUTE in scene
    
    tracePrint('initialize() method complete' + '\n');
}
// ================================================================================================

