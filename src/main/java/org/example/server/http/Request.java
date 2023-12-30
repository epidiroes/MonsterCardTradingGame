package org.example.server.http;

public class Request {

    private String method;
    private String route;
    private String host;
    private String contentType;
    private int contentLength;
    private String authorization;
    private String body;

    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod.getMethod();
    }

    public String getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(HttpContentType contentType) {
        this.contentType = contentType.getMimeType();
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
