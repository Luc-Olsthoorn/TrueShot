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

}
