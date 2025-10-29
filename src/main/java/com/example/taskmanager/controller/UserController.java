package com.example.taskmanager.controller;

import com.example.taskmanager.dto.User;
import com.example.taskmanager.exception.UserAlreadyExistsException;
import com.example.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){;
        return ResponseEntity.ok(userService.fetchUser(userId));
    }

    @PutMapping("/{userId}/changePassword")
    public ResponseEntity<?> updateUserPassword(@RequestBody User user){
        userService.changePassword(user.getPassword(), user.getUsername());
        return ResponseEntity.ok(200);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok(201);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserExists(UserAlreadyExistsException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", "User already exists");
        body.put("message", ex.getMessage());
        body.put("path", "/api/users");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}