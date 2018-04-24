package threeD.trueshot.app.scenarios;
import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;
import java.util.ArrayList;

/**
 * Continuous shooting at a given location: A shooter is fire continuous from a location.
 */
public class Scenario8 implements TrueScenario {

    public ArrayList<D3Sound> sounds;
    private ArrayList<HrtfSession> sessions;
    private String subject;
    private int frame = 0;
    private int duration;
    private TrueCoordinates[] shotCoords = {
            new TrueCoordinates(4, 0, 0)
    };

    public Scenario8(String subject){
        duration = 8;
        sessions = new ArrayList<>();
        sounds = new ArrayList<>();
        this.subject = subject;

        for (int i = 0; i < duration; i++){
            sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
            sounds.add(new D3Sound(44100*4, new File("res/sound/test/6C.wav"), sessions.get(i)));
        }
    }

    @Override
    public byte[] buildNextStep(TrueCoordinates headRotation) {

        int index = 0;
        //Pay attention to the index of shotCoords.
        for (D3Sound sound: sounds) {
            // Set the origin to this new rotation angle
            shotCoords[0].setOrigin(headRotation.azimuth);

            // Now update the sound to use the correct azimuth and elevation
            sound.changeSoundDirection(shotCoords[0].getAdjustedAzimuth(), shotCoords[0].getAdjustedElevation());
            index++;
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
                sounds.get(3).stepSilent();
                break;
            case 4:
                sounds.get(4).stepSilent();
                break;
            case 5:
                sounds.get(5).stepSilent();
                break;
            case 6:
                sounds.get(6).stepSilent();
                break;
            case 7:
                sounds.get(7).stepSilent();
            default:
                break;
        }

        if(frame > sounds.size())
        {
            return new byte[50];
        }

        System.out.println(shotCoords[0].azimuth);

        return sounds.get(frame++-1).soundWithHeader();
    }

    @Override
    public ScenarioInfo scenarioInfo() {
        return null;
    }
}
