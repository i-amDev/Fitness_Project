package com.fitnessProject.user_service.service;

import com.fitnessProject.user_service.dto.RegisterRequest;
import com.fitnessProject.user_service.dto.UserResponse;
import com.fitnessProject.user_service.entity.User;
import com.fitnessProject.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserResponse registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("User already exist with the given email!!");
        }

        User entity = modelMapper.map(registerRequest, User.class);

        User savedEntity = userRepository.save(entity);

        return modelMapper.map(savedEntity, UserResponse.class);
    }
}
