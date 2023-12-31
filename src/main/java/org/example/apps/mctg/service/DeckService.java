package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.Request;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeckService {
    private final UserRepository userRepository;
    private final DeckRepository deckRepository;
    public DeckService(UserRepository userRepository, DeckRepository deckRepository) {
        this.userRepository = userRepository;
        this.deckRepository = deckRepository;
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
}
