function trigger_event (value) {
	root.addChildren = Browser.createVrmlFromString (
	' DEF VIEW1 Viewpoint { ' + 
	' position 0 0 -10 ' +
	' orientation 0 1 0 3.14159 ' + 
	' description \"Rear View\" }' 
	); 
}
