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
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
import ru.andreycherenkov.taskmasterserver.impl.mapper.UserMapper;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerImplTest {

    private static final String POSTGRES_IMAGE = "postgres:16-alpine";
    private static final String USERNAME = "username";
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PASSWORD = "testPassword";
    private static final String INVALID_UUID = "invalid-uuid-format";
    private static final String REQUEST_MAPPING = "/api/v1/users";
    private static final String HEADER_USER_ID = "User-Id";

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
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        userRepository.deleteAll();
    }

    @Test
    public void createUserShouldReturnStatus200() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .username(USERNAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(userCreateDto)
                .when()
                .post(REQUEST_MAPPING + "/register")
                .then()
                .statusCode(200);
    }

    @Test
    public void createUserShouldReturnStatus400WhenPasswordsDoNotMatch() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .username(USERNAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD + "1")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(userCreateDto)
                .when()
                .post(REQUEST_MAPPING + "/register")
                .then()
                .statusCode(400);
    }

    @Test
    public void getUserForExistingUserShouldReturnStatus200() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .username(USERNAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD)
                .build();
        ApplicationUser user = userRepository.save(userMapper.toUser(userCreateDto));

        given()
                .contentType(ContentType.JSON)
                .when()
                .header(HEADER_USER_ID, user.getId())
                .get(REQUEST_MAPPING)
                .then()
                .statusCode(200);
    }

    @Test
    public void getUserForNonExistingUserShouldReturnStatus404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .header(HEADER_USER_ID, UUID.randomUUID())
                .get(REQUEST_MAPPING)
                .then()
                .statusCode(404);
    }

    //todo написать тесты на невалидный UUID (после добавления логики в сервис)
}
