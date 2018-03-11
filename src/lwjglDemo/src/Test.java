import org.lwjgl.Sys;

import java.io.IOException;
import java.util.Scanner;


import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {
    public static void main(String args[]) throws IOException {
//        AudioMaster.init();
//        AudioMaster.setListenerData();
//        int buffer = AudioMaster.loadSound("Footsteps.wav");

        // Loop. User input
        System.out.println("3D Audio Test");
        String str = "";
        char c = ' ';
        Scanner stdin = new Scanner(System.in);


        try {
            System.out.println("Input \"k\" to input coordinates by keyboard, input \"m\" to select coordinates by mouse");
            c = (char) stdin.nextLine().charAt(0);
        } catch (Exception ex) {
            c = 'q';
        }

        switch (c) {
            case 'k': {
                //initial and load sound
                AudioMaster.init();
                AudioMaster.setListenerData();
                int buffer = AudioMaster.loadSound("Footsteps.wav");

                System.out.println("Please input x y z coordinates of sound,\n(split by space): ");
                str = stdin.nextLine();
                String[] arr = str.split(" ");
                Source source = new Source(Float.parseFloat(arr[0]), Float.parseFloat(arr[1]), Float.parseFloat(arr[2]));

                source.play(buffer);
                try {
                    //duration
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {
                }

                source.delete();
                AudioMaster.cleanUp();
            }
            break;

            case 'm': {
                JFrame jf = new JFrame("Coordinates Selector");
                jf.setSize(600, 600);
                jf.setVisible(true);
                jf.setLayout(new FlowLayout(FlowLayout.CENTER));
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JLabel label = new JLabel("Click and show the coordinates");
                jf.add(label);

                jf.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) { //1 is left, 3 is right

                            double x = (double) e.getX() / 100;
                            double y = (double) e.getY() / 100;


                            String banner = "Sound coordinate is (" + (float) (x - 3) + "," + (float) ((-y) + 3) + ")";
                            label.setText(banner);

                            //initial and load sound
                            AudioMaster.init();
                            AudioMaster.setListenerData();
                            int buffer = AudioMaster.loadSound("Footsteps.wav");


                            Source source = new Source((float) (x - 3), (float) ((-y) + 3), 0);
                            source.play(buffer);
                            try {
                                //duration
                                Thread.sleep(3000);
                            } catch (InterruptedException ignored) {
                            }

                            source.delete();
                            AudioMaster.cleanUp();

                        }
                    }
                });
            }
            break;
        }
    }
}
