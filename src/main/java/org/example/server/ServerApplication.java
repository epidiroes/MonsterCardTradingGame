package org.example.server;

import org.example.server.http.Request;
import org.example.server.http.Response;

public interface ServerApplication {
    Response handle(Request request);
}
