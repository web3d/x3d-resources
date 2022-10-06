// ConformanceNist\Sounds\Sound\direction.js

function colorEllipse1(value)
{
    if (value == true)
    {
        ellipsoid1Color.set_color = white;
        xMaterial.set_diffuseColor = sfWhite;
        sound1.set_direction = xAxis;

    }
    else
    {
        ellipsoid1Color.set_color = red;
        xMaterial.set_diffuseColor = sfRed;
    }
}

function colorEllipse2(value)
{
    if (value == true)
    {
        ellipsoid2Color.set_color = white;
        yMaterial.set_diffuseColor = sfWhite;
        sound1.set_direction = yAxis;
    }
    else
    {
        ellipsoid2Color.set_color = green;
        yMaterial.set_diffuseColor = sfGreen;
    }
}

function colorEllipse3(value)
{
    if (value == true)
    {
        ellipsoid3Color.set_color = white;
        zMaterial.set_diffuseColor = sfWhite;
        sound1.set_direction = zAxis;
    }
    else
    {
        ellipsoid3Color.set_color = blue;
        zMaterial.set_diffuseColor = sfBlue;
    }
}
function printOutsideMaxFront(value)
{
    if (value == true)
    {
        typestring1.string = outsideMaxFrontString;
    }
}
function printJustOutsideMaxFront(value)
{
    if (value == true)
    {
        typestring1.string = justOutsideMaxFrontString;
    }
}
function printBetweenFront(value)
{
    if (value == true)
    {
        typestring1.string = betweenFrontString;
    }
}
function printOutsideMaxRight(value)
{
    if (value == true)
    {
        typestring1.string = outsideMaxRightString;
    }
}
function printJustOutsideMaxRight(value)
{
    if (value == true)
    {
        typestring1.string = justOutsideMaxRightString;
    }
}
function printBetweenRight(value)
{
    if (value == true)
    {
        typestring1.string = betweenRightString;
    }
}
function printOutsideMaxRear(value)
{
    if (value == true)
    {
        typestring1.string = outsideMaxRearString;
    }
}

function printJustOutsideMaxRear(value)
{
    if (value == true)
    {
        typestring1.string = justOutsideMaxRearString;
    }
}
function printBetweenRear(value)
{
    if (value == true)
    {
        typestring1.string = betweenRearString;
    }
}
function printOutsideMaxLeft(value)
{
    if (value == true)
    {
        typestring1.string = outsideMaxLeftString;
    }
}

function printJustOutsideMaxLeft(value)
{
    if (value == true)
    {
        typestring1.string = justOutsideMaxLeftString;
    }
}
function printBetweenLeft(value)
{
    if (value == true)
    {
        typestring1.string = betweenLeftString;
    }
}
function printOutsideMaxTop(value)
{
    if (value == true)
    {
        typestring1.string = outsideMaxTopString;
    }
}
function printJustOutsideMaxTop(value)
{
    if (value == true)
    {
        typestring1.string = justOutsideMaxTopString;
    }
}
function printBetweenTop(value)
{
    if (value == true)
    {
        typestring1.string = betweenTopString;
    }
}
function printMinTop(value)
{
    if (value == true)
    {
        typestring1.string = minTopString;
    }
}
function printMinCenterTop(value)
{
    if (value == true)
    {
        typestring1.string = minCenterTopString;
    }
}
function printOutsideMaxBottom(value)
{
    if (value == true)
    {
        typestring1.string = outsideMaxBottomString;
    }
}
function printJustOutsideMaxBottom(value)
{
    if (value == true)
    {
        typestring1.string = justOutsideMaxBottomString;
    }
}
function printBetweenBottom(value)
{
    if (value == true)
    {
        typestring1.string = betweenBottomString;
    }
}
