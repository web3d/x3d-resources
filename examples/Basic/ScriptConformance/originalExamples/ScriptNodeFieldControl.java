// **********************************************************************
// 
// Script Name:	ScriptNodeFieldControl.java
//
// Description:	Practice program for JSAI Script node links for DIS
//				Use SFNodes as directly modifiable Script node fields
//
// Author:		Don Brutzman
// Revised:		30 November 97
// World:		ScriptNodeFieldControl.wrl
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

public class ScriptNodeFieldControl extends Script 
{
	public static final boolean DEBUG = true;

	// Declare EventIn and eventOuts
	private SFTime		startTime; 		// eventIn
	private SFNode		ChangedText;  		// field
	private SFNode 		ChangedPosition;	// field

	private Node		node;
	private MFString	text;
	private SFVec3f		translation;
	private SFVec3f		position;
				
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public String getName() { return "ScriptNodeFieldControl"; }

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public void initialize ()
	{
		// see examples 3.11/3.12 pp. 83-86
		// "Java for 3D and VRML Worlds," Lea/Matsuda/Miyashita
 		if (DEBUG) System.out.println ("");
 		if (DEBUG) System.out.println ("initializing ScriptNodeFieldControl script node");

		ChangedText = (SFNode) getField ("ChangedText"); // instantiate

 		String [] message = new String [3];
 		message [0] = "Java ScriptNodeFieldControl.class";
 		message [1] = "has reinitialized the ChangedText node";
 		message [2] = "and is ready to be clicked...";

		node = (Node) (ChangedText.getValue());
		text = (MFString) node.getExposedField ("string");
		text.setValue (message);
		if (DEBUG) System.out.println ("ChangedText.string = " + message[0]);
	 	if (DEBUG) System.out.println (message[1] + " " + message[2]);

		ChangedPosition = (SFNode) getField ("ChangedPosition"); // instantiate
		
	 	position = new SFVec3f ( 0, 3, 0 );
		node = (Node) (ChangedPosition.getValue());
		translation = (SFVec3f) node.getExposedField ("translation");
		translation.setValue (position);
		if (DEBUG) System.out.println("ChangedPosition.translation = " + position);
		if (DEBUG) System.out.println("");

		return;
 	}

	public void processEvent (Event touch) 
	{
		if (DEBUG) System.out.println
				("ScriptNodeFieldControl processEvent startTime = " + startTime); 
		if (DEBUG) System.out.println ("Event touch = " + touch); 
    	String [] message = new String [4];
    	message [0] = "Click seen by Java processEvent";
     	message [1] = "via Script node eventIn.";
     	message [2] = "Text & position successfully changed";
     	message [3] = "via SFNode field control.";

		node = (Node) (ChangedText.getValue());
		text = (MFString) node.getExposedField ("string");
		text.setValue (message);
		if (DEBUG) System.out.println ("ChangedText.string = ");
		if (DEBUG) System.out.println (message[0] + " " + message[1]);
	 	if (DEBUG) System.out.println (message[2] + " " + message[3]);

	 	position = new SFVec3f ( 0, -1, 0 );
		node = (Node) (ChangedPosition.getValue());
		translation = (SFVec3f) node.getExposedField ("translation");
		translation.setValue (position);
		if (DEBUG) System.out.println("ChangedPosition.translation = " + position);
		if (DEBUG) System.out.println ("");

		return;
	}
}

