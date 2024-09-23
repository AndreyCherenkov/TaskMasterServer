package ru.andreycherenkov.taskmasterserver.impl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.taskmasterserver.api.controller.TaskController;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;

import java.util.List;

@RestController
public class TaskControllerImpl implements TaskController {

    @Override
    public ResponseEntity<List<TaskDtoResponse>> getTasks(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<TaskDtoResponse> getTask(String taskId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<TaskDtoResponse> createTask(TaskDtoCreate taskDtoCreate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<TaskDtoResponse> deleteTask(String taskId) {
        throw new UnsupportedOperationException();
    }
}
