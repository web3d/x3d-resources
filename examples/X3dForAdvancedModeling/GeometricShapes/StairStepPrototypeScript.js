// StairStepPrototypeScript.js

// diffuseColor SFColor inputOutput; default value 0.8 0.8 0.6
// scale SFVec3f inputOutput; size of step
// numberOfSteps SFInt32 inputOutput; TODO
// traceEnabled SFBool inputOutput; debug trace to Browser output console
// computedPoints MFVec3f outputOnly
// computedCoordIndex MFInt32 outputOnly

// ================== Trace output functions ==================

function tracePrint (outputString)
{
   // if traceEnabled is true, print outputString on X3D browser console
   if (traceEnabled)
   {
       Browser.println ('[StairStepPrototypeScript: ' + outputString + ']');
   }
}
function alwaysPrint (outputString)
{
      // always print outputString on X3D browser console
      Browser.println ('[StairStepPrototypeScript: ' + outputString + ']');
}
function set_traceEnabled (eventValue)
{
      // input eventValue received for inputOutput field
      traceEnabled = eventValue;
}
// ===========================================================

function set_diffuseColor (eventValue)
{
    // input eventValue received for inputOutput field diffuseColor
    diffuseColor = eventValue;
    tracePrint ('diffuseColor = ' + diffuseColor);
}
function set_scale (eventValue)
{
    // input eventValue received for inputOutput field scale
    scale = eventValue;
    tracePrint ('scale = ' + scale);
}

function set_numberOfSteps (eventValue) {
    // input eventValue received for inputOutput field numberOfSteps
    
    var i, n, d, s; // index variables
    
    var pointsFirst = [0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1]; // first step coordinate points
 
    /* arrays to store points of new steps */
    var points  = [ ];
    var points1 = [ ];
    var points2 = [ ];
    var points3 = [ ];
    var points4 = [ ];
    var points5 = [ ];
    var points6 = [ ];
    var points7 = [ ];
    var points8 = [ ];
    var points9 = [ ];
    var points10= [ ];

    alwaysPrint ('trace progress 1');
    for (i = 0; i  < 10; i++) {
        alwaysPrint ('trace progress 2: i=' + i);
        if ( i < eventValue  ) {
            for (n = 0; n  < 30; n++) {
                if (n == 0 || n == 3 || n == 6 || n == 9 || n == 12 || n == 15 || n == 18 || n == 21 || n == 24 || n == 27) { 
                    points[n] = pointsFirst[n] ; //width of the steps never changes
                } else {
                    points[n] = pointsFirst[n] + i ;	 
                }  
            }
        } else {
            for (n = 0; n  < 30; n++) {
               points[n] = 0 ; // fills remening points, from numberOfSteps to 10; this saves lines of the script code in the final computedPoints array  
            }
        }
        /* Store points in the proper array */
        if (i==0) {
            for (d = 0; d <30; d++) {
                points1[d] = points[d];
            }
        } else if (i==1) {
            for (d = 0; d <30; d++) {
                points2[d] = points[d];
            } 
        } else if (i==2) {
            for (d = 0; d <30; d++) {
                points3[d] = points[d];
            }  
        } else if (i==3) {
            for (d = 0; d <30; d++) {
                points4[d] = points[d];
            }    
        } else if (i==4) {
            for (d = 0; d <30; d++) {
                points5[d] = points[d];
            } 
        } else if (i==5) {
            for (d = 0; d <30; d++) {
                points6[d] = points[d];
            } 
        } else if (i==6) {
            for (d = 0; d <30; d++) {
                points7[d] = points[d];
            } 
        } else if (i==7) {
            for (d = 0; d <30; d++) {
                points8[d] = points[d];
            } 
        } else if (i==8) {
            for (d = 0; d <30; d++) {
                points9[d] = points[d];
            }   
        } else if (i==9) {
            for (d = 0; d <30; d++) {
                points10[d] = points[d];
            }  
        }  
    }
        
    var coordFirst = [0, 1, 2, 3, -1, 4, 5, 6, 7, -1, 0, 1, 5, 4, -1, 1, 2, 6, 5, -1, 0, 3, 7, 4, -1, 3, 2, 6, 7, -1]; // first step coordinate index
    
     /* arrays to store coordinate indexes of new steps */
    var coord  = [ ];
    var coord1 = [ ];
    var coord2 = [ ];
    var coord3 = [ ];
    var coord4 = [ ];
    var coord5 = [ ];
    var coord6 = [ ];
    var coord7 = [ ];
    var coord8 = [ ];
    var coord9 = [ ];
    var coord10= [ ];

    for (s =0; s  <= eventValue; s++) {
        for ( n = 0; n  < 30; n++) {
            alwaysPrint ('trace progress 3: s=' + s + ', n=' + n);
            if (n == 4 || n == 9 || n == 14 || n == 19 || n == 24 || n == 29) { 
                coord[n] = coordFirst[n] ; // -1 values
            } else {
 	 	coord[n] = coordFirst[n] + 8*s ; 
            }  
        }
        
        /* Store coordinate indexes in the proper array */
        if (s==0) {
            for (d = 0; d <30; d++) {
                coord1[d] = coord[d];
            }
        } else if (s==1) {
            for (d = 0; d <30; d++) {
                coord2[d] = coord[d];
            } 
        } else if (s==2) {
            for (d = 0; d <30; d++) {
                coord3[d] = coord[d];
            }  
        } else if (s==3) {
            for (d = 0; d <30; d++) {
                coord4[d] = coord[d];
            }    
        } else if (s==4) {
            for (d = 0; d <30; d++) {
                coord5[d] = coord[d];
            } 
        } else if (s==5) {
            for (d = 0; d <30; d++) {
                coord6[d] = coord[d];
            } 
        } else if (s==6) {
            for (d = 0; d <30; d++) {
                coord7[d] = coord[d];
            } 
        } else if (s==7) {
            for (d = 0; d <30; d++) {
                coord8[d] = coord[d];
            } 
        } else if (s==8) {
            for (d = 0; d <30; d++) {
                coord9[d] = coord[d];
            }   
        } else if (s==9) {
            for (d = 0; d <30; d++) {
                coord10[d] = coord[d];
            }  
        }  
    }
    
   /* output values */
    computedPoints  = new MFVec3f (new SFVec3f(points1[0], points1[1], points1[2]), new SFVec3f(points1[3], points1[4], points1[5]), new SFVec3f(points1[6], points1[7], points1[8]), new SFVec3f(points1[9], points1[10], points1[11]), new SFVec3f(points1[12], points1[13], points1[14]), new SFVec3f(points1[15], points1[16], points1[17]), new SFVec3f(points1[18], points1[19], points1[20]), new SFVec3f(points1[21], points1[22], points1[23]),
                                   new SFVec3f(points2[0], points2[1], points2[2]), new SFVec3f(points2[3], points2[4], points2[5]), new SFVec3f(points2[6], points2[7], points2[8]), new SFVec3f(points2[9], points2[10], points2[11]), new SFVec3f(points2[12], points2[13], points2[14]), new SFVec3f(points2[15], points2[16], points2[17]), new SFVec3f(points2[18], points2[19], points2[20]), new SFVec3f(points2[21], points2[22], points2[23]),
                                   new SFVec3f(points3[0], points3[1], points3[2]), new SFVec3f(points3[3], points3[4], points3[5]), new SFVec3f(points3[6], points3[7], points3[8]), new SFVec3f(points3[9], points3[10], points3[11]), new SFVec3f(points3[12], points3[13], points3[14]), new SFVec3f(points3[15], points3[16], points3[17]), new SFVec3f(points3[18], points3[19], points3[20]), new SFVec3f(points3[21], points3[22], points3[23]),
                                   new SFVec3f(points4[0], points4[1], points4[2]), new SFVec3f(points4[3], points4[4], points4[5]), new SFVec3f(points4[6], points4[7], points4[8]), new SFVec3f(points4[9], points4[10], points4[11]), new SFVec3f(points4[12], points4[13], points4[14]), new SFVec3f(points4[15], points4[16], points4[17]), new SFVec3f(points4[18], points4[19], points4[20]), new SFVec3f(points4[21], points4[22], points4[23]),
                                   new SFVec3f(points5[0], points5[1], points5[2]), new SFVec3f(points5[3], points5[4], points5[5]), new SFVec3f(points5[6], points5[7], points5[8]), new SFVec3f(points5[9], points5[10], points5[11]), new SFVec3f(points5[12], points5[13], points5[14]), new SFVec3f(points5[15], points5[16], points5[17]), new SFVec3f(points5[18], points5[19], points5[20]), new SFVec3f(points5[21], points5[22], points5[23]),
                                   new SFVec3f(points6[0], points6[1], points6[2]), new SFVec3f(points6[3], points6[4], points6[5]), new SFVec3f(points6[6], points6[7], points6[8]), new SFVec3f(points6[9], points6[10], points6[11]), new SFVec3f(points6[12], points6[13], points6[14]), new SFVec3f(points6[15], points6[16], points6[17]), new SFVec3f(points6[18], points6[19], points6[20]), new SFVec3f(points6[21], points6[22], points6[23]),
                                   new SFVec3f(points7[0], points7[1], points7[2]), new SFVec3f(points7[3], points7[4], points7[5]), new SFVec3f(points7[6], points7[7], points7[8]), new SFVec3f(points7[9], points7[10], points7[11]), new SFVec3f(points7[12], points7[13], points7[14]), new SFVec3f(points7[15], points7[16], points7[17]), new SFVec3f(points7[18], points7[19], points7[20]), new SFVec3f(points7[21], points7[22], points7[23]),
                                   new SFVec3f(points8[0], points8[1], points8[2]), new SFVec3f(points8[3], points8[4], points8[5]), new SFVec3f(points8[6], points8[7], points8[8]), new SFVec3f(points8[9], points8[10], points8[11]), new SFVec3f(points8[12], points8[13], points8[14]), new SFVec3f(points8[15], points8[16], points8[17]), new SFVec3f(points8[18], points8[19], points8[20]), new SFVec3f(points8[21], points8[22], points8[23]),
                                   new SFVec3f(points9[0], points9[1], points9[2]), new SFVec3f(points9[3], points9[4], points9[5]), new SFVec3f(points9[6], points9[7], points9[8]), new SFVec3f(points9[9], points9[10], points9[11]), new SFVec3f(points9[12], points9[13], points9[14]), new SFVec3f(points9[15], points9[16], points9[17]), new SFVec3f(points9[18], points9[19], points9[20]), new SFVec3f(points9[21], points9[22], points9[23]),
                                   new SFVec3f(points10[0], points10[1], points10[2]), new SFVec3f(points10[3], points10[4], points10[5]), new SFVec3f(points10[6], points10[7], points10[8]), new SFVec3f(points10[9], points10[10], points10[11]), new SFVec3f(points10[12], points10[13], points10[14]), new SFVec3f(points10[15], points10[16], points10[17]), new SFVec3f(points10[18], points10[19], points10[20]), new SFVec3f(points10[21], points10[22], points10[23]));

    alwaysPrint ('trace progress 4, eventValue=' + eventValue);
    if (eventValue == 1) {
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]));
    } else if (eventValue == 2){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]));
    } else if (eventValue == 3){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29])); 
    } else if (eventValue == 4){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]),
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29])); 
    } else if (eventValue == 5){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]),
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29]),
                                         new SFInt32(coord5[0]), new SFInt32(coord5[1]), new SFInt32(coord5[2]), new SFInt32(coord5[3]), new SFInt32(coord5[4]), new SFInt32(coord5[5]), new SFInt32(coord5[6]), new SFInt32(coord5[7]), new SFInt32(coord5[8]), new SFInt32(coord5[9]), new SFInt32(coord5[10]), new SFInt32(coord5[11]), new SFInt32(coord5[12]), new SFInt32(coord5[13]), new SFInt32(coord5[14]), new SFInt32(coord5[15]), new SFInt32(coord5[16]), new SFInt32(coord5[17]), new SFInt32(coord5[18]), new SFInt32(coord5[19]), new SFInt32(coord5[20]), new SFInt32(coord5[21]), new SFInt32(coord5[22]), new SFInt32(coord5[23]), new SFInt32(coord5[24]), new SFInt32(coord5[25]), new SFInt32(coord5[26]), new SFInt32(coord5[27]), new SFInt32(coord5[28]), new SFInt32(coord5[29])); 
    } else if (eventValue == 6){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]),
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29]),
                                         new SFInt32(coord5[0]), new SFInt32(coord5[1]), new SFInt32(coord5[2]), new SFInt32(coord5[3]), new SFInt32(coord5[4]), new SFInt32(coord5[5]), new SFInt32(coord5[6]), new SFInt32(coord5[7]), new SFInt32(coord5[8]), new SFInt32(coord5[9]), new SFInt32(coord5[10]), new SFInt32(coord5[11]), new SFInt32(coord5[12]), new SFInt32(coord5[13]), new SFInt32(coord5[14]), new SFInt32(coord5[15]), new SFInt32(coord5[16]), new SFInt32(coord5[17]), new SFInt32(coord5[18]), new SFInt32(coord5[19]), new SFInt32(coord5[20]), new SFInt32(coord5[21]), new SFInt32(coord5[22]), new SFInt32(coord5[23]), new SFInt32(coord5[24]), new SFInt32(coord5[25]), new SFInt32(coord5[26]), new SFInt32(coord5[27]), new SFInt32(coord5[28]), new SFInt32(coord5[29]), 
                                         new SFInt32(coord6[0]), new SFInt32(coord6[1]), new SFInt32(coord6[2]), new SFInt32(coord6[3]), new SFInt32(coord6[4]), new SFInt32(coord6[5]), new SFInt32(coord6[6]), new SFInt32(coord6[7]), new SFInt32(coord6[8]), new SFInt32(coord6[9]), new SFInt32(coord6[10]), new SFInt32(coord6[11]), new SFInt32(coord6[12]), new SFInt32(coord6[13]), new SFInt32(coord6[14]), new SFInt32(coord6[15]), new SFInt32(coord6[16]), new SFInt32(coord6[17]), new SFInt32(coord6[18]), new SFInt32(coord6[19]), new SFInt32(coord6[20]), new SFInt32(coord6[21]), new SFInt32(coord6[22]), new SFInt32(coord6[23]), new SFInt32(coord6[24]), new SFInt32(coord6[25]), new SFInt32(coord6[26]), new SFInt32(coord6[27]), new SFInt32(coord6[28]), new SFInt32(coord6[29]));
    } else if (eventValue == 7){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]),
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29]),
                                         new SFInt32(coord5[0]), new SFInt32(coord5[1]), new SFInt32(coord5[2]), new SFInt32(coord5[3]), new SFInt32(coord5[4]), new SFInt32(coord5[5]), new SFInt32(coord5[6]), new SFInt32(coord5[7]), new SFInt32(coord5[8]), new SFInt32(coord5[9]), new SFInt32(coord5[10]), new SFInt32(coord5[11]), new SFInt32(coord5[12]), new SFInt32(coord5[13]), new SFInt32(coord5[14]), new SFInt32(coord5[15]), new SFInt32(coord5[16]), new SFInt32(coord5[17]), new SFInt32(coord5[18]), new SFInt32(coord5[19]), new SFInt32(coord5[20]), new SFInt32(coord5[21]), new SFInt32(coord5[22]), new SFInt32(coord5[23]), new SFInt32(coord5[24]), new SFInt32(coord5[25]), new SFInt32(coord5[26]), new SFInt32(coord5[27]), new SFInt32(coord5[28]), new SFInt32(coord5[29]), 
                                         new SFInt32(coord6[0]), new SFInt32(coord6[1]), new SFInt32(coord6[2]), new SFInt32(coord6[3]), new SFInt32(coord6[4]), new SFInt32(coord6[5]), new SFInt32(coord6[6]), new SFInt32(coord6[7]), new SFInt32(coord6[8]), new SFInt32(coord6[9]), new SFInt32(coord6[10]), new SFInt32(coord6[11]), new SFInt32(coord6[12]), new SFInt32(coord6[13]), new SFInt32(coord6[14]), new SFInt32(coord6[15]), new SFInt32(coord6[16]), new SFInt32(coord6[17]), new SFInt32(coord6[18]), new SFInt32(coord6[19]), new SFInt32(coord6[20]), new SFInt32(coord6[21]), new SFInt32(coord6[22]), new SFInt32(coord6[23]), new SFInt32(coord6[24]), new SFInt32(coord6[25]), new SFInt32(coord6[26]), new SFInt32(coord6[27]), new SFInt32(coord6[28]), new SFInt32(coord6[29]),
                                         new SFInt32(coord7[0]), new SFInt32(coord7[1]), new SFInt32(coord7[2]), new SFInt32(coord7[3]), new SFInt32(coord7[4]), new SFInt32(coord7[5]), new SFInt32(coord7[6]), new SFInt32(coord7[7]), new SFInt32(coord7[8]), new SFInt32(coord7[9]), new SFInt32(coord7[10]), new SFInt32(coord7[11]), new SFInt32(coord7[12]), new SFInt32(coord7[13]), new SFInt32(coord7[14]), new SFInt32(coord7[15]), new SFInt32(coord7[16]), new SFInt32(coord7[17]), new SFInt32(coord7[18]), new SFInt32(coord7[19]), new SFInt32(coord7[20]), new SFInt32(coord7[21]), new SFInt32(coord7[22]), new SFInt32(coord7[23]), new SFInt32(coord7[24]), new SFInt32(coord7[25]), new SFInt32(coord7[26]), new SFInt32(coord7[27]), new SFInt32(coord7[28]), new SFInt32(coord7[29]));
    } else if (eventValue == 8){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]),
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29]),
                                         new SFInt32(coord5[0]), new SFInt32(coord5[1]), new SFInt32(coord5[2]), new SFInt32(coord5[3]), new SFInt32(coord5[4]), new SFInt32(coord5[5]), new SFInt32(coord5[6]), new SFInt32(coord5[7]), new SFInt32(coord5[8]), new SFInt32(coord5[9]), new SFInt32(coord5[10]), new SFInt32(coord5[11]), new SFInt32(coord5[12]), new SFInt32(coord5[13]), new SFInt32(coord5[14]), new SFInt32(coord5[15]), new SFInt32(coord5[16]), new SFInt32(coord5[17]), new SFInt32(coord5[18]), new SFInt32(coord5[19]), new SFInt32(coord5[20]), new SFInt32(coord5[21]), new SFInt32(coord5[22]), new SFInt32(coord5[23]), new SFInt32(coord5[24]), new SFInt32(coord5[25]), new SFInt32(coord5[26]), new SFInt32(coord5[27]), new SFInt32(coord5[28]), new SFInt32(coord5[29]), 
                                         new SFInt32(coord6[0]), new SFInt32(coord6[1]), new SFInt32(coord6[2]), new SFInt32(coord6[3]), new SFInt32(coord6[4]), new SFInt32(coord6[5]), new SFInt32(coord6[6]), new SFInt32(coord6[7]), new SFInt32(coord6[8]), new SFInt32(coord6[9]), new SFInt32(coord6[10]), new SFInt32(coord6[11]), new SFInt32(coord6[12]), new SFInt32(coord6[13]), new SFInt32(coord6[14]), new SFInt32(coord6[15]), new SFInt32(coord6[16]), new SFInt32(coord6[17]), new SFInt32(coord6[18]), new SFInt32(coord6[19]), new SFInt32(coord6[20]), new SFInt32(coord6[21]), new SFInt32(coord6[22]), new SFInt32(coord6[23]), new SFInt32(coord6[24]), new SFInt32(coord6[25]), new SFInt32(coord6[26]), new SFInt32(coord6[27]), new SFInt32(coord6[28]), new SFInt32(coord6[29]),
                                         new SFInt32(coord7[0]), new SFInt32(coord7[1]), new SFInt32(coord7[2]), new SFInt32(coord7[3]), new SFInt32(coord7[4]), new SFInt32(coord7[5]), new SFInt32(coord7[6]), new SFInt32(coord7[7]), new SFInt32(coord7[8]), new SFInt32(coord7[9]), new SFInt32(coord7[10]), new SFInt32(coord7[11]), new SFInt32(coord7[12]), new SFInt32(coord7[13]), new SFInt32(coord7[14]), new SFInt32(coord7[15]), new SFInt32(coord7[16]), new SFInt32(coord7[17]), new SFInt32(coord7[18]), new SFInt32(coord7[19]), new SFInt32(coord7[20]), new SFInt32(coord7[21]), new SFInt32(coord7[22]), new SFInt32(coord7[23]), new SFInt32(coord7[24]), new SFInt32(coord7[25]), new SFInt32(coord7[26]), new SFInt32(coord7[27]), new SFInt32(coord7[28]), new SFInt32(coord7[29]),
                                         new SFInt32(coord8[0]), new SFInt32(coord8[1]), new SFInt32(coord8[2]), new SFInt32(coord8[3]), new SFInt32(coord8[4]), new SFInt32(coord8[5]), new SFInt32(coord8[6]), new SFInt32(coord8[7]), new SFInt32(coord8[8]), new SFInt32(coord8[9]), new SFInt32(coord8[10]), new SFInt32(coord8[11]), new SFInt32(coord8[12]), new SFInt32(coord8[13]), new SFInt32(coord8[14]), new SFInt32(coord8[15]), new SFInt32(coord8[16]), new SFInt32(coord8[17]), new SFInt32(coord8[18]), new SFInt32(coord8[19]), new SFInt32(coord8[20]), new SFInt32(coord8[21]), new SFInt32(coord8[22]), new SFInt32(coord8[23]), new SFInt32(coord8[24]), new SFInt32(coord8[25]), new SFInt32(coord8[26]), new SFInt32(coord8[27]), new SFInt32(coord8[28]), new SFInt32(coord8[29]));
    } else if (eventValue == 9){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]), 
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29]),
                                         new SFInt32(coord5[0]), new SFInt32(coord5[1]), new SFInt32(coord5[2]), new SFInt32(coord5[3]), new SFInt32(coord5[4]), new SFInt32(coord5[5]), new SFInt32(coord5[6]), new SFInt32(coord5[7]), new SFInt32(coord5[8]), new SFInt32(coord5[9]), new SFInt32(coord5[10]), new SFInt32(coord5[11]), new SFInt32(coord5[12]), new SFInt32(coord5[13]), new SFInt32(coord5[14]), new SFInt32(coord5[15]), new SFInt32(coord5[16]), new SFInt32(coord5[17]), new SFInt32(coord5[18]), new SFInt32(coord5[19]), new SFInt32(coord5[20]), new SFInt32(coord5[21]), new SFInt32(coord5[22]), new SFInt32(coord5[23]), new SFInt32(coord5[24]), new SFInt32(coord5[25]), new SFInt32(coord5[26]), new SFInt32(coord5[27]), new SFInt32(coord5[28]), new SFInt32(coord5[29]), 
                                         new SFInt32(coord6[0]), new SFInt32(coord6[1]), new SFInt32(coord6[2]), new SFInt32(coord6[3]), new SFInt32(coord6[4]), new SFInt32(coord6[5]), new SFInt32(coord6[6]), new SFInt32(coord6[7]), new SFInt32(coord6[8]), new SFInt32(coord6[9]), new SFInt32(coord6[10]), new SFInt32(coord6[11]), new SFInt32(coord6[12]), new SFInt32(coord6[13]), new SFInt32(coord6[14]), new SFInt32(coord6[15]), new SFInt32(coord6[16]), new SFInt32(coord6[17]), new SFInt32(coord6[18]), new SFInt32(coord6[19]), new SFInt32(coord6[20]), new SFInt32(coord6[21]), new SFInt32(coord6[22]), new SFInt32(coord6[23]), new SFInt32(coord6[24]), new SFInt32(coord6[25]), new SFInt32(coord6[26]), new SFInt32(coord6[27]), new SFInt32(coord6[28]), new SFInt32(coord6[29]),
                                         new SFInt32(coord7[0]), new SFInt32(coord7[1]), new SFInt32(coord7[2]), new SFInt32(coord7[3]), new SFInt32(coord7[4]), new SFInt32(coord7[5]), new SFInt32(coord7[6]), new SFInt32(coord7[7]), new SFInt32(coord7[8]), new SFInt32(coord7[9]), new SFInt32(coord7[10]), new SFInt32(coord7[11]), new SFInt32(coord7[12]), new SFInt32(coord7[13]), new SFInt32(coord7[14]), new SFInt32(coord7[15]), new SFInt32(coord7[16]), new SFInt32(coord7[17]), new SFInt32(coord7[18]), new SFInt32(coord7[19]), new SFInt32(coord7[20]), new SFInt32(coord7[21]), new SFInt32(coord7[22]), new SFInt32(coord7[23]), new SFInt32(coord7[24]), new SFInt32(coord7[25]), new SFInt32(coord7[26]), new SFInt32(coord7[27]), new SFInt32(coord7[28]), new SFInt32(coord7[29]),
                                         new SFInt32(coord8[0]), new SFInt32(coord8[1]), new SFInt32(coord8[2]), new SFInt32(coord8[3]), new SFInt32(coord8[4]), new SFInt32(coord8[5]), new SFInt32(coord8[6]), new SFInt32(coord8[7]), new SFInt32(coord8[8]), new SFInt32(coord8[9]), new SFInt32(coord8[10]), new SFInt32(coord8[11]), new SFInt32(coord8[12]), new SFInt32(coord8[13]), new SFInt32(coord8[14]), new SFInt32(coord8[15]), new SFInt32(coord8[16]), new SFInt32(coord8[17]), new SFInt32(coord8[18]), new SFInt32(coord8[19]), new SFInt32(coord8[20]), new SFInt32(coord8[21]), new SFInt32(coord8[22]), new SFInt32(coord8[23]), new SFInt32(coord8[24]), new SFInt32(coord8[25]), new SFInt32(coord8[26]), new SFInt32(coord8[27]), new SFInt32(coord8[28]), new SFInt32(coord8[29]),
                                         new SFInt32(coord9[0]), new SFInt32(coord9[1]), new SFInt32(coord9[2]), new SFInt32(coord9[3]), new SFInt32(coord9[4]), new SFInt32(coord9[5]), new SFInt32(coord9[6]), new SFInt32(coord9[7]), new SFInt32(coord9[8]), new SFInt32(coord9[9]), new SFInt32(coord9[10]), new SFInt32(coord9[11]), new SFInt32(coord9[12]), new SFInt32(coord9[13]), new SFInt32(coord9[14]), new SFInt32(coord9[15]), new SFInt32(coord9[16]), new SFInt32(coord9[17]), new SFInt32(coord9[18]), new SFInt32(coord9[19]), new SFInt32(coord9[20]), new SFInt32(coord9[21]), new SFInt32(coord9[22]), new SFInt32(coord9[23]), new SFInt32(coord9[24]), new SFInt32(coord9[25]), new SFInt32(coord9[26]), new SFInt32(coord9[27]), new SFInt32(coord9[28]), new SFInt32(coord9[29]));
    } else if (eventValue == 10){
        computedCoordIndex = new MFInt32(new SFInt32(coord1[0]), new SFInt32(coord1[1]), new SFInt32(coord1[2]), new SFInt32(coord1[3]), new SFInt32(coord1[4]), new SFInt32(coord1[5]), new SFInt32(coord1[6]), new SFInt32(coord1[7]), new SFInt32(coord1[8]), new SFInt32(coord1[9]), new SFInt32(coord1[10]), new SFInt32(coord1[11]), new SFInt32(coord1[12]), new SFInt32(coord1[13]), new SFInt32(coord1[14]), new SFInt32(coord1[15]), new SFInt32(coord1[16]), new SFInt32(coord1[17]), new SFInt32(coord1[18]), new SFInt32(coord1[19]), new SFInt32(coord1[20]), new SFInt32(coord1[21]), new SFInt32(coord1[22]), new SFInt32(coord1[23]), new SFInt32(coord1[24]), new SFInt32(coord1[25]), new SFInt32(coord1[26]), new SFInt32(coord1[27]), new SFInt32(coord1[28]), new SFInt32(coord1[29]),
                                         new SFInt32(coord2[0]), new SFInt32(coord2[1]), new SFInt32(coord2[2]), new SFInt32(coord2[3]), new SFInt32(coord2[4]), new SFInt32(coord2[5]), new SFInt32(coord2[6]), new SFInt32(coord2[7]), new SFInt32(coord2[8]), new SFInt32(coord2[9]), new SFInt32(coord2[10]), new SFInt32(coord2[11]), new SFInt32(coord2[12]), new SFInt32(coord2[13]), new SFInt32(coord2[14]), new SFInt32(coord2[15]), new SFInt32(coord2[16]), new SFInt32(coord2[17]), new SFInt32(coord2[18]), new SFInt32(coord2[19]), new SFInt32(coord2[20]), new SFInt32(coord2[21]), new SFInt32(coord2[22]), new SFInt32(coord2[23]), new SFInt32(coord2[24]), new SFInt32(coord2[25]), new SFInt32(coord2[26]), new SFInt32(coord2[27]), new SFInt32(coord2[28]), new SFInt32(coord2[29]),
                                         new SFInt32(coord3[0]), new SFInt32(coord3[1]), new SFInt32(coord3[2]), new SFInt32(coord3[3]), new SFInt32(coord3[4]), new SFInt32(coord3[5]), new SFInt32(coord3[6]), new SFInt32(coord3[7]), new SFInt32(coord3[8]), new SFInt32(coord3[9]), new SFInt32(coord3[10]), new SFInt32(coord3[11]), new SFInt32(coord3[12]), new SFInt32(coord3[13]), new SFInt32(coord3[14]), new SFInt32(coord3[15]), new SFInt32(coord3[16]), new SFInt32(coord3[17]), new SFInt32(coord3[18]), new SFInt32(coord3[19]), new SFInt32(coord3[20]), new SFInt32(coord3[21]), new SFInt32(coord3[22]), new SFInt32(coord3[23]), new SFInt32(coord3[24]), new SFInt32(coord3[25]), new SFInt32(coord3[26]), new SFInt32(coord3[27]), new SFInt32(coord3[28]), new SFInt32(coord3[29]),
                                         new SFInt32(coord4[0]), new SFInt32(coord4[1]), new SFInt32(coord4[2]), new SFInt32(coord4[3]), new SFInt32(coord4[4]), new SFInt32(coord4[5]), new SFInt32(coord4[6]), new SFInt32(coord4[7]), new SFInt32(coord4[8]), new SFInt32(coord4[9]), new SFInt32(coord4[10]), new SFInt32(coord4[11]), new SFInt32(coord4[12]), new SFInt32(coord4[13]), new SFInt32(coord4[14]), new SFInt32(coord4[15]), new SFInt32(coord4[16]), new SFInt32(coord4[17]), new SFInt32(coord4[18]), new SFInt32(coord4[19]), new SFInt32(coord4[20]), new SFInt32(coord4[21]), new SFInt32(coord4[22]), new SFInt32(coord4[23]), new SFInt32(coord4[24]), new SFInt32(coord4[25]), new SFInt32(coord4[26]), new SFInt32(coord4[27]), new SFInt32(coord4[28]), new SFInt32(coord4[29]),
                                         new SFInt32(coord5[0]), new SFInt32(coord5[1]), new SFInt32(coord5[2]), new SFInt32(coord5[3]), new SFInt32(coord5[4]), new SFInt32(coord5[5]), new SFInt32(coord5[6]), new SFInt32(coord5[7]), new SFInt32(coord5[8]), new SFInt32(coord5[9]), new SFInt32(coord5[10]), new SFInt32(coord5[11]), new SFInt32(coord5[12]), new SFInt32(coord5[13]), new SFInt32(coord5[14]), new SFInt32(coord5[15]), new SFInt32(coord5[16]), new SFInt32(coord5[17]), new SFInt32(coord5[18]), new SFInt32(coord5[19]), new SFInt32(coord5[20]), new SFInt32(coord5[21]), new SFInt32(coord5[22]), new SFInt32(coord5[23]), new SFInt32(coord5[24]), new SFInt32(coord5[25]), new SFInt32(coord5[26]), new SFInt32(coord5[27]), new SFInt32(coord5[28]), new SFInt32(coord5[29]), 
                                         new SFInt32(coord6[0]), new SFInt32(coord6[1]), new SFInt32(coord6[2]), new SFInt32(coord6[3]), new SFInt32(coord6[4]), new SFInt32(coord6[5]), new SFInt32(coord6[6]), new SFInt32(coord6[7]), new SFInt32(coord6[8]), new SFInt32(coord6[9]), new SFInt32(coord6[10]), new SFInt32(coord6[11]), new SFInt32(coord6[12]), new SFInt32(coord6[13]), new SFInt32(coord6[14]), new SFInt32(coord6[15]), new SFInt32(coord6[16]), new SFInt32(coord6[17]), new SFInt32(coord6[18]), new SFInt32(coord6[19]), new SFInt32(coord6[20]), new SFInt32(coord6[21]), new SFInt32(coord6[22]), new SFInt32(coord6[23]), new SFInt32(coord6[24]), new SFInt32(coord6[25]), new SFInt32(coord6[26]), new SFInt32(coord6[27]), new SFInt32(coord6[28]), new SFInt32(coord6[29]),
                                         new SFInt32(coord7[0]), new SFInt32(coord7[1]), new SFInt32(coord7[2]), new SFInt32(coord7[3]), new SFInt32(coord7[4]), new SFInt32(coord7[5]), new SFInt32(coord7[6]), new SFInt32(coord7[7]), new SFInt32(coord7[8]), new SFInt32(coord7[9]), new SFInt32(coord7[10]), new SFInt32(coord7[11]), new SFInt32(coord7[12]), new SFInt32(coord7[13]), new SFInt32(coord7[14]), new SFInt32(coord7[15]), new SFInt32(coord7[16]), new SFInt32(coord7[17]), new SFInt32(coord7[18]), new SFInt32(coord7[19]), new SFInt32(coord7[20]), new SFInt32(coord7[21]), new SFInt32(coord7[22]), new SFInt32(coord7[23]), new SFInt32(coord7[24]), new SFInt32(coord7[25]), new SFInt32(coord7[26]), new SFInt32(coord7[27]), new SFInt32(coord7[28]), new SFInt32(coord7[29]),
                                         new SFInt32(coord8[0]), new SFInt32(coord8[1]), new SFInt32(coord8[2]), new SFInt32(coord8[3]), new SFInt32(coord8[4]), new SFInt32(coord8[5]), new SFInt32(coord8[6]), new SFInt32(coord8[7]), new SFInt32(coord8[8]), new SFInt32(coord8[9]), new SFInt32(coord8[10]), new SFInt32(coord8[11]), new SFInt32(coord8[12]), new SFInt32(coord8[13]), new SFInt32(coord8[14]), new SFInt32(coord8[15]), new SFInt32(coord8[16]), new SFInt32(coord8[17]), new SFInt32(coord8[18]), new SFInt32(coord8[19]), new SFInt32(coord8[20]), new SFInt32(coord8[21]), new SFInt32(coord8[22]), new SFInt32(coord8[23]), new SFInt32(coord8[24]), new SFInt32(coord8[25]), new SFInt32(coord8[26]), new SFInt32(coord8[27]), new SFInt32(coord8[28]), new SFInt32(coord8[29]),
                                         new SFInt32(coord9[0]), new SFInt32(coord9[1]), new SFInt32(coord9[2]), new SFInt32(coord9[3]), new SFInt32(coord9[4]), new SFInt32(coord9[5]), new SFInt32(coord9[6]), new SFInt32(coord9[7]), new SFInt32(coord9[8]), new SFInt32(coord9[9]), new SFInt32(coord9[10]), new SFInt32(coord9[11]), new SFInt32(coord9[12]), new SFInt32(coord9[13]), new SFInt32(coord9[14]), new SFInt32(coord9[15]), new SFInt32(coord9[16]), new SFInt32(coord9[17]), new SFInt32(coord9[18]), new SFInt32(coord9[19]), new SFInt32(coord9[20]), new SFInt32(coord9[21]), new SFInt32(coord9[22]), new SFInt32(coord9[23]), new SFInt32(coord9[24]), new SFInt32(coord9[25]), new SFInt32(coord9[26]), new SFInt32(coord9[27]), new SFInt32(coord9[28]), new SFInt32(coord9[29]),
                                         new SFInt32(coord10[0]), new SFInt32(coord10[1]), new SFInt32(coord10[2]), new SFInt32(coord10[3]), new SFInt32(coord10[4]), new SFInt32(coord10[5]), new SFInt32(coord10[6]), new SFInt32(coord10[7]), new SFInt32(coord10[8]), new SFInt32(coord10[9]), new SFInt32(coord10[10]), new SFInt32(coord10[11]), new SFInt32(coord10[12]), new SFInt32(coord10[13]), new SFInt32(coord10[14]), new SFInt32(coord10[15]), new SFInt32(coord10[16]), new SFInt32(coord10[17]), new SFInt32(coord10[18]), new SFInt32(coord10[19]), new SFInt32(coord10[20]), new SFInt32(coord10[21]), new SFInt32(coord10[22]), new SFInt32(coord10[23]), new SFInt32(coord10[24]), new SFInt32(coord10[25]), new SFInt32(coord10[26]), new SFInt32(coord10[27]), new SFInt32(coord10[28]), new SFInt32(coord10[29]));
    }
}

function initialize() {
    // compute points and compute indices
    set_numberOfSteps (numberOfSteps);

    tracePrint ('initialization() successful');
}
