package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SampleConverterTest extends TestCase
{
	public SampleConverterTest(String testName)
	{
		super(testName);
	}

	public static Test suite()
	{
		return new TestSuite(SampleConverterTest.class);
	}

	/**
	 *
	 */
	public void testConvertsSampleToDouble()
	{
		// Four bytes gets us 1 sample
		int BUFFER_SIZE = 4 * 5512;
		int nBytesRead = 0;
		int count = 1;

		byte[] sampledData = new byte[BUFFER_SIZE];
		double temp = 0;

		File soundsFile = new File(System.getProperty("user.dir") + "/res/sound/test/nope.wav" );
		AudioInputStream audioInputStream = null;

		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundsFile);
			nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
			ByteBuffer byteBuffer = ByteBuffer.wrap(sampledData);
			while(byteBuffer.remaining() >= 2)
			{
				temp = (double)((byteBuffer.get() & 0xff) | (byteBuffer.get() << 8));
				System.out.println("Count: " + count + "This is the value: " + temp);
				System.out.println("Count: " + count + "This is the value normalized: " + (temp / 32768.0));
				count++;
				byteBuffer.getShort();
			}
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
