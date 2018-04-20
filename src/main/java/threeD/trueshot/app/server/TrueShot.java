package threeD.trueshot.app.server;

import threeD.trueshot.app.scenarios.*;
import threeD.trueshot.app.util.TrueCoordinates;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class TrueShot
{
	private ServerSocket serverSocket;
	private TrueScenario currentScenario;

	public TrueShot(int port)
	{
		initServer(port);
		startServer();
	}

	/*
		Currently not multi threaded, single connection, single instance.
	 */
	private void startServer()
	{
		try
		{
			ClientConnection clientConnection = new ClientConnection(serverSocket.accept());

			// While the client still wants to interact with server
			while(!clientConnection.exit())
			{
				// Blocks waiting for scenario number
				startScenario(clientConnection.getScenarioNumber());
				TrueCoordinates rotationCoordinates = null;
				byte[] nextStep = null;

				// While scenario is not complete
				while((rotationCoordinates = clientConnection.waitRotationEvent()) != null)
				{
					// Pass this new rotation to our scenario
					nextStep = currentScenario.buildNextStep(rotationCoordinates);

					// Send the resulting byte array from the rotation
					clientConnection.sendNextStep(nextStep);
				}
			}
		} catch(SocketTimeoutException timeOut)
		{
			System.out.println("Timed out!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
		Creates the correct scenario.
	 */
	private void startScenario(String scenarioNumber)
	{
		switch (scenarioNumber)
		{
			case "1":
				break;
			case "2":
				break;
			case "3":
//				currentScenario = new Scenario3();
				break;
			case "4":
				break;
			case "5":
				currentScenario = new Scenario5("58");
				break;
			case "6":
				currentScenario = new Scenario6("58");
				break;
			case "7":
				currentScenario = new Scenario7("58");
				break;
			case "8":
				break;
			case "9":
				currentScenario = new Scenario9("58");
				break;
			default:
		}
	}

	/*
		Creates the current server.
	 */
	private void initServer(int port)
	{
		try
		{
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}


}
