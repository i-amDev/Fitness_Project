package com.fitnessProject.activity_service.repository;

import com.fitnessProject.activity_service.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {


}
