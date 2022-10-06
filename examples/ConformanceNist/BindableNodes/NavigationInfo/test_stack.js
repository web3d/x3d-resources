#VRML V2.0 utf8
#
# Module: Bindable_Nodes
# Node  : Fog
#
# test_stack.wrl - 
#
#  A basic test of "pushing" and "popping" the NavigationInfo node stack.
# The viewer may push and
# pop any combination of 3 different  NavigationInfo nodes onto the 
# stack, for up to 10 nodes.  The "popping" order should be the exact reverse of the "push" order.
# For example, pushing "EXAMINE", "FLY" and "WALK", should result in a 
# popping order of "WALK", "FLY" and "EXAMINE".
#
# Features Tested:
#    SR's - #23 - #29
#
# Written:  Michael Kass
#
# Revision History
# 	None
#

Viewpoint {
	position	0 0 3
	orientation	0 0 1  0
	description	"Front View"
}

Inline {
url "bigstack_script.wrl"
}
