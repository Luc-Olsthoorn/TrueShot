package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;
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
	public D3Mixer mixer;
	private String subject;
	private String[] soundsFiles =
			{
				"res/sound/test/cello-down.wav",
				"res/sound/test/cat.wav",
				"res/sound/test/crunch.wav",
				"res/sound/test/gong.wav"
			};

	/*
		Four shooters.
	 */
	private TrueCoordinates[] shotCoords  =
			{
				new TrueCoordinates(4, 4, 0),
				new TrueCoordinates(-4, 2, 0),
				new TrueCoordinates(-5, -3, 0),
				new TrueCoordinates(5, 0, 0)
			};

	/**
	 * $
	 */
	public Scenario3(String subject)
	{
		this.subject = subject;
		sessions = new ArrayList<>();
		sounds = new ArrayList<>();

		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));

		sounds.add(new D3Sound(44100 * 20, new File(soundsFiles[0]), sessions.get(0)));
		sounds.add(new D3Sound(44100 * 20, new File(soundsFiles[1]), sessions.get(1)));
		sounds.add(new D3Sound(44100 * 20, new File(soundsFiles[2]), sessions.get(2)));
		sounds.add(new D3Sound(44100 * 20, new File(soundsFiles[3]), sessions.get(3)));

		mixer = new D3Mixer(sounds);
	}

	/**
	 * Builds the next sound for scenario 3, multi-shot.
	 * @param headRotation
	 * @return
	 */
	@Override
	public byte[] buildNextStep(TrueCoordinates headRotation)
	{
		int index = 0;
		for (D3Sound sound:
		     sounds)
		{
			// Set the origin to this new rotation angle
			shotCoords[index].setOrigin(headRotation.azimuth);

			// Now update the sound to use the correct azimuth and elevation
			sound.changeSoundDirection(shotCoords[index].getAdjustedAzimuth(), shotCoords[index].getAdjustedElevation());
			index++;
		}

		mixer.stepAll();

		// Step all for this scenario and feed to server.
		return mixer.mix();
	}

}










