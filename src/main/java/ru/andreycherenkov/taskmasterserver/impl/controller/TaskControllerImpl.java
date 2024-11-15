package ru.andreycherenkov.taskmasterserver.impl.controller;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.taskmasterserver.api.controller.TaskController;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoUpdate;
import ru.andreycherenkov.taskmasterserver.api.service.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
public class TaskControllerImpl implements TaskController {

    private TaskService taskService;

    @Override
    public ResponseEntity<List<TaskDtoResponse>> getTasks(String userId) {
        return taskService.getTasks(userId);
    }

    @Override
    public ResponseEntity<TaskDtoResponse> getTask(String taskId) {
        return taskService.getTask(taskId);
    }

    @Override
    public ResponseEntity<TaskDtoResponse> createTask(TaskDtoCreate taskDtoCreate) {
        var a = LoggerFactory.getLogger(TaskControllerImpl.class);
        a.info("CALL CREATE TASK", taskDtoCreate.getPriority());
        return taskService.createTask(taskDtoCreate);
    }

    @Override
    public ResponseEntity<TaskDtoResponse> updateTask(TaskDtoUpdate taskDtoUpdate) {
        return taskService.updateTask(taskDtoUpdate);
    }

    @Override
    public ResponseEntity<Void> deleteTask(String taskId) {
        return taskService.deleteTask(taskId);
    }
}
