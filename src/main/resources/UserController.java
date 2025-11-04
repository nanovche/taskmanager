package com.example.taskmanager.controller;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.exception.ApiErrorCode;
import com.example.taskmanager.exception.UserCreationException;
import com.example.taskmanager.exception.UserAlreadyExistsException;
import com.example.taskmanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    //or throw not found 404
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.fetchUser(userId));
    }

    @PutMapping("/{userId}/changePassword")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserDTO user){
        userService.changePassword(user.getPassword(), user.getUsername());
        return ResponseEntity.ok(200);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
        UserDTO created = userService.createUser(user);
//        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_ALREADY_EXISTS.toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Map<String, String>> handleUserCreationFailure(UserCreationException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_CREATION_FAILED.toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

/*    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchUser(NoSuchUserException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.NO_SUCH_USER.toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }*/
}