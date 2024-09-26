package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Film {
    private Long id; // Id фильма
    @NotBlank
    private String name; // Название фильма
    @NotBlank
    @Size(max = 200)
    private String description; // Описание фильма
    private LocalDate releaseDate; // Дата релиза
    @PositiveOrZero
    private int duration; // Продолжительность фильма

    /**
     * Поле с перечислением пользователей поставивших лайки
     */
    private Set<Long> like = new HashSet<>();

    /**
     * Поле с перечислением жанров фильма
     */
    private HashSet<Genre> genres;

    /**
     * Поле с указанием рейтинга фильма
     */
    @NotNull
    private Mpa mpa;

    /**
     * Конструктор создание нового объекта фильма.
     *
     * @see Film #Film(Long, String, String, LocalDate, int, HashSet<Genre>, Mpa)
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public Film(Long id, String name, String description, LocalDate releaseDate, int duration, HashSet<Genre> genres, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
    }

    /**
     * Метод добавления лайка
     *
     * @param id id пользователя поставившего свой лайк
     */
    public void addLike(Long id) {
        like.add(id);
    }

    /**
     * Метод удаления лайка у фильма
     *
     * @param id id пользователя удалившего свой лайк
     */
    public void deleteLike(Long id) {
        like.remove(id);
    }

    /**
     * Метод получения значения количества лайков у фильма
     *
     * @return возвращает количество лайков
     */
    public Integer getLike() {
        return like.size();
    }
}