package org.example.server.http;

public enum ContentType {
    APPLICATION_JSON("application/json"),

    TEXT_PLAIN("text/plain"),

    TEXT_HTML("text/html");

    private final String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
