package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.exception.*;
import com.example.taskmanager.mappers.UserMapper;
import com.example.taskmanager.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO fetchUser(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userid is null");
        }

        try {
            return userRepository.findById(userId)
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new UserFetchingFailedException("Failed to fetch user: " + userId, new NoSuchUserException("User not found: " + userId)));
        } catch (RepositoryException ex) {
            throw new UserFetchingFailedException("Failed to fetch user " + userId, ex);
        }
    }

    public UserDTO createUser(UserDTO dtoUser) {

        if (dtoUser == null || StringUtils.isEmpty(dtoUser.getUsername()) || StringUtils.isEmpty(dtoUser.getPassword())) {
            throw new IllegalArgumentException("insufficient user data");
        }

        if (dtoUser.getAuthorities() == null || dtoUser.getAuthorities().length == 0) {
            throw new IllegalArgumentException("no authorities data");
        }

        if(userRepository.existsByUsername(dtoUser.getUsername())) {
            throw new UserAlreadyExistsException(String.format("User %s already exists", dtoUser.getUsername()));
        } else {
            if(checkPasswordStrength(dtoUser.getPassword())) {

                UserEntity userEntity = userMapper.toEntity(dtoUser);
                userEntity.setPassword(passwordEncoder.encode(dtoUser.getPassword()));

                UserEntity user;
                try {
                    user = userRepository.save(userEntity);
                } catch (Exception e) {
                    throw new UserCreationFailedException("failed to create user", e);
                }
                return userMapper.toDto(user);
            } else {
                throw new WeakPasswordException("weak password");
            }
        }
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

    private boolean checkPasswordStrength(String password){
        return true;
    }
}