package com.ecofriendly.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank private String cedula;
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    private String genero;
    @Email @NotBlank private String email;
    private String carrera;
    @NotBlank private String password;
}