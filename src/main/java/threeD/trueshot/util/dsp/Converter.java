package threeD.trueshot.util.dsp;

import java.nio.ByteBuffer;

public class Converter
{

	/**
	 * Supported file formats for the converter.
	 */
	public enum Type { WAV }

	/**
	 * Converts a sample of a given file format to a double array of the same length.
	 * @param sample the sample of the given file format.
	 * @param format the file format
	 * @return the double array containing the samples
	 */
	public static double[] sampleToDouble(byte[] sample, Type format)
	{
		int nBytesRead = 0;
		double temp = 0;
		double[] doubleSamples = new double[(sample.length/4)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(sample);
		int count = 0;

		while(byteBuffer.remaining() >= 2)
		{
			doubleSamples[count++] = ((double)((byteBuffer.get() & 0xff) | (byteBuffer.get() << 8))) / 32768.0;
			byteBuffer.getShort();
		}
		return doubleSamples;
	}

	/**
	 *
	 * Converts the double array, to sample of the given format.
	 * @param  dbNorArray the sample of the given file format.
	 * @param format the file format
	 * @return the byte array containing the samples
	 */
	public static byte[] doubleToSample(double[] dbNorArray, Type format)
	{
		byte[] bytes = new byte[dbNorArray.length*4];
		short[]  stArray = new short[dbNorArray.length];

		//de-normalize
		for (int i = 0; i < dbNorArray.length; i++)
		{
			stArray[i] = (short) (dbNorArray[i] * 32768.0);
		}

		int count = 0;
		for (int i = 0; i < stArray.length; i++)
		{
			byte[] temp = {short2Byte(stArray[i])[1], short2Byte(stArray[i])[0]};
			bytes[count] = temp[0];
			bytes[count+1] = temp[1];
			bytes[count+2] = temp[0];
			bytes[count+3] = temp[1];

			count += 4;
		}
		return bytes;
	}

	private static byte[] short2Byte(int s)
	{
		byte[] targets = new byte[2];
		targets[0] = (byte) (s >> 8 & 0xFF);
		targets[1] = (byte) (s & 0xFF);
		return targets;
	}
}
