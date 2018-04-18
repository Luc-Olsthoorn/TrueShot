package threeD.trueshot.app.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import threeD.trueshot.app.scenarios.Scenario3;
import threeD.trueshot.app.scenarios.TrueScenario;
import threeD.trueshot.app.util.TrueCoordinates;

public class SocketServer {
    private SocketIOServer server;
    private int port;
    private TrueScenario currentScenario;

    SocketServer(int inputPort){
        port = inputPort;
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        currentScenario = new Scenario3("58");
    }
    //DEPRECATED FOR NOW
    public void newScenarioListener()
    {
        server.addEventListener("updateScenario", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient socketIOClient, byte[] bytes, AckRequest ackRequest) throws Exception {
                // build depending on scenario
                currentScenario = new Scenario3("58");
            }
        });
    }
    public void addCoordinateListener(){
        server.addEventListener("updateCoordinates", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
                System.out.println("Recieved new x,y,z, processing...");
                byte[] audio = currentScenario.buildNextStep(new TrueCoordinates(1,1,1));
                client.sendEvent("sound", audio);
                System.out.println("Sent");
            }
        });
    }
    public void run(){
        System.out.println("Socket Server attempting to run");
        try{
            server.start();
            Thread.sleep(Integer.MAX_VALUE);
            server.stop();
        }catch (Exception e) {
            System.out.println("Server Can't start");
            e.printStackTrace();     
            return;
        }
    }
}