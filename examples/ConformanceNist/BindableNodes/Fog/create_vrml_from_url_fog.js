// ConformanceNist\BindableNodes\Fog\create_vrml_from_url_fog.js

function trigger_event (value)
{
	Browser.createVrmlFromURL(myurl, myself, 'nodesLoaded');
}
	
function nodesLoaded (value)
{
	root.addChildren = value ;
}
