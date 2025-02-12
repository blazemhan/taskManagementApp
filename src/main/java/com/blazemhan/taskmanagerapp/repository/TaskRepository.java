package com.blazemhan.taskmanagerapp.repository;

import com.blazemhan.taskmanagerapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedToId(Long userId);
    List<Task> findByCreatedById(Long managerId);
}