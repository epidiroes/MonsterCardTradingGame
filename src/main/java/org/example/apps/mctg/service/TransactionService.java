package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
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

    public Package buy(User user) {
        // check if the user has enough coins to buy a package
        if (user.getCoins() < 5) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "User doesn't have enough coins to buy package");
        }

        // find admin user
        Optional<User> adminOptional = userRepository.find("admin");
        if (adminOptional.isEmpty()) {
            return null;
        }
        User admin = adminOptional.get();

        List<Package> packages = packageRepository.findAll(admin);
        if (packages.isEmpty()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "No available packages");
        }
        Package pack = packageRepository.transaction(user, packages.get(0));
        cardRepository.updateUserIdPackage(pack, user);
        userRepository.removeCoins(user);

        return pack;
    }
}
