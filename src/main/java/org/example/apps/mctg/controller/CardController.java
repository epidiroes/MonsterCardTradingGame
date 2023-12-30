package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.CardService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class CardController extends Controller{
    private final CardService cardService;
    public CardController() {
        this.cardService = new CardService(new CardRepository(), new UserRepository());
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
        return null;
    }

    private Response readAll(Request request) {
        List<Card> cards = cardService.findAll(request);
        if (cards == null) {
            return status(HttpStatus.UNAUTHORIZED);
        }
        return ok(json(cards));
    }
}
