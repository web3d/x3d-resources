package vrml.field;
import vrml.*;

public class MFColor extends MField
{
  public int getSize() { return 0; }
  public void clear() {}
  public void delete(int index) {}
  public MFColor()
  {
  }
  public MFColor(float colors[][])
  {
  }
  public MFColor(float colors[])
  {
  }
  public MFColor(int size, float colors[])
  {
  }

  public void getValue(float colors[][])
  {
  }
  public void getValue(float colors[])
  {
  }

  public void get1Value(int index, float colors[])
  {
  }
  public void get1Value(int index, SFColor color)
  {
  }

  public void setValue(float colors[][])
  {
  }
  public void setValue(float colors[])
  {
  }
  public void setValue(int size, float colors[])
  {
  }
  /****************************************************
    color[0] ... color[size - 1] are used as color data
    in the way that color[0], color[1], and color[2] 
    represent the first color. The number of colors
    is defined as &quot
    {
    }size / 3&quot;.
   ***************************************************/
  public void setValue(MFColor colors)
  {
  }
  public void setValue(ConstMFColor colors)
  {
  }

  public void set1Value(int index, ConstSFColor color)
  {
  }
  public void set1Value(int index, SFColor color)
  {
  }
  public void set1Value(int index, float red, float green, float blue)
  {
  }

  public void addValue(ConstSFColor color)
  {
  }
  public void addValue(SFColor color)
  {
  }
  public void addValue(float red, float green, float blue)
  {
  }

  public void insertValue(int index, ConstSFColor color)
  {
  }
  public void insertValue(int index, SFColor color)
  {
  }
  public void insertValue(int index, float red, float green, float blue)
  {
  }

  public String toString()
  {
  return null;
  }   // This overrides a method in Object
}

