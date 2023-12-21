package org.example.apps.mctg.controller;

import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.UserService;
import org.example.apps.mctg.entity.User;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class UserController extends Controller {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService(new UserRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/users");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/users")) {
            switch (request.getMethod()) {
                case "GET": return readAll(request);
                case "POST": return create(request);
            }

            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }


        return null;
    }

    public Response readAll(Request request) {
        List<User> users = userService.findAll();
        return ok(json(users));
    }

    public Response create(Request request) {
        User user = toObject(request, User.class);
        user = userService.save(user);
        if (user == null) {
            return statusMessage(HttpStatus.BAD_REQUEST, "User already exists");
        }
        return created(json(user));
    }
}
