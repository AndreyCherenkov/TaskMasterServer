package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskPriority;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDtoUpdate {

    @Schema(description = "UUID задачи",
            example = OpenApiConstants.TASK_UUID,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("task_id")
    private UUID taskId;

    @Schema(description = "UUID пользователя",
            example = OpenApiConstants.USER_UUID,
            requiredMode = Schema.RequiredMode.REQUIRED)
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

    @Schema(description = "Приоритет задачи",
            example = OpenApiConstants.TASK_PRIORITY,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("priority")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "Дата окончания задачи",
            example = OpenApiConstants.DUE_DATE,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

}
