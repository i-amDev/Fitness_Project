package com.fitnessProject.activity_service.service;

import com.fitnessProject.activity_service.dto.ActivityRequest;
import com.fitnessProject.activity_service.dto.ActivityResponse;
import com.fitnessProject.activity_service.entity.Activity;
import com.fitnessProject.activity_service.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ModelMapper modelMapper;

    private final UserValidationService userValidationService;

    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

//        Activity entity = modelMapper.map(activityRequest, Activity.class);
//         Not using the model mapper to map activityRequest to activity class because the createdAt
//         field was not working properly because it was already defaulted with the value null.

        boolean isValidUser = userValidationService.validateUser(activityRequest.getUserId());

        if (!isValidUser) {
            throw new RuntimeException("Invalid user with userId " + activityRequest.getUserId());
        }

        Activity entity = Activity.builder()
                .userId(activityRequest.getUserId())
                .activityType(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .build();

        Activity savedEntity = activityRepository.save(entity);

        try{
            kafkaTemplate.send(topicName, savedEntity.getUserId(), savedEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return modelMapper.map(savedEntity, ActivityResponse.class);
    }

}
