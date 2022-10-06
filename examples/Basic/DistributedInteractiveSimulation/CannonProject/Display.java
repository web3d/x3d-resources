

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import javax.vecmath.*;


/**
 * Title:   Display
 * Description:   Used in conjunction with the Cannon class to display a 2D representation
 *                   of the projectile motion.
 *                The display has two graphs.  The top graph indicates the profile
 *                    of the projectile on a y (verticle) and x (horizontal) plane,
 *                    where the x axis is down the direction of firing
 *                    The bottom graph shows a top-down view of the projectile on a
 *                    z (verticle) and x (horizontal) plane, where the x axis is the
 *                    same as the top graph, and the z axis shows lateral displacement
 *                    from the initial direction of fire,; a function of cross wind
 *                Initially each tick-mark on the axises indicates 50 meters with
 *                    the scale at 100percent
 * Copyright:    Copyright (c) 2001
 * @author        Ernesto Salles
 * @version 1.0
 */

public class Display extends JPanel{

  JPanel panel;

  Vector paths,pathData;
  int xStartPosit = 50;
  int xEndPosit = 700;
  int zStartPosit = 50;
  int zEndPosit = 250;
  int yStartPosit = 350;
  int yEndPosit = 550;
  int yMidPosit = 450;
  int width = xEndPosit - xStartPosit;

  /**
   * The constructor
   */
  public Display() {
    this.setLayout(new BorderLayout());
    createPanel();
    this.add(panel,BorderLayout.CENTER);
    pathData = new Vector();
    paths = new Vector();
    this.repaint();
  }

  /**
   * Adds the given path to the Vector of Paths
   */
  public void addData(Vector path){
    paths.add(path);
  }

  /**
   * creates the main panel
   */
  private void createPanel(){
    panel = new JPanel();
    panel.setBackground(Color.black);
  }


  /**
   * Draws the graphs and paths, if available
   */
  public void paint (Graphics g){
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    drawTopGraph(g2d);
    drawBottomGraph(g2d);
    drawTicks(g2d);

    // go through the Vector of paths, and draw each path
    for (int i=0; i < paths.size(); i++) {
      pathData = (Vector) paths.elementAt(i);
      g2d.setColor((Color) pathData.elementAt(0));
      drawWind(g2d);
      g2d.setColor((Color) pathData.elementAt(0));
      drawTrajectory(g2d);
    }
    g2d.dispose();
  }

  /**
   * draws the top graph
   */
  private void drawTopGraph(Graphics2D g2d){
    g2d.setColor(Color.white);
    g2d.drawLine(xStartPosit, zEndPosit, xEndPosit, zEndPosit);
    g2d.drawLine(xStartPosit, zStartPosit, xStartPosit, zEndPosit);

  }

  /**
   * draws the bottom graph
   */
  private void drawBottomGraph(Graphics2D g2d){
    g2d.setColor(Color.white);
    g2d.drawLine(xStartPosit, yMidPosit, xEndPosit, yMidPosit);
    g2d.drawLine(xStartPosit, yStartPosit, xStartPosit, yEndPosit);

  }

  /**
   * draws the wind vector for the current path.  The wind information is stored
   *    in the second element of the vector
   */
  private void drawWind(Graphics2D g2d){

    Vector3d wind = new Vector3d((Vector3d) pathData.elementAt(1));
    wind.scale(4);
    g2d.drawLine(400,425,(400 + (int) wind.x),(425 + (int)wind.z));
    g2d.setColor(Color.yellow);
    g2d.drawOval(398,423,4,4);
  }

  /**
   * draws the tick marks on the graph
   */
  private void drawTicks(Graphics2D g2d){
    g2d.setColor(Color.red);
    int xPos = xStartPosit;
    while (xPos <= xEndPosit){
      g2d.drawLine(xPos,(zEndPosit+1),xPos,(zEndPosit+10));
      g2d.drawLine(xPos,(yMidPosit-10),xPos,(yMidPosit+10));
      xPos += 20;
    }

    int yPos = yStartPosit;
    while (yPos <= yEndPosit){
      g2d.drawLine((xStartPosit-10),yPos,(xStartPosit-1),yPos);
      yPos += 10;
    }

    int zPos = zStartPosit;
    while (zPos <= zEndPosit){
      g2d.drawLine((xStartPosit-10),zPos,(xStartPosit-1),zPos);
      zPos += 10;
    }
  }

  /**
   * goes through the path data of the current path and draws the path
   */
  public void drawTrajectory(Graphics2D g2d){
    if (pathData.size() > 0){
      int elementNum = 3;
      Vector3d data, prevData;
      prevData = (Vector3d) pathData.elementAt(2);

      while(elementNum < pathData.size()){
        data = (Vector3d) pathData.elementAt(elementNum);
        g2d.drawLine((int)(prevData.x + xStartPosit), (int)(yMidPosit + prevData.z),
                     (int)(data.x + xStartPosit), (int)(yMidPosit + data.z));
        g2d.drawLine((int)(prevData.x + xStartPosit), (int)(zEndPosit - prevData.y),
                     (int)(data.x + xStartPosit), (int)(zEndPosit - data.y));
        prevData = data;
        elementNum++;
      }
    }
  }

  /**
   * returns the display width
   */
  public int getDisplayWidth() {
    return width;
  }

  /**
   * Used to remove all the path data
   */
  public void clearDisplay(){
    paths.clear();
    this.repaint();
  }





}