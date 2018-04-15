package threeD.trueshot.app.util;

/**
 * Create this and pass it to each scenario when we get a rotation.
 */
public class RotationEvent
{
	private double x;
	private double y;
	private double z;
	private double azimuth;
	private double elevation;

	public RotationEvent(double x,double y,double z)
	{
		this.x =x;
		this.y = y;
		this.z = z;
		calculateAzimuth();
		calculateElevation();
	}

	private void calculateAzimuth()
	{

	}

	private void calculateElevation()
	{

	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	public double getAzimuth()
	{
		return azimuth;
	}

	public double getElevation()
	{
		return elevation;
	}
}
