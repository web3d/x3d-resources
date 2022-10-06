/**
 * Title:        Advanced Physically Based Modeling - Baseball
 * Description:  Modeling the effects of backspin on a fastball
 * Copyright:    Copyright (c) 2001
 * Class:		 MV4472
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Title: ScenePicker.java
 * Description: Implements a JDialog that allows the user to pick the appropriate 
 * vrml or html file to load.  Uses a system call to invoke Netscape and load 
 * the file selected.  Only tested on Netscape 4.7 with Cosmoplayer plugin 
 * loaded.  If you want to load an online file, must type url in manually. 
 * Assumes netscape is loaded on c drive, may add later capability to select 
 * it's path as well, but for now since everywhere I work it's loaded on the 
 * c drive...if you need to change just switch the browser path variable in 
 * the appropriate method below.  Looks for the vrml/html in the standard 
 * directory from the root the user is working from. Note that HTML is also 
 * put in as a default file extension since you can view the vrml from the 
 * html pages in the Savage structure.
 *
 * Date: 11 December 2001
 * Revised: 14 December 2001 by Victor Spears
 *
 * @author James Harney
 */

public class ScenePicker extends JFrame
{

    /**
     * Relative file path for the baseball diamond which displays the physics from 
	 * the Backspin class. 
     */
    public final File DIS_FILE = new File
    ("..\\BaseballBackspin.wrl");

    /**
     * Copy of the path above; no alternate case given.
     */
    public final File NO_DIS_FILE = new File
    ("..\\BaseballBackspinStandalone.wrl");

    JFileChooser myChooser;
    JFileFilter myFilter;

    /**
	 * Constructor for the class.  Instantiates and uses a JFileChoose to allow
     * the end-user to select the vrml or html file to load.  Developed since 
     * I'm too lazy to double click and search for the file in Netscape manually. 
     *
     * @param pLoadDis - whether or not we want the default file shown on the 
     * file chooser to be the DIS-VRML file or the reconstruction VRML file.
     */
    public ScenePicker(boolean pLoadDis)
    {

        myChooser = new JFileChooser();
        //File filter from JFileFilter.java used to set the default types 
        //the end user will see when choosing a file.
        myFilter = new JFileFilter();

        //The vrml file extension
        myFilter.addType("wrl");

        //The xml file extension
        myFilter.addType("xml");

        //The x3d file extension
        myFilter.addType("x3d");

        //HTML file extension
        myFilter.addType("html");
        myFilter.setDescription("VRML (.wrl), X3D/XML and .html files");

        //Add them to the JFileChooser
        myChooser.addChoosableFileFilter(myFilter);
        this.getContentPane().add(myChooser);
        //If the end user wants the DIS-VRML file
        if(pLoadDis)
        {
            myChooser.setSelectedFile(DIS_FILE);
        }
        //Otherwise they want the reconstruction, note doesn't load, just
        //dynamically changes the default file they see when the JFileChooser
        //is loaded to help them pick the correct file
        else
        {
            myChooser.setSelectedFile(NO_DIS_FILE);
        }

        //Hide the other extensions from them
        myChooser.setFileHidingEnabled(true);


        int returnVal = myChooser.showOpenDialog(this.getContentPane());

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            setState(ICONIFIED);

            System.out.println("Chose file named: " +
                    myChooser.getSelectedFile().getPath());

            //Load the file
            loadVrmlFile(myChooser.getSelectedFile().getPath());

        }
        else
        {
            System.out.println("Didn't Choose a file");
        }
    }

    /**
	 * Loads the appropriate VRML file for interaction with the simulation 
     * based on argument passed.
     *
     * @param pFilePath - file path of the vrml file to load in Netscape
     */
    public void loadVrmlFile(String pFilePath)
    {
        String browserPath = "c:/Program Files/Netscape/Communicator/Program/netscape.exe ";
        try
        {
            Runtime.getRuntime().exec(browserPath + pFilePath);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }

    //Overriden so we can exit on System Close
    protected void processWindowEvent(WindowEvent e)
    {
        //super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
          System.exit(0);
        }
    }

    /**
     * Main for class in case you want to run separately.
     */
    public static void main(String[] args)
    {
        ScenePicker myPicker = new ScenePicker(true);
        myPicker.setVisible(true);
    }
}
