package Race;
import java.lang.*;
import java.io.*;
import java.util.*;
import Race.*;

public class Scene {
    public Track courseGraph[] = new Track[200];
    public int cgfreepointer = 0;
    public TrackType trackGraph[] = new TrackType[50];
    public int tgfreepointer = 0;
    private String nl = new String("\n");

    public int ReadTrackTypesFile() {
	// read tracktypes.txt into String parseMe
	try {

	    //File testout = new File("ohyeah.txt");
	    //FileWriter fout = new FileWriter(testout);
	    //char oo[] = {'b','i','t','e'};
	    //fout.write(oo);
	    //fout.close();

	    String inputFileName = new String("tracktypes.txt");
	    File inputFile = new File(inputFileName);
	    FileReader fin = new FileReader(inputFile);
	    char c[] = new char[(char)inputFile.length()];
	    fin.read(c);
	    String parseMe = new String(c);
	    fin.close();
	    // Construct TrackType objects
	    // Place them in trackGraph[]
	    // Return 0 if error, else return 1
	    StringTokenizer st = new StringTokenizer(parseMe," ");
	   	    
	    while (st.hasMoreTokens()) {
		TrackType t = new TrackType();
		int TrackEnd = 0;
		while(TrackEnd == 0) {
		    String s = new String(st.nextToken());
		    if(s.compareTo("N") == 0) {
			s = st.nextToken();
			t.name = s;
		    }
		    else if(s.compareTo("T") == 0) {
			s = st.nextToken();
			t.texture = s;
		    }
		    else if(s.compareTo("NBB") == 0) {
			s = st.nextToken();
			t.numBBoxes = Integer.valueOf(s).intValue();
		    }
		    else if(s.compareTo("XCP") == 0) {
			int SectionEnd = 0;
			while(SectionEnd==0) {
			    s = st.nextToken();
			    if(s.compareTo(";") == 0)
				SectionEnd = 1;
			    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0) {
				SectionEnd = 1;
				TrackEnd = 1;
			    }
			    else {
				t.xcollisionPlanes[t.xcpfreepointer] = Double.valueOf(s).doubleValue();
				t.xcpfreepointer++;
			    }
			}
		    }
		    else if(s.compareTo("ZCP") == 0) {
			int SectionEnd = 0;
			while(SectionEnd==0) {
			    s = st.nextToken();
			    if(s.compareTo(";") == 0)
				SectionEnd = 1;
			    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0) {
				SectionEnd = 1;
				TrackEnd = 1;
			    }
			    else {
				t.zcollisionPlanes[t.zcpfreepointer] = Double.valueOf(s).doubleValue();
				t.zcpfreepointer++;
			    }
			}
		    }
		    else if(s.compareTo("LBB") == 0) {
			int SectionEnd = 0;
			while(SectionEnd==0) {
			    s = st.nextToken();
			    if(s.compareTo(";") == 0)
				SectionEnd = 1;
			    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0) {
				SectionEnd = 1;
				TrackEnd = 1;
			    }
			    else {
				t.lengthBBoxes[t.lBBfreepointer] = Double.valueOf(s).doubleValue();
				t.lBBfreepointer++;
			    }
			}
		    }
		    else if(s.compareTo("WBB") == 0) {
			int SectionEnd = 0;
			while(SectionEnd==0) {
			    s = st.nextToken();
			    if(s.compareTo(";") == 0)
				SectionEnd = 1;
			    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0) {
				SectionEnd = 1;
				TrackEnd = 1;
			    }
			    else {
				t.widthBBoxes[t.wBBfreepointer] = Double.valueOf(s).doubleValue();
				t.wBBfreepointer++;
			    }
			}
		    }
		    else if(s.compareTo("PXBB") == 0) {
			int SectionEnd = 0;
			while(SectionEnd==0) {
			    s = st.nextToken();
			    if(s.compareTo(";") == 0)
				SectionEnd = 1;
			    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0) {
				SectionEnd = 1;
				TrackEnd = 1;
			    }
			    else {
				t.pxBBoxes[t.pxBBfreepointer] = Double.valueOf(s).doubleValue();
				t.pxBBfreepointer++;
			    }
			}
		    }
		    else if(s.compareTo("PZBB") == 0) {
			int SectionEnd = 0;
			while(SectionEnd==0) {
			    s = st.nextToken();
			    if(s.compareTo(";") == 0)
				SectionEnd = 1;
			    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0) {
				SectionEnd = 1;
				TrackEnd = 1;
			    }
			    else {
				t.pzBBoxes[t.pzBBfreepointer] = Double.valueOf(s).doubleValue();
				t.pzBBfreepointer++;
			    }
			}
		    }
		    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0)
			TrackEnd = 1;
		    else {
			System.out.println("Got "+s+" ReadTrackTypes FAILED\n");
			return 0;
		    }
		}
		trackGraph[tgfreepointer] = t;
		tgfreepointer++;
	    }
	}
	catch(java.io.IOException e){
	    System.out.println("Cannot access file");
	}
	//System.out.println("ReadTrackTypes success\n");
	return 1;
    }
    public int ReadCourseFile() {
	// read course.txt into String parseMe
	try {
	    String inputFileName = new String("course.txt");
	    File inputFile = new File(inputFileName);
	    FileReader fin = new FileReader(inputFile);
	    char c[] = new char[(char)inputFile.length()];
	    fin.read(c);
	    String parseMe = new String(c);
	    fin.close();
	    // Construct Track objects
	    // Place them in courseGraph[]
	    // Return 0 if error, else return 1
	    StringTokenizer st = new StringTokenizer(parseMe," ");
	   	    
	    while (st.hasMoreTokens()) {
		Track t = new Track();
		int TrackEnd = 0;
		while(TrackEnd == 0) {
		    String s = new String(st.nextToken());

		    if(s.compareTo("N") == 0) {
			s = st.nextToken();
			t.track = FindTrackType(s);
			if(t.track == null)
			    return 0;
		    }
		    else if(s.compareTo("PX") == 0) {
			s = st.nextToken();
			t.px = Double.valueOf(s).doubleValue();
		    }
		    else if(s.compareTo("PZ") == 0) {
			s = st.nextToken();
			t.pz = Double.valueOf(s).doubleValue();
		    }
		    else if(s.compareTo("L") == 0) {
			s = st.nextToken();
			t.length = Double.valueOf(s).doubleValue();
		    }
		    else if(s.compareTo("W") == 0) {
			s = st.nextToken();
			t.width = Double.valueOf(s).doubleValue();
		    }
		    else if(s.compareTo("E") == 0) {
			s = st.nextToken();
			t.elevation = Double.valueOf(s).doubleValue();
		    }
		    else if(s.compareTo("T") == 0) {
			for(int i=0;i<4;i++) {
			    s = st.nextToken();
			    t.tilt[i] = Double.valueOf(s).doubleValue();
			}
		    }
		    else if(s.compareTo(";;") == 0 || s.compareTo(";;\n") == 0)
			TrackEnd = 1;
		    else {
			System.out.println("ReadCourse FAILED\n");
			return 0;		
		    }	
		}
		courseGraph[cgfreepointer] = t;
		cgfreepointer++;
	    }
	}
	catch(java.io.IOException e){
	    System.out.println("Cannot access file");
	}
	//System.out.println("ReadCourse success\n");
	return 1;
    }
    private TrackType FindTrackType(String s) {
	for(int i=0;i<tgfreepointer;i++) {
	    if(trackGraph[i].name.compareTo(s) == 0)
		return trackGraph[i];
	}
	return null;
    }
}
	
	
