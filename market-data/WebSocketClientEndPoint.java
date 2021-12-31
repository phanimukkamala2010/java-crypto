package testapp;

import java.net.URI;
import javax.websocket.*;

public class WebsocketClientEndpoint {

        Session userSession = null;
        private MessageHandler messageHandler;

        public WebsocketClientEndpoint(URI endpointURI) {
                try {
                        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                        container.connectToServer(this, endpointURI);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        public void onOpen(Session userSession) {
                System.out.println("opening websocket");
                this.userSession = userSession;
        }

        public void onClose(Session userSession, CloseReason reason) {
                System.out.println("closing websocket");
                this.userSession = null;
        }

        public void onMessage(String message) {
                if (this.messageHandler != null) {
                        this.messageHandler.handleMessage(message);
                }
        }

        public void onMessage(ByteBuffer bytes) {
                System.out.println("Handle byte buffer")
        }

        public void addMessageHandler(MessageHandler msgHandler) {
                this.messageHandler = msgHandler;
        }

        public void sendMessage(String message) {
                this.userSession.getAsyncRemote().sendText(message);
        }

        public static interface MessageHandler {

                public void handleMessage(String message);
        }
}
