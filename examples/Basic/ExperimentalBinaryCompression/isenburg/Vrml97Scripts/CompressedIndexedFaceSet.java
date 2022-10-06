// **********************************************************************
// 
//	Class Name:	CompressedIndexedFaceSet.java
//
// 	Description:	the decoder for an ASCII compressed IndexedFaceSet
//	Version:
//		+ v1.0 may 2003 - ported from Shout3D's custom_nodes mechanism
//		      to VRML97 EXTERN PROTO style (at home on my sofa after I3D)
//		+ v0.0 nov 2002 - first release
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
import java.util.Vector;
import java.util.Stack;

//	no import needed, .class code located in local directory
//	import ArithmeticDecoder;
//	import ArithmeticModel;
//	import PositionQuantizer;
//	import TexcoordQuantizer;

public class CompressedIndexedFaceSet extends Script 
{
	public static boolean DEBUG = false;

	private SFString code = null;
	private MFFloat box = null;
	private SFInt32 bits = null;
	private MFFloat box_tex = null;
	private SFInt32 bits_tex = null;

	private SFNode IFS; // field == initializeOnly

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public String getName() { return "CompressedIndexedFaceSet"; }

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

	public void initialize ()
	{
		try
		{
			if (DEBUG) System.out.println ("");
			if (DEBUG) System.out.println ("initializing CompressedIndexedFaceSet - START");

			code = (SFString) getField ("code"); // instantiate
			box = (MFFloat) getField ("box"); // instantiate
			bits = (SFInt32) getField ("bits"); // instantiate
			box_tex = (MFFloat) getField ("box_tex"); // instantiate
			bits_tex = (SFInt32) getField ("bits_tex"); // instantiate

			if (DEBUG) System.out.println ("code string length = " + code.getValue().length());
			if (DEBUG) System.out.println ("box = " + box.toString());
			if (DEBUG) System.out.println ("bits = " + bits.toString());
			if (DEBUG) System.out.println ("box_tex = " + box_tex.toString());
			if (DEBUG) System.out.println ("bits_tex = " + bits_tex.toString());

			IFS = (SFNode) getField ("IFS"); // instantiate
			if (DEBUG) System.out.println ("IFS = " + IFS.toString());

			SFNode temp = null;
			temp = (SFNode)((Node)IFS.getValue()).getExposedField("coord");
			if (temp == null)
			{
				if (DEBUG) System.out.println ("ERROR: IFS has no coord field ... exiting");
				return;
			}
			else
			{
				if (temp.getValue() == null)
				{
					if (DEBUG) System.out.println ("coord field value is null ... creating Coordinate node");
					BaseNode mist[] = getBrowser().createVrmlFromString("Coordinate { point [ ] }");  
					temp.setValue(mist[0]);
				} 
				coord = (MFVec3f)((Node)temp.getValue()).getExposedField("point");
				if (DEBUG) System.out.println ("coord length = " + coord.getSize());
			}

			if (bits_tex.getValue() == 0)
			{
				texCoord = null;
			}
			else
			{
				temp = (SFNode)((Node)IFS.getValue()).getExposedField("texCoord");
				if (temp == null)
				{
					if (DEBUG) System.out.println ("ERROR: IFS has no texCoord field ... exiting");
					return;
				}
				else
				{
					if (temp.getValue() == null)
					{
						if (DEBUG) System.out.println ("texCoord field value is null ... creating TextureCoordinate node");
						BaseNode mist[] = getBrowser().createVrmlFromString("TextureCoordinate { point [ ] }");  
						temp.setValue(mist[0]);
					} 
					texCoord = (MFVec2f)((Node)temp.getValue()).getExposedField("point");
					if (DEBUG) System.out.println ("texCoord length = " + texCoord.getSize());
				}
			}
			
			set_coordIndex = (MFInt32)((Node)IFS.getValue()).getEventIn("set_coordIndex");
			set_texCoordIndex = (MFInt32)((Node)IFS.getValue()).getEventIn("set_texCoordIndex");

			if (DEBUG) System.out.println ("set_coordIndex = " + set_coordIndex.toString());
			if (DEBUG) System.out.println ("set_texCoordIndex = " + set_texCoordIndex.toString());

			if (DEBUG) System.out.println ("decompressing ...");
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

	private final static char bits2base[] = {
	// 0   1   2   3   4   5   6   7
		'A','B','C','D','E','F','G','H', // 0
		'I','J','K','L','M','N','O','P', // 1
		'Q','R','S','T','U','V','W','X', // 2
		'Y','Z','a','b','c','d','e','f', // 3
		'g','h','i','j','k','l','m','n', // 4
		'o','p','q','r','s','t','u','v', // 5
		'w','x','y','z','0','1','2','3', // 6
		'4','5','6','7','8','9','+','/'  // 7
	};

	private int[] decodeBase64()
	{
		int i,j,k;
		String string = code.getValue();
//		code.setValue(null);
		int uuStart;
		int uuRest = (int)string.charAt(0) - (int)'A';
		int uuFull = string.length();
		int bits[] = null;
		int numBits = 0;
	
		if (uuRest > 0)
		{
			uuStart = 2;
			bits = new int[(uuFull-2)*6+uuRest];
			
			char uu = string.charAt(1);
			for (j = 0; j < 64; j++)
			{
				if (uu == bits2base[j])
				{
					for (k = 0; k < uuRest; k++)
					{
						if ((j & 1) == 1)
						{
							bits[numBits++] = 1;
						}
						else
						{
							bits[numBits++] = 0;
						}
						j = j>>1;
					}
					break;
				}
			}
		}
		else
		{
			uuStart = 1;
			bits = new int[(uuFull-1)*6];
		}
	
		for (i = uuStart; i < uuFull; i++)
		{
			char uu = string.charAt(i);
			for (j = 0; j < 64; j++)
			{
				if (uu == bits2base[j])
				{
					for (k = 0; k < 6; k++)
					{
						if ((j & 1) == 1)
						{
							bits[numBits++] = 1;
						}
						else
						{
							bits[numBits++] = 0;
						}
						j = j>>1;
					}
					break;
				}
			}
		}		
		return bits;
	}

	public void decode()
	{
		// create the Arithmetic decoders
		
		int bitArray[] = decodeBase64();
			
		ad_conn = new ArithmeticDecoder(bitArray, bitArray.length);
		ad_geom = ad_conn;
		if (texCoord != null)
		{
			ad_texmap = ad_conn;
			ad_texval = ad_conn;
		}

		// probability table for initialization
		int[] table = new int[9];
		
		// create face degree probability tables
		table[0] = ad_conn.decode(2); table[1] = ad_conn.decode(2); table[2] = ad_conn.decode(2); table[3] = ad_conn.decode(2);
		amFaceDegree0 = new ArithmeticModel(4,true,table); // 0: deg3, 1: deg4, 2: deg5, 3: deg6 (or bigger)
		amFaceDegree1 = new ArithmeticModel(4,true,table); // 0: deg3, 1: deg4, 2: deg5, 3: deg6 (or bigger)
		amFaceDegree2 = new ArithmeticModel(4,true,table); // 0: deg3, 1: deg4, 2: deg5, 3: deg6 (or bigger)
		amFaceDegree3 = new ArithmeticModel(4,true,table); // 0: deg3, 1: deg4, 2: deg5, 3: deg6 (or bigger)
		amFaceDegreeLarge = new ArithmeticModel(8,true,null);

		// create vertex degree probability tables
		table[0] = ad_conn.decode(2); table[1] = ad_conn.decode(2); table[2] = ad_conn.decode(2); table[3] = ad_conn.decode(2); table[4] = ad_conn.decode(2); table[5] = ad_conn.decode(2); table[6] = ad_conn.decode(2); table[7] = ad_conn.decode(2); table[8] = ad_conn.decode(2); 
		amVertexDegree0 = new ArithmeticModel(9,true,table); // 0: deg2, 1: deg3, 2: deg4, 3: deg5, 4: deg6, 5: deg7, 6: deg8, 7:deg9, 8:deg10, 9:deg11 (or bigger)
		amVertexDegree1 = new ArithmeticModel(9,true,table); // 0: deg2, 1: deg3, 2: deg4, 3: deg5, 4: deg6, 5: deg7, 6: deg8, 7:deg9, 8:deg10, 9:deg11 (or bigger)
		amVertexDegree2 = new ArithmeticModel(9,true,table); // 0: deg2, 1: deg3, 2: deg4, 3: deg5, 4: deg6, 5: deg7, 6: deg8, 7:deg9, 8:deg10, 9:deg11 (or bigger)
		amVertexDegree3 = new ArithmeticModel(9,true,table); // 0: deg2, 1: deg3, 2: deg4, 3: deg5, 4: deg6, 5: deg7, 6: deg8, 7:deg9, 8:deg10, 9:deg11 (or bigger)
		amVertexDegreeLarge = new ArithmeticModel(8,true,null);

		// create other connectivity probability tables
		table[0] = 100; table[1] = ad_conn.decode(2); 
		amPolygonOrHole = new ArithmeticModel(2,true,table); // 0: face, 1: hole
		table[0] = 100; table[1] = ad_conn.decode(2); 
		amPositionManifoldOrNot = new ArithmeticModel(2,true,table); // 0: manifold, 1: not manifold
		table[0] = 100; table[1] = 1; 
		amAddOrSplitMerge = new ArithmeticModel(2,true,table); // 0: add, 1: split or merge
		table[0] = 20; table[1] = 1; 
		amSplitOrMerge = new ArithmeticModel(2,true,table); // 0: split, 1: merge		

		if (texCoord != null)
		{
			// create vertex and corner bits probability tables
			table[0] = ad_texmap.decode(2); table[1] = 1; 	
			amVertexBit0 = new ArithmeticModel(2,true,table);
			amVertexBit1 = new ArithmeticModel(2,true,table);
			amCornerBit0 = new ArithmeticModel(2,true,null);
			amCornerBit1 = new ArithmeticModel(2,true,null);
			amCornerBit2 = new ArithmeticModel(2,true,null);
			amCornerBit3 = new ArithmeticModel(2,true,null);
			amCornerBit4 = new ArithmeticModel(2,true,null);
			amCornerBit5 = new ArithmeticModel(2,true,null);
			amCornerBit6 = new ArithmeticModel(2,true,null);
			table[0] = 10; table[1] = ad_texmap.decode(2); 	
			amTexcoordManifoldOrNot = new ArithmeticModel(2,true,table); // 0: manifold, 1: not manifold
		}

		// create vectors
		faces = new Vector();
		boundaries = new Stack();
		if (texCoord != null)
		{
			rings = new Vector();
		}

		// create position quantizer
		float b_box[] = new float[6];
		box.getValue(b_box);
		
		pq = new PositionQuantizer(bits.getValue(), b_box);

		// init bit plane usage
		bitsUsed = new int[5];	
		if (bits.getValue() == 18)
		{
			bitsUsed[0] = 7;
			bitsUsed[1] = 4;
			bitsUsed[2] = 3;
			bitsUsed[3] = 2;
			bitsUsed[4] = 1;
		}
		else if (bits.getValue() == 16)
		{
			bitsUsed[0] = 6;
			bitsUsed[1] = 3;
			bitsUsed[2] = 3;
			bitsUsed[3] = 2;
			bitsUsed[4] = 1;
		}
		else if (bits.getValue() == 14)
		{
			bitsUsed[0] = 5;
			bitsUsed[1] = 3;
			bitsUsed[2] = 2;
			bitsUsed[3] = 2;
			bitsUsed[4] = 1;
		}
		else if (bits.getValue() == 12)
		{
			bitsUsed[0] = 5;
			bitsUsed[1] = 3;
			bitsUsed[2] = 2;
			bitsUsed[3] = 1;
			bitsUsed[4] = 0;
		}
		else if (bits.getValue() == 10)
		{
			bitsUsed[0] = 5;
			bitsUsed[1] = 3;
			bitsUsed[2] = 1;
			bitsUsed[3] = 0;
			bitsUsed[4] = 0;
		}
		else if (bits.getValue() == 8)
		{
			bitsUsed[0] = 4;
			bitsUsed[1] = 2;
			bitsUsed[2] = 1;
			bitsUsed[3] = 0;
			bitsUsed[4] = 0;
		}
		else if (bits.getValue() == 6)
		{
			bitsUsed[0] = 3;
			bitsUsed[1] = 2;
			bitsUsed[2] = 0;
			bitsUsed[3] = 0;
			bitsUsed[4] = 0;
		}
		else if (bits.getValue() == 4)
		{
			bitsUsed[0] = 3;
			bitsUsed[1] = 0;
			bitsUsed[2] = 0;
			bitsUsed[3] = 0;
			bitsUsed[4] = 0;
		}
		
		// create geometry probability tables
		amSign = new ArithmeticModel(2,true,null);
		amCenterBits = new ArithmeticModel[5];
		amLastBits = new ArithmeticModel[5];
		amAcrossBits = new ArithmeticModel[5];
		amWithinBits = new ArithmeticModel[5];
		for (int i = 0; i < 5; i++)
		{
			if (bitsUsed[i] != 0)
			{
				amCenterBits[i] = new ArithmeticModel((1 << bitsUsed[i]),true,null);
				amLastBits[i] = new ArithmeticModel((1 << bitsUsed[i]),true,null);
				amAcrossBits[i] = new ArithmeticModel((1 << bitsUsed[i]),true,null);
				amWithinBits[i] = new ArithmeticModel((1 << bitsUsed[i]),true,null);
			}
			else
			{
				amCenterBits[i] = null;
				amLastBits[i] = null;
				amAcrossBits[i] = null;
				amWithinBits[i] = null;
			}			
		}

		if (texCoord != null)
		{
			float b_box_tex[] = new float[4];
			box_tex.getValue(b_box_tex);
			
			// create texcoord quantizer
			tq = new TexcoordQuantizer(bits_tex.getValue(), b_box_tex);

			// init bit plane usage
			bitsUsedTex = new int[5];	
			if (bits_tex.getValue() == 16)
			{
				bitsUsedTex[0] = 6;
				bitsUsedTex[1] = 3;
				bitsUsedTex[2] = 3;
				bitsUsedTex[3] = 2;
				bitsUsedTex[4] = 1;
			}
			else if (bits_tex.getValue() == 14)
			{
				bitsUsedTex[0] = 5;
				bitsUsedTex[1] = 3;
				bitsUsedTex[2] = 2;
				bitsUsedTex[3] = 2;
				bitsUsedTex[4] = 1;
			}
			else if (bits_tex.getValue() == 12)
			{
				bitsUsedTex[0] = 5;
				bitsUsedTex[1] = 3;
				bitsUsedTex[2] = 2;
				bitsUsedTex[3] = 1;
				bitsUsedTex[4] = 0;
			}
			else if (bits_tex.getValue() == 10)
			{
				bitsUsedTex[0] = 5;
				bitsUsedTex[1] = 3;
				bitsUsedTex[2] = 1;
				bitsUsedTex[3] = 0;
				bitsUsedTex[4] = 0;
			}
			else if (bits_tex.getValue() == 8)
			{
				bitsUsedTex[0] = 4;
				bitsUsedTex[1] = 2;
				bitsUsedTex[2] = 1;
				bitsUsedTex[3] = 0;
				bitsUsedTex[4] = 0;
			}
			else if (bits_tex.getValue() == 6)
			{
				bitsUsedTex[0] = 3;
				bitsUsedTex[1] = 2;
				bitsUsedTex[2] = 0;
				bitsUsedTex[3] = 0;
				bitsUsedTex[4] = 0;
			}
			else if (bits_tex.getValue() == 4)
			{
				bitsUsedTex[0] = 3;
				bitsUsedTex[1] = 0;
				bitsUsedTex[2] = 0;
				bitsUsedTex[3] = 0;
				bitsUsedTex[4] = 0;
			}
			
			// create texval probability tables
			amSignTex = new ArithmeticModel(2,true,null);
			amWithinBitsTex = new ArithmeticModel[5];
			amAcrossBitsTex = new ArithmeticModel[5];
			amNearbyBitsTex = new ArithmeticModel[5];
			amCenterBitsTex = new ArithmeticModel[5];
			for (int i = 0; i < 5; i++)
			{
				if (bitsUsedTex[i] != 0)
				{
					amWithinBitsTex[i] = new ArithmeticModel((1 << bitsUsedTex[i]),true,null);
					amAcrossBitsTex[i] = new ArithmeticModel((1 << bitsUsedTex[i]),true,null);
					amNearbyBitsTex[i] = new ArithmeticModel((1 << bitsUsedTex[i]),true,null);
					amCenterBitsTex[i] = new ArithmeticModel((1 << bitsUsedTex[i]),true,null);
				}
				else
				{
					amWithinBitsTex[i] = null;
					amAcrossBitsTex[i] = null;
					amNearbyBitsTex[i] = null;
					amCenterBitsTex[i] = null;
				}			
			}
		}

		// for statistics only
		non_manifold_position_count = 0;

		// create space for the positions
		positions = new int[10000*3];
		position_alloc = 10000;
		position_count = 0;
		
		if (texCoord != null)
		{
			// for statistics only
			non_manifold_texcoord_count = 0;

			texcoords = new int[10000*2];
			texcoord_alloc = 10000;
			texcoord_count = 0;
		}

		// decode
		decodeEverything();

		if (DEBUG) System.out.print("number of positions: "+ position_count);
		if (non_manifold_position_count > 0)
		{
			if (DEBUG) System.out.println(" (with "+ non_manifold_position_count + " non-manifold uses)");		
		}
		else
		{
			if (DEBUG) System.out.println();
		}

		if (texCoord != null)
		{
			if (DEBUG) System.out.print("number of texcoords: "+ texcoord_count);
			if (non_manifold_texcoord_count > 0)
			{
				if (DEBUG) System.out.println(" (with "+ non_manifold_texcoord_count + " non-manifold uses)");		
			}
			else
			{
				if (DEBUG) System.out.println();
			}
		}

		// create coordinates
		float[] final_positions = new float[position_count*3];
		
		pq.DeQuantize(positions, final_positions, position_count);
		coord.setValue(final_positions);
		
		if (texCoord != null)
		{
			float[] final_texcoords = new float[texcoord_count*2];

			tq.DeQuantize(texcoords, final_texcoords, texcoord_count);
			texCoord.setValue(final_texcoords);
		}
		
		// garbage collection
	 	positions = null;
	 	ad_conn = null;
		ad_geom = null;
		faces = null;
		boundaries = null;
		amFaceDegree0 = null;
		amFaceDegree1 = null;
		amFaceDegree2 = null;
		amFaceDegree3 = null;
		amFaceDegreeLarge = null;
		amVertexDegree0 = null;
		amVertexDegree1 = null;
		amVertexDegree2 = null;
		amVertexDegree3 = null;
		amVertexDegreeLarge = null;
		amPolygonOrHole = null;
		amPositionManifoldOrNot = null;
		amAddOrSplitMerge = null;
		amSplitOrMerge = null;
		amSign = null;
		amCenterBits = null;		
		amLastBits = null;		
		amAcrossBits = null;		
		amWithinBits = null;		
		pq = null;
		
		if (texCoord != null)
		{
			texcoords = null;
			ad_texmap = null;
			ad_texval = null;
			rings = null;
			amVertexBit0 = null;
			amVertexBit1 = null;
			amCornerBit0 = null;
			amCornerBit1 = null;
			amCornerBit2 = null;
			amCornerBit3 = null;
			amCornerBit4 = null;
			amCornerBit5 = null;
			amCornerBit6 = null;
			amPositionManifoldOrNot = null;
			amSign = null;
			amWithinBitsTex = null;		
			amAcrossBitsTex = null;		
			amNearbyBitsTex = null;		
			amCenterBitsTex = null;		
			tq = null;
		}
	}
	
	private void decodeEverything()
	{
		int v0, v1, C=-1;
		int degree;
		int counter;
		int min_degree;
		int sum_v_degree;
		int boundary_slots = 0;
		int remaining_slots;
		int index_count = 0;
		boolean is_polygon;
		Boundary boundary;
		TwinEdge gate;
		TwinEdge run;
		TwinEdge slide;
		TwinEdge boundary_prev;
		
		while (ad_conn.hasBits() || ad_geom.hasBits())
		{
			v0 = getPositionCenter();
			v1 = getPositionLast(v0);
			
			gate = createTwinEdge(v0, v1);

			gate.boundary_prev = gate.inv;
			gate.boundary_next = gate.inv;
			gate.inv.boundary_prev = gate;
			gate.inv.boundary_next = gate;

			gate.v_degree = getVertexDegree(4);
			if (texCoord != null)
			{
				rings.addElement(gate);
			}

			gate.inv.v_degree = getVertexDegree(4);
			if (texCoord != null)
			{
				rings.addElement(gate.inv);
			}

			gate.slots = gate.v_degree - 1;
			gate.inv.slots = gate.inv.v_degree - 1;
			
			boundaries.push(new Boundary(gate, gate.slots + gate.inv.slots));
			
			while (boundaries.isEmpty() == false)
			{
				boundary = (Boundary)boundaries.pop();
				gate = boundary.gate;
				boundary_slots = boundary.slots;
				while (true)
				{
					gate = best_gate(gate);

					slide = gate;
					gate = gate.boundary_next;

					// is it a face or a hole
					is_polygon = (ad_conn.decode(amPolygonOrHole) == 0); // 0 is face and 1 is hole

					// can we trivially include part or all of a face
					min_degree = 2;
					sum_v_degree = gate.v_degree + slide.v_degree;
					while (slide.slots == 0 && slide != gate)
					{
						min_degree++;
						slide.next = slide.boundary_prev.inv;
						C = slide.inv.position;
						slide = slide.boundary_prev;
						sum_v_degree += slide.v_degree;
					}
					if (slide == gate)
					{
						slide.next = slide.boundary_prev.inv;
						if (is_polygon)
						{
							faces.addElement(slide);
							index_count += min_degree;
						}
						break; // face trivially included . boundary loop ends
					}

					// otherwise this face uses up two boundary degrees
					boundary_slots -= 2;
					// how many degrees will be left at the vertex we work on
					remaining_slots = slide.slots - 1;
										
					// what is the degree of this face/hole
					if (is_polygon)
					{
						degree = getFaceDegree((float)sum_v_degree/(float)min_degree, min_degree);
						faces.addElement(slide);
						index_count += (degree+1);
					}
					else
					{
						degree = getLargeFaceDegree(min_degree);						
					}
					
					counter = degree - min_degree;
					
					// important pointer
					boundary_prev = slide.boundary_prev;
					while (counter > 0)
					{
						if (ad_conn.decode(amAddOrSplitMerge) == 0) // 0: add 1: split or merge
						{
							if (min_degree == 2)
							{
								if (slide.boundary_prev == slide.boundary_next)
								{
									v0 = getPositionLast(slide.inv.position);
								}
								else 
								{
									v0 = getPositionAcross(slide.inv.next.inv.position, slide.inv.position, slide.position);
								}
							}
							else
							{
								if (is_polygon)
								{
									v0 = getPositionWithin(C, slide.inv.position, slide.position);
								}
								else
								{
									v0 = getPositionLast(slide.position);
								}
							}
							run = createTwinEdge(v0, slide.position);
							run.v_degree = getVertexDegree(degree);
							if (texCoord != null)
							{
								rings.addElement(run);
							}
							run.inv.v_degree = slide.v_degree;
							slide.next = run.inv;
							boundary_prev.boundary_next = run.inv;
							run.inv.boundary_prev = boundary_prev;
							run.inv.slots = remaining_slots;
							remaining_slots = run.v_degree - 2;
							boundary_slots += remaining_slots;
							C = slide.inv.position;
							slide = run;
							boundary_prev = run.inv;
						}
						else
						{
							if (ad_conn.decode(amSplitOrMerge) == 0) // 0: split 1: merge
							{
								// the fact we are connecting to a vertex reduces the num of boundary_slots by two
								boundary_slots -= 2;
								
								int offset = ad_conn.decode(boundary_slots+1);

								boundary_slots -= (offset + remaining_slots); // remaining_slots, because offset did not count the degrees at the adjacent vertex
						
								int offset_remainder = offset;
								TwinEdge glue_next = boundary_prev;
								while (offset_remainder >= glue_next.slots)
								{
									offset_remainder -= glue_next.slots;
									glue_next = glue_next.boundary_prev;
								}
								TwinEdge glue_prev = glue_next.boundary_prev;
								run = createTwinEdge(glue_next.position, slide.position);
								run.v_degree = glue_next.v_degree;
								run.inv.v_degree = slide.v_degree;
								slide.next = run.inv;

								boundary_prev.boundary_next = run.inv;
								run.inv.boundary_prev = boundary_prev;
								run.inv.slots = remaining_slots;
								remaining_slots = glue_next.slots - (offset_remainder + 1);
								glue_next.slots = offset_remainder;
		
								glue_next.boundary_prev = run.inv;
								run.inv.boundary_next = glue_next;
		
								boundaries.push(new Boundary(run.inv, offset + run.inv.slots)); // these are also the remaining_slots, because offset did not count the degrees at the adjacent vertex
		
								while (remaining_slots == 0)
								{
									run.next = glue_prev.inv;
									remaining_slots = glue_prev.slots;
									slide = run; // for C only
									run = glue_prev;
									glue_prev = glue_prev.boundary_prev;
									counter--;
								}
								remaining_slots--;
		
								C = slide.inv.position;
								slide = run;
								boundary_prev = glue_prev;
							}
							else
							{
							// the fact we are connecting to a vertex reduces the num of boundary_slots by two
								boundary_slots -= 2;
								
								int index = ad_conn.decode(boundaries.size());
								boundary = (Boundary)boundaries.elementAt(index);
								boundaries.removeElementAt(index);
								
								TwinEdge stack_gate = boundary.gate;
								int offset = ad_conn.decode(boundary.slots);
	
								boundary_slots += boundary.slots;
						
								int offset_remainder = offset;
								TwinEdge glue_next = stack_gate;
								while (offset_remainder >= glue_next.slots)
								{
									offset_remainder -= glue_next.slots;
									glue_next = glue_next.boundary_prev;
								}
								TwinEdge glue_prev = glue_next.boundary_prev;
								run = createTwinEdge(glue_next.position, slide.position);
								run.v_degree = glue_next.v_degree;
								run.inv.v_degree = slide.v_degree;
								slide.next = run.inv;
								boundary_prev.boundary_next = run.inv;
								run.inv.boundary_prev = boundary_prev;
								run.inv.slots = remaining_slots;
								remaining_slots = glue_next.slots - (offset_remainder + 1);
								glue_next.slots = offset_remainder;
		
								glue_next.boundary_prev = run.inv;
								run.inv.boundary_next = glue_next;
			
								while (remaining_slots == 0)
								{
									run.next = glue_prev.inv;
									remaining_slots = glue_prev.slots;
									slide = run; // for C only
									run = glue_prev;
									glue_prev = glue_prev.boundary_prev;
									counter--;
								}
								remaining_slots--;
		
								C = slide.inv.position;
								slide = run;
								boundary_prev = glue_prev;
							}
						}
						min_degree++;
						counter--;
					}
					run = createTwinEdge(gate.position, slide.position);
					run.v_degree = gate.v_degree;
					run.inv.v_degree = slide.v_degree;
					slide.next = run.inv;
					boundary_prev.boundary_next = run.inv;
					run.inv.boundary_prev = boundary_prev;
					run.inv.slots = remaining_slots;
					gate.slots--;

					run.next = gate.boundary_prev.inv;
					gate.boundary_prev = run.inv;
					run.inv.boundary_next = gate;

					gate = run.inv;
					
					// check out boundary_slots calculation is right
//					if (boundary_slots != compute_boundary_slots(gate))
//					{
//						System.out.println("boundary_slots: " + boundary_slots);     
//						System.out.println("compute_boundary_slots(gate): " + compute_boundary_slots(gate));     
//					}
				}
			}
			if (texCoord != null)
			{
				decodeTexcoordMapping();
				rings.removeAllElements();
			}
		}
		
		int indices[];
		int tex_indices[] = null;
		
		indices = new int[index_count];
		if (texCoord != null)
		{
			tex_indices = new int[index_count];
		}

		index_count = 0;
		for (v0 = 0; v0 < faces.size(); v0++)
		{
			gate = (TwinEdge)faces.elementAt(v0);
			slide = gate;
			do
			{
				indices[index_count] = slide.position;
				if (texCoord != null)
				{
					tex_indices[index_count] = slide.texcoord;			
				}
				index_count++;
				run = slide.inv.next;
				while (run.next != slide.inv)
				{
					run = run.next;
				}
				slide = run;
			}
			while (slide != gate);
			indices[index_count] = -1;
			if (texCoord != null)
			{
				tex_indices[index_count] = -1;			
			}
			index_count++;
		}
		set_coordIndex.setValue(indices);
		if (texCoord != null)
		{
			set_texCoordIndex.setValue(tex_indices);
		}		
	}
	
	private void requestTexcoord()
	{
		if (texcoord_count == texcoord_alloc)
		{
			int[] temp_texcoords = new int[2*texcoord_alloc*2];
			System.arraycopy(texcoords, 0, temp_texcoords, 0, texcoord_count*2);
			texcoords = temp_texcoords;
			texcoord_alloc = 2*texcoord_alloc;
		}
	}

	private void decodeTexcoordMapping()
	{
		int i;
		TwinEdge e;
		
		// note ... the 'slots' and the 'v_degree' fields are no longer needed
		// since the connectivity has already been decoded with degree duality
		// coding. we reuse them as the 'flag' and 'crease' fields that we have
		// in the C++ implementation
		for (i = 0; i < rings.size(); i++)
		{
			e = (TwinEdge)rings.elementAt(i);
			while (e.v_degree != 0)
			{
				e.slots = 0;    // used as the 'flag'
				e.v_degree = 0;  // used as the 'crease'
				e = e.next;
			}
		}
		for (i = 0; i < rings.size(); i++)
		{
			e = (TwinEdge)rings.elementAt(i);
			decodeVertexRing(e);
		}
	}

	private void decodeVertexRing(TwinEdge e)
	{
		ArithmeticModel context;
		TwinEdge r;

		// in this run we are figuring out whether any previously
		// decoded vertex ring has a crease corner pair pointing
		// our way
		r = e;
		context = amVertexBit1;
		do
		{
			if ((r.inv.slots == 1) && (r.inv.v_degree == 1))	// there is a previously visited vertex and it 
			{										        											// has a crease corner pair looking my way
				context = amVertexBit0;
				break;
			}
			r = r.next;
	  }
		while (r != e);
	
		if (ad_texmap.decode(context) == 1) // we have a single texcoord
		{
/*    it is already zero
			r = e;
			do
			{
				r.v_degree = 0; // crease = 0
				r = r.next;
			} while (r != e);
*/
			decodeTexcoord(e);
		}
		else // we have multiple texcoords
		{
			// first recover the corner bits and mark corners as crease or smooth starting with corner e.next
			r = e.next;
			int countConsecutiveSmoothCorners = 0;
			int countTotalCreaseCorners = 0;
	  	do
			{
				if ((r == e) && (countTotalCreaseCorners == 1)) // if this is the last corner and we've had only one crease corner
				{
					r.v_degree = 1; // crease = 1
					break;
				}
				if ((r.next == e) && (countTotalCreaseCorners == 0))	// if this is the second last corner  and we've had not yet had crease corner
				{
					r.v_degree = 1; // crease = 1
					r.next.v_degree = 1; // crease = 1
					break;
				}

//			if (false && r.strip == -1) // REMOVED FOR NOW // this is a hole corner, they do not actually have a texcoord but are
//			{										// assigned the previous texcoord for reasons of simplicity
//				r.crease = 0;
//			}

				if (r.inv.slots == 1) // there is a previously visited vertex and it ...
				{
					if (r.inv.v_degree == 1) // ... has a crease corner pair looking my way
					{
						context = amCornerBit0;
					}
					else                     // ... has a smooth corner pair looking my way
					{
						context = amCornerBit1;
					}
				}
				else // we must base it on what we have done already to this vertex
				{
					if (countTotalCreaseCorners > 1)
					{
						context = amCornerBit2; // we already had two crease corners
					}
					else if ((countTotalCreaseCorners == 1) && (countConsecutiveSmoothCorners < 2))
					{
						context = amCornerBit3; // we've just had a crease but less than two smooth ones
					}
					else if (countConsecutiveSmoothCorners == 2)
					{
						context = amCornerBit4; // we've just had two consecutive smooth corners
					}
					else if (countConsecutiveSmoothCorners >= 3)
					{
						context = amCornerBit5; // we've just had three or more consecutive smooth corners
					}
					else
					{
						context = amCornerBit6; // whatever is left ...
					}
				}
				
				if (ad_texmap.decode(context) == 1) // we have a crease corner
				{
					r.v_degree = 1; // crease = 1
					countConsecutiveSmoothCorners = 0;
					countTotalCreaseCorners++;
				}
				else // we have a smooth corner
				{
//				r.v_degree = 0; // crease = 0
					countConsecutiveSmoothCorners++;
				}
				r = r.next;
			}
			while (r != e.next);

			// in this run we decode the texcoord staring with the corner e
			r = e;
			do
			{
				if (r.v_degree == 1)
				{
					decodeTexcoord(r);
				}
				r = r.next;
			}
			while (r != e);
		}
		
		// in this run we the the visited flag
		r = e;
		do
		{
			r.slots = 1;
			r = r.next;
		}
		while (r != e);
	}

	private void decodeTexcoord(TwinEdge e)
	{
		int t;
		TwinEdge a;
		TwinEdge b;
		TwinEdge c;
		TwinEdge r;

		// non manifold check
		if (ad_texmap.decode(amTexcoordManifoldOrNot) == 1)
		{
			non_manifold_texcoord_count++;

			// read non-manifold texcoord index
			t = ad_texmap.decode(texcoord_count);

			e.texcoord = t;
			r = e.next;
			while ((r.v_degree == 0) && (r != e))
			{
				r.texcoord = t;
				r = r.next;
			}
		}
		else
		{
			requestTexcoord();
			t = texcoord_count;
			texcoord_count++;
			
			e.texcoord = t;
			r = e.next;
			while ((r.v_degree == 0) && (r != e))
			{
				r.texcoord = t;
				r = r.next;
			}

			// now predict and uncompress the texcoord

			// first attempt ... try parallelogram rule within a non-triangular polygon
			r = e;
			do
			{
				c = r.next.inv;
				b = c.next.inv;
				a = b.next.inv;

				while (a != r)
				{
					if ((c.slots == 1) && (b.slots == 1) && (a.slots == 1) && ((c == r.next.inv) || (a.next.inv == r)))
					{
						predictTexcoordWithin(c.texcoord, b.texcoord, a.texcoord, r.texcoord);
						return;
					}
					c = b;
					b = a;
					a = a.next.inv;
				}
				r = r.next;
			} while ((r != e) && (r.texcoord == t));

			// second attempt ... try parallelogram rule across polygons without crease connection
			r = e;
			do
			{
				c = r.next.inv;
				a = c.next.inv;

				while (a != r)
				{
					if ((c.slots == 1) && (a.slots == 1) && (a.v_degree == 0)) // no crease connection
					{
						b = a.inv.next.inv;
						while (b.next != a)
						{
							if (b.slots == 1)
							{
								predictTexcoordAcross(c.texcoord, b.texcoord, a.texcoord, r.texcoord);
								return;
							}
							b = b.next.inv;
						}
					}
					c = a;
					a = a.next.inv;
				}
				r = r.next;
			} while ((r != e) && (r.texcoord == t));

			// third attempt ... try prediction along an edge
			r = e;
			do
			{
				a = r.next.inv;
				while (a != r)
				{
					if (a.slots == 1)
					{
							predictTexcoordLast(a.texcoord, r.texcoord);
							return;
					}
					a = a.next.inv;
				}
				r = r.next;
			} while ((r != e) && (r.texcoord == t));

			// final attempt ... no prediction
			predictTexcoordCenter(e.texcoord);
		}
	}

	private void predictTexcoordCenter(int n)
	{
		int C[] = new int[2];
		int N[] = new int[2];

		getTexcoordCorrector(C, amCenterBitsTex);
		
		tq.AddCorrector(null, C, N);
		
		texcoords[2*n+0] = N[0];
		texcoords[2*n+1] = N[1];
	}

	private void predictTexcoordLast(int l, int n)
	{
		int[] P = new int[2];
		int[] N = new int[2];
		int[] C = new int[2];

		getTexcoordCorrector(C, amNearbyBitsTex);
		
		P[0] = texcoords[l*2+0];
		P[1] = texcoords[l*2+1];

		tq.Clamp(P);
		tq.AddCorrector(P, C, N);

		texcoords[2*n+0] = N[0];
		texcoords[2*n+1] = N[1];
	}

	private void predictTexcoordAcross(int c, int b, int a, int n)
	{
		int[] P = new int[2];
		int[] N = new int[2];
		int[] C = new int[2];
		
		getTexcoordCorrector(C, amAcrossBitsTex);

		P[0] = texcoords[a*2+0] - texcoords[b*2+0] + texcoords[c*2+0];
		P[1] = texcoords[a*2+1] - texcoords[b*2+1] + texcoords[c*2+1];

		tq.Clamp(P);
		tq.AddCorrector(P, C, N);
		
		texcoords[2*n+0] = N[0];
		texcoords[2*n+1] = N[1];
	}

	private void predictTexcoordWithin(int c, int b, int a, int n)
	{
		int[] P = new int[2];
		int[] N = new int[2];
		int[] C = new int[2];
		
		getTexcoordCorrector(C, amWithinBitsTex);

		P[0] = texcoords[a*2+0] - texcoords[b*2+0] + texcoords[c*2+0];
		P[1] = texcoords[a*2+1] - texcoords[b*2+1] + texcoords[c*2+1];

		tq.Clamp(P);
		tq.AddCorrector(P, C, N);
		
		texcoords[2*n+0] = N[0];
		texcoords[2*n+1] = N[1];
	}

	private TwinEdge next_gate(TwinEdge gate)
	{
		TwinEdge next = gate;
		while (next.boundary_next.slots == 0)
		{
			next = next.boundary_next;
			if (next == gate)
			{
				return gate;
			}
		}
		return next;
	}

	private TwinEdge best_gate(TwinEdge gate)
	{
		int min_degree = gate.slots;
		TwinEdge min_edge = gate;
		TwinEdge run = gate.boundary_prev;
		do
		{
			if (min_degree == 0)
			{
				break;
			}
			if (run.slots < min_degree)
			{
				min_degree = run.slots;
				min_edge = run;
			}
			run = run.boundary_prev;
		} while (run != gate);
		
		if (min_degree > 1)
		{
			return gate;
		}
		else
		{
			return next_gate(min_edge);
		}
	}

	private int compute_boundary_slots(TwinEdge eEdge)
	{
		int counter = 0;
		int degrees = eEdge.slots;
		TwinEdge sEdge = eEdge.boundary_prev;
		while (sEdge != eEdge)
		{
			degrees += sEdge.slots;
			sEdge = sEdge.boundary_prev;
			if (counter++ > 1000)
			{
				if (DEBUG) System.out.println("counter++ > 1000");
				return -1;
			}
		}
		return degrees;
	}

	private int getPositionIndex()
	{
		if (ad_conn.decode(amPositionManifoldOrNot) == 0) // 0 is manifold and 1 is non-manifold
		{
			return position_count++; // manifold
		}
		else
		{
			non_manifold_position_count++;
			return ad_conn.decode(position_count); // non-manifold
		}
	}
	
	private int getVertexDegree(int ratio)
	{
		ArithmeticModel context;
		
		if (ratio == 3)
		{
			context = amVertexDegree0;
		}
		else if  (ratio == 4)
		{
			context = amVertexDegree1;
		}
		else if  (ratio == 5)
		{
			context = amVertexDegree2;
		}
		else
		{
			context = amVertexDegree3;
		}

		int degree = 2 + ad_conn.decode(context);
		if (degree == 10)
		{
			int continued;
			do
			{
				continued = ad_conn.decode(amVertexDegreeLarge);
				degree += continued;
			} while (continued == 7);
		}
		return degree;
	}
	
	private int getFaceDegree(float ratio, int min_degree)
	{
		ArithmeticModel context;
		
		if (ratio < 3.3f)
		{
			context = amFaceDegree0;
		}
		else if  (ratio < 4.3f)
		{
			context = amFaceDegree1;
		}
		else if  (ratio < 4.9f)
		{
			context = amFaceDegree2;
		}
		else
		{
			context = amFaceDegree3;
		}
		if (min_degree <= 3)
		{
			min_degree = 3;
		}
		else if (min_degree > 5)
		{
			return 3 + getLargeFaceDegree(min_degree-3);
		}
		int degree = ad_conn.decode(context, min_degree-3);
		if (degree == 3)
		{
			return degree + getLargeFaceDegree(min_degree-3);
		}
		else
		{
			return degree + 3;
		}
	}

	private int getLargeFaceDegree(int min_degree)
	{
		int c = 7;
		int d = 3;

		if (min_degree <= 3)
		{
			min_degree = 3;
		}
		else
		{
			while (min_degree > 9)
			{
				min_degree -= 7;
				d += 7;
			}
		}
		while (c == 7)
		{
			c = ad_conn.decode(amFaceDegreeLarge, min_degree-3);
			d += c;
			min_degree = 3;
		}
		return d;
	}

	private void getPositionCorrector(int[] corr, ArithmeticModel[] amBits)
	{
		int i, j, c;
		for (i = 0; i < 3; i++)
		{
			c = 0;
			for (j = 4; j >= 0; j--)
			{
				if (amBits[j] != null)
				{
					c = c << bitsUsed[j];			
					c = c | ad_geom.decode(amBits[j]);
				}
			}
	    if (c != 0) // decode sign
			{
				if (ad_geom.decode(amSign) == 1)
				{
					c = -c;
				}
			}
			corr[i] = c;
		}
	}
	
	private void getTexcoordCorrector(int[] corr, ArithmeticModel[] amBitsTex)
	{
		int i, j, c;
		for (i = 0; i < 2; i++)
		{
			c = 0;
			for (j = 4; j >= 0; j--)
			{
				if (amBitsTex[j] != null)
				{
					c = c << bitsUsedTex[j];			
					c = c | ad_texval.decode(amBitsTex[j]);
				}
			}
	    if (c != 0) // decode sign
			{
				if (ad_texval.decode(amSignTex) == 1)
				{
					c = -c;
				}
			}
			corr[i] = c;
		}
	}

	private void requestPosition()
	{
		if (position_count == position_alloc)
		{
			int[] temp_positions = new int[2*position_alloc*3];
			System.arraycopy(positions, 0, temp_positions, 0, position_count*3);
			positions = temp_positions;
			position_alloc = 2*position_alloc;
		}
	}

	private int getPositionCenter()
	{
		if (ad_conn.decode(amPositionManifoldOrNot) == 0) // 0 is manifold and 1 is non-manifold
		{
			requestPosition();
			int C[] = new int[3];
			int N[] = new int[3];

			getPositionCorrector(C, amCenterBits);
			
			pq.AddCorrector(null, C, N);
			
			positions[3*position_count+0] = N[0];
			positions[3*position_count+1] = N[1];
			positions[3*position_count+2] = N[2];			
			return position_count++; // manifold
		}
		else
		{
			non_manifold_position_count++;
			return ad_conn.decode(position_count); // non-manifold
		}
	}

	private int getPositionLast(int l)
	{
		if (ad_conn.decode(amPositionManifoldOrNot) == 0) // 0 is manifold and 1 is non-manifold
		{
			requestPosition();
			int C[] = new int[3];
			int P[] = new int[3];
			int N[] = new int[3];

			getPositionCorrector(C, amLastBits);

			P[0] = positions[3*l+0];
			P[1] = positions[3*l+1];
			P[2] = positions[3*l+2];

			pq.Clamp(P);
			pq.AddCorrector(P, C, N);
	
			positions[3*position_count+0] = N[0];
			positions[3*position_count+1] = N[1];
			positions[3*position_count+2] = N[2];			
			return position_count++; // manifold
		}
		else
		{
			non_manifold_position_count++;
			return ad_conn.decode(position_count); // non-manifold
		}
	}

	private int getPositionAcross(int c, int b, int a)
	{
		if (ad_conn.decode(amPositionManifoldOrNot) == 0) // 0 is manifold and 1 is non-manifold
		{
			requestPosition();
			int C[] = new int[3];
			int P[] = new int[3];
			int N[] = new int[3];
			
			getPositionCorrector(C, amAcrossBits);

			P[0] = positions[3*a+0] + positions[3*b+0] - positions[3*c+0];
			P[1] = positions[3*a+1] + positions[3*b+1] - positions[3*c+1];
			P[2] = positions[3*a+2] + positions[3*b+2] - positions[3*c+2];
			
			pq.Clamp(P);
			pq.AddCorrector(P, C, N);
			
			positions[3*position_count+0] = N[0];
			positions[3*position_count+1] = N[1];
			positions[3*position_count+2] = N[2];			
			return position_count++; // manifold
		}
		else
		{
			non_manifold_position_count++;
			return ad_conn.decode(position_count); // non-manifold
		}
	}

	private int getPositionWithin(int a, int b, int c)
	{
		if (ad_conn.decode(amPositionManifoldOrNot) == 0) // 0 is manifold and 1 is non-manifold
		{
			requestPosition();
			int C[] = new int[3];
			int P[] = new int[3];
			int N[] = new int[3];
			
			getPositionCorrector(C, amWithinBits);
			
			P[0] = positions[3*a+0] - positions[3*b+0] + positions[3*c+0];
			P[1] = positions[3*a+1] - positions[3*b+1] + positions[3*c+1];
			P[2] = positions[3*a+2] - positions[3*b+2] + positions[3*c+2];
			
			pq.Clamp(P);
			pq.AddCorrector(P, C, N);
			
			positions[3*position_count+0] = N[0];
			positions[3*position_count+1] = N[1];
			positions[3*position_count+2] = N[2];			
			return position_count++; // manifold
		}
		else
		{
			non_manifold_position_count++;
			return ad_conn.decode(position_count); // non-manifold
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

	// for accessing the IFS (in VRML PROTO)
	
	MFInt32 set_coordIndex = null;
	MFInt32 set_texCoordIndex = null; 
	MFVec3f coord = null;
	MFVec2f texCoord = null;
	
	// for statistics only
	int non_manifold_position_count;
	int non_manifold_texcoord_count;

	boolean decode_on;
	boolean separate_bitstreams;

	int[] positions;
	int[] texcoords;

	int position_count;
	int position_alloc;

	int texcoord_count;
	int texcoord_alloc;

	Vector faces;
	Stack boundaries;
	Vector rings;
	
	// for connectivity
	ArithmeticDecoder ad_conn;
	ArithmeticModel amFaceDegree0;
	ArithmeticModel amFaceDegree1;
	ArithmeticModel amFaceDegree2;
	ArithmeticModel amFaceDegree3;
	ArithmeticModel amFaceDegreeLarge;
	ArithmeticModel amVertexDegree0;
	ArithmeticModel amVertexDegree1;
	ArithmeticModel amVertexDegree2;
	ArithmeticModel amVertexDegree3;
	ArithmeticModel amVertexDegreeLarge;
	ArithmeticModel amPolygonOrHole;
	ArithmeticModel amPositionManifoldOrNot;
	ArithmeticModel amAddOrSplitMerge;
	ArithmeticModel amSplitOrMerge;	

	// for geometry
	ArithmeticDecoder ad_geom;
	ArithmeticModel amSign;
	ArithmeticModel[] amCenterBits;
	ArithmeticModel[] amLastBits;
	ArithmeticModel[] amAcrossBits;
	ArithmeticModel[] amWithinBits;
	int[] bitsUsed;
	
	// for texture mapping
	ArithmeticDecoder ad_texmap;
	ArithmeticModel amVertexBit0;
	ArithmeticModel amVertexBit1;
	ArithmeticModel amCornerBit0;
	ArithmeticModel amCornerBit1;
	ArithmeticModel amCornerBit2;
	ArithmeticModel amCornerBit3;
	ArithmeticModel amCornerBit4;
	ArithmeticModel amCornerBit5;
	ArithmeticModel amCornerBit6;
	ArithmeticModel amTexcoordManifoldOrNot;

	// for texture values
	ArithmeticDecoder ad_texval;
			
	ArithmeticModel amSignTex;
	ArithmeticModel[] amWithinBitsTex;
	ArithmeticModel[] amAcrossBitsTex;
	ArithmeticModel[] amNearbyBitsTex;
	ArithmeticModel[] amCenterBitsTex;
	int[] bitsUsedTex;

	PositionQuantizer pq;	
	TexcoordQuantizer tq;	
	
	class TwinEdge
	{
		TwinEdge next = null;
		TwinEdge inv  = null;
	
		TwinEdge boundary_prev = null;
		TwinEdge boundary_next = null;
			
		int position = -1;
		int texcoord = -1;
		int slots = -1;	
		int v_degree = -1;	
	}

	class Boundary
	{
		public Boundary(TwinEdge gate, int slots)
		{
			this.gate = gate;
			this.slots = slots;
		}
		TwinEdge gate = null;
		int slots = -1;
	}
}
