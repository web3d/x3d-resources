#
# Program name:	x3d/content/Makefile
#
# Description:	Perform a variety of X3D development tasks.
# Author:	Don Brutzman
# Revised:	16 April 2006
#
# Prerequisite: make utility from the gnu tools distribution
#               [Windows freeware available at http://sourceware.cygnus.com/cygwin]
#
# Location:	http://www.web3d.org/x3d/content/Makefile
# Example use:	http://www.web3d.org/x3d/content/Makefile.out
#
# ==============================================================================

help:
	@echo "make --unix -i [ all | compress | etc. ]"
	@echo ""


# ==============================================================================

#    set VERBOSE=-verbose
# or set VERBOSE=

VERBOSE=

verbose:
	@echo Makefile verbose mode setting:  $(VERBOSE)

# ==============================================================================

# You may need to edit some of these drives and directories to match your machine

# Xeena is very fussy/stupid about path/classpath, you may need to also edit xeena.bat there:

XEENA_PATH		= c:/IBM/Xeena

#JDK_CLASSPATH		= c:/jdk1.3.1_12
#JDK_CLASSPATH		= c:/j2sdk1.4.2_06
#JDK_CLASSPATH		= C:/jdk1.5.0
JDK_CLASSPATH		= 'C:/Program Files/Java/jdk1.5.0_05'

JAVA_DIR		= $(JDK_CLASSPATH)/bin

#JSWSDP_DIR		= C:/jwsdp-1.4.0
#JSWSDP_DIR		= C:/jwsdp-1.5
#JSWSDP_DIR		='C:/Program Files/Java/jwsdp-1.5'
JSWSDP_DIR		='C:/Sun/jwsdp-1.6'

VRTP_CLASSPATH		= /vrtp

CONTENT_DRIVE		= C:

CONTENT_DIR		= www.web3d.org/x3d/content

DOM_DIR			= $(CONTENT_DIR)/dom

TRANSITIONAL_DTD_DIR	= www.web3d.org/TaskGroups/x3d/translation

IMAGES_DIR		= www.web3d.org/x3d/content/images

EXAMPLES_DIR		= $(CONTENT_DRIVE)/$(CONTENT_DIR)/examples

EXAMPLES_SPEC_DIR	= $(CONTENT_DRIVE)/$(CONTENT_DIR)/examples/Basic/Vrml97Specification

SVG_DTD_stylable	= $(CONTENT_DRIVE)/$(CONTENT_DIR)/svg-20000303-stylable.dtd

SVG_DTD_exchange	= $(CONTENT_DRIVE)/$(CONTENT_DIR)/svg-20000303-exchange.dtd

WEBSITE_DIR		= http://www.web3d.org/x3d/content

X3D_DTD			= $(CONTENT_DRIVE)/$(CONTENT_DIR)/x3d-3.0.dtd

X3D_DTD_WEB		= $(WEBSITE_DIR)/x3d-3.0.dtd

X3D_COMPACT_DTD		= $(CONTENT_DRIVE)/$(CONTENT_DIR)/x3d-3.0.dtd

X3D_PROFILE		= x3d-3.0.profile.xml

X3D_COMPACT_PROFILE	= x3d-3.0.profile.xml

# xmlint.exe for XML syntax checking locatable via http://search.microsoft.com/us/dev

XMLINT_LOCATION		= c:/xml/microsoft

# Saxon for XSLT available at http://saxon.sourceforge.net

SAXON			= java -jar c:/xml/saxonb8-7j/saxon8.jar  
#SAXON			= java net.sf.saxon.Transform  -cp c:/xml/saxonb8-7j/saxon.jar 
#SAXON			= java -cp c:/xml/saxon6.5.4/saxon.jar com.icl.saxon.StyleSheet
# SAXON			= java -cp c:/xml/saxon6.5.3/saxon.jar com.icl.saxon.StyleSheet
# [saxon.bat fails on = conversions]
# SAXON			= c:/xml/saxon/saxon.bat
# [instant saxon requires Microsoft JVM]
# SAXON			= c:/xml/saxon/saxon.exe
# SAXON			= java -jar c:/xml/saxon/saxon7.jar

# Xeena doesn't care about the following...   - - - - - - - - - - - - - - - - - -

MAKE_MODE = UNIX

# Prerequisite:  Xj3D requires Java3D installed with the above JDK

Xj3D_PATH		= c:/Xj3D/x3d

XML4J_CLASSPATH		= /xml/xml4j_2_0_15/xml4j.jar

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
path:
	java -version
	@echo
	@echo X3D_DTD="$(X3D_DTD)"
	@ls -l $(X3D_DTD)
	@echo
	@echo Xj3D_PATH=
	@echo "$(Xj3D_PATH)"
	@echo
	@echo JDK_CLASSPATH=
	@echo "$(JDK_CLASSPATH)"
	@echo
	@echo XEENA_PATH=
	@echo "$(XEENA_PATH)"
	@echo
	@echo augmented original PATH=
	@echo "$(PATH)"
	@echo
	@echo original CLASSPATH=
	@echo "$(CLASSPATH)"
	@echo
	@echo environment variables:
	set
	@echo

classpath:
	@echo CLASSPATH="$(CLASSPATH)"
# keep 'java -version' last due to error-condition exit
	java -version

# ==============================================================================

all:
	@echo make all X3D examples and compress results
	date
	@echo =========================================================================
	@echo recent Makefile output maintained at
	@echo http://www.web3d.org/x3d/content/Makefile.out
	@echo =========================================================================
	make -i tooltips
	@echo =========================================================================
	make -i Vrml97ToX3dNistAll
	@echo =========================================================================
	make -i HelloWorld
	@echo =========================================================================
	make -i BinaryJava
	make -i BinaryCompressionExamples
	@echo =========================================================================
	make -i CADexamples
	@echo =========================================================================
	make -i course
	@echo =========================================================================
	make -i unlockSpecialScenes
	make -i buildSpecialScenes
	make -i lockSpecialScenes
	@echo =========================================================================
	make -i developmentExamples
	@echo =========================================================================
	make -i DIS
	@echo =========================================================================
	make -i EAI
	@echo =========================================================================
	make -i GeoSpatialExamples
	@echo =========================================================================
	make -i HumanoidAnimationExamples
	@echo =========================================================================
	make -i KelpForestExhibit
	@echo =========================================================================
	make -i LatticeXvl
	@echo =========================================================================
	make -i NURBS
	@echo =========================================================================
	make -i ScriptConformance
	@echo =========================================================================
	make -i StudentProjects
	@echo =========================================================================
	make -i UniversalMedia
	@echo =========================================================================
	make -i VrmlSourceBookExamples
	@echo =========================================================================
	make -i Vrml97Specification
	@echo =========================================================================
	make -i X3dSpecificationExamples
#	@echo =========================================================================
#	make -i Vrml97SpecificationExamplesToX3D
#	@echo =========================================================================
#	make -i wrapVrml97SpecificationExamples
#	@echo =========================================================================
#	make -i unwrapVrml97SpecificationExamples
#	@echo =========================================================================
#	make -i copyWrappedVersions
#	@echo =========================================================================
#	make -i Vrml97SpecificationExamplesFromX3D
#	@echo =========================================================================
#	make -i HtmlizeVrml97SpecificationExamples
	@echo =========================================================================
	make -i CreateWebPages
	@echo =========================================================================
	make -i xmlint
	@echo =========================================================================
	make -i xjc
	@echo =========================================================================
	make -i X3dDtdChecker
	@echo =========================================================================
	make -i XeenaBuild
	@echo =========================================================================
	make -i XenuLinkSleuth
	@echo =========================================================================
	make -i zipX3dSpecifications
	@echo =========================================================================
	make -i compress
	@echo =========================================================================

vorlon:
	vorlon -url *.wrl

build:
	make all

XeenaBuild:
	cd C:/www.web3d.org/x3d/content/Vrml97ToX3dNist; make all
	cd C:/Ibm/Xeena; make all

CreateWebPages:
	@echo =========================================================================
	cd dom; pwd; make -i ReferenceDocumentationLayout
	@echo =========================================================================
	make -i catalog

catalog:
	make -i ModelsCatalog
	make -i Vrml2.0SourcebookCatalog

catalog-fast:
	cd dom; pwd; make -i fast

catalog-fast-Sourcebook:
	cd dom; pwd; make -i fast-Sourcebook


ModelsCatalog:
	@echo =========================================================================
#	c:/enableDOM.bat
	rm -f  examples/Basic/BinaryCompression/_pages/*
	rm -f  examples/Basic/CAD/_pages/*
	rm -f  examples/Basic/course/_pages/*
	rm -f  examples/Basic/development/_pages/*
	rm -f  examples/Basic/DistributedInteractiveSimulation/_pages/*
	rm -f  examples/Basic/ExternalAuthoringInterface/_pages/*
	rm -f  examples/Basic/GeoSpatial/_pages/*
	rm -f  examples/Basic/HumanoidAnimation/_pages/*
	rm -f  examples/Basic/KelpForestExhibit/_pages/*
	rm -f  examples/Basic/LatticeXvl/_pages/*
	rm -f  examples/Basic/NURBS/_pages/*
	rm -f  examples/Basic/ScriptConformance/_pages/*
	rm -f  examples/Basic/StudentProjects/_pages/*
	rm -f  examples/Basic/UniversalMediaMaterials/_pages/*
	rm -f  examples/Basic/UniversalMediaPanoramas/_pages/*
	rm -f  examples/Basic/Vrml97Specification/_pages/*
	rm -f  examples/Basic/X3dSpecification/_pages/*
#
#	rm -f  examples/ConformanceNist/*/*/_pages/*
	rm -f  examples/Vrml2.0Sourcebook/*/*/_pages/*
#	rm -f  examples/Savage/*/*/_pages/*
#	rm -f  examples/SavageDefense/*/*/_pages/*
#	hide help-related .x3d files in root directories of subordinate distributions, so that they are not included in this catalog
	mv -f examples/ConformanceNist/HelloWorld.x3d      		examples/ConformanceNist/HelloWorld.x3d.hide
	mv -f examples/Savage/HelloWorld.x3d      		examples/Savage/HelloWorld.x3d.hide
	mv -f examples/Vrml2.0Sourcebook/HelloWorld.x3d		examples/Vrml2.0Sourcebook/HelloWorld.x3d.hide
#	invoke stylesheet catalog builder
	cd dom; pwd; make x3d-examples
#	unhide help-related .x3d files
	mv -f examples/ConformanceNist/HelloWorld.x3d.hide 		examples/ConformanceNist/HelloWorld.x3d
	mv -f examples/Savage/HelloWorld.x3d.hide 		examples/Savage/HelloWorld.x3d
	mv -f examples/Vrml2.0Sourcebook/HelloWorld.x3d.hide 	examples/Vrml2.0Sourcebook/HelloWorld.x3d
#	c:/disableDOM.bat

Vrml2.0SourcebookCatalog:
	@echo =========================================================================
#	c:/enableDOM.bat
	rm -f  examples/Vrml2.0Sourcebook/*/*/_pages/*
	cd dom; pwd; make -i Vrml2.0Sourcebook
	rm -rf   'examples\Vrml2.0Sourcebook\chapter.html'
#	c:/disableDOM.bat

ScriptConformance:
	@echo
	@echo [After updating source, save output as examples/Basic/ScriptConformance/Makefile.out]
	cd examples/Basic/ScriptConformance; make -i all


# ==============================================================================

AllVrml97Nodes:
	make AllVrml97NodesX3D-Edit

AllVrml97NodesX3D-Edit:
#	cd $(XEENA_PATH); pwd; ./xeena.bat  -dtd $(X3D_COMPACT_DTD)  -root X3D  -xml $(EXAMPLES_DIR)/development/AllVrml97Nodes.xml
	X3D-Edit-compact.bat $(EXAMPLES_DIR)/development/AllVrml97Nodes.xml

translate:
	make -i translation

translation:
	@echo Using Java3D Vrml2x3d to translate AllVrml97Nodes.wrl...
#	cd $(Xj3D_PATH)/release; cp x3d.jar ../..; ls -l ../../x3d.jar
	vorlon -nowarning $(EXAMPLES_DIR)/development/AllVrml97Nodes.wrl
#
	@echo $(X3D_DTD)
#	@echo local copy of Vrml2x3d.java edited to read local x3d-compromise.dtd
#	cd $(Xj3D_PATH)/simple; pwd;	javac  Vrml2x3d.java
# Xj3D not exiting properly in batch mode:
#		-Dmode="batch"
	rm -f $(EXAMPLES_DIR)/development/AllVrml97Nodes.xml
	cd $(Xj3D_PATH)/simple; pwd; 	java   $(VERBOSE)	\
		-DDTD="file:///$(X3D_COMPACT_DTD)"			\
		-Dmode="batch"						\
		Vrml2x3d  file:///$(EXAMPLES_DIR)/development/AllVrml97Nodes.wrl
# > $(EXAMPLES_DIR)/development/AllVrml97Nodes.xml
	ls -al $(EXAMPLES_DIR)/development/AllVrml97Nodes.wrl $(EXAMPLES_DIR)/development/AllVrml97Nodes.xml
	xmlint examples\development\AllVrml97Nodes.xml
#	make save
#	make AllVrml97NodesX3D-Edit
	rm -f $(EXAMPLES_DIR)/development/AllVrml97Nodes.vorlon.txt
	vorlon -nowarning $(EXAMPLES_DIR)/development/AllVrml97Nodes.wrl > $(EXAMPLES_DIR)/development/AllVrml97Nodes.vorlon.txt
	make -i saxon
	make -i xmlint

# ==============================================================================

save:
#	"Save as" bug in SimpleVrml97Viewer ignores changed directory when saving:
	cp $(Xj3D_PATH)/simple/AllVrml97Nodes.xml examples/Basic/development/
	cp examples/Basic/development/AllVrml97Nodes.wrl            examples/Basic/development/AllVrml97Nodes.wrl.txt
	cp examples/Basic/development/AllVrml97NodesTranslated.wrl  examples/Basic/development/AllVrml97NodesTranslated.wrl.txt
	cp examples/Basic/development/AllVrml97Nodes.xml            examples/Basic/development/AllVrml97Nodes.xml.txt
	ls -l examples/Basic/development/AllVrml97Nodes.* x3d-compromise*
	cp x3d-compromise.dtd x3d-compromise.dtd.txt
	cp examples/Basic/X3DRetreatProtoExercise.xml examples/Basic/X3DRetreatProtoExercise.xml.txt

# ==============================================================================



CoreProfileExample:
	cd $(XEENA_PATH); pwd; ./xeena.bat  -dtd $(X3D_DTD)  -root X3D  -xml $(EXAMPLES_DIR)/CoreProfileExample.xml

ECMA:
	vorlon ECMAScriptAnnexExample2.wrl
	cd $(Xj3D_PATH)/simple; pwd; 	java   $(VERBOSE)  Vrml2x3d	\
		$(EXAMPLES_DIR)/ECMAScriptAnnexExample2.wrl
	cp $(Xj3D_PATH)/simple/ECMAScriptAnnexExample2.xml .

HelloWorld.old:
	cd $(Xj3D_PATH)/simple; pwd; 	java   $(VERBOSE)  Vrml2x3d	\
		$(EXAMPLES_DIR)/course/HelloX3dAuthors.wrl
	cp $(Xj3D_PATH)/simple/HelloWorld.xml .

multi:
	cd $(XEENA_PATH); pwd; ./xeena.bat  -dtd $(X3D_DTD)  -root X3D  -xml $(EXAMPLES_DIR)/MultiTextureExamples-1.xml
	cd $(EXAMPLES_DIR); pwd; mv MultiTextureExamples-1.xml MultiTextureExamples-rename.xml
	cd $(EXAMPLES_DIR); pwd; mv MultiTextureExamples-rename.xml MultiTextureExamples-1.xml

layout:
	cd layout; pwd; ./xeena.bat  -dtd $(X3D_DTD)  -root X3D  -xml $(EXAMPLES_DIR)/newScene.xml

profile-check:
	$(XMLINT_LOCATION)/xmlint $(X3D_PROFILE)
	$(XMLINT_LOCATION)/xmlint *.xslt

quad:
	X3dToVrml97.bat 'examples\development\QuadTreeExamples' -same
	X3dToHtml.bat   'examples\development\QuadTreeExamples' -same
	cp examples/Basic/development/QuadTreeExamples.xml examples/Basic/development/QuadTreeExamples.xml.txt




SpinGroupInternal:
	X3dToVrml97.bat 'examples\development\SpinGroupInternalSubsetDeclaration' -same
	X3dToHtml.bat   'examples\development\SpinGroupInternalSubsetDeclaration' -same
	cp examples/Basic/development/SpinGroupInternalSubsetDeclaration.xml examples/Basic/development/SpinGroupInternalSubsetDeclaration.xml.txt

reform:
	cd $(XEENA_PATH); pwd; ./xeena.bat  -dtd c:/vrtp/org/web3d/vrtp/dabp/reform.dtd  -root PROTOCOL_DESCRIPTION -xml c:/vrtp/org/web3d/vrtp/dabp/reform.xml

rick:
	X3dToVrml97.bat 'examples\ProtoMFNodeSensorExample'
	X3dToVrml97.bat 'examples\rickP'

saxon:
#	$(SAXON) -t -o HelloWorldStyled.wrl HelloWorld.xml X3dToVrml97.xslt
#	vorlon HelloWorldStyled.wrl -nowarnings
#	saxon.bat -t -o ExampleEXTERNPROTO-1Styled.wrl ExampleEXTERNPROTO-1.xml X3dToVrml97.xslt
#	vorlon ExampleEXTERNPROTO-1Styled.wrl -nowarnings
#	saxon.bat -t -o PROTOexampleStyled.wrl PROTOexample.xml X3dToVrml97.xslt
#	vorlon PROTOexampleStyled.wrl -nowarnings
	$(SAXON) -t -o examples/Basic/AllVrml97NodesStyled.wrl examples/Basic/AllVrml97Nodes.xml X3dToVrml97.xslt
	vorlon  examples/Basic/development/AllVrml97NodesStyled.wrl -nowarnings
	vpp.exe examples/Basic/development/AllVrml97NodesStyled.wrl > examples/Basic/AllVrml97NodesStyledPretty.wrl
	vorlon  examples/Basic/development/AllVrml97NodesStyledPretty.wrl -nowarnings

SimpleVrml97Viewer:
	vorlon -nowarning *.wrl
	cd $(Xj3D_PATH)/simple; pwd; 					\
		java  $(VERBOSE)  SimpleVrml97Viewer	\
		$(EXAMPLES_DIR)/development/AllVrml97Nodes.wrl
#		-dtd $(X3D_DTD)
	make save

schema-x3d-build:
	SchemaX3dProfileBuilder.bat

schema-x3d-edit:
	SchemaX3dEditor.bat

simple:
	make SimpleViewer

SimpleViewer:
	cd $(Xj3D_PATH)/simple; pwd; java  $(VERBOSE)  SimpleVrml97Viewer parts/hello_earth.wrl

HelloWorldVrml2x3d:
	cd $(Xj3D_PATH)/simple; pwd; 	java   $(VERBOSE)			\
		-DDTD="file:///www.web3d.org/x3d/content/x3d-compact.dtd"	\
		Vrml2x3d  file:///$(EXAMPLES_DIR)/HelloWorld.wrl

#		-Dmode="batch"								\

wrappers:
	X3D-Edit.bat $(EXAMPLES_DIR)/WrapperTagsExample.xml
	$(SAXON) -t -o examples/Basic/WrapperTagsExampleUnwrapped.xml   examples/Basic/WrapperTagsExample.xml  X3dUnwrap.xslt



wraptest:
# X3dWrap.xslt
# X3dWrapTextMode.xslt
#
#	$(SAXON) -t -o examples/Basic/WrapperTagsExampleWrapped.xml examples/Basic/WrapperTagsExample.xml     X3dWrap.xslt
#	$(SAXON) -t -o examples/Basic/HelloWorldWrapped.xml         examples/Basic/HelloWorld.xml             X3dWrap.xslt
	$(SAXON) -t -o examples/Basic/AllVrml97NodesWrapped.xml     examples/Basic/AllVrml97Nodes.xml         X3dWrap.xslt
	$(SAXON) -t -o examples/Basic/AllVrml97NodesUnwrapped.xml   examples/Basic/AllVrml97NodesWrapped.xml  X3dUnwrap.xslt
	$(XMLINT_LOCATION)/xmlint 'examples\AllVrml97NodesWrapped.xml'
	$(XMLINT_LOCATION)/xmlint 'examples\AllVrml97NodesUnwrapped.xml'

X3D-Edit:
	cd $(XEENA_PATH); pwd; xeena.bat  -dtd $(X3D_DTD)  -root X3D  -xml $(EXAMPLES_DIR)/newScene.xml

X3D-Edit-config:
	@echo "Don't forget to update "  $(X3D_PROFILE)
	@echo "Watch out, X3D-Edit will clobber the parameter entities in the DTD fragment."
	cd $(XEENA_PATH); pwd; xeena.bat  -cfg -dtd $(X3D_DTD)

SVG-Edit-config:
	cp svg*.dtd C:/IBM/Xeena/
	ls C:/IBM/Xeena/svg*.profile
	cp C:/IBM/Xeena/svg*.profile .
	@echo "Xeena configuration mode is buggy!  Create new document to create new profile."
	cd $(XEENA_PATH); pwd; xeena.bat  -cfg -dtd $(SVG_DTD_stylable)
	cd $(XEENA_PATH); pwd; xeena.bat  -cfg -dtd $(SVG_DTD_exchange)
	@echo Ensure the edited profiles (if any) are copied back to x3d/content directory

GeoVRML-config:
	cp GeoVRML.dtd C:/IBM/Xeena/
	ls C:/IBM/Xeena/GeoVRML.profile
	cp C:/IBM/Xeena/GeoVRML.profile .
	@echo "Xeena configuration mode is buggy!  Create new document to create new profile."
	cd $(XEENA_PATH); pwd; xeena.bat  -cfg -dtd GeoVRML.dtd
	@echo Ensure the edited profiles (if any) are copied back to x3d/content directory

X3D-Edit-web:
	cd $(XEENA_PATH); pwd; ./xeena.bat  -dtd $(X3D_DTD_WEB)  -root X3D  -xml $(EXAMPLES_DIR)/newScene.xml

xeena-config:
#	used for creating new Xeena profiles, dangerous on existing profiles
#	seems to work best when you edit the DTD in the same directory as xeena.bat itself
	cd $(XEENA_PATH); pwd; ./xeena.bat  -cfg  -dtd $(X3D_DTD)  -root X3D
#	cd $(XEENA_PATH); pwd; ./xeena.bat  -cfg  -dtd $(EXAMPLES_DIR)/multi.dtd  -root X3D
#	cd $(XEENA_PATH); pwd; ./xeena.bat  -cfg  -dtd f:/P2.2/ato.dtd  -root PROTOCOL_DESCRIPTION
# -xml AllVrml97Nodes.profile

dbp:
	cd $(XEENA_PATH); pwd; xeena.bat  -cfg  -dtd $(EXAMPLES_DIR)/../DynamicBehaviorProtocol.dtd  -root Protocol
	cd $(XEENA_PATH); pwd; xeena.bat        -dtd $(EXAMPLES_DIR)/../DynamicBehaviorProtocol.dtd

X3DView:
	cd /java3D/x3d/x3dview; pwd;  java  -classpath '../release/x3d.jar;/java3D/xml-tr2/xml.jar;.' X3DViewFramed $(EXAMPLES_DIR)/AllVrml97Nodes.xml

xmlviewer:
	c:/xml/xmlviewer/xmlviewer.bat

XSLEditor:
	XSLEditor.bat

# best to run xmlint from command line, bad interactions apparently due to use of stderr

xmlint:
	make -i xmlint-all

xmlint-all:
	$(XMLINT_LOCATION)/xmlint $(X3D_PROFILE)
	$(XMLINT_LOCATION)/xmlint $(X3D_COMPACT_PROFILE)
	$(XMLINT_LOCATION)/xmlint *.xslt
	cd examples;									pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/course;								pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/development;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/DistributedInteractiveSimulation;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/ExternalAuthoringInterface;						pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/GeoSpatial;								pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/HumanoidAnimation;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/KelpForestExhibit;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/LatticeXvl;								pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/NURBS;								pwd; $(XMLINT_LOCATION)/xmlint *.x3d
#	cd examples/Basic/PeriscopeTrainer;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/ScriptConformance;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/StudentProjects;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/UniversalMediaMaterials;						pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/UniversalMediaPanoramas;						pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/Vrml97Specification;						pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter02-Introduction;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter04-Text;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes;			pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale;	pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces;			pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter16-Color;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter22-Background;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter26-Viewpoint;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision;	pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter28-Anchor;					pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter29-WorldInfo;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes;				pwd; $(XMLINT_LOCATION)/xmlint *.x3d
	cd examples/Basic/X3dSpecification;							pwd; $(XMLINT_LOCATION)/xmlint *.x3d

xt:
	xt.bat c:/web3D/x3d/content/HelloWorld.xml $(EXAMPLES_DIR)/X3dToVrml97.xslt

xalan:
	java org.apache.xalan.xslt.Process -IN examples/HelloWorld.xml -XSL X3dToVrml97.xslt -OUT examples/HelloWorldXalan.wrl
	vorlon examples/HelloWorldXalan.wrl

# ==============================================================================

test2:
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_2' -DTD x3d-compact.dtd
	cp 'examples\Vrml97Specification\exampleD_2Translated.xml' 'examples\Vrml97Specification\exampleD_2TranslatedXj3D.xml'
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_2TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_2Translated.xml' X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_2TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_2Translated.xml' X3dUnwrap.xslt
	cp 'examples\Vrml97Specification\exampleD_2TranslatedWrapped.xml' 'examples\Vrml97Specification\exampleD_2Translated.xml'
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_2Translated'
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_2Translated'

Vrml97SpecificationExamplesToX3D:
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_2' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_3' -DTD x3d-compact.dtd
	@echo superfluous ROUTE bug in exampleD_4, manually corrected...
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_4' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_9' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_10' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_11' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_12' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_13_2' -DTD x3d-compact.dtd
	echo problem 'with exampleD_13_3 source not exiting, skipping...'
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_13_3' -DTD x3d-compact.dtd
	echo problem 'with exampleD_14..16 and 19, skipping...'
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_17' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_18' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\chopperbody' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\chopperrotor' -DTD x3d-compact.dtd
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\refractivematerial' -DTD x3d-compact.dtd
	Vrml97ToX3d.bat 'examples\Vrml97Specification\rotor' -DTD x3d-compact.dtd
#	cp 'examples\Vrml97Specification\exampleD_2Translated.xml'		'examples\Vrml97Specification\exampleD_2TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_3Translated.xml'		'examples\Vrml97Specification\exampleD_3TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_4Translated.xml'		'examples\Vrml97Specification\exampleD_4TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_9Translated.xml'		'examples\Vrml97Specification\exampleD_9TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_10Translated.xml'		'examples\Vrml97Specification\exampleD_10TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_11Translated.xml'		'examples\Vrml97Specification\exampleD_11TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_12Translated.xml'		'examples\Vrml97Specification\exampleD_12TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_13_2Translated.xml'		'examples\Vrml97Specification\exampleD_13_2TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_13_3Translated.xml'		'examples\Vrml97Specification\exampleD_13_3TranslatedXj3D.xml'
#	echo problem 'with exampleD_14..16 and 19, skipping...'
#	cp 'examples\Vrml97Specification\exampleD_17Translated.xml'		'examples\Vrml97Specification\exampleD_17TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\exampleD_18Translated.xml'		'examples\Vrml97Specification\exampleD_18TranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\chopperbodyTranslated.xml'		'examples\Vrml97Specification\chopperbodyTranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\chopperrotorTranslated.xml'		'examples\Vrml97Specification\chopperrotorTranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\refractivematerialTranslated.xml'	'examples\Vrml97Specification\refractivematerialTranslatedXj3D.xml'
#	cp 'examples\Vrml97Specification\rotorTranslated.xml'			'examples\Vrml97Specification\rotorTranslatedXj3D.xml'

wrapVrml97SpecificationExamples:
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_2TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_2Translated.xml'		X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_3TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_3Translated.xml'		X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_4TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_4Translated.xml'		X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_5AuthoredX3dWrapped.xml'		'examples\Vrml97Specification\exampleD_5AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_6AuthoredX3dWrapped.xml'		'examples\Vrml97Specification\exampleD_6AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_7AuthoredX3dWrapped.xml'		'examples\Vrml97Specification\exampleD_7AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_8AuthoredX3dWrapped.xml'		'examples\Vrml97Specification\exampleD_8AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\refractivematerialAuthoredX3dWrapped.xml'	'examples\Vrml97Specification\refractivematerialAuthoredX3d.xml' X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_9TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_9Translated.xml'		X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_10TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_10Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_11TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_11Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_12TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_12Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_13_2TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_13_2Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_13_3TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_13_3Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_14AuthoredX3dWrapped.xml'	'examples\Vrml97Specification\exampleD_14AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_15AuthoredX3dWrapped.xml'	'examples\Vrml97Specification\exampleD_15AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_16AuthoredX3dWrapped.xml'	'examples\Vrml97Specification\exampleD_16AuthoredX3d.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\chopperbodyTranslatedWrapped.xml'		'examples\Vrml97Specification\chopperbodyTranslated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\chopperrotorTranslatedWrapped.xml'	'examples\Vrml97Specification\chopperrotorTranslated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\rotorAuthoredX3dWrapped.xml'      	'examples\Vrml97Specification\rotorAuthoredX3d.xml'		X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_17TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_17Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_18TranslatedWrapped.xml'		'examples\Vrml97Specification\exampleD_18Translated.xml'	X3dWrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_19AuthoredX3dWrapped.xml'	'examples\Vrml97Specification\exampleD_19AuthoredX3d.xml'	X3dWrap.xslt

unwrapVrml97SpecificationExamples:
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_2TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_2Translated.xml'		X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_3TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_3Translated.xml'		X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_4TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_4Translated.xml'		X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_5AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_5AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_6AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_6AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_7AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_7AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_8AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_8AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\refractivematerialAuthoredX3dUnwrapped.xml' 'examples\Vrml97Specification\refractivematerialAuthoredX3d.xml' X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_9TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_9Translated.xml'		X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_10TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_10Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_11TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_11Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_12TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_12Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_13_2TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_13_2Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_13_3TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_13_3Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_14AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_14AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_15AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_15AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_16AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_16AuthoredX3d.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\chopperbodyTranslatedUnwrapped.xml'	'examples\Vrml97Specification\chopperbodyTranslated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\chopperrotorTranslatedUnwrapped.xml'	'examples\Vrml97Specification\chopperrotorTranslated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\rotorAuthoredX3dUnwrapped.xml'      	'examples\Vrml97Specification\rotorAuthoredX3d.xml'		X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_17TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_17Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_18TranslatedUnwrapped.xml'	'examples\Vrml97Specification\exampleD_18Translated.xml'	X3dUnwrap.xslt
	$(SAXON) -t -o 'examples\Vrml97Specification\exampleD_19AuthoredX3dUnwrapped.xml'	'examples\Vrml97Specification\exampleD_19AuthoredX3d.xml'
	.xslt

copyWrappedVersions:
	cp 'examples\Vrml97Specification\exampleD_2TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_2Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_3TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_3Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_4TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_4Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_9TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_9Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_10TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_10Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_11TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_11Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_12TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_12Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_13_2TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_13_2Translated.xml'
#	cp 'examples\Vrml97Specification\exampleD_13_3TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_13_3Translated.xml'
#	echo problem 'with exampleD_14..16 and 19, skipping...'
	cp 'examples\Vrml97Specification\exampleD_17TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_17Translated.xml'
	cp 'examples\Vrml97Specification\exampleD_18TranslatedWrapped.xml'	'examples\Vrml97Specification\exampleD_18Translated.xml'
	cp 'examples\Vrml97Specification\chopperbodyTranslatedWrapped.xml'	'examples\Vrml97Specification\chopperbodyTranslated.xml'
	cp 'examples\Vrml97Specification\chopperrotorTranslatedWrapped.xml'	'examples\Vrml97Specification\chopperrotorTranslated.xml'

Vrml97SpecificationExamplesFromX3D:
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_2Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_3Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_4AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_5AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_6AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_7AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\refractivematerialAuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_8AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_9Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_10Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_11Translated'	-same
# problems with color values getting truncated and routed fields not having set_:
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_12Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_13_2Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_13_3Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_14AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_15AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_16AuthoredX3d'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\chopperbodyTranslated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\chopperrotorTranslated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\rotorAuthoredX3d'		-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_17Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_18Translated'	-same
	X3dToVrml97.bat 'examples\Vrml97Specification\exampleD_19AuthoredX3d'	-same

HtmlizeVrml97SpecificationExamples:
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_2Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_3Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_4AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_5AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_6AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_7AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\refractivematerialAuthoredX3d'	-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_8AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_9Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_10Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_11Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_12Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_2Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_3Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_14AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_15AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_16AuthoredX3d'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\rotorAuthoredX3d'			-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperbodyTranslated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperrotorTranslated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_17Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_18Translated'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_19AuthoredX3d'		-same
#
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_2TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_3TranslatedWrapped'		-same
#	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_4TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_5AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_6AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_7AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\refractivematerialAuthoredX3dWrapped'	-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_8AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_9TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_10TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_11TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_12TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_2TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_3TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_14AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_15AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_16AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\rotorAuthoredX3dWrapped'			-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperbodyTranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperrotorTranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_17TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_18TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_19AuthoredX3dWrapped'		-same
#
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_2TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_3TranslatedUnwrapped'		-same
#	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_4TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_5AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_6AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_7AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\refractivematerialAuthoredX3dUnwrapped'	-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_8AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_9TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_10TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_11TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_12TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_2TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_3TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_14AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_15AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_16AuthoredX3dUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\rotorAuthoredX3dUnwrapped'			-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperbodyTranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperrotorTranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_17TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_18TranslatedUnwrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_19AuthoredX3dUnwrapped'		-same
#
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_2TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_3TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_4AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_5AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_6AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_7AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\refractivematerialAuthoredX3dWrapped'	-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_8AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_9TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_10TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_11TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_12TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_2TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_3TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_14AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_15AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_16AuthoredX3dWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\rotorAuthoredX3dWrapped'			-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperbodyTranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\chopperrotorTranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_17TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_18TranslatedWrapped'		-same
	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_19AuthoredX3dWrapped'		-same

#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_5'
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_6'
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_7'
#
#	exampleD_13_2 hangs without giving a reason, skipping...
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_13_2'
#	X3dToHtml.bat 'examples\Vrml97Specification\exampleD_13_2Translated'
#
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_14'
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_15'
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_16'
#	Vrml97ToX3d.bat 'examples\Vrml97Specification\exampleD_19'

old:
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_5AuthoredX3dAndTranslatedBack.wrl  > ExampleD_5AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_6AuthoredX3dAndTranslatedBack.wrl  > ExampleD_6AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_7AuthoredX3dAndTranslatedBack.wrl  > ExampleD_7AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_14AuthoredX3dAndTranslatedBack.wrl > ExampleD_14AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_15AuthoredX3dAndTranslatedBack.wrl > ExampleD_15AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_16AuthoredX3dAndTranslatedBack.wrl > ExampleD_16AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; ../vpp ExampleD_19AuthoredX3dAndTranslatedBack.wrl > ExampleD_19AuthoredX3dAndTranslatedBackPretty.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_5AuthoredX3dAndTranslatedBackPretty.wrl  ExampleD_5AuthoredX3dAndTranslatedBack.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_6AuthoredX3dAndTranslatedBackPretty.wrl  ExampleD_6AuthoredX3dAndTranslatedBack.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_7AuthoredX3dAndTranslatedBackPretty.wrl  ExampleD_7AuthoredX3dAndTranslatedBack.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_14AuthoredX3dAndTranslatedBackPretty.wrl ExampleD_14AuthoredX3dAndTranslatedBack.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_15AuthoredX3dAndTranslatedBackPretty.wrl ExampleD_15AuthoredX3dAndTranslatedBack.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_16AuthoredX3dAndTranslatedBackPretty.wrl ExampleD_16AuthoredX3dAndTranslatedBack.wrl
	cd examples\Vrml97Specification; pwd; mv  -f ExampleD_19AuthoredX3dAndTranslatedBackPretty.wrl ExampleD_19AuthoredX3dAndTranslatedBack.wrl

specExplicitExample:
	cd $(Xj3D_PATH)/simple; pwd; 	java   $(VERBOSE)			\
		-DDTD="file:///www.web3d.org/x3d/content/x3d-compact.dtd"	\
		-Dmode="batch"								\
		Vrml2x3d  file:///$(EXAMPLES_SPEC_DIR)/exampleD_2.wrl > exampleD_2Compact.xml
	ls -l $(Xj3D_PATH)/simple/exampleD_2Compact.xml
	cd $(Xj3D_PATH)/simple; $(XMLINT_LOCATION)/xmlint exampleD_2Compact.xml
	X3dWrap $(Xj3D_PATH)/simple/exampleD_2Compact.xml
	ls -l $(Xj3D_PATH)/simple/exampleD_2*.xml

nancyTranslate:
	cd $(Xj3D_PATH)/simple; pwd; 	java   $(VERBOSE)			\
		-DDTD="file:///www.web3d.org/x3d/content/x3d-compact.dtd"	\
		-Dmode="batch"								\
		Vrml2x3d  file:///vrtp/demo/helicopter/nancy_h-anim.wrl

#		> /vrtp/demo/helicopter/nancy_h-anim.xml
#	X3DWrap  \vrtp\demo\helicopter\nancy_h-anim


# ==============================================================================

unwrap:
	X3dUnwrap.bat 	'examples.compact\newScene'										-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\A1_GeoElevationGrid'								-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\A2_AnimatedGeoViewpoint'							-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\A3_GeoInline'									-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\A4_GeoLocation'								-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\A5_GeoOrigin'									-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\GeoSpatialExternProtoDeclarations'						-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\newGeoSpatialScene'								-same
	X3dUnwrap.bat 	'examples.compact\GeoSpatial\temple'									-same
	X3dUnwrap.bat 	'examples.compact\HelloWorld'										-same
	X3dUnwrap.bat 	'examples.compact\HumanoidAnimation\NancyNativeTags'							-same
	X3dUnwrap.bat 	'examples.compact\HumanoidAnimation\NancyPrototypes'							-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\AircraftFixedWing\RussiaBear\bear'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\AircraftHelicopters\RussiaHelix\helix'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\AircraftHelicopters\US-CH-53\CH-53E'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\AircraftMiscellaneous\Balloon\HunsbergerHotAirBalloon'		-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\AircraftMiscellaneous\Blimp\mphadley_blimp_2.0'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Biologics\Dolphin\Dolphin'						-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Environment\TimeOfDay\TimeOfDay'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Periscope\CircleLinesExample'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Periscope\CircleLinesExample60'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\FishingTrawler\Trawler'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\MerchantLivestockCarrier\MerchantLivestockCarrier'		-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\RunningLights\Lights&ShipsTestWorld'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\RunningLights\RunningLightsExternalProtoTest'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\RunningLights\RunningLightsGeneric'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\RunningLights\SupertankerMasterFile'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\RunningLights\SupertankerWorkingModel'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny-initial\Sovremenny'		-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny-initial\Superstructure'		-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny-initial\YardArm'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\AK130_Gun'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\AK630_Gun'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\AntennaMast'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\CaptainsBarge'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\Globe_Raydome'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\MR114_Radar'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\SA-N-7_Missile'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\SS-N-22_Missile'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\Sovremenny'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\Superstructure'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\T_Cylinder_Raydome'		-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\Y_Base_Raydome'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\YardArm'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\YardArmBackup'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\YardArmBlack'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-Destroyer-Sovremenny\helix'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-PatrolCraft-Nanuchka-Lighthouse\Lighthouse-02'		-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-PatrolCraft-Nanuchka-Lighthouse\Nanuchka Missile Corvette with Lighthouse'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Russia-PatrolCraft-Nanuchka-Lighthouse\Nanuchka Missile Corvette'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\RunningLightsExternalProtoTest'			-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\RunningLightsGeneric'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\SubAndTanker'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\SupertankerMasterFile'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\SupertankerWorkingModel'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\TA_nodes'						-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\US_sub'						-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\Supertanker\supertanker'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-Carrier-Independence\TowerFinal'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-Carrier-Independence\independence'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-Carrier-Independence\independence2'				-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-Cruiser\AntennaMast'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-Cruiser\cruise-final'					-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-Cruiser\ugur'						-same
	X3dUnwrap.bat 	'examples.compact\PeriscopeTrainer\Ships\US-PatrolTextWithAxes'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.04ListTextStringsAndLengths'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.04ListTextStringsAndLengthsWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.05TextLimitedByMaximumExtent'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.05TextLimitedByMaximumExtentWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.06aTextPlainSerifFont'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.06aTextPlainSerifFontWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.06bTextBoldSerifFont'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.06bTextBoldSerifFontWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.07SmallText'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.07SmallTextWithAxes'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.08aTextMoreLineSpacing'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.08aTextMoreLineSpacingWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.08bTextLessLineSpacing'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.08bTextLessLineSpacingWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.09TwoTextShapesDifferentJustification'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.09TwoTextShapesDifferentJustificationWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.10TwoLinesTextFlatBox'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.10TwoLinesTextFlatBoxWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.11TextOverstrikeFont'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter04-Text\Figure04.11TextOverstrikeFontWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.03bCylinderTranslation'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.03bCylinderTranslationWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.04bCylinderTranslation'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.04bCylinderTranslationWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.10Archway'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.10ArchwayWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.11NestedArchways'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter05-PositioningShapes\Figure05.11NestedArchwaysWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.04bRotation45xWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.05bRotation-45xWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.06bRotation45YWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.07bRotation-45ZWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.08Asterisk3dWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.09TripleAsterisk3dWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.10SimpleArch'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.11DeskLampBase'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter06-RotatingShapes\Figure06.12DeskLampBaseTwoArms'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.04bScale2xWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.05bScale0.5xWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.06bScale2yWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.07bScale0.5zWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.08bScale2x_0.5y_4zWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.09SimpleSpaceshipBodyWingsWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.10SimpleSpaceship'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.11ScaledArches'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.12aSimpleTree'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.12bVerticallyScaledTree'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter07-ScalingShapes\Figure07.13DiagonallyScaledTree'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter08-AnimatingPositionOrientationScale\Figure08.3PositionInterpolator'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter08-AnimatingPositionOrientationScale\Figure08.3PositionInterpolatorWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter08-AnimatingPositionOrientationScale\Figure08.4OrientationInterpolator'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter08-AnimatingPositionOrientationScale\Figure08.4OrientationInterpolatorWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter08-AnimatingPositionOrientationScale\Figure08.5AnimatingScale'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter08-AnimatingPositionOrientationScale\Figure08.5AnimatingScaleWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.1SpinningCubeTouchSensor'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.1SpinningCubeTouchSensorWithAxes'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.2SpinningCubeTouchSensorClicked'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.2SpinningCubeTouchSensorClickedWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.3ConePlaneSensor'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.3ConePlaneSensorWithAxes'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.4ConePlaneSensorConstrained'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.4ConePlaneSensorConstrainedWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.5FourStackingBlocks'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.5FourStackingBlocksWithAxes'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.6SphereSensor'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.6SphereSensorWithAxes'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.7CylinderSensor'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.7CylinderSensorWithAxes'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.8ClickDragTouchSensorPlaneSensor'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.8ClickDragTouchSensorPlaneSensorWithAxes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.9DeskLamp'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter09-SensingViewer\Figure09.9DeskLampWithAxes'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.04BrightRedSphere'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.05MultiColoredSpaceStation'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.06PulsatingSpaceStation'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.07LightBulb'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.08BlinkingSphere'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.09ThreeBlinkingShapes'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.10SemiTransparentSpaceStation'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter10-Materials\Figure10.11AnimatingTransparencySpaceStation'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter11-Grouping\Figure11.1CafeSignSwitchResolutions'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter11-Grouping\Figure11.2CafeSignBillboard'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter11-Grouping\Figure11.3BoundingBox'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter12-Inline\Figure12.1SimpleArch'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter12-Inline\Figure12.2InlinedArches'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter12-Inline\Figure12.3TempleOfInlinedArches'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.10PointSetBoxCorners'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.11IndexedLineSetBoxWireframe'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.12IndexedFaceSetCube'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.13IndexedFaceSetLightningBolt'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.14VaultedCeilingPieceCreased'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.15VaultedCeilingPiece'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.16VaultedCeiling'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter13-PointsLinesFaces\Figure13.17MorphingCube'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter14-ElevationGrid\Figure14.2ElevationGridMountain'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter14-ElevationGrid\Figure14.3ElevationGridRibbon'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter14-ElevationGrid\Figure14.4aElevationGridPuddleSplash10x10'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter14-ElevationGrid\Figure14.4bElevationGridPuddleSplash40x40'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter14-ElevationGrid\Figure14.5ElevationGridTerrain'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter14-ElevationGrid\Figure14.6ElevationGridMedallion'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.07ExtrudedCylinder'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.08ExtrudedHalfCylinderWithCaps'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.08ExtrudedHalfCylinderWithCapsWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.09ExtrudedHalfCylinderWithoutCaps'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.09ExtrudedHalfCylinderWithoutCapsWithAxes'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.10ExtrudedRoom'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.10ExtrudedRoomWithAxes'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.11ExtrudedPlaygroundSlide'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0SoursparencyTree'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter17-Textures\Figure17.11bGreyTextureGreenMaterialStripedCylinder'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter17-Textures\Figure17.12bWoodTextures'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter17-Textures\Figure17.13SimplePixelTexture'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter17-Textures\Figure17.14bMovieTextureWhirlpool'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.19TextureCoordinateTest'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.20bTextureCoordinatePizzaSlice'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.21TextureCoordinatePizzaSlice'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.22bTextureCoordinateVideoWall'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.23bTextureCoordinateGrillwork'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.24TextureCoordinateRotatedGrillworkFence'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.25bTextureTransformBrickBox'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.26bUncorrectedTextureSanDiegoBay'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.26cCorrectedTextureSanDiegoBay'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter18-TextureMapping\Figure18.27RotatingFanAnimatedTextureTransform'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.21NormalSquare'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.22NormalSquareShaded'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.23HalfColumn'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.24aHalfColumnCreaseAngle'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.24bTwoHalfColumnsCreaseAngleSeam'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.25aHalfColumnNormals'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.25bTwoHalfColumnsNormalsSeamless'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.26NormalShadedElevationGrid'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter19-NormalsShading\Figure19.27SquareFaceAnimatingNormals'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.04HeadlightSpheres'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.05FlatMesh'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.06aPointLightSpheres'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.06bPointLightSpheresIntensity'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.07PointLightSpheresAttenuation'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.08PointLightSpheresSmallRadius'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.09GlowingOrbArchwaysExhibit'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.10BlinkingRedLightbulb'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.11aDirectionalLightAimingRightThroughSpheres'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.11bDirectionalLightAimingUpperRightThroughSpheres'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.12TwoDirectionalLightsAimingLeftRightThroughSpheres'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.13aDirectionalLightSunrise'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.13bDirectionalLightNoon'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.13cDirectionalLightSunset'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.14DirectionalLightSunAnimated'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.15aSpotLightSpheres'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.15bWideAngleSpotLightSpheres'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.16SpotLightBeamWidthComparison'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.17aSimpleWorldNoShadows'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter20-Lighting\Figure20.17bSimpleWorldFakeShadows'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter21-ShinyMaterials\Figure21.4aDullGreyVase'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter21-ShinyMaterials\Figure21.4bSomewhatShinyGreyVase'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter21-ShinyMaterials\Figure21.4cShinyGreyVase'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter21-ShinyMaterials\Figure21.4dVeryShinyGreyVase'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter21-ShinyMaterials\Figure21.5ShinyGoldVase'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter22-Background\Figure22.1BackgroundSkySphere'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter22-Background\Figure22.2BackgroundSkyGround'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter22-Background\Figure22.3cBackgroundSkyGroundMountains'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter22-Background\Figure22.4BackgroundBinding'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.1FogTestWorld'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.2aLinearFogVisibility0'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.2bLinearFogVisibility40'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.2cLinearFogVisibility30'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.2dLinearFogVisibility20'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.3aExponentialFogVisibility100'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.3bExponentialFogVisibility40'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.3cExponentialFogVisibility20'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.4BlackFogVisibility40'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter23-Fog\Figure23.5BindingFogsAndBackgrounds'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.1EmitterMarkers'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.2AmbientSoundEmitterMarkers'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.3TouchSensorTriggeredSound'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.4FourKeyKeyboard'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.5TwoAmbientCirclingSounds'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.6DirectedAmbientSound'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter24-Sound\Figure24.7VirtualTV'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.01TorchHighDetail'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.02TorchMediumDetail'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.03TorchLowDetail'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.04ThreeTorchesSideBySide'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.05ThreeTorchesSingleLOD'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.06DungeonRoomFloor'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.07DungeonRoomWall'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.08DungeonRoom'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.09TwoDungeonRoomsWithLODHiding'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.10DungeonWallWithDoorway'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.11DungeonDoor'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter25-LevelOfDetail\Figure25.12DungeonRoomsWithSlidingDoors'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter26-Viewpoint\Figure26.5DungeonViewpoint'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter26-Viewpoint\Figure26.7DungeonViewpointsAndFieldsOfView'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter27-SensingVisibilityProximityCollision\Figure27.1VisibilitySensorDungeonSlidingDoors'	-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter27-SensingVisibilityProximityCollision\Figure27.2ProximitySensorDungeonSlidingDoors'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter27-SensingVisibilityProximityCollision\Figure27.3CollisionDetectionSpinningOrb'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter28-Anchor\Figure28.1AnchorViewpointsDungeon'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter28-Anchor\Figure28.2AnchorLoadAnotherWorld'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter29-WorldInfo\Figure29.1WorldInfo'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter30-Scripts\Figure30.1ScriptSlidingBall'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter30-Scripts\Figure30.2ScriptBackgroundSelect'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter30-Scripts\Figure30.3ScriptSoundActivate'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter30-Scripts\Figure30.4ScriptSpiralingBallTrace'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter30-Scripts\Figure30.5ScriptTorusBuilder'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.1WallColorPrototype'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.2MaterialPrototypesLibrary'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.3MaterialExternalPrototypes'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.4WoodAppearancePrototypesLibrary'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.5WoodAppearanceExternalPrototypes'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.6DonutPrototype'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.7DonutExternalPrototype'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml2.0Sourcebook\Chapter31-Prototypes\Figure31.9SpinGroupPrototype'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\chopperbodyTranslated'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\chopperbodyTranslatedUnwrapped'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\chopperbodyTranslatedWrapped'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\chopperrotorTranslated'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\chopperrotorTranslatedUnwrapped'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\chopperrotorTranslatedWrapped'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\chopperbodyTranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\chopperrotorTranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_10TranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_11TranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_12TranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_13_2TranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_17TranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_18TranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_2TranslatedXj3D'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_3TranslatedXj3D'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\exampleD_9TranslatedXj3D'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\compact\refractivematerialTranslatedXj3D'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_10Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_10TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_10TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_11Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_11TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_11TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_12Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_12TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_12TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_13_2Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_13_2TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_13_2TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_13_3Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_13_3TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_13_3TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_14AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_14AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_14AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_14TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_15AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_15AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_15AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_16AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_16AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_16AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_17Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_17TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_17TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_18Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_18TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_18TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_19AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_19AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_19AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_2Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_2TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_2TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_3Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_3TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_3TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_4AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_4Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_4TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_4TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_5AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_5AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_5AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_6AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_6AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_6AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_6TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_6Unwrapped'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_7AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_7AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_7AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_8AuthoredX3d'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_8AuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_8AuthoredX3dWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_9Translated'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_9TranslatedUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\exampleD_9TranslatedWrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\refractivematerialAuthoredX3d'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\refractivematerialAuthoredX3dUnwrapped'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\refractivematerialAuthoredX3dWrapped'		-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\rotorAuthoredX3d'					-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\rotorAuthoredX3dUnwrapped'			-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\rotorAuthoredX3dWrapped'				-same
	X3dUnwrap.bat 	'examples.compact\Vrml97Specification\rotorTranslated'					-same
	X3dUnwrap.bat 	'examples.compact\course\BeckyRoadOverpass'						-same
	X3dUnwrap.bat 	'examples.compact\course\CoordinateAxes'						-same
	X3dUnwrap.bat 	'examples.compact\course\CoordinateAxesInlineExample'					-same
	X3dUnwrap.bat 	'examples.compact\course\ExplodingBuilding'						-same
	X3dUnwrap.bat 	'examples.compact\course\ExtrusionCrossSectionExampleShip'				-same
	X3dUnwrap.bat 	'examples.compact\course\ExtrusionCrossSectionPrototype'				-same
	X3dUnwrap.bat 	'examples.compact\course\GridXY_20x20Movable'						-same
	X3dUnwrap.bat 	'examples.compact\course\GridXZ_20x20Fixed'						-same
	X3dUnwrap.bat 	'examples.compact\course\GridXZ_20x20Movable'						-same
	X3dUnwrap.bat 	'examples.compact\course\GridYZ_20x20Movable'						-same
	X3dUnwrap.bat 	'examples.compact\course\GridsExample'							-same
	X3dUnwrap.bat 	'examples.compact\course\GroupFormation'						-same
	X3dUnwrap.bat 	'examples.compact\course\HelloX3dAuthors'						-same
	X3dUnwrap.bat 	'examples.compact\course\PositionInterpolatorExample'					-same
	X3dUnwrap.bat 	'examples.compact\course\SimpleExtrusionShipHull'					-same
	X3dUnwrap.bat 	'examples.compact\course\cetus\cetus'							-same
	X3dUnwrap.bat 	'examples.compact\course\compact\pyramidUnwrapped'					-same
	X3dUnwrap.bat 	'examples.compact\course\geometryExample'						-same
	X3dUnwrap.bat 	'examples.compact\course\pyramid'							-same
	X3dUnwrap.bat 	'examples.compact\course\pyramidWithAxes'						-same
	X3dUnwrap.bat 	'examples.compact\course\simpleCarrierHull'						-same
	X3dUnwrap.bat 	'examples.compact\development\AllVrml97Nodes'						-same
	X3dUnwrap.bat 	'examples.compact\development\AmelaExampleScene'					-same
	X3dUnwrap.bat 	'examples.compact\development\BradenProtoInitializeExample'				-same
#	CoreProfileExample has parameter entity definitions and requires conversion by hand
#	X3dUnwrap.bat 	'examples.compact\development\CoreProfileExample'					-same
	X3dUnwrap.bat 	'examples.compact\development\ECMAScriptAnnexExample2'					-same
	X3dUnwrap.bat 	'examples.compact\development\ExampleExternProto-1'					-same
	X3dUnwrap.bat 	'examples.compact\development\ExampleExternProto-2'					-same
	X3dUnwrap.bat 	'examples.compact\development\HelloSiggraph2000'					-same
	X3dUnwrap.bat 	'examples.compact\development\HelloWWW9'						-same
	X3dUnwrap.bat 	'examples.compact\development\HelloWorld'						-same
	X3dUnwrap.bat 	'examples.compact\development\Orbiter'							-same
	X3dUnwrap.bat 	'examples.compact\development\ProtoInstanceExample'					-same
	X3dUnwrap.bat 	'examples.compact\development\ProtoMFNodeSensorExample'					-same
	X3dUnwrap.bat 	'examples.compact\development\ProtoTagDtdDeclarationExamples'				-same
	X3dUnwrap.bat 	'examples.compact\development\ProxyShapeExample'					-same
	X3dUnwrap.bat 	'examples.compact\development\QuadTreeExamples'						-same
	X3dUnwrap.bat 	'examples.compact\development\SceneNodePrototype'					-same
	X3dUnwrap.bat 	'examples.compact\development\ScriptExample'						-same
	X3dUnwrap.bat 	'examples.compact\development\ShapeChildRoutingExample'					-same
#	SpinGroupInternalSubsetDeclaration has parameter entity definitions and requires conversion by hand
#	X3dUnwrap.bat 	'examples.compact\development\SpinGroupInternalSubsetDeclaration'			-same
	X3dUnwrap.bat 	'examples.compact\development\SpinGroupParameterEntityDeclaration'			-same
	X3dUnwrap.bat 	'examples.compact\development\TestFieldChecks'						-same
	X3dUnwrap.bat 	'examples.compact\development\USEexample'						-same
	X3dUnwrap.bat 	'examples.compact\development\WelcomeToWWW9'						-same
	X3dUnwrap.bat 	'examples.compact\development\WrapperTagsExample'					-same
	X3dUnwrap.bat 	'examples.compact\development\X3dRetreatProtoExercise'					-same
#	mv examples examples.compromise
	mv examples.compact examples
	make -i all
#	mv examples examples.compact
#	mv examples.compromise examples

convertSriCampus:
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-0\world.wrl"	 "SriCampus0.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-1\world.wrl"	 "SriCampus1.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-2\world.wrl"	 "SriCampus2.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-3\world.wrl"	 "SriCampus3.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-4\world.wrl"	 "SriCampus4.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-5\world.wrl"	 "SriCampus5.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-6\world.wrl"	 "SriCampus6.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-7\world.wrl"	 "SriCampus7.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-8\world.wrl"	 "SriCampus8.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-9\world.wrl"	 "SriCampus9.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-10\world.wrl"	 "SriCampus10.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-11\world.wrl"	 "SriCampus11.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-12\world.wrl"	 "SriCampus12.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-13\world.wrl"	 "SriCampus13.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-14\world.wrl"	 "SriCampus14.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-15\world.wrl"	 "SriCampus15.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-16\world.wrl"	 "SriCampus16.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-17\world.wrl"	 "SriCampus17.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-18\world.wrl"	 "SriCampus18.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-19\world.wrl"	 "SriCampus19.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-20\world.wrl"	 "SriCampus20.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-21\world.wrl"	 "SriCampus21.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-22\world.wrl"	 "SriCampus22.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-23\world.wrl"	 "SriCampus23.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-24\world.wrl"	 "SriCampus24.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-25\world.wrl"	 "SriCampus25.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-26\world.wrl"	 "SriCampus26.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-27\world.wrl"	 "SriCampus27.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-28\world.wrl"	 "SriCampus28.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-29\world.wrl"	 "SriCampus29.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-30\world.wrl"	 "SriCampus30.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-31\world.wrl"	 "SriCampus31.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-32\world.wrl"	 "SriCampus32.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-33\world.wrl"	 "SriCampus33.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-34\world.wrl"	 "SriCampus34.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-35\world.wrl"	 "SriCampus35.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-36\world.wrl"	 "SriCampus36.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-37\world.wrl"	 "SriCampus37.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-38\world.wrl"	 "SriCampus38.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-39\world.wrl"	 "SriCampus39.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-40\world.wrl"	 "SriCampus40.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-41\world.wrl"	 "SriCampus41.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-42\world.wrl"	 "SriCampus42.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-43\world.wrl"	 "SriCampus43.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-44\world.wrl"	 "SriCampus44.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-45\world.wrl"	 "SriCampus45.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-46\world.wrl"	 "SriCampus46.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-47\world.wrl"	 "SriCampus47.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-48\world.wrl"	 "SriCampus48.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-49\world.wrl"	 "SriCampus49.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-50\world.wrl"	 "SriCampus50.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-51\world.wrl"	 "SriCampus51.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-52\world.wrl"	 "SriCampus52.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-53\world.wrl"	 "SriCampus53.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-54\world.wrl"	 "SriCampus54.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-55\world.wrl"	 "SriCampus55.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-56\world.wrl"	 "SriCampus56.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-57\world.wrl"	 "SriCampus57.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-58\world.wrl"	 "SriCampus58.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-59\world.wrl"	 "SriCampus59.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-60\world.wrl"	 "SriCampus60.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-61\world.wrl"	 "SriCampus61.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-62\world.wrl"	 "SriCampus62.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-63\world.wrl"	 "SriCampus63.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-64\world.wrl"	 "SriCampus64.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-65\world.wrl"	 "SriCampus65.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-66\world.wrl"	 "SriCampus66.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-67\world.wrl"	 "SriCampus67.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-68\world.wrl"	 "SriCampus68.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-69\world.wrl"	 "SriCampus69.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-70\world.wrl"	 "SriCampus70.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-71\world.wrl"	 "SriCampus71.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-72\world.wrl"	 "SriCampus72.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-73\world.wrl"	 "SriCampus73.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-74\world.wrl"	 "SriCampus74.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-75\world.wrl"	 "SriCampus75.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-76\world.wrl"	 "SriCampus76.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-77\world.wrl"	 "SriCampus77.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-78\world.wrl"	 "SriCampus78.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-79\world.wrl"	 "SriCampus79.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-80\world.wrl"	 "SriCampus80.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-81\world.wrl"	 "SriCampus81.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-82\world.wrl"	 "SriCampus82.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-83\world.wrl"	 "SriCampus83.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-84\world.wrl"	 "SriCampus84.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-85\world.wrl"	 "SriCampus85.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-86\world.wrl"	 "SriCampus86.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-87\world.wrl"	 "SriCampus87.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-88\world.wrl"	 "SriCampus88.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-89\world.wrl"	 "SriCampus89.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-90\world.wrl"	 "SriCampus90.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-91\world.wrl"	 "SriCampus91.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-92\world.wrl"	 "SriCampus92.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-93\world.wrl"	 "SriCampus93.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-94\world.wrl"	 "SriCampus94.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-95\world.wrl"	 "SriCampus95.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-96\world.wrl"	 "SriCampus96.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-97\world.wrl"	 "SriCampus97.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-98\world.wrl"	 "SriCampus98.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-99\world.wrl"	 "SriCampus99.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-100\world.wrl"	 "SriCampus100.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-101\world.wrl"	 "SriCampus101.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-102\world.wrl"	 "SriCampus102.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-103\world.wrl"	 "SriCampus103.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-104\world.wrl"	 "SriCampus104.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-105\world.wrl"	 "SriCampus105.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-106\world.wrl"	 "SriCampus106.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-107\world.wrl"	 "SriCampus107.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-108\world.wrl"	 "SriCampus108.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-109\world.wrl"	 "SriCampus109.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-110\world.wrl"	 "SriCampus110.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-111\world.wrl"	 "SriCampus111.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-112\world.wrl"	 "SriCampus112.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-113\world.wrl"	 "SriCampus113.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-114\world.wrl"	 "SriCampus114.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-115\world.wrl"	 "SriCampus115.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-116\world.wrl"	 "SriCampus116.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-117\world.wrl"	 "SriCampus117.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-118\world.wrl"	 "SriCampus118.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-119\world.wrl"	 "SriCampus119.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-120\world.wrl"	 "SriCampus120.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-121\world.wrl"	 "SriCampus121.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-122\world.wrl"	 "SriCampus122.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-123\world.wrl"	 "SriCampus123.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-124\world.wrl"	 "SriCampus124.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-125\world.wrl"	 "SriCampus125.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-126\world.wrl"	 "SriCampus126.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-127\world.wrl"	 "SriCampus127.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-128\world.wrl"	 "SriCampus128.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-129\world.wrl"	 "SriCampus129.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-130\world.wrl"	 "SriCampus130.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-131\world.wrl"	 "SriCampus131.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-132\world.wrl"	 "SriCampus132.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-133\world.wrl"	 "SriCampus133.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-134\world.wrl"	 "SriCampus134.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-135\world.wrl"	 "SriCampus135.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-136\world.wrl"	 "SriCampus136.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-137\world.wrl"	 "SriCampus137.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-138\world.wrl"	 "SriCampus138.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-139\world.wrl"	 "SriCampus139.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-140\world.wrl"	 "SriCampus140.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-141\world.wrl"	 "SriCampus141.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-142\world.wrl"	 "SriCampus142.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-143\world.wrl"	 "SriCampus143.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-144\world.wrl"	 "SriCampus144.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-145\world.wrl"	 "SriCampus145.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-146\world.wrl"	 "SriCampus146.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-147\world.wrl"	 "SriCampus147.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-148\world.wrl"	 "SriCampus148.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-149\world.wrl"	 "SriCampus149.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-150\world.wrl"	 "SriCampus150.x3d"
	cd examples/Basic/GeoSpatial/; pwd;	../../Vrml97ToX3dNist.bat	"GeoVrmlSiteExamples\sricampus\object-151\world.wrl"	 "SriCampus151.x3d"

sriCampusRenumber:
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus0.x3d	 SriCampus000.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus1.x3d	 SriCampus001.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus2.x3d	 SriCampus002.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus3.x3d	 SriCampus003.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus4.x3d	 SriCampus004.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus5.x3d	 SriCampus005.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus6.x3d	 SriCampus006.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus7.x3d	 SriCampus007.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus8.x3d	 SriCampus008.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus9.x3d	 SriCampus009.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus10.x3d	 SriCampus010.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus11.x3d	 SriCampus011.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus12.x3d	 SriCampus012.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus13.x3d	 SriCampus013.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus14.x3d	 SriCampus014.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus15.x3d	 SriCampus015.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus16.x3d	 SriCampus016.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus17.x3d	 SriCampus017.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus18.x3d	 SriCampus018.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus19.x3d	 SriCampus019.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus20.x3d	 SriCampus020.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus21.x3d	 SriCampus021.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus22.x3d	 SriCampus022.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus23.x3d	 SriCampus023.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus24.x3d	 SriCampus024.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus25.x3d	 SriCampus025.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus26.x3d	 SriCampus026.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus27.x3d	 SriCampus027.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus28.x3d	 SriCampus028.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus29.x3d	 SriCampus029.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus30.x3d	 SriCampus030.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus31.x3d	 SriCampus031.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus32.x3d	 SriCampus032.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus33.x3d	 SriCampus033.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus34.x3d	 SriCampus034.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus35.x3d	 SriCampus035.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus36.x3d	 SriCampus036.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus37.x3d	 SriCampus037.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus38.x3d	 SriCampus038.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus39.x3d	 SriCampus039.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus40.x3d	 SriCampus040.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus41.x3d	 SriCampus041.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus42.x3d	 SriCampus042.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus43.x3d	 SriCampus043.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus44.x3d	 SriCampus044.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus45.x3d	 SriCampus045.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus46.x3d	 SriCampus046.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus47.x3d	 SriCampus047.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus48.x3d	 SriCampus048.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus49.x3d	 SriCampus049.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus50.x3d	 SriCampus050.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus51.x3d	 SriCampus051.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus52.x3d	 SriCampus052.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus53.x3d	 SriCampus053.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus54.x3d	 SriCampus054.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus55.x3d	 SriCampus055.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus56.x3d	 SriCampus056.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus57.x3d	 SriCampus057.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus58.x3d	 SriCampus058.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus59.x3d	 SriCampus059.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus60.x3d	 SriCampus060.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus61.x3d	 SriCampus061.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus62.x3d	 SriCampus062.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus63.x3d	 SriCampus063.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus64.x3d	 SriCampus064.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus65.x3d	 SriCampus065.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus66.x3d	 SriCampus066.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus67.x3d	 SriCampus067.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus68.x3d	 SriCampus068.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus69.x3d	 SriCampus069.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus70.x3d	 SriCampus070.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus71.x3d	 SriCampus071.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus72.x3d	 SriCampus072.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus73.x3d	 SriCampus073.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus74.x3d	 SriCampus074.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus75.x3d	 SriCampus075.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus76.x3d	 SriCampus076.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus77.x3d	 SriCampus077.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus78.x3d	 SriCampus078.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus79.x3d	 SriCampus079.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus80.x3d	 SriCampus080.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus81.x3d	 SriCampus081.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus82.x3d	 SriCampus082.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus83.x3d	 SriCampus083.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus84.x3d	 SriCampus084.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus85.x3d	 SriCampus085.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus86.x3d	 SriCampus086.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus87.x3d	 SriCampus087.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus88.x3d	 SriCampus088.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus89.x3d	 SriCampus089.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus90.x3d	 SriCampus090.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus91.x3d	 SriCampus091.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus92.x3d	 SriCampus092.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus93.x3d	 SriCampus093.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus94.x3d	 SriCampus094.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus95.x3d	 SriCampus095.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus96.x3d	 SriCampus096.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus97.x3d	 SriCampus097.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus98.x3d	 SriCampus098.x3d
	cd examples/Basic/GeoSpatial/; pwd;	mv SriCampus99.x3d	 SriCampus099.x3d

vpp-compile:
	gcc vpp.c -o vpp.exe
	vpp -?


####	perl checklink.pl -s -html -q -r file://c:/brutzman.html


needed:

new:


2D:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Geometry2dComponentPrototypes		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Geometry2dComponentPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Geometry2dComponentExamples		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Geometry2dComponentExamples		-same

Rendering:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RenderingComponentPrototypes		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RenderingComponentPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RenderingComponentExamples		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RenderingComponentExamples		-same


ViewpointGroup:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ViewpointGroupExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ViewpointGroupExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ViewpointGroupPrototype		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ViewpointGroupPrototype		-same

not_sure:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DeadReckoningComparisons	-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DeadReckoningComparisons	-same
#	need compilation invocation for DeadReckoningComparisonsProject4.jar



	

FixRefile:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Chess8Levels				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Chess8Levels				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat StealthHelo				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  StealthHelo				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FormulaOneRaceCar			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FormulaOneRaceCar			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProjectileInterpolatorArena		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProjectileInterpolatorArena		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProjectileInterpolatorPrototype	-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProjectileInterpolatorPrototype	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProjectileSliderBarPrototype		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProjectileSliderBarPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PropellerControlPanel		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PropellerControlPanel		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat USE-example				-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  USE-example				-same



next:



nurbs:
	make -i NURBS

NURBS:
	@echo -------------------------------------------------------------------------
	ls examples/Basic/NURBS/*.java
	cd examples/Basic/NURBS; pwd;	javac -source 1.2 -target 1.1   -classpath ../ScriptConformance/Vrml97ScriptNodePackages.jar *.java
#	mkdir examples/Basic/NURBS/javadoc
	cd examples/Basic/NURBS; pwd;	javadoc -version -author -package -breakiterator \
		-windowtitle "X3D NURBS Prototypes Javadoc"			\
		-doctitle "<center>X3D NURBS Prototypes Javadoc</center>"	\
		-d javadoc							\
		-classpath ../ScriptConformance/Vrml97ScriptNodePackages.jar *.java
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AnimatedNurbsPatchSurfaceHead			-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AnimatedNurbsPatchSurfaceHead			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsExternPrototypes				-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsExternPrototypes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FourDucks					-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FourDucks					-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FredTheBunny					-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FredTheBunny					-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HurricaneLantern				-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HurricaneLantern				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsCurveExample				-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsCurveExample				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsExternPrototypes				-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsExternPrototypes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsPatchSurfaceExample			-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsPatchSurfaceExample			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsPatchSurfaceExampleLarge			-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsPatchSurfaceExampleLarge			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsPatchSurfaceExampleWithOahuTexture		-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsPatchSurfaceExampleWithOahuTexture		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsPositionInterpolatorExample		-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsPositionInterpolatorExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NurbsPrototypes					-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NurbsPrototypes					-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TrimmingContourExample				-same
	cd examples/Basic/NURBS; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TrimmingContourExample				-same

NURBS-zip:
	@echo -------------------------------------------------------------------------
	cd c:/; pwd; jar -cvf X3D-Examples-NURBS.zip			\
		$(CONTENT_DIR)/examples/Basic/NURBS/*.x3d					\
		$(CONTENT_DIR)/examples/Basic/NURBS/*.wrl					\
		$(CONTENT_DIR)/examples/Basic/NURBS/*.html					\
		$(CONTENT_DIR)/examples/Basic/NURBS/*.txt
	mv c:/X3D-Examples-NURBS.zip .
	ls -l X3D-Examples-NURBS.zip


HelloWorld:
	@echo -------------------------------------------------------------------------
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloWorld -same
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloWorld -same
	cp examples/HelloWorld.x3d  examples/Basic
	cp examples/HelloWorld.x3d  examples/ConformanceNist
	cp examples/HelloWorld.x3d  examples/Vrml2.0Sourcebook
	cp examples/HelloWorld.x3d  examples/Savage
	cp examples/HelloWorld.x3dv examples/Basic
	cp examples/HelloWorld.x3dv examples/ConformanceNist
	cp examples/HelloWorld.x3dv examples/Vrml2.0Sourcebook
	cp examples/HelloWorld.x3dv examples/Savage
	cp examples/HelloWorld.wrl  examples/Basic
	cp examples/HelloWorld.wrl  examples/ConformanceNist
	cp examples/HelloWorld.wrl  examples/Vrml2.0Sourcebook
	cp examples/HelloWorld.wrl  examples/Savage
	cp examples/HelloWorld.html examples/Basic
	cp examples/HelloWorld.html examples/ConformanceNist
	cp examples/HelloWorld.html examples/Vrml2.0Sourcebook
	cp examples/HelloWorld.html examples/Savage
	cp examples/earth-topo.*    examples/Basic
	cp examples/earth-topo.*    examples/ConformanceNist
	cp examples/earth-topo.*    examples/Vrml2.0Sourcebook
	cp examples/earth-topo.*    examples/Savage

BinaryJava:
	cd examples/Basic/BinaryCompression/isenburg/Vrml97Scripts; pwd;	ls *.java
	cd examples/Basic/BinaryCompression/isenburg/Vrml97Scripts; pwd;	javac -source 1.2 -target 1.1 -classpath '../../../ScriptConformance/Vrml97ScriptNodePackages.jar;.' *.java

BinaryCompressionExamples:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CodedCompressedPrototypes	-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CodedCompressedPrototypes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Eight		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Eight		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat EightCoded		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  EightCoded		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat EightCompressed	-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  EightCompressed	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Fish		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Fish		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishCoded		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishCoded		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishCompressed	-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishCompressed	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Lion		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Lion		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LionCoded		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LionCoded		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LionCompressed	-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LionCompressed	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Teapot		-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Teapot		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TeapotCoded	-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TeapotCoded	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TeapotCompressed	-same
	cd examples/Basic/BinaryCompression; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TeapotCompressed	-same


# don't use binaryTranslations again or it will clobber final corrected versions
#binaryTranslations:
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	Eight.wrl		Eight.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	EightCoded.wrl		EightCoded.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	EightCompressed.wrl	EightCompressed.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	Lion.wrl		Lion.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	LionCoded.wrl		LionCoded.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	LionCompressed.wrl	LionCompressed.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	Teapot.wrl		Teapot.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	TeapotCoded.wrl		TeapotCoded.x3d
#	cd examples/Basic/BinaryCompression; pwd;	../../Vrml97ToX3dNist.bat	TeapotCompressed.wrl	TeapotCompressed.x3d

course:
	rm -f   'examples\course\_new_result.wrl'
	rm -f   'examples\course\_new_pretty_print.html'
#	@echo -------------------------------------------------------------------------
#	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat newSceneCompact 	-same
#	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  newSceneCompact 	-same
#	@echo -------------------------------------------------------------------------
#	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat newSceneCompromise 	-same
#	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  newSceneCompromise 	-same
#	@echo -------------------------------------------------------------------------
#	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat newSceneFinalDoctype 	-same
#	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  newSceneFinalDoctype 	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BeckyRoadOverpass			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BeckyRoadOverpass			-same
#boxes:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CreateVrmlFromStringRandomBoxes	-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CreateVrmlFromStringRandomBoxes	-same
#extrusion:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExtrusionCrossSectionExampleShip	-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionCrossSectionExampleShip	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExtrusionCrossSectionExampleTorus	-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionCrossSectionExampleTorus	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExtrusionCrossSectionPrototype		-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionCrossSectionPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExtrusionExampleShip			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionExampleShip			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExtrusionHeart				-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionHeart				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GeometryExample			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GeometryExample			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GroupFormation				-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GroupFormation				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloX3dAuthors			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloX3dAuthors			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ModelOrientation			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ModelOrientation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PositionInterpolatorExample		-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PositionInterpolatorExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Pyramid				-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Pyramid				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PyramidWithAxes			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PyramidWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SimpleCarrierHull			-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SimpleCarrierHull			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SimpleExtrusionShipHull		-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SimpleExtrusionShipHull		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ViewPositionOrientationExample		-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ViewPositionOrientationExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ViewPositionOrientationPrototype	-same
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ViewPositionOrientationPrototype	-same


unlockSpecialScenes:
	chmod +wr,-x -v examples/newScene.x3d
	chmod +wr,-x -v examples/newSceneMasterCopy.x3d
	chmod +wr,-x -v examples/Basic/development/ProtoTagDtdDeclarationExamples.x3d
	chmod +wr,-x -v examples/Basic/development/QuadTreeExamples.x3d
	chmod +wr,-x -v examples/Basic/development/SpinGroupInternalSubsetDeclaration.x3d
	chmod +wr,-x -v examples/Basic/development/X3dRetreatProtoExercise.x3d

lockSpecialScenes:
	chmod -wx,+r -v examples/newScene.x3d
	chmod -wx,+r -v examples/newSceneMasterCopy.x3d
	chmod -wx,+r -v examples/Basic/development/ProtoTagDtdDeclarationExamples.x3d
	chmod -wx,+r -v examples/Basic/development/QuadTreeExamples.x3d
	chmod -wx,+r -v examples/Basic/development/SpinGroupInternalSubsetDeclaration.x3d
	chmod -wx,+r -v examples/Basic/development/X3dRetreatProtoExercise.x3d

buildSpecialScenes:
	@echo -------------------------------------------------------------------------
	chmod +wr,-x -v examples/newScene.x3d
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat newScene -same
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  newScene -same
	cp examples/newSceneMasterCopy.x3d examples/newScene.x3d
	chmod -wx,+r -v examples/newScene.x3d
	@echo -------------------------------------------------------------------------
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat newSceneMasterCopy -same
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  newSceneMasterCopy -same
	@echo -------------------------------------------------------------------------
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SchemaTest -same
	cd examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SchemaTest -same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProtoTagDtdDeclarationExamples		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProtoTagDtdDeclarationExamples		-same
	cd examples/Basic/development; cp       ProtoTagDtdDeclarationExamples.x3d ProtoTagDtdDeclarationExamples.x3d.txt
	cd examples/Basic/development; ls -al   ProtoTagDtdDeclarationExamples.*
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat QuadTreeExamples				-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  QuadTreeExamples				-same
	cd examples/Basic/development; cp       QuadTreeExamples.x3d QuadTreeExamples.x3d.txt
	cd examples/Basic/development; ls -al   QuadTreeExamples.*
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SpinGroupInternalSubsetDeclaration	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SpinGroupInternalSubsetDeclaration	-same
	cd examples/Basic/development; cp       SpinGroupInternalSubsetDeclaration.x3d SpinGroupInternalSubsetDeclaration.x3d.txt
	cd examples/Basic/development; ls -al   SpinGroupInternalSubsetDeclaration.*
#	@echo -------------------------------------------------------------------------
#	the Proto exercise contains a number of invalid scene-graph examples and so doesn't translate without VRML errors
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat X3dRetreatProtoExercise			-same
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  X3dRetreatProtoExercise			-same


LoadSensor:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LoadSensorPrototype		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LoadSensorPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LoadSensorExample			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LoadSensorExample			-same

LODlevelChangedExample:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LODlevelChangedExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LODlevelChangedExample		-same

CADexamples:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CADGeometryExternPrototypes	-same
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CADGeometryExternPrototypes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CADGeometryPrototypes		-same
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CADGeometryPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IndexedQuadSetExample		-same
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IndexedQuadSetExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Teapot				-same
	cd examples/Basic/CAD; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Teapot				-same


developmentExamples:
	rm -f   	'examples\development\_new_result.wrl'
	rm -f   	'examples\development\_new_pretty_print.html'
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AdditiveSubtractiveLight		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AdditiveSubtractiveLight		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AllVrml97Nodes			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AllVrml97Nodes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AmelaExampleScene			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AmelaExampleScene			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BooleanSequencerExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BooleanSequencerExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BooleanSequencerPrototype		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BooleanSequencerPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BradenPrototypeInitializeExample	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BradenPrototypeInitializeExample	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CoordinateInterpolator2dExample	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CoordinateInterpolator2dExample	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CoordinateInterpolator2dPrototype -same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CoordinateInterpolator2dPrototype -same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ECMAScriptAnnexExample2		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ECMAScriptAnnexExample2		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat EventUtilityExamples		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  EventUtilityExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat EventUtilityPrototypes		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  EventUtilityPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExampleEXTERNPROTO-1		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExampleEXTERNPROTO-1		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExampleEXTERNPROTO-2		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExampleEXTERNPROTO-2		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HeaderExample			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HeaderExample			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloSiggraph2000			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloSiggraph2000			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloWorld			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloWorld			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloWWW9				-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloWWW9				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloX3dDay2001			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloX3dDay2001			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelloX3dTrademark			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelloX3dTrademark			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ImportExportSyntax		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ImportExportSyntax		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IntegerSequencerExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IntegerSequencerExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IntegerSequencerPrototype		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IntegerSequencerPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KeySensorActivationKeySwitchTest	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KeySensorActivationKeySwitchTest	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KeySensorActivationKeySwitchTestInterchange	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KeySensorActivationKeySwitchTestInterchange	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KeySensorActivationKeySwitchTestInteractive	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KeySensorActivationKeySwitchTestInteractive	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MetadataPrototypes		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MetadataPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MetadataExamples			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MetadataExamples			-same
#	@echo -------------------------------------------------------------------------
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MultiTextureExamples-1		-same
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MultiTextureExamples-1		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Orbiter				-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Orbiter				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PositionInterpolator2dExample	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PositionInterpolator2dExample	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PositionInterpolator2dPrototype	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PositionInterpolator2dPrototype	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProfileExampleInterchange		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProfileExampleInterchange		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProtoInstanceExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProtoInstanceExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat 'TestProtoInterfaceNodeScope' 	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToHtml.bat   'TestProtoInterfaceNodeScope' 	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProtoMFNodeSensorExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProtoMFNodeSensorExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ProxyShapeExample			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ProxyShapeExample			-same
	@echo -------------------------------------------------------------------------
#QuadTree:
	cd examples/Basic/development; chmod +w QuadTreeExamples.x3d
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat QuadTreeExamples			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  QuadTreeExamples			-same
	cd examples/Basic/development; cp       QuadTreeExamples.x3d QuadTreeExamples.x3d.txt
	cd examples/Basic/development; chmod -w QuadTreeExamples.x3d
	cd examples/Basic/development; ls -al   QuadTreeExamples.*
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SceneNodePrototype		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SceneNodePrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ScriptSyntaxExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ScriptSyntaxExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ShapeChildRoutingExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ShapeChildRoutingExample		-same
	@echo -------------------------------------------------------------------------
	make -i TestScenes
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestDiagnosticOverloadedChildNodes	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestDiagnosticOverloadedChildNodes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TextExamples				-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TextExamples				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat UnderstandingSceneGraphs-TouchToLight	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  UnderstandingSceneGraphs-TouchToLight	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat UnquotedTokensNavigationInfoAndFontStyle	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  UnquotedTokensNavigationInfoAndFontStyle	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat USE-example				-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  USE-example				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WelcomeToWWW9			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WelcomeToWWW9			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WrapperTagsExample		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WrapperTagsExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat X3dHeaderPrototypeSyntaxExamples	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  X3dHeaderPrototypeSyntaxExamples	-same
	@echo -------------------------------------------------------------------------
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat X3dProfileComponentDeclarations	-same
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  X3dProfileComponentDeclarations	-same
#	@echo -------------------------------------------------------------------------
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WrapperTagsExampleUnwrapped	-same
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WrapperTagsExampleUnwrapped	-same
#	@echo -------------------------------------------------------------------------
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WrapperTagsExampleWrapped		-same
#	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WrapperTagsExampleWrapped		-same
#	@echo -------------------------------------------------------------------------

TestScenes:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestColorCount			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestColorCount			-same
	@echo -------------------------------------------------------------------------
	@echo TestFieldChecks contains some intentional errors to test translation checking
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestFieldChecks			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestFieldChecks			-same
	@echo -------------------------------------------------------------------------
	@echo TestContainerFieldValues contains some intentional errors to test translation checking
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestContainerFieldValues		-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestContainerFieldValues		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestDoctype			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestDoctype			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestMetadataNodes			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestMetadataNodes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestOrientationInterpolatorSize	-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestOrientationInterpolatorSize	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TestViewpointJump			-same
	cd examples/Basic/development; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TestViewpointJump			-same

dis:
	make -i DIS

DistributedInteractiveSimulation:
	make -i DIS

disNew:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NetworkedCamera			-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NetworkedCamera			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DISEntityPrototypes			-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DISEntityPrototypes			-same

DIS:
	make -i classpath
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BeachTanks				-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BeachTanks				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation/BaseballBackspin; pwd; compile.bat
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BaseballBackspin			-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BaseballBackspin			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation/CannonProject; pwd; compile.bat
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CannonProject			-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CannonProject			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CoordinateAxesX3dDIS			-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CoordinateAxesX3dDIS			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat EspduTransformExample		-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  EspduTransformExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat EspduTransformPrototypes		-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  EspduTransformPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExternProtoDeclareDefinitions	-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExternProtoDeclareDefinitions	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Gimbals				-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Gimbals				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RadioCommunicationsObserver		-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RadioCommunicationsObserver		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RadioCommunicationsPrototypes	-same
	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RadioCommunicationsPrototypes	-same
#	@echo -------------------------------------------------------------------------
#	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SelectMasterGhostPrototype		-same
#	cd examples/Basic/DistributedInteractiveSimulation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SelectMasterGhostPrototype		-same

EAI:
	cd examples/Basic/ExternalAuthoringInterface; pwd; javac -source 1.2 -target 1.1 -classpath corteai.ZIP -verbose BoxEai.java
	cd examples/Basic/ExternalAuthoringInterface; pwd; javac -source 1.2 -target 1.1 -classpath corteai.ZIP -verbose CreateSphereEai.java
	cd examples/Basic/ExternalAuthoringInterface; pwd; javac -source 1.2 -target 1.1 -classpath corteai.ZIP -verbose HelixEai.java
	cd examples/Basic/ExternalAuthoringInterface; pwd; javac -source 1.2 -target 1.1 -classpath corteai.ZIP -verbose SwitcherEai.java
	cd examples/Basic/ExternalAuthoringInterface; pwd; javac -source 1.2 -target 1.1 -classpath corteai.ZIP -verbose ViewSwitchEai.java
	@echo -------------------------------------------------------------------------
	@echo GraphicalLogAnalyzer uses Netscape security and so a different .jar for EAI.
	@echo See GraphicalLogAnalyzerReadme.txt for details.
	cd examples/Basic/ExternalAuthoringInterface; pwd; javac -source 1.2 -target 1.1 -classpath plywood.jar -verbose GraphicalLogAnalyzer.java
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; javadoc  -version -author -package	\
		-windowtitle      "VRML 97 External Authoring Interface (EAI) Examples for X3D - Javadoc"		\
		-doctitle "<center>VRML 97 External Authoring Interface (EAI) Examples for X3D</center>"	\
		-d javadoc								\
		-classpath "corteai.ZIP;plywood.jar" *.java
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BoxEaiJava			-same
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BoxEaiJava			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CreateSphereEaiJava	-same
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CreateSphereEaiJava	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GraphicalLogAnalyzer	-same
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GraphicalLogAnalyzer	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HelixEaiJava		-same
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HelixEaiJava		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SwitcherEaiJava		-same
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SwitcherEaiJava		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ViewSwitchEaiJava		-same
	cd examples/Basic/ExternalAuthoringInterface; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ViewSwitchEaiJava		-same

geo:
	make GeoSpatialExamples

GeoSpatialExamples:
	rm -f   	'examples\GeoSpatial\_new_result.wrl'
	rm -f   	'examples\GeoSpatial\_new_pretty_print.html'
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat A1_GeoElevationGrid		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  A1_GeoElevationGrid		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat A2_AnimatedGeoViewpoint		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  A2_AnimatedGeoViewpoint		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat A3_GeoInline			-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  A3_GeoInline			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat A4_GeoLocation			-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  A4_GeoLocation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat A5_GeoOrigin			-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  A5_GeoOrigin			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Box				-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Box				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat newGeoSpatialScene			-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  newGeoSpatialScene			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Temple				-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Temple				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GeoSpatialExternProtoDeclarations	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GeoSpatialExternProtoDeclarations	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GeoTouchSensorExample		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GeoTouchSensorExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Mars				-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Mars				-same

halt:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD000	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD000	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD001	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD001	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD002	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD002	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD003	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD003	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD004	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD004	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD005	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD005	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD006	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD006	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD007	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD007	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD008	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD008	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD009	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD009	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD010	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD010	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD011	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD011	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD012	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD012	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD013	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD013	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD015	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD015	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD016	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD016	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD017	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD017	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD018	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD018	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD019	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD019	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD020	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD020	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD021	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD021	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD022	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD023	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MarsLOD023	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MarsLOD023	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Mexico		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Mexico		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Squaw		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Squaw		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD000	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD000	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD001	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD001	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD002	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD002	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD003	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD003	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD004	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD004	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD005	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD005	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD006	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD006	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD007	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD007	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD008	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD008	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD009	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD009	-same
	@echo -------------------------------------------------------------------------
	cd /examples; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GeoSpatial\SquawLOD010	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD010	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD011	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD011	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD012	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD012	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD013	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD013	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD014	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD014	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD015	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD015	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD016	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD016	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD017	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD017	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD018	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD018	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD019	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD019	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD020	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD020	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD021	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD021	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD022	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD022	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD023	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD023	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD024	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD024	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD025	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD025	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD026	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD026	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD027	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD027	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD028	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD028	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SquawLOD029	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SquawLOD029	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Temple		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Temple		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TempleNoOrigin	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TempleNoOrigin	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TempleOrigin	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TempleOrigin	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TemplePretty	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TemplePretty	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TripsAroundWorld	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TripsAroundWorld	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TripsModel	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TripsModel	-same
	@echo -------------------------------------------------------------------------
#world:
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat World		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  World		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WorldHigh		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WorldHigh		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WorldLow		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WorldLow		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WorldTest		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WorldTest		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WorldTestSolid	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WorldTestSolid	-same
	@echo -------------------------------------------------------------------------
#sriCampusBuild:
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus		-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus000	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus000	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus001	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus001	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus002	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus002	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus003	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus003	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus004	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus004	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus005	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus005	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus006	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus006	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus007	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus007	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus008	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus008	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus009	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus009	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus010	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus010	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus011	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus011	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus012	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus012	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus013	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus013	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus014	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus014	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus015	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus015	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus016	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus016	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus017	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus017	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus018	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus018	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus019	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus019	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus020	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus020	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus021	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus021	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus022	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus022	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus023	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus023	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus024	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus024	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus025	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus025	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus026	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus026	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus027	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus027	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus028	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus028	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus029	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus029	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus030	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus030	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus031	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus031	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus032	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus032	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus033	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus033	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus034	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus034	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus035	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus035	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus036	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus036	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus037	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus037	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus038	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus038	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus039	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus039	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus040	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus040	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus041	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus041	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus042	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus042	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus043	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus043	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus044	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus044	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus045	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus045	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus046	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus046	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus047	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus047	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus048	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus048	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus049	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus049	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus050	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus050	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus051	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus051	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus052	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus052	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus053	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus053	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus054	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus054	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus055	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus055	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus056	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus056	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus057	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus057	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus058	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus058	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus059	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus059	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus060	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus060	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus061	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus061	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus062	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus062	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus063	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus063	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus064	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus064	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus065	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus065	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus066	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus066	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus067	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus067	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus068	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus068	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus069	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus069	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus070	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus070	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus071	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus071	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus072	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus072	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus073	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus073	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus074	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus074	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus075	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus075	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus076	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus076	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus077	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus077	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus078	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus078	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus079	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus079	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus080	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus080	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus081	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus081	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus082	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus082	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus083	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus083	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus084	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus084	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus085	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus085	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus086	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus086	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus087	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus087	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus088	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus088	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus089	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus089	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus090	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus090	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus091	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus091	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus092	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus092	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus093	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus093	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus094	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus094	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus095	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus095	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus096	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus096	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus097	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus097	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus098	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus098	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus099	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus099	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus100	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus100	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus101	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus101	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus102	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus102	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus103	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus103	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus104	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus104	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus105	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus105	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus106	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus106	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus107	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus107	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus108	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus108	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus109	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus109	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus110	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus110	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus111	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus111	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus112	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus112	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus113	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus113	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus114	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus114	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus115	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus115	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus116	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus116	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus117	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus117	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus118	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus118	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus119	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus119	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus120	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus120	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus121	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus121	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus122	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus122	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus123	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus123	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus124	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus124	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus125	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus125	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus126	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus126	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus127	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus127	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus128	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus128	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus129	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus129	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus130	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus130	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus131	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus131	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus132	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus132	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus133	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus133	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus134	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus134	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus135	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus135	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus136	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus136	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus137	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus137	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus138	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus138	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus139	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus139	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus140	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus140	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus141	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus141	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus142	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus142	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus143	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus143	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus144	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus144	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus145	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus145	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus146	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus146	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus147	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus147	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus148	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus148	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus149	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus149	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus150	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus150	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SriCampus151	-same
	cd examples/Basic/GeoSpatial; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SriCampus151	-same


h-anim:
	make -i HumanoidAnimationExamples

HAnim:
	make -i HumanoidAnimationExamples

HumanoidAnimationExamples:
#	@echo -------------------------------------------------------------------------
#	@echo unwrapping necessary because fieldValue children used...
#	X3dUnwrap.bat 	'examples\HumanoidAnimation\NancyNativeTags'
#	X3dUnwrap.bat 	'examples\HumanoidAnimation\NancyPrototypes'
#	@echo -------------------------------------------------------------------------
#	$(SAXON) -t -o 							\
#		'examples\HumanoidAnimation\NancyNativeTags.x3d'	\
#		'examples\HumanoidAnimation\NancyPrototypes.x3d'	\
#		'examples\HumanoidAnimation\ProtoToNativeTag.xsl'
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AllenDutton				-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AllenDutton				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AllenDuttonBlurryMotion		-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AllenDuttonBlurryMotion		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AllenDuttonProtoInstances		-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AllenDuttonProtoInstances		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AllenStandShootRifleM24		-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AllenStandShootRifleM24		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BoxMan				-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BoxMan				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Bubbles				-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Bubbles				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DiamondManLOA-0			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DiamondManLOA-0			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DiamondManLOA-1			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DiamondManLOA-1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DiamondManLOA-2			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DiamondManLOA-2			-same
	@echo -------------------------------------------------------------------------
#	some strings may be too big, if so then manually convert using X3D-Edit
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat InterchangableActorsViaDynamicRouting	-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  InterchangableActorsViaDynamicRouting	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LOA1_JumpAnimation			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LOA1_JumpAnimation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LOA1_KneelAnimation			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LOA1_KneelAnimation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LOA1_RunAnimation			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LOA1_RunAnimation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LOA1_StandAnimation			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LOA1_StandAnimation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LOA1_SwimmingFlutterKickAnimation	-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LOA1_SwimmingFlutterKickAnimation	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LOA1_WalkAnimation			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LOA1_WalkAnimation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MaskAndSnorkel			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MaskAndSnorkel			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NancyDiving				-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NancyDiving				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NancyDivingProtoInstances		-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NancyDivingProtoInstances		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NancyNativeTags			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NancyNativeTags			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NancyPrototypes			-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NancyPrototypes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NancyStandShootRifleM24		-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NancyStandShootRifleM24		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ScubaTank				-same
	cd examples/Basic/HumanoidAnimation; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ScubaTank				-same


kelp:
	make KelpForestExhibit
	make kelp-compress

kelp-compress:
	cd c:/; pwd; jar -cvf X3D-Examples-Kelp.zip			\
		$(CONTENT_DIR)/examples/Basic/KelpForestExhibit/
	mv ../../../../X3D-Examples-Kelp.* .



KelpForestExhibit:
	rm -f MakeKelp.out
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BlackSurfperch		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BlackSurfperch		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat BlueRockFish		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  BlueRockFish		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ChangingFog			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ChangingFog			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CircleFish			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CircleFish			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CircleFishExample		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CircleFishExample		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CircleFishLodExample	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CircleFishLodExample	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CircleFishLodPrototype	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CircleFishLodPrototype	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CircleFishPrototype		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CircleFishPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat CopperRockFishPlacardViewer -same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  CopperRockFishPlacardViewer -same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Fish			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Fish			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishExamples		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishModelComparison		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishModelComparison		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishPrototype		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishSchool			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishSchool			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Garibaldi			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Garibaldi			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HalfMoonGroup		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HalfMoonGroup		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HalfMoonProtoype		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HalfMoonProtoype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IntroductionMessage		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IntroductionMessage		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpBass			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpBass			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpBulb			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpBulb			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpFlexibleStipe		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpFlexibleStipe		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpForestMain		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpForestMain		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpExamplesNoBase		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpExamplesNoBase		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpPrototype		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpSurfPerch		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpSurfPerch		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpTank			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpTank			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpTankExternalLight	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpTankExternalLight	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpTankExternalLights	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpTankExternalLights	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KelpTankWaterSurface	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KelpTankWaterSurface	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RockFloor			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RockFloor			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RubberLipGroup		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RubberLipGroup		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Sardine			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Sardine			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineSchool		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineSchool		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineSchoolDouble		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineSchoolDouble		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineSchoolFinal		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineSchoolFinal		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineSchoolMidterm	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineSchoolMidterm	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineSchoolOriginal	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineSchoolOriginal	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineSchoolTriple		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineSchoolTriple		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineShape		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineShape		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineWagging		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineWagging		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SardineX			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SardineX			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SeaStarGroup		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SeaStarGroup		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SeaStarSimple		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SeaStarSimple		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SeaStarPrototype		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SeaStarPrototype		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SeaStarHighResolutionExample	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SeaStarHighResolutionExample	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SeaStarHighResolutionPrototype	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SeaStarHighResolutionPrototype	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SharkLefty			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SharkLefty			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SharkLeftyLocale		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SharkLeftyLocale		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SharkLucy			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SharkLucy			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SharkLucyLocale		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SharkLucyLocale		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat StripedSurfPerch		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  StripedSurfPerch		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat StripedSurfPerchPlaque	-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  StripedSurfPerchPlaque	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TreeFishPlaque		-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TreeFishPlaque		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WaterTank			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WaterTank			-same
	@echo -------------------------------------------------------------------------


shark2:
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Shark2			-same
	cd examples/Basic/KelpForestExhibit; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Shark2			-same


LatticeXvl:
	@echo -------------------------------------------------------------------------
#	Recompilation is only required if modifying the LatticeXvl sources
	cd examples/Basic/LatticeXvl/javasrc; pwd; make -i all
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellExternProtoDeclare		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellExternProtoDeclare		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellProtoDeclare		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellProtoDeclare		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeAll		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeAll		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeEdgeRound		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeEdgeRound		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeEdgeVector	-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeEdgeVector	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeFaceFlags		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeFaceFlags		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeNumberOfDivisions	-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeNumberOfDivisions	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeShellType		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeShellType		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeTexture		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeTexture		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellAttributeVertexRound	-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellAttributeVertexRound	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellModelBaby			-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellModelBaby			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellModelDolphin		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellModelDolphin		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellModelHand			-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellModelHand			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellModelOctopus		-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellModelOctopus		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat XvlShellModelStar			-same
	cd examples/Basic/LatticeXvl; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  XvlShellModelStar			-same


newStudent:

StudentProjects:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AllenDuttonVillage			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AllenDuttonVillage			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Chair					-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Chair					-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ComputerKeyboard			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ComputerKeyboard			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Desk					-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Desk					-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DeskLamp				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DeskLamp				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DirtBike				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DirtBike				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DraggingSphere			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DraggingSphere			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat FishingLure				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  FishingLure				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat HomeInCreteGreece			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  HomeInCreteGreece			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IkeaBeadToy				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IkeaBeadToy				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IkeaBench				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IkeaBench				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IkeaBlueChair				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IkeaBlueChair				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IkeaGreenChair			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IkeaGreenChair			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IkeaRockingHorse			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IkeaRockingHorse			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat IkeaTable				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  IkeaTable				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat KeyboardEightyEightKeys		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  KeyboardEightyEightKeys		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Laetitia				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Laetitia				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LaetitiaSprints			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LaetitiaSprints			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LaetitiaWithDvdController		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LaetitiaWithDvdController		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LaserPrinter				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LaserPrinter				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NetworkReroutingAlternateRoutes	-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NetworkReroutingAlternateRoutes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NetworkReroutingNetwork		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NetworkReroutingNetwork		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NetworkReroutingRoutes		-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NetworkReroutingRoutes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PinballTable				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PinballTable				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PlayRoom				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PlayRoom				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Room					-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Room					-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SweetHome				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SweetHome				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SweetHomeLowDetail			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SweetHomeLowDetail			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SweetHomeFullDetail			-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SweetHomeFullDetail			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Trebuchet				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Trebuchet				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WallClock				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WallClock				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WallLamp				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WallLamp				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WaterTower				-same
	cd examples/Basic/StudentProjects; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WaterTower				-same


UniversalMedia:
	make -i UniversalMediaMaterials
	make -i UniversalMediaPanoramas

UniversalMediaMaterials:
	@echo -------------------------------------------------------------------------
	rm                        'examples\UniversalMediaMaterials\*.x3dv'
	$(XMLINT_LOCATION)/xmlint 'examples\UniversalMediaMaterials\*.x3d'
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ArtDecoPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ArtDecoPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ArtDecoExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ArtDecoExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AutumnPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AutumnPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AutumnExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AutumnExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GlassPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GlassPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GlassExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GlassExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat gridBack			-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  gridBack			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MetalsPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MetalsPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat MetalsExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  MetalsExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NeonPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NeonPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat NeonExamples			-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  NeonExamples			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RococoPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RococoPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RococoExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RococoExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SantaFePrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SantaFePrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SantaFeExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SantaFeExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SheenPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SheenPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SheenExamples			-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SheenExamples			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SilkyPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SilkyPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SilkyExamples			-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SilkyExamples			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SpringPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SpringPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SpringExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SpringExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SummerPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SummerPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat SummerExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  SummerExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TropicalPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TropicalPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat TropicalExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  TropicalExamples		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WinterPrototypes		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WinterPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat WinterExamples		-same
	cd examples/Basic/UniversalMediaMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  WinterExamples		-same

UniversalMediaPanoramas:
	$(XMLINT_LOCATION)/xmlint 'examples\UniversalMediaPanoramas\*.x3d'
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat desert1			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  desert1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat desert2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  desert2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat desert3			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  desert3			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat forest1			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  forest1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat forest2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  forest2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat forest3			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  forest3			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon1			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon3			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon3			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon4			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon4			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon5			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon5			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon6			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon6			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat horizon7			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  horizon7			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat meadow1			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  meadow1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat meadow2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  meadow2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat mountains2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  mountains2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat mountains3			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  mountains3			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ocean1			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ocean1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ocean2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ocean2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ocean3			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ocean3			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ocean4			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ocean4			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat sky1				-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  sky1				-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat space1			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  space1			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat space2			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  space2			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat space3			-same
	cd examples/Basic/UniversalMediaPanoramas; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  space3			-same



zipX3dSpecificationExamples:
	cd c:/$(CONTENT_DIR)/examples/Basic/X3dSpecification/; pwd; jar -cvf X3D-Examples-Joe.zip *.x3d


X3dSpecificationExamples:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat AlarmClock			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  AlarmClock			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Chopper			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Chopper			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ChopperBody			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ChopperBody			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ChopperRotor			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ChopperRotor			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ClickToAnimate		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ClickToAnimate		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ColorInterpolator		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ColorInterpolator		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat DirectionalLight		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  DirectionalLight		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Elevator			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Elevator			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExecutionModel		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExecutionModel		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GeometricProperties		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GeometricProperties		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat GuidedTour			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  GuidedTour			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Instancing			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Instancing			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat LevelOfDetail		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  LevelOfDetail		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PointSet			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PointSet			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Prototype			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Prototype			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat PrototypesPlus		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  PrototypesPlus		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RedSphereBlueBox		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RedSphereBlueBox		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RefractiveMaterial		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RefractiveMaterial		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Robot			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Robot			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Rotor			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Rotor			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Scripting			-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Scripting			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ShuttlesAndPendulums		-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ShuttlesAndPendulums		-same
# model of specification figure
	@echo -------------------------------------------------------------------------
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ExtrusionFigureX3dSpecification	-same
	cd examples/Basic/X3dSpecification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionFigureX3dSpecification	-same


Vrml97Specification:
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ChopperBody		-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ChopperBody		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat ChopperRotor		-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ChopperRotor		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example02			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example02			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example03			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example03			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example04			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example04			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example05			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example05			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example06			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example06			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example07			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example07			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example08			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example08			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example09			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example09			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example10			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example10			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example11			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example11			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example12			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example12			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example13_2		-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example13_2		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example13_3		-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example13_3		-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example14			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example14			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example15			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example15			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example16			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example16			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example17			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example17			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example18			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example18			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Example19			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Example19			-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat RefractiveMaterial	-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  RefractiveMaterial	-same
	@echo -------------------------------------------------------------------------
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Rotor			-same
	cd examples/Basic/Vrml97Specification; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Rotor			-same

VrmlSourceBookExamples:
	rm -rf   'examples\Vrml2.0Sourcebook\_new_result.wrl'
	rm -rf   'examples\Vrml2.0Sourcebook\_new_pretty_print.html'
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter02-Introduction; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure02.1Hut				-same
	cd examples/Vrml2.0Sourcebook/Chapter02-Introduction; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure02.1Hut				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.01DefaultBox				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.01DefaultBox				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.01DefaultBoxWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.01DefaultBoxWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.02ResizedBox				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.02ResizedBox				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.02ResizedBoxWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.02ResizedBoxWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.03DefaultCone				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.03DefaultCone				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.03DefaultConeWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.03DefaultConeWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.04FlattenedCone				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.04FlattenedCone				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.04FlattenedConeWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.04FlattenedConeWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.05DefaultCylinder				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.05DefaultCylinder				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.05DefaultCylinderWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.05DefaultCylinderWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.06ResizedCylinder				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.06ResizedCylinder				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.06ResizedCylinderWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.06ResizedCylinderWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.07ResizedCylinderHollow			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.07ResizedCylinderHollow			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.07ResizedCylinderHollowWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.07ResizedCylinderHollowWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.08DefaultSphere				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.08DefaultSphere				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.08DefaultSphereWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.08DefaultSphereWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.09_3dPlusSign				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.09_3dPlusSign				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure03.10SpaceStation				-same
	cd examples/Vrml2.0Sourcebook/Chapter03-Shapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure03.10SpaceStation				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.01TextShape					-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.01TextShape					-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.01TextShapeWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.01TextShapeWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.02ListOfTextStrings				-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.02ListOfTextStrings				-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.02ListOfTextStringsWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.02ListOfTextStringsWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.03CompressedText				-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.03CompressedText				-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.03CompressedTextWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.03CompressedTextWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.04ListTextStringsAndLengths			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.04ListTextStringsAndLengths			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.04ListTextStringsAndLengthsWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.04ListTextStringsAndLengthsWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.05TextLimitedByMaximumExtent		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.05TextLimitedByMaximumExtent		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.05TextLimitedByMaximumExtentWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.05TextLimitedByMaximumExtentWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.06aTextPlainSerifFont			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.06aTextPlainSerifFont			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.06aTextPlainSerifFontWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.06aTextPlainSerifFontWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.06bTextBoldSerifFont			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.06bTextBoldSerifFont			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.06bTextBoldSerifFontWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.06bTextBoldSerifFontWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.07SmallText					-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.07SmallText					-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.07SmallTextWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.07SmallTextWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.08aTextMoreLineSpacing			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.08aTextMoreLineSpacing			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.08aTextMoreLineSpacingWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.08aTextMoreLineSpacingWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.08bTextLessLineSpacing			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.08bTextLessLineSpacing			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.08bTextLessLineSpacingWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.08bTextLessLineSpacingWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.09TwoTextShapesDifferentJustification	-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.09TwoTextShapesDifferentJustification	-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.09TwoTextShapesDifferentJustificationWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.09TwoTextShapesDifferentJustificationWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.10TwoLinesTextFlatBox			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.10TwoLinesTextFlatBox			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.10TwoLinesTextFlatBoxWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.10TwoLinesTextFlatBoxWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.11TextOverstrikeFont			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.11TextOverstrikeFont			-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure04.11TextOverstrikeFontWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter04-Text; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure04.11TextOverstrikeFontWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.03bCylinderTranslation		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.03bCylinderTranslation		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.03bCylinderTranslationWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.03bCylinderTranslationWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.04bCylinderTranslation		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.04bCylinderTranslation		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.04bCylinderTranslationWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.04bCylinderTranslationWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.10Archway			-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.10Archway			-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.10ArchwayWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.10ArchwayWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.11NestedArchways		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.11NestedArchways		-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure05.11NestedArchwaysWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter05-PositioningShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure05.11NestedArchwaysWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.04bRotation45xWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.04bRotation45xWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.05bRotation-45xWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.05bRotation-45xWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.06bRotation45yWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.06bRotation45yWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.07bRotation-45zWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.07bRotation-45zWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.08Asterisk3dWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.08Asterisk3dWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.09TripleAsterisk3dWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.09TripleAsterisk3dWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.10SimpleArch			-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.10SimpleArch			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.11DeskLampBase			-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.11DeskLampBase			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure06.12DeskLampBaseTwoArms		-same
	cd examples/Vrml2.0Sourcebook/Chapter06-RotatingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure06.12DeskLampBaseTwoArms		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.04bScale2xWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.04bScale2xWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.05bScale0.5xWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.05bScale0.5xWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.06bScale2yWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.06bScale2yWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.07bScale0.5zWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.07bScale0.5zWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.08bScale2x_0.5y_4zWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.08bScale2x_0.5y_4zWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.09SimpleSpaceshipBodyWingsWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.09SimpleSpaceshipBodyWingsWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.10SimpleSpaceship			-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.10SimpleSpaceship			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.11ScaledArches			-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.11ScaledArches			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.12aSimpleTree			-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.12aSimpleTree			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.12bVerticallyScaledTree		-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.12bVerticallyScaledTree		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure07.13DiagonallyScaledTree		-same
	cd examples/Vrml2.0Sourcebook/Chapter07-ScalingShapes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure07.13DiagonallyScaledTree		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure08.3PositionInterpolator		-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure08.3PositionInterpolator		-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure08.3PositionInterpolatorWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure08.3PositionInterpolatorWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure08.4OrientationInterpolator	-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure08.4OrientationInterpolator	-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure08.4OrientationInterpolatorWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure08.4OrientationInterpolatorWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure08.5AnimatingScale		-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure08.5AnimatingScale		-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure08.5AnimatingScaleWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter08-AnimatingPositionOrientationScale; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure08.5AnimatingScaleWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.1SpinningCubeTouchSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.1SpinningCubeTouchSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.1SpinningCubeTouchSensorWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.1SpinningCubeTouchSensorWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.2SpinningCubeTouchSensorClicked			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.2SpinningCubeTouchSensorClicked			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.2SpinningCubeTouchSensorClickedWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.2SpinningCubeTouchSensorClickedWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.3ConePlaneSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.3ConePlaneSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.3ConePlaneSensorWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.3ConePlaneSensorWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.4ConePlaneSensorConstrained		-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.4ConePlaneSensorConstrained		-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.4ConePlaneSensorConstrainedWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.4ConePlaneSensorConstrainedWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.5FourStackingBlocks			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.5FourStackingBlocks			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.5FourStackingBlocksWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.5FourStackingBlocksWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.6SphereSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.6SphereSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.6SphereSensorWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.6SphereSensorWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.7CylinderSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.7CylinderSensor				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.7CylinderSensorWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.7CylinderSensorWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.8ClickDragTouchSensorPlaneSensor		-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.8ClickDragTouchSensorPlaneSensor		-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.8ClickDragTouchSensorPlaneSensorWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.8ClickDragTouchSensorPlaneSensorWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.9DeskLamp					-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.9DeskLamp					-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure09.9DeskLampWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter09-SensingViewer; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure09.9DeskLampWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.04BrightRedSphere				-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.04BrightRedSphere				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.05MultiColoredSpaceStation			-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.05MultiColoredSpaceStation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.06PulsatingSpaceStation			-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.06PulsatingSpaceStation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.07LightBulb					-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.07LightBulb					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.08BlinkingSphere				-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.08BlinkingSphere				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.09ThreeBlinkingShapes				-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.09ThreeBlinkingShapes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.10SemiTransparentSpaceStation			-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.10SemiTransparentSpaceStation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure10.11AnimatingTransparencySpaceStation		-same
	cd examples/Vrml2.0Sourcebook/Chapter10-Materials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure10.11AnimatingTransparencySpaceStation		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure11.1CafeSignSwitchResolutions			-same
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure11.1CafeSignSwitchResolutions			-same
	@echo Alternate versions generated manually:
	ls examples/Vrml2.0Sourcebook/Chapter11-Grouping/Figure11.1CafeSignSwitchResolutions*.wrl
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure11.2CafeSignBillboard				-same
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure11.2CafeSignBillboard				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure11.3BoundingBox					-same
	cd examples/Vrml2.0Sourcebook/Chapter11-Grouping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure11.3BoundingBox					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure12.1SimpleArch					-same
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure12.1SimpleArch					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure12.2InlinedArches					-same
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure12.2InlinedArches					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure12.3TempleOfInlinedArches				-same
	cd examples/Vrml2.0Sourcebook/Chapter12-Inline; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure12.3TempleOfInlinedArches				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.10PointSetBoxCorners			-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.10PointSetBoxCorners			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.11IndexedLineSetBoxWireframe		-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.11IndexedLineSetBoxWireframe		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.12IndexedFaceSetCube			-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.12IndexedFaceSetCube			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.13IndexedFaceSetLightningBolt		-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.13IndexedFaceSetLightningBolt		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.14VaultedCeilingPieceCreased		-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.14VaultedCeilingPieceCreased		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.15VaultedCeilingPiece			-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.15VaultedCeilingPiece			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.16VaultedCeiling			-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.16VaultedCeiling			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.17aMorphingCube				-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.17aMorphingCube				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure13.17bMorphingCubeLineSet			-same
	cd examples/Vrml2.0Sourcebook/Chapter13-PointsLinesFaces; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure13.17bMorphingCubeLineSet			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure14.2ElevationGridMountain			-same
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure14.2ElevationGridMountain			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure14.3ElevationGridRibbon			-same
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure14.3ElevationGridRibbon			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure14.4aElevationGridPuddleSplash10x10		-same
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure14.4aElevationGridPuddleSplash10x10		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure14.4bElevationGridPuddleSplash40x40		-same
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure14.4bElevationGridPuddleSplash40x40		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure14.5ElevationGridTerrain			-same
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure14.5ElevationGridTerrain			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure14.6ElevationGridMedallion			-same
	cd examples/Vrml2.0Sourcebook/Chapter14-ElevationGrid; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure14.6ElevationGridMedallion			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.07ExtrudedCylinder				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.07ExtrudedCylinder				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.08ExtrudedHalfCylinderWithCapsWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.08ExtrudedHalfCylinderWithCapsWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.08ExtrudedHalfCylinderWithCaps			-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.08ExtrudedHalfCylinderWithCaps			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.09ExtrudedHalfCylinderWithoutCapsWithAxes	-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.09ExtrudedHalfCylinderWithoutCapsWithAxes	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.09ExtrudedHalfCylinderWithoutCaps		-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.09ExtrudedHalfCylinderWithoutCaps		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.10ExtrudedRoomWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.10ExtrudedRoomWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.10ExtrudedRoom					-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.10ExtrudedRoom					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.11ExtrudedPlaygroundSlideWithAxes		-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.11ExtrudedPlaygroundSlideWithAxes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.11ExtrudedPlaygroundSlide			-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.11ExtrudedPlaygroundSlide			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.12TorusWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.12TorusWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.12Torus					-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.12Torus					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.13ExtrudedPyramidWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.13ExtrudedPyramidWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.13ExtrudedPyramid				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.13ExtrudedPyramid				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.14ExtrudedVaseWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.14ExtrudedVaseWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.14ExtrudedVase					-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.14ExtrudedVase					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.15TwistedBarWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.15TwistedBarWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.15TwistedBar					-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.15TwistedBar					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.16ExtrudedLipstickWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.16ExtrudedLipstickWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.16ExtrudedLipstick				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.16ExtrudedLipstick				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.17WigglingSnakeWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.17WigglingSnakeWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure15.17WigglingSnake				-same
	cd examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure15.17WigglingSnake				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.1ColoredPointSetScatterPlot			-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.1ColoredPointSetScatterPlot			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.2ColoredLinesPlot					-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.2ColoredLinesPlot					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.3ColoredLinesStarburstWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.3ColoredLinesStarburstWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.3ColoredLinesStarburst				-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.3ColoredLinesStarburst				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.4ColoredBookFacesWithAxes				-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.4ColoredBookFacesWithAxes				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.4ColoredBookFaces					-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.4ColoredBookFaces					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.5LogFireColoredFacesWithAxes			-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.5LogFireColoredFacesWithAxes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.5LogFireColoredFaces				-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.5LogFireColoredFaces				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.6ColoredElevationGridCheckerboard			-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.6ColoredElevationGridCheckerboard			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure16.7VertexColoredElevationGridMountain		-same
	cd examples/Vrml2.0Sourcebook/Chapter16-Color; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure16.7VertexColoredElevationGridMountain		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.04aTextureBoxTest				-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.04aTextureBoxTest				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.04bTextureConeTest				-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.04bTextureConeTest				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.04cTextureCylinderTest				-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.04cTextureCylinderTest				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.04dTextureSphereTest				-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.04dTextureSphereTest				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.05dTexturedSodaCan				-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.05dTexturedSodaCan				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.06bTexturedText					-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.06bTexturedText					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.07bTexturedLightningBolt			-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.07bTexturedLightningBolt			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.08bTexturedElevationGridMountain		-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.08bTexturedElevationGridMountain		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.09bTexturedDonutTorusExtrusion			-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.09bTexturedDonutTorusExtrusion			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.10cTextureTransparencyTree			-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.10cTextureTransparencyTree			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.11bGreyTextureGreenMaterialStripedCylinder	-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.11bGreyTextureGreenMaterialStripedCylinder	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.12bWoodTextures					-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.12bWoodTextures					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.13SimplePixelTexture				-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.13SimplePixelTexture				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure17.14bMovieTextureWhirlpool			-same
	cd examples/Vrml2.0Sourcebook/Chapter17-Textures; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure17.14bMovieTextureWhirlpool			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.19TextureCoordinateTest			-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.19TextureCoordinateTest			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.20bTextureCoordinatePizzaSlice		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.20bTextureCoordinatePizzaSlice		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.21TextureCoordinatePizzaSlice		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.21TextureCoordinatePizzaSlice		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.22bTextureCoordinateVideoWall		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.22bTextureCoordinateVideoWall		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.23bTextureCoordinateGrillwork		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.23bTextureCoordinateGrillwork		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.24TextureCoordinateRotatedGrillworkFence	-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.24TextureCoordinateRotatedGrillworkFence	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.25bTextureTransformBrickBox		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.25bTextureTransformBrickBox		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.26bUncorrectedTextureSanDiegoBay		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.26bUncorrectedTextureSanDiegoBay		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.26cCorrectedTextureSanDiegoBay		-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.26cCorrectedTextureSanDiegoBay		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure18.27RotatingFanAnimatedTextureTransform	-same
	cd examples/Vrml2.0Sourcebook/Chapter18-TextureMapping; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure18.27RotatingFanAnimatedTextureTransform	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.21NormalSquare				-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.21NormalSquare				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.22NormalSquareShaded			-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.22NormalSquareShaded			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.23HalfColumn				-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.23HalfColumn				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.24aHalfColumnCreaseAngle			-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.24aHalfColumnCreaseAngle			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.24bTwoHalfColumnsCreaseAngleSeam		-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.24bTwoHalfColumnsCreaseAngleSeam		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.25aHalfColumnNormals			-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.25aHalfColumnNormals			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.25bTwoHalfColumnsNormalsSeamless		-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.25bTwoHalfColumnsNormalsSeamless		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.26NormalShadedElevationGrid		-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.26NormalShadedElevationGrid		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure19.27SquareFaceAnimatingNormals		-same
	cd examples/Vrml2.0Sourcebook/Chapter19-NormalsShading; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure19.27SquareFaceAnimatingNormals		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.04HeadlightSpheres				-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.04HeadlightSpheres				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.05FlatMesh					-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.05FlatMesh					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.06aPointLightSpheres				-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.06aPointLightSpheres				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.06bPointLightSpheresIntensity			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.06bPointLightSpheresIntensity			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.07PointLightSpheresAttenuation			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.07PointLightSpheresAttenuation			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.08PointLightSpheresSmallRadius			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.08PointLightSpheresSmallRadius			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.09GlowingOrbArchwaysExhibit				-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.09GlowingOrbArchwaysExhibit				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.10BlinkingRedLightbulb					-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.10BlinkingRedLightbulb					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.11aDirectionalLightAimingRightThroughSpheres		-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.11aDirectionalLightAimingRightThroughSpheres		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.11bDirectionalLightAimingUpperRightThroughSpheres	-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.11bDirectionalLightAimingUpperRightThroughSpheres	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.12TwoDirectionalLightsAimingLeftRightThroughSpheres	-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.12TwoDirectionalLightsAimingLeftRightThroughSpheres	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.13aDirectionalLightSunrise			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.13aDirectionalLightSunrise			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.13bDirectionalLightNoon				-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.13bDirectionalLightNoon				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.13cDirectionalLightSunset			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.13cDirectionalLightSunset			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.14DirectionalLightSunAnimated			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.14DirectionalLightSunAnimated			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.15aSpotLightSpheres				-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.15aSpotLightSpheres				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.15bWideAngleSpotLightSpheres			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.15bWideAngleSpotLightSpheres			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.16SpotLightBeamWidthComparison			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.16SpotLightBeamWidthComparison			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.17aSimpleWorldNoShadows				-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.17aSimpleWorldNoShadows				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure20.17bSimpleWorldFakeShadows			-same
	cd examples/Vrml2.0Sourcebook/Chapter20-Lighting; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure20.17bSimpleWorldFakeShadows			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure21.4aDullGreyVase				-same
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure21.4aDullGreyVase				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure21.4bSomewhatShinyGreyVase			-same
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure21.4bSomewhatShinyGreyVase			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure21.4cShinyGreyVase				-same
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure21.4cShinyGreyVase				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure21.4dVeryShinyGreyVase			-same
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure21.4dVeryShinyGreyVase			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure21.5ShinyGoldVase				-same
	cd examples/Vrml2.0Sourcebook/Chapter21-ShinyMaterials; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure21.5ShinyGoldVase				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure22.1BackgroundSkySphere				-same
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure22.1BackgroundSkySphere				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure22.2BackgroundSkyGround				-same
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure22.2BackgroundSkyGround				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure22.3cBackgroundSkyGroundMountains		-same
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure22.3cBackgroundSkyGroundMountains		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure22.4BackgroundBinding				-same
	cd examples/Vrml2.0Sourcebook/Chapter22-Background; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure22.4BackgroundBinding				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.1FogTestWorld					-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.1FogTestWorld					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.2aLinearFogVisibility0				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.2aLinearFogVisibility0				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.2bLinearFogVisibility40				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.2bLinearFogVisibility40				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.2cLinearFogVisibility30				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.2cLinearFogVisibility30				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.2dLinearFogVisibility20				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.2dLinearFogVisibility20				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.3aExponentialFogVisibility100			-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.3aExponentialFogVisibility100			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.3bExponentialFogVisibility40				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.3bExponentialFogVisibility40				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.3cExponentialFogVisibility20				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.3cExponentialFogVisibility20				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.4BlackFogVisibility40				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.4BlackFogVisibility40				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure23.5BindingFogsAndBackgrounds				-same
	cd examples/Vrml2.0Sourcebook/Chapter23-Fog; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure23.5BindingFogsAndBackgrounds				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.1EmitterMarkers					-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.1EmitterMarkers					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.2AmbientSoundEmitterMarkers			-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.2AmbientSoundEmitterMarkers			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.3TouchSensorTriggeredSound				-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.3TouchSensorTriggeredSound				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.4FourKeyKeyboard					-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.4FourKeyKeyboard					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.5TwoAmbientCirclingSounds				-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.5TwoAmbientCirclingSounds				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.6DirectedAmbientSound				-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.6DirectedAmbientSound				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure24.7VirtualTV						-same
	cd examples/Vrml2.0Sourcebook/Chapter24-Sound; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure24.7VirtualTV						-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.01TorchHighDetail				-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.01TorchHighDetail				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.02TorchMediumDetail			-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.02TorchMediumDetail			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.03TorchLowDetail				-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.03TorchLowDetail				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.04ThreeTorchesSideBySide			-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.04ThreeTorchesSideBySide			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.05ThreeTorchesSingleLOD			-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.05ThreeTorchesSingleLOD			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.06DungeonRoomFloor				-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.06DungeonRoomFloor				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.07DungeonRoomWall				-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.07DungeonRoomWall				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.08DungeonRoom				-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.08DungeonRoom				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.09TwoDungeonRoomsWithLODHiding		-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.09TwoDungeonRoomsWithLODHiding		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.10DungeonWallWithDoorway			-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.10DungeonWallWithDoorway			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.11DungeonDoor				-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.11DungeonDoor				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure25.12DungeonRoomsWithSlidingDoors		-same
	cd examples/Vrml2.0Sourcebook/Chapter25-LevelOfDetail; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure25.12DungeonRoomsWithSlidingDoors		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter26-Viewpoint; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure26.5DungeonViewpoint				-same
	cd examples/Vrml2.0Sourcebook/Chapter26-Viewpoint; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure26.5DungeonViewpoint				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter26-Viewpoint; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure26.7DungeonViewpointsAndFieldsOfView		-same
	cd examples/Vrml2.0Sourcebook/Chapter26-Viewpoint; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure26.7DungeonViewpointsAndFieldsOfView		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure27.1VisibilitySensorDungeonSlidingDoors	-same
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure27.1VisibilitySensorDungeonSlidingDoors	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure27.2ProximitySensorDungeonSlidingDoors		-same
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure27.2ProximitySensorDungeonSlidingDoors		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure27.3CollisionDetectionSpinningOrb	-same
	cd examples/Vrml2.0Sourcebook/Chapter27-SensingVisibilityProximityCollision; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure27.3CollisionDetectionSpinningOrb	-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter28-Anchor; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure28.1AnchorViewpointsDungeon				-same
	cd examples/Vrml2.0Sourcebook/Chapter28-Anchor; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure28.1AnchorViewpointsDungeon				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter28-Anchor; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure28.2AnchorLoadAnotherWorld				-same
	cd examples/Vrml2.0Sourcebook/Chapter28-Anchor; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure28.2AnchorLoadAnotherWorld				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter29-WorldInfo; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure29.1WorldInfo					-same
	cd examples/Vrml2.0Sourcebook/Chapter29-WorldInfo; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure29.1WorldInfo					-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure30.1ScriptSlidingBall				-same
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure30.1ScriptSlidingBall				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure30.2ScriptBackgroundSelect				-same
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure30.2ScriptBackgroundSelect				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure30.3ScriptSoundActivate				-same
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure30.3ScriptSoundActivate				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure30.4ScriptSpiralingBallTrace			-same
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure30.4ScriptSpiralingBallTrace			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure30.5ScriptTorusBuilder				-same
	cd examples/Vrml2.0Sourcebook/Chapter30-Scripts; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure30.5ScriptTorusBuilder				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.1WallColorPrototype				-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.1WallColorPrototype				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.2MaterialPrototypesLibrary			-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.2MaterialPrototypesLibrary			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.3MaterialExternalPrototypes			-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.3MaterialExternalPrototypes			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.4WoodAppearancePrototypesLibrary		-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.4WoodAppearancePrototypesLibrary		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.5WoodAppearanceExternalPrototypes		-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.5WoodAppearanceExternalPrototypes		-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.6DonutPrototype				-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.6DonutPrototype				-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.7DonutExternalPrototype			-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.7DonutExternalPrototype			-same
	@echo -------------------------------------------------------------------------
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToVrml97.bat Figure31.9SpinGroupPrototype				-same
	cd examples/Vrml2.0Sourcebook/Chapter31-Prototypes; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  Figure31.9SpinGroupPrototype				-same



# ==============================================================================

Vrml97ToX3dNistAll:
	cd Vrml97ToX3dNist; make -i all


tooltips:
	@echo start tooltips...
	date
	$(XMLINT_LOCATION)/xmlint x3d-3.?.profile*.xml
	$(SAXON) -?
# problems with shell escaping of date switches; use EXSLT date:date() function in stylesheet instead
	$(SAXON) -t -o X3dTooltips.html 		x3d-3.1.profile.xml 		X3dTooltipConversions.xslt
	$(SAXON) -t -o X3dTooltipsChinese.html		x3d-3.1.profileChinese.xml	X3dTooltipConversions.xslt tooltipLanguage='Chinese'   
	$(SAXON) -t -o X3dTooltipsFrench.html		x3d-3.1.profileFrench.xml	X3dTooltipConversions.xslt tooltipLanguage='French'    
	$(SAXON) -t -o X3dTooltipsGerman.html		x3d-3.1.profileGerman.xml	X3dTooltipConversions.xslt tooltipLanguage='German'    
	$(SAXON) -t -o X3dTooltipsItalian.html		x3d-3.1.profileItalian.xml	X3dTooltipConversions.xslt tooltipLanguage='Italian'   
	$(SAXON) -t -o X3dTooltipsPortuguese.html	x3d-3.1.profilePortuguese.xml	X3dTooltipConversions.xslt tooltipLanguage='Portuguese'
	$(SAXON) -t -o X3dTooltipsSpanish.html		x3d-3.1.profileSpanish.xml	X3dTooltipConversions.xslt tooltipLanguage='Spanish'  
# ==============================================================================

zip:
	make compress

compress:
	make -i edit-compress
	make -i examples-compress
	make -i release_date_file

clean:
	rm -f  *.bak
	rm -f  examples/*.bak
	rm -f  examples/*/*.bak
	rm -f  examples/*/*/*.bak
	rm -f  examples/*/*/*/*.bak
	rm -f  examples/*/*/*/*/*.bak
	rm -f  examples/*/*/*/*/*/*.bak
	@echo 'Delete PFE ."$$$$$$" backup files:'
#	       ($$ => $ in bash shell)
	@echo 'Delete X3D-Edit default-output files:'
	rm -f  *.'$$$$$$'
	rm -f  examples/*.'$$$$$$'
	rm -f  examples/*/*.'$$$$$$'
	rm -f  examples/*/*/*.'$$$$$$'
	rm -f  examples/*/*/*/*.'$$$$$$'
	rm -f  examples/*/*/*/*/*.'$$$$$$'
	rm -f  examples/*/*/*/*/*/*.'$$$$$$'
	rm -f  _new_result.wrl
	rm -f  examples/_new_result.wrl
	rm -f  examples/*/_new_result.wrl
	rm -f  examples/*/*/_new_result.wrl
	rm -f  examples/*/*/*/_new_result.wrl
	rm -f  _new_result.x3dv
	rm -f  examples/_new_result.x3dv
	rm -f  examples/*/_new_result.x3dv
	rm -f  examples/*/*/_new_result.x3dv
	rm -f  examples/*/*/*/_new_result.x3dv
	rm -f  _new_pretty_print.html
	rm -f  examples/_new_pretty_print.html
	rm -f  examples/*/_new_pretty_print.html
	rm -f  examples/*/*/_new_pretty_print.html
	rm -f  examples/*/*/*/_new_pretty_print.html
	rm -f  examples/*/.wrl
	rm -f  examples/*/*/.wrl
	rm -f  examples/*/*/*/.wrl
	rm -f  examples/*/_*.wrl
	rm -f  examples/*/*/_*.wrl
	rm -f  examples/*/*/*/_*.wrl
	rm -f  examples/*/*/*/*Styled.wrl
	rm -f  examples/*/.html
	rm -f  examples/*/*/.html
	rm -f  examples/*/*/*/.html
	rm -f  examples/*/_*.html
	rm -f  examples/*/*/_*.html
	rm -f  examples/*/*/*/_*.html
	rm -f  examples/*/contents.html
	rm -f  examples/*/chapter.html
	rm -f  examples/*/*/contents.html
	rm -f  examples/*/*/chapter.html
	rm -f  examples/*Styled.html
	rm -f  examples/*/*Styled.html
	rm -f  examples/*/*/*Styled.html
	rm -f  examples/*/*/*/*Styled.html
	rm -f  examples/*/*/*/*Styled.html
#	rm -f  Vrml97ToX3dNist/*.'$$$$$$'
#	rm -f  Vrml97ToX3dNist/*/*.'$$$$$$'
#	rm -f  Vrml97ToX3dNist/*/*/*.'$$$$$$'
#	rm -f  Vrml97ToX3dNist/*/*/*/*.'$$$$$$'
	rm -f  Thumbs.db
	rm -f  */Thumbs.db
	rm -f  */*/Thumbs.db

specifications:
	make -i zipX3dSpecifications

zipX3dSpecifications:
	make -i menuShortcuts
	make -i specification_release_date_file
#	this content is also included in X3D-Edit.zip, X3D-Edit.tar.gz compressed archives for convenient distribution
	cd C:/; pwd;	jar -cvf X3DPublicSpecifications.zip								\
		'www.web3d.org/x3d/specifications/build.date.X3DPublicSpecifications.txt'				\
		'www.web3d.org/x3d/specifications/make.zipX3dSpecifications.output.txt'					\
		'www.web3d.org/x3d/specifications/ISO-IEC-14772-IS-VRML97WithAmendment1/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19774-FCD-HumanoidAnimation/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19775-Amendment1-FPDAM-X3DAbstractSpecification/'*		\
		'www.web3d.org/x3d/specifications/ISO-IEC-19775-IS-2005-X3DAbstractSpecification/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-1-FDIS-X3DEncodings-XML/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-2-FDIS-X3DEncodings-ClassicVRML/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-3-CD-X3DEncodings-CompressedBinary/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19777-1-FDIS-X3DLanguageBindings-ECMAScript/'*		\
		'www.web3d.org/x3d/specifications/ISO-IEC-19777-2-FDIS-X3DLanguageBindings-Java/'*			\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*	\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*				\
		  'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*
	cd C:/; pwd;	mv -f X3DPublicSpecifications.zip                  C:/www.web3d.org/x3d/specifications/
	cd C:/; pwd;	mv -f build.date.X3DPublicSpecifications.txt.prior C:/www.web3d.org/x3d/specifications/
	ls    C:/www.web3d.org/x3d/specifications/
	ls -l C:/www.web3d.org/x3d/specifications/X3DPublicSpecifications.zip
	cat C:/www.web3d.org/x3d/specifications/build.date.X3DPublicSpecifications.txt


specification_release_date_file:
	@echo 'Update the X3DPublicSpecifications release date file:'
	cd C:/www.web3d.org/x3d/specifications/;      mv build.date.X3DPublicSpecifications.txt c:/build.date.X3DPublicSpecifications.txt.prior
	@echo '=============================================================================='	>  build.date.X3DPublicSpecifications.txt
	@echo '=============================================================================='	>> build.date.X3DPublicSpecifications.txt
	@echo 'Build date for current version of X3DPublicSpecifications.zip'			>> build.date.X3DPublicSpecifications.txt
	@echo ''		>> build.date.X3DPublicSpecifications.txt
	c:/cygwin/bin/date	>> build.date.X3DPublicSpecifications.txt
	@echo ''		>> build.date.X3DPublicSpecifications.txt
	cd C:/www.web3d.org/x3d/specifications/; ls -l X3DPublicSpecifications.zip		>> C:/www.web3d.org/x3d/content/build.date.X3DPublicSpecifications.txt
	@echo ''		>> build.date.X3DPublicSpecifications.txt
	@echo 'Latest versions:' >> build.date.X3DPublicSpecifications.txt
	@echo 'http://www.web3d.org/x3d/specifications/X3DPublicSpecifications.zip'		>> build.date.X3DPublicSpecifications.txt
	@echo 'http://www.web3d.org/x3d/specifications/build.date.X3DPublicSpecifications.txt'	>> build.date.X3DPublicSpecifications.txt
	@echo '=============================================================================='	>> build.date.X3DPublicSpecifications.txt
	@echo '=============================================================================='	>> build.date.X3DPublicSpecifications.txt
	@echo ''			>> build.date.X3DPublicSpecifications.txt
	mv -f build.date.X3DPublicSpecifications.txt C:/www.web3d.org/x3d/specifications/
	@echo '(note that these file sizes are from prior build)'
	cat C:/www.web3d.org/x3d/specifications/build.date.X3DPublicSpecifications.txt


dtdDiff:
	@echo Show X3D DTD and Schema differences between 3.0 and 3.1:
	@echo .
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.0.dtd				C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.1.dtd
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.0-InputOutputFields.dtd		C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.1-InputOutputFields.dtd
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.0.xsd				C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.1.xsd
	@echo ============================================================
#
	@echo Show X3D DTD differences since last specification updates:
	head --lines=100 C:/www.web3d.org/TaskGroups/x3d/translation/x3d-dtd-changelog.txt
	@echo .
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.0.dtd		C:/www.web3d.org/specifications/x3d-3.0.dtd
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.1.dtd		C:/www.web3d.org/specifications/x3d-3.1.dtd
	@echo ============================================================
	@echo Show X3D Schema differences since last specification updates:
	head --lines=150 C:/www.web3d.org/TaskGroups/x3d/translation/x3d-schema-changelog.txt
	@echo .
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.0.xsd		C:/www.web3d.org/x3d/content/x3d-3.0.xsd
	diff C:/www.web3d.org/TaskGroups/x3d/translation/x3d-3.1.xsd		C:/www.web3d.org/x3d/content/x3d-3.1.xsd
	@echo ============================================================
#
	@echo Copy latest DTDs/schemas to official specifications version.  Take care before overwriting!
	cp -f C:/www.web3d.org/TaskGroups/x3d/translation/*.dtd 		C:/www.web3d.org/specifications
	cp -f C:/www.web3d.org/TaskGroups/x3d/translation/*.xsd 		C:/www.web3d.org/specifications
	cp -f C:/www.web3d.org/TaskGroups/x3d/translation/*.txt 		C:/www.web3d.org/specifications
#
	@echo Copy latest DTDs/schemas from transitional DOCTYPE location to X3D-Edit working directory, show filesizes/dates
	cp -f C:/www.web3d.org/TaskGroups/x3d/translation/*.dtd 		C:/www.web3d.org/x3d/content
	cp -f C:/www.web3d.org/TaskGroups/x3d/translation/*.xsd 		C:/www.web3d.org/x3d/content
	cp -f C:/www.web3d.org/TaskGroups/x3d/translation/*.txt 		C:/www.web3d.org/x3d/content
	ls -l C:/www.web3d.org/TaskGroups/x3d/translation/*.dtd
	ls -l C:/www.web3d.org/TaskGroups/x3d/translation/*.xsd
	ls -l C:/www.web3d.org/TaskGroups/x3d/translation/*.txt


X3dSchemaDocumentation-compress:
	@echo 'X3dSchemaDocumentation is manually generated using XML Spy'
	cd c:/; pwd; jar -cvf X3dSchemaDocumentation.zip	\
		$(CONTENT_DIR)/X3dSchemaDocumentation3.0/*
	cd c:/; pwd; jar -uvf X3dSchemaDocumentation.zip	\
		$(CONTENT_DIR)/X3dSchemaDocumentation3.1/*
	mv	c:/X3dSchemaDocumentation.zip .
	ls -l      X3dSchemaDocumentation.zip


menuShortcuts:
	@echo Create or clean out secondary shortcuts-menu directories
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics'
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit'
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples'
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications'
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips'
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates'
	mkdir                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies'
	mkdir   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies'
	mkdir 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies'
	chmod -R +w -v                   'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/'
	chmod -R +w -v  'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
	chmod -R +w -v 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
#	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics'/*.*
#	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics'/*.*
#	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics'/*.*
	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit'/*.*
	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit'/*.*
	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit'/*.*
	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples'/*.*
	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples'/*.*
	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples'/*.*
	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications'/*.*
	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications'/*.*
	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications'/*.*
	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips'/*.*
	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips'/*.*
	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips'/*.*
	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates'/*.*
	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates'/*.*
	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates'/*.*
	rm                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies'/*.*
	rm   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies'/*.*
	rm 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies'/*.*
	@echo Copy shortcuts-menu directories from master location
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/';                     pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/';                     pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/';                     pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/';            pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/';            pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/';            pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/';        pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/';        pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/';        pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/';  pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/';  pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/';  pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/';        pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/';        pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/';        pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/';         pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/';         pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/';         pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'; pwd; cp -R *.*                    'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'; pwd; cp -R *.*   'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'
	cd 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'; pwd; cp -R *.* 'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'
#
	@echo Exhaustively list original and copied links:
	ls -alR 'c:/Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
	ls -alR                          'c:/Windows/Start Menu/Programs/X3D Extensible 3D Graphics/'
	ls -alR         'c:/WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
	ls -alR       'c:/Windows/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/'
#
	@echo Copy desktop link to Windows XP desktop directory
	cp X3D-Edit.lnk 'c:/Documents and Settings/All Users/Desktop/'
	ls -l           'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk'
	@echo Copy desktop X3D-Edit icon to Windows ME desktop directory
	cp -f 'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk' 'C:/WINDOWS/Start Menu/Programs'
	ls -l                                                            'C:/WINDOWS/Start Menu/Programs'
	@echo Copy Windows 2000/XP desktop icons to Windows 98 desktop directory
	cp -f 'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk' 'C:/WINDOWS/Desktop'
	ls -l                                                            'C:/WINDOWS/Desktop'
	@echo Copy Windows 2000/XP icons to Windows NT directory
	cp -f 'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk' 'C:/WINNT/Profiles/All Users/Desktop'
	ls -l                                                            'C:/WINNT/Profiles/All Users/Desktop'
	@echo Copy Windows 2000/XP icons to Windows directory
	cp -f 'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk' 'C:/WINDOWS/Profiles/All Users/Desktop'
	ls -l                                                            'C:/WINDOWS/Profiles/All Users/Desktop'	
#	@echo Copy Windows 2000/XP icons to other Windows XP directory
#	cp -f 'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk' 'C:/WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics'
#	ls -l                                                            'C:/WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics'
# Copy Windows 2000/XP icons to Windows XP directory not needed, already in correct directory on primary-host machine
#	cp -f 'c:/Documents and Settings/All Users/Desktop/X3D-Edit.lnk' n/a


edit-compress:
#	make -i tooltips
	make -i dtdDiff
	make -i X3dSchemaDocumentation-compress
	make -i menuShortcuts
	make -i clean
	cp X3d-Edit-3.1.bat		X3d-Edit.bat
	cp x3d-3.0.profile.xml	x3d-3.0.profile.save
#zipp:
#	debug:  swap ls and jar lines which follow, keeping uncontinued comment in between
#	cd c:/; pwd; ls								\
#		no continuation character on this # comment line
	cd c:/; pwd; jar -cvf X3D-Edit.zip					\
		$(DOM_DIR)/*.dtd						\
		$(DOM_DIR)/*.java						\
		$(DOM_DIR)/*.xslt						\
		$(DOM_DIR)/Makefile						\
		$(DOM_DIR)/Makefile.out						\
		$(CONTENT_DIR)/examples/ContentCatalog.*			\
		$(CONTENT_DIR)/examples/ConformanceNist/ContentCatalog.*		\
		$(CONTENT_DIR)/examples/Vrml2.0Sourcebook/ContentCatalog.*	\
		$(CONTENT_DIR)/examples/Savage/ContentCatalog.*		\
		$(IMAGES_DIR)/x3d2-s.gif					\
		$(IMAGES_DIR)/contactbtn.gif					\
		$(IMAGES_DIR)/cortbest.gif					\
		$(IMAGES_DIR)/cplayer21.gif					\
		$(IMAGES_DIR)/web3d_logo2.png					\
		$(IMAGES_DIR)/X3DtextIcon16.png					\
		$(IMAGES_DIR)/X3D-Edit.LaunchX3dUnwrap.xsl.png			\
		$(IMAGES_DIR)/CoordinateAxesSceneGraph-*.png			\
		$(IMAGES_DIR)/SdkVolumeI-Tools.jpg				\
		$(IMAGES_DIR)/SdkVolumeI-Tools.X3D-Edit.jpg			\
		$(IMAGES_DIR)/Vrml97ImportFileMenu.png				\
		$(IMAGES_DIR)/Vrml97FileSelectionDialogBox.png			\
		$(IMAGES_DIR)/Vrml97ImportDialogBox.png				\
		$(CONTENT_DIR)/X3D-EditSampleScreenSnapshot*.png		\
		$(TRANSITIONAL_DTD_DIR)/*					\
		$(CONTENT_DIR)/profile.dtd					\
		$(CONTENT_DIR)/XmlToolRelationshipsForX3d.pdf			\
		$(CONTENT_DIR)/X3D-EditAuthoringTool.pdf			\
		$(CONTENT_DIR)/X3D-Edit.lnk					\
		$(CONTENT_DIR)/*.bat						\
		$(CONTENT_DIR)/x3d*.dtd						\
		$(CONTENT_DIR)/*.html						\
		$(CONTENT_DIR)/*.profile*.xml					\
		$(CONTENT_DIR)/*.xsd						\
		$(CONTENT_DIR)/*.xslt						\
		$(CONTENT_DIR)/*.sh						\
		$(CONTENT_DIR)/*.txt						\
		$(CONTENT_DIR)/vpp*						\
		$(CONTENT_DIR)/Makefile						\
		$(CONTENT_DIR)/X3dDtdChecker.java				\
		$(CONTENT_DIR)/X3dDtdChecker.class				\
		$(CONTENT_DIR)/X3D-XmlSpyProject.spp				\
		$(CONTENT_DIR)/examples/images/*				\
		$(CONTENT_DIR)/examples/newScene.*				\
		$(CONTENT_DIR)/examples/newSceneMasterCopy.*			\
		$(CONTENT_DIR)/examples/HelloWorld.*				\
		$(CONTENT_DIR)/examples/help.html				\
		$(CONTENT_DIR)/examples/Basic/X3dSceneAuthoringHints.html		\
		$(CONTENT_DIR)/examples/Basic/images/*				\
		$(CONTENT_DIR)/examples/Basic/newScene.*				\
		$(CONTENT_DIR)/examples/Basic/newSceneMasterCopy.*			\
		$(CONTENT_DIR)/examples/Basic/HelloWorld.*				\
		$(CONTENT_DIR)/examples/Basic/help.html				\
		$(CONTENT_DIR)/examples/Basic/X3dSceneAuthoringHints.html		\
		$(CONTENT_DIR)/examples/Basic/earth-topo-small.gif			\
		$(CONTENT_DIR)/examples/Basic/development/BooleanSequencerPrototype.*				\
		$(CONTENT_DIR)/examples/Basic/development/CoordinateInterpolator2dPrototype.*			\
		$(CONTENT_DIR)/examples/Basic/development/EventUtilityPrototypes.*				\
		$(CONTENT_DIR)/examples/Basic/development/IntegerSequencerPrototype.*				\
		$(CONTENT_DIR)/examples/Basic/development/LoadSensorPrototype.*				\
		$(CONTENT_DIR)/examples/Basic/development/PositionInterpolator2dPrototype.*			\
		$(CONTENT_DIR)/examples/Basic/development/Geometry2dComponentPrototypes.*			\
		$(CONTENT_DIR)/examples/Basic/development/RenderingComponentPrototypes.*			\
		$(CONTENT_DIR)/examples/Basic/development/MetadataPrototypes.*				\
		$(CONTENT_DIR)/examples/Basic/DistributedInteractiveSimulation/EspduTransform*.*		\
		$(CONTENT_DIR)/examples/Basic/NURBS/*.class				\
		$(CONTENT_DIR)/examples/Basic/NURBS/NurbsPrototypes.*			\
		$(CONTENT_DIR)/icons/*.gif					\
		$(CONTENT_DIR)/icons/*.ico					\
		$(CONTENT_DIR)/icons/*.pdf					\
		$(CONTENT_DIR)/Vrml97ToX3dNist.jar				\
		$(CONTENT_DIR)/Vrml97ToX3dNist/*				\
		Ibm/Xeena/Xeena.bat						\
		Ibm/Xeena/Makefile						\
		Ibm/Xeena/MakeBuild.out						\
		Ibm/Xeena/dtdprofile.dtd					\
		Ibm/Xeena/src/com/ibm/hrl/xmleditor/extension/vrml/*		\
		Ibm/Xeena/lib/com/ibm/hrl/xmleditor/extension/vrml/*.class	\
		Ibm/Xeena/vrmlExtensionDoc/*					\
		Ibm/Xeena/lib/Vrml97ToX3dImport.jar				\
		Ibm/Xeena/lib/Vrml97ToX3dNist.jar				\
		vrtp/dis-java-vrml.jar						\
		vrtp/mil/navy/nps/awt/AwtEspduSender.*				\
		vrtp/mil/navy/nps/awt/AwtPduViewer.*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-14772-IS-VRML97WithAmendment1/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19774-FCD-HumanoidAnimation/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19775-Amendment1-FPDAM-X3DAbstractSpecification/'*		\
		'www.web3d.org/x3d/specifications/ISO-IEC-19775-IS-2005-X3DAbstractSpecification/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-1-FDIS-X3DEncodings-XML/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-2-FDIS-X3DEncodings-ClassicVRML/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-3-CD-X3DEncodings-CompressedBinary/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19777-1-FDIS-X3DLanguageBindings-ECMAScript/'*		\
		'www.web3d.org/x3d/specifications/ISO-IEC-19777-2-FDIS-X3DLanguageBindings-Java/'*			\
		'www.web3d.org/x3d/specifications/Ecma-262.pdf'
#
	@echo intermediate size:
	ls -l c:/X3D-Edit.zip
#	use jar -update option for remainder, because full list of filenames in one command overflows the buffer (Error 255)
	cd c:/; pwd; jar -uvf X3D-Edit.zip										\
		'Documents and Settings/All Users/Desktop/X3D-Edit.lnk'							\
		                         'WINDOWS/Desktop/X3D-Edit.lnk'							\
		        'WINNT/Profiles/All Users/Desktop/X3D-Edit.lnk'							\
		      'WINDOWS/Profiles/All Users/Desktop/X3D-Edit.lnk'							\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'		\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*		\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'					\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*					\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*					\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*				\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*				\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'			\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*			\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*		\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*		\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*			\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'www.web3d.org/TaskGroups/x3d/translation/'*	\
		'www.web3d.org/specifications/'*
	cd c:/; pwd; jar -uvf X3D-Edit.zip										\
		$(CONTENT_DIR)/X3dSchemaDocumentation3.1/*
#
	chmod 666 c:/X3D-Edit.zip
	@echo final size:
	ls -l c:/X3D-Edit.zip
	@echo X3D-Edit.zip complete
	@echo [Xeena.bat includes a CLASSPATH-preservation bugfix]
#tar:
	@echo =====================
	cd c:/; pwd; tar -cvf X3D-Edit.tar					\
		$(DOM_DIR)/*.dtd						\
		$(DOM_DIR)/*.java						\
		$(DOM_DIR)/*.xslt						\
		$(DOM_DIR)/Makefile						\
		$(DOM_DIR)/Makefile.out						\
		$(CONTENT_DIR)/examples/ContentCatalog.*			\
		$(CONTENT_DIR)/examples/ConformanceNist/ContentCatalog.*		\
		$(CONTENT_DIR)/examples/Vrml2.0Sourcebook/ContentCatalog.*	\
		$(CONTENT_DIR)/examples/Savage/ContentCatalog.*		\
		$(IMAGES_DIR)/x3d2-s.gif					\
		$(IMAGES_DIR)/contactbtn.gif					\
		$(IMAGES_DIR)/cortbest.gif					\
		$(IMAGES_DIR)/cplayer21.gif					\
		$(IMAGES_DIR)/web3d_logo2.png					\
		$(IMAGES_DIR)/X3DtextIcon16.png					\
		$(IMAGES_DIR)/X3D-Edit.LaunchX3dUnwrap.xsl.png			\
		$(IMAGES_DIR)/CoordinateAxesSceneGraph-*.png			\
		$(IMAGES_DIR)/SdkVolumeI-Tools.jpg				\
		$(IMAGES_DIR)/SdkVolumeI-Tools.X3D-Edit.jpg			\
		$(IMAGES_DIR)/Vrml97ImportFileMenu.png				\
		$(IMAGES_DIR)/Vrml97FileSelectionDialogBox.png			\
		$(IMAGES_DIR)/Vrml97ImportDialogBox.png				\
		$(CONTENT_DIR)/X3D-EditSampleScreenSnapshot*.png		\
		$(TRANSITIONAL_DTD_DIR)/*					\
		$(CONTENT_DIR)/profile.dtd					\
		$(CONTENT_DIR)/XmlToolRelationshipsForX3d.pdf			\
		$(CONTENT_DIR)/X3D-EditAuthoringTool.pdf			\
		$(CONTENT_DIR)/X3D-Edit.lnk					\
		$(CONTENT_DIR)/*.bat						\
		$(CONTENT_DIR)/x3d*.dtd						\
		$(CONTENT_DIR)/*.html						\
		$(CONTENT_DIR)/*.profile*.xml					\
		$(CONTENT_DIR)/*.xsd						\
		$(CONTENT_DIR)/*.xslt						\
		$(CONTENT_DIR)/*.sh						\
		$(CONTENT_DIR)/*.txt						\
		$(CONTENT_DIR)/vpp*						\
		$(CONTENT_DIR)/Makefile						\
		$(CONTENT_DIR)/X3dDtdChecker.java				\
		$(CONTENT_DIR)/X3dDtdChecker.class				\
		$(CONTENT_DIR)/X3D-XmlSpyProject.spp				\
		$(CONTENT_DIR)/examples/images/*				\
		$(CONTENT_DIR)/examples/newScene.*				\
		$(CONTENT_DIR)/examples/newSceneMasterCopy.*			\
		$(CONTENT_DIR)/examples/HelloWorld.*				\
		$(CONTENT_DIR)/examples/help.html				\
		$(CONTENT_DIR)/examples/X3dSceneAuthoringHints.html		\
		$(CONTENT_DIR)/examples/earth-topo-small.gif			\
		$(CONTENT_DIR)/examples/Basic/images/*				\
		$(CONTENT_DIR)/examples/Basic/newScene.*				\
		$(CONTENT_DIR)/examples/Basic/newSceneMasterCopy.*			\
		$(CONTENT_DIR)/examples/Basic/HelloWorld.*				\
		$(CONTENT_DIR)/examples/Basic/help.html				\
		$(CONTENT_DIR)/examples/Basic/X3dSceneAuthoringHints.html		\
		$(CONTENT_DIR)/examples/Basic/earth-topo-small.gif			\
		$(CONTENT_DIR)/examples/Basic/development/BooleanSequencerPrototype.*				\
		$(CONTENT_DIR)/examples/Basic/development/CoordinateInterpolator2dPrototype.*			\
		$(CONTENT_DIR)/examples/Basic/development/EventUtilityPrototypes.*				\
		$(CONTENT_DIR)/examples/Basic/development/IntegerSequencerPrototype.*				\
		$(CONTENT_DIR)/examples/Basic/development/LoadSensorPrototype.*				\
		$(CONTENT_DIR)/examples/Basic/development/PositionInterpolator2dPrototype.*			\
		$(CONTENT_DIR)/examples/Basic/development/Geometry2dComponentPrototypes.*			\
		$(CONTENT_DIR)/examples/Basic/development/RenderingComponentPrototypes.*			\
		$(CONTENT_DIR)/examples/Basic/development/MetadataPrototypes.*				\
		$(CONTENT_DIR)/examples/Basic/DistributedInteractiveSimulation/EspduTransform*.*		\
		$(CONTENT_DIR)/examples/Basic/NURBS/*.class				\
		$(CONTENT_DIR)/examples/Basic/NURBS/NurbsPrototypes.*			\
		$(CONTENT_DIR)/icons/*.gif					\
		$(CONTENT_DIR)/icons/*.ico					\
		$(CONTENT_DIR)/icons/*.pdf					\
		$(CONTENT_DIR)/Vrml97ToX3dNist.jar				\
		$(CONTENT_DIR)/Vrml97ToX3dNist/*				\
		Ibm/Xeena/Xeena.bat						\
		Ibm/Xeena/Makefile						\
		Ibm/Xeena/MakeBuild.out						\
		Ibm/Xeena/dtdprofile.dtd					\
		Ibm/Xeena/src/com/ibm/hrl/xmleditor/extension/vrml/*		\
		Ibm/Xeena/lib/com/ibm/hrl/xmleditor/extension/vrml/*.class	\
		Ibm/Xeena/vrmlExtensionDoc/*					\
		Ibm/Xeena/lib/Vrml97ToX3dImport.jar				\
		Ibm/Xeena/lib/Vrml97ToX3dNist.jar				\
		vrtp/dis-java-vrml.jar						\
		vrtp/mil/navy/nps/awt/AwtEspduSender.*				\
		vrtp/mil/navy/nps/awt/AwtPduViewer.*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-14772-IS-VRML97WithAmendment1/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19774-FCD-HumanoidAnimation/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19775-Amendment1-FPDAM-X3DAbstractSpecification/'*		\
		'www.web3d.org/x3d/specifications/ISO-IEC-19775-IS-2005-X3DAbstractSpecification/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-1-FDIS-X3DEncodings-XML/'*				\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-2-FDIS-X3DEncodings-ClassicVRML/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19776-3-CD-X3DEncodings-CompressedBinary/'*			\
		'www.web3d.org/x3d/specifications/ISO-IEC-19777-1-FDIS-X3DLanguageBindings-ECMAScript/'*		\
		'www.web3d.org/x3d/specifications/ISO-IEC-19777-2-FDIS-X3DLanguageBindings-Java/'*			\
		'www.web3d.org/x3d/specifications/Ecma-262.pdf'
#
	@echo intermediate size:
	ls -l c:/X3D-Edit.tar
#	split out each part.  use tar -update option for remainder, because full list of filenames in one command overflows the buffer (Error 255)
	cd c:/; pwd; tar -uvf X3D-Edit.tar										\
		'Documents and Settings/All Users/Desktop/X3D-Edit.lnk'							\
		                         'WINDOWS/Desktop/X3D-Edit.lnk'							\
		        'WINNT/Profiles/All Users/Desktop/X3D-Edit.lnk'							\
		      'WINDOWS/Profiles/All Users/Desktop/X3D-Edit.lnk'							\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'		\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*		\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'					\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*					\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*					\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*				\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*				\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'			\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*			\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*		\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*		\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Help.lnk'		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D-Edit/'*			\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Tooltips/'*		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Specifications/'*	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Updates/'*		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk' \
		'www.web3d.org/TaskGroups/x3d/translation/'*								\
		'www.web3d.org/specifications/'*
	cd c:/; pwd; tar -uvf X3D-Edit.tar										\
		$(CONTENT_DIR)/X3dSchemaDocumentation3.1/*
	chmod 666 c:/X3D-Edit.tar
	@echo final size:
	ls -l c:/X3D-Edit.tar
	cd /cygdrive/c; pwd; gzip X3D-Edit.tar
	chmod 666 c:/X3D-Edit.tar.gz
	@echo X3D-Edit.tar.gz complete
	@echo =====================
	mv      /cygdrive/c/X3D-Edit.* .
	cp       X3D-Edit.zip        X3D-Edit-3.1.zip
	cp       X3D-Edit.tar.gz     X3D-Edit-3.1.tar.gz
	ls -l    X3D-Edit*zip X3D-Edit*tar.gz
	make -i release_date_file


examples-compress:
	make -i clean
	@echo 'Hide movies to keep distribution size down.  These are on website already.'
	ls -l  examples/Basic/HumanoidAnimation/VoiceActivated/NancyVUI.wmv
	mv -f  examples/Basic/HumanoidAnimation/VoiceActivated/NancyVUI.wmv .
	ls -l  examples/Basic/HumanoidAnimation/VoiceActivated/ApaydinOzanThesisFinalVideo.mpg
	mv -f  examples/Basic/HumanoidAnimation/VoiceActivated/ApaydinOzanThesisFinalVideo.mpg .
	ls -l  examples/Vrml2.0Sourcebook/Chapter24-Sound/tv.mpg
	mv -f  examples/Vrml2.0Sourcebook/Chapter24-Sound/tv.mpg      .
	cp examples/help.html                   index.html
	cp examples/help.html                   Basic/index.html
	cp examples/help.html                   examples/Vrml2.0Sourcebook     .
	cp examples/X3dSceneAuthoringHints.html examples/Vrml2.0Sourcebook
	cd c:/; pwd; jar -cvf X3D-Examples.zip					\
		$(IMAGES_DIR)/x3d2-s.gif					\
		$(IMAGES_DIR)/web3d_logo2.png					\
		$(IMAGES_DIR)/Help.png						\
		$(IMAGES_DIR)/NextChapter.png					\
		$(IMAGES_DIR)/NextPage.png					\
		$(IMAGES_DIR)/PreviousChapter.png				\
		$(IMAGES_DIR)/PreviousPage.png					\
		$(IMAGES_DIR)/ThisChapter.png					\
		$(IMAGES_DIR)/ToC.png						\
		$(CONTENT_DIR)/examples/Basic/*.x3d					\
		$(CONTENT_DIR)/examples/Basic/*.wrl					\
		$(CONTENT_DIR)/examples/Basic/*.html					\
		$(CONTENT_DIR)/examples/Basic/*.gif					\
		$(CONTENT_DIR)/examples/Basic/*.png					\
		$(CONTENT_DIR)/examples/Basic/images/*				\
		$(CONTENT_DIR)/examples/Basic/images/*				\
		$(CONTENT_DIR)/examples/Basic/course/					\
		$(CONTENT_DIR)/examples/Basic/development/				\
		$(CONTENT_DIR)/examples/Basic/DistributedInteractiveSimulation/	\
		$(CONTENT_DIR)/examples/Basic/ExternalAuthoringInterface/		\
		$(CONTENT_DIR)/examples/Basic/GeoSpatial/				\
		$(CONTENT_DIR)/examples/Basic/HumanoidAnimation/			\
		$(CONTENT_DIR)/examples/Basic/KelpForestExhibit/			\
		$(CONTENT_DIR)/examples/Basic/LatticeXvl/				\
		$(CONTENT_DIR)/examples/Basic/NURBS/					\
		$(CONTENT_DIR)/examples/Basic/ScriptConformance/			\
		$(CONTENT_DIR)/examples/Basic/StudentProjects/			\
		$(CONTENT_DIR)/examples/Basic/UniversalMediaMaterials/		\
		$(CONTENT_DIR)/examples/Basic/UniversalMediaPanoramas/		\
		$(CONTENT_DIR)/examples/Vrml2.0Sourcebook/			\
		$(CONTENT_DIR)/examples/Basic/Vrml97Specification/			\
		$(CONTENT_DIR)/examples/Basic/X3dSpecification/			\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'		\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'				\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'			\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'			\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'		\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'	\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'Program Files/GeoVRML/'
	cd c:/; pwd; tar -cvf X3D-Examples.tar					\
		$(IMAGES_DIR)/x3d2-s.gif					\
		$(IMAGES_DIR)/web3d_logo2.png					\
		$(IMAGES_DIR)/Help.png						\
		$(IMAGES_DIR)/NextChapter.png					\
		$(IMAGES_DIR)/NextPage.png					\
		$(IMAGES_DIR)/PreviousChapter.png				\
		$(IMAGES_DIR)/PreviousPage.png					\
		$(IMAGES_DIR)/ThisChapter.png					\
		$(IMAGES_DIR)/ToC.png						\
		$(CONTENT_DIR)/examples/Basic/*.x3d					\
		$(CONTENT_DIR)/examples/Basic/*.wrl					\
		$(CONTENT_DIR)/examples/Basic/*.html					\
		$(CONTENT_DIR)/examples/Basic/*.gif					\
		$(CONTENT_DIR)/examples/Basic/*.png					\
		$(CONTENT_DIR)/examples/Basic/course/					\
		$(CONTENT_DIR)/examples/Basic/development/				\
		$(CONTENT_DIR)/examples/Basic/DistributedInteractiveSimulation/	\
		$(CONTENT_DIR)/examples/Basic/ExternalAuthoringInterface/		\
		$(CONTENT_DIR)/examples/Basic/GeoSpatial/				\
		$(CONTENT_DIR)/examples/Basic/HumanoidAnimation/			\
		$(CONTENT_DIR)/examples/Basic/KelpForestExhibit/			\
		$(CONTENT_DIR)/examples/Basic/LatticeXvl/				\
		$(CONTENT_DIR)/examples/Basic/NURBS/					\
		$(CONTENT_DIR)/examples/Basic/ScriptConformance/			\
		$(CONTENT_DIR)/examples/Basic/StudentProjects/			\
		$(CONTENT_DIR)/examples/Basic/UniversalMediaMaterials/		\
		$(CONTENT_DIR)/examples/Basic/UniversalMediaPanoramas/		\
		$(CONTENT_DIR)/examples/Vrml2.0Sourcebook/			\
		$(CONTENT_DIR)/examples/Basic/Vrml97Specification/			\
		$(CONTENT_DIR)/examples/Basic/X3dSpecification/			\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'		\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'	\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'				\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'			\
		'WINDOWS/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'			\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'		\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'	\
		'WINNT/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D basic examples.lnk'		\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/VRML97 SourceBook examples.lnk'	\
		'WINDOWS/Profiles/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/X3D Examples/X3D Scene Authoring Hints.lnk'	\
		'Program Files/GeoVRML/'
#
#		$(CONTENT_DIR)/examples/*.txt
#
#examples-tar:
	cd ../../..; pwd; gzip X3D-Examples.tar
	mv ../../../X3D-Examples.* .
	echo 'Restore movie locations.'
	mv -f NancyVUI.wmv                    examples/Basic/HumanoidAnimation/VoiceActivated/
	mv -f ApaydinOzanThesisFinalVideo.mpg examples/Basic/HumanoidAnimation/VoiceActivated/
	mv -f tv.mpg       examples/Vrml2.0Sourcebook/Chapter24-Sound/
	cp       X3D-Examples.zip    X3D-Examples-3.1.zip
	cp       X3D-Examples.tar.gz X3D-Examples-3.1.tar.gz
	ls -l    X3D-Examples*zip X3D-Examples*tar.gz
		
release_date_file:
	@echo 'Update the release date file:'
#	@echo ''			>  build.date.X3D-Edit.txt
	@echo '======================================================================='	>  build.date.X3D-Edit.txt
	@echo '======================================================================='	>> build.date.X3D-Edit.txt
	@echo 'Build date for current version of X3D-Edit      build.date.X3D-Edit.txt'	>> build.date.X3D-Edit.txt
	@echo 'Latest version http://www.web3d.org/x3d/content/build.date.X3D-Edit.txt' >> build.date.X3D-Edit.txt
	@echo ''			>> build.date.X3D-Edit.txt
	c:/cygwin/bin/date		>> build.date.X3D-Edit.txt
	@echo ''			>> build.date.X3D-Edit.txt
	cp    Makefile.out Makefile.out.previous
	ls -l X3D-Edit.tar.gz 		>> build.date.X3D-Edit.txt
	ls -l X3D-Edit.zip		>> build.date.X3D-Edit.txt
	@echo '======================================================================='	>> build.date.X3D-Edit.txt
	@echo '======================================================================='	>> build.date.X3D-Edit.txt
	@echo ''			>> build.date.X3D-Edit.txt
	@echo '(note that these file sizes are from prior build)'
	cat build.date.X3D-Edit.txt

test-tar:
	cd c:; pwd; tar -cvf X3D-Examples.tar				\
		$(CONTENT_DIR)/examples/*.x3d			\
		$(CONTENT_DIR)/examples/*.wrl			\
		$(CONTENT_DIR)/examples/*.html			\
		$(CONTENT_DIR)/examples/*.gif			\
		$(CONTENT_DIR)/examples/*.mpg			\
		$(CONTENT_DIR)/examples/*.png			\
		$(CONTENT_DIR)/examples/*.txt			\
		$(CONTENT_DIR)/examples/Basic/Vrml97Specification/	\
		$(CONTENT_DIR)/examples/Basic/GeoSpatial/			\
		'Program Files/GeoVRML/'			\
		$(CONTENT_DIR)/examples/Basic/HumanoidAnimation/

compress-UniversalMediaPanoramas:
	@echo 'Delete PFE ."$$$$$$" backup files:'
#	       ($$ => $ in bash shell)
	rm -f  examples/Basic/UniversalMediaPanoramas/*.'$$$$$$'
	rm -f  examples/Basic/UniversalMediaPanoramas/_new_result.wrl
	rm -f  examples/Basic/UniversalMediaPanoramas/_new_pretty_print.html
	cd $(CONTENT_DRIVE)/; jar -cvf UniversalMediaPanoramas.zip $(CONTENT_DIR)/examples/Basic/UniversalMediaPanoramas/*
	mv $(CONTENT_DRIVE)/UniversalMediaPanoramas.zip .
	pwd
	ls -al UniversalMediaPanoramas.zip

#########################################################################

schema-ibm:
	cd /IBMXMLTools/bin; pwd;	\
	Dtd2Schema.bat	\
	-dtd    \www.web3d.org\x3d\content\x3d-compromise.dtd	\
	-schema \www.web3d.org\x3d\content\x3d-compromise.schema	\
	-verbose -java -root X3D

schema-w3c:
	perl dtd2xsd.pl x3d-compromise.dtd

schema-xeena:
	cd $(XEENA_PATH); pwd; xeena.bat -dtd "samples\structures.dtd" -root "schema"  -xml $(CONTENT_DRIVE)/$(CONTENT_DIR)/x3d-compromise.XeenaOriginalTranslation.xsd -root X3D
#	$(XEENA_PATH)/editschema.bat

schema-pretty:
	cd dom; pwd;	java DomDocumentOutputStreamWriter file:///C:/www.web3d.org/x3d/content/X3dSchemaDraftXeena.xml ../X3dSchemaDraft.xml
	ls -l X3dSchemaDraft.xml

SchemaQualityChecker:
	cd C:\xml\SchemaQualityChecker; SchemaQualityChecker C:\www.web3d.org\x3d\content\X3dSchemaDraftSpy.xsd

#########################################################################

sqc:
	cd C:/xml/SchemaQualityChecker; pwd; SQC.bat  C:/www.web3d.org/x3d/content/X3dSchemaDraftSpy.xsd

#	ensure system PATH includes XSV directory, e.g. @C:\Program Files\XSV\;

#	http://www.w3.org/2001/03/webdata/xsv
#	http://www.ltg.ed.ac.uk/~ht/xsv-status.html
	
xsv:
#	cd 'C:\Program Files\XSV'; pwd;	xsv -w C:/www.web3d.org/x3d/content/X3dSchemaDraftSpy.xsd
#	@echo $(PATH)
	cd C:/www.web3d.org/x3d/content; pwd;	'C:/Program Files/XSV/xsv'    -w examples/HelloWorld.x3d C:\www.web3d.org\TaskGroups\x3d\translation\x3d-3.0.xsd
#	don't append schema on command line or else it is loaded a second time after internal reference, throwing warnings
#	cd C:/www.web3d.org/x3d/content; pwd;	xsv    -w examples/schemaTest.x3d X3dSchemaDraftSpy.xsd

# C:\www.web3d.org\x3d\content\X3dSchemaDraftSpy.xsd
# C:/www.web3d.org/x3d/content/X3dSchemaDraftSpy.xsd

trace:
	$(SAXON) -t 'examples\test.x3d' X3dToVrml97.xslt

SCHEMA			= x3d-3.1.xsd

generateSpecificationInterfacesFromSchema:
	$(XMLINT_LOCATION)/xmlint $(SCHEMA)
	$(XMLINT_LOCATION)/xmlint BuildSpecificationInterfacesFromSchema.xslt
	$(CONTENT_DRIVE)/$(CONTENT_DIR)/saxon.bat  -t  $(SCHEMA)  BuildSpecificationInterfacesFromSchema.xslt
	cp    EncodingOfNodes.html				X3dEncodings/Part01/EncodingOfNodes.html
	cp    c:/www.web3d.org/TaskGroups/x3d/translation/*.dtd	X3dEncodings/Part01/
	cp    c:/www.web3d.org/TaskGroups/x3d/translation/*.xsd	X3dEncodings/Part01/
	date
	pwd; ls -l						X3dEncodings/X3dEncodings.css
	pwd; ls -l						X3dEncodings/Part01/*.*


schemaZip:
	rm -f  X3dEncodings/Part01/*.'$$$$$$'
	rm -f  X3dEncodings/Part01/*.bak
	jar -cvf X3D-EncodingSpecification.zip	\
		X3dEncodings/Part01/*

X3dToXhtmlExamplesZip:
	cd c:/; pwd; jar -cvf X3dToXhtmlExamples.zip	\
		$(CONTENT_DIR)/X3dToXhtmlDiscussion.txt		\
		$(CONTENT_DIR)/X3dToXhtml*.xslt			\
		$(CONTENT_DIR)/examples/newSceneCssTest.x3d		\
		$(CONTENT_DIR)/examples/newSceneCssTest*.html	\
		$(CONTENT_DIR)/X3dToHtml.xslt
	mv          c:/X3dToXhtmlExamples.zip examples/
	ls -l examples/X3dToXhtmlExamples.zip


xjc:
	@echo Validate X3D Schema using xjc from Java Web Services Developer Pack
	@echo Validate X3D Schema using xjc from Java Web Services Developer Pack > Makefile.xjc.out
	@echo 'make xjc workaround:  must comment out globalAttributes 'class' attribute in x3d-3.0.xsd line 1146'
	@echo 'make xjc workaround:  must comment out globalAttributes 'class' attribute in x3d-3.0.xsd line 1146' >> Makefile.xjc.out
	cd '$(JWSDP_HOME)/jaxb/bin'; pwd; xjc.bat C:/www.web3d.org/x3d/content/x3d-3.0.xsd -version
	cd '$(JWSDP_HOME)/jaxb/bin'; pwd; xjc.bat C:/www.web3d.org/x3d/content/x3d-3.0.xsd -version      >> C:/www.web3d.org/x3d/content/Makefile.xjc.out
#	2>&1 directs both standard output and standard error to the file
	cd '$(JWSDP_HOME)/jaxb/bin'; pwd; xjc.bat C:/www.web3d.org/x3d/content/x3d-3.0.xsd -nv -p XjcX3d >> C:/www.web3d.org/x3d/content/Makefile.xjc.out 2>&1
	@echo =============================================================================================================
	@echo cat Makefile.xjc.out
	@echo --------------------
	@cat Makefile.xjc.out
	@echo =============================================================================================================

X3dDtdChecker:
	javac -source 1.4 -target 1.4 -verbose X3dDtdChecker.java
	ls -l				examples/HelloWorld.x3d
	java  X3dDtdChecker examples/HelloWorld.x3d
	ls -l				examples/HelloWorld.x3d
	java  X3dDtdChecker examples/HelloWorld.x3d -setTransitionalDTD
	ls -l				examples/HelloWorld.x3d
#	$(XMLINT_LOCATION)/xmlint examples/HelloWorld.x3d
	java  X3dDtdChecker examples/HelloWorld.x3d -setFinalDTD
	ls -l				examples/HelloWorld.x3d

X3dCanonicalizer:
	javac -source 1.4 -target 1.4 -verbose X3dCanonicalizer.java
	java  X3dCanonicalizer examples/HelloWorld.x3d			examples/HelloWorldCanonical.x3d
	ls -l 								examples/HelloWorldCanonical.x3d
	$(XMLINT_LOCATION)/xmlint -w					examples\HelloWorldCanonical.x3d
	java  X3dCanonicalizer examples/development/QuadTreeExamples.x3d examples/development/QuadTreeExamplesCanonical.x3d
	ls -l 								examples/development/QuadTreeExamplesCanonical.x3d
	$(XMLINT_LOCATION)/xmlint -w					examples\development\QuadTreeExamplesCanonical.x3d

Vapour:
	cd c:/; pwd; jar -cvf VapourVrmlToolsWindows.zip								\
		'Documents and Settings/All Users/Start Menu/Programs/X3D Extensible 3D Graphics/Vapour Technologies/'*		\
		'Program Files/Vapour/Dizzy/'*.*										\
		'Program Files/Vapour/Peek/'*.*											\
		'Program Files/Vapour/SpinDoctor/'*.*										\
		'Program Files/Vapour/Twister/'*.*
	mv ../../../VapourVrmlToolsWindows.zip .
	ls -l VapourVrmlToolsWindows.zip

xenu:
	make -i XenuLinkSleuth

XenuLinkSleuth:
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/help.html				-report XenuResults/XenuHelpReport.html
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html		-report XenuResults/XenuX3dSceneAuthoringHintsReport.html
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/index.html				-report XenuResults/XenuExamplesReport.html
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/Basic/index.html			-report XenuResults/XenuExamplesBasicReport.html
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/Vrml2.0Sourcebook/index.html 	-report XenuResults/XenuVrml20SourcebookReport.html
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/ConformanceNist/index.html		-report XenuResults/XenuConformanceNistReport.html
	'c:/Program Files/Xenu/Xenu.exe' -url file:///c:/www.web3d.org/x3d/content/examples/Savage/index.html			-report XenuResults/XenuSavageReport.html
	ls -l XenuResults/Xenu*.html

X3dExtrusionToSvg:
	java  X3dDtdChecker examples/Vrml2.0Sourcebook/Chapter15-Extrusion/Figure15.07ExtrudedCylinder.x3d -setTransitionalDTD
	make -i X3dExtrusionToSvg1
#	make -i X3dExtrusionToSvg2
	java  X3dDtdChecker examples/Vrml2.0Sourcebook/Chapter15-Extrusion/Figure15.07ExtrudedCylinder.x3d -setFinalDTD

X3dExtrusionToSvg1:
	$(XMLINT_LOCATION)/xmlint X3dExtrusionToSvgViaXslt1.1.xslt
	$(SAXON) -t  -o Figure15.07ExtrudedCylinder.svg examples/Vrml2.0Sourcebook/Chapter15-Extrusion/Figure15.07ExtrudedCylinder.x3d  X3dExtrusionToSvgViaXslt1.1.xslt
	ls -l Figure15.07ExtrudedCylinder.svg
	X3dToXhtml.bat   'examples\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.07ExtrudedCylinder				-same
	ls -l 'examples\Vrml2.0Sourcebook\Chapter15-Extrusion\Figure15.07ExtrudedCylinder.svg'

X3dExtrusionToSvg2:
# need 2.0 capable engine...
	$(XMLINT_LOCATION)/xmlint X3dExtrusionToSvgViaXslt2.0.xslt
	$(SAXON) -t  -o Figure15.07ExtrudedCylinderViaXslt2.0.svg examples/Vrml2.0Sourcebook/Chapter15-Extrusion/Figure15.07ExtrudedCylinder.x3d  X3dExtrusionToSvgViaXslt2.0.xslt
	ls -l Figure15.07ExtrudedCylinderViaXslt2.0.svg

X3dExtrusionToSvg3:
# test X3dToXhtml integration
	$(XMLINT_LOCATION)/xmlint X3dExtrusionToSvgViaXslt1.1.xslt
	cd    examples/Vrml2.0Sourcebook/Chapter15-Extrusion; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat   Figure15.07ExtrudedCylinder	-same
	ls -l examples/Vrml2.0Sourcebook/Chapter15-Extrusion/_svg/*.svg

X3dExtrusionToSvg4:
	cd examples/Basic/course; pwd; $(CONTENT_DRIVE)/$(CONTENT_DIR)/X3dToXhtml.bat  ExtrusionCrossSectionPrototype		-same

 

newSceneSaxon:
	cd examples; pwd;	$(SAXON) -t -o newScene.html	newScene.x3d 	../X3dToXhtml.xslt
	cd examples; pwd;	$(SAXON) -t -o newScene.wrl	newScene.x3d 	../X3dToVrml97.xslt
	cd examples; pwd;	$(SAXON) -t -o newScene.x3dv	newScene.x3d 	../X3dToX3dvClassicVrmlEncoding.xslt


HelloWorldSaxon:
	cd examples; pwd;	$(SAXON) -t -o HelloWorld.html	HelloWorld.x3d 	../X3dToXhtml.xslt
	cd examples; pwd;	$(SAXON) -t -o HelloWorld.wrl	HelloWorld.x3d 	../X3dToVrml97.xslt
	cd examples; pwd;	$(SAXON) -t -o HelloWorld.x3dv	HelloWorld.x3d 	../X3dToX3dvClassicVrmlEncoding.xslt

