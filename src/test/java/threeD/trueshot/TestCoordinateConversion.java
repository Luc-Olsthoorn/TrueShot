package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import threeD.trueshot.app.util.TrueCoordinates;

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
		TrueCoordinates cord1 = new TrueCoordinates(4, 4, 0);
		TrueCoordinates cord2	= new TrueCoordinates(-4, 2, 0);
		TrueCoordinates cord3 = new TrueCoordinates(-5, -3, 0);
		TrueCoordinates cord4 = new TrueCoordinates(5, 0, 0);
		TrueCoordinates cord5 = new TrueCoordinates(0, 5, 0);
		TrueCoordinates cord6 = new TrueCoordinates(5, -5, 0);

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
//		TrueCoordinates headLeft = new TrueCoordinates(-4, 4,0);
		TrueCoordinates headLeft = new TrueCoordinates(4, 4, 0);

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
