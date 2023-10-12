package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Film {
    @Positive
    private Integer id; // Id фильма
    @NotBlank
    private String name; // Название фильма
    @NotBlank
    @Size(max = 200)
    private String description; // Описание фильма
    private LocalDate releaseDate; // Дата релиза
    @Min(1)
    private long duration; // Продолжительность фильма
}