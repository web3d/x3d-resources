

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;

/**
 * Title:  InputPanel
 * Description:  An interface panel used in conjunction with the Cannon class.
 *              Allows the user to select initial firing velocity and angle fo rthe projectiel.
 *              Also allows user the user to set the wind directiona dn velocity;
 *              as well as indicate if density or changing densities are to be used.
 * Copyright:    Copyright (c) 2001
 * @author  Ernesto Salles
 * @version 1.0
 */

public class InputPanel extends JFrame implements ActionListener, ChangeListener  {

  // the cannon
  Cannon cannon;

  // the main panel
  JPanel mainPanel;

  // the sliders used for input
  JSlider angleSlider, velocitySlider, windDirSlider, windVelSlider, scaleSlider;

  // button used to fire the cannon
  JButton fireButton, clearButton;

  // used to toggle the drag and density options
  JRadioButton dragButton, densityButton, vrmlButton;

  // the cannon variables
  int angle = 45;
  int fireDirection = 90;
  int initialVelocity = 700;
  int windDirection = 000;
  int windVelocity = 0;
  int scaleFactor = 100;
  boolean dragActivated = false;
  boolean densityChange = false;
  boolean vrmlOption = false;

  /**
   * The Constructor
   * @param c The host cannon
   */
  public InputPanel(Cannon c) {
    cannon = c;

    // set the window variables and create the input panel
    this.setTitle("Cannon Control Panel");
    this.setSize(210, 590);
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    this.setContentPane(mainPanel);
    createPanel();
    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        System.exit(0);}});

  }

  /**
   * Creates the GUI
   */
  private void createPanel(){
    fireButton = new JButton("Fire");
    fireButton.setBounds(0,450,100,50);
    fireButton.addActionListener(this);
    fireButton.setBackground(Color.red);
    mainPanel.add(fireButton);

    clearButton = new JButton("Clear");
    clearButton.setBounds(100,450,100,50);
    clearButton.addActionListener(this);
    clearButton.setBackground(Color.yellow);
    mainPanel.add(clearButton);


    dragButton = new JRadioButton("Enable Drag ");
    dragButton.setBounds(000,400,100,20);
    dragButton.addActionListener(this);
    mainPanel.add(dragButton);

    densityButton = new JRadioButton("Enable Changing Density");
    densityButton.setBounds(25,430,150,20);
    densityButton.addActionListener(this);
    densityButton.setEnabled(false);
    mainPanel.add(densityButton);

    vrmlButton = new JRadioButton("Enable VRML Interface");
    vrmlButton.setBounds(0,520,150,20);
    vrmlButton.addActionListener(this);
    vrmlButton.setEnabled(true);
    mainPanel.add(vrmlButton);

    angleSlider = new JSlider(JSlider.HORIZONTAL,0,90,angle);
    angleSlider.setBounds(0,0,200,75);
    angleSlider.setBorder(new TitledBorder("Firing Angle (" + angle + " degrees)"));
    angleSlider.setMajorTickSpacing(30);
    angleSlider.setMinorTickSpacing(15);
    angleSlider.addChangeListener(this);
    angleSlider.setPaintTicks(true);
    angleSlider.setPaintLabels(true);
    mainPanel.add(angleSlider);

    velocitySlider = new JSlider(JSlider.HORIZONTAL,200,800,initialVelocity);
    velocitySlider.setBounds(0,75,200,75);
    velocitySlider.setBorder(new TitledBorder("Initial Velocity (" + initialVelocity+ " M/s)"));
    velocitySlider.setMajorTickSpacing(200);
    velocitySlider.setMinorTickSpacing(100);
    velocitySlider.addChangeListener(this);
    velocitySlider.setPaintTicks(true);
    velocitySlider.setPaintLabels(true);
    mainPanel.add(velocitySlider);

    windDirSlider = new JSlider(JSlider.HORIZONTAL,0,360,windDirection);
    windDirSlider.setBounds(0,150,200,75);
    windDirSlider.setBorder(new TitledBorder("Wind Direction (" + windDirection + " degrees)"));
    windDirSlider.setMajorTickSpacing(90);
    windDirSlider.setMinorTickSpacing(15);
    windDirSlider.addChangeListener(this);
    windDirSlider.setPaintTicks(true);
    windDirSlider.setPaintLabels(true);
    mainPanel.add(windDirSlider);


    windVelSlider = new JSlider(JSlider.HORIZONTAL,0,40,windVelocity);
    windVelSlider.setBounds(0,225,200,75);
    windVelSlider.setBorder(new TitledBorder("Wind Velocity (" + windVelocity+ " Kts)"));
    windVelSlider.setMajorTickSpacing(10);
    windVelSlider.setMinorTickSpacing(5);
    windVelSlider.addChangeListener(this);
    windVelSlider.setPaintTicks(true);
    windVelSlider.setPaintLabels(true);
    mainPanel.add(windVelSlider);

    scaleSlider = new JSlider(JSlider.HORIZONTAL,0,100,scaleFactor);
    scaleSlider.setBounds(0,300,200,75);
    scaleSlider.setBorder(new TitledBorder("Scale Factor (" + scaleFactor + " %)"));
    scaleSlider.setMajorTickSpacing(25);
    scaleSlider.setMinorTickSpacing(5);
    scaleSlider.addChangeListener(this);
    scaleSlider.setPaintTicks(true);
    scaleSlider.setPaintLabels(true);
    mainPanel.add(scaleSlider);
  }


  public void actionPerformed (ActionEvent event){
    if(event.getSource() == fireButton){
      System.out.println("shot away!!!!! at " + angle + " degrees");
      System.out.println("   Initial velocity: " + initialVelocity + " M/s");
      System.out.println("Wind: " + windDirection + " at " + windVelocity + "M/s  = " + (270-windDirection) );
      if (dragActivated) {System.out.println("drag activated"); }
      cannon.setDrag(dragActivated);
      if (densityChange) {System.out.println("density activated");}
      cannon.setChangingDensities(densityChange);
      cannon.setScaleFactor(scaleFactor);

      // convert knts to m/s = 1852 (meters/knt) / 3600 (second/hour) = meters/sec
      double windVelMeters = (double)windVelocity;
      windVelMeters = windVelMeters * (double)((double)1852/(double)3600);

      cannon.fireCannon(initialVelocity,angle,(270-windDirection),windVelMeters);
    }

    if(event.getSource() == clearButton) {
      cannon.clearDisplay();
    }

    if(event.getSource() == dragButton) {
      if(dragButton.isSelected()) {
        dragActivated = true;
        densityButton.setEnabled(true);
      }
      else {dragActivated = false;
        densityChange = false;
        densityButton.setSelected(false);
        densityButton.setEnabled(false);
      }
    }

    if(event.getSource() == vrmlButton) {
      if(vrmlButton.isSelected()) {
        vrmlOption = true;
        cannon.enableVRML(true);
      }
      else {
        vrmlOption = false;
        cannon.enableVRML(false);
      }
    }

    if(event.getSource() == densityButton) {
      if(densityButton.isSelected()) {densityChange = true;}
      else {densityChange = false;}
    }
  }

  public void stateChanged (ChangeEvent event){

    if(event.getSource() == angleSlider) {
      angle = angleSlider.getValue();
      angleSlider.setBorder(new TitledBorder("Firing Angle (" + angle + " degrees)"));
      cannon.changeAngle(angle);
    }

    if(event.getSource() == scaleSlider) {
      scaleFactor = scaleSlider.getValue();
      scaleSlider.setBorder(new TitledBorder("Scale Factor (" + scaleFactor + " %)"));
    }

    if(event.getSource() == velocitySlider) {
      initialVelocity = velocitySlider.getValue();
      velocitySlider.setBorder(new TitledBorder("Initial Velocity (" + initialVelocity+ " M/s)"));
    }

    if(event.getSource() == windDirSlider) {
      windDirection = windDirSlider.getValue();
      windDirSlider.setBorder(new TitledBorder("Wind Direction (" + windDirection + " degrees)"));
    }

    if(event.getSource() == windVelSlider) {
      windVelocity = windVelSlider.getValue();
      windVelSlider.setBorder(new TitledBorder("Wind Velocity (" + windVelocity+ " Kts)"));
    }
  }
}