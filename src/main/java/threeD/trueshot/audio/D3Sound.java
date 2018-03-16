package threeD.trueshot.audio;

import threeD.trueshot.hrtf.HrtfSession;
import threeD.trueshot.util.dsp.Converter;
import threeD.trueshot.util.dsp.Convolution;

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
		rightConvolution = new Convolution(session.getHrir_l().data().asDouble());
		leftConvolution = new Convolution(session.getHrir_l().data().asDouble());
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			audioFormat = audioInputStream.getFormat();
			info = new DataLine.Info(SourceDataLine.class, audioFormat);
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			bytesRead = 0;
		} catch (LineUnavailableException
				| IOException
				| UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
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
			System.out.println(sampledData.length);
			byte[] convoledData = applyHrtf(sampledData);
			// Writes audio data to the mixer via this source data line.
			soundLine.write(convoledData, 0,convoledData.length);
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
		double[] convolvedRight = leftConvolution.linearConv(sampleAsDouble);

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
}
