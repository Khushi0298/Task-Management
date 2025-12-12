package com.example.taskmanager.config;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadData(UserRepository userRepo, TaskRepository taskRepo) {
        return args -> {

            // Check if data already exists
            if (userRepo.count() == 0) {
                UserEntity u1 = new UserEntity();
                u1.setUsername("Khushi");
                u1.setPassword("pass");  // encode in real project
                u1.setRole("USER");

                userRepo.save(u1);

                Task t1 = new Task(null, "Meet", "Meet with Manager", false, u1);
                Task t2 = new Task(null, "Learn JWT", "Implement JWT Auth", false, u1);
                Task t3 = new Task(null, "Deploy", "Deploy code in prod", true, u1);
                Task t4 = new Task(null, "walk", "walk for 10 min", false, u1);
                Task t5 = new Task(null, "Drink", "Drink water", false, u1);

                taskRepo.save(t1);
                taskRepo.save(t2);
                taskRepo.save(t3);
                taskRepo.save(t4);
                taskRepo.save(t5);


                System.out.println("✨ Default data added on startup!");
            }
        };
    }
}
