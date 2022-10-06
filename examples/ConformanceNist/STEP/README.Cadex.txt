README.Cadex.txt

Add X3D DOCTYPE
X3D needs version 3.3, profile='CADInterchange' also schema information, i.e.

<!DOCTYPE X3D PUBLIC "ISO//Web3D//DTD X3D 3.3//EN" "http://www.web3d.org/specifications/x3d-3.3.dtd">
<X3D profile='CADInterchange' version='3.3' xmlns:xsd='http://www.w3.org/2001/XMLSchema-instance' xsd:noNamespaceSchemaLocation='http://www.web3d.org/specifications/x3d-3.3.xsd'>

CADInterchange profile already includes <component name="Navigation" level="1"/>

Capitalization of element: Head -> head

Include meta modified date, avoid abbreviation of month names, e.g.

    <meta content='24 February 2018' name='created'/>
    <meta content='11 March 2018' name='modified'/>

