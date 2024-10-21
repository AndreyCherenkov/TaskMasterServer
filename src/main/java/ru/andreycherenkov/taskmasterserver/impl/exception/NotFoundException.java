package ru.andreycherenkov.taskmasterserver.impl.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
