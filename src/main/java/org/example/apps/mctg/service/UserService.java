package org.example.apps.mctg.service;

import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.entity.User;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return userRepository.save(user);
    }
}
