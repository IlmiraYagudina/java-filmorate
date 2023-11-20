package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.dao.friend.FriendDao;
import ru.yandex.practicum.filmorate.storage.dao.genre.GenreDao;
import ru.yandex.practicum.filmorate.storage.dao.like.LikeDao;
import ru.yandex.practicum.filmorate.storage.dao.mpa.MpaDao;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Класс-сервис с логикой для оперирования пользователями с хранилищами <b>userDbStorage<b/>
 */
@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDbService {
    /**
     * Поле хранилище пользователей
     */
    private final UserStorage userStorage;

    /**
     * Поле для доступа к операциям с жанрами
     */
    private final GenreDao genreDao;

    /**
     * Поле для доступа к операциям с рейтингом
     */
    private final MpaDao mpaDao;

    /**
     * Поле для доступа к операциям с лайками
     */
    private final LikeDao likeDao;

    /**
     * Поле для доступа к операциям с друзьями
     */
    private final FriendDao friendDao;

    /**
     * Конструктор сервиса.
     *
     * @see UserDbService#UserDbService(UserDbStorage, GenreDao, MpaDao, LikeDao, FriendDao)
     */
    @Autowired
    public UserDbService(@Qualifier("UserDbStorage") UserDbStorage userStorage,
                         GenreDao genreDao,
                         MpaDao mpaDao,
                         LikeDao likeDao,
                         FriendDao friendDao) {

        this.userStorage = userStorage;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
        this.likeDao = likeDao;
        this.friendDao = friendDao;
    }

    /**
     * Добавление в друзья.
     *
     * @param userId   айди пользователя, добавляющего в друзья.
     * @param idFriend айди добавляемого пользователя в друзья.
     * @throws NotFoundException генерирует ошибку 404 если введен не верный id пользователя или друга.
     */
    public void addFriends(Long userId, Long idFriend) {
        if (userId > 0 && idFriend > 0) {
            boolean status = friendDao.statusFriend(userId, idFriend);
            friendDao.addFriends(userId, idFriend, status);

            log.info("Пользователи с id {} и {} добавлены друг другу в друзья", userId, idFriend);
        } else {
            throw new NotFoundException(String.format("Введен не верный id пользователя %s или друга %s", userId, idFriend));
        }
    }

    /**
     * Удаление из друзей.
     *
     * @param userId   айди пользователя, удаляющего из друзей.
     * @param idFriend айди удаляемого пользователя из друзей.
     * @throws NotFoundException генерирует ошибку 404 если пользователей с id userId и idFriend не существует.
     */
    public void deleteFriends(Long userId, Long idFriend) {
        if (userStorage.getByIdUser(userId) != null && userStorage.getByIdUser(idFriend) != null) {
            friendDao.deleteFriends(userId, idFriend);

            log.info("Пользователь с id {} и {} удалены друг у друга из друзей", userId, idFriend);
        } else {
            throw new NotFoundException(String.format("Пользователь с id %s и его друг с id %s не существует", userId, idFriend));
        }
    }

    /**
     * Получение списка общих друзей у двух пользователей.
     *
     * @param userId   айди пользователя, от кого поступает запрос на получение информации.
     * @param idFriend айди пользователя, с кем необходимо отобразить общих друзей.
     * @return возвращает список общих друзей или пустой список, если таковых необнаружено.
     */
    public List<User> getMutualFriends(Long userId, Long idFriend) {
        List<User> userFriends = getFriends(userId);
        List<User> friendFriends = getFriends(idFriend);

        log.info("Запрошены общие друзья у пользователя с id {} и {}", userId, idFriend);

        return friendFriends.stream()
                .filter(userFriends::contains)
                .filter(friendFriends::contains)
                .collect(Collectors.toList());

    }

    /**
     * Получение списка друзей у пользователя.
     *
     * @param id айди пользователя, чьих друзей необходимо вывести.
     * @return возвращает список друзей или пустой список если их нет.
     * @throws NotFoundException генерирует 404 ошибку в случае если пользователя не существует.
     */
    public List<User> getFriends(Long id) {
        return friendDao.getFriend(id);
    }

    /**
     * Получение доступа к хранилищу через сервис.
     */
    public UserStorage getUserStorage() {
        return userStorage;
    }

    public User create(@Valid User user) {
        return userStorage.create(user);
    }

    public Collection<User> getUser() {
        return userStorage.getUser();
    }
}