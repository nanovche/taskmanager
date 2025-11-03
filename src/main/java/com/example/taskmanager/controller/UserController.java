package com.example.taskmanager.controller;

import com.example.taskmanager.dto.User;
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
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){;
        return ResponseEntity.ok(userService.fetchUser(userId));
    }

    @PutMapping("/{userId}/changePassword")
    public ResponseEntity<?> updateUserPassword(@RequestBody User user){
        userService.changePassword(user.getPassword(), user.getUsername());
        return ResponseEntity.ok(200);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok().build();
//        return ResponseEntity.created(201);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_ALREADY_EXISTS.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Map<String, String>> handleUserCreation(UserCreationException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_CREATION_FAILED.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchUser(NoSuchUserException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.NO_SUCH_USER.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}