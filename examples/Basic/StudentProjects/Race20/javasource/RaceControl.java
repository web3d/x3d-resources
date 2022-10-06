package Race;
import vrml.*;
import vrml.field.*;
import vrml.node.*;
import Race.*;
import java.lang.*;

public class RaceControl extends Script {

    // local variables
    public Movement movement = new Movement();
    private SFBool camBind = new SFBool(true);
    private int textureindex = 1;

    // eventOuts
    private SFVec3f newTranslation;
    private SFRotation newRotation;
    private SFRotation newTireRotation;
    private SFRotation newWheelSpin;
    private SFBool cam1Out;
    private SFBool cam2Out;
    private SFBool cam3Out;
    private SFBool cam4Out;
    private SFBool cam5Out;
    private Node rootnode;
    private MFNode children;
    private MFString cartex;
    private SFFloat newpitch;

    Browser browser;

    public void initialize(){
	browser = getBrowser();
	if(browser == null)
	    System.out.println("Can't get Browser\n");
	rootnode = (Node)((SFNode)getField("tracks")).getValue(); 
	if(this.movement.createWorld()==1)
	    CreateVRML();	
	//getTestData();
	//movement.sceneOK = 1;
	newTranslation = (SFVec3f) getEventOut("newTranslation");
	newRotation = (SFRotation) getEventOut("newRotation");
	newTireRotation = (SFRotation) getEventOut("newTireRotation");
	newWheelSpin = (SFRotation) getEventOut("newWheelSpin");
	cam1Out = (SFBool) getEventOut("cam1Out");
	cam2Out = (SFBool) getEventOut("cam2Out");
	cam3Out = (SFBool) getEventOut("cam3Out");
	cam4Out = (SFBool) getEventOut("cam4Out");
	cam5Out = (SFBool) getEventOut("cam5Out");
	cartex = (MFString) getEventOut("cartex");
	newpitch = (SFFloat) getEventOut("pitch");
    }
    public void CreateVRML() {
	try {
	    for(int i=0;i<movement.scene.cgfreepointer;i++) {
		Track t = new Track();
		t = movement.scene.courseGraph[i];
		double twidth4 = t.width/4;
		double tlength4 = t.length/4;
		double twidth2 = t.width/2;
		double tlength2 = t.length/2;
		double twidthn2 = t.width/(-2);
		double tlengthn2 = t.length/(-2);

		String s = new String(""+
"Transform {"+
"    translation "+t.px+" "+t.elevation+" "+t.pz+
"    rotation "+t.tilt[0]+" "+t.tilt[1]+" "+t.tilt[2]+" "+t.tilt[3]+                                                 
"    children [                                                                                                      "+
"         Shape {                                                                                                    "+
"              appearance Appearance {                                                                               "+
"                    texture ImageTexture {url[\""+t.track.texture+"\"]}                                             "+
"              }                                                                                                     "+
"              geometry IndexedFaceSet {                                                                             "+
"                    texCoord TextureCoordinate {                                                                    "+
"                        point [                                                                                     "+
"                              0 0,"+twidth4+" 0,"+twidth4+" "+tlength4+",0 "+tlength4+
"                              0 0,1 0,1 1,0 1                                                                       "+
"                              0 0,1 0,1 1,0 1                                                                       "+
"                              0 0,1 0,1 1,0 1                                                                       "+
"                              0 0,1 0,1 1,0 1                                                                       "+
"                              0 0,1 0,1 1,0 1                                                                       "+
"                         ]                                                                                          "+
"                    }                                                                                               "+
"                    texCoordIndex [0,1,2,3,-1,4,5,6,7,-1,11,8,9,10,-1,15,12,13,14,-1,19,16,17,18,-1,23,20,21,22]    "+
"                    coord Coordinate {                                                                              "+
"                         point [                                                                                    "+
"                              "+twidthn2+" .1 "+tlength2+","+twidth2+" .1 "+tlength2+",                             "+
"                              "+twidth2+" .1 "+tlengthn2+","+twidthn2+" .1 "+tlengthn2+",                           "+
"                              "+twidthn2+" -.1 "+tlength2+","+twidth2+" -.1 "+tlength2+",                           "+
"                              "+twidth2+" -.1 "+tlengthn2+","+twidthn2+" -.1 "+tlengthn2+
"                         ]                                                                                          "+
"                    }                                                                                               "+
"                    coordIndex [0,1,2,3,-1,7,6,5,4,-1,0,4,5,1,-1,1,5,6,2,-1,2,6,7,3,-1,3,7,4,0]                     "+
"                }                                                                                                   "+
"          }                                                                                                         "+
"     ]                                                                                                              "+
"}                                                                                                                   "+
"                ");
		children = (MFNode)rootnode.getEventIn("addChildren");
		BaseNode newTrack[] = browser.createVrmlFromString(s);
		children.setValue(newTrack);
	    }
	}catch (Exception e) {
	    System.out.println("Cannot output VRML");
	}
    }
    public void processEvent(Event e){
	String eventName = e.getName();
        if(eventName.equals("timeChange")) {

	    float outputTrans[] = this.movement.getTranslation();
	    newTranslation.setValue(outputTrans[0],outputTrans[1],outputTrans[2]);

	    float outputRot[] = this.movement.getRotation(); 
	    newRotation.setValue(outputRot[0],outputRot[1],outputRot[2],outputRot[3]);

	    outputRot = this.movement.getTireRotation();
	    newTireRotation.setValue(outputRot[0],outputRot[1],outputRot[2],outputRot[3]);

	    outputRot = this.movement.getWheelSpin();
	    newWheelSpin.setValue(outputRot[0],outputRot[1],outputRot[2],outputRot[3]);

	}
	else if(eventName.equals("keyUp")) {
	    int key = ((ConstSFInt32) e.getValue()).getValue();
	    key -= 65536;
	    //System.out.println("Passing key "+key);
	    this.movement.keyUp(key);
	}
	else if(eventName.equals("keyDown")) {
	    int key = ((ConstSFInt32) e.getValue()).getValue();
	    key -= 65536;
	    //System.out.println("Passing key "+key);
	    int cam = this.movement.keyDown(key);
	    if(cam == 49)
		cam1Out.setValue(camBind);
	    else if(cam == 50)
		cam2Out.setValue(camBind);
	    else if(cam == 51)
		cam3Out.setValue(camBind);
	    else if(cam == 52)
		cam4Out.setValue(camBind);
	    else if(cam == 53)
		cam5Out.setValue(camBind);
	    else if(cam == 84)
		changeTexture();
	    else if(cam == 38 || cam == 40) {
		float setpitch = (float)movement.throttle/10;
		newpitch.setValue(setpitch);
	    }
	}
    }
    public void getTestData() {
	TrackType tt = new TrackType();
	tt.name = "vtrack";
	tt.texture = "vtrack.jpg";
	tt.xcollisionPlanes[0] = .2;
	tt.xcollisionPlanes[1] = .8;
	movement.scene.trackGraph[movement.scene.tgfreepointer] = tt;
	movement.scene.tgfreepointer++;

	Track t = new Track();
	t.track = tt;
	t.px = 0.0;
	t.pz = -2.0;
	t.length = 16;
	t.width = 4;
	t.elevation = .4;
	movement.scene.courseGraph[movement.scene.cgfreepointer] = t;
	movement.scene.cgfreepointer++;
	//System.out.println("GotTestData");
    }
    private void changeTexture() {
	String tex1[] = {"cartex1.jpg","http://home.attbi.com/~cnadams/race20/cartex1.jpg"};
	String tex2[] = {"cartex2.jpg","http://home.attbi.com/~cnadams/race20/cartex2.jpg"};
	String tex3[] = {"cartex3.jpg","http://home.attbi.com/~cnadams/race20/cartex3.jpg"};
	String tex4[] = {"cartex4.jpg","http://home.attbi.com/~cnadams/race20/cartex4.jpg"};
	String[] alltex[] = {tex1,tex2,tex3,tex4};
	cartex.setValue(alltex[textureindex]);
	textureindex++;
	textureindex %= 4;
    }	
}
