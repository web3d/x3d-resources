/*
Copyright (c) 2008-2013 held by the author(s).  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
 * Neither the names of the Naval Postgraduate School (NPS)
Modeling Virtual Environments and Simulation (MOVES) Institute
(http://www.nps.edu and http://www.MovesInstitute.org)
nor the names of its contributors may be used to endorse or
promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Description: Create X3D scene consisting of example circle and cross-hairs made out of line segments
 * Filename:    CircleLines.java
 * @author      Don Brutzman
 * Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/CircleLines.java
 * Created:     1 January 2002
 * Revised:     12 August 2013
 * Compilation: javac CircleLines.java
 * Invocation:  java  CircleLines [numberOfsegmentCount] [ > CircleLinesFileName.x3d ]
 * Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/CircleLinesExample.x3d
 * License:     ../license.html
 */

import java.text.DecimalFormat;
import java.util.*;

/**
 * Create an X3D scene that draws a 2D circle outline consisting of line segments
 * @author brutzman
 */
public class CircleLines {

	static final int defaultSegmentCount = 24;
	static       int segmentCount = defaultSegmentCount;

 /**
 * Command line invocation
 * @param parameters command line parameters:  number of segments
 */
        public static void main(String[] parameters)
{
	int index;
	double angle;
	DecimalFormat   precision3  = new DecimalFormat ("#0.000");

	// Read command line parameters, if any

	if (parameters.length == 0) // no value provided, use default
	{
		segmentCount = defaultSegmentCount;
	}
	else if (parameters.length == 1) // number of segments
	{
		segmentCount = Integer.parseInt(parameters [0]);
		if (segmentCount < 3) segmentCount = defaultSegmentCount;
	}
        else // provide help
	{
		// Compilation:    javac CircleLines.java
                System.out.println ("Usage:    java CircleLines [numberOfsegmentCount] [ > CircleLinesFileName.x3d ]");
		System.out.println ("Example:  java CircleLines 60 > CircleLines60.x3d");
		System.exit (-1);
	}		
	printX3dSceneHeader  (); // begin scene output
        
        // Now loop to create coordIndex connections, followed by loop to compute Coordinate points

	System.out.println ("        <IndexedLineSet coordIndex=\"");  // \" is an escaped " literal
	for (index = 0; index < segmentCount; index++)
	{
		System.out.print (index + " ");
                // twenty indices per printed line, for readability:
		if (((index + 1) % 20) == 0) System.out.println ();
	}
	System.out.println (String.valueOf(segmentCount    ) + " -1"); // sentinel index -1 terminates each polyline segment
	System.out.println (String.valueOf(segmentCount    ) + " " + String.valueOf(segmentCount + 1) + " -1"); // horizontal crosshair
	System.out.print   (String.valueOf(segmentCount + 2) + " " + String.valueOf(segmentCount + 3) + " -1"); //   vertical crosshair
	System.out.println ("\">"); // end of <IndexedLineSet> opening tag
	
	System.out.println ("            <Coordinate point=\"");
	for (index = 0; index < segmentCount; index++)
	{
		angle = 2.0 * Math.PI * index / segmentCount;
		System.out.print (precision3.format(Math.sin(angle)) + " " +
			          precision3.format(Math.cos(angle)) + " 0.0,\t"); // three values x y z for each point
                // five pairs of coordinate triples per printed line, for readability:
		if (((index + 1) % 5) == 0) System.out.println (); 
	}
	System.out.println ("0 1 0,\t0 -1 0,\t1 0 0,\t-1 0 0"); // crosshair coordinates, \t is tab character
	System.out.println ("\"/>"); // end of // end of <Coordinate/> singleton tag
	System.out.println ("        </IndexedLineSet>"); // end of IndexedLineSet: closing tag
        
	printX3dSceneFooter (); // finish scene output
}

public static void printX3dSceneHeader  ()
{
	Calendar calendar = new GregorianCalendar ();
        String      today = (calendar.get(Calendar.DAY_OF_MONTH)  + " " +
			     calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("en", "US")) + " " +
			     calendar.get(Calendar.YEAR));
		
	System.out.println ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	System.out.println ("<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.2//EN\" \"http://www.web3d.org/specifications/x3d-3.2.dtd\">");
	System.out.println ("<X3D profile='Interchange' version='3.2'>");
	System.out.println ("  <head>");
	System.out.print   ("    <meta name=\"title\" content=\"CircleLinesExample");
	System.out.print   (segmentCount);
	System.out.println (".x3d\"/>");
	System.out.println ("    <meta name=\"description\" content=\"Example circle and cross-hairs made out of line segments, autogenerated by a simple Java program.\"/>");
	System.out.println ("    <meta name=\"creator\" content=\"Don Brutzman\"/>");
	System.out.println ("    <meta name=\"created\" content=\"" + today + "\"/>");
	System.out.println ("    <meta name=\"generated\" content=\"" + today + "\"/>");
	System.out.println ("    <meta name=\"modified\" content=\"" + today + "\"/>");
						
	System.out.println ("    <meta name=\"reference\" content=\"http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/CircleLinesExample.x3d\"/>");
	System.out.println ("    <meta name=\"generator\" content=\"CircleLines.java\"/>");
	System.out.println ("    <meta name=\"generator\" content=\"http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09EventUtilitiesScripting/CircleLines.java\"/>");
	System.out.println ("    <meta name=\"license\" content=\"../license.html\"/>");
	System.out.println ("  </head>");
	System.out.println ("  <Scene>");
	System.out.println ("    <!-- CircleLines is an IndexedLineSet made out of " + segmentCount + " line segments -->");
	System.out.println ("    <Viewpoint description=\"CircleLines Example\" position=\"0 0 4\"/>");
	System.out.println ("    <Shape>");
}

public static void printX3dSceneFooter ()
{
	System.out.println ("        <Appearance>");
	System.out.println ("            <Material emissiveColor=\"1 .5 .2\"/>");
	System.out.println ("        </Appearance>");
	System.out.println ("    </Shape>");
	System.out.println ("  </Scene>");
	System.out.println ("</X3D>");
}
}
