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
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
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
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TaskControllerImplTest {

    @LocalServerPort
    private Integer port;

    private static final String POSTGRES_IMAGE = "postgres:16-alpine";
    private static final UUID USER_UUID = UUID.randomUUID();

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            POSTGRES_IMAGE
    );


    @AfterAll
    static void afterAll() {
        postgres.start();
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
    public void shouldReturnAllTasks() {
        ApplicationUser user = new ApplicationUser(
                USER_UUID,
                "username",
                "test@gmail.com",
                "pwd",
                LocalDate.now(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
        List<Task> tasks = List.of(
                new Task(null,
                        UUID.randomUUID(),
                        "Title1",
                        "descr1",
                        TaskStatus.NEW,
                        LocalDate.now(),
                        LocalDate.now(),
                        LocalDateTime.now()),
                new Task(null,
                        UUID.randomUUID(),
                        "Title2",
                        "descr2",
                        TaskStatus.NEW,
                        LocalDate.now(),
                        LocalDate.now(),
                        LocalDateTime.now())
        );

        userRepository.save(user);
        taskRepository.saveAll(tasks);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/tasks")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

}
