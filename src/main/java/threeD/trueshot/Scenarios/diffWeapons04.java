package threeD.trueshot.Scenarios;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;
import java.util.ArrayList;

/**
 * Multi-person-shot with identifiable sounds that represent different weapons.
 */
public class diffWeapons04 {
    private  double x;
    private  double y;
    private  double ele;
    private  double azimuth;
    public HrtfSession[] sessions;
    public D3Sound[] sounds;
    private ArrayList<byte[]> convolvedByteArrays;
    private int bufferSize;

    private String[] pathes = {
            "res/sound/test/input16.wav",
            "res/sound/test/crunch.wav",
            "res/sound/test/c2.wav"

    };

    public diffWeapons04(double x, double y, double ele, int numOfSound){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
        //A single 4*44100 is buffer size about 1 second of beep
        this.bufferSize = 1*4*44100;//duration*4*44100

        sessions = new HrtfSession[numOfSound];
        sounds = new D3Sound[numOfSound];

        convolvedByteArrays = new ArrayList<>();

        for (int i = 0; i < numOfSound; i++){
            sessions[i] = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth), ele);
            sounds[i] = new D3Sound(bufferSize, new File(pathes[i]), sessions[i]);
        }


        step();
    }





    public void step(){
        // Generate convolved array of each sound
        for (int i = 0; i < sounds.length; i++){
            sounds[i].step();
        }
        this.setConvolvedBteArray();//Copy convolved array here
//        modify();//Do modification
    }

    private void setConvolvedBteArray() {
        for (int i = 0; i < sounds.length; i++){
            convolvedByteArrays.add(i, sounds[i].getConvolvedByteArray());
        }

    }

    public ArrayList<byte[]> getConvolvedByteArray() {
        return convolvedByteArrays;
    }


    /*private void modify(){
        int firstIndex = sound.getfirstNonZero();
        int lastIndex = sound.getlastNonZero();

        for (int i = lastIndex+1, j = firstIndex; i < convolvedByteArray.length; i++, j++){
//				System.arraycopy(convoledData, j, convolvedByteArray, i, 1);
            convolvedByteArray[i] = convolvedByteArray[j];
            if (j == lastIndex) {
                j =firstIndex; i+=1;
            }
        }
    }*/
}
