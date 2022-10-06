import vrml.*;
import vrml.field.*;
import vrml.node.*;
import java.util.*;
import java.math.*;

/* 
 * NurbsPatchSurface.java
 * @author  Charles Adams
 * @author  Don Brutzman
 * @author  Jeffrey Weekley
 * @author  Al Shaffer
 *
 * @created  6 February 2003
 * @revised 23 February 2005
 * @url http://www.web3D.org/TaskGroups/x3d/translation/examples/Nurbs/NurbsPatchSurface.java
 * 
 * <p>Equations for NurbsPatchSurface obtained from:
 * Rogers, David F. An Introduction to NURBS. Ch 6,7. Academic Press. San Diego, 2001.
 *
 * NurbsPatchSurface implements section 27.4.8 of the X3D Specification
 *
 * In addition to the specification, the field "SFBool initializeOnly closedSurface false" has been added.
 * This field is necessary to retain end conditions of surfaces exported using the X3D Maya(tm) Plug-in.
 * The plug-in was written by the same author, specifically to interact with this NurbsPatchSurfacePrototype.
 * </p>
*/

public class NurbsPatchSurface extends vrml.node.Script {

    private MFVec3f controlPoint;
    private SFInt32 uTessellation;
    private SFInt32 vTessellation;
    private MFFloat weight;
    private SFBool  uClosed;
    private SFInt32 uDimension;
    private MFFloat uKnot;
    private SFInt32 uOrder;
    private SFBool  vClosed;
    private SFInt32 vDimension;
    private MFFloat vKnot;
    private SFInt32 vOrder;
    private MFVec3f coord;
    private MFInt32 coordIndex;
    private String errors[] = {"Invalid knot vector. Leave blank for automatic creation of uniform vector.", "Invalid weight vector. Leave blank for automatic creation.","Invalid knot vector. Values must be in non-decreasing order"};
    private Vector svec = new Vector(4);
    private MFVec3f controlPoint_changed;
    private SFInt32 uTessellation_changed;
    private SFInt32 vTessellation_changed;
    private MFFloat weight_changed;
    private int firsttime = 1;

    /**Static arrays for faster operation*/
    double StatS[][][][];
    double StatBS[][][][][];
    float Statsumuv[][][];
    float StatoldcontrolPoint[][];
    MFInt32 StattempcoordIndex = new MFInt32();
    /////////////////////////////////////

    public void initialize() {
	controlPoint = (MFVec3f)getField("controlPoint");
	uTessellation = (SFInt32)getField("uTessellation");
	vTessellation = (SFInt32)getField("vTessellation");
	weight = (MFFloat)getField("weight");
	uDimension = (SFInt32)getField("uDimension");
	uKnot = (MFFloat)getField("uKnot");
	uOrder = (SFInt32)getField("uOrder");
	vDimension = (SFInt32)getField("vDimension");
	vKnot = (MFFloat)getField("vKnot");
	vOrder = (SFInt32)getField("vOrder");
	coord = (MFVec3f)getEventOut("coord");
	coordIndex = (MFInt32)getEventOut("coordIndex");
	uClosed = (SFBool)getField("uClosed");
	vClosed = (SFBool)getField("vClosed");

	controlPoint_changed = (MFVec3f)getEventOut("controlPoint_changed");
	uTessellation_changed = (SFInt32)getEventOut("uTessellation_changed");
	vTessellation_changed = (SFInt32)getEventOut("vTessellation_changed");
	weight_changed = (MFFloat)getEventOut("weight_changed");
	
	try{
	    
	/** Determine tessellation value*/
	if(uTessellation.getValue() <= 0)
	    uTessellation.setValue(20);
	if(vTessellation.getValue() <= 0)
	    vTessellation.setValue(20);

	StatS = new double[uTessellation.getValue()][vTessellation.getValue()][uDimension.getValue()+1][vDimension.getValue()+1];
	StatBS = new double[uTessellation.getValue()][vTessellation.getValue()][uDimension.getValue()+1][vDimension.getValue()+1][3];
	Statsumuv = new float[uTessellation.getValue()][vTessellation.getValue()][3];
	StatoldcontrolPoint = new float[uDimension.getValue()*vDimension.getValue()][3];
	
	int exitcode = drawSurface();
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

    private int drawSurface() {

	try{

	/** Determine tessellation value*/
	if(uTessellation.getValue() <= 0)
	    uTessellation.setValue(20);
	if(vTessellation.getValue() <= 0)
	    vTessellation.setValue(20);

	/** Test size of knot vector. Create one if not specified*/
	if(checkKnotVector(uKnot, uOrder, uDimension) == 0)
	    return 0;
	if(checkKnotVector(vKnot, vOrder, vDimension) == 0)
	    return 0;
	
	/** Test size of weight vector. Create one if not specified*/
	if(weight.getSize() == 0) {
	    for(int i=0;i<controlPoint.getSize();i++)
		weight.addValue(1);
	}
	if(weight.getSize()!=controlPoint.getSize())
	    return 1;

	/** Determine t interval*/
	double utmin = uKnot.get1Value(0);
	double utmax = uKnot.get1Value(uKnot.getSize()-1);
	double utstep = (utmax - utmin)/(uTessellation.getValue()-1);
	double vtmin = vKnot.get1Value(0);
	double vtmax = vKnot.get1Value(vKnot.getSize()-1);
	double vtstep = (vtmax - vtmin)/(vTessellation.getValue()-1);

	/** Array to store N(u,i,k) values*/
	double N[][][] = new double[uTessellation.getValue()][uDimension.getValue()+uOrder.getValue()][uOrder.getValue()+1];

	/** Array to store M(v,j,l) values*/
	double M[][][] = new double[vTessellation.getValue()][vDimension.getValue()+vOrder.getValue()][vOrder.getValue()+1];

	/** Calculate N(u,i,k) and M(v,j,l).*/
	int uindex = 0;
	for(double u=utmin+((utmax-utmin)/100);u<utmax;u+=utstep) {
	    calcBasisFunction(uindex, u, N, uOrder, uKnot, uDimension);
	    uindex++;
	}
	calcBasisFunction(uindex, utmax-((utmax-utmin)/100), N, uOrder, uKnot, uDimension);

	int vindex = 0;
	for(double v=vtmin+((vtmax-vtmin)/1000);v<vtmax;v+=vtstep) { 
	    calcBasisFunction(vindex, v, M, vOrder, vKnot, vDimension);
	    vindex++;
	}
	calcBasisFunction(vindex, vtmax-((vtmax-vtmin)/1000), M, vOrder, vKnot, vDimension);

	/** Temporary storage for output points*/
	MFVec3f tempcoord = new MFVec3f();
	MFInt32 tempcoordIndex = new MFInt32();

	/** For u,v pair. */
	double S[][][][] = new double[uTessellation.getValue()][vTessellation.getValue()][uDimension.getValue()+1][vDimension.getValue()+1];
	double BS[][][][][] = new double[uTessellation.getValue()][vTessellation.getValue()][uDimension.getValue()+1][vDimension.getValue()+1][3];
	float sumuv[][][] = new float[uTessellation.getValue()][vTessellation.getValue()][3];
	float oldcontrolPoint[][] = new float[controlPoint.getSize()][3]; 
	controlPoint.getValue(oldcontrolPoint);
	for(uindex=0;uindex<uTessellation.getValue();uindex++) {
	    for(vindex=0;vindex<vTessellation.getValue();vindex++) {
		double sum = 0;
		for(int i=1;i<=uDimension.getValue();i++) {
		    for(int j=1;j<=vDimension.getValue();j++) {
			if(N[uindex][i][uOrder.getValue()]!=0) {
			    if(M[vindex][j][vOrder.getValue()]!=0) {
				S[uindex][vindex][i][j]  = (weight.get1Value(i-1 + ((j-1)*uDimension.getValue()))*N[uindex][i][uOrder.getValue()]*M[vindex][j][vOrder.getValue()]);
				sum += S[uindex][vindex][i][j];
			    }
			}
			else
			    S[uindex][vindex][i][j]  = 0;
		    }
		}

		/** Calculate S(i,j) for order k*/
		for(int i=1;i<=uDimension.getValue();i++) {
		    for(int j=1;j<=vDimension.getValue();j++) {
			if(sum==0) {
			    System.out.println("Sum is zero\n");
			    S[uindex][vindex][i][j] = 0;
			}
			else 
			    S[uindex][vindex][i][j] /= sum; 
		    }
		}

		/** Calculate Q(u,v)*/
		float ptsum[] = {0,0,0};
		for(int i=1;i<=uDimension.getValue();i++) {
		    for(int j=1;j<=vDimension.getValue();j++) {
			if(S[uindex][vindex][i][j] != 0) {
			    float pt[] = new float[3];
			    controlPoint.get1Value(i-1 + ((j-1)*uDimension.getValue()),pt);
			    for(int x=0;x<3;x++) {
				BS[uindex][vindex][i][j][x] = pt[x]*S[uindex][vindex][i][j];
				ptsum[x] += BS[uindex][vindex][i][j][x];
			    } 
			}
		    }
		}
		for(int x=0;x<3;x++)
		    sumuv[uindex][vindex][x] = ptsum[x];
		tempcoord.addValue(ptsum[0],ptsum[1],ptsum[2]);
	    }
	    svec.removeAllElements();
	    svec.addElement(S);
	    svec.addElement(BS);
	    svec.addElement(sumuv);
	    svec.addElement(oldcontrolPoint);
	    
	}

	
	/** Write out IndexedFaceSet data*/
	int ut = uTessellation.getValue();
	int vt = vTessellation.getValue();
	int base = -vt;
	int base2 = -vt+1;
	for(int i=1;i<vt;i++) {
	    for(int j=1;j<(ut);j++) {
		tempcoordIndex.addValue(base+vt);
		tempcoordIndex.addValue(base+(2*vt));
		tempcoordIndex.addValue(base+(2*vt)+1);
		tempcoordIndex.addValue(base+vt+1);
		tempcoordIndex.addValue(base+vt);
		tempcoordIndex.addValue(-1);
		base+=vt;
	    }
	    base = base2;
	    base2++;
	}
	if(uClosed.getValue() == true) {
	    base = -vt;
	    base2 = -vt+1;
	    for(int i=1;i<vt;i++) {
		tempcoordIndex.addValue(base+ut*vt);
		tempcoordIndex.addValue(base+vt);
		tempcoordIndex.addValue(base+vt+1);
		tempcoordIndex.addValue(base+ut*vt+1);
		tempcoordIndex.addValue(base+vt*vt);
		tempcoordIndex.addValue(-1);
		base = base2;
		base2++;
	    }
	}
	if(vClosed.getValue() == true) {
	    base = -vt;
	    base2 = -vt+1;
	    for(int i=1;i<vt;i++) {
		tempcoordIndex.addValue(base+ut*vt);
		tempcoordIndex.addValue(base+vt);
		tempcoordIndex.addValue(base+vt+1);
		tempcoordIndex.addValue(base+ut*vt+1);
		tempcoordIndex.addValue(base+vt*vt);
		tempcoordIndex.addValue(-1);
		base = base2;
		base2++;
	    }
	}
	coord.setValue(tempcoord);
	coordIndex.setValue(tempcoordIndex);
	svec.addElement(tempcoordIndex);

	//////////////////////////////////////////////////////////////////////////
	// CODE TO PRINT OUT ALL utess X vtess COORDS AND COORDINDEX VALUES
	/*
	for(int i=0;i<coord.getSize();i++) {
	    float pt[] = new float[3];
	    coord.get1Value(i,pt);
	    System.out.println(pt[0]+" "+pt[1]+" "+pt[2]+"\n");
	}
	
	for(int i=0;i<coordIndex.getSize();i+=6)
	    System.out.println(coordIndex.get1Value(i)+" "+coordIndex.get1Value(i+1)+" "+coordIndex.get1Value(i+2)+" "+coordIndex.get1Value(i+3)+" "+coordIndex.get1Value(i+4)+" "+coordIndex.get1Value(i+5)+" "+"\n");
	*/
	//////////////////////////////////////////////////////////////////////////


	/** If first time through, Fill static Arrays*/
	if(firsttime == 1) {
	    StatS = S;
	    StatBS = BS;
	    Statsumuv = sumuv;
	    StatoldcontrolPoint = oldcontrolPoint;
	    StattempcoordIndex.setValue(tempcoordIndex);
	    firsttime = 0;
	}
	////////////////////////////////////////////

	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node drawSurface method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	return -1;
    }

    private void recalcQ() {
	MFVec3f tempcoord = new MFVec3f();
	
	try{

	if(firsttime == 0) {
	    Vector chgptsi = new Vector();
	    Vector chgptsj = new Vector();
	    for(int i=1;i<=uDimension.getValue();i++) {
		for(int j=1;j<=vDimension.getValue();j++) {
		    float pt[] = new float[3];
		    controlPoint.get1Value(i-1 + ((j-1)*uDimension.getValue()),pt);
		    int diff = 0;
		    for(int x=0;x<3;x++) {
			if(pt[x]!=StatoldcontrolPoint[i-1 + ((j-1)*uDimension.getValue())][x])
			    diff = 1;
		    }
		    if(diff==1) {
			chgptsi.addElement(new Double((double)i));
			chgptsj.addElement(new Double((double)j));
		    }
		}
	    }
	    
	    for(int i=0;i<chgptsi.size();i++) {
		int iindex = (int)((Double)chgptsi.elementAt(i)).doubleValue();
		int jindex = (int)((Double)chgptsj.elementAt(i)).doubleValue();
		float pt[] = new float[3];
		controlPoint.get1Value(iindex-1 + ((jindex-1)*uDimension.getValue()),pt);
		
		for(int uindex=0;uindex<uTessellation.getValue();uindex++) {
		    for(int vindex=0;vindex<vTessellation.getValue();vindex++) {
			if(StatS[uindex][vindex][iindex][jindex] != 0) {
			    for(int x=0;x<3;x++) {
				Statsumuv[uindex][vindex][x] -= StatBS[uindex][vindex][iindex][jindex][x];
				StatBS[uindex][vindex][iindex][jindex][x] = pt[x]*StatS[uindex][vindex][iindex][jindex];
				Statsumuv[uindex][vindex][x] += StatBS[uindex][vindex][iindex][jindex][x];
			    }
			}
		    }
		}
	    }
	    
	    for(int uindex=0;uindex<uTessellation.getValue();uindex++) {
		for(int vindex=0;vindex<vTessellation.getValue();vindex++) 
		    tempcoord.addValue(Statsumuv[uindex][vindex][0],Statsumuv[uindex][vindex][1],Statsumuv[uindex][vindex][2]);
	    }
	    
	    controlPoint.getValue(StatoldcontrolPoint);
	    coord.setValue(tempcoord);
	    coordIndex.setValue(StattempcoordIndex);
	}

	else if(firsttime == -1) {
	    double S[][][][] = new double[uTessellation.getValue()][vTessellation.getValue()][uDimension.getValue()+1][vDimension.getValue()+1];
	    double BS[][][][][] = new double[uTessellation.getValue()][vTessellation.getValue()][uDimension.getValue()+1][vDimension.getValue()+1][3];
	    float sumuv[][][] = new float[uTessellation.getValue()][vTessellation.getValue()][3];
	    float oldcontrolPoint[][] = new float[uDimension.getValue()*vDimension.getValue()][3];
	    MFInt32 tempcoordIndex = new MFInt32();
	    
	    S = (double[][][][])svec.elementAt(0);
	    BS = (double[][][][][])svec.elementAt(1);
	    sumuv = (float[][][])svec.elementAt(2);
	    oldcontrolPoint = (float[][])svec.elementAt(3);
	    tempcoordIndex.setValue((MFInt32)svec.elementAt(4));
	    
	    Vector chgptsi = new Vector();
	    Vector chgptsj = new Vector();
	    for(int i=1;i<=uDimension.getValue();i++) {
		for(int j=1;j<=vDimension.getValue();j++) {
		    float pt[] = new float[3];
		    controlPoint.get1Value(i-1 + ((j-1)*uDimension.getValue()),pt);
		    int diff = 0;
		    for(int x=0;x<3;x++) {
			if(pt[x]!=oldcontrolPoint[i-1 + ((j-1)*uDimension.getValue())][x])
			    diff = 1;
		    }
		    if(diff==1) {
			chgptsi.addElement(new Double((double)i));
			chgptsj.addElement(new Double((double)j));
		    }
		}
	    }
	    
	    for(int i=0;i<chgptsi.size();i++) {
		int iindex = (int)((Double)chgptsi.elementAt(i)).doubleValue();
		int jindex = (int)((Double)chgptsj.elementAt(i)).doubleValue();
		float pt[] = new float[3];
		controlPoint.get1Value(iindex-1 + ((jindex-1)*uDimension.getValue()),pt);
		
		for(int uindex=0;uindex<uTessellation.getValue();uindex++) {
		    for(int vindex=0;vindex<vTessellation.getValue();vindex++) {
			if(S[uindex][vindex][iindex][jindex] != 0) {
			    for(int x=0;x<3;x++) {
				sumuv[uindex][vindex][x] -= BS[uindex][vindex][iindex][jindex][x];
				BS[uindex][vindex][iindex][jindex][x] = pt[x]*S[uindex][vindex][iindex][jindex];
				sumuv[uindex][vindex][x] += BS[uindex][vindex][iindex][jindex][x];
			    }
			}
		    }
		}
	    }
	    
	    for(int uindex=0;uindex<uTessellation.getValue();uindex++) {
		for(int vindex=0;vindex<vTessellation.getValue();vindex++) 
		    tempcoord.addValue(sumuv[uindex][vindex][0],sumuv[uindex][vindex][1],sumuv[uindex][vindex][2]);
	    }
	    
	    controlPoint.getValue(oldcontrolPoint);
	    svec.removeAllElements();
	    svec.addElement(S);
	    svec.addElement(BS);
	    svec.addElement(sumuv);
	    svec.addElement(oldcontrolPoint);
	    svec.addElement(tempcoordIndex);
	
	    coord.setValue(tempcoord);
	    coordIndex.setValue(tempcoordIndex);
	}

	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node recalcQ method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }

    }

    private int checkKnotVector(MFFloat knot, SFInt32 order, SFInt32 dimension) {
	try {

	if(knot.getSize()==0) {
	    for(int i=0;i<order.getValue();i++)
		knot.addValue(0);
	    for(int i=1;i<(dimension.getValue()-order.getValue()+1);i++)
		knot.addValue(i);
	    for(int i=0;i<order.getValue();i++)
		knot.addValue(dimension.getValue()-order.getValue()+1);
	}
	if(knot.getSize() != (dimension.getValue()+order.getValue()))
	    return 0;
	for(int i=0;i<knot.getSize()-1;i++) {
	    if(knot.get1Value(i)>knot.get1Value(i+1))
		return 2;
	}
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node checkKnotVector method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
	return -1;
    }	

    private void calcBasisFunction(int tindex, double t, double[][][] NN, SFInt32 order, MFFloat knot, SFInt32 dimension) {
	try{
	for(int k=1;k<=order.getValue();k++) {
	    for(int i=(dimension.getValue()+order.getValue()-k);i>0;i--) {
		if(k==1) {
		    if(t>=knot.get1Value(i-1) && t<knot.get1Value(i))
			NN[tindex][i][k] = 1;
		    else
			NN[tindex][i][k] = 0;
		}
		else {
		    double ps1=0, ps2=0;
		    double temp =  (knot.get1Value(i+k-2)-knot.get1Value(i-1));
		    if(temp!=0)
			ps1 = (((t-knot.get1Value(i-1))*NN[tindex][i][k-1])/temp);
		    temp = (knot.get1Value(i+k-1)-knot.get1Value(i));
		    if(temp!=0)
			ps2 = (((knot.get1Value(i+k-1)-t)*NN[tindex][i+1][k-1])/temp);
		    NN[tindex][i][k] =  ps1+ps2;
		}
	    }
	}
	}
	catch (Exception e)
	  {
	  	System.err.println ("Exception caught in Script node calcBasisFunction method:");
	  	System.err.println (e);
	  	e.printStackTrace();
	  }
    }

    public void processEvent(Event e) {
	String eventName = e.getName();
	int redrawAll = 0;
	
	try{
	if(eventName.equals("set_controlPoint")) {
	    MFVec3f temp = new MFVec3f();
	    temp.setValue((ConstMFVec3f)e.getValue());
	    if(temp.getSize() == controlPoint.getSize()) {
		controlPoint.setValue(temp);
		recalcQ();
	        controlPoint_changed.setValue(controlPoint);
	    }
	    else
		System.err.println("Cannot change size of controlPoint\n");
	}
	else if(eventName.equals("set_uTessellation")) {
	    uTessellation.setValue(((ConstSFInt32) e.getValue()).getValue());
	    uTessellation_changed.setValue(uTessellation);
	    redrawAll = 1;
	}
	else if(eventName.equals("set_vTessellation")) {
	    vTessellation.setValue(((ConstSFInt32) e.getValue()).getValue());
	    vTessellation_changed.setValue(vTessellation);
	    redrawAll = 1;
	}
	else if(eventName.equals("set_weight")) {
	    weight.setValue((ConstMFFloat)e.getValue());
	    weight_changed.setValue(weight);
	    redrawAll = 2;
	}
	if(redrawAll == 1) {
	    firsttime = -1;
	    int exitcode = drawSurface();
	    if(exitcode != -1)
		System.err.println(errors[exitcode]+"\n");
	}
	else if(redrawAll == 2) {
	    int exitcode = drawSurface();
	    if(exitcode != -1)
		System.err.println(errors[exitcode]+"\n");
	}
	}
	catch (Exception ee)
	  {
	  	System.err.println ("Exception caught in Script node processEvent method:");
	  	System.err.println (ee);
	  	ee.printStackTrace();
	  }
    }
}
