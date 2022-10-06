#VRML V2.0 utf8 
#
# NIST VTS v1.0
#
# Module:  Bindable_Nodes
# Node  :  Viewpoint
#
# file - jumpcut_collision.wrl - 
# 
# This world contains a collision node that should NOT send events during a jumpcut.
# The initial Viewpoin in this world is in front of a red Box geometry.  A second
# Viewpoint exists BEHIND the red Box geometry.  Indicating the Box with the pointing
# device should cause a "jumpcut" from the front to rear Viewpoint. During this 
# jumcput, collision detection should be disabled.  As a result, the Box geometry should
# remain a red color, indicating that no collision detection took place during the
# jumpcut.  If the red Box turns yellow, then that indicates that collision
# detection occured during the jumpcut, and the browser fails the test.
#
# Features Tested
#   SR's - #22
#
# Written by: Michael Kass                       
#
# Revision History
#      None
#

NavigationInfo {
type ["EXAMINE","WALK","FLY","ANY","NONE"]
}

Background { 
	skyColor 0 0 1
	groundColor [0 0.5 0, 0 0.5 0]
	groundAngle 1.57
}

DEF VIEW1 Viewpoint {
	position	0 0 15
	orientation	0 0 1  0
	description	"Front View"
	jump TRUE
}

DEF VIEW2 Viewpoint {
	position	0 0 -20
	orientation	0 0 1  0
	description	"Behind Origin"
	jump TRUE
}

DEF TIME0 TimeSensor {
	cycleInterval 2
}

DEF COLOR_INTERP ColorInterpolator {
	key [0, 0.5, 1]
	keyValue [1 1 0, 1 1 0, 1 1 0]
}

DEF TOUCH TouchSensor {
}

DEF COLLIDE1	Collision {
	children
	DEF TRANS1 Transform {
		children
		Shape {
			appearance Appearance {
				material DEF BOX1 Material {	
					diffuseColor 1 0 0
				}
			}
			geometry Box {
				size 3 3 3
			}
		}
	}
}

DEF TRANS2 Transform {
	translation 0 0 -100
	children
	Shape {
		appearance Appearance {
			material DEF BOX2 Material {	
				diffuseColor 0 1 1
			}
		}
		geometry Box {
			size 50 50 50
		}
	}
}


ROUTE TOUCH.isOver TO VIEW2.set_bind
ROUTE COLLIDE1.collideTime TO TIME0.startTime
ROUTE TIME0.fraction_changed TO COLOR_INTERP.set_fraction
ROUTE COLOR_INTERP.value_changed TO BOX1.set_diffuseColor







