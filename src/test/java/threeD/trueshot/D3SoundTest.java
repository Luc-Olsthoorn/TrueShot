package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

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
		HrtfSession session = new HrtfSession(Hrtf.getCipicSubject("58"), 90, 0);

		System.out.println("Azimuth: " + session.getAzimuth());
		System.out.println("Azimuth index: " + session.azimuthIndex);
		System.out.println("Elevation: " + session.getElevation());
		System.out.println("Elevation index: " + session.elevationIndex);
		System.out.println("Delay: " + session.getDelay());
		System.out.println("Hrir R: " + session.getHrir_r());
		System.out.println("Hrir R shape: " + session.getHrir_r().shapeInfoToString());
		System.out.println("Hrir L:");
		for (double dee : session.getHrir_l().data().asDouble())
		{
			System.out.print(dee + ", ");
		}
		System.out.println("Hrir L shape: " + session.getHrir_l().shapeInfoToString());

		// A large buffer size gives better results.
		D3Sound sound = new D3Sound(44100 * 8, new File("res/sound/test/cello-down.wav"), session);
		sound.step();

		sound.changeAzimuth(65);
		sound.step();

		sound.changeAzimuth(55);
		sound.step();

		sound.changeAzimuth(45);
		sound.step();

		sound.changeAzimuth(40);
		sound.step();

		sound.changeAzimuth(35);
		sound.step();

		sound.changeAzimuth(30);
		sound.step();

		sound.changeAzimuth(25);
		sound.step();

		sound.changeAzimuth(20);
		sound.step();

		sound.changeAzimuth(15);
		sound.step();

		sound.changeAzimuth(10);
		sound.step();

		sound.changeAzimuth(5);
		sound.step();

		sound.changeAzimuth(0);
		sound.step();

		while(sound.step());

		sound.reset();

		sound.changeAzimuth(-5);
		sound.step();

		sound.changeAzimuth(-10);
		sound.step();

		sound.changeAzimuth(-15);
		sound.step();

		sound.changeAzimuth(-20);
		sound.step();

		sound.changeAzimuth(-25);
		sound.step();

		sound.changeAzimuth(-30);
		sound.step();

		sound.changeAzimuth(-35);
		sound.step();

		sound.changeAzimuth(-40);
		sound.step();

		sound.changeAzimuth(-45);
		sound.step();

		sound.changeAzimuth(-55);
		sound.step();

		sound.changeAzimuth(-65);
		sound.step();

		sound.changeAzimuth(-80);
		sound.step();
		while(sound.step());
	}
}
