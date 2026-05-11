package com.ecofriendly.api.controller;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecofriendly.api.dto.common.ApiResponse;
import com.ecofriendly.api.dto.dashboard.DashboardSummary;
import com.ecofriendly.api.model.User;
import com.ecofriendly.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummary>> getSummary(Authentication authentication) {
        // Obtener email del usuario autenticado desde el JWT
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Construir respuesta con datos REALES del usuario
        DashboardSummary summary = DashboardSummary.builder()
            .userName(user.getNombre() + " " + user.getApellido())  // ← Nombre real de la BD
            .userLevel(1)  // TODO: Calcular nivel real basado en puntos
            .institutionRank(1)  // TODO: Calcular ranking real
            .totalInstitutionUsers((int) userRepository.count())  // ← Total real de usuarios
            .stats(Arrays.asList(
                DashboardSummary.Stat.builder()
                    .id("total-points")
                    .value(0)  // TODO: Calcular puntos reales
                    .label("Puntos Totales")
                    .trend("+0%")
                    .trendType("up")
                    .icon("trophy")
                    .color("green")
                    .build()
            ))
            .recentActivity(Arrays.asList())  // ← Vacío por ahora, luego llenar con BD
            .environmentalImpact(Arrays.asList())  // ← Vacío por ahora
            .build();

        ApiResponse<DashboardSummary> response = ApiResponse.<DashboardSummary>builder()
            .success(true)
            .timestamp(Instant.now())
            .data(summary)
            .build();

        return ResponseEntity.ok(response);
    }
}