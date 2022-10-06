function trigger_event (value)
{
	Browser.createVrmlFromURL(myurl, myself, 'nodesLoaded');
}
	
function nodesLoaded (value)
{
	root.addChildren = value ;
}

