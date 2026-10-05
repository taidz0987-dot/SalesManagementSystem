package com.swp391.model;

public record User(int id, String email, String passwordHash, boolean active) {}
