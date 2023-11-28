package org.example.apps.mctg.controller;

import org.example.apps.mctg.service.UserService;
import org.example.apps.mctg.entity.User;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController extends Controller {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/users");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/tasks")) {
            switch (request.getMethod()) {
                case "GET": return readAll(request);
                case "POST": return create(request);
            }

            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }


        return null;
    }

    public Response readAll(Request request) {
        return null;
    }

    public Response create(Request request) {
        User user = toObject(request.getBody(), User.class);
        //user = UserService.save(user);
        return json(user);
    }
}
