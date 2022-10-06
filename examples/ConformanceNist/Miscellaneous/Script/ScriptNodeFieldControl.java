// Java Script Authoring Interface (JSAI) classes for VRML
import vrml.*;
import vrml.field.*;
import vrml.node.*;

/** <p>
  * ScriptNodeFieldControl provides Script functionality for 3D scenes
  * ScriptNodeFieldControl-Java (xml, wrl), serving as a simple
  * example &amp; conformance test.
  * </p> <p>
  * <b>Related scene</b>:
  * <ul>
  *     <li>	<a href="../ScriptNodeFieldControl-Java.xml">ScriptNodeFieldControl-Java.x3d</a></li>
  * 	<li>	<a href="../ScriptNodeFieldControl-Java.wrl">ScriptNodeFieldControl-Java.wrl</a></li>
  * <ul>
  * </p>
  * @author  Don Brutzman
  * @version 10 October 2002
  * @url http://www.web3D.org/TaskGroups/x3d/translation/examples/ScriptConformance/ScriptNodeFieldControl.java
  */

public class ScriptNodeFieldControl extends vrml.node.Script 
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
	  * </ul>
  	  */
	private int mode = 0;

	/** accessor method to get DEBUG value. */
	public boolean getDEBUG()
	{
		return DEBUG;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	// Declare eventIn and eventOuts.
	// Note no explicit declaration of accessType (eventIn|eventOut|field|exposedField).

	/** eventIn startTime provides the activating event time of a
	  * mouse/pointer click by user on triggering text. */
	private SFTime		startTime;

	/** Script field sceneText is a reference to the scene node for the new message which 
	  * replaces prior 3D text in the scene. */
	private SFNode	sceneText;

	/** sceneTextString is a reference to the 'string' field of the sceneText node. */
	private MFString sceneTextString;

	/** Script field sceneTransform is a reference to the scene node that moves the new message to 
	  * another position to make changes more noticeable. */
	private SFNode 	sceneTransform;

	/** sceneTransformTranslation is a reference to the 'translation' field of the sceneTransform node. */
	private SFVec3f sceneTransformTranslation;

	/** Script field sceneMaterial is a reference to the scene node for text material. */
	private SFNode 	sceneMaterial;

	/** sceneMaterialDiffuseColor is a reference to the 'diffuseColor' field of the sceneMaterial node. */
	private SFColor sceneMaterialDiffuseColor;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	/** toString() overrides a utility method in Object, allowing
	  * run-time inspection of class name.
	  * @see java.lang.Object#toString()
	  */
	public String getName() { return "ScriptNodeFieldControl"; }

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	/** Initialize scene variables prior to rendering.
	  * Reference: 
	  * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.6">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.6</a>
	  */
	public void initialize()
	{
 	  String [] message = new String [4];  // use intermediate variables for clarity
	  SFVec3f  position = new SFVec3f ();  // otherwise expressions get  v e r y   l o n g
	  SFColor     color = new SFColor ();

	  try {
 		if (DEBUG) System.out.println (getName() + " initialize():");

		// caution:  string-based access to field names must be exact!
 		sceneText = (SFNode) getField ("sceneText"); // instantiate link to field

 		message [0] = "Java ScriptNodeFieldControl.class";
 		message [1] = "has reinitialized the sceneText node";
 		message [2] = "";
 		message [3] = "Please click text for processEvent results.";
 		
		// use field access to get reference then set value in scene node
	 	sceneTextString = (MFString) ((Node) sceneText.getValue()).getExposedField ("string");
	 	sceneTextString.setValue ( message );  // action:  setValue sends change back to scene
		// now use console to view positive indication of results
		if (DEBUG) System.out.println ("sceneText.string = " + sceneTextString.toString() );

		// use field access to get reference then set value in scene node
	 	sceneTransform = (SFNode) getField ("sceneTransform"); // instantiate link to field
	 	position.setValue ( 0, 3, 0 );
	 	sceneTransformTranslation = (SFVec3f) ((Node) sceneTransform.getValue()).getExposedField ("translation");
	 	sceneTransformTranslation.setValue ( position );  // action:  setValue sends change back to scene
		if (DEBUG) System.out.println ("sceneTransform.translation = " +
	 		sceneTransformTranslation.toString() );

		// use field access to get reference then set value in scene node
	 	sceneMaterial = (SFNode) getField ("sceneMaterial"); // instantiate link to field
	 	color.setValue ( 0.8f, 0.8f, 0.2f ); // note floats, not doubles
	 	sceneMaterialDiffuseColor = (SFColor) ((Node) sceneMaterial.getValue()).getExposedField ("diffuseColor");
	 	sceneMaterialDiffuseColor.setValue ( color );
		if (DEBUG) System.out.println ("sceneMaterial.diffuseColor = " +
	 		 sceneMaterialDiffuseColor.toString() );
		if (DEBUG) System.out.println ("");
		mode = 1;
	  }
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
	  SFVec3f  position = new SFVec3f();
	  SFColor     color = new SFColor();

	  try {
		if (DEBUG) System.out.println ("==============================");
		// what is name of this Script that is printing out a diagnostic?
		if (DEBUG) System.out.println (getName() + " processEvent (touchEvent):");
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
 			message [1] = "Java initialize() in Script using Field control.";
 			message [2] = "This text appears first, if Java initialization fails.";
 			message [3] = "";
	 	 	position.setValue ( 0, 1, 0 );
	 	 	color.setValue    ( 0.8f, 0.2f, 0.2f ); // note floats, not doubles
		  	break;

		  case 1:	// post-initialize ready-to-click message
		  	initialize();
		  	break;

		  case 2:	// post-click all-done message
			message [0] = "User click on text seen by Java";
 			message [1] = "processEvent via Script node eventIn.";
 			message [2] = "Text & position successfully changed";
 			message [3] = "via Field control.  Test passed.";
	 	 	position.setValue ( 0, -1, 0 );
	 	 	color.setValue    ( 0.2f, 0.8f, 0.2f ); // note floats, not doubles
		  	break;
		}
		
	 	if (mode != 1)
	 	{
	 		// use field access to set value in scene node
	 		sceneTextString.setValue ( message );
			if (DEBUG) System.out.println ("sceneText.string = " +
				sceneTextString.toString() );
	
			// use field access to set value in scene node
	 		sceneTransformTranslation.setValue ( position );
			if (DEBUG) System.out.println ("sceneTransform.translation = " +
	 			sceneTransformTranslation.toString() );

			// use field access to set value in scene node
	 		sceneMaterialDiffuseColor.setValue ( color );
			if (DEBUG) System.out.println ("sceneMaterial.diffuseColor = " +
	 			sceneMaterialDiffuseColor.toString() );
			if (DEBUG) System.out.println ("");
	 	}
	  }
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

	/** shutdown() is called when this Script node is deleted.
	  * Use is optional, typically only needed for cleanup such as shutting down socket connections.
	  * Reference: 
	  * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.5">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.5</a>
	  */
	public void shutdown()  // 
	{
		if (DEBUG) System.out.println ("==============================");
	  	if (DEBUG) System.out.println (getName() + " script shutdown.");
		if (DEBUG) System.out.println ("==============================");
	}
}

