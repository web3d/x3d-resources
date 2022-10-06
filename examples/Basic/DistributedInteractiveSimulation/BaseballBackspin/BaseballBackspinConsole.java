/**
 * Title:        Advanced Physically Based Modeling - Baseball
 * Description:  Modeling the effects of backspin on a fastball
 * Copyright:    Copyright (c) 2001
 * Class:        MV4472
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class: BaseballBackspinConsole
 * @author Victor Spears & David Back
 * @version 1.0
 *
 * User interface for the BackspinPhysics and BaseballDisSender classes.  Allows the 
 * user to select the VRML file for DIS output and set the angular and  
 * linear velocity of the baseball, as well as 'pitch' (starting the 
 * simulation) and 'quit'.
 */
public class BaseballBackspinConsole extends JFrame implements AdjustmentListener,
                                                            ActionListener{

    //Multicast address we're using, completely made up ("big sky theory")
    public static final String CONNECTION_ADDRESS = "224.0.0.3";

    //Port connecting on
    public static final int CONNECTION_PORT = 64200;

    /**
//     * @param jPanel1 - the Top portion of the TB gui
     */
    JPanel jPanel1 = new JPanel();

    /**
//     * @param jPanel2 - the bottom portion of the gui,has 2-D map of scene
     */
    JPanel jPanel2 = new JPanel();

    /**
//     * @param pitchBall - pitches the ball
     */
    JButton pitchBall = new JButton();
    JButton quit = new JButton();
    JTextField speedTextField = new JTextField();
    JScrollBar angVelScroll = new JScrollBar();
    JTextField angVelTextField = new JTextField();
    JScrollBar speedScroll = new JScrollBar();
    JTextField turnTextField = new JTextField();

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel10 = new JLabel();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();
    JLabel jLabel13 = new JLabel();
    JLabel jLabel14 = new JLabel();
    JPanel jPanel4 = new JPanel();

    private static int turnPosition = 0;                        // each of the scroll bars
    private static int angVelPosition = 0;

  //Construct the frame

/**
 * Really just a pass-through for the jbInit() method 
 * 
 */

   public BaseballBackspinConsole() {

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    //Initialize the panel
    try
    {
      jbInit();
    }

    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * intialize the gui, called from constructor.  
   * Also contains anonymous class definitions for all button and slider 
   * actions from the gui.
   */
  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(new FlowLayout());
    this.setResizable(false);

    //Set the size of the gui in pixels 250 325
    this.setSize(250, 325);

    //Place it on the screen based on screensize
    this.setLocation(5,0);
    //Make it visible
    this.setVisible(true);

    //Set the foreground and background colors and font
    this.setBackground(Color.black);
    this.setForeground(Color.black);

    //Control Panel INfo
    jPanel1.setLayout(null);
    jPanel1.setSize(200, 290);

    //Set the preferred size of the Control Panel
    Dimension d = new Dimension(200,290);
    jPanel1.setPreferredSize(d);

    //Set the background color
    jPanel1.setBackground(new Color(150, 150, 200));
    jPanel1.setFont(new Font("Serif", 0, 10));

    //2-D Map Panel Info
    jPanel2.setLayout(null);

    //Quit button info
    quit.setText("Quit");
    quit.setFont(new Font("Garamond",1,10));
    quit.setBounds(new Rectangle(50, 230, 100, 34));
    quit.addActionListener(this);

    //Pitch button info
    pitchBall.setText("Pitch!");
    pitchBall.setFont(new Font("Comic Sans MS", 1, 10));
    pitchBall.setBounds(new Rectangle(50, 180, 100, 34));
    pitchBall.addActionListener(this);

    //angVel scroll and text information for GUI
    angVelScroll.setVisibleAmount(10);
    angVelScroll.setMaximum(1010);
    angVelScroll.setMinimum(1);
    angVelScroll.setFont(new Font("SansSerif", 1, 10));
    angVelScroll.setOrientation(JScrollBar.HORIZONTAL);
    angVelScroll.setBounds(new Rectangle(5, 30, 187, 20));
    angVelScroll.addAdjustmentListener(this);
    angVelTextField.setHorizontalAlignment(JTextField.CENTER);
    angVelTextField.setText("1");
    angVelTextField.setEditable(false);
    angVelTextField.setBounds(new Rectangle(75, 51, 35, 20));

    jLabel4.setForeground(Color.black);
    jLabel4.setText("Angular Velocity Control");
    jLabel4.setBounds(new Rectangle(35, 15, 150, 15));	// x,y,width,height

    //Bounds for Angular Velocity slider
    jLabel9.setForeground(Color.black);
    jLabel9.setText("1");
    jLabel9.setBounds(new Rectangle(10, 51, 12, 15));
    jLabel11.setForeground(Color.black);
    jLabel11.setText("1000");
    jLabel11.setBounds(new Rectangle(160, 51, 50, 15));

    //Turn scroll info
    speedScroll.setVisibleAmount(4);
    speedScroll.setMaximum(47);
    speedScroll.setOrientation(JScrollBar.HORIZONTAL);
    speedScroll.setMinimum(1);
    speedScroll.setBounds(new Rectangle(5, 110, 187, 20));
    speedScroll.addAdjustmentListener(this);

    jLabel1.setForeground(Color.black);
    jLabel1.setText("Speed of pitch");
    jLabel1.setBounds(new Rectangle(40, 91, 110, 17));

    //Speed Rate info
    turnTextField.setHorizontalAlignment(JTextField.CENTER);
    turnTextField.setText("1");
    turnTextField.setEditable(false);
    turnTextField.setBounds(new Rectangle(75, 147, 35, 20));

    //Bounds indicators on Turn slider
    jLabel7.setForeground(Color.black);
    jLabel7.setText("43");
    jLabel7.setBounds(new Rectangle(144, 131, 21, 15));
    jLabel8.setForeground(Color.black);
    jLabel8.setText("1");
    jLabel8.setBounds(new Rectangle(10, 131, 23, 15));


    //Adding to main panel
    this.setTitle("MV4472 - Baseball backspin" );
    this.setBackground(Color.black);
    this.setFont(new Font("Georgia", 1, 12));

    this.getContentPane().add(jPanel1, null);

    //Adding angVel labels and fields to Control Panel
    jPanel1.add(jLabel4, null);
    jPanel1.add(angVelScroll, null);
    jPanel1.add(jLabel9, null);
    jPanel1.add(jLabel2, null);
    jPanel1.add(angVelTextField, null);
    jPanel1.add(jLabel11, null);


    //Adding speed info to control Panel
    jPanel1.add(jLabel1, null);
    jPanel1.add(speedTextField, null);
    jPanel1.add(speedScroll, null);
    jPanel1.add(jLabel8, null);
    jPanel1.add(jLabel6, null);
    jPanel1.add(jLabel7, null);
    jPanel1.add(jLabel3, null);
    jPanel1.add(turnTextField, null);

    //Add the buttons to the Control Panel
    jPanel1.add(pitchBall, null);
    jPanel1.add(quit, null);

    this.getContentPane().add(jPanel2, null);

    setVisible(true);

    /**
     * anonymous class - if button pressed performs the
     * appropriate actions.
     */
    pitchBall.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            BackspinPhysics baseball = new BackspinPhysics(CONNECTION_ADDRESS,CONNECTION_PORT,Double.valueOf(angVelTextField.getText()).doubleValue(),
                Double.valueOf(turnTextField.getText()).doubleValue());
            System.out.println("Pitching the ball");
            baseball.run();
            repaint();

         }
        }
     );// end of anonymous class for pitchBall

    /**
     * anonymous class - if button pressed performs the
     * appropriate actions.
     */
    quit.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Quitting");
            System.exit(0);
         }
        }
     );// end of anonymous class for quit

    /**
     * anonymous class - if slider moved/adjusted performs 
     * appropriate actions
     */
    angVelScroll.addAdjustmentListener(new AdjustmentListener()
    {
        public void adjustmentValueChanged(AdjustmentEvent e)
        {
            angVelPosition = e.getValue();         // update the current slider position

            angVelTextField.setText(String.valueOf(angVelPosition).toString());            // print new value in the textField
            repaint();
        }
      } // end of anonymous class
     );


    /**
     * anonymous class - if slider moved/adjusted performs 
     * appropriate actions
     */
     speedScroll.addAdjustmentListener(new AdjustmentListener()
     {
        public void adjustmentValueChanged(AdjustmentEvent e)
        {
            turnPosition = e.getValue();                // update the current slider position
            turnTextField.setText(String.valueOf(turnPosition).toString());
            repaint();
        }
      } // end of anonymous class
     );

  }

    //Overriden so we can exit on System Close
    protected void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
          System.exit(0);
        }
    }

   /**
    * stubbed, does nothing, definitions for actions defined 
    * as anonymous classes found in JBINIT().  If not stubbed results in compile time 
    * error.
    */
     public void adjustmentValueChanged(AdjustmentEvent evt)
     {
        //Do nothing in body
     } // end adjustmentValueChanged()

   /**
    * stubbed, does nothing, definitions for actions defined 
    * as anonymous classes found in JBINIT().  If not stubbed results in compile time 
    * error.
    */
   public void actionPerformed(ActionEvent evt)
   {
        //do nothing in body
   } // end actionPerformed()

/**
 * Main method for Control Panel.  Allows the user to specify the .wrl file desired, 
 * then creates a console to send the physics updates to it through DIS 
 */
   public static void main(String[] args)
   {

       //Create a new BaseballBackspinConsole Panel
       BaseballBackspinConsole thePanel = new BaseballBackspinConsole();
       ScenePicker thePicker = new ScenePicker(true);

      //Invoke it
      thePanel.show();

   } // end main
}//end BaseballBackspinConsole.java

