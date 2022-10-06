// default_speed.js

function printSpeed(value, ts)
{
    var deltaTime = ts - previousTime;
    if (deltaTime > 1)
    {
        var currentSpeed = Math.sqrt(Math.pow(value.x - previousLoc.x, 2) + 
                Math.pow(value.y - previousLoc.y, 2) + Math.pow(value.z - previousLoc.z, 2)) / deltaTime;
        typestring1.string = new MFString(new SFString('current speed = ' + currentSpeed));
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
