package org.example.server;

import org.example.server.ServerApplication;
import org.example.server.http.HttpContentType;
import org.example.server.http.HttpException;
import org.example.server.http.Request;
import org.example.server.http.Response;
import org.example.server.util.HttpMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler implements Runnable {

    private BufferedReader in;
    private PrintWriter out;

    private final Socket client;

    private final ServerApplication app;

    public RequestHandler(Socket client, ServerApplication app) {
        this.client = client;
        this.app = app;
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handle() throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String httpRequest = getHttpStringFromStream(in);

        Request request = HttpMapper.toRequestObject(httpRequest);
        Response response = new Response();
        try {
            response = app.handle(request);
        } catch (HttpException e) {
            response.setStatus(e.getStatus());
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody(e.getMessage());
        }

        out = new PrintWriter(client.getOutputStream(), true);
        out.write(HttpMapper.toResponseString(response));

        out.close();
        in.close();
        client.close();
    }

    // THOUGHT: create a SocketReader class
    private String getHttpStringFromStream(BufferedReader in) throws IOException {
        StringBuilder builder = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null && !inputLine.equals("")) {
            builder
                    .append(inputLine)
                    .append(System.lineSeparator());
        }

        String httpRequest = builder.toString();

        Pattern regex = Pattern.compile("^Content-Length:\\s(.+)", Pattern.MULTILINE);
        Matcher matcher = regex.matcher(httpRequest);

        if (!matcher.find()) {
            return builder.toString();
        }

        builder.append(System.lineSeparator());

        int contentLength = Integer.parseInt(matcher.group(1));
        char[] buffer = new char[contentLength];
        in.read(buffer, 0, contentLength);
        builder.append(buffer);

        return builder.toString();
    }
}