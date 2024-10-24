package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDtoResponse {

    //todo @Schema
    private UUID taskId;

    private UUID userId;

    @Schema(description = "Название задачи",
            example = OpenApiConstants.TASK_TITLE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Описание задачи",
            example = OpenApiConstants.TASK_DESCRIPTION,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Статус задачи",
            example = OpenApiConstants.TASK_STATUS,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private TaskStatus status;

    @Schema(description = "Дата завершения задачи.",
            example = OpenApiConstants.DUE_DATE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

}
