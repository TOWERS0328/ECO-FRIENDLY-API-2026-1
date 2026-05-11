package com.ecofriendly.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecofriendly.api.dto.configuration.ConfigurationSummaryResponse;
import com.ecofriendly.api.model.User;
import com.ecofriendly.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final UserRepository userRepository;

    public ConfigurationSummaryResponse getSummary(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ConfigurationSummaryResponse.builder()

                .userProfile(
                        ConfigurationSummaryResponse.UserProfile.builder()
                                .name(user.getNombre() + " " + user.getApellido())
                                .email(user.getEmail())
                                .university("Unicolombo")
                                .career(user.getCarrera())
                                .profileImage(null)
                                .build()
                )

                .settings(
                        ConfigurationSummaryResponse.Settings.builder()
                                .theme("light")
                                .language("es")
                                .notifications(
                                        ConfigurationSummaryResponse.Notifications.builder()
                                                .recyclingApproved(true)
                                                .newRewards(true)
                                                .weeklyReport(false)
                                                .systemUpdates(true)
                                                .build()
                                )
                                .build()
                )

                .availableLanguages(
                        List.of(
                                ConfigurationSummaryResponse.LanguageOption.builder()
                                        .code("es")
                                        .name("Español")
                                        .flag("🇨🇴")
                                        .build(),

                                ConfigurationSummaryResponse.LanguageOption.builder()
                                        .code("en")
                                        .name("English")
                                        .flag("🇺🇸")
                                        .build()
                        )
                )

                .build();
    }
}