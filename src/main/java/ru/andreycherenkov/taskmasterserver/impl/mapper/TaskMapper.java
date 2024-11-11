package ru.andreycherenkov.taskmasterserver.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoUpdate;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {
                LocalDate.class,
                LocalDateTime.class,
                UUID.class
        }
)
public interface TaskMapper {

    @Mapping(target = "taskId", source = "id")
    TaskDtoResponse taskToTaskDtoResponse(Task task);

    @Mapping(target = "startDate", expression = "java(LocalDate.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Task toTask(TaskDtoCreate taskDtoCreate);

    @Mapping(target = "id", source = "taskId")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Task toTask(TaskDtoUpdate taskDtoUpdate);

    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    void updateTaskFromDto(TaskDtoUpdate taskDtoUpdate, @MappingTarget Task task);

}
