package com.fitnessProject.ai_service.service;

import com.fitnessProject.ai_service.model.Activity;
import com.fitnessProject.ai_service.model.Recommendation;
import com.fitnessProject.ai_service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;

    private final RecommendationRepository recommendationRepository;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(Activity activity) {
        log.info("Received activity for processing {}", activity.getUserId());
        Recommendation recommendation = activityAIService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }


}
