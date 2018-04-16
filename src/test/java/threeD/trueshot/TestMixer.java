package threeD.trueshot;

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
		testMixTwo();
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
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
			soundLine.write(mixer.mix(),0, mixer.getMixedLength());
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
