package ru.andreycherenkov.taskmasterserver.db.entity.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {

    NEW("новая"),
    IN_PROGRESS("в процессе"),
    DONE("выполнена");

    private String description;

    TaskStatus(String description) {
        this.description = description;
    }
}
