// speed_range.js

function changeSpeed0(value)
{
    view0.set_bind = true;
    nav1.speed = 0.0;
    typestring1.string = zero;
    typestring2.string = speedSetToZeroString;
}

function changeSpeed100(value)
{
    view10.set_bind = true;
    nav1.speed = 100.0;
    typestring1.string = zero;
    typestring2.string = speedSetToHundredString;
}
function printSpeed(value, ts)
{
    var deltaTime = ts - previousTime;
    if (deltaTime > 1)
    {
        var currentSpeed = Math.sqrt(Math.pow(value.x - previousLoc.x, 2) + 
                Math.pow(value.y - previousLoc.y, 2) + Math.pow(value.z - previousLoc.z, 2)) / deltaTime;
        if (currentSpeed < 0.001)
        {
            typestring1.string = zero;
        }
        else
        {
            typestring1.string = new MFString(new SFString('current speed = ' + currentSpeed));
        }
        previousLoc = value;
        previousTime = ts;
    }
}

function printZeroSpeed(value)
{
    if (value == true)
    {
        typestring2.string = setZeroSpeedString;
    }
    else
    {
        typestring2.string = emptyString;
    }
}

function printHundredSpeed(value)
{
    if (value == true)
    {
        typestring2.string = setHundredSpeedString;
    }
    else
    {
        typestring2.string = emptyString;
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
