#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : NavigationInfo
#
# false_instack.wrl - 
#
# A world with "WALK" and "FLY" NavigationInfo instances is defined, with an initial state in which
# neither NavigationInfo instance is bound.  To test the NavigationInfo stack for binding state when the
# a NavigationInfo instance already inside the stack is "popped", the VRML browser does the following::
# 
# 1) Indicate the "PUSH" button for the "WALK" NavigationInfo instance
# 2) Indicate the "PUSH" button for the "FLY" NavigationInfo instance
# 3) Indicate the "POP" button for the "WALK" NavigationInfo instance inside the stack
# 4) Indicate the "POP" button for the "FLY" NavigationInfo instance at the top of the stack
# 
# The result should be: No NavigationInfo is displayed.  All
# NavigationInfo instances should be popped off of the stack, the "WALK" NavigationInfo "isBound" eventout = FALSE,
# and the "FLY" NavigationInfo "isBound" eventOut = FALSE. The timestamp of the "FLY" NavigationInfo instance
# should be later than the timestamp of the "WALK" NavigationInfo instance.</DD>
#
#
# Features Tested:
#    SR's - #26
#
# Written:  Michael Kass
#
# Revision History
# 	None
#

Viewpoint {
  position 0 0 4
	orientation	0 0 1  0
	description	"Front View"
}

Inline {
url "stack28.wrl"
}
