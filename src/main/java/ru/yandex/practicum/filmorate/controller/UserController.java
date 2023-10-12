package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.Validation;
import ru.yandex.practicum.filmorate.exception.ValidationException;


import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j

@RequestMapping("/users")
@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    /**
     * Добавление пользователя.
     *
     * @param user информация о пользователе.
     */

    @ResponseBody
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        Validation.userValidation(user);
        users.put(user.getId(), user);
        log.info("The user '{}' has been saved with the identifier '{}'", user.getEmail(), user.getId());
        return user;
    }

    /**
     * Получение списка пользователей.
     *
     * @return users возвращает коллекцию пользователей.
     */

    @ResponseBody
    @GetMapping
    public List<User> getUsers() {
        log.info("The number of users: '{}'", users.size());
        return new ArrayList<>(users.values());
    }

    /**
     * Обновление пользователя.
     *
     * @param user информация о пользователе.
     */

    @ResponseBody
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        Validation.userValidation(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("'{}' info with identifier '{}' was updated", user.getLogin(), user.getId());
        } else {
            throw new ValidationException("Attempt to update non-existing user");
        }
        return user;
    }
}