package com.swp391.security;

public interface PasswordHasher {
    boolean matches(String rawPassword, String passwordHash);
}
