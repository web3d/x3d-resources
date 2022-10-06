// Description: 
// Filename:    PixelTextureInterpolator.js
// Author:      Don Brutzman
// Identifier:  https://savage.nps.edu/Savage/Tools/Authoring/PixelTextureInterpolator.js
// Created:     15 April 2008
// Revised:     16 April 2008
// License:     ../../license.html

function initialize ()
{
    forcePrint ('commence initialize()...');
    
    // check array values legal, consistent
    
    if (key.length != keyValue.length)
    {
        forcePrint ('Error:  key.length=' + key.length + ', keyValue.length=' + keyValue.length);
    }
    if (key.length > 1) // pairwise compare
    {
        for (i = 0; i < key.length - 1; i++)
        {
            if (key[i] > key[i+1]) forcePrint ('Error:  key array not monotonically increasing');
        }
    }
    if (keyValue.length >= 1) // check each
    {
        for (i = 0; i < keyValue.length; i++) // for each PixelTexture
        {
            if      (keyValue[i].image.length < 3)
                forcePrint ('Error:  insufficient data in PixelTexture[' + i + '].image=' + keyValue[i].image);
            else if ((keyValue[i].image[0] < 0) || (keyValue[i].image[1] < 0))
                forcePrint ('Error:  illegal image size, PixelTexture[' + i + '].image=' + keyValue[i].image);
            else if ((keyValue[i].image[2] < 0) || (keyValue[i].image[2] > 4))
                forcePrint ('Error:  illegal image component count (must be 0..4), PixelTexture[' + i + '].image=' + keyValue[i].image);
                
            // TODO:  create integer array
            
            if (i < keyValue.length - 1)
            {
                // this is oversimplified, should turn array data into floats first
                if (keyValue[i].length != keyValue[i+1].length)
                    forcePrint ('Error:  PixelTexture[' +  i    + '].image.length=' + keyValue[i].image.length +
                         ' mismatch with PixelTexture[' + (i+1) + '].image.length=' + keyValue[i+1].image.length);
            }
        }
    }
    
    // compute proper key and proportional interval
    
    // compute output using corresponding keyValue elements and interpolation proportion
    
    forcePrint ('initialize() complete');
}
function set_fraction (value)
{
	tracePrint ('set_fraction=' + value);
}
function tracePrint (outputString)
{
	if (traceEnabled) Browser.print ('[PixelTextureGenerator]' + outputString);
}
function forcePrint (outputString)
{
	Browser.print ('[PixelTextureGenerator]' + outputString);
}

//////////////////////////////////////////////////////////
//	if ((waypoints.length == 2) &&
//	    (waypoints[0].x == 0) && (waypoints[0].y == 0) && (waypoints[0].z == 0) &&
//	    (waypoints[1].x == 0) && (waypoints[1].y == 0) && (waypoints[1].z == 0))
//	{
//		tracePrint ('[default waypoints, no action needed]');
//		return;
//	}
//	if (waypoints.length < 2)
//	{
//		forcePrint ('*** error: insufficient waypoints, WaypointInterpolator ignored ***');
//		scriptError=true;
//		return;
//	}
//	if (	heightLabel.toLowerCase()!='altitude' &&
//		heightLabel.toLowerCase()!='depth' &&
//		heightLabel.toLowerCase()!='none')
//	{
//		forcePrint ('*** error, heightLabel =' + heightLabel + ', allowed values (none, altitude, depth) ***');
//		heightLabel ='none';
//	}
//
//	useDefaultSpeed = false; // initialize booleans
//	useLegSpeeds    = false;
//	useLegDurations = false;
//
//	if ((legSpeeds.length == 0) && (legDurations.length == 0)) // use defaultSpeed
//	{
//		tracePrint ('defaultSpeed    =' + defaultSpeed + ' meters/second');
//		if (defaultSpeed <= 0)
//		{
//			forcePrint ('*** error, defaultSpeed <= 0 ***');
//			scriptError=true;
//			return;
//		}
//		else
//		{
//			useDefaultSpeed = true;
//			tracePrint ('useDefaultSpeed = true');
//		}
//	}
//	else if (legSpeeds.length > 0)
//	{
//		tracePrint ('legSpeeds       =' + legSpeeds + ' meters/second');
//		if (legSpeeds.length != waypoints.length - 1)
//		{
//			forcePrint ('*** error, legSpeeds.length must be one less than waypoints.length ***');
//			scriptError=true;
//			return;
//		}
//		for (i = 0; i < legSpeeds.length; i++)
//		{
//			if (legSpeeds[i] <= 0)
//			{
//				forcePrint ('*** error, legSpeeds[' + i + '] zero or negative ***');
//				scriptError=true;
//				return;
//			}
//		}
//		if (legDurations.length > 0)
//			tracePrint ('warning: legDurations ignored, useLegSpeeds=true');
//		else	tracePrint ('useLegSpeeds=true');
//		useLegSpeeds=true;
//	}
//	else // legDurations.length > 0
//	{
//		tracePrint ('legDurations    =' + legDurations + ' seconds');
//		if (legDurations.length != waypoints.length - 1)
//		{
//			forcePrint ('*** error, legDurations.length must be one less than waypoints.length ***');
//			scriptError=true;
//			return;
//		}
//		for (i = 0; i < legDurations.length; i++)
//		{
//			if (legDurations[i] < 0)
//			{
//				legDurations[i] = Math.abs(legDurations[i]);
//				forcePrint ('*** error, legDurations[' + i + ']= -' + legDurations[i]
//					+ ' is less than zero ***');
//				scriptError=true;
//				return;
//			}
//			else if (legDurations[i] == 0)
//			{
//				forcePrint ('*** Warning, zero value encountered/ignored: ' +
//				'legDurations[' + i + '] =' + legDurations[i]);
//			}
//		}
//		tracePrint ('useLegDurations=true');
//		useLegDurations=true;
//	}
//	var positionKeyValueArray = new MFVec3f ();
//	positionKeyValueArray = waypoints;
//
//	// pass interpolator updates by reference, pointIndices by value
//	var distances                         = new MFFloat ();
//	var pointIndicesAccumulator           = new MFInt32 ();
//	var verticalDropLineIndicesAccumulator        = new MFInt32 ();
//	var verticalDropLinePointsAccumulator = new MFVec3f ();
//	var totalDistance = 0;
//	for (i = 0; i < (waypoints.length - 1); i++)
//	{
//		distances[i] = Math.sqrt (
//			(waypoints[i+1].x - waypoints[i].x) * (waypoints[i+1].x - waypoints[i].x) +
//			(waypoints[i+1].y - waypoints[i].y) * (waypoints[i+1].y - waypoints[i].y) +
//			(waypoints[i+1].z - waypoints[i].z) * (waypoints[i+1].z - waypoints[i].z));
//		totalDistance += distances[i];
//		pointIndicesAccumulator[i]= i;
//	}
//	tracePrint ('distances       =' + distances + ' meters');
//	if (outputInitializationComputations)
//	    forcePrint ('totalDistance   =' + Math.round (totalDistance * 10)/10 + ' meters');
//	pointIndicesAccumulator[waypoints.length - 1]= waypoints.length - 1;
//	pointIndicesAccumulator[waypoints.length]    = -1;
//
//	for (i = 0; i < (waypoints.length ); i++)
//	{
//		verticalDropLineIndicesAccumulator[3*i]    = 2*i;
//		verticalDropLineIndicesAccumulator[3*i+ 1] = 2*i + 1;
//		verticalDropLineIndicesAccumulator[3*i+ 2] = -1;
//		verticalDropLinePointsAccumulator[2*i]     = waypoints[i];
//		verticalDropLinePointsAccumulator[2*i+1]   = new SFVec3f(waypoints[i].x, 0.0, waypoints[i].z);
//	}
//	pointIndices = pointIndicesAccumulator;
//	tracePrint ('pointIndices    =' + pointIndices);
//	verticalDropLineIndices = verticalDropLineIndicesAccumulator;
//	tracePrint ('verticalDropLineIndices  =' + verticalDropLineIndices );
//	verticalDropLinePoints = verticalDropLinePointsAccumulator;
//	tracePrint ('verticalDropLinePoints =' + verticalDropLinePoints);
//
//	totalDurationAccumulator = 0.0;
//	for (i = 0; i < (waypoints.length - 1); i++)
//	{
//		if      (useDefaultSpeed)
//		{
//			totalDurationAccumulator += distances[i] / defaultSpeed;
//		}
//		else if (useLegSpeeds)
//		{
//			totalDurationAccumulator += distances[i] / legSpeeds[i];
//		}
//		else //  useLegDurations
//		{
//			totalDurationAccumulator += legDurations[i];
//		//	forcePrint ('legDurations[' + i + ']=' + legDurations[i]);
//		//	forcePrint ('totalDurationAccumulator=' + totalDurationAccumulator + ' seconds');
//		}
//	}
//	totalDuration = totalDurationAccumulator; // send eventOut
//	hours   = Math.floor  (totalDuration / 3600.0); // % is modulo operator, provides remainder
//	minutes = Math.floor ((totalDuration - hours * 3600) / 60.0);
//	seconds = Math.round ((totalDuration - hours * 3600 - minutes * 60) * 10) / 10; // 0.1 sec resolution
//	if (totalDuration <= 0)
//	{
//		forcePrint ('*** error:  totalDuration=' + totalDuration + ' seconds (' +
//	  	  hours + ' hours,' + minutes + ' minutes,' + seconds + ' seconds)');
//		scriptError=true;
//		return;
//	}
//	else if (outputInitializationComputations)
//	    	 forcePrint ('totalDuration   =' + Math.round (totalDuration * 10)/10 + ' seconds (' +
//	  	 		hours + ' hours,' + minutes + ' minutes,' + seconds + ' seconds)');
//
//	positionKey[0] = 0;
//	for (i = 1; i < waypoints.length; i++)
//	{
//		if      (useDefaultSpeed)
//		{
//			positionKey[i] = i / (waypoints.length - 1); // simple fraction
//		}
//		else if (useLegSpeeds)
//		{
//			positionKey[i] = ((distances[i-1] / legSpeeds[i-1]) / totalDuration) + positionKey[i-1];
//		}
//		else //  useLegDurations
//		{
//			positionKey[i] = (legDurations[i-1] / totalDuration) + positionKey[i-1];
//		}
//	}
//	positionKey[waypoints.length-1] = 1.0; // avoid roundup greater than 1.0
//
//	tracePrint ('positionKey.length           =' + positionKey.length);
//	tracePrint ('positionKey                  =' + positionKey);
//	tracePrint ('positionKeyValueArray.length =' + positionKeyValueArray.length);
//	tracePrint ('positionKeyValueArray        =' + positionKeyValueArray);
//
//	WaypointPI.key      = positionKey;
//	WaypointPI.keyValue = positionKeyValueArray;
//
//	tracePrint ('pitchUpDownForVerticalWaypoints=' + pitchUpDownForVerticalWaypoints);
//
//	// different approaches to orientation calculations
//	whichRotationVersion ='FirstHeadingThenPitchStayVertical';
//				//'IndependentLegOrientations';
//				//'RelativeLegOrientations';
//				//'FirstHeadingThenPitchStayVertical';
//	tracePrint ('whichRotationVersion=' + whichRotationVersion);
//	var orientations    = new MFRotation ();
//	// SFRotation constructor for two Vector3Arrays returns rotation from first to second
//	// default body axis is along X axis
//	orientations[0] = new SFRotation (new SFVec3f (1, 0, 0),
//		waypoints[1].subtract(waypoints[0]).normalize()); // first leg
//	var dx = waypoints[1].x - waypoints[0].x;
//	var dy = waypoints[1].y - waypoints[0].y;
//	var dz = waypoints[1].z - waypoints[0].z;
//	var distance      = Math.sqrt (dx*dx + dy*dy + dz*dz);
//	levelDistance = Math.sqrt (dx*dx + dz*dz);
//	tracePrint (' dx=' + dx + ', dy=' + dy + ', dz=' + dz + ', distance=' + distance + ', levelDistance=' + levelDistance);
//	tracePrint (' orientations[0] =' + orientations[0]);
//
//	for (i = 1; i < (waypoints.length - 1); i++)
//	{
//		dx = waypoints[i+1].x - waypoints[i].x;
//		dy = waypoints[i+1].y - waypoints[i].y;
//		dz = waypoints[i+1].z - waypoints[i].z;
//		distance      = Math.sqrt (dx*dx + dy*dy + dz*dz);
//		levelDistance = Math.sqrt (dx*dx + dz*dz);
//		tracePrint (' dx=' + dx + ', dy=' + dy + ', dz=' + dz +
//		', distance='      + Math.round (     distance*10)/10 +
//		', levelDistance=' + Math.round (levelDistance*10)/10);
//
////		tracePrint ('waypoints[i  ].subtract(waypoints[i-1]) =' + waypoints[i  ].subtract(waypoints[i-1]));
////		tracePrint ('waypoints[i+1].subtract(waypoints[i])   =' + waypoints[i+1].subtract(waypoints[i]));
////		tracePrint ('dot product=' + waypoints[i+1].subtract(waypoints[i]).normalize().
////					 dot(waypoints[i].subtract(waypoints[i-1]).normalize()));
//		switch (whichRotationVersion)
//		{
//		  case'IndependentLegOrientations':
//			// buggy: can twist/roll unpredictably about relative-x axis
//			// apparently a CosmoPlayer bug in SFRotation constructor when pointing (-1, 0, 0)
//			orientations[i] = new SFRotation (
//				new SFVec3f (1, 0, 0),
//				waypoints[i+1].subtract(waypoints[i]).normalize());
//
//		  case'RelativeLegOrientations':
//			orientations[i] = new SFRotation (
//				waypoints[i  ].subtract(waypoints[i-1]).normalize(),
//				waypoints[i+1].subtract(waypoints[i]).normalize());
//			// orientation multiplication (i.e. composition) is order dependent
//			orientations[i] = orientations[i-1].multiply (orientations[i]); // relative to previous leg
//
//		  case'FirstHeadingThenPitchStayVertical':
//			if ( (distance == 0.0) ||
//			    ((levelDistance == 0.0) && (pitchUpDownForVerticalWaypoints == false)))
//			{
//				if (distance == 0.0)
//					tracePrint ('...staying in one place');
//				else
//					tracePrint ('...maintaining orientation during vertical motion');
//				orientations[i] = orientations[i-1];
//				break;
//			}
//			else if (levelDistance == 0.0)  // pitch up/down along vertical axis
//			{
//				// still twisting about roll axis, unfortunately...
//				if (waypoints[i+1].y > waypoints[i].y)  // or test dy
//				{
//					tracePrint ('...pitching up vertical axis');
//					orientations[i] = new SFRotation (
//						waypoints[i].subtract(waypoints[i-1]).normalize(),
//						new SFVec3f (0, 1, 0));  // relative
//				}
//				else
//				{
//					tracePrint ('...pitching down vertical axis');
//					orientations[i] = new SFRotation (
//						waypoints[i].subtract(waypoints[i-1]).normalize(),
//						new SFVec3f (0, -1, 0));  // relative
//				}
//				orientations[i] = orientations[i-1].multiply (orientations[i]); // relative to previous leg
//			}
//			else // carefully rotate about Y axis then pitch up/down to avoid unpredictable twists/rolls
//			{
//				var heading = Math.atan2 (dz, dx); // atan2 returns arctangent in any of 4 quadrants
//				orientations[i] = new SFRotation (0, 1, 0, -heading); // note negation
//				// can go vertical if preferred, levelDistance == 0 cases handled above
//				var pitchAngle  = Math.atan (dy / levelDistance); // negative angle should pitch down, note no negation
//				// orientation multiplication (i.e. composition) is order dependent
//				// !! this is the step that causes a Cosmo/Cortona sign error !!
//				// it is due to opposite responses to multiplication order.
//				tempHold = orientations[i];  // not assuming that browser self-multiplication is safe
//				if (Browser.name=='CosmoPlayer') // reverse multiplication order for old browser
//					orientations[i] = (new SFRotation (0, 0, 1, pitchAngle)).multiply (tempHold); // mod heading
//				else	orientations[i] = tempHold.multiply (new SFRotation (0, 0, 1, pitchAngle));   // mod heading
//				tracePrint (' heading='    + Math.round (degrees (heading)   *10)/10 + ' degrees,' +
//					   ' pitchAngle=' + Math.round (degrees (pitchAngle)*10)/10 + ' degrees');
//			}
//		}
//		tracePrint ('orientations[' + i + '] =' + orientations[i]);
//	}
//	// full array trace
//	tracePrint ('orientations   =' + orientations);
//
////	traceEnabled = saveTrace;
//
//	if (turningRate < 0)
//	{
//		forcePrint (' ** error:  negative value for turningRate illegal, making turningRate positive');
//		turningRate = -turningRate;
//	}
//	tracePrint ('turningRate     =' + turningRate + ' degrees/second');
//
//	var orientationKey = new MFFloat ();
//	orientationKey[0] = 0;
//	for (i = 1; i < (waypoints.length-1); i++)
//	{
//		deltaAngle = orientations[i].multiply(orientations[i-1].inverse()).angle;
//		deltaAngle = normalizePi (deltaAngle);
//		turnTime = Math.abs (deltaAngle) / radians (turningRate);
//		tracePrint ('deltaAngle[' + i + ']=' + degrees (deltaAngle) + ' degrees, turnTime=' + turnTime);
//
//		precedingLegDuration = (positionKey[i]   - positionKey[i-1]) * totalDuration;
//		followingLegDuration = (positionKey[i+1] - positionKey[i]  ) * totalDuration;
//		// turn for no more than 1/3 of preceding or following leg durations, respectively
//		precedingTurnKeyOffset = Math.min (turnTime/2, precedingLegDuration/3) / totalDuration;
//		followingTurnKeyOffset = Math.min (turnTime/2, followingLegDuration/3) / totalDuration;
//		tracePrint ('precedingTurnKeyOffset=' + (precedingTurnKeyOffset * totalDuration) + ' seconds');
//		tracePrint ('followingTurnKeyOffset=' + (followingTurnKeyOffset * totalDuration) + ' seconds');
//
//		orientationKey[3*i - 2] = positionKey[i] - precedingTurnKeyOffset;
//		orientationKey[3*i - 1] = positionKey[i];
//		orientationKey[3*i]     = positionKey[i] + followingTurnKeyOffset;
//		if (orientationKey[3*i - 2] <= positionKey[i-1]) // interpolate preceding key if needed
//		{
//			orientationKey[3*i - 2] = positionKey[i-1] + ((positionKey[i] - positionKey[i-1]) * 2 / 3);
//		}
//		if (orientationKey[3*i] >= positionKey[i+1]) // interpolate following key if needed
//		{
//			orientationKey[3*i]     = positionKey[i] + ((positionKey[i+1] - positionKey[i])   * 1 / 3);
//		}
//		if ((orientationKey[3*i - 2] > orientationKey[3*i - 1]) || (orientationKey[3*i - 1] > orientationKey[3*i]))
//		{
//			forcePrint (' ** error computing orientationKey [' + (3*i - 2) + '..' + (3*i) + ']');
//		}
//	}
//	orientationKey[3*(waypoints.length-1)-2] = 1.0; // avoid roundup greater than 1
//	tracePrint ('orientationKey.length =' + orientationKey.length);
//	tracePrint ('orientationKey        =' + orientationKey);
//
//	// check orientationKey
//	for (i = 2; i < (orientationKey.length-1); i++)
//	{
//	   if (orientationKey [i-1] > orientationKey [i])
//		forcePrint ('*** error,' +
//		'orientationKey [' + (i-1) + ']=' + orientationKey [i-1] + ',' +
//		'orientationKey [' + (i) + ']='   + orientationKey [i] +
//		' values are not monotonically increasing ***');
//	   if ((orientationKey [i] < 0) || (orientationKey [i] > 1))
//		forcePrint ('*** error, orientationKey [' + i + ']=' + orientationKey [i] +
//		' value is out of range [0..1] ***');
//	}
//	orientationKeyValueArray = new MFRotation ();
//	orientationKeyValueArray[0] = orientations[0];
//	orientationKeyValueArray[1] = orientations[0];
//	for (i = 1; i < (waypoints.length - 1); i++)
//	{
//	//	spherical linear interpolation (slerp) 0.5 interpolates halfway between adjacent orientations
//		orientationKeyValueArray[3*i - 1] = orientations[i-1].slerp(orientations[i], 0.5);
//		orientationKeyValueArray[3*i]     = orientations[i];
//		orientationKeyValueArray[3*i + 1] = orientations[i]; // straight-line track, same orientation
//	}
//	tracePrint ('orientationKeyValueArray.length =' + orientationKeyValueArray.length);
//	tracePrint ('orientationKeyValueArray        =' + orientationKeyValueArray);
//
//	// eliminate orientationKey triplicates (smaller arrays overcome CosmoPlayer overflow bug)
//	var newKey      = new MFFloat ();
//	newKey      [0] = orientationKey [0];
//	newKey      [1] = orientationKey [1];
//	var newKeyValue = new MFRotation ();
//	newKeyValue [0] = orientationKeyValueArray [0];
//	newKeyValue [1] = orientationKeyValueArray [1];
//	index = 2; // keep first two orientations identical, index is for next value
//        for (i = 2; i < (orientationKeyValueArray.length-3) ; i++)
//	{
//	   dotProductBA      =  orientationKeyValueArray [i-1].getAxis().dot(orientationKeyValueArray [i-2].getAxis());
//	   dotProductCB      =  orientationKeyValueArray [i].getAxis().dot(orientationKeyValueArray [i-1].getAxis());
//	   angleDifferenceBA = normalizePi(
//	   	normalize2Pi (orientationKeyValueArray [i-1].angle) -
//	   	normalize2Pi (orientationKeyValueArray [i-2].angle)) * 180 / Math.PI;
//	   angleDifferenceCB = normalizePi(
//	   	normalize2Pi (orientationKeyValueArray [i].angle) -
//	   	normalize2Pi (orientationKeyValueArray [i-1].angle)) * 180 / Math.PI;
//
//	   if (i < 10) // too many outputs clobbers the trace console
//	   {
// 	     tracePrint ('orientationKeyValueArray [' + (i-2) + ']=' + orientationKeyValueArray [i-2]);
// 	     tracePrint ('orientationKeyValueArray [' + (i-1) + ']=' + orientationKeyValueArray [i-1]);
//    	     tracePrint ('orientationKeyValueArray [' + (i  ) + ']=' + orientationKeyValueArray [i  ]);
//	     tracePrint ('dotProductBA     =' + dotProductBA +     ', dotProductCB     =' + dotProductCB);
//	     tracePrint ('angleDifferenceBA=' + angleDifferenceBA + ', angleDifferenceBC=' + angleDifferenceCB + ' degrees');
//	   }
//
////         // depth check also needed!  but positionKey is already optimized/compressed, so how to check?
////	   if ((Math.abs (dotProductCB - 1)  < 0.01) &&
////	       (Math.abs (dotProductBA - 1)  < 0.01) &&
////	       (Math.abs (angleDifferenceCB) < 1.0 ) &&
////	       (Math.abs (angleDifferenceBA) < 1.0 ))  // degrees
////	   {
////		// replace key time with later value
////		tracePrint ('... matching this orientationKey time,' +
////		'updating key' + newKey [index-1] + ' to' + orientationKey [i]);
////		newKey      [index-1] = orientationKey [i];
////		// don't update orientation in order to avoid creeping matches
////	   }
////	   else
////	   {
//		newKey      [index] = orientationKey [i];
//		newKeyValue [index] = orientationKeyValueArray [i];
//		index ++;
//		tracePrint ('...  keeping this orientationKeyValue');
////	   }
//	   if (newKey [index-2] > newKey [index-1])
//		forcePrint ('*** error,' +
//		'newKey [' + (index-2) + ']=' + newKey [index-2] + ',' +
//		'newKey [' + (index-1) + ']=' + newKey [index-1] +
//		' values are not monotonically increasing ***');
//	   if ((newKey [index-1] < 0) || (newKey [index-1] > 1))
//		forcePrint ('*** error, newKey [' + (index-1) + ']=' + newKey [index-1] +
//		' value is out of range [0..1] ***');
//	}
//	newKey      [index] = orientationKey [orientationKeyValueArray.length-2]; // match finals values
//	newKeyValue [index] = orientationKeyValueArray [orientationKeyValueArray.length-2];
//	index++;
//	newKey      [index] = orientationKey [orientationKeyValueArray.length-1]; // match finals values
//	newKeyValue [index] = orientationKeyValueArray [orientationKeyValueArray.length-1];
//	tracePrint ('orientation newKey.length      =' + newKey.length);
//	tracePrint ('orientation newKey             =' + newKey);
//	tracePrint ('orientation newKeyValue.length =' + newKeyValue.length);
//	tracePrint ('orientation newKeyValue        =' + newKeyValue);
//
//	WaypointOI.key      = newKey;
//	WaypointOI.keyValue = newKeyValue;
//
//	tracePrint ('labelDisplayMode=' + labelDisplayMode);
//	if (labelDisplayMode.toLowerCase() =='waypoints')
//	{
//	  // create text labels for each waypoint
//	  var outputChild = new MFNode ();
//	  outputVrmlString ='';
//	  for (i = 0; i < waypoints.length; i++)
//	  {
//		textOffset = waypoints[i].add(labelOffset);
//		if ((i == waypoints.length-1) && (waypoints[i].x == waypoints[0].x) &&
//			(waypoints[i].y == waypoints[0].y) && (waypoints[i].z == waypoints[0].z))
//		    // double offset for endpoint when waypoints are a loop
//		    textOffset = textOffset.subtract(new SFVec3f (0, 3 * labelFontSize, 0));
//		hours   = Math.floor  (totalDuration * positionKey[i] / 3600.0); // % is modulo operator, provides remainder
//		minutes = Math.floor ((totalDuration * positionKey[i] - hours * 3600.0) / 60.0);
//		seconds = Math.round  (totalDuration * positionKey[i] - hours * 3600.0 - minutes * 60.0);
//		while (minutes >= 60)
//		{
//			minutes -= 60;
//			hours   += 1;
//		}
//		while (seconds >= 60)
//		{
//			seconds -= 60;
//			minutes += 1;
//		}
//		if (hours   < 10) hours   ='0' + hours;
//		if (minutes < 10) minutes ='0' + minutes;
//		if (seconds < 10) seconds ='0' + seconds;
//		locationX =  Math.round (waypoints[i].x);
//		depth     = -Math.round (waypoints[i].y * 10) / 10;
//		locationZ =  Math.round (waypoints[i].z);
//		if      (heightLabel.toLowerCase()=='altitude')
//			depthString = (-depth) + ' ';
//		else if (heightLabel.toLowerCase()=='depth')
//			depthString = depth + ' ';
//		else if (heightLabel.toLowerCase()=='none')
//			depthString =' ';
//		else	depthString =' ';
//		outputVrmlString +=
//			 'Transform { translation' + textOffset + '\n'
//			+ ' children LOD { range [' + 150 * labelFontSize + ' ]\n'
//			+ '  level [\n'
//			+ '   Billboard { axisOfRotation 0 1 0 \n'
//			+ '    children Shape {\n'
//			+ '	geometry Text {\n'
//			+ '	   string [ \"' + hours + ':' + minutes + ':' + seconds + '\"\n'
//			+ '	            \"' + locationX + ' ' + depthString +  locationZ + ' ' + '\" ]\n'
//			+ '	   fontStyle DEF WPIFontStyle FontStyle {\n'
//			+ '		size' + labelFontSize + '\n'
//			+ '		justify [ \"MIDDLE\" \"MIDDLE\" ]\n'
//			+ '	   }\n'
//			+ '	}\n'
//			+ '	appearance DEF WPIAppearance Appearance {\n'
//			+ '	   material Material { diffuseColor' + labelColor + ' }\n'
//			+ '	}\n'
//			+ '    }\n'
//			+ '   }\n'
//			+ '  WorldInfo { } ]\n'
//			+ ' }\n'
//			+ '}\n';
//	  }
//	  tracePrint ('outputVrmlString=' + outputVrmlString);
//	  outputChild = Browser.createVrmlFromString (outputVrmlString);
//	  OutputLabelsGroup.addChildren = outputChild;
////	  tracePrint ('OutputLabelsGroup.children =');
////	  tracePrint ('  ' + outputChild + '  ' + OutputLabelsGroup.children.toString());
//	}
//	else if (labelDisplayMode.toLowerCase() =='interpolation')
//	{
//		// updates occur when fraction changes
//	}
//	else if ((labelDisplayMode.toLowerCase() !='none') && (labelDisplayMode !=''))
//	{
//	  forcePrint ('*** illegal value labelDisplayMode=' + labelDisplayMode + ', ignored');
//	}
//}
//
//function set_fraction (fractionValue, timeStamp)
//{
////	tracePrint ('fractionValue=' + fractionValue);
////	tracePrint ('previousFractionIndex=' + previousFractionIndex);
//
//	if (scriptError==true) return;
//
////	wide input range supported by interpolators,
////	usually no range check on fractionValue.
////	however WaypointInterpolator input range is [0..1], so check
//	if ((fractionValue < 0) || (fractionValue > 1))
//	{
//		forcePrint ('*** error:  set_fraction=' + fractionValue + ' out of range [0..1], ignored');
//		return;
//	}
//
//	if (previousFractionIndex == -1)
//	{
//		previousFractionIndex = 0; // start
//		while (fractionValue >= positionKey[previousFractionIndex+1])
//		{
//			previousFractionIndex ++;
//			if (previousFractionIndex >= waypoints.length - 2) break;
//		}
//		highlightCoordinates = new MFVec3f (waypoints[previousFractionIndex],
//			waypoints[previousFractionIndex +1]);
//		tracePrint ('highlightCoordinates=' + highlightCoordinates);
//	}
//	else if (waypoints.length == 2)
//	{
//		// only one segment, no action required
//	}
//	else if (previousFractionIndex == waypoints.length - 2) // last leg
//	{
//	  if (fractionValue < positionKey[previousFractionIndex]) // looped
//	  {
//		previousFractionIndex = 0; // start
//		while (fractionValue >= positionKey[previousFractionIndex+1])
//		{
//			previousFractionIndex ++;
//			if (previousFractionIndex >= waypoints.length - 2) break;
//		}
//		highlightCoordinates = new MFVec3f (waypoints[previousFractionIndex],
//			waypoints[previousFractionIndex +1]);
//		tracePrint ('highlightCoordinates=' + highlightCoordinates);
//	  }
//	}
//	else if (fractionValue >= positionKey[previousFractionIndex+1])
//	{
//		previousFractionIndex++;
//		while (fractionValue >= positionKey[previousFractionIndex+1])
//		{
//			previousFractionIndex ++;
//			if (previousFractionIndex >= waypoints.length - 2) break;
//		}
//		if (previousFractionIndex > waypoints.length - 2) previousFractionIndex = 0;
//		highlightCoordinates = new MFVec3f (
//			waypoints[previousFractionIndex],
//			waypoints[previousFractionIndex+1]);
//		tracePrint ('highlightCoordinates=' + highlightCoordinates);
//	}
//	// else previousFractionIndex ought to be OK
//
//	if (labelDisplayMode =='interpolation')
//	{
//		hours   = Math.floor  (totalDuration * fractionValue / 3600.0); // % is modulo operator, provides remainder
//		minutes = Math.floor ((totalDuration * fractionValue - hours * 3600) / 60.0);
//		seconds = Math.round  (totalDuration * fractionValue - hours * 3600 - minutes * 60);
//		while (minutes > 60)
//		{
//			minutes -= 60;
//			hours   += 1;
//		}
//		while (seconds > 60)
//		{
//			seconds -= 60;
//			minutes += 1;
//		}
//		if (hours   < 10) hours   ='0' + hours;
//		if (minutes < 10) minutes ='0' + minutes;
//		if (seconds < 10) seconds ='0' + seconds;
//
//		// compute course and pitch
//		currentAxis     = WaypointOI.value_changed.getAxis().normalize();
//		currentRotation = WaypointOI.value_changed;
//	//	forcePrint ('=====currentRotation=' + currentRotation);
//
//		var rotatedVector = currentRotation.multVec (new SFVec3f (1, 0, 0)); // rotate x-centered body
//		dx = rotatedVector.x;
//		dy = rotatedVector.y;
//		dz = rotatedVector.z;
//		levelDistance = Math.sqrt (dx*dx + dz*dz);
//		var heading = Math.atan2 (dz, dx); // atan2 returns arctangent in any of 4 quadrants
//		var pitchAngle;
//		if (levelDistance > 0)
//			pitchAngle =  Math.atan (dy / levelDistance); // negative angle should pitch down, note no negation
//		else if (dy > 0)
//			pitchAngle =  1.57;
//		else    pitchAngle = -1.57;
//
//	//	forcePrint (' rotatedVector=' + rotatedVector);
//	//	forcePrint (' heading=' + degrees(heading) + ', pitchAngle=' + degrees(pitchAngle));
//
//		course = Math.round (normalize2Pi ( heading)    * 180 / Math.PI);
//		pitch  = Math.round (normalizePi  ( pitchAngle) * 180 / Math.PI);
//		// format angles in degrees
//		if      (course <  10) course ='00' + course;
//		else if (course < 100) course = '0' + course;
//
//		tracePrint (' course=' + course + ', pitch=' + pitch);
//
//		locationX =  Math.round (WaypointPI.value_changed.x);
//		depth     = -Math.round (WaypointPI.value_changed.y * 10) / 10;
//		locationZ =  Math.round (WaypointPI.value_changed.z);
//		if      (heightLabel.toLowerCase()=='altitude')
//			depthString =', altitude' + (-depth) + 'm';
//		else if (heightLabel.toLowerCase()=='depth')
//			depthString =', depth'    + depth + 'm';
//		else if (heightLabel.toLowerCase()=='none')
//			depthString ='';
//		else	depthString ='';
//	  	var labelInterpolation  = new MFString (
//			description,
//			(hours + ':' + minutes + ':' + seconds + ', course' + course + ', pitch' + pitch),
//			('location' + locationX + ' ' + locationZ + depthString));
//		tracePrint ('labelInterpolation=' + labelInterpolation);
//	}
//	return;
//}
//
