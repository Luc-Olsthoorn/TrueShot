package threeD.trueshot.app.scenarios;

import threeD.trueshot.app.util.TrueCoordinates;

/**
 * Hard code everything you need for the scenario
 * in the actual scenario, such as sound files,
 * data lines, etc.
 *
 * Implement this method, it abstracts away everything.
 * Server gives a scenario some coordinates, the scenario
 * should produce the correct byte array.
 */
public interface TrueScenario
{
	public byte[] buildNextStep(TrueCoordinates newRotation);
}
