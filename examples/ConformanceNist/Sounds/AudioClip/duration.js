// ConformanceNist\Sounds\AudioClip\duration.js

function colorPhonograph(value)
{
    if (value == true)
    {
        phonoColor.set_diffuseColor = yellow;
         hornColor.set_diffuseColor = yellow;
    }
    else
    {
        phonoColor.set_diffuseColor = white;
         hornColor.set_diffuseColor = white;
    }
}

function durationText(value)
{
    myText.string = new MFString('duration = ' + value.toString());
}

function newUrl(value, ts)
{
    mySound1.set_url = new MFString('chimes10.wav');
    mySound1.set_startTime = ts;
}

function oldUrl(value, ts)
{
    mySound1.set_url = new MFString('chimes.wav');
    mySound1.set_startTime = ts;
}
