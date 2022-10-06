<?

/*
       <meta content='OpenStreetMapToX3D.php' name='title'/>
       <meta content='PHP script to retrieve OpenMap imagery on top of sphere - TODO, superimpose X3D Earth topography' name='description'/>
       <meta content='1 April 2008' name='created'/>
       <meta content='4 February 2009' name='revised'/>
       <meta content='Byounghyun Yoo and Don Brutzman' name='author'/>
       <meta content='http://sourceforge.net/p/x3d/code/HEAD/tree/www.web3d.org/x3d/content/examples/Basic/GeoSpatial/OpenStreetMapToX3D.php' name='reference'/>
       <meta content='http://x3d-earth.nps.edu' name='reference'/>
       <meta content='http://www.web3D.org/x3d-earth' name='reference'/>
       <meta content='X3D-Earth server-side autogeneration' name='subject'/>
       <meta content='under development' name='warning'/>
       <meta content='http://www.web3d.org/x3d/content/examples/Basic/GeoSpatial/OpenStreetMapToX3D.php' name='identifier'/>
       <meta content='OpenStreetMapToX3D.php, http://www.web3d.org/x3d/content/examples/Basic/GeoSpatial/OpenStreetMapToX3D.php' name='generator'/>
       <meta content='http://www.web3d.org/x3d/content/examples/license.html' name='license'/>
 *
 * TODO:
 * - build README for installation, operation, modification, testing, (re)deployment
 * - default to workable initial globe view if no initial coordinate area is passed in as parameter
 * - module settings for apache httpd
 * - .x3d example that invokes it as an Inline
 * - see email trail...
 */

//  Administrator setup section
    $urlPath  = "http://x3d-earth.nps.edu/"; // configure to match local server name, plus path (if any), plus /

// X3D Earth scene-graph design pattern parameters

// PHP script

//http_send_content_type ("model/x3d+xml");
	//http_send_content_type ("text/xml");
	if ($_GET['mime'] == "xml")
		header("Content-type: text/xml");
	else
		header("Content-type: model/x3d+xml");

	$max_height = 50000000;
	$semi_major_axis = 6378137;
	$semi_minor_axis = 6356752.314245;

	$t = $_GET['t'];
	$f = $_GET['f'];
	$p = $_GET['p']; // Projection type
	$t = str_replace (array("0", "1", "2", "3"), array("t", "q", "r", "s"), $t);
	$id = str_replace (array("t", "q", "r", "s"), array("0", "1", "2", "3"), $t);
	//$id_ve = str_replace (array("q", "r", "s", "t"), array("0", "1", "2", "3"), $t);
	$level = strlen($t) - 1;
	//$url_texture = $t.".jpg";
	//$url_texture = $t.".png";
	//if (($id % 4) == 1) $url_texture = $id.".jpg";
	//else $url_texture = $id.".png"; 
	//$url_texture = "m".$id.".jpg";	
	//$url_texture = $id.".png";
	$url_texture = $id.".png";
	
	// OpenStreetMap URL

	$n = rand (0, 2);
	$n = str_replace (array("0", "1", "2"), array("a", "b", "c"), $n);
	$ref_head = "http://".$n.".tile.openstreetmap.org/";
	$ref_foot = ".png";
	$level = strlen($t) - 1;
	$tile = getTileInfo($t);
	$x = $tile[1];
	$y = $tile[2];
	//$url_texture = $ref_head.$level."/".$x."/".$y.$ref_foot;

	switch ($f) {
		case "g": // google
				$url_texture = "g".$id.".jpg";
				break;
		case "m": // ms
				$url_texture = "m".$id.".jpg";
				break;
		case "l": // on-the-fly grid structure
				$url_texture = $id.$p.".png";
				break;
		case "a": // mix l & m
				if (substr ($id, -1) == "1") $url_texture = $ref_head.$level."/".$x."/".$y.$ref_foot;
				else if (substr ($id, -1) == "3") $url_texture = $ref_head.$level."/".$x."/".$y.$ref_foot;
				else $url_texture = $id.$p.".png";
				break;
		case "d": // ms cache
				$url_texture = $ref_head.$level."/".$x."/".$y.$ref_foot;
				break;
	}

	switch ($p) {
		case "g" : // geographic
				$coverage = getSatLatLongGeographic($t);
				break;
		case "m" : // mercator
				$coverage = getSatLatLong($t);
				break;
	}
	$latN = $coverage[0];
	$latS = $coverage[1];
	$lonE = $coverage[2];
	$lonW = $coverage[3];
	$xCoverage = $coverage[4];
	$zCoverage = $coverage[5];
	$Mercator_latN = $coverage[6];
	$Mercator_latS = $coverage[7];
	$latCenter = ($latN + $latS) / 2;
	$lonCenter = ($lonE + $lonW) / 2;
	$height = ($max_height - $semi_major_axis)/ pow (2, $level); // height for geoViewpoint
	$orientation = "-1 0 0 1.57";
	$geoOrigin = "0 0 ".-1*$semi_major_axis;
	//$geoOrigin = $latCenter." ".$lonCenter." 0";
	$geoViewpoint = $latCenter." ".$lonCenter." ".$height;
	$geoLODcenter = $latCenter." ".$lonCenter." 0";
	$geoLODrange = ($max_height - $semi_major_axis) / pow (2, $level) * 1.5;
	$geoGridOrigin = $latS." ".$lonW." 0";
	$xDimension = 20 + 1; //xDimension = # of xGrid + 1
	$zDimension = 20 + 1; //zDimension = # of zGrid + 1
	$xSpacing = $xCoverage / ($xDimension - 1);
	$zSpacing = $zCoverage / ($zDimension - 1);
	$geoLODheight ="";
	if ($p=="m") {
		$textureCoord = "";
		for ($i = 0; $i < $zDimension; $i ++)
		{
				$textureLatitude = $i / ($zDimension - 1) * ($latN - $latS) + $latS;
				$textMercatorLatitude = getMercatorLatitude($textureLatitude);
				$textCoord_t = sprintf ("%.8f", ($textMercatorLatitude - $Mercator_latS) / ($Mercator_latN - $Mercator_latS));
			for ($j = 0; $j < $xDimension; $j ++)
			{
				$geoLODheight .= " 0";
				$textCoord_s = $j / ($xDimension - 1);
				$textureCoord .=" ".$textCoord_s." ".$textCoord_t.",";
			}
		}
		$textureCoord = substr ($textureCoord, 0, -1);
	}

	if ($p=="g")
		for ($i = 0; $i < $zDimension; $i ++)
			for ($j = 0; $j < $xDimension; $j ++)
				$geoLODheight .= " 0";

    $selfName = "osm".$f.$t.$p;

	// tile ID referencing mechanism using variable q r s t (Google form)
    $childUrl_t =
               "child1Url='osm".$f.$t."t".$p.".x3d"."'
				child2Url='osm".$f.$t."q".$p.".x3d"."' 
				child3Url='osm".$f.$t."r".$p.".x3d"."' 
				child4Url='osm".$f.$t."s".$p.".x3d"."'";

	// tile ID referencing mechanism using integers 0..3 (Microsoft form)
    $childUrl_i =
               "child1Url='osm".$f.$id."0".$p.".x3d"."'
				child2Url='osm".$f.$id."1".$p.".x3d"."'
				child3Url='osm".$f.$id."2".$p.".x3d"."'
				child4Url='osm".$f.$id."3".$p.".x3d"."'";

	$x3d_head = "
<?xml version=\"1.0\" encoding=\"UTF-8\"?> 
<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.2//EN\" \"http://www.web3d.org/specifications/x3d-3.2.dtd\"> 
<X3D 
	profile=\"Immersive\" 
	version=\"3.2\" 
	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema-instance\" 
	xsd:noNamespaceSchemaLocation=\"http://www.web3d.org/specifications/x3d-3.2.xsd\"> 
	<head> 
		<component name=\"Geospatial\" level=\"1\"/> 
        <meta content='".$selfName."' name='title'/>
        <meta content='".$id.".png' name='image'/>
        <meta content='".$urlPath.$selfName.".x3d' name='identifier'/>
        <meta content='X3D Earth tile via .php script invocation' name='description'/>
        <meta content='".date()."' name='created'/>
        <meta content='http://x3d-earth.nps.edu' name='reference'/>
        <meta content='http://www.web3D.org/x3d-earth' name='reference'/>
        <meta content='http://www.web3d.org/x3d/content/examples/Basic/GeoSpatial/OpenStreetMapToX3D.php' name='reference'/>
        <meta content='http://sourceforge.net/p/x3d/code/HEAD/tree/www.web3d.org/x3d/content/examples/Basic/GeoSpatial/OpenStreetMapToX3D.php' name='reference'/>
        <meta content='X3D-Earth tile' name='subject'/>
        <meta content='under development' name='warning'/>
        <meta content='OpenStreetMapToX3D.php, http://www.web3d.org/x3d/content/examples/Basic/GeoSpatial/OpenStreetMapToX3D.php' name='generator'/>
        <meta content='http://www.web3d.org/x3d/content/examples/license.html' name='license'/>
</head>";

	$x3d_foot = "
</X3D>";

// TODO replace escaped-quotation \" with '

	$x3d_scene ="
	<Scene> 
		<GeoViewpoint
			description=\"GeoViewpoint ".$id."\"
			geoSystem=\"GDC\"
			position=\"".$geoViewpoint."\"
			orientation=\"".$orientation."\">
			<!--TODO mechanism for deciding GeoOrigin, or simple omission.  Note node is also USEd below.
                GeoOrigin DEF=\"ORIGIN\"
				geoCoords=\"".$geoOrigin."\"
				geoSystem=\"GDC\"/-->
		</GeoViewpoint>
		<GeoLOD 
			center=\"".$geoLODcenter."\" 
			range=\"".$geoLODrange."\" 
			geoSystem=\"GDC\" ".$childUrl_i."> 
		<!--GeoOrigin USE=\"ORIGIN\" /-->
		<Group containerField='rootNode'> 
			<Shape> 
				<Appearance> 
					<Material
						emissiveColor=\"0.5 0.5 0.5\"/> 
					<ImageTexture 
						url=\"".$url_texture." ".$urlPath.$url_texture."\"
						repeatS=\"false\" 
						repeatT=\"false\" 
					/>
				</Appearance> 
				<GeoElevationGrid 
					ccw='true' 
					colorPerVertex='false' 
					creaseAngle='0' 
					geoGridOrigin =\"".$geoGridOrigin."\" 
					yScale ='1.0' 
					geoSystem='\"GDC\"' 
					normalPerVertex='false' 
					solid='true' 
					xDimension='".$xDimension."' 
					xSpacing='".$xSpacing."' 
					zDimension='".$zDimension."' 
					zSpacing='".$zSpacing."' 
					height=\"".$geoLODheight."\">";
	if ($p=="m") {
		$x3d_scene .= "
					<TextureCoordinate point=\"".$textureCoord."\"/>";
	}
	$x3d_scene .= "
					<!--GeoOrigin USE=\"ORIGIN\" /--> 
				</GeoElevationGrid> 
			</Shape> 
		</Group> 
		</GeoLOD>
	</Scene>";

	echo $x3d_head.$x3d_scene.$x3d_foot;

	function getSatLatLong($tileid)
	{
		$zoom = strlen($tileid) - 1;

		$latN = M_PI;
		$lonW = -M_PI;
		$coverage = 2 * M_PI;

		for ($i = 1; $i <= $zoom; $i++)
		{
			$id = substr($tileid, $i, 1);
			$coverage /= 2;

			if (($id == "s") || ($id == "t")) $latN -= $coverage;
			if (($id == "r") || ($id == "s")) $lonW += $coverage;
		}

		$latS = $latN - $coverage;
		$lonE = $lonW + $coverage;

		$latitudeN = getGeographicLatitude($latN);
		$latitudeS = getGeographicLatitude($latS);
		$longitudeE = getGeographicLongitude($lonE);
		$longitudeW = getGeographicLongitude($lonW);
		$xCoverage = $longitudeE - $longitudeW;
		$zCoverage = $latitudeN - $latitudeS;

		return array ($latitudeN, $latitudeS, $longitudeE, $longitudeW, $xCoverage, $zCoverage, $latN, $latS);
	}

	function getSatLatLongGeographic($tileid)
	{
		$zoom = strlen($tileid) - 1;

		$latN = M_PI/2;
		$lonW = -M_PI;
		$coverage = 2 * M_PI;

		for ($i = 1; $i <= $zoom; $i++)
		{
			$id = substr($tileid, $i, 1);
			$coverage /= 2;

			if (($id == "s") || ($id == "t")) $latN -= $coverage/2;
			if (($id == "r") || ($id == "s")) $lonW += $coverage;
		}

		$latS = $latN - $coverage/2;
		$lonE = $lonW + $coverage;

		$latitudeN = $latN * 180 / M_PI;
		$latitudeS = $latS * 180 / M_PI;
		$longitudeE = $lonE * 180 / M_PI;
		$longitudeW = $lonW * 180 / M_PI;
		$xCoverage = $longitudeE - $longitudeW;
		$zCoverage = $latitudeN - $latitudeS;

		return array ($latitudeN, $latitudeS, $longitudeE, $longitudeW, $xCoverage, $zCoverage, $latN, $latS);
	}

	function getGeographicLatitude($lat)
	{
		return 180 * atan(sinh($lat)) / M_PI; // standard PHP library functions and constants
	}

	function getGeographicLongitude($lon)
	{
		return 180 * $lon / M_PI;
	}

	function getMercatorLatitude($lat)
	{
		if ($lat > 90) $lat = $lat - 180;
		if ($lat < -90) $lat = $lat + 180;
		$phi = M_PI * $lat / 180; // conversion degree=>radians
		$res = 0.5 * log((1 + sin($phi)) / (1 - sin($phi)));
		return $res;
	}

	function getTileInfo($tileid)
	{
		$zoom = strlen($tileid) - 1;

		$x = 0;
		$y = 0;
		$max = 1;

		for ($i = 1; $i <= $zoom; $i++)
		{
			$id = substr($tileid, $i, 1);
			$x *= 2;
			$y *= 2;
			$max *= 2;
			if (($id == "s") || ($id == "t")) $y += 1;
			if (($id == "r") || ($id == "s")) $x += 1;
		}

		return array ($zoom, $x, $y);
	}
?>
