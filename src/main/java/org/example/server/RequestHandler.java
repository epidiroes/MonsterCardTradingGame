package org.example.server;

import org.example.server.http.Request;
import org.example.server.http.Response;
import org.example.server.util.HttpMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {

    private BufferedReader in;
    private PrintWriter out;

    private final Socket client;

    private final ServerApplication app;

    public RequestHandler(Socket client, ServerApplication app) {
        this.client = client;
        this.app = app;
    }

    public void handle() throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        System.out.println(getHttpStringFromStream(in));

        out = new PrintWriter(client.getOutputStream(), true);
        out.write(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 5\r\n" +
                        "\r\n" +
                        "Hallo");

        out.close();
        in.close();
        client.close();
    }

    // socket reader selber implementieren zb
    private String getHttpStringFromStream(BufferedReader in) throws IOException {
        StringBuilder builder = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null && !inputLine.equals("")) {
            builder
                    .append(inputLine)
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }
}
