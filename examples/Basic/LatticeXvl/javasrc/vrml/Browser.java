package vrml;

public class Browser 
{
  private Browser()
  {
  }
  public String toString()
  {
  return null;
  }   // This overrides a method in Object

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

