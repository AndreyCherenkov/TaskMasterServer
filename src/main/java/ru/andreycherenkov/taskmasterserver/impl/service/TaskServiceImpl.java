package ru.andreycherenkov.taskmasterserver.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.service.TaskService;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;
import ru.andreycherenkov.taskmasterserver.db.repository.TaskRepository;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.mapper.TaskMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    @Override
    public ResponseEntity<List<TaskDtoResponse>> getTasks(String userId) {
        List<TaskDtoResponse> responseList = taskRepository.findAllTasksById(UUID.fromString(userId))
                .stream()
                .map(taskMapper::taskToTaskDtoResponse)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<TaskDtoResponse> getTask(String taskId) {
        TaskDtoResponse dtoResponse = taskMapper.taskToTaskDtoResponse(
                taskRepository.findById(UUID.fromString(taskId))
                        .orElseThrow(() -> new NotFoundException("Task not found"))
        );
        return ResponseEntity.ok(dtoResponse);
    }

    @Override
    public ResponseEntity<TaskDtoResponse> createTask(TaskDtoCreate taskDtoCreate) {
        if (taskDtoCreate.getStatus() == null) {
            taskDtoCreate.setStatus(TaskStatus.NEW);
        }

        Task task = taskRepository.save(taskMapper.toTask(taskDtoCreate));
        return ResponseEntity.ok(taskMapper.taskToTaskDtoResponse(task));
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
