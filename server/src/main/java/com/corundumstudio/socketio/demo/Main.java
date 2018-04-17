package com.corundumstudio.socketio.demo;

public class Main{
	public static void main(String[] args) throws Exception{
		SoundAnalyzer soundAnalyzer = new SoundAnalyzer();
		SocketServer socketServer = new SocketServer(9092);
		HttpServer httpServer = new HttpServer(8000);
		
		socketServer.addCoordinateListener(soundAnalyzer);
	}
}