package threeD.trueshot.Scenarios;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import java.io.File;


public class singleShot01 {

    private  double x;
    private  double y;
    private  double ele;
    private  double azimuth;
    private HrtfSession session;
    private D3Sound sound;

    public singleShot01(double x, double y, double ele, String path){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
        session = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth), ele);
        sound = new D3Sound(5512 * 100, new File(path), session);
    }



    public void play(){
        sound.step();
    }







}