package melonlauncher;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

/**
 * Title:        Melon Launcher
 * Description:  This is the main Jframe class where all the displays and sliders reside
 * Copyright:    Copyright (c) 2001
 * Company:      CS2973/MV4473
 * @author Andrew Wiest
 * @version 1.0
 */

public class Framemain extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JToolBar jToolBar = new JToolBar();
  JButton jButtonFileOpen = new JButton();
  JButton jButtonFileSave = new JButton();
  JButton jButtonHelp = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  CustomPanelXY jPanelXYPlane = new CustomPanelXY();
  CustomPanelXZ jPanelXZPlane = new CustomPanelXZ();
  JPanel jPanelControls = new JPanel();
  JPanel jPanelTitle = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabelVelocity = new JLabel();
  TitledBorder titledBorder1;
  JLabel jLabelElevation = new JLabel();
  TitledBorder titledBorder2;
  JLabel jLabelAzimuth = new JLabel();
  JSlider jSliderVelocity = new JSlider();
  JSlider jSliderAzimuth = new JSlider();
  JSlider jSliderElevation = new JSlider();
  JLabel jLabelWindDirection = new JLabel();
  JLabel jLabelWindSpeed = new JLabel();
  JSlider jSliderWindDirection = new JSlider();
  JSlider jSliderWindSpeed = new JSlider();
  JButton jButtonQuit = new JButton();
  JButton jButtonRunSim = new JButton();
  TitledBorder titledBorder3;
  private Vector FlightPath = new Vector(20);
  JLabel jLabelVerticalTrajectory = new JLabel();
  JLabel jLabelHorizontalDrift = new JLabel();

//----------------------------------
//    Data Members:
//----------------------------------
  //initialize slider values
  double sliderVelocity = 50;
  double sliderAzimuth = 0;
  double sliderElevation = 45;
  double sliderWindDirection = 0;
  double sliderWindSpeed = 0;

  /**Construct the frame*/
  public Framemain() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Component initialization*/
  private void jbInit() throws Exception  {
    image1 = new ImageIcon(melonlauncher.Framemain.class.getResource("openFile.gif"));
    image2 = new ImageIcon(melonlauncher.Framemain.class.getResource("closeFile.gif"));
    image3 = new ImageIcon(melonlauncher.Framemain.class.getResource("help.gif"));
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Framemain.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");

    contentPane.setLayout(null);
    this.setSize(new Dimension(879, 800));
    this.setTitle("Melon Launcher");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    jButtonFileOpen.setIcon(image1);
    jButtonFileOpen.setToolTipText("Open File");
    jButtonFileSave.setIcon(image2);
    jButtonFileSave.setToolTipText("Close File");
    jButtonHelp.setIcon(image3);
    jButtonHelp.setToolTipText("Help");
    contentPane.setEnabled(true);
    contentPane.setBorder(titledBorder3);
    contentPane.setMinimumSize(new Dimension(1, 1));
    contentPane.setPreferredSize(new Dimension(1000, 1000));
    //jPanel1.setBounds(new Rectangle(0, 27, 10, 548));
    jToolBar.setBounds(new Rectangle(0, 0, 774, 27));

    jPanelXYPlane.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanelXYPlane.setBounds(new Rectangle(15, 60, 300, 300));

    jPanelXZPlane.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanelXZPlane.setBounds(new Rectangle(16, 402, 300, 300));

    jPanelControls.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanelControls.setBounds(new Rectangle(334, 125, 497, 598));
    jPanelControls.setLayout(null);
    jPanelTitle.setBorder(BorderFactory.createEtchedBorder());
    jPanelTitle.setBounds(new Rectangle(386, 39, 394, 71));
    jPanelTitle.setLayout(null);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel1.setForeground(Color.blue);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Melon Launcher");
    jLabel1.setBounds(new Rectangle(115, 19, 169, 34));
    jLabelVelocity.setBorder(BorderFactory.createEtchedBorder());
    jLabelVelocity.setDisplayedMnemonic('0');
    jLabelVelocity.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelVelocity.setText("Set initial Launch Velocity(m/s)");
    jLabelVelocity.setBounds(new Rectangle(3, 15, 193, 37));
    jLabelElevation.setBorder(BorderFactory.createEtchedBorder());
    jLabelElevation.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelElevation.setText("Set initial Elevation Angle (deg)");
    jLabelElevation.setBounds(new Rectangle(12, 264, 191, 32));
    jLabelAzimuth.setBorder(BorderFactory.createEtchedBorder());
    jLabelAzimuth.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAzimuth.setText("Set initial Azimuth Angle (deg)");
    jLabelAzimuth.setBounds(new Rectangle(138, 66, 196, 34));
    jSliderVelocity.setMinorTickSpacing(1);
    jSliderVelocity.setMajorTickSpacing(10);
    jSliderVelocity.setPaintLabels(true);
    jSliderVelocity.setPaintTicks(true);
    jSliderVelocity.setSnapToTicks(true);
    jSliderVelocity.setBackground(Color.lightGray);
    jSliderVelocity.setBounds(new Rectangle(198, 15, 280, 47));

    //implement JsliderVelocity Listening
    jSliderVelocity.addChangeListener(
                new ChangeListener()  {
                      public void stateChanged( ChangeEvent e )
                      {
                      //System.out.println("inside Velocity Slider");
                      sliderVelocity = jSliderVelocity.getValue();
                      //System.out.println(sliderVelocity);

                      }
    });

    jSliderAzimuth.setMinorTickSpacing(5);
    jSliderAzimuth.setMajorTickSpacing(25);
    jSliderAzimuth.setPaintLabels(true);
    jSliderAzimuth.setMinimum(0);
    jSliderAzimuth.setValue(0);
    jSliderAzimuth.setPaintTicks(true);
    jSliderAzimuth.setMaximum(360);
    jSliderAzimuth.setSnapToTicks(true);
    jSliderAzimuth.setDoubleBuffered(true);
    jSliderAzimuth.setBackground(Color.lightGray);
    jSliderAzimuth.setBounds(new Rectangle(7, 103, 472, 55));

    //implement JsliderAzimuth Listening
    jSliderAzimuth.addChangeListener(
                new ChangeListener()  {
                      public void stateChanged( ChangeEvent e )
                      {
                      //System.out.println("inside Azimuth Slider");
                      sliderAzimuth = jSliderAzimuth.getValue();
                      }
    });

    jSliderElevation.setMinorTickSpacing(1);
    jSliderElevation.setMajorTickSpacing(10);
    jSliderElevation.setOrientation(JSlider.VERTICAL);
    jSliderElevation.setPaintLabels(true);
    jSliderElevation.setValue(45);
    jSliderElevation.setPaintTicks(true);
    jSliderElevation.setMaximum(90);
    jSliderElevation.setSnapToTicks(true);
    jSliderElevation.setBackground(Color.lightGray);
    jSliderElevation.setBounds(new Rectangle(219, 164, 52, 252));

    //implement JsliderElevation Listening
    jSliderElevation.addChangeListener(
                new ChangeListener()  {
                      public void stateChanged( ChangeEvent e )
                      {
                      //System.out.println("inside Elevation Slider");
                      sliderElevation = jSliderElevation.getValue();
                      }
    });

    jLabelWindDirection.setBorder(BorderFactory.createEtchedBorder());
    jLabelWindDirection.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelWindDirection.setText("Set Wind Direction (deg)");
    jLabelWindDirection.setBounds(new Rectangle(12, 483, 155, 36));
    jLabelWindSpeed.setBorder(BorderFactory.createEtchedBorder());
    jLabelWindSpeed.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelWindSpeed.setText("Set Wind Speed (m/s)");
    jLabelWindSpeed.setBounds(new Rectangle(13, 432, 154, 36));
    jSliderWindDirection.setMajorTickSpacing(30);
    jSliderWindDirection.setPaintLabels(true);
    jSliderWindDirection.setMinimum(0);
    jSliderWindDirection.setValue(0);
    jSliderWindDirection.setPaintTicks(true);
    jSliderWindDirection.setMaximum(360);
    jSliderWindDirection.setSnapToTicks(true);
    jSliderWindDirection.setBackground(Color.lightGray);
    jSliderWindDirection.setBounds(new Rectangle(179, 478, 307, 46));

    //implement JsliderWindDirection Listening
    jSliderWindDirection.addChangeListener(
                new ChangeListener()  {
                      public void stateChanged( ChangeEvent e )
                      {
                      //System.out.println("inside WindDirection Slider");
                      sliderWindDirection = jSliderWindDirection.getValue();
                      }
    });

    jSliderWindSpeed.setMajorTickSpacing(10);
    jSliderWindSpeed.setPaintLabels(true);
    jSliderWindSpeed.setPaintTicks(true);
    jSliderWindSpeed.setSnapToTicks(true);
    jSliderWindSpeed.setBackground(Color.lightGray);
    jSliderWindSpeed.setBounds(new Rectangle(180, 423, 302, 45));

    //implement JsliderWindSpeed Listening
    jSliderWindSpeed.addChangeListener(
                new ChangeListener()  {
                      public void stateChanged( ChangeEvent e )
                      {
                      //System.out.println("inside WindSpeed Slider");
                      sliderWindSpeed = jSliderWindSpeed.getValue();
                      }
    });

    jButtonQuit.setBackground(new Color(192, 192, 140));
    jButtonQuit.setText("Quit");
    jButtonQuit.setBounds(new Rectangle(362, 536, 114, 52));

    //Quit Button Listener
    jButtonQuit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonQuit_actionPerformed(e);
      }
    });

    jButtonRunSim.setBackground(Color.pink);
    jButtonRunSim.setText("Run Simulation");
    jButtonRunSim.setBounds(new Rectangle(14, 531, 184, 55));

    //Run Simulation Button Listener
    jButtonRunSim.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonRunSim_actionPerformed(e);
      }
    });

    jLabelVerticalTrajectory.setBackground(Color.lightGray);
    jLabelVerticalTrajectory.setForeground(Color.darkGray);
    jLabelVerticalTrajectory.setBorder(BorderFactory.createEtchedBorder());
    jLabelVerticalTrajectory.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelVerticalTrajectory.setText("Vertical Trajectory");
    jLabelVerticalTrajectory.setBounds(new Rectangle(95, 33, 145, 21));
    jLabelHorizontalDrift.setForeground(Color.darkGray);
    jLabelHorizontalDrift.setBorder(BorderFactory.createEtchedBorder());
    jLabelHorizontalDrift.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelHorizontalDrift.setText("Horizontal Drift");
    jLabelHorizontalDrift.setBounds(new Rectangle(90, 373, 158, 22));
    jToolBar.add(jButtonFileOpen);
    jToolBar.add(jButtonFileSave);
    jToolBar.add(jButtonHelp);
    contentPane.add(jPanelTitle, null);
    jPanelTitle.add(jLabel1, null);
    contentPane.add(jPanelXZPlane, null);
    contentPane.add(jPanelXYPlane, null);
    contentPane.add(jLabelVerticalTrajectory, null);
    contentPane.add(jLabelHorizontalDrift, null);
    contentPane.add(jPanelControls, null);
    //jPanelControls.add(component1, null);
    jPanelControls.add(jSliderVelocity, null);
    jPanelControls.add(jSliderAzimuth, null);
    jPanelControls.add(jLabelVelocity, null);
    jPanelControls.add(jButtonRunSim, null);
    jPanelControls.add(jLabelWindSpeed, null);
    jPanelControls.add(jSliderWindDirection, null);
    jPanelControls.add(jLabelElevation, null);
    jPanelControls.add(jSliderElevation, null);
    jPanelControls.add(jLabelAzimuth, null);
    jPanelControls.add(jButtonQuit, null);
    jPanelControls.add(jLabelWindDirection, null);
    jPanelControls.add(jSliderWindSpeed, null);
    //contentPane.add(jPanel1, null);
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
    contentPane.add(jToolBar, null);
  }
  /**File | Exit action performed*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  /**Help | About action performed*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    Framemain_AboutBox dlg = new Framemain_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }

  void jButtonQuit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

// Simulation Button Actions

  void jButtonRunSim_actionPerformed(ActionEvent e) {

    MelonLauncherPduGenerator PDUsender = new MelonLauncherPduGenerator("melon");
    //create trajectory
  CalculateParameterConversions calcConvert = new CalculateParameterConversions(sliderVelocity, sliderAzimuth, sliderElevation, sliderWindDirection, sliderWindSpeed);
  //obtain vector of trajectory data - X values are even, Y values are odd
  FlightPath = calcConvert.GetFlightPath();
  //pass the points in the XY plane to the appropriate panel
  passXYpoints();
  drawXYpoints();

 // pass points one by one to PDU network class
  Enumeration enum = FlightPath.elements();
  Double xObject, lastXObject;
  Double yObject, lastYObject;
  Double zObject, lastZObject;
  double xValue,yValue,zValue,lastxValue,lastyValue,lastzValue;
  //get initial points
    xObject = (Double) enum.nextElement();
    lastxValue = (double) xObject.doubleValue();//get x value
    yObject = (Double) enum.nextElement();
    lastyValue = (double) yObject.doubleValue();//get y value
    zObject = (Double) enum.nextElement();
    lastzValue = (double) zObject.doubleValue();//get z value
  //get rest of points
  while (enum.hasMoreElements() ) {
        float speed;
        double xDiff, yDiff, zDiff;

        xObject = (Double) enum.nextElement();
        xValue = (double) xObject.doubleValue();//get x value
        yObject = (Double) enum.nextElement();
        yValue = (double) yObject.doubleValue();//get y value
        zObject = (Double) enum.nextElement();
        zValue = (double) zObject.doubleValue();//get z value
      //calculate speed
        xDiff = xValue-lastxValue;
        yDiff = yValue-lastyValue;
        zDiff = zValue-lastzValue;
        speed = (float) Math.sqrt(((xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff)));
        //System.out.println(speed);
       // output data to PDU stream
         if (yValue <=0) {

          double slope;

          slope = - yValue/lastyValue;
          xValue = (xValue + slope * lastxValue) / (1 + slope);
          yValue = 0;

        }
        else {
        // do nothing
        }
        //output PDU's
        PDUsender.moveToWaypoint(xValue,yValue,zValue,speed);
      //move points for next iteration
        lastxValue = xValue;
        lastyValue = yValue;
        lastzValue = zValue;
    }

  }

  public void passXYpoints()  {

    System.out.println("Inside passXYpoints method");

    jPanelXYPlane.SetFlightPath(FlightPath);

  }

  public void drawXYpoints()  {

    System.out.println("inside drawXYpoint method" );
    jPanelXYPlane.SetReadyToDraw(true);
    jPanelXYPlane.repaint();
    //jPanelXYPlane.SetReadyToDraw(false);
  }

}