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

            TrueScenario scenario ;
            switch (c) {
                case '1': {
                    scenario = new Scenario1("58");
                    Player player = new Player(((Scenario1) scenario).sounds.get(0).audioFormat, ((Scenario1) scenario).sounds.get(0).info);
                    TrueCoordinates rotation = new TrueCoordinates(3, 0, 0);
                    for (int i = 0; i < 5; i++){
                        player.play(scenario.buildNextStep(rotation));
                    }
                }
                break;

                case '2': {
                    scenario = new Scenario2("58");
                    Player player = new Player(((Scenario2) scenario).sounds.get(0).audioFormat, ((Scenario2) scenario).sounds.get(0).info);
                    TrueCoordinates rotation = new TrueCoordinates(3, 0, 0);
                    for (int i = 0; i < 5; i++){
                        player.play(scenario.buildNextStep(rotation));
                    }
                }
                break;

                case '4': {
                    scenario = new Scenario4("58");
                    Player player = new Player(((Scenario4) scenario).sounds.get(0).audioFormat, ((Scenario4) scenario).sounds.get(0).info);
                    TrueCoordinates rotation = new TrueCoordinates(3, 0, 0);
                    for (int i = 0; i < 5; i++){
                        player.play(scenario.buildNextStep(rotation));
                    }
                }
                break;

                case '8': {
                    scenario = new Scenario8("58");
                    Player player = new Player(((Scenario8) scenario).sounds.get(0).audioFormat, ((Scenario8) scenario).sounds.get(0).info);
                    TrueCoordinates rotation = new TrueCoordinates(3, 0, 0);
                    for (int i = 0; i < 5; i++){
                        player.play(scenario.buildNextStep(rotation));
                    }
                }
                break;

                    default:
                        break;
            }



    }

}
