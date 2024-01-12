package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.UserRepository;

import java.util.Optional;

public class AuthorizationService {
    private UserRepository userRepository;
    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authorizedUser(String authorization) {
        if (authorization.isEmpty()) {
            return Optional.empty();
        }
        String name = authorization.substring(authorization.indexOf(" ") + 1, authorization.indexOf("-", authorization.indexOf(" ") + 1));
        return userRepository.find(name);
    }
}
