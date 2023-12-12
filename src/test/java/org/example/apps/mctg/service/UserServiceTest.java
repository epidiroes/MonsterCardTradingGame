package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Test
    void shouldSetUserId_whenSaveUser() {
        // ARRANGE
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        User user = new User("", "helene", "1234asdf");

        when(userRepository.save(any())).then(returnsFirstArg());

        // ACT
        userService.save(user);

        // ASSERT
        assertNotEquals("", user.getId());
        assertEquals("helene", user.getUsername());
        assertEquals("1234asdf", user.getPassword());
    }
}