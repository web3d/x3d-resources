/**
 * Title:        Advanced Physically Based Modeling - Baseball
 * Description:  Modeling the effects of backspin on a fastball
 * Copyright:    Copyright (c) 2001
 * Class:		 MV4472
 */

import java.text.*;					// for class DecimalFormat
import java.util.*;					// for class Vector
import mil.navy.nps.dis.*;			// for class EntityStatePdu
import mil.navy.nps.testing.*;	    // for MulticastPduSender
import mil.navy.nps.util.*;
import mil.navy.nps.disEnumerations.*;
import java.awt.event.AdjustmentEvent;
import java.lang.Math.*;


/**
 * Class: BaseballDisSender
 * @author Victor Spears & David Back
 * @version 1.0
 *
 * Portal for the physics engine (class BackspinPhysics) to transmit state changes to
 * a listening receiver on a multicast network.  Supports the ability to initialize
 * a single entity and transmit espdus with explicitly assigned parameters.
 */

public class BaseballDisSender {

	private EntityStatePdu			espdu;
	private	CreateEntityPdu			crpdu;
	private BehaviorStreamBufferUDP	streamBuffer;
	private	String					ipAddress;
	private int						portNumber;

	private static final boolean	DEBUG = false;		//prints out espdu before sending


	 /**
	  * Constructor; creates and initializes all of the required pdus for the baseball, as well 
	  * as the BehaviorStreamBuffer to send them out over multicast. 
	  *
	  * @param	ipAddr		The multicast address that the espdus are sent on
	  * @param	portNumber	The port; for DIS, this must match the espdu transform in the VRML scene
	  *	@param	timeToLive	Hops an espdu can go before being dropped; 15+ for local area, 100+ worldwide
	  *	@param	marking		How the entity should be marked in the receiving environment
	  *	@param	rtpHeaderEnabled	whether you wish the rtpHeader to be prepended (false works)
	  */
	public BaseballDisSender (	String	ipAddr,
						int		portNumber,
						int		timeToLive,
						String	marking,
						boolean	rtpHeaderEnabled )
	{
		//initialize the various pdus
		crpdu = new CreateEntityPdu();
		crpdu.setRtpHeaderEnabled(rtpHeaderEnabled);
		espdu = new EntityStatePdu();
		espdu.setMarking(marking);
		espdu.setEntityID((short)3, (short)1, (short)0);		//essentially random
		crpdu.setOriginatingEntityID(espdu.getEntityID());
		crpdu.setExemplar(crpdu);

		//initialize the stream buffer
		ipAddress = ipAddr;
		this.portNumber = portNumber;
		streamBuffer = new BehaviorStreamBufferUDP(ipAddr, portNumber);
		streamBuffer.setTimeToLive(timeToLive);
		if (DEBUG) System.out.println(crpdu.toString());
		streamBuffer.sendPdu(crpdu.getExemplar(), ipAddr, portNumber);
	}

	 /**
	  * Sets the appropriate values for the espdu and transmits it out the behaviorStreamBuffer 
	  * These values are oriented according to DIS-coordinates; thus, if you wish to transmit to 
	  * a VRML world, put 'up' into Y and 'right' into -Z (reverse sign).  The function calls 
	  * would look like: <p>
	  *
	  *		to DIS:	 bbds.transmitUpdate(x, y, z, etc) <p>
	  *		to VRML: bbds.transmitUpdate(x, z, -y, etc) <p>
	  * 
	  * Similar transformations must be done with the remaining arguments 
	  *
	  * @param	X							position along the x axis (meters)	
	  * @param	Y							position along the y axis (meters)
	  * @param	Z							position along the z axis (meters)
	  * @param	Psi							roll (radians)
	  * @param	Phi							yaw (radians)
	  * @param	Theta						pitch (radians)
	  * @param	velX						velocity on the x axis (meters per second)
	  * @param	velY						velocity on the y axis (meters per second)
	  * @param	velZ						velocity on the z axis (meters per second)
	  * @param	angularVelocityAboutX		rate of roll (radians per second)
	  * @param	angularVelocityAboutY		rate of yaw (radians per second)
	  * @param	angularVelocityAboutZ		rate of pitch (radians per second)
	  */
	public void transmitUpdate (	float	X,
						float	Y,
						float	Z,
						float	Psi,
						float	Phi,
						float	Theta,
						float	velX,
						float	velY,
						float	velZ,
						float	angularVelocityAboutX,
						float	angularVelocityAboutY,
						float	angularVelocityAboutZ )
	{
		espdu.setEntityLocationX(X);
		espdu.setEntityLocationY(Y);
		espdu.setEntityLocationZ(Z);
		espdu.setEntityOrientationPsi(Psi);
		espdu.setEntityOrientationPhi(Phi);
		espdu.setEntityOrientationTheta(Theta);
		espdu.setEntityLinearVelocityX(velX);
		espdu.setEntityLinearVelocityY(velY);
		espdu.setEntityLinearVelocityZ(velZ);
		espdu.setEntityAngularVelocityX(angularVelocityAboutX);
		espdu.setEntityAngularVelocityY(angularVelocityAboutY);
		espdu.setEntityAngularVelocityZ(angularVelocityAboutZ);

		espdu.makeTimestampCurrent();
		if (DEBUG) System.out.println(espdu.toString());
		if (DEBUG) System.out.println(espdu.getEntityLocationX());
		if (DEBUG) System.out.println(espdu.getEntityLocationY());
		if (DEBUG) System.out.println(espdu.getEntityLocationZ());
		if (DEBUG) System.out.println(espdu.getEntityOrientationPsi());
		if (DEBUG) System.out.println(espdu.getEntityOrientationPhi());
		if (DEBUG) System.out.println(espdu.getEntityOrientationTheta());
		if (DEBUG) System.out.println(espdu.getEntityLinearVelocityX());
		if (DEBUG) System.out.println(espdu.getEntityLinearVelocityY());
		if (DEBUG) System.out.println(espdu.getEntityLinearVelocityZ());
		if (DEBUG) System.out.println(espdu.getEntityAngularVelocityX());
		if (DEBUG) System.out.println(espdu.getEntityAngularVelocityY());
		if (DEBUG) System.out.println(espdu.getEntityAngularVelocityZ());

		streamBuffer.sendPdu(espdu, ipAddress, portNumber);
	}

	/**
	 * shuts the stream buffer down and cleans up the stream for the next use.
	 */
	public void shutdown ()
	{
		streamBuffer.cleanup();
	}
}
