package com.amandx36.trainmanagement.entity;


import com.amandx36.trainmanagement.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Table (name = "users")
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String email ;
    private  String Password ;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDateTime createdAt;
    private Boolean active;

}
