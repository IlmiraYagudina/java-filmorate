package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

@SpringBootTest
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmControllerTest {
    private final InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    private final Film film = Film.builder()
            .id(1L)
            .name("Movie")
            .description("The most awesome movie I've ever seen")
            .releaseDate(LocalDate.of(2020, 2, 2))
            .duration(120)
            .build();


    @Test
    void create_shouldNotAddAMovieWithAnEmptyName() { // Если название пустое
        film.setName("");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilms(film));
        Assertions.assertEquals(0, inMemoryFilmStorage.getFilm().size());
    }

    @Test
    void create_shouldNotAddAMovieWithDescriptionMoreThan200() { // Если описание более 200
        film.setDescription("This is the most amazing and terrifying movie in my life. I love scary movies," +
                "but I've never seen such precise details of serial killers doing thier job." +
                "You should deffinately see this one. Actually, this movie was based on a true story. Creepy...");

        Assertions.assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilms(film));
        Assertions.assertEquals(0, inMemoryFilmStorage.getFilm().size());
    }

    @Test
    void create_shouldNotAddAMovieWithDateReleaseMoreThan1895() { // Если дата старше 28.12.1895
        film.setReleaseDate(LocalDate.of(1891, 2, 2));

        Assertions.assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilms(film));
        Assertions.assertEquals(0, inMemoryFilmStorage.getFilm().size());
    }

    @Test
    void create_shouldNotAddAMovieIfDurationIsMoreThan0() { // Продолжительность отрицательная
        film.setDuration(-15);

        Assertions.assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilms(film));
        Assertions.assertEquals(0, inMemoryFilmStorage.getFilm().size());
    }
}

