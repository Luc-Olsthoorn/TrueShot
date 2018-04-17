package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;

/**
 * Two shots at the same time.
 * Twos shots happen at the same time at different locations. Can we identify that both have happened? Does this disorientate the user?
 */
public class Scenario7 implements  TrueScenario
{
	@Override
	public byte[] buildNextStep(TrueCoordinates newRotation)
	{
		return new byte[0];
	}
}
