package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.exception.*;
import com.example.taskmanager.mappers.UserMapper;
import com.example.taskmanager.repository.UserRepository;
import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepository, ValidationService validationService, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO fetchUser(Long userId) {

        if (userId == null) {
            logger.warn("fetchUser called with null userId");
            throw new ValidationException("userId is required");
        }

        logger.info("calling fetch user with userId: {}", userId);

        try {
            return userRepository.findById(userId)
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new UserFetchingFailedException("Failed to fetch user: " + userId, new UserNotFoundException("User not found: " + userId)));
        } catch (RepositoryException ex) {
            logger.error("Failed to fetch user: userId {}", userId, ex);
            throw new UserFetchingFailedException("Failed to fetch user " + userId, ex);
        }
    }

    public UserDTO createUser(UserDTO dtoUser) {

        validationService.validateUserInput(dtoUser);

        if(!validationService.checkPasswordStrength(dtoUser.getPassword())) {
            logger.warn("Weak password for username={}", dtoUser.getUsername());
            throw new UserCreationFailedException(new WeakPasswordException("Weak Password: " + dtoUser.getUsername()));
        }
        if(userRepository.existsByUsername(dtoUser.getUsername())) {
            logger.warn("User already exists: {}", dtoUser.getUsername());
            throw new UserCreationFailedException(new UserAlreadyExistsException("User already exists: " + dtoUser.getUsername()));
        }
        UserEntity userEntity = userMapper.toEntity(dtoUser);
        userEntity.setPassword(passwordEncoder.encode(dtoUser.getPassword()));

        logger.info("creating user: {}", dtoUser.getId());

        UserEntity user;
        try {
            user = userRepository.save(userEntity);
            logger.info("User created successfully id={}", user.getUsername());
        } catch (RepositoryException ex) {
            logger.error("error creating user: {}", dtoUser.getId(), ex);
            throw new UserCreationFailedException("failed to create user " + dtoUser.getUsername(), ex);
        }
        return userMapper.toDto(user);
    }

    public void deleteUser(Long userId) {

        if (userId == null) {
            logger.warn("deleteUser called with null userId");
            throw new ValidationException("userId is required");
        }

        logger.info("deleting user: {}", userId);

        try {
            userRepository.deleteById(userId);
            logger.info("User deleted id={}", userId);
        } catch (UserNotFoundException ex) {
            logger.warn("could not delete user: user not found: {}", userId, ex);
            throw new UserDeletionFailedException("Cannot delete: user not found (id=" + userId + ")", ex);
        } catch (RepositoryException ex) {
            logger.error("could not delete user: repo failure: {}", userId, ex);
            throw new UserDeletionFailedException("Cannot delete: repository failure for user " + userId, ex);
        }
    }
}