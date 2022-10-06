# Program name:	Makefile
# Description:	Build Script node packages for VRML 97 conformance testing
# Author:	Don Brutzman
# Created:	13 May 2001
# Revised:	12 August 2001
# Invocation:	make all

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# References:
#
# Virtual Reality Modeling Language (VRML 97) International Standard ISO/IEC 14772-1:1997
#
# Annex B (normative) Java platform scripting reference
# http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html
#
# Annex C (normative) ECMAScript scripting reference
# http://www.web3D.org/technicalinfo/specifications/vrml97/part1/javascript.html

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

help:
	@echo "make --unix -i [ all | examples | stubs | documentation | zip ]"
	@echo ""
	@echo "[make is only needed for dis-java-vrml source developers]"
	@echo "[note that you can omit --unix in the invocation if you ]"
	@echo "[     set the environment variable MAKE_MODE = UNIX     ]"
	@echo ""

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

all:
	make -i verbose
	make -i clean
	make -i java
	make -i examples
	make -i documentation
	make -i zip

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#    set VERBOSE=-verbose
# or set VERBOSE=

VERBOSE=

verbose:
	@echo Makefile verbose mode setting:  $(VERBOSE)

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# clean out binaries and backups
# don't remove classes in subdirectory originalExamples/
	
clean:
	rm -f  *.class
	rm -rf vrml/*.class
	rm -f  *.'$$$$$$'
	rm -rf vrml/*.'$$$$$$'
	rm -f  _new_result.wrl
	rm -f  _new_pretty_print.html
	rm -f  Vrml97ScriptNodePackages.jar
	rm -f  ../ScriptConformance.zip

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# translate X3D scenes into VRML 97 and create pretty-print documentation HTML

examples:
	@echo -------------------------------------------------------------------------
	cd ../..; X3dToVrml97.bat 'examples\ScriptConformance\ScriptNodeEventOutControl-Java'		-same
	cd ../..; X3dToHtml.bat   'examples\ScriptConformance\ScriptNodeEventOutControl-Java'		-same
	@echo -------------------------------------------------------------------------
	cd ../..; X3dToVrml97.bat 'examples\ScriptConformance\ScriptNodeFieldControl-Java'		-same
	cd ../..; X3dToHtml.bat   'examples\ScriptConformance\ScriptNodeFieldControl-Java'		-same
	@echo -------------------------------------------------------------------------
	cd ../..; X3dToVrml97.bat 'examples\ScriptConformance\ScriptNodeEventOutControl-EcmaScript'	-same
	cd ../..; X3dToHtml.bat   'examples\ScriptConformance\ScriptNodeEventOutControl-EcmaScript'	-same
	@echo -------------------------------------------------------------------------
	cd ../..; X3dToVrml97.bat 'examples\ScriptConformance\ScriptNodeFieldControl-EcmaScript'	-same
	cd ../..; X3dToHtml.bat   'examples\ScriptConformance\ScriptNodeFieldControl-EcmaScript'	-same

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# vrml.* Java Script Authoring Interface (JSAI) stubs constructed by
#        Greg Seidman, Brown University/NIST gseidman@acm.org
#        to provide browser-independent vrml.* class access
#        http://zing.ncsl.nist.gov/~gseidman/vrml/JSAIstubs.zip

# Stub classes provide all packages, classes and method signatures
# specified by VRML 97 specification Annex B, Java

# setting classpath to local directory reveals only local vrml.* stub-class packages

java:
	javac $(VERBOSE) -deprecation -classpath . *.java vrml/*.java vrml/*/*.java

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

documentation:
#	mkdir javadoc
	javadoc -version -author -package						\
		-windowtitle      "VRML 97 Specification, Script Node Annex:  vrml.* Packages for Java"			\
		-doctitle "<center>VRML 97 Specification, Script Node Annex:  vrml.* Packages for Java</center>"	\
		-d javadoc								\
		-classpath .								\
		*.java									\
		vrml									\
		vrml.node								\
		vrml.field

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

zip:
	jar -cvf Vrml97ScriptNodePackages.jar	\
	 	 vrml/*.class 			\
		 vrml/*/*.class
	cd ..; jar -cvf ScriptConformance.zip ScriptConformance/*
	ls -l Vrml97ScriptNodePackages.jar ../ScriptConformance.zip

