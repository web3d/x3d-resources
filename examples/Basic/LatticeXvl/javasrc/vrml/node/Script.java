package vrml.node; 
import vrml.*;

/** This is the general Script class, to be subclassed by all scripts.
  * Note that the provided methods allow the script author to explicitly
  * throw tailored exceptions in case something goes wrong in the
  * script.
  */
public abstract class Script extends BaseNode
{ 
  /** <p>
    * The initialize() method is called before any event is generated.
    * </p><p>
    * Authors may define an initialize() method within the Script class 
    * that is called before the browser presents the world to the user 
    * and before any events are processed by any nodes in the same VRML 
    * file as the Script node containing this script 
    * (see 4.12.3, Initialize() and shutdown()). 
    * The various methods of the Script class such as 
    * getEventIn(), getEventOut(), getExposedField(), and getField() 
    * are not guaranteed to return correct values before the 
    * initialize() method has been executed. The initialize() method 
    * is called once during the life of the Script object.
    * </p><p>
    * Reference:
    * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.6">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.6</a>
    * </p><p>
    * Its default behaviour is no operation.
    * See 
    * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.5.1">Example2.java</a>
    * in 
    * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.5.1">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.5.1</a>
    * for an example of a user-specified initialize() method.
    * </p>
    */
  public void initialize()
  {
  }

  /** Get a Field by name.
    * @throws InvalidFieldException if fieldName isn't a valid field name for a node of this type.
    */
  protected final Field getField(String fieldName)
  {
  return null;
  }

  /** Get an EventOut by name.
    *  Throws an InvalidEventOutException if eventOutName isn't a valid
    *  eventOut name for a node of this type.
    */ 
  protected final Field getEventOut(String eventOutName)
  {
  return null;
  }

  /** Get an EventIn by name.
    *   Throws an InvalidEventInException if eventInName isn't a valid
    *   eventIn name for a node of this type.
    */ 
  protected final Field getEventIn(String eventInName)
  {
  return null;
  }

  /** processEvents() is called automatically when the script receives 
    * some set of events. It shall not be called directly except by the author-defined subclass.
    * @param count  indicates the number of events delivered.
    * @param events provides the event(s) ROUTEd to the script.
    */
  public void processEvents(int count, Event events[])
  {
  }

  /** processEvent() is called automatically when the script receives an event.
    * @param event provides the event ROUTEd to the script.
    */ 
  public void processEvent(Event event)
  {
  }

  /** eventsProcessed() is called after every invocation of processEvents().
    */ 
  public void eventsProcessed()
  {
  }

  /** shutdown() is called when this Script node is deleted.
    * Reference: 
    * <a href="http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.5">http://www.web3D.org/technicalinfo/specifications/vrml97/part1/java.html#B.4.5</a>
    */ 
  public void shutdown()
  {
  }

  /** toString() overrides a utility method in Object, allowing
    * run-time inspection of class name.
    * @see java.lang.Object#toString()
    */
  public String toString()
  {
  return null;
  }   
}
