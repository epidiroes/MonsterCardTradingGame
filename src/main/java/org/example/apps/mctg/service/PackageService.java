package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.Request;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PackageService {
    private final PackageRepository packageRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    public PackageService(PackageRepository packageRepository, CardRepository cardRepository, UserRepository userRepository) {
        this.packageRepository = packageRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Package save(Request request, List<Card> cards) {
        // check authorization
        if (!Objects.equals(request.getAuthorization(), "Bearer admin-mtcgToken")) {
            return null;
        }
        Optional<User> user = userRepository.find("admin");
        if (user.isEmpty()) {
            return null;
        }
        User admin = user.get();

        // save cards
        if(!saveAll(cards, admin)) {
            return null;
        }
        return packageRepository.save(admin, cards);
    }

    public boolean saveAll(List<Card> cards, User user) {
        for (Card card : cards) {
            card.setUser_id(user.getId());
            if (!cardRepository.save(card)) {
                return false;
            }
        }
        return true;
    }
}
