// MV4924 Spring 03 Project 4 framework
// you need to add code at ...

import vrml.node.Script;
import vrml.field.SFVec3f;
import vrml.field.MFString;
import java.io.*;
import java.net.*;

public class Project4 extends Script
{
  double    xPos, yPos, zPos, xPosDR, yPosDR, zPosDR;
  float     xVel, yVel, zVel, oldXVel, oldYVel, oldZVel;
  float     xAcc, yAcc, zAcc;
  int       loopsCompleted;
  DISSender sender;
  pduReader reader;
  long      packetTime;

  SFVec3f   position;
  MFString  message;
  String    text[];

  boolean   validVelocity, validAcceleration;

  /**
   * Initializes this script node.
   */
  public void initialize()
  {
    validVelocity = false;
    validAcceleration = false;
    // Links to the VRML file through event outs
    position = (SFVec3f)getEventOut("position_changed");
    position.setValue(-5.0f, 0.0f, 0.0f);
    message = (MFString)getEventOut("text_changed");
    text = new String[1];
    text[0] = "Initial Message";
    message.setValue(text);

    reader = new pduReader(this);
    Thread readerThread = new Thread(reader);
    readerThread.start();
    // Set initial values for position, velocity and acceleration
    xVel = 0;
    yVel = 0;
    zVel = 0;
    oldXVel = 0;
    oldYVel = 0;
    oldZVel = 0;
    xPos = 0;
    yPos = 0;
    zPos = 0;
    xAcc = 0;
    yAcc = 0;
    zAcc = 0;

    // Create a DISSender - this should actually be done on another computer
    //  that is controlling the object that this reader will be tracking.
    //  It is done here because there is no other computer.
    sender = new DISSender();
    Thread senderThread = new Thread(sender);
    senderThread.start();
  }

  /**
   * Processes a VRML event.
   *
   * @param e the event object
   */
  public void processEvent(vrml.Event e)
  {
    // elapsedTime is the time in seconds that has passed since the last packet was received
    double elapsedTime = (System.currentTimeMillis() - packetTime)/1000.0;
    // the events we expect will be named set_time
    if(e.getName().equals("set_time"))
    {
      if(validAcceleration)
      {
        // We have received at least 2 packets so all dead reckoning methods have sufficient data
        switch(loopsCompleted%3)
        {
          // First and every third loop uses instantaneous velocity only for dead reckoning
          case(0):
            xPosDR = xPos + xVel*elapsedTime;
            yPosDR = yPos + yVel*elapsedTime;
            zPosDR = zPos + zVel*elapsedTime;
            text[0] = "Instantaneous Velocity";
            break;
          // Second loop uses approximate acceleration based on previous and current velocities
          case(1):
            xPosDR = xPos + xVel*elapsedTime + (xVel - oldXVel)*elapsedTime*elapsedTime*0.5;
            yPosDR = yPos + yVel*elapsedTime + (yVel - oldYVel)*elapsedTime*elapsedTime*0.5;
            zPosDR = zPos + zVel*elapsedTime + (zVel - oldZVel)*elapsedTime*elapsedTime*0.5;
            text[0] = "Approximated Acceleration";
            break;
          // Third loop uses actual acceleration value for dead reckoning
          case(2):
            xPosDR = xPos + xVel*elapsedTime + (xAcc)*elapsedTime*elapsedTime*0.5;
            yPosDR = yPos + yVel*elapsedTime + (yAcc)*elapsedTime*elapsedTime*0.5;
            zPosDR = zPos + zVel*elapsedTime + (zAcc)*elapsedTime*elapsedTime*0.5;
            text[0] = "Instantaneous Acceleration";
            break;
        }
      }
      else if(validAcceleration)
      {
        // this is the first time a packet was received, so there is no old velocity
        //  values to calculate acceleration with - so just use velocity
        xPosDR = xPos + xVel*elapsedTime;
        yPosDR = yPos + yVel*elapsedTime;
        zPosDR = zPos + zVel*elapsedTime;
      }
      // translate the object accordingly
      position.setValue(new Double(xPosDR).floatValue(), new Double(yPosDR).floatValue(),
                        new Double(zPosDR).floatValue());
      message.setValue(text);
    }
  } // end processEvent

  public void setMovementParameters(double newXPos, double newYPos, double newZPos,
                                    float newXVel, float newYVel, float newZVel,
                                    float newXAcc, float newYAcc, float newZAcc,
                                    int newLoopsCompleted)
  {
    // Save the old velocity values so we can approximate acceleration
    oldXVel = xVel;
    oldYVel = yVel;
    oldZVel = zVel;
    // Store the new velocity values
    xVel = newXVel;
    yVel = newYVel;
    zVel = newZVel;
    // Store the position of the object
    xPos = newXPos;
    yPos = newYPos;
    zPos = newZPos;
    // Store the new acceleration values
    xAcc = newXAcc;
    yAcc = newYAcc;
    zAcc = newZAcc;
    // Store the new number of loops completed
    loopsCompleted = newLoopsCompleted;
    // Record the time that this update was received
    packetTime = System.currentTimeMillis();

    // If velocity was already valid, then we now have valid old velocity values
    //  which means that dead reckoning can calculate acceleration
    if(validVelocity)
      validAcceleration = true;
    // After the first packet, velocity becomes true;
    validVelocity = true;
  }

  class pduReader extends Project4 implements Runnable
  {
    DatagramSocket       socket;
    DatagramPacket       packet;
    Project4             parent;
    byte[]               buffer;
    ByteArrayInputStream bais;
    DataInputStream      dis;
    double               px, py, pz;
    float                vx, vy, vz;
    float                ax, ay, az;
    int                  loopsCompleted;

    public pduReader(Project4 pParent)
    {
      // Keep a reference back to the parent to return velocity and position
      //  data received in PDU packets
      parent = pParent;
      try
      {
        socket = new DatagramSocket(7777);
      }
      catch(Exception e)
      {
        System.out.println("Error establishing socket in pduReader:" + e);
        System.exit(-1);
      }
    }

    public void run()
    {
      while(true)
      {
        buffer = new byte[144];
        try
        {
          packet = new DatagramPacket(buffer, 144);
          socket.receive(packet);
          bais = new ByteArrayInputStream(buffer);
          dis = new DataInputStream(bais);
          dis.skipBytes(36);
          vx = dis.readFloat();
          vy = dis.readFloat();
          vz = dis.readFloat();
          px = dis.readDouble();
          py = dis.readDouble();
          pz = dis.readDouble();
          dis.skipBytes(16);
          ax = dis.readFloat();
          ay = dis.readFloat();
          az = dis.readFloat();
          dis.skipBytes(40);
          loopsCompleted = dis.readInt();
        }
        catch(Exception e)
        {
          System.out.println("Error trying to read a packet from the socket in pduReader: " + e);
          System.exit(-1);
        }
        parent.setMovementParameters(px, py, pz, vx, vy, vz, ax, ay, az, loopsCompleted);
      }
    } // end run method
  } // end pduReader class
} // end Project4
