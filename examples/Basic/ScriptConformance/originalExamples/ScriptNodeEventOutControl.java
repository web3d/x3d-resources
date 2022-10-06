// **********************************************************************
// 
// Script Name:	ScriptNodeEventOutControl.java
//
// Description:	Practice program for JSAI Script node links for DIS
//				Experiment with ROUTEs from Script node eventOuts
//
// Author:		Don Brutzman
// Revised:		30 November 97
// World:		ScriptNodeEventOutControl.wrl
// Location:	http://www.stl.nps.navy.mil/~brutzman/vrml/examples/course
//
// Notes:		System.out.println text appears on WorldView VRML console
//					(using Java via the Script node, not EAI)
//
// **********************************************************************

// **********************************************************************
//   IMPORT
// **********************************************************************

// Java JSAI classes for VRML
import vrml.*;
import vrml.field.*;
import vrml.node.*;

// **********************************************************************
//   CLASS
// **********************************************************************

public class ScriptNodeEventOutControl extends Script 
{
	public static final boolean DEBUG = true;

	// Declare EventIn and eventOuts
	private SFTime		startTime;		// eventIn
	private MFString	ChangedText;		// eventOut
	private SFVec3f 	ChangedPosition;	// eventOut

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public String getName()
	{ return "ScriptNodeEventOutControl"; }

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public void initialize ()
	{
 		if (DEBUG) System.out.println ("");
 		if (DEBUG) System.out.println ("initializing ScriptNodeEventOutControl script node");

		ChangedText = (MFString) getEventOut ("ChangedText"); // instantiate

 		String [] message = new String [3];
 		message [0] =  "Java ScriptNodeEventOutControl.class";
 		message [1] = "has reinitialized the ChangedText node";
 		message [2] =     "and is ready to be clicked...";
		ChangedText.setValue ( message );
		if (DEBUG) System.out.println ("ChangedText = " + message[0]);
	 	if (DEBUG) System.out.println (message[1] + " " + message[2]);

		ChangedPosition = (SFVec3f) getEventOut ("ChangedPosition"); // instantiate
	 	SFVec3f position = new SFVec3f ( 0, 3, 0 );
	 	ChangedPosition.setValue ( position );
		if (DEBUG) System.out.println ("ChangedPosition = " + ChangedPosition);
		if (DEBUG) System.out.println ("");

		return;
 	}

	public void processEvent (Event touch) 
	{
		if (DEBUG) System.out.println
		  ("ScriptNodeEventOutControl processEvent startTime = " + startTime); 
		if (DEBUG) System.out.println ("Event touch = " + touch); 
    	String [] message = new String [4];
    	message [0] = "Click seen by Java processEvent";
 		message [1] = "via Script node eventIn.";
     	message [2] = "Text & position successfully changed";
     	message [3] = "via eventOut control.";
	 	ChangedText.setValue ( message );
	 	if (DEBUG) System.out.println ("ChangedText = ");
	 	if (DEBUG) System.out.println (message[0] + " " + message[1]);
	 	if (DEBUG) System.out.println (message[2] + " " + message[3]);

	 	SFVec3f position = new SFVec3f ( 0, -1, 0 );
	 	ChangedPosition.setValue ( position );
	 	if (DEBUG) System.out.println ("ChangedPosition = " + position );
		if (DEBUG) System.out.println ("");

	 	return;
	}
}

