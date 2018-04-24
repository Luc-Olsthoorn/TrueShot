package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;
import java.util.ArrayList;

/**
 * One shooter shoots once at multiple locations. Model predicts time and direction the shooter is traveling.
 */
public class Scenario2 implements TrueScenario{


    public ArrayList<D3Sound> sounds;
    public ArrayList<HrtfSession> sessions;
    private String subject;
    private int frame;
    private boolean isWarning;

    private TrueCoordinates[] shotCoords = {
            new TrueCoordinates(3, 0, 0),
            new TrueCoordinates(0, 1, 0),
            new TrueCoordinates(-2, 0, 0)

    };




    public Scenario2(String subject){

        this.subject = subject;
        sessions = new ArrayList<>();
        sounds = new ArrayList<>();
        frame = 0;
        isWarning = false;
        warning(shotCoords);

        for (int i = 0; i < shotCoords.length; i++){
            sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
            sounds.add(new D3Sound(44100*4, new File("res/sound/test/6C.wav"), sessions.get(i)));

            // TODO: 4/22 022 Write a function to apply attenuation
        }

        if (isWarning){
            sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
            sounds.add(new D3Sound(44100*4, new File("res/sound/test/warning.wav"), sessions.get(sessions.size()-1)));
        }



    }

    private void warning(TrueCoordinates[] shotCoords){
        double x1 = (double) shotCoords[shotCoords.length-1].x;
        double y1 = (double) shotCoords[shotCoords.length-1].y;

        double x2 = (double) shotCoords[shotCoords.length-2].x;
        double y2 = (double) shotCoords[shotCoords.length-2].y;

        double A = y2-y1;
        double B = x1-x2;
        double C = x2*y1-x1*y2;
        double d = C/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)));

        if (d > 1){
            isWarning = false;
        }else {
            isWarning = true;
        }
    }

    @Override
    public byte[] buildNextStep(TrueCoordinates headRotation) {
        int index = 0;
        for (D3Sound sound: sounds) {
            if (index < shotCoords.length){
                // Set the origin to this new rotation angle
                shotCoords[index].setOrigin(headRotation.azimuth);

                // Now update the sound to use the correct azimuth and elevation
                sound.changeSoundDirection(shotCoords[index].getAdjustedAzimuth(), shotCoords[index].getAdjustedElevation());
                index++;
            }
        }


        switch (frame){
            case 0:
                sounds.get(0).stepSilent();
                break;
            case 1:
                sounds.get(1).stepSilent();
                break;
            case 2:
                sounds.get(2).stepSilent();
                break;
            case 3:
                if (isWarning){
                    sounds.get(3).stepSilent();
                }
                break;
            default:
                break;
        }

        return sounds.get(frame++-1).soundWithHeader();
    }

    @Override
    public ScenarioInfo scenarioInfo() {
        ScenarioInfo info = new ScenarioInfo();
//        info.mixer = this.mixer;
        return info;
    }
}
