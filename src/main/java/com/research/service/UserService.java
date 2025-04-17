package com.research.service;

import com.research.model.User;
import com.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateProfile(User user) {
        return userRepository.save(user);
    }

    public String getUserEmail(Long userId) {
        return userRepository.findById(userId)
                .map(User::getEmail)
                .orElse(null);
    }

    public String getUserPassword(Long userId) {
        return userRepository.findById(userId)
                .map(User::getPassword)
                .orElse(null);
    }
}
