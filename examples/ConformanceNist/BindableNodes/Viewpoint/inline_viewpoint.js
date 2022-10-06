#VRML V2.0 utf8
#
# NIST VTS v1.0
#
# Module:  Bindable_Nodes
# Node  :  Viewpoint
#
# file -  inline_viewpoint.wrl
# 
#
# A Viewpoint node is introduced into this world via the Inline node.
# The url field of the Inline node points to a file containing a RIGHT SIDE Viewpoint
# node.  Because the Viewpoint node is introduced via an Inline node, it should not be 
# bound to the top of the stack. As a result, the default frontal Viewpoint values
# should be used by the browser, and the viewer should have a FRONT view of the cube
# geometry, and Text at the bottom of the world should indicate that the Viewpoint 
# position is 0 0 10.
#
# Features Tested
#   SR's - #1
#
# Written by: Michael Kass                       
#
# Revision History
#      None
#

NavigationInfo {
type "EXAMINE"
}

Background { 
	skyColor 0 0 1
	groundColor [0 0.5 0, 0 0.5 0]
	groundAngle 1.57
}

Inline { 
	url "viewinline.wrl"
}



