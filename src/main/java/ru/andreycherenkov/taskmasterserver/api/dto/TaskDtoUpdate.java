package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskPriority;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//todo add @Schema
public class TaskDtoUpdate {

    @JsonProperty("task_id")
    private UUID taskId;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("priority")
    private TaskPriority priority;

    @JsonProperty("task_status")
    private TaskStatus status;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("due_date")
    private LocalDate dueDate;

}
