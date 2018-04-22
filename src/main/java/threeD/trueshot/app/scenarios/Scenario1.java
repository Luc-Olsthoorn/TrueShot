package threeD.trueshot.app.scenarios;
import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;

/**
 * One shooter, one shot at one location.
 */
public class Scenario1 implements TrueScenario {

    private  double x;
    private  double y;
    private  double ele;
    private  double azimuth;
    public HrtfSession session;
    public D3Sound sound;
    private byte[] convolvedByteArray;
    private int bufferSize;


    /**
     *
     * @param x
     * @param y
     * @param ele
     * @param path
     */
    public Scenario1(double x, double y, double ele, String path, int duration){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
        this.bufferSize = duration*4*44100;//177222 is 1 second of beep

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
        sound.stepSilent();
        this.convolvedByteArray = setConvolvedBteArray();
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
