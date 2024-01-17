package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.CardService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class CardController extends Controller{
    private final AuthorizationService authorizationService;
    private final CardService cardService;
    public CardController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.cardService = new CardService(new CardRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/cards");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/cards")) {
            if (request.getMethod().equals("GET")) {
                return readAll(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response readAll(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        List<Card> cards = cardService.findAll(user);
        return ok(json(cards));
    }
}
