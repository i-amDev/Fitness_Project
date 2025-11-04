package com.fitnessProject.user_service.repository;

import com.fitnessProject.user_service.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Boolean existsByKeyCloakId(String keyCloakId);

    User findByEmail(String email);
}
