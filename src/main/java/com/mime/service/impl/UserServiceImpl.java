package com.mime.service.impl;

import com.mime.model.Role;
import com.mime.model.User;
import com.mime.repository.RoleRepository;
import com.mime.repository.UserRepository;
import com.mime.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
public User registerUser(User user) {
        return createUser(user, false);
    }

    @Override
    public User createUser(User user, boolean adminRole) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email already exists: " + existing.getEmail());
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));

         Role role = adminRole
                ? roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")))
                : roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));
        
        user.setRoles(Set.of(role));
        user.setEnabled(true);

        return userRepository.save(user);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
