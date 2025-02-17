package com.blazemhan.taskmanagerapp.controller;

import com.blazemhan.taskmanagerapp.model.Task;
import com.blazemhan.taskmanagerapp.model.User;
import com.blazemhan.taskmanagerapp.repository.UserRepository;
import com.blazemhan.taskmanagerapp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @PostMapping("/manager/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User manager = userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found"));

        task.setCreatedBy(manager);
        return new ResponseEntity<>( taskService.createTask(task), HttpStatus.CREATED);



    }

    @GetMapping("/user/task-list")
    public ResponseEntity<List<Task>> getTasksByUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();
        return  ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @GetMapping("/manager/task-list")
    public ResponseEntity<List<Task>> getTasksByManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User manager = userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("Wrong Email.. Manager not found"));
        Long managerId = manager.getId();

        return ResponseEntity.ok(taskService.getTasksByManager(managerId));
    }
    @PutMapping("/user/complete-task/{taskId}")
    public ResponseEntity<Task> completeTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

    @PutMapping("/manager/approve-task/{taskId}")
    public ResponseEntity<Task> approveTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.approveTask(taskId));
    }

    @PutMapping("/manager/reject-task/{taskId}")
    public ResponseEntity<Task> rejectTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.rejectTask(taskId));
    }
}
