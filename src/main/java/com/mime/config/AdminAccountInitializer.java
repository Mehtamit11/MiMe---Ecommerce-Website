package com.mime.config;

import com.mime.model.User;
import com.mime.repository.UserRepository;
import com.mime.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;

    public AdminAccountInitializer(UserRepository userRepository,
                                   UserService userService,
                                   @Value("${app.admin.name:Admin}") String adminName,
                                   @Value("${app.admin.email:admin@mime.com}") String adminEmail,
                                   @Value("${app.admin.password:admin123}") String adminPassword) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        User admin = new User();
        admin.setName(adminName);
        admin.setEmail(adminEmail);
        admin.setPassword(adminPassword);

        userService.createUser(admin, true);
        System.out.printf("Created default admin user: %s%n", adminEmail);
    }
}
