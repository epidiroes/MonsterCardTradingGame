package org.example.apps.mctg.controller;

import com.sun.jdi.request.DuplicateRequestException;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.DeckService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class DeckController extends Controller {
    private final DeckService deckService;
    public  DeckController() {
        this.deckService = new DeckService(new UserRepository(), new DeckRepository());
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

        return null;
    }

    private Response read(Request request) {
        List<Card> cards = deckService.findAll(request);
        if (cards.isEmpty()) {
            return ok("There are no cards in the deck");
        }
        return ok(json(cards));
    }

    private Response readPlain(Request request) {
        List<Card> cards = deckService.findAll(request);
        if (cards.isEmpty()) {
            return ok("There are no cards in the deck");
        }
        return ok(json(cards));
    }

    private Response config(Request request) {
        return ok("ok");
    }
}
