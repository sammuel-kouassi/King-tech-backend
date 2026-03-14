package com.example.kingtechbackend.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String motDePasse;
}