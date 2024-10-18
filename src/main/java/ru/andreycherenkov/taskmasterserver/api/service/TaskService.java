package ru.andreycherenkov.taskmasterserver.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;

import java.util.List;

public interface TaskService {

    ResponseEntity<List<TaskDtoResponse>> getTasks(@RequestHeader String userId);

    ResponseEntity<TaskDtoResponse> getTask(@PathVariable String taskId);

    ResponseEntity<TaskDtoResponse> createTask(@RequestBody TaskDtoCreate taskDtoCreate);

    ResponseEntity<TaskDtoResponse> deleteTask(@PathVariable String taskId);

}
