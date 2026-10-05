package com.swp391.service;

import com.swp391.model.User;
import com.swp391.repository.UserRepository;
import com.swp391.security.PasswordHasher;
import com.swp391.security.TokenService;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordHasher passwordHasher, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    public String login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Email and password are required");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.active()) throw new IllegalStateException("User is inactive");
        if (!passwordHasher.matches(password, user.passwordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return tokenService.generate(user.id());
    }
}
