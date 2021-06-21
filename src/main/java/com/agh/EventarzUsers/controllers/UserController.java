package com.agh.EventarzUsers.controllers;

import com.agh.EventarzUsers.exceptions.UserNotFoundException;
import com.agh.EventarzUsers.model.BanForm;
import com.agh.EventarzUsers.model.User;
import com.agh.EventarzUsers.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findUsersByUsername(@RequestParam String username) {
        return userService.findUsersByUsername(username);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!", e);
        }
    }

    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/users/{username}")
    public void checkIfUserExists(@PathVariable String username) {
        try {
            userService.checkIfUserExists(username);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!", e);
        }
    }

    @GetMapping("/users/{username}/securityDetails")
    public String getPasswordHash(@PathVariable String username) {
        try {
            return userService.getPasswordHash(username);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!", e);
        }
    }

    @PutMapping("users/{username}/securityDetails/banned")
    public User changeBanStatus(@PathVariable String username, @RequestBody BanForm banForm) {
        try {
            return userService.changeBanStatus(username, banForm);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!", e);
        }
    }
}
