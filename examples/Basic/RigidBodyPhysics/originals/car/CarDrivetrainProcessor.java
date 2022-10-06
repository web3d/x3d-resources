/**
 * Handler for the toy car model processing. Takes the output from the control device
 * and applies it to the physics model.
 */
import org.web3d.x3d.sai.*;

import java.util.Map;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class CarDrivetrainProcessor implements X3DFieldEventListener {

    /** Field for steering wheel input from the sensor */
    private SFFloat wheelDirectionInput;

    /** Field for accelerator pedal input */
    private SFFloat wheelAcceleratorInput;

    /** Has someone selected reverse gear? */
    private SFBool reverseSelectedInput;

    /** Steering angle input from the joint */
    private SFFloat steeringAngleInput;

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

    /** Motor torque applied to the front wheel. */
    private SFFloat frontWheelTorque;

    /** Motor torque applied to the rear wheel. */
    private SFFloat rearWheelTorque;

    /** Field for the brake bias value */
    private SFFloat brakeBiasInput;

    /** Field for the engine bias value */
    private SFFloat engineFrontPercInput;

    /** Field for the engine bias value */
    private SFFloat engineRearPercInput;

    /** Field for the drag coefficient value */
    private SFFloat dragCoefficientInput;

    /** Field for the downforce coefficient value */
    private SFFloat downforceCoefficientInput;

    /** Input holding the current body rotation */
    private SFRotation bodyOrientation;

    /**
     * Output from the physics model indicating the car velocity
     * calculated from the last frame.
     */
    private SFVec3f carVelocity;

    /** Output of the calculated forces on the car - mainly air resistance */
    private MFVec3f outputForces;

    /** Output field for the speed of the car */
    private SFFloat speedOutput;

    /** Output field indicating the revs of the car */
    private SFFloat revsOutput;

    /** Output field indicating that brakes are currently applied */
    private SFBool  brakesAppliedOutput;

    /** Output field indicating the car is travelling in reverse */
    private SFBool  reversingOutput;

    /** Speed is negative */
    private boolean reversing;

    /** Amount of dead spot in the center of the steering */
    private float steeringDeadspot;

    /** The currently set amount of steering input */
    private float currentSteering;

    /** Maximum limit we'll let the user turn the wheel */
    private float steeringLimit;

    /**
     * Constant multiplication factor to adjust the control input value for
     * steering (in the range -1 to +1) to give the steering limits.
     */
    private float steeringCorrectionFactor;

    /** Maximum speed we'll let the user drive */
    private float speedLimit;

    /** Maximum amount of torque the engine could provide to each wheel */
    private float maxEngineTorque;

    /** Maximum amount of torque brakes can apply to each wheel */
    private float maxBrakingTorque;

    /** Maximum RPMs the engine can generate */
    private float maxEngineRevs;

    /** Bias of braking from the amount on the front versus back wheels */
    private float brakeBias;

    /** Bias of torque from the engine on the back versus front wheels */
    private float engineFrontPercentage;

    /** Bias of torque from the engine on the back versus front wheels */
    private float engineRearPercentage;

    /** Flag indicating if we're braking or accelerating right now */
    private boolean braking;

    /** Drag coeffficient currently set for the car */
    private float dragCoefficient;

    /** Drag coeffficient currently set for the car */
    private float downforceCoefficient;

    /** Working values get set here to pass to/from node fields */
    private float[] fieldValues;

    /** Values for setting the forces on the car */
    private float[] carForces;

    /** Utility for deriving the friction direction */
    private AxisAngle4f orientation;

    /** Rotation matrix from axisAngle */
    private Matrix3f rotation;

    /** Vector to be transformed */
    private Vector3f bodyVector;

    /**
     * Create a new car control script.
     */
    public CarDrivetrainProcessor() {
        fieldValues = new float[4];
        carForces = new float[6];
        braking = false;

        orientation = new AxisAngle4f();
        rotation = new Matrix3f();
        bodyVector = new Vector3f();

        downforceCoefficient = 0.4f;
        reversing = false;
    }

    /**
     * Initialise everything in the script now. All the nodes are valid, so
     * let's go for it.
     */
    public void initialize(Map fields) {
        wheelDirectionInput = (SFFloat)fields.get("wheelDirectionInput");
        wheelDirectionInput.addX3DEventListener(this);

        wheelAcceleratorInput = (SFFloat)fields.get("wheelAcceleratorInput");
        wheelAcceleratorInput.addX3DEventListener(this);

        reverseSelectedInput = (SFBool)fields.get("reverseSelected");
        reverseSelectedInput.addX3DEventListener(this);

        steeringAngleInput = (SFFloat)fields.get("currentSteeringAngle");

        bodyOrientation = (SFRotation)fields.get("carOrientation");

        carVelocity = (SFVec3f)fields.get("carVelocity");
        carVelocity.addX3DEventListener(this);

        outputForces = (MFVec3f)fields.get("frictionForces");
        speedOutput = (SFFloat)fields.get("speed");
        revsOutput = (SFFloat)fields.get("revs");
        brakesAppliedOutput = (SFBool)fields.get("brakesApplied");
        reversingOutput = (SFBool)fields.get("reversing");

        SFFloat field = (SFFloat)fields.get("steeringDeadspot");
        steeringDeadspot = field.getValue();

        field = (SFFloat)fields.get("steeringLimitAngle");
        steeringLimit = field.getValue();
        steeringCorrectionFactor = 1 / steeringLimit;

        field = (SFFloat)fields.get("speedLimit");
        speedLimit = field.getValue();

        field = (SFFloat)fields.get("maxEngineTorque");
        maxEngineTorque = field.getValue();

        field = (SFFloat)fields.get("maxBrakingTorque");
        maxBrakingTorque = field.getValue();

        field = (SFFloat)fields.get("maxEngineRevs");
        maxEngineRevs = field.getValue();

        brakeBiasInput = (SFFloat)fields.get("brakeBias");
        brakeBiasInput.addX3DEventListener(this);
        brakeBias = brakeBiasInput.getValue();

        engineFrontPercInput = (SFFloat)fields.get("engineFrontPercentage");
        engineFrontPercInput.addX3DEventListener(this);
        engineFrontPercentage = engineFrontPercInput.getValue();

        engineRearPercInput = (SFFloat)fields.get("engineRearPercentage");
        engineRearPercInput.addX3DEventListener(this);
        engineRearPercentage = engineRearPercInput.getValue();

        dragCoefficientInput = (SFFloat)fields.get("dragCoefficient");
        dragCoefficientInput.addX3DEventListener(this);
        dragCoefficient = dragCoefficientInput.getValue();

        downforceCoefficientInput = (SFFloat)fields.get("downforceCoefficient");
        downforceCoefficientInput.addX3DEventListener(this);
        downforceCoefficient = downforceCoefficientInput.getValue();

        frontWheelMinStop = (SFFloat)fields.get("frontWheelMinStop");
        frontWheelMaxStop = (SFFloat)fields.get("frontWheelMaxStop");
        frontLeftWheelSpeed = (SFFloat)fields.get("frontLeftWheelSpeed");
        frontRightWheelSpeed = (SFFloat)fields.get("frontRightWheelSpeed");
        rearLeftWheelSpeed = (SFFloat)fields.get("rearLeftWheelSpeed");
        rearRightWheelSpeed = (SFFloat)fields.get("rearRightWheelSpeed");

        rearWheelTorque = (SFFloat)fields.get("rearWheelTorque");
        frontWheelTorque = (SFFloat)fields.get("frontWheelTorque");
    }

    public void shutdown() {
        wheelDirectionInput.removeX3DEventListener(this);
        wheelAcceleratorInput.removeX3DEventListener(this);
        brakeBiasInput.removeX3DEventListener(this);
        engineFrontPercInput.removeX3DEventListener(this);
        engineRearPercInput.removeX3DEventListener(this);
    }

    //----------------------------------------------------------
    // Methods defined by X3DFieldEventListener
    //----------------------------------------------------------

    public void readableFieldChanged(X3DFieldEvent evt) {
        Object src = evt.getSource();

        if(src == wheelDirectionInput) {
            currentSteering = wheelDirectionInput.getValue();

            if(Math.abs(currentSteering) < steeringDeadspot)
                currentSteering = 0;

            currentSteering *= steeringLimit;

            frontWheelMinStop.setValue(currentSteering);
            frontWheelMaxStop.setValue(currentSteering);

        } else if(src == wheelAcceleratorInput) {
            float accelerator_pos = wheelAcceleratorInput.getValue();

            // Little tiny deadzone to handle the controller not coming right
            // back to zero.
            if(Math.abs(accelerator_pos) < 0.01f)
                accelerator_pos = 0;

            if(accelerator_pos < 0) {
                // Braking so try to slow the car down
                float brake_output = -accelerator_pos * maxBrakingTorque;

                rearWheelTorque.setValue(brake_output * (1 - brakeBias));
                frontWheelTorque.setValue(brake_output * brakeBias);

                frontLeftWheelSpeed.setValue(0);
                frontRightWheelSpeed.setValue(0);
                rearLeftWheelSpeed.setValue(0);
                rearRightWheelSpeed.setValue(0);

                if(!braking) {
                    braking = true;
                    brakesAppliedOutput.setValue(true);
                }
            } else if(accelerator_pos > 0) {
                float speed = (reversing ? -1 : 1) * accelerator_pos * speedLimit;
                frontLeftWheelSpeed.setValue( speed);
                frontRightWheelSpeed.setValue(-speed);
                rearLeftWheelSpeed.setValue( speed);
                rearRightWheelSpeed.setValue(-speed);

                float eng_output = accelerator_pos * maxEngineTorque;
                rearWheelTorque.setValue(eng_output * engineFrontPercentage);
                frontWheelTorque.setValue(eng_output * engineRearPercentage);

                if(braking) {
                    braking = false;
                    brakesAppliedOutput.setValue(false);
                }

            } else {
                // No pedal input, so coast to a stop if there is a air
                // resistance force being applied.
                rearWheelTorque.setValue(0);
                frontWheelTorque.setValue(0);

                if(braking) {
                    braking = false;
                    brakesAppliedOutput.setValue(false);
                }
            }
        } else if(src == carVelocity) {
            // air resistance is proportional to the square of the speed and in
            // the equal and opposite direction.

            carVelocity.getValue(fieldValues);
            float x = fieldValues[0];
            float y = fieldValues[1];
            float z = fieldValues[2];
            float speed = (float)Math.sqrt(x * x + y * y + z * z);
            float drag = dragCoefficient * speed * speed;
            float downforce = downforceCoefficient * speed * speed;

            if(drag == 0) {
                outputForces.setValue(0, carForces);
            } else {
                carForces[0] = -x * drag / speed;
                carForces[1] = -y * drag / speed;
                carForces[2] = -z * drag / speed;

                // Normalised the vector first to get our rotation matrix
                bodyOrientation.getValue(fieldValues);
                orientation.set(fieldValues);
                rotation.set(orientation);

                bodyVector.set(0, -1, 0);
                rotation.transform(bodyVector);

                carForces[3] = bodyVector.x * downforce;
                carForces[4] = bodyVector.y * downforce;
                carForces[5] = bodyVector.z * downforce;

                outputForces.setValue(2, carForces);
            }

            speedOutput.setValue(speed);

        } else if(src == reverseSelectedInput) {
            reversing = reverseSelectedInput.getValue();
            reversingOutput.setValue(reversing);
        } else if(src == brakeBiasInput) {
            brakeBias = brakeBiasInput.getValue();
        } else if(src == engineFrontPercInput) {
            engineFrontPercentage = engineFrontPercInput.getValue();
        } else if(src == engineRearPercInput) {
            engineRearPercentage = engineRearPercInput.getValue();
        } else if(src == dragCoefficientInput) {
            dragCoefficient = dragCoefficientInput.getValue();
        } else if(src == downforceCoefficientInput) {
            downforceCoefficient = downforceCoefficientInput.getValue();
        }
    }

    public void updateSteering() {
        frontWheelMinStop.setValue(currentSteering);
        frontWheelMaxStop.setValue(currentSteering);
    }
}