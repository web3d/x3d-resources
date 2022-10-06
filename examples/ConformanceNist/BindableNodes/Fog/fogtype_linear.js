// fogtype_linear.js

function set_fraction (value1) 
{
    if      (value1 > 0.0 && value1 <= 0.1)
    {
        text.set_string = new MFString('0.1');
    }
    else if (value1 > 0.1 && value1 <= 0.2)
    {
        text.set_string = new MFString('0.2');
    }
    else if (value1 > 0.2 && value1 <= 0.3)
    {
        text.set_string = new MFString('0.3');
    }
    else if (value1 > 0.3 && value1 <= 0.4)
    {
        text.set_string = new MFString('0.4');
    }
    else if (value1 > 0.4 && value1 <= 0.5)
    {
        text.set_string = new MFString('0.5');
    }
    else if (value1 > 0.5 && value1 <= 0.6)
    {
        text.set_string = new MFString('0.6');
    }
    else if (value1 > 0.6 && value1 <= 0.7)
    {
        text.set_string = new MFString('0.7');
    }
    else if (value1 > 0.7 && value1 <= 0.8)
    {
        text.set_string = new MFString('0.8');
    }
    else if (value1 > 0.8 && value1 <= 0.9)
    {
        text.set_string = new MFString('0.9');
    }
    else if (value1 > 0.9 && value1 <= 1.0)
    {
        text.set_string = new MFString('1.0');
    }
} 
