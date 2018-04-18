package threeD.trueshot.lib.audio;

import threeD.trueshot.lib.hrtf.HrtfSession;
import threeD.trueshot.lib.util.dsp.Converter;
import threeD.trueshot.lib.util.dsp.Convolution;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class D3Sound
{
	int WAVE_HEADER_SIZE = 44;

	public HrtfSession session;
	private double attenuation;
	private Convolution rightConvolution;
	private  Convolution leftConvolution;
	private int BUFFER_SIZE;
	private int bytesRead;
	private SourceDataLine soundLine;
	private File soundFile;
	private AudioInputStream audioInputStream;
	public AudioFormat audioFormat;
	public DataLine.Info info;
	private byte[] header = new byte[WAVE_HEADER_SIZE];
	private byte[] convolutedByteArray;

	/**
	 * Creates a 3D sound which can be played using the step() method.
	 * @param bufferSize
	 * @param soundFile
	 * @param session
	 */
	public D3Sound(int bufferSize, File soundFile, HrtfSession session)
	{
		this.session = session;
		BUFFER_SIZE = bufferSize;
		this.soundFile = soundFile;
		attenuation = 1.0;
		createConvolutioners();
		try
		{
			prepareSoundLine();
			FileInputStream stream = new FileInputStream(soundFile);
			int headerRead = stream.read(header);
			if(headerRead != WAVE_HEADER_SIZE)
			{
				System.out.println("Failed to read header");
			}
		} catch (LineUnavailableException
				| IOException
				| UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
	}

	/*
		Inits Sounds line
	 */
	private void prepareSoundLine() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		audioFormat = audioInputStream.getFormat();
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
		if(soundLine != null)
		{
			soundLine.close();
		}
		soundLine = (SourceDataLine) AudioSystem.getLine(info);
		soundLine.open(audioFormat);
		soundLine.start();
		bytesRead = 0;
	}


	/**
	 * Reads a buffer size load of data from the sound file and returns the convoluted byte array.
	 * @return true if data was read, false otherwise
	 */
	public byte[] stepSilent()
	{
		byte[] sampledData = new byte[BUFFER_SIZE];

		try
		{
			bytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (bytesRead >= 0)
		{
			byte[] convolutedData = applyHrtf(sampledData);
			//Copy this byte[] to a new byte[]
			convolutedByteArray = new byte[convolutedData.length];
			System.arraycopy(convolutedData, 0, convolutedByteArray, 0, convolutedData.length);
		}
		return convolutedByteArray;
	}

	/**
	 * Reads a buffer size load of data from the sound file and writes to sound line.
	 * @return true if data was read, false otherwise
	 */
	public boolean step()
	{
		byte[] sampledData = new byte[BUFFER_SIZE];

		try
		{
			bytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (bytesRead >= 0)
		{
			byte[] convoledData = applyHrtf(sampledData);

			//Copy this byte[] to a new byte[]
			convolutedByteArray = new byte[convoledData.length];
			System.arraycopy(convoledData, 0, convolutedByteArray, 0, convoledData.length);

			// Writes audio data to the mixer via this source data line.
			soundLine.write(convoledData, 0, convoledData.length); //This is the original convolved data
			return true;
		}
		return false;
	}

	/*
		Applies convolution.
	 */
	private byte[] applyHrtf(byte[] sampledData)
	{
		// bytes to double
		double[] sampleAsDouble = Converter.sampleToDouble(sampledData, Converter.Type.WAV);

		// Volume change
		if(attenuation != 1.0)
		{
			sampleAsDouble = applyAttenuation(sampleAsDouble);
		}

		// Convolution
		double[] convolvedLeft = leftConvolution.linearConv(sampleAsDouble);
		double[] convolvedRight = rightConvolution.linearConv(sampleAsDouble);

		// Convert back to byte arrays
		ByteBuffer left = ByteBuffer.wrap(Converter.doubleToSample(convolvedLeft, Converter.Type.WAV));
		ByteBuffer right = ByteBuffer.wrap(Converter.doubleToSample(convolvedRight, Converter.Type.WAV));

		ByteBuffer combined = ByteBuffer.allocate(left.capacity());

		// Grab two bytes from left convolution and two from right convolution
		while(left.remaining() >= 2)
		{
			combined.put(left.get());
			combined.put(left.get());
			combined.put(right.get());
			combined.put(right.get());

			left.getShort();
			right.getShort();
		}
		combined.flip();
		return combined.array();
	}

	/*
		Applies attenuation to the sample.
	 */
	private double[] applyAttenuation(double[] sampleAsDouble)
	{
		for (int i = 0; i < sampleAsDouble.length; i++)
		{
			sampleAsDouble[i] = sampleAsDouble[i] * attenuation;
		}
		return sampleAsDouble;
	}

	/**
	 * Resets the sound file back to before it was read.
	 */
	public void reset()
	{
		try
		{
			prepareSoundLine();
		} catch (IOException
				| LineUnavailableException
				| UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *  Changes the direction of the sound.
	 * @param azimuth
	 * @param elevation
	 */
	public void changeSoundDirection(double azimuth, double elevation)
	{
		session.setAzimuth(azimuth);
		session.setElevation(elevation);
		createConvolutioners();
	}

	/**
	 * Changes the azimuth of the sound
	 * @param azimuth
	 */
	public void changeAzimuth(double azimuth)
	{
		session.setAzimuth(azimuth);
		createConvolutioners();
	}

	/**
	 * Changes the elevation of the sounds.
	 * @param elevation
	 */
	public void changeElevation(double elevation)
	{
		session.setElevation(elevation);
		createConvolutioners();
	}

	/*
		Creates convolution objects.
	 */
	private void createConvolutioners()
	{
		this.rightConvolution = new Convolution(session.getHrir_r().data().asDouble());
		this.leftConvolution = new Convolution(session.getHrir_l().data().asDouble());
	}


	public int getfirstNonZero(){
		int firstNonZero = 0;
		for (int i = 0; i < convolutedByteArray.length; i++){
			int temp = convolutedByteArray[i];
			if (temp != 0) {
				System.out.println("First non-zero index is: "+i);
				firstNonZero = i;
				break;
			}
		}
		return firstNonZero;
	}

	public int getlastNonZero(){
		int lastNonZero = 0;
		for (int i = convolutedByteArray.length-1; i > 0 ; i--){
			int temp = convolutedByteArray[i];
			if (temp != 0){
				System.out.println("Last non-zero index is: "+i);
				lastNonZero = i;
				break;
			}
		}
		return lastNonZero;
	}

	/**
	 * Returns the sound with the header attached. This may be needed when a
	 * file has not been specified.
	 * @return the header + the convoluted sound.
	 */
	public byte[] soundWithHeader()
	{
		ByteBuffer buffer = ByteBuffer.allocate(convolutedByteArray.length + WAVE_HEADER_SIZE);
		buffer.put(convolutedByteArray);
		buffer.put(header);
		return buffer.array();
	}

	/**
	 * Gets the current steps convoluted byte array.
	 * @return
	 */
	public byte[] getConvolutedByteArray() {
		return convolutedByteArray;
	}


	/**
	 * Sets the attenuation for the sounds.
	 * Values (0.0 - 1.0)
	 * @param attenuation
	 */
	public void setAttenuation(double attenuation)
	{
		this.attenuation = attenuation;
	}

	/*private void modify(){
		int firstIndex = getfirstNonZero();
		int lastIndex = getlastNonZero();

		for (int i = lastIndex+1, j = firstIndex; i < convolutedByteArray.length; i++, j++){
//				System.arraycopy(convoledData, j, convolutedByteArray, i, 1);
			convolutedByteArray[i] = convolutedByteArray[j];
			if (j == lastIndex) {
				j =firstIndex; i+=1;
			}
		}
	}*/
}
