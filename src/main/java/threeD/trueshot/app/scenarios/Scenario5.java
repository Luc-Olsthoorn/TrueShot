package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;
import threeD.trueshot.lib.audio.D3Mixer;
import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import java.io.File;
import java.util.ArrayList;

/**
 * Single shot added comms.
 * Single-shot with additional background military communications. How much does the communication interfere with locating the gunshot?
 */
public class Scenario5 implements TrueScenario
{
	private ArrayList<D3Sound> sounds;
	private ArrayList<HrtfSession> sessions;
	public D3Mixer mixer;
	private String subject;
	private int frame;
	private String[] soundsFiles =
			{
					"res/sound/test/radioGarble.wav",
					"res/sound/test/crunch.wav",
			};

	/*
<<<<<<< Updated upstream
		Four shooters.
=======
		Single
>>>>>>> Stashed changes
	 */
	private TrueCoordinates[] shotCoords  =
			{
					new TrueCoordinates(0, 0, 0),
					new TrueCoordinates(4, -4, 0),
			};

	/**
<<<<<<< Updated upstream
	 * $
=======
	 * $ Constructor
>>>>>>> Stashed changes
	 */
	public Scenario5(String subject)
	{
		this.subject = subject;
		sessions = new ArrayList<>();
		sounds = new ArrayList<>();
		frame = 0;

		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));
		sessions.add(new HrtfSession(Hrtf.getCipicSubject(subject), 0, 0));

		sounds.add(new D3Sound(44100 * 4, new File(soundsFiles[0]), sessions.get(0)));
		sounds.get(0).setAttenuation(0.8);

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

		switch (frame)
		{
			case 0:
				sounds.get(0).stepSilent();
				break;
			case 1:
				sounds.get(0).stepSilent();
				break;
			case 2:
				sounds.get(0).stepSilent();
				sounds.get(1).stepSilent();
				break;
			default:
				break;
		}
		frame++;

		// TODO: Not checking for all sounds finished, need to do this
		// Step all for this scenario and feed to server.
		return mixer.mixAddHeader();
	}

	@Override
	public ScenarioInfo scenarioInfo()
	{
		ScenarioInfo info = new ScenarioInfo();
		info.mixer = this.mixer;
		return info;
	}
}
