package com.example.restapinote.auth.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String name;
//    private int age;
}
