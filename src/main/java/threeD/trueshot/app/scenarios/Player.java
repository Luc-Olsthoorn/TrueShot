package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;

import javax.sound.sampled.*;
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
                    Scenario1 scenario01 = new Scenario1(x, y, -45, "res/sound/test/input16.wav", duration);
                    byte[] convolvedBteArray01 = scenario01.getConvolvedByteArray();
                    Player player01 = new Player(scenario01.sound.audioFormat, scenario01.sound.info);
                    player01.play(convolvedBteArray01);
                }
                break;

                case '2': {
                    ArrayList<TrueCoordinates> shotCoords = new ArrayList<>();
                    shotCoords.add(0, new TrueCoordinates(3, 0, 0));
                    shotCoords.add(1, new TrueCoordinates(1, 0, 0));
                    shotCoords.add(2, new TrueCoordinates(-2, 0, 0));

                    Scenario2 scenario02 = new Scenario2(shotCoords, 3);

                    ArrayList<byte[]> convolvedByteArrays02 = scenario02.getConvolvedByteArray();
                    Player player02 = new Player(scenario02.sounds.get(0).audioFormat, scenario02.sounds.get(0).info);

//                    TrueCoordinates headRotation = new TrueCoordinates(0, 0, 0);
//                    player02.play(scenario02.buildNextStep(headRotation));

                    for (int i = 0; i < convolvedByteArrays02.size(); i++) {
                        player02.play(convolvedByteArrays02.get(i));
                    }

                }
                break;

                case '4': {
                    Scenario4 scenario04 = new Scenario4(x, y, elevation, 3);
                    ArrayList<byte[]> convolvedByteArrays04 = scenario04.getConvolvedByteArray();
                    Player player04 = new Player(scenario04.sounds[0].audioFormat, scenario04.sounds[0].info);
                    for (int i = 0; i < convolvedByteArrays04.size(); i++) {
                        player04.play(convolvedByteArrays04.get(i));
                    }
                }
                break;

                case '8': {
                    Scenario8 scenario08 = new Scenario8(x, y, elevation, "res/sound/test/beep.wav", duration);
                    byte[] convolvedByteArray08 = scenario08.getConvolvedByteArray();
                    Player player08 = new Player(scenario08.sound.audioFormat, scenario08.sound.info);
                    player08.play(convolvedByteArray08);
                }

            }
    }

}
