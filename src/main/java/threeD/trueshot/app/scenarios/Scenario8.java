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
        duration = 4;
        sessions = new ArrayList<>();
        sounds = new ArrayList<>();
        this.subject = subject;

        for (int i = 0; i < duration; i++){
            sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
            sounds.add(new D3Sound(44100*4, new File("res/sound/test/beep.wav"), sessions.get(i)));
//            sounds.get(i).setAttenuation(0.6);

            // TODO: 4/22 022 Write a function to apply attenuation
        }
    }

//    private byte[] setConvolvedBteArray() {
//        return sound.getConvolutedByteArray();
//    }
//
//    /**
//     * Can be invoked to get the convoluted byte[].
//     * @return
//     */
//    public byte[] getConvolvedByteArray() {
//        return convolvedByteArray;
//    }
//
//    public void step(){
//        sound.stepSilent();// Generate convolved array
//        this.convolvedByteArray = setConvolvedBteArray();//Copy convolved array here
//        modify();//Do modification
//    }
//
//    private void modify(){
//        int firstIndex = sound.getfirstNonZero();
//        int lastIndex = sound.getlastNonZero();
//
//        for (int i = lastIndex+1, j = firstIndex; i < convolvedByteArray.length; i++, j++){
////				System.arraycopy(convoledData, j, convolvedByteArray, i, 1);
//            convolvedByteArray[i] = convolvedByteArray[j];
//            if (j == lastIndex) {
//                j =firstIndex; i+=1;
//            }
//        }
//    }

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

/*        //This is to test whether a sound is null.
        //Result is: only the first call of this function is not null, the following calls will get null getConvolutedByteArray()
        //Debug: when coming to frame=1, in sound.stepSilent() function, the bytesRead will be set as -1 when calling AudioInputStream.read
        for (int i = 0; i < sound.getConvolutedByteArray().length; i++){
            if (sound.getConvolutedByteArray()[i] != 0){
                System.out.println("not null:  "+frame);
                break;
            }
        }
        frame++;*/

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
