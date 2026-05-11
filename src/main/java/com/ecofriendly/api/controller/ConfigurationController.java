package com.ecofriendly.api.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecofriendly.api.dto.common.ApiResponse;
import com.ecofriendly.api.dto.configuration.ConfigurationSummaryResponse;
import com.ecofriendly.api.service.ConfigurationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/configuration")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ConfigurationSummaryResponse>> getSummary(
            Authentication authentication
    ) {

        String email = authentication.getName();

        ConfigurationSummaryResponse data =
                configurationService.getSummary(email);

        ApiResponse<ConfigurationSummaryResponse> response =
                ApiResponse.<ConfigurationSummaryResponse>builder()
                        .success(true)
                        .timestamp(Instant.now())
                        .data(data)
                        .build();

        return ResponseEntity.ok(response);
    }
}