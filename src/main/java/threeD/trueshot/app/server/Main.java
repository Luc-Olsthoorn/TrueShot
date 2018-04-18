package threeD.trueshot.app.server;

public class Main{
	public static void main(String[] args) throws Exception{
		Thread thread1 = new Thread() {
		    public void run() {
		    	HttpServer httpServer = new HttpServer(8000);
		    }
		};

		Thread thread2 = new Thread() {
		    public void run() {
		        SocketServer socketServer = new SocketServer(9092);
				socketServer.addCoordinateListener();
				socketServer.run();
		    }
		};


		thread1.start();
		thread2.start();
		
		
	}
}