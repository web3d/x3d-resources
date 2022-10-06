// create_vrml_from_url_viewpoint.js

function trigger_event (value)
{
    Browser.createVrmlFromURL(root, myself, 'nodesLoaded');  // TODO root was originally undefined myurl?
}
	
function nodesLoaded (value)
{
    root.addChildren = value ;
}

