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
            return switch (request.getMethod()) {
                case "GET" -> readAll(request);
                case "POST" -> create(request);
                default -> status(HttpStatus.METHOD_NOT_ALLOWED);
            };

        } else {
            return switch (request.getMethod()) {
                case "GET" -> read(request);
                case "PUT" -> editUser(request);
                default -> status(HttpStatus.METHOD_NOT_ALLOWED);
            };
        }
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

    public Response read(Request request) {
        User user = userService.find(request);
        if (user == null) {
            return status(HttpStatus.UNAUTHORIZED);
        }
        return ok(json(user));
    }

    public Response editUser(Request request) {
        return ok("not implemented yet");
    }
}
