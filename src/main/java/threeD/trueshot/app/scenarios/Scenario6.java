package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.RotationEvent;

/**
 * Similar Gunfire Locations
 * Two shots with very similar origins. Can we determine that these shots are very close?
 */
public class Scenario6 implements TrueScenario
{
	@Override
	public byte[] buildNextStep(RotationEvent newRotation)
	{
		return new byte[0];
	}
}
