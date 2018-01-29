package com.dcc.scratch;

import javax.websocket.CloseReason;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;

public class ReplyHandler implements MessageHandler.Whole<String> {

    Session session;

    RemoteEndpoint.Basic remoteEndpoint;

    public ReplyHandler(Session session) {
        this.session = session;
        remoteEndpoint = session.getBasicRemote();
    }

    @Override
    public void onMessage(String message) {

//        System.out.println("processing reply "+message);

        String request = null;

        boolean disconnect = false;
        String response = null;
        String payload = null;
        String[] components = message.split("\n", 2);

        if (components.length > 0)
            response = components[0];

        if (components.length > 1)
            payload = components[1];

        switch (response) {
            case "HELLO":

                System.out.println("handshake complete - sending request");

                request = "PERMUTE\nhere is yoda now";

                break;

            case "PERMUTE":

                System.out.println("received response:");

                System.out.println(payload);

                System.out.println("request complete - sending close session request");

                request = "BYE";

                break;

            case "BYE":

                System.out.println("session close acknowledged - disconnecting");

                disconnect = true;

                break;

            default:
                request = "BYE";
        }

        try {
            if (request != null) {

                System.out.println("sending " + request);

                remoteEndpoint.sendText(request);
            }

            if (disconnect) {

                System.out.println("closing session");

                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Done"));
            }

        } catch (IOException ex) {
            System.out.println("wha???");

            ex.printStackTrace();
        }
    }
}
