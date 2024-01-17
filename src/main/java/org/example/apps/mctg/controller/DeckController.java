package org.example.apps.mctg.controller;

import com.sun.jdi.request.DuplicateRequestException;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Deck;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.DeckService;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class DeckController extends Controller {
    private final AuthorizationService authorizationService;
    private final DeckService deckService;
    public  DeckController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.deckService = new DeckService(new UserRepository(), new DeckRepository(), new CardRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/deck");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/deck")) {
            return switch (request.getMethod()) {
                case "GET" -> read(request);
                case "PUT" -> config(request);
                default -> status(HttpStatus.METHOD_NOT_ALLOWED);
            };
        }

        if (request.getRoute().equals("/deck?format=plain")) {
            if (request.getMethod().equals("GET")) {
                return readPlain(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }

        return status(HttpStatus.NOT_FOUND);
    }

    private Response read(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        List<Card> cards = deckService.findAll(user);
        if (cards == null) {
            return ok("There are no cards in the deck");
        }
        return ok(json(cards));
    }

    private Response readPlain(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        List<Card> cards = deckService.findAll(user);
        if (cards == null) {
            return ok("There are no cards in the deck");
        }
        StringBuilder message = new StringBuilder(500);
        for (Card card : cards) {
            message.append(card.toString());
        }
        return ok(message.toString());
    }

    private Response config(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        List<String> cardIdList = toList(request.getBody());
        Deck deck = deckService.configDeck(user, cardIdList);
        if (deck == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "This deck already exists");
        }
        return created(json(deck));
    }
}
