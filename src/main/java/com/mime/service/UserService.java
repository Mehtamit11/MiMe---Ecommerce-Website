package com.mime.service;

import com.mime.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);

    User createUser(User user, boolean adminRole);

    List<User> getAllUsers();
}
