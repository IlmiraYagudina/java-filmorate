package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-модель для создания объекта жанра со свойствами <b>id<b/>, <b>name<b/>.
 */
@Data
@NoArgsConstructor
public class Genre {
    /**
     * Поле идентификатор жанра
     */
    Integer id;

    /**
     * Поле содержащие имя жанра
     */
    String name;
}