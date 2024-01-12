package org.example.apps.mctg.controller;

import org.example.apps.mctg.logic.BattleLogic;
import org.example.apps.mctg.repository.*;
import org.example.apps.mctg.service.BattleService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class BattleController extends Controller {
    private final BattleService battleService;
    public BattleController() {
        this.battleService = new BattleService(new UserRepository(), new BattleRepository(), new BattleLogic(new DeckRepository(), new CardRepository()), new StatsRepository());
    }
    @Override
    public boolean supports(String route) {
        return route.startsWith("/battles");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/battles")) {
            if (request.getMethod().equals("POST")) {
                return battle(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response battle(Request request) {
        String log = battleService.battle(request);
        if (log == null) {
            return status(HttpStatus.BAD_REQUEST);
        }
        return ok(log);
    }
}
