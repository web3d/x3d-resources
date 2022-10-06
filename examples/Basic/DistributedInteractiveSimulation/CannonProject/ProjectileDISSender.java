

/**
 * Based on baseballDISSender  by VictorSpears and David Back
 *      Used to transmit Entity State PDUs to the indicated IP address and port number
 *        for the given entity ID.
 *      This class is used in conjunction with the Cannon class to model the
 *         motion of a projectile.
 *
 * @author Ernesto Salles
 * @version 1.0
 */

import java.text.*;			// for class DecimalFormat
import java.util.*;			// for class Vector
import mil.navy.nps.dis.*;		// for class EntityStatePdu
import mil.navy.nps.testing.*;	        // for MulticastPduSender
import mil.navy.nps.util.*;
import mil.navy.nps.disEnumerations.*;
import java.awt.event.AdjustmentEvent;
import java.lang.Math.*;



public class ProjectileDISSender {

	private EntityStatePdu		    espdu;
	private	CreateEntityPdu		    crpdu;
	private RemoveEntityPdu		    rmpdu;
	private BehaviorStreamBufferUDP	    streamBuffer;
	private	String			    ipAddress;
	private int			    portNumber;

	private static final boolean	DEBUG = true;		//prints out espdu before sending


	public ProjectileDISSender (	String	ipAddr,
						int	portNumber,
						int	timeToLive,
						String	marking,
						boolean	rtpHeaderEnabled )
	{
		//initialize the various pdus
		crpdu = new CreateEntityPdu();
		crpdu.setRtpHeaderEnabled(rtpHeaderEnabled);
		rmpdu = new RemoveEntityPdu();
		rmpdu.setRtpHeaderEnabled(rtpHeaderEnabled);
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

	public void shutdown ()
	{
		streamBuffer.cleanup();
	}
}