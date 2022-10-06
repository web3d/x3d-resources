#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : NavigationInfo
#
# false_topstack.wrl - 
#
# A world with "WALK" and "FLY" NavigationInfo node instances is defined, with an initial state in which
# neither NavigationInfo instance is bound.  To test the NavigationInfo stack for binding state when the
# top NavigationInfo is "popped", the VRML browser does the following::
#
# 1) Indicate the "PUSH" button for the "WALK" NavigationInfo instance
# 2) Indicate the "PUSH" button for the "FLY" NavigationInfo instance
# 3) Indicate the "POP" button for the same "FLY" NavigationInfo instance
# 
# The result should be: The "WALK" NavigationInfo is displayed, with its "isBound" eventOut = TRUE.
# The "FLY" NavigationInfo "isBound" eventOut = FALSE. Both timestamps for the "FLY" and "WALK" NavigationInfo
# should be equal.
#
# Features Tested:
#    SR's - #28
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
url "stack26.wrl"
}
