package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс-модель для создания пользователя со свойствами <b>id<b/>, <b>email<b/>, <b>login<b/>, <b>name<b/>, <b>birthday<b/>, <b>friends<b/>.
 */
@Data
public class User {
    private Long id; // Идентификатор
    @Email
    @NotBlank
    @NotNull
    private String email; // Электронная почта
    @NotEmpty
    @NotBlank
    private String login; // Логин пользователя
    private String name; // Имя для отображения
    @NotNull
    @PastOrPresent
    private LocalDate birthday; // Дата рождения

    /**
     * Поле со списком друзей пользователя
     */
    private Set<Long> friends = new HashSet<>();

    /**
     * Конструктор создание нового объекта пользователя.
     *
     * @see User#User(Long, String, String, String, LocalDate)
     */
    @Autowired
    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    /**
     * Метод добавление друга
     *
     * @param id id пользователя добавляемого в друзья.
     */
    public void addFriend(Long id) {
        friends.add(id);}

    /**
     * Метод удаление друга
     *
     * @param id id пользователя удаляемого из друзей.
     */
    public void deleteFriend(Long id) {
        friends.remove(id);
    }
}