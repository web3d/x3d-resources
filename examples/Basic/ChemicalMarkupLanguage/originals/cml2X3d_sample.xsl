<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<!--
  <Header>
   <meta name="filename"    content="cmlToX3d_3.xsl" />
   <meta name="author"      content="Nicholas F. Polys" />
   <meta name="revised"     content="September 2002" />
   <meta name="description" content=" XSL stylesheet to convert CML-XML files to X3D-XML files" />

  </Header>

Recommended tools:
-  Gnome Libs and Perl Modules
-  SAXON XML Toolkit (and Instant Saxon) http://users.iclway.co.uk/mhkay/saxon
- Can also be used with Apache server, Xalan & Cacoon

-->
	<!-- <xsl:strip-space elements="*" />   -->
	<xsl:output method="xml" encoding="UTF-8" media-type="model/x3d+xml" cdata-section-elements="Script" indent="yes" doctype-public="ISO//Web3D//DTD X3D 3.0//EN" doctype-system="http://www.web3d.org/specifications/x3d-3.0.dtd"/>

 <xsl:template match="/">
		<X3D profile="Immersive">&#10; <head>&#10; 
   				<meta content="translatedCML.x3d" name="filename"/>
   				<meta content="1.0" name="CML version"/>
				<meta content="http://www.xml-cml.org" name="CML sources"/>
				<meta content="JUMBO" name="Chemical Format Conversion Tool"/>
			       <meta content="http://webbook.nist.gov/chemistry/" name="CML sources"/>
				<meta content="Nov. 24, 2005" name="revised"/>
				<meta content="cml2x3d_sample.xsl by Nicholas F. Polys" name="author"/>
				<meta name='created' content='Originally Published in Proceedings of Web3D 2003, ACM Press '/> 
				<meta name='license' content='../../license.html'/> 
			</head>
			<Scene>
				<!-- Insert  EXTERN / PROTO declarations, if not otherwise provided

  	# render-style prototypes
  		# atom billboard sphere defs
  		# bond line
  		# Fonts- Formula
  		#      - annotation 1
  		#      - annotation 2  -->
				<ProtoDeclare name="Carbon">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoC">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoC_mat" diffuseColor="0 0 0" shininess=".8" specularColor=".29 .3 .29">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius=".77"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="C">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Hydrogen">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoH">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoH_mat" ambientIntensity=".0933" diffuseColor=".38 .38 .42" shininess="0.5" specularColor=".53 .53 .53">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius=".32"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="H">
										<FontStyle size=".4"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Nitrogen">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoN">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoN_mat" diffuseColor="0 0 .72" emissiveColor="0 0 .13" specularColor=".5 .5 .5">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius=".75"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="N">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Oxygen">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoO">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoO_mat" ambientIntensity=".487" diffuseColor=".54 .05 .25" emissiveColor="0 0 0" shininess=".2" specularColor=".81 .77 .75">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius=".73"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="O">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Fluorine">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoF">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoF_mat" diffuseColor="1 .48 .79" emissiveColor=".09 .04 .07" specularColor="0 0 0">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius=".72"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="F">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Silicon">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoSi">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoSi_mat" diffuseColor=".8 .8 .8" emissiveColor="0 0 0" specularColor="0 0 0">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1.18"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="Si">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Phosphorus">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoP">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoP_mat" ambientIntensity=".11" diffuseColor=".9 .41 0" emissiveColor=".1 .04 0" shininess="0.8" specularColor=".1 .1 .1">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1.1"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="P">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Sulpher">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoS">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoS_mat" ambientIntensity=".0467" diffuseColor=".25 .39 .25" emissiveColor=".05 .08 .05" shininess="0.6" specularColor=" .11 .12 .08">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1.3"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="S">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Chlorine">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoCl">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoCl_mat" diffuseColor=".28 .7 0" emissiveColor=".06 .15 0" shininess="0.8" specularColor=".5 .5 .5">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="Cl">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Bromine">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoBr">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoBr_mat" ambientIntensity=".0833" diffuseColor=".5 .3 .19" emissiveColor=".12 .13 .08" shininess="0.17" specularColor=".08 .08 .05">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1.14"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="Br">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="Iodine">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="atoI">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="atoI_mat" diffuseColor=".56 .37 .74" emissiveColor=".15 .1 .2" shininess=".09" specularColor=".12 .12 .12">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1.33"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="I">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="unknown">
					<ProtoInterface>
						<field accessType="inputOutput" name="position" type="SFVec3f"/>
						<field accessType="inputOutput" name="Mat" type="SFFloat" value=".6"/>
					</ProtoInterface>
					<ProtoBody>
						<Group>
							<Transform DEF="ato_">
								<IS>
									<connect nodeField="translation" protoField="position"/>
								</IS>
								<Shape>
									<Appearance>
										<Material DEF="ato__mat" diffuseColor="1 1 1" emissiveColor=".15 .1 .2" shininess=".09" specularColor=".12 .12 .12">
											<IS>
												<connect nodeField="transparency" protoField="Mat"/>
											</IS>
										</Material>
									</Appearance>
									<Sphere radius="1"/>
								</Shape>
								<Shape>
									<Appearance/>
									<Text string="?">
										<FontStyle size=".8"/>
									</Text>
								</Shape>
							</Transform>
						</Group>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="line">
					<ProtoInterface>
						<field accessType="inputOutput" name="bond_set" type="MFVec3f"/>
					</ProtoInterface>
					<ProtoBody>
						<Transform translation="0 0 0">
							<Shape>
								<Appearance>
									<Material diffuseColor="1 1 1" emissiveColor="1 1 1"/>
								</Appearance>
								<IndexedLineSet coordIndex="0, 1, -1 ">
									<Coordinate DEF="bondo" point="-1 0 0, 1 0 0">
										<IS>
											<connect nodeField="point" protoField="bond_set"/>
										</IS>
									</Coordinate>
								</IndexedLineSet>
							</Shape>
						</Transform>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="title_text">
					<ProtoInterface>
						<field accessType="inputOutput" name="txt" type="MFString"/>
					</ProtoInterface>
					<ProtoBody>
						<Transform>
							<Group>
								<Transform>
									<Shape>
										<Appearance/>
										<Text DEF="cmpd_name">
											<IS>
												<connect nodeField="string" protoField="txt"/>
											</IS>
											<FontStyle size="1"/>
										</Text>
									</Shape>
								</Transform>
							</Group>
						</Transform>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="ano1_text">
					<ProtoInterface>
						<field accessType="inputOutput" name="txt" type="MFString"/>
					</ProtoInterface>
					<ProtoBody>
						<Transform>
							<Group>
								<Transform>
									<Shape>
										<Appearance/>
										<Text DEF="cmpd_name1">
											<IS>
												<connect nodeField="string" protoField="txt"/>
											</IS>
											<FontStyle size=".8"/>
										</Text>
									</Shape>
								</Transform>
							</Group>
						</Transform>
					</ProtoBody>
				</ProtoDeclare>
				<ProtoDeclare name="ano2_text">
					<ProtoInterface>
						<field accessType="inputOutput" name="txt" type="MFString"/>
					</ProtoInterface>
					<ProtoBody>
						<Transform>
							<Group>
								<Transform>
									<Shape>
										<Appearance/>
										<Text DEF="cmpd_name2">
											<IS>
												<connect nodeField="string" protoField="txt"/>
											</IS>
											<FontStyle size=".6"/>
										</Text>
									</Shape>
								</Transform>
							</Group>
						</Transform>
					</ProtoBody>
				</ProtoDeclare>
				<!-- 	# spectrum graphs
  	# HUD interface & text screens? -->
				<!-- Insert NavigationInfo , Viewpoint, and Background if not otherwise provided -->
				<Background groundAngle="1.309, 1.571" groundColor="0 0.5 0.7, 0 0.4 0.7, 0.6 0.5 0.7" skyAngle="1.309, 1.571" skyColor="0 0.5 0.8, 0 0.6 .7, 0.6 0.6 0.7"/>
				<PointLight ambientIntensity="1" intensity="1" location="0 0 5" radius="30"/>
				<!--
<Inline url="space/GridXY_20x20Movable.wrl"/>
<Inline url="space/GridXZ_20x20Movable.wrl"/>
<Inline url="space/GridYZ_20x20Movable.wrl"/>
-->
				<NavigationInfo type="&quot;EXAMINE&quot; &quot;ANY&quot;" avatarSize="0.25 1.6 0.75" headlight="true" speed="1" visibilityLimit="0"/>
				<Viewpoint description="Entry" position="0 2 20" fieldOfView="0.785398" jump="true" orientation="0 0 1 0"/>
				<xsl:apply-templates/>
			</Scene>
		</X3D>
	</xsl:template>
	<!-- <xsl:template match="cml">
        <xsl:call-template name="molecule"/>
            # <xsl:call-template name="chimeral:spectrum"/>
</xsl:template> -->
	<xsl:template match="molecule" name="molecule">
		<!--         # manifest molecule info in scene via text nodes    -->
		<Transform DEF="infogroupa" translation="-8 2 -4">
			<Transform translation="0 6 0">
				<ProtoInstance name="title_text">
					<xsl:variable name="fullname" select="concat(@title,': ', .//formula)"/>
					<fieldValue>
						<xsl:attribute name="name">txt</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="$fullname"/></xsl:attribute>
					</fieldValue>
				</ProtoInstance>
			</Transform>
			<!-- #recurse text positioning           -->
			<xsl:variable name="begin1" select="6"/>
			<xsl:for-each select="./float">
				<xsl:variable name="place" select="position()"/>
				<xsl:variable name="tpl" select="$begin1 - $place"/>
				<xsl:variable name="txtPos" select="concat('0 ',$tpl,' 0')"/>
				<Transform>
					<xsl:attribute name="translation"><xsl:value-of select="$txtPos"/></xsl:attribute>
					<ProtoInstance name="ano1_text">
						<fieldValue>
							<xsl:attribute name="name">txt</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="concat (@*,': ',.)"/></xsl:attribute>
						</fieldValue>
						<!--  		# <float title="melting point" units="degC">238</float>      -->
					</ProtoInstance>
				</Transform>
			</xsl:for-each>
			<!-- #recurse text positioning     -->
			<xsl:variable name="begin2" select="1"/>
			<xsl:for-each select="./string">
				<xsl:variable name="place" select="position()"/>
				<xsl:variable name="tpl" select="$begin2 - $place"/>
				<xsl:variable name="txtPos" select="concat('0 ',$tpl,' 0')"/>
				<Transform>
					<xsl:attribute name="translation"><xsl:value-of select="$txtPos"/></xsl:attribute>
					<ProtoInstance name="ano2_text">
						<fieldValue>
							<xsl:attribute name="name">txt</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="concat (@*,': ',.)"/></xsl:attribute>
						</fieldValue>
						<!--                 # <string title="water solubility" units="g/100 mL" convention="g per 100 mL at 23 degC">1-5</string>     -->
					</ProtoInstance>
				</Transform>
			</xsl:for-each>
		</Transform>
		<xsl:apply-templates/>
	</xsl:template>
	<!--  	 #</molecule>-->
	<xsl:template match="molecule/float">
</xsl:template>
	<xsl:template match="molecule/string">
</xsl:template>
	<xsl:template match="molecule/date">
</xsl:template>
	<xsl:template match="molecule/formula">
</xsl:template>
	<xsl:template match="molecule/list">
</xsl:template>
	<xsl:template match="chimeral/*">
</xsl:template>
	<xsl:template match="chimeral/spectrum">
</xsl:template>
	<xsl:template match="chimeral/string">
</xsl:template>
	<xsl:template match="chimeral/float">
</xsl:template>
	<xsl:template match="chimeral/list">
</xsl:template>
	<xsl:template match="atomArray">
		<!--       # manifest atom geometry in scene
      #<atomArray>
      #atoms                                    -->
		<Group>
			<xsl:for-each select="atom">
				<xsl:choose>
					<xsl:when test=".//float[@builtin='x3']">
						<!--    3D Molecule build                         -->
						<xsl:variable name="elem" select=".//string[@builtin='elementType']"/>
						<xsl:variable name="elemID" select="@id"/>
						<xsl:variable name="elemX" select=".//float[@builtin='x3']"/>
						<xsl:variable name="elemY" select=".//float[@builtin='y3']"/>
						<xsl:variable name="elemZ" select=".//float[@builtin='z3']"/>
						<xsl:variable name="elemPos" select="concat ($elemX,' ',$elemY,' ',$elemZ)"/>
						<xsl:choose>
							<xsl:when test="$elem = 'N'">
								<Transform>
									<ProtoInstance name="Nitrogen">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'H'">
								<Transform>
									<ProtoInstance name="Hydrogen">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'C'">
								<Transform>
									<ProtoInstance name="Carbon">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'O'">
								<Transform>
									<ProtoInstance name="Oxygen">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'F'">
								<Transform>
									<ProtoInstance name="Fluorine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'Si'">
								<Transform>
									<ProtoInstance name="Silicon">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'P'">
								<Transform>
									<ProtoInstance name="Phosphorus">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'S'">
								<Transform>
									<ProtoInstance name="Sulpher">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'Cl'">
								<Transform>
									<ProtoInstance name="Chlorine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'Br'">
								<Transform>
									<ProtoInstance name="Bromine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'I'">
								<Transform>
									<ProtoInstance name="Iodine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:otherwise>
								<Transform>
									<ProtoInstance DEF="unknown" name="unknown">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!--     </xsl:when 3D>            -->
					<xsl:otherwise>
						<xsl:variable name="elem" select=".//string[@builtin='elementType']"/>
						<xsl:variable name="elemID" select="@id"/>
						<xsl:variable name="elemX" select=".//float[@builtin='x2']"/>
						<xsl:variable name="elemY" select=".//float[@builtin='y2']"/>
						<xsl:variable name="elemZ" select="0"/>
						<xsl:variable name="elemPos" select="concat ($elemX,' ',$elemY,' ',$elemZ)"/>
						<xsl:choose>
							<xsl:when test="$elem = 'N'">
								<Transform>
									<ProtoInstance name="Nitrogen">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'H'">
								<Transform>
									<ProtoInstance name="Hydrogen">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'C'">
								<Transform>
									<ProtoInstance name="Carbon">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'O'">
								<Transform>
									<ProtoInstance name="Oxygen">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'F'">
								<Transform>
									<ProtoInstance name="Fluorine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'Si'">
								<Transform>
									<ProtoInstance name="Silicon">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'P'">
								<Transform>
									<ProtoInstance name="Phosphorus">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'S'">
								<Transform>
									<ProtoInstance name="Sulpher">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'Cl'">
								<Transform>
									<ProtoInstance name="Chlorine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'Br'">
								<Transform>
									<ProtoInstance name="Bromine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:when test="$elem = 'I'">
								<Transform>
									<ProtoInstance name="Iodine">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:otherwise>
								<Transform>
									<ProtoInstance DEF="unknown" name="unknown">
										<xsl:attribute name="DEF"><xsl:value-of select="$elemID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">position</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$elemPos"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</Group>
	</xsl:template>
	<!--    close atomArray template            -->
	<!--    close atomArray template            -->
	<xsl:template match="bondArray">
		<!--            # manifest bond geometry in scene
      #<bondArray>
      #bonds   -->
		<Group>
			<xsl:for-each select="bond">
				<xsl:variable name="ptA" select=".//string[@builtin='atomRef'][1]"/>
				<xsl:variable name="ptB" select=".//string[@builtin='atomRef'][2]"/>
				<xsl:variable name="order" select=".//string[@builtin='order']"/>
				<xsl:variable name="aNod" select="//atom[@id=$ptA]"/>
				<xsl:variable name="bNod" select="//atom[@id=$ptB]"/>
				<xsl:choose>
					<xsl:when test="$order = 1">
						<xsl:choose>
							<xsl:when test="$aNod/float[@builtin='x3']">
								<xsl:variable name="elemXa" select="$aNod/float[@builtin='x3']"/>
								<xsl:variable name="elemYa" select="$aNod/float[@builtin='y3']"/>
								<xsl:variable name="elemZa" select="$aNod/float[@builtin='z3']"/>
								<xsl:variable name="elemPosa" select="concat ($elemXa,' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemXb" select="$bNod/float[@builtin='x3']"/>
								<xsl:variable name="elemYb" select="$bNod/float[@builtin='y3']"/>
								<xsl:variable name="elemZb" select="$bNod/float[@builtin='z3']"/>
								<xsl:variable name="elemPosb" select="concat ($elemXb,' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="pset" select="concat($elemPosa,', ',$elemPosb)"/>
								<xsl:variable name="bondID" select="@id"/>
								<Transform translation="0 0 0">
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="$bondID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="elemXa" select="$aNod/float[@builtin='x2']"/>
								<xsl:variable name="elemYa" select="$aNod/float[@builtin='y2']"/>
								<xsl:variable name="elemZa" select="0"/>
								<xsl:variable name="elemPosa" select="concat ($elemXa,' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemXb" select="$bNod/float[@builtin='x2']"/>
								<xsl:variable name="elemYb" select="$bNod/float[@builtin='y2']"/>
								<xsl:variable name="elemZb" select="0"/>
								<xsl:variable name="elemPosb" select="concat ($elemXb,' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="pset" select="concat($elemPosa,', ',$elemPosb)"/>
								<xsl:variable name="bondID" select="@id"/>
								<Transform translation="0 0 0">
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="$bondID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="$order = 2">
						<xsl:choose>
							<xsl:when test="$aNod/float[@builtin='x3']">
								<xsl:variable name="elemXa" select="$aNod/float[@builtin='x3']"/>
								<xsl:variable name="elemYa" select="$aNod/float[@builtin='y3']"/>
								<xsl:variable name="elemZa" select="$aNod/float[@builtin='z3']"/>
								<xsl:variable name="elemPosa" select="concat (($elemXa - .02),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemPosa2" select="concat (($elemXa + .02),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemXb" select="$bNod/float[@builtin='x3']"/>
								<xsl:variable name="elemYb" select="$bNod/float[@builtin='y3']"/>
								<xsl:variable name="elemZb" select="$bNod/float[@builtin='z3']"/>
								<xsl:variable name="elemPosb" select="concat (($elemXb - .02),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="elemPosb2" select="concat (($elemXb + .02),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="pset" select="concat($elemPosa,', ',$elemPosb)"/>
								<xsl:variable name="pset2" select="concat($elemPosa2,', ',$elemPosb2)"/>
								<xsl:variable name="bondID" select="@id"/>
								<Transform translation="0 0 0">
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="$bondID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="concat($bondID,'_2')"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset2"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="elemXa" select="$aNod/float[@builtin='x2']"/>
								<xsl:variable name="elemYa" select="$aNod/float[@builtin='y2']"/>
								<xsl:variable name="elemZa" select="0"/>
								<xsl:variable name="elemPosa" select="concat (($elemXa - .02),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemPosa2" select="concat (($elemXa + .02),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemXb" select="$bNod/float[@builtin='x2']"/>
								<xsl:variable name="elemYb" select="$bNod/float[@builtin='y2']"/>
								<xsl:variable name="elemZb" select="0"/>
								<xsl:variable name="elemPosb" select="concat (($elemXb - .02),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="elemPosb2" select="concat (($elemXb + .02),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="pset" select="concat($elemPosa,', ',$elemPosb)"/>
								<xsl:variable name="pset2" select="concat($elemPosa2,', ',$elemPosb2)"/>
								<xsl:variable name="bondID" select="@id"/>
								<Transform translation="0 0 0">
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="$bondID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="concat($bondID,'_2')"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset2"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="$order = 3">
						<xsl:choose>
							<xsl:when test="$aNod/float[@builtin='x3']">
								<xsl:variable name="elemXa" select="$aNod/float[@builtin='x3']"/>
								<xsl:variable name="elemYa" select="$aNod/float[@builtin='y3']"/>
								<xsl:variable name="elemZa" select="$aNod/float[@builtin='z3']"/>
								<xsl:variable name="elemPosa" select="concat ($elemXa,' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemPosa2" select="concat (($elemXa + .04),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemPosa3" select="concat (($elemXa - .04),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemXb" select="$bNod/float[@builtin='x3']"/>
								<xsl:variable name="elemYb" select="$bNod/float[@builtin='y3']"/>
								<xsl:variable name="elemZb" select="$bNod/float[@builtin='z3']"/>
								<xsl:variable name="elemPosb" select="concat ($elemXb,' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="elemPosb2" select="concat (($elemXb + .04),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="elemPosb3" select="concat (($elemXb - .04),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="pset" select="concat($elemPosa,', ',$elemPosb)"/>
								<xsl:variable name="pset2" select="concat($elemPosa2,', ',$elemPosb2)"/>
								<xsl:variable name="pset3" select="concat($elemPosa3,', ',$elemPosb3)"/>
								<xsl:variable name="bondID" select="@id"/>
								<Transform translation="0 0 0">
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="$bondID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="concat($bondID,'_2')"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset2"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="concat($bondID,'_3')"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset3"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="elemXa" select="$aNod/float[@builtin='x2']"/>
								<xsl:variable name="elemYa" select="$aNod/float[@builtin='y2']"/>
								<xsl:variable name="elemZa" select="0"/>
								<xsl:variable name="elemPosa" select="concat ($elemXa,' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemPosa2" select="concat (($elemXa + .04),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemPosa3" select="concat (($elemXa - .04),' ',$elemYa,' ',$elemZa)"/>
								<xsl:variable name="elemXb" select="$bNod/float[@builtin='x2']"/>
								<xsl:variable name="elemYb" select="$bNod/float[@builtin='y2']"/>
								<xsl:variable name="elemZb" select="0"/>
								<xsl:variable name="elemPosb" select="concat ($elemXb,' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="elemPosb2" select="concat (($elemXb + .04),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="elemPosb3" select="concat (($elemXb - .04),' ',$elemYb,' ',$elemZb)"/>
								<xsl:variable name="pset" select="concat($elemPosa,', ',$elemPosb)"/>
								<xsl:variable name="pset2" select="concat($elemPosa2,', ',$elemPosb2)"/>
								<xsl:variable name="pset3" select="concat($elemPosa3,', ',$elemPosb3)"/>
								<xsl:variable name="bondID" select="@id"/>
								<Transform translation="0 0 0">
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="$bondID"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="concat($bondID,'_2')"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset2"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
									<ProtoInstance name="line">
										<xsl:attribute name="DEF"><xsl:value-of select="concat($bondID,_'3')"/></xsl:attribute>
										<fieldValue>
											<xsl:attribute name="name">bond_set</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="$pset3"/></xsl:attribute>
										</fieldValue>
									</ProtoInstance>
								</Transform>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>
		</Group>
	</xsl:template>
	<!--         #	<xsl:include href="CML_chim.xsl"/>           -->
</xsl:stylesheet>
