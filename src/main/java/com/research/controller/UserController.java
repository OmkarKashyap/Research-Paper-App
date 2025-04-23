package com.research.controller;
import java.util.List;
import com.research.model.Paper;

import com.research.model.LoginRequest;
import com.research.model.User;
import com.research.model.Paper;
import com.research.service.JwtService;
import com.research.service.UserService;
import com.research.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

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
@GetMapping("{userId}/liked-papers")
public ResponseEntity<Map<String, Object>> getLikedPapersByUser(@PathVariable int userId) {
    Map<String, Object> likedPapers = paperService.getLikedPapers(userId);
    System.out.println(likedPapers);
    return ResponseEntity.ok(likedPapers);
}
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            // Generate a token (e.g., JWT)
            String token = jwtService.generateToken(user);

            // Return the user details along with the token
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("token", token); // Include the token here

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
        // public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        // Optional<User> existingUser = userService.findByEmail(user.getEmail());

        // if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(),
        // existingUser.get().getPassword())) {
        // return ResponseEntity.ok(existingUser.get());
        // } else {
        // return ResponseEntity.badRequest().body("Invalid credentials. Please try
        // again.");
        // }
    }
}