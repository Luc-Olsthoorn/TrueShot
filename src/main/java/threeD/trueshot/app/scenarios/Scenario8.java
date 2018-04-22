package threeD.trueshot.app.scenarios;
import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;

/**
 * Continuous shooting at a given location: A shooter is fire continuous from a location.
 */
public class Scenario8 implements TrueScenario {
    private  double x;
    private  double y;
    private  double ele;
    private  double azimuth;
    public HrtfSession session;
    public D3Sound sound;
    private byte[] convolvedByteArray;
    private int bufferSize;


    public Scenario8(double x, double y, double ele, String path, int duration){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
        //A single 4*44100 is buffer size about 1 second of beep
        this.bufferSize = duration*4*44100;

        session = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth), ele);
        sound = new D3Sound(bufferSize, new File(path), session);

        step();
    }

    private byte[] setConvolvedBteArray() {
        return sound.getConvolutedByteArray();
    }

    /**
     * Can be invoked to get the convoluted byte[].
     * @return
     */
    public byte[] getConvolvedByteArray() {
        return convolvedByteArray;
    }

    public void step(){
        sound.stepSilent();// Generate convolved array
        this.convolvedByteArray = setConvolvedBteArray();//Copy convolved array here
        modify();//Do modification
    }

    private void modify(){
        int firstIndex = sound.getfirstNonZero();
        int lastIndex = sound.getlastNonZero();

        for (int i = lastIndex+1, j = firstIndex; i < convolvedByteArray.length; i++, j++){
//				System.arraycopy(convoledData, j, convolvedByteArray, i, 1);
            convolvedByteArray[i] = convolvedByteArray[j];
            if (j == lastIndex) {
                j =firstIndex; i+=1;
            }
        }
    }

    @Override
    public byte[] buildNextStep(TrueCoordinates newRotation) {
        return new byte[0];
    }

    @Override
    public ScenarioInfo scenarioInfo() {
        return null;
    }
}
