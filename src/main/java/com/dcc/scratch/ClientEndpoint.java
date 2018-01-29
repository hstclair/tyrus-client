package com.dcc.scratch;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;

public class ClientEndpoint extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        try {

            System.out.println("Connected");

            session.addMessageHandler(new ReplyHandler(session));

            System.out.println("Sending HELLO");

            session.getBasicRemote().sendText("HELLO");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


