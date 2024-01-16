package org.example;

import org.example.apps.mctg.MctgApp;
import org.example.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new MctgApp());
        server.start();
    }
}