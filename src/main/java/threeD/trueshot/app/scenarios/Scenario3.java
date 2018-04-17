package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.RotationEvent;
import threeD.trueshot.app.util.ShotCoord;
import threeD.trueshot.lib.audio.D3Mixer;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import java.io.File;
import java.util.ArrayList;

/**
 * Multi Shot
 * Multiple shooters, shooting from multiple locations. Are the multiple shooter able to be identified?
 * How many can the average person identify?
 */
public class Scenario3 implements TrueScenario
{
	private ArrayList<D3Sound> sounds;
	private ArrayList<HrtfSession> sessions;
	private D3Mixer mixer;
	private String subject;
	private String[] soundsFiles = {
			"res/sound/test/cat.wav",
			"res/sound/test/explosion.wav",
			"res/sound/test/chrunch.wav"
	};

	/*
		Three shooters.
	 */
	private ShotCoord[] shotCoords  = {
			new ShotCoord(4,4,0),
			new ShotCoord(-4, 2,0),
			new ShotCoord(-5,-3,0),
			new ShotCoord(5,0,0)
	};

	/**
	 *
	 */
	public Scenario3(String subject)
	{
		this.subject = subject;
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));

		sounds.add(new D3Sound(44100 * 4, new File(soundsFiles[0]), sessions.get(0)));
		sounds.add(new D3Sound(44100 * 4, new File(soundsFiles[1]), sessions.get(1)));
		sounds.add(new D3Sound(44100 * 4, new File(soundsFiles[2]), sessions.get(2)));

		D3Mixer mixer = new D3Mixer(sounds);
	}

	@Override
	public byte[] buildNextStep(RotationEvent newRotation)
	{
		int index = 0;
		for (D3Sound sound:
		     sounds)
		{
		}
		// Calculate the next correct azimuth and elevation values for the given rotation
		// Against the coordinates of the shots

		// Change values in sounds

		// Mix new sounds

		// Feed array back to server
		return new byte[0];
	}

}










