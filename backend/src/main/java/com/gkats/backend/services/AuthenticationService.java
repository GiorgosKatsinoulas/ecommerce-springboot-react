package com.gkats.backend.services;

import com.gkats.backend.config.JwtService;
import com.gkats.backend.model.Role;
import com.gkats.backend.model.User;
import com.gkats.backend.repository.UserRepository;
import com.gkats.backend.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type Authentication service.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register api response.
     *
     * @param request the request
     * @return the api response
     */
    public ApiResponse<Object> register(RegisterRequest request) {
        // Check if user already exists by email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ApiResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(ApiMessages.INTERNAL_ERROR)
                    .data("User already exists with this email")
                    .build();
        }
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();


        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(ApiMessages.SUCCESS)
                .data(jwtToken)
                .build();
    }

    /**
     * Login api response.
     *
     * @param request the request
     * @return the api response
     */
    public ApiResponse<Object> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(ApiMessages.SUCCESS)
                .data(jwtToken)
                .build();
    }
}
