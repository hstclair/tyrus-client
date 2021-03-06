package com.dcc.scratch;


import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    private static CountDownLatch messageLatch;
    private static final String SENT_MESSAGE = "Hello World";

    public static void main(String [] args){
        try {
            messageLatch = new CountDownLatch(1);

            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();

            ClientManager client = ClientManager.createClient();
            client.connectToServer(new ClientEndpoint(), cec, new URI("ws://localhost:8025/websockets/permutifier"));

            messageLatch.await(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



/*
new Endpoint() {

                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    try {
                        session.addMessageHandler(new MessageHandler.Whole<String>() {

                            @Override
                            public void onMessage(String message) {
                                System.out.println("Received message: "+message);

                                try {
                                    session.getBasicRemote().sendText("Hello again, world!");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
//                                messageLatch.countDown();
                            }
                        });
                        session.getBasicRemote().sendText(SENT_MESSAGE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, cec, new URI("ws://localhost:8025/websockets/echo"));
 */

