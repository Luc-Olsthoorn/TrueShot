package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import threeD.trueshot.util.dsp.Converter;

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
		double[] d_sampledData = new double[(int) (BUFFER_SIZE/4.0)];

		File soundsFile = new File(System.getProperty("user.dir") + "/res/sound/test/nope.wav" );
		AudioInputStream audioInputStream = null;

		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundsFile);
			nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
			d_sampledData = Converter.sampleToDouble(sampledData, Converter.Type.WAV);
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		for (double d_sample: d_sampledData)
		{
			System.out.println("Count: " + count + "This is the value: " + d_sample);
		}

		ByteBuffer actualSample = ByteBuffer.wrap(sampledData);
		ByteBuffer testSample = ByteBuffer.wrap(Converter.doubleToSample(d_sampledData, Converter.Type.WAV));

		for (int i = 0; i < BUFFER_SIZE / 4.0; i++)
		{
			// Check to see if the bytes match
			assertEquals(actualSample.get(), testSample.get());
			assertEquals(actualSample.get(), testSample.get());

			// This is the channel we didn't do anything with
			actualSample.getShort();
			testSample.getShort();
		}
	}


	public void testConvertDoubleToSample()
	{

	}
}
