# Program name:	Makefile
# Description:	Build LatticeXvl node packages
# Author:	Marc Jablonski, adapted from an original by Don Brutzman
# Revised:	21 January 2003
# Invocation:	make all

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# References:
#
# X3D LatticeXvl Extension
# http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

help:
	@echo "make -i [ all | kernel | latticexvl | jar | upload | documentation ]"
	@echo ""
	@echo "[make is only needed for LatticeXvl source developers   ]"
	@echo ""

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

all:
	make -i verbose
	make -i clean
	make -i kernel
	make -i latticexvl
	make -i jar
	make -i upload

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#    set VERBOSE=-verbose
# or set VERBOSE=

VERBOSE=

verbose:
	@echo Makefile verbose mode setting:  $(VERBOSE)

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# clean out binaries and backups
	
clean:
	rm -f  *.class
	rm -rf vrml/*.class
	rm -rf jp
	rm -rf javadoc
	rm -f  LatticeXvl.jar

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

kernel:
	@echo "build the Lattice Kernel Java classes - see Lv_Java_v0_31/BatOut/* for errors"
	cd Lv_Java_v0_31; JavacXVL3View.bat; cd ..; \
	rm -rf jp; cp -r Lv_Java_v0_31/CodeBase/jp .;
	pwd; ls -al Lv_Java_v0_31/BatOut/*; cat Lv_Java_v0_31/BatOut/*.txt

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# vrml.* Java Script Authoring Interface (JSAI) stubs constructed by
#        Greg Seidman, Brown University/NIST gseidman@acm.org
#        to provide browser-independent vrml.* class access
#        http://zing.ncsl.nist.gov/~gseidman/vrml/JSAIstubs.zip

# Stub classes provide all packages, classes and method signatures
# specified by VRML 97 specification Annex B, Java

# setting classpath to local directory reveals only local vrml.* stub-class packages

latticexvl:
	javac $(VERBOSE) -deprecation -classpath . *.java

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

jar:
	@echo "build the java archive (.jar) file, which can also be read as a .zip file"
	jar -vcf LatticeXvl.jar	\
		*.class				\
		jp/co/lattice/*/*.class			\
		jp/co/lattice/*/*/*.class		\
		jp/co/lattice/*/*/*/*.class
	@echo check result:
	ls -l *.jar

	
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

upload:
	cd ..; rm LatticeXvl.jar; cp javasrc/LatticeXvl.jar LatticeXvl.jar;
	
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
documentation:
	rm -rf javadoc; mkdir javadoc;
	javadoc -version -author -package						\
		-windowtitle      "LatticeXvl and Lattice Kernel"			\
		-doctitle "<center>LatticeXvl and Lattice Kernel</center>"	\
		-d javadoc								\
		-classpath .							\
		*.java									\
		Lv_Java_v0_31/*/*/*/*.java		\
		Lv_Java_v0_31/*/*/*/SubClass/*.java		\
		vrml									\
		vrml.node								\
		vrml.field								\

	
	

