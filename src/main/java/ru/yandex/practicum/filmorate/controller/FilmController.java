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
    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        filmValidation(film);
        film.setId(id);
        films.put(film.getId(), film);
        log.info("'{}' movie was added to a library, the identifier is '{}'", film.getName(), film.getId());
        return film;
    }

    @ResponseBody
    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("There is '{}' movies in a library now", films.size());
        return new ArrayList<>(films.values());
    }

    @ResponseBody
    @PutMapping("/films")
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

    private void filmValidation(Film film) {
        String str = film.getDescription();
        char[] strToArray = str.toCharArray(); // Преобразуем строку str в массив символов (char)
        if (film.getReleaseDate() == null ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Incorrect release date");
        }
        if (film.getName().isEmpty() || film.getName().isBlank() || film.getName() == null) {
            throw new ValidationException("Attempt to set an empty movie name");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Attempt to set duration less than zero");
        }
        if (strToArray.length > 200 || strToArray.length == 0) {
            throw new ValidationException("Description increases 200 symbols or empty");
        }
        if (film.getId() <= 0) {
            film.setId(++id);
            log.info("Incorrect movie identifier was set as '{}", film.getId());
        }
    }
}