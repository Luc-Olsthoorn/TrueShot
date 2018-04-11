package threeD.trueshot.Scenarios;

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
        String filePath = "res/sound/test/beep.wav";
        int duration = 4;// Seconds
        //int bufferSize = 4*177222;
        singleShot01 test = new singleShot01(x, y, elevation, filePath, duration);
        test.step();

        Player player = new Player(test.sound.audioFormat, test.sound.info);

        byte[] convolvedByteArray = test.getConvolvedByteArray();
        player.play(convolvedByteArray);


    }

}
