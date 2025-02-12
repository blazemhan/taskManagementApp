package com.blazemhan.taskmanagerapp.service;

import com.blazemhan.taskmanagerapp.model.Task;
import com.blazemhan.taskmanagerapp.model.TaskStatus;
import com.blazemhan.taskmanagerapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {


    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }
    public Task createTask(Task task) {
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task completeTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new RuntimeException("Task Not Found!"));

            task.setStatus(TaskStatus.COMPLETED);
            task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);

    }

    public Task approveTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getStatus() != TaskStatus.COMPLETED) {
            throw new RuntimeException("Only completed tasks can be approved or rejected.");
        }

        task.setStatus(TaskStatus.APPROVED);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task rejectTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getStatus() != TaskStatus.COMPLETED) {
            throw new RuntimeException("Only completed tasks can be approved or rejected.");
        }

        task.setStatus(TaskStatus.REJECTED);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);

    }


    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    public List<Task> getTasksByManager(Long managerId) {
        return taskRepository.findByCreatedById(managerId);
    }
}
