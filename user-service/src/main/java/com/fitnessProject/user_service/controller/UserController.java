package com.fitnessProject.user_service.controller;

import com.fitnessProject.user_service.dto.RegisterRequest;
import com.fitnessProject.user_service.dto.UserResponse;
import com.fitnessProject.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser (@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile (@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUser (@PathVariable String userId) {
        return ResponseEntity.ok(userService.existsByUserId(userId));
    }
}
