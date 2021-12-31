package testapp;

import java.net.*;

public class TestApp {

    public static void main(String[] args) {
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://ftx.com/ws"));

            clientEndPoint.start();
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
