package com.example.taskmanager.repository;

import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.exception.NoSuchUserException;
import com.example.taskmanager.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Primary
public class UserRepositoryAdapter implements UserRepository{

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return Optional.empty();
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Boolean existsByUsername(String username) {
        return null;
    }

    /**
     *
     */
    @Override
    public void flush() {

    }

    /**
     * @param entity
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    /**
     * @param entities
     */
    @Override
    public void deleteAllInBatch(Iterable<UserEntity> entities) {

    }

    /**
     * @param longs
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    /**
     *
     */
    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public UserEntity getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public UserEntity getById(Long aLong) {
        return null;
    }

    /**
     * @param aLong
     * @return
     */
    @Override
    public UserEntity getReferenceById(Long aLong) {
        return null;
    }

    /**
     * @param example
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    /**
     * @param example
     * @param sort
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    /**
     * @param entities
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    /**
     * @return
     */
    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    /**
     * @param longs
     * @return
     */
    @Override
    public List<UserEntity> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    /**
     * @param entity
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> S save(S entity) {
        return null;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Optional<UserEntity> findById(Long userId) {

        try {
            return userRepository.findById(userId);
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to fetch user " + userId, ex);
        }
    }


    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * @param userId
     */
    @Override
    public void deleteById(Long userId) {

        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchUserException("User not found: " + userId, ex);
        } catch (DataAccessException ex) {
            throw new RepositoryException("Failed to delete user " + userId, ex);
        }
    }

    /**
     * @param entity
     */
    @Override
    public void delete(UserEntity entity) {

    }

    /**
     * @param longs
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    /**
     * @param entities
     */
    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {

    }

    /**
     *
     */
    @Override
    public void deleteAll() {

    }

    /**
     * @param sort
     * @return
     */
    @Override
    public List<UserEntity> findAll(Sort sort) {
        return List.of();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return null;
    }

    /**
     * @param example
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example
     * @param pageable
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> long count(Example<S> example) {
        return 0;
    }

    /**
     * @param example
     * @param <S>
     * @return
     */
    @Override
    public <S extends UserEntity> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * @param example
     * @param queryFunction
     * @param <S>
     * @param <R>
     * @return
     */
    @Override
    public <S extends UserEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
