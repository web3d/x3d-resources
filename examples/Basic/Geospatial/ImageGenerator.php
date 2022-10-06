<?
	error_reporting (E_ALL & ~E_NOTICE & ~E_WARNING);
	$image_format = $_GET['f'];
	$p = $_GET['p'];
	if ($image_format == "jpg") header("Content-type: image/jpeg");
	else header("Content-type: image/png");
	//http_send_content_type ("image/png");
	//header("Content-type: image/png");

	$t = $_GET['t'];
	$t = str_replace (array("0", "1", "2", "3"), array("t", "q", "r", "s"), $t);
	$id = str_replace (array("t", "q", "r", "s"), array("0", "1", "2", "3"), $t);
	$level = strlen($t) - 1;
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

	$im = imagecreate (256, 256);
	$bgcolor = imagecolorallocate($im, 255, 255, 255);
	$fgcolor = imagecolorallocate($im, 0, 255, 0);
	$textcolor = imagecolorallocate($im, 0, 0, 0);

	for ($i = 0; $i <= 10; $i ++)
	{
		imageline ($im, $i * 255 / 10, 0, $i * 255 / 10, 255, $fgcolor);
		imageline ($im, 0, $i * 255 / 10, 255, $i * 255 / 10, $fgcolor);
	}

	$g_width = ($latN-$latS) * M_PI * 6378137 / 180 / 10;
	imagestring ($im, 5, 20, 40, "Level: ".$level, $textcolor);
	imagestring ($im, 4, 20, 60, "LatN: ".$latN, $textcolor);
	imagestring ($im, 4, 20, 80, "LatS: ".$latS, $textcolor);
	imagestring ($im, 4, 20, 100, "LonE: ".$lonE, $textcolor);
	imagestring ($im, 4, 20, 120, "LonW: ".$lonW, $textcolor);
	imagestring ($im, 4, 20, 140, "gID: ".$t, $textcolor);
	imagestring ($im, 4, 20, 160, "nID: ".$id, $textcolor);
	imagestring ($im, 4, 20, 180, "Grid Width: ".sprintf("%01.4f", $g_width)." m", $textcolor);
	imagestring ($im, 4, 20, 200, "Pixel Res.: ".sprintf("%01.5f", ($g_width*10/256))." m", $textcolor);
	switch ($p) {
		case "g" : // geographic
				imagestring ($im, 4, 20, 220, "Proj: Geographic", $textcolor);
				break;
		case "m" : // mercator
				imagestring ($im, 4, 20, 220, "Proj: Mercator", $textcolor);
				break;
	}

	if ($image_format == "jpg") imagejpeg ($im);
	else imagepng ($im);
	imagedestroy ($im);

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

		return array ($latitudeN, $latitudeS, $longitudeE, $longitudeW, $xCoverage, $zCoverage);
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
		return 180 * atan(sinh($lat)) / M_PI;
	}

	function getGeographicLongitude($lon)
	{
		return 180 * $lon / M_PI;
	}

?>
