	function trigger_event (value) {
	root.addChildren = Browser.createVrmlFromString( 
	
	' DEF NAV1 NavigationInfo { type \"EXAMINE\" }' + 
	
	'Transform { ' +
	'	translation -7 6 -8 ' + 
	' children ' +
		'[ Shape { ' + 
		'  		appearance Appearance { ' + 
		'     							material Material {  ' + 
		'																diffuseColor 1 1 1 ' + 
		'                            }' + 
		'                }' +
		'     geometry DEF TYPESTRING1 Text { ' + 
		'  																string \"Scripted NavigationInfo node not pushed\" ' + 
		'    													 } ' + 
		'  } ' + 
		'] ' + 
	'} ' +
	
		'Transform { ' +
	'	translation -4 5 -8 ' + 
	' children ' +
		'[ Shape { ' + 
		'  		appearance Appearance { ' + 
		'     							material Material {  ' + 
		'																diffuseColor 1 1 1 ' + 
		'                            }' + 
		'                }' +
		'     geometry DEF TYPESTRING2 Text { ' + 
		'  																string \"isBound = FALSE\" ' + 
		'    													 } ' + 
		'  } ' + 
		'] ' + 
	'} ' +

	'Transform { ' +
	'	translation -4 4 -8 ' + 
	' children ' +
		'[ Shape { ' + 
		'  		appearance Appearance { ' + 
		'     							material Material {  ' + 
		'																diffuseColor 1 1 1 ' + 
		'                            }' + 
		'                }' +
		'     geometry DEF TYPESTRING3 Text { ' +  
		'    													 } ' + 
		'  } ' + 
		'] ' + 
	'} ' +
	
		'Transform { ' +
	'	translation 0 4 -8 ' + 
	' children ' +
		'[ Shape { ' + 
		'  		appearance Appearance { ' + 
		'     							material Material {  ' + 
		'																diffuseColor 1 1 1 ' + 
		'                            }' + 
		'                }' +
		'     geometry DEF TYPESTRING4 Text { ' +  
		'    													 } ' + 
		'  } ' + 
		'] ' + 
	'} ' +
	
	'DEF MYSCRIPT2 Script ' +
	
	'{ field SFNode nav1 USE NAV1 ' +
	'  eventIn SFBool printType1 ' +
	'  field SFNode typestring1 USE TYPESTRING1 ' +
	'  field SFNode typestring2 USE TYPESTRING2 ' +
	'  field SFNode typestring3 USE TYPESTRING3 ' +
	'  field SFNode typestring4 USE TYPESTRING4 ' +
	'  field SFString string0 \"type = \" ' +
	'  field SFString string1 \"Scripted NavigationInfo node pushed \" ' +
	'  field SFString string2 \"isBound = TRUE \" ' +
	'  directOutput TRUE ' +
	'  url \"javascript: function printType1 (value) { ' +
	'  	if ( nav1.isBound == true ) ' +
	'   { typestring1.string = new MFString (string1);  ' +
	'			typestring2.string = new MFString (string2); ' +
	'     typestring3.string = new MFString (string0); ' +
	'     typestring4.string = nav1.type;} } \" } ' + 
	
	'ROUTE NAV1.isBound TO MYSCRIPT2.printType1 '); 
	}


