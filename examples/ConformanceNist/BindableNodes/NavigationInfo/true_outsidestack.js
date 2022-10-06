#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : NavigationInfo
#
# true_outsidestack.wrl - 
#
# A world with "WALK" and "FLY" NavigationInfo instances is defined, with an initial state in which
# neither NavigationInfo instance is bound.  To test the NavigationInfo stack for binding state when the
# a NavigationInfo instance not on the stack is "pushed", the VRML browser does the following::
#
# 1) Indicate the "PUSH" button for the "WALK" NavigationInfo instance
# 2) Indicate the "PUSH" button for the "FLY" NavigationInfo instance
#
# The result should be: The "FLY" NavigationInfo should be displayed. The
# "WALK" NavigationInfo "isBound" eventout = FALSE, and the "FLY" NavigationInfo "isBound" eventOut = TRUE.
# Both timestamps should be the same.
#
# Features Tested:
#    SR's - #25
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
url "stack31.wrl"
}
