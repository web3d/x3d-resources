package vrml;

/** This is the general BaseNode class. */

public abstract class BaseNode 
{
  /** Returns the type of the node.  If the node is a prototype
    * it returns the name of the prototype.
    */
  public String getType()
  {
  return null;
  }

  /** Get the Browser that this node is contained in.
    */
  public Browser getBrowser()
  {
  return null;
  }
}

