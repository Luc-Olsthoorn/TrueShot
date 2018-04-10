package threeD.trueshot.Scenarios;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;

/**
 * One shooter, one shot at one location.
 */
public class singleShot01 {

    private  double x;
    private  double y;
    private  double ele;
    private  double azimuth;
    private HrtfSession session;
    private D3Sound sound;
    private byte[] convolvedByteArray;


    /**
     *
     * @param x
     * @param y
     * @param ele
     * @param path
     */
    public singleShot01(double x, double y, double ele, String path){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);

        session = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth), ele);
        sound = new D3Sound(5512 * 100, new File(path), session);

        this.convolvedByteArray = setConvolvedBteArray();
    }

    private byte[] setConvolvedBteArray() {
        return sound.getConvolvedByteArray();
    }


    /**
     * Can be invoked to get the convoluted byte[].
     * @return
     */
    public byte[] getConvolvedByteArray() {
        return convolvedByteArray;
    }

    public void play(){
        sound.step();
    }







}
