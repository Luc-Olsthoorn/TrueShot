package threeD.trueshot.Scenarios;

import javax.sound.sampled.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

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



    public static void main(String args[]) throws IOException{

        double x = 3;
        double y = 0;
        double elevation = 0;
        int duration = 2;// Seconds

        String str = "";
        char c = ' ';
        Scanner stdin = new Scanner(System.in);
        try {
            System.out.println("Select scenario number:  (q = quit)");
            System.out.println("1 = single shot; 2 = some shots; 4 = some weapons; 8 = continuous shot");
            c = (char) stdin.nextLine().charAt(0);
        } catch (Exception ex) {
            c = 'q';
        }


            switch (c) {
                case '1': {
                    singleShot01 scenario01 = new singleShot01(x, y, -45, "res/sound/test/input16.wav", duration);
                    byte[] convolvedBteArray01 = scenario01.getConvolvedByteArray();
                    Player player01 = new Player(scenario01.sound.audioFormat, scenario01.sound.info);
                    player01.play(convolvedBteArray01);
                }
                break;

                case '2': {
                    ArrayList<locationCoords> LocationCoords = new ArrayList<>();
                    LocationCoords.add(0, new locationCoords(4, 0, 0));
                    LocationCoords.add(1, new locationCoords(1, 0, 0));
                    LocationCoords.add(2, new locationCoords(-4, 0, 0));

                    someShots02 scenario02 = new someShots02(LocationCoords, 3);

                    ArrayList<byte[]> convolvedByteArrays02 = scenario02.getConvolvedByteArray();
                    Player player02 = new Player(scenario02.sounds[0].audioFormat, scenario02.sounds[0].info);

                    for (int i = 0; i < convolvedByteArrays02.size(); i++) {
                        player02.play(convolvedByteArrays02.get(i));
                    }

                }
                break;

                case '4': {
                    diffWeapons04 scenario04 = new diffWeapons04(x, y, elevation, 3);
                    ArrayList<byte[]> convolvedByteArrays04 = scenario04.getConvolvedByteArray();
                    Player player04 = new Player(scenario04.sounds[0].audioFormat, scenario04.sounds[0].info);
                    for (int i = 0; i < convolvedByteArrays04.size(); i++) {
                        player04.play(convolvedByteArrays04.get(i));
                    }
                }
                break;

                case '8': {
                    ctnsShot08 scenario08 = new ctnsShot08(x, y, elevation, "res/sound/test/beep.wav", duration);
                    byte[] convolvedByteArray08 = scenario08.getConvolvedByteArray();
                    Player player08 = new Player(scenario08.sound.audioFormat, scenario08.sound.info);
                    player08.play(convolvedByteArray08);
                }

            }


        //Create scenarios
//        singleShot01 scenario01 = new singleShot01(x, y, -45, "res/sound/test/input16.wav", duration);
//        ctnsShot08 scenario08 = new ctnsShot08(x, y, elevation, "res/sound/test/beep.wav", duration);
//        diffWeapons04 scenario04 = new diffWeapons04(x, y, elevation, 3);

        /*ArrayList<locationCoords> LocationCoords = new ArrayList<>();
        LocationCoords.add(0, new locationCoords(4, 0, 0));
        LocationCoords.add(1, new locationCoords(1, 0, 0));
        LocationCoords.add(2, new locationCoords(-4, 0, 0));

        someShots02 scenario02 = new someShots02(LocationCoords, 3);*/




        //Get final array and play
//        byte[] convolvedByteArray08 = scenario08.getConvolvedByteArray();
//        Player player08 = new Player(scenario08.sound.audioFormat, scenario08.sound.info);

//        byte[] convolvedBteArray01 = scenario01.getConvolvedByteArray();
//        Player player01 = new Player(scenario01.sound.audioFormat, scenario01.sound.info);

//        ArrayList<byte[]> convolvedByteArrays04 = scenario04.getConvolvedByteArray();
//        Player player04 = new Player(scenario04.sounds[0].audioFormat, scenario04.sounds[0].info);

//        ArrayList<byte[]> convolvedByteArrays02 = scenario02.getConvolvedByteArray();
//        Player player02 = new Player(scenario02.sounds[0].audioFormat, scenario02.sounds[0].info);






//        player01.play(convolvedBteArray01);
//        player08.play(convolvedByteArray08);
//        for (int i = 0; i < convolvedByteArrays04.size(); i++){
//            player04.play(convolvedByteArrays04.get(i));
//        }

//        for (int i = 0; i < convolvedByteArrays02.size(); i++){
//            player02.play(convolvedByteArrays02.get(i));
//        }

    }

}
