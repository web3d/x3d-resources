#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : NavigationInfo
#
# true_topstack.wrl - 
#
# A world with "WALK" and "FLY" NavigationInfo instances is defined, with an initial state in which
# neither NavigationInfo instance is bound.  To test the NavigationInfo stack for binding state when the
# top NavigationInfo instance is "pushed" again, the VRML browser does the following::
#
# 1) Indicate the "PUSH" button for the "WALK" NavigationInfo instance
# 2) Indicate the "PUSH" button for the "FLY" NavigationInfo instance
# 3) Indicate the "PUSH" button again for the same "FLY" NavigationInfo instance
#
# The result should be: No change in the stack should occur.
# The "FLY" NavigationInfo is displayed, with its "isBound" eventOut = TRUE.
# The "WALK" NavigationInfo "isBound" eventOut = FALSE. The timestamps of both
# NavigationInfo instances should be equal, indicating no change in the NavigationInfo stack.</DD>
#
#
# Features Tested:
#    SR's - #24
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
url "stack27.wrl"
}
