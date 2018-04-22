package threeD.trueshot.app.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import threeD.trueshot.app.scenarios.Scenario1;
import threeD.trueshot.app.scenarios.Scenario2;
import threeD.trueshot.app.scenarios.Scenario3;
import threeD.trueshot.app.scenarios.Scenario4;
import threeD.trueshot.app.scenarios.Scenario5;
import threeD.trueshot.app.scenarios.Scenario6;
import threeD.trueshot.app.scenarios.Scenario7;
import threeD.trueshot.app.scenarios.Scenario8;
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
                String cipic = "58";
                String scenario =  data.getScenario();
                //TODO: EXTRACT THIS INTO ITS OWN CLASS GODDAMIT 
                switch (scenario)
                {
                    case "Scenario1":
                        //currentScenario = new Scenario1(cipic);
                        break;
                    case "Scenario2":
                        //currentScenario = new Scenario2(cipic);
                        break;
                    case "Scenario3":
                        currentScenario = new Scenario3(cipic);
                    //              currentScenario = new Scenario3();
                        break;
                    case "Scenario4":
                        //currentScenario = new Scenario4(cipic);
                        break;
                    case "Scenario5":
                        currentScenario = new Scenario5(cipic);
                        break;
                    case "Scenario6":
                        currentScenario = new Scenario6(cipic);
                        break;
                    case "Scenario7":
                        currentScenario = new Scenario7(cipic);
                        break;
                    case "Scenario8":
                        //currentScenario = new Scenario8(cipic);
                        break;
                    case "Scenario9":
                        currentScenario = new Scenario9(cipic);
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
                    TrueCoordinates trueCoordinates= new TrueCoordinates(0,0,0);
                    trueCoordinates.azimuth = Double.valueOf(rotation);
                    byte[] audio = currentScenario.buildNextStep(trueCoordinates);
                    
                    //
                    //TestHeader testHeader=  new TestHeader();
                    //byte[] audio = testHeader.testHeader();
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