package com.fitnessProject.activity_service.dto;

import com.fitnessProject.activity_service.types.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponse {

    private String id;

    private String userId;

    private ActivityType activityType;

    private Integer duration;

    private Integer caloriesBurned;

    private LocalDateTime startTime;

    private Map<String, Object> additionalMetrics;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
