package threeD.trueshot.lib.audio;

import threeD.trueshot.lib.hrtf.HrtfSession;
import threeD.trueshot.lib.util.dsp.Converter;
import threeD.trueshot.lib.util.dsp.Convolution;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class D3Sound
{
	HrtfSession session;
	Convolution rightConvolution;
	Convolution leftConvolution;

	int BUFFER_SIZE;
	int bytesRead;
	SourceDataLine soundLine;
	File soundFile;
	AudioInputStream audioInputStream;
	AudioFormat audioFormat;
	DataLine.Info info;

	public D3Sound(int bufferSize, File soundFile, HrtfSession session)
	{
		this.session = session;
		BUFFER_SIZE = bufferSize;
		this.soundFile = soundFile;
		createConvolutioners();
		try
		{
			prepareSoundLine();
		} catch (LineUnavailableException
				| IOException
				| UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
	}

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

			// Writes audio data to the mixer via this source data line.
			soundLine.write(convoledData, 0, convoledData.length);
			return true;
		}
		return false;
	}

	private byte[] applyHrtf(byte[] sampledData)
	{
		// bytes to double
		double[] sampleAsDouble = Converter.sampleToDouble(sampledData, Converter.Type.WAV);

		// Convolution
		double[] convolvedLeft = leftConvolution.linearConv(sampleAsDouble);
		double[] convolvedRight = rightConvolution.linearConv(sampleAsDouble);

		// Convert back to byte arrays
		ByteBuffer left = ByteBuffer.wrap(Converter.doubleToSample(convolvedLeft, Converter.Type.WAV));
		ByteBuffer right = ByteBuffer.wrap(Converter.doubleToSample(convolvedRight, Converter.Type.WAV));

		ByteBuffer combined = ByteBuffer.allocate(left.capacity());

		// Grab two bytes from left convolution and two from right convoltion
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
		session.setAzimuth(elevation);
		createConvolutioners();
	}

	private void createConvolutioners()
	{
		rightConvolution = new Convolution(session.getHrir_r().data().asDouble());
		leftConvolution = new Convolution(session.getHrir_l().data().asDouble());
	}
}
