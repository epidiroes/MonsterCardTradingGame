package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Trade;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.TradingsRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.CardService;
import org.example.apps.mctg.service.TradingsService;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class TradingsController extends Controller {
    private final AuthorizationService authorizationService;
    private final TradingsService tradingsService;
    private final CardService cardService;
    public TradingsController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.tradingsService = new TradingsService(new TradingsRepository());
        this.cardService = new CardService(new CardRepository());
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
        authorizationService.checkAuthorization(request.getAuthorization());
        return ok(json(tradingsService.readAll()));
    }

    private Response create(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        Trade trade = toObject(request, Trade.class);
        trade.setUserId(user.getId());
        return created(json(tradingsService.create(trade)));
    }

    private Response trade(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        String id = request.getRoute().substring(request.getRoute().lastIndexOf('/') + 1);
        Trade trade = tradingsService.find(id);
        if (user.getId().equals(trade.getUserId())) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "trying to trade with yourself");
        }
        String cardId = toObject(request, String.class);
        Card card = cardService.findById(cardId);
        if (
                trade.getType().equals("monster") && card.isSpell() ||
                trade.getType().equals("spell") && !card.isSpell() ||
                trade.getMinimumDamage() > card.getDamage()
        ) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "offered card doesn't fulfill trading requirements");
        }
        cardService.updateUser(card, user);
        tradingsService.delete(id);
        return ok(json("Trade successful"));
    }

    private Response delete(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        String id = request.getRoute().substring(request.getRoute().lastIndexOf('/') + 1);
        Trade trade = tradingsService.find(id);
        if (!user.getId().equals(trade.getUserId())) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "trade doesn't belong to user trying to delete it");
        }
        tradingsService.delete(id);
        return ok(json(trade));
    }
}
