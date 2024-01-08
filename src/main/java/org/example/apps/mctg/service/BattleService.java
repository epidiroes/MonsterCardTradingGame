package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Battle;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.logic.BattleLogic;
import org.example.apps.mctg.repository.BattleRepository;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.Request;

import java.util.Objects;
import java.util.Optional;

public class BattleService {
    private final UserRepository userRepository;
    private final BattleRepository battleRepository;
    private final BattleLogic battleLogic;
    public BattleService(UserRepository userRepository, BattleRepository battleRepository, BattleLogic battleLogic) {
        this.userRepository = userRepository;
        this.battleRepository = battleRepository;
        this.battleLogic = battleLogic;
    }
    public String battle(Request request) {
        // Authentication
        String authorization = request.getAuthorization();
        if (Objects.equals(authorization, "")) {
            return null;
        }
        String name = authorization.substring(authorization.indexOf(" ") + 1, authorization.indexOf("-", authorization.indexOf(" ") + 1));
        Optional<User> userOptional = userRepository.find(name);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();

        // Find open battle or open new battle
        Optional<Battle> openBattle = battleRepository.findOpenBattle();
        if (openBattle.isEmpty()) {
            Battle battle = battleRepository.openBattle(user);
            return awaitBattle(battle);
        } else {
            Battle battle = openBattle.get();
            return battleLogic.battle(battle.getPlayer1(), user.getId());
        }
    }

    private String awaitBattle(Battle battle) {
        // TODO
        return null;
    }
}
