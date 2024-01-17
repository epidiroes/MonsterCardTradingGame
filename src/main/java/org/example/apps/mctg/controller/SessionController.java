package org.example.apps.mctg.controller;

import org.example.apps.mctg.dto.Token;
import org.example.apps.mctg.dto.TokenRequest;
import org.example.apps.mctg.service.SessionService;
import org.example.server.http.HttpContentType;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class SessionController extends Controller {
    private final SessionService sessionService;

    public SessionController() {
        this.sessionService = new SessionService();
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/sessions");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/sessions")) {
            switch (request.getMethod()) {
                case "POST": return getToken(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response getToken(Request request) {
        TokenRequest tokenRequest = toObject(request, TokenRequest.class);
        Token token = sessionService.getToken(tokenRequest);
        return ok(json(token.getToken()));
    }



}
