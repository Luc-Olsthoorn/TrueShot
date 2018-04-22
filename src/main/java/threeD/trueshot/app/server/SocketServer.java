package threeD.trueshot.app.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import threeD.trueshot.app.scenarios.Scenario3;
import threeD.trueshot.app.scenarios.Scenario5;
import threeD.trueshot.app.scenarios.Scenario6;
import threeD.trueshot.app.scenarios.Scenario7;
import threeD.trueshot.app.scenarios.Scenario9;

import threeD.trueshot.app.scenarios.TrueScenario;
import threeD.trueshot.app.util.TrueCoordinates;

public class SocketServer {
    private SocketIOServer server;
    private int port;
    private TrueScenario currentScenario;

    SocketServer(int inputPort){
        port = inputPort;
        Configuration config = new Configuration();
        //config.setHostname("localhost");
        config.setPort(port);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        currentScenario = new Scenario3("58");
        this.newScenarioListener();
    }
    public void newScenarioListener()
    {
        server.addEventListener("updateScenario", ScenarioObject.class, new DataListener<ScenarioObject>() {

            @Override
            public void onData(SocketIOClient socketIOClient, ScenarioObject data, AckRequest ackRequest) throws Exception {
                // build depending on scenario
                String scenario =  data.getScenario();
                //TODO: EXTRACT THIS INTO ITS OWN CLASS GODDAMIT 
                switch (scenario)
                {
                    case "Scenario1":
                        break;
                    case "Scenario2":
                        break;
                    case "Scenario3":
                    //              currentScenario = new Scenario3();
                        break;
                    case "Scenario4":
                        break;
                    case "Scenario5":
                        currentScenario = new Scenario5("58");
                        break;
                    case "Scenario6":
                        currentScenario = new Scenario6("58");
                        break;
                    case "Scenario7":
                        currentScenario = new Scenario7("58");
                        break;
                    case "Scenario8":
                        break;
                    case "Scenario9":
                        currentScenario = new Scenario9("58");
                        break;
                    default:
                }           
                System.out.println(scenario);
            }
        });
    }
    public void addCoordinateListener(){
        server.addEventListener("updateCoordinates", CoordinateObject.class, new DataListener<CoordinateObject>() {
            @Override
            public void onData(SocketIOClient client, CoordinateObject data, AckRequest ackRequest) {
                
                try{
                    String rotation = data.getRotation();
                    System.out.println(rotation);
                    System.out.println("Recieved new x,y,z, processing...");
                    //byte[] audio = currentScenario.buildNextStep(new TrueCoordinates(1,1,1));
                    TestHeader testHeader=  new TestHeader();
                    byte[] audio = testHeader.testHeader();
                    client.sendEvent("sound", audio);
                    System.out.println("Sent");
                }catch(Exception e){
                    System.out.println("Invalid Json");
                    e.printStackTrace();     
                    return;
                }
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