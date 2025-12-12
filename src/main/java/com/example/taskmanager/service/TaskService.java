package com.example.taskmanager.service;

import com.example.taskmanager.dto.SummaryDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public List<Task> getAllByUsername(String username) {
        UserEntity u = userRepo.findByUsername(username).orElseThrow();
        return taskRepo.findByOwner(u);
    }

    public Task create(String username, Task task) {
        UserEntity u = userRepo.findByUsername(username).orElseThrow();
        task.setOwner(u);
        return taskRepo.save(task);
    }

    public Task updateTask(String username, Long id, Task updated) {

        Task existing = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Ensure user can update only his own tasks
        if (!existing.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Not allowed");
        }

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());

        // Optional fields if you add priority/category later
        // existing.setPriority(updated.getPriority());

        return taskRepo.save(existing);
    }

    public SummaryDTO getSummary(String username) {

        long total = taskRepo.countByOwnerUsername(username);
        long completed = taskRepo.countByOwnerUsernameAndCompleted(username, true);
        long pending = taskRepo.countByOwnerUsernameAndCompleted(username, false);

        SummaryDTO dto = new SummaryDTO();
        dto.setTotalTasks(total);
        dto.setCompletedTasks(completed);
        dto.setPendingTasks(pending);

        return dto;
    }

    public List<Task> searchTasks(String username, String keyword) {

        List<Task> tasks = taskRepo.findByTitleContainingIgnoreCase(keyword);

        // Return only tasks of the logged-in user
        return tasks.stream()
                .filter(t -> t.getOwner().getUsername().equals(username))
                .toList();
    }

    public Optional<Task> findById(Long id) { return taskRepo.findById(id); }

    public void delete(Long id) { taskRepo.deleteById(id); }
}
