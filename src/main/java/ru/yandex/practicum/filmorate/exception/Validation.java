package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class Validation {
    private Validation() {

    }

    /**
     * Проверка фильма на корректность.
     */
    public static void filmValidation(Film film) {
        String str = film.getDescription();
        char[] strToArray = str.toCharArray(); // Преобразуем строку str в массив символов (char)
        if (strToArray.length > 200 || strToArray.length == 0) {
            log.debug("Длина описание фильма > 200");
            throw new ValidationException(String.format("Description increases 200 symbols or empty"));
        }
        if (film.getReleaseDate() == null ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Дата релиза < 28.12.1895");
            throw new ValidationException(String.format("Incorrect release date"));
        }
        if (film.getName().isEmpty() || film.getName().isBlank() || film.getName() == null) {
            log.debug("Название фильма пустое");
            throw new ValidationException(String.format("Attempt to set an empty movie name"));
        }
        if (film.getDuration() < 0) {
            log.debug("Длительность меньше 0");
            throw new ValidationException(String.format("Attempt to set duration less than zero"));
        }
    }

    /**
     * Проверка пользователя на корректность.
     */
    public static void userValidation(User user) {
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Incorrect user's birthday with identifier '" + user.getId() + "'");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Incorrect user's email with identifier '" + user.getId() + "'");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("User's name was set as '{}' and user identifier was set as '{}'", user.getId(), user.getName());
        }
        if (user.getId() == 0 || user.getId() < 0) {
            int id = user.getId();
            user.setId(++id);
            log.info("Incorrect user identifier was set as '{}'", user.getId());
        }
        if (user.getLogin().isBlank() || user.getLogin().isEmpty()) {
            throw new ValidationException("Incorrect login with user's identifier '" + user.getId() + "'");
        }
    }
}
