// Description: Compute geometry for ExtrusionCrossSectionITSSPrototype.x3d, used with X3D Script node
// Filename:    ExtrusionCrossSectionITSSPrototype.js
// Author:      Don Brutzman, Sungmin Kwon
// Identifier:  http://www.web3d.org/x3d/content/examples/Basic/course/ExtrusionCrossSectionITSSPrototype.js
// Created:     11 October 2017
// Revised:     22 December 2017
// Reference:   http://www.web3d.org/x3d/content/examples/Basic/course/ExtrusionCrossSectionPrototype.x3d
// Reference:   http://www.web3d.org/x3d/content/examples/Basic/course/ExtrusionCrossSectionExampleTorus.x3d
// Reference:   http://www.web3d.org/x3d/content/examples/Basic/course/ExtrusionCrossSectionExampleShip.x3d
// License:     ../../license.html

function tracePrint (value)
{
    if (traceEnabled == true)
    {
            if (!name || (name==''))
            {
                Browser.print ('[ExtrusionCrossSection] ' + value + '\n');
            }
            else	{
                Browser.print ('[ExtrusionCrossSection ' + name + '] ' + value + '\n');
            }
    }
}
function forcePrint (value)
{
    if (!name || (name==''))
    {
        Browser.print ('[ExtrusionCrossSection] ' + value + '\n');
    }
    else	{
        Browser.print ('[ExtrusionCrossSection ' + name + '] ' + value + '\n');
    }
}
function MFVec2fToString (arrayValue) // output array as string, workaround for Xj3D bug 572
{
    var result = '';
    if (arrayValue.length > 0)
    {
        var i; // index variable
        for (i=0;i<arrayValue.length; i++)
        {
         result += arrayValue[i].x + ' ' + arrayValue[i].y;
          if (i<arrayValue.length-1) 
          {
              result += ', ';
          }
        }
    }
    return result;
}
function rotateVector (rotationAxisAngle4Tuple, vector3Tuple) // to calculate Rotation Matrix
{
	var rotatedResult			 = new SFVec3f (-1, -1, -1);
	var x, y, z, a, c, s, t;
	
	x = rotationAxisAngle4Tuple[0];	y = rotationAxisAngle4Tuple[1];	z = rotationAxisAngle4Tuple[2];	a = rotationAxisAngle4Tuple[3];
	c = Math.cos(a);	s = Math.sin(a);	t = 1 - c;
	
	rotatedResult[0] = (t*x*x + c  )*vector3Tuple[0] + (t*y*x - s*z)*vector3Tuple[1] + (t*z*x + s*y)*vector3Tuple[2];
	rotatedResult[1] = (t*x*y + s*z)*vector3Tuple[0] + (t*y*y + c  )*vector3Tuple[1] + (t*y*z - s*x)*vector3Tuple[2];
	rotatedResult[2] = (t*x*z - s*y)*vector3Tuple[0] + (t*y*z + s*x)*vector3Tuple[1] + (t*z*z + c  )*vector3Tuple[2];
		
	//Browser.print('my function called \n');
	//Browser.print(' Rot.Vec = ' + rotationAxisAngle4Tuple + ',   Vec1 = ' + vector3Tuple + ',   Vec2 = ' + rotatedResult + '\n');
	
	return rotatedResult;
}

function normalizeTest ()
{
  var testAxis = new SFVec3f (2, 4, 6);
  tracePrint ('normalizeTest:  testAxis=[' + testAxis.toString() + '], testAxis.normalize()=[' + testAxis.normalize().toString() + ']');
}
function initialize ()
{  
  tracePrint ('==============================================================');
  tracePrint ('traceEnabled=' + traceEnabled);
  var x3dResult = '\n' +
              '<X3D version="3.1" profile="Immersive">\n' + 
              '  <Scene>\n'  +
              '    <!-- Extrusion hull -->\n' +
              '    <Shape>\n' +
              '      <Extrusion crossSection="'  + new MFVec2fToString(crossSection) +
                         '" scale="'             + new MFVec2fToString(scale) +
                         '" orientation="'       + orientation.toString() +
                         '" spine="'             + spine.toString() +
                         '" convex="false' +
                         '" solid="false' +
                         '"/>\n' +
              '      <Appearance>\n' +
              '        <Material diffuseColor="' + crossSectionColor.toString() +
                         '" transparency="'      + crossSectionTransparency.toString() +
                         '"/>\n' +
              '      </Appearance>\n' +
              '    </Shape>\n';
  tracePrint ('===== normalizeTest() - verify function availability =========');
  normalizeTest ();
  tracePrint ('===== initialize() - calculate extrusion representations =====');
  var errorFound = false;
  if (spine.length < 2)
  {
  	errorFound = true;
	forcePrint ('[Error] spine.length' + spine.length + ' < 2');
  }
  // single scale value is applied uniformly across all spine points
  if ((spine.length != scale.length) && (scale.length > 1))
  {
  	errorFound = true;
	forcePrint ('[Error] spine.length' + spine.length + ' != scale.length' + scale.length);
  }
  // single orientation value is applied uniformly across all spine points
  if ((spine.length != orientation.length) && (orientation.length > 1))
  {
  	errorFound = true;
	forcePrint ('[Error] spine.length' + spine.length + ' != orientation.length' + orientation.length);
  }
  if (errorFound) 
  {
      return;
  }

  tracePrint ('spine.length=' + spine.length);
  tracePrint ('spine=' + spine.toString());
  var sp; // index variable
  for ( sp = 0; sp <= spine.length - 1; sp++ ) {
	spineIndex[sp] =  sp;
  }
    x3dResult += '    <!-- Spine -->\n' +
               '    <Shape>\n' +
               '      <IndexedLineSet coordIndex="' + spineIndex.toString() + '">\n' +
               '        <Coordinate point="' + spine.toString() + '"/>\n' +
               '      </IndexedLineSet>\n' +
               '      <Appearance>\n' +
               '        <Material emissiveColor="' + spineColor.toString() + '"/>\n' +
               '      </Appearance>\n' +
               '    </Shape>\n';

  tracePrint ('crossSection.length=' + crossSection.length);
//tracePrint ('crossSection=' + crossSection.toString()); // TODO Xj3D bugfix 572
  tracePrint ('crossSection=' + new MFVec2fToString(crossSection));

  tracePrint ('scale.length=' + scale.length);
  if (scale.length == 0)
  {
	scale[0] = new SFVec2f(1, 1);
	tracePrint ('reset scale=' + scale.toString());
  }
//else tracePrint ('scale=' + scale.toString()); // TODO Xj3D bugfix 572
  else 
  {
      tracePrint ('scale=' + new MFVec2fToString(scale));
  }

  tracePrint ('orientation.length=' + orientation.length);
  if (orientation.length == 0)
  {
	orientation[0] = new SFRotation(0, 0, 1, 0);
	tracePrint ('reset orientation=' + orientation.toString());
  }
  else 
  {
      tracePrint ('orientation=' + orientation.toString());
  }

  var SCPxAxis           = new SFVec3f ();
  var SCPyAxis           = new SFVec3f ();
  var SCPzAxis           = new SFVec3f ();
//var SCPxAxisPrevious   = new SFVec3f ();
  var SCPyAxisPrevious   = new SFVec3f ();
  var SCPzAxisPrevious   = new SFVec3f ();
  var startAxis          = new SFVec3f ();
  var SCPnewAxisRotation = new SFVec3f ();
  var offset             = new SFVec3f ();
  var orientationFix     = new SFRotation();
  var orientedOffset     = new SFVec3f ();
  var SCPoffset          = new SFVec3f ();
  var yRotated;
  var i, j, k = 0, l = 0; // index variables
  var st, n = spine.length;
  var firstSpine;
  var uniqueCrossSectionPoints;

  // When CrossSection is Closed, (crossSection.length - 1) is total number of diff points.
  if( ( crossSection[0][0] == crossSection[crossSection.length-1][0] ) &&
      ( crossSection[0][1] == crossSection[crossSection.length-1][1] ))
  {
    uniqueCrossSectionPoints = crossSection.length - 1; 
  }
  else   // When CrossSection is Open, (crossSection.length) is total number of diff points.
  {
    uniqueCrossSectionPoints = crossSection.length;
  }
  
  tracePrint ('====== n==spine.length=' + n + ' ======');
  tracePrint ('====== spine[0]=' + spine[0] + ', spine[n-1]=' + spine[n-1] + ' ======');
  for ( sp = 0; sp < n ; sp++ ) {

	tracePrint ('====== sp spineIndex=' + sp + ', spine[sp]=' + spine[sp] + ' ======');
	// so far, these crossSection faces are perpendicular to x axis.
	// must align each crossSection with spine-aligned cross-section plane (SCP),
	// which bisects incoming and outgoing spine tangents at each control point

	// apparently all three SCP axes are defined in the specification for thoroughness,
	// but only one will be needed to actually rotate the SCP from the default x-axis perpendicular.

	if (spine.length == 2)// 2-element spine:  second is last, same SCP as first
	{
		tracePrint ('2-element spine:  second is last, same SCP as first');
		SCPyAxis = (spine[1].subtract(spine[0])).normalize();
		firstSpine = SCPyAxis;
		if ((Math.abs(firstSpine.x)!=1) || (firstSpine.y!=0) || (firstSpine.z!=0))
                {
                    SCPzAxis = (new SFRotation (1, 0, 0, Math.PI/2)).multVec(firstSpine);
                }
		else
                {
                    SCPzAxis = (new SFRotation (0, 1, 0, Math.PI/2)).multVec(firstSpine);
                }
	}
	else if ((sp == 0) && (spine[0].x == spine[n-1].x) && (spine[0].y == spine[n-1].y) && (spine[0].z == spine[n-1].z))	// first, closed
	{
		tracePrint ('SCP axis:  first [sp==0], closed');
		SCPyAxis = (spine[1].subtract(spine[n-2])).normalize();
		SCPzAxis = (spine[1].subtract(spine[0])).cross(spine[n-2].subtract(spine[0])).normalize();
	}
	else if (sp == 0)				// first, open
	{
		tracePrint ('SCP axis:  first [sp==0], open');
		SCPyAxis = (spine[1].subtract(spine[0])).normalize();
		SCPzAxis = (spine[2].subtract(spine[1])).cross(spine[0].subtract(spine[1]));
	}
	else if ((sp == n-1) && (spine[0].x == spine[n-1].x) && (spine[0].y == spine[n-1].y) && (spine[0].z == spine[n-1].z)) // last, closed
	{
		tracePrint ('SCP axis:  last [sp==' + (n-1) + '], closed');
		SCPyAxis = (spine[1].subtract(spine[n-2])).normalize();
		SCPzAxis = (spine[1].subtract(spine[0])).cross(spine[n-2].subtract(spine[0])).normalize();
	}
	else if ((sp == n-1) && (spine.length > 2))	// last, open
	{
		tracePrint ('SCP axis:  last [sp==' + (n-1) + '], open');
		SCPyAxis = (spine[n-1].subtract(spine[n-2])).normalize();
		SCPzAxis = (spine[n-1].subtract(spine[n-2])).cross(spine[n-3].subtract(spine[n-2])).normalize();
	}
	else if (sp < n - 1)				// intermediate
	{
		tracePrint ('SCP axis:  intermediate');
		SCPyAxis = (spine[sp+1].subtract(spine[sp-1])).normalize();
		SCPzAxis = (spine[sp+1].subtract(spine[sp])).cross(spine[sp-1].subtract(spine[sp])).normalize();
	}
	else 
        {
            forcePrint ('!!! SCP axis:  error, unexpected spine case');
        }

	if ((sp > 0) && (spine.length > 2))
	{
		// prevent small changes in spine segment angles from flipping cross-section 180 degrees
		if (SCPzAxis.dot(SCPzAxisPrevious) < 0)
		{
			SCPzAxis = SCPzAxis.multiply (-1);
			tracePrint ('SCPzAxis.multiply (-1), sp=' + sp);
		}
	}
	// zero SCP axis means collinear spines, so must compute one axis from plane of acceptable values.
	// thus use prior value for smooth continuity.
	if ((SCPyAxis.x==0) && (SCPyAxis.y==0) && (SCPyAxis.z==0))
	{
	   if (sp == 0)
	   {
		tracePrint ('*** indeterminate SCPyAxis=(0, 0, 0) for spine.point sp=' + sp + ', rotating 90 degrees from spine');
		firstSpine = new SFVec3f((spine[1].subtract(spine[0])).normalize());
		if ((Math.abs(firstSpine.x)!=1) || (firstSpine.y!=0) || (firstSpine.z!=0))
                {
                    SCPyAxis = (new SFRotation (1, 0, 0, Math.PI/2)).multVec(firstSpine);
                }
		else
                {
                    SCPyAxis = (new SFRotation (0, 1, 0, Math.PI/2)).multVec(firstSpine);
                }
	   }
	   else
	   {
		tracePrint ('*** indeterminate SCPyAxis=(0, 0, 0) for spine.point sp=' + sp + ', reset to previous value');
		SCPyAxis = SCPyAxisPrevious;
	   }
	}
	if ((SCPzAxis.x==0) && (SCPzAxis.y==0) && (SCPzAxis.z==0))
	{
	   if (sp == 0)
	   {
		tracePrint ('*** indeterminate SCPzAxis=(0, 0, 0) for spine.point sp=' + sp + ', rotating 90 degrees from SCPyAxis');
		if ((Math.abs(SCPyAxis.x)!=1) || (SCPyAxis.y!=0) || (SCPyAxis.z!=0))
                {
                    SCPzAxis = (new SFRotation (1, 0, 0, Math.PI/2)).multVec(SCPyAxis);
                }
		else
                {
			SCPzAxis = (new SFRotation (0, 1, 0, Math.PI/2)).multVec(SCPyAxis);
                }
	   }
	   else
	   {
		tracePrint ('*** indeterminate SCPzAxis=(0, 0, 0) for spine.point sp=' + sp + ', reset to previous value');
		SCPzAxis = SCPzAxisPrevious;
	   }
	}
        SCPyAxis = SCPyAxis.normalize();
        SCPzAxis = SCPzAxis.normalize();
	SCPxAxis = SCPyAxis.cross(SCPzAxis);

	SCPyAxisPrevious = SCPyAxis;
	SCPzAxisPrevious = SCPzAxis;
	tracePrint ('SCPxAxis=' + SCPxAxis.toString());
	tracePrint ('SCPyAxis=' + SCPyAxis.toString());
	tracePrint ('SCPzAxis=' + SCPzAxis.toString());
	if ((SCPxAxis.x==0) && (SCPxAxis.y==0) && (SCPxAxis.z==0))
	{
		forcePrint ('*** indeterminate SCPxAxis=(0, 0, 0) for spine.point sp=' + sp + ', unexpected error');
	}

	startAxis = new SFVec3f (1, 0, 0); // basis for comparison relative to rotation of SCPyAxis
	tracePrint ('startAxis=' + startAxis.toString());
	if (Math.abs(startAxis.dot(SCPyAxis)) != 1)
	{
		// spec calls local negative x-axis the y-axis of SCP (i.e. SCP perpendicular)
		// we want to rotate default y axis of current cross section to align with SCPyAxis
		SCPnewAxisRotation = new SFRotation (startAxis, SCPyAxis);
		yRotated = true;
		tracePrint ('[a] yRotated=' + yRotated + ', SCPnewAxisRotation=' + SCPnewAxisRotation.toString());
	}
	else if (startAxis.dot(SCPyAxis) == 1) // aligned, same direction
	{
		// dot-product==1 means already aligned with y-axis
		SCPnewAxisRotation = new SFRotation (1, 0, 0, 0);
		tracePrint ('*** SCPyAxis [' + SCPyAxis.toString() + '] colinear with startAxis [' + startAxis.toString() + '] in same direction for spine.point sp=' + sp);
		yRotated = false;
		tracePrint ('[b] yRotated=[' + yRotated + '], SCPnewAxisRotation=[' + SCPnewAxisRotation.toString() + ']');
	}
	else // aligned, opposite direction
	{
		// dot-product==1 means already aligned with y-axis
		SCPnewAxisRotation = new SFRotation (0, 0, 1, -Math.PI);
		tracePrint ('*** SCPyAxis [' + SCPyAxis.toString() + '] colinear with startAxis [' + startAxis.toString() + '] in opposite direction for spine.point sp=' + sp);
		yRotated = false;
		tracePrint ('[c] yRotated=' + yRotated + ', SCPnewAxisRotation=[' + SCPnewAxisRotation.toString() + ']');
	}

        tracePrint ('yRotated=' + yRotated + ', SCPnewAxisRotation=[' + SCPnewAxisRotation.toString() + ']\n');
        
	// generate crossSection points + repeated initial point + [-1] flag for each loop
	for ( i = 0; i <= crossSection.length - 1; i++ )
	{
	   j = sp * (crossSection.length + 2) + i;
	   
	   //offset = new SFVec3f();
	   if (scale.length > 1)
	   {	// not sure why crossSection leading coefficient is negative, maybe an SCP factor
		offset[0] =   0;				// x
		offset[1] = - crossSection[i][0]*scale[sp][0];	// y
		offset[2] = - crossSection[i][1]*scale[sp][1] * (-1);	// z
	   }
	   else if ((SCPzAxis.x==0) && (SCPzAxis.y==1) && (SCPzAxis.z==0))
	   {	// special case, Extrusion spine in horizontal plane:  switch order of coordinates to rotate 90 degrees about spine axis
                // not sure why crossSection leading coefficient is negative, maybe an SCP factor
		offset[0] =   0;				// x
		offset[1] = - crossSection[i][1]*scale[0][1] * (-1);	// y
		offset[2] = - crossSection[i][0]*scale[0][0];	// z
		tracePrint ('*** spine in horizontal plane, sp=' + sp + ', rotating startAxis by -90 degrees');
                // MoebiusStrip.x3d still appears to have one mismatched case at midpoint SCP if crossSection is asymmetric
	   }
	   else
	   {	// not sure why crossSection leading coefficient is negative, maybe an SCP factor
		offset[0] =   0;				// x
		offset[1] = - crossSection[i][0]*scale[0][0];	// y
		offset[2] = - crossSection[i][1]*scale[0][1] * (-1);	// z
	   }

           tracePrint ('['+i+'] offset=[' + offset.toString() + '], ');

	   if (orientation.length > 1)
	   {
		orientationFix = new SFRotation(
			orientation[sp].y,
			orientation[sp].x * (-1),
			orientation[sp].z,
			orientation[sp].angle);
		// rotate this point by fixed orientation value
		// -------------------------------------------------------------------------------------------- THIS : POINT 1
		//orientedOffset = orientationFix.multVec (offset);  // bad calculation for vector rotation
		orientedOffset = rotateVector (orientationFix, offset);
		tracePrint ('[e] orientedOffset=[' + orientedOffset.toString() + ']');

                tracePrint (' orientationFix=[' + orientationFix.toString() + '], ');
                tracePrint ('[e] orientedOffset=[' + orientedOffset.toString() + ']\n');
	   }
	   else	// single orientation to apply throughout
	   {
		orientationFix = new SFRotation(
			orientation[0].y,
			orientation[0].x * (-1),
			orientation[0].z,
			orientation[0].angle);
		// -------------------------------------------------------------------------------------------- THIS : POINT 2
		//orientedOffset = orientationFix.multVec (offset);  // bad calculation for vector rotation
		orientedOffset = rotateVector (orientationFix, offset);
		tracePrint ('[f] orientedOffset=[' + orientedOffset.toString() + ']');
                
                tracePrint (' orientationFix=[' + orientationFix.toString() + '], ');
		tracePrint ('[f] orientedOffset=[' + orientedOffset.toString() + ']\n');
	   }
	   // rotate this point by SCPnewAxisRotation correction
	   // -------------------------------------------------------------------------------------------- THIS : POINT 3
	   //SCPoffset = SCPnewAxisRotation.multVec (orientedOffset);  // bad calculation for vector rotation
	   SCPoffset = rotateVector (SCPnewAxisRotation, orientedOffset);

       //Browser.print ('SCPoffset=[' + SCPoffset.toString() + ']\n');
           
	   computedFacePoints[j][0] =  spine[sp][0] + SCPoffset[0];	// x
	   computedFacePoints[j][1] =  spine[sp][1] + SCPoffset[1];	// y
	   computedFacePoints[j][2] =  spine[sp][2] + SCPoffset[2];	// z

//	   tracePrint ('offset=' + offset + ', orientedOffset=' + orientedOffset + ', SCPoffset=' + SCPoffset);
  	}
        // tessellation of each section
        // assume convex, for case of concave we should change the algorithm
        // (number of triangle) = (uniqueCrossSectionPoints - 2)
        st =  sp * (crossSection.length + 2);   // starting point of triangles

        /* CASE: TriangleSet 
        for (i = 0; i < uniqueCrossSectionPoints - 2; i++ )
        {
            computedFacesIndex[k++] = st;
            computedFacesIndex[k++] = st + 1 + i;
            computedFacesIndex[k++] = st + 2 + i;
        }*/

        /* CASE: TriangleFanSet */
        for (i = 0; i < uniqueCrossSectionPoints; i++ )  
            computedFacesIndex[k++] = st + i;
        computedFacesIndex[k++] = -1;

        // tessellation of each enclosing extrusion
        // (number of triangle) = (uniqueCrossSectionPoints * 2)
        //if( sp == 0 )
        if( sp < spine.length - 1)
        {
            for (i = 0; i < uniqueCrossSectionPoints; i++ )
            {
                computedTriStripIndex[l++] = st + i + crossSection.length + 2;
                computedTriStripIndex[l++] = st + i;
            }
            
            computedTriStripIndex[l++] = st + crossSection.length + 2;
            computedTriStripIndex[l++] = st;
            computedTriStripIndex[l++] = -1;
        }

	// Computation algorithm example provided next to illustrate steps.
	// crossSection.length=9  ==>  point sequence as follows:
	//  0..8,  initial point 0 repeat at 9, terminator 10,
	// 11..19, initial point 11 repeat at 20, terminator 21,
	// 22..30, initial point 22 repeat at 31, terminator 32, etc.
	j = (sp + 1) * (crossSection.length + 2) - 2;
	// first close polygon with repeated initial point, then [-1] terminator
	computedFacePoints[j]  [0] = 0; // unused, corresponds to repeated polygon point
	computedFacePoints[j]  [1] = 0; // unused
	computedFacePoints[j]  [2] = 0; // unused
	computedFacePoints[j+1][0] = 0; // unused, corresponds to [-1] end-of-face flag
	computedFacePoints[j+1][1] = 0; // unused
	computedFacePoints[j+1][2] = 0; // unused
  }
  //computedTriStripIndex[l] = -1;

  // send events only once via assignment statement
  facesIndex = computedFacesIndex;
  facePoints = computedFacePoints;
  triStripIndex = computedTriStripIndex;
  
  tracePrint ('facesIndex.length=' + facesIndex.length);
  tracePrint ('facePoints.length=' + facePoints.length);
  tracePrint ('facesIndex=[' + facesIndex.toString() + ']');
  tracePrint ('facePoints=[' + facePoints.toString() + ']');
  
  x3dResult += '    <!-- Cross sections -->\n' +
               '    <Shape>\n' +
               '      <IndexedFaceSet coordIndex="' + facesIndex.toString() +
                         '" convex="false' +
                         '" solid="false' +
                         '">\n' +
               '        <Coordinate point="' + facePoints.toString() + '"/>\n' +
               '      </IndexedFaceSet>\n' +
               '      <Appearance>\n' +
               '        <Material diffuseColor="' + extrusionColor.toString() + '" transparency="' + extrusionTransparency.toString() + '"/>\n' +
               '      </Appearance>\n' +
               '    </Shape>\n' +
               '  </Scene>\n' +
               '</X3D>\n';
  tracePrint ('===== initialize() - output illustrated extrusion X3D =====');
  tracePrint (x3dResult);
}
// run-time event updates:  recalculate everything
function set_crossSection (value)
{
	tracePrint ('set_crossSection=[' + value.toString() + ']');
	crossSection = value;
	initialize ();
}
function set_orientation  (value) // , timeStamp
{
	tracePrint ('set_orientation=[' + value.toString() + ']');
	orientation = value;
	initialize ();
}
function set_scale        (value) // , timeStamp
{
	tracePrint ('set_scale=[' + value.toString() + ']');
	scale = value;
	initialize ();
}
function set_spine        (value) // , timeStamp
{
	tracePrint ('set_spine=[' + value.toString() + ']');
	spine = value;
	initialize ();
}
