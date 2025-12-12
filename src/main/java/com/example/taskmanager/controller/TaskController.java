package com.example.taskmanager.controller;

import com.example.taskmanager.dto.SummaryDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService svc;

    public TaskController(TaskService svc) { this.svc = svc; }

    @GetMapping
    public List<Task> myTasks(@AuthenticationPrincipal UserDetails user) {
        return svc.getAllByUsername(user.getUsername());
    }

    @PostMapping
    public Task create(@AuthenticationPrincipal UserDetails user, @RequestBody Task t) {
        return svc.create(user.getUsername(), t);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user,
            @RequestBody Task updated) {

        return svc.updateTask(user.getUsername(), id, updated);
    }

    @GetMapping("/summary")
    public SummaryDTO summary(@AuthenticationPrincipal UserDetails user) {
        return svc.getSummary(user.getUsername());
    }

    @GetMapping("/search")
    public List<Task> search(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam String keyword) {

        return svc.searchTasks(user.getUsername(), keyword);
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        svc.delete(id);
    }
}
