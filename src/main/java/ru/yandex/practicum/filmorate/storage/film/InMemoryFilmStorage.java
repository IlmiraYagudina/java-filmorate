package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.Validation;
import ru.yandex.practicum.filmorate.model.Genre;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Класс-хранилище реализующий интерфейс {@link FilmStorage} для хранения и обновления фильмов со свойствами <b>films<b/> и <b>id<b/>
 */
@Component("InMemoryFilmStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    /**
     * Поле хранилище фильмов
     */
    private final Map<Long, Film> films = new HashMap<>();

    /**
     * Поле счетчик идентификаторов фильмов
     */
    private Long id = 1L;

    /**
     * Метод добавление фильма.
     *
     * @param film информация о фильме.
     * @return возвращает созданный фильм
     */
    public Film addFilms(@Valid Film film) {
        Validation.filmValidation(film);
        log.debug("Фильм добавлен");
        film.setId(id);
        films.put(film.getId(), film);
        id++;
        return film;
    }

    /**
     * Метод обновления фильма.
     *
     * @param film информация о фильме.
     * @return возвращает обновленный фильм
     * @throws NotFoundException генерирует 404 ошибку в случае если фильма не существует.
     */
    public Film put(@Valid Film film) {
        Long filmId = film.getId();
        Validation.filmValidation(film);
        if (films.containsKey(filmId)) {
            log.debug("Фильм обновлен");
            films.put(filmId, film);
        } else {
            log.debug(String.format("Фильм с id %s не существует", filmId));
            throw new NotFoundException("Данного фильма нет в базе данных");
        }
        return film;
    }

    /**
     * Метод получения списка фильмов.
     *
     * @return films возвращает коллекцию фильмов.
     */
    public Collection<Film> getFilm() {
        log.debug("Запрошен список фильмов, их количество: {} ", films.size());
        return films.values();
    }

    /**
     * Метод получения фильма по id.
     *
     * @param id айди фильма.
     * @return возвращает фильм с указанным id
     * @throws NotFoundException генерирует 404 ошибку в случае если фильма не существует.
     */
    public Film getByIdFilm(Long id) {
        if (films.containsKey(id)) {
            log.debug("Запрошен фильм с id : {} ", id);
            return films.get(id);
        } else {
            log.debug("Фильм не существует");
            throw new NotFoundException(String.format("Фильм с id %s не существует", id));
        }
    }

    /**
     * Заглушка
     */
    @Override
    public HashSet<Genre> getGenresByFilm(Long filmId) {
        return null;
    }
}