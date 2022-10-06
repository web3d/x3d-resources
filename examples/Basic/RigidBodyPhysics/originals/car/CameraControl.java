import java.util.*;
import java.io.*;

import org.web3d.x3d.sai.*;
import org.j3d.util.MatrixUtils;
import javax.vecmath.*;

/**
 * Control the camera.  Takes the center location and rotation of the
 * car and creates a rubberbanded camera.
 *
 * @author Alan Hudson
 */
public class CameraControl implements X3DScriptImplementation,
    X3DFieldEventListener {

    private Browser browser;

    private SFVec3f carPos;
    private SFVec3f cameraPos;
    private SFRotation cameraRot;
    private SFVec3f resetPosition;
    private SFRotation resetOrientation;

    private MatrixUtils mutils;

    float[] pos;
    float[] rot;
    private float[] eye;
    private Vector3f up = new Vector3f(0,1,0);
    private Vector3f behindCar = new Vector3f(0,0,1);

    private Point3f center = new Point3f();
    private Point3f tmpPnt = new Point3f();
    private Quat4f tmpQuat = new Quat4f();
    private Vector3f tmpVec = new Vector3f();
    private AxisAngle4f tmpAxis = new AxisAngle4f();
    private float[] lastPos = new float[3];

    private float height;
    private float maxLength;

    private Matrix4f result;

    private boolean firstPos;
    private boolean reset;

    public void setBrowser(Browser browser) {
        this.browser = browser;

        eye = new float[3];
        pos = new float[3];
        rot = new float[] {0, -1, 0, 2.2f};
        firstPos = false;
        reset = false;

        mutils = new MatrixUtils();
        result = new Matrix4f();
    }

    public void setFields(X3DScriptNode externalView, Map fields) {
        carPos = (SFVec3f) fields.get("carPosition");
        cameraPos = (SFVec3f) fields.get("cameraTranslation");
        cameraRot = (SFRotation) fields.get("cameraRotation");
        resetPosition = (SFVec3f) fields.get("resetPosition");
        resetOrientation = (SFRotation) fields.get("resetOrientation");

        SFFloat heightField = (SFFloat) fields.get("height");
        height = heightField.getValue();
        SFFloat maxLengthField = (SFFloat) fields.get("chainLength");
        maxLength = maxLengthField.getValue();

        carPos.addX3DEventListener(this);
        resetPosition.addX3DEventListener(this);
        resetOrientation.addX3DEventListener(this);
    }

    public void initialize() {
    }

    public void eventsProcessed() {
       if (reset == true) {
System.out.println("Reset");
          resetPosition.getValue(pos);
          resetOrientation.getValue(rot);

          center.x = pos[0];
          center.y = pos[1];
          center.z = pos[2];

          result.setIdentity();
          tmpAxis.set(rot);

          result.set(tmpAxis);
          tmpVec.set(behindCar);

          result.transform(tmpVec);

          pos[0] += tmpVec.x * maxLength;
          pos[1] += height * 2.5;
          pos[2] += tmpVec.z * maxLength;

          cameraPos.setValue(pos);

          lastPos[0] = pos[0];
          lastPos[1] = pos[1];
          lastPos[2] = pos[2];

          tmpPnt.x = pos[0];
          tmpPnt.y = pos[1];
          tmpPnt.z = pos[2];


           mutils.lookAt(tmpPnt, center, up, result);
           result.invert();

           result.get(tmpVec);
           result.get(tmpQuat);
           tmpAxis.set(tmpQuat);
           if (rot[3] == 0) {
              // Got a zero, force the VP away
              // Maybe use reset orientation?
              lastPos[0] = pos[0] - maxLength * 1.5f;
              lastPos[2] = pos[2] - maxLength * 1.5f;
           }
           rot[0] = tmpAxis.x;
           rot[1] = tmpAxis.y;
           rot[2] = tmpAxis.z;
           rot[3] = tmpAxis.angle;

           cameraRot.setValue(rot);
           reset = false;
       }

    }

    public void shutdown() {
    }

    public void readableFieldChanged(X3DFieldEvent evt) {
        X3DField field = (X3DField) evt.getSource();

        if (field == carPos) {
           carPos.getValue(pos);

           center.x = pos[0];
           center.y = pos[1];
           center.z = pos[2];

           if (firstPos) {
              lastPos[0] = pos[0] - maxLength * 1.5f;
              lastPos[2] = pos[2] - maxLength * 1.5f;

              firstPos = false;
           }

           float xd = pos[0] - lastPos[0];
           float zd = pos[2] - lastPos[2];

           float len = (float) Math.sqrt(xd*xd + zd*zd);
           float t;
           float tn;

           if (len > maxLength) {
               t = (len / maxLength) - 1;

               if (t > 1)
                  t = 0.92f;

               tn = 1.0f - t;

               float x = pos[0] * t + (tn) * lastPos[0];
               float z = pos[2] * t + (tn) * lastPos[2];

               pos[0] = x;
               pos[1] += height;
               pos[2] = z;

               lastPos[0] = pos[0];
               lastPos[1] = pos[1];
               lastPos[2] = pos[2];
               cameraPos.setValue(pos);
           }

           tmpPnt.x = lastPos[0];
           tmpPnt.y = lastPos[1];
           tmpPnt.z = lastPos[2];


           mutils.lookAt(tmpPnt, center, up, result);
           result.invert();

           result.get(tmpVec);
           result.get(tmpQuat);
           tmpAxis.set(tmpQuat);
           if (rot[3] == 0) {
              // Got a zero, force the VP away
              // Maybe use reset orientation?
              lastPos[0] = pos[0] - maxLength * 1.5f;
              lastPos[2] = pos[2] - maxLength * 1.5f;
           }
           rot[0] = tmpAxis.x;
           rot[1] = tmpAxis.y;
           rot[2] = tmpAxis.z;
           rot[3] = tmpAxis.angle;


           cameraRot.setValue(rot);
       } else if (field == resetPosition) {
           reset = true;
       } else if (field == resetOrientation) {
           reset = true;
       }
    }
}

