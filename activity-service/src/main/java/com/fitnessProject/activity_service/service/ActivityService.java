package com.fitnessProject.activity_service.service;

import com.fitnessProject.activity_service.dto.ActivityRequest;
import com.fitnessProject.activity_service.dto.ActivityResponse;
import com.fitnessProject.activity_service.entity.Activity;
import com.fitnessProject.activity_service.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ModelMapper modelMapper;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

//        Activity entity = modelMapper.map(activityRequest, Activity.class);
//         Not using the model mapper to map activityRequest to activity class because the createdAt
//         field was not working properly because it was already defaulted with the value null.

        Activity entity = Activity.builder()
                .userId(activityRequest.getUserId())
                .activityType(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .build();

        Activity savedEntity = activityRepository.save(entity);

        return modelMapper.map(savedEntity, ActivityResponse.class);
    }

}
