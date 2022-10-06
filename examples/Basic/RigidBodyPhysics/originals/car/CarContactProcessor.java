/**
 * Handler for the toy car model processing. Takes the output from the collision
 * detection system and applies it to the physics model.
 */
import org.web3d.x3d.sai.*;

import java.util.HashSet;
import java.util.Map;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class CarContactProcessor implements X3DFieldEventListener {

    /**
     * The set of parameters we want to apply to ground contacts with
     * the wheels.
     */
    private static final String[] WHEEL_GROUND_PARAMS = {
        "BOUNCE", "SLIP-2", "USER-FRICTION"
    };

    /**
     * The set of parameters we want to apply to any other geometry contacts
     * with the wheels or chassis geom.
     */
    private static final String[] WHEEL_GEOM_PARAMS = {
        "BOUNCE", "ERROR_REDUCTION", "CONSTANT_FORCE"
    };

    /** Input field with the contacts from the collision system */
    private MFNode contactInput;

    /** Output field for sending contacts to the rigid body system */
    private MFNode contactOutput;

    /** Input holding the last valid front wheel orientation axleVector */
    private SFVec3f frontWheelOrientation;

    /** Input holding the last valid rear wheel orientation axleVector */
    private SFVec3f rearWheelOrientation;

    /** InputOutput field holding the current road friction coefficient */
    private SFFloat roadFrictionInput;

    /** InputOutput field holding the current wall friction coefficient */
    private SFFloat wallFrictionInput;

    /** InputOutput field holding the current sand friction coefficient */
    private SFFloat sandFrictionInput;

    /** InputOutput field holding the current grass friction coefficient */
    private SFFloat grassFrictionInput;

    /** InputOutput field holding the current generic friction coefficient */
    private SFFloat otherFrictionInput;

    /** InputOutput field holding the current side slip coefficient */
    private SFFloat sideSlipInput;

    /** Collection of everything that is walls terrain */
    private HashSet grassObjects;

    /** Collection of everything that is walls terrain */
    private HashSet roadObjects;

    /** Collection of everything that is walls terrain */
    private HashSet sandObjects;

    /** Collection of everything that is walls terrain */
    private HashSet wallObjects;

    /** Collection of everything that is other terrain */
    private HashSet terrainObjects;

    /** Collection of the 4 wheels */
    private HashSet wheels;

    /** Node representing front left wheel */
    private X3DNode frontLeftWheel;

    /** Node representing front right wheel */
    private X3DNode frontRightWheel;

    /** Node representing back left wheel */
    private X3DNode backLeftWheel;

    /** Node representing back right wheel */
    private X3DNode backRightWheel;

    /** Node representing the chassis */
    private X3DNode chassis;

    // Constants derived from the script fields

    /** Amount of friction that road to wheel has */
    private float roadFrictionCoefficient;

    /** Amount of friction that wall to wheel or chassis has */
    private float wallFrictionCoefficient;

    /** Amount of friction that road to sand has */
    private float sandFrictionCoefficient;

    /** Amount of friction that road to grass has */
    private float grassFrictionCoefficient;

    /** Amount of friction for anything not in the above categories */
    private float otherFrictionCoefficient;

    /** Amount of side slip we can have in turning */
    private float sideSlipCoefficient;

    /** Working values get set here to pass to/from node fields */
    private float[] fieldValues;

    /** An array used to transfer contact lists */
    private X3DNode[] processedContacts;

    /** Utility for deriving the friction direction */
    private AxisAngle4f orientation;

    /** Rotation matrix from axisAngle */
    private Matrix3f rotation;

    /** Vector to be transformed */
    private Vector3f axleVector;

    /** Vector to be transformed */
    private Vector3f bodyVector;

    /**
     * Output from the physics model indicating the car velocity
     * calculated from the last frame.
     */
    private SFVec3f carVelocity;

    /**
     * Create a new car control script.
     */
    public CarContactProcessor() {
        terrainObjects = new HashSet();
        grassObjects = new HashSet();
        roadObjects = new HashSet();
        sandObjects = new HashSet();
        wallObjects = new HashSet();
        wheels = new HashSet();

        fieldValues = new float[4];
        processedContacts = new X3DNode[1024];
        orientation = new AxisAngle4f();
        rotation = new Matrix3f();
        axleVector = new Vector3f();
        bodyVector = new Vector3f();
    }

    /**
     * Initialise everything in the script now. All the nodes are valid, so
     * let's go for it.
     */
    public void initialize(Map fields) {

        contactInput = (MFNode)fields.get("collisionContacts");
        contactInput.addX3DEventListener(this);

        contactOutput = (MFNode)fields.get("correctedContacts");

        rearWheelOrientation = (SFVec3f)fields.get("rearWheelOrientation");
        frontWheelOrientation = (SFVec3f)fields.get("frontWheelOrientation");

        carVelocity = (SFVec3f)fields.get("carVelocity");

        // Fetch the constants
        roadFrictionInput = (SFFloat)fields.get("roadFrictionCoefficient");
        roadFrictionInput.addX3DEventListener(this);
        roadFrictionCoefficient = roadFrictionInput.getValue();

        wallFrictionInput = (SFFloat)fields.get("wallFrictionCoefficient");
        wallFrictionInput.addX3DEventListener(this);
        wallFrictionCoefficient = wallFrictionInput.getValue();

        sandFrictionInput = (SFFloat)fields.get("sandFrictionCoefficient");
        sandFrictionInput.addX3DEventListener(this);
        sandFrictionCoefficient = sandFrictionInput.getValue();

        grassFrictionInput = (SFFloat)fields.get("grassFrictionCoefficient");
        grassFrictionInput.addX3DEventListener(this);
        grassFrictionCoefficient = grassFrictionInput.getValue();

        otherFrictionInput = (SFFloat)fields.get("otherFrictionCoefficient");
        otherFrictionInput.addX3DEventListener(this);
        otherFrictionCoefficient = otherFrictionInput.getValue();

        sideSlipInput = (SFFloat)fields.get("sideSlipCoefficient");
        sideSlipInput.addX3DEventListener(this);
        sideSlipCoefficient = sideSlipInput.getValue();

        // Prepare the terrain objects
        MFNode terrain_field = (MFNode)fields.get("bigTerrainBox");
        int num_terrain = terrain_field.size();

System.out.println("num general " + num_terrain);
        for(int i = 0; i < num_terrain; i++)
            terrainObjects.add(terrain_field.get1Value(i));

        terrain_field = (MFNode)fields.get("roadTerrain");
        num_terrain = terrain_field.size();

System.out.println("num roads " + num_terrain);
        for(int i = 0; i < num_terrain; i++)
            roadObjects.add(terrain_field.get1Value(i));

        terrain_field = (MFNode)fields.get("grassTerrain");
        num_terrain = terrain_field.size();

System.out.println("num grass " + num_terrain);
        for(int i = 0; i < num_terrain; i++)
            grassObjects.add(terrain_field.get1Value(i));

        terrain_field = (MFNode)fields.get("sandTerrain");
        num_terrain = terrain_field.size();

System.out.println("num sand " + num_terrain);

        for(int i = 0; i < num_terrain; i++)
            sandObjects.add(terrain_field.get1Value(i));

        terrain_field = (MFNode)fields.get("wallTerrain");
        num_terrain = terrain_field.size();

System.out.println("num walls " + num_terrain);

        for(int i = 0; i < num_terrain; i++)
            wallObjects.add(terrain_field.get1Value(i));

        // Bits of car stuff.
        SFNode wheel = (SFNode)fields.get("frontLeftWheel");
        frontLeftWheel = wheel.getValue();
        wheels.add(frontLeftWheel);

        wheel = (SFNode)fields.get("frontRightWheel");
        frontRightWheel = wheel.getValue();
        wheels.add(frontRightWheel);

        wheel = (SFNode)fields.get("backLeftWheel");
        backLeftWheel = wheel.getValue();
        wheels.add(backLeftWheel);

        wheel = (SFNode)fields.get("backRightWheel");
        backRightWheel = wheel.getValue();
        wheels.add(backRightWheel);

        wheel = (SFNode)fields.get("chassis");
        chassis = wheel.getValue();
    }

    public void shutdown() {
        contactInput.removeX3DEventListener(this);
    }

    //----------------------------------------------------------
    // Methods defined by X3DFieldEventListener
    //----------------------------------------------------------

    /**
     * Process an incoming field event now. Since this is only for the contact
     * list, just head straight into processing that.
     */
    public void readableFieldChanged(X3DFieldEvent evt) {
        Object src = evt.getSource();
        if(src == contactInput)
            processContacts();
        else if(src == roadFrictionInput)
            roadFrictionCoefficient = roadFrictionInput.getValue();
        else if(src == wallFrictionInput)
            wallFrictionCoefficient = wallFrictionInput.getValue();
        else if(src == sandFrictionInput)
            sandFrictionCoefficient = sandFrictionInput.getValue();
        else if(src == grassFrictionInput)
            grassFrictionCoefficient = grassFrictionInput.getValue();
        else if(src == otherFrictionInput)
            otherFrictionCoefficient = otherFrictionInput.getValue();
        else if(src == sideSlipInput)
            sideSlipCoefficient = sideSlipInput.getValue();
    }

    /**
     * Process the contact list for this frame.
     */
    private void processContacts() {
        int num_contacts = contactInput.size();

        if(processedContacts.length < num_contacts)
            processedContacts = new X3DNode[num_contacts];

        int contact_count = 0;

        for(int i = 0; i < num_contacts; i++) {
            X3DNode contact = contactInput.get1Value(i);

            SFNode nf = (SFNode)contact.getField("geometry1");
            X3DNode geom1 = nf.getValue();

            nf = (SFNode)contact.getField("geometry2");
            X3DNode geom2 = nf.getValue();

            // Check to see if we have a contact with the ground. If we do,
            // then that's most likely a wheel, so we want to do some
            // processing on it to set the friction direction and slip-2 value.
            // If so, process and then immediately move onto the next contact.

            if(roadObjects.contains(geom1) || roadObjects.contains(geom2)) {
//System.out.println("road hit");
                if(processGroundGeoms(contact,
                                      geom1,
                                      geom2,
                                      roadObjects,
                                      roadFrictionCoefficient,
                                      false)) {
                    processedContacts[contact_count++] = contact;
                    continue;
                }
            } else if(wallObjects.contains(geom1) ||
                      wallObjects.contains(geom2)) {
//System.out.println("wall hit");
                // Ignore hits on the wall by the wheels - they only get us stuck
                if(wheels.contains(geom1) || wheels.contains(geom2))
                    continue;

                if(processGroundGeoms(contact,
                                      geom1,
                                      geom2,
                                      wallObjects,
                                      wallFrictionCoefficient,
                                      true)) {
                    processedContacts[contact_count++] = contact;
                    continue;
                }
            } else if(sandObjects.contains(geom1) ||
                      sandObjects.contains(geom2)) {
//System.out.println("sand hit");
                if(processGroundGeoms(contact,
                                      geom1,
                                      geom2,
                                      sandObjects,
                                      sandFrictionCoefficient,
                                      false)) {
                    processedContacts[contact_count++] = contact;
                    continue;
                }
            } else if(terrainObjects.contains(geom1) ||
                      terrainObjects.contains(geom2)) {
                if(processGroundGeoms(contact,
                                      geom1,
                                      geom2,
                                      terrainObjects,
                                      otherFrictionCoefficient,
                                      false)) {
                    processedContacts[contact_count++] = contact;
                    continue;
                }
            } else if(grassObjects.contains(geom1) ||
                      grassObjects.contains(geom2)) {
// System.out.println("grass hit");
                if(processGroundGeoms(contact,
                                      geom1,
                                      geom2,
                                      roadObjects,
                                      grassFrictionCoefficient,
                                      false)) {
                    processedContacts[contact_count++] = contact;
                    continue;
                }
            }

            // So it wasn't the ground we hit, maybe it was an object. Now
            // check the chassis and wheels for collisions and see what they
            // hit. If they hit it, it'll be really soft, so make it bounce a
            // long way with the physics parameters.
            if(chassis.equals(geom1) || chassis.equals(geom2)) {
                SFFloat bounce = (SFFloat)contact.getField("bounce");
                bounce.setValue(0.1f);

                SFFloat b_speed = (SFFloat)contact.getField("minBounceSpeed");
                b_speed.setValue(0.2f);

                SFVec2f coeff = (SFVec2f)contact.getField("frictionCoefficients");
                fieldValues[0] = 0.1f;
                fieldValues[1] = 0.1f;
                coeff.setValue(fieldValues);

                SFFloat erp = (SFFloat)contact.getField("softnessErrorCorrection");
                erp.setValue(0.00001f);

                SFFloat cfm = (SFFloat)contact.getField("softnessConstantForceMix");
                erp.setValue(1);

                MFString params = (MFString)contact.getField("appliedParameters");
                params.setValue(WHEEL_GEOM_PARAMS.length, WHEEL_GEOM_PARAMS);

                processedContacts[contact_count++] = contact;
                continue;
            }

            if(geom2.equals(frontLeftWheel) ||
               geom2.equals(frontRightWheel) ||
               geom2.equals(backLeftWheel) ||
               geom2.equals(backRightWheel)) {

                // something hit us, make it _really_ bounce
                SFFloat bounce = (SFFloat)contact.getField("bounce");
                bounce.setValue(0.95f);

                SFFloat b_speed = (SFFloat)contact.getField("minBounceSpeed");
                b_speed.setValue(0.2f);

                SFVec2f coeff = (SFVec2f)contact.getField("frictionCoefficients");
                fieldValues[0] = 5;
                fieldValues[1] = 5;
                coeff.setValue(fieldValues);

                SFFloat erp = (SFFloat)contact.getField("softnessErrorCorrection");
                erp.setValue(0.00001f);

                SFFloat cfm = (SFFloat)contact.getField("softnessConstantForceMix");
                erp.setValue(1);

                MFString params = (MFString)contact.getField("appliedParameters");
                params.setValue(WHEEL_GEOM_PARAMS.length, WHEEL_GEOM_PARAMS);

                processedContacts[contact_count++] = contact;
            }
        }

        // Now write it all to the output
        if(contact_count != 0)
            contactOutput.setValue(contact_count, processedContacts);
    }


    private boolean processGroundGeoms(X3DNode contact,
                                       X3DNode geom1,
                                       X3DNode geom2,
                                       HashSet objects,
                                       float frictionMu,
                                       boolean useBounce) {
        if(geom1.equals(chassis) || geom2.equals(chassis)) {
            if(!useBounce)
                return false;

            SFVec2f friction = (SFVec2f)contact.getField("frictionCoefficients");
            fieldValues[0] = frictionMu;
            fieldValues[1] = frictionMu;
            friction.setValue(fieldValues);

            SFFloat bounce = (SFFloat)contact.getField("bounce");
            bounce.setValue(0.6f);
        } else if(wheels.contains(geom1) || wheels.contains(geom2)) {

            // Calculate the rotation matrix which describes the
            // wheel relative to the car body.
            SFFloat bounce = (SFFloat)contact.getField("bounce");
            if(useBounce)
                bounce.setValue(0.5f);
            else
                bounce.setValue(0.1f);

            SFVec3f f_dir =
                (SFVec3f)contact.getField("frictionDirection");
            SFVec2f slip = (SFVec2f)contact.getField("slipCoefficients");

            SFVec2f friction = (SFVec2f)contact.getField("frictionCoefficients");
            fieldValues[0] = frictionMu;
            fieldValues[1] = frictionMu;
            friction.setValue(fieldValues);

            carVelocity.getValue(fieldValues);

            float x = fieldValues[0];
            float y = fieldValues[1];
            float z = fieldValues[2];
            float speed = (float)Math.sqrt(x * x + y * y + z * z);

            fieldValues[0] = 0;
            fieldValues[1] = sideSlipCoefficient * speed;
            slip.setValue(fieldValues);

            MFString params =
                (MFString)contact.getField("appliedParameters");

            params.setValue(WHEEL_GROUND_PARAMS.length,
                            WHEEL_GROUND_PARAMS);

            SFRotation lin_v = null;

            if(objects.contains(geom1))  {
                SFNode val = (SFNode)contact.getField("body2");
                X3DNode node = val.getValue();
                lin_v = (SFRotation)node.getField("orientation");
            } else {
                SFNode val = (SFNode)contact.getField("body1");
                X3DNode node = val.getValue();
                lin_v = (SFRotation)node.getField("orientation");
            }

            axleVector.set(1, 0, 0);

            lin_v.getValue(fieldValues);

            orientation.set(fieldValues);
            rotation.set(orientation);
            rotation.transform(axleVector);

            SFVec3f c_normal = (SFVec3f)contact.getField("contactNormal");
            c_normal.getValue(fieldValues);

            bodyVector.set(fieldValues);

            bodyVector.cross(axleVector, bodyVector);
            bodyVector.normalize();

            bodyVector.get(fieldValues);
            f_dir.setValue(fieldValues);
        }

        return true;
    }
}