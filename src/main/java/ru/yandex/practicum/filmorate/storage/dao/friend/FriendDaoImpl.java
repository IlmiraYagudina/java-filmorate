package ru.yandex.practicum.filmorate.storage.dao.friend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.FriendMapper;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;

import java.util.List;

@Component
public class FriendDaoImpl implements FriendDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriends(Long userId, Long idFriend, boolean status) {
        jdbcTemplate.update("INSERT INTO friends (user_id, friend_id, status) VALUES (?,?,?)", userId, idFriend, status);
    }

    @Override
    public void deleteFriends(Long userId, Long idFriend) {
        jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, idFriend);
        jdbcTemplate.update("UPDATE friends SET status = false WHERE user_id=? AND friend_id=?", idFriend, userId);
    }

    @Override
    public boolean statusFriend(Long userId, Long friendId) {
        try {
            jdbcTemplate.queryForObject("SELECT user_id, friend_id, status FROM friends WHERE user_id=? AND friend_id=?",
                    new FriendMapper(), userId, friendId);
            return true;

        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    @Override
    public List<User> getFriend(Long userId) {
        return jdbcTemplate.query("SELECT * FROM users  WHERE user_id IN (SELECT friend_id FROM friends WHERE user_id=?)",
                        new UserMapper(), userId);
    }
}