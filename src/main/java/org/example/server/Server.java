package org.example.server;
public class Server {
    private static ServerApplication app;
    private Object server;

    public Server(ServerApplication app) {
        this.app = app;
    }

    public static void Server(ServerApplication app) {}
    public static void start() {
        // start server

        Object client = null;

        RequestHandler requestHandler = new RequestHandler(client, app);
    }
}
