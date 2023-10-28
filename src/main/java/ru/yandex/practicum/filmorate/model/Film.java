package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class Film {
    @Positive
    private Long id; // Id фильма
    @NotBlank
    private String name; // Название фильма
    @NotBlank
    @Size(max = 200)
    private String description; // Описание фильма
    private LocalDate releaseDate; // Дата релиза
    @Min(1)
    private long duration; // Продолжительность фильма

    /**
     * Поле с перечислением пользователей поставивших лайки
     */
    private Set<Long> like = new HashSet<>();

    /**
     * Конструктор создание нового объекта фильма.
     *
     * @see Film#Film(Long, String, String, LocalDate, int)
     */
    @Autowired
    public Film(Long id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
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