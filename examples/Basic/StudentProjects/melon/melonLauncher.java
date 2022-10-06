package melonlauncher;

import javax.swing.UIManager;
import java.awt.*;

/**
 * Title:        Melon Launcher
 * Description:  Physics simulation of shooting a watermelon out of an air cannon.
 * Copyright:    Copyright (c) 2001
 * Company:      CS2973/MV4473
 * @author Andrew Wiest
 * @version 1.0
 */

public class melonLauncher {
  boolean packFrame = false;

  /**Construct the application*/
  public melonLauncher() {
    Framemain frame = new Framemain();
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }
  /**Main method*/
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new melonLauncher();
    System.out.println("inside main");

  }
}