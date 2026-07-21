package com.amandx36.trainmanagement.config;

import com.amandx36.trainmanagement.dto.request.LoginRequest;
import com.amandx36.trainmanagement.entity.User;
import com.amandx36.trainmanagement.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;



@AllArgsConstructor
public class PasswordMatcher {
    final AuthRepository authRepository ;
    final PasswordEncoder passwordEncoder;

   public boolean isValidPassword(LoginRequest loginRequest){
       Optional<User> user = authRepository.findByEmail(loginRequest.getEmail());
       String hasshedPassword = user.get().getPassword();
       return passwordEncoder.matches(hasshedPassword,loginRequest.getPassword());

   }


}
