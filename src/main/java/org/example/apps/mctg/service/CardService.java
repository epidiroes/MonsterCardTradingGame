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
    private final UserRepository userRepository;
    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Card findById(String id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "card does not exist");
        }
        return optionalCard.get();
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

        return cardRepository.findAll(user);
    }

    public void updateUser(Card card, User user) {
        cardRepository.updateUserId(card, user);
    }
}
