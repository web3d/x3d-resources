	function isOver1 (value) {
	
	if (value)
	{
	nodeout1.set_translation = (nodein1.value_changed) ;
	}
	else
	{
	nodeout1.set_translation = reset;
	}
	}
	
	function isOver2 (value) {
	
	if (value)
	{
	nodeout2.set_translation = (nodein2.value_changed) ;
	}
	else
	{
	nodeout2.set_translation = reset;
	}
	}

