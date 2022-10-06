

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Title:   Cannon
 * Description:  Final project for MV-4472, Physically-based modeling.
 *               This project models the motion of a projectile.  Through an
 *                  instantiation of the 'InputPanel' class, it allows the user
 *                  to alter the initial firing angle & velocity, as well as the
 *                  the direction and velocity of any influencing wind.  Also,
 *                  air density and variable density (due to altitude changes)
 *                  can be selected for application in the model.
 *               The resulting motion model will be depicted on an instantiation
 *                  of the 'Display' class.
 *               This class is designed to work with the CannonProjectII.wrl vrml
 *                  model.  By selecting the 'enable vrml interface' button, an instantiation
 *                  of the ProjectileDISSender class will be created that will
 *                  be used to transmit state packets to the .wrl file, allowing the
 *                  the resulting projectile motion to be visible via 3D.
 *               The physics for this model(with the exception of wind) is based
 *                  on those in the 'Computational Physics' textbook
 *                  by Nicholas J. Giordano.  The following assumptions apply:
 *                    - b2m is based on a projectile of approximately 10 cm in diameter,
 *                      this value corresponds to the approximate drag coefficient
 *                       experienced by the projectile
 *                    - Wind affects the model as follows
 *                        constant effect throughout range of projectile
 *                        the effects of wind are not affected by drag
 *                        the head/tail component of the wind on the projectile are
 *                          taken into account for determining the drag affect on the
 *                          projectile's velocity
 *         Future work:
 *                - add descriptive lables to the display graphs
 *                - allow VRML scene control of the model
 *                    - altering howitzer's turret/barrel alters the direction and angle of fire
 *                    - use a touch sensor to fire the projectile
 *                - allow altering of angle in input panel to alter the barrel elevation
 *
 * Copyright:    Copyright (c) 2002
 * @author      Ernesto Salles
 * date:      20 january 2002
 * @version 1.0
 */

public class Cannon extends Thread{

  private Display display;
  private JFrame mainFrame;
  // following variables used for the transmission of data packets to the VRML scene
  private ProjectileDISSender  dataDISSender;
  private String ipAddress = new String ("224.0.0.3");
  private int portNumber = 64200;
  private int timeToLive = 50;
  private String marking = new String("Projectile");
  private boolean displayClosed = false;

  // following variables used for the actual model execution
  private double dt, initialVelocity, angle, bm, gravity;
  private double scaleFactor = 1.0;
  private double windVelocity, windDirection;
  private Vector path, colors;
  private int currentColor = 0;
  private boolean dragOn = false;
  private boolean changingDensitiesOn = false;
  private boolean vrmlSelected = false;
  private double y0 = 10000.0;

  /**
   * The constructor
   */
  public Cannon() {

    InputPanel controlPanel = new InputPanel(this);
    controlPanel.setVisible(true);
    display = new Display();
    mainFrame = new JFrame("The Cannon");

    mainFrame.setSize(800,600);
    mainFrame.setContentPane(display);

    mainFrame.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        displayClosed = true;}});

    mainFrame.setVisible(true);

    // create the vector of colors
    colors = new Vector();
    colors.add(Color.blue);
    colors.add(Color.red);
    colors.add(Color.green);
    colors.add(Color.yellow);
    colors.add(Color.gray);
    colors.add(Color.white);

    // initialize and fire the cannon
    initializeCannon();
  }

  /**
   * Initiates the projectile motion based on the provided information
   *
   * @param initVel   The initial velocity in Meters/second
   * @param a         The firing angle in degrees
   * @param wD        The wind direction
   * @param wV        The wind velocity
   */
  public void fireCannon(int initVel, int a, int wD, double wV){
    initialVelocity = (double) initVel;
    angle = (double) a;
    windDirection = wD;
    windVelocity = wV;
    path = new Vector();
    fireCannon();
    if(vrmlSelected){
      sendData();
    }
    if (!displayClosed){
      display.addData(path);
      display.repaint();
    }
  }


  /**
   * clears the display
   */
  public void clearDisplay(){
    display.clearDisplay();
  }

  /**
   * Used to indicate if drag is to be included in the current model
   */
  public void setDrag (boolean setting){
    dragOn = setting;
  }

  /**
   * used to indicate if changing densities is to be included in the current model
   */
  public void setChangingDensities(boolean setting){
    changingDensitiesOn = setting;
  }

  /**
   * Used to enable the VRMl interface
   */
  public void enableVRML(boolean setting){
    vrmlSelected = setting;
    initializeDISSender();
  }

  /**
   * Used to alter the scale factor for the motion data
   */
  public void setScaleFactor(int scale){
    scaleFactor = (double) ((double) scale)/100.0;
  }

  /**
   * initiates The projectile motion
   */
  private void fireCannon(){
    double ratio = ((double) ((double) 8/400)) * scaleFactor;;
    boolean done = false;
    double drag;

    path.add((Color) colors.elementAt(currentColor%colors.size()));
    currentColor++;
    Vector3d initPosition = new Vector3d(0,0,0);
    Vector3d newPosition;
    Vector3d initVelocity = new Vector3d(0,0,0);
    Vector3d newVelocity = new Vector3d(0,0,0);
    Vector3d wind = new Vector3d(0,0,0);
    initVelocity.x = initialVelocity*Math.cos(Math.PI*(angle/180));
    initVelocity.y = initialVelocity*Math.sin(Math.PI*(angle/180));
    initVelocity.z = 0;

    // determine the wind's head/tail & perpindicular component, and it's
    //   effect to the trajectory path
    wind.x = windVelocity*Math.cos(Math.PI*(windDirection/180));
    wind.y = 0;
    wind.z = -windVelocity*Math.sin(Math.PI*(windDirection/180));
    path.add(wind);
    Vector3d windEffect = new Vector3d(wind);
    windEffect.scale(dt);

    // place the initial position into the data vector
    path.add(initPosition);

    // compute positions till we're done
    while (!done){

      // determine the new position & place it into the data vector
      newPosition = new Vector3d();
      newPosition.x = initPosition.x + initVelocity.x*dt + windEffect.x;
      newPosition.y = initPosition.y + initVelocity.y*dt + windEffect.y;
      newPosition.z = initPosition.z + initVelocity.z*dt + windEffect.z;
      Vector3d temp = new Vector3d(newPosition);
      temp.scale(ratio);
      path.add(temp);
      initPosition = newPosition;

      // determine the new velocity

       // determine the new basic velocities (w/o drag)
       newVelocity.x = initVelocity.x;
       newVelocity.y = initVelocity.y - gravity*dt;
       newVelocity.z = 0;

      // if drag option is selected, add the drag effect into the new velocity
      if (dragOn){
        Vector3d dragVector = new Vector3d();
        drag = bm*(initVelocity.length() - wind.x);
        dragVector.x = drag*(initVelocity.x - wind.x)*dt;
        dragVector.y = drag*initVelocity.y*dt;
        dragVector.z = 0;

        // if the changing densities option is selected, modify the drag by the ratio
        //   of the current altitude to y0= 10000m.
        if (changingDensitiesOn){
          double altitude = newPosition.y;
          double densityRatio = altitude/y0;
          double changeCoefficient = Math.exp(-densityRatio);
          dragVector.scale(changeCoefficient);
        }

        // add the drag effects into the new velocity
        newVelocity.x -= dragVector.x;
        newVelocity.y -= dragVector.y;
        newVelocity.z -= dragVector.z;

    }

      // assign the new velocity as the initial velocity for the next round
       initVelocity.set(newVelocity);

       // if the round has hit the ground, set the flag and exit the while loop
       if (initPosition.y <= 0.0) {done = true;}
    }

  }  // end fireCannon

  /**
   * Used to cycle through the path data and send it to the vrml scene
   */
  private void sendData(){
    Vector3d lastPosition,currentPosition;
    Vector3f dataFloat;
    lastPosition = (Vector3d) path.elementAt(2);
    float dX,dY,theta;

    for(int i=2; i<path.size(); i++){
      currentPosition = (Vector3d) path.elementAt(i);
      dataFloat=new Vector3f(currentPosition);
	dX = (float) (currentPosition.x - lastPosition.x);
	dY = (float) (currentPosition.y - lastPosition.y);

	theta = (float) Math.atan2(dY,dX);

           // send dataunits here
        sendPacket(dataFloat, theta);

        lastPosition.set(currentPosition);

      //wait a few before sending the next packet
      try{
        this.sleep(20);
      }
      catch(InterruptedException intE){
        System.out.println(intE);
      }
    }  // end for loop
  }

  /**
   * initializes the DIS packet transmitter
   */
  private void initializeDISSender(){
    dataDISSender = new ProjectileDISSender(ipAddress, portNumber, timeToLive, marking, false);
  }


  /**
   * Initializes the Cannon variables to those of the InputPanel and Creates the
   * Vector container for the path data
   */
  private void initializeCannon(){
    dt = .25;          // the time increment in seconds
    bm = 4*Math.pow(10,-5);    // a necessary number, refer to 'Computational Physics' pg. 26
    gravity = 9.8;     // in meters/(second squared)
    // create the path vector
        path = new Vector();
  }

  /**
   * used to send a DIS packet to the VRMl scene
   */
  private void sendPacket(Vector3f positVector, float theta){
           // send dataunits here
      dataDISSender.transmitUpdate(  positVector.x, positVector.z, -positVector.y,  // translation
                      0.0f, 0.0f, theta,					    // orientation
                      0.0f, 0.0f, 0.0f,						    // no velocity
                      0.0f, 0.0f, 0.0f);					    // no angular velocity
  }


  public void changeAngle(int angle){
    if (vrmlSelected){
      float theta = (float) Math.toRadians(angle);
      sendPacket(new Vector3f(0,0,0), theta);
    }
  }

  /**
   * The main method that starts it all
   */
  public static void main(String[] args) {
    Cannon cannon1 = new Cannon();

  }
}