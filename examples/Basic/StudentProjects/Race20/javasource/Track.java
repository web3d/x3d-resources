package Race;
import java.lang.*;
import java.math.*;
import Race.*;

public class Track {
    public double length = 2.0, width = 2.0;
    public double px = 0.0 , pz = 0.0;
    public double elevation = 0.0;
    public double tilt[] = {0,0,1,0};
    public TrackType track = new TrackType();
    
    public Track(){};
    public boolean isInside(double cpx, double cpz) {
	//System.out.println("Checking if inside");
	if((Math.abs(cpx-this.px)<=(this.width/2)+.2)&&(Math.abs(cpz-this.pz)<=(this.length/2)+.2))
	    return true;
	else
	    return false;
    }
    public int didCollide(double collTranslation[], double cpx, double cpz, double npx, double npz, int collide[]) {
	collide[0] = 0;
	collide[1] = 0;
 	double V[] = {npx-cpx,npz-cpz};
	double Vl = Math.sqrt(Math.pow(V[0],2)+Math.pow(V[1],2));
	int returnvalue = 0;

	for(int i=0;i<this.track.xcpfreepointer;i++) {
	    double xcp = rel2abs(this.track.xcollisionPlanes[i],0);
	    if(((V[0]>0) && (xcp-cpx>=0)) || ((V[0]<0) && (xcp-cpx<=0))) {
		double H[] = {xcp,((V[1]/V[0])*xcp)+cpz-((V[1]/V[0])*cpx)};
		if(this.isInside(H[0],H[1])!=true) {
		    collTranslation[0] = H[0];
		    collTranslation[2] = H[1];
		    returnvalue = 1;
		}
		H[0] -= cpx;
		H[1] -= cpz;
		double Hl = Math.sqrt(Math.pow(H[0],2)+Math.pow(H[1],2));
		if(Hl <= Vl) 
		    collide[0]=1;
		//}
		//else {
		// collTranslation[0] = H[0];
		//collTranslation[2] = H[1];
		//returnvalue = 1;
		//}
	    }
	}
	for(int i=0;i<this.track.zcpfreepointer;i++) {
	    double zcp = rel2abs(this.track.zcollisionPlanes[i],1);
	    if(((V[1]>0) && (zcp-cpz>=0)) || ((V[1]<0) && (zcp-cpz<=0))) {
		double H[] = {(zcp-(cpz-((V[1]/V[0])*cpx)))/(V[1]/V[0]),zcp};
		if(this.isInside(H[0],H[1])!=true) {
		    collTranslation[0] = H[0];
		    collTranslation[2] = H[1];
		    if(returnvalue==1)
			returnvalue = 3;
		    else
			returnvalue = 2;
		}
		H[0] -= cpx;
		H[1] -= cpz;
		double Hl = Math.sqrt(Math.pow(H[0],2)+Math.pow(H[1],2));
		if(Hl <= Vl) 
		    collide[1]=1;
		//	}
		//		else {
		//collTranslation[0] = H[0];
		//collTranslation[2] = H[1];
		//if(returnvalue==1)
		//	returnvalue = 3;
		// else
		//returnvalue = 2;
		//}
	    }
	}
	for(int i=0;i<this.track.numBBoxes;i++) {
	    double BBpx = rel2abs(this.track.pxBBoxes[i],0);
	    double BBpz = rel2abs(this.track.pzBBoxes[i],0);
	    //System.out.println("coords "+cpx+" "+cpz+"\n");
	    if(((V[0]>0) && (BBpx-cpx>=0)) || ((V[0]<0) && (BBpx-cpx<=0))) {
		double H[] = {BBpx,((V[1]/V[0])*BBpx)+cpz-((V[1]/V[0])*cpx)};
		//System.out.println("hit x"+H[0]+" "+H[1]+"\n");
		if(this.isInside(H[0],H[1])==true) {
		    if((Math.abs(H[0]-BBpx)<(this.track.widthBBoxes[i]/2))&&(Math.abs(H[1]-BBpz)<(this.track.lengthBBoxes[i]/2))) {
			H[0] -= cpx;
			H[1] -= cpz;
			double Hl = Math.sqrt(Math.pow(H[0],2)+Math.pow(H[1],2));
			if(Hl <= Vl) 
			    collide[0]=1;
		    }
		}
		else {
		    collTranslation[0] = H[0];
		    collTranslation[2] = H[1];
		    returnvalue = 1;
		}
	    }
	    if(((V[1]>0) && (BBpz-cpx>=0)) || ((V[1]<0) && (BBpz-cpx<=0))) {
		double H[] = {(BBpz-(cpz-((V[1]/V[0])*cpx)))/(V[1]/V[0]),BBpz};
		//System.out.println("hit z"+H[0]+" "+H[1]+"\n");
		if(this.isInside(H[0],H[1])==true) {
		    if((Math.abs(H[0]-BBpx)<(this.track.widthBBoxes[i]/2))&&(Math.abs(H[1]-BBpz)<(this.track.lengthBBoxes[i]/2))) {
			H[0] -= cpx;
			H[1] -= cpz;
			double Hl = Math.sqrt(Math.pow(H[0],2)+Math.pow(H[1],2));
			if(Hl <= Vl) 
			    collide[1]=1;
		    }
		}
		else {
		    collTranslation[0] = H[0];
		    collTranslation[2] = H[1];
		    if(returnvalue==1)
			returnvalue = 3;
		    else
			returnvalue = 2;
		}
	    }
	}
	return returnvalue;
    }
    private void abs2rel(double cpx, double cpz, double rel[]) {
	rel[0] = (cpx - (this.px - (.5*this.width)))/this.width;
	rel[1] = (cpz - (this.pz - (.5*this.length)))/this.length;
    }
    private double rel2abs(double pos, int dir) {
	if(dir==0)
	    return ((pos*this.width)+this.px-(.5*this.width));
	else if(dir==1)
	    return ((pos*this.length)+this.pz-(.5*this.length));
	return 0.0;
    }
}
