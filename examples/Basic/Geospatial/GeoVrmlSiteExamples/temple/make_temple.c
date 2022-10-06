#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE   1
#define FALSE  0

static double origin_n = 8167780.0;
static double origin_e = 258126.0;

static int use_origin = TRUE;

void
write_header( void )
{
  printf("#VRML V2.0 utf8\n" );
  printf("\n" );
  printf("EXTERNPROTO GeoLocation [\n" );
  printf("  field SFNode   geoOrigin\n" );
  printf("  field MFString geoSystem\n" );
  printf("  field SFString geoCoords\n" );
  printf("  field MFNode   children\n" );
  printf("] \"http://www.ai.sri.com/VRMLSets/v1.0/protos/GeoLocation/GeoLocation.wrl\"\n" );
  printf("\n" );
  if ( use_origin ) {
    printf("PROTO GeoOrigin [\n" );
    printf("  exposedField MFString geoSystem [ \"UTM\" ]\n" );
    printf("  exposedField SFString geoCoords \"Z11 8167780 258126 0\"\n" );
    printf("] { Group{} }\n" );
  }
  printf("\n" );
  printf("NavigationInfo {\n" );
  printf("   avatarSize [ 0.000001 0.000001 0.000001 ]\n" );
  printf("   speed 0.0001\n" );
  printf("   type [ \"EXAMINE\" \"ANY\" ]\n" );
  printf("}\n" );
  printf("\n" );
  printf("Viewpoint {\n" );
  if ( use_origin ) {
    printf("   position -0.0000023708 -0.000003422 .0000138728\n" );
    printf("   orientation .827405 -.5579944 .0635881 .273737\n" );
  } else {
    printf("   position -.3449464 -.4994166 2.030750\n" );
    printf("   orientation .8385237 -.5404772 0.069009 .265539\n" );
  }
  printf("   description \"Temple\"\n" );
  printf("}\n" );
  printf("\n" );
  printf("Background {\n" );
  /*
  printf("  groundAngle [ 1.45 2.57 ]\n" );
  printf("  groundColor [ 0.01 0.09 0.2, 0.05 0.35 0.47, 0.4 0.75 0.85 ]\n" );
  printf("  skyAngle    [ 0.9 2.57 ]\n" );
  printf("  skyColor    [ 0 0 0.2, 0.1 0.1 0.8, 0.7 0.7 1 ]\n" );
  */
  printf("  skyColor    [ 1 1 1 ]\n" );
  printf("}\n" );
}

void
make_column( double n, double e )
{
  static int first_time = TRUE;

  if ( first_time ) {
    printf( "GeoLocation {\n" );
    printf( "  geoSystem [ \"UTM\" ]\n" );
    printf( "  geoCoords \"Z11 %1.1lf %1.1lf 2.5\"\n", n, e );
    if ( use_origin ) {
      printf( "  geoOrigin DEF ORIGIN GeoOrigin { \n" );
      printf( "     geoSystem [ \"UTM\" ]\n" );
      printf( "     geoCoords \"Z11 %lg %lg 0\"\n", origin_n, origin_e );
      printf( "  }\n" );
    }
    printf( "  children Transform { \n" );
    printf( "     children [\n" );
    printf( "        DEF COLUMN Shape {\n" );
    printf( "          appearance Appearance {\n" );
    printf( "            texture ImageTexture { url \"stone6.gif\" }\n" );
    printf( "            material Material { diffuseColor 1 .9 .8 }\n" );
    printf( "          }\n" );
    /*
    printf( "          appearance Appearance { material\n" );
    printf( "            Material { diffuseColor 1 1 0 }}\n" );
    */
    printf( "          geometry Cylinder { radius 0.6 height 5 }\n" );
    printf( "      }\n" );
    printf( "    ] \n" );
    printf( "  }\n" );
    printf( "}\n" );
    first_time = FALSE;
  } else {
    printf("GeoLocation {\n" );
    printf("  geoSystem [ \"UTM\" ]\n" );
    printf("  geoCoords \"Z11 %1.1lf %1.1lf 2.5\"\n", n, e );
    if ( use_origin )
      printf("  geoOrigin USE ORIGIN\n" );
    printf("  children USE COLUMN\n" );
    printf("}\n" );
  }
}

void
make_box( double n, double e, double elev, double w, double h, double d )
{
  printf( "GeoLocation {\n" );
  printf( "  geoSystem [ \"UTM\" ]\n" );
  printf( "  geoCoords \"Z11 %1.1lf %1.1lf %1.1lf\"\n", n, e, elev );
  if ( use_origin )
    printf( "  geoOrigin USE ORIGIN\n" );
  printf( "  children Transform {\n" );
  printf( "    rotation 0 1 0 0.13" );
  printf( "    children Shape {\n" );
  printf( "          appearance Appearance {\n" );
  printf( "            texture ImageTexture { url \"stone5.gif\" }\n" );
  printf( "            material Material { diffuseColor 1 1 1 }\n" );
  printf( "          }\n" );
    /*
      printf( "    appearance Appearance { material\n" );
      printf( "      Material { diffuseColor 1 0 0 }}\n" );
    */
  printf( "    geometry Box { size %1.1lf %1.1lf %1.1lf }\n", w, h, d );
  printf( "  }}\n" );
  printf( "}\n" );
}

int
main( int argc, char **argv )
{
  int    rows = 11;
  int    cols = 11;
  double n = origin_n - rows;
  double e = origin_e - cols;
  double w = cols * 2;
  double h = rows * 2;
  int    x, y;

  write_header();

  for ( x = 0; x < cols; x++ ) {
    for ( y = 0; y < rows; y++ ) {
      if ( y > 1 && y < rows-2 && x > 1 && x < cols-2 ) continue;
      make_column( n + y*2, e + x*2 );
    }
  }

  make_box( origin_n - 1, origin_e - 1, -0.5, w, 1, h );
  make_box( origin_n - 1, origin_e - 1, -1.5, w+2, 1, h+2 );
  make_box( origin_n - 1, origin_e - 1, -2.5, w+4, 1, h+4 );

  return 0;
}
