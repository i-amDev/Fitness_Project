package com.fitnessProject.user_service.service;

import com.fitnessProject.user_service.dto.RegisterRequest;
import com.fitnessProject.user_service.dto.UserResponse;
import com.fitnessProject.user_service.entity.User;
import com.fitnessProject.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserResponse registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
//            throw new RuntimeException("User already exist with the given email!!");
            User existingUser = userRepository.findByEmail(registerRequest.getEmail());
            return modelMapper.map(existingUser, UserResponse.class);
        }

        User entity = modelMapper.map(registerRequest, User.class);

        User savedEntity = userRepository.save(entity);

        return modelMapper.map(savedEntity, UserResponse.class);
    }

    public UserResponse getUserProfile(String userId) {

        User userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(userEntity, UserResponse.class);
    }

    public Boolean existsByUserId(String userId) {
        log.info("Calling user service for {}", userId);
//        return userRepository.existsById(userId);
        return userRepository.existsByKeyCloakId(userId);
    }

}
