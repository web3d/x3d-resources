REM	Filename:	compile.bat
REM	Created:	18 DEC 2001
REM	Revised:	21 OCT 2019

REM cd to original directory, in case launched from HTML page:
cd \www.web3D.org\TaskGroups\x3d\translation\examples\DistributedInteractiveSimulation\CannonProject

javac   -verbose *.java

REM mkdir if needed as javadoc output destination
mkdir javadoc
javadoc -d javadoc   *.java

