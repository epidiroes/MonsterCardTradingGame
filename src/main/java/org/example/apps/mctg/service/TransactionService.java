package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.Request;

import java.util.List;
import java.util.Optional;

public class TransactionService {
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public TransactionService(PackageRepository packageRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public Package buy(Request request) {
        // find user by name in the authorization header
        String authorization = request.getAuthorization();
        String name = authorization.substring(authorization.indexOf(" ") + 1, authorization.indexOf("-", authorization.indexOf(" ") + 1));
        Optional<User> userOptional = userRepository.find(name);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();

        // check if the user has enough coins to buy a package
        if (user.getCoins() < 5) {
            return null;
        }

        // find admin user
        Optional<User> adminOptional = userRepository.find("admin");
        if (adminOptional.isEmpty()) {
            return null;
        }
        User admin = adminOptional.get();

        List<Package> packages = packageRepository.findAll(admin);
        if (packages.isEmpty()) {
            return null;
        }
        Package pack = packageRepository.transaction(user, packages.get(0));
        if (pack == null) {
            return null;
        }
        cardRepository.updateUserIdPackage(pack, user);
        userRepository.removeCoins(user);

        return pack;
    }
}
