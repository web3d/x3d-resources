#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE   1
#define FALSE  0

void
write_header( void )
{
  printf("#VRML V2.0 utf8\n" );
  printf("\n" );
  printf("NavigationInfo {\n" );
  /*
  printf("   avatarSize [ 0.000001 0.000001 0.000001 ]\n" );
  printf("   speed 0.0001\n" );
  */
  printf("   type [ \"EXAMINE\" \"ANY\" ]\n" );
  printf("}\n" );
  printf("\n" );
  printf("Viewpoint {\n" );
  printf("  position 0 0 30\n" );
  printf("   description \"Temple\"\n" );
  printf("}\n" );
  printf("\n" );
  printf("Background {\n" );
  printf("  groundAngle [ 1.25 1.37 ]\n" );
  printf("  groundColor [ 0.01 0.09 0.2, 0.05 0.65 0.47, 0.4 0.95 0.85 ]\n" );
  printf("  skyAngle    [ 1.3 1.37 ]\n" );
  printf("  skyColor    [ 0 0 0.2, 0.1 0.1 0.8, 0.7 0.7 1 ]\n" );
  printf("}\n" );
}

void
make_column( double n, double e )
{
  printf( "Transform { \n" );
  printf( "  translation %1.1f 2.5 %1.1f\n", n, e );
  printf( "  children [\n" );
  printf( "     DEF COLUMN Shape {\n" );
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
  printf( "}\n" );
}

void
make_box( double n, double e, double elev, double w, double h, double d )
{
  printf( "Transform {\n" );
  printf( "  translation %1.1lf %1.1lf %1.1lf\n", n, elev, e );
  printf( "  children Transform {\n" );
  /* printf( "    rotation 0 1 0 0.13" ); */
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
  double n = -rows;
  double e = -cols;
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

  make_box( -1, -1, -0.5, w, 1, h );
  make_box( -1, -1, -1.5, w+2, 1, h+2 );
  make_box( -1, -1, -2.5, w+4, 1, h+4 );

  return 0;
}
