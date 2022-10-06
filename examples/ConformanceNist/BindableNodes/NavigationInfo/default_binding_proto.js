function printType1(value)
{ 
	if ( value == true )
	{
		typestring1.string = new MFString ('PROTO NavigationInfo pushed,');
		typestring2.string = new MFString ('isBound = TRUE');
		typestring3.string = new MFString ('type = ');
		typestring4.string = nav1.type;
	}
	else
	{
		typestring1.string = new MFString ('PROTO NavigationInfo not pushed,');
		typestring2.string = new MFString ('isBound = FALSE');
	}
}
	
