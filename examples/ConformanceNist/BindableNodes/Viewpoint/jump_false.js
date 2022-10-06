// ConformanceNist\BindableNodes\Viewpoint\jump_false.js

function printPosition(value)
{
    typestring1.string = new MFString(new SFString('(' + value.x + ',' + value.y + ',' + value.z + ')'));
}
function printFrontView(value)
{
    if (value == true)
    {
        typestring2.set_string = frontIsBound;
    }
}
function printRightView(value)
{
    if (value == true)
    {
        typestring2.set_string = rightIsBound;
    }
}
