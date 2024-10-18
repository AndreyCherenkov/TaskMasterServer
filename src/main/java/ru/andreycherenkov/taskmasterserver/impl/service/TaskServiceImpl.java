package ru.andreycherenkov.taskmasterserver.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.service.TaskService;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;
import ru.andreycherenkov.taskmasterserver.db.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public ResponseEntity<List<TaskDtoResponse>> getTasks(String userId) {
        throw new UnsupportedOperationException();
    }

    //todo method
    @Override
    public ResponseEntity<TaskDtoResponse> getTask(String taskId) {
        Task task = taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(() -> new IllegalArgumentException("Task not found")); //todo продумать возврат ошибок
        return null;
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
