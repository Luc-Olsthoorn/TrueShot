package threeD.trueshot.hrtf;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import sun.nio.cs.ext.MacHebrew;

public class HrtfSession
{
	// User interacts with these
	private HrtfSubject subject;
	private double azimuth;
	private double elevation;

	// We allow user to get these
	private INDArray hrir_l;
	private INDArray hrir_r;
	private int delay;

	public int azimuthIndex;
	public int elevationIndex;
	private int ItdIndex;

	/**
	 *
	 * @param subject
	 * @param azimuth
	 * @param elevation
	 */
	public HrtfSession(HrtfSubject subject, double azimuth, double elevation)
	{
		hrir_l = Nd4j.create(200);
		hrir_r = Nd4j.create(200);

		this.subject = subject;
		this.azimuth = azimuth;
		this.elevation = elevation;
		restartSession();
	}

	public HrtfSession setAzimuth(double azimuth)
	{
		this.azimuth = azimuth;
		restartSession();
		return this;
	}

	public HrtfSession setElevation(double elevation)
	{
		this.elevation = elevation;
		restartSession();
		return this;
	}

	public HrtfSession setSubject(HrtfSubject subject)
	{
		this.subject = subject;
		restartSession();
		return this;
	}

	private void restartSession()
	{
		// Do all HRTF stuff here
		findClosestAngleIndex(false);
		findClosestAngleIndex(true);

		// Grab my hrirL
		hrir_l = getHrir_r().get(NDArrayIndex.point(azimuthIndex), NDArrayIndex.point(elevationIndex), NDArrayIndex.all());
		// Grab my hrirR


		// Grab my delay
	}

	private void findClosestAngleIndex(boolean findElevation)
	{
		final int INDEXES_PER_45_DEGREES = 8;
		if(findElevation)
		{
			if (azimuth > 0)
			{
				elevationIndex = (int) Math.round(azimuth / 5.625) + INDEXES_PER_45_DEGREES;
			}
			else
			{
				elevationIndex = (int) Math.abs(Math.round(azimuth / 5.625));
			}
		}
		else
		{
			double[] azimuths = subject.getPossibleAzimuths();
			double smallestDiff = Double.MAX_VALUE;
			for (int i = 0; i < azimuths.length; i++)
			{
				if (smallestDiff < Math.abs(azimuths[i] - elevation))
				{
					azimuthIndex = i - 1;
					break;
				}
				smallestDiff = Math.abs(azimuths[i] - elevation);
			}
		}
	}

	public double getAzimuth()
	{
		return azimuth;
	}

	public double getElevation()
	{
		return elevation;
	}

	public HrtfSubject getSubject()
	{
		return subject;
	}

	public INDArray getHrir_l()
	{
		return hrir_l;
	}

	public INDArray getHrir_r()
	{
		return hrir_r;
	}

	public int getDelay()
	{
		return delay;
	}
}
