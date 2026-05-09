package com.ecofriendly.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private UserDto user;
    
    @Data
    @Builder
    public static class UserDto {
        private String id;
        private String email;
        private String nombre;
        private String apellido;
        private String rol;
    }
}