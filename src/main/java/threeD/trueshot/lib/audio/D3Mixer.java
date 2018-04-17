package threeD.trueshot.lib.audio;

import java.util.ArrayList;
import java.util.Arrays;

public class D3Mixer
{
	ArrayList<D3Sound> sounds;
	public int mixedLength;

	/**
	 * Sounds to add to the mixer.
	 * @param sounds
	 */
	public D3Mixer(ArrayList<D3Sound> sounds)
	{

		this.sounds = sounds;
		mixedLength = 0;
	}

	/**
	 * Constructor
	 */
	public D3Mixer()
	{
		this.sounds = new ArrayList<>();
	}


	/**
	 * Adds a sound to the mixer
	 * @param sound
	 */
	public void addSound(D3Sound sound)
	{
		sounds.add(sound);
	}


	/**
	 * Mixes the sounds added to this mixer object, returns the
	 * resulting byte array.
	 * @return mixed byte array
	 */
	public byte[] mix()
	{
		byte[] mixedArray = sounds.get(0).getConvolutedByteArray();
		boolean newSmaller = false;

		// For each sound
		for (D3Sound sound: sounds.subList(1, sounds.size()))
		{
			byte[] soundArray = sound.getConvolutedByteArray();
			int smaller;
			int larger;

			// Find the smaller byte size
			if(soundArray.length > mixedArray.length)
			{
				smaller = mixedArray.length;
				larger = soundArray.length;
				newSmaller = false;
			}
			else
			{
				smaller = soundArray.length;
				larger = mixedArray.length;
				newSmaller = true;
			}

			// If new sound sample is larger, we need to
			// increase our mixed sample length to fit this
			if(mixedArray.length < larger)
			{
				mixedArray = Arrays.copyOf(mixedArray, larger);
			}

			for (int i = 0; i < smaller; i += 2)
			{
				short buffer1A = mixedArray[i + 1];
				short buffer2A = mixedArray[i];
				buffer1A = (short) ((buffer1A & 0xff) << 8);
				buffer2A = (short) (buffer2A & 0xff);

				short buffer1B = soundArray[i + 1];
				short buffer2B = soundArray[i];
				buffer1B = (short) ((buffer1B & 0xff) << 8);
				buffer2B = (short) (buffer2B & 0xff);

				short buffer1C = (short) (buffer1A + buffer1B);
				short buffer2C = (short) (buffer2A + buffer2B);

				short result = (short) (buffer1C + buffer2C);

				mixedArray[i] = (byte) result;
				mixedArray[i + 1] = (byte) (result >> 8);
			}
		}

		mixedLength = mixedArray.length;
		return mixedArray;
	}

	/**
	 * The length of the mixed array.
	 * @return mixed array length
	 */
	public int getMixedLength()
	{
		return mixedLength;
	}

	/**
	 * Steps all the sounds. When you don't want to individually step them yourself.
	 */
	public void stepAll()
	{
		for (D3Sound sound:
		     sounds)
		{
			sound.stepSilent();
		}
	}
}

























