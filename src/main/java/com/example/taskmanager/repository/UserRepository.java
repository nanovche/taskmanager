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
}