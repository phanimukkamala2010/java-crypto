package testapp;

import java.net.URI;
import java.nio.*;
import javax.json.*;
import javax.websocket.*;

@ClientEndpoint
public class WebsocketClientEndpoint extends Thread implements MessageHandler {

        Session userSession = null;
        private MessageHandler messageHandler;
        private boolean bExit = false;

        public WebsocketClientEndpoint(URI endpointURI) {
                try {
                        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                        System.out.println("endpointURI=" + endpointURI);
                        container.connectToServer(this, endpointURI);
                } 
                catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
        }

        public void handleMessage(String message) {
                System.out.println("*** " + message);
        }

        public void run() {
                messageHandler = this;
                while(!bExit) {
                        try {
                                sleep(1000);
                        }
                        catch(Exception e) {}
                }
        }

        @OnOpen
        public void onOpen(Session userSession) {
                System.out.println("opening websocket");
                this.userSession = userSession;
            
                SendSub("trades", "BTC-PERP");
                SendSub("trades", "ETH-PERP");

                SendSub("trades", "BTC/USD");
                SendSub("trades", "ETH/USD");
        }

        private void SendSub(String channel, String symbol) {
                JsonObject obj = Json.createObjectBuilder()
                        .add("op", "subscribe")
                        .add("channel", channel)
                        .add("market", symbol)
                        .build();
                sendMessage(obj.toString());
        }

        @OnClose
        public void onClose(Session userSession, CloseReason reason) {
                System.out.println("closing websocket");
                this.userSession = null;
        }

        @OnMessage
        public void onMessage(String message) {
                if (this.messageHandler != null) {
                        this.messageHandler.handleMessage(message);
                }
        }

        @OnMessage
        public void onMessage(ByteBuffer bytes) {
                System.out.println("Handle byte buffer");
        }

        public void addMessageHandler(MessageHandler msgHandler) {
                this.messageHandler = msgHandler;
        }

        public void sendMessage(String message) {
                this.userSession.getAsyncRemote().sendText(message);
        }

}
