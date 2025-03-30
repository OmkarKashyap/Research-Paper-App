package com.research.controller;

import com.research.model.User;
import com.research.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateProfile(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());

        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return ResponseEntity.ok(existingUser.get());
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials. Please try again.");
        }
    }
}