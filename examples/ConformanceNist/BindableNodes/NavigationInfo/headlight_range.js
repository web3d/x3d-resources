// ConformanceNist\BindableNodes\NavigationInfo\headlight_range.js

function toggle(value) {
    if (value == true)
    {
        if (nav1.headlight == true)
        {
            nav1.set_headlight = false;
        }
        else if (nav1.headlight == false)
        {
            nav1.set_headlight = true;
        }
    }
}