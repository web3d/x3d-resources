//
// Filename: simple.java
//
// Author: Martin Reddy, SRI International. 13 April 1998
//
// This EAI applet will try to find all of the GeoInline nodes
// that occur within a scene. It will then output a description
// of the current setting of these nodes to a TextArea widget.
//
// $Id: simple.java,v 1.4 1998/05/04 17:36:43 reddy Exp $
//

import java.awt.*;
import java.lang.*;
import java.applet.*;
import vrml.external.*;
import vrml.external.field.*;
import vrml.external.exception.*; 
//import netscape.javascript.JSObject;

public class simple extends Applet {

  Browser browser = null;
  TextArea output = null;
  String  button1 = "Find GeoInline Proto";

  // First is the init() function which is called once only
  // Here we add the AWT buttons to the HTML page.

  public void init() {
    add( new Button( button1 ) );
    output = new TextArea(5, 40);
    output.setEditable( false );
    add(output);
  }

  // Now we have the start() function which is called on refresh
  // Here we try and get hooks to the VRML plugin instance.

  public void start() {
    browser = Browser.getBrowser( this );

    output.appendText( "GeoInline PROTO Tester - M.Reddy, 1998\n" );
    if ( browser == null )
      output.appendText( "getBrowser failed!\n" );
    else {
      output.appendText( "---\nUp Arrow = Load Inline\n" );
      output.appendText( "Down Arrow = Unload Inline\n---\n" );
    }
  }

  // get_proto will find all of the GeoInline nodes extant
  // within the Group node called root_node. It will then query
  // the eventOut fields of each of these and output some details

  public void get_proto() {

    // Start off by getting a hold of the root group node in the
    // scene. This must be named, e.g. DEF root_node Group {...

    if ( browser == null ) return;

    Node root_node = browser.getNode( "root_node" );
    if ( root_node == null ) return;

    output.setText( "" );

    // Now get the "children" field of the group node and pass

    get_proto_recur( (EventOutMFNode) root_node.getEventOut( "children" ) );

  }

  // get_proto_recur is a recursive method called by get_proto. It will
  // traverse all of the children nodes in a grouping node and if any
  // GeoInline nodes are found, it will query the current
  // settings for the node and output a description to the TextArea

  public void get_proto_recur( EventOutMFNode children ) {
    if ( children == null ) return;

    // Now get the "children" field of the group node and work
    // out how many nodes are contained in this group.

    int num_nodes = children.getSize();
    Node nodes[]  = children.getValue();

    // traverse through each node, checking for GeoInline's

    for ( int i = 0; i < num_nodes; ++i ) {
      String node_type = nodes[i].getType();

      if ( node_type.compareTo( "GeoInline" ) == 0 ) {

	// Query the eventOut fields of the GeoInline node

	EventOutMFNode scene = (EventOutMFNode)
	  nodes[i].getEventOut( "children" );

	EventOutMFString curr_url = (EventOutMFString)
	  nodes[i].getEventOut( "url_changed" );

	EventOutSFBool curr_load = (EventOutSFBool)
	  nodes[i].getEventOut( "load_changed" );

	// Output a description of this instance to the TextArea

	output.appendText( "Got the GeoInline PROTO instance:\n" );

	if ( curr_url.getSize() == 0 )
	  output.appendText( "  URL = <not set>\n" );
	else
	  output.appendText( "  URL = '" + curr_url.get1Value(0) + "'\n" );

	if ( curr_load.getValue() == false )
	  output.appendText( "  URL is not loaded.\n" );

	for ( int j = 0; j < scene.getSize(); ++j ) {
	  output.appendText( "  Node " + j + " = " + 
			     scene.get1Value(j).getType() + "\n" );
	}

      } else if ( node_type.compareTo( "Group" ) == 0 ||
		  node_type.compareTo( "Transform" ) == 0 ||
		  node_type.compareTo( "Anchor" ) == 0 ||
		  node_type.compareTo( "Collision" ) == 0 ) {
	
	// If we have a grouping node, then recursively check
	// all of the children of this node also.

	get_proto_recur( (EventOutMFNode) nodes[i].getEventOut("children") );
      }
    }
  }

  // The button event handling routine

  public boolean action( Event event, Object what ) {
    if ( event.target instanceof Button ) {
      Button b = (Button) event.target;

      if ( b.getLabel() == button1 ) {
	get_proto();
      }
    }
    return true;
  }

  // Set the background colour for the applet viewport

  public void paint( Graphics g ) {
    Dimension d = this.size();
    g.setColor( Color.white );
    g.fillRect( 0, 0, d.width, d.height );
  }

}
