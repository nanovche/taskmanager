package com.example.taskmanager.service;

import com.example.taskmanager.dto.User;
import com.example.taskmanager.exception.NoSuchUserException;
import com.example.taskmanager.exception.UserCreationException;
import com.example.taskmanager.exception.UserAlreadyExistsException;
import com.example.taskmanager.exception.WeakPasswordException;
import com.example.taskmanager.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(JdbcUserDetailsManager jdbcUserDetailsManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User fetchUser(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userid is null");
        }
        User user;
        try {
            user = userRepository.fetchUserById(userId);
        } catch (DataAccessException ex) {
            throw new NoSuchUserException(String.format("user with id %s does not exist", userId), ex);
        }
        return user;
    }

    public User createUser(User dtoUser) {

        if (dtoUser == null || StringUtils.isEmpty(dtoUser.getUsername()) || StringUtils.isEmpty(dtoUser.getPassword())) {
            throw new IllegalArgumentException("insufficient user data");
        }

        if (dtoUser.getAuthorities() == null || dtoUser.getAuthorities().length == 0) {
            throw new IllegalArgumentException("no authorities data");
        }

        if(jdbcUserDetailsManager.userExists(dtoUser.getUsername())) {
            throw new UserAlreadyExistsException(String.format("User %s already exists", dtoUser.getUsername()));
        } else {
            if(checkPasswordStrength(dtoUser.getPassword())) {
                UserDetails newUser = org.springframework.security.core.userdetails.User
                        .withUsername(dtoUser.getUsername())
                        .password(passwordEncoder.encode(dtoUser.getPassword()))
                        .authorities(Arrays.stream(dtoUser.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                        .build();

                try {
                    jdbcUserDetailsManager.createUser(newUser);
                } catch (DataAccessException ex) {
                    throw new UserCreationException("failed to create user", ex);
                }
                return dtoUser;
            } else {
                throw new WeakPasswordException("weak password");
            }
        }
    }

    public void changePassword(String newPassword, String username){

        if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("insufficient data");
        }

        if(checkPasswordStrength(newPassword)){
//            jdbcUserDetailsManager.changePassword();
//            userRepository.updateUserPassword(newPassword, username);
        } else {
            throw new WeakPasswordException("weak password");
        }
    }

    public void updateUser(User userDto) {

        //double check validation?
        if (userDto == null || StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword())) {
            throw new IllegalArgumentException("insufficient user data");
        }

        if (userDto.getAuthorities() == null || userDto.getAuthorities().length == 0) {
            throw new IllegalArgumentException("no authorities data");
        }

        UserDetails existingDbUser = jdbcUserDetailsManager.loadUserByUsername(userDto.getUsername());

//        needsUpdate()?
        //check if needs update -> check if different from one in db?
        UserDetails updatedUser = org.springframework.security.core.userdetails.User
                .withUsername(userDto.getUsername())
                .passwordEncoder(passwordEncoder::encode)
                .authorities(Arrays.stream(userDto.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .build();

        jdbcUserDetailsManager.updateUser(updatedUser);
    }

    public void deleteUser(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }

        String username;
        try {
            username = userRepository.fetchUsernameById(userId);
        } catch (DataAccessException ex) {
            throw new NoSuchUserException(String.format("user with id %s does not exist - no user is deleted", userId), ex);
        }
        jdbcUserDetailsManager.deleteUser(username);
    }

    private boolean checkPasswordStrength(String password){
        return true;
    }
}