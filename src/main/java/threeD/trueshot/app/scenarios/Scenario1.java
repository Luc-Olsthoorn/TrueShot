package threeD.trueshot.app.scenarios;
import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;
import java.util.ArrayList;

/**
 * One shooter, one shot at one location.
 */
public class Scenario1 implements TrueScenario {


    public ArrayList<D3Sound> sounds;
    private ArrayList<HrtfSession> sessions;
    private String subject;

    private TrueCoordinates[] shotCoords = {
            new TrueCoordinates(4, 0, 0)
    };

    public Scenario1(String subject){

        this.subject = subject;
        sessions = new ArrayList<>();
        sounds = new ArrayList<>();

       sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
       sounds.add(new D3Sound(44100*4, new File("res/sound/test/6C.wav"), sessions.get(0)));
       sounds.get(0).setAttenuation(0.6);
    }


    @Override
    public byte[] buildNextStep(TrueCoordinates headRotation) {
        int index = 0;
        for (D3Sound sound : sounds){
            shotCoords[index].setOrigin(headRotation.azimuth);
            sound.changeSoundDirection(shotCoords[index].getAdjustedAzimuth(), shotCoords[index].getAdjustedElevation());
            index++;
        }

        sounds.get(0).stepSilent();
        return sounds.get(0).soundWithHeader();
    }

    @Override
    public ScenarioInfo scenarioInfo() {
        return null;
    }
}
