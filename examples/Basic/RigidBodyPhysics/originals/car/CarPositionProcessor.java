/**
 * Handler for the toy car model positions. Takes the base position and sets up
 * the location of the wheels, bodies etc.
 */
import org.web3d.x3d.sai.*;

import java.util.Map;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class CarPositionProcessor implements X3DFieldEventListener {

    private SFVec3f positionInput;
    private SFRotation orientationInput;

    /** Trigger to set a new location */
    private SFBool resetRequestInput;

    /** Flag to control setting the physics model to active or inactive */
    private SFBool enablePhysicsOutput;

    /** Output direction for the axles on the front */
    private SFVec3f frontLeftAxleAxis;

    /** Output direction for the axles on the front */
    private SFVec3f frontRightAxleAxis;

    /** Output direction for the axles on the rear */
    private SFVec3f rearLeftAxleAxis;

    /** Output direction for the axles on the rear */
    private SFVec3f rearRightAxleAxis;

    private MFNode connectJointsOutput;
    private SFVec3f chassisPositionOutput;
    private SFRotation chassisOrientationOutput;

    private SFVec3f frontLeftWheelPositionOutput;
    private SFRotation frontLeftWheelOrientationOutput;
    private SFVec3f frontRightWheelPositionOutput;
    private SFRotation frontRightWheelOrientationOutput;
    private SFVec3f backLeftWheelPositionOutput;
    private SFRotation backLeftWheelOrientationOutput;
    private SFVec3f backRightWheelPositionOutput;
    private SFRotation backRightWheelOrientationOutput;

    /** Output to turn the front wheels */
    private SFFloat frontWheelMinStop;

    /** Output to turn the front wheels */
    private SFFloat frontWheelMaxStop;

    /** Motor velocity applied to the front wheel. */
    private SFFloat frontLeftWheelSpeed;

    /** Motor velocity applied to the front wheel. */
    private SFFloat frontRightWheelSpeed;

    /** Motor velocity applied to the rear wheel. */
    private SFFloat rearLeftWheelSpeed;

    /** Motor velocity applied to the rear wheel. */
    private SFFloat rearRightWheelSpeed;

    private X3DNode[] joints;

    /** Total number of joints */
    private int numJoints;

    /** Total number of bodies */
    private int numBodies;

    /** Orientation field for each body */
    private SFVec3f[] linearVelocities;

    /** Orientation field for each body */
    private SFVec3f[] angularVelocities;

    /** Orientation field for each body */
    private MFVec3f[] forces;

    /** Orientation field for each body */
    private MFVec3f[] torques;

    /** Working values get set here to pass to/from node fields */
    private float[] fieldValues;

    /** dimensions of the wheel geometry */
    private float wheelRadius;

    /** dimensions of the modelled chassis */
    private float[] bodyDimensions;

    /** Utility for deriving the friction direction */
    private AxisAngle4f orientation;

    /** Rotation matrix from axisAngle */
    private Matrix3f rotation;

    /** Vector to be transformed */
    private Vector3f upVector;

    /** Vector to be transformed */
    private Vector3f bodyPosition;

    /** Flag indicating that the position was changed last frame */
    private boolean positionChanged;

    /**
     * Create a new car control script.
     */
    public CarPositionProcessor() {
        fieldValues = new float[4];
        wheelRadius = 0.5f;
        bodyDimensions = new float[3];

        orientation = new AxisAngle4f();
        rotation = new Matrix3f();
        upVector = new Vector3f(0, 1, 0);
        bodyPosition = new Vector3f();

        positionChanged = false;
    }

    /**
     * Initialise everything in the script now. All the nodes are valid, so
     * let's go for it.
     */
    public void initialize(Map fields) {

        positionInput = (SFVec3f)fields.get("startingPosition");

        resetRequestInput = (SFBool)fields.get("resetRequest");
        resetRequestInput.addX3DEventListener(this);

        orientationInput = (SFRotation)fields.get("startingOrientation");

        // Fetch size constants.
        SFFloat f_field = (SFFloat)fields.get("wheelBodyRadius");
        wheelRadius = f_field.getValue();

        SFVec3f v_field = (SFVec3f)fields.get("chassisBodyDimensions");
        v_field.getValue(bodyDimensions);

        MFNode n_field = (MFNode)fields.get("bodies");
        numBodies = n_field.size();

        linearVelocities = new SFVec3f[numBodies];
        angularVelocities = new SFVec3f[numBodies];
        forces = new MFVec3f[numBodies];
        torques = new MFVec3f[numBodies];

        for(int i = 0; i < numBodies; i++) {
            X3DNode body = n_field.get1Value(i);
            linearVelocities[i] = (SFVec3f)body.getField("linearVelocity");
            angularVelocities[i] = (SFVec3f)body.getField("angularVelocity");
            forces[i] = (MFVec3f)body.getField("forces");
            torques[i] = (MFVec3f)body.getField("torques");
        }

        n_field = (MFNode)fields.get("joints");
        numJoints = n_field.size();
        joints = new X3DNode[numJoints];
        n_field.getValue(joints);

        // Fetch the output fields.
        enablePhysicsOutput = (SFBool)fields.get("enablePhysics");

        frontLeftAxleAxis = (SFVec3f)fields.get("frontLeftAxleAxis");
        frontRightAxleAxis = (SFVec3f)fields.get("frontRightAxleAxis");
        rearLeftAxleAxis = (SFVec3f)fields.get("rearLeftAxleAxis");
        rearRightAxleAxis = (SFVec3f)fields.get("rearRightAxleAxis");

        chassisPositionOutput = (SFVec3f)fields.get("chassisPosition_reset");
        chassisOrientationOutput = (SFRotation)fields.get("chassisOrientation_reset");

        connectJointsOutput = (MFNode)fields.get("connectJoints");

        frontLeftWheelPositionOutput = (SFVec3f)fields.get("frontLeftWheelPosition_reset");
        frontLeftWheelOrientationOutput = (SFRotation)fields.get("frontLeftWheelOrientation_reset");
        frontRightWheelPositionOutput = (SFVec3f)fields.get("frontRightWheelPosition_reset");
        frontRightWheelOrientationOutput = (SFRotation)fields.get("frontRightWheelOrientation_reset");
        backLeftWheelPositionOutput = (SFVec3f)fields.get("backLeftWheelPosition_reset");
        backLeftWheelOrientationOutput = (SFRotation)fields.get("backLeftWheelOrientation_reset");
        backRightWheelPositionOutput = (SFVec3f)fields.get("backRightWheelPosition_reset");
        backRightWheelOrientationOutput = (SFRotation)fields.get("backRightWheelOrientation_reset");
        frontWheelMinStop = (SFFloat)fields.get("frontWheelMinStop");
        frontWheelMaxStop = (SFFloat)fields.get("frontWheelMaxStop");
        frontLeftWheelSpeed = (SFFloat)fields.get("frontLeftWheelSpeed");
        frontRightWheelSpeed = (SFFloat)fields.get("frontRightWheelSpeed");
        rearLeftWheelSpeed = (SFFloat)fields.get("rearLeftWheelSpeed");
        rearRightWheelSpeed = (SFFloat)fields.get("rearRightWheelSpeed");

        setupPosition();
    }

    public void shutdown() {
    }
    //----------------------------------------------------------
    // Methods defined by X3DFieldEventListener
    //----------------------------------------------------------

    public void readableFieldChanged(X3DFieldEvent evt) {
        setupPosition();
    }

    /**
     * Update the position now
     */
    private void setupPosition() {
        // Turn off physics for this frame. Turn it on next frame after the
        // joints have been added.

        enablePhysicsOutput.setValue(false);

        // set up the basic rotation matrix
        orientationInput.getValue(fieldValues);
        orientation.set(fieldValues);
        rotation.set(orientation);

        chassisOrientationOutput.setValue(fieldValues);
        frontLeftWheelOrientationOutput.setValue(fieldValues);
        frontRightWheelOrientationOutput.setValue(fieldValues);
        backLeftWheelOrientationOutput.setValue(fieldValues);
        backRightWheelOrientationOutput.setValue(fieldValues);

        frontLeftWheelSpeed.setValue(0);
        frontRightWheelSpeed.setValue(0);
        rearLeftWheelSpeed.setValue(0);
        rearRightWheelSpeed.setValue(0);
        frontWheelMinStop.setValue(0);
        frontWheelMaxStop.setValue(0);

        // Update the angles for the axles. Initial direction is along the +X
        // axis, so re-orient to suit that.
        bodyPosition.set(1, 0, 0);
        rotation.transform(bodyPosition);
        bodyPosition.get(fieldValues);
        frontLeftAxleAxis.setValue(fieldValues);
        rearLeftAxleAxis.setValue(fieldValues);

        bodyPosition.set(-1, 0, 0);
        rotation.transform(bodyPosition);
        bodyPosition.get(fieldValues);
        frontRightAxleAxis.setValue(fieldValues);
        rearRightAxleAxis.setValue(fieldValues);

        positionInput.getValue(fieldValues);

        // Body position is center + axle offset
        float x = fieldValues[0];
        float y = fieldValues[1];
        float z = fieldValues[2];

        fieldValues[1] += wheelRadius * 0.3f;

        chassisPositionOutput.setValue(fieldValues);

        // correct the height again

        // front left
        bodyPosition.set(-bodyDimensions[0] * 0.5f,
                         wheelRadius,
                         -bodyDimensions[2] * 0.5f);
        rotation.transform(bodyPosition);

        fieldValues[0] = bodyPosition.x + x;
        fieldValues[1] = bodyPosition.y + y;
        fieldValues[2] = bodyPosition.z + z;
        frontLeftWheelPositionOutput.setValue(fieldValues);

        // front right
        bodyPosition.set(bodyDimensions[0] * 0.5f,
                         wheelRadius,
                         -bodyDimensions[2] * 0.5f);
        rotation.transform(bodyPosition);

        fieldValues[0] = bodyPosition.x + x;
        fieldValues[1] = bodyPosition.y + y;
        fieldValues[2] = bodyPosition.z + z;
        frontRightWheelPositionOutput.setValue(fieldValues);

        // back left
        bodyPosition.set(-bodyDimensions[0] * 0.5f,
                         wheelRadius,
                         bodyDimensions[2] * 0.5f);
        rotation.transform(bodyPosition);

        fieldValues[0] = bodyPosition.x + x;
        fieldValues[1] = bodyPosition.y + y;
        fieldValues[2] = bodyPosition.z + z;
        backLeftWheelPositionOutput.setValue(fieldValues);

        // back right
        bodyPosition.set(bodyDimensions[0] * 0.5f,
                         wheelRadius,
                         bodyDimensions[2] * 0.5f);
        rotation.transform(bodyPosition);

        fieldValues[0] = bodyPosition.x + x;
        fieldValues[1] = bodyPosition.y + y;
        fieldValues[2] = bodyPosition.z + z;
        backRightWheelPositionOutput.setValue(fieldValues);

        fieldValues[0] = 0;
        fieldValues[1] = 0;
        fieldValues[2] = 0;
        for(int i = 0; i < numBodies; i++) {
            linearVelocities[i].setValue(fieldValues);
            angularVelocities[i].setValue(fieldValues);
            forces[i].setValue(0, fieldValues);
            torques[i].setValue(0, fieldValues);
        }

        positionChanged = true;
    }

    public void finishPositionUpdate() {
        if(!positionChanged)
            return;

        connectJointsOutput.setValue(numJoints, joints);
        enablePhysicsOutput.setValue(true);
        positionChanged = false;
    }
}