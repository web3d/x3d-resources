package melonlauncher;

import java.lang.Double;
import java.util.*;
import javax.swing.*;
import java.awt.*;
/**
 * Title:         Calculate3Dtrajectory
 * Description:   Calcuation Method for 3d trajectories
 * Copyright:     Copyright (c) 2001
 * Company:       NPS
 * @author        Andrew Wiest
 * @version 1.0
 */

public class Calculate3DTrajectory {

  //populate default values
  private static final double[] DEFAULT_VELOCITY = {300,300,0};
  private static final double[] DEFAULT_TRAJECTORY = {45,0};
  private static final double[] DEFAULT_WIND_INFO = {0,0,0};
  private static final double DEFAULT_DRAG_FORCE = 0.0005;
  private Vector FlightPath = new Vector(50);
  private double initialVelocityX, initialVelocityY,initialVelocityZ;
  private boolean defaultparameters = false;
  private double positionX, positionY, positionZ;
  private double DragForce;

  //----------------------------------
//    Constructors
//----------------------------------

   /**
    * Default constructor
    */
  public Calculate3DTrajectory()  {

    this(DEFAULT_VELOCITY,DEFAULT_TRAJECTORY,DEFAULT_WIND_INFO,DEFAULT_DRAG_FORCE);

  }

  //Constructor with input values
  public Calculate3DTrajectory(double[] velocity, double[] trajectory, double[] wind, double Bm) {

  System.out.println("inside calculation primary constructor");//stub putput
  this.calculate(velocity,trajectory,wind, Bm);

  }

  // this method calculates entire trajectory and stores it in the Vector FlightPath
  public void calculate(double[] velocity, double[] trajectory, double[] wind, double Bm) {

   System.out.println("inside calculation start");//Stub output
   //use 1 sec time interval
   // x = V * cos(alpha)*t
   //y = V * sin(alpha)*t-g*t2/2
   //main calcluation loop
   int i = 0; //time counter in seconds
   double gravity = 9.8;//acceleration due to gravity
   double localXPosition,localYPosition, localZPosition;
   positionX = 0;
   positionY = 0;
   positionZ = 0;
   DragForce = Bm;
   // initialize class variables for velocity
    initialVelocityX = velocity[0];
    initialVelocityY = velocity[1];
    initialVelocityZ = velocity[2];

    //basic trajectory shot
    while (positionY >= 0) {

    positionX = velocity[0]*i;
    positionY = velocity[1]*i-(gravity*i*i)/2;

    //System.out.println(positionY);
    localXPosition = positionX;
    localYPosition = positionY;
    localZPosition = positionZ;
    FlightPath.add(new Double(localXPosition));// X
    FlightPath.add(new Double(localYPosition));// Y
    FlightPath.add(new Double(localZPosition));// Z
    i++;
    }

  }
  //method for access to the created vector

  public Vector GetFlightPath(){

  System.out.println("Inside Calculate3DTrajectory GetFlightPath");

  return FlightPath;

  }


  // this method uses the Euler method to get Vector data one set at a time rather than process the whole data set
  public double[] GetEulerVector(double[] InputData, boolean initialize) {

      double[] OutputData = new double[6];
      double PositionX, PositionY, PositionZ, VelocityX, VelocityY, VelocityZ;
      double LastX, LastY, LastZ, LastVelocityX, LastVelocityY, LastVelocityZ;
      double AirResistance, DeltaTime;
      System.out.println("indside Calculate3DTrajectory GetEulerVector Method");
    /* Input data contains:
          InputData[0] = last X position
          InputData[1] = last Y position
          InputData[2] = last Z position
          InputData[3] = CurrentVelocityX
          InputData[4] = CurrentVelocityY
          InputData[5] = CurrentVelocityZ
          InputData[6] = DeltaTime in sec
      initialize determines whether this is a new path or not
    */
          //read input data into readablew variables
          LastX = InputData[0];
          LastY = InputData[1];
          LastZ = InputData[2];
          LastVelocityX = InputData[3];
          LastVelocityY = InputData[4];
          LastVelocityZ = InputData[5];
          DeltaTime = InputData[6];
          PositionX = LastX + LastVelocityX*DeltaTime;
          PositionY = LastY + LastVelocityY*DeltaTime;
          PositionZ = LastZ + LastVelocityZ*DeltaTime;
          AirResistance = DragForce * Math.sqrt((LastVelocityX * LastVelocityX) + (LastVelocityY * LastVelocityY) + ( LastVelocityZ * LastVelocityZ));
          // update velocities
          VelocityX = LastVelocityX - AirResistance * LastVelocityX * DeltaTime;
          VelocityY = LastVelocityY - 9.8 * DeltaTime - AirResistance * LastVelocityY * DeltaTime;
          VelocityZ = LastVelocityZ - AirResistance * LastVelocityZ * DeltaTime;

      // interpolate X and Y positions if Y is less than zero
        if (PositionY <=0) {

          double slope;

          slope = - PositionY/LastY;
          PositionX = (PositionX + slope * LastX) / (1 + slope);
          PositionY = 0;

        }
        else {
        // do nothing
        }


      // Populate returned Data into an array
        OutputData[0] = PositionX;
        OutputData[1] = PositionY;
        OutputData[2] = PositionZ;
        OutputData[3] = VelocityX;
        OutputData[4] = VelocityY;
        OutputData[5] = VelocityZ;

      return OutputData;
       /* OutputData contains:
          OutputData[0] = new X position
          OutputData[1] = new Y position
          OutputData[2] = new Z position
          OutputData[3] = new X velocity
          OutputData[4] = new Y velocity
          OutputData[5] = new Z velocity
       */

  }

}