package ru.andreycherenkov.taskmasterserver.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;

import java.util.List;

@Tag(name = "TaskController", description = "Endpoints to work with tasks")
@RequestMapping("api/v1/tasks")
public interface TaskController {

    //todo write DTO classes
    @GetMapping
    ResponseEntity<List<TaskDtoResponse>> getTasks(@RequestHeader String userId);

    @GetMapping("/{taskId}")
    ResponseEntity<TaskDtoResponse> getTask(@PathVariable String taskId);

    @PostMapping
    ResponseEntity<TaskDtoResponse> createTask(@RequestBody TaskDtoCreate taskDtoCreate); //todo продумать ответ

    @DeleteMapping("/{taskId}")
    ResponseEntity<TaskDtoResponse> deleteTask(@PathVariable String taskId);



}
