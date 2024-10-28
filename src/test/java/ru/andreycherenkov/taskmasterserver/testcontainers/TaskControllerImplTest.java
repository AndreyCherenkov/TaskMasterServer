package ru.andreycherenkov.taskmasterserver.testcontainers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.andreycherenkov.taskmasterserver.api.dto.TaskDtoCreate;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;
import ru.andreycherenkov.taskmasterserver.db.repository.TaskRepository;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
import ru.andreycherenkov.taskmasterserver.impl.mapper.TaskMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerImplTest {

    private static final String POSTGRES_IMAGE = "postgres:16-alpine";
    private static final String USERNAME = "username";
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PASSWORD = "testPassword";
    private static final String TASK_TITLE = "Test title";
    private static final String TASK_DESCRIPTION = "Test description";
    private static final String INVALID_UUID = "invalid-uuid-format";
    private static final String REQUEST_MAPPING = "/api/v1/tasks";
    private static final String HEADER_USER_ID = "User-Id";

    private UUID userId;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            POSTGRES_IMAGE
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        taskRepository.deleteAll();
        userRepository.deleteAll();
        ApplicationUser user = new ApplicationUser(
                null,
                USERNAME,
                USER_EMAIL,
                USER_PASSWORD,
                LocalDate.now(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    public void saveValidTaskDtoShouldReturnStatus200() {
        TaskDtoCreate taskDtoCreate = TaskDtoCreate.builder()
                .title(TASK_TITLE)
                .userId(userId)
                .description(TASK_DESCRIPTION)
                .status(TaskStatus.NEW)
                .build();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(taskDtoCreate)
                .post(REQUEST_MAPPING)
                .then()
                .statusCode(200);
    }

    @Test
    public void saveTaskWithoutUserShouldReturnStatus400() {
        TaskDtoCreate taskDtoCreate = TaskDtoCreate.builder()
                .title(TASK_TITLE)
                .userId(UUID.randomUUID())
                .description(TASK_DESCRIPTION)
                .status(TaskStatus.NEW)
                .build();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(taskDtoCreate)
                .post(REQUEST_MAPPING)
                .then()
                .statusCode(400);
    }

    @Test
    public void saveInvalidDataShouldReturnStatus500() {
        ApplicationUser invalidData = new ApplicationUser();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(invalidData)
                .post(REQUEST_MAPPING)
                .then()
                .statusCode(400);
    }

    @Test
    public void getTasksForValidUserShouldReturnStatus200() {
        List<TaskDtoCreate> tasks = List.of(
                TaskDtoCreate.builder()
                        .title(TASK_TITLE)
                        .userId(userId)
                        .description(TASK_DESCRIPTION)
                        .status(TaskStatus.IN_PROGRESS)
                        .build(),
                TaskDtoCreate.builder()
                        .title(TASK_TITLE)
                        .userId(userId)
                        .description(TASK_DESCRIPTION)
                        .status(TaskStatus.NEW)
                        .build()
        );
        taskRepository.saveAll(tasks.stream()
                .map(taskMapper::toTask)
                .collect(Collectors.toList()));

        given()
                .contentType(ContentType.JSON)
                .when()
                .header(HEADER_USER_ID, userId)
                .get(REQUEST_MAPPING)
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    @Test
    public void getTasksForNonExistentUserShouldReturnStatus404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .header(HEADER_USER_ID, UUID.randomUUID())
                .get(REQUEST_MAPPING)
                .then()
                .statusCode(404);
    }

    @Test
    public void getTasksWithInvalidUserIdFormatShouldReturnStatus400() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .header(HEADER_USER_ID, INVALID_UUID)
                .get(REQUEST_MAPPING)
                .then()
                .statusCode(400);
    }

    @Test
    public void getTaskForNonExistentTaskShouldReturnStatus404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(REQUEST_MAPPING + "/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    public void getTaskWithInvalidTaskIdFormatShouldReturnStatus400() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(REQUEST_MAPPING + "/" + INVALID_UUID)
                .then()
                .statusCode(400);
    }

    @Test
    public void getTaskForExistingTaskShouldReturnStatus200() {
        TaskDtoCreate taskDtoResponse = TaskDtoCreate.builder()
                .title(TASK_TITLE)
                .userId(userId)
                .description(TASK_DESCRIPTION)
                .status(TaskStatus.IN_PROGRESS)
                .build();
        Task task = taskRepository.save(taskMapper.toTask(taskDtoResponse));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(REQUEST_MAPPING + "/" + task.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteTaskForNonExistentTaskShouldReturnStatus404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(REQUEST_MAPPING + "/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteTaskWithInvalidTaskIdFormatShouldReturnStatus400() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(REQUEST_MAPPING + "/" + INVALID_UUID)
                .then()
                .statusCode(400);
    }

    @Test
    public void deleteTaskForExistingTaskShouldReturnStatus204() {
        TaskDtoCreate taskDtoResponse = TaskDtoCreate.builder()
                .title(TASK_TITLE)
                .userId(userId)
                .description(TASK_DESCRIPTION)
                .status(TaskStatus.IN_PROGRESS)
                .build();
        Task task = taskRepository.save(taskMapper.toTask(taskDtoResponse));

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(REQUEST_MAPPING + "/" + task.getId())
                .then()
                .statusCode(204);
    }
}
