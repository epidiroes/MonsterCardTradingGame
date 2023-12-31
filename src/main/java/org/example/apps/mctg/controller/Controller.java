package org.example.apps.mctg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.http.HttpContentType;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;
import org.example.apps.mctg.entity.User;

public abstract class Controller {

    public abstract boolean supports(String route);

    public abstract Response handle(Request request);

    protected Response status(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody("{ \"error\": \""+ httpStatus.getMessage() + "\"}");

        return response;
    }

    protected Response statusMessage(HttpStatus httpStatus, String body) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody("{ \"error\": \""+ httpStatus.getMessage() + "\", \"message\" \"" + body + "\"}");

        return response;
    }

    protected Response created(String body) {
        Response response = new Response();
        response.setStatus(HttpStatus.CREATED);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(body);

        return response;
    }

    protected Response ok(String body) {
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(body);

        return response;
    }

    protected <T> T toObject(Request request, Class<T> targetClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        T object;
        try {
            object = objectMapper.readValue(request.getBody(), targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    protected <T> String json(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String objectJson = null;
        try {
            objectJson = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return objectJson;
    }

}
