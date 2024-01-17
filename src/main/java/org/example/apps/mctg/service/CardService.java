package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CardService {
    private final CardRepository cardRepository;
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card findById(String id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "card does not exist");
        }
        return optionalCard.get();
    }
    public List<Card> findAll(User user) {
        return cardRepository.findAll(user);
    }

    public void updateUser(Card card, User user) {
        cardRepository.updateUserId(card, user);
    }
}
