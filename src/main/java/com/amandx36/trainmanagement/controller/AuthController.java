package com.amandx36.trainmanagement.controller;


import com.amandx36.trainmanagement.services.AuthService;
import com.amandx36.trainmanagement.dto.reponse.AuthResponse;
import com.amandx36.trainmanagement.dto.reponse.RegisterResponse;
import com.amandx36.trainmanagement.dto.request.LoginRequest;
import com.amandx36.trainmanagement.dto.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private   AuthService authService ;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
      RegisterResponse registerResponse = authService.register(request);
      return ResponseEntity.ok(registerResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@Valid @RequestBody LoginRequest loginRequest){
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }


}
