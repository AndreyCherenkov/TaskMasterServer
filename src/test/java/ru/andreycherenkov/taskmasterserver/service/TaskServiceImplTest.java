package ru.andreycherenkov.taskmasterserver.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoResponse;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;
import ru.andreycherenkov.taskmasterserver.db.repository.TaskRepository;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
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
    UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private static final UUID USER_UUID = UUID.randomUUID();
    private static final UUID TASK_UUID = UUID.randomUUID();
    private static final String INVALID_UUID = "invalid-uuid-format";

    @Test
    public void getTasksReturnOkWhenTasksExist() {
        Task task = new Task();
        TaskDtoResponse taskDtoResponse = new TaskDtoResponse();

        when(userRepository.existsById(USER_UUID)).thenReturn(true);
        when(taskRepository.findAllTasksByUserId(USER_UUID)).thenReturn(List.of(task));
        when(taskMapper.taskToTaskDtoResponse(task)).thenReturn(taskDtoResponse);

        ResponseEntity<List<TaskDtoResponse>> response = taskService.getTasks(USER_UUID.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(taskDtoResponse, response.getBody().getFirst());

        verify(userRepository).existsById(USER_UUID);
        verify(taskRepository).findAllTasksByUserId(USER_UUID);
        verify(taskMapper).taskToTaskDtoResponse(task);
    }

    @Test
    public void getTasksReturnEmptyListWhenTasksNotFound() {
        when(taskRepository.findAllTasksByUserId(USER_UUID)).thenReturn(Collections.emptyList());
        when(userRepository.existsById(USER_UUID)).thenReturn(true);

        ResponseEntity<List<TaskDtoResponse>> response = taskService.getTasks(USER_UUID.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(taskRepository).findAllTasksByUserId(USER_UUID);
        verify(taskMapper, never()).taskToTaskDtoResponse(any());
    }

    @Test
    public void getTasksReturnBadRequestWhenUserIdIsInvalid() {
        ResponseEntity<List<TaskDtoResponse>> response = taskService.getTasks(INVALID_UUID);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(userRepository, never()).existsById(any());
        verify(taskRepository, never()).findAllTasksByUserId(any());
    }

    @Test
    public void getTasksReturnNotFoundWhenUserDoesNotExist() {
        when(userRepository.existsById(USER_UUID)).thenReturn(false);

        ResponseEntity<List<TaskDtoResponse>> response = taskService.getTasks(USER_UUID.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(taskRepository, never()).findAllTasksByUserId(any());
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

    @Test
    public void testCreateTaskWithValidData() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(USER_UUID);

        TaskDtoCreate taskDtoCreate = new TaskDtoCreate();
        taskDtoCreate.setUserId(applicationUser.getId());
        taskDtoCreate.setStatus(TaskStatus.NEW);

        TaskDtoResponse taskDtoResponse = new TaskDtoResponse();
        Task task = new Task();
        task.setUserId(taskDtoCreate.getUserId());

        when(userRepository.existsById(applicationUser.getId())).thenReturn(true);
        when(taskMapper.toTask(taskDtoCreate)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskDtoResponse(task)).thenReturn(taskDtoResponse);

        ResponseEntity<TaskDtoResponse> response = taskService.createTask(taskDtoCreate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDtoResponse, response.getBody());

        verify(userRepository).existsById(applicationUser.getId());
        verify(taskMapper).toTask(taskDtoCreate);
        verify(taskRepository).save(task);
        verify(taskMapper).taskToTaskDtoResponse(task);
    }

    @Test
    public void testCreateTaskWithNonExistentUser() {
        TaskDtoCreate taskDtoCreate = new TaskDtoCreate();
        taskDtoCreate.setUserId(USER_UUID);
        taskDtoCreate.setStatus(TaskStatus.NEW);

        when(userRepository.existsById(taskDtoCreate.getUserId())).thenReturn(false);

        ResponseEntity<TaskDtoResponse> response = taskService.createTask(taskDtoCreate);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(userRepository).existsById(taskDtoCreate.getUserId());
    }

    @Test
    public void testDeleteTaskSuccess() {
        when(taskRepository.existsById(TASK_UUID)).thenReturn(true);

        ResponseEntity<Void> response = taskService.deleteTask(TASK_UUID.toString());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskRepository).deleteById(TASK_UUID);
    }

    @Test
    public void testDeleteTaskNotFound() {
        when(taskRepository.existsById(TASK_UUID)).thenReturn(false);

        ResponseEntity<Void> response = taskService.deleteTask(TASK_UUID.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskRepository, never()).deleteById(any());
    }
}
