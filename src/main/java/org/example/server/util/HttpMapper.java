package org.example.server.util;

import org.example.server.http.HttpContentType;
import org.example.server.http.HttpMethod;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.Objects;
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

        String contentType = getHttpHeader("Content-Type", httpRequest);
        request.setContentType(getHttpContentType(Objects.requireNonNullElse(contentType, "text/plain")));

        String authorization = getHttpHeader("Authorization", httpRequest);
        request.setAuthorization(Objects.requireNonNullElse(authorization, ""));

        // THOUGHT: don't do the content parsing in this method
        String contentLengthHeader = getHttpHeader("Content-Length", httpRequest);
        if (null == contentLengthHeader) {
            return request;
        }
        int contentLength = Integer.parseInt(contentLengthHeader);
        request.setContentLength(contentLength);

        if (0 == contentLength) {
            return request;
        }
        request.setBody(httpRequest.substring(httpRequest.length() - contentLength));

        return request;
    }

    public static String toResponseString(Response response) {

        return "HTTP/1.1 " + response.getStatusCode() + " " + response.getStatusMessage() + "\r\n" +
                "Content-Type: " + response.getContentType() + "\r\n" +
                "Content-Length: " + response.getBody().length() + "\r\n" +
                "\r\n" +
                response.getBody();
    }

    // THOUGHT: Maybe some better place for this logic?
    public static HttpMethod getHttpMethod(String httpRequest) {
        String httpMethod = httpRequest.split(" ")[0];

        // THOUGHT: Use constants instead of hardcoded strings
        return switch (httpMethod) {
            case "GET" -> HttpMethod.GET;
            case "POST" -> HttpMethod.POST;
            case "PUT" -> HttpMethod.PUT;
            case "DELETE" -> HttpMethod.DELETE;
            default -> throw new RuntimeException("No HTTP Method");
        };
    }

    private static HttpContentType getHttpContentType(String contentType) {
        return switch (contentType) {
            case "application/json" -> HttpContentType.APPLICATION_JSON;
            case "text/html" -> HttpContentType.TEXT_HTML;
            case "text/plain" -> HttpContentType.TEXT_PLAIN;
            default -> throw new RuntimeException("contentType not supported");
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
