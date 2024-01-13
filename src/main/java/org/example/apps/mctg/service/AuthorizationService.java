package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;

import java.util.Optional;

public class AuthorizationService {
    private final UserRepository userRepository;
    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authorizedUser(String authorization) {
        return findUser(authorization);
    }

    public void checkAuthorization(String authorization) {
        findUser(authorization);
    }

    private User findUser(String authorization) {
        if (authorization.isEmpty()) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Unauthorized, authorization is empty");
        }
        String name = authorization.substring(authorization.indexOf(" ") + 1, authorization.indexOf("-", authorization.indexOf(" ") + 1));
        Optional<User> optionalUser = userRepository.find(name);
        if (optionalUser.isEmpty()) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Unauthorized, user not found");
        }
        return optionalUser.get();
    }
}
