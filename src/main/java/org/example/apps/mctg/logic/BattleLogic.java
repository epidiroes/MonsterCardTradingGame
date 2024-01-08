package org.example.apps.mctg.logic;

import org.example.apps.mctg.entity.Battle;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.enums.Status;
import org.example.apps.mctg.repository.DeckRepository;

import java.util.List;
import java.util.Random;

public class BattleLogic {
    private int maxRounds = 100;
    private final DeckRepository deckRepository;
    public BattleLogic(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }
    public Battle battle(Battle battle, User user1, User user2) {
        battle.setPlayer1(user1.getId());
        battle.setPlayer2(user2.getId());

        // Get decks and check if they each contain 4 cards
        List<Card> deck1 = deckRepository.findAll(user1);
        List<Card> deck2 = deckRepository.findAll(user2);
        if (deck1.size() != 4 || deck2.size() != 4) {
            battle.setLog("One or both of the decks are not full. Battle stopped.");
            battle.setOpen(false);
            return battle;
        }

        StringBuilder log = new StringBuilder();
        Random rand = new Random();

        log.append("The battle between ").append(user1.getName()).append(" and ");
        log.append(user2.getName()).append(" begins ...\n");
        for (int round = 0; round < maxRounds; round++) {
            log.append("\nRound ").append(round).append(": ");
            Card card1 = deck1.get(rand.nextInt(deck1.size()));
            Card card2 = deck2.get(rand.nextInt(deck2.size()));

            log.append(card1.getName()).append(" vs ").append(card2.getName());
            Status result = fight(card1, card2);
            Card card = null;
            if (result.equals(Status.CARD1)) {
                card = card2;
                log.append(" -> ").append(card1.getName()).append(" wins! ");
                log.append(card2.getName()).append(" goes to ").append(user1.getName()).append("\n");
            } else if (result.equals(Status.CARD2)) {
                card = card1;
                log.append(" -> ").append(card2.getName()).append(" wins! ");
                log.append(card1.getName()).append(" goes to ").append(user2.getName()).append("\n");
            }
            if (!result.equals(Status.DRAW)) {
                if (deck1.contains(card)) {
                    deck1.remove(card);
                    deck2.add(card);
                } else {
                    deck2.remove(card);
                    deck1.add(card);
                }

                if (deck1.isEmpty()) {
                    battle.setWinner(user2.getId());
                    round = maxRounds;
                } else if (deck2.isEmpty()) {
                    battle.setWinner(user1.getId());
                    round = maxRounds;
                }
            } else {
                log.append(" -> Draw! No card won.\n");
            }
        }


        battle.setLog(log.toString());
        battle.setOpen(false);
        return battle;
    }

    private Status fight(Card card1, Card card2) {
        return Status.DRAW;
    }
}
