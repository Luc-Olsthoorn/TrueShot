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
	}
}
