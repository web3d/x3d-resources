import vrml.*;
import vrml.field.*;
import vrml.node.*;
import java.util.*;

/* 
 * NurbsCurve.java
 * @author  Charles Adams
 * @author  Don Brutzman
 * @author  Jeffrey Weekley
 * @author  Al Shaffer
 *
 * @created  3 February 2003
 * @revised  12 March 2005
 * @url http://www.web3D.org/TaskGroups/x3d/translation/examples/Nurbs/NurbsCurve.java
 * 
 * <p>Equations for NurbsCurve obtained from:
 * Rogers, David F. An Introduction to NURBS. Ch 3,4. Academic Press. San Diego, 2001.
 *
 * NurbsCurve implements section 27.4.4 of the X3D Specification
 * </p>
*/
public class NurbsCurve extends vrml.node.Script {

    private MFVec3f controlPoint;  //type MFNode (NurbsCurve) or MFVec2f (NurbsCurve2D)?
    private SFInt32 tessellation;
    private MFFloat weight;       // MFDouble in X3D, MFFloat in VRML97
    private MFFloat knot;         // MFDouble in X3D, MFFloat in VRML97
    private SFInt32 order;
    private MFVec3f point;         //field not in NurbsCurve/NurbsCurve2D nodes
    private MFInt32 pointIndex;    //field not in NurbsCurve/NurbsCurve2D nodes
    private String errors[] = {"Invalid knot vector. Leave blank for automatic creation of uniform vector.","Invalid weight vector. Leave blank for automatic creation.","Invalid knot vector. Values must be in non-decreasing order"};
    private Vector rvec = new Vector(1);

    private MFVec3f controlPoint_changed;  //MFNode or MFVec2f?
    private SFInt32 tessellation_changed;
    private MFFloat weight_changed;       // MFDouble in X3D, MFFloat in VRML97

    public void initialize() {
	controlPoint = (MFVec3f)getField("controlPoint");  //MFNode or MFVec2f?
	tessellation = (SFInt32)getField("tessellation");
	weight = (MFFloat)getField("weight");       // MFDouble in X3D, MFFloat in VRML97
	knot = (MFFloat)getField("knot");           // MFDouble in X3D, MFFloat in VRML97
	order = (SFInt32)getField("order");
	point = (MFVec3f)getEventOut("point");
	pointIndex = (MFInt32)getEventOut("pointIndex");

	controlPoint_changed = (MFVec3f)getEventOut("controlPoint_changed");  //MFNode or MFVec2f?
	tessellation_changed = (SFInt32)getEventOut("tessellation_changed");
	weight_changed = (MFFloat)getEventOut("weight_changed");
	
	try {
	int exitcode = drawCurve();
	if(exitcode != -1)
	    System.err.println(errors[exitcode]+"\n");
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node initialize method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
    }

    private int drawCurve() {

	try {
	// Array to store N(i,k) values
	double N[][] = new double[controlPoint.getSize()+order.getValue()][order.getValue()+1];

	// Test size of knot vector. Create one if not specified
	if(knot.getSize()==0)
	    makeKnotVector();
	if(knot.getSize() != (controlPoint.getSize()+order.getValue()))
	    return 0;
	for(int i=0;i<knot.getSize()-1;i++) {
	    if(knot.get1Value(i)>knot.get1Value(i+1))
		return 2;
	}

	// Test size of weight vector. Create one if not specified
	if(weight.getSize() == 0) {
	    for(int i=0;i<controlPoint.getSize();i++)
		weight.addValue(1);
	}
	if(weight.getSize()!=controlPoint.getSize())
	    return 1;

	// Determine tessellation value
	if(tessellation.getValue() <= 0)
	    tessellation.setValue(20);

	// Determine t interval
	double tmin = knot.get1Value(0);
	double tmax = knot.get1Value(knot.getSize()-1);
	double tstep = (tmax - tmin)/(tessellation.getValue()-1);

	// Temporary storage for output points
//***NEED TO CHECK BELOW TYPES***
	MFVec3f coord = new MFVec3f();
	MFInt32 coordIndex = new MFInt32();
	int index = 0;
	double R[][] = new double[tessellation.getValue()+1][controlPoint.getSize()+1];

	for(double t=tmin;t<tmax;t+=tstep) {

	    // Calculate N(i,k)
	    for(int k=1;k<=order.getValue();k++) {
		for(int i=(controlPoint.getSize()+order.getValue()-k);i>0;i--)
		    N[i][k] = calcBasisFunction(i,k,t,N);
	    }

	    // Calculate R(i) for order k
	    double sum = 0;
	    for(int i=1;i<=controlPoint.getSize();i++)
		sum += weight.get1Value(i-1)*N[i][order.getValue()];
	    if(sum == 0) {
		for(int i=0;i<(controlPoint.getSize()+1);i++)
		    R[index][i]=0;
	    }
	    else {
		for(int i=1;i<=controlPoint.getSize();i++)
		    R[index][i] = (weight.get1Value(i-1)*N[i][order.getValue()])/sum;
	    }

	    // Calculate P(t), add it to MFVec3f coord
	    float ptsum[] = {0,0,0};
	    for(int i=1;i<=controlPoint.getSize();i++) {
		float pt[] = new float[3];
		controlPoint.get1Value(i-1,pt);
		for(int j=0;j<3;j++)
		    ptsum[j] += pt[j]*R[index][i];
	    }
	    coord.addValue(ptsum[0],ptsum[1],ptsum[2]);
	    coordIndex.addValue(index);
	    index++;
	}

	rvec.removeAllElements();
	rvec.addElement(R);
	point.setValue(coord);
	pointIndex.setValue(coordIndex);
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node drawCurve method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	return -1;
    }

    private void recalcP() {
	double R[][] = new double[tessellation.getValue()+1][controlPoint.getSize()+1];
	R = (double[][])rvec.firstElement();
//***NEED TO CHECK BELOW TYPES***
	MFVec3f coord = new MFVec3f();
	MFInt32 coordIndex = new MFInt32();

	try {
	for(int index=0;index<tessellation.getValue();index++) {
	    float ptsum[] = {0,0,0};
	    for(int i=1;i<=controlPoint.getSize();i++) {
		float pt[] = new float[3];
		controlPoint.get1Value(i-1,pt);
		for(int j=0;j<3;j++)
		    ptsum[j] += pt[j]*R[index][i];
	    }
	    coord.addValue(ptsum[0],ptsum[1],ptsum[2]);
	    coordIndex.addValue(index);
	}
	point.setValue(coord);
	pointIndex.setValue(coordIndex);
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node recalcP method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
    }

    private void makeKnotVector() {
	try {
	knot.clear();
	for(int i=0;i<order.getValue();i++)
	    knot.addValue(0);
	for(int i=1;i<(controlPoint.getSize()-order.getValue()+1);i++)
	    knot.addValue(i);
	for(int i=0;i<order.getValue();i++)
	    knot.addValue(controlPoint.getSize()-order.getValue()+1);
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node makeKnotVector method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
    }

    private double calcBasisFunction(int i, int k, double t, double[][] N) {

	try {
	if(k==1) {
	    if(t>=knot.get1Value(i-1) && t<knot.get1Value(i))
		return 1;
	    else
		return 0;
	}
	else {
	    double ps1=0, ps2=0;
	    double temp =  (knot.get1Value(i+k-2)-knot.get1Value(i-1));
	    if(temp!=0)
		ps1 = (((t-knot.get1Value(i-1))*N[i][k-1])/temp);
	    temp = (knot.get1Value(i+k-1)-knot.get1Value(i));
	    if(temp!=0)
		ps2 = (((knot.get1Value(i+k-1)-t)*N[i+1][k-1])/temp);
	    return ps1+ps2;
	}
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node calcBasisFunction method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	return -1;
    }

    public void processEvent(Event e) {
	try {
	String eventName = e.getName();
	int redrawAll = 0;
        if(eventName.equals("set_controlPoint")) {
//***NEED TO CHECK BELOW TYPE***
		MFVec3f temp = new MFVec3f();
	    temp.setValue((ConstMFVec3f)e.getValue());
	    if(temp.getSize() == controlPoint.getSize()) {
		controlPoint.setValue(temp);
		recalcP();
	    controlPoint_changed.setValue(controlPoint);
	    }
	}
	else if(eventName.equals("set_tessellation")) {
	    tessellation.setValue(((ConstSFInt32)e.getValue()).getValue());
	    tessellation_changed.setValue(tessellation);
	    redrawAll = 1;
	}
	else if(eventName.equals("set_weight")) {
	    weight.setValue((ConstMFFloat)e.getValue());     // MFDouble in X3D, MFFloat in VRML97
	    weight_changed.setValue(weight);
	    redrawAll = 1;
	}
	if(redrawAll == 1) {
	    int exitcode = drawCurve();
	    if(exitcode != -1)
		System.out.println(errors[exitcode]+"\n");
	}
	}
	catch (Exception ee)
	  {
	  	System.err.println ("Exception caught in Script node processEvent method:");
	  	System.err.println (e);
	  	ee.printStackTrace();
	  }
    }

}
