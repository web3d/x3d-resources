<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : KmlToX3dViewpoints.xslt
    Identifier : http://www.web3d.org/x3d/content/examples/Basic/GeoSpatial/X3dToKmlViewpoints.xslt
    Created on : 4 March 2010
    Author     : Don Brutzman, Naval Postgraduate School
    Description: Convert KML placemarks into X3D GeoViewpoints and interpolated tours
    License    : https://savage.nps.edu/Savage/license.html
-->

<xsl:stylesheet version="1.1"
    xmlns="http://www.web3d.org/specifications/x3d-3.2.xsd"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->

    <!-- ============================= -->
    
    <xsl:variable name="tourAltitude"  select="200000"/>
    <!-- TODO earthSurfaceHeight -->
    <xsl:variable name="viewInterval"  select="10"/>
    
    <xsl:template match="/">
      <xsl:text disable-output-escaping="yes"><![CDATA[<!DOCTYPE X3D PUBLIC "ISO//Web3D//DTD X3D 3.2//EN" "http://www.web3d.org/specifications/x3d-3.2.dtd">]]>&#10;</xsl:text>
      <xsl:text disable-output-escaping="yes"><![CDATA[<X3D profile='Immersive' version='3.2' xmlns:xsd='http://www.w3.org/2001/XMLSchema-instance' xsd:noNamespaceSchemaLocation='http://www.web3d.org/specifications/x3d-3.2.xsd'>]]>&#10;</xsl:text>
            <head> 
                <component level='1' name='Geospatial'/>
                <meta content='CaliforniaCampusesTour5Altitudes.x3d' name='title'/>
                <meta content='Viewpoints and tour of California campuses' name='description'/>
                <meta content='Don Brutzman, Dale Tourtelotte, Mike Bailey, Don McGregor' name='creator'/>
                <meta content='4 March 2010' name='created'/>
                <meta content='7 August 2010' name='modified'/>
                <meta content='CaliforniaCampuses.kml' name='reference'/>
                <meta content='http://www.web3D.org/x3d-earth' name='reference'/>
                <meta content='http://x3d-earth.nps.edu' name='reference'/>
                <meta content='http://hamming.uc.nps.edu' name='reference'/>
                <meta content='X3D Earth' name='subject'/>
                <meta content='http://www.web3d.org/x3d/content/examples/Basic/GeoSpatial/CaliforniaCampusesTour5Altitudes.x3d' name='identifier'/>
                <meta content='http://mmog.ern.nps.edu/California/California.x3d' name='reference'/>
                <meta content='under development' name='warning'/>
                <meta content='mmog.ern.nps.edu restricted to internal access within NPS firewall only' name='warning'/>
                <meta content='KmlToX3dViewpoints.xslt' name='generator'/>
                <meta content='X3D-Edit, https://savage.nps.edu/X3D-Edit' name='generator'/>
                <meta content='https://savage.nps.edu/Savage/license.html' name='license'/>
            </head>
            <Scene>
                <Group DEF="LightGroup">
                  <!-- lights are for looking at off angles, headlight is sufficient for looking straight down
                  <PointLight location='10000000000 0 0' radius='100000000000000'/>
                  <PointLight location='-10000000000 0 0' radius='100000000000000'/>
                  <PointLight location='0 10000000000 0' radius='100000000000000'/>
                  <PointLight location='0 -10000000000 0' radius='100000000000000'/>
                  <PointLight location='0 0 10000000000' radius='100000000000000'/>
                  <PointLight location='0 0 -10000000000' radius='100000000000000'/>
                  -->
                </Group>
                <Switch DEF='SwitchGlobes' whichChoice='-1'>
                    <xsl:comment> Select globe of interest.  Warning: may need to keep alternate globes commented out to prevent browser caching. </xsl:comment>
                    <xsl:comment> <![CDATA[
                      <Inline load='true' url='"CaliforniaDemo.x3d"'/>
]]>
                    </xsl:comment>
                    <xsl:comment> <![CDATA[
                      <Inline load='true' url='"http://x3d-earth.nps.edu/osmdemo.x3d"'/>
]]>
                    </xsl:comment>
                    <xsl:comment> <![CDATA[
                      <Inline load='false' url='"http://mmog.ern.nps.edu/California/California.x3d"'/>
]]>
                    </xsl:comment>
                    <xsl:comment> <![CDATA[
                      <Inline load='false' url='"http://x3d-earth.nps.edu/7_levels_plus/tiles/0/globe.x3d"'/>
]]>
                    </xsl:comment>
                    <xsl:comment> <![CDATA[
                      <Inline load='false' url='"http://x3d-earth.nps.edu/globe/MBARI1MinuteBathy/world.x3d"'/>
]]>
                    </xsl:comment>
                    <xsl:comment> <![CDATA[
                      <Inline load='false' url='"http://x3d-earth.nps.edu/globe/SRTM30Plus/world.x3d"'/>
]]>
                    </xsl:comment>
                </Switch>
                <!-- TODO route true/false to load field when Switch selection changes -->

                <!-- keep "quotes" literal (rather than escaped -->
                <xsl:text>&#10;</xsl:text>
                <xsl:text disable-output-escaping="yes"><![CDATA[<NavigationInfo transitionType='"ANIMATE"'/>]]>&#10;</xsl:text>
                
                <Group DEF='PlacemarkGroup'>
                    <xsl:apply-templates select="*"/>
                </Group>

                <xsl:comment> ==================== </xsl:comment>
                <ExternProtoDeclare appinfo='CrossHair prototype provides a heads-up display (HUD) crosshair at the view center, which is useful for assessing NavigationInfo lookAt point' name='CrossHair' url='"../../Savage/Tools/HeadsUpDisplays/CrossHairPrototype.x3d#CrossHair" "https://savage.nps.edu/Savage/Tools/HeadsUpDisplays/CrossHairPrototype.x3d#CrossHair" "../../Savage/Tools/HeadsUpDisplays/CrossHairPrototype.wrl#CrossHair" "https://savage.nps.edu/Savage/Tools/HeadsUpDisplays/CrossHairPrototype.wrl#CrossHair"'>
                  <field accessType='initializeOnly' appinfo='whether CrossHair prototype is enabled or not' name='enabled' type='SFBool'/>
                  <field accessType='inputOnly' appinfo='control whether enabled/disabled' name='set_enabled' type='SFBool'/>
                  <field accessType='inputOutput' appinfo='color of CrossHair marker' name='markerColor' type='SFColor'/>
                  <field accessType='inputOutput' appinfo='size of CrossHair in meters' name='scale' type='SFVec3f'/>
                  <field accessType='inputOutput' appinfo='distance in front of HUD viewpoint' name='positionOffsetFromCamera' type='SFVec3f'/>
                </ExternProtoDeclare>
                <ProtoInstance DEF='CrossHairInstance' name='CrossHair'>
                  <fieldValue name='enabled' value='true'/>
                  <fieldValue name='markerColor' value='0.1 0.8 0.1'/>
                  <fieldValue name='scale' value='0.5 0.5 0.5'/>
                  <fieldValue name='positionOffsetFromCamera' value='0 0 -5'/>
                </ProtoInstance>
                <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/CrossHairExample.x3d -->
                <xsl:comment> ==================== </xsl:comment>
                <ExternProtoDeclare appinfo='Heads-up display (HUD) keeps child geometry aligned on screen in a consistent location' name='HeadsUpDisplay' url='"../../../X3dForWebAuthors/Chapter14-Prototypes/HeadsUpDisplayPrototype.x3d#HeadsUpDisplay" "http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14-Prototypes/HeadsUpDisplayPrototype.x3d#HeadsUpDisplay" "../../../X3dForWebAuthors/Chapter14-Prototypes/HeadsUpDisplayPrototype.wrl#HeadsUpDisplay" "http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14-Prototypes/HeadsUpDisplayPrototype.wrl#HeadsUpDisplay"'>
                  <field accessType='inputOutput' appinfo='X3D content positioned at HUD offset' name='children' type='MFNode'/>
                  <field accessType='inputOutput' appinfo='offset position for HUD relative to current view location, default 0 0 -5' name='screenOffset' type='SFVec3f'/>
                  <field accessType='outputOnly' appinfo='HUD position update (in world coordinates) relative to original location' name='position_changed' type='SFVec3f'/>
                  <field accessType='outputOnly' appinfo='HUD orientation update relative to original location' name='orientation_changed' type='SFRotation'/>
                </ExternProtoDeclare>
                <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/HeadsUpDisplayExample.x3d -->
                <xsl:comment> ==================== </xsl:comment>
                <ExternProtoDeclare appinfo='Sequentially binds each Viewpoint in a set of Viewpoint USE nodes, creating an automatic tour for a scene' name='ViewpointSequencer' url='"../../Savage/Tools/Animation/ViewpointSequencerPrototype.x3d#ViewpointSequencer" "https://savage.nps.edu/Savage/Tools/Animation/ViewpointSequencerPrototype.x3d#ViewpointSequencer" "../../Savage/Tools/Animation/ViewpointSequencerPrototype.wrl#ViewpointSequencer" "https://savage.nps.edu/Savage/Tools/Animation/ViewpointSequencerPrototype.wrl#ViewpointSequencer"'>
                  <field accessType='initializeOnly' appinfo='Viewpoint USE nodes that are sequentially bound' name='viewpoints' type='MFNode'/>
                  <field accessType='inputOutput' appinfo='number of seconds between viewpoint shifts' name='interval' type='SFTime'/>
                  <field accessType='inputOutput' appinfo='whether ViewpointSequencer is enabled or not' name='enabled' type='SFBool'/>
                  <field accessType='inputOnly' appinfo='whether ViewpointSequencer is enabled or not' name='set_enabled' type='SFBool'/>
                  <field accessType='inputOnly' appinfo='bind previous Viewpoint in list' name='previous' type='SFBool'/>
                  <field accessType='inputOnly' appinfo='bind next Viewpoint in list' name='next' type='SFBool'/>
                  <field accessType='inputOutput' appinfo='Select message to toggle ViewpointSequencer' name='toggleMessage' type='MFString'/>
                  <field accessType='initializeOnly' appinfo='Color for toggleMessage text' name='toggleMessageFontSize' type='SFFloat'/>
                  <field accessType='inputOutput' appinfo='Color for toggleMessage text' name='toggleMessageColor' type='SFColor'/>
                  <field accessType='inputOutput' appinfo='enable console output' name='traceEnabled' type='SFBool'/>
                </ExternProtoDeclare>
                <ProtoInstance DEF='ViewpointTour' name='ViewpointSequencer'>
                    <fieldValue name='interval' value='{$viewInterval}'/>
                    <xsl:comment> TODO initially enabled needs to be off, and scene provides selectable text or Viewpoint to activate </xsl:comment>
                    <fieldValue name='enabled' value='true'/>
                    <fieldValue name='toggleMessage' value='ViewpointSequencer tour'/>
                    <fieldValue name='viewpoints'>
                        <xsl:for-each select="//Placemark">
                            <xsl:element name="GeoViewpoint">
                                <xsl:attribute name="USE">
                                    <xsl:text>View</xsl:text>
                                    <xsl:number value="position()" format="01"/>
                                    <xsl:text>a</xsl:text>
                                </xsl:attribute>
                            </xsl:element>
                            <xsl:element name="GeoViewpoint">
                                <xsl:attribute name="USE">
                                    <xsl:text>View</xsl:text>
                                    <xsl:number value="position()" format="01"/>
                                    <xsl:text>b</xsl:text>
                                </xsl:attribute>
                            </xsl:element>
                            <xsl:element name="GeoViewpoint">
                                <xsl:attribute name="USE">
                                    <xsl:text>View</xsl:text>
                                    <xsl:number value="position()" format="01"/>
                                    <xsl:text>c</xsl:text>
                                </xsl:attribute>
                            </xsl:element>
                            <xsl:element name="GeoViewpoint">
                                <xsl:attribute name="USE">
                                    <xsl:text>View</xsl:text>
                                    <xsl:number value="position()" format="01"/>
                                    <xsl:text>d</xsl:text>
                                </xsl:attribute>
                            </xsl:element>
                            <xsl:element name="GeoViewpoint">
                                <xsl:attribute name="USE">
                                    <xsl:text>View</xsl:text>
                                    <xsl:number value="position()" format="01"/>
                                    <xsl:text>e</xsl:text>
                                </xsl:attribute>
                            </xsl:element>
                        </xsl:for-each>
                        <Viewpoint DEF='View4' description='View four (-X axis)' orientation='0 1 0 -1.57' position='-10 0 0'/>
                    </fieldValue>
                </ProtoInstance>
                <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/ViewpointSequencerExample.x3d -->
                <xsl:comment> ==================== </xsl:comment>
                <!-- TODO recompute lat/long from first Placemark -->
                <GeoLocation geoCoords='36.595599 -121.877148 624990'>
                    <Billboard axisOfRotation='0 0 0'>
                        <Shape>
                          <Text string='"ViewpointSequencer tour"'>
                            <FontStyle justify='"MIDDLE" "MIDDLE"'/>
                          </Text>
                          <Appearance>
                          <Material ambientIntensity='0.25' diffuseColor='0.795918 0.505869 0.093315' shininess='0.39' specularColor='0.923469 0.428866 0.006369'>
                            <!-- Universal Media Library: Autumn 9 -->
                          </Material>
                          </Appearance>
                        </Shape>
                    </Billboard>
                    <TouchSensor DEF='TourTouch' description='Touch text to turn tour on/off'/>
                    <BooleanToggle DEF='TourToggle'/>
                    <ROUTE fromField='isActive' fromNode='TourTouch' toField='set_boolean' toNode='TourToggle'/>
                    <ROUTE fromField='toggle' fromNode='TourToggle' toField='set_enabled' toNode='ViewpointTour'/>
                </GeoLocation>
                <xsl:comment> ==================== </xsl:comment>
                <ExternProtoDeclare appinfo='ViewPositionOrientation provides provides console output of local position and orientation as user navigates' name='ViewPositionOrientation' url='"../../Savage/Tools/Authoring/ViewPositionOrientationPrototype.x3d#ViewPositionOrientation" "https://savage.nps.edu/Savage/Tools/Authoring/ViewPositionOrientationPrototype.x3d#ViewPositionOrientation" "../../Savage/Tools/Authoring/ViewPositionOrientationPrototype.wrl#ViewPositionOrientation" "https://savage.nps.edu/Savage/Tools/Authoring/ViewPositionOrientationPrototype.wrl#ViewPositionOrientation"'>
                  <field accessType='inputOutput' appinfo='Whether or not ViewPositionOrientation sends output to console' name='enabled' type='SFBool'/>
                  <field accessType='initializeOnly' appinfo='Output internal trace messages for debugging this node, intended for developer use only' name='traceEnabled' type='SFBool'/>
                  <field accessType='inputOnly' appinfo='Ability to turn output tracing on/off at runtime' name='set_traceEnabled' type='SFBool'/>
                  <field accessType='outputOnly' appinfo='Output local position' name='position_changed' type='SFVec3f'/>
                  <field accessType='outputOnly' appinfo='Output local orientation' name='orientation_changed' type='SFRotation'/>
                  <field accessType='outputOnly' appinfo='MFString value of new Viewpoint' name='outputViewpointString' type='MFString'/>
                </ExternProtoDeclare>
                <ProtoInstance DEF='ExampleViewPositionOrientation' name='ViewPositionOrientation'>
                  <fieldValue name='enabled' value='false'/>
                </ProtoInstance>
                <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/ViewPositionOrientationExample.x3d -->
                <xsl:comment> ==================== </xsl:comment>

            </Scene>
      <xsl:text disable-output-escaping="yes">&#10;<![CDATA[</X3D>]]>&#10;</xsl:text>
    </xsl:template>

    <!-- ============================= -->

    <xsl:template match="*">

        <!-- trace tree traversal
        <xsl:comment>
            <xsl:text>KML element </xsl:text>
            <xsl:value-of select="name()"/>
        </xsl:comment>
        -->

        <xsl:apply-templates select="*"/>

    </xsl:template>

    <!-- ============================= -->

    <xsl:template match="Placemark">

        <xsl:variable name="longitude" select="                 substring-before(normalize-space(Point/coordinates),',')"/>
        <xsl:variable name="latitude"  select="substring-before(substring-after (normalize-space(Point/coordinates),','),',')"/>
        <xsl:element name="GeoViewpoint">
            <xsl:attribute name="DEF">
                <xsl:text>View</xsl:text>
                <xsl:number value="count(preceding::Placemark)+1" format="01"/>
                <xsl:text>a</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="name"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 1000"/>
                <xsl:text>km</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="orientation">
                <xsl:text>1 0 0 -1.57</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="position">
                <xsl:value-of select="$latitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$longitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude"/>
            </xsl:attribute>
            <!-- use KML Lookat for position and orientation -->
        </xsl:element>
                <xsl:element name="GeoViewpoint">
            <xsl:attribute name="DEF">
                <xsl:text>View</xsl:text>
                <xsl:number value="count(preceding::Placemark)+1" format="01"/>
                <xsl:text>b</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="name"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 2000"/>
                <xsl:text>km</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="orientation">
                <xsl:text>1 0 0 -1.57</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="position">
                <xsl:value-of select="$latitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$longitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 2"/>
            </xsl:attribute>
            <!-- use KML Lookat for position and orientation -->
        </xsl:element>
        <xsl:element name="GeoViewpoint">
            <xsl:attribute name="DEF">
                <xsl:text>View</xsl:text>
                <xsl:number value="count(preceding::Placemark)+1" format="01"/>
                <xsl:text>c</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="name"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 4000"/>
                <xsl:text>km</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="orientation">
                <xsl:text>1 0 0 -1.57</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="position">
                <xsl:value-of select="$latitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$longitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 4"/>
            </xsl:attribute>
            <!-- use KML Lookat for position and orientation -->
        </xsl:element>
        <xsl:element name="GeoViewpoint">
            <xsl:attribute name="DEF">
                <xsl:text>View</xsl:text>
                <xsl:number value="count(preceding::Placemark)+1" format="01"/>
                <xsl:text>d</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="name"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 8000"/>
                <xsl:text>km</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="orientation">
                <xsl:text>1 0 0 -1.57</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="position">
                <xsl:value-of select="$latitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$longitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 8"/>
            </xsl:attribute>
            <!-- use KML Lookat for position and orientation -->
        </xsl:element>
        <xsl:element name="GeoViewpoint">
            <xsl:attribute name="DEF">
                <xsl:text>View</xsl:text>
                <xsl:number value="count(preceding::Placemark)+1" format="01"/>
                <xsl:text>e</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="name"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 20000"/>
                <xsl:text>km</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="orientation">
                <xsl:text>1 0 0 -1.57</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="position">
                <xsl:value-of select="$latitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$longitude"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$tourAltitude div 20"/>
            </xsl:attribute>
            <!-- use KML Lookat for position and orientation -->
        </xsl:element>

    </xsl:template>

</xsl:stylesheet>
