package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import threeD.trueshot.audio.D3Sound;
import threeD.trueshot.hrtf.Hrtf;
import threeD.trueshot.hrtf.HrtfSession;

import java.io.File;

public class D3SoundTest extends TestCase
{
	public D3SoundTest(String name)
	{
		super(name);
	}

	public static Test suite()
	{
		return new TestSuite(D3Sound.class);
	}

	public void test3DSound()
	{
		HrtfSession session = new HrtfSession(Hrtf.getCipicSubject("58"), -45, 90);


		System.out.println("Azimuth: " + session.getAzimuth());
		System.out.println("Azimuth index: " + session.azimuthIndex);
		System.out.println("Elevation: " + session.getAzimuth());
		System.out.println("Elevation index: " + session.elevationIndex);
		System.out.println("Delay: " + session.getDelay());
		System.out.println("Hrir R: " + session.getHrir_r());
		System.out.println("Hrir R shape: " + session.getHrir_r().shapeInfoToString());
		System.out.println("Hrir L: " + session.getHrir_l());
		System.out.println("Hrir L shape: " + session.getHrir_l().shapeInfoToString());

		D3Sound sound = new D3Sound(5512 * 4, new File("res/sound/test/nope.wav"), session);
		while(sound.step());
	}
}
