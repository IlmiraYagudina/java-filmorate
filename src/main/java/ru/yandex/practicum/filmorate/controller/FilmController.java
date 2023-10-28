package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private final FilmService filmService;

    public FilmController() {
        filmService = null;
    }

    /**
     * Добавление фильма.
     *
     * @param film информация о фильме.
     */

    @ResponseBody
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.addFilms(film);
    }

    /**
     * Получение списка фильмов.
     *
     * @return films возвращает коллекцию фильмов.
     */

    @ResponseBody
    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilm();
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
        return filmService.put(film);
    }

    /**
     * Добавляет лайк фильму
     *
     * @param id     id фильма.
     * @param userId id поставившего лайк.
     */
    @PutMapping("{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(userId, id);
    }

    /**
     * Удаляет лайк у фильма
     *
     * @param id     id фильма.
     * @param userId id удалившего свой лайк.
     */
    @DeleteMapping("{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(userId, id);
    }
    /**
     * Запрос фильма по id
     *
     * @param id id фильма
     * @return возвращает фильм
     */
    @GetMapping("{id}")
    public Film getByIdFilm(@PathVariable Long id) {
        return filmService.getByIdFilm(id);
    }

    /**
     * Запрос фильмов по количеству лайков
     *
     * @param count количество попавших в топ фильмов(Если не указано, то 10)
     * @return возвращает список фильмов с количеством лайков (От большего к меньшему)
     */
    @GetMapping("popular")
    public List<Film> getPopularFilm(@PathVariable @RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularFilm(Long.valueOf(count));
    }
    /**
     * Запрос фильмов
     *
     * @return возвращает коллекцию фильмов
     */
    @GetMapping
    public Collection<Film> getFilm() {
        return filmService.getFilm();
    }
}