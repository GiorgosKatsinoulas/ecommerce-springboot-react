package com.gkats.backend.utils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.gkats.backend.model.Role;
import com.gkats.backend.model.User;
import com.gkats.backend.repository.UserRepository;

import java.util.Optional;

/**
 * The type Admin user initializer.
 */
@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the admin user already exists
        Optional<User> admin = userRepository.findByEmail("admin@example.com");

        if (admin.isPresent()) {
            // admin user exists
            System.out.println("Admin user found.");
        } else {
            // admin user does not exist
            User adminUser = User.builder()
                    .firstname("Admin")
                    .lastname("User")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin")) // Use a secure password
                    .role(Role.ADMIN)  // Directly assign the ADMIN role from the enum
                    .build();
            userRepository.save(adminUser);
            System.out.println("Admin user not found.");
        }

    }
}
