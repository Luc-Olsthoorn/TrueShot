package threeD.trueshot;

import threeD.trueshot.app.scenarios.Scenario3;
import threeD.trueshot.app.scenarios.Scenario6;
import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Mixer;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class TestMixer
{

	public TestMixer()
	{
//		testMixTwo();
//		testScenario3();
		testScenario6();
	}

	private void testScenario6()
	{
		Scenario6 scenario6 = new Scenario6("58");
		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(new File("res/sound/test/church.wav"));
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		AudioFormat audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine soundLine;
		try
		{
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			soundLine.write(scenario6.buildNextStep(new TrueCoordinates(0,0,0)), 0, scenario6.mixer.getMixedLength());
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}


	private void testScenario3()
	{
		Scenario3 scenario3 = new Scenario3("58");

		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(new File("res/sound/test/church.wav"));
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		AudioFormat audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine soundLine;
		try
		{
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			soundLine.write(scenario3.buildNextStep(new TrueCoordinates(0,0,0)), 0, scenario3.mixer.getMixedLength());
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	private void testMixTwo()
	{
		// Sound 1
		HrtfSession session = new HrtfSession(Hrtf.getCipicSubject("58"), 90, 0);
		D3Sound sound1 = new D3Sound(44100 * 8, new File("res/sound/test/cello-down.wav"), session);

		// Sound 2
		HrtfSession session2 = new HrtfSession(Hrtf.getCipicSubject("58"), -90, 0);
		D3Sound sound2 = new D3Sound(44100 * 8, new File("res/sound/test/crunch.wav"), session2);

		// Sound 3
		HrtfSession session3 = new HrtfSession(Hrtf.getCipicSubject("58"), -90, 0);
		D3Sound sound3 = new D3Sound(44100 * 8, new File("res/sound/test/cat.wav"), session3);

		// Sound 4
		HrtfSession session4 = new HrtfSession(Hrtf.getCipicSubject("58"), 0, 0);
		D3Sound sound4 = new D3Sound(44100 * 8, new File("res/sound/test/explosion.wav"), session4);

		D3Mixer mixer = new D3Mixer();
		mixer.addSound(sound1);
		mixer.addSound(sound2);
		mixer.addSound(sound3);
		mixer.addSound(sound4);


		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(new File("res/sound/test/church.wav"));
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		AudioFormat audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine soundLine;
		try
		{
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			mixer.stepAll();
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
			mixer.stepAll();
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
			mixer.stepAll();
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
			mixer.stepAll();
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new TestMixer();
	}
}
