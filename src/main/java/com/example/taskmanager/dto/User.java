package com.example.taskmanager.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private String username;
    private String password;
    private String[] authorities;
}