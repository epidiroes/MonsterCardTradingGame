package org.example.apps.mctg.controller;

import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.TradingsService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class TradingsController extends Controller {
    private final AuthorizationService authorizationService;
    private final TradingsService tradingsService;
    public TradingsController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.tradingsService = new TradingsService();
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/tradings");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/tradings")) {
            return switch (request.getMethod()) {
                case "GET" -> readAll(request);
                case "POST" -> create(request);
                default -> status(HttpStatus.METHOD_NOT_ALLOWED);
            };

        } else {
            return switch (request.getMethod()) {
                case "POST" -> trade(request);
                case "DELETE" -> delete(request);
                default -> status(HttpStatus.NOT_FOUND);
            };
        }
    }

    private Response readAll(Request request) {
        return ok("TODO");
    }

    private Response create(Request request) {
        return ok("TODO");
    }

    private Response trade(Request request) {
        return ok("TODO");
    }

    private Response delete(Request request) {
        return ok("TODO");
    }
}
