@echo off
set XEENA_HOME=C:\IBM\Xeena

rem SchemaProfileBuilder.bat
rem Run XEENA to build a new XML Schema profile

rem update schema from these draftsor later
rem http://www.w3.org/2000/10/XMLSchema.xsd -> .xml
rem http://www.w3.org/2000/10/XMLSchema.dtd
rem http://www.w3.org/2000/10/datatypes.xsd -> .xml
rem http://www.w3.org/2000/10/datatypes.dtd

echo -
echo update system and public identifiers in <defaults> tag:
echo file://localhost/C:/www.web3D.org/TaskGroups/x3d/translation/XMLSchema.dtd
echo http://www.w3.org/2000/10/datatypes.dtd
echo -
echo save as XMLSchema.profile
echo -

REM %XEENA_HOME%\xeena -cfg -dtd "%XEENA_HOME%\XMLSchema.dtd" -root "schema"

%XEENA_HOME%\xeena -cfg -dtd "C:\www.web3D.org\TaskGroups\x3d\translation\XMLSchema.dtd" -root "schema"

