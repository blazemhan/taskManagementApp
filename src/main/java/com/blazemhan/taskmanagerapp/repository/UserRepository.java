package com.blazemhan.taskmanagerapp.repository;

import com.blazemhan.taskmanagerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}