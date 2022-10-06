// speed_scaling.js

function largeScale(value)
{
    if (value == true)
    {
        rootTrans.scale = large;
        typestring0.set_string = largeScaleSetString;
        typestring1.set_string = zero;
//        viewFlag = true;
    }
}

function smallScale(value)
{
    if (value == true)
    {
        rootTrans.scale = small;
        typestring0.set_string = smallScaleSetString;
        typestring1.set_string = zero;
//        viewFlag = true;
    }
}

function normalScale(value)
{
    if (value == true)
    {
        rootTrans.scale = normal;
        typestring0.set_string = normalScaleSetString;
        typestring1.set_string = zero;
//        viewFlag = true;
    }
}

function printSpeed(value, ts)
{
    var deltaTime = ts - previousTime;
    if (deltaTime > 1)
    {
        var currentSpeed = Math.sqrt(Math.pow(value.x - previousLoc.x, 2) + 
                Math.pow(value.y - previousLoc.y, 2) + Math.pow(value.z - previousLoc.z, 2)) / deltaTime;
        typestring1.string = new MFString(new SFString('current speed = ' +
                currentSpeed));
        previousLoc = value;
        previousTime = ts;
    }

}

function printZero(value)
{
    if (currentLoc.x == previousLoc.x && currentLoc.y == previousLoc.y && currentLoc.z == previousLoc.z)
    {
        typestring1.string = zero;
    }
    else
    {
        currentLoc.x = previousLoc.x;
        currentLoc.y = previousLoc.y;
        currentLoc.z = previousLoc.z;
    }
}

function printSmallScale(value)
{
    if (value == true)
    {
        typestring0.string = setSmallScaleString;
    }
    else
    {
        typestring0.string = emptyString;
    }
}

function printNormalScale(value)
{
    if (value == true)
    {
        typestring0.string = setNormalScaleString;
    }
    else
    {
        typestring0.string = emptyString;
    }
}

function printLargeScale(value)
{
    if (value == true)
    {
        typestring0.string = setLargeScaleString;
    }
    else
    {
        typestring0.string = emptyString;
    }
}
