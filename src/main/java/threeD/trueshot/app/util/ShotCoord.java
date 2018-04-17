package threeD.trueshot.app.util;

/**
 * Grouping for a shooters locations.
 * Made everything public for easier readability
 */
public class ShotCoord
{
	public int x;
	public int y;
	public int z;
	public double azimuth;
	public double elevation;
	private double origin;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public ShotCoord(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		origin = 0;
		calculateAzimuth();
	}

	private void calculateAzimuth()
	{
		azimuth = Math.toDegrees(Math.atan2(x,y));
	}

	public void setOrigin(double origin)
	{
		azimuth = azimuth + this.origin;
		this.origin = origin;
		azimuth = azimuth - origin;
	}

	/**
	 * Gets the adjust azimuth that would be used with the HRTF Session.
	 * @return
	 */
	public double getAdjustedAzimuth()
	{
		if(azimuth > 90)
		{
			return (90 - (azimuth - 90));
		}
		else if(azimuth < -90)
		{
			return (-90 + ((-1 * azimuth) - 90));
		}
		else
		{
			return azimuth;
		}
	}

	/**
	 * Gets the adjust azimuth that would be used with the HRTF Session.
	 * @return
	 */
	public double getAdjustedElevation()
	{
		if(azimuth < -90
				|| azimuth > 90)
		{
			return 180;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public String toString()
	{
		return "Coordinates: " + "(" + x + "," + y + "," + z + ")" + "\nAzimuth: "
				+ azimuth + "\nElevation: " + elevation
				+ "\nOrigin: " + origin;
	}
}
