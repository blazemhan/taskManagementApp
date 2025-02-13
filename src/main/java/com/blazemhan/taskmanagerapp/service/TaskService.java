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
    private final NotificationService notificationService;

    public TaskService(TaskRepository taskRepository, NotificationService notificationService) {

        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
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
         taskRepository.save(task);

        // Send Email Notification
        String email = task.getAssignedTo().getEmail();
        String subject = "✅ Task Approved: " + task.getTitle();
        String message = "Dear " + task.getAssignedTo().getName() + ",<br><br>" +
                "Your task <strong>'" + task.getTitle() + "'</strong> has been <strong>APPROVED</strong> by the manager.<br>" +
                "You can now proceed with the next steps.<br><br>" +
                "Best Regards,<br>Task Management System";

        notificationService.sendTaskNotification(email, subject, message);

        return task;
    }

    public Task rejectTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getStatus() != TaskStatus.COMPLETED) {
            throw new RuntimeException("Only completed tasks can be approved or rejected.");
        }

        task.setStatus(TaskStatus.REJECTED);
        task.setUpdatedAt(LocalDateTime.now());
         taskRepository.save(task);
        String email = task.getAssignedTo().getEmail();
        String subject = "❌ Task Rejected: " + task.getTitle();
        String message = "Dear " + task.getAssignedTo().getName() + ",<br><br>" +
                "Your task <strong>'" + task.getTitle() + "'</strong> has been <strong>REJECTED</strong> by the manager.<br>" +
                "Please review the feedback and resubmit if necessary.<br><br>" +
                "Best Regards,<br>Task Management System";

        notificationService.sendTaskNotification(email, subject, message);

        return task;

    }


    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    public List<Task> getTasksByManager(Long managerId) {
        return taskRepository.findByCreatedById(managerId);
    }
}
