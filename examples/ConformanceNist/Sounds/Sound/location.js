// ConformanceNist\Sounds\Sound\location.js

function colorEllipse2(value)
{
    if (value == true)
    {
        ellipsoid2.set_color = yellow;
        sound1.set_location = near;
    }
    else
    {
        ellipsoid2.set_color = blue;
    }
}

function colorEllipse3(value)
{
    if (value == true)
    {
        ellipsoid3.set_color = yellow;
        sound1.set_location = far;
    }
    else
    {
        ellipsoid3.set_color = blue;
    }
}
