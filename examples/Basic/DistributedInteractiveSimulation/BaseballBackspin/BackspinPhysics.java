/**
 * Title:        Advanced Physically Based Modeling - Baseball
 * Description:  Modeling the effects of backspin on a fastball
 * Copyright:    Copyright (c) 2001
 * Class:		 MV4472
 */

import java.lang.Math.*;
import java.lang.Thread;


/**
 * Class: BackspinPhysics
 * @author Victor Spears & David Back
 * @version 1.0
 *
 * A physics engine for modeling the effects of spin on trajectory due to  
 * Magnus force.  This phenomenon is caused by a backspinning ball due 
 * to the difference in air speed between the top and bottom of the ball; 
 * this difference causes the ball to be deflected laterally (to the 
 * right from the pitcher's point of view).  This deflection increases 
 * as the backspin rotational velocity increases. 
 */

public class BackspinPhysics extends Object implements Runnable
{

  //constants
  private final double S0 = 0.00041*0.149;
  private final double DISTANCE = 18.288;
  private final double MASS_KGS = 0.149;
  private final double DELTA_T = 0.001;
  private final boolean gravity = true;

  private double angularVelocity = 1000;	//rpm; converted to rad/sec below
  private double topVelocity = 0;

  //transmits the updates to the multicast network
  private BaseballDisSender	transmitter;
  private String ipAddress = new String("224.0.0.3");	//default
  private int portNumber = 4567;				//default
  private int timeToLive = 500;				//default
  private String marking = new String("Baseball");	//default

 /**
  * Constructor; initializes the transmitter with the specified ip and port; 
  * Also tells the physics engine how fast the ball is pitched and how much 
  * backspin is put on it. 
  *
  * @param	ip		The multicast address that the DIS is send on
  * @param	port	The port; for DIS, this must match the espdu transform in the VRML scene
  *	@param	angVel	Angular velocity (backspin) of the ball, in revolutions per minute
  *	@param	vel		Linear velocity of the ball (how fast it is pitched) in meters per second
  */
  public BackspinPhysics(String ip, int port, double angVel, double vel) {
	//if specified, the invocation can include IP address and port number

        portNumber = port;
	ipAddress = ip;
        angularVelocity = angVel;
        topVelocity = vel;

  }

 /**
  * runs the physics for the pitch and outputs the position, velocity, and orientation updates 
  * to DIS espdus which are broadcast on the multicast port specified in the constructor. 
  */
  public void run()
  {

    double force = 0;
    double old_force = 0;
    double drag_accel = 0;
    double time = DISTANCE/topVelocity;
    double distance = 0;
    double lateral_distance = 0;
    double b2overm = 0;
    double velocityX = topVelocity;
    float fallDist = 0.0f;

    transmitter = new BaseballDisSender(ipAddress, portNumber, timeToLive, marking, false);

    int counter = 0;
    transmitter.transmitUpdate(	0f, 0f, 0f,				//initial position
						0f, 0f, 0f,						//initial orientation
						0f, 0f, 0f,						//initial velocity
						0f, 0f, 0f					);	//initial angular velocity

    for (double time_count = 0; time_count < time; time_count+=DELTA_T)
    {
		//all the fun mathematics
		b2overm = 0.0039 + (0.0058/(1 + Math.exp((velocityX - 35)/(double)5)));
		drag_accel = -b2overm * Math.pow(velocityX,2);
		velocityX += drag_accel * DELTA_T;
		distance += velocityX * DELTA_T;
		force = S0 * angularVelocity * velocityX;
		lateral_distance += ((force+old_force)/MASS_KGS)*Math.pow(DELTA_T,2);
		old_force = force;
		counter++;

		//send the information over the network via espdu
		if (gravity) fallDist = (float)(-4.9 * Math.pow(time_count,2));
		float rotation = (float)(-(angularVelocity/Math.PI)*time_count);

		//conversions to fit the VRML scene
		float x = (float)distance;				//actual distance between pitcher and catcher is 18 meters (correct)
		float y = fallDist*4;					//pitcher in scene is 6.6 meters tall, so fall dist is exaggerated 4 times
		float z = (float)(10*lateral_distance);	//likewise, lateral offset is exaggerated
		float theta = ((float)angularVelocity * 2f * (float)Math.PI/(60f) * (float)time_count)%((float)Math.PI*2f);
		
		if (fallDist < -6.6)	//stop the updates if the ball gets to the 'ground' in the scene
			break;

		//updates are going through every 1/10 second, so velocity updates are not given (no interpolation necessary)
		transmitter.transmitUpdate(	x, z, -y,				//position
							0f, 0f, theta,					//orientation
							0f, 0f, 0f,						//linear velocity
							0f, 0f, 0f	);					//angular velocity

		try
		{
			Thread.sleep(10);	//keeps the VRML from gagging
		}
		catch (InterruptedException intE)
		{
			System.out.println(intE);
		}
    }

    transmitter.shutdown();
    System.out.println("angular: " + angularVelocity + " velocity: " + topVelocity);
  }
}
