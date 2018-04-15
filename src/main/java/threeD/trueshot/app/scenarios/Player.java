package threeD.trueshot.app.scenarios;

import javax.sound.sampled.*;


public class Player {

    SourceDataLine soundLine;
    AudioFormat audioFormat;
    DataLine.Info info;

    public Player(AudioFormat audioFormat, DataLine.Info info){
        this.audioFormat = audioFormat;
        this.info = info;
    }


    public void play(byte[] bt){
        try {
            if(soundLine != null)
            {
                soundLine.close();
            }
            soundLine = (SourceDataLine) AudioSystem.getLine(info);
            soundLine.open(audioFormat);
            soundLine.start();
        }catch (LineUnavailableException e){
            e.printStackTrace();
        }

        soundLine.write(bt, 0, bt.length);
    }



    public static void main(String args[]){

        double x = 3;
        double y = 0;
        double elevation = 0;
        int duration = 4;// Seconds

        //Create scenarios
        singleShot01 scenario01 = new singleShot01(x, y, elevation, "res/sound/test/input16.wav", duration);
        ctnsShot08 scenario08 = new ctnsShot08(x, y, elevation, "res/sound/test/beep.wav", duration);




        scenario08.step();

        Player player = new Player(scenario08.sound.audioFormat, scenario08.sound.info);

        byte[] convolvedByteArray = scenario08.getConvolvedByteArray();
        player.play(convolvedByteArray);


    }

}
