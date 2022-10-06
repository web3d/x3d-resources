// addremove.js
// TODO where is parent scene using this script?

function addChildren(value) {

    if (value == true)
    {
        root.addChildren = Browser.createVrmlFromString('DEF MYBOX	Shape { ' +
                '			appearance Appearance { ' +
                ' 				material DEF BOXMATERIAL Material {} ' +
                ' 			} ' +
                ' 			geometry Box { ' +
                ' size 3 3 3 ' +
                ' 			} ' +
                ' 		} ');
    }
}

function removeChildren(value) {

    if (value == true)
    {
        root.removeChildren = root.children;
    }
}


