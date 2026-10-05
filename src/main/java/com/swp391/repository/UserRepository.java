package com.swp391.repository;

import com.swp391.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
