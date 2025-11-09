package com.example.taskmanager.service;

import com.example.taskmanager.domain.SecurityUser;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.repository.UserRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepositoryAdapter userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepositoryAdapter userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entityUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user %s not found", username)));

        return new SecurityUser(entityUser);
    }
}