package org.example.apps.mctg.controller;

import org.example.apps.mctg.logic.BattleLogic;
import org.example.apps.mctg.repository.BattleRepository;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.BattleService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class BattleController extends Controller {
    private final BattleService battleService;
    public BattleController() {
        this.battleService = new BattleService(new UserRepository(), new BattleRepository(), new BattleLogic());
    }
    @Override
    public boolean supports(String route) {
        return route.startsWith("/battles");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/cards")) {
            if (request.getMethod().equals("GET")) {
                return battle(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return null;
    }

    private Response battle(Request request) {
        return ok(battleService.battle(request));
    }
}
