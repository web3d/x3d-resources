package melonlauncher;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Title:        Melon Launcher
 * Description:  Custom Panel for drawing
 * Copyright:    Copyright (c) 2001
 * Company:      CS2973/MV4473
 * @author Andrew Wiest
 * @version 1.0
 */

public class CustomPanelXY extends JPanel {

 //data members
  private boolean ReadyToDraw = false;
  private Vector FlightPath;
  private Double xObject;
  private Double yObject;
  private Double zObject;
  private double xValue,yValue,zValue;
  private int intxValue = 35;
  private int intyValue = 30;
  private int intzValue = 0;
  private double lastXValue,lastYValue,lastzValue;
  private int intlastXValue,intlastYValue,lastZValue;

  public CustomPanelXY() {
  System.out.println("inside customXY panel default constructor");
  }

  public void paint ( Graphics g)  {

    super.paint(g);

    System.out.println("inside paintcomponent method for XY Plane");

    //draw axes
    g.drawLine(150,0,150,300);
    g.drawLine(0,150,300,150);
    if (ReadyToDraw)  {
    draw(g);
    }
    else  {
    }

  }

  public void SetFlightPath(Vector FlightPathInput) {
  // get the vector input and populate local vector
    FlightPath = FlightPathInput;
    System.out.println("FlightPath is set");
   }

  public void draw(Graphics g)  {

    System.out.println("inside draw method");

   Enumeration enum = FlightPath.elements();

    double bValue,mValue;



    while (enum.hasMoreElements() ) {

        xObject = (Double) enum.nextElement();
        xValue = (double) xObject.doubleValue();//get x value
        yObject = (Double) enum.nextElement();
        yValue = (double) yObject.doubleValue();//get y value
        zObject = (Double) enum.nextElement();
        zValue = (double) zObject.doubleValue();//get z value
        //System.out.println(zValue);
        //interpolate below ground Y values
        if (yValue < 0) {

          mValue = (yValue-lastYValue)/(xValue-lastXValue); //slope of a linear interpolation
          bValue = yValue - xValue*mValue;
          xValue = -bValue/mValue;
          yValue = 0;
          }
        //store values for last point interpolation
        lastXValue = xValue;
        lastYValue = yValue;
        //System.out.println(yValue);
        //scale for panel size
        xValue = xValue/3;
        yValue = yValue/3;

        //integerize xValue and yValue for plotting
        intxValue = (int) xValue;
        intyValue = (int) yValue;

        //move launch point to center of panel display

        intyValue = - intyValue + 148;
        intxValue = intxValue +150;
        //System.out.println(intyValue);
        //draw the points as Ovals on the screen
        g.setColor(Color.green);
        g.fillOval(intxValue,intyValue,10,5);
      } //end while


  }

  public void SetReadyToDraw(boolean inputReadyToDraw) {

  ReadyToDraw = inputReadyToDraw;


  }









}