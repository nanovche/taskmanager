package com.example.taskmanager;

import com.example.taskmanager.entity.Authority;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.repository.AuthorityRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (authorityRepository.count() == 0) {
            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.saveAll(List.of(admin, user));
        }

        if (userRepository.count() == 0) {
            Authority adminRole = authorityRepository.findByName("ROLE_ADMIN");
            Authority userRole = authorityRepository.findByName("ROLE_USER");

            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAuthorities(Set.of(adminRole, userRole));

            UserEntity normalUser = new UserEntity();
            normalUser.setUsername("john");
            normalUser.setPassword(passwordEncoder.encode("user123"));
            normalUser.setAuthorities(Set.of(userRole));

            userRepository.saveAll(List.of(admin, normalUser));
        }
    }
}