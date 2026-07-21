package com.amandx36.trainmanagement.dto.reponse;


import com.amandx36.trainmanagement.enums.UserRole;
import lombok.Data;

@Data
public class RegisterResponse {
    String email;
    String message;
    UserRole role ;
    String token ;
}
