package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String roleAdmin);
}