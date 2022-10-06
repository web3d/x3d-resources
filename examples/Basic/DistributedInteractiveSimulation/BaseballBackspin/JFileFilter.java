/**
 * Title:        Advanced Physically Based Modeling - Baseball
 * Description:  Modeling the effects of backspin on a fastball
 * Copyright:    Copyright (c) 2001
 * Class:		 MV4472
 */


import java.io.File;
import java.util.*;

/** 
 *	class JFileFilter
 *	A simple FileFilter class that works by filename extension, like 
 *  one in JDK demo called ExtentionFilter to be released in future 
 *  Swing.  Based on code from p 393 in Java Cookbook by Ian Darwin, 
 *  O'Reilly INC, 2001.  Used with permission.  Source for entire 
 *  text can be downloaded at http://javacook.darwinsys.com. 
 *
 *  11 December 2001
 *  Revised: 14 December 2001 by Victor Spears
 *
 *  implemented by James Harney for Cole Terrorist Attack Project.
 */
 public class JFileFilter extends javax.swing.filechooser.FileFilter
 {
    protected String description;
    protected ArrayList exts = new ArrayList();

	 /**
	  * adds a new file extension to the list of those allowable
	  * @param	s	the new allowable extension.  Once added, the 'accept' method will 
	  *				return true for files with this extension
	  */
    public void addType(String s)
    {
        exts.add(s);
    }

	 /**
	  * Tells whether the file is acceptable; either a directory or a file
	  * with an extension from the ArrayList exts (set with addType())
	  * @param	f	file to be queried
	  * @return		true if the file 'f' is a directory or has an appropriate extension
	  */
    public boolean accept(File f)
    {
        if(f.isDirectory())
        {
            return true;
        }
        else if(f.isFile())
        {
            Iterator it = exts.iterator();
            while(it.hasNext())
            {
                if(f.getName().endsWith((String)it.next()))
                {
                    return true;
                }
            }

        }
        return false;
    }

	 /**
	  * Sets the description string for the file types desired.  Has no effect on the
	  * operation of the file filter other than the getter, below
	  * @param	s	Desired description of the file types allowed
	  */
    public void setDescription(String s)
    {
        description = s;
    }

	 /**
	  * gets the description string for the file types desired.
	  * @return		the String description of the file types allowed
	  */
    public String getDescription()
    {
        return description;
    }
 }
