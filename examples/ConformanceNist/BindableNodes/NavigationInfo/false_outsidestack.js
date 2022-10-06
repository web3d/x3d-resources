#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : NavigationInfo
#
# false_outsidestack.wrl - 
#
# A world with "WALK" and "FLY" NavigationInfo instances is defined, with an initial state in which
# neither NavigationInfo instance is bound.  To test the NavigationInfo stack for binding state when the
# a NavigationInfo instance not on the stack is "popped", the VRML browser does the following::
# 
# 1) Indicate the "PUSH" button for the "WALK" NavigationInfo instance
# 2) Indicate the "POP" button for the "FLY" NavigationInfo instance
#
# The result should be: No change in the stack should occur. The "WALK" NavigationInfo should
# be displayed, and the "WALK" NavigationInfo "isBound" eventout = TRUE. A timestamp should
# be present for the "WALK" NavigationInfo. No events should be sent from the "FLY" NavigationInfo, and
# no isBound event, or timestamp should be displayed.
#
# Features Tested:
#    SR's - #27
#
# Written:  Michael Kass
#
# Revision History
# 	None
#

Viewpoint {
	position	0 0 4
	orientation	0 0 1  0
	description	"Front View"
}

Inline {
url "stack30.wrl"
}
