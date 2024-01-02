package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Deck;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
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

    public List<Card> findAll(Request request) {
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

        return deckRepository.findAll(user);
    }

    public Deck configDeck(Request request, List<String> cardId) {
        if (cardId.size() != 4) {
            return null;
        }
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

        List<Card> cards = new ArrayList<>();
        for(String id : cardId) {
            Optional<Card> cardOptional = cardRepository.findById(id);
            if (cardOptional.isEmpty()) {
                return null;
            }
            Card card = cardOptional.get();
            cards.add(card);
        }

        // create deck if it isn't set yet, or update if the cards differ
        List<Card> deck = this.findAll(request);
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
