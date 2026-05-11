package com.ecofriendly.api.dto.dashboard;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummary {
    private String userName;
    private int userLevel;
    private int institutionRank;
    private int totalInstitutionUsers;
    private List<Stat> stats;
    private List<Activity> recentActivity;
    private List<Impact> environmentalImpact;

    @Data @Builder
    public static class Stat {
        private String id;
        private int value;
        private String label;
        private String trend;
        private String trendType;
        private String icon;
        private String color;
    }

    @Data @Builder
    public static class Activity {
        private String id;
        private String material;
        private String materialType;
        private double weight;
        private String weightUnit;
        private String date;
        private int points;
        private String status;
    }

    @Data @Builder
    public static class Impact {
        private String material;
        private double amount;
        private String unit;
        private double percentage;
        private String color;
    }
}