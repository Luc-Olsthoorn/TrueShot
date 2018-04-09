
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import static java.lang.Thread.sleep;


public class CoordinateSoundLauncher extends JFrame
{
    private Image image;
    private float xx;
    private float yy;
    private HrtfSession session;
    private D3Sound sound;
    private boolean triggered;

    public boolean clickedOnce = false;
    public boolean buttonPress = false;

    public float getXx()
    {
        return xx;
    }

    public float getYy()
    {
        return yy;
    }

    public void setXx(float xx)
    {
        this.xx = xx;
    }

    public void setYy(float yy)
    {
        this.yy = yy;
    }

    public CoordinateSoundLauncher()
    {
        this.setTitle("Mouse");
        this.setBounds(0, 0, 612, 650);//window size
//        this.setVisible(true);
//        this.setLayout(new CardLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit for close


        //Label
        JLabel label = new JLabel("Click to select a position of sound source...");
        label.setBounds(300, 18, 300, 20);
        this.add(label);
        label.setVisible(true);

        JLabel label2 = new JLabel("User Input:");
        label2.setBounds(5, 18, 70, 20);
        this.add(label2);
        label2.setVisible(true);

        JTextField textX = new JTextField("x value");
        textX.setBounds(80, 18, 50, 20);
        this.add(textX);
        textX.setVisible(true);

        JTextField textY = new JTextField("y value");
        textY.setBounds(140, 18, 50, 20);
        this.add(textY);
        textY.setVisible(true);

        JButton btn1 = new JButton("Listen");
        btn1.setBounds(200, 18, 80, 20);
        this.add(btn1);
        btn1.setVisible(true);
        //Add button press action
        btn1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Input sound source coordinate is: ");
                setXx((Float.valueOf(textX.getText())));
                setYy((Float.valueOf(textY.getText())));
                System.out.println("(" + getXx() + ", " + getYy() + ")");
                buttonPress = true;

                double azimuth = Math.atan2(getYy(), getXx()) * (180 / Math.PI);
                sound.reset();
                sound.changeAzimuth((90 - azimuth));
                System.out.println("Current Angle Position: " + (90 - azimuth));
                while (sound.step())
                {
                    ;
                }
            }
        });

        JPanel contentPane = new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                //Pait the image file as background.

                ImageIcon icon = new ImageIcon("res/images/back.JPG");
                image = icon.getImage();
                g.drawImage(image, 0, 50, null);
                // TODO Auto-generated method stub
            }
        };

        contentPane.addMouseListener(new MouseListener()
                                     {
                                         @Override
                                         public void mouseClicked(MouseEvent e)
                                         {
                                             double x = (double) e.getX() / 100;
                                             double y = (double) e.getY() / 100;
                                             setXx((float) (x - 3));
                                             setYy((float) (4.2 - y));
                                             String banner = "Sound source coordinate is:  (" + getXx() + "," + getYy() + ")";
                                             label.setText(banner);  //Reset label content
                                             clickedOnce = true;

                                             if (sound == null)
                                                 return;
                                             double azimuth = Math.atan2(getYy(), getXx()) * (180 / Math.PI);
                                             sound.reset();
                                             sound.changeAzimuth((90 - azimuth));
                                             System.out.println("Current Angle Position: " + (90 - azimuth));
                                             while (sound.step())
                                             {
                                                 ;
                                             }
                                         }

                                         @Override
                                         public void mousePressed(MouseEvent e)
                                         {

                                         }

                                         @Override
                                         public void mouseReleased(MouseEvent e)
                                         {

                                         }

                                         @Override
                                         public void mouseEntered(MouseEvent e)
                                         {

                                         }

                                         @Override
                                         public void mouseExited(MouseEvent e)
                                         {

                                         }
                                     }
        );
        this.add(contentPane);
        this.setVisible(true);
    }

    /**
     * To use this GUI, create a instance of CoordinateSoundLauncher first,
     * Then use the while loop to pause and check whether the button
     * was clicked or mouse was clicked.
     * After the one action, coordinates (x, y) of this action can be obtained.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        CoordinateSoundLauncher mouse = new CoordinateSoundLauncher();
        while (!mouse.clickedOnce && !mouse.buttonPress)
        {
            sleep(1000);
        }
        double x = mouse.getXx();
        double y = mouse.getYy();

        // Converts the magnitude value to degrees.
        double azimuth = Math.atan2(y, x) * (180 / Math.PI);

        HrtfSession session = new HrtfSession(Hrtf.getCipicSubject("58"), (90 - azimuth), 0);
        D3Sound sound = new D3Sound(5512 * 100, new File("res/sound/test/rain.wav"), session);

        mouse.setSession(session);
        mouse.setSound(sound);
        while (sound.step())
    {
        ;
    }
    }

    public void setSession(HrtfSession session)
    {
        this.session = session;
    }

    public void setSound(D3Sound sound)
    {
        this.sound = sound;
    }
}
