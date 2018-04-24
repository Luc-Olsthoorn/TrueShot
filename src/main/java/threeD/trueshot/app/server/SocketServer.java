package threeD.trueshot.app.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import threeD.trueshot.app.scenarios.*;

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

                currentScenario = ScenarioBuilder.build(cipic, scenario);
                System.out.println(scenario);
            }
        });
    }
    public void addCoordinateListener(){
        server.addEventListener("updateCoordinates", CoordinateObject.class, new DataListener<CoordinateObject>() {
            @Override
            public void onData(SocketIOClient client, CoordinateObject data, AckRequest ackRequest) {
                long start = System.currentTimeMillis();
                System.out.println("Data: ");
                try{
                    String rotation = data.getRotation();
                    System.out.println(rotation);
                    System.out.println("Recieved new x,y,z, processing...");
                    TrueCoordinates trueCoordinates= new TrueCoordinates(0,0,0);
                    double value = 0;
                    try
                    {
                        value = Double.valueOf(rotation);
                        if(value > 0
                                && value <= 180)
                        {
                            value *= -1;
                        }
                        else
                        {
                            value = 360 - value;
                        }
                        trueCoordinates.azimuth = value;
                    }
                    catch (Exception e)
                    {
                        trueCoordinates.azimuth = 0;
                    }

                    System.out.println(value);
                    System.out.println("Corrected vlaue: " + trueCoordinates.getAdjustedAzimuth() + " corrected elevation: " + trueCoordinates.getAdjustedElevation());
                    byte[] audio = currentScenario.buildNextStep(trueCoordinates);
                    
                    //TestHeader testHeader=  new TestHeader();
                    //byte[] audio = testHeader.testHeader();
                    System.out.println("Time to convolve" +( System.currentTimeMillis() - start));
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