
//Boid.java

//***************************************
// Stewart W. Liles
// CS 4202
// Flocking Boid Project
// Prof. Brutzman
//
//***************************************
//
//
import java.*;
import java.util.*;

import vrml.node.*;
import vrml.field.*;

//import Vec3f;
//import Quaternion;

public class Boid {//boid extends Object
  // BOID CONSTANTS and initial values
  static final float  initPos[]     = {0f, 0f, 0f};
  static final float  initVel[]     = {0f, 0f, -1f};
  static final float  initAcc[]     = {0f, 0f, 0f};
  static final float  initRot[]     = {0f, 1f, 0f, 0f};
  static final float  BOUND_SPHERE_SIZE = 400f; // in terms of r^2 ie r=30 r^2 = 900
  static final float  MAX_VELOCITY = 10f;
  static final float  CRUISE_SPEED = .3f*MAX_VELOCITY;//30% of max velocity
  static final float  CRUISE_DISTANCE = 4f;
  static final float  SEP_FACTOR = 0.09f;
  static final float  APP_FACTOR = 0.05f;
  static final int    NUMBOIDS   = 10;

  static Vector       boidList      = null;
  static int          visMat[][]    = null;

  Node                node          = null;
  SFVec3f             myPos         = null;
  SFRotation          myRot         = null;
  Vec3f               posVec        = new Vec3f();
  Vec3f               velVec        = new Vec3f();
  Vec3f               accVec        = new Vec3f();
  Quaternion          currQuat      = null;
  Quaternion          tmpQuat       = null;
  int                 currFrame     = 0;

  public  Boid (Node node)
    {
    int tmpVisMat[][] = null;

    if (boidList == null)
      {
      System.out.println("creating boidList");
      boidList = new Vector();
      }
    if (visMat == null)
      {
      System.out.println("creating visMat");
      visMat = new int[0][0];  // nothing?
      }

    boidList.addElement(this);

    // create and init new visibility matrix.  Each
    // boid has the same index for the list and the
    // matrix...which is now boidList.size()-1
    // *this routine has been tested*

    tmpVisMat = new int[boidList.size()][boidList.size()];
    for (int i=0; i<boidList.size(); i++)
      {
      if (i == boidList.size()-1)
        {
        // these are new...just set to no vis for now
        for (int j=0; j<boidList.size(); j++)
          {
          tmpVisMat[i][j] = 0;
          }
        }
      else
        {
        for (int j=0; j<boidList.size(); j++)
          {
          if (j == boidList.size()-1)
            {
            // this is new...just set to no vis for now
            tmpVisMat[i][j] = 0;
            }
          else
            {
            tmpVisMat[i][j] = visMat[i][j];
            }
          }
        }
      }
    visMat = tmpVisMat; // no delete
//    printVisMat();

    myPos = (SFVec3f) node.getExposedField("translation");
    myRot = (SFRotation) node.getExposedField("rotation");


    posVec.set(initPos);
    velVec.set(initVel);
    accVec.set(initAcc);
    tmpQuat = new Quaternion();
    currQuat  = new Quaternion();
    currQuat.setAxisAngle(initRot);

    myPos.setValue(initPos);
    myRot.setValue(initRot);
    initPos[0]+=1;
    }


  public void cycle()
    {
    float position[] = new float[3]; // x, y, z
    float rotation[] = new float[4]; // axis, angle (radians)
    Vec3f tmpVec = new Vec3f();


   float hpr[] = new float[3];  // heading, pitch, roll

   // calculate new acceleration vector
   // I assumed dt = .1 for now
   // need to tie dt to framerate

   calcAcceleration();
   tmpVec.scale(.1f, accVec);
   velVec.add(tmpVec);
   tmpVec.scale(.1f, velVec);
   posVec.add(tmpVec);
   posVec.get(position);

   // calculate new euler angles based on above
   calcEulers(hpr);

   // convert to quaternion for VRML
   currQuat.setEulers(hpr);
   currQuat.getAxisAngle(rotation);

    // now apply any changes in pos/rot
    myPos.setValue(position);
    myRot.setValue(rotation);
    }


  public void dispose()
    {
    int index;
    int tmpVisMat[][];
    int i, tmp_i;
    int j, tmp_j;

    // Each boid has the same index for the boidList
    // as it has for the matrix.  So the question is,
    // what index is "this" in the list?
    index = boidList.indexOf(this);

    // Now remove boid from list
    boidList.removeElement(this);

    // Now remove the index^th row and col from mat
    // *this routine has been tested*
    tmpVisMat = new int[boidList.size()][boidList.size()];
    tmp_i = 0;
    for (i=0; i<=boidList.size(); i++)
      {
      if (i != index)
        {
        tmp_j = 0;
        for (j=0; j<=boidList.size(); j++)
          {
          if (j != index)
            {
            tmpVisMat[tmp_i][tmp_j] = visMat[i][j];
            tmp_j++;
            }
          }
        tmp_i++;
        }
      }
    visMat = tmpVisMat; // no delete
//    printVisMat();
    }


  // Returns number of boids in list
  //
  public static int getNum()
    {
    if (boidList == null)
      return 0;
    return boidList.size();
    }


// Returns boid
//
  public static Boid get(int index)
    {
    if (boidList == null)
      return null;
    if (index < 0 || index >= boidList.size())
      return null;
    return (Boid)(boidList.elementAt(index));
    }


// Prints visMat for troubleshooting
//
  public static void printVisMat()
    {
    System.out.println("VisMat = ");
    for (int i=0; i<boidList.size(); i++)
      {
      for (int j=0; j<boidList.size(); j++)
        {
        System.out.print("  " + visMat[i][j]);
        }
      System.out.println(" ");
      }
    }


// Updates VisMat each frame using isVisible function
//
  public static void updateVisMat()
    {
    Boid tmpBoid1 = null;
    Boid tmpBoid2 = null;
    int i, j;
    int size = Boid.getNum();



    for (i=0; i<Boid.getNum(); i++)
      {
      tmpBoid1 = Boid.get(i);
      for (j=0; j<Boid.getNum(); j++)
        {

          tmpBoid2 = Boid.get(j);
          if (tmpBoid1.isVisible(tmpBoid2) == 1)
            {
            visMat[i][j] = 1;

            }
          else
            {
            visMat[i][j] = 0;

            }

        }
      }

    }


// Returns 1 if visible and 0 if not
//
  public int isVisible(Boid that) // as opposed to "this" :)
    {
    Vec3f tmp_pos = new Vec3f();
    float dist_sqr;
    float angleBetween,dotP;
    int probe = 0;
    // trivial case. Note that we don't set
    // true because it should not affect self
    if (this == that)
      return 0;

    // simple test if two boids are within 10m
    that.getPosition(tmp_pos);
    tmp_pos.sub(posVec);
    dist_sqr = tmp_pos.length_sqr();
    if (dist_sqr < 100f)
      probe = 1;

    //if boids are within 10m and within 120 degree field of view
    // they are fish with eyes on the side of their head
    if(probe == 1){
       dotP = velVec.dot(tmp_pos);
       angleBetween = (float)Math.acos(dotP/(velVec.length()*tmp_pos.length()));
       if(angleBetween < 2.5f)
           return 1;
       else
           return 0;
    }
    else{
       return 0;
    }
  }


// returns position vector
//
  public void getPosition(Vec3f pos)
    {
    posVec.get(pos);
    }


// Calculates Euler angles for flight model
// Very simple model
// could be converted to Quaterion math
//
  public void calcEulers(float hpr[])
    {

        // -z-x-y coordinate system
        Vec3f tempvec = new Vec3f(0f,0f,0f);
        Vec3f lateralDir = new Vec3f(0f,0f,0f);
        float lateralMag;

        tempvec.cross(velVec,accVec);
        tempvec.cross(tempvec,velVec);
        lateralDir.normalize(tempvec);
        lateralMag = accVec.dot(lateralDir);

        //roll calculation
        if(lateralMag == 0)
           hpr[2] = 0;
        else
           hpr[2] = (float)(Math.atan2(9.806650,(double)(lateralMag))-Math.PI/2);

        // pitch
        if((velVec.get(2) != 0) && (velVec.get(0) != 0))
           hpr[1] = (float)(Math.atan(velVec.get(1)/
                         Math.sqrt(velVec.get(2)*velVec.get(2) +
                            velVec.get(0)*velVec.get(0))));
        else
           hpr[1] = 0;

        // heading - yaw
        if(velVec.get(2) != 0)
           hpr[0] = (float)(Math.atan2(velVec.get(0),velVec.get(2))+Math.PI);
        else
           hpr[0] = 0;


    }


// Returns acceleration vector that is added to calc position for
// each frame.
// Controls priority of behaviors
//
  public void calcAcceleration()
    {
    int curr_index;

    // get this boid's index now and
    // pass into routines below so
    // that it can be used for indexing
    // the visibility matrix.
    curr_index = boidList.indexOf(this);


    // first clear acceleration
    accVec.makeNull();

    // avoid obtacles and other boids
    avoidObstacles(curr_index);
    if (accVec.length_sqr() > 1.0f)
      return;

    // try to move towards the flock's center
    flockCenter(curr_index);
    if (accVec.length_sqr() > 1.0f)
      return;

    // try to maintain cruising distance
    maintainDistance(curr_index);
    if (accVec.length_sqr() > 1.0f)
      return;

    // try to match flock velocity
    matchVelocity(curr_index);
    if (accVec.length_sqr() > 1.0f)
      return;


    // wander around
    wanderAround(curr_index);
    if (accVec.length_sqr() > 1.0f)
      return;

    // level out flight
    levelOut(curr_index);
      return;
    }

// Keeps boids in bounding sphere
// Out of Creation Sphere
// and from colliding with each other
//
  public void avoidObstacles(int curr_index)
    {
    Vec3f tmpVec = new Vec3f();
    Boid tempBoid = null;
    float tempDist = 0f;
    // stay away from imaginery bounding sphere
    if (posVec.length_sqr() > BOUND_SPHERE_SIZE)
      {
      tmpVec.set(posVec);
      tmpVec.normalize();
      tmpVec.negate();
      tmpVec.scale(0.5f); // not too powerful
      accVec.add(tmpVec);
      }

    // stay away from visable boids
    for (int j=0; j<boidList.size(); j++)
      {
      if (j != curr_index) // ignore myself
        {
        if (visMat[curr_index][j] == 1) // I see you
          {
          // make adjustments
          // do not hit other boids
          // use small force to not overpower cruising distance

          tempBoid = Boid.get(j);
          tmpVec.set(tempBoid.posVec);
          tmpVec.sub(posVec);
          tempDist = tmpVec.length();
          if(tempDist < 2f)
             {

             tmpVec.set(posVec);
             tmpVec.normalize();
             tmpVec.negate();
             tmpVec.scale(1.5f); // overpowering so there are not many collisions
             accVec.add(tmpVec);

             }

          }
        }
      }
    }


// Calc average position of visible group adds resulting
// vector to acceleration vector
//
  public void flockCenter(int curr_index)
    {
    Boid  that = null; // as opposed to this :)
    Vec3f center = new Vec3f();
    Vec3f tmpVec = new Vec3f();
    int boidsSeen = 0;

    // flock towards pac center
    for (int j=0; j<boidList.size(); j++)
      {
      if (j != curr_index) // ignore myself
        {
        if (visMat[curr_index][j] == 1) // I see you
          {
          that = Boid.get(j);
          tmpVec.set(that.posVec);
          center.add(center,tmpVec);
          boidsSeen++;
          }
        }
      }

          // Now fly towards center of pack
    if(boidsSeen != 0)
       {
       center.scale(1/boidsSeen);
       tmpVec.sub(center, posVec);
       tmpVec.normalize();
       tmpVec.scale(0.40f);
       accVec.add(tmpVec);
       }

    }// end flock centering



// finds closest boid ensures it maintains a preset distance away
//
  public void maintainDistance(int curr_index)
    {

        Vec3f tempvec = new Vec3f(0f,0f,0f);
        float distToCloseBoid = Float.MAX_VALUE;//distance to closest boid
        boolean foundCloseBoid = false;
        Boid closestBoid = this,tempBoid = this;
        float tempDist = 0f;
        Vec3f speedAdjust = new Vec3f(0f,0f,0f);
        Vec3f separationVec = new Vec3f(0f,0f,0f);
        float separationFactor = SEP_FACTOR;
        float approachFactor = APP_FACTOR;
        float cruiseDist = CRUISE_DISTANCE;

        for(int ix=0;ix<boidList.size();ix++){
           //skip boids can't see
           if(ix != curr_index)
           {
              if(visMat[curr_index][ix] == 1){
                 tempBoid = Boid.get(ix);
                 tempvec.set(tempBoid.posVec);
                 tempvec.sub(posVec);
                 tempDist = tempvec.length();
              }
              //remeber dist to closest boid
              if(tempDist < distToCloseBoid){
                 distToCloseBoid = tempDist;
                 closestBoid = tempBoid;
                 foundCloseBoid = true;
              } // end if
           } // end if
        } // end for

        // Have boid try to remain at least cruisedistance away from
        // closestBoid

        if(foundCloseBoid){
           separationVec.sub(closestBoid.posVec,posVec);

           if(separationVec.get(1) < cruiseDist)
              speedAdjust.set(1,speedAdjust.get(1) + separationFactor);

           else if(separationVec.get(1) > cruiseDist)
              speedAdjust.set(1,speedAdjust.get(1) - approachFactor);

           if(separationVec.get(0) < cruiseDist)
              speedAdjust.set(0,speedAdjust.get(0) + separationFactor);

           else if(separationVec.get(0) > cruiseDist)
              speedAdjust.set(0,speedAdjust.get(0) - approachFactor);

           if(separationVec.get(2) < cruiseDist)
              speedAdjust.set(2,speedAdjust.get(2) + separationFactor);

           else if(separationVec.get(2) > cruiseDist)
              speedAdjust.set(2,speedAdjust.get(2) - approachFactor);

        }//end if

       speedAdjust.normalize();
       speedAdjust.scale(.10f);
       accVec.add(speedAdjust);

    }


// Matches Velocity of closest boid
//
  public void matchVelocity(int curr_index)
    {

        Vec3f velCloseBoid = new Vec3f(0f,0f,0f);
        float tempDist = 0f;
        float distToCloseBoid = Float.MAX_VALUE;
        Boid tempBoid = null;
        Vec3f tempvec = new Vec3f(0f,0f,0f);

        for(int ix=0;ix<boidList.size();ix++){
           //skip boids can't see
           if((ix != curr_index) && (visMat[curr_index][ix] == 1)) //ignore myself
           {
              tempBoid = Boid.get(ix);
              tempvec.set(tempBoid.posVec);
              tempvec.sub(posVec);
              tempDist = tempvec.length();

              //remember velocity of closest boid
              if(tempDist < distToCloseBoid){
                 distToCloseBoid = tempDist;
                 velCloseBoid.set(tempBoid.velVec);
              } // end if
           }// end if
        } // end for

        if(distToCloseBoid != Float.MAX_VALUE){
           velCloseBoid.normalize();
           velCloseBoid.scale(0.05f);
        }

        accVec.add(velCloseBoid);

    }


// Directs speed of boid that has no other imputs
//
  public void wanderAround(int curr_index)
    {

        float distFromCruiseSpeed;
        float urgency;

        Vec3f tempvec = new Vec3f();

        tempvec.set(this.velVec);
        distFromCruiseSpeed = (tempvec.length() - CRUISE_SPEED) / MAX_VELOCITY;
        urgency = (float)Math.abs((double)distFromCruiseSpeed);

        if(urgency > 0.15f)
           urgency = 0.15f;

        if(distFromCruiseSpeed > 0)
           distFromCruiseSpeed = -1;
        else distFromCruiseSpeed = 1;

        tempvec.normalize();
        tempvec.scale(urgency*distFromCruiseSpeed);

        accVec.add(tempvec);

    }


// levels out boid to stop a turn in absence of visible boids
//
  public void levelOut(int curr_index)
    {

        Vec3f tempvec = new Vec3f(0f,0f,accVec.get(2));

        tempvec.normalize();
        tempvec.scale(.25f);
        accVec.add(tempvec);

    }

  }// end Boid.class

// end Boid.class
