package ru.andreycherenkov.taskmasterserver.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;
import ru.andreycherenkov.taskmasterserver.db.TaskStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDtoCreate {

    @Schema(description = "Название задачи",
            example = OpenApiConstants.TASK_TITLE,
            requiredMode = Schema.RequiredMode.REQUIRED) //todo продумать функц. требования
    private String title;

    @Schema(description = "Описание задачи",
            example = OpenApiConstants.TASK_DESCRIPTION,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(description = "Статус задачи",
            example = OpenApiConstants.TASK_STATUS,
            requiredMode = Schema.RequiredMode.REQUIRED) //todo ??
    private TaskStatus completed;
    private TaskStatus status;

}
