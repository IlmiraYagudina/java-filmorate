package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.Validation;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    /**
     * Добавление фильма.
     *
     * @param film информация о фильме.
     */

    @ResponseBody
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        Validation.filmValidation(film);
        log.info("'{}' movie was added to a library, the identifier is '{}'", film.getName(), film.getId());
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    /**
     * Получение списка фильмов.
     *
     * @return films возвращает коллекцию фильмов.
     */

    @ResponseBody
    @GetMapping
    public List<Film> getFilms() {
        log.info("There is '{}' movies in a library now", films.size());
        return new ArrayList<>(films.values());
    }

    /**
     * Обновление фильма.
     *
     * @param film информация о фильме.
     */

    @ResponseBody
    @PutMapping
    public Film
    update(@Valid @RequestBody Film film) {
        Validation.filmValidation(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("'{}' movie was updated in a library, the identifier is '{}'", film.getName(), film.getId());
        } else {
            log.debug("Фильм не существует");
            throw new ValidationException("Attempt to update non-existing movie");
        }
        return film;
    }
}