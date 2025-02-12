package com.blazemhan.taskmanagerapp.repository;

import com.blazemhan.taskmanagerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

}