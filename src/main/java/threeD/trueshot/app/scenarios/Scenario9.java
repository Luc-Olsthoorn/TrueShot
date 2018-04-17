package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;

/**
 * Trajectory of Bullet.
 * Map the trajectory of the bullet if it intersects a given radius around the user. Is this useful? Can the user imagine the trajectory of bullet?
 */
public class Scenario9 implements TrueScenario
{
	@Override
	public byte[] buildNextStep(TrueCoordinates newRotation)
	{
		return new byte[0];
	}
}
