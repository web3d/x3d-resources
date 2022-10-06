Sir,

	All you need to do to run the program is double-click the Project4.wrl file.  Both the PDUSender and PDUReader are 
fired up by the VRML file.  The code does not work unless the .jar file is included with the .class files.  Apparently,
the VRML browser needs the .class files that it calls directly to be in the directory.  I also found it necessary to 
include the directory that all the files are in my class path.

	I made the sphere move in a circular path just like I did in Project 1.  The path is impossible to predict with just
velocity values so the movement becomes jerky as the sphere snaps back to the path.  There are 3 dead reckoning algorithms
that the program uses.  The first simply uses the last known velocity so the sphere travels along line tangent to the path.
The second uses velocity and an approximated acceleration.  The approximated acceleration is the difference between the
last 2 known velocities.  The third and final algorithm uses the acceleration value passed in from the PDU Sender file.
The third algorithm performs the best.  All 3 algorithms send 2 packets per second.

Brian Hittner
