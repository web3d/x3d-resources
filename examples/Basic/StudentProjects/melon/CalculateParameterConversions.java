package melonlauncher;

//import javax.swing.JComponent;
import java.util.*;
/**
 * Title:        Melon Launcher
 * Description:  Does the math conversion from the input values to X,Y,Z coordinate data.
 * Copyright:    Copyright (c) 2001
 * Company:      CS2973/MV4473
 * @author  Andrew Wiest
 * @version 1.0
 */

public class CalculateParameterConversions  {

  //data members
  double[] velocity = new double[3];
  double[] trajectory = new double[2];
  double[] wind  = new double[3];
  double thetaT,phiT,thetaW,phiW; //angles for trajectory and wind
  private static final double DEFAULT_sliderVelocity = 50;
  private static final double DEFAULT_sliderAzimuth = 0;
  private static final double DEFAULT_sliderElevation = 45;
  private static final double DEFAULT_sliderWindDirection = 0;
  private static final double DEFAULT_sliderWindSpeed = 0;
  private Vector FlightPath = new Vector(20);
  double Bm = 0.00004; //initialize dragforce

  //constructors

  //default constructor
  public CalculateParameterConversions() {

  this(DEFAULT_sliderVelocity, DEFAULT_sliderAzimuth, DEFAULT_sliderElevation, DEFAULT_sliderWindDirection, DEFAULT_sliderWindSpeed);

  }


 //primary constructor
  public CalculateParameterConversions(double sliderVelocity, double sliderAzimuth, double sliderElevation, double sliderWindDirection, double sliderWindSpeed) {

  System.out.println("inside CalculateParameterConversion primary constructor");

  this.conversion(sliderVelocity, sliderAzimuth, sliderElevation, sliderWindDirection, sliderWindSpeed);
  }
  //conversion method to pass values to calculation class
  public void conversion(double sliderVelocity, double sliderAzimuth, double sliderElevation, double sliderWindDirection, double sliderWindSpeed) {

      System.out.println("inside conversion main method");//stub output

    //calculate array values from input Jsliders
    trajectory[0] = sliderElevation; //vertical deflection angle

    trajectory[1] = sliderAzimuth; //horizontal deflection angle

    thetaT = Math.toRadians(sliderElevation);

    velocity[0] = sliderVelocity * Math.cos(thetaT);  // X vector

    velocity[1] = sliderVelocity * Math.sin(thetaT);  // Y vector

    phiT = Math.toRadians(sliderAzimuth);

    velocity[2] = sliderVelocity * Math.sin(phiT);  // Z vector

    thetaW = Math.toRadians(sliderWindDirection);

    wind[0] = sliderWindSpeed * Math.cos(thetaW); // wind X vector

    wind[1] = sliderWindSpeed * Math.sin(thetaW); // wind Z vector

    wind[2] = sliderWindSpeed; // wind speed


   Calculate3DTrajectory calc3Dtraj = new Calculate3DTrajectory(velocity,trajectory,wind, Bm);
    // get complete flightpath data
    FlightPath = calc3Dtraj.GetFlightPath();
  }

  //method for access to the created vector

  public Vector GetFlightPath(){

    System.out.println("Inside CalculateParameterConversion GetFlightPath");

    return FlightPath;

  }
  // Euler Method version
   public void EulerConversion(double sliderVelocity, double sliderAzimuth, double sliderElevation, double sliderWindDirection, double sliderWindSpeed) {

      System.out.println("inside conversion main method");//stub output

    //calculate array values from input Jsliders
    trajectory[0] = sliderElevation; //vertical deflection angle

    trajectory[1] = sliderAzimuth; //horizontal deflection angle

    thetaT = Math.toRadians(sliderElevation);

    velocity[0] = sliderVelocity * Math.cos(thetaT);  // X vector

    velocity[1] = sliderVelocity * Math.sin(thetaT);  // Y vector

    phiT = Math.toRadians(sliderAzimuth);

    velocity[2] = sliderVelocity * Math.sin(phiT);  // Z vector

    thetaW = Math.toRadians(sliderWindDirection);

    wind[0] = sliderWindSpeed * Math.cos(thetaW); // wind X vector

    wind[1] = sliderWindSpeed * Math.sin(thetaW); // wind Z vector

    wind[2] = sliderWindSpeed; // wind speed

  // inititialize calculation class
   Calculate3DTrajectory calc3Dtraj = new Calculate3DTrajectory(velocity,trajectory,wind, Bm);

  }

 /*
  public double[] GetEulerVector(double[] InputData, boolean initialize) {
    double[] temp;
    System.out.println("inside CalculateParamterConversions GetEulerVector Method");
    temp =
   }
  */
}