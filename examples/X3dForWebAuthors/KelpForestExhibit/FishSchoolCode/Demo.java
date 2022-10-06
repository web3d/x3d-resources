//demo.java

//************************************
//
// Stewart Liles
//
// Stewart W. Liles
// Used with boid.java
//
//************************************

import java.util.*;

import vrml.*;
import vrml.node.*;

import vrml.field.*;


//import boid;

public class Demo extends Script
  {

  private   Node        node[]       = null;
  private   SFFloat   GlobalTick     = null;
  private   Boid      globalTmpBoid  = null;
  private   int       NUMBOIDS       = 10;
  private   SFNode    B0  = null;
  private   SFNode    B1  = null;
  private   SFNode    B2  = null;
  private   SFNode    B3  = null;
  private   SFNode    B4  = null;
  private   SFNode    B5  = null;
  private   SFNode    B6  = null;
  private   SFNode    B7  = null;
  private   SFNode    B8  = null;
  private   SFNode    B9  = null;


  public void initialize ()
    {
    node = new Node[NUMBOIDS];
    // Get event ins and outs
    B0 = (SFNode) getField ("B0");
    B1 = (SFNode) getField ("B1");
    B2 = (SFNode) getField ("B2");
    B3 = (SFNode) getField ("B3");
    B4 = (SFNode) getField ("B4");
    B5 = (SFNode) getField ("B5");
    B6 = (SFNode) getField ("B6");
    B7 = (SFNode) getField ("B7");
    B8 = (SFNode) getField ("B8");
    B9 = (SFNode) getField ("B9");


    node[0] = (Node) (B0.getValue());
    node[1] = (Node) (B1.getValue());
    node[2] = (Node) (B2.getValue());
    node[3] = (Node) (B3.getValue());
    node[4] = (Node) (B4.getValue());
    node[5] = (Node) (B5.getValue());
    node[6] = (Node) (B6.getValue());
    node[7] = (Node) (B7.getValue());
    node[8] = (Node) (B8.getValue());
    node[9] = (Node) (B9.getValue());

    addBoidsToWorld();
    }


  public void processEvent (Event event)
    {

     // update the visibility matrix
     Boid.updateVisMat();

     // let each boid update itself
     for (int i=0; i<NUMBOIDS; i++)
        {
        ((Boid)(Boid.get(i))).cycle();
        }

    }



//  public boolean action(Event event, Object what)
  public void addBoidsToWorld()
    {
     for(int ix=0;ix<NUMBOIDS;ix++)
        {
// add boids to world and create boidList
        globalTmpBoid = new Boid(node[ix]);
        }
    }

  }// End Demo

