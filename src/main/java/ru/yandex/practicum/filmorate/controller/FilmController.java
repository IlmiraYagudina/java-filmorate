package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @ResponseBody
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        filmValidation(film);
        log.info("'{}' movie was added to a library, the identifier is '{}'", film.getName(), film.getId());
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    @ResponseBody
    @GetMapping
    public List<Film> getFilms() {
        log.info("There is '{}' movies in a library now", films.size());
        return new ArrayList<>(films.values());
    }

    @ResponseBody
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        filmValidation(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("'{}' movie was updated in a library, the identifier is '{}'", film.getName(), film.getId());
        } else {
            log.debug("Фильм не существует");
            throw new ValidationException("Attempt to update non-existing movie");
        }
        return film;
    }

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
}