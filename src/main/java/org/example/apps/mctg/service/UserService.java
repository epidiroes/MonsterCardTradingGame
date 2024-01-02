package org.example.apps.mctg.service;

import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.entity.User;
import org.example.server.http.Request;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(Request request) {
        String nameRoute = request.getRoute().substring(request.getRoute().lastIndexOf('/') + 1);
        Optional<User> userOptional = userRepository.find(nameRoute);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();

        String authorization = request.getAuthorization();
        if (Objects.equals(authorization, "")) {
            return null;
        }
        String nameAuth = authorization.substring(authorization.indexOf(" ") + 1, authorization.indexOf("-", authorization.indexOf(" ") + 1));

        if (nameRoute.equals(nameAuth)) {
            return user;
        } else {
            return null;
        }

    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        Optional<User> findUser = userRepository.find(user.getUsername());
        if (findUser.isPresent()) {
            return null;
        }
        user.setId(UUID.randomUUID().toString());
        user.setCoins(20);
        return userRepository.save(user);
    }
}
