//
// Copyright (C) 1999-2000 Lattice Technology, Inc. & FAMOTIK LTD. All rights reserved.
//

//
// XVL3View.java
//
 
import	java.applet.*;
import	java.awt.*;
import	java.awt.event.*;

import	jp.co.lattice.vApplet.xvMain;


/**
 * @author	  created by K.Yoneyama    (99/11/01-)
 * @author	  renewed by Eishin Matsui (00/06/30-)
 */
public class XVL3View extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener {
	
	private xvMain  xmain = null;
	
	
	public void
	start()
	{
		xmain = new xvMain();
		xmain.Start( this, ( Runnable )this, ( MouseListener )this, ( MouseMotionListener )this, ( KeyListener )this );
	}

	public void
	stop()
	{
		xmain.Stop();
		xmain = null;
	}

	public void
	run()
	{
		xmain.Run( this );
	}
	
	public void
	paint( Graphics g )
	{
		xmain.Paint( this, g );
	}

	public void
	update( Graphics g )
	{
		xmain.Update( this, g );
	}

	public void
	mouseClicked( MouseEvent e )
	{
	}
	
	public void
	mousePressed( MouseEvent e )
	{
		xmain.MousePressed( e );
	}

	public void
	mouseReleased( MouseEvent e )
	{
		xmain.MouseReleased( this, e );
	}

	public void
	mouseEntered( MouseEvent e )
	{
	}

	public void
	mouseExited( MouseEvent e )
	{
	}
	
	public void
	mouseDragged( MouseEvent e )
	{
		xmain.MouseDragged( this, e );
	}
  
	public void
	mouseMoved( MouseEvent e )
	{
	}
	
	public void
	keyPressed( KeyEvent evt )
	{
		xmain.KeyPressed( this, evt );
	}

	public void
	keyReleased( KeyEvent evt )
	{
	}

	public void
	keyTyped( KeyEvent evt )
	{
	}

}
