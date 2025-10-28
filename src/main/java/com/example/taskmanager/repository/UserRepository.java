package com.example.taskmanager.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findUsernameById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT username FROM users WHERE id = ?",
                String.class,
                id);
    }

    public Long findIdByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM users WHERE username = ?",
                new Object[]{username},
                Long.class
        );
    }
}