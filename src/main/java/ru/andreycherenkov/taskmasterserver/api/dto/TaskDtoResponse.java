package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskPriority;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString //todo delete
@NoArgsConstructor
@AllArgsConstructor
public class TaskDtoResponse {

    //todo @Schema
    @JsonProperty("task_id")
    private UUID taskId;

    @JsonProperty("user_id")
    private UUID userId;

    @Schema(description = "Название задачи",
            example = OpenApiConstants.TASK_TITLE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("title")
    private String title;

    @Schema(description = "Описание задачи",
            example = OpenApiConstants.TASK_DESCRIPTION,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("description")
    private String description;

    private TaskPriority priority;

    @Schema(description = "Статус задачи",
            example = OpenApiConstants.TASK_STATUS,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("task_status")
    private TaskStatus status;

    @Schema(description = "Дата начала задачи.",
            example = OpenApiConstants.DUE_DATE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("start_date")
    private LocalDateTime startDate;


    @Schema(description = "Дата завершения задачи.",
            example = OpenApiConstants.DUE_DATE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("due_date")
    private LocalDateTime dueDate;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
