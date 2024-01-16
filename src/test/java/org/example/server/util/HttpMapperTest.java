package org.example.server.util;

import org.example.server.http.HttpContentType;
import org.example.server.http.HttpMethod;
import org.example.server.http.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpMapperTest {

    private final String httpRequest1 = "GET /users HTTP/1.1\n" +
            "Host: localhost:10001\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:120.0) Gecko/20100101 Firefox/120.0\n" +
            "Accept: application/json, text/plain, */*\n" +
            "Accept-Language: en-GB,en;q=0.5\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Content-Type: application/json\n" +
            "Connection: keep-alive\n" +
            "Sec-Fetch-Dest: empty\n" +
            "Sec-Fetch-Mode: cors\n" +
            "Sec-Fetch-Site: same-origin";

    @Test
    public void getCorrectMethod_whenCallingGetHttpMethod() {
        // ARRANGE
        // httpRequests defined above

        // ACT
        HttpMethod method = HttpMapper.getHttpMethod(httpRequest1);

        // ASSERT
        assertEquals(HttpMethod.GET, method);
    }

    @Test
    public void shouldSaveHoseInRequestObject_whenCallingToRequestObject() {
        // ARRANGE
        // httpRequests defined above

        // ACT
        Request request = HttpMapper.toRequestObject(httpRequest1);

        // ASSERT
        assertEquals("localhost:10001", request.getHost());
    }

    @Test
    public void shouldSaveRouteInRequestObject_whenCallingToRequestObject() {
        // ARRANGE
        // httpRequests defined above

        // ACT
        Request request = HttpMapper.toRequestObject(httpRequest1);

        // ASSERT
        assertEquals("/users", request.getRoute());
    }

    @Test
    public void shouldSaveContentTypeInRequestObject_whenCallingToRequestObject() {
        // ARRANGE
        // httpRequests defined above

        // ACT
        Request request = HttpMapper.toRequestObject(httpRequest1);

        // ASSERT
        assertEquals(HttpContentType.APPLICATION_JSON.getMimeType(), request.getContentType());
    }
}