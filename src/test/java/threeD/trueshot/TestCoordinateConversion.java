package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import threeD.trueshot.app.util.ShotCoord;

public class TestCoordinateConversion extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public TestCoordinateConversion( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(TestCoordinateConversion.class );
	}

	/**
	 * Test conversion
	 */
	public void testConversion()
	{
		ShotCoord cord1 = new ShotCoord(4, 4, 0);
		ShotCoord cord2	= new ShotCoord(-4, 2,0);
		ShotCoord cord3 = new ShotCoord(-5,-3,0);
		ShotCoord cord4 = new ShotCoord(5,0,0);
		ShotCoord cord5 = new ShotCoord(0,5,0);
		ShotCoord cord6 = new ShotCoord(5,-5,0);

		// Initial angles
		System.out.println(cord1.azimuth);
		System.out.println(cord2.azimuth);
		System.out.println(cord3.azimuth);
		System.out.println(cord4.azimuth);
		System.out.println(cord5.azimuth);
		System.out.println(cord6.azimuth + "\n");

		System.out.println(cord3.toString());
		System.out.println(cord3.azimuth);
		System.out.println(cord3.getAdjustedAzimuth());
		System.out.println(cord3.getAdjustedElevation());

		// Now to add some head movement
//		ShotCoord headLeft = new ShotCoord(-4, 4,0);
		ShotCoord headLeft = new ShotCoord(4, 4,0);

		System.out.println(headLeft.azimuth);
		cord1.setOrigin(headLeft.azimuth);
		System.out.println("Azimuth: " + cord1.azimuth);

		cord2.setOrigin(headLeft.azimuth);
		System.out.println("Azimuth: " + cord2.azimuth);

		cord3.setOrigin(headLeft.azimuth);
		System.out.println("Azimuth: " + cord3.azimuth);

		cord6.setOrigin(headLeft.azimuth);
		System.out.println("Azimuth: " + cord6.azimuth);

		cord5.setOrigin(headLeft.azimuth);
		System.out.println("Azimuth: " + cord5.azimuth);
	}
}
