package org.example.server.util;

import org.example.server.http.Request;
import org.example.server.http.Response;

public class HttpMapper {

    public static Request toRequestObject(String request) {
        // TODO
        // (A-Z)+\s/\S+)\sHTTP
        // Host:\s(.)+
        //System.out.println(request.split(" ")[0]);


        return new Request();
    }

    public static String toResponseString(Response response) {
        // TODO
        return null;
    }
}
