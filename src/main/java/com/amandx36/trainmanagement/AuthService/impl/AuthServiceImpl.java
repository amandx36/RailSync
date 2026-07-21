package com.amandx36.trainmanagement.AuthService.impl;

import com.amandx36.trainmanagement.AuthService.AuthService;
import com.amandx36.trainmanagement.config.JwtGenerator;
import com.amandx36.trainmanagement.config.PasswordMatcher;
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
    private  final PasswordMatcher passwordMatcher ;

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
//        Receive email & password -> find user by email -> passwordEncoder -> generate jwt -> Return AuthResponse
        AuthResponse authResponse = new AuthResponse();

        if(!authRepository.existsByEmail(request.getEmail())){
           authResponse.setMessage("Register first");

            return  authResponse;
        }
       if(passwordMatcher.isValidPassword(request)){
        authResponse.setMessage("Success");
        java.util.Optional<User> user = authRepository.findByEmail(request.getEmail());
    String token = jwtGenerator.generateToken(user.get());
        authResponse.setToken(token);
        User newUser = user.get();
        authResponse.setRole(newUser.getRole().toString());
        authResponse.setEmail(newUser.getEmail());
        authResponse.setMessage("Success");



       }



        authResponse.setMessage("Error");

        return  authResponse;

    }
}