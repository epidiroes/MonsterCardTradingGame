package org.example.server.http;

public class Request {

    // GET, POST, PUT, DELETE
    private HttpMethod method;
    // /, /home, /package
    private String route;

    // application/json, text/plain
    private ContentType contentType;
    private int contentLength;
    private String body;
}
