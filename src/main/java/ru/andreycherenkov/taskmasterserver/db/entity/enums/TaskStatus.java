package ru.andreycherenkov.taskmasterserver.db.entity.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {

    NEW("новая"),
    IN_PROGRESS("в процессе"),
    DONE("выполнена");

    private String status;

    TaskStatus(String description) {
        this.status = description;
    }
}
