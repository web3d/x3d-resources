/**
 * <p>Title: Project 1</p>
 * <p>Description: Send DIS Packets to NPS Viewer to Control Simple Movement</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Brian Hittner
 * @version 1.0
 */

import java.net.*;
import java.io.*;

public class DISSender implements Runnable
{
  DatagramSocket        socket;
  DatagramPacket        packet;
  ByteArrayOutputStream baos;
  DataOutputStream      dos;
  byte                  buffer[];
  InetAddress           address;
  long                  startTime, currentTime;
  long                  sleepTime;
  double                cycleTime;
  double                lx, ly, lz;              // Hold the x, y, z positions
  float                 vx, vy, vz;              // Hold the x, y, z velocities
  float                 ax, ay, az;              // Hold the x, y, z accelerations
  int                   loopsCompleted;          // Counts number of complete
                                                 //  circles moved

  public DISSender()
  {
    try
    {
      address = InetAddress.getLocalHost();
      socket = new DatagramSocket(7776);
      baos = new ByteArrayOutputStream();
      dos = new DataOutputStream(baos);
    }
    catch(Exception e)
    {
      System.out.println("Exception error in DISSender constructor.");
      System.out.print(e);
    }
    // Record the time this thread was constructed since the timing for the position
    //  and velocity will be based on elapsed time
    startTime = System.currentTimeMillis();

    // The l stands for location; x, y, z are the coordinate axes
    lx = -5.0;
    ly = 0.0;
    lz = 0.0;

    // The v stands for velocity; x, y, z are the coordinate axes
    vx = 0.0f;
    vy = 0.0f;
    vz = 0.0f;

    // The a stands for acceleration; x, y, z are the coordinate axes
    ax = 0.0f;
    ay = 0.0f;
    az = 0.0f;

    cycleTime = 5000.0;
    sleepTime = 1000;    // Initially, sleep for 500 ms = 2 packets per second
  }

  public DatagramPacket createDISPacket()
  {
    baos.reset();
    try
    {
      // Version Number: 1 byte long
      dos.writeByte(5);
      // Exercise ID: 1 byte long
      dos.writeByte(1);
      // PDU Type: 1 byte long
      dos.writeByte(1);
      // Protocol Familty: 1 byte long
      dos.writeByte(1);
      // Time Stamp: 4 bytes long = 1 integer
      dos.writeInt(0);
      // Length: 2 bytes long = 1 short
      dos.writeShort(144);
      // Padding: 2 bytes long
      dos.writeByte(0);
      dos.writeByte(0);
      // Entity ID: site: 2 bytes long = 1 short
      dos.writeShort(0);
      // Application: 2 bytes long = 1 short
      dos.writeShort(0);
      // Entity: 2 bytes long = 1 short
      dos.writeShort(0);
      // Force ID: 1 byte long
      dos.writeByte(0);
      // Number of Articulations: 1 byte long
      dos.writeByte(0);
      // Entity Type: kind: 1 byte long
      dos.writeByte(2);
      // Domain: 1 byte long
      dos.writeByte(6);
      // Country: 2 bytes long = 1 short
      dos.writeShort(0);
      // Category: 1 byte long
      dos.writeByte(3);
      // Subcategory: 1 byte long
      dos.writeByte(0);
      // Specific: 1 byte long
      dos.writeByte(0);
      // Extra: 1 byte long
      dos.writeByte(0);
      // Alternative entity type: 8 bytes long = 1 double
      dos.writeDouble(0);

      // **********************************************************************
      // Linear Velocity: 12 bytes = 3 floats
      dos.writeFloat(vx);
      dos.writeFloat(vy);
      dos.writeFloat(vz);
      // Location: 24 bytes = 3 doubles
      dos.writeDouble(lx);
      dos.writeDouble(ly);
      dos.writeDouble(lz);
      // **********************************************************************

      // Orientation: 12 bytes = 3 floats
      dos.writeFloat(0.0f);
      dos.writeFloat(0.0f);
      dos.writeFloat(0.0f);
      // Appearance: 4 bytes
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      // Dead Reckoning: 40 bytes = treat as 5 doubles?
      // **********************************************************************
      // I chose to write the acceleration values into the dead reckoning space
      dos.writeFloat(ax);
      dos.writeFloat(ay);
      dos.writeFloat(az);
      // **********************************************************************
      dos.writeFloat(0.0f);  // These 4 lines will fill out the rest of the
      dos.writeDouble(0.0);  //  dead reckoning space
      dos.writeDouble(0.0);
      dos.writeDouble(0.0);
      // Marking: 12 bytes
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      dos.writeByte(0);
      // Capabilities: 4 bytes
      // **********************************************************************
      // I chose to use the capabilities space to send the number of loops that
      //  have been completed
      dos.writeInt(loopsCompleted);
    }
    catch(Exception e)
    {
      System.out.println("Exception in createDISPacket.");
      System.out.print(e);
    }
    buffer = baos.toByteArray();
    return new DatagramPacket(buffer, buffer.length, address, 7777);
  }

  public void moveObject()
  {
    currentTime = System.currentTimeMillis();
    // keep track of the startTime for each cycle so that the fraction
    //  variable is always a value from 0 to 1
    if((currentTime - startTime) > cycleTime)
    {
      startTime += cycleTime;
      loopsCompleted++;
    }
    double fraction = ((double)(currentTime - startTime)) / cycleTime;
    double T = fraction*Math.PI*2.0;
    // The path is a circle, so the x value is determined by sin
    lx = Math.sin(T)*5.0;
    // The y value of the circle is determined by cos
    ly = Math.cos(T)*5.0;
    // The velocity is determined by taking the first derivative of the position
    //  for the x value the derivative is cos
    vx = new Double(Math.cos(T)).floatValue()*5.0f;
    // The derivative for the y direction is -sin
    vy = new Double(Math.sin(T)).floatValue()*(-5.0f);
    // The acceleration is determined by taking the first derivative of the velocity
    //  for the x value of cos(x), the derivative is -sin(x)
    ax = new Double(Math.sin(T)).floatValue()*(-5.0f);
    // The derivative of the y value of -sin(x) is cos(x)
    ay = new Double(Math.cos(T)).floatValue()*(-5.0f);

    // create the packet with the new position and velocity values
    packet = createDISPacket();
    try
    {
      socket.send(packet);
    }
    catch(Exception e)
    {
      System.out.println("Exception in moveObject");
      System.out.print(e);
    }
  }

  public void run()
  {
    while(true)
    {
      moveObject();
      try
      {
        Thread.sleep(sleepTime);
      }
      catch(Exception e)
      {
        System.out.println("Error while sleeping in DISSender");
      }
    }
  }
}
