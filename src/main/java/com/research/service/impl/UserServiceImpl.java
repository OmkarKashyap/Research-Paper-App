package com.research.service.impl;

import com.research.model.User;
import java.util.List;
import com.research.model.Paper;
import com.research.repository.UserRepository;
import com.research.repository.PaperRepository;
import com.research.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaperRepository paperRepository; // Inject paper repo

     @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PaperRepository paperRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        return null; // Return null if authentication fails
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateProfile(User user) {
        return userRepository.save(user);
    }
}