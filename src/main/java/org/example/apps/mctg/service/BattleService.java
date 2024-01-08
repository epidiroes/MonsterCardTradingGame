package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Battle;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.logic.BattleLogic;
import org.example.apps.mctg.repository.BattleRepository;
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
            Battle finishedBattle = battleLogic.battle(battle.getPlayer1(), user.getId());
            return message(finishedBattle, 2);
        }
    }

    private String awaitBattle(Battle battle) {
        // TODO
        // loop where i continue to check if the battle is still open
        while (battleRepository.isOpen(battle.getId())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return null;
            }
        }
        return message(battleRepository.findBattle(battle.getId()), 1);
    }

    private String message(Battle battle, int player) {
        StringBuilder builder = new StringBuilder();
        int score1 = battle.getPlayer1_score();
        int score2 = battle.getPlayer2_score();
        boolean won = false;
        if (player == 1) {
            won = score1 > score2;
        } else if (player == 2) {
            won = score2 > score1;
        }
        if (won) {
            builder.append("YOU WON, with a score of ");
        } else {
            builder.append("YOU LOST, with a score of ");
        }
        builder.append(battle.getPlayer1_score());
        builder.append(":");
        builder.append(battle.getPlayer2_score());

        return builder.toString();
    }
}
