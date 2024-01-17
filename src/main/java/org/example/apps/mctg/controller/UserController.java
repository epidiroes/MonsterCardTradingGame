package org.example.apps.mctg.controller;

import org.example.apps.mctg.dto.Bio;
import org.example.apps.mctg.repository.StatsRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.StatsService;
import org.example.apps.mctg.service.UserService;
import org.example.apps.mctg.entity.User;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class UserController extends Controller {

    private final UserService userService;
    private final StatsService statsService;
    public UserController() {
        this.userService = new UserService(new UserRepository());
        this.statsService = new StatsService(new StatsRepository());
    }
    @Override
    public boolean supports(String route) {
        return route.startsWith("/users");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/users")) {
            return switch (request.getMethod()) {
                case "GET" -> readAll();
                case "POST" -> create(request);
                default -> status(HttpStatus.METHOD_NOT_ALLOWED);
            };

        } else {
            return switch (request.getMethod()) {
                case "GET" -> read(request);
                case "PUT" -> editUser(request);
                default -> status(HttpStatus.NOT_FOUND);
            };
        }
    }

    public Response readAll() {
        List<User> users = userService.findAll();
        return ok(json(users));
    }

    public Response create(Request request) {
        User user = toObject(request, User.class);
        user = userService.save(user);
        if (user == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        statsService.createStats(user);
        return created(json(user));
    }

    public Response read(Request request) {
        User user = userService.find(request);
        if (user == null) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Unauthorized to read user profile");
        }
        return ok(json(user));
    }

    public Response editUser(Request request) {
        Bio bio = toObject(request, Bio.class);
        User user = userService.edit(request, bio);
        if (user == null) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Unauthorized to edit user profile");
        }
        return ok(json(user));
    }
}
