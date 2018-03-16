package threeD.trueshot;

import threeD.trueshot.hrtf.Hrtf;
import threeD.trueshot.hrtf.HrtfSession;

/**
 * Launcher for TrueShot
 */
public class App 
{
    public static void main( String[] args )
    {
        HrtfSession session = new HrtfSession(Hrtf.getCipicSubject("58"), 20, 20);
        System.out.println("Azimuth: " + session.getAzimuth() + " elevation: " + session.getElevation());
        System.out.println("Azimuth Index: " + session.azimuthIndex + " elevation: " + session.elevationIndex);
        System.out.println("Azimuth Index: " + session.azimuthIndex + " elevation: " + session.elevationIndex);
    }
}
