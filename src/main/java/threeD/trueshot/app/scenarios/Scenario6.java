package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Mixer;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import java.io.File;
import java.util.ArrayList;

/**
 * Similar Gunfire Locations
 * Two shots with very similar origins. Can we determine that these shots are close but different?
 */
public class Scenario6 implements TrueScenario
{
	private ArrayList<D3Sound> sounds;
	private ArrayList<HrtfSession> sessions;
	public D3Mixer mixer;
	private int frame;
	private String subject;
	private String[] soundsFiles =
			{
					"res/sound/test/cat.wav",
					"res/sound/test/crunch.wav"
			};

	/*
		Four shooters.
	 */
	private TrueCoordinates[] shotCoords  =
			{
					new TrueCoordinates(5, -4, 0),
					new TrueCoordinates(6, -2, 0),
			};

	/**
	 * $
	 */
	public Scenario6(String subject)
	{
		frame = 1;
		this.subject = subject;
		sessions = new ArrayList<>();
		sounds = new ArrayList<>();

		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));

		sounds.add(new D3Sound(44100 * 4, new File(soundsFiles[0]), sessions.get(0)));
		sounds.get(0).setAttenuation(0.2);
		sounds.add(new D3Sound(44100 * 4, new File(soundsFiles[1]), sessions.get(1)));
		sounds.get(1).setAttenuation(0.5);

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

		// Could make this a class for complex sounds
		switch (frame)
		{
			case 1:
				sounds.get(0).stepSilent();
				sounds.get(1).halfStepSilent("delay");
				break;
			case 2:
				sounds.get(0).stepSilent();
				sounds.get(1).stepSilent();
				break;
			default:
				break;
		}
		frame++;

		// Step all for this scenario and feed to server.
		return mixer.mix();
	}
}
