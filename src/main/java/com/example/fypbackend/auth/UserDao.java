package com.example.fypbackend.auth;

import java.util.Optional;

public interface UserDao {
    Optional<User> selectByUsername(String username);
}
