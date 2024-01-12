package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.StatsRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.StatsService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.Optional;

public class StatsController extends Controller {
    private final StatsService statsService;
    private final AuthorizationService authorizationService;
    public StatsController() {
        this.statsService = new StatsService(new StatsRepository());
        this.authorizationService = new AuthorizationService(new UserRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/stats");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/stats")) {
            if (request.getMethod().equals("GET")) {
                return read(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response read(Request request) {
        Optional<User> optionalUser = authorizationService.authorizedUser(request.getAuthorization());
        if (optionalUser.isEmpty()) {
            return status(HttpStatus.UNAUTHORIZED);
        }
        User user = optionalUser.get();
        return ok(json(statsService.read(user)));
    }
}
