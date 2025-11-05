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
//            return modelMapper.map(existingUser, UserResponse.class);
            UserResponse userResponse = new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setKeyCloakId(existingUser.getKeyCloakId());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());
            return userResponse;
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setKeyCloakId(registerRequest.getKeyCloakId());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setKeyCloakId(savedUser.getKeyCloakId());
        userResponse.setId(savedUser.getId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;

//        User entity = modelMapper.map(registerRequest, User.class);
//
//        User savedEntity = userRepository.save(entity);
//
//        return modelMapper.map(savedEntity, UserResponse.class);
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
