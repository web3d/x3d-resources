// Java Script Authoring Interface (JSAI) classes for VRML
import vrml.*;
import vrml.field.*;
import vrml.node.*;

/** <p>
  * ScriptNodeEventOutControl provides Script functionality for 3D scenes
  * ScriptNodeEventOutControl-Java (xml, wrl), serving as a simple
  * example &amp; conformance test.
  * </p> <p>
  * <b>Related scene</b>:
  * <ul>
  *     <li>	<a href="../ScriptNodeEventOutControl-Java.xml">ScriptNodeEventOutControl-Java.x3d</a></li>
  * 	<li>	<a href="../ScriptNodeEventOutControl-Java.wrl">ScriptNodeEventOutControl-Java.wrl</a></li>
  * </ul>
  * </p>
  * @author  Don Brutzman
  * @version 10 October 2002
  * @url http://www.web3D.org/TaskGroups/x3d/translation/examples/ScriptConformance/ScriptNodeEventOutControl.java
  */

public class ScriptNodeEventOutControl extends vrml.node.Script 
{
	/** DEBUG allows developers to turn execution-trace messages on/off -
	  * these System.out.println messages appear in the Java Console window.
	  */
	public static boolean DEBUG = true;

	/** accessor method to set DEBUG value. */
	public void setDEBUG (boolean newValue)
	{
		DEBUG = newValue;
	}

	/** mode indicates state of text message:
	  * <ul>
	  *     <li>	<code>mode==0<code> indicates initial, uninitialized state </li>
	  * 	<li>	<code>mode==1<code> indicates initialized state </li>
	  * 	<li>	<code>mode==2<code> indicates clicked, completed state </li>
	  * <ul>
  	  */
	private int mode = 0;

	/** accessor method to get DEBUG value. */
	public boolean getDEBUG ()
	{
		return DEBUG;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	// Declare eventIn and eventOuts.
	// Note no explicit declaration of accessType (eventIn|eventOut|field|exposedField).

	/** eventIn startTime provides the activating event time of a
	  * mouse/pointer click by user on triggering text. */
	private SFTime		startTime;

	/** eventOut changedText provides the new message which 
	  * replaces prior 3D text in the scene. */
	private MFString	changedText;

	/** eventOut changedPosition moves the new message to 
	  * another position to make changes more noticeable. */
	private SFVec3f 	changedPosition;

	/** eventOut changedColor changes text color. */
	private SFColor 	changedColor;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	/** toString() overrides a utility method in Object, allowing
	  * run-time inspection of class name.
	  * @see java.lang.Object#toString()
	  */
	public String getName() { return "ScriptNodeEventOutControl"; }

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	/** Initialize scene variables prior to rendering.
	  * Reference: 
	  * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.6">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.6</a>
	  */
	public void initialize ()
	{
 	  String [] message = new String [4];  // use intermediate variables for clarity
	  SFVec3f  position = new SFVec3f ();  // otherwise expressions get  v e r y   l o n g
	  SFColor     color = new SFColor ();

	  try {
 		if (DEBUG) System.out.println (getName () + " initialize ():");

		// caution:  string-based access to field names must be exact!
 		changedText = (MFString) getEventOut ("changedText"); // instantiate local eventOut

 		message [0] = "Java ScriptNodeEventOutControl.class";
 		message [1] = "has reinitialized the changedText node";
 		message [2] = "";
 		message [3] = "Please click text for processEvent results.";
		changedText.setValue ( message ); // action:  setting value in scene sends eventOut
		// now use console to view positive indication of results
		if (DEBUG) System.out.println ("changedText = ");
	 	if (DEBUG) System.out.println (message[0] + "\n" + message[1]);
	 	if (DEBUG) System.out.println (message[2] + "\n" + message[3]);

		changedPosition = (SFVec3f) getEventOut ("changedPosition"); // instantiate local eventOut
	 	position.setValue ( 0, 3, 0 );
	 	changedPosition.setValue ( position ); // action:  setting value in scene sends eventOut
		if (DEBUG) System.out.println ("changedPosition = " + changedPosition);

		changedColor = (SFColor) getEventOut ("changedColor"); // instantiate local eventOut
	 	color.setValue ( 0.8f, 0.8f, 0.2f ); // note floats, not doubles
	 	changedColor.setValue ( color ); // action: setting value in scene sends eventOut
		if (DEBUG) System.out.println ("changedColor = " + changedColor);
		if (DEBUG) System.out.println ("");
		mode = 1;
	  }
	  // Script errors are very hard to debug and will fail silently if not caught
	  // See browser's Java Console for output
	  catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node initialize method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	  return;
 	}

	/** processEvent() is called automatically when the script receives any event.
	  * @param touchEvent provides the user's "click" Event ROUTEd into the Script.
	  */ 
	public void processEvent (Event touchEvent) 
	{
	  String [] message = new String [4];
	  SFVec3f  position = new SFVec3f ();
	  SFColor     color = new SFColor ();

	  try {
		if (DEBUG) System.out.println ("==============================");
		// what is name of this Script that is printing out a diagnostic?
		if (DEBUG) System.out.println (getName () + " processEvent (touchEvent):");
		// what event has arrived (event name and event value)?
		if (DEBUG) System.out.println ("touchEvent " + touchEvent.getName() +
			" = " + touchEvent.getValue());

		// run-time code now looks essentially identical to EcmaScript version
		// all the hard work occurred during initialize() setups

		mode = (mode + 1) % 3;
		if (DEBUG) System.out.println ("mode = " + mode);

		switch (mode)
		{
		  case 0:	// pre-initialize error messsage
			message [0] = "Default text in VRML scene will be replaced by";
 			message [1] = "Java initialize() in Script using EventOut control.";
 			message [2] = "This text appears first, if Java initialization fails.";
 			message [3] = "";
	 	 	position.setValue ( 0, 1, 0 );
	 	 	color.setValue    ( 0.8f, 0.2f, 0.2f ); // note floats, not doubles
		  	break;

		  case 1:	// post-initialize ready-to-click message
		  	initialize ();
		  	break;

		  case 2:	// post-click all-done message
			message [0] = "User click on text seen by Java";
 			message [1] = "processEvent via Script node eventIn.";
 			message [2] = "Text & position successfully changed";
 			message [3] = "via EventOut control.  Test passed.";
	 	 	position.setValue ( 0, -1, 0 );
	 	 	color.setValue    ( 0.2f, 0.8f, 0.2f ); // note floats, not doubles
		  	break;
		}
		
	 	if (mode != 1)
	 	{
	 		changedText.setValue ( message ); // setting value in scene sends eventOut
			if (DEBUG) System.out.println ("changedText = ");
	 		if (DEBUG) System.out.println (message[0] + "\n" + message[1]);
	 		if (DEBUG) System.out.println (message[2] + "\n" + message[3]);
	
			changedPosition.setValue ( position ); // setting value in scene sends eventOut
	 		if (DEBUG) System.out.println ("changedPosition = " + position );

			changedColor.setValue ( color ); // setting value in scene sends eventOut
	 		if (DEBUG) System.out.println ("changedColor = " + changedColor );
			if (DEBUG) System.out.println ("");
	 	}
	  }
	  // Script errors are very hard to debug and will fail silently if not caught
	  // See browser's Java Console for output
	  catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node processEvent method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	  return;
	}
	/** 
    */ 

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	/** shutdown () is called when this Script node is deleted.
	  * Use is optional, typically only needed for cleanup such as shutting down socket connections.
	  * Reference: 
	  * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.5">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.5</a>
	  */
	public void shutdown()
	{
		if (DEBUG) System.out.println ("==============================");
	  	if (DEBUG) System.out.println (getName () + " script shutdown.");
		if (DEBUG) System.out.println ("==============================");
	}
}

