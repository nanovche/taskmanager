package com.example.taskmanager.repository;

import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.exception.UserNotFoundException;
import com.example.taskmanager.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class UserRepositoryAdapter {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        try {
            List<S> saved = userRepository.saveAll(entities);
            userRepository.flush();
            return saved;
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to save users", ex);
        }
    }

    public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
        try {
            return userRepository.saveAll(entities);
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to save users", ex);
        }
    }

    public long count(){
        return userRepository.count();
    }

    public <S extends UserEntity> S save(S entity) {

        try {
            return userRepository.save(entity);
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to save user " + entity.getUsername(), ex);
        }
    }

    public Optional<UserEntity> findById(Long userId) {

        try {
            return userRepository.findById(userId);
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to fetch user " + userId, ex);
        }
    }

    public void deleteById(Long userId) {

        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException("User not found: " + userId, ex);
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to delete user " + userId, ex);
        }
    }
}