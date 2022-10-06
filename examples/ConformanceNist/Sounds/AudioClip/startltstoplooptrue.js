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
	
	function set_stopTime (value)
	{
	mySound.set_stopTime = value + 12;
	}
	
