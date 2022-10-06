import java.util.*;
import java.io.*;

import org.web3d.x3d.sai.*;

/**
 * Loads the config file.  Sends events to realize values
 * in the simulation.
 *
 * Script {
 *    outputOnly SFVec3f racePositionNumberScale
 *    outputOnly SFVec3f racePositionNumberTranslation
 *    outputOnly SFVec3f racePositionEndingScale
 *    outputOnly SFVec3f racePositionEndingTranslation
 *    outputOnly SFVec3f raceTimeLabelScale
 *    outputOnly SFVec3f raceTimeLabelTranslation
 *    outputOnly SFVec3f raceTimeScale
 *    outputOnly SFVec3f raceTimeTranslation
 *    outputOnly SFVec3f raceTimeMinuteTranslation
 *    outputOnly SFVec3f raceTimeSecond1Translation
 *    outputOnly SFVec3f raceTimeSecond2Translation
 *    outputOnly SFVec3f raceTimeMilliSecond1Translation
 *    outputOnly SFVec3f raceTimeMilliSecond2Translation
 *    outputOnly SFVec3f raceTimeSeparatorScale
 *    outputOnly SFVec3f raceTimeSeparator1Translation
 *    outputOnly SFVec3f raceTimeSeparator2Translation
 *    outputOnly SFVec3f locationMapScale
 *    outputOnly SFVec3f locationMapTranslation
 *    outputOnly SFVec3f speedDialTranslation
 *    outputOnly SFVec3f speedDialScale
 *    outputOnly SFVec3f tachometerTranslation
 *    outputOnly SFVec3f tachometerScale
 *    outputOnly SFInt32 racerNumber
 *    outputOnly SFInt32 numberLapsToRace
 * }
 */
public class ConfigFileLoader implements X3DScriptImplementation,
    X3DFieldEventListener {

    private static final String PROPERTY_FILE="racer_config.properties";
    private static final String SCORE_FILE="score.properties";
    private Browser browser;

    private SFFloat currTime;
    private SFBool checkBest;
    private SFFloat newBest;
    private SFInt32 newBestSwitch;
    private SFFloat opponentNewBest;

    private SFVec3f racePositionNumberScale;
    private SFVec3f racePositionNumberTranslation;
    private SFVec3f racePositionEndingScale;
    private SFVec3f racePositionEndingTranslation;
    private SFVec3f raceTimeLabelScale;
    private SFVec3f raceTimeLabelTranslation;
    private SFVec3f raceTimeScale;
    private SFVec3f raceTimeTranslation;
    private SFVec3f raceTimeMinuteTranslation;
    private SFVec3f raceTimeSecond1Translation;
    private SFVec3f raceTimeSecond2Translation;
    private SFVec3f raceTimeMilliSecond1Translation;
    private SFVec3f raceTimeMilliSecond2Translation;
    private SFVec3f raceTimeSeparatorScale;
    private SFVec3f raceTimeSeparator1Translation;
    private SFVec3f raceTimeSeparator2Translation;
    private SFVec3f locationMapScale;
    private SFVec3f locationMapTranslation;
    private SFVec3f speedDialTranslation;
    private SFVec3f speedDialScale;
    private SFVec3f tachometerTranslation;
    private SFVec3f tachometerScale;
    private SFInt32 racerNumber;
    private SFInt32 numberLapsToRace;

    String user_dir;
    private int currBest;

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public void setFields(X3DScriptNode externalView, Map fields) {
        currTime = (SFFloat) fields.get("currTime");
        checkBest = (SFBool) fields.get("checkBest");
        newBest = (SFFloat) fields.get("newBest");
        newBestSwitch = (SFInt32) fields.get("newBestSwitch");
        opponentNewBest = (SFFloat) fields.get("opponentNewBest");

        checkBest.addX3DEventListener(this);
        opponentNewBest.addX3DEventListener(this);

        racePositionNumberScale = (SFVec3f) fields.get("racePositionNumberScale");
        racePositionNumberTranslation = (SFVec3f) fields.get("racePositionNumberTranslation");
        racePositionEndingScale = (SFVec3f) fields.get("racePositionEndingScale");
        racePositionEndingTranslation = (SFVec3f) fields.get("racePositionEndingTranslation");
        raceTimeLabelScale = (SFVec3f) fields.get("raceTimeLabelScale");
        raceTimeLabelTranslation = (SFVec3f) fields.get("raceTimeLabelTranslation");
        raceTimeScale = (SFVec3f) fields.get("raceTimeScale");
        raceTimeTranslation = (SFVec3f) fields.get("raceTimeTranslation");
        raceTimeMinuteTranslation = (SFVec3f) fields.get("raceTimeMinuteTranslation");
        raceTimeSecond1Translation = (SFVec3f) fields.get("raceTimeSecond1Translation");
        raceTimeSecond2Translation = (SFVec3f) fields.get("raceTimeSecond2Translation");
        raceTimeMilliSecond1Translation = (SFVec3f) fields.get("raceTimeMilliSecond1Translation");
        raceTimeMilliSecond2Translation = (SFVec3f) fields.get("raceTimeMilliSecond2Translation");
        raceTimeSeparatorScale = (SFVec3f) fields.get("raceTimeSeparatorScale");
        raceTimeSeparator1Translation = (SFVec3f) fields.get("raceTimeSeparator1Translation");
        raceTimeSeparator2Translation = (SFVec3f) fields.get("raceTimeSeparator2Translation");
        locationMapScale = (SFVec3f) fields.get("locationMapScale");
        locationMapTranslation = (SFVec3f) fields.get("locationMapTranslation");
        speedDialTranslation = (SFVec3f) fields.get("speedDialTranslation");
        speedDialScale = (SFVec3f) fields.get("speedDialScale");
        tachometerTranslation = (SFVec3f) fields.get("tachometerTranslation");
        tachometerScale = (SFVec3f) fields.get("tachometerScale");
        racerNumber = (SFInt32) fields.get("racerNumber");
        numberLapsToRace = (SFInt32) fields.get("numberLapsToRace");
    }

    public void initialize() {
        user_dir = System.getProperty("user.dir");

        loadProperties();
        loadScore();
    }

    public void eventsProcessed() {
    }

    public void shutdown() {
    }

    private void loadScore() {
        InputStream is;
        String file = user_dir + File.separator + SCORE_FILE;

        try {
            is = new FileInputStream(file);
        } catch(FileNotFoundException fnfe) {
            is = (InputStream) ClassLoader.getSystemResourceAsStream(SCORE_FILE);
        }

        if (is == null) {
            ClassLoader cl = ConfigFileLoader.class.getClassLoader();
            is = (InputStream)cl.getResourceAsStream(SCORE_FILE);
        }
        if (is == null) {
            System.out.println("Couldn't find properties: " + SCORE_FILE);
        } else {
            Properties props = new Properties();
            try {
                props.load(is);
                is.close();
            } catch(IOException ioe) {
                System.out.println("Error reading: " + SCORE_FILE);
            }

            String prop = "bestTotalTime";
            String str = props.getProperty(prop);
            currBest = Integer.parseInt(str);

            System.out.println("Current Best Score: " + currBest);
        }
    }

    private void loadProperties() {
        String user_dir = System.getProperty("user.dir");
        InputStream is;
        String file = user_dir + File.separator + PROPERTY_FILE;

        try {
            is = new FileInputStream(file);
        } catch(FileNotFoundException fnfe) {
            is = (InputStream) ClassLoader.getSystemResourceAsStream(PROPERTY_FILE);
        }

        if (is == null) {
            ClassLoader cl = ConfigFileLoader.class.getClassLoader();
            is = (InputStream)cl.getResourceAsStream(PROPERTY_FILE);
        }
        if (is == null) {
            System.out.println("Couldn't find properties: " + PROPERTY_FILE);
        } else {
            Properties props = new Properties();
            try {
                props.load(is);
                is.close();
            } catch(IOException ioe) {
                System.out.println("Error reading: " + PROPERTY_FILE);
            }

            String prop = "RacePositionNumber.Scale";
            String str = props.getProperty(prop);

            float[] val = new float[3];
            int ival;

            if (str != null) {
                parseSFVec3f(str, val);
                racePositionNumberScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }


            prop = "RacePositionNumber.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                racePositionNumberTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RacePositionEnding.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                racePositionEndingScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RacePositionEnding.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                racePositionEndingTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTimeLabel.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeLabelScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTimeLabel.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeLabelTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTime.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTime.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            float[] subtrans = new float[3];
            float[] singleSpacing = new float[3];
            float[] doubleSpacing = new float[3];
            prop = "RaceTime.SubTranslation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, subtrans);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTime.SingleSpacing";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, singleSpacing);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTime.DoubleSpacing";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, doubleSpacing);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            float[] trans = new float[3];

            raceTimeMinuteTranslation.setValue(subtrans);

            trans[0] = subtrans[0] + doubleSpacing[0];
            trans[1] = subtrans[1] + doubleSpacing[1];
            trans[1] = subtrans[2] + doubleSpacing[2];
            raceTimeSecond1Translation.setValue(trans);

            trans[0] = trans[0] + singleSpacing[0];
            trans[1] = trans[1] + singleSpacing[1];
            trans[2] = trans[2] + singleSpacing[2];
            raceTimeSecond2Translation.setValue(trans);


            trans[0] = trans[0] + doubleSpacing[0];
            trans[1] = trans[1] + doubleSpacing[1];
            trans[2] = trans[2] + doubleSpacing[2];
            raceTimeMilliSecond1Translation.setValue(trans);


            trans[0] = trans[0] + singleSpacing[0];
            trans[1] = trans[1] + singleSpacing[1];
            trans[2] = trans[2] + singleSpacing[2];
            raceTimeMilliSecond2Translation.setValue(trans);

            prop = "RaceTimeSeparator.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeSeparatorScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTimeSeparator1.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeSeparator1Translation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RaceTimeSeparator2.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                raceTimeSeparator2Translation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "LocationMap.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                locationMapScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "LocationMap.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                locationMapTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "SpeedDial.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                speedDialTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "SpeedDial.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                speedDialScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "Tachometer.Translation";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                tachometerTranslation.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "Tachometer.Scale";
            str = props.getProperty(prop);

            if (str != null) {
                parseSFVec3f(str, val);
                tachometerScale.setValue(val);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "RacerNumber";
            str = props.getProperty(prop);

            if (str != null) {
                ival = Integer.parseInt(str);
                racerNumber.setValue(ival);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

            prop = "NumberLapsToRace";
            str = props.getProperty(prop);

            if (str != null) {
                ival = Integer.parseInt(str);
                numberLapsToRace.setValue(ival);
            } else {
                System.out.println("Couldn't find property: " + prop);
            }

        }
    }

    public void readableFieldChanged(X3DFieldEvent evt) {
        X3DField field = (X3DField) evt.getSource();
        if (field == checkBest) {
            int curr = (int) (currTime.getValue() * 1000f);
            System.out.println("Check best  Curr: " + curr + " best: " + currBest);

            if (curr < currBest) {
    System.out.println("New Best Time, set Switch");
                newBest.setValue(currTime.getValue());
                newBestSwitch.setValue(0);
                currBest = curr;

                writeBest((int)curr);

            }
        } else {
            // OpponentNewBest
            int curr = (int) (opponentNewBest.getValue() * 1000f);

            //System.out.println("Curr: " + curr + " best: " + currBest);

            if (curr > 0 && curr < currBest) {
                currBest = curr;
                System.out.println("Opponent got a new best: " + curr + " best: " + currBest);
                writeBest((int)curr);
            }
        }
    }


    /**
     * Write out a new best
     */
    private void writeBest(int curr) {
        String file = user_dir + File.separator + SCORE_FILE;

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.print("bestTotalTime =" + curr);
            pw.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Parse a string representing a SFVec3f and put the result
     * into an array.
     *
     * @param str The string to parse
     * @param result The preallocated result array
     */
    private void parseSFVec3f(String str, float[] result) {
        int i = 0;

        StringTokenizer strtok = new StringTokenizer(str);
        String sval;

        while (i < 3 && strtok.hasMoreTokens()) {
            sval = strtok.nextToken();
            result[i++] = Float.parseFloat(sval);
        }
    }
}

