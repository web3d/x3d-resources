import vrml.*;
import vrml.field.*;
import vrml.node.*;
import java.util.*;

/* 
 * NurbsPositionInterpolator.java
 * @author  Charles Adams
 * @author  Don Brutzman
 * @author  Jeffrey Weekley
 * @author  Al Shaffer
 *
 * @created 25 February 2003
 * @revised  4 March 2005
 * @url http://www.web3D.org/TaskGroups/x3d/translation/examples/Nurbs/NurbsPositionInterpolator.java
 * 
 * NurbsPositionInterpolator is implemented according to section 27.4.7 of the X3D Specification
*/

public class NurbsPositionInterpolator extends vrml.node.Script {

    private SFNode controlPoints;         // changed from MFVec3f to a Coordinate node
    private MFVec3f controlPointsValues;  // point data from intermediate Coordinate node
    private MFFloat weight;        // MFDouble in X3D, MFFloat in VRML97
    private MFFloat knot;          // MFDouble in X3D, MFFloat in VRML97
    private SFInt32 order;
    private String errors[] = {"Invalid knot vector. Leave blank for automatic creation of uniform vector.","Invalid weight vector. Leave blank for automatic creation.","Invalid knot vector. Values must be in non-decreasing order"};
    private SFVec3f value_changed;  //type SFRotation in NurbsOrientationInterpolator node?
    private MFFloat key;  //field not in NurbsPositionInterpolator/NurbsOrientationInterpolator nodes
    private SFNode controlPoints_changed;  //changed from MFVec3f to a Coordinate node
    private MFFloat weight_changed;        // MFDouble in X3D, MFFloat in VRML97
    private MFFloat key_changed;

    public void initialize() {
	controlPoints        = (SFNode)  getField("controlPoints");   //changed from MFVec3f to a Coordinate node
	controlPointsValues  = (MFVec3f) controlPoints.getExposedField("point");
	
	weight = (MFFloat)getField("weight");      // MFDouble in X3D, MFFloat in VRML97
	knot   = (MFFloat)getField("knot");        // MFDouble in X3D, MFFloat in VRML97
	order  = (SFInt32)getField("order");
	value_changed = (SFVec3f)getEventOut("value_changed");
	key = (MFFloat)getField("key");
	controlPoints_changed = (SFNode)getEventOut("keyValue_changed");   //changed from MFVec3f to a Coordinate node
	weight_changed = (MFFloat)getEventOut("weight_changed");        // MFDouble in X3D, MFFloat in VRML97
	key_changed = (MFFloat)getEventOut("key_changed");
		
	try {
	int exitcode = initGlobals();
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

    private int initGlobals() {

	try {

	// Test size of knot vector. Create one if not specified
	if(knot.getSize()==0)
	    makeKnotVector();
	if(knot.getSize() != (controlPointsValues.getSize()+order.getValue()))
	    return 0;
	for(int i=0;i<knot.getSize()-1;i++) {
	    if(knot.get1Value(i)>knot.get1Value(i+1))
		return 2;
	}

	// Test size of weight vector. Create one if not specified
	if(weight.getSize() == 0) {
	    for(int i=0;i<controlPointsValues.getSize();i++)
		weight.addValue(1);
	}
	if(weight.getSize()!=controlPointsValues.getSize())
	    return 1;
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node initGlobals method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	return -1;
    }

    private void calcT(float f) {
	
	try {

	int index = 0;
	double inc = (1.0)/(controlPointsValues.getSize()-1);
	double keyValue[] = new double[controlPointsValues.getSize()];
	keyValue[0] = 0;
	for(int i=1;i<controlPointsValues.getSize();i++) 
	    keyValue[i] = i*inc;
	float k = key.get1Value(index);
	double kval = keyValue[index];
	float key1 = k, key2 = 0;
	double kval1 = kval, kval2 = 0;

	if(f>=k) {
	    if(f==k) {
		key2 = key.get1Value(index+1);
		kval2 = keyValue[index+1];
	    }
	    else {
		while(f>k) {
		    key1 = k;
		    kval1 = kval;
		    if(index < key.getSize()-1) {
			k = key.get1Value(index+1);
			kval = keyValue[index+1];
			index++;
		    }
		}
		key2 = k;
		kval2 = kval;
	    }
	    double t = ((f-key1)/(key2-key1))*(kval2-kval1)+kval1;
	    t = knot.get1Value(0)+(t*(knot.get1Value(knot.getSize()-1)-knot.get1Value(0)));
	    drawCurve(t);
	}
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node calcT method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
    }

    private int drawCurve(double t) {

	try {
	// Array to store N(i,k) values
	double N[][] = new double[controlPointsValues.getSize()+order.getValue()][order.getValue()+1];
	double R[] = new double[controlPointsValues.getSize()+1];

	    // Calculate N(i,k)
	    for(int k=1;k<=order.getValue();k++) {
		for(int i=(controlPointsValues.getSize()+order.getValue()-k);i>0;i--)
		    N[i][k] = calcBasisFunction(i,k,t,N);
	    }

	    // Calculate R(i) for order k
	    double sum = 0;
	    for(int i=1;i<=controlPointsValues.getSize();i++)
		sum += weight.get1Value(i-1)*N[i][order.getValue()];
	    if(sum == 0) {
		for(int i=0;i<(controlPointsValues.getSize()+1);i++)
		    R[i]=0;
	    }
	    else {
		for(int i=1;i<=controlPointsValues.getSize();i++)
		    R[i] = (weight.get1Value(i-1)*N[i][order.getValue()])/sum;
	    }

	    // Calculate P(t), add it to MFVec3f coord
	    float ptsum[] = {0,0,0};
	    for(int i=1;i<=controlPointsValues.getSize();i++) {
		float pt[] = new float[3];
		controlPointsValues.get1Value(i-1,pt);
		for(int j=0;j<3;j++)
		    ptsum[j] += pt[j]*R[i];
	    }
	    value_changed.setValue(ptsum[0],ptsum[1],ptsum[2]);
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node drawCurve method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	return -1;
    }

    private void makeKnotVector() {
	try {
	knot.clear();
	for(int i=0;i<order.getValue();i++)
	    knot.addValue(0);
	for(int i=1;i<(controlPointsValues.getSize()-order.getValue()+1);i++)
	    knot.addValue(i);
	for(int i=0;i<order.getValue();i++)
	    knot.addValue(controlPointsValues.getSize()-order.getValue()+1);
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
	if(eventName.equals("set_fraction")) {
	    calcT(((ConstSFFloat) e.getValue()).getValue());
	}
        else if(eventName.equals("set_keyValue")) {
	    MFVec3f temp = new MFVec3f();
	    temp.setValue((ConstMFVec3f)e.getValue());
	    if(temp.getSize() == controlPointsValues.getSize()) {
		controlPointsValues.setValue(temp);
		controlPoints_changed.setValue(controlPoints);
	    }
	}
	else if(eventName.equals("set_weight")) {
	    MFFloat temp = new MFFloat();
	    temp.setValue((ConstMFFloat)e.getValue());
	    if(temp.getSize() == weight.getSize()) {
		weight.setValue(temp);
		weight_changed.setValue(weight);
	    }
	}
	else if(eventName.equals("set_key")) {
	    MFFloat temp = new MFFloat();
	    temp.setValue((ConstMFFloat)e.getValue());
	    if(temp.getSize() == key.getSize()) {
		key.setValue(temp);
		key_changed.setValue(key);
	    }
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
