package com.example.taskmanager.controller;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.exception.ApiErrorCode;
import com.example.taskmanager.exception.NoSuchUserException;
import com.example.taskmanager.exception.UserAlreadyExistsException;
import com.example.taskmanager.exception.UserCreationException;
import com.example.taskmanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId){;
        return ResponseEntity.ok(userService.fetchUser(userId));
    }

    /* when replace jdbc manager with jpa repo -> return obj with id from repo and add id to .created(uri) */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
        UserDTO userCreated = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(userCreated.getId())
                .toUri();

        return ResponseEntity.created(location).body(userCreated);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}