package vrml.field;
import vrml.*;

public class MFTime extends MField
{
  public int getSize() { return 0; }
  public void clear() {}
  public void delete(int index) {}
  public MFTime()
  {
  }
  public MFTime(int size, double times[])
  {
  }
  public MFTime(double times[])
  {
  }

  public void getValue(double times[])
  {
  }

  public double get1Value(int index)
  {
  return 0;
  }

  public void setValue(double times[])
  {
  }
  public void setValue(int size, double times[])
  {
  }
  public void setValue(MFTime times)
  {
  }
  public void setValue(ConstMFTime times)
  {
  }

  public void set1Value(int index, double time)
  {
  }
  public void set1Value(int index, ConstSFTime time)
  {
  }
  public void set1Value(int index, SFTime time)
  {
  }

  public void addValue(double time)
  {
  }
  public void addValue(ConstSFTime time)
  {
  }
  public void addValue(SFTime time)
  {
  }

  public void insertValue(int index, double time)
  {
  }
  public void insertValue(int index, ConstSFTime time)
  {
  }
  public void insertValue(int index, SFTime time)
  {
  }

  public String toString()
  {
  return null;
  }   // This overrides a method in Object
}

