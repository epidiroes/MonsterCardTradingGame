package org.example.apps.mctg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.example.server.http.HttpContentType;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy());
        T object;
        try {
            object = objectMapper.readValue(request.getBody(), targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
    protected <T> List<T> toObjects(Request request, Class<T> targetClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy());
        String body = request.getBody().substring(1, request.getBody().length() - 1);

        String[] parts = body.split("\\},\\s*\\{");
        for (int i = 0; i < parts.length; i++) {
            if (i != 0) {
                parts[i] = "{" + parts[i];
            }
            if (i != parts.length - 1) {
                parts[i] = parts[i] + "}";
            }
        }

        List<T> objects = new ArrayList<>();
        for (String part : parts) {
            try {
                T object = objectMapper.readValue(part, targetClass);
                objects.add(object);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return objects;
    }

    protected List<String> toList(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(body, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
