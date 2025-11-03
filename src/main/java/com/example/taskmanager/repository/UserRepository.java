package com.example.taskmanager.repository;

import com.example.taskmanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String fetchUsernameById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT username FROM users WHERE id = ?",
                String.class,
                id);
    }

    public User fetchUserById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id,username,password FROM users WHERE id = ?",
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(Long.valueOf(rs.getString("id")));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                },
                id
        );
    }
}