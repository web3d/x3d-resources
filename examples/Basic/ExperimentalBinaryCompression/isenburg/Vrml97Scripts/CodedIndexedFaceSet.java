// **********************************************************************
// 
//	Class Name:	CodedIndexedFaceSet.java
//
// 	Description:	the decoder for an ASCII coded IndexedFaceSet
//	Version:
//		+ v2.1 may 2003 - fixed all bugs (while waiting at ORD airport)
//		+ v2.0 apr 2003 - ported from Shout3D's custom_node mechanism
//		      to VRML97 EXTERN PROTO style (at I3D'03 in Monterey)
//		+ v1.0 feb 2002 - fixed some bugs
//		+ v0.0 sep 2001 - first release
//	(C) 2002 - martin isenburg@cs.unc.edu - all rights reserved
//
// **********************************************************************

// **********************************************************************
//   IMPORT
// **********************************************************************

// Java JSAI classes for VRML
import vrml.*;
import vrml.field.*;
import vrml.node.*;

// decoder imports
import java.util.Stack;

// **********************************************************************
//   CLASS
// **********************************************************************

public class CodedIndexedFaceSet extends Script 
{
	public static boolean DEBUG = true;

	private MFInt32 code = null;
	private SFFloat pos = null;
	private SFFloat tex = null;

	private SFNode IFS; // field == initializeOnly

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public String getName() { return "CodedIndexedFaceSet"; }

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public void initialize ()
	{
		try {
			if (DEBUG) System.out.println ("");
			if (DEBUG) System.out.println ("initializing CodedIndexedFaceSet - START (MI: should work now)");

			code = (MFInt32) getField ("code"); // instantiate
			pos = (SFFloat) getField ("pos"); // instantiate
			tex = (SFFloat) getField ("tex"); // instantiate

			if (DEBUG) System.out.println ("code array length = " + code.getSize());
			if (DEBUG) System.out.println ("pos = " + pos.toString());
			if (DEBUG) System.out.println ("tex = " + tex.toString());

			IFS = (SFNode) getField ("IFS"); // instantiate
			if (DEBUG) System.out.println ("IFS = " + IFS.toString());

			SFNode temp = null;
			temp = (SFNode)((Node)IFS.getValue()).getExposedField("coord");
			if (temp == null || temp.getValue() == null)
			{
				coord = null;
				if (DEBUG) System.out.println ("no coordinates ... exiting");
				return;
			}
			else
			{
				if (DEBUG) System.out.println ("temp = " + temp.toString());
				coord = (MFVec3f)((Node)temp.getValue()).getExposedField("point");
				if (DEBUG) System.out.println ("coord length = " + coord.getSize());
			}
			
			temp = (SFNode)((Node)IFS.getValue()).getExposedField("texCoord");
			if (DEBUG) System.out.println ("temp = " + temp.toString());
			if (temp == null || temp.getValue() == null)
			{
				texCoord = null;
				if (DEBUG) System.out.println ("no texture coordinates");
			}
			else
			{
				if (DEBUG) System.out.println ("temp = " + temp.toString());
				texCoord = (MFVec2f)((Node)temp.getValue()).getExposedField("point");
				if (DEBUG) System.out.println ("texCoord length = " + texCoord.getSize());
			}

			set_coordIndex = (MFInt32)((Node)IFS.getValue()).getEventIn("set_coordIndex");
			set_texCoordIndex = (MFInt32)((Node)IFS.getValue()).getEventIn("set_texCoordIndex");

			if (DEBUG) System.out.println ("set_coordIndex = " + set_coordIndex.toString());
			if (DEBUG) System.out.println ("set_texCoordIndex = " + set_texCoordIndex.toString());

			//	 if (DEBUG) System.out.println ("coord = " + coord.ge());
			//	 if (DEBUG) System.out.println ("texCoord = " + texCoord.toString());

			if (DEBUG) System.out.println ("decoding ...");
			decode();
			if (DEBUG) System.out.println ("done.");

			if (DEBUG) System.out.println("");

		}
		catch (Exception e)
		{
			System.err.println ("Exception caught in Script node initialize method:");
			System.err.println (e);
			e.printStackTrace();
		}
	}

	private void decode()
	{
		code_words = new int[code.getSize()];
		code.getValue(code_words);
		code.clear();
		
		decodePositions(pos.getValue());

		if (texCoord != null && texCoord.getSize() > 0)
		{
		  decodeTexCoords(tex.getValue());

		  if ( (texCoord.getSize()/2) != (coord.getSize()/3) )
		  {
		  	texindices = new int[code_words[0]];
		  }
			else
			{
				if (DEBUG) System.out.println("TRACE: per vertex texCoord mapping");
		 	}
		}

		indices = new int[code_words[0]];

		decodeIndices();

		set_coordIndex.setValue(indices);
		if (texCoord != null && texCoord.getSize() > 0)
		{
			set_texCoordIndex.setValue(texindices);
		}

		indices = null;
		texindices = null;
		code_words = null;
	}

	private void decodeIndices()
	{
		int code;
		int v0, v1;
		TwinEdge run;
		TwinEdge gate;
		TwinEdge newedge;

		Stack gates = new Stack();

		do 
		{
		  gate = null;
		  do // 0 = right, 1 = left, 2 = split, 3 = end, 4 = merge, 5++ = degree n-2 face
		  {
		    code = code_words[code_count++];
		    if (code > 4) // face or hole
		    {
		      run = gate.boundary_prev;
		      run.inv.next = gate;
		      while (code > 5)
		      {
			      run = run.boundary_prev;
			      run.inv.next = run.boundary_next;
			      code--;
		      }
		      newedge = createTwinEdge(run.position, gate.inv.position);
		      run.boundary_prev.boundary_next = newedge;
		      newedge.boundary_prev = run.boundary_prev;
		      gate.boundary_next.boundary_prev = newedge;
		      newedge.boundary_next = gate.boundary_next;
		      newedge.next = run;
		      gate.inv.next = newedge.inv;
		      if (code_words[code_count] != -1) // face
		      {
			      if (texindices == null)
			      {
				      do
				      {
					      indices[index_count++] = gate.position;
					      gate = gate.boundary_prev;
				      } while (gate.boundary_next != newedge);
				      indices[index_count++] = newedge.inv.position;
			      }
			      else
			      {
				      indices[index_count] = gate.inv.position;
				      gate.inv.texcoord = index_count++;
				      gate = gate.boundary_prev;
				      do
				      {
					      indices[index_count] = gate.inv.position;
					      gate.inv.texcoord = index_count++;
					      decodeTexCoordBinding(gate.inv);
					      gate = gate.boundary_prev;
				      } while (gate.boundary_next != newedge);
				      indices[index_count] = newedge.position;
				      newedge.texcoord = index_count++;
				      texindices[index_count] = -1;
			      }
			      indices[index_count++] = -1;
		      }
		      else // hole
		      {
			      code_count++;
			      if (texindices != null)
			      {
				      gate = gate.boundary_prev;
				      do
				      {
					      decodeTexCoordBinding(gate.inv);
					      gate = gate.boundary_prev;
				      } while (gate.boundary_next != newedge);
			      }
		      }
		      gate = newedge;
		    }
		    else if (code == 0) // label R
		    {
		      v0 = getPositionIndex(-1);
		      newedge = createTwinEdge(gate.inv.position, v0);

		      newedge.boundary_prev = gate;
		      newedge.boundary_next = newedge.inv;
		      newedge.inv.boundary_prev = newedge;
		      newedge.inv.boundary_next = gate.boundary_next;
		      gate.boundary_next.boundary_prev = newedge.inv;
		      gate.boundary_next = newedge;
		      gate = newedge;
		    }
		    else if (code == 1) // label L
		    {
		      v0 = getPositionIndex(-1);
		      newedge = createTwinEdge(v0, gate.position);

		      newedge.boundary_prev = newedge.inv;
		      newedge.boundary_next = gate;
		      newedge.inv.boundary_prev = gate.boundary_prev;
		      newedge.inv.boundary_next = newedge;
		      gate.boundary_prev.boundary_next = newedge.inv;
		      gate.boundary_prev = newedge;
		      gate = newedge;
		    }
		    else if (code == 2) // label S
		    {									 
		      TwinEdge stackgate = (TwinEdge)gates.pop();
		      
		      newedge = createTwinEdge(stackgate.inv.position, gate.position);

		      stackgate.boundary_next.boundary_prev = newedge.inv;
		      newedge.inv.boundary_next = stackgate.boundary_next;

		      gate.boundary_prev.boundary_next = newedge.inv;
		      newedge.inv.boundary_prev = gate.boundary_prev;
		      
		      stackgate.boundary_next = newedge;
		      newedge.boundary_prev = stackgate;

		      gate.boundary_prev = newedge;
		      newedge.boundary_next = gate;
		      
		      gate = newedge;
		    }
		    else if (code == 3) // E
		    {
		      if (gate != null)
		      {
			      gates.push(gate);
		      }
		      
		      v0 = getPositionIndex(-1);
		      v1 = getPositionIndex(-2);
		      gate = createTwinEdge(v0, v1);

		      gate.boundary_prev = gate.inv;
		      gate.boundary_next = gate.inv;
		      gate.inv.boundary_prev = gate;
		      gate.inv.boundary_next = gate;
		    }
		    else // M
		    {
		      int index = code_words[code_count++];
		      int offset1 = code_words[code_count++];
		      int offset2 = offset1 + code_words[code_count++] - 1;
		      
		      run = gate;
		      while (offset2 > 0)
		      {
			      run = run.boundary_prev;
			      offset2--;
		      }
		      newedge = createTwinEdge(run.position, gate.inv.position);
		      
		      run.boundary_prev.boundary_next = newedge;
		      newedge.boundary_prev = run.boundary_prev;
		      
		      gate.boundary_next.boundary_prev = newedge;
		      newedge.boundary_next = gate.boundary_next;
		      
		      run.boundary_prev = newedge.inv;
		      newedge.inv.boundary_next = run;
		      
		      gate.boundary_next = newedge.inv;
		      newedge.inv.boundary_prev = gate;
		      
		      run = gate;
		      while (offset1 > 0)
		      {
			      run = run.boundary_prev;
			      offset1--;
		      }
		      gates.insertElementAt(run, index);
		      gate = newedge;
		    }
		  } while (gate.boundary_prev != gate.boundary_next || gate.inv == gate.boundary_next);
		  
		  if (texindices != null)
		  {
		    run = gate;
		    while (run.next.next != null) run = run.next;
		    run.next = gate;
		    run = gate.boundary_prev;
		    while (run.next.next != null) run = run.next;
		    run.next = gate.boundary_prev;
		    decodeTexCoordBinding(gate.boundary_prev);
		    decodeTexCoordBinding(gate);
		  }
		} while (code_words[code_count] != 2);
	}

	private void decodeTexCoordBinding(TwinEdge edge)
	{
		TwinEdge run = edge.next.next;
		while (run != edge)
		{
		  if (run == null)
		  {
		    return;
		  }
		  run = run.next;
		}

		if (code_words[code_count++] == 0) // 0 is smooth and 6 is not
		{
		  int texcoord = getTexCoordIndex(-1);
		  do
		  {
		    if (run.texcoord != -1)
		    {
		      texindices[run.texcoord] = texcoord;
		    }
		    run = run.next;
		  } while (run != edge);
		}
		else
		{
		  int counter = 0;
		  int texcoord = -1;
		  do
		  {
		    if (run.texcoord != -1)
		    {
		      if (code_words[code_count++] == 0) // 0 is smooth and 6 is not
		      {
			      if (texcoord != -1)
			      {
				      texindices[run.texcoord] = texcoord;
			      }
			      else
			      {
				      counter++;
			      }
		      }
		      else
		      {
			      texcoord = getTexCoordIndex(-1);
			      texindices[run.texcoord] = texcoord;
		      }
		    }
		    run = run.next;
		  } while (run != edge);
		  while (counter > 0)
		  {
		    if (run.texcoord != -1)
		    {
		      texindices[run.texcoord] = texcoord;
		    }
		    counter--;
		    run = run.next;			 
		  }
		}
	}
	      
	private int getPositionIndex(int non_manifold)
	{
	  if (code_words[code_count] == non_manifold)
	  {
	    code_count++;
	    return code_words[code_count++];
	  }
	  else
	  {
	    return position_count++;
	  }
	}
	      
	private int getTexCoordIndex(int non_manifold)
	{
    if (code_words[code_count] == non_manifold)
    {
      code_count++;
      return code_words[code_count++];
    }
    else
    {
      return texcoord_count++;
    }
	}

	private TwinEdge createTwinEdge(int v0, int v1)
	{
    TwinEdge e0 = new TwinEdge();
    TwinEdge e1 = new TwinEdge();
    e0.position = v0;
    e1.position = v1;
    e0.inv = e1;
    e1.inv = e0;
    return e0;
	}

	private void decodePositions(float pos_mult)
	{
    int counter;
    float position[] = new float[coord.getSize()*3];
    coord.getValue(position);
    counter = position.length-4;
    while (counter >= 0)
    {
      position[counter] = position[counter+3] + pos_mult * position[counter];
      counter--;
    }
    coord.setValue(position);
	}

	private void decodeTexCoords(float tex_mult)
	{
    int counter;
    float texcoord[] = new float[texCoord.getSize()*2];
    texCoord.getValue(texcoord);
    counter = texcoord.length-3;
    while (counter >= 0)
    {
      texcoord[counter] = texcoord[counter+2] + tex_mult * texcoord[counter];
      counter--;
    }
		texCoord.setValue(texcoord);
	} 

	int code_count = 1;
	int code_words[] = null;

	int index_count = 0;
	int indices[] = null;
	int texindices[] = null;

	MFInt32 set_coordIndex = null;
	MFInt32 set_texCoordIndex = null;

	MFVec3f coord = null;
	MFVec2f texCoord = null;

	int position_count = 0; 
	int texcoord_count = 0;
	
	class TwinEdge extends Object
	{
		TwinEdge next = null;
		TwinEdge inv  = null;

		TwinEdge boundary_prev = null;
		TwinEdge boundary_next = null;
			
		int position = -1;
		int texcoord = -1; 
	}
}
