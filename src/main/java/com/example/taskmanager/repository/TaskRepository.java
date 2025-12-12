package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwner(UserEntity owner);
    List<Task> findByTitleContainingIgnoreCase(String keyword);
    long countByOwnerUsername(String username);
    long countByOwnerUsernameAndCompleted(String username, boolean completed);
}
