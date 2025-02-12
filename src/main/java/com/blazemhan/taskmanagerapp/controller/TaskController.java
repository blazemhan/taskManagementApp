package com.blazemhan.taskmanagerapp.controller;

import com.blazemhan.taskmanagerapp.model.Task;
import com.blazemhan.taskmanagerapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/manager/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId){
        return  ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Task>> getTasksByManager(@PathVariable Long managerId){
        return ResponseEntity.ok(taskService.getTasksByManager(managerId));
    }
    @PostMapping("/user/complete-task/{taskId}")
    public ResponseEntity<Task> completeTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

    @PostMapping("/manager/approve-task/{taskId}")
    public ResponseEntity<Task> approveTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.approveTask(taskId));
    }

    @PostMapping("/manager/reject-task/{taskId}")
    public ResponseEntity<Task> rejectTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.rejectTask(taskId));
    }
}
