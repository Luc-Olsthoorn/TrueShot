package threeD.trueshot.app.scenarios;
import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;
import java.util.ArrayList;

/**
 * Multi-person-shot with identifiable sounds that represent different weapons.
 */
public class Scenario4 implements TrueScenario {

    public ArrayList<D3Sound> sounds;
    private ArrayList<HrtfSession> sessions;
    private String subject;
    private int frame;
    private String[] soundsFiles = {
            "res/sound/test/6C.wav",
            "res/sound/test/25E.wav",
            "res/sound/test/33F.wav"

    };

    private TrueCoordinates[] shotCoords  =
            {
                    new TrueCoordinates(4, -4, 0)
            };


    /**
     * Constructor
     * @param subject
     */
    public Scenario4(String subject){
        this.subject = subject;
        sessions = new ArrayList<>();
        sounds = new ArrayList<>();
        frame = 0;


        //Build sounds according to weapons numbers
        for (int i = 0; i < soundsFiles.length; i++){
            sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
            sounds.add(new D3Sound(44100*4, new File(soundsFiles[i]), sessions.get(i)));

            // TODO: 4/22 022 Write a function to apply attenuation
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
                break;
            default:
                break;
        }
        frame++;

        return sounds.get(frame-1).soundWithHeader();
    }

    @Override
    public ScenarioInfo scenarioInfo() {
        return null;
    }

}
