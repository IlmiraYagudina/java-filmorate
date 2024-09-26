package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * Класс-модель для создания объекта рейтинга со свойствами <b>id<b/>, <b>name<b/>.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mpa {

    /**
     * Поле с идентификатором рейтинга.
     */
    @NotNull
    private Integer id;

    /**
     * Поле c именем рейтинга.
     */
    @NotNull
    private String name;
}