package org.example.server;

import java.net.Socket;

public class RequestHandler {

    private Object client;
    private ServerApplication app;
    public RequestHandler(Object client, ServerApplication app) {
        this.client = client;
        this.app = app;
    }

    public void handle() {

    }
}
