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

public class ScoreboardController extends Controller {
    private final AuthorizationService authorizationService;
    private final StatsService statsService;
    public ScoreboardController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.statsService = new StatsService(new StatsRepository());
    }
    @Override
    public boolean supports(String route) {
        return route.startsWith("/scoreboard");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/scoreboard")) {
            if (request.getMethod().equals("GET")) {
                return read(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response read(Request request) {
        authorizationService.checkAuthorization(request.getAuthorization());
        return ok(json(statsService.readAll()));
    }
}
