#include <stdio.h>
#include <stdlib.h>

int
main( int argc, char **argv )
{
  FILE *handle;
  int  width = 168;
  int  height = 84;
  char *filename = "gtopo8.raw";

  int  values = width * height;
  int  col = 0, i, x, y, h, *val, r = 0, c = 0;
  char buffer[32];
  
  if ( ( handle = fopen( filename, "r" ) ) == NULL ) {
    printf( "Couldn't open file %s\n", filename );
    exit( 1 );
  }

  if ( ( val = malloc( values * sizeof(int) ) ) == NULL ) {
    printf( "Couldn't allocate %d ints of memory\n", values );
    exit( 1 );
  }

  /* read in all of the height values (lrbt ordering) */

  for ( i = 0; i < values; i++ ) {
    fread( &h, 4, 1, handle );
    if ( h == -9999 ) h = 0;
    val[i] = h;
  }

  /* and output in correct ordering */

  for ( y = 0; y < height; y++ ) {
    if ( y % 8 != 0 ) continue;
    r++; c = 0;
    for ( x = 0; x < width; x++ ) {
      if ( x % 8 != 0 ) continue;
      c++;
      h = val[x+(height-1-y)*width];
      sprintf( buffer, " %d", h );
      col += strlen( buffer );
      if ( col > 76 ) {
	printf( "\n" );
	col = strlen( buffer );
      }
      printf( buffer );
    }
  }

  printf( "\n\nrows = %d, cols = %d\n", r, c );
  free( val );
  fclose( handle );

  return 0;
}
