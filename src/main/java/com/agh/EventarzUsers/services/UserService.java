package com.agh.EventarzUsers.services;

import com.agh.EventarzUsers.exceptions.UserNotFoundException;
import com.agh.EventarzUsers.model.BanForm;
import com.agh.EventarzUsers.model.User;
import com.agh.EventarzUsers.repositories.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Retry(name = "UserServiceRetry")
@CircuitBreaker(name = "UserServiceCircuitBreaker")
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsersByUsername(String name) {
        List<User> users = userRepository.findByUsernameLikeIgnoreCase("%" + name + "%");
        return users;
    }

    public User createUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
        return user;
    }

    public void checkIfUserExists(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
    }

    public String getPasswordHash(String username) throws UserNotFoundException {
        String passwordHash = userRepository.findPasswordHashOf(username);
        if (passwordHash == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
        return passwordHash;
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public User changeBanStatus(String username, BanForm banForm) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
        user.setBanned(banForm.isBanned());
        // Save necessary
        user = userRepository.save(user);
        return user;
    }
}
