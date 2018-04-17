package com.corundumstudio.socketio.demo;

import java.io.UnsupportedEncodingException;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.listener.DataListener;


import java.io.IOException;

public class SocketServer {
    private SocketIOServer server;
    private int port;
    SocketServer(int inputPort){
        port = inputPort;
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
    }
    
    public void addCoordinateListener(final SoundAnalyzer soundAnalyzer){
        server.addEventListener("updateCoordinates", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
                byte[] audio = soundAnalyzer.newCoordinates();
                client.sendEvent("sound", audio);
            }
        });
    }
    public void run(){
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