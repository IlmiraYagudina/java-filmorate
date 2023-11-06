package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

/**
 * Утилитарный класс реализующий проверку соответствия данных в полях объектов с типом Film и User
 */
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Validation {
    /**
     * Пустой приватный конструктор для запрета создания экземпляров утилитарного класса
     */
    private Validation() {

    }

    /**
     * Проверка фильма на корректность.
     */
    public static void filmValidation(Film film) {
        String str = film.getDescription();
        final LocalDate const_data = LocalDate.of(1895, 12, 28);
        char[] strToArray = str.toCharArray(); // Преобразуем строку str в массив символов (char)
        if (strToArray.length > 200 || strToArray.length == 0) {
            log.debug("Длина описание фильма > 200");
            throw new ValidationException("Description increases 200 symbols or empty");
        }
        if (film.getReleaseDate().isBefore(const_data)) {
            log.debug("Дата релиза < 28.12.1895");
            throw new ValidationException("Incorrect release date");
        }
        if (film.getName().isEmpty() || film.getName().isBlank() || film.getName() == null) {
            log.debug("Название фильма пустое");
            throw new ValidationException("Attempt to set an empty movie name");
        }
        if (film.getDuration() < 0) {
            log.debug("Длительность меньше 0");
            throw new ValidationException("Attempt to set duration less than zero");
        }
    }

    /**
     * Проверка пользователя на корректность.
     */
    public static void userValidation(User user) {
        char[] nameChar = user.getLogin().toCharArray();

        for (char c : nameChar) {
            if (c == ' ') { // Для наглядности вставим пробел между индексами
                throw new ValidationException("Логин содержит пробел");
            }
        }

        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            log.debug("Неверный  День Рождения");
            throw new ValidationException("Incorrect user's birthday with identifier '" + user.getId() + "'");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Incorrect user's email with identifier '" + user.getId() + "'");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("User's name was set as '{}' and user identifier was set as '{}'", user.getId(), user.getName());
        }
        if (user.getLogin().isBlank() || user.getLogin().isEmpty()) {
            log.debug("У пользователя нет логина");
            user.setName(user.getLogin());
            throw new ValidationException("Incorrect login with user's identifier '" + user.getId() + "'");
        }
    }
}
