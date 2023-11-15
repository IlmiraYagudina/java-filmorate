package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.Validation;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.time.LocalDate;

@SpringBootTest
public class UserControllerTest {
    private final UserStorage inMemoryUserStorage = new InMemoryUserStorage();
    private final User user = new User("yandex1@yandex.ru", "user", "User1", LocalDate.of(1996, 1, 1));
    private final User user2 = new User("yandex2@yandex.ru", "user", "User2", LocalDate.of(1996, 1, 1));
    private final User user3 = new User("yandex3@yandex.ru", "user", "User3", LocalDate.of(1996, 1, 1));


    @Test
    void create_shouldThrowExceptionIfEmailIncorrect() { // Если пустой @
        user.setEmail("yandex.mail.ru");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.create(user));
        Assertions.assertEquals(0, inMemoryUserStorage.getUser().size());
    }

    @Test
    void create_shouldThrowExceptionIfEmailIsEmpty() { // Если пустой email
        user.setEmail("");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.create(user));
        Assertions.assertEquals(0, inMemoryUserStorage.getUser().size());
    }

    @Test
    void create_shouldNotAddUserIfLoginIsEmpty() { // Если логин пуст
        user.setLogin("");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.create(user));
        Assertions.assertEquals(0, inMemoryUserStorage.getUser().size());
    }

    @Test
    void create_shouldNotAddUserIfBirthdayIsInTheFuture() { // Если день рождения записано в будущем
        user.setBirthday(LocalDate.of(2024, 6, 28));

        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.create(user));
    }

    @Test
    void dateOfBirthFromToDay() { // Если др сегодня, то все ок
        user.setBirthday(LocalDate.now());
        Validation.userValidation(user);

        Assertions.assertEquals(user.getBirthday(), LocalDate.now());
    }

    @Test
    void getUserId() { // Запрос пользователя по id
        inMemoryUserStorage.create(user);

        Assertions.assertEquals(inMemoryUserStorage.getByIdUser(1L), user);
    }

    @Test
    void loginContainSpace() { // Если логин содержит пробел
        user.setLogin("And ex");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.create(user));
        Assertions.assertEquals(0, inMemoryUserStorage.getUser().size());
    }

    @Test
    void loginSpace() { // Если логин состоит из пробела
        user.setLogin(" ");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.create(user));
        Assertions.assertEquals(0, inMemoryUserStorage.getUser().size());
    }

    @Test
    void emptyName() { // Если имя пустое, имя = логин
        user.setName(" ");
        Validation.userValidation(user);

        Assertions.assertEquals(user.getName(), user.getLogin());
    }
}