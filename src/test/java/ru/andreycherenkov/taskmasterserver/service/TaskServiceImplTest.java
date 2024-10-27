package ru.andreycherenkov.taskmasterserver.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;
import ru.andreycherenkov.taskmasterserver.db.repository.TaskRepository;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.mapper.TaskMapper;
import ru.andreycherenkov.taskmasterserver.impl.service.TaskServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private static final UUID USER_UUID = UUID.randomUUID();
    private static final UUID TASK_UUID = UUID.randomUUID();

    @Test
    public void getTasksReturnSomeTasksWhenTasksExist() {

        Task task = new Task();
        TaskDtoResponse taskDtoResponse = new TaskDtoResponse();

        when(taskRepository.findAllTasksById(USER_UUID)).thenReturn(List.of(task));
        when(taskMapper.taskToTaskDtoResponse(task)).thenReturn(taskDtoResponse);

        ResponseEntity<List<TaskDtoResponse>> response = taskService.getTasks(USER_UUID.toString());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(taskDtoResponse, response.getBody().getFirst());

        verify(taskRepository).findAllTasksById(USER_UUID);
        verify(taskMapper).taskToTaskDtoResponse(task);

    }

    @Test
    public void getTasksReturnEmptyListWhenTasksNotFound() {

        when(taskRepository.findAllTasksById(USER_UUID)).thenReturn(Collections.emptyList());

        ResponseEntity<List<TaskDtoResponse>> response = taskService.getTasks(USER_UUID.toString());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(taskRepository).findAllTasksById(USER_UUID);
        verify(taskMapper, never()).taskToTaskDtoResponse(any());
    }

    @Test
    void getTaskShouldReturnTaskDtoResponseWhenTaskExists() {

        Task task = new Task();
        TaskDtoResponse taskDtoResponse = new TaskDtoResponse();

        when(taskRepository.findById(TASK_UUID)).thenReturn(Optional.of(task));
        when(taskMapper.taskToTaskDtoResponse(task)).thenReturn(taskDtoResponse);

        ResponseEntity<TaskDtoResponse> response = taskService.getTask(TASK_UUID.toString());

        assertEquals(ResponseEntity.ok(taskDtoResponse), response);

        verify(taskRepository).findById(TASK_UUID);
        verify(taskMapper).taskToTaskDtoResponse(task);
    }

    @Test
    void getTaskShouldThrowNotFoundExceptionWhenTaskDoesNotExist() {

        when(taskRepository.findById(TASK_UUID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTask(TASK_UUID.toString()));

        verify(taskRepository).findById(TASK_UUID);
        verify(taskMapper, never()).taskToTaskDtoResponse(any());
    }

    //todo после создания глобального перехватчика написать тесты на данные сценарии (на BAD_REQUEST)
    @Test
    public void testCreateTaskWithNullStatus() {

        Task task = new Task();
        TaskDtoCreate taskDtoCreate = new TaskDtoCreate();
        TaskDtoResponse taskDtoResponse = new TaskDtoResponse();

        when(taskMapper.toTask(taskDtoCreate)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskDtoResponse(task)).thenReturn(taskDtoResponse);

        ResponseEntity<TaskDtoResponse> response = taskService.createTask(taskDtoCreate);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(taskDtoResponse, response.getBody());
        assertEquals(TaskStatus.NEW, taskDtoCreate.getStatus());

        verify(taskMapper).toTask(taskDtoCreate);
        verify(taskRepository).save(task);
        verify(taskMapper).taskToTaskDtoResponse(task);
    }

    @Test
    public void testDeleteTaskSuccess() {

        when(taskRepository.existsById(TASK_UUID)).thenReturn(true);

        ResponseEntity<Void> response = taskService.deleteTask(TASK_UUID.toString());

        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(taskRepository).deleteById(TASK_UUID);
    }

    @Test
    public void testDeleteTaskNotFound() {

        when(taskRepository.existsById(TASK_UUID)).thenReturn(false);

        ResponseEntity<Void> response = taskService.deleteTask(TASK_UUID.toString());

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        verify(taskRepository, never()).deleteById(any());
    }

}