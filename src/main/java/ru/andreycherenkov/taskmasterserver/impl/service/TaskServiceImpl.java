package ru.andreycherenkov.taskmasterserver.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoUpdate;
import ru.andreycherenkov.taskmasterserver.api.service.TaskService;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;
import ru.andreycherenkov.taskmasterserver.db.repository.TaskRepository;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.mapper.TaskMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    @Override
    @Transactional
    public ResponseEntity<List<TaskDtoResponse>> getTasks(String userId) {
        UUID uuidUserId;
        try {
            uuidUserId = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        if (!userRepository.existsById(uuidUserId)) {
            return ResponseEntity.notFound().build();
        }

        List<TaskDtoResponse> responseList = taskRepository.findAllTasksByUserId(uuidUserId)
                .stream()
                .map(taskMapper::taskToTaskDtoResponse)
                .toList();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseList);
    }

    @Override
    public ResponseEntity<TaskDtoResponse> getTask(String taskId) {
        TaskDtoResponse dtoResponse = taskMapper.taskToTaskDtoResponse(
                taskRepository.findById(UUID.fromString(taskId))
                        .orElseThrow(() -> new NotFoundException("Task not found"))
        );
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtoResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<TaskDtoResponse> createTask(TaskDtoCreate taskDtoCreate) {
        UUID userId = taskDtoCreate.getUserId();

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest().build();
        }

        setDefaultStatusIfNull(taskDtoCreate);
        Task task = taskRepository.save(taskMapper.toTask(taskDtoCreate));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskMapper.taskToTaskDtoResponse(task));
    }

    @Override
    public ResponseEntity<TaskDtoResponse> updateTask(TaskDtoUpdate taskDtoUpdate) {
        Task existingTask = taskRepository.findById(taskDtoUpdate.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found"));

        taskMapper.updateTaskFromDto(taskDtoUpdate, existingTask);

        Task task = taskRepository.save(existingTask);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskMapper.taskToTaskDtoResponse(task));
    }

    private void setDefaultStatusIfNull(TaskDtoCreate taskDtoCreate) {
        if (taskDtoCreate.getStatus() == null) {
            taskDtoCreate.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteTask(String taskId) {
        UUID uuid = UUID.fromString(taskId);
        if (!taskRepository.existsById(uuid)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(uuid);
        return ResponseEntity.noContent().build();
    }
}
