package threeD.trueshot.Scenarios;

import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;
import java.io.File;
import java.util.ArrayList;

/**
 * One shooter shoots once at multiple locations. Model predicts time and direction the shooter is traveling.
 */
public class someShots02 {

    private  double[] x;
    private  double[] y;
    private  double[] ele;
    private  double[] azimuth;
    public HrtfSession[] sessions;
    public D3Sound[] sounds;
    private ArrayList<byte[]> convolvedByteArrays;
    private int bufferSize;


    /**
     *
     * @param LocationCoords
     * @param numofLocations
     */

    public someShots02(ArrayList<locationCoords> LocationCoords, int numofLocations){

        azimuth = new double[numofLocations];
        ele = new double[numofLocations];
        sessions = new HrtfSession[numofLocations];
        sounds = new D3Sound[numofLocations];


        for (int i = 0; i < LocationCoords.size(); i++){
            azimuth[i] = LocationCoords.get(i).azimuth;
            ele[i] = LocationCoords.get(i).ele;
        }


//        this.x = x;
//        this.y = y;
//        this.ele = ele;
//        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
        //A single 4*44100 is buffer size about 1 second of beep
        this.bufferSize = 1*4*44100;//duration*4*44100



        for (int i = 0; i < numofLocations; i++){
            sessions[i] = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth[i]), ele[i]);
            sounds[i] = new D3Sound(bufferSize, new File("res/sound/test/input16.wav"), sessions[i]);
        }

        convolvedByteArrays = new ArrayList<>();
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

}
