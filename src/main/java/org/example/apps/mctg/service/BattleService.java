package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Battle;
import org.example.apps.mctg.entity.Stat;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.logic.BattleLogic;
import org.example.apps.mctg.repository.BattleRepository;
import org.example.apps.mctg.repository.StatsRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;

import java.util.Objects;
import java.util.Optional;

public class BattleService {
    private final UserRepository userRepository;
    private final BattleRepository battleRepository;
    private final BattleLogic battleLogic;
    private final StatsRepository statsRepository;
    public BattleService(UserRepository userRepository, BattleRepository battleRepository, BattleLogic battleLogic, StatsRepository statsRepository) {
        this.userRepository = userRepository;
        this.battleRepository = battleRepository;
        this.battleLogic = battleLogic;
        this.statsRepository = statsRepository;
    }
    public synchronized String battle(Request request) {
        // Authentication
        String authorization = request.getAuthorization();
        if (Objects.equals(authorization, "")) {
            System.err.print("no authorization");
            return null;
        }
        String name = authorization.substring(authorization.indexOf(" ") + 1, authorization.indexOf("-", authorization.indexOf(" ") + 1));
        Optional<User> userOptional = userRepository.find(name);
        if (userOptional.isEmpty()) {
            System.err.print("user not found");
            return null;
        }
        User user = userOptional.get();

        // Find open battle or open new battle
        Optional<Battle> openBattle = battleRepository.findOpenBattle();
        if (openBattle.isEmpty()) {
            Battle battle = battleRepository.openBattle(user);
            try {
                wait();
            } catch (InterruptedException e) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Battle got interrupted");
            }
            Battle foundBattle = battleRepository.findBattle(battle.getId());
            updateStats(battle, user);
            return foundBattle.getLog();
        } else {
            Battle battle = openBattle.get();
            User opponent = userRepository.findById(battle.getPlayer1());
            Battle finishedBattle = battleLogic.battle(battle, opponent, user);
            battleRepository.update(finishedBattle);
            updateStats(finishedBattle, user);
            notify();
            return finishedBattle.getLog();
        }
    }

    private void updateStats(Battle battle, User user) {
        Stat stat = statsRepository.find(user);
        stat.setGames_played(stat.getGames_played() + 1);

        if (battle.getWinner() != null) {
            if (battle.getWinner().equals(user.getId())) {
                stat.setElo(stat.getElo() + 3);
                stat.setGames_won(stat.getGames_won() + 1);
            } else {
                stat.setElo(stat.getElo() - 5);
            }
        }
        statsRepository.update(stat);
    }
}
