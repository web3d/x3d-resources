// scaling.js

function largeScale(value)
{
    if (value == true)
    {
        if (view1.isBound == false)
        {
             view1.set_bind = true;
            redfog.set_bind = true;
        }
    }
}


function smallScale(value)
{

    if (value == true)
    {
        if (view2.isBound == false)
        {
            view2.set_bind = true;
            redfog.set_bind = true;
        }
    }
}
	
	
