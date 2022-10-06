package melonlauncher;

import javax.swing.JComponent;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class MelonLauncherAgent extends JComponent {

//--------------------------------
//  Data Members
//--------------------------------

  double[] velocity   = new double[3];
  double[] trajectory = new double[2];
  double[] wind       = new double[3];

  // Default Constructor
  public MelonLauncherAgent() {

  System.out.println("inside Agent default constructor");

 }

   public void calculateMelonMovement() {
  //TEMP VALUES

  System.out.println("inside melon movement method");

  velocity[0]   = 5;
  velocity[1]   = 5;
  velocity[2]   = 5;
  trajectory[0] = 90;
  trajectory[1] = 90;
  wind[0]       = 10;
  wind[1]       = 10;
  wind[2]       = 10;

  //call calculation class
  Calculate3DTrajectory calc3Dtraj = new Calculate3DTrajectory(velocity,trajectory,wind);


  }


}