package com.research.service;

import com.research.model.User;

import java.util.Optional;

public interface UserService {
    User authenticate(String email, String password);

    Optional<User> findByEmail(String email);

    User registerUser(User user);

    User updateProfile(User user);
}