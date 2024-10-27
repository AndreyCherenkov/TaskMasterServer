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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

//todo написать yaml со свойствами, для интеграционных тестов
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerImplTest {

    @LocalServerPort
    private Integer port;

    private static final String POSTGRES_IMAGE = "postgres:16-alpine";
    private static final String USERNAME_DB = "username";

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

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveTaskShouldReturnStatus200() {
        ApplicationUser user = new ApplicationUser(
                null,
                USERNAME_DB,
                "test@gmail.com",
                "pwd",
                LocalDate.now(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
        userRepository.save(user);
        UUID userId = user.getId();

        TaskDtoCreate taskDtoCreate = TaskDtoCreate.builder()
                .title("Test title")
                .userId(userId)
                .description("descr")
                .status(TaskStatus.NEW)
                .build();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(taskDtoCreate)
                .post("/api/v1/tasks")
                .then()
                .statusCode(200);
    }

}
