package org.example.apps.mctg.service;

import org.example.apps.mctg.dto.Token;
import org.example.apps.mctg.dto.TokenRequest;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.UserRepository;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

public class SessionService {
    private final UserRepository userRepository;

    public SessionService() {
        this.userRepository = new UserRepository();
    }

    public Token getToken (TokenRequest tokenRequest) {
        Optional<User> userOptional = findByUsername(tokenRequest.getUsername());
        if (userOptional.isEmpty()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "User with this username doesn't exit");
        } else {
            User user = userOptional.get();
            if(!checkPassword(user, tokenRequest.getPassword())) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Wrong password");
            }
            return generateToken();
        }
    }

    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    private Token generateToken() {
        return new Token(UUID.randomUUID().toString());
    }

    private Optional<User> findByUsername(String username) {
        return userRepository.find(username);
    }
}
