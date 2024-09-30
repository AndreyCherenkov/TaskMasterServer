package ru.andreycherenkov.taskmasterserver.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.andreycherenkov.taskmasterserver.db.entity.enums.TaskStatus;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiConstants {

    public static final String FIRSTNAME = "Жора";
    public static final String LASTNAME = "Сракандаев";
    public static final String USERNAME = "nagibator228";
    public static final String PASSWORD = "qwerty123";
    public static final String CONFIRMED_PASSWORD = "qwerty123";
    public static final String EMAIL = "example@mail.ru";
    public static final String TASK_TITLE = "Important task";
    public static final String TASK_DESCRIPTION = "Important task description";
    public static final TaskStatus TASK_STATUS = TaskStatus.NEW;
    public static final LocalDateTime DUE_DATE = LocalDateTime.now();
    public static final Integer AGE = 54;

}
