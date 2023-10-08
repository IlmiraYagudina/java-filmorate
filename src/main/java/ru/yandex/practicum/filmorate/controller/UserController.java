package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j

@RequestMapping("/users")
@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @ResponseBody
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userValidation(user);
        users.put(user.getId(), user);
        log.info("The user '{}' has been saved with the identifier '{}'", user.getEmail(), user.getId());
        return user;
    }

    @ResponseBody
    @GetMapping
    public List<User> getUsers() {
        log.info("The number of users: '{}'", users.size());
        return new ArrayList<>(users.values());
    }

    @ResponseBody
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        userValidation(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("'{}' info with identifier '{}' was updated", user.getLogin(), user.getId());
        } else {
            throw new ValidationException("Attempt to update non-existing user");
        }
        return user;
    }

    public static void userValidation(User user) {
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Incorrect user's birthday with identifier '" + user.getId() + "'");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Incorrect user's email with identifier '" + user.getId() + "'");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("User's name with identifier '{}' was set as '{}'", user.getId(), user.getName());
        }

        if (user.getLogin().isBlank() || user.getLogin().isEmpty()) {
            throw new ValidationException("Incorrect login with user's identifier '" + user.getId() + "'");
        }
    }
}