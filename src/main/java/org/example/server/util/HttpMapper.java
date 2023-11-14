package org.example.server.util;

import org.example.server.http.HttpMethod;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// in zwei Klassen aufspalten um funktionalität aufzuteilen?
// nicht statisch machen, damit man nachher besser testen kann
public class HttpMapper {

    public static Request toRequestObject(String httpRequest) {
        Request request = new Request();

        request.setMethod(getHttpMethod(httpRequest));
        request.setRoute(getRoute(httpRequest));
        request.setHost(getHttpHeader("Host", httpRequest));

        return new Request();
    }

    public static String toResponseString(Response response) {
        // TODO
        return null;
    }

    public static HttpMethod getHttpMethod(String httpRequest) {
        String httpMethod = httpRequest.split(" ")[0];

        return switch (httpMethod) {
            case "GET" -> HttpMethod.GET;
            case "POST" -> HttpMethod.POST;
            case "PUT" -> HttpMethod.PUT;
            case "DELETE" -> HttpMethod.DELETE;
            default -> throw new RuntimeException("No HTTP Method");
        };
    }

    private static String getRoute(String httpRequest) {
        return httpRequest.split(" ")[1];
    }

    private static String getHttpHeader(String header, String httpRequest) {
        Pattern regex = Pattern.compile("^" + header + ":\\s(.+)", Pattern.MULTILINE);
        Matcher matcher = regex.matcher(httpRequest);

        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }
}
