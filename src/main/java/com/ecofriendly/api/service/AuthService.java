package com.ecofriendly.api.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecofriendly.api.dto.auth.LoginRequest;
import com.ecofriendly.api.dto.auth.LoginResponse;
import com.ecofriendly.api.dto.auth.RegisterRequest;
import com.ecofriendly.api.dto.common.ApiResponse;
import com.ecofriendly.api.model.User;
import com.ecofriendly.api.repository.UserRepository;
import com.ecofriendly.api.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public ApiResponse<?> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.builder()
                .success(false)
                .timestamp(Instant.now())
                .message("El email ya está registrado")
                .build();
        }
        if (userRepository.existsByCedula(request.getCedula())) {
            return ApiResponse.builder()
                .success(false)
                .timestamp(Instant.now())
                .message("La cédula ya está registrada")
                .build();
        }

        User user = User.builder()
            .cedula(request.getCedula())
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .genero(request.getGenero())
            .email(request.getEmail())
            .carrera(request.getCarrera())
            .password(passwordEncoder.encode(request.getPassword()))
            .rol(User.Role.ESTUDIANTE)
            .build();

        userRepository.save(user);

        return ApiResponse.builder()
            .success(true)
            .timestamp(Instant.now())
            .message("Usuario registrado exitosamente")
            .build();
    }

    public ApiResponse<LoginResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );

        String token = tokenProvider.generateToken(authentication);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        LoginResponse response = LoginResponse.builder()
            .token(token)
            .user(LoginResponse.UserDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .rol(user.getRol().name().toLowerCase())
                .build())
            .build();

        return ApiResponse.<LoginResponse>builder()
            .success(true)
            .timestamp(Instant.now())
            .data(response)
            .build();
    }
}