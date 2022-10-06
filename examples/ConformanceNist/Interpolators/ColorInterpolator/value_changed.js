// value_changed.js
	
var white = new SFColor ( 1, 1, 1 );

function isOver (value) {
	
    if (value)
    {
        nodeout.set_diffuseColor = ( nodein.value_changed );
    }
    else
    {
        nodeout.set_diffuseColor =  white;
    }
}
	
function isOver2 (value) {
	
    if (value)
    {
        nodeout2.set_diffuseColor = ( nodein2.value_changed );
    }
    else
    {
        nodeout2.set_diffuseColor =  white;
    }
}


