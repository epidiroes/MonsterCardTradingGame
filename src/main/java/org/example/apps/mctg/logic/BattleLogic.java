package org.example.apps.mctg.logic;

import org.example.apps.mctg.entity.Battle;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.enums.CreatureType;
import org.example.apps.mctg.enums.ElementType;
import org.example.apps.mctg.enums.Status;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;

import java.util.List;
import java.util.Random;

public class BattleLogic {
    private int maxRounds = 100;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    public BattleLogic(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
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
        String name1 = user1.getName();
        String name2 = user2.getName();

        log.append("\nThe battle between ").append(name1).append(" and ");
        log.append(name2).append(" begins ...\n");

        for (int round = 0; round < maxRounds; round++) {
            log.append("Round ").append(round).append(": ");
            Card card1 = deck1.get(rand.nextInt(deck1.size()));
            Card card2 = deck2.get(rand.nextInt(deck2.size()));
            String card1Name = card1.getName();
            String card2Name = card2.getName();

            log.append(card1Name).append(" (").append(card1.getDamage()).append(")").
                    append(" vs ").append(card2Name).append(" (").append(card2.getDamage()).append(")");
            Status result = fight(card1, card2);
            Card card = null;
            if (result.equals(Status.CARD1)) {
                card = card2;
                log.append(" -> ").append(card1Name).append(" wins! ");
                log.append(card2Name).append(" goes to ").append(name1).append("\n");
            } else if (result.equals(Status.CARD2)) {
                card = card1;
                log.append(" -> ").append(card2Name).append(" wins! ");
                log.append(card1Name).append(" goes to ").append(name2).append("\n");
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

        // reset both decks to be empty then change card owners
        deckRepository.reset(user1);
        deckRepository.reset(user2);

        for (Card card : deck1) {
            cardRepository.updateUserId(card, user1);
        }
        for (Card card : deck2) {
            cardRepository.updateUserId(card, user2);
        }

        battle.setLog(log.toString());
        battle.setOpen(false);
        return battle;
    }

    private Status fight(Card card1, Card card2) {

        if (card1.isSpell() || card2.isSpell()) {

            if (card1.creatureType().equals(CreatureType.KRAKEN)) {
                return Status.CARD1;

            } else if (card2.creatureType().equals(CreatureType.KRAKEN)) {
                return Status.CARD2;
            }
            if (card1.hasElementType() && card2.hasElementType()) {
                if (card1.getDamage() * card1.getDamageMultiplier(card2) > card2.getDamage() * card2.getDamageMultiplier(card1)) {
                    return Status.CARD1;

                } else if (card2.getDamage() * card2.getDamageMultiplier(card1) > card1.getDamage() * card1.getDamageMultiplier(card2)) {
                    return Status.CARD2;
                }
            } else if (card1.creatureType().equals(CreatureType.KNIGHT) && card2.elementType().equals(ElementType.WATER)) {
                return Status.CARD2;

            } else if (card2.creatureType().equals(CreatureType.KNIGHT) && card1.elementType().equals(ElementType.WATER)) {
                return Status.CARD1;
            }
        }

        if (
                card1.creatureType().equals(CreatureType.GOBLIN) && card2.creatureType().equals(CreatureType.DRAGON) ||
                card1.creatureType().equals(CreatureType.ORK) && card2.creatureType().equals(CreatureType.WIZARD) ||
                card1.creatureType().equals(CreatureType.DRAGON) && card2.creatureType().equals(CreatureType.ELF) && card2.elementType().equals(ElementType.FIRE)
        ) {
            return Status.CARD2;
        } else if (
                card2.creatureType().equals(CreatureType.GOBLIN) && card1.creatureType().equals(CreatureType.DRAGON) ||
                card2.creatureType().equals(CreatureType.ORK) && card1.creatureType().equals(CreatureType.WIZARD) ||
                card2.creatureType().equals(CreatureType.DRAGON) && card1.creatureType().equals(CreatureType.ELF) && card1.elementType().equals(ElementType.FIRE)
        ) {
            return Status.CARD1;

        } else if (card1.getDamage() > card2.getDamage()) {
            return Status.CARD1;

        } else if (card2.getDamage() > card1.getDamage()) {
            return Status.CARD2;
        }

        return Status.DRAW;
    }
}
