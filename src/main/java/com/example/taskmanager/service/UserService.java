package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.exception.*;
import com.example.taskmanager.mappers.UserMapper;
import com.example.taskmanager.repository.UserRepository;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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
            throw new IllegalArgumentException("userid is null");
        }

        try {
            return userRepository.findById(userId)
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new UserFetchingFailedException("Failed to fetch user: " + userId, new UserNotFoundException("User not found: " + userId)));
        } catch (RepositoryException ex) {
            throw new UserFetchingFailedException("Failed to fetch user " + userId, ex);
        }
    }

    public UserDTO createUser(UserDTO dtoUser) {

        validationService.validateUserInput(dtoUser);

        if(!validationService.checkPasswordStrength(dtoUser.getPassword())) {
            throw new UserCreationFailedException(new WeakPasswordException("Weak Password: " + dtoUser.getUsername()));
        }
        if(userRepository.existsByUsername(dtoUser.getUsername())) {
            throw new UserCreationFailedException(new UserAlreadyExistsException("User already exists: " + dtoUser.getUsername()));
        }
        UserEntity userEntity = userMapper.toEntity(dtoUser);
        userEntity.setPassword(passwordEncoder.encode(dtoUser.getPassword()));

        UserEntity user;
        try {
            user = userRepository.save(userEntity);
        } catch (RepositoryException ex) {
            throw new UserCreationFailedException("failed to create user " + dtoUser.getUsername(), ex);
        }
        return userMapper.toDto(user);
    }

    public void deleteUser(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }

        try {
            userRepository.deleteById(userId);
        } catch (UserNotFoundException ex) {
            throw new UserDeletionFailedException("Cannot delete: user not found (id=" + userId + ")", ex);
        } catch (RepositoryException ex) {
            throw new UserDeletionFailedException("Cannot delete: repository failure for user " + userId, ex);
        }
    }
}