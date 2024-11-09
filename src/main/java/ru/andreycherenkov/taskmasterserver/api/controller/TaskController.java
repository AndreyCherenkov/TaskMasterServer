package ru.andreycherenkov.taskmasterserver.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoUpdate;

import java.util.List;
import java.util.UUID;

@Tag(name = "TaskController", description = "Endpoints to work with tasks")
@RequestMapping("api/v1/tasks")
public interface TaskController {

    @GetMapping
    ResponseEntity<List<TaskDtoResponse>> getTasks(@RequestHeader(name = "User-Id") String userId);

    @GetMapping("/{taskId}")
    ResponseEntity<TaskDtoResponse> getTask(@PathVariable String taskId);

    @PostMapping
    ResponseEntity<TaskDtoResponse> createTask(@RequestBody TaskDtoCreate taskDtoCreate); //todo продумать ответ

    @PutMapping
    ResponseEntity<TaskDtoResponse> updateTask(@RequestBody TaskDtoUpdate taskDtoUpdate);

    @DeleteMapping("/{taskId}")
    ResponseEntity<Void> deleteTask(@PathVariable String taskId);

}
