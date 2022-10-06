package vrml;

/**
 * <p>
 * The Browser class contains the public Java platform interfaces
 * which allow scripts to get and set browser information.
 * </p>
 * <p>
 * <b>References:</b>
 * <ul>
 * 	<li>	<a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.6.5">VRML 97 specification, section B.6.5, Browser class</a></li>
 * 	<li>	<a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/concepts.html#4.12.10">VRML 97 specification, section 4.12.10, Browser script interface</a></li>
 * </ul>
 * </p>
 * 
 */
public class Browser 
{
  private Browser()
  {
  }
  /** toString() overrides a method in Object. */
  public String toString()
  {
  return null;
  }   

  // Browser interface
  public String getName()
  {
  return null;
  }    
  public String getVersion()
  {
  return null;
  }    

  public float getCurrentSpeed()
  {
  return 0;
  }

  public float getCurrentFrameRate()
  {
  return 0;
  }

  public String getWorldURL()
  {
  return null;
  }
  public void replaceWorld(BaseNode[] nodes)
  {
  }

  public BaseNode[] createVrmlFromString(String vrmlSyntax)
    throws InvalidVRMLSyntaxException
    {
    return null;
    }

  public void createVrmlFromURL(String[] url, BaseNode node, String event)
    throws InvalidVRMLSyntaxException
    {
    }

  public void addRoute(BaseNode fromNode, String fromEventOut,
      BaseNode toNode, String toEventIn)
  {
  }

  public void deleteRoute(BaseNode fromNode, String fromEventOut,
      BaseNode toNode, String toEventIn)
  {
  }

  public void loadURL(String[] url, String[] parameter)
    throws InvalidVRMLSyntaxException
    {
    }

  public void setDescription(String description)
  {
  }
}

