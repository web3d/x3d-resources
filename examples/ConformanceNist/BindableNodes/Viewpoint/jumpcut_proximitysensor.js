// jumpcut_proximitysensor.js

function checkEnter(value)
{
    if (view2.bindTime == value)
    {
        typestring0.string = enterTimeCorrect;
    }
    else
    {
        typestring0.string = enterTimeIncorrect;
    }
}

function checkExit(value)
{
    if (view1.bindTime == value)
    {
        typestring1.string = exitTimeCorrect;
    }
    else
    {
        typestring1.string = exitTimeIncorrect;
    }
}
