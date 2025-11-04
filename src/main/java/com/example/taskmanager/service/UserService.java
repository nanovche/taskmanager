package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.exception.NoSuchUserException;
import com.example.taskmanager.exception.WeakPasswordException;
import com.example.taskmanager.mappers.UserMapper;
import com.example.taskmanager.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchUserException(String.format("user with id %s does not exist", userId)));
    }

    /* when replace jdbc manager with jpa repo -> return obj with id from repo and add id to .created(uri) */
    public UserDTO createUser(UserDTO dtoUser) {

/*        if (dtoUser == null || StringUtils.isEmpty(dtoUser.getUsername()) || StringUtils.isEmpty(dtoUser.getPassword())) {
            throw new IllegalArgumentException("insufficient user data");
        }

        if (dtoUser.getAuthorities() == null || dtoUser.getAuthorities().length == 0) {
            throw new IllegalArgumentException("no authorities data");
        }

        if(jdbcUserDetailsManager.userExists(dtoUser.getUsername())) {
            throw new UserAlreadyExistsException(String.format("UserDTO %s already exists", dtoUser.getUsername()));
        } else {
            if(checkPasswordStrength(dtoUser.getPassword())) {
                UserDetails newUser = org.springframework.security.core.userdetails.UserDTO
                        .withUsername(dtoUser.getUsername())
                        .password(passwordEncoder.encode(dtoUser.getPassword()))
                        .authorities(Arrays.stream(dtoUser.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                        .build();

                try {
                    UserDTO user = userRepository.save(newUser);
//                    jdbcUserDetailsManager.createUser(newUser);
                } catch (DataAccessException ex) {
                    throw new UserCreationException("failed to create user", ex);
                }
                return dtoUser;
            } else {
                throw new WeakPasswordException("weak password");
            }
        }*/
        return null;
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

    public void deleteUser(Long userId) {

/*        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }

        String username;
        try {
            username = userRepository.fetchUsernameById(userId);
        } catch (DataAccessException ex) {
            throw new NoSuchUserException(String.format("user with id %s does not exist - no user is deleted", userId), ex);
        }
        jdbcUserDetailsManager.deleteUser(username);*/
    }

    private boolean checkPasswordStrength(String password){
        return true;
    }
}