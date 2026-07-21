package com.amandx36.trainmanagement.dto.reponse;


import lombok.Data;

@Data
public class AuthResponse {
    String token ;
    String email ;
    String role;
    String message ;
}
