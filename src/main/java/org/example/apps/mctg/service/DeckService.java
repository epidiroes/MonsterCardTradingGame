package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Deck;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeckService {
    private final UserRepository userRepository;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    public DeckService(UserRepository userRepository, DeckRepository deckRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    public List<Card> findAll(User user) {
        return deckRepository.findAll(user);
    }

    public Deck configDeck(User user, List<String> cardId) {
        if (cardId.size() != 4) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Deck size must be 4");
        }

        List<Card> cards = new ArrayList<>();
        for(String id : cardId) {
            Optional<Card> cardOptional = cardRepository.findById(id);
            if (cardOptional.isEmpty()) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "CardId not found");
            }
            Card card = cardOptional.get();
            cards.add(card);
        }

        // create deck if it isn't set yet, or update if the cards differ
        List<Card> deck = this.findAll(user);
        if (deck.isEmpty()) {
            return deckRepository.create(user, cards);
        } else {
            for(Card card : deck) {
                if(!cardId.contains(card.getId())) {
                    return deckRepository.update(user, cards);
                }
            }
            return deckRepository.find(user);
        }

    }
}
