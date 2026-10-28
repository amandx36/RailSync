package com.amandx36.trainmanagement.services.impl;

import com.amandx36.trainmanagement.services.AuthService;
import com.amandx36.trainmanagement.config.JwtGenerator;
import com.amandx36.trainmanagement.dto.reponse.AuthResponse;
import com.amandx36.trainmanagement.dto.reponse.RegisterResponse;
import com.amandx36.trainmanagement.dto.request.LoginRequest;
import com.amandx36.trainmanagement.dto.request.RegisterRequest;
import com.amandx36.trainmanagement.entity.User;
import com.amandx36.trainmanagement.enums.UserRole;
import com.amandx36.trainmanagement.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;


    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (authRepository.existsByEmail(request.getEmail())) {

            RegisterResponse response = new RegisterResponse();
            response.setMessage("Email already registered");
            return response;
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setActive(true);

        authRepository.save(user);

        String token = jwtGenerator.generateToken(user);

        RegisterResponse response = new RegisterResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);
        response.setMessage("Registration successful");

        return response;
    }
    @Override
    public AuthResponse login(LoginRequest request) {

        AuthResponse response = new AuthResponse();

        Optional<User> optionalUser = authRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            response.setMessage("User not found");
            return response;
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.setMessage("Invalid password");
            return response;
        }

        String token = jwtGenerator.generateToken(user);

        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setToken(token);
        response.setMessage("Login successful");

        return response;
    }
}