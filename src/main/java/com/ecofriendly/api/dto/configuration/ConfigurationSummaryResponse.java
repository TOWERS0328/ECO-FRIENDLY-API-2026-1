package com.ecofriendly.api.dto.configuration;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigurationSummaryResponse {

    private UserProfile userProfile;
    private Settings settings;
    private List<LanguageOption> availableLanguages;

    @Data
    @Builder
    public static class UserProfile {
        private String name;
        private String email;
        private String university;
        private String career;
        private String profileImage;
    }

    @Data
    @Builder
    public static class Settings {
        private String theme;
        private String language;
        private Notifications notifications;
    }

    @Data
    @Builder
    public static class Notifications {
        private boolean recyclingApproved;
        private boolean newRewards;
        private boolean weeklyReport;
        private boolean systemUpdates;
    }

    @Data
    @Builder
    public static class LanguageOption {
        private String code;
        private String name;
        private String flag;
    }
}