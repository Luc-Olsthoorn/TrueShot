package threeD.trueshot.app.scenarios;
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
    public singleShot01(double x, double y, double ele, String path, int duration){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
        this.bufferSize = duration*177222;//177222 is 1 second of beep

        session = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth), ele);
        sound = new D3Sound(bufferSize, new File(path), session);

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

    public void step(){
        sound.step();
        this.convolvedByteArray = setConvolvedBteArray();
        modify();
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




}
