package threeD.trueshot.app.server;


import threeD.trueshot.lib.audio.D3Sound;
import threeD.trueshot.lib.hrtf.Hrtf;
import threeD.trueshot.lib.hrtf.HrtfSession;

import java.io.File;
import java.nio.ByteBuffer;
public class TestHeader
{
	public static void main(String args[])
	{
		new TestHeader();
	}

	public TestHeader()
	{
		testHeader();
	}

	public byte[] testHeader()
	{
		// Creates a sound.
		HrtfSession session = new HrtfSession(Hrtf.getCipicSubject("58"), 0, 0);
		D3Sound soundToTestHeader = new D3Sound(44100 * 4, new File("res/sound/test/church.wav"), session);

		// step the sound
		soundToTestHeader.step();

		// sound with header can now be accessed
		byte[] soundWithHeader = soundToTestHeader.soundWithHeader();
		ByteBuffer buffer = ByteBuffer.wrap(soundWithHeader);
		System.out.println();
		/*
		for (int i = 0; i < 46; i++)
		{
			System.out.print(buffer.get() + " ");
		}
		*/
		return soundWithHeader;
		// send to client somehow

	}

}