package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;

/**
 * Single shot added comms.
 * Single-shot with additional background military communications. How much does the communication interfere with locating the gunshot?
 */
public class Scenario5 implements TrueScenario
{
	@Override
	public byte[] buildNextStep(TrueCoordinates newRotation)
	{
		return new byte[0];
	}
}
