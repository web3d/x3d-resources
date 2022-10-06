#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : NavigationInfo
#
# true_instack.wrl - 
#
# A world with "WALK" and "FLY" NavigationInfo instances is defined, with an initial state in which
# neither NavigationInfo instance is bound.  To test the NavigationInfo stack for binding state when the
# a NavigationInfo instance already inside the stack is "pushed", the VRML browser does the following::
#
# 1) Indicate the "PUSH" button for the "WALK" NavigationInfo instance
# 2) Indicate the "PUSH" button for the "FLY" NavigationInfo instance
# 3) Indicate the "PUSH" button for the "WALK" NavigationInfo instance again
#
# The result should be: The "WALK" NavigationInfo instance should be moved to the top of the stack.
# The "WALK" NavigationInfo instance should be displayed, and its "WALK" "isBound" eventout = TRUE.
# The "FLY" NavigationInfo "isBound" eventOut = FALSE. Both timestamps should be equal.</DD>
#
#
# Features Tested:
#    SR's - #23
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
url "stack29.wrl"
}
