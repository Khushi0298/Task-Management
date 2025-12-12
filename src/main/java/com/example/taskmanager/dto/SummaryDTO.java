package com.example.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SummaryDTO {
    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
}
