	// http://www.web3d.org/x3d/content/examples/ConformanceNist/Sounds/Sound/switch_sounds.js
	
	function colorPhonograph (value)
	{
	if ( value == true )
	{
	phonoColor.set_diffuseColor = yellow;
	hornColor.set_diffuseColor = yellow;
	}
	else
	{
	phonoColor.set_diffuseColor = white;
	hornColor.set_diffuseColor = white;
	}
	}
	
	function switchIn (value)
	{
	mySwitch.set_whichChoice = 0;
	}
	
	function switchOut (value)
	{
	mySwitch.set_whichChoice = -1;
	}
	
