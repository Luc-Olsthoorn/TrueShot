package threeD.trueshot.app.server;

import threeD.trueshot.app.util.RotationEvent;

import java.net.Socket;

public class ClientConnection
{
	private Socket clientSocket;
	private boolean finished;

	public ClientConnection(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		finished = false;
	}


	/**
	 * This blocks waiting for the next scenario number.
	 * @return
	 */
	public String getScenarioNumber()
	{
		return "";
	}

	/**
	 * This Blocks waiting for next set of cordinates
	 * @return
	 */
	public RotationEvent waitRotationEvent()
	{
		return null;
	}

	/**
	 * Send a byte array to the client
	 * @param toSend
	 */
	public void sendNextStep(byte[] toSend)
	{

	}

	public boolean exit()
	{
		return finished;
	}
}
